package com.caihong.cms.dao.assist;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.assist.CmsScoreGroup;

public interface CmsScoreGroupDao {
	public Pagination getPage(int pageNo, int pageSize);

	public CmsScoreGroup findById(Integer id);
	
	public CmsScoreGroup findDefault(Integer siteId);

	public CmsScoreGroup save(CmsScoreGroup bean);

	public CmsScoreGroup updateByUpdater(Updater<CmsScoreGroup> updater);

	public CmsScoreGroup deleteById(Integer id);
}