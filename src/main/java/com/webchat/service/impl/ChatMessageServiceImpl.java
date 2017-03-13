package com.webchat.service.impl;

import java.util.Date;
import java.util.List;

import com.webchat.ApiRequestController;
import com.webchat.dao.ChatMessageDao;
import com.webchat.model.ChatMessage;
import com.webchat.model.UserRequest;
import com.webchat.service.ChatMessageService;
import com.webchat.service.util.ChatMessageKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    private ChatMessageDao chatMessageDao;
    
    private static final Logger logger = LoggerFactory.getLogger(ApiRequestController.class);

    @Override
    public void create(ChatMessage chatMessage) {
    	
    	Long sysTime = System.currentTimeMillis();
    	chatMessage.setChatTime(new Date(sysTime));
    	String name = ChatMessageKey.computeChatMessageKey(chatMessage.getAuthor(), chatMessage.getReceiver());
    	chatMessage.setChatName(name);
        chatMessageDao.create(chatMessage);
    }
    
    @Override
	public List<ChatMessage> getAllUsers() {
    	return chatMessageDao.getAllUsers();
    }

	@Override
	public List<ChatMessage> fetch(String from, String to) {
		
		String key = ChatMessageKey.computeChatMessageKey(from,to);
		return chatMessageDao.fetch(key);
		
	}
}
