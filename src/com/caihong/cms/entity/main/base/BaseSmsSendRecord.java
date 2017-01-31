package com.caihong.cms.entity.main.base;

import java.io.Serializable;

public class BaseSmsSendRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String REF = "SmsSendRecord";
	public static String PROP_CREATEON = "createOn";
	public static String PROP_SENDOUTBODY = "sendOutBody";
	public static String PROP_SENDSTATUS = "sendStatus";
	public static String PROP_content = "content";
	public static String PROP_TELPHONE = "telphone";
	public static String PROP_ID = "id";
	public static String PROP_SITE = "site";


	// constructors
	public BaseSmsSendRecord () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseSmsSendRecord (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseSmsSendRecord (
		java.lang.Integer id,
		java.lang.String telphone,
		java.util.Date createOn) {

		this.setId(id);
		this.setTelphone(telphone);
		this.setCreateOn(createOn);
		initialize();
	}

	protected void initialize () {}
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.util.Date createOn;
	private java.lang.String telphone;
	private java.lang.String sendStatus;
	private java.lang.String sendOutBody;
	private java.lang.String content;
	
	private com.caihong.core.entity.CmsSite site;

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




	

	/**
	 * Return the value associated with the column: content
	 */
	public java.lang.String getContent () {
		return content;
	}

	/**
	 * Set the value related to the column: content
	 * @param content the content value
	 */
	public void setContent (java.lang.String content) {
		this.content = content;
	}

	public int getHashCode() {
		return hashCode;
	}

	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}

	public java.util.Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(java.util.Date createOn) {
		this.createOn = createOn;
	}

	public java.lang.String getTelphone() {
		return telphone;
	}

	public void setTelphone(java.lang.String telphone) {
		this.telphone = telphone;
	}

	public java.lang.String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(java.lang.String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public java.lang.String getSendOutBody() {
		return sendOutBody;
	}

	public void setSendOutBody(java.lang.String sendOutBody) {
		this.sendOutBody = sendOutBody;
	}

	public com.caihong.core.entity.CmsSite getSite() {
		return site;
	}

	public void setSite(com.caihong.core.entity.CmsSite site) {
		this.site = site;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.caihong.cms.entity.main.SmsSendRecord)) return false;
		else {
			com.caihong.cms.entity.main.SmsSendRecord sysSendRecord = (com.caihong.cms.entity.main.SmsSendRecord) obj;
			if (null == this.getId() || null == sysSendRecord.getId()) return false;
			else return (this.getId().equals(sysSendRecord.getId()));
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
