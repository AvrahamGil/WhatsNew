package com.gil.whatsnew.bean;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((multimedia == null) ? 0 : multimedia.hashCode());
		result = prime * result + ((newsType == null) ? 0 : newsType.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
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
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (multimedia == null) {
			if (other.multimedia != null)
				return false;
		} else if (!multimedia.equals(other.multimedia))
			return false;
		if (newsType == null) {
			if (other.newsType != null)
				return false;
		} else if (!newsType.equals(other.newsType))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NewYorkTimesApi [title=" + title + ", description=" + description + ", url=" + url + ", multimedia="
				+ multimedia + ", newsType=" + newsType + "]";
	}
}