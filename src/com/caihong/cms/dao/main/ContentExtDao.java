package com.caihong.cms.dao.main;

import com.caihong.cms.entity.main.ContentExt;
import com.caihong.common.hibernate4.Updater;

public interface ContentExtDao {
	public ContentExt findById(Integer id);

	public ContentExt save(ContentExt bean);

	public ContentExt updateByUpdater(Updater<ContentExt> updater);
}