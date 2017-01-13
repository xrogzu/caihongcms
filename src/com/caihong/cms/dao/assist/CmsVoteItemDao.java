package com.caihong.cms.dao.assist;

import com.caihong.cms.entity.assist.CmsVoteItem;
import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;

public interface CmsVoteItemDao {
	public Pagination getPage(int pageNo, int pageSize);

	public CmsVoteItem findById(Integer id);

	public CmsVoteItem save(CmsVoteItem bean);

	public CmsVoteItem updateByUpdater(Updater<CmsVoteItem> updater);

	public CmsVoteItem deleteById(Integer id);
}