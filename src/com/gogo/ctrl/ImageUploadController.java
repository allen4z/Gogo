package com.gogo.ctrl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.gogo.domain.ImageModel;
import com.gogo.domain.User;
import com.gogo.exception.Business4JsonException;
import com.gogo.helper.CommonConstant;
import com.gogo.helper.CompressPic;
import com.gogo.helper.MD5Util;
import com.gogo.helper.UUIDHelper;
import com.gogo.service.ImageUploadService;

@Controller
@RequestMapping("/upload")
public class ImageUploadController extends BaseController {

	@Autowired
	ImageUploadService imageUploadService;
	/**
	 * 上传图片前先查询图片md5 如果md5存在，则直接返回给链接地址
	 * @param md5
	 * @return
	 */
	@RequestMapping(value = "befroUpload")
	@ResponseBody
	public String beforUpload(String md5){
		ImageModel im = imageUploadService.loadImageUrlByMd5(md5);
		if(im != null){
			return im.getUrl();
		}
		return null;
	}
	
	
	/**
	 * 用户头像图片上传
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "uploadUserHead", method = RequestMethod.POST)
	@ResponseBody
	public String uploadImage4UserHead(HttpServletRequest request,@RequestParam MultipartFile userHeadFile) throws Exception{
		return this.uploadImage(userHeadFile, CommonConstant.IMAGE_TYPE_USERHEAD);
	}

	private String uploadImage(@RequestParam MultipartFile file, @RequestParam int type)
			throws Exception {
		// 返回的路径信息
		String uploadAndCompressPath = null;
		// 读取配置文件
		ResourceBundle rb = ResourceBundle.getBundle("imageConfig");
		// 图片正式目录
		String formalPath = null;
		// 图片名字
		String outFileName = null;
		
		int defaultRatio = 0;
		SimpleDateFormat fomate = new SimpleDateFormat("yyyyMMdd");
		
		if (type == CommonConstant.IMAGE_TYPE_USERHEAD) {
			
			formalPath = rb.getString("image.formal.user");
			defaultRatio = Integer.valueOf(rb.getString("image.default.ratio.middle"));
			outFileName = "uh";
		} else if (type == CommonConstant.IMAGE_TYPE_ACTIVITYLOGO) {
			formalPath = rb.getString("image.formal.act.logo");
			defaultRatio = Integer.valueOf(rb.getString("image.default.ratio.middle"));
			outFileName = "al";
		} else if (type == CommonConstant.IMAGE_TYPE_ACTIVITYSHARE) {
			formalPath = rb.getString("image.formal.act.share");
			formalPath += fomate.format(new Date()) + File.separator ;
			defaultRatio = Integer.valueOf(rb.getString("image.default.ratio.large"));
			outFileName = "as";
		}
		// 默认参考尺寸
		
		// TODO 图片服务器地址 暂时为本服务器
		String imageServer = rb.getString("image.upload.imageserver");
		// 服务器真路径信息
		String serverPath = System.getProperty("myappfuse.root");

		// 1.获得文件信息
		if (file != null && !file.isEmpty()) {
			// 2.1 创建相关目录
			
			String imageCompressPath = serverPath + formalPath;
			File imageCompressPathFile = new File(imageCompressPath);
			createDir(imageCompressPathFile);
			
			//查询是否已经存在此图片
			
			//得到上传的文件
	        CommonsMultipartFile cf= (CommonsMultipartFile)file; 
	        DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
	        File sourceImageFile = fi.getStoreLocation(); 
			// 3.处理图片并修改文件名
			// 生成随机的名字
			String uuid = UUIDHelper.generateShortUuid();
			outFileName += uuid + ".png";
			//按大图缩放
			CompressPic compressPic = new CompressPic(defaultRatio);
			String result = compressPic.compressPic(sourceImageFile, imageCompressPath, outFileName);
			if (result.equals("success")) {
				uploadAndCompressPath = imageServer + formalPath + outFileName;
			} else {
				throw new Business4JsonException(result);
			}
			// 4.1 删除源文件
			sourceImageFile.delete();
			// 4.2 返回文件路径
			return uploadAndCompressPath;
		}
		return "faild";
	}

	private void createDir(File pathFile) {
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
	}


}
