package com.caihong.common.web;
/**
 * 订单状态
 * @author rishi.ding
 *
 */
public enum OrderStatus {
	PAID(1,"支付"),
	UNPAID(2,"未支付"),
	CANCEL(3,"取消");
	private Integer value;
	private String name;
	public void setValue(Integer value) {
		this.value = value;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取值
	 * @return value
	 */
	public Integer getValue() {
		return value;
	}
	/**
	 * 获取值
	 * @return name
	 */
	public String getName() {
		return name;
	}
	OrderStatus(Integer value,String name){
		setValue(value);
		setName(name);
	}
	public static OrderStatus getOrderStatusValue(Integer value){
		if (null == value)
			return null;
		for (OrderStatus _enum : OrderStatus.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
}
