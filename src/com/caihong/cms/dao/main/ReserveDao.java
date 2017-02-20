package com.caihong.cms.dao.main;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;

import java.util.Date;

import com.caihong.cms.entity.main.Reserve;

public interface ReserveDao {
	public Pagination getPage(int pageNo, int pageSize);
	public Pagination search(Integer userid,Integer doctorid,Date startDate,Date endDate,Boolean paystatus,Integer status,int pageNo, int pageSize,String patientName,String doctorName);
	public Reserve findById(Integer id);

	public Reserve save(Reserve bean);

	public Reserve updateByUpdater(Updater<Reserve> updater);

	public Reserve deleteById(Integer id);
}