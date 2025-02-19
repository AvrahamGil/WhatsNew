package com.gil.whatsnew.bean;

import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document("user_articles")
@Getter
@Setter
@EqualsAndHashCode
public class UserArticles {

	@Id
	private String id;
	private String userId;
	private String title;
	private String type;

}
