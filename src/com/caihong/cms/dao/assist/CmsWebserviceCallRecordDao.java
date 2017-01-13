package com.caihong.cms.dao.assist;

import com.caihong.cms.entity.assist.CmsWebserviceCallRecord;
import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;

public interface CmsWebserviceCallRecordDao {
	public Pagination getPage(int pageNo, int pageSize);

	public CmsWebserviceCallRecord findById(Integer id);

	public CmsWebserviceCallRecord save(CmsWebserviceCallRecord bean);

	public CmsWebserviceCallRecord updateByUpdater(Updater<CmsWebserviceCallRecord> updater);

	public CmsWebserviceCallRecord deleteById(Integer id);
}