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

import com.caihong.cms.entity.main.Schedule;
import com.caihong.cms.manager.main.ScheduleMng;
import com.caihong.core.web.util.CmsUtils;
import com.caihong.core.web.WebErrors;
import com.caihong.common.page.Pagination;
import com.caihong.common.web.CookieUtils;

@Controller
public class ScheduleAct {
	private static final Logger log = LoggerFactory.getLogger(ScheduleAct.class);

	@RequiresPermissions("schedule:v_list")
	@RequestMapping("/schedule/v_list.do")
	public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
		Pagination pagination = manager.getPage(cpn(pageNo), CookieUtils
				.getPageSize(request));
		model.addAttribute("pagination",pagination);
		model.addAttribute("pageNo",pagination.getPageNo());
		return "schedule/list";
	}

	@RequiresPermissions("schedule:v_add")
	@RequestMapping("/schedule/v_add.do")
	public String add(ModelMap model) {
		return "schedule/add";
	}

	@RequiresPermissions("schedule:v_edit")
	@RequestMapping("/schedule/v_edit.do")
	public String edit(Integer id, Integer pageNo, HttpServletRequest request, ModelMap model) {
		
		model.addAttribute("schedule", manager.findById(id));
		model.addAttribute("pageNo",pageNo);
		return "schedule/edit";
	}

	@RequiresPermissions("schedule:o_save")
	@RequestMapping("/schedule/o_save.do")
	public String save(Schedule bean, HttpServletRequest request, ModelMap model) {
		
		bean = manager.save(bean);
		log.info("save Schedule id={}", bean.getId());
		return "redirect:v_list.do";
	}

	@RequiresPermissions("schedule:o_update")
	@RequestMapping("/schedule/o_update.do")
	public String update(Schedule bean, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		
		bean = manager.update(bean);
		log.info("update Schedule id={}.", bean.getId());
		return list(pageNo, request, model);
	}

	@RequiresPermissions("schedule:o_delete")
	@RequestMapping("/schedule/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		
		Schedule[] beans = manager.deleteByIds(ids);
		for (Schedule bean : beans) {
			log.info("delete Schedule id={}", bean.getId());
		}
		return list(pageNo, request, model);
	}
	private boolean vldExist(Integer id, Integer siteId, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		Schedule entity = manager.findById(id);
		if(errors.ifNotExist(entity, Schedule.class, id)) {
			return true;
		}
		
		return false;
	}
	
	@Autowired
	private ScheduleMng manager;
}