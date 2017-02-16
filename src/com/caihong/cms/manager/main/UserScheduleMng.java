package com.caihong.cms.manager.main;

import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.main.UserSchedule;

public interface UserScheduleMng {
	public Pagination getPage(String username ,int pageNo, int pageSize);

	public UserSchedule findById(Integer id);

	public UserSchedule save(UserSchedule bean);

	public UserSchedule update(UserSchedule bean);

	public UserSchedule deleteById(Integer id);
	
	public UserSchedule[] deleteByIds(Integer[] ids);
}