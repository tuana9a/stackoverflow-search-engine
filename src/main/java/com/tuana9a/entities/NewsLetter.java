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
}
