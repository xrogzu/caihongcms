package com.caihong.common.web;

public enum OrderType {
	REWARD(1,"打赏"),
	RESERVE(2,"预约");
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
	OrderType(Integer value,String name){
		setValue(value);
		setName(name);
	}
	public static OrderType getGetGrainTypeValue(Integer value){
		if (null == value)
			return null;
		for (OrderType _enum : OrderType.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
}
