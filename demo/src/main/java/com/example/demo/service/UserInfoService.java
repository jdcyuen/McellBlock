package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.UserInfo;
import com.example.demo.repository.UserDetailsRepository;

@Repository
@Transactional
public class UserInfoService {

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	public UserInfo getUserInfoByUserName(String userName) {
		short enabled = 1;
		return userDetailsRepository.findByUserNameAndEnabled(userName, enabled);
	}

	public List<UserInfo> getAllActiveUserInfo() {
		return userDetailsRepository.findAllByEnabled((short) 1);
	}

	public UserInfo getUserInfoById(Integer id) {
		return userDetailsRepository.findById(id);
	}

	public UserInfo addUser(UserInfo userInfo) {
		userInfo.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
		return userDetailsRepository.save(userInfo);
	}

	public UserInfo updateUser(UserInfo userInfo) {
		return userDetailsRepository.save(userInfo);
	}

	public void deleteUser(Integer id) {
		userDetailsRepository.deleteById(id);
	}
}


//https://github.com/riddhiparkhiya/spring-boot-security-demo/blob/master/src/main/java/com/example/service/UserInfoService.java