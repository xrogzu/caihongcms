package com.caihong.cms.manager.main;

import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.main.SmsSendRecord;

public interface SmsSendRecordMng {
	public Pagination getPage(int pageNo, int pageSize,String...args);

	public SmsSendRecord findById(Integer id);

	public SmsSendRecord save(SmsSendRecord bean);

	public SmsSendRecord update(SmsSendRecord bean);

	public SmsSendRecord deleteById(Integer id);
	
	public SmsSendRecord[] deleteByIds(Integer[] ids);
}