package com.helloqidi.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.helloqidi.dao.UserDao;
import com.helloqidi.model.User;

@Repository
public class UserDaoImpl implements UserDao {
	
	private SessionFactory sessionFactory;



	@Override
	public User login(User user) throws Exception {
		User resultUser=null;
		Session session=this.getSession();
		Query query=session.createQuery("from User u where u.userName=? and u.password=?");
		query.setString(0, user.getUserName());
		query.setString(1, user.getPassword());
		@SuppressWarnings("unchecked")
		List<User> userList=(List<User>)query.list();
		if(userList.size()>0){
			resultUser=userList.get(0);
		}
		return resultUser;
	}

	@Resource
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}

}
