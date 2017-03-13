package com.webchat.dao.impl;

import java.util.List;

import com.webchat.ApiRequestController;
import com.webchat.dao.ChatMessageDao;
import com.webchat.model.ChatMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

@Repository
public class ChatMessageDaoImpl implements ChatMessageDao {
	
	private static final Logger logger = LoggerFactory.getLogger(ApiRequestController.class); 
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(ChatMessage chatMessage) {
        entityManager.persist(chatMessage);
    }

    @Override
    public void update(ChatMessage chatMessage) {
        entityManager.merge(chatMessage);
    }

    @Override
    public ChatMessage getChatMessageByName(String name) {
        return entityManager.find(ChatMessage.class, name);
    }

    @Override
    public void delete(String name) {
        ChatMessage ChatMessage = getChatMessageByName(name);
        if (ChatMessage != null) {
            entityManager.remove(ChatMessage);
        }
    }

   
	@Override
	public List<ChatMessage> getAllUsers() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ChatMessage> cq = cb.createQuery(ChatMessage.class);
        Root<ChatMessage> rootEntry = cq.from(ChatMessage.class);
        CriteriaQuery<ChatMessage> all = cq.select(rootEntry);
        TypedQuery<ChatMessage> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
	}

	@Override
	public List<ChatMessage> fetch(String chatN) {
		// TODO Auto-generated method stub
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ChatMessage> cq = cb.createQuery(ChatMessage.class);
        Root<ChatMessage> rootEntry = cq.from(ChatMessage.class);
        
       // Predicate hasChatName = cb.equal(rootEntry.get(ChatMessage_.chat_name), today);
        //ParameterExpression<String> p = cb.parameter(String.class);
       
        //logger.info(chatN.toString());
        CriteriaQuery<ChatMessage> all = cq.select(rootEntry).where(cb.equal(rootEntry.get("chatName"), chatN))
        									.orderBy(cb.asc(rootEntry.get("chatTime")));

        TypedQuery<ChatMessage> query = entityManager.createQuery(all);
       // query.setParameter(p, name+"_"+name2);
        
        //TypedQuery<ChatMessage> allQuery = entityManager.createQuery(all);
        
        /*TypedQuery<ChatMessage> q2 =
        		entityManager.createQuery("SELECT c FROM ChatMessage c WHERE c.chatName = :p", ChatMessage.class);
        q2.setParameter(p, name+"_"+name2);
        */
        return query.getResultList();
	}


}
