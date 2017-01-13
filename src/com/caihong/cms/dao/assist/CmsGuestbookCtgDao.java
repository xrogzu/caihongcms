package com.caihong.cms.dao.assist;

import java.util.List;

import com.caihong.cms.entity.assist.CmsGuestbookCtg;
import com.caihong.common.hibernate4.Updater;

public interface CmsGuestbookCtgDao {
	public List<CmsGuestbookCtg> getList(Integer siteId);

	public CmsGuestbookCtg findById(Integer id);

	public CmsGuestbookCtg save(CmsGuestbookCtg bean);

	public CmsGuestbookCtg updateByUpdater(Updater<CmsGuestbookCtg> updater);

	public CmsGuestbookCtg deleteById(Integer id);
}