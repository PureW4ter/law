package com.jfzy.service;

import java.util.List;

import com.jfzy.service.bo.QABo;

public interface QAService {

	void createQA(QABo bo);

	List<QABo> getUnprocessedQA(int cityId);

	void processQA(int[] qaIds);

	List<QABo> getQAByUserId(int userId);

}
