package com.caihong.cms.dao.main;

import com.caihong.cms.entity.main.ContentDoc;
import com.caihong.common.hibernate4.Updater;

public interface ContentDocDao {
	public ContentDoc findById(Integer id);

	public ContentDoc save(ContentDoc bean);

	public ContentDoc updateByUpdater(Updater<ContentDoc> updater);
}