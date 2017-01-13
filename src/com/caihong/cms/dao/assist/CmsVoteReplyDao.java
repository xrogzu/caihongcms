package com.caihong.cms.dao.assist;

import com.caihong.cms.entity.assist.CmsVoteReply;
import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;

public interface CmsVoteReplyDao {

	public Pagination getPage(Integer  subTopicId, int pageNo, int pageSize);
	
	public CmsVoteReply findById(Integer id);

	public CmsVoteReply save(CmsVoteReply bean);

	public CmsVoteReply updateByUpdater(Updater<CmsVoteReply> updater);

	public CmsVoteReply deleteById(Integer id);
}