package com.caihong.core.dao;

import java.util.List;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.core.entity.CmsWorkflow;

public interface CmsWorkflowDao {
	public Pagination getPage(Integer siteId,int pageNo, int pageSize);
	
	public List<CmsWorkflow> getList(Integer siteId,Boolean disabled);

	public CmsWorkflow findById(Integer id);

	public CmsWorkflow save(CmsWorkflow bean);

	public CmsWorkflow updateByUpdater(Updater<CmsWorkflow> updater);

	public CmsWorkflow deleteById(Integer id);
}