package com.webchat.service;

import java.util.List;

import com.webchat.model.UserRequest;

public interface UserRequestService {
    boolean create(UserRequest userRequest);
    List<UserRequest> getAllUsers();
}
