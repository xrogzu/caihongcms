package com.caihong.cms.dao.main;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.main.SmsSendRecord;

public interface SmsSendRecordDao {
	public Pagination getPage(int pageNo, int pageSize,String... args);

	public SmsSendRecord findById(Integer id);

	public SmsSendRecord save(SmsSendRecord bean);

	public SmsSendRecord updateByUpdater(Updater<SmsSendRecord> updater);

	public SmsSendRecord deleteById(Integer id);
}