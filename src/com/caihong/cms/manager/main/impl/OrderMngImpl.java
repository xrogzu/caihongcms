package com.caihong.cms.manager.main.impl;

import java.io.File;
import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.common.util.AliPay;
import com.caihong.common.util.Num62;
import com.caihong.common.util.PropertyUtils;
import com.caihong.common.util.WeixinPay;
import com.caihong.common.web.OrderType;
import com.caihong.common.web.springmvc.RealPathResolver;
import com.caihong.core.entity.CmsUser;
import com.caihong.core.manager.CmsUserMng;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.caihong.cms.dao.main.OrderDao;
import com.caihong.cms.entity.assist.CmsConfigContentCharge;
import com.caihong.cms.entity.main.Content;
import com.caihong.cms.entity.main.ContentBuy;
import com.caihong.cms.entity.main.ContentCharge;
import com.caihong.cms.entity.main.Order;
import com.caihong.cms.manager.assist.CmsConfigContentChargeMng;
import com.caihong.cms.manager.main.OrderMng;

@Service
@Transactional
public class OrderMngImpl implements OrderMng {
	public static final String WEIXIN_ORDER_QUERY_URL="weixin.orderquery.url";
	public static final String ALI_PAY_URL="alipay.openapi.url";
	
	@Autowired
	private RealPathResolver realPathResolver;
	
	
	@Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = dao.getPage(pageNo, pageSize);
		return page;
	}

	@Transactional(readOnly = true)
	public Order findById(Integer id) {
		Order entity = dao.findById(id);
		return entity;
	}

	public Order save(Order bean) {
		dao.save(bean);
		return bean;
	}

	public Order update(Order bean) {
		Updater<Order> updater = new Updater<Order>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public Order deleteById(Integer id) {
		Order bean = dao.deleteById(id);
		return bean;
	}
	
	public Order[] deleteByIds(Integer[] ids) {
		Order[] beans = new Order[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private OrderDao dao;

	@Autowired
	public void setDao(OrderDao dao) {
		this.dao = dao;
	}

	@Transactional(readOnly = true)
	public Order findByOrderNumber(String orderNumber) {
		return dao.findByOrderNumber(orderNumber);
	}

	@Transactional(readOnly = true)
	public Order findByOutOrderNum(String orderNum, Integer payMethod) {
		return dao.findByOutOrderNum(orderNum, payMethod);
	}
	private Double getWeChatOrderAmount(String outOrderNum,
			CmsConfigContentCharge config){
		Map<String, String>map=WeixinPay.weixinOrderQuery(outOrderNum,
	    			null, getWeiXinPayUrl(), config);
	    String returnCode = map.get("return_code");
	    Double orderAmount=0d;
		if(StringUtils.isNotBlank(returnCode)){
			if (returnCode.equalsIgnoreCase("SUCCESS")) {
			 if (map.get("result_code").equalsIgnoreCase(
					"SUCCESS")) {
				String trade_state = map.get("trade_state");
				//支付成功
				if(trade_state.equalsIgnoreCase("SUCCESS")){
					String total_fee= map.get("total_fee");
					Integer totalFee=Integer.parseInt(total_fee);
					if(totalFee!=0){
						orderAmount=totalFee/100.0;
					}
				}
			 }
			}
		}
		return orderAmount;
	}
	
	private Double getAliPayOrderAmount(String outOrderNum,
			CmsConfigContentCharge config){
		AlipayTradeQueryResponse res=AliPay.query(getAliPayUrl(), config,
				null,outOrderNum);
		Double orderAmount=0d;
		if (null != res && res.isSuccess()) {
			if (res.getCode().equals("10000")) {
				if ("TRADE_SUCCESS".equalsIgnoreCase(res
						.getTradeStatus())) {
					String totalAmout=res.getTotalAmount();
					if(StringUtils.isNotBlank(totalAmout)){
						orderAmount=Double.parseDouble(totalAmout);
					}
				} 
			}
		}
		return orderAmount;
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
					new File(realPathResolver.get(com.caihong.cms.Constants.JEECMS_CONFIG)),WEIXIN_ORDER_QUERY_URL));
		}
	}
	
	private String weiXinPayUrl;
	
	private String aliPayUrl;
	
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

	@Transactional(readOnly = true)
	public Pagination getPageByUser(Integer userId, Integer type, int pageNo, int pageSize) {
		return dao.getPageByUser(userId, type, pageNo, pageSize);
	}
}