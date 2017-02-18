package com.caihong.cms.manager.main.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.cms.dao.main.UserScheduleDao;
import com.caihong.cms.entity.main.UserSchedule;
import com.caihong.cms.manager.main.UserScheduleMng;

@Service
@Transactional
public class UserScheduleMngImpl implements UserScheduleMng {
	@Transactional(readOnly = true)
	public Pagination getPage(String username ,int pageNo, int pageSize) {
		Pagination page = dao.getPage(username,pageNo, pageSize);
		return page;
	}
	
	@Transactional(readOnly = true)
	public Pagination getPage(Integer userid ,int pageNo, int pageSize) {
		Pagination page = dao.getPage(userid,pageNo, pageSize);
		return page;
	}
	@Transactional(readOnly = true)
	public Pagination getPage(Integer userid,Date startDate,Date endDate,Integer nationId,Integer majorId,Integer jobTitleId,Integer jobLevelId,int pageNo, int pageSize){
		Pagination page = dao.getPage(userid, startDate, endDate, nationId, majorId, jobTitleId, jobLevelId, pageNo, pageSize);
		return page;
	}
	@Transactional(readOnly = true)
	public UserSchedule findById(Integer id) {
		UserSchedule entity = dao.findById(id);
		return entity;
	}

	public UserSchedule save(UserSchedule bean) {
		dao.save(bean);
		return bean;
	}

	public UserSchedule update(UserSchedule bean) {
		Updater<UserSchedule> updater = new Updater<UserSchedule>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public UserSchedule deleteById(Integer id) {
		UserSchedule bean = dao.deleteById(id);
		return bean;
	}
	
	public UserSchedule[] deleteByIds(Integer[] ids) {
		UserSchedule[] beans = new UserSchedule[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private UserScheduleDao dao;

	@Autowired
	public void setDao(UserScheduleDao dao) {
		this.dao = dao;
	}
}