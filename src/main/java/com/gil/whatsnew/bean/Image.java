package com.gil.whatsnew.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
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
}
