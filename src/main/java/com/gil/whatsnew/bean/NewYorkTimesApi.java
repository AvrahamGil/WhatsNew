package com.gil.whatsnew.bean;

import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class NewYorkTimesApi {

	private String id;
	
	private String section;
	
	private String title;
	
	private String description;
	
	private String url;
	
	private String urlToImage;
	
	private Multimedia[] multimedia;
	
	private String newsType = "NewYorkTimes";
	
	
	public String getId() {
		return id;
	}
	
	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
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

	public String getUrlToImage() {
		return urlToImage;
	}

	public void setUrlToImage(String urlToImage) {
		this.urlToImage = urlToImage;
	}

	public Multimedia[] getMultimedia() {
		return multimedia;
	}

	public void setMultimedia(Multimedia[] multimedia) {
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Arrays.hashCode(multimedia);
		result = prime * result + ((newsType == null) ? 0 : newsType.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((urlToImage == null) ? 0 : urlToImage.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (!Arrays.equals(multimedia, other.multimedia))
			return false;
		if (newsType == null) {
			if (other.newsType != null)
				return false;
		} else if (!newsType.equals(other.newsType))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
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
		if (urlToImage == null) {
			if (other.urlToImage != null)
				return false;
		} else if (!urlToImage.equals(other.urlToImage))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NewYorkTimesApi [id=" + id + ", section=" + section + ", title=" + title + ", description="
				+ description + ", url=" + url + ", urlToImage=" + urlToImage + ", multimedia="
				+ Arrays.toString(multimedia) + ", newsType=" + newsType + "]";
	}

	
}
