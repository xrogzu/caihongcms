package com.caihong.cms.dao.main;

import com.caihong.cms.entity.main.ContentCheck;
import com.caihong.common.hibernate4.Updater;

public interface ContentCheckDao {
	public ContentCheck findById(Long id);

	public ContentCheck save(ContentCheck bean);

	public ContentCheck updateByUpdater(Updater<ContentCheck> updater);
}