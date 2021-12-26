package com.tuana9a.controllers;

import com.tuana9a.config.AppConfig;
import com.tuana9a.service.SearchEngineService;
import lombok.AllArgsConstructor;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@RestController
@RequestMapping("/api/search")
@AllArgsConstructor
public class NewsLetterSearchController {

    private SearchEngineService searchEngineService;

    private AppConfig appConfig;

    @GetMapping
    public ResponseEntity<Object> search(@RequestParam("q") String q, @RequestParam("limit") Integer limit) throws ParseException, IOException {
        return ResponseEntity.ok(searchEngineService.searchDocument(q, limit));
    }

    @PostMapping
    public ResponseEntity<Object> addDocuments(@RequestParam("file") MultipartFile file, @RequestParam("create") Boolean create) throws ParseException, IOException {
        Path folder = Paths.get(appConfig.LUCENE_DOCUMENT_DIR);
        String fileName =  new Date().getTime() + "_" +file.getOriginalFilename();
        try {
            Files.copy(file.getInputStream(), folder.resolve(fileName));
            File document = new File(appConfig.LUCENE_DOCUMENT_DIR +"/"+fileName);
            searchEngineService.addDocuments(document, create);
            return ResponseEntity.noContent().build();
        } catch (IOException e) {
            return ResponseEntity.status(500).body(e);
        }
    }

}
