package com.caihong.plug.weixin.dao;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.plug.weixin.entity.Weixin;

public interface WeixinDao {
	
	public Pagination getPage(Integer siteId,int pageNo,int pageSize);
	
	public Weixin save(Weixin bean);
	
	public Weixin deleteById(Integer id);
	
	public Weixin findById(Integer id);
	
	public Weixin find(Integer siteId);

	public Weixin updateByUpdater(Updater<Weixin> updater);
}
