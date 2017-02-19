package com.caihong.cms.manager.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.cms.dao.main.PatientDao;
import com.caihong.cms.entity.main.Patient;
import com.caihong.cms.manager.main.PatientMng;

@Service
@Transactional
public class PatientMngImpl implements PatientMng {
	@Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = dao.getPage(pageNo, pageSize);
		return page;
	}

	@Transactional(readOnly = true)
	public Patient findById(Integer id) {
		Patient entity = dao.findById(id);
		return entity;
	}
	
	@Transactional(readOnly = true)
	public Patient findByIdNo(String idNo) {
		Patient entity = dao.findByIdNo(idNo);
		return entity;
	}

	public Patient save(Patient bean) {
		dao.save(bean);
		return bean;
	}

	public Patient update(Patient bean) {
		Updater<Patient> updater = new Updater<Patient>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public Patient deleteById(Integer id) {
		Patient bean = dao.deleteById(id);
		return bean;
	}
	
	public Patient[] deleteByIds(Integer[] ids) {
		Patient[] beans = new Patient[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private PatientDao dao;

	@Autowired
	public void setDao(PatientDao dao) {
		this.dao = dao;
	}
}