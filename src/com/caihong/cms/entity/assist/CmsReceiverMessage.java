package com.caihong.cms.entity.assist;

import org.json.JSONException;
import org.json.JSONObject;

import com.caihong.cms.entity.assist.base.BaseCmsReceiverMessage;
import com.caihong.common.util.DateUtils;
import com.caihong.common.util.StrUtils;

public class CmsReceiverMessage extends BaseCmsReceiverMessage {
	
	public JSONObject convertToJson() 
			throws JSONException{
		JSONObject json=new JSONObject();
		json.put("id", getId());
		json.put("msgTitle", getMsgTitle());
		json.put("msgContent", getMsgContent());
		json.put("sendTime", DateUtils.parseDateToTimeStr(getSendTime()));
		json.put("msgStatus", isMsgStatus());
		json.put("msgBox", getMsgBox());
		if(getMsgReceiverUser()!=null){
			json.put("msgReceiverUser", getMsgReceiverUser().getUsername());
		}
		if(getMsgSendUser()!=null){
			json.put("msgSendUser", getMsgSendUser().getUsername());
		}
		return json;
	}
	
	private static final long serialVersionUID = 1L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsReceiverMessage() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsReceiverMessage(java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsReceiverMessage(java.lang.Integer id,
			com.caihong.core.entity.CmsUser msgReceiverUser,
			com.caihong.core.entity.CmsUser msgSendUser,
			com.caihong.core.entity.CmsSite site, java.lang.String msgTitle,
			java.lang.String msgContent, java.util.Date sendTime,
			boolean msgStatus, java.lang.Integer msgBox) {

		super(id, msgReceiverUser, msgSendUser, site, msgTitle, msgContent,
				sendTime, msgStatus, msgBox);
	}

	public CmsReceiverMessage(CmsMessage message) {
		super(message.getId(), message.getMsgReceiverUser(), message
				.getMsgSendUser(), message.getSite(), message.getMsgTitle(),
				message.getMsgContent(), message.getSendTime(), message
						.getMsgStatus(), message.getMsgBox());
	}
	public String getTitleHtml() {
		return StrUtils.txt2htm(getMsgTitle());
	}
	public String getContentHtml() {
		return StrUtils.txt2htm(getMsgContent());
	}

	/* [CONSTRUCTOR MARKER END] */

}