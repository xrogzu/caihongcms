package com.caihong.cms.action.front;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.caihong.common.web.ResponseUtils;

import sun.misc.BASE64Decoder;

@Controller
public class UploadifyAct {
		private static final String url="uploads";
		/**
		 * 文件上传
		 * @param request
		 * @param response
		 */
		@RequestMapping(value = "/uploadify/uploadify.jspx")
	    public void upload(HttpServletRequest request, HttpServletResponse response) {
//	        request = new MulpartRequestWrapper(request);
	        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
	        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();   
	        String returnUrl="/"+url+"/";
	        String ctxPath=request.getSession().getServletContext().getRealPath("/")+url+"/"; 
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");  
	        String ymd = sdf.format(new Date());  
	        ctxPath += ymd + "/";  
	        returnUrl+=ymd+ "/";
	        //创建文件夹  
	            File file = new File(ctxPath);    
	            if (!file.exists()) {    
	                file.mkdirs();    
	            }    
	            String fileName = null;
	            for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {    
	                // 上传文件名    
	                // System.out.println("key: " + entity.getKey());    
	                MultipartFile mf = entity.getValue();    
	                fileName = mf.getOriginalFilename();  
	               //String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();      
	               //SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");  
	               // String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;  
	                
	                String uuid =com.caihong.common.util.UUIDGenerator.getUUID();// 返回一个随机UUID。
	                String suffix = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf("."), fileName.length()) : null;

	                String newFileName =  uuid + (suffix!=null?suffix:"");// 构成新文件名。   
	                File uploadFile = new File(ctxPath + newFileName);    
	                try {  
	                    FileCopyUtils.copy(mf.getBytes(), uploadFile); 
	                    returnUrl=returnUrl+newFileName;
	                   
		            } catch (Exception e) {  
		                e.printStackTrace();  
		            }    
	            }   
	            
	            ResponseUtils.renderJson(response, returnUrl);  
	    }
		/**
		 * 二进制转图片
		 * @param byteString
		 * @return
		 */
		public static String saveByteImg(HttpServletRequest request,String byteString){
			BASE64Decoder decoder = new sun.misc.BASE64Decoder(); 
			String returnUrl="/"+url+"/";
			try {   
				 
			        String ctxPath=request.getSession().getServletContext().getRealPath("/")+url+"/"; 
			        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");  
			        String ymd = sdf.format(new Date());  
			        ctxPath += ymd + "/";  
			        returnUrl+=ymd+ "/";
			        //创建文件夹  
			            File file = new File(ctxPath);    
			            if (!file.exists()) {    
			                file.mkdirs();    
			            }    
			            String uuid =com.caihong.common.util.UUIDGenerator.getUUID()+".";// 返回一个随机UUID。
		                String suffix ="jpg";
		                String newFileName =  uuid + (suffix!=null?suffix:"");// 构成新文件名。   
		                returnUrl=returnUrl+newFileName;
			            
	            byte[] bytes1 = decoder.decodeBuffer(byteString.split(",")[1]);   
	               
	            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);   
	            BufferedImage bi1 =ImageIO.read(bais);   
	            File w2 = new File(ctxPath + newFileName);//可以是jpg,png,gif格式   
	            ImageIO.write(bi1, suffix, w2);//不管输出什么格式图片，此处不需改动   
	        } catch (IOException e) {   
	            e.printStackTrace();   
	        }   
			return returnUrl;
		}
		
		
}
