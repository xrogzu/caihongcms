package com.caihong.cms.manager.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.cms.dao.main.GrainDetailDao;
import com.caihong.cms.entity.main.GrainDetail;
import com.caihong.cms.manager.main.GrainDetailMng;

@Service
@Transactional
public class GrainDetailMngImpl implements GrainDetailMng {
	@Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = dao.getPage(pageNo, pageSize);
		return page;
	}

	@Transactional(readOnly = true)
	public GrainDetail findById(Integer id) {
		GrainDetail entity = dao.findById(id);
		return entity;
	}

	public GrainDetail save(GrainDetail bean) {
		dao.save(bean);
		return bean;
	}

	public GrainDetail update(GrainDetail bean) {
		Updater<GrainDetail> updater = new Updater<GrainDetail>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public GrainDetail deleteById(Integer id) {
		GrainDetail bean = dao.deleteById(id);
		return bean;
	}
	
	public GrainDetail[] deleteByIds(Integer[] ids) {
		GrainDetail[] beans = new GrainDetail[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private GrainDetailDao dao;

	@Autowired
	public void setDao(GrainDetailDao dao) {
		this.dao = dao;
	}
}