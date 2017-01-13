package com.caihong.cms.dao.main;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;

import java.util.List;

import com.caihong.cms.entity.main.ContentRecord;

public interface ContentRecordDao {
	public Pagination getPage(int pageNo, int pageSize);

	public ContentRecord findById(Long id);

	public ContentRecord save(ContentRecord bean);

	public ContentRecord updateByUpdater(Updater<ContentRecord> updater);

	public ContentRecord deleteById(Long id);

	public List<ContentRecord> getListByContentId(Integer contentId);
}