package com.caihong.cms.dao.main.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.caihong.common.hibernate4.Finder;
import com.caihong.common.hibernate4.HibernateBaseDao;
import com.caihong.common.page.Pagination;
import com.caihong.cms.dao.main.PatientDao;
import com.caihong.cms.entity.main.Order;
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
	public Patient findByIdNo(String idNo){
		String hql="from Patient bean where bean.idNo=:idNo";
		Finder finder=Finder.create(hql).setParam("idNo", idNo);
		List<Patient>list=find(finder);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
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