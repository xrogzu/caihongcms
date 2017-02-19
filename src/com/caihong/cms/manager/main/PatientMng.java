package com.caihong.cms.manager.main;

import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.main.Patient;

public interface PatientMng {
	public Pagination getPage(int pageNo, int pageSize);

	public Patient findById(Integer id);
	
	public Patient findByIdNo(String idNo);

	public Patient save(Patient bean);

	public Patient update(Patient bean);

	public Patient deleteById(Integer id);
	
	public Patient[] deleteByIds(Integer[] ids);
}