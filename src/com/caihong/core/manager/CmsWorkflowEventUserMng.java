package com.caihong.core.manager;

import java.util.Set;

import com.caihong.core.entity.CmsUser;
import com.caihong.core.entity.CmsWorkflowEvent;
import com.caihong.core.entity.CmsWorkflowEventUser;

public interface CmsWorkflowEventUserMng {
	
	public Set<CmsWorkflowEventUser> save(CmsWorkflowEvent event,Set<CmsUser>users);

	public Set<CmsWorkflowEventUser> update(CmsWorkflowEvent event,Set<CmsUser>users);
	
	public void deleteByEvent(Integer eventId);

}