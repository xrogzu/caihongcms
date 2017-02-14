package com.caihong.cms.manager.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.cms.dao.main.ReserveDao;
import com.caihong.cms.entity.main.Reserve;
import com.caihong.cms.manager.main.ReserveMng;

@Service
@Transactional
public class ReserveMngImpl implements ReserveMng {
	@Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = dao.getPage(pageNo, pageSize);
		return page;
	}

	@Transactional(readOnly = true)
	public Reserve findById(Integer id) {
		Reserve entity = dao.findById(id);
		return entity;
	}

	public Reserve save(Reserve bean) {
		dao.save(bean);
		return bean;
	}

	public Reserve update(Reserve bean) {
		Updater<Reserve> updater = new Updater<Reserve>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public Reserve deleteById(Integer id) {
		Reserve bean = dao.deleteById(id);
		return bean;
	}
	
	public Reserve[] deleteByIds(Integer[] ids) {
		Reserve[] beans = new Reserve[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private ReserveDao dao;

	@Autowired
	public void setDao(ReserveDao dao) {
		this.dao = dao;
	}
}