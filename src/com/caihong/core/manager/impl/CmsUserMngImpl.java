package com.caihong.core.manager.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.caihong.cms.entity.main.Channel;
import com.caihong.cms.entity.main.GrainDetail;
import com.caihong.cms.manager.main.ChannelMng;
import com.caihong.cms.manager.main.ContentMng;
import com.caihong.cms.manager.main.GrainDetailMng;
import com.caihong.cms.ws.TopicHttpSender;
import com.caihong.common.email.EmailSender;
import com.caihong.common.email.MessageTemplate;
import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.common.web.GetGrainType;
import com.caihong.core.dao.CmsUserDao;
import com.caihong.core.entity.CmsDepartment;
import com.caihong.core.entity.CmsGroup;
import com.caihong.core.entity.CmsSite;
import com.caihong.core.entity.CmsUser;
import com.caihong.core.entity.CmsUserExt;
import com.caihong.core.entity.CmsWorkflowEvent;
import com.caihong.core.entity.UnifiedUser;
import com.caihong.core.manager.CmsDepartmentMng;
import com.caihong.core.manager.CmsDictionaryMng;
import com.caihong.core.manager.CmsGroupMng;
import com.caihong.core.manager.CmsRoleMng;
import com.caihong.core.manager.CmsSiteMng;
import com.caihong.core.manager.CmsUserExtMng;
import com.caihong.core.manager.CmsUserMng;
import com.caihong.core.manager.CmsUserSiteMng;
import com.caihong.core.manager.CmsWorkflowEventMng;
import com.caihong.core.manager.UnifiedUserMng;

@Service
@Transactional
public class CmsUserMngImpl implements CmsUserMng {
	@Transactional(readOnly = true)
	public List<CmsUser> getListForTag(Integer nationId, Integer majorId, Integer jobTitleId, Integer jobLevelId,
			Integer departId) {		
		return dao.getListForTag(nationId, majorId, jobTitleId, jobLevelId, departId);
	}
	public Pagination getPageListForTag(Integer nationId,Integer majorId,Integer jobTitleId,Integer jobLevelId,Integer departId, int pageNo, int pageSize){
		return dao.getPageListForTag(nationId, majorId, jobTitleId, jobLevelId, departId, pageNo, pageSize);
	}
	@Transactional(readOnly = true)
	public Pagination getPage(String username, String email, Integer siteId,
			Integer groupId, Boolean disabled, Boolean admin, Integer rank,
			String realName,Integer departId,Integer roleId,
			Boolean allChannel,Boolean allControlChannel,Integer nation,Integer major,Integer jobTitle,Integer jobLevel,String idNo,
			int pageNo, int pageSize) {
		Pagination page = dao.getPage(username, email, siteId, groupId,
				disabled, admin, rank,realName,departId,roleId, 
				allChannel,allControlChannel,nation,major,jobTitle,jobLevel,idNo,pageNo, pageSize);
		return page;
	}
	
	@Transactional(readOnly = true)
	public List<CmsUser> getList(String username, String email, Integer siteId,
			Integer groupId, Boolean disabled, Boolean admin, Integer rank,Integer nation,Integer major,Integer jobTitle,Integer jobLevel,String idNo) {
		List<CmsUser> list = dao.getList(username, email, siteId, groupId,
				disabled, admin, rank,nation,major,jobTitle,jobLevel,idNo);
		return list;
	}

	@Transactional(readOnly = true)
	public List<CmsUser> getAdminList(Integer siteId, Boolean allChannel,
			Boolean disabled, Integer rank) {
		return dao.getAdminList(siteId, allChannel, disabled, rank);
	}
	
	@Transactional(readOnly = true)
	public Pagination getAdminsByDepartId(Integer id, int pageNo,int pageSize){
		return dao.getAdminsByDepartId(id, pageNo, pageSize);
	}
	
	@Transactional(readOnly = true)
	public Pagination getAdminsByRoleId(Integer roleId, int pageNo, int pageSize){
		return dao.getAdminsByRoleId(roleId, pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public CmsUser findById(Integer id) {
		CmsUser entity = dao.findById(id);
		return entity;
	}

	@Transactional(readOnly = true)
	public CmsUser findByUsername(String username) {
		CmsUser entity = dao.findByUsername(username);
		return entity;
	}

	public CmsUser registerMember(String username, String email,String telphone,
			String password, String ip, Integer groupId,Integer departmentId,Integer grain,boolean disabled,Integer nation,Integer major,Integer jobTitle,Integer jobLevel,String idNo,Integer fansCnt,Integer followCnt,Double price,CmsUserExt userExt,Map<String,String>attr,Integer prestige){
		UnifiedUser unifiedUser = unifiedUserMng.save(username, email,telphone,
				password, ip);
		CmsUser user = new CmsUser();
		user.forMember(unifiedUser);
		user.setGrain(grain);
		user.setAttr(attr);
		user.setDisabled(disabled);
		if(nation!=null){
			user.setNation(cmsDictionaryMng.findById(nation));
		}
		if(jobLevel!=null){
			user.setJobLevel(cmsDictionaryMng.findById(jobLevel));
		}
		if(major!=null){
			user.setMajor(cmsDictionaryMng.findById(major));
		}
		if(jobTitle!=null){
			user.setJobTitle(cmsDictionaryMng.findById(jobTitle));
		}
		if(prestige!=null){
			user.setGrain(prestige);
		}
		if(price!=null){
			user.setPrice(price);
		}
		user.setIdNo(idNo);
		user.setFansCnt(fansCnt);
		user.setFollowCnt(followCnt);
		CmsGroup group = null;
		if (groupId != null) {
			group = cmsGroupMng.findById(groupId);
		} else {
			group = cmsGroupMng.getRegDef();
		}
		if (group == null) {
			throw new RuntimeException(
					"register default member group not found!");
		}
		if(departmentId!=null){
			user.setDepartment(cmsDepartmentMng.findById(departmentId));
		}
		user.setGroup(group);
		user.init();
		user=dao.save(user);
		saveGrainDetail(user,null,user.getGrain(),GetGrainType.REG);
		cmsUserExtMng.save(userExt, user);
		return user;
	}

	public void saveGrainDetail(CmsUser user,CmsUser fromUser,Integer grain,GetGrainType type){
		GrainDetail detail=new GrainDetail();
		if(grain==null||grain==0){
			return;
		}
		detail.setUser(user);
		if(fromUser!=null){
			detail.setFromUser(fromUser);
		}
		detail.setGrainCnt(grain);
		detail.setType(type.getValue());
		detail.setTime(new Date());
		grainDetailMng.save(detail);
		if(type!=GetGrainType.REG&&type!=GetGrainType.BBS){//注册和论坛来源不做同步
			TopicHttpSender.updateGrain(user.getUsername(), grain);//注册不同步，接口请求修改
		}
	}
	
	public CmsUser registerMember(String username, String email,String telphone,
			String password, String ip, Integer groupId,Integer departmentId, boolean disabled,Integer nation,Integer major,Integer jobTitle,Integer jobLevel,String idNo,Integer fansCnt,Integer followCnt, Integer prestige,Double price,CmsUserExt userExt,Map<String,String>attr,
			Boolean activation, EmailSender sender, MessageTemplate msgTpl)throws UnsupportedEncodingException, MessagingException{
		UnifiedUser unifiedUser = unifiedUserMng.save(username, email,telphone,
				password, ip, activation, sender, msgTpl);
		CmsUser user = new CmsUser();
		user.forMember(unifiedUser);
		user.setAttr(attr);
		user.setDisabled(disabled);
		if(nation!=null){
			user.setNation(cmsDictionaryMng.findById(nation));
		}
		if(jobLevel!=null){
			user.setJobLevel(cmsDictionaryMng.findById(jobLevel));
		}
		if(major!=null){
			user.setMajor(cmsDictionaryMng.findById(major));
		}
		if(jobTitle!=null){
			user.setJobTitle(cmsDictionaryMng.findById(jobTitle));
		}
		if(price!=null){
			user.setPrice(price);
		}
		user.setIdNo(idNo);
		user.setFansCnt(fansCnt);
		user.setFollowCnt(followCnt);
		if( prestige!=null){
			user.setGrain(prestige);
		}
		CmsGroup group = null;
		if (groupId != null) {
			group = cmsGroupMng.findById(groupId);
		} else {
			group = cmsGroupMng.getRegDef();
		}
		if(departmentId!=null){
			user.setDepartment(cmsDepartmentMng.findById(departmentId));
		}
		if (group == null) {
			throw new RuntimeException(
					"register default member group not found!");
		}
		user.setGroup(group);
		user.init();
		user=dao.save(user);
		this.saveGrainDetail(user, null, user.getGrain(), GetGrainType.REG);
		
		cmsUserExtMng.save(userExt, user);
		return user;
	}

	public void updateLoginInfo(Integer userId, String ip,Date loginTime,String sessionId) {
		CmsUser user = findById(userId);
		if (user != null) {
			user.setLoginCount(user.getLoginCount() + 1);
			if(StringUtils.isNotBlank(ip)){
				user.setLastLoginIp(ip);
			}
			if(loginTime!=null){
				user.setLastLoginTime(loginTime);
			}
			user.setSessionId(sessionId);
		}
	}
	
	public void updateCommentCnt(Integer userId, int cnt){
		CmsUser user = findById(userId);
		if (user != null) {
			if((user.getCommentCnt() + cnt)<0){
				user.setFansCnt(0);
			}else{
				user.setCommentCnt(user.getFansCnt() + cnt);	
			}
		}
	}
	
	public void updateFansCnt(Integer userId, int cnt){
		CmsUser user = findById(userId);
		if (user != null) {
			if((user.getFansCnt() + cnt)<0){
				user.setFansCnt(0);
			}else{
				user.setFansCnt(user.getFansCnt() + cnt);	
			}
		}
	}
	
	public void updateFollowCnt(Integer userId, int cnt){
		CmsUser user = findById(userId);
		if (user != null) {
			if((user.getFollowCnt() + cnt)<0){
				user.setFollowCnt(0);
			}else{
				user.setFollowCnt(user.getFollowCnt() + cnt);	
			}
		}
		
	}
	public CmsUser updateGrainCnt(String username, int cnt,GetGrainType type) {
		CmsUser user=dao.findByUsername(username);
		
		return updateGrainCnt(user,null,cnt,type);
	}
	public CmsUser updateGrainCnt(CmsUser user,CmsUser fromuser,int cnt,GetGrainType type){
		if(user!=null&&cnt!=0){
			if((user.getGrain()+cnt)<0){
				user.setGrain(0);
			}else{
				user.setGrain(user.getGrain()+cnt);
			}
			
			saveGrainDetail(user, fromuser, cnt, type);//插入记录
		}
		
		return user;
	}
	public void updateUploadSize(Integer userId, Integer size) {
		CmsUser user = findById(userId);
		user.setUploadTotal(user.getUploadTotal() + size);
		if (user.getUploadDate() != null) {
			if (CmsUser.isToday(user.getUploadDate())) {
				size += user.getUploadSize();
			}
		}
		user.setUploadDate(new java.sql.Date(System.currentTimeMillis()));
		user.setUploadSize(size);
	}
	
	public void updateUser(CmsUser user){
		Updater<CmsUser>updater=new Updater<CmsUser>(user);
		dao.updateByUpdater(updater);
	}

	public boolean isPasswordValid(Integer id, String password) {
		return unifiedUserMng.isPasswordValid(id, password);
	}

	public void updatePwdEmail(Integer id, String password, String email,String telphone) {
		CmsUser user = findById(id);
		if (!StringUtils.isBlank(email)) {
			user.setEmail(email);
		} else {
			user.setEmail(null);
		}
		if (!StringUtils.isBlank(telphone)) {
			user.setTelphone(telphone);
		} else {
			user.setTelphone(null);
		}
		unifiedUserMng.update(id, password, email,telphone);
	}

	public CmsUser saveAdmin(String username, String email, String telphone,String password,
			String ip, boolean viewOnly, boolean selfAdmin, int rank,
			Integer groupId,Integer departmentId, Integer[] roleIds,Integer[] channelIds,
			Integer[] siteIds, Byte[] steps, Boolean[] allChannels, Boolean[] allControlChannels,
			CmsUserExt userExt) {
		UnifiedUser unifiedUser = unifiedUserMng.save(username, email,telphone,
				password, ip);
		CmsUser user = new CmsUser();
		user.forAdmin(unifiedUser, viewOnly, selfAdmin, rank);
		CmsGroup group = null;
		CmsDepartment department=null;
		if (groupId != null) {
			group = cmsGroupMng.findById(groupId);
		} else {
			group = cmsGroupMng.getRegDef();
		}
		if(departmentId!=null){
			department=cmsDepartmentMng.findById(departmentId);
		}
		if (group == null) {
			throw new RuntimeException(
					"register default member group not setted!");
		}
		/*
		if (department == null) {
			throw new RuntimeException(
					"register default admin department not setted!");
		}
		*/
		user.setGroup(group);
		user.setDepartment(department);
		user.init();
		dao.save(user);
		cmsUserExtMng.save(userExt, user);
		if (roleIds != null) {
			for (Integer rid : roleIds) {
				user.addToRoles(cmsRoleMng.findById(rid));
			}
		}
		if (channelIds != null) {
			Channel channel;
			for (Integer cid : channelIds) {
				channel = channelMng.findById(cid);
				channel.addToUsers(user);
			}
		}
		if (siteIds != null) {
			CmsSite site;
			for (int i = 0, len = siteIds.length; i < len; i++) {
				site = cmsSiteMng.findById(siteIds[i]);
				cmsUserSiteMng.save(site, user, steps[i], allChannels[i],allControlChannels[i]);
			}
		}
		return user;
	}

	public void addSiteToUser(CmsUser user, CmsSite site, Byte checkStep) {
		cmsUserSiteMng.save(site, user, checkStep, true,true);
	}

	public CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
			Integer groupId,Integer departmentId, Integer[] roleIds, Integer[] channelIds,
			Integer siteId, Byte step, Boolean allChannel,Boolean allControlChannel) {
		CmsUser user = updateAdmin(bean, ext, password, groupId,departmentId, roleIds,
				channelIds);
		// 更新所属站点
		cmsUserSiteMng.updateByUser(user, siteId, step, allChannel,allControlChannel);
		return user;
	}

	public CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
			Integer groupId,Integer departmentId, Integer[] roleIds, Integer[] channelIds,
			Integer[] siteIds, Byte[] steps, Boolean[] allChannels,Boolean[] allControlChannels) {
		CmsUser user = updateAdmin(bean, ext, password, groupId,departmentId,roleIds,channelIds);
		// 更新所属站点
		cmsUserSiteMng.updateByUser(user, siteIds, steps, allChannels,allControlChannels);
		return user;
	}

	private CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
			Integer groupId,Integer departmentId,Integer[] roleIds, Integer[] channelIds) {
		Updater<CmsUser> updater = new Updater<CmsUser>(bean);
		updater.include("email");
		CmsUser user = dao.updateByUpdater(updater);
		user.setGroup(cmsGroupMng.findById(groupId));
		if(departmentId!=null){
			user.setDepartment(cmsDepartmentMng.findById(departmentId));
		}
		cmsUserExtMng.update(ext, user);
		// 更新角色
		user.getRoles().clear();
		if (roleIds != null) {
			for (Integer rid : roleIds) {
				user.addToRoles(cmsRoleMng.findById(rid));
			}
		}
		/*
		// 更新栏目权限
		Set<Channel> channels = user.getChannels();
		// 清除
		for (Channel channel : channels) {
			channel.getUsers().remove(user);
		}
		user.getChannels().clear();
		// 添加
		if (channelIds != null) {
			Channel channel;
			for (Integer cid : channelIds) {
				channel = channelMng.findById(cid);
				channel.addToUsers(user);
			}
		}
		*/
		unifiedUserMng.update(bean.getId(), password, bean.getEmail(),bean.getTelphone());
		return user;
	}

	public CmsUser updateMember(Integer id, String email,String telphone, String password,
			Boolean isDisabled,Integer nation,Integer major,Integer jobTitle,Integer jobLevel,String idNo,CmsUserExt ext, Integer groupId,Integer departmentId,Integer grain,Integer fansCnt,Integer followCnt,Double price,Map<String,String>attr) {
		CmsUser entity = findById(id);
		entity.setEmail(email);
		/*
		if (!StringUtils.isBlank(email)) {
			entity.setEmail(email);
		}
		*/
		if (!StringUtils.isBlank(telphone)) {
			entity.setTelphone(telphone);
		}
		if (isDisabled != null) {
			entity.setDisabled(isDisabled);
		}
		if (groupId != null) {
			entity.setGroup(cmsGroupMng.findById(groupId));
		}
		if(departmentId!=null){
			entity.setDepartment(cmsDepartmentMng.findById(departmentId));
		}
			
		if(grain!=null){
			entity.setGrain(grain);
		}
		if(price!=null){
			entity.setPrice(price);
		}
		if(fansCnt!=null){
			entity.setFansCnt(fansCnt);
		}
		if(followCnt!=null){
			entity.setFollowCnt(followCnt);
		}
		if(nation!=null){
			entity.setNation(cmsDictionaryMng.findById(nation));
		}
		if(jobLevel!=null){
			entity.setJobLevel(cmsDictionaryMng.findById(jobLevel));
		}
		if(major!=null){
			entity.setMajor(cmsDictionaryMng.findById(major));
		}
		if(jobTitle!=null){
			entity.setJobTitle(cmsDictionaryMng.findById(jobTitle));
		}
		if (!StringUtils.isBlank(idNo)) {
			entity.setIdNo(idNo);
		}
		// 更新属性表
		if (attr != null) {
			Map<String, String> attrOrig = entity.getAttr();
			attrOrig.clear();
			attrOrig.putAll(attr);
		}
		cmsUserExtMng.update(ext, entity);
		unifiedUserMng.update(id, password, email,telphone);
		return entity;
	}
	
	public CmsUser updateMember(Integer id, String email,String telphone, String password,Integer groupId,Integer departmentId,String realname,String mobile,Boolean sex,Integer nation,Integer major,Integer jobTitle,Integer jobLevel,String idNo,Integer fansCnt,Integer followCnt,Double price) {
		CmsUser entity = findById(id);
		CmsUserExt ext =entity.getUserExt();
		if (!StringUtils.isBlank(email)) {
			entity.setEmail(email);
		}
		if (!StringUtils.isBlank(telphone)) {
			entity.setTelphone(telphone);
		}
		if(nation!=null){
			entity.setNation(cmsDictionaryMng.findById(nation));
		}
		if(jobLevel!=null){
			entity.setJobLevel(cmsDictionaryMng.findById(jobLevel));
		}
		if(major!=null){
			entity.setMajor(cmsDictionaryMng.findById(major));
		}
		if(jobTitle!=null){
			entity.setJobTitle(cmsDictionaryMng.findById(jobTitle));
		}
		if (!StringUtils.isBlank(idNo)) {
			entity.setIdNo(idNo);
		}
		if(fansCnt!=null){
			entity.setFansCnt(fansCnt);
		}
		if(followCnt!=null){
			entity.setFollowCnt(followCnt);
		}
		if (groupId != null) {
			entity.setGroup(cmsGroupMng.findById(groupId));
		}
		if(price!=null){
			entity.setPrice(price);
		}
		if(departmentId!=null){
			entity.setDepartment(cmsDepartmentMng.findById(departmentId));
		}
		if (!StringUtils.isBlank(realname)) {
			ext.setRealname(realname);
		}
		if (!StringUtils.isBlank(mobile)) {
			ext.setMobile(mobile);
		}
		if (sex!=null) {
			ext.setGender(sex);
		}
		cmsUserExtMng.update(ext, entity);
		unifiedUserMng.update(id, password, email,telphone);
		return entity;
	}
	
	public CmsUser updateUserConllection(CmsUser user,Integer cid,Integer operate){
		Updater<CmsUser> updater = new Updater<CmsUser>(user);
		user = dao.updateByUpdater(updater);
		if (operate.equals(1)) {
			user.addToCollection(contentMng.findById(cid));
		}// 取消收藏
		else if (operate.equals(0)) {
			user.delFromCollection(contentMng.findById(cid));
		}
		return user;
	}

	public CmsUser deleteById(Integer id) {
		//清除流程轨迹
		List<CmsWorkflowEvent>events=workflowEventMng.getListByUserId(id);
		for(CmsWorkflowEvent event:events){
			workflowEventMng.deleteById(event.getId());
		}
		unifiedUserMng.deleteById(id);
		CmsUser bean = dao.deleteById(id);
		//删除收藏信息
		bean.clearCollection();
		return bean;
	}

	public CmsUser[] deleteByIds(Integer[] ids) {
		CmsUser[] beans = new CmsUser[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public boolean usernameNotExist(String username) {
		return dao.countByUsername(username) <= 0;
	}
	
	public boolean usernameNotExistInMember(String username){
		return dao.countMemberByUsername(username)<= 0;
	}

	public boolean emailNotExist(String email) {
		return dao.countByEmail(email) <= 0;
	}

	private CmsUserSiteMng cmsUserSiteMng;
	private CmsSiteMng cmsSiteMng;
	private ChannelMng channelMng;
	private CmsRoleMng cmsRoleMng;
	private CmsDepartmentMng cmsDepartmentMng;
	private CmsGroupMng cmsGroupMng;
	private UnifiedUserMng unifiedUserMng;
	private CmsUserExtMng cmsUserExtMng;
	private CmsUserDao dao;
	@Autowired
	private ContentMng contentMng;
	@Autowired
	private CmsWorkflowEventMng workflowEventMng;	
	@Autowired
	private CmsDictionaryMng cmsDictionaryMng;
	@Autowired
	private GrainDetailMng grainDetailMng;

	@Autowired
	public void setCmsUserSiteMng(CmsUserSiteMng cmsUserSiteMng) {
		this.cmsUserSiteMng = cmsUserSiteMng;
	}

	@Autowired
	public void setCmsSiteMng(CmsSiteMng cmsSiteMng) {
		this.cmsSiteMng = cmsSiteMng;
	}

	@Autowired
	public void setChannelMng(ChannelMng channelMng) {
		this.channelMng = channelMng;
	}

	@Autowired
	public void setCmsRoleMng(CmsRoleMng cmsRoleMng) {
		this.cmsRoleMng = cmsRoleMng;
	}
	
	@Autowired
	public void setCmsDepartmentMng(CmsDepartmentMng cmsDepartmentMng) {
		this.cmsDepartmentMng = cmsDepartmentMng;
	}

	@Autowired
	public void setUnifiedUserMng(UnifiedUserMng unifiedUserMng) {
		this.unifiedUserMng = unifiedUserMng;
	}

	@Autowired
	public void setCmsUserExtMng(CmsUserExtMng cmsUserExtMng) {
		this.cmsUserExtMng = cmsUserExtMng;
	}

	@Autowired
	public void setCmsGroupMng(CmsGroupMng cmsGroupMng) {
		this.cmsGroupMng = cmsGroupMng;
	}

	@Autowired
	public void setDao(CmsUserDao dao) {
		this.dao = dao;
	}
	

}