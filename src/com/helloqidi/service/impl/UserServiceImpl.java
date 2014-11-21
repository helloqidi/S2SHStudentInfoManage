package com.helloqidi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.helloqidi.dao.UserDao;
import com.helloqidi.model.User;
import com.helloqidi.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;

	@Override
	public User login(User user) throws Exception {
		return userDao.login(user);
	}

}
