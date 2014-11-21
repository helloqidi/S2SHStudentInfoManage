package com.helloqidi.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;

import com.helloqidi.model.User;
import com.helloqidi.service.UserService;
import com.helloqidi.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Namespace("/")
@Action(value="login",results={@Result(name="success",type="redirect",location="/main.jsp"),@Result(name="error",location="/index.jsp")})
public class LoginAction extends ActionSupport implements ServletRequestAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private UserService userService;
	
	private User user;
	private String error;
	private String imageCode;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	

	public String getImageCode() {
		return imageCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}


	HttpServletRequest request;
	
	@Override
	public String execute() throws Exception {
		// 获取Session
		HttpSession session=request.getSession();
		if(StringUtil.isEmpty(user.getUserName())||StringUtil.isEmpty(user.getPassword())){
			error="用户名或密码为空！";
			return ERROR;
		}
		if(StringUtil.isEmpty(imageCode)){
			error="验证码为空！";
			return ERROR;
		}
		if(!imageCode.equals(session.getAttribute("sRand"))){
			error="验证码错误！";
			return ERROR;
		}
		try {
			User currentUser=userService.login(user);
			if(currentUser==null){
				error="用户名或密码错误！";
				return ERROR;
			}else{
				session.setAttribute("currentUser", currentUser);
				return SUCCESS;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
	}

	
}
