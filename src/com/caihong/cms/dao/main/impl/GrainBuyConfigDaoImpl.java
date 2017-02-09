package com.caihong.cms.dao.main.impl;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.caihong.common.hibernate4.HibernateBaseDao;
import com.caihong.common.page.Pagination;
import com.caihong.cms.dao.main.GrainBuyConfigDao;
import com.caihong.cms.entity.main.GrainBuyConfig;

@Repository
public class GrainBuyConfigDaoImpl extends HibernateBaseDao<GrainBuyConfig, Integer> implements GrainBuyConfigDao {
	public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	public GrainBuyConfig findById(Integer id) {
		GrainBuyConfig entity = get(id);
		return entity;
	}

	public GrainBuyConfig save(GrainBuyConfig bean) {
		getSession().save(bean);
		return bean;
	}

	public GrainBuyConfig deleteById(Integer id) {
		GrainBuyConfig entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<GrainBuyConfig> getEntityClass() {
		return GrainBuyConfig.class;
	}
}