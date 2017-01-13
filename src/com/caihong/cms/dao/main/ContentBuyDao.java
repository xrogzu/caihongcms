package com.caihong.cms.dao.main;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;

import java.util.List;

import com.caihong.cms.entity.main.ContentBuy;

public interface ContentBuyDao {
	public Pagination getPage(String orderNum,Integer buyUserId,Integer authorUserId,
			Short payMode,int pageNo, int pageSize);
	
	public List<ContentBuy> getList(String orderNum,Integer buyUserId,
			Integer authorUserId,Short payMode,Integer first, Integer count);
	
	public Pagination getPageByContent(Integer contentId,
			Short payMode,int pageNo, int pageSize);
	
	public List<ContentBuy> getListByContent(Integer contentId,
			Short payMode,Integer first, Integer count);

	public ContentBuy findById(Long id);
	
	public ContentBuy findByOrderNumber(String orderNumber);
	
	public ContentBuy findByOutOrderNum(String orderNum,Integer payMethod);
	
	public ContentBuy find(Integer buyUserId,Integer contentId);

	public ContentBuy save(ContentBuy bean);

	public ContentBuy updateByUpdater(Updater<ContentBuy> updater);

	public ContentBuy deleteById(Long id);
}