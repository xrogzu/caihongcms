package com.caihong.cms.manager.main;

import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.main.Schedule;

public interface ScheduleMng {
	public Pagination getPage(int pageNo, int pageSize);

	public Schedule findById(Integer id);

	public Schedule save(Schedule bean);

	public Schedule update(Schedule bean);

	public Schedule deleteById(Integer id);
	
	public Schedule[] deleteByIds(Integer[] ids);
}