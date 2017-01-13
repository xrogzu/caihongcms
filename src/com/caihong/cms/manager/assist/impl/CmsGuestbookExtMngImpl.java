package com.caihong.cms.manager.assist.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caihong.cms.dao.assist.CmsGuestbookExtDao;
import com.caihong.cms.entity.assist.CmsGuestbook;
import com.caihong.cms.entity.assist.CmsGuestbookExt;
import com.caihong.cms.manager.assist.CmsGuestbookExtMng;
import com.caihong.common.hibernate4.Updater;

@Service
@Transactional
public class CmsGuestbookExtMngImpl implements CmsGuestbookExtMng {
	public CmsGuestbookExt save(CmsGuestbookExt ext, CmsGuestbook guestbook) {
		guestbook.setExt(ext);
		ext.setGuestbook(guestbook);
		ext.init();
		dao.save(ext);
		return ext;
	}

	public CmsGuestbookExt update(CmsGuestbookExt ext) {
		Updater<CmsGuestbookExt> updater = new Updater<CmsGuestbookExt>(ext);
		CmsGuestbookExt entity = dao.updateByUpdater(updater);
		entity.blankToNull();
		return entity;
	}

	private CmsGuestbookExtDao dao;

	@Autowired
	public void setDao(CmsGuestbookExtDao dao) {
		this.dao = dao;
	}
}