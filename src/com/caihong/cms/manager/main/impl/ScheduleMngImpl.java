package com.caihong.cms.manager.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.cms.dao.main.ScheduleDao;
import com.caihong.cms.entity.main.Schedule;
import com.caihong.cms.manager.main.ScheduleMng;

@Service
@Transactional
public class ScheduleMngImpl implements ScheduleMng {
	@Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = dao.getPage(pageNo, pageSize);
		return page;
	}

	@Transactional(readOnly = true)
	public Schedule findById(Integer id) {
		Schedule entity = dao.findById(id);
		return entity;
	}

	public Schedule save(Schedule bean) {
		dao.save(bean);
		return bean;
	}

	public Schedule update(Schedule bean) {
		Updater<Schedule> updater = new Updater<Schedule>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public Schedule deleteById(Integer id) {
		Schedule bean = dao.deleteById(id);
		return bean;
	}
	
	public Schedule[] deleteByIds(Integer[] ids) {
		Schedule[] beans = new Schedule[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private ScheduleDao dao;

	@Autowired
	public void setDao(ScheduleDao dao) {
		this.dao = dao;
	}
}