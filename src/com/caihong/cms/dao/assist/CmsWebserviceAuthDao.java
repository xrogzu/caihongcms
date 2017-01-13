package com.caihong.cms.dao.assist;

import com.caihong.cms.entity.assist.CmsWebserviceAuth;
import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;

public interface CmsWebserviceAuthDao {
	public Pagination getPage(int pageNo, int pageSize);
	
	public CmsWebserviceAuth findByUsername(String username);

	public CmsWebserviceAuth findById(Integer id);

	public CmsWebserviceAuth save(CmsWebserviceAuth bean);

	public CmsWebserviceAuth updateByUpdater(Updater<CmsWebserviceAuth> updater);

	public CmsWebserviceAuth deleteById(Integer id);
}