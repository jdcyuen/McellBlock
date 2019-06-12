package com.example.demo.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserInfo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserInfoService userInfoDAO;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserInfo userInfo = userInfoDAO.getUserInfoByUserName(userName);
		GrantedAuthority authority = new SimpleGrantedAuthority(userInfo.getRole());
		return new User(userInfo.getUserName(), userInfo.getPassword(), Arrays.asList(authority));
	}
}

//https://github.com/riddhiparkhiya/spring-boot-security-demo/blob/master/src/main/java/com/example/service/UserDetailsServiceImpl.java