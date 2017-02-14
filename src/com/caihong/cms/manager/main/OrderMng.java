package com.caihong.cms.manager.main;

import com.caihong.common.page.Pagination;
import com.caihong.cms.entity.main.Order;

public interface OrderMng {
	public Pagination getPage(int pageNo, int pageSize);

	public Order findById(Integer id);

	public Order save(Order bean);

	public Order update(Order bean);

	public Order deleteById(Integer id);
	
	public Order[] deleteByIds(Integer[] ids);
	public Order findByOrderNumber(String orderNumber);
	
	public Order findByOutOrderNum(String orderNum,Integer payMethod);
	
	public Pagination getPageByUser(Integer userId,Integer type,int pageNo, int pageSize);
	
//	public Order contentOrder(Integer rewardUserId,OrderType type,Integer objectId,Integer buyUserId,String outOrderNum,Integer payType);
}