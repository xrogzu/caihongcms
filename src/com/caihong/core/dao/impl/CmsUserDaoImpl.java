package com.caihong.core.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.caihong.common.hibernate4.Finder;
import com.caihong.common.hibernate4.HibernateBaseDao;
import com.caihong.common.page.Pagination;
import com.caihong.core.dao.CmsUserDao;
import com.caihong.core.entity.CmsUser;

@Repository
public class CmsUserDaoImpl extends HibernateBaseDao<CmsUser, Integer>
		implements CmsUserDao {
	@SuppressWarnings("unchecked")
	public List<CmsUser> getListForTag(Integer nationId,Integer majorId,Integer jobTitleId,Integer jobLevelId,Integer departId){
		Finder f = Finder.create("select bean from CmsUser bean join bean.userExtSet ext ");
		if(nationId!=null){
			f.append(" and  bean.nation.id=:nationId");
			f.setParam("nationId", nationId);
		}
		if(jobTitleId!=null){
			f.append(" and  bean.jobTitle.id = :jobTitleId");
			f.setParam("jobTitleId", jobTitleId);
		}
		if(jobLevelId!=null){
			f.append(" and  bean.jobLevel.id = :jobLevelId");
			f.setParam("jobLevelId", jobLevelId);
		}
		if(majorId!=null){
			f.append(" and  bean.major.id = :majorId");
			f.setParam("majorId", majorId);
		}
		if(departId!=null){
			f.append(" and bean.department.id=:departId");
			f.setParam("departId", departId);
		}
		f.setCacheable(true);
		return find(f);
	}
	public Pagination getPageListForTag(Integer nationId,Integer majorId,Integer jobTitleId,Integer jobLevelId,Integer departId, int pageNo, int pageSize){
		Finder f = Finder.create("select bean from CmsUser bean join bean.userExtSet ext ");
		if(nationId!=null){
			f.append(" and  bean.nation.id=:nationId");
			f.setParam("nationId", nationId);
		}
		if(jobTitleId!=null){
			f.append(" and  bean.jobTitle.id = :jobTitleId");
			f.setParam("jobTitleId", jobTitleId);
		}
		if(jobLevelId!=null){
			f.append(" and  bean.jobLevel.id = :jobLevelId");
			f.setParam("jobLevelId", jobLevelId);
		}
		if(majorId!=null){
			f.append(" and  bean.major.id = :majorId");
			f.setParam("majorId", majorId);
		}
		if(departId!=null){
			f.append(" and bean.department.id=:departId");
			f.setParam("departId", departId);
		}
		f.setCacheable(true);
		return find(f, pageNo, pageSize);
	}
	public Pagination getPage(String username, String email, Integer siteId,
			Integer groupId, Boolean disabled, Boolean admin, Integer rank,
			String realName,Integer departId,Integer roleId,
			Boolean allChannel,Boolean allControlChannel,Integer nationId,Integer majorId,Integer jobTitleId,Integer jobLevelId,String idNo,
			int pageNo, int pageSize) {
		Finder f = Finder.create("select bean from CmsUser bean join bean.userExtSet ext ");
		if (siteId != null||allChannel!=null||allControlChannel!=null) {
			f.append(" join bean.userSites userSite");
		}
		if(roleId!=null){
			f.append(" join bean.roles role ");
		}
		f.append(" where 1=1");
		if(siteId!=null){
			f.append(" and  userSite.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		if(nationId!=null){
			f.append(" and  bean.nation.id=:nationId");
			f.setParam("nationId", nationId);
		}
		if(jobTitleId!=null){
			f.append(" and  bean.jobTitle.id = :jobTitleId");
			f.setParam("jobTitleId", jobTitleId);
		}
		if(jobLevelId!=null){
			f.append(" and  bean.jobLevel.id = :jobLevelId");
			f.setParam("jobLevelId", jobLevelId);
		}
		if(majorId!=null){
			f.append(" and  bean.major.id = :majorId");
			f.setParam("majorId", majorId);
		}
		if(!StringUtils.isBlank(idNo)){
			f.append(" and  bean.idNo = :idNo");
			f.setParam("idNo", idNo);
		}
		if (!StringUtils.isBlank(username)) {
			f.append(" and bean.username like :username");
			f.setParam("username", "%" + username + "%");
		}
		if (!StringUtils.isBlank(email)) {
			f.append(" and bean.email like :email");
			f.setParam("email", "%" + email + "%");
		}
		if (groupId != null) {
			f.append(" and bean.group.id=:groupId");
			f.setParam("groupId", groupId);
		}
		if (disabled != null) {
			f.append(" and bean.disabled=:disabled");
			f.setParam("disabled", disabled);
		}
		if (admin != null) {
			f.append(" and bean.admin=:admin");
			f.setParam("admin", admin);
		}
		if (rank != null) {
			f.append(" and bean.rank<=:rank");
			f.setParam("rank", rank);
		}
		if (!StringUtils.isBlank(realName)) {
			f.append(" and ext.realname like :realname");
			f.setParam("realname", "%" + realName + "%");
		}
		if(departId!=null){
			f.append(" and bean.department.id=:departId");
			f.setParam("departId", departId);
		}
		if(roleId!=null){
			f.append(" and role.id=:roleId");
			f.setParam("roleId", roleId);
		}
		if (allChannel != null) {
			f.append(" and userSite.allChannel=:allChannel");
			f.setParam("allChannel", allChannel);
		}
		if (allControlChannel != null) {
			f.append(" and userSite.allChannelControl=:allControlChannel");
			f.setParam("allControlChannel", allControlChannel);
		}
		//用户有多个站的管理权限需要去重复
		/*
		if(allChannel!=null||allControlChannel!=null){
			f.append(" group by bean having count(bean)=1");
		}
		*/
		f.append(" order by bean.id desc");
		return find(f, pageNo, pageSize);
	}
	
	@SuppressWarnings("unchecked")
	public List<CmsUser> getList(String username, String email, Integer siteId,
			Integer groupId, Boolean disabled, Boolean admin, Integer rank,Integer nationId,Integer majorId,Integer jobTitleId,Integer jobLevelId,String idNo) {
		Finder f = Finder.create("select bean from CmsUser bean");
		if (siteId != null) {
			f.append(" join bean.userSites userSite");
			f.append(" where userSite.site.id=:siteId");
			f.setParam("siteId", siteId);
		} else {
			f.append(" where 1=1");
		}
		if(nationId!=null){
			f.append(" and  bean.nation.id=:nationId");
			f.setParam("nationId", nationId);
		}
		if(jobTitleId!=null){
			f.append(" and  bean.jobTitle.id = :jobTitleId");
			f.setParam("jobTitleId", jobTitleId);
		}
		if(jobLevelId!=null){
			f.append(" and  bean.jobLevel.id = :jobLevelId");
			f.setParam("jobLevelId", jobLevelId);
		}
		if(majorId!=null){
			f.append(" and  bean.major.id = :majorId");
			f.setParam("major", majorId);
		}
		if(!StringUtils.isBlank(idNo)){
			f.append(" and  bean.idNo = :idNo");
			f.setParam("idNo", idNo);
		}
		if (!StringUtils.isBlank(username)) {
			f.append(" and bean.username like :username");
			f.setParam("username", "%" + username + "%");
		}
		if (!StringUtils.isBlank(email)) {
			f.append(" and bean.email like :email");
			f.setParam("email", "%" + email + "%");
		}
		if (groupId != null) {
			f.append(" and bean.group.id=:groupId");
			f.setParam("groupId", groupId);
		}
		if (disabled != null) {
			f.append(" and bean.disabled=:disabled");
			f.setParam("disabled", disabled);
		}
		if (admin != null) {
			f.append(" and bean.admin=:admin");
			f.setParam("admin", admin);
		}
		if (rank != null) {
			f.append(" and bean.rank<=:rank");
			f.setParam("rank", rank);
		}
		f.append(" order by bean.id desc");
		return find(f);
	}

	@SuppressWarnings("unchecked")
	public List<CmsUser> getAdminList(Integer siteId, Boolean allChannel,
			Boolean disabled, Integer rank) {
		Finder f = Finder.create("select bean from CmsUser");
		f.append(" bean join bean.userSites us");
		f.append(" where us.site.id=:siteId");
		f.setParam("siteId", siteId);
		if (allChannel != null) {
			f.append(" and us.allChannel=:allChannel");
			f.setParam("allChannel", allChannel);
		}
		if (disabled != null) {
			f.append(" and bean.disabled=:disabled");
			f.setParam("disabled", disabled);
		}
		if (rank != null) {
			f.append(" and bean.rank<=:rank");
			f.setParam("rank", rank);
		}
		f.append(" and bean.admin=true");
		f.append(" order by bean.id asc");
		return find(f);
	}
	
	public Pagination getAdminsByDepartId(Integer id, int pageNo,int pageSize){
		Finder f = Finder.create("select bean from CmsUser bean ");
		f.append(" where bean.department.id=:departId");
		f.setParam("departId", id);
		f.append(" and bean.admin=true");
		f.append(" order by bean.id asc");
		return find(f,pageNo,pageSize);
	}
	
	public Pagination getAdminsByRoleId(Integer roleId, int pageNo, int pageSize){
		Finder f = Finder.create("select bean from CmsUser");
		f.append(" bean join bean.roles role");
		f.append(" where role.id=:roleId");
		f.setParam("roleId", roleId);
		f.append(" and bean.admin=true");
		f.append(" order by bean.id asc");
		return find(f,pageNo,pageSize);
	}

	public CmsUser findById(Integer id) {
		CmsUser entity = get(id);
		return entity;
	}

	public CmsUser findByUsername(String username) {
		return findUniqueByProperty("username", username);
	}

	public int countByUsername(String username) {
		String hql = "select count(*) from CmsUser bean where bean.username=:username";
		Query query = getSession().createQuery(hql);
		query.setParameter("username", username);
		return ((Number) query.iterate().next()).intValue();
	}
	public int countMemberByUsername(String username) {
		String hql = "select count(*) from CmsUser bean where bean.username=:username and bean.admin=false";
		Query query = getSession().createQuery(hql);
		query.setParameter("username", username);
		return ((Number) query.iterate().next()).intValue();
	}

	public int countByEmail(String email) {
		String hql = "select count(*) from CmsUser bean where bean.email=:email";
		Query query = getSession().createQuery(hql);
		query.setParameter("email", email);
		return ((Number) query.iterate().next()).intValue();
	}

	public CmsUser save(CmsUser bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsUser deleteById(Integer id) {
		CmsUser entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsUser> getEntityClass() {
		return CmsUser.class;
	}

	
}