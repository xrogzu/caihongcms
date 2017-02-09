package com.caihong.cms.manager.main;

import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.main.GrainBuyConfig;

public interface GrainBuyConfigMng {
	public Pagination getPage(int pageNo, int pageSize);

	public GrainBuyConfig findById(Integer id);

	public GrainBuyConfig save(GrainBuyConfig bean);

	public GrainBuyConfig update(GrainBuyConfig bean);

	public GrainBuyConfig deleteById(Integer id);
	
	public GrainBuyConfig[] deleteByIds(Integer[] ids);
}