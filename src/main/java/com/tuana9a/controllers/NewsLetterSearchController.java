package com.tuana9a.controllers;

import com.tuana9a.indexes.NewsLetterIndexer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/search")
public class NewsLetterSearchController {

    @Autowired
    private NewsLetterIndexer newsLetterIndexer;

    @GetMapping
    public ResponseEntity<Object> search(@RequestParam("field") String field, @RequestParam("q") String q, @RequestParam("limit") Integer limit) throws ParseException, IOException {
        return ResponseEntity.ok().body(newsLetterIndexer.search(field, q, limit));
    }

}
