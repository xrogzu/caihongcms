package com.caihong.core.dao;

import java.util.List;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.core.entity.CmsWorkflowRecord;

public interface CmsWorkflowRecordDao {
	public List<CmsWorkflowRecord> getList(Integer eventId,Integer userId);
	
	public Pagination getPage(int pageNo, int pageSize);

	public CmsWorkflowRecord findById(Integer id);

	public CmsWorkflowRecord save(CmsWorkflowRecord bean);

	public CmsWorkflowRecord updateByUpdater(Updater<CmsWorkflowRecord> updater);

	public CmsWorkflowRecord deleteById(Integer id);
}