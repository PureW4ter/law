package com.jfzy.service.weixin.dto;

public class MessageRequestDto {

	public static class MessageData {

		private MessageLine first;
		private MessageLine keyword1;
		private MessageLine keyword2;
		private MessageLine keyword3;
		private MessageLine keyword4;
		private MessageLine remark;

		public MessageLine getFirst() {
			return first;
		}

		public void setFirst(MessageLine first) {
			this.first = first;
		}

		public MessageLine getKeyword1() {
			return keyword1;
		}

		public void setKeyword1(MessageLine keyword1) {
			this.keyword1 = keyword1;
		}

		public MessageLine getKeyword2() {
			return keyword2;
		}

		public void setKeyword2(MessageLine keyword2) {
			this.keyword2 = keyword2;
		}

		public MessageLine getKeyword3() {
			return keyword3;
		}

		public void setKeyword3(MessageLine keyword3) {
			this.keyword3 = keyword3;
		}

		public MessageLine getKeyword4() {
			return keyword4;
		}

		public void setKeyword4(MessageLine keyword4) {
			this.keyword4 = keyword4;
		}

		public MessageLine getRemark() {
			return remark;
		}

		public void setRemark(MessageLine remark) {
			this.remark = remark;
		}
	}

	public static class MessageLine {
		private String value;
		private String color;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}
	}

	private String touser = "OPENID";
	private String template_id;
	private String url;
	private MessageData data;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String templateId) {
		this.template_id = templateId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public MessageData getData() {
		return data;
	}

	public void setData(MessageData data) {
		this.data = data;
	}

}
