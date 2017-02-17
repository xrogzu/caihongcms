package com.caihong.common.util;

import static com.caihong.cms.Constants.TPLDIR_SPECIAL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.springframework.ui.ModelMap;

import com.caihong.cms.entity.assist.CmsConfigContentCharge;
import com.caihong.common.web.ClientCustomSSL;
import com.caihong.common.web.Constants;
import com.caihong.common.web.HttpClientUtil;
import com.caihong.common.web.RequestUtils;
import com.caihong.core.entity.CmsSite;
import com.caihong.core.entity.CmsUser;
import com.caihong.core.web.util.CmsUtils;
import com.caihong.core.web.util.FrontUtils;

public class WeixinPay {
	public static final String CONTENT_CODE_WEIXIN="tpl.content.code.weixin";
	public static final String CONTENT_PREPAY="tpl.content.prePay";
	//微信统一下单
	public  static Map<String, String>  weixinUniformOrder(String trade_type,String openId,
			HttpServletRequest request,String serverUrl,CmsConfigContentCharge config,
			String content,String orderNum,Double rewardAmount,String product_id){
		CmsSite site=CmsUtils.getSite(request);
		CmsUser user=CmsUtils.getUser(request);
		Map<String, String> paramMap = new HashMap<String, String>();
		// 微信分配的公众账号ID（企业号corpid即为此appId）[必填]
		paramMap.put("appid", config.getWeixinAppId());
		// 微信支付分配的商户号 [必填]
		paramMap.put("mch_id", config.getWeixinAccount());
		// 终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB" [非必填]
		paramMap.put("device_info", "WEB");
		// 随机字符串，不长于32位。 [必填]
		paramMap.put("nonce_str", RandomStringUtils.random(10,Num62.N62_CHARS));
		// 商品或支付单简要描述 [必填]
		
		if(content==null){
			content="购买";
		}
		String bodyString=content;
//		try {
//			 bodyString = new String(content.getBytes("utf-8"));
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		paramMap.put("body", bodyString);
		// 商户系统内部的订单号,32个字符内、可包含字母, [必填]
		paramMap.put("out_trade_no", orderNum);
		// 符合ISO 4217标准的三位字母代码，默认人民币：CNY. [非必填]
		paramMap.put("fee_type", "CNY");
		Double amount=0d;
		if(rewardAmount!=null){
			amount=rewardAmount;
		}
		// 金额必须为整数 单位为分 [必填]
		paramMap.put("total_fee", PayUtil.changeY2F(amount));
		// APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP [必填]
		paramMap.put("spbill_create_ip", RequestUtils.getIpAddr(request));
		// 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。 [必填]
		paramMap.put("notify_url", "http://" + site.getDomain()
				+ "/order/payCallByWeiXin.jspx");
		// 交易类型{取值如下：JSAPI，NATIVE，APP，(JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付)}
		// [必填]
		paramMap.put("trade_type",trade_type);
		//openid trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识
		if(trade_type.equals("JSAPI")){
			if(StringUtils.isNotBlank(openId)){
				paramMap.put("openid",openId);
			}else if(user!=null&&user.getUserAccount()!=null){
				paramMap.put("openid",user.getUserAccount().getAccountWeixinOpenId());
			}
		}
		// 商品ID{trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。}
		paramMap.put("product_id",product_id);
		if (StringUtils.isNotBlank(config.getTransferApiPassword())) {
			// 根据微信签名规则，生成签名
			paramMap.put("sign",
					PayUtil.createSign(paramMap, config.getTransferApiPassword()));
		}
		// 把参数转换成XML数据格式
		String xmlWeChat = PayUtil.assembParamToXml(paramMap);
		
		String resXml = HttpClientUtil.post(serverUrl,xmlWeChat,Constants.UTF8);
		Map<String, String> map=new HashMap<String, String>();
		try {
			if(StringUtils.isNotBlank(resXml)){
				map = PayUtil.parseXMLToMap(resXml);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return map;
	}
	
	public  static Map<String, String>  weixinOrderQuery(
			String transaction_id,String orderNum,
			String serverUrl,CmsConfigContentCharge config){
		Map<String, String> paramMap = new HashMap<String, String>();
		// 微信分配的公众账号ID（企业号corpid即为此appId）[必填]
		paramMap.put("appid", config.getWeixinAppId());
		// 微信支付分配的商户号 [必填]
		paramMap.put("mch_id", config.getWeixinAccount());
		// 微信订单号
		paramMap.put("transaction_id", transaction_id);
		//商户订单号
		paramMap.put("out_trade_no", orderNum);
		// 随机字符串，不长于32位。 [必填]
		paramMap.put("nonce_str", RandomStringUtils.random(10,Num62.N62_CHARS));
		// 签名类型
		paramMap.put("sign_type","MD5");
		if (StringUtils.isNotBlank(config.getTransferApiPassword())) {
			// 根据微信签名规则，生成签名
			paramMap.put("sign",
					PayUtil.createSign(paramMap, config.getTransferApiPassword()));
		}
		// 把参数转换成XML数据格式
		String xmlWeChat = PayUtil.assembParamToXml(paramMap);
		String resXml = HttpClientUtil.post(serverUrl,xmlWeChat);
		Map<String, String> map=new HashMap<String, String>();
		try {
			if(StringUtils.isNotBlank(resXml)){
				map = PayUtil.parseXMLToMap(resXml);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return map;
	}
	
	public static void main(String args[]){		
		
		String url="https://api.mch.weixin.qq.com/secapi/pay/refund"; //
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("appid", "wx7057eaa8d8dafab9");
		// 微信支付分配的商户号 [必填]
		paramMap.put("mch_id", "1428676402");	
		paramMap.put("device_info", "WEB");
		paramMap.put("nonce_str",  RandomStringUtils.random(10,Num62.N62_CHARS));
		paramMap.put("op_user_id", "1428676402");
		paramMap.put("out_trade_no","148697605719451653");
		paramMap.put("out_refund_no","148697605719451653");
		paramMap.put("refund_fee","1");
		paramMap.put("total_fee","1");
		
		paramMap.put("sign",PayUtil.createSign(paramMap, "THfSFiifVVvrRShin3NJ3HDNPaHeyJyx"));
		
		String reuqestXml = PayUtil.assembParamToXml(paramMap);
	
		
		
	
        try {
        	KeyStore keyStore  = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File(WeixinPay.class.getResource(".").getFile().toString()+"apiclient_cert.p12"));//放退款证书的路径
            try {
                keyStore.load(instream, "1428676402".toCharArray());
            } finally {
                instream.close();
            }

            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, "1428676402".toCharArray()).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");//退款接口
            
            System.out.println("executing request" + httpPost.getRequestLine());
            StringEntity  reqEntity  = new StringEntity(reuqestXml);
            // 设置类型 
            reqEntity.setContentType("application/x-www-form-urlencoded"); 
            httpPost.setEntity(reqEntity);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();

              
                String line = "";  
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
                    String text;
                    
                     
        			
                    while ((text = bufferedReader.readLine()) != null) {
                    	line=line+text;
                        System.out.println(text);
                    }
                   
                }
                EntityUtils.consume(entity);
                System.out.println(line);
                Map<String, String> map=new HashMap<String, String>();
        		try {
        			if(StringUtils.isNotBlank(line)){
        				map = PayUtil.parseXMLToMap(line);
        			}
        		} catch (Exception e) {
        			e.printStackTrace();
        		} 
            }catch(Exception e){
            	
            }finally {
                try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }catch(Exception e){
        	
        }
        
	}
	/**
	 * 微信退费
	 */
	public static Map<String, String> refoundWeixin(HttpServletRequest request,CmsConfigContentCharge config,
			String orderNum,Double rewardAmount){
		String url="https://api.mch.weixin.qq.com/secapi/pay/refund"; //
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("appid", config.getWeixinAppId());
		// 微信支付分配的商户号 [必填]
		paramMap.put("mch_id", config.getWeixinAccount());		
		paramMap.put("nonce_str", UUIDGenerator.getUUID());
		paramMap.put("op_user_id", config.getWeixinAccount());
		paramMap.put("out_trade_no",orderNum);
		paramMap.put("out_refund_no",orderNum);
		paramMap.put("refund_fee",PayUtil.changeY2F(rewardAmount));
		paramMap.put("total_fee",PayUtil.changeY2F(rewardAmount));
		paramMap.put("transaction_id","");
		if (StringUtils.isNotBlank(config.getTransferApiPassword())) {
			// 根据微信签名规则，生成签名
			paramMap.put("sign",PayUtil.createSign(paramMap, config.getTransferApiPassword()));
		}
		String reuqestXml = PayUtil.assembParamToXml(paramMap);
		
		String resXml ="";
		try {
        	KeyStore keyStore  = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File(WeixinPay.class.getResource(".").getFile().toString()+"apiclient_cert.p12"));//放退款证书的路径
            try {
                keyStore.load(instream, config.getAlipayAccount().toCharArray());
            } finally {
                instream.close();
            }
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, config.getAlipayAccount().toCharArray()).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            HttpPost httpPost = new HttpPost(url);//退款接口            
            StringEntity  reqEntity  = new StringEntity(reuqestXml);
            // 设置类型 
            reqEntity.setContentType("application/x-www-form-urlencoded"); 
            httpPost.setEntity(reqEntity);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();              
                String line = "";  
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
                    String text;        			
                    while ((text = bufferedReader.readLine()) != null) {
                    	line=line+text;                       
                    }                   
                }
                EntityUtils.consume(entity);               
            }catch(Exception e){            	
            }finally {
                try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }catch(Exception e){        	
        }      
	
		Map<String, String> map=new HashMap<String, String>();
		try {
			if(StringUtils.isNotBlank(resXml)){
				map = PayUtil.parseXMLToMap(resXml);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return map; 
		
	}
	//微信扫码支付
	public static  String enterWeiXinPay(String serverUrl,
			CmsConfigContentCharge config,String content,String orderNumber,String product_id,String url,
			Double rewardAmount,HttpServletRequest request,
			HttpServletResponse response,ModelMap model) throws JSONException {
		if (StringUtils.isNotBlank(config.getWeixinAppId())
				&& StringUtils.isNotBlank(config.getWeixinAccount())) {
			CmsSite site=CmsUtils.getSite(request);
			if (StringUtils.isNotBlank(config.getTransferApiPassword())) {
				Map<String, String> map=weixinUniformOrder( "NATIVE",null,
						request, serverUrl,config, content, orderNumber, rewardAmount,product_id);
				String returnCode = map.get("return_code");
				if(StringUtils.isNotBlank(returnCode)){
					if (returnCode.equalsIgnoreCase("FAIL")) {
						return FrontUtils.showMessage(request, model, map.get("return_msg"));
					} else if (returnCode.equalsIgnoreCase("SUCCESS")) {
						if (map.get("err_code") != null) {
							return FrontUtils.showMessage(request, model, map.get("err_code_des"));
						} else if (map.get("result_code").equalsIgnoreCase(
								"SUCCESS")) {
							String code_url = map.get("code_url");
							model.addAttribute("code_url", code_url);
							model.addAttribute("orderNumber",orderNumber);
							if(rewardAmount!=null){
								model.addAttribute("payAmount", rewardAmount);
							}
							model.addAttribute("url", url);
							FrontUtils.frontData(request, model, site);
							return FrontUtils.getTplPath(request, site.getSolutionPath(),
									TPLDIR_SPECIAL,CONTENT_CODE_WEIXIN);
						}
					}
				}
				return FrontUtils.showMessage(request, model, "error.connect.timeout");
			} else {
				return FrontUtils.showMessage(request, model,"error.contentCharge.need.key");
			}
		} else {
			return FrontUtils.showMessage(request, model,"error.contentCharge.need.appid");
		}
	}

	//微信公众号支付(手机端)
	public static String weixinPayByMobile(String serverUrl,CmsConfigContentCharge config,
			String openId,String content,String orderNum,Double rewardAmount,String product_id,
			HttpServletRequest request,HttpServletResponse response,
			ModelMap model){
		CmsSite site=CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		if (StringUtils.isNotBlank(config.getWeixinAppId())
				&& StringUtils.isNotBlank(config.getWeixinAccount())) {
			if (StringUtils.isNotBlank(config.getTransferApiPassword())) {
				Map<String, String> map=weixinUniformOrder("JSAPI",openId,
						request, serverUrl,config, content, orderNum, rewardAmount,product_id);
				String returnCode = map.get("return_code");
				if (returnCode.equalsIgnoreCase("FAIL")) {
					return FrontUtils.showMessage(request, model, map.get("return_msg"));
				} else if (returnCode.equalsIgnoreCase("SUCCESS")) {
					if (map.get("err_code") != null) {
						return FrontUtils.showMessage(request, model, map.get("err_code_des"));
					} else if (map.get("result_code").equalsIgnoreCase(
							"SUCCESS")) {
						String prepay_id = map.get("prepay_id");
						Long time=System.currentTimeMillis()/1000;
						String nonceStr=RandomStringUtils.random(16,Num62.N10_CHARS);
						//公众号appid
						model.addAttribute("appId",config.getWeixinAppId());
						//时间戳 当前的时间 需要转换成秒
						model.addAttribute("timeStamp",time);
						//随机字符串  不长于32位
						model.addAttribute("nonceStr",nonceStr);
						//订单详情扩展字符串 统一下单接口返回的prepay_id参数值，提交格式如：prepay_id=***
						model.addAttribute("package","prepay_id="+prepay_id);
						//签名方式 签名算法，暂支持MD5
						model.addAttribute("signType","MD5");
						Map<String, String> paramMap = new HashMap<String, String>();
						paramMap.put("appId",config.getWeixinAppId());
						paramMap.put("timeStamp",time.toString());
						paramMap.put("nonceStr",nonceStr);
						paramMap.put("package","prepay_id="+prepay_id);
						paramMap.put("signType","MD5");
						//签名
						model.addAttribute("paySign",PayUtil.createSign(paramMap, config.getTransferApiPassword()));
						model.addAttribute("orderNumber",orderNum);
						if(rewardAmount!=null){
							model.addAttribute("payAmount", rewardAmount);
						}
						model.addAttribute("content", content);
						return FrontUtils.getTplPath(request, site.getSolutionPath(),
								TPLDIR_SPECIAL,CONTENT_PREPAY);
					}
				}
				return FrontUtils.showMessage(request, model, "error.connect.timeout");
			} else {
				return FrontUtils.showMessage(request, model,"error.contentCharge.need.key");
			}
		} else {
			return FrontUtils.showMessage(request, model,"error.contentCharge.need.appid");
		}
	}
	
	//企业付款接口
	public  static Object[] payToUser(CmsConfigContentCharge config,File pkcFile,
			String serverUrl,String orderNum,String weixinOpenId,String realname
			,Double payAmount,String desc,String ip){
		Map<String, String> paramMap = new HashMap<String, String>();
		// 公众账号appid[必填]
		paramMap.put("mch_appid", config.getWeixinAppId());
		// 微信支付分配的商户号 [必填]
		paramMap.put("mchid", config.getWeixinAccount());
		// 终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB" [非必填]
		paramMap.put("device_info", "WEB");
		// 随机字符串，不长于32位。 [必填]
		paramMap.put("nonce_str", RandomStringUtils.random(16,Num62.N62_CHARS));
		
		// 商户订单号,需保持唯一性[必填]
		paramMap.put("partner_trade_no", orderNum);
		// 商户appid下，某用户的openid[必填]
		paramMap.put("openid", weixinOpenId);
		//校验用户姓名选项
		paramMap.put("check_name", "OPTION_CHECK");
		//收款用户姓名,如果check_name设置为FORCE_CHECK或OPTION_CHECK，则必填用户真实姓名
		paramMap.put("re_user_name", realname);
		// 企业付款金额，金额必须为整数 单位为分 [必填]
		paramMap.put("amount", PayUtil.changeY2F(payAmount));
		// 企业付款描述信息 [必填]
		paramMap.put("desc",  desc);
		// 调用接口的机器Ip地址[必填]
		paramMap.put("spbill_create_ip", ip);
		if (StringUtils.isNotBlank(config.getTransferApiPassword())) {
			// 根据微信签名规则，生成签名
			paramMap.put("sign",
					PayUtil.createSign(paramMap, config.getTransferApiPassword()));
		}
		// 把参数转换成XML数据格式
		String xmlWeChat = PayUtil.assembParamToXml(paramMap);
		String resXml="";
		boolean postError=false;
		try {
			resXml = ClientCustomSSL.getInSsl(serverUrl, pkcFile, config.getWeixinAccount()
					,xmlWeChat,"application/xml");
		} catch (Exception e1) {
			postError=true;
			e1.printStackTrace();
		}
		Object[] result=new Object[2];
		result[0]=postError;
		result[1]=resXml;
		return result;
	}
	
	//通知微信正确接收
	public  static void noticeWeChatSuccess(String weiXinPayUrl){
		Map<String,String> parames=new HashMap<String,String>();
		parames.put("return_code", "SUCCESS");
		parames.put("return_msg", "OK");
		//将参数转成xml格式
		String xmlWeChat = PayUtil.assembParamToXml(parames);
		try{    		
			if(StringUtils.isNotBlank(weiXinPayUrl)){
				String s=HttpClientUtil.post(weiXinPayUrl, xmlWeChat, Constants.UTF8);
				System.out.println("notice:"+s);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
