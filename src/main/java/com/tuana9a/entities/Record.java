package com.tuana9a.entities;


import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Record {
    private String title;
    private String url;
    private String content;
}
