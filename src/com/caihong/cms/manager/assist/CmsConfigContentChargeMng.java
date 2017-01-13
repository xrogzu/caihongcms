package com.caihong.cms.manager.assist;

import java.util.Map;

import com.caihong.cms.entity.assist.CmsConfigContentCharge;

public interface CmsConfigContentChargeMng {
	
	public CmsConfigContentCharge findById(Integer id) ;
	
	public CmsConfigContentCharge getDefault();

	public CmsConfigContentCharge update(CmsConfigContentCharge bean
			,String payTransferPassword,Map<String,String> keys,Map<String,String>fixVal);
	
	public CmsConfigContentCharge afterUserPay(Double payAmout);

}