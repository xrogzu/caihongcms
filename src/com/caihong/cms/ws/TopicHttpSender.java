package com.caihong.cms.ws;


import java.util.ArrayList;
import java.util.List;

import com.caihong.common.web.HttpClientUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 短信发送
 * @author rishi.ding
 *
 */
public class TopicHttpSender {
	private static int pageno=1;
	private static int pagesize=100;
	public static List<Topic> getUserTopic(String username,Integer pageNo,Integer pageSize){
		List<Topic> list=new ArrayList<Topic>();
		if(pageNo==null){
			pageNo=pageno;
		}
		if(pageSize==null){
			pageSize=pagesize;
		}
		String url="http://bbs.caihongyixue.com/topic/usertopic.jspx?username="+username+"&pageNo="+pageNo+"&pageSize="+pageSize;
		String out=HttpClientUtil.getInstance().get(url);
		if(!out.equals("0")){
			JSONArray array=JSONArray.fromObject(out);
			for(int i = 0; i < array.size(); i++){ 
				list.add((Topic)JSONObject.toBean(array.getJSONObject(i),Topic.class));
			}
		}
		return list;
	}

	
	public static void main(String[] args) {
		List<Topic> list=TopicHttpSender.getUserTopic("admin", 1, 10);
		System.out.println(list.size());
	}
}
