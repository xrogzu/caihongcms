package com.caihong.cms.action.admin.main;

import static com.caihong.common.page.SimplePage.cpn;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caihong.cms.entity.main.GrainDetail;
import com.caihong.cms.manager.main.GrainDetailMng;
import com.caihong.common.page.Pagination;
import com.caihong.common.web.CookieUtils;
import com.caihong.common.web.GetGrainType;

@Controller
public class GrainDetailAct {
	private static final Logger log = LoggerFactory.getLogger(GrainDetailAct.class);

	@RequestMapping("/GrainDetail/v_list.do")
	public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
		Pagination pagination = manager.getPage(cpn(pageNo), CookieUtils
				.getPageSize(request));
		model.addAttribute("pagination",pagination);
		model.addAttribute("pageNo",pagination.getPageNo());
		return "GrainDetail/list";
	}

	@RequestMapping("/GrainDetail/v_add.do")
	public String add(ModelMap model) {
		return "GrainDetail/add";
	}

	@RequestMapping("/GrainDetail/v_edit.do")
	public String edit(Integer id, Integer pageNo, HttpServletRequest request, ModelMap model) {
		model.addAttribute("grainDetail", manager.findById(id));
		model.addAttribute("pageNo",pageNo);
		return "GrainDetail/edit";
	}

	@RequestMapping("/GrainDetail/o_save.do")
	public String save(GrainDetail bean, HttpServletRequest request, ModelMap model) {
		bean.setTime(new java.util.Date());
		bean = manager.save(bean);
		log.info("save GrainDetail id={}", bean.getId());
		return "redirect:v_list.do";
	}

	@RequestMapping("/GrainDetail/o_update.do")
	public String update(GrainDetail bean, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		
		bean = manager.update(bean);
		log.info("update GrainDetail id={}.", bean.getId());
		return list(pageNo, request, model);
	}

	@RequestMapping("/GrainDetail/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		
		GrainDetail[] beans = manager.deleteByIds(ids);
		for (GrainDetail bean : beans) {
			log.info("delete GrainDetail id={}", bean.getId());
		}
		return list(pageNo, request, model);
	}

	

	

	
	@Autowired
	private GrainDetailMng manager;
}