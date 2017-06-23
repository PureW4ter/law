package com.jfzy.service;

import com.jfzy.service.bo.LawyerReplyBo;

public interface LawyerReplyService {

	void createReply(LawyerReplyBo bo);

	LawyerReplyBo getReply(int orderId);

	void scoreReply(int replyId, int score);
	
	public void confirmReply(int orderId);

}
