package com.gogo.domain;

import com.gogo.domain.helper.DomainStateHelper;

public class GoError extends BaseDomain {
	
	private String code;
	private String message;
	@Override
	public String getCategory() {
		return DomainStateHelper.ERROR_FLAG;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
