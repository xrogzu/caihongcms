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

import com.caihong.cms.entity.main.UserSchedule;
import com.caihong.cms.manager.main.UserScheduleMng;
import com.caihong.core.web.WebErrors;
import com.caihong.common.page.Pagination;
import com.caihong.common.web.CookieUtils;

@Controller
public class UserScheduleAct {
	private static final Logger log = LoggerFactory.getLogger(UserScheduleAct.class);

	@RequiresPermissions("userschedule:v_list")
	@RequestMapping("/userschedule/v_list.do")
	public String list(Integer pageNo,String username, HttpServletRequest request, ModelMap model) {
		Pagination pagination = manager.getPage(username,cpn(pageNo), CookieUtils
				.getPageSize(request));
		model.addAttribute("pagination",pagination);
		model.addAttribute("pageNo",pagination.getPageNo());
		return "userschedule/list";
	}

	@RequiresPermissions("userschedule:v_add")
	@RequestMapping("/userschedule/v_add.do")
	public String add(ModelMap model) {
		return "userschedule/add";
	}

	@RequiresPermissions("userschedule:v_edit")
	@RequestMapping("/userschedule/v_edit.do")
	public String edit(Integer id, Integer pageNo, HttpServletRequest request, ModelMap model) {
		
		model.addAttribute("userSchedule", manager.findById(id));
		model.addAttribute("pageNo",pageNo);
		return "userschedule/edit";
	}

	@RequiresPermissions("userschedule:o_save")
	@RequestMapping("/userschedule/o_save.do")
	public String save(UserSchedule bean, HttpServletRequest request, ModelMap model) {
		
		bean = manager.save(bean);
		log.info("save UserSchedule id={}", bean.getId());
		return "redirect:v_list.do";
	}

	@RequiresPermissions("userschedule:o_update")
	@RequestMapping("/userschedule/o_update.do")
	public String update(UserSchedule bean, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		
		bean = manager.update(bean);
		log.info("update UserSchedule id={}.", bean.getId());
		return list(pageNo, null,request, model);
	}

	@RequiresPermissions("userschedule:o_delete")
	@RequestMapping("/userschedule/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		
		UserSchedule[] beans = manager.deleteByIds(ids);
		for (UserSchedule bean : beans) {
			log.info("delete UserSchedule id={}", bean.getId());
		}
		return list(pageNo, null,request, model);
	}

	
	private boolean vldExist(Integer id, Integer siteId, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		UserSchedule entity = manager.findById(id);
		if(errors.ifNotExist(entity, UserSchedule.class, id)) {
			return true;
		}
		
		return false;
	}
	
	@Autowired
	private UserScheduleMng manager;
}