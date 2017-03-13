package com.webchat.service;

import java.util.List;

import com.webchat.model.ChatMessage;
import com.webchat.model.UserRequest;

public interface ChatMessageService {
    void create(ChatMessage chatMessage);
    List<ChatMessage> getAllUsers();
    List<ChatMessage> fetch(String from,String to);
}
