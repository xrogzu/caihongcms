package com.caihong.cms.dao.main;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.main.Schedule;

public interface ScheduleDao {
	public Pagination getPage(int pageNo, int pageSize);

	public Schedule findById(Integer id);

	public Schedule save(Schedule bean);

	public Schedule updateByUpdater(Updater<Schedule> updater);

	public Schedule deleteById(Integer id);
}