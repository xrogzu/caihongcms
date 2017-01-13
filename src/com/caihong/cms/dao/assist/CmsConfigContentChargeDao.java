package com.caihong.cms.dao.assist;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.assist.CmsConfigContentCharge;

public interface CmsConfigContentChargeDao {
	public Pagination getPage(int pageNo, int pageSize);

	public CmsConfigContentCharge findById(Integer id);

	public CmsConfigContentCharge save(CmsConfigContentCharge bean);

	public CmsConfigContentCharge updateByUpdater(Updater<CmsConfigContentCharge> updater);

	public CmsConfigContentCharge deleteById(Integer id);
}