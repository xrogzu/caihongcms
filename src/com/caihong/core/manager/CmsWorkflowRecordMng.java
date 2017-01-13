package com.caihong.core.manager;

import java.util.Date;
import java.util.List;

import com.caihong.core.entity.CmsSite;
import com.caihong.core.entity.CmsUser;
import com.caihong.core.entity.CmsWorkflowEvent;
import com.caihong.core.entity.CmsWorkflowRecord;

public interface CmsWorkflowRecordMng {
	
	public List<CmsWorkflowRecord> getList(Integer eventId,Integer userId);

	public CmsWorkflowRecord save(CmsSite site, CmsWorkflowEvent event,
			CmsUser user, String opinion,Date recordTime, Integer type);

}