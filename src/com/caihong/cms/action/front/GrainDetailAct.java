package com.caihong.cms.action.front;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caihong.common.web.ResponseUtils;
import com.caihong.core.entity.CmsUser;
import com.caihong.core.manager.CmsUserMng;
import com.caihong.common.web.GetGrainType;

@Controller
public class GrainDetailAct {
	@Autowired
	private CmsUserMng cmsUserMng;
	/**
	 * 打赏
	 * @param userid
	 * @param follow_userid
	 * @param response
	 */
	@RequestMapping(value="/reward")
	public void reward(Integer userid,Integer fromuserid,Integer grainCnt,HttpServletResponse response){
		if(grainCnt==null||grainCnt<=0||userid==null||fromuserid==null||fromuserid==userid){
			ResponseUtils.renderJson(response, "false");
		}else{
			 CmsUser user=cmsUserMng.findById(userid);
			 CmsUser fromUser=cmsUserMng.findById(fromuserid);
			 if(user==null||fromUser==null){
				 ResponseUtils.renderJson(response, "false");
			 }else{
				 cmsUserMng.updateGrainCnt(user, fromUser, grainCnt, GetGrainType.SEND);	
				 cmsUserMng.updateGrainCnt(fromUser, null, -grainCnt, GetGrainType.SEND);
				 ResponseUtils.renderJson(response, "true");
			 }
		}
		
	}
		
}
