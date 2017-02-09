package com.caihong.cms.dao.main.impl;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.caihong.common.hibernate4.HibernateBaseDao;
import com.caihong.common.page.Pagination;
import com.caihong.cms.dao.main.GrainDetailDao;
import com.caihong.cms.entity.main.GrainDetail;

@Repository
public class GrainDetailDaoImpl extends HibernateBaseDao<GrainDetail, Integer> implements GrainDetailDao {
	public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	public GrainDetail findById(Integer id) {
		GrainDetail entity = get(id);
		return entity;
	}

	public GrainDetail save(GrainDetail bean) {
		getSession().save(bean);
		return bean;
	}

	public GrainDetail deleteById(Integer id) {
		GrainDetail entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<GrainDetail> getEntityClass() {
		return GrainDetail.class;
	}
}