package com.caihong.plug.store.dao;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.plug.store.entity.PlugStoreConfig;

public interface PlugStoreConfigDao {
	public Pagination getPage(int pageNo, int pageSize);

	public PlugStoreConfig findById(Integer id);

	public PlugStoreConfig save(PlugStoreConfig bean);

	public PlugStoreConfig updateByUpdater(Updater<PlugStoreConfig> updater);

	public PlugStoreConfig deleteById(Integer id);
}