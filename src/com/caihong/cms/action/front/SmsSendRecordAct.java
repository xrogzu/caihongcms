package com.caihong.cms.action.front;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.caihong.cms.entity.main.SmsSendRecord;
import com.caihong.cms.manager.main.SmsSendRecordMng;
import com.caihong.common.page.Pagination;
import com.caihong.common.util.SmsUtils;
import com.caihong.common.web.ResponseUtils;
import com.caihong.core.entity.CmsSite;
import com.caihong.core.web.util.CmsUtils;
import com.mysql.jdbc.StringUtils;


@Controller
public class SmsSendRecordAct {
	private static final Logger log = LoggerFactory.getLogger(SmsSendRecordAct.class);
	private static final int minus_time=30;//验证码有效期 30分钟
	

	@Autowired
	private SmsSendRecordMng manager;
	
	@RequestMapping(value="/sendCode", method = RequestMethod.GET)
	public void sendCode(String phone,HttpServletRequest request,HttpServletResponse response){
		Random random = new Random();
		String code = random.nextInt(9) + "" + random.nextInt(9) + random.nextInt(9) + random.nextInt(9); 
		if(SmsUtils.sendRegValiCode(phone, code)){
			SmsSendRecord bean=new SmsSendRecord();
			bean.setContent(code);
			bean.setTelphone(phone);
			CmsSite site = CmsUtils.getSite(request);
			bean.setSite(site);
			bean=manager.save(bean);
			log.info("save SmsSendRecord id={}", bean.getId());
			ResponseUtils.renderJson(response, "true");
		}else{
			ResponseUtils.renderJson(response, "false");
		}
	}
	
	@RequestMapping(value="/checkPhoneCode", method = RequestMethod.GET)
	public void checkPhoneCode(String code,String phone,HttpServletResponse response){
		if(StringUtils.isNullOrEmpty(phone)||StringUtils.isNullOrEmpty(code)){
			ResponseUtils.renderJson(response, "false");
		}
		String args[]=new String[1];
		args[0]=phone;
		
		Pagination pagination =manager.getPage(1, 1,args);
		if(pagination.getTotalCount()>0){
			List<SmsSendRecord> list=(List<SmsSendRecord>)pagination.getList();
			SmsSendRecord bean=list.get(0);
			if(bean!=null&&bean.getContent().equals(code)){
				Timestamp now = new Timestamp(new Date().getTime()-minus_time*60*1000);
				if(bean.getCreateOn().before(now)){
					ResponseUtils.renderJson(response, "false");
				}else{
					ResponseUtils.renderJson(response, "true");
				}
				
			}else{
				ResponseUtils.renderJson(response, "false");
			}
			
		}else{
			ResponseUtils.renderJson(response, "false");
		}
	}
	
	
}
