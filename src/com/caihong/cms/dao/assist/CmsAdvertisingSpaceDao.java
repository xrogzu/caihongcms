package com.caihong.cms.dao.assist;

import java.util.List;

import com.caihong.cms.entity.assist.CmsAdvertisingSpace;
import com.caihong.common.hibernate4.Updater;

public interface CmsAdvertisingSpaceDao {
	public List<CmsAdvertisingSpace> getList(Integer siteId);

	public CmsAdvertisingSpace findById(Integer id);

	public CmsAdvertisingSpace save(CmsAdvertisingSpace bean);

	public CmsAdvertisingSpace updateByUpdater(
			Updater<CmsAdvertisingSpace> updater);

	public CmsAdvertisingSpace deleteById(Integer id);
}