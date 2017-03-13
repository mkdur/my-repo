package com.webchat.service.impl;

import java.util.Date;
import java.util.List;

import com.webchat.ApiRequestController;
import com.webchat.dao.UserRequestDao;
import com.webchat.model.UserRequest;
import com.webchat.service.UserRequestService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRequestServiceImpl implements UserRequestService {

    @Autowired
    private UserRequestDao userRequestDao;
    @Autowired
    private SecurityService securityService;

    private static final Logger logger = LoggerFactory.getLogger(ApiRequestController.class);

    @Override
    public boolean create(UserRequest userRequest) {
    	
    	String name = userRequest.getName();
    	UserRequest existingUser = userRequestDao.getUserRequestByName(name);
    	if(existingUser != null && name.equalsIgnoreCase(existingUser.getName())){
    		logger.info("User Already Exists. Logging in..");
    	}else{
	    	logger.info("Create new user..");
	    	userRequest.setRequestTime(new Date(System.currentTimeMillis()));
	        userRequestDao.create(userRequest);
    	}
        return securityService.login(userRequest.getName(), userRequest.getPassword());
    }
    
    @Override
	public List<UserRequest> getAllUsers() {
    	return userRequestDao.getAllUsers();
    }
}
