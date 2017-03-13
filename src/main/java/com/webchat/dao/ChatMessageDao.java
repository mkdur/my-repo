package com.webchat.dao;

import java.util.List;

import com.webchat.model.ChatMessage;

public interface ChatMessageDao {
    void create(ChatMessage chatMessage);

    void update(ChatMessage chatMessage);

    ChatMessage getChatMessageByName(String name);

    void delete(String name);
    
    List<ChatMessage> getAllUsers();


	List<ChatMessage> fetch(String chatN);
}
