//package com.dbsample.mysqlcrud.entity;
//
//import javax.persistence.Entity;
//import javax.validation.constraints.NotNull;
//
//import com.dbsample.mysqlcrud.errors.ErrorMessageCodes;
//
//@Entity
//public class LoginInfo {
//
//	@NotNull(message = ErrorMessageCodes.USERNAME_IS_BLANK)
//	private String username;
//	@NotNull(message = ErrorMessageCodes.PASSWORD_IS_BLANK)
//	private String password;
//
//	public LoginInfo() {
//		super();
//	}
//
//	public LoginInfo(@NotNull(message = "User Name cannot be blank. ") String username,
//			@NotNull(message = "Password cannot be blank. ") String password) {
//		super();
//		this.username = username;
//		this.password = password;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	@Override
//	public String toString() {
//		return "LoginInfo [username=" + username + ", password=" + password + "]";
//	}
//
//}
