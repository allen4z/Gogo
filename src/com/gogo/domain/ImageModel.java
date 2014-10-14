package com.gogo.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

public class ImageModel {

	//主键
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="im_id",length=32,nullable=false)
	private String imageModelID;
	
	//指纹信息
	@Column(name="act_name",length=32,nullable=false)
	private String md5;
	
	//路径信息
	@Column(name="act_name",length=200,nullable=false)
	private String url;

	public String getImageModelID() {
		return imageModelID;
	}

	public void setImageModelID(String imageModelID) {
		this.imageModelID = imageModelID;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
