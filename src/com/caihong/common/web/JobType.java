package com.caihong.common.web;
/**
 * 订单状态
 * @author rishi.ding
 *
 */
public enum JobType {
	NORMAL(1,"普通员工"),
	WORKER(2,"工人"),
	FARMER(3,"农民"),
	ORTHER(4,"其他");
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
	JobType(Integer value,String name){
		setValue(value);
		setName(name);
	}
	public static JobType getJobTypeValue(Integer value){
		if (null == value)
			return null;
		for (JobType _enum : JobType.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
}
