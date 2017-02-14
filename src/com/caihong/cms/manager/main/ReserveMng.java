package com.caihong.cms.manager.main;

import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.main.Reserve;

public interface ReserveMng {
	public Pagination getPage(int pageNo, int pageSize);

	public Reserve findById(Integer id);

	public Reserve save(Reserve bean);

	public Reserve update(Reserve bean);

	public Reserve deleteById(Integer id);
	
	public Reserve[] deleteByIds(Integer[] ids);
}