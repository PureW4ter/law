package com.jfzy.service.bo;

public enum ArticleTypeEnum {
	ARTICLE(0), QA(1);
	
	private int id;

	private ArticleTypeEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
