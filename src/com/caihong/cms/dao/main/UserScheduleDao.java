package com.caihong.cms.dao.main;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.main.UserSchedule;

public interface UserScheduleDao {
	public Pagination getPage(String username,int pageNo, int pageSize);

	public UserSchedule findById(Integer id);

	public UserSchedule save(UserSchedule bean);

	public UserSchedule updateByUpdater(Updater<UserSchedule> updater);

	public UserSchedule deleteById(Integer id);
}