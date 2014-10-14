package com.gogo.dao;

import com.gogo.domain.ImageModel;

public class ImageUploadDao extends BaseDao<ImageModel> {

	public ImageModel loadImageUrlByMd5(String md5){
		String hql = " from ImageModel imageModel where imageModel.md5=md5";
		ImageModel im = (ImageModel) getSession().createQuery(hql).setString("md5", md5).uniqueResult();
		return im;
	}
	
}
