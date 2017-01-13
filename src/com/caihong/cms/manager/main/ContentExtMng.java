package com.caihong.cms.manager.main;

import com.caihong.cms.entity.main.Content;
import com.caihong.cms.entity.main.ContentExt;

public interface ContentExtMng {
	public ContentExt save(ContentExt ext, Content content);

	public ContentExt update(ContentExt ext);
}