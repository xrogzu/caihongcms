package com.caihong.cms.dao.main.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.caihong.common.hibernate4.HibernateBaseDao;
import com.caihong.common.page.Pagination;
import com.caihong.cms.dao.main.SmsSendRecordDao;
import com.caihong.cms.entity.main.SmsSendRecord;

@Repository
public class SmsSendRecordDaoImpl extends HibernateBaseDao<SmsSendRecord, Integer> implements SmsSendRecordDao {
	public Pagination getPage(int pageNo, int pageSize,String... args) {
		Criteria crit = createCriteria();
		if(args.length>0){
			crit.add(Restrictions.eq("telphone", args[0]));
		}
		crit.addOrder(Order.desc("createOn"));
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	public SmsSendRecord findById(Integer id) {
		SmsSendRecord entity = get(id);
		return entity;
	}

	public SmsSendRecord save(SmsSendRecord bean) {
		getSession().save(bean);
		getSession().flush();
		return bean;
	}

	public SmsSendRecord deleteById(Integer id) {
		SmsSendRecord entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<SmsSendRecord> getEntityClass() {
		return SmsSendRecord.class;
	}
}