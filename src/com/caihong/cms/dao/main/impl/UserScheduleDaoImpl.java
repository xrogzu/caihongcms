package com.caihong.cms.dao.main.impl;

import java.util.Date;

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
	
	public Pagination getPage(Integer userid,int pageNo, int pageSize) {
		String hql=" select bean from UserSchedule bean where 1=1 ";
		Finder f=Finder.create(hql);
		if(userid!=null){
			f.append(" and bean.id=:userid").setParam("userid", userid);
		}
		f.setCacheable(true);
		return find(f, pageNo, pageSize);
	}
	public Pagination getPage(Integer userid,Date startDate,Date endDate,Integer nationId,Integer majorId,Integer jobTitleId,Integer jobLevelId,int pageNo, int pageSize){
		String hql=" select bean from UserSchedule bean join bean.user ext where 1=1 ";
		Finder f=Finder.create(hql);
		if(userid!=null){
			f.append(" and bean.id=:userid").setParam("userid", userid);
		}
		if(startDate!=null){
			f.append(" and bean.startDate>=:startDate").setParam("startDate", startDate);
		}
		if(endDate!=null){
			f.append(" and bean.endDate<=:endDate").setParam("endDate", endDate);
		}
		if(nationId!=null){
			f.append(" and ext.nation.id=:nationId").setParam("nationId", nationId);
		}
		if(majorId!=null){
			f.append(" and ext.major.id=:majorId").setParam("majorId", majorId);
		}
		
		if(jobTitleId!=null){
			f.append(" and ext.jobTitle.id=:jobTitleId").setParam("jobTitleId", jobTitleId);
		}
		if(jobLevelId!=null){
			f.append(" and ext.jobLevel.id=:jobLevelId").setParam("jobLevelId", jobLevelId);
		}
		f.append(" order by bean.createTime desc");
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