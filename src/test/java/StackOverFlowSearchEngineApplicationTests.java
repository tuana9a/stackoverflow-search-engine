import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuana9a.entities.Record;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
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
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StackOverFlowSearchEngineApplicationTests {
    @Test
    public void test() throws IOException, ParseException {
        String filePath = "resource/newsletter1.stackoverflow.json";

        Directory memoryIndex = new RAMDirectory();
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(memoryIndex, indexWriterConfig);
        List<Record> records = new ObjectMapper().readValue(new File(filePath), new TypeReference<List<Record>>() {
        });

        records.forEach(record -> {
            try {
//                System.out.println(record.getTitle());
                Document document = new Document();
                document.add(new TextField("url", record.getUrl(), Field.Store.YES));
                document.add(new TextField("title", record.getTitle(), Field.Store.YES));
                document.add(new TextField("content", record.getContent(), Field.Store.YES));
                indexWriter.addDocument(document);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        indexWriter.close();

        Query query = new QueryParser("content", analyzer)
                .parse("html");

        IndexReader indexReader = DirectoryReader.open(memoryIndex);
        IndexSearcher searcher = new IndexSearcher(indexReader);
        TopDocs topDocs = searcher.search(query, 10);
        List<Document> documents = new ArrayList<>();
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            documents.add(searcher.doc(scoreDoc.doc));
        }
        documents.forEach(x -> {
            System.out.println("q=" + x.get("url"));
        });
    }
}
