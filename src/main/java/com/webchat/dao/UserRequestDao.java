package com.webchat.dao;

import java.util.List;

import com.webchat.model.UserRequest;

public interface UserRequestDao {
    void create(UserRequest userRequest);

    void update(UserRequest userRequest);

    UserRequest getUserRequestByName(String name);

    void delete(String name);
    
    List<UserRequest> getAllUsers();
}
