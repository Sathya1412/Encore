package com.dbsample.mysqlcrud.dto;

import com.dbsample.mysqlcrud.entity.Message;

public class PostRequest {

	private Message message;

	public PostRequest() {
		super();
	}

	public PostRequest(Message message) {
		super();
		this.message = message;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
}
