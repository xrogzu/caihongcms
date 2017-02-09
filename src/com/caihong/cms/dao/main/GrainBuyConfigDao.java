package com.caihong.cms.dao.main;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.main.GrainBuyConfig;

public interface GrainBuyConfigDao {
	public Pagination getPage(int pageNo, int pageSize);

	public GrainBuyConfig findById(Integer id);

	public GrainBuyConfig save(GrainBuyConfig bean);

	public GrainBuyConfig updateByUpdater(Updater<GrainBuyConfig> updater);

	public GrainBuyConfig deleteById(Integer id);
}