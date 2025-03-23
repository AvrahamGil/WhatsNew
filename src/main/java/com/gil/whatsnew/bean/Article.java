package com.gil.whatsnew.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("articles")
@Getter
@Setter
public class Article {

    @Id
    private String articleId;
    private String title;
    private String url;
    private String description;
    private String content;
    private String category;
    private String imageUrl;
    private String published;

    public Article() {

    }
}
