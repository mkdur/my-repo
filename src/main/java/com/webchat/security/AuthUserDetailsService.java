package com.webchat.security;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.webchat.ApiRequestController;
import com.webchat.dao.UserRequestDao;
import com.webchat.model.UserRequest;

@Service
public class AuthUserDetailsService implements UserDetailsService {

	@Autowired
    private UserRequestDao userRequestDao;
	private static final Logger logger = LoggerFactory.getLogger(ApiRequestController.class);
	@Override
	public UserDetails loadUserByUsername(String arg0)
			throws UsernameNotFoundException {
		/*logger.info("in AuthUserDetailsService :"+arg0);
		UserRequest user = userRequestDao.getUserRequestByName(arg0);
		if(user == null){
			throw new UsernameNotFoundException("Username " + arg0 + " not	found");
		}
		return new User(user.getName(), user.getPassword(), null);*/
		
		UserRequest user = userRequestDao.getUserRequestByName(arg0);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        
            grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
     

        return new User(user.getName(), user.getPassword(), grantedAuthorities);

	}
	
	

}
