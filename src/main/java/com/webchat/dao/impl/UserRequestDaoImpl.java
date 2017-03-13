package com.webchat.dao.impl;

import java.util.List;

import com.webchat.dao.UserRequestDao;
import com.webchat.model.UserRequest;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

@Repository
public class UserRequestDaoImpl implements UserRequestDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(UserRequest userRequest) {
        entityManager.persist(userRequest);
    }

    @Override
    public void update(UserRequest userRequest) {
        entityManager.merge(userRequest);
    }

    @Override
    public UserRequest getUserRequestByName(String name) {
        return entityManager.find(UserRequest.class, name);
    }

    @Override
    public void delete(String name) {
        UserRequest UserRequest = getUserRequestByName(name);
        if (UserRequest != null) {
            entityManager.remove(UserRequest);
        }
    }

   
	@Override
	public List<UserRequest> getAllUsers() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserRequest> cq = cb.createQuery(UserRequest.class);
        Root<UserRequest> rootEntry = cq.from(UserRequest.class);
        CriteriaQuery<UserRequest> all = cq.select(rootEntry);
        TypedQuery<UserRequest> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
	}


}
