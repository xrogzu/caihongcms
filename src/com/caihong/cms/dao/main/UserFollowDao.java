package com.caihong.cms.dao.main;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.main.UserFollow;

public interface UserFollowDao {
	public Pagination getPage(int pageNo, int pageSize);

	public UserFollow findById(Integer id);
	public UserFollow findByFollowUser(Integer userid,Integer followUserid);
	public UserFollow save(UserFollow bean);

	public UserFollow updateByUpdater(Updater<UserFollow> updater);

	public UserFollow deleteById(Integer id);
	
	public UserFollow delete(UserFollow bean);
}