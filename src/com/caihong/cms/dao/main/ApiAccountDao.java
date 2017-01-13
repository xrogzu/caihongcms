package com.caihong.cms.dao.main;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.main.ApiAccount;

public interface ApiAccountDao {
	public Pagination getPage(int pageNo, int pageSize);
	
	public ApiAccount findByAppId(String appId);

	public ApiAccount findById(Integer id);

	public ApiAccount save(ApiAccount bean);

	public ApiAccount updateByUpdater(Updater<ApiAccount> updater);

	public ApiAccount deleteById(Integer id);
}