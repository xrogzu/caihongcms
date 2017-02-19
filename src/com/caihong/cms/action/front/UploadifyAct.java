package com.caihong.cms.action.front;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.caihong.common.web.ResponseUtils;

@Controller
public class UploadifyAct {
		private static final String url="uploads";
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
}
