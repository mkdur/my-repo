package com.webchat.dao;

import com.webchat.model.ApiRequest;

public interface ApiRequestDao {
    void create(ApiRequest apiRequest);

    void update(ApiRequest apiRequest);

    ApiRequest getApiRequestById(long id);

    void delete(long id);
}
