package com.caihong.core.manager;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import com.caihong.common.email.EmailSender;
import com.caihong.common.email.MessageTemplate;
import com.caihong.common.page.Pagination;
import com.caihong.core.entity.CmsSite;
import com.caihong.core.entity.CmsUser;
import com.caihong.core.entity.CmsUserExt;

public interface CmsUserMng {
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

	public CmsUser registerMember(String username, String email,String telphone,
			String password, String ip, Integer groupId,Integer departmentId,Integer grain, boolean disabled,Integer nation,Integer major,Integer jobTitle,Integer jobLevel,String idNo,Integer fansCnt,Integer followCnt,CmsUserExt userExt,Map<String,String>attr);
	
	public CmsUser registerMember(String username, String email,String telphone,
			String password, String ip, Integer groupId, Integer departmentId,boolean disabled,Integer nation,Integer major,Integer jobTitle,Integer jobLevel,String idNo,Integer fansCnt,Integer followCnt,CmsUserExt userExt,Map<String,String>attr, Boolean activation , EmailSender sender, MessageTemplate msgTpl)throws UnsupportedEncodingException, MessagingException ;

	public void updateLoginInfo(Integer userId, String ip,Date loginTime,String sessionId);
	
	public void updateFansCnt(Integer userId, int cnt);
	
	public void updateFollowCnt(Integer userId, int cnt);

	public void updateUploadSize(Integer userId, Integer size);
	
	public void updateUser(CmsUser user);

	public void updatePwdEmail(Integer id, String password, String email,String telphone);

	public boolean isPasswordValid(Integer id, String password);

	public CmsUser saveAdmin(String username, String email,String telphone, String password,
			String ip, boolean viewOnly, boolean selfAdmin, int rank,
			Integer groupId, Integer departmentId,Integer[] roleIds, Integer[] channelIds,
			Integer[] siteIds, Byte[] steps, Boolean[] allChannels,Boolean[] allControlChannels,
			CmsUserExt userExt);

	public CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
			Integer groupId,Integer departmentId,Integer[] roleIds, Integer[] channelIds,
			Integer[] siteIds, Byte[] steps, Boolean[] allChannels,Boolean[] allControlChannels);

	public CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
			Integer groupId,Integer departmentId, Integer[] roleIds, Integer[] channelIds,
			Integer siteId, Byte step, Boolean allChannel,Boolean allControlChannel);

	public CmsUser updateMember(Integer id, String email,String telphone, String password,
			Boolean isDisabled,Integer nation,Integer major,Integer jobTitle,Integer jobLevel, String idNo,CmsUserExt ext, Integer groupId,Integer departmentId,Integer grain,Integer fansCnt,Integer followCnt,Map<String,String>attr);
	
	public CmsUser updateMember(Integer id, String email, String telphone,String password,Integer groupId,Integer departmentId,String realname,String mobile,Boolean sex,Integer nation,Integer major,Integer jobTitle,Integer jobLevel,String idNo,Integer fansCnt,Integer followCnt);
	
	public CmsUser updateUserConllection(CmsUser user,Integer cid,Integer operate);

	public void addSiteToUser(CmsUser user, CmsSite site, Byte checkStep);

	public CmsUser deleteById(Integer id);

	public CmsUser[] deleteByIds(Integer[] ids);

	public boolean usernameNotExist(String username);
	
	public boolean usernameNotExistInMember(String username);

	public boolean emailNotExist(String email);

}