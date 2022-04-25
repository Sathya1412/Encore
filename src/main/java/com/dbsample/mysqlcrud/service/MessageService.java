package com.dbsample.mysqlcrud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbsample.mysqlcrud.entity.Message;
import com.dbsample.mysqlcrud.errors.ErrorMessageCodes;
import com.dbsample.mysqlcrud.exception.RequestException;
import com.dbsample.mysqlcrud.repository.MessageRepository;

@Service
public class MessageService {
	
	@Autowired
	private MessageRepository mRepository;

	public Message deleteMessage(int mId) {
		Message message = mRepository.findById(mId).orElse(null);
		if(message == null) {
			RequestException messageRequestException = new RequestException(ErrorMessageCodes.MID_DNE);
			throw messageRequestException;
		}
		try {
			mRepository.deleteById(mId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(message);
		return message;
	}

	public Message getMessageById(int mId) {
		Message message = null;
		try {
			message = mRepository.findById(mId).orElse(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (message == null) {
			RequestException messageRequestException = new RequestException(ErrorMessageCodes.MID_DNE);
			throw messageRequestException;
		}
		return message;
	}
	
}
