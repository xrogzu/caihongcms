package com.caihong.common.util;

import com.taobao.api.TaobaoClient;

import java.sql.Timestamp;
import java.util.Date;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * 短信发送方法
 * @author rishi.ding
 * @date 2017-01-25
 */
public class SmsUtils {
	private static String url="http://gw.api.taobao.com/router/rest";
	private static String appkey="23563197";
	private static String secret="0b769624277fc00a53cd796e3749397d";
	private static String signName="彩虹医学网";
	private static String product="彩虹医学网";
	
	private static String reg_template="SMS_33700785";
	private static String vali_template="SMS_33700789";
/**
 * 发送注册验证码
 * @param telphones
 * @param content
 * @throws ApiException
 */
	public static boolean sendRegValiCode(String telphone,String code){
		return sendMsg(telphone,code,reg_template);		
	}	
	
	private static boolean sendMsg(String telphone,String code,String msg_template) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("");
		req.setSmsType("normal");
		req.setSmsFreeSignName(signName);
		req.setSmsParamString("{\"code\":'"+code+"',\"product\":'"+product+"'}");
		req.setRecNum(telphone);
		req.setSmsTemplateCode(msg_template);
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			rsp = client.execute(req);
			return rsp.getResult().getSuccess();
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 * 找回密码
	 * @param telphones
	 * @param content
	 * @throws ApiException
	 */
		public static boolean sendValiCode(String telphone,String code) {
			return sendMsg(telphone,code,vali_template);				
		}
	public static void main(String[] args) {
		//			SmsUtils.sendValiCode("15882454451","1233");
		Date now = new Date(new Date().getTime()-30*60*1000);
		String date="2017-01-31 17:40:00";
		Date da=DateFormatUtils.parseDate(date, "yyyy-MM-dd HH:mm:ss");
		System.out.println(DateFormatUtils.formatDate(now,"yyyy-MM-dd HH:mm:ss"));
		System.out.println(DateFormatUtils.formatDate(da,"yyyy-MM-dd HH:mm:ss"));
		System.out.println(da.before(now));
//		System.out.println(now.after(da));
		
	}

}
