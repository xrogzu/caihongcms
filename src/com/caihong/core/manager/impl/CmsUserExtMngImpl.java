package com.caihong.core.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caihong.common.hibernate4.Updater;
import com.caihong.core.dao.CmsUserExtDao;
import com.caihong.core.entity.CmsUser;
import com.caihong.core.entity.CmsUserExt;
import com.caihong.core.manager.CmsUserExtMng;

@Service
@Transactional
public class CmsUserExtMngImpl implements CmsUserExtMng {
	@Transactional(readOnly = true)
	public CmsUserExt findById(Integer userId){
		return dao.findById(userId);
	}
	
	public CmsUserExt save(CmsUserExt ext, CmsUser user) {
		ext.blankToNull();
		ext.setUser(user);
		dao.save(ext);
		return ext;
	}

	public CmsUserExt update(CmsUserExt ext, CmsUser user) {
		CmsUserExt entity = dao.findById(user.getId());
		if (entity == null) {
			entity = save(ext, user);
			user.getUserExtSet().add(entity);
			return entity;
		} else {
			Updater<CmsUserExt> updater = new Updater<CmsUserExt>(ext);
		//	updater.include("gender");
		//	updater.include("birthday");
			ext = dao.updateByUpdater(updater);
			ext.blankToNull();
			return ext;
		}
	}

	private CmsUserExtDao dao;

	@Autowired
	public void setDao(CmsUserExtDao dao) {
		this.dao = dao;
	}
}