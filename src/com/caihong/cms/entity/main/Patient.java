package com.caihong.cms.entity.main;

import java.io.Serializable;
import java.util.Date;

import com.caihong.common.web.JobType;
import com.caihong.common.web.ReserveStatus;
import com.caihong.core.entity.CmsUser;

public class Patient  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private CmsUser user;
	private java.lang.Boolean gender;
	private java.lang.String birthday;
	private java.lang.String telphone;
	private java.lang.String idNo;
	private java.lang.String name;
	private java.lang.Integer job;
	private Date time;
	private java.lang.String workAddress;
	private java.lang.String homeAddress;
	
	public String getJobName(){
		if(this.getJob()!=null){
			return JobType.getJobTypeValue(getJob()).getName();
		}else{
			return "";
		}
	}
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
	public java.lang.Boolean getGender() {
		return gender;
	}
	public void setGender(java.lang.Boolean gender) {
		this.gender = gender;
	}
	public java.lang.String getBirthday() {
		return birthday;
	}
	public void setBirthday(java.lang.String birthday) {
		this.birthday = birthday;
	}
	public java.lang.String getTelphone() {
		return telphone;
	}
	public void setTelphone(java.lang.String telphone) {
		this.telphone = telphone;
	}
	public java.lang.String getIdNo() {
		return idNo;
	}
	public void setIdNo(java.lang.String idNo) {
		this.idNo = idNo;
	}
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public java.lang.Integer getJob() {
		return job;
	}
	public void setJob(java.lang.Integer job) {
		this.job = job;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public java.lang.String getWorkAddress() {
		return workAddress;
	}
	public void setWorkAddress(java.lang.String workAddress) {
		this.workAddress = workAddress;
	}
	public java.lang.String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(java.lang.String homeAddress) {
		this.homeAddress = homeAddress;
	}
}
