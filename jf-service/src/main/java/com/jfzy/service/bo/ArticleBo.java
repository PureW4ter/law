package com.jfzy.service.bo;

import java.sql.Timestamp;

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
	private String shareIconUrl;
	private String content;
	private Timestamp createTime;
	private Timestamp updateTime;
	private String summary;
	private int cityId;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String[] tags;

	public ArticleBo(int id, String title, String titleImgUrl, String content, String shareIconUrl, String summary, String[] tags, 
			Timestamp createTime, Timestamp updateTime, int cityId) {
		this.id = id;
		this.title = title;
		this.titleImgUrl = titleImgUrl;
		this.shareIconUrl = shareIconUrl;
		this.summary = summary;
		this.content = content;
		this.tags = tags;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.cityId = cityId;
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

	public String getShareIconUrl() {
		return shareIconUrl;
	}

	public void setShareIconUrl(String shareIconUrl) {
		this.shareIconUrl = shareIconUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
}
