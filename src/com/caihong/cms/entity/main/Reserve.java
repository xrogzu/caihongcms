package com.caihong.cms.entity.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.caihong.core.entity.CmsUser;

public class Reserve implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.Double price;
	private java.util.Date expectTime;
	private java.lang.Integer status;
	private java.util.Date time;
	private java.lang.Boolean payStatus;
	private java.lang.String orderNum;
	private java.lang.String diagnosis;
	private java.lang.String clinicalDiagnosis;
	private CmsUser doctorUser;
	private Patient patient;
	private CmsUser reserveUser;
	private java.util.List<com.caihong.cms.entity.main.ReserveAttachment> attachments;
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.lang.Double getPrice() {
		return price;
	}
	public void setPrice(java.lang.Double price) {
		this.price = price;
	}
	public java.util.Date getExpectTime() {
		return expectTime;
	}
	public void setExpectTime(java.util.Date expectTime) {
		this.expectTime = expectTime;
	}
	public java.lang.Integer getStatus() {
		return status;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	public java.util.Date getTime() {
		return time;
	}
	public void setTime(java.util.Date time) {
		this.time = time;
	}
	public java.lang.Boolean getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(java.lang.Boolean payStatus) {
		this.payStatus = payStatus;
	}
	public java.lang.String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(java.lang.String orderNum) {
		this.orderNum = orderNum;
	}
	public java.lang.String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(java.lang.String diagnosis) {
		this.diagnosis = diagnosis;
	}
	public java.lang.String getClinicalDiagnosis() {
		return clinicalDiagnosis;
	}
	public void setClinicalDiagnosis(java.lang.String clinicalDiagnosis) {
		this.clinicalDiagnosis = clinicalDiagnosis;
	}
	public CmsUser getDoctorUser() {
		return doctorUser;
	}
	public void setDoctorUser(CmsUser doctorUser) {
		this.doctorUser = doctorUser;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public CmsUser getReserveUser() {
		return reserveUser;
	}
	public void setReserveUser(CmsUser reserveUser) {
		this.reserveUser = reserveUser;
	}
	public java.util.List<com.caihong.cms.entity.main.ReserveAttachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(java.util.List<com.caihong.cms.entity.main.ReserveAttachment> attachments) {
		this.attachments = attachments;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void addToAttachmemts(String path, String name, String filename) {
		List<ReserveAttachment> list = getAttachments();
		if (list == null) {
			list = new ArrayList<ReserveAttachment>();
			setAttachments(list);
		}
		ReserveAttachment ca = new ReserveAttachment(path, name);
		if (!StringUtils.isBlank(filename)) {
			ca.setFilename(filename);
		}
		list.add(ca);
	}
}
