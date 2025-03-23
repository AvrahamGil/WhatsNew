package com.gil.whatsnew.bean;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document("articles")
@ToString
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class GNewsApiStructure {

    private String image;
    private String publishedAt;
    private String description;
    private SourceStructure source; // Nested source object
    private String title;
    private String content;
    private String url;

    public GNewsApiStructure() {

    }
}
