package com.gogo.domain.enums;

/**
 * 用户状态枚举
 * @author allen
 *
 */
public enum UserState {
	FORMAL(0,"正常用户状态"),DELETE(-1,"用户注销状态");
	
	private int index;
	private String description;
	
	private UserState(int index,String description){
		this.index = index;
		this.description = description;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
