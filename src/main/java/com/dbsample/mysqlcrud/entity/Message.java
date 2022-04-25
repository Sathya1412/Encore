package com.dbsample.mysqlcrud.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.dbsample.mysqlcrud.errors.ErrorMessageCodes;

@Entity
//@Table(name="MESSAGES")
public class Message {
	
	@Id
	@GeneratedValue
	private int mId;
	@NotNull(message = ErrorMessageCodes.MESSAGE_IS_BLANK)
	private String message;
	
	public Message() {
		super();
	}
	
	public Message(@NotNull(message = "Message cannot be blank. ") String message) {
		super();
		this.message = message;
	}

	public Message(int mId, @NotNull(message = "Message cannot be blank. ") String message) {
		super();
		this.mId = mId;
		this.message = message;
	}

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Message [mId=" + mId + ", message=" + message + "]";
	}
	
}
