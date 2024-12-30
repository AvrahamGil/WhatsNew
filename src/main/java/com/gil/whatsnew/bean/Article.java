package com.gil.whatsnew.bean;


import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document("articles")
public class Article {

	@Id
	private String id;
	private String title;
	private String url;
	private String description;
	private String language;
	private String image;
	private String author;
	private String published;
	private boolean isLiked;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublished() {
		return published;
	}
	public void setPublished(String published) {
		this.published = published;
	}

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, image, isLiked, language, author, title, published, url);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				 && Objects.equals(image, other.image)
				&& isLiked == other.isLiked
				&& Objects.equals(language, other.language) && Objects.equals(author, other.author)
				&& Objects.equals(title, other.title) && Objects.equals(published, other.published)
				&& Objects.equals(url, other.url);
	}

	@Override
	public String toString() {
		return "Article{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", url='" + url + '\'' +
				", description='" + description + '\'' +
				", language='" + language + '\'' +
				", imageUrl='" + image + '\'' +
				", newsType='" + author + '\'' +
				", published='" + published + '\'' +
				", isLiked=" + isLiked +
				'}';
	}
}
