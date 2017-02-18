package com.caihong.cms.action.admin.main;

import static com.caihong.common.page.SimplePage.cpn;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.caihong.core.entity.CmsDictionary;
import com.caihong.core.entity.CmsUser;
import com.caihong.core.manager.CmsDictionaryMng;
import com.caihong.core.manager.CmsUserMng;
import com.caihong.core.web.WebErrors;
import com.caihong.common.page.Pagination;
import com.caihong.common.web.CookieUtils;

@Controller
public class UserScheduleAct {
	private static final String TYPE_MAJOR="专业";
	private static final String TYPE_NATION="国籍";
	private static final String TYPE_JOBLEVEL="级别";
	private static final String TYPE_JOBTITLE="职称";
	private static final Logger log = LoggerFactory.getLogger(UserScheduleAct.class);
	@Autowired
	private CmsUserMng cmsUserMng;
	@Autowired
	private CmsDictionaryMng cmsDictionaryMng;
	@RequiresPermissions("userschedule:v_list")
	@RequestMapping("/userschedule/v_list.do")
	public String list(String queryUsername, String queryEmail,
			 Boolean queryDisabled,Integer nationId,Integer majorId,Integer jobTitleId,Integer jobLevelId,String idNo, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		Pagination pagination = cmsUserMng.getPage(queryUsername, queryEmail,
				null, 2, queryDisabled, false, null, null,
				null,null,null,null,nationId,majorId,jobTitleId,jobLevelId,idNo,cpn(pageNo),
				CookieUtils.getPageSize(request));
		List<CmsDictionary> jobLevelList=cmsDictionaryMng.getList(TYPE_JOBLEVEL);
		List<CmsDictionary> majorList=cmsDictionaryMng.getList(TYPE_MAJOR);
		List<CmsDictionary> nationList=cmsDictionaryMng.getList(TYPE_NATION);
		List<CmsDictionary> jobTitleList=cmsDictionaryMng.getList(TYPE_JOBTITLE);
		model.addAttribute("pagination", pagination);
		model.addAttribute("jobLevelList", jobLevelList);
		model.addAttribute("majorList", majorList);
		model.addAttribute("nationList", nationList);
		model.addAttribute("jobTitleList", jobTitleList);	
		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("queryEmail", queryEmail);
		
		model.addAttribute("nationId", nationId);
		model.addAttribute("majorId", majorId);	
		model.addAttribute("jobTitleId", jobTitleId);
		model.addAttribute("jobLevelId", jobLevelId);	
		
		model.addAttribute("queryDisabled", queryDisabled);
		return "userschedule/list";
	}



	@RequiresPermissions("userschedule:v_edit")
	@RequestMapping("/userschedule/v_edit.do")
	public String edit(Integer id, Integer pageNo, HttpServletRequest request, ModelMap model) {
		CmsUser user=cmsUserMng.findById(id);
		String mon="0",tue="0",wed="0",thur="0",fri="0",sat="0",sun="0";
		if(user.getUserSchedule()!=null){
			String zuozhentimes=user.getUserSchedule().getZuozhenTimes();
			if(zuozhentimes!=null){
				String arr[]=zuozhentimes.split(",");
				if(arr.length==7){
					mon=arr[0];tue=arr[1];wed=arr[2];thur=arr[3];fri=arr[4];sat=arr[5];sun=arr[6];
					
				}
			}
		}
		model.addAttribute("mon", mon);
		model.addAttribute("tue", tue);
		model.addAttribute("wed", wed);
		model.addAttribute("thur", thur);
		model.addAttribute("fri", fri);
		model.addAttribute("sat", sat);
		model.addAttribute("sun", sun);
		model.addAttribute("cmsMember", user);
		model.addAttribute("pageNo",pageNo);
		return "userschedule/edit";
	}



	@RequiresPermissions("userschedule:o_update")
	@RequestMapping("/userschedule/o_update.do")
	public String update(UserSchedule bean, Integer pageNo,Integer id,int mon,int tue,int wed,int thur,int fri,int sat,int sun, HttpServletRequest request,
			ModelMap model) {
		CmsUser user=cmsUserMng.findById(id);
		String zuozhenTimes=mon+","+tue+","+wed+","+thur+","+fri+","+sat+","+sun;
		bean.setZuozhenTimes(zuozhenTimes);
		bean.setUser(user);
		bean.setId(id);
		if(user.getUserSchedule()!=null){
			bean = manager.update(bean);
			
		}else{
			bean.setCreateTime(new Date());
			bean = manager.save(bean);			
		}	
		 Set<UserSchedule> set=new HashSet<UserSchedule>();
		 set.add(bean);
		 user.setUserScheduleSet(set);
		 //cmsUserMng.updateUser(user);
		log.info("update UserSchedule id={}.", bean.getId());
		return list(null,null,null,null,null,null,null,null,pageNo,request, model);
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