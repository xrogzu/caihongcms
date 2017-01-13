package com.caihong.cms.dao.main;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.main.ApiUserLogin;

public interface ApiUserLoginDao {
	public Pagination getPage(int pageNo, int pageSize);

	public ApiUserLogin findById(Long id);
	
	public ApiUserLogin findUserLogin(String username,String sessionKey);

	public ApiUserLogin save(ApiUserLogin bean);

	public ApiUserLogin updateByUpdater(Updater<ApiUserLogin> updater);

	public ApiUserLogin deleteById(Long id);
}