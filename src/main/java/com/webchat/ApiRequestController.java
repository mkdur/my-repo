package com.webchat;

import com.webchat.model.*;
import com.webchat.service.*;
import com.webchat.service.impl.SecurityService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@EnableAutoConfiguration
public class ApiRequestController {

    @Autowired
    private ApiRequestService apiRequestService;
    @Autowired
    private UserRequestService userRequestService;
    @Autowired
    private ChatMessageService chatMessageService;

    private static final Logger logger = LoggerFactory.getLogger(ApiRequestController.class);

    @RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getHome() {
        logger.info("Api request received");

        Map<String, String> response = new HashMap<String, String>();
        try {
            ApiRequest apiRequest = new ApiRequest(new Date());
            apiRequestService.create(apiRequest);
            response.put("status", "success");
        } catch (Exception e) {
            logger.error("Error occurred while trying to process api request", e);
            response.put("status", "fail");
        }

        return response;
    }
    
    @RequestMapping(value="/signUp", method=RequestMethod.POST)
    public boolean signUp(@RequestBody UserRequest user) {
        logger.info("Signup/SignIn request received for user : " + user.getName());
       
        try {
        	boolean result = userRequestService.create(user);
            return result;

        } catch (Exception e) {
            logger.error("Error occurred while trying to process signup request", e);
            return false;
        }
    }
    
    @RequestMapping(value="/getUsers", method=RequestMethod.GET)
    public List<UserRequest> getUsers(@RequestParam(value = "s_n",required = false) String sender) {
        //logger.info("getAll users request received by " + sender);
        List<UserRequest> allUsers ;
        try {
        	 allUsers = userRequestService.getAllUsers();
        } catch (Exception e) {
            logger.error("Error occurred while trying to process signup request", e);
            allUsers = null;
        }
        return allUsers;
    }
    
    @RequestMapping(value="/send", method=RequestMethod.POST)
    public boolean send(@RequestBody ChatMessage chat) {
        logger.info("Sending Chat from " + chat.getAuthor() + " to " + chat.getReceiver());
      
        try {
        	
        	chatMessageService.create(chat);
            return true;
        } catch (Exception e) {
            logger.error("Error occurred while trying to send chat", e);
            return false;
        }
    }
    
    @RequestMapping(value="/receive/{key1}/{key2}", method=RequestMethod.GET)
    public List<ChatMessage>  receive(@PathVariable("key1") String key1,@PathVariable("key2") String key2) {
        logger.info("Receive chat message from  "+key2 + " requested by " + key1  );
      
        List<ChatMessage> allUsers ;
        try {
        	 allUsers = chatMessageService.fetch(key1, key2);
        } catch (Exception e) {
            logger.error("Error occurred while trying to process signup request", e);
            allUsers = null;
        }
        return allUsers;
    }
}
