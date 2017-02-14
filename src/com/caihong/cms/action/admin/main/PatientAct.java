package com.caihong.cms.action.admin.main;

import static com.caihong.common.page.SimplePage.cpn;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caihong.cms.entity.main.Patient;
import com.caihong.core.entity.CmsSite;
import com.caihong.cms.manager.main.PatientMng;
import com.caihong.core.web.util.CmsUtils;
import com.caihong.core.web.WebErrors;
import com.caihong.common.page.Pagination;
import com.caihong.common.web.CookieUtils;

@Controller
public class PatientAct {
	private static final Logger log = LoggerFactory.getLogger(PatientAct.class);

	@RequiresPermissions("Patient:v_list")
	@RequestMapping("/Patient/v_list.do")
	public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
		Pagination pagination = manager.getPage(cpn(pageNo), CookieUtils
				.getPageSize(request));
		model.addAttribute("pagination",pagination);
		model.addAttribute("pageNo",pagination.getPageNo());
		return "Patient/list";
	}

	@RequiresPermissions("Patient:v_add")
	@RequestMapping("/Patient/v_add.do")
	public String add(ModelMap model) {
		return "Patient/add";
	}

	@RequiresPermissions("Patient:v_edit")
	@RequestMapping("/Patient/v_edit.do")
	public String edit(Integer id, Integer pageNo, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		model.addAttribute("patient", manager.findById(id));
		model.addAttribute("pageNo",pageNo);
		return "Patient/edit";
	}

	@RequiresPermissions("Patient:o_save")
	@RequestMapping("/Patient/o_save.do")
	public String save(Patient bean, HttpServletRequest request, ModelMap model) {
		
		bean = manager.save(bean);
		log.info("save Patient id={}", bean.getId());
		return "redirect:v_list.do";
	}

	@RequiresPermissions("Patient:o_update")
	@RequestMapping("/Patient/o_update.do")
	public String update(Patient bean, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		
		bean = manager.update(bean);
		log.info("update Patient id={}.", bean.getId());
		return list(pageNo, request, model);
	}

	@RequiresPermissions("Patient:o_delete")
	@RequestMapping("/Patient/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Patient[] beans = manager.deleteByIds(ids);
		for (Patient bean : beans) {
			log.info("delete Patient id={}", bean.getId());
		}
		return list(pageNo, request, model);
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
		Patient entity = manager.findById(id);
		if(errors.ifNotExist(entity, Patient.class, id)) {
			return true;
		}
		
		return false;
	}
	
	@Autowired
	private PatientMng manager;
}