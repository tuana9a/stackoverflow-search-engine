package com.tuana9a.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuana9a.config.AppConfig;
import com.tuana9a.entities.NewsLetter;
import com.tuana9a.entities.Result;
import lombok.AllArgsConstructor;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SearchEngineImpl implements SearchEngineService {
    private AppConfig appConfig;

    @Override
    public Directory getFSDirectory() throws IOException {
        return FSDirectory.open(Paths.get(appConfig.LUCENE_INDEXES_DIR));
    }

    @Override
    public EnglishAnalyzer getAnalyzer() {
        EnglishAnalyzer analyzer = new EnglishAnalyzer();
        return analyzer;
    }

    @Override
    public void addDocuments(File file, boolean create) throws IOException {
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(getAnalyzer());
        if(create){
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        }else{
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.APPEND);
        }
        ByteBuffersDirectory byteBuffersDirectory = new ByteBuffersDirectory();
        Directory fsDirectory = getFSDirectory();
        IndexWriter indexWriter = new IndexWriter(fsDirectory, indexWriterConfig);

        List<NewsLetter> newsLetters = new ObjectMapper().readValue(file, new TypeReference<List<NewsLetter>>() {
        });

        newsLetters.forEach(newsLetter -> {
            try {
                Document document = new Document();
                document.add(new TextField("url", newsLetter.getUrl(), Field.Store.YES));
                document.add(new TextField("title", newsLetter.getTitle(), Field.Store.YES));
                document.add(new TextField("content", newsLetter.getContent(), Field.Store.YES));
                indexWriter.addDocument(document);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        indexWriter.close();
    }

    @Override
    public List<Result> searchDocument(String keyword, int limit) throws IOException, ParseException {
        DirectoryReader ireader = DirectoryReader.open(getFSDirectory());
        IndexSearcher isearcher = new IndexSearcher(ireader);

        EnglishAnalyzer analyzer = getAnalyzer();

        TokenStream searchTokenStream = analyzer.tokenStream("content", keyword);
        CharTermAttribute searchCharTermAttribute = searchTokenStream.addAttribute(CharTermAttribute.class);
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        BooleanQuery.Builder builder1 = new BooleanQuery.Builder();

        try{
            searchTokenStream.reset();
            while(searchTokenStream.incrementToken()){
                String word = searchCharTermAttribute.toString();
                Term term = new Term("content", word);
                TermQuery termQuery = new TermQuery(term);
                builder.add(termQuery, BooleanClause.Occur.SHOULD);
                builder1.add(termQuery, BooleanClause.Occur.MUST);
            }
            searchTokenStream.end();
        }finally {
            searchTokenStream.close();
        }

        builder.add(builder1.build(), BooleanClause.Occur.SHOULD);
        Query query = builder.build();
        System.out.println(query.toString());

        ScoreDoc[] hits = isearcher.search(query, limit).scoreDocs;
        List<Result> results = new ArrayList<>();

        for(int i = 0; i < hits.length; i++){
            Document hitDoc = isearcher.doc(hits[i].doc);
            Result result = new Result(hitDoc);
            result.setScore(hits[i].score);

            TokenStream tokenStream = analyzer.tokenStream("content", hitDoc.get("content"));

            CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);

            String tokenizedString = new String();
            try{
                tokenStream.reset();
                while(tokenStream.incrementToken()){
                    String word = charTermAttribute.toString();
                    Term term = new Term("content", word);
                    long termFrequency = ireader.totalTermFreq(term);
                    long docFrequency = ireader.docFreq(term);
                    tokenizedString+= " [" + word + " " + termFrequency + " " +docFrequency+ "]";
                }
                tokenStream.end();
            }finally {
                tokenStream.close();
            }
            result.setTokenizedString(tokenizedString);
            results.add(result);
        }

        return results;
    }
}
