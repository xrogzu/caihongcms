package com.caihong.common.web;

public enum GetGrainType {
	REG(1,"注册"),
	BBS(2,"论坛"),
	BUY(3,"购买"),
	SEND(4,"打赏"),
	SENDTO(5,"打赏用户");
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
	GetGrainType(Integer value,String name){
		setValue(value);
		setName(name);
	}
	public static GetGrainType getGetGrainTypeValue(Integer value){
		if (null == value)
			return null;
		for (GetGrainType _enum : GetGrainType.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
}
