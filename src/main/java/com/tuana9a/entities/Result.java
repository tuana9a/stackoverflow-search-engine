package com.tuana9a.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.lucene.document.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    String title;
    String link;
    String content;
    String tokenizedString;
    public Result(Document document){
        this.title = document.get("title");
        this.link = document.get("url");
        this.content = document.get("content");
    }
}
