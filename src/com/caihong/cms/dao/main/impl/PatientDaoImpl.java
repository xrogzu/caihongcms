package com.caihong.cms.dao.main.impl;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.caihong.common.hibernate4.HibernateBaseDao;
import com.caihong.common.page.Pagination;
import com.caihong.cms.dao.main.PatientDao;
import com.caihong.cms.entity.main.Patient;

@Repository
public class PatientDaoImpl extends HibernateBaseDao<Patient, Integer> implements PatientDao {
	public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	public Patient findById(Integer id) {
		Patient entity = get(id);
		return entity;
	}

	public Patient save(Patient bean) {
		getSession().save(bean);
		return bean;
	}

	public Patient deleteById(Integer id) {
		Patient entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<Patient> getEntityClass() {
		return Patient.class;
	}
}