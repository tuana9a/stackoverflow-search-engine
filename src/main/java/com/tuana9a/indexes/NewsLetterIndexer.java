package com.tuana9a.indexes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuana9a.entities.NewsLetter;
import lombok.Setter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Component
public class NewsLetterIndexer {
    private Analyzer analyzer;
    private Directory directory;

    public NewsLetterIndexer() {

    }

    public void index(List<NewsLetter> newsLetters) throws IOException {
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

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

    public List<NewsLetter> search(String field, String q, Integer limit) throws ParseException, IOException {
        Query query = new QueryParser(field, analyzer)
                .parse(q);

        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(indexReader);
        TopDocs topDocs = searcher.search(query, limit);
        List<Document> documents = new ArrayList<>();
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            documents.add(searcher.doc(scoreDoc.doc));
        }

        return documents.stream()
                .map(fields -> NewsLetter.builder().url(fields.get("url"))
                        .content(fields.get("content")).title(fields.get("title")).build())
                .collect(Collectors.toList());
    }

}
