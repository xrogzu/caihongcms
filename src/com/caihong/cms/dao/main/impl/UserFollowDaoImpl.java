package com.caihong.cms.dao.main.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.caihong.common.hibernate4.Finder;
import com.caihong.common.hibernate4.HibernateBaseDao;
import com.caihong.common.page.Pagination;
import com.caihong.cms.dao.main.UserFollowDao;
import com.caihong.cms.entity.main.UserFollow;

@Repository
public class UserFollowDaoImpl extends HibernateBaseDao<UserFollow, Integer> implements UserFollowDao {
	public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	public UserFollow findById(Integer id) {
		UserFollow entity = get(id);
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	public UserFollow findByFollowUser(Integer userid,Integer followUserid){
		Finder f = Finder.create("select bean from UserFollow bean where 1=1");
		if(userid!=null){
			f.append(" and  bean.user.id=:userid");
			f.setParam("userid", userid);
		}
		if(followUserid!=null){
			f.append(" and  bean.followUser.id=:followUserid");
			f.setParam("followUserid", followUserid);
		}
		f.setCacheable(true);
		List<UserFollow> list=find(f);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	public UserFollow save(UserFollow bean) {
		getSession().save(bean);
		return bean;
	}

	public UserFollow deleteById(Integer id) {
		UserFollow entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	public UserFollow delete(UserFollow bean){
		if (bean != null) {
			getSession().delete(bean);
		}
		return bean;
	}
	
	@Override
	protected Class<UserFollow> getEntityClass() {
		return UserFollow.class;
	}
}