package com.caihong.cms.manager.main;

import com.caihong.common.page.Pagination;

import java.util.Date;

import com.caihong.cms.entity.main.Reserve;

public interface ReserveMng {
	public Pagination getPage(int pageNo, int pageSize);
	
	public Pagination search(Integer userid,Integer doctorid,Date startDate,Date endDate,Boolean paystatus,Integer status,int pageNo, int pageSize,String patientName,String doctorname);

	public Reserve findById(Integer id);

	public Reserve save(Reserve bean);

	public Reserve update(Reserve bean);

	public Reserve deleteById(Integer id);
	
	public Reserve[] deleteByIds(Integer[] ids);
}