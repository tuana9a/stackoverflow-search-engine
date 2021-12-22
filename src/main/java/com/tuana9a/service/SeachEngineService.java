package com.tuana9a.service;

import com.tuana9a.entities.Result;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface SeachEngineService {
    public Directory getDirectory() throws IOException;
    public EnglishAnalyzer getAnalyzer();
    public void addDocuments(File file, boolean create) throws IOException;
    public List<Result> searchDocument(String keyword, int limit) throws IOException, ParseException;
}
