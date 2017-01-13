package com.caihong.cms.manager.assist;

import com.caihong.cms.entity.assist.CmsGuestbook;
import com.caihong.cms.entity.assist.CmsGuestbookExt;

public interface CmsGuestbookExtMng {
	public CmsGuestbookExt save(CmsGuestbookExt ext, CmsGuestbook guestbook);

	public CmsGuestbookExt update(CmsGuestbookExt ext);
}