package com.caihong.cms.dao.main;

import java.util.List;

import com.caihong.cms.entity.main.ContentShareCheck;
import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;

public interface ContentShareCheckDao {
	public ContentShareCheck findById(Integer id);

	public ContentShareCheck save(ContentShareCheck bean);

	public ContentShareCheck updateByUpdater(Updater<ContentShareCheck> updater);
	
	public ContentShareCheck update(ContentShareCheck bean);
	
	public ContentShareCheck deleteById(Integer id);
	
	public ContentShareCheck[] deleteByIds(Integer[] ids);

	public List<ContentShareCheck> getList(Integer contentId, Integer channelId);
	
	public Pagination getPageForShared(String title, Byte status, Integer siteId,Integer targetSiteId, Integer requestSiteId, int pageNo, int pageSize);
	
	
}