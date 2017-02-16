package com.caihong.cms.dao.main.impl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.caihong.common.hibernate4.Finder;
import com.caihong.common.hibernate4.HibernateBaseDao;
import com.caihong.common.page.Pagination;
import com.caihong.cms.dao.main.UserScheduleDao;
import com.caihong.cms.entity.main.UserSchedule;

@Repository
public class UserScheduleDaoImpl extends HibernateBaseDao<UserSchedule, Integer> implements UserScheduleDao {
	public Pagination getPage(String username,int pageNo, int pageSize) {
		String hql=" select bean from UserSchedule bean where 1=1 ";
		Finder f=Finder.create(hql);
		if(StringUtils.isNotBlank(username)){
			f.append(" and bean.user.username=:username").setParam("username", username);
		}
		f.setCacheable(true);
		return find(f, pageNo, pageSize);
	}

	public UserSchedule findById(Integer id) {
		UserSchedule entity = get(id);
		return entity;
	}

	public UserSchedule save(UserSchedule bean) {
		getSession().save(bean);
		return bean;
	}

	public UserSchedule deleteById(Integer id) {
		UserSchedule entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<UserSchedule> getEntityClass() {
		return UserSchedule.class;
	}
}