package com.tuana9a.entities;


import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewsLetter {
    private String url;
    private String title;
    private String content;

    public String getUrl() { return this.url; }
    public String getTitle() { return this.title; }
    public String getContent() { return this.content; }
}