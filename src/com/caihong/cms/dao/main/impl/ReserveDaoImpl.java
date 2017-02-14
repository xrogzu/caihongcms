package com.caihong.cms.dao.main.impl;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.caihong.common.hibernate4.HibernateBaseDao;
import com.caihong.common.page.Pagination;
import com.caihong.cms.dao.main.ReserveDao;
import com.caihong.cms.entity.main.Reserve;

@Repository
public class ReserveDaoImpl extends HibernateBaseDao<Reserve, Integer> implements ReserveDao {
	public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	public Reserve findById(Integer id) {
		Reserve entity = get(id);
		return entity;
	}

	public Reserve save(Reserve bean) {
		getSession().save(bean);
		return bean;
	}

	public Reserve deleteById(Integer id) {
		Reserve entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<Reserve> getEntityClass() {
		return Reserve.class;
	}
}