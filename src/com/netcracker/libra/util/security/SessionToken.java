package com.netcracker.libra.util.security;

public class SessionToken {
	
	public SessionToken(){};
	
	public SessionToken(Long userId, String userEmail, int userAccessLevel) {
		this.userId = userId;
		this.userEmail = userEmail;
		this.userAccessLevel=userAccessLevel;
	}
	
	private Long userId = null;
	private String userEmail = null;
	private int userAccessLevel = -1;
	
	public Long getUserId() {
		return userId;
	}

	public String getUserEmail() {
		return userEmail;
	}
	
	public int getUserAccessLevel() {
		return userAccessLevel;
	}

}
