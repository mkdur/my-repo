package com.webchat.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webchat.service.util.ChatMessageKey;

import java.util.Date;

@Entity
@Table(name = "chat_message")
public class ChatMessage {
    
	@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="chat_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date chatTime;
    
    
    @Column(name="chat_name")
    private String chatName;
    
    @Column(name="chat_message")
    private String message;
    
    @Column(name="chat_author")
    private String author;
    
    @Column(name="chat_receiver")
    private String receiver;
    
    public ChatMessage() {
    }

    public ChatMessage(String from, String to, String message) {
    	Long sysTime = System.currentTimeMillis();
        this.chatTime = new Date(sysTime);
        this.chatName = ChatMessageKey.computeChatMessageKey(from,to);
        this.message = message;
        this.author = from;
        this.receiver = to;
    }

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getChatTime() {
		return chatTime;
	}

	public void setChatTime(Date chatTime) {
		this.chatTime = chatTime;
	}

	public String getChatName() {
		return chatName;
	}

	public void setChatName(String chatName) {
		this.chatName = chatName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	
   
}
