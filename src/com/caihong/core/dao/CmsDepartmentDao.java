package com.caihong.core.dao;

import java.util.List;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.core.entity.CmsDepartment;

public interface CmsDepartmentDao {

	public List<CmsDepartment> getList(Integer parentId,boolean all);

	public Pagination getPage(String name, int pageNo,int pageSize);

	public CmsDepartment findById(Integer id);

	public CmsDepartment findByName(String name);

	public CmsDepartment save(CmsDepartment bean);

	public CmsDepartment deleteById(Integer id);

	public CmsDepartment updateByUpdater(Updater<CmsDepartment> updater);

}