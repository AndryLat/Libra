package com.netcracker.libra.util.security;

public class SessionToken {
	
	public SessionToken(){};
	
	public SessionToken(Integer userId, String userEmail, int userAccessLevel) {
		this.userId = userId;
		this.userEmail = userEmail;
		this.userAccessLevel=userAccessLevel;
	}
	
	private Integer userId = null;
	private String userEmail = null;
	private int userAccessLevel = -1;
	private boolean appFormFlag = false;
	
	public Integer getUserId() {
		return userId;
	}

	public String getUserEmail() {
		return userEmail;
	}
	
	public int getUserAccessLevel() {
		return userAccessLevel;
	}

	public boolean getAppFormFlag() {
		return appFormFlag;
	}

	public void setAppFormFlag(boolean appFormFlag) {
		this.appFormFlag = appFormFlag;
	}


}
