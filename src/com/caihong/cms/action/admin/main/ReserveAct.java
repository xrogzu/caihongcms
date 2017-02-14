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

import com.caihong.cms.entity.main.Reserve;
import com.caihong.core.entity.CmsSite;
import com.caihong.cms.manager.main.ReserveMng;
import com.caihong.core.web.util.CmsUtils;
import com.caihong.core.web.WebErrors;
import com.caihong.common.page.Pagination;
import com.caihong.common.web.CookieUtils;

@Controller
public class ReserveAct {
	private static final Logger log = LoggerFactory.getLogger(ReserveAct.class);

	@RequiresPermissions("Reserve:v_list")
	@RequestMapping("/Reserve/v_list.do")
	public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
		Pagination pagination = manager.getPage(cpn(pageNo), CookieUtils
				.getPageSize(request));
		model.addAttribute("pagination",pagination);
		model.addAttribute("pageNo",pagination.getPageNo());
		return "Reserve/list";
	}

	@RequiresPermissions("Reserve:v_add")
	@RequestMapping("/Reserve/v_add.do")
	public String add(ModelMap model) {
		return "Reserve/add";
	}

	@RequiresPermissions("Reserve:v_edit")
	@RequestMapping("/Reserve/v_edit.do")
	public String edit(Integer id, Integer pageNo, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		model.addAttribute("reserve", manager.findById(id));
		model.addAttribute("pageNo",pageNo);
		return "Reserve/edit";
	}

	@RequiresPermissions("Reserve:o_save")
	@RequestMapping("/Reserve/o_save.do")
	public String save(Reserve bean, HttpServletRequest request, ModelMap model) {
		
		bean = manager.save(bean);
		log.info("save Reserve id={}", bean.getId());
		return "redirect:v_list.do";
	}

	@RequiresPermissions("Reserve:o_update")
	@RequestMapping("/Reserve/o_update.do")
	public String update(Reserve bean, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		
		bean = manager.update(bean);
		log.info("update Reserve id={}.", bean.getId());
		return list(pageNo, request, model);
	}

	@RequiresPermissions("Reserve:o_delete")
	@RequestMapping("/Reserve/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Reserve[] beans = manager.deleteByIds(ids);
		for (Reserve bean : beans) {
			log.info("delete Reserve id={}", bean.getId());
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
		Reserve entity = manager.findById(id);
		if(errors.ifNotExist(entity, Reserve.class, id)) {
			return true;
		}
		
		return false;
	}
	
	@Autowired
	private ReserveMng manager;
}