package com.caihong.cms.entity.main;

import com.caihong.cms.entity.main.base.BaseSmsSendRecord;

public class SmsSendRecord extends BaseSmsSendRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SmsSendRecord(){
		super();
	}
	
	/**
	 * Constructor for primary key
	 */
	public SmsSendRecord (java.lang.Integer id) {
		super(id);
	}
	/**
	 * Constructor for primary key
	 */
	public SmsSendRecord (java.lang.Integer id,java.lang.String telphone,java.util.Date createOn) {
		super(id,telphone,createOn);
	}

}
