package com.upemor.petsorerest.exception;

import com.upemor.petsorerest.model.User;

public class UserErrorException extends User {

	private String errorMsg;

	public UserErrorException(final String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

}