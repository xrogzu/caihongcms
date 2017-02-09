package com.caihong.cms.action.admin.main;

import static com.caihong.common.page.SimplePage.cpn;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caihong.cms.entity.main.GrainBuyConfig;
import com.caihong.core.entity.CmsSite;
import com.caihong.cms.manager.main.GrainBuyConfigMng;
import com.caihong.core.web.util.CmsUtils;
import com.caihong.core.web.WebErrors;
import com.caihong.common.page.Pagination;
import com.caihong.common.web.CookieUtils;

@Controller
public class GrainBuyConfigAct {
	private static final Logger log = LoggerFactory.getLogger(GrainBuyConfigAct.class);

	@RequestMapping("/GrainBuyConfig/v_list.do")
	public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
		Pagination pagination = manager.getPage(cpn(pageNo), CookieUtils
				.getPageSize(request));
		model.addAttribute("pagination",pagination);
		model.addAttribute("pageNo",pagination.getPageNo());
		return "GrainBuyConfig/list";
	}

	@RequestMapping("/GrainBuyConfig/v_add.do")
	public String add(ModelMap model) {
		return "GrainBuyConfig/add";
	}

	@RequestMapping("/GrainBuyConfig/v_edit.do")
	public String edit(Integer id, Integer pageNo, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		model.addAttribute("grainBuyConfig", manager.findById(id));
		model.addAttribute("pageNo",pageNo);
		return "GrainBuyConfig/edit";
	}

	@RequestMapping("/GrainBuyConfig/o_save.do")
	public String save(GrainBuyConfig bean, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean.setTime(new Date());
		bean = manager.save(bean);
		log.info("save GrainBuyConfig id={}", bean.getId());
		return "redirect:v_list.do";
	}

	@RequestMapping("/GrainBuyConfig/o_update.do")
	public String update(GrainBuyConfig bean, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.update(bean);
		log.info("update GrainBuyConfig id={}.", bean.getId());
		return list(pageNo, request, model);
	}

	@RequestMapping("/GrainBuyConfig/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		GrainBuyConfig[] beans = manager.deleteByIds(ids);
		for (GrainBuyConfig bean : beans) {
			log.info("delete GrainBuyConfig id={}", bean.getId());
		}
		return list(pageNo, request, model);
	}

	private WebErrors validateSave(GrainBuyConfig bean, HttpServletRequest request) {
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
		GrainBuyConfig entity = manager.findById(id);
		if(errors.ifNotExist(entity, GrainBuyConfig.class, id)) {
			return true;
		}
		
		return false;
	}
	
	@Autowired
	private GrainBuyConfigMng manager;
}