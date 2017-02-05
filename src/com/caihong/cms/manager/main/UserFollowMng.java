package com.caihong.cms.manager.main;

import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.main.UserFollow;

public interface UserFollowMng {
	public Pagination getPage(int pageNo, int pageSize);

	public UserFollow findById(Integer id);
	
	public UserFollow findByFollowUser(Integer userid,Integer followUserid);

	public UserFollow save(UserFollow bean);

	public UserFollow update(UserFollow bean);

	public UserFollow deleteById(Integer id);
	
	public UserFollow delete(UserFollow bean);
	
	public UserFollow[] deleteByIds(Integer[] ids);
}