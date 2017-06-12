package com.jfzy.service;

import com.jfzy.service.bo.LawyerReplyBo;

public interface LawyerReplyService {

	void createReply(LawyerReplyBo bo);

	void updateReply(LawyerReplyBo bo);

	LawyerReplyBo getReply(int orderId);

	void scoreReply(int replyId, int score);

}
