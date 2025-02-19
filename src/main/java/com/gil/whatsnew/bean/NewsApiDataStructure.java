package com.gil.whatsnew.bean;


import java.util.List;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document("articles")
@ToString
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class NewsApiDataStructure {

		private List<String> country;
		private String sentiment;
		private String pubDateTZ;
		private List<String> keywords;
		private String link;
		private String description;
		private String language;
		private String title;
		private String content;
		private String source_url;
		private String article_id;
		private String video_url;
		private int source_priority;
		private String source_name;
		private List<String> creator;
		private String sentiment_stats;
		private String image_url;
		private String ai_region;
		private boolean duplicate;
		private String pubDate;
		private String source_icon;
		private String ai_org;
		private String ai_tag;
		private String source_id;
		private List<String> category;

		public NewsApiDataStructure() {

		}
}
