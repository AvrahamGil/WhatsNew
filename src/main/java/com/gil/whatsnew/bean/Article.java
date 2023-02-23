package com.gil.whatsnew.bean;


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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((imageUrl == null) ? 0 : imageUrl.hashCode());
		result = prime * result + (isSafe ? 1231 : 1237);
		result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
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
		Article other = (Article) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (imageUrl == null) {
			if (other.imageUrl != null)
				return false;
		} else if (!imageUrl.equals(other.imageUrl))
			return false;
		if (isSafe != other.isSafe)
			return false;
		if (keyword == null) {
			if (other.keyword != null)
				return false;
		} else if (!keyword.equals(other.keyword))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
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
		return "Article [id=" + id + ", title=" + title + ", url=" + url + ", description=" + description + ", keyword="
				+ keyword + ", language=" + language + ", isSafe=" + isSafe + ", image=" + image + ", imageUrl="
				+ imageUrl + ", newsType=" + newsType + ", type=" + type + "]";
	}


	
}
