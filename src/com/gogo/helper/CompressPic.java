package com.gogo.helper;

/**
 *  缩略图实现，将图片(jpg、bmp、png、gif等等)真实的变成想要的大小
 */


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/*******************************************************************************
 * 缩略图类（通用） 本java类能将jpg、bmp、png、gif图片文件，进行等比或非等比的大小转换。 具体使用方法
 * compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true))
 */
 public class CompressPic { 
	 
	 //等比缩放
	 public static int PROPORTION_TYPE_EQUALRATION=0;
	 //按宽缩放
	 public static int PROPORTION_TYPE_WIDTH=1;
	 //按高缩放
	 public static int PROPORTION_TYPE_HEIGHT=2;
	 
	 
	 private File file = null; // 文件对象 
	 private String outputDir; // 输出图路径
	 private String outputFileName; // 输出图文件名
	 private int outputWidth = 100; // 默认输出图片宽
	 private int outputHeight = 100; // 默认输出图片高
	 private int proportion = PROPORTION_TYPE_EQUALRATION; // 缩放类型
	 public CompressPic() { // 初始化变量
		 outputDir = ""; 
		 outputFileName = ""; 
		 outputWidth = 100; 
		 outputHeight = 100; 
	 } 
	 
	 public CompressPic(int ratio) { // 初始化变量
		 outputDir = ""; 
		 outputFileName = ""; 
		 this.outputWidth = ratio;
		 this.outputHeight = ratio;
	 } 
	 public void setInputDir(String inputDir) { 
	 } 
	 public void setOutputDir(String outputDir) { 
		 this.outputDir = outputDir; 
	 } 
	 public void setInputFileName(String inputFileName) {
	 } 
	 public void setOutputFileName(String outputFileName) { 
		 this.outputFileName = outputFileName; 
	 } 
	 public void setOutputWidth(int outputWidth) {
		 this.outputWidth = outputWidth; 
	 } 
	 public void setOutputHeight(int outputHeight) { 
		 this.outputHeight = outputHeight; 
	 } 
	 public void setWidthAndHeight(int width, int height) { 
		 this.outputWidth = width;
		 this.outputHeight = height; 
	 } 
	 
	 /* 
	  * 获得图片大小 
	  * 传入参数 String path ：图片路径 
	  */ 
	 public long getPicSize(String path) { 
		 file = new File(path); 
		 return file.length(); 
	 }
	 
	 // 图片处理 
	 public String compressPic(Image img) throws IOException { 
		 
		 FileOutputStream out = null;
		 try { 
			 // 判断图片格式是否正确 
			 if (img.getWidth(null) == -1) {
				 return "can't read,retry!"; 
			 } else { 
				 int newWidth; int newHeight; 
				 // 判断是否是等比缩放 
				 if (this.proportion == 0) { 
					 // 为等比缩放计算输出的图片宽度及高度 
					 double rate1 = ((double) img.getWidth(null)) / (double) outputWidth + 0.1; 
					 double rate2 = ((double) img.getHeight(null)) / (double) outputHeight + 0.1; 
					 // 根据缩放比率大的进行缩放控制 
					 double rate = rate1 > rate2 ? rate1 : rate2; 
					 newWidth = (int) (((double) img.getWidth(null)) / rate); 
					 newHeight = (int) (((double) img.getHeight(null)) / rate); 
				 } else if(this.proportion == CompressPic.PROPORTION_TYPE_WIDTH) { 
					 double rate = ((double) img.getWidth(null)) / (double) outputWidth + 0.1; 
					 newWidth = outputWidth; // 输出的图片宽度 
					 newHeight = (int)(((double) img.getHeight(null))/rate); // 输出的图片高度 
				 } else if(this.proportion == CompressPic.PROPORTION_TYPE_HEIGHT) { 
					 double rate = ((double) img.getHeight(null)) / (double) outputHeight + 0.1;  
					 newWidth = (int)(((double) img.getWidth(null))/rate); // 输出的图片宽度 
					 newHeight = outputHeight; // 输出的图片高度 
				 } else{
					 newWidth = outputWidth; // 输出的图片宽度 
					 newHeight = outputHeight; // 输出的图片高度 
				 }
			 	BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB); 
			 	/*
				 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的
				 * 优先级比速度高 生成的图片质量比较好 但速度慢
				 */ 
			 	tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
			 	out = new FileOutputStream(outputDir + outputFileName);
			 	// JPEGImageEncoder可适用于其他图片类型的转换 
			 	JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out); 
			 	encoder.encode(tag); 
			 } 
		 } catch (IOException ex) { 
			 ex.printStackTrace(); 
			 throw ex;
		 } finally{
			if(out != null){
				out.close(); 
			}
		 }
		 return "ok"; 
	} 
 	public String compressPic (String inputDir, String outputDir, String inputFileName, String outputFileName) throws IOException { 
 		// 输出图路径 
 		this.outputDir = outputDir; 
 		// 输出图文件名
 		this.outputFileName = outputFileName; 
 	 		
 		//获得源文件 
		file = new File(inputDir + inputFileName); 
		if (!file.exists()) { 
			 return "filei not's exists"; 
		}
 		Image img = ImageIO.read(file); 
 		
 		int imgWidth = img.getWidth(null);
 		int imgHeight = img.getHeight(null);
 		
 		
 		int temp = 0;
 		if(imgWidth > imgHeight){
 			temp = imgWidth;
 			imgWidth = imgHeight;
 			imgHeight = temp;
 			this.proportion = CompressPic.PROPORTION_TYPE_HEIGHT;
 			
 		}else{
 			this.proportion = CompressPic.PROPORTION_TYPE_WIDTH;
 			
 		}
 		if(imgWidth <= this.outputWidth){
			return reNameFile();
		}
 		return compressPic(img); 
 	} 
 	
 	public String reNameFile(){
 		file.renameTo(new File(file.getParent()+File.separator+outputFileName));
		return "ok";
 	}
 	
 	public String compressPic (String inputDir, String outputDir, String inputFileName, String outputFileName,int gp) throws IOException { 
 		// 输出图路径 
 		this.outputDir = outputDir; 
 		// 输出图文件名
 		this.outputFileName = outputFileName; 
 	 		
 		//获得源文件 
		file = new File(inputDir + inputFileName); 
		if (!file.exists()) { 
			 return "faild,filei not's exists"; 
		}
 		Image img = ImageIO.read(file); 
 		
 		this.proportion = gp;
 		
 		return compressPic(img); 
 	} 

 	// main测试 
 	// compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true))
 	public static void main(String[] arg) { 
 		CompressPic mypic = new CompressPic(); 
 		System.out.println("输入的图片大小：" + mypic.getPicSize("e:\\1.jpg")/1024 + "KB"); 
 		int count = 0; // 记录全部图片压缩所用时间
 		for (int i = 0; i < 100; i++) { 
 			int start = (int) System.currentTimeMillis();	// 开始时间 
 			//mypic.compressPic("e:\\", "e:\\test\\", "1.jpg", "r1"+i+".jpg", 120, 120, 0); 
 			int end = (int) System.currentTimeMillis(); // 结束时间 
 			int re = end-start; // 但图片生成处理时间 
 			count += re; System.out.println("第" + (i+1) + "张图片压缩处理使用了: " + re + "毫秒"); 
 			System.out.println("输出的图片大小：" + mypic.getPicSize("e:\\test\\r1"+i+".jpg")/1024 + "KB"); 
 		}
 		System.out.println("总共用了：" + count + "毫秒"); 
 	} 
 }
