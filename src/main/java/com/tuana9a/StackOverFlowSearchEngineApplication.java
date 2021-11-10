package com.tuana9a;

import com.tuana9a.config.AppConfig;
import com.tuana9a.indexes.NewsLetterIndexer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Paths;


@SpringBootApplication
public class StackOverFlowSearchEngineApplication implements CommandLineRunner {

    @Autowired
    private AppConfig config;

    @Autowired
    private NewsLetterIndexer indexer;

    public static void main(String[] args) {
        SpringApplication.run(StackOverFlowSearchEngineApplication.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
        Directory directory = FSDirectory.open(Paths.get(config.LUCENE_INDEXES_DIR)); // new RAMDirectory();
        Analyzer analyzer = new StandardAnalyzer();
        indexer.setAnalyzer(analyzer);
        indexer.setDirectory(directory);
        indexer.indexFromFile("data/data1.json");
        indexer.indexFromFile("data/data2.json");
    }
}