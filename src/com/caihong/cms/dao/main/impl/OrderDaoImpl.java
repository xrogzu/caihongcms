package com.caihong.cms.dao.main.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.caihong.common.hibernate4.Finder;
import com.caihong.common.hibernate4.HibernateBaseDao;
import com.caihong.common.page.Pagination;
import com.caihong.cms.dao.main.OrderDao;
import com.caihong.cms.entity.main.Order;

@Repository
public class OrderDaoImpl extends HibernateBaseDao<Order, Integer> implements OrderDao {
	public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	public Order findById(Integer id) {
		Order entity = get(id);
		return entity;
	}

	public Order save(Order bean) {
		getSession().save(bean);
		return bean;
	}

	public Order deleteById(Integer id) {
		Order entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<Order> getEntityClass() {
		return Order.class;
	}

	@Override
	public Order findByOrderNumber(String orderNumber) {
		String hql="from Order bean where bean.orderNum=:orderNum";
		Finder finder=Finder.create(hql).setParam("orderNum", orderNumber);
		List<Order>list=find(finder);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public Order findByOutOrderNum(String orderNum, Integer payMethod) {
		String hql;
		if(payMethod==Order.PAY_METHOD_WECHAT){
			hql="from Order bean where bean.orderNumWeiXin=:orderNum";
		}else{
			hql="from Order bean where bean.orderNumAliPay=:orderNum";
		}
		Finder finder=Finder.create(hql).setParam("orderNum", orderNum);
		List<Order>list=find(finder);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public Pagination getPageByUser(Integer userId, Integer type, int pageNo, int pageSize) {
		String hql="from Order bean where 1=1 ";
		Finder f=Finder.create(hql);
		if(userId!=null){
			f.append(" and bean.user.id=:userId").setParam("userId", userId);
		}
		if(type!=null){
			f.append(" and bean.type=:type")
			.setParam("type", type);
		}
		f.append(" order by bean.time desc");
		f.setCacheable(true);
		Pagination page = find(f, pageNo, pageSize);
		return page;
	}
}