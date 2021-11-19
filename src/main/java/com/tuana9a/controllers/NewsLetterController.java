package com.tuana9a.controllers;

import com.tuana9a.entities.NewsLetter;
import com.tuana9a.indexes.NewsLetterIndexer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/newsletter")
public class NewsLetterController {

    @Autowired
    private NewsLetterIndexer newsLetterIndexer;

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam("field") String field, @RequestParam("q") String q, @RequestParam("limit") Integer limit) throws ParseException, IOException {
        return ResponseEntity.ok().body(newsLetterIndexer.search(field, q, limit));
    }

    @PostMapping("/index")
    public ResponseEntity<Object> search(@RequestBody List<NewsLetter> newsLetters) throws IOException {
        Object result = newsLetterIndexer.index(newsLetters);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/index")
    public ResponseEntity<Object> clear() throws IOException {
        Object result = newsLetterIndexer.clear();
        return ResponseEntity.ok().body(result);
    }

}
