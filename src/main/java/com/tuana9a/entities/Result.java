package com.tuana9a.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.lucene.document.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    Document document;
    String tokenizedString;
}
