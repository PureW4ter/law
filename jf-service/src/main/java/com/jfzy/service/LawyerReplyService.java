package com.jfzy.service;

import com.jfzy.service.bo.LawyerReplyBo;

public interface LawyerReplyService {

	void createReply(LawyerReplyBo bo, boolean isTemp);

	LawyerReplyBo getReply(int orderId);

	void scoreReply(int replyId, double score);
	
	public void confirmReply(int orderId);
	
	void addReplyPhotos(String[] picList, int orderId);

}
