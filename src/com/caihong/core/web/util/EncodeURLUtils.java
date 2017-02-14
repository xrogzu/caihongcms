package com.caihong.core.web.util;

import java.net.URLDecoder;
import java.net.URLEncoder;



public class EncodeURLUtils {
	
	public static String getURLEncode(String src)
	{
		if(src==null||src.equals(""))return null;
		String requestValue="";
		try{
			requestValue = URLEncoder.encode(src,"UTF-8");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return requestValue;
	}
	public static String getURLDecode(String src)
	{
		if(src==null||src.equals(""))return null;
		String requestValue="";
		try{
			requestValue = URLDecoder.decode(src,"UTF-8");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return requestValue;
	}
	public static void main(String args[]){
		String out=EncodeURLUtils.getURLEncode("http://www.caihongyixue.com/blog/彩虹妹妹.jspx#");
		
		EncodeURLUtils.getURLDecode("http://www.caihongyixue.com/blog/％E5％BD％A9％E8％99％B9％E5％A6％B9％E5％A6％B9.jspx".replace("％", "%"));
	}
}
