package com.gil.whatsnew.bean;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Image {
	
	private String url;
	private String height;
	private String width;
	private String thumbnail;
	private String thumbnailHeight;
	private String thumbnailWidth;
	private String base64Encoding;
	private String name;
	private String title;
	private String imageWebSearchUrl;
	private String webpageUrl;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getThumbnailHeight() {
		return thumbnailHeight;
	}
	
	public String getThumbnailWidth() {
		return thumbnailWidth;
	}
	public void setThumbnailWidth(String thumbnailWidth) {
		this.thumbnailWidth = thumbnailWidth;
	}
	
	public void setThumbnailHeight(String thumbnailHeight) {
		this.thumbnailHeight = thumbnailHeight;
	}
	public String getBase64Encoding() {
		return base64Encoding;
	}
	public void setBase64Encoding(String base64Encoding) {
		this.base64Encoding = base64Encoding;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageWebSearchUrl() {
		return imageWebSearchUrl;
	}
	public void setImageWebSearchUrl(String imageWebSearchUrl) {
		this.imageWebSearchUrl = imageWebSearchUrl;
	}
	public String getWebpageUrl() {
		return webpageUrl;
	}
	public void setWebpageUrl(String webpageUrl) {
		this.webpageUrl = webpageUrl;
	}
	
	
}
