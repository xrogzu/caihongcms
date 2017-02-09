package com.caihong.cms.manager.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.cms.dao.main.GrainBuyConfigDao;
import com.caihong.cms.entity.main.GrainBuyConfig;
import com.caihong.cms.manager.main.GrainBuyConfigMng;

@Service
@Transactional
public class GrainBuyConfigMngImpl implements GrainBuyConfigMng {
	@Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = dao.getPage(pageNo, pageSize);
		return page;
	}

	@Transactional(readOnly = true)
	public GrainBuyConfig findById(Integer id) {
		GrainBuyConfig entity = dao.findById(id);
		return entity;
	}

	public GrainBuyConfig save(GrainBuyConfig bean) {
		dao.save(bean);
		return bean;
	}

	public GrainBuyConfig update(GrainBuyConfig bean) {
		Updater<GrainBuyConfig> updater = new Updater<GrainBuyConfig>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public GrainBuyConfig deleteById(Integer id) {
		GrainBuyConfig bean = dao.deleteById(id);
		return bean;
	}
	
	public GrainBuyConfig[] deleteByIds(Integer[] ids) {
		GrainBuyConfig[] beans = new GrainBuyConfig[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private GrainBuyConfigDao dao;

	@Autowired
	public void setDao(GrainBuyConfigDao dao) {
		this.dao = dao;
	}
}