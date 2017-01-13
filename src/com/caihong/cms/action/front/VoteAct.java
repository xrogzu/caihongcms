package com.caihong.cms.action.front;

import static com.caihong.cms.Constants.TPLDIR_SPECIAL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.caihong.cms.entity.assist.CmsVoteItem;
import com.caihong.cms.entity.assist.CmsVoteSubTopic;
import com.caihong.cms.entity.assist.CmsVoteTopic;
import com.caihong.cms.manager.assist.CmsVoteRecordMng;
import com.caihong.cms.manager.assist.CmsVoteReplyMng;
import com.caihong.cms.manager.assist.CmsVoteSubTopicMng;
import com.caihong.cms.manager.assist.CmsVoteTopicMng;
import com.caihong.common.page.Pagination;
import com.caihong.common.web.CookieUtils;
import com.caihong.common.web.RequestUtils;
import com.caihong.core.entity.CmsSite;
import com.caihong.core.entity.CmsUser;
import com.caihong.core.web.WebErrors;
import com.caihong.core.web.util.CmsUtils;
import com.caihong.core.web.util.FrontUtils;


import static com.caihong.common.page.SimplePage.cpn;

@Controller
public class VoteAct extends AbstractVote{
	private static final Logger log = LoggerFactory.getLogger(VoteAct.class);
	/**
	 * 投票cookie前缀
	 */
	public static final String VOTE_COOKIE_PREFIX = "_vote_cookie_";

	public static final String VOTE_INPUT = "tpl.voteInput";
	public static final String VOTE_RESULT = "tpl.voteResult";
	public static final String VOTE_REPLY_RESULT = "tpl.voteReplyResult";

	@RequestMapping(value = "/vote_result.jspx", method = RequestMethod.GET)
	public String result(Integer voteId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsVoteTopic vote=null;
		if(voteId!=null){
			vote = cmsVoteTopicMng.findById(voteId);
		}
		if(vote!=null){
			model.addAttribute("vote", vote);
			FrontUtils.frontData(request, model, site);
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_SPECIAL, VOTE_RESULT);
		}else{
			WebErrors errors=WebErrors.create(request);
			errors.addError("error.vote.novotefind");
			return FrontUtils.showError(request, response, model, errors);
		}
	}
	
	@RequestMapping(value = "/vote_reply_view.jspx", method = RequestMethod.GET)
	public String reply_view(Integer subId,Integer pageNo, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsVoteSubTopic subTopic=null;
		if(subId!=null){
			subTopic=cmsVoteSubTopicMng.findById(subId);
		}
		if(subTopic!=null){
			Pagination pagination=cmsVoteReplyMng.getPage(subId, cpn(pageNo), CookieUtils.getPageSize(request));
			model.addAttribute("subTopic", subTopic);
			model.addAttribute("pagination", pagination);
			FrontUtils.frontData(request, model, site);
			FrontUtils.frontPageData(request, model);
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_SPECIAL, VOTE_REPLY_RESULT);
		}else{
			WebErrors errors=WebErrors.create(request);
			errors.addError("error.vote.novotesubfind");
			return FrontUtils.showError(request, response, model, errors);
		}
	}

	@RequestMapping(value = "/vote.jspx", method = RequestMethod.GET)
	public String input(Integer voteId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsVoteTopic vote =null;
		if(voteId!=null){
			vote= cmsVoteTopicMng.findById(voteId);
		}
		if(vote!=null){
			model.addAttribute("vote", vote);
			FrontUtils.frontData(request, model, site);
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_SPECIAL, VOTE_INPUT);
		}else{
			WebErrors errors=WebErrors.create(request);
			errors.addError("error.vote.novotefind");
			return FrontUtils.showError(request, response, model, errors);
		}
	}
	

	@RequestMapping(value = "/vote.jspx", method = RequestMethod.POST)
	public String submit(Integer voteId,Integer[]subIds,String[]reply, 
			HttpServletRequest request, HttpServletResponse response,ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		CmsVoteTopic vote=vote(user, voteId, subIds, reply, request, response, model);
		if(vote!=null){
			log.info("vote CmsVote id={}, name={}", vote.getId(), vote
					.getTitle());
		}
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, VOTE_RESULT);
	}

	@Autowired
	private CmsVoteTopicMng cmsVoteTopicMng;
	@Autowired
	private CmsVoteSubTopicMng cmsVoteSubTopicMng;
	@Autowired
	private CmsVoteReplyMng cmsVoteReplyMng;
	@Autowired
	private CmsVoteRecordMng cmsVoteRecordMng;
}
