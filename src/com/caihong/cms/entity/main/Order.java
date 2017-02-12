package com.caihong.cms.entity.main;

import java.io.Serializable;

import com.caihong.core.entity.CmsUser;

/**
 * 订单
 * @author Administrator
 *
 */
public class Order  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	public static final Integer PAY_METHOD_WECHAT=1;
	public static final Integer PAY_METHOD_ALIPAY=2;
	
	//订单号错误
	public static final Integer PRE_PAY_STATUS_ORDER_NUM_ERROR=2;
	//订单成功
	public static final Integer PRE_PAY_STATUS_SUCCESS=1;
	//订单金额不足以购买内容
	public static final Integer PRE_PAY_STATUS_ORDER_AMOUNT_NOT_ENOUGH=3;
	
	private java.lang.Integer id;
	private CmsUser user;
	private CmsUser rewardUser;
	private java.lang.Double amount;
	private String orderNum;
	private String orderNumWeiXin;
	private String orderNumAliPay;
		// fields
	private java.util.Date time;	
	private java.lang.Integer type;
	private java.lang.Integer status;
	private GrainBuyConfig grainConfig;
	
	public int getPrePayStatus() {
		return prePayStatus;
	}

	public void setPrePayStatus(int prePayStatus) {
		this.prePayStatus = prePayStatus;
	}

	private int prePayStatus;
	
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public CmsUser getUser() {
		return user;
	}
	public void setUser(CmsUser user) {
		this.user = user;
	}
	public CmsUser getRewardUser() {
		return rewardUser;
	}
	public void setRewardUser(CmsUser rewardUser) {
		this.rewardUser = rewardUser;
	}
	public java.lang.Double getAmount() {
		return amount;
	}
	public void setAmount(java.lang.Double amount) {
		this.amount = amount;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getOrderNumWeiXin() {
		return orderNumWeiXin;
	}
	public void setOrderNumWeiXin(String orderNumWeiXin) {
		this.orderNumWeiXin = orderNumWeiXin;
	}
	public String getOrderNumAliPay() {
		return orderNumAliPay;
	}
	public void setOrderNumAliPay(String orderNumAliPay) {
		this.orderNumAliPay = orderNumAliPay;
	}
	public java.util.Date getTime() {
		return time;
	}
	public void setTime(java.util.Date time) {
		this.time = time;
	}
	public java.lang.Integer getType() {
		return type;
	}
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	public java.lang.Integer getStatus() {
		return status;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	public GrainBuyConfig getGrainConfig() {
		return grainConfig;
	}
	public void setGrainConfig(GrainBuyConfig grainConfig) {
		this.grainConfig = grainConfig;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
