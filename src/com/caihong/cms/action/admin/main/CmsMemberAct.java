package com.caihong.cms.action.admin.main;

import static com.caihong.common.page.SimplePage.cpn;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caihong.cms.entity.assist.CmsWebservice;
import com.caihong.cms.entity.main.GrainDetail;
import com.caihong.cms.manager.assist.CmsWebserviceMng;
import com.caihong.cms.manager.main.GrainDetailMng;
import com.caihong.common.page.Pagination;
import com.caihong.common.web.CookieUtils;
import com.caihong.common.web.GetGrainType;
import com.caihong.common.web.RequestUtils;
import com.caihong.common.web.ResponseUtils;
import com.caihong.core.entity.CmsConfig;
import com.caihong.core.entity.CmsConfigItem;
import com.caihong.core.entity.CmsDepartment;
import com.caihong.core.entity.CmsDictionary;
import com.caihong.core.entity.CmsGroup;
import com.caihong.core.entity.CmsSite;
import com.caihong.core.entity.CmsUser;
import com.caihong.core.entity.CmsUserExt;
import com.caihong.core.manager.CmsConfigItemMng;
import com.caihong.core.manager.CmsDepartmentMng;
import com.caihong.core.manager.CmsDictionaryMng;
import com.caihong.core.manager.CmsGroupMng;
import com.caihong.core.manager.CmsLogMng;
import com.caihong.core.manager.CmsUserMng;
import com.caihong.core.web.WebErrors;
import com.caihong.core.web.util.CmsUtils;

@Controller
public class CmsMemberAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsMemberAct.class);
	private static final String TYPE_MAJOR="专业";
	private static final String TYPE_NATION="国籍";
	private static final String TYPE_JOBLEVEL="级别";
	private static final String TYPE_JOBTITLE="职称";

	@RequiresPermissions("member:v_list")
	@RequestMapping("/member/v_list.do")
	public String list(String queryUsername, String queryEmail,
			Integer queryGroupId, Boolean queryDisabled, Integer pageNo,Integer nationId,Integer majorId,Integer jobTitleId,Integer jobLevelId,String idNo,
			HttpServletRequest request, ModelMap model) {
		Pagination pagination = manager.getPage(queryUsername, queryEmail,
				null, queryGroupId, queryDisabled, false, null, null,
				null,null,null,null,nationId,majorId,jobTitleId,jobLevelId,idNo,cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);

		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("queryEmail", queryEmail);
		model.addAttribute("queryGroupId", queryGroupId);
		model.addAttribute("queryDisabled", queryDisabled);
		model.addAttribute("groupList", cmsGroupMng.getList());
		return "member/list";
	}

	@RequiresPermissions("member:v_add")
	@RequestMapping("/member/v_add.do")
	public String add(ModelMap model,HttpServletRequest request) {
		CmsSite site=CmsUtils.getSite(request);
		List<CmsGroup> groupList = cmsGroupMng.getList();
		List<CmsDepartment> toplist=cmsDepartmentMng.getList(null, false);
		List<CmsDepartment> departmentList=CmsDepartment.getListForSelect(toplist);
		List<CmsDictionary> jobLevelList=cmsDictionaryMng.getList(TYPE_JOBLEVEL);
		List<CmsDictionary> majorList=cmsDictionaryMng.getList(TYPE_MAJOR);
		List<CmsDictionary> nationList=cmsDictionaryMng.getList(TYPE_NATION);
		List<CmsDictionary> jobTitleList=cmsDictionaryMng.getList(TYPE_JOBTITLE);
		List<CmsConfigItem>registerItems=cmsConfigItemMng.getList(site.getConfig().getId(), CmsConfigItem.CATEGORY_REGISTER);
		model.addAttribute("registerItems", registerItems);
		model.addAttribute("groupList", groupList);
		model.addAttribute("jobLevelList", jobLevelList);
		model.addAttribute("majorList", majorList);
		model.addAttribute("nationList", nationList);
		model.addAttribute("jobTitleList", jobTitleList);		
		
		model.addAttribute("departmentList", departmentList);
		return "member/add";
	}

	@RequiresPermissions("member:v_edit")
	@RequestMapping("/member/v_edit.do")
	public String edit(Integer id, Integer queryGroupId, Boolean queryDisabled,
			HttpServletRequest request, ModelMap model) {
		String queryUsername = RequestUtils.getQueryParam(request,
				"queryUsername");
		String queryEmail = RequestUtils.getQueryParam(request, "queryEmail");
		CmsSite site=CmsUtils.getSite(request);
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		List<CmsDepartment> toplist=cmsDepartmentMng.getList(null, false);
		List<CmsDepartment> departmentList=CmsDepartment.getListForSelect(toplist);
		List<CmsDictionary> jobLevelList=cmsDictionaryMng.getList(TYPE_JOBLEVEL);
		List<CmsDictionary> majorList=cmsDictionaryMng.getList(TYPE_MAJOR);
		List<CmsDictionary> nationList=cmsDictionaryMng.getList(TYPE_NATION);
		List<CmsDictionary> jobTitleList=cmsDictionaryMng.getList(TYPE_JOBTITLE);
		CmsUser user=manager.findById(id);
		if(user.getDepartment()==null){
			user.setDepartment(new CmsDepartment());
		}
		if(user.getJobLevel()==null){
			user.setJobLevel(new CmsDictionary());
		}
		if(user.getJobTitle()==null){
			user.setJobTitle(new CmsDictionary());
		}
		if(user.getMajor()==null){
			user.setMajor(new CmsDictionary());
		}
		if(user.getNation()==null){
			user.setNation(new CmsDictionary());
		}
		
		List<CmsGroup> groupList = cmsGroupMng.getList();
		List<CmsConfigItem>registerItems=cmsConfigItemMng.getList(site.getConfig().getId(), CmsConfigItem.CATEGORY_REGISTER);
		List<String>userAttrValues=new ArrayList<String>();
		for(CmsConfigItem item:registerItems){
			userAttrValues.add(user.getAttr().get(item.getField()));
		}
		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("queryEmail", queryEmail);
		model.addAttribute("queryGroupId", queryGroupId);
		model.addAttribute("queryDisabled", queryDisabled);
		model.addAttribute("departmentList", departmentList);
		model.addAttribute("groupList", groupList);
		model.addAttribute("jobLevelList", jobLevelList);
		model.addAttribute("majorList", majorList);
		model.addAttribute("nationList", nationList);
		model.addAttribute("jobTitleList", jobTitleList);	
		model.addAttribute("cmsMember", user);
		model.addAttribute("registerItems", registerItems);
		model.addAttribute("userAttrValues", userAttrValues);
		return "member/edit";
	}

	@RequiresPermissions("member:o_save")
	@RequestMapping("/member/o_save.do")
	public String save(CmsUser bean, CmsUserExt ext, String username,String telphone,
			String email, String password, Integer groupId,Integer departmentId,Integer grain,Integer nationId,Integer majorId,Integer jobTitleId,Integer jobLevelId,String idNo,Integer fansCnt,Integer followCnt,Double price,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsSite site = CmsUtils.getSite(request);
		CmsConfig config=site.getConfig();
		String ip = RequestUtils.getIpAddr(request);
		Map<String,String>attrs=RequestUtils.getRequestMap(request, "attr_");
		bean = manager.registerMember(username, email, password,telphone, ip, groupId,departmentId,grain,false, nationId, majorId, jobTitleId,jobLevelId, idNo, fansCnt, followCnt,price,ext,attrs,config.getMemberConfig().getRegisterSendGrain());
		
		cmsWebserviceMng.callWebService("false",username, password, email, telphone,groupId+"",config.getMemberConfig().getRegisterSendGrain(),ext,CmsWebservice.SERVICE_TYPE_ADD_USER);
		log.info("save CmsMember id={}", bean.getId());
		cmsLogMng.operating(request, "cmsMember.log.save", "id=" + bean.getId()
				+ ";username=" + bean.getUsername());
		return "redirect:v_list.do";
	}

	@RequiresPermissions("member:o_update")
	@RequestMapping("/member/o_update.do")
	public String update(Integer id, String email,String telphone, String password,
			Boolean disabled, CmsUserExt ext, Integer groupId,Integer departmentId,Integer grain,Integer nationId,Integer majorId,Integer jobTitleId,Integer jobLevelId,String idNo,Integer fansCnt,Integer followCnt,Double price,
			String queryUsername, String queryEmail, Integer queryGroupId,
			Boolean queryDisabled, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateUpdate(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Map<String,String>attrs=RequestUtils.getRequestMap(request, "attr_");
		CmsUser bean = manager.updateMember(id, email, telphone,password, disabled, nationId, majorId, jobTitleId,jobLevelId, idNo,ext,groupId,departmentId,grain, fansCnt, followCnt,price,attrs);
		cmsWebserviceMng.callWebService("false",bean.getUsername(), password, email,telphone, groupId+"",null,ext,CmsWebservice.SERVICE_TYPE_UPDATE_USER);
		log.info("update CmsMember id={}.", bean.getId());
		cmsLogMng.operating(request, "cmsMember.log.update", "id="
				+ bean.getId() + ";username=" + bean.getUsername());
		return list(queryUsername, queryEmail, queryGroupId, queryDisabled,
				pageNo, nationId,majorId,jobTitleId,jobLevelId,idNo,request, model);
	}

	@RequiresPermissions("member:o_delete")
	@RequestMapping("/member/o_delete.do")
	public String delete(Integer[] ids, Integer queryGroupId,
			Boolean queryDisabled, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		String queryUsername = RequestUtils.getQueryParam(request,
				"queryUsername");
		String queryEmail = RequestUtils.getQueryParam(request, "queryEmail");
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsUser[] beans = manager.deleteByIds(ids);
		for (CmsUser bean : beans) {
			Map<String,String>paramsValues=new HashMap<String, String>();
			paramsValues.put("username", bean.getUsername());
			paramsValues.put("admin", "false");
			cmsWebserviceMng.callWebService(CmsWebservice.SERVICE_TYPE_DELETE_USER, paramsValues);
			log.info("delete CmsMember id={}", bean.getId());
			cmsLogMng.operating(request, "cmsMember.log.delete", "id="
					+ bean.getId() + ";username=" + bean.getUsername());
		}
		return list(queryUsername, queryEmail, queryGroupId, queryDisabled,
				pageNo,null,null,null,null,null, request, model);
	}
	
	@RequiresPermissions("member:o_check")
	@RequestMapping("/member/o_check.do")
	public String check(Integer[] ids, Integer queryGroupId,
			Boolean queryDisabled, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		String queryUsername = RequestUtils.getQueryParam(request,
				"queryUsername");
		String queryEmail = RequestUtils.getQueryParam(request, "queryEmail");
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		for(Integer id:ids){
			CmsUser user=manager.findById(id);
			user.setDisabled(false);
			manager.updateUser(user);
			log.info("check CmsMember id={}", user.getId());
			cmsLogMng.operating(request, "cmsMember.log.delete", "id="
					+ user.getId() + ";username=" + user.getUsername());
		}
		return list(queryUsername, queryEmail, queryGroupId, queryDisabled,
				pageNo, null,null,null,null,null,request, model);
	}

	@RequiresPermissions("member:v_check_username")
	@RequestMapping(value = "/member/v_check_username.do")
	public void checkUsername(HttpServletRequest request, HttpServletResponse response) {
		String username=RequestUtils.getQueryParam(request,"username");
		String pass;
		if (StringUtils.isBlank(username)) {
			pass = "false";
		} else {
			pass = manager.usernameNotExist(username) ? "true" : "false";
		}
		ResponseUtils.renderJson(response, pass);
	}
	
	

	private WebErrors validateSave(CmsUser bean, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		return errors;
	}

	private WebErrors validateEdit(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(id, errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(id, errors)) {
			return errors;
		}
		// TODO 验证是否为管理员，管理员不允许修改。
		return errors;
	}

	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(!errors.ifEmpty(ids, "ids")){
			for (Integer id : ids) {
				vldExist(id, errors);
			}
		}
		return errors;
	}

	private boolean vldExist(Integer id, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsUser entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsUser.class, id)) {
			return true;
		}
		return false;
	}
	
	@Autowired
	private CmsDepartmentMng cmsDepartmentMng;
	@Autowired
	private CmsDictionaryMng cmsDictionaryMng;
	@Autowired
	private CmsGroupMng cmsGroupMng;
	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private CmsUserMng manager;
	@Autowired
	private CmsConfigItemMng cmsConfigItemMng;
	@Autowired
	private CmsWebserviceMng cmsWebserviceMng;
}