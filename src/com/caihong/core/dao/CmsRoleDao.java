package com.caihong.core.dao;

import java.util.List;

import com.caihong.common.hibernate4.Updater;
import com.caihong.core.entity.CmsRole;

public interface CmsRoleDao {
	public List<CmsRole> getList(Integer level);

	public CmsRole findById(Integer id);

	public CmsRole save(CmsRole bean);

	public CmsRole updateByUpdater(Updater<CmsRole> updater);

	public CmsRole deleteById(Integer id);
}