package com.helloqidi.dao;

import com.helloqidi.model.User;

public interface UserDao {
	
	public User login(User user) throws Exception;

}
