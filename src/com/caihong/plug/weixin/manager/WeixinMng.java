package com.caihong.plug.weixin.manager;

import com.caihong.common.page.Pagination;
import com.caihong.plug.weixin.entity.Weixin;

public interface WeixinMng {

	public Pagination getPage(Integer siteId,int pageNo,int pageSize);
	
	public Weixin findById(Integer id);
	
	public Weixin find(Integer siteId);
	
	public Weixin save(Weixin bean);
	
	public Weixin update(Weixin bean);
	
	public Weixin deleteById(Integer id);
	
	public Weixin[] delete(Integer[] id);
}
