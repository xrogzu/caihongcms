package com.caihong.cms.dao.assist;

import java.util.List;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.assist.CmsDirectiveTpl;

public interface CmsDirectiveTplDao {
	public Pagination getPage(int pageNo, int pageSize);
	
	public List<CmsDirectiveTpl> getList(int count);

	public CmsDirectiveTpl findById(Integer id);

	public CmsDirectiveTpl save(CmsDirectiveTpl bean);

	public CmsDirectiveTpl updateByUpdater(Updater<CmsDirectiveTpl> updater);

	public CmsDirectiveTpl deleteById(Integer id);
}