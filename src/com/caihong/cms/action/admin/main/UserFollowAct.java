package com.caihong.cms.action.admin.main;

import static com.caihong.common.page.SimplePage.cpn;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caihong.cms.entity.main.UserFollow;
import com.caihong.core.entity.CmsSite;
import com.caihong.core.manager.CmsUserMng;
import com.caihong.cms.manager.main.UserFollowMng;
import com.caihong.core.web.util.CmsUtils;
import com.caihong.core.web.WebErrors;
import com.caihong.common.page.Pagination;
import com.caihong.common.web.CookieUtils;

@Controller
public class UserFollowAct {
	private static final Logger log = LoggerFactory.getLogger(UserFollowAct.class);

	@RequestMapping("/UserFollow/v_list.do")
	public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
		Pagination pagination = manager.getPage(cpn(pageNo), CookieUtils
				.getPageSize(request));
		model.addAttribute("pagination",pagination);
		model.addAttribute("pageNo",pagination.getPageNo());
		return "UserFollow/list";
	}

	@RequestMapping("/UserFollow/v_add.do")
	public String add(ModelMap model) {
		return "UserFollow/add";
	}

	@RequestMapping("/UserFollow/v_edit.do")
	public String edit(Integer id, Integer pageNo, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		model.addAttribute("userFollow", manager.findById(id));
		model.addAttribute("pageNo",pageNo);
		return "UserFollow/edit";
	}

	@RequestMapping("/UserFollow/o_save.do")
	public String save(UserFollow bean, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.save(bean);
		cmsUserMng.updateFollowCnt(bean.getUser().getId(), 1);//关注数减1
		cmsUserMng.updateFansCnt(bean.getFollowUser().getId(), 1);//粉丝数减1
		log.info("save UserFollow id={}", bean.getId());
		return "redirect:v_list.do";
	}

	@RequestMapping("/UserFollow/o_update.do")
	public String update(UserFollow bean, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.update(bean);
		log.info("update UserFollow id={}.", bean.getId());
		return list(pageNo, request, model);
	}

	@RequestMapping("/UserFollow/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		UserFollow[] beans = manager.deleteByIds(ids);
		for (UserFollow bean : beans) {
			cmsUserMng.updateFollowCnt(bean.getUser().getId(), -1);//关注数减1
			 cmsUserMng.updateFansCnt(bean.getFollowUser().getId(), -1);//粉丝数减1
			log.info("delete UserFollow id={}", bean.getId());
		}
		return list(pageNo, request, model);
	}

	private WebErrors validateSave(UserFollow bean, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		
		return errors;
	}
	
	private WebErrors validateEdit(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getId(), errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getId(), errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (errors.ifEmpty(ids, "ids")) {
			return errors;
		}
		for (Integer id : ids) {
			vldExist(id, site.getId(), errors);
		}
		return errors;
	}

	private boolean vldExist(Integer id, Integer siteId, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		UserFollow entity = manager.findById(id);
		if(errors.ifNotExist(entity, UserFollow.class, id)) {
			return true;
		}
		
		return false;
	}
	@Autowired
	private CmsUserMng cmsUserMng;
	@Autowired
	private UserFollowMng manager;
}