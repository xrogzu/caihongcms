package com.caihong.cms.dao.main;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.main.Order;

public interface OrderDao {
	public Pagination getPage(int pageNo, int pageSize);

	public Order findById(Integer id);

	public Order save(Order bean);

	public Order updateByUpdater(Updater<Order> updater);

	public Order deleteById(Integer id);
	public Order findByOrderNumber(String orderNumber);
	public Pagination getPageByUser(Integer userId,Integer type,int pageNo, int pageSize);
	public Order findByOutOrderNum(String orderNum,Integer payMethod);
}