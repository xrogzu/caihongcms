package com.caihong.cms.action.front;



import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.caihong.cms.entity.main.UserFollow;
import com.caihong.cms.manager.main.UserFollowMng;

import com.caihong.common.web.ResponseUtils;
import com.caihong.core.entity.CmsUser;
import com.caihong.core.manager.CmsUserMng;



@Controller
public class UserFollowAct {
	private static final Logger log = LoggerFactory.getLogger(UserFollowAct.class);
	

	@Autowired
	private UserFollowMng manager;
	@Autowired
	private CmsUserMng cmsUserMng;
	
	@RequestMapping(value="/follow")
	public void follow(Integer userid,Integer follow_userid,HttpServletResponse response){
		if(userid==null||follow_userid==null||follow_userid==userid){
			ResponseUtils.renderJson(response, "false");
		}else{
			 CmsUser user=cmsUserMng.findById(userid);
			 CmsUser followUser=cmsUserMng.findById(follow_userid);
			 if(user==null||followUser==null){
				 ResponseUtils.renderJson(response, "false");
			 }else{
				 if(manager.findByFollowUser(userid, follow_userid)==null){
					 UserFollow uf=new UserFollow();
					 uf.setUser(user);
					 uf.setFollowUser(followUser);
					 uf.setTime(new Date());
					 manager.save(uf);
					 cmsUserMng.updateFollowCnt(userid, 1);//关注数加1
					 cmsUserMng.updateFansCnt(follow_userid, 1);//粉丝数加1
					 ResponseUtils.renderJson(response, "true");
				 }else{
					 ResponseUtils.renderJson(response, "false");
				 }
			 }
		}
		
	}
	
	@RequestMapping(value="/unfollow")
	public void unFollow(Integer userid,Integer follow_userid,HttpServletResponse response){
		if(userid==null||follow_userid==null){
			ResponseUtils.renderJson(response, "false");
		}else{
			 UserFollow bean=manager.findByFollowUser(userid, follow_userid);
			 
			 if(bean==null){
				 ResponseUtils.renderJson(response, "false");
			 }else{				
				 manager.delete(bean);
				 cmsUserMng.updateFollowCnt(userid, -1);//关注数减1
				 cmsUserMng.updateFansCnt(follow_userid, -1);//粉丝数减1
				 ResponseUtils.renderJson(response, "true");
			 }
		}
		
	}
	
	
}
