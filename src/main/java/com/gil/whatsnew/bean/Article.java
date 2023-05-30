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
	private String keyword;
	private String language;
	private boolean isSafe;
	private Image image;
	private String imageUrl;
	private String newsType;
	private String type;
	private boolean isLiked;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public boolean isSafe() {
		return isSafe;
	}
	public void setSafe(boolean isSafe) {
		this.isSafe = isSafe;
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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, image, imageUrl, isLiked, isSafe, keyword, language, newsType, title, type,
				url);
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
				&& Objects.equals(image, other.image) && Objects.equals(imageUrl, other.imageUrl)
				&& isLiked == other.isLiked && isSafe == other.isSafe && Objects.equals(keyword, other.keyword)
				&& Objects.equals(language, other.language) && Objects.equals(newsType, other.newsType)
				&& Objects.equals(title, other.title) && Objects.equals(type, other.type)
				&& Objects.equals(url, other.url);
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", url=" + url + ", description=" + description + ", keyword="
				+ keyword + ", language=" + language + ", isSafe=" + isSafe + ", image=" + image + ", imageUrl="
				+ imageUrl + ", newsType=" + newsType + ", type=" + type + ", isLiked=" + isLiked + "]";
	}



	
}
