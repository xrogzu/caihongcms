package com.caihong.cms.dao.main.impl;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.caihong.common.hibernate4.HibernateBaseDao;
import com.caihong.common.page.Pagination;
import com.caihong.cms.dao.main.ScheduleDao;
import com.caihong.cms.entity.main.Schedule;

@Repository
public class ScheduleDaoImpl extends HibernateBaseDao<Schedule, Integer> implements ScheduleDao {
	public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	public Schedule findById(Integer id) {
		Schedule entity = get(id);
		return entity;
	}

	public Schedule save(Schedule bean) {
		getSession().save(bean);
		return bean;
	}

	public Schedule deleteById(Integer id) {
		Schedule entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<Schedule> getEntityClass() {
		return Schedule.class;
	}
}