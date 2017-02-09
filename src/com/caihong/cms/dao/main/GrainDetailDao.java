package com.caihong.cms.dao.main;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.main.GrainDetail;

public interface GrainDetailDao {
	public Pagination getPage(int pageNo, int pageSize);

	public GrainDetail findById(Integer id);

	public GrainDetail save(GrainDetail bean);

	public GrainDetail updateByUpdater(Updater<GrainDetail> updater);

	public GrainDetail deleteById(Integer id);
}