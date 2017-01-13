package com.caihong.cms.manager.main;

import java.util.Date;
import java.util.List;

import com.caihong.cms.entity.main.Content;
import com.caihong.cms.entity.main.ContentCharge;
import com.caihong.common.page.Pagination;

public interface ContentChargeMng {
	
	public List<ContentCharge> getList(String contentTitle,Integer authorUserId,
			Date buyTimeBegin,Date buyTimeEnd,int orderBy,Integer first,Integer count);
	
	public Pagination getPage(String contentTitle,Integer authorUserId,
			Date buyTimeBegin,Date buyTimeEnd,
			int orderBy,int pageNo,int pageSize);
	
	public ContentCharge save(Double chargeAmount, Short charge,
			Boolean rewardPattern,Double rewardRandomMin,Double rewardRandomMax,
			Content content);
	
	public void afterContentUpdate(Content bean,Short charge,Double chargeAmount,
			Boolean rewardPattern,Double rewardRandomMin,Double rewardRandomMax);

	public ContentCharge update(ContentCharge charge);
	
	public ContentCharge afterUserPay(Double payAmout, Content content);
}