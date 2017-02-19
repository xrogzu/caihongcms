package com.caihong.cms.dao.main;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.main.Patient;

public interface PatientDao {
	public Pagination getPage(int pageNo, int pageSize);

	public Patient findById(Integer id);
	
	public Patient findByIdNo(String idNo);

	public Patient save(Patient bean);

	public Patient updateByUpdater(Updater<Patient> updater);

	public Patient deleteById(Integer id);
}