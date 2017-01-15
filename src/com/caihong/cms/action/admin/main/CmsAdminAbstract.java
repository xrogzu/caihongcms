package com.caihong.cms.action.admin.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import com.caihong.cms.entity.main.Channel;
import com.caihong.cms.manager.assist.CmsWebserviceMng;
import com.caihong.cms.manager.main.ChannelMng;
import com.caihong.common.web.RequestUtils;
import com.caihong.common.web.ResponseUtils;
import com.caihong.core.entity.CmsDepartment;
import com.caihong.core.entity.CmsUser;
import com.caihong.core.manager.CmsDepartmentMng;
import com.caihong.core.manager.CmsGroupMng;
import com.caihong.core.manager.CmsLogMng;
import com.caihong.core.manager.CmsRoleMng;
import com.caihong.core.manager.CmsSiteMng;
import com.caihong.core.manager.CmsUserMng;
import com.caihong.core.security.CmsAuthorizingRealm;

public class CmsAdminAbstract {
	protected String channelsAddJson(Integer siteId,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		List<Channel> channelList = channelMng.getTopList(siteId, false);
		model.addAttribute("channelList", channelList);
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return "admin/channels_add";
	}

	protected String channelsEditJson(Integer userId, Integer siteId,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		List<Channel> channelList = channelMng.getTopList(siteId, false);
		CmsUser user = manager.findById(userId);
		model.addAttribute("channelList", channelList);
		model.addAttribute("channelIds", user.getChannelIds(siteId));
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return "admin/channels_edit";
	}
	
	public String departAdd(String root, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		boolean isRoot;
		// jquery treeview的根请求为root=source
		if (StringUtils.isBlank(root) || "source".equals(root)) {
			isRoot = true;
		} else {
			isRoot = false;
		}
		model.addAttribute("isRoot", isRoot);
		List<CmsDepartment> departList;
		if(isRoot){
			departList= cmsDepartmentMng.getList(null,false);
		}else{
			departList = cmsDepartmentMng.getList(Integer.parseInt(root),false);
		}
		model.addAttribute("list", departList);
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return "admin/depart_tree";
	}

	protected void checkUserJson(HttpServletRequest request,HttpServletResponse response) {
		String username=RequestUtils.getQueryParam(request,"username");
		String pass;
		if (StringUtils.isBlank(username)) {
			pass = "false";
		} else {
			pass = manager.usernameNotExist(username) ? "true" : "false";
		}
		ResponseUtils.renderJson(response, pass);
	}

	protected void checkEmailJson(String email, HttpServletResponse response) {
		String pass;
		if (StringUtils.isBlank(email)) {
			pass = "false";
		} else {
			pass = manager.emailNotExist(email) ? "true" : "false";
		}
		ResponseUtils.renderJson(response, pass);
	}

	@Autowired
	protected CmsSiteMng cmsSiteMng;
	@Autowired
	protected ChannelMng channelMng;
	@Autowired
	protected CmsRoleMng cmsRoleMng;
	@Autowired
	protected CmsGroupMng cmsGroupMng;
	@Autowired
	protected CmsLogMng cmsLogMng;
	@Autowired
	protected CmsUserMng manager;
	@Autowired
	protected CmsDepartmentMng cmsDepartmentMng;
	@Autowired
	protected CmsWebserviceMng cmsWebserviceMng;
	@Autowired
	protected CmsAuthorizingRealm authorizingRealm;
}