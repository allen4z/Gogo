package com.gogo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogo.dao.ImageUploadDao;
import com.gogo.domain.ImageModel;

@Service
public class ImageUploadService {

	@Autowired
	private ImageUploadDao imageUploadDao;
	
	public ImageModel loadImageUrlByMd5(String md5){
		return imageUploadDao.loadImageUrlByMd5(md5);
	}
	
}
