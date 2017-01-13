package com.caihong.cms.dao.assist;

import com.caihong.cms.entity.assist.CmsGuestbookExt;
import com.caihong.common.hibernate4.Updater;

public interface CmsGuestbookExtDao {
	public CmsGuestbookExt findById(Integer id);

	public CmsGuestbookExt save(CmsGuestbookExt bean);

	public CmsGuestbookExt updateByUpdater(Updater<CmsGuestbookExt> updater);

	public CmsGuestbookExt deleteById(Integer id);
}