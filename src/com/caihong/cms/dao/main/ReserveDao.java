package com.caihong.cms.dao.main;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.main.Reserve;

public interface ReserveDao {
	public Pagination getPage(int pageNo, int pageSize);

	public Reserve findById(Integer id);

	public Reserve save(Reserve bean);

	public Reserve updateByUpdater(Updater<Reserve> updater);

	public Reserve deleteById(Integer id);
}