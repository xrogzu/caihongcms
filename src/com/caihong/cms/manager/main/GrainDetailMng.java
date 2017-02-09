package com.caihong.cms.manager.main;

import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.main.GrainDetail;

public interface GrainDetailMng {
	public Pagination getPage(int pageNo, int pageSize);

	public GrainDetail findById(Integer id);

	public GrainDetail save(GrainDetail bean);

	public GrainDetail update(GrainDetail bean);

	public GrainDetail deleteById(Integer id);
	
	public GrainDetail[] deleteByIds(Integer[] ids);
}