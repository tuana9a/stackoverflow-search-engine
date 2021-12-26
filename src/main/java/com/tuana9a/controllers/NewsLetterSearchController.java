package com.tuana9a.controllers;

import com.tuana9a.service.SearchEngineService;
import lombok.AllArgsConstructor;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/search")
@AllArgsConstructor
public class NewsLetterSearchController {
    SearchEngineService searchEngineService;

    @GetMapping
    public ResponseEntity<Object> search(@RequestParam("q") String q, @RequestParam("limit") Integer limit) throws ParseException, IOException {
        return ResponseEntity.ok(searchEngineService.searchDocument(q, limit));
    }

    @PostMapping
    public ResponseEntity<Object> addDocuments(@RequestParam("file") MultipartFile file) throws ParseException, IOException {
        String fileName =  new Date().getTime() + "_" +file.getOriginalFilename();
        return null;
    }

}
