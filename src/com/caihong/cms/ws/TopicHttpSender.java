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
	
	/**
	 * 彩虹币同步
	 * @param username
	 * @param grain
	 * @return
	 */
	public static boolean updateGrain(String username,Integer grain){
		if(username==null||username.equals("")||grain==null||grain==0){
			return false;
		}
		String url="http://bbs.caihongyixue.com/ws/updateGrain.jspx?username="+username+"&prestige="+grain;
		String out=HttpClientUtil.getInstance().get(url);
		if(!out.equals("0")){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		boolean out=TopicHttpSender.updateGrain("彩虹妹妹", 1);
		System.out.println(out);
	}
}
