package com.caihong.cms.dao.main;

import com.caihong.cms.entity.main.ContentTxt;
import com.caihong.common.hibernate4.Updater;

public interface ContentTxtDao {
	public ContentTxt findById(Integer id);

	public ContentTxt save(ContentTxt bean);

	public ContentTxt updateByUpdater(Updater<ContentTxt> updater);
}