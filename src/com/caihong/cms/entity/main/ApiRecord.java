package com.caihong.cms.entity.main;

import com.caihong.cms.entity.main.base.BaseApiRecord;



public class ApiRecord extends BaseApiRecord {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ApiRecord () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ApiRecord (java.lang.Long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ApiRecord (
		java.lang.Long id,
		com.caihong.cms.entity.main.ApiAccount apiAccount,
		com.caihong.cms.entity.main.ApiInfo apiInfo,
		java.util.Date callTime,
		java.lang.Long callTimeStamp) {

		super (
			id,
			apiAccount,
			apiInfo,
			callTime,
			callTimeStamp);
	}

/*[CONSTRUCTOR MARKER END]*/


}