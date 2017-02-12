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

import com.caihong.cms.entity.main.Order;
import com.caihong.core.entity.CmsSite;
import com.caihong.cms.manager.main.OrderMng;
import com.caihong.core.web.util.CmsUtils;
import com.caihong.core.web.WebErrors;
import com.caihong.common.page.Pagination;
import com.caihong.common.web.CookieUtils;

@Controller
public class OrderAct {
	private static final Logger log = LoggerFactory.getLogger(OrderAct.class);

	@RequiresPermissions("Order:v_list")
	@RequestMapping("/Order/v_list.do")
	public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
		Pagination pagination = manager.getPage(cpn(pageNo), CookieUtils
				.getPageSize(request));
		model.addAttribute("pagination",pagination);
		model.addAttribute("pageNo",pagination.getPageNo());
		return "Order/list";
	}

	@RequiresPermissions("Order:v_add")
	@RequestMapping("/Order/v_add.do")
	public String add(ModelMap model) {
		return "Order/add";
	}

	@RequiresPermissions("Order:v_edit")
	@RequestMapping("/Order/v_edit.do")
	public String edit(Integer id, Integer pageNo, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		model.addAttribute("order", manager.findById(id));
		model.addAttribute("pageNo",pageNo);
		return "Order/edit";
	}

	@RequiresPermissions("Order:o_save")
	@RequestMapping("/Order/o_save.do")
	public String save(Order bean, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.save(bean);
		log.info("save Order id={}", bean.getId());
		return "redirect:v_list.do";
	}

	@RequiresPermissions("Order:o_update")
	@RequestMapping("/Order/o_update.do")
	public String update(Order bean, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.update(bean);
		log.info("update Order id={}.", bean.getId());
		return list(pageNo, request, model);
	}

	@RequiresPermissions("Order:o_delete")
	@RequestMapping("/Order/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Order[] beans = manager.deleteByIds(ids);
		for (Order bean : beans) {
			log.info("delete Order id={}", bean.getId());
		}
		return list(pageNo, request, model);
	}

	private WebErrors validateSave(Order bean, HttpServletRequest request) {
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
		Order entity = manager.findById(id);
		if(errors.ifNotExist(entity, Order.class, id)) {
			return true;
		}
		
		return false;
	}
	
	@Autowired
	private OrderMng manager;
}