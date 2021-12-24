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
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SearchEngineImpl implements SearchEngineService {

    @Override
    public Directory getDirectory() throws IOException {
        return FSDirectory.open(Paths.get(AppConfig.LUCENE_INDEXES_DIR));
    }

    @Override
    public EnglishAnalyzer getAnalyzer() {
        EnglishAnalyzer analyzer = new EnglishAnalyzer();
        analyzer.getStopwordSet().add("'ve");
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
        IndexWriter indexWriter = new IndexWriter(getDirectory(), indexWriterConfig);

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
        DirectoryReader ireader = DirectoryReader.open(getDirectory());
        IndexSearcher isearcher = new IndexSearcher(ireader);

        EnglishAnalyzer analyzer = getAnalyzer();
        MultiFieldQueryParser parsers = new MultiFieldQueryParser(new String[]{"content","title"}, analyzer);
        parsers.setDefaultOperator(QueryParser.Operator.OR);

        Query query = parsers.parse(keyword);

        ScoreDoc[] hits = isearcher.search(query, limit).scoreDocs;

        List<Result> results = new ArrayList<>();

        for(int i = 0; i < hits.length; i++){
            Document hitDoc = isearcher.doc(hits[i].doc);
            Result result = new Result(hitDoc);

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
                    tokenizedString+= " [" + term + " " + termFrequency + " " +docFrequency+ "]";
                }
                System.out.println();
                tokenStream.end();
            }finally {
                tokenStream.close();
            }
            result.setTokenizedString(tokenizedString);
        }
        return results;
    }
}
