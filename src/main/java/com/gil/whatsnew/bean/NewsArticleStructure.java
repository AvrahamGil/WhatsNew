package com.gil.whatsnew.bean;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Document("articles")
@ToString
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class NewsArticleStructure {

    private String id;
    private String title;
    private String description;
    private String url;
    private String author;
    private String image;
    private String language;
    private List<String> category;
    private String published;


    public NewsArticleStructure() {

    }
}
