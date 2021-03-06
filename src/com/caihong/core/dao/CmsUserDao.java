package com.caihong.core.dao;

import java.util.List;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.core.entity.CmsUser;

/**
 * 用户DAO接口
 */
public interface CmsUserDao{
	public List<CmsUser> getListForTag(Integer nation,Integer major,Integer jobTitle,Integer jobLevel,Integer departId);
	public Pagination getPageListForTag(Integer nation,Integer major,Integer jobTitle,Integer jobLevel,Integer departId, int pageNo, int pageSize);
	public Pagination getPage(String username, String email, Integer siteId,
			Integer groupId, Boolean disabled, Boolean admin, Integer rank,
			String realName,Integer departId,Integer roleId,
			Boolean allChannel,Boolean allControlChannel,Integer nation,Integer major,Integer jobTitle,Integer jobLevel,String idNo,
			int pageNo, int pageSize);
	
	
	public List<CmsUser> getList(String username, String email, Integer siteId,
			Integer groupId, Boolean disabled, Boolean admin, Integer rank,Integer nation,Integer major,Integer jobTitle,Integer jobLevel,String idNo);

	public List<CmsUser> getAdminList(Integer siteId, Boolean allChannel,
			Boolean disabled, Integer rank);
	
	public Pagination getAdminsByDepartId(Integer id, int pageNo,int pageSize);
	
	public Pagination getAdminsByRoleId(Integer roleId, int pageNo, int pageSize);

	public CmsUser findById(Integer id);

	public CmsUser findByUsername(String username);

	public int countByUsername(String username);
	
	public int countMemberByUsername(String username);

	public int countByEmail(String email);

	public CmsUser save(CmsUser bean);

	public CmsUser updateByUpdater(Updater<CmsUser> updater);

	public CmsUser deleteById(Integer id);
}