package com.gil.whatsnew.bean;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document("articles")
public class NewYorkTimesApi {

	@Id
	private String id;
	
	private String title;
	
	private String description;
	
	private String url;
	
	private Multimedia multimedia;
	
	private String newsType = "NewYorkTimes";

	private boolean isLiked;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Multimedia getMultimedia() {
		return multimedia;
	}

	public void setMultimedia(Multimedia multimedia) {
		this.multimedia = multimedia;
	}

	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, isLiked, multimedia, newsType, title, url);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewYorkTimesApi other = (NewYorkTimesApi) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& isLiked == other.isLiked && Objects.equals(multimedia, other.multimedia)
				&& Objects.equals(newsType, other.newsType) && Objects.equals(title, other.title)
				&& Objects.equals(url, other.url);
	}

	@Override
	public String toString() {
		return "NewYorkTimesApi [id=" + id + ", title=" + title + ", description=" + description + ", url=" + url
				+ ", multimedia=" + multimedia + ", newsType=" + newsType + ", isLiked=" + isLiked + "]";
	}


}