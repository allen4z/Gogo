package com.gogo.ctrl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gogo.domain.User;
import com.gogo.exception.Business4JsonException;
import com.gogo.helper.CommonConstant;
import com.gogo.helper.CompressPic;


@Controller
@RequestMapping("/upload")
public class ImageUploadController extends BaseController {

	
	/**
	 * 图片上传
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="uploadimage",method=RequestMethod.POST)
	@ResponseBody
	public String uploadImage(HttpSession session,  @RequestParam MultipartFile file,@RequestParam int type) throws Exception {
		User curUser= getSessionUser(session);
		
		String uploadAndCompressPath =null;
		
		ResourceBundle rb = ResourceBundle.getBundle("imageConfig");
		
		String imageTempPath = null;
		String formalPath = null;
		if(type == CommonConstant.IMAGE_TYPE_USERHEAD){
			imageTempPath=rb.getString("image.upload.temppath.user");
			formalPath = rb.getString("image.formal.user");
		}else if(type == CommonConstant.IMAGE_TYPE_ACTIVITYLOGO){
			imageTempPath=rb.getString("image.upload.temppath.act.logo");
			formalPath = rb.getString("image.formal.act.logo");
		}else if(type == CommonConstant.IMAGE_TYPE_ACTIVITYSHARE){
			imageTempPath=rb.getString("image.upload.temppath.act.share");
			formalPath = rb.getString("image.formal.act.share");
		}
		int defaultRatio = Integer.valueOf(rb.getString("image.default.ratio"));
		
		String imageServer= rb.getString("image.upload.imageserver");
		
		String serverPath = System.getProperty("myappfuse.root");
		
		//1.获得文件信息
		if(file != null && !file.isEmpty()){
			//2.1 创建相关目录
			
			SimpleDateFormat fomate = new SimpleDateFormat("yyMMdd");
			imageTempPath += fomate.format(new Date()) + File.separator;
			formalPath += fomate.format(new Date()) + File.separator + curUser.getUserId() + File.separator;
			
			String imagePath = serverPath + imageTempPath;
			String imageCompressPath = serverPath + formalPath;
			File pathFile = new File(imagePath);
			if(!pathFile.exists()){
				pathFile.mkdirs();
			}
			File imageCompressPathFile = new File(imageCompressPath);
			if(!imageCompressPathFile.exists()){
				imageCompressPathFile.mkdirs();
			}
			File sourceImageFile = new File(imagePath+file.getOriginalFilename());
			//2.2保存原图片
			file.transferTo(sourceImageFile);
			//3.处理图片并修改文件名
			String outFileName="aaa.png";
			CompressPic compressPic = new CompressPic(defaultRatio);
			String result = compressPic.compressPic(imagePath, imageCompressPath, file.getOriginalFilename(), outFileName);
			if(result.equals("ok")){
				uploadAndCompressPath = imageServer + formalPath +outFileName ;
			}else{
				throw new Business4JsonException(result);
			}
			
			//4.1 删除源文件
			sourceImageFile.delete();
			//4.2 返回文件路径
			return uploadAndCompressPath;
		}
		return "faild";
	}
	
}
