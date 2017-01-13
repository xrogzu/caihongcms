package com.caihong.cms.manager.main;

import com.caihong.cms.entity.main.Content;
import com.caihong.cms.entity.main.ContentTxt;

public interface ContentTxtMng {
	public ContentTxt save(ContentTxt txt, Content content);

	public ContentTxt update(ContentTxt txt, Content content);
}