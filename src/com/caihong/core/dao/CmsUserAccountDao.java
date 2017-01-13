package com.caihong.core.dao;

import java.util.Date;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.core.entity.CmsUserAccount;

public interface CmsUserAccountDao {
	
	public Pagination getPage(String username,Date drawTimeBegin,Date drawTimeEnd,
			int orderBy,int pageNo,int pageSize);
	
	public CmsUserAccount findById(Integer id);

	public CmsUserAccount save(CmsUserAccount bean);

	public CmsUserAccount updateByUpdater(Updater<CmsUserAccount> updater);
}