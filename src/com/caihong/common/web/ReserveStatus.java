package com.caihong.common.web;
/**
 * 订单状态
 * @author rishi.ding
 *
 */
public enum ReserveStatus {
	RESERVE(1,"已申请"),
	ARRANGED(2,"已安排"),
	CONSULTATION(3,"已会诊"),
	CANCEL(4,"已取消");
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
	ReserveStatus(Integer value,String name){
		setValue(value);
		setName(name);
	}
	public static ReserveStatus getReserveStatusValue(Integer value){
		if (null == value)
			return null;
		for (ReserveStatus _enum : ReserveStatus.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
}
