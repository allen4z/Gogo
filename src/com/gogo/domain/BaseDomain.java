package com.gogo.domain;

public class BaseDomain {
	//此属性仅用户返回给前端作为标志使用
	private String category;

	public String getCategory() {
		return this.getClass().getName();
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
}
