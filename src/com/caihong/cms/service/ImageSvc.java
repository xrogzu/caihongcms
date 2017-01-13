package com.caihong.cms.service;

import com.caihong.core.entity.CmsSite;
import com.caihong.core.entity.Ftp;

/**
 * @author Tom
 */
public interface ImageSvc {
	/**
	 * 抓取远程图片返回本地地址
	 * @param imgUrl 远程图片URL
	 * @return
	 */
	public String crawlImg(String imgUrl,CmsSite site);
	
	public String crawlImg(String imgUrl,String ctx,boolean uploadToDb,String dbFileUri,Ftp ftp,String uploadPath);
}
