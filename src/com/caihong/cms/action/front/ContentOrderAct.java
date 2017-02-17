package com.caihong.cms.action.front;


import static com.caihong.cms.Constants.TPLDIR_SPECIAL;
import static com.caihong.common.page.SimplePage.cpn;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.JDOMException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.caihong.cms.entity.assist.CmsConfigContentCharge;
import com.caihong.cms.entity.main.ContentCharge;
import com.caihong.cms.entity.main.GrainBuyConfig;
import com.caihong.cms.entity.main.Order;
import com.caihong.cms.entity.main.Reserve;
import com.caihong.cms.manager.assist.CmsConfigContentChargeMng;
import com.caihong.cms.manager.main.GrainBuyConfigMng;
import com.caihong.cms.manager.main.OrderMng;
import com.caihong.cms.manager.main.ReserveMng;
import com.caihong.common.util.PropertyUtils;
import com.caihong.common.util.StrUtils;
import com.caihong.common.util.WeixinPay;
import com.caihong.common.web.CookieUtils;
import com.caihong.common.web.GetGrainType;
import com.caihong.common.web.OrderStatus;
import com.caihong.common.web.OrderType;
import com.caihong.common.web.ReserveStatus;
import com.caihong.common.web.ResponseUtils;
import com.caihong.common.web.session.SessionProvider;
import com.caihong.common.web.springmvc.RealPathResolver;
import com.caihong.core.entity.CmsSite;
import com.caihong.core.entity.CmsUser;
import com.caihong.core.manager.CmsUserMng;
import com.caihong.core.web.WebErrors;
import com.caihong.core.web.util.CmsUtils;
import com.caihong.core.web.util.FrontUtils;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import com.caihong.common.page.Pagination;
import com.caihong.common.util.AliPay;
import com.caihong.common.util.Num62;
import com.caihong.common.util.PayUtil;

@Controller
public class ContentOrderAct {
	//收费
	public static final Integer CONTENT_PAY_MODEL_CHARGE=1;
	//打赏
	public static final Integer CONTENT_PAY_MODEL_REWARD=2;
	public static final String WEIXIN_PAY_URL="weixin.pay.url";
	public static final String ALI_PAY_URL="alipay.openapi.url";
	

	public static final String CONTENT_REWARD="tpl.content.reward";
	public static final String CONTENT_ALIPAY_MOBILE="tpl.content.alipay.mobile";
	public static final String CONTENT_ORDERS="tpl.content.orders";
	public static final String WEIXIN_AUTH_CODE_URL ="weixin.auth.getCodeUrl";
	
//	private static final String url="http://www.caihongyixue.com/buy/reward%s.jspx";
	
	//支付购买（先选择支付方式，在进行支付）
	@RequestMapping(value = "/content/buy.jspx")
	public String contentBuy(Integer doctorId,
			HttpServletRequest request,
			HttpServletResponse response,ModelMap model) throws JSONException {
		WebErrors errors=WebErrors.create(request);
		CmsUser user=CmsUtils.getUser(request);
		CmsSite site=CmsUtils.getSite(request);
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}else{
			if(doctorId==null){
				errors.addErrorCode("error.required","doctorId");
				return FrontUtils.showError(request, response, model, errors);
			}else{
				CmsUser content=userMng.findById(doctorId);
			    if(content!=null){
			  	   	String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
				  		boolean webCatBrowser=false;
				  		String wxopenid=null;
			  	        if (ua.indexOf("micromessenger") > 0) {
			  	        	// 是微信浏览器
			  	        	webCatBrowser=true;
			  	        	wxopenid=(String) session.getAttribute(request, "wxopenid");
			  	        }
			  	    	String orderNumber=System.currentTimeMillis()+RandomStringUtils.random(5,Num62.N10_CHARS);
			  	    	FrontUtils.frontData(request, model, site);
				  		model.addAttribute("doctorId", doctorId);
				  		model.addAttribute("orderNumber", orderNumber);
				  		model.addAttribute("content", content);
				  		model.addAttribute("type", ContentCharge.MODEL_CHARGE);
				  		model.addAttribute("webCatBrowser", webCatBrowser);
				  		model.addAttribute("wxopenid", wxopenid);
				  		return FrontUtils.getTplPath(request, site.getSolutionPath(),
								TPLDIR_SPECIAL, CONTENT_REWARD);
			  	    
			    }else{
			    	errors.addErrorCode("error.beanNotFound","content");
			    	return FrontUtils.showError(request, response, model, errors);
			    }
			}
		}
	}
	
	//彩虹币购买
	@RequestMapping(value = "/buy/reward{objectId}.jspx")
	public String contentReward(String code, @PathVariable(value="objectId")Integer objectId,
			HttpServletRequest request,Integer type,
			HttpServletResponse response,ModelMap model) throws JSONException {
		WebErrors errors=WebErrors.create(request);
		CmsSite site=CmsUtils.getSite(request);
		CmsUser user=CmsUtils.getUser(request);
		if(user!=null){
				String returnurl = request.getHeader("Referer");  
				if(returnurl==null||returnurl.equals("")){
					returnurl =site.getProtocol()+site.getDomain();  
				}
				model.addAttribute("returnurl",returnurl);
				double  s=1d;
				if(objectId!=null){
					if(type==OrderType.REWARD.getValue()){
					 GrainBuyConfig config=grainBuyConfigMng.findById(objectId);
					 	s=config.getPrice();
					 	model.addAttribute("rewardAmount", config.getPrice());
					}else{
						Reserve config=reserveMng.findById(objectId);
					 	s=config.getPrice();
					 	model.addAttribute("rewardAmount", config.getPrice());
					}
				}
	  	    	String ua = ((HttpServletRequest) request).getHeader("user-agent")
			  	          .toLowerCase();
		  		boolean webCatBrowser=false;
		  		String wxopenid=null;
	  	        if (ua.indexOf("micromessenger") > 0) {
	  	        	// 是微信浏览器
	  	        	webCatBrowser=true;
	  	        	wxopenid=(String) session.getAttribute(request, "wxopenid");
	  	        }     
	  	      if(type==OrderType.REWARD.getValue()){
				Pagination pagination=grainBuyConfigMng.getPage(1, 100);
				List<GrainBuyConfig> confList=null;
				if(pagination!=null){
					 confList= (List<GrainBuyConfig>)pagination.getList();
				}
				model.addAttribute("confList", confList);
	  	      }
				model.addAttribute("type", type);
				
	  	    	String orderNumber=System.currentTimeMillis()+RandomStringUtils.random(5,Num62.N10_CHARS);
	  	    	FrontUtils.frontData(request, model, site);
	  	    	
	  			model.addAttribute("objectId", objectId);
		  		model.addAttribute("orderNumber", orderNumber);		  		
		  		model.addAttribute("webCatBrowser", webCatBrowser);
		  		model.addAttribute("wxopenid", wxopenid);
		  		
		  		model.addAttribute("randomOne", s);
		  		return FrontUtils.getTplPath(request, site.getSolutionPath(),
						TPLDIR_SPECIAL, CONTENT_REWARD);
		   
		}else{
			errors.addErrorCode("error.beanNotFound","user");
	    	return FrontUtils.showError(request, response, model, errors);
		}
	}
	
	@RequestMapping(value = "/buy/fixSelect.jspx")
	public String contentFixSelect(
			Integer objectId,String orderNumber,
			Double rewardAmount,Integer type,String returnurl,
			HttpServletRequest request,
			HttpServletResponse response,ModelMap model) throws JSONException {
		WebErrors errors=WebErrors.create(request);
		CmsSite site=CmsUtils.getSite(request);
    	String ua = ((HttpServletRequest) request).getHeader("user-agent")
	  	          .toLowerCase();
  		boolean webCatBrowser=false;
  		String wxopenid=null;
        if (ua.indexOf("micromessenger") > 0) {
        	// 是微信浏览器
        	webCatBrowser=true;
        	wxopenid=(String) session.getAttribute(request, "wxopenid");
        }
        if(objectId==null){
			errors.addErrorCode("error.required","objectId");
			return FrontUtils.showError(request, response, model, errors);
		}else{
			Object obj=null;
			if(type==OrderType.REWARD.getValue()){
				obj=grainBuyConfigMng.findById(objectId);
			}else{
				obj=reserveMng.findById(objectId);				 	
			}
		    if(obj!=null){
		    	if(type==OrderType.REWARD.getValue()){					
					 	model.addAttribute("rewardAmount", ((GrainBuyConfig)obj).getPrice());
					}else{
						obj=reserveMng.findById(objectId);
					 	model.addAttribute("rewardAmount", ((Reserve)obj).getPrice());
					}
		    	FrontUtils.frontData(request, model, site);
		    	model.addAttribute("returnurl",returnurl);
				model.addAttribute("objectId", objectId);
		  		model.addAttribute("orderNumber", orderNumber);		  		
		  		
				
		  		model.addAttribute("type", type);
		  		model.addAttribute("webCatBrowser", webCatBrowser);
		  		model.addAttribute("wxopenid", wxopenid);
		  		return FrontUtils.getTplPath(request, site.getSolutionPath(),
						TPLDIR_SPECIAL, CONTENT_REWARD);
		    }else{
		    	errors.addErrorCode("error.beanNotFound","content");
		    	return FrontUtils.showError(request, response, model, errors);
		    }
		}
	}
	
	//订单记录
	@RequestMapping(value = "/content/orders.jspx")
	public String contentOrders(Integer doctorId,Integer type,Integer pageNo,
			HttpServletRequest request,HttpServletResponse response
			,ModelMap model) throws JSONException {
		WebErrors errors=WebErrors.create(request);
		CmsSite site=CmsUtils.getSite(request);
		if(type==null){
			type=OrderType.REWARD.getValue();
		}
		if(doctorId==null){
			errors.addErrorCode("error.required","doctorId");
			return FrontUtils.showError(request, response, model, errors);
		}else{
			CmsUser content=userMng.findById(doctorId);
		    if(content!=null){
	  	    	FrontUtils.frontData(request, model, site);
	  	    	Pagination pagination=orderMng.getPageByUser(doctorId,
	  	    			type, cpn(pageNo), CookieUtils.getPageSize(request));
		  		model.addAttribute("doctorId", doctorId);
		  		model.addAttribute("type", type);
		  		model.addAttribute("pagination", pagination);
		  		return FrontUtils.getTplPath(request, site.getSolutionPath(),
						TPLDIR_SPECIAL, CONTENT_ORDERS);
		    }else{
		    	errors.addErrorCode("error.beanNotFound","content");
		    	return FrontUtils.showError(request, response, model, errors);
		    }
		}
	}
		
	@RequestMapping(value = "/reward/random.jspx")
	public void randomReward(HttpServletRequest request,
			HttpServletResponse response) {
		 CmsConfigContentCharge config=configContentChargeMng.getDefault(); 
		 Double max=config.getRewardMax();
		 Double min=config.getRewardMin();
	     Double s =StrUtils.retainTwoDecimal(min + ((max - min) * new Random().nextDouble()));
	     ResponseUtils.renderJson(response, s.toString());
	}
	
	
	@RequestMapping(value = "/buy/test.jspx")
	public void test(HttpServletRequest request,
			HttpServletResponse response) {
		WeixinPay.test();
	}
	
	/**
	 * 选择支付方式
	 * @param doctorId 打赏用户ID
	 * @param orderNumber 订单号
	 * @param payMethod 支付方式 1微信扫码 2支付宝即时支付  3微信浏览器打开[微信移动端] 4支付宝扫码5支付宝手机网页
	 * @param rewardAmount 金额
	 */
	@RequestMapping(value = "/buy/selectPay.jspx")
	public String selectPay(String orderNumber,
			Integer payMethod,Double rewardAmount,Integer objectId,Integer type,String returnurl,
			HttpServletRequest request,
			HttpServletResponse response,ModelMap model) throws JSONException {
		WebErrors errors=WebErrors.create(request);
		CmsUser user=CmsUtils.getUser(request);
		CmsSite site=CmsUtils.getSite(request);
		initWeiXinPayUrl();
		initAliPayUrl();
		if(objectId==null||type==null){
			errors.addErrorCode("error.required","objectId");
			return FrontUtils.showError(request, response, model, errors);
		}else{
			Object buyConfig=null;
			if(type==OrderType.REWARD.getValue()){
			 buyConfig= grainBuyConfigMng.findById(objectId);
			}else{
				buyConfig=reserveMng.findById(objectId);
			}
		    if(buyConfig!=null){
		    		String content="";
		    		Double totalAmount=0d;
		    		if(type==OrderType.REWARD.getValue()){
		    			GrainBuyConfig conf=(GrainBuyConfig)buyConfig;
		    			content="彩虹币"+conf.getCount()+"个";
		    			totalAmount=conf.getPrice();
		    		}else{
		    			Reserve reserve=(Reserve)buyConfig;
		    			content="预约医生:"+reserve.getDoctorUser().getRealname();
		    			totalAmount=reserve.getPrice();
		    		}
		  	    	CmsConfigContentCharge config=configContentChargeMng.getDefault();
		  			
		  	    	if(user!=null){
		  	    		cache.put(new Element(orderNumber,
			  	    			user.getId()+","+rewardAmount+","+objectId+","+type+","+content));
		  	    	}else{
		  	    		cache.put(new Element(orderNumber,rewardAmount+","+objectId+","+type+","+content));
		  	    	}
  	    			
  	    			if(rewardAmount!=null){
  	    				totalAmount=rewardAmount;
  	    			}
		  	    	if(payMethod!=null){
		  	    		if(payMethod==1){
		  	    			return WeixinPay.enterWeiXinPay(getWeiXinPayUrl(),config,content,
									orderNumber,objectId+"",returnurl,rewardAmount,request, response, model);
		  	    		}else if(payMethod==3){
		  	    			String openId=(String) session.getAttribute(request, "wxopenid");
		  	    			return WeixinPay.weixinPayByMobile(getWeiXinPayUrl(),config,
		  	    					openId,content, orderNumber, rewardAmount,objectId+"",
		  	    					request, response, model);
		  	    		}else if(payMethod==2){
		  	    			return AliPay.enterAliPayImmediate(config,orderNumber,content, rewardAmount,content,returnurl,null,
									request, response, model);
		  	    		}else if(payMethod==4){
		  	    			return AliPay.enterAlipayScanCode(request,response, model,
		  	    					getAliPayUrl(), config, content, content,returnurl,
		  	    					orderNumber, totalAmount);
		  	    		}else if(payMethod==5){		  				
		  					model.addAttribute("url", returnurl);
				  	    	model.addAttribute("objectId",objectId);
				  	    	model.addAttribute("type",type);
				  	    	model.addAttribute("orderNumber",orderNumber);
		  					model.addAttribute("content", content);
		  					model.addAttribute("rewardAmount", rewardAmount);
		  	    			FrontUtils.frontData(request, model, site);
		  					return FrontUtils.getTplPath(request, site.getSolutionPath(),
		  							TPLDIR_SPECIAL, CONTENT_ALIPAY_MOBILE);
		  	    		}
					}//支付宝
		  	    	
					return AliPay.enterAliPayImmediate(config,orderNumber,content, rewardAmount,content,returnurl,null,
							request, response, model);
		  	    
		    }else{
		    	errors.addErrorCode("error.beanNotFound","ObjectId");
		    	return FrontUtils.showError(request, response, model, errors);
		    }
		}
	}
	
	@RequestMapping(value = "/buy/alipayInMobile.jspx")
	public String enterAlipayInMobile(String orderNumber,Integer objectId,
			Double rewardAmount,HttpServletRequest request,String returnurl,String content ,
			HttpServletResponse response,ModelMap model) throws JSONException {
		WebErrors errors=WebErrors.create(request);
		initAliPayUrl();
		if(objectId==null){
			errors.addErrorCode("error.required","objectId");
			return FrontUtils.showError(request, response, model, errors);
		}else{
			
			if(content!=null){			
				
				CmsConfigContentCharge config=configContentChargeMng.getDefault();
			
				
				AliPay.enterAlipayInMobile(request, response,
						getAliPayUrl(), config, content, orderNumber, rewardAmount,content,returnurl);
				return "";
			}else{
		    	errors.addErrorCode("error.beanNotFound","content");
		    	return FrontUtils.showError(request, response, model, errors);
		    }
		}
	}
	
	
	/**
	 * 微信回调
	 * @param code
	 */
	@RequestMapping(value = "/order/payCallByWeiXin.jspx")
	public void orderPayCallByWeiXin(String orderNumber,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws JDOMException, IOException, JSONException {
		JSONObject json = new JSONObject();
		CmsConfigContentCharge config=configContentChargeMng.getDefault();
		if (StringUtils.isNotBlank(orderNumber)) {
			Order order=orderMng.findByOrderNumber(orderNumber);
			if (order!=null&&StringUtils.isNotBlank(order.getOrderNumWeiXin())) {
				//已成功支付过
				WeixinPay.noticeWeChatSuccess(getWeiXinPayUrl());
				json.put("status", 4);
			} else {
				//订单未成功支付
				json.put("status", 6);
			}
		}else{
			// 回调结果
			String xml_receive_result = PayUtil.getWeiXinResponse(request);
			System.out.println("微信回调参数："+xml_receive_result);
			if (StringUtils.isBlank(xml_receive_result)) {
				//检测到您可能没有进行扫码支付，请支付
				json.put("status", 5);
			} else {
				Map<String, String> result_map = PayUtil.parseXMLToMap(xml_receive_result);
				String sign_receive = result_map.get("sign");
				result_map.remove("sign");
				String key = config.getWeixinPassword();
				if (key == null) {
					//微信扫码支付密钥错误，请通知商户
					json.put("status", 1);
				}
//				String checkSign = PayUtil.createSign(result_map, key);
//				if (checkSign != null && checkSign.equals(sign_receive)) {
					try {
						if (result_map != null) {
							String return_code = result_map.get("return_code");
							if ("SUCCESS".equals(return_code)
									&& "SUCCESS".equals(result_map
											.get("result_code"))) {
								// 微信返回的微信订单号（属于微信商户管理平台的订单号，跟自己的系统订单号不一样）
								String transaction_id = result_map
										.get("transaction_id");
								// 商户系统的订单号，与请求一致。
								String out_trade_no = result_map.get("out_trade_no");
								// 通知微信该订单已处理
								WeixinPay.noticeWeChatSuccess(getWeiXinPayUrl());
								payAfter(out_trade_no,transaction_id, null);
								//支付成功
								json.put("status", 0);
							} else if ("SUCCESS".equals(return_code)
									&& result_map.get("err_code") != null) {
								String message = result_map.get("err_code_des");
								json.put("status", 2);
								json.put("error", message);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				/*} else {
					Map<String, String> parames = new HashMap<String, String>();
					parames.put("return_code", "FAIL");
					parames.put("return_msg", "校验错误");
					// 将参数转成xml格式
					String xmlWeChat = PayUtil.assembParamToXml(parames);
					try {
						HttpClientUtil.post(getWeiXinPayUrl(), xmlWeChat, Constants.UTF8);
					} catch (Exception e) {
						e.printStackTrace();
					}
					//支付参数错误，请重新支付!
					json.put("status", 3);
				}*/
			}
		}
		ResponseUtils.renderJson(response, json.toString());
	}
	

	
	//支付宝即时支付回调地址
	@RequestMapping(value = "/order/payCallByAliPay.jspx")
	public String payCallByAliPay(HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
					throws UnsupportedEncodingException {
		CmsConfigContentCharge config=configContentChargeMng.getDefault();
		CmsSite site=CmsUtils.getSite(request);
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		System.out.println("支付宝返回参数："+requestParams.toString());
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		//商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		FrontUtils.frontData(request, model, site);
		if(PayUtil.verifyAliPay(params,config.getAlipayPartnerId(),config.getAlipayKey())){//验证成功
			if(trade_status.equals("TRADE_FINISHED")||trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
				CmsUser content=payAfter(out_trade_no, null, trade_no);
				try {
					response.sendRedirect("http://www.caihongyixue.com/blog/"+content.getUsername()+".jspx");
				} catch (IOException e) {
					//e.printStackTrace();
				}
				return  "http://www.caihongyixue.com/blog/"+content.getUsername()+".jspx";
				//注意：TRADE_FINISHED
				//该种交易状态只在两种情况下出现
				//1、开通了普通即时到账，买家付款成功后。
				//2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
				//TRADE_SUCCESS
				//该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
			}
		}else{//验证失败
			return  FrontUtils.showMessage(request, model,"error.alipay.status.valifail");
		}
		return  FrontUtils.showMessage(request, model,"error.alipay.status.payfail");
	}
	/**
	 * 退费
	 * @param orderNumber
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/buy/refund.jspx")
	public void refund(String orderNumber,String reason,HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		JSONObject json = new JSONObject();
		try{
			if(orderNumber!=null){
				Order order=orderMng.findByOrderNumber(orderNumber);
				if(order==null){
					json.put("status", 2);
				}else{
					CmsConfigContentCharge config=configContentChargeMng.getDefault();
					if(order.getOrderNumWeiXin()!=null){
						Map<String,String> map= WeixinPay.refoundWeixin(request,  config,  order.getOrderNum(), order.getAmount());
						if(map!=null&&map.get("result_code").equals("SUCCESS")){
							json.put("status", 0);
							refundAfter(order.getOrderNum(),order.getOrderNumWeiXin(),	null);
						}else{
							json.put("status", 1);
						}
					}else{
						AlipayTradeRefundResponse res=AliPay.AlipayRefund(getAliPayUrl(), config, order.getOrderNum(), order.getAmount(), reason);
						if(res!=null&&res.isSuccess()){
							json.put("status", 0);
							refundAfter(order.getOrderNum(),null,	order.getOrderNumAliPay());
						}else{
							json.put("status", 1);
						}
						
					}
				}
			}else{
				json.put("status", 1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		ResponseUtils.renderJson(response, json.toString());
	}
	
	
	//支付宝查询订单状态（扫码支付和手机网页支付均由此处理订单）
	@RequestMapping(value = "/content/orderQuery.jspx")
	public void aliPayOrderQuery(String orderNumber,HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
					throws UnsupportedEncodingException {
		CmsConfigContentCharge config=configContentChargeMng.getDefault();
		JSONObject json = new JSONObject();
		CmsSite site=CmsUtils.getSite(request);
		initAliPayUrl();
		FrontUtils.frontData(request, model, site);
		AlipayTradeQueryResponse res=AliPay.query(getAliPayUrl(), config,
				orderNumber,null);
		try {
			if (null != res && res.isSuccess()) {
				if (res.getCode().equals("10000")) {
					if ("TRADE_SUCCESS".equalsIgnoreCase(res
							.getTradeStatus())) {
							json.put("status", 0);
							payAfter(orderNumber, 
									null, res.getTradeNo());
					} else if ("WAIT_BUYER_PAY".equalsIgnoreCase(res
							.getTradeStatus())) {
						// 等待用户付款状态，需要轮询查询用户的付款结果
						json.put("status", 1);
					} else if ("TRADE_CLOSED".equalsIgnoreCase(res.getTradeStatus())) {
						// 表示未付款关闭，或已付款的订单全额退款后关闭
						json.put("status", 2);
					} else if ("TRADE_FINISHED".equalsIgnoreCase(res.getTradeStatus())) {
						// 此状态，订单不可退款或撤销
						json.put("status", 0);
					}
				} else {
					// 如果请求未成功，请重试
					json.put("status", 3);
				}
			}else{
				json.put("status", 4);
			}
		} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		ResponseUtils.renderJson(response, json.toString());
	}
	/**
	 * 退费之后的事件
	 * @param orderNumber
	 * @param weixinOrderNum
	 * @param alipyOrderNum
	 */
	private CmsUser refundAfter(String orderNumber,String weixinOrderNum,	String alipyOrderNum){
		
		 CmsUser user= null;
	   
		if(StringUtils.isNotBlank(orderNumber)){
		    Order b=orderMng.findByOrderNumber(orderNumber);
		    //不能重复提交
		    if(b!=null&&(b.getStatus()==OrderStatus.PAID.getValue())){
		    	b.setStatus(OrderStatus.CANCEL.getValue());
		    	orderMng.save(b);
			  
			   if(b.getType()==OrderType.REWARD.getValue()){//如果是彩虹币
				   GrainBuyConfig config=grainBuyConfigMng.findById(b.getObjectId());			   	    
		   	    	userMng.updateGrainCnt(b.getUser(), null, -config.getCount(), GetGrainType.REFUND);//退费
			   }else{
				   Reserve reserve=reserveMng.findById(b.getObjectId());		   	    	
		   	    	reserve.setPayStatus(false);
		   	    	reserve.setStatus(ReserveStatus.CANCEL.getValue());
		   	    	reserveMng.save(reserve);
			   }
		    }
		}
	    return user;
	}
	
	private CmsUser payAfter(String orderNumber,
			String weixinOrderNum,
			String alipyOrderNum){
		Element e = cache.get(orderNumber);
	    CmsUser user= null;
	    System.out.println(e);
		if(e!=null&&StringUtils.isNotBlank(orderNumber)){
		    Order b=orderMng.findByOrderNumber(orderNumber);
		    //不能重复提交
		    if(b==null){
		    	Object obj= e.getObjectValue();
				String[] objArray=new String[4];
				if(obj!=null){
					objArray=obj.toString().split(",");
				}
				Double rewardAmount=null;
				Integer buyUserId=null;
				
				Integer objectId=null;
				Integer type=null;
				String content=null;
				if(objArray!=null&&objArray[0]!=null){
					buyUserId=Integer.parseInt(objArray[0]) ;
				}
				if(objArray!=null&&objArray[1]!=null&&StringUtils.isNotBlank(objArray[1])){
					rewardAmount=Double.parseDouble(objArray[1]);
				}
				if(objArray!=null&&objArray[2]!=null&&StringUtils.isNotBlank(objArray[2])
						&&!objArray[2].toLowerCase().equals("null")){
					objectId=Integer.parseInt(objArray[2]);
				}
				if(objArray!=null&&objArray[3]!=null){
					type=Integer.parseInt(objArray[3]);;
				}
				if(objArray!=null&&objArray[4]!=null){
					content=objArray[4];
				}
				
			    Order order=new Order();
			    if(buyUserId!=null&&type!=null&&objectId!=null){
			    	user=userMng.findById(buyUserId);
			    	order.setUser(user);
			    	order.setType(type);;
			   	   
			   	    if(type==OrderType.REWARD.getValue()){ //彩虹币购买
			   	    	GrainBuyConfig config=grainBuyConfigMng.findById(objectId);
			   	    			   	    	
			   	    	rewardAmount=config.getPrice();
			   	    	userMng.updateGrainCnt(user, null, config.getCount(), GetGrainType.BUY);//获取彩虹币
			   	    }else{
			   	    	Reserve reserve=reserveMng.findById(objectId);
			   	    	rewardAmount=reserve.getPrice();
			   	    	reserve.setPayStatus(true);
			   	    	reserveMng.update(reserve);
			   	    }
			   	    order.setObjectId(objectId);
			   	    order.setAmount(rewardAmount);
			   	    order.setOrderNum(orderNumber);
			   	    order.setNote(content);
			   	    order.setTime(Calendar.getInstance().getTime());
			 		// 这里是把微信商户的订单号放入了交易号中
		 			order.setOrderNumWeiXin(weixinOrderNum);
		 			order.setOrderNumAliPay(alipyOrderNum);
		 			order.setStatus(OrderStatus.PAID.getValue());
		 			order=orderMng.save(order);
		 			
			 	}
		    }
		}
	    return user;
	}
	
	private void initAliPayUrl(){
		if(getAliPayUrl()==null){
			setAliPayUrl(PropertyUtils.getPropertyValue(
					new File(realPathResolver.get(com.caihong.cms.Constants.JEECMS_CONFIG)),ALI_PAY_URL));
		}
	}
	
	private void initWeiXinPayUrl(){
		if(getWeiXinPayUrl()==null){
			setWeiXinPayUrl(PropertyUtils.getPropertyValue(
					new File(realPathResolver.get(com.caihong.cms.Constants.JEECMS_CONFIG)),WEIXIN_PAY_URL));
		}
	}
	
	private String weiXinPayUrl;
	
	private String aliPayUrl;
	private String weixinAuthCodeUrl;
	
	public String getWeiXinPayUrl() {
		return weiXinPayUrl;
	}

	public void setWeiXinPayUrl(String weiXinPayUrl) {
		this.weiXinPayUrl = weiXinPayUrl;
	}

	public String getAliPayUrl() {
		return aliPayUrl;
	}

	public void setAliPayUrl(String aliPayUrl) {
		this.aliPayUrl = aliPayUrl;
	}
	
	public String getWeixinAuthCodeUrl() {
		return weixinAuthCodeUrl;
	}

	public void setWeixinAuthCodeUrl(String weixinAuthCodeUrl) {
		this.weixinAuthCodeUrl = weixinAuthCodeUrl;
	}


	@Autowired
	private GrainBuyConfigMng grainBuyConfigMng;

	@Autowired
	private OrderMng orderMng;
	
	@Autowired
	private ReserveMng reserveMng;

	@Autowired
	private CmsConfigContentChargeMng configContentChargeMng;
	@Autowired
	private RealPathResolver realPathResolver;

	@Autowired
	private CmsUserMng userMng;
	@Autowired
	private SessionProvider session;
	@Autowired
	@Qualifier("contentOrderTemp")
	private Ehcache cache;
}

