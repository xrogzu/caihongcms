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
	
	public static final String CONTENT_REWARD="tpl.content.doctor";
	@Autowired
	private CmsUserMng cmsUserMng;
	
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
			  		model.addAttribute("topList", list);
			  		return FrontUtils.getTplPath(request, site.getSolutionPath(),
			  				TPLDIR_SPECIAL, CONTENT_REWARD);
		  	    
		    }else{
		    	errors.addErrorCode("error.beanNotFound","content");
		    	return FrontUtils.showError(request, response, model, errors);
		    }
		}
	}
	
	
	
	
}
