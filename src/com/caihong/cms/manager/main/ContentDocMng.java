package com.caihong.cms.manager.main;

import com.caihong.cms.entity.main.Content;
import com.caihong.cms.entity.main.ContentDoc;
import com.caihong.core.entity.CmsUser;
public interface ContentDocMng {
	public ContentDoc save(ContentDoc doc, Content content);

	public ContentDoc update(ContentDoc doc, Content content);
	
	public ContentDoc operateDocGrain(CmsUser downUser, ContentDoc doc);
	
	public ContentDoc createSwfFile(ContentDoc doc);
	
	public ContentDoc createPdfFile(ContentDoc doc);
}