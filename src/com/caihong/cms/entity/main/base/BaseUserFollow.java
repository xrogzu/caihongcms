package com.caihong.cms.entity.main.base;
import java.io.Serializable;

import com.caihong.core.entity.CmsUser;

public class BaseUserFollow implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// constructors
	public BaseUserFollow () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseUserFollow (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseUserFollow (
		java.lang.Integer id,
		CmsUser user,
		CmsUser followUser) {

		this.setId(id);
		this.setUser(user);
		this.setFollowUser(followUser);
		initialize();
	}

	protected void initialize () {}
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.util.Date time;
	private CmsUser user;
	private CmsUser followUser;
	
	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="log_id"
     */
	public java.lang.Integer getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	
	public java.util.Date getTime() {
		return time;
	}

	public void setTime(java.util.Date time) {
		this.time = time;
	}

	public CmsUser getUser() {
		return user;
	}

	public void setUser(CmsUser user) {
		this.user = user;
	}

	public CmsUser getFollowUser() {
		return followUser;
	}

	public void setFollowUser(CmsUser followUser) {
		this.followUser = followUser;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getHashCode() {
		return hashCode;
	}

	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.caihong.cms.entity.main.UserFollow)) return false;
		else {
			com.caihong.cms.entity.main.UserFollow userFollow = (com.caihong.cms.entity.main.UserFollow) obj;
			if (null == this.getId() || null == userFollow.getId()) return false;
			else return (this.getId().equals(userFollow.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}
