package com.caihong.core.manager;

import com.caihong.core.entity.CmsUser;
import com.caihong.core.entity.CmsUserResume;

public interface CmsUserResumeMng {
	public CmsUserResume save(CmsUserResume ext, CmsUser user);

	public CmsUserResume update(CmsUserResume ext, CmsUser user);
}