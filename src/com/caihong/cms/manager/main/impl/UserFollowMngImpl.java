package com.caihong.cms.manager.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.cms.dao.main.UserFollowDao;
import com.caihong.cms.entity.main.UserFollow;
import com.caihong.cms.manager.main.UserFollowMng;

@Service
@Transactional
public class UserFollowMngImpl implements UserFollowMng {
	@Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = dao.getPage(pageNo, pageSize);
		return page;
	}

	@Transactional(readOnly = true)
	public UserFollow findById(Integer id) {
		UserFollow entity = dao.findById(id);
		return entity;
	}
	@Transactional(readOnly = true)
	public UserFollow findByFollowUser(Integer userid,Integer followUserid){
		return dao.findByFollowUser(userid, followUserid);
	}

	public UserFollow save(UserFollow bean) {
		dao.save(bean);
		return bean;
	}

	public UserFollow update(UserFollow bean) {
		Updater<UserFollow> updater = new Updater<UserFollow>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public UserFollow deleteById(Integer id) {
		UserFollow bean = dao.deleteById(id);
		return bean;
	}
	
	public UserFollow delete(UserFollow bean){
		return dao.delete(bean);
	}
	
	public UserFollow[] deleteByIds(Integer[] ids) {
		UserFollow[] beans = new UserFollow[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private UserFollowDao dao;

	@Autowired
	public void setDao(UserFollowDao dao) {
		this.dao = dao;
	}
}