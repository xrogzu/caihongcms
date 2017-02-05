package com.caihong.cms.entity.main;

import com.caihong.cms.entity.main.base.BaseUserFollow;
import com.caihong.core.entity.CmsUser;

public class UserFollow extends BaseUserFollow{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UserFollow(){
		super();
	}
	
	/**
	 * Constructor for primary key
	 */
	public UserFollow (java.lang.Integer id) {
		super(id);
	}
	/**
	 * Constructor for primary key
	 */
	public UserFollow (java.lang.Integer id,CmsUser user,CmsUser followUser) {
		super(id,user,followUser);
	}
}
