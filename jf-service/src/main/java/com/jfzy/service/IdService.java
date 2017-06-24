package com.jfzy.service;

public interface IdService {

	void init(int type, int page);

	int generateId(int type, int page);

}
