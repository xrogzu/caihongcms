package com.caihong.cms.dao.main;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;

import java.util.Date;

import com.caihong.cms.entity.main.UserSchedule;

public interface UserScheduleDao {
	public Pagination getPage(String username,int pageNo, int pageSize);	
	
	public Pagination getPage(Integer userid,int pageNo, int pageSize);	
	
	public Pagination getPage(Integer userid,Date startDate,Date endDate,Integer nationId,Integer majorId,Integer jobTitleId,Integer jobLevelId,int pageNo, int pageSize);
	
	public UserSchedule findById(Integer id);

	public UserSchedule save(UserSchedule bean);

	public UserSchedule updateByUpdater(Updater<UserSchedule> updater);

	public UserSchedule deleteById(Integer id);
}