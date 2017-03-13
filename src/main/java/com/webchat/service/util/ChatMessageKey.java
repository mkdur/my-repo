package com.webchat.service.util;

public  class ChatMessageKey {

	private ChatMessageKey(){
		
	}
	
	public static String computeChatMessageKey(String from, String to){
		if(from.compareTo(to) > 0)
			return from+"_"+to;
		else
			return to+"_"+from;
	}
}
