package com.caihong.core.dao;

import com.caihong.common.hibernate4.Updater;
import com.caihong.core.entity.CmsConfig;

public interface CmsConfigDao {
	public CmsConfig findById(Integer id);

	public CmsConfig updateByUpdater(Updater<CmsConfig> updater);
}