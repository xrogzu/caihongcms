package com.caihong.core.manager;

import com.caihong.core.entity.CmsUser;
import com.caihong.core.entity.CmsUserExt;

public interface CmsUserExtMng {
	public CmsUserExt findById(Integer userId);
	
	public CmsUserExt save(CmsUserExt ext, CmsUser user);

	public CmsUserExt update(CmsUserExt ext, CmsUser user);
}