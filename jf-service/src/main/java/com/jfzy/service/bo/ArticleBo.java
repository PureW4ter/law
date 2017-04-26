package com.jfzy.service.bo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "article", type = "article", shards = 1, replicas = 0, refreshInterval = "-1")
public class ArticleBo {

	@Id
	private int id;
	private String title;
	private String titleImgUrl;
	private String content;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String[] tags;

	public ArticleBo(int id, String title, String titleImgUrl, String content, String[] tags) {
		this.id = id;
		this.title = title;
		this.titleImgUrl = titleImgUrl;
		this.content = content;
		this.tags = tags;
	}

	public ArticleBo() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleImgUrl() {
		return titleImgUrl;
	}

	public void setTitleImgUrl(String titleImgUrl) {
		this.titleImgUrl = titleImgUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

}
