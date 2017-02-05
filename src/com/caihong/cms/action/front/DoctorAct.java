package com.caihong.cms.action.front;


import static com.caihong.cms.Constants.TPLDIR_SPECIAL;


import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caihong.cms.entity.assist.CmsComment;
import com.caihong.cms.manager.assist.CmsCommentMng;
import com.caihong.cms.ws.Topic;
import com.caihong.cms.ws.TopicHttpSender;
import com.caihong.core.entity.CmsSite;
import com.caihong.core.entity.CmsUser;
import com.caihong.core.manager.CmsUserMng;
import com.caihong.core.web.WebErrors;
import com.caihong.core.web.util.CmsUtils;
import com.caihong.core.web.util.FrontUtils;


@Controller
public class DoctorAct {
	private static final Logger log = LoggerFactory.getLogger(DoctorAct.class);
	
	public static final String DOCTOR_BLOG="tpl.content.doctor";
	public static final String DOCTOR_MESSAGE="tpl.doctor.message";
	@Autowired
	private CmsUserMng cmsUserMng;
	@Autowired
	private CmsCommentMng cmsCommentMng;
	@RequestMapping(value="/blog/{username}.jspx")
	public String viewDoctor( @PathVariable(value="username")String username,HttpServletRequest request,HttpServletResponse response,ModelMap model){
		WebErrors errors=WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		if(username==null){
			errors.addErrorCode("error.required","username");
			return FrontUtils.showError(request, response, model, errors);
		}else{
			CmsUser doctor = cmsUserMng.findByUsername(username);
		    if(doctor!=null){	 	   
			  		model.addAttribute("doctor", doctor);
			  		model.addAttribute("user", user);
			  		List<Topic> list=TopicHttpSender.getUserTopic(username,null, null);
			  		if(list.size()>0){
			  			model.addAttribute("topList", list);
			  		}
			  		List<CmsComment> commentList = cmsCommentMng.getListForTag(1, doctor.getId(),
							null,null, null, null, true, 0,5);
			  		if(commentList.size()>0){
			  			model.addAttribute("commentList", commentList);
			  		}
			  		return FrontUtils.getTplPath(request, site.getSolutionPath(),
			  				TPLDIR_SPECIAL, DOCTOR_BLOG);
		  	    
		    }else{
		    	errors.addErrorCode("error.beanNotFound","content");
		    	return FrontUtils.showError(request, response, model, errors);
		    }
		}
	}
	
	@RequestMapping(value="/blog/{id}/message.jspx")
	public String messageDoctor( @PathVariable(value="id")Integer id,HttpServletRequest request,HttpServletResponse response,ModelMap model){
		WebErrors errors=WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		if(id==null){
			errors.addErrorCode("error.required","id");
			return FrontUtils.showError(request, response, model, errors);
		}else{
			CmsUser doctor = cmsUserMng.findById(id);
		    if(doctor!=null){	 	   
			  		model.addAttribute("doctor", doctor);
			  		model.addAttribute("user", user);
			  		
			  		return FrontUtils.getTplPath(request, site.getSolutionPath(),
			  				TPLDIR_SPECIAL, DOCTOR_MESSAGE);
		  	    
		    }else{
		    	errors.addErrorCode("error.beanNotFound","content");
		    	return FrontUtils.showError(request, response, model, errors);
		    }
		}
	}
	
	
}
