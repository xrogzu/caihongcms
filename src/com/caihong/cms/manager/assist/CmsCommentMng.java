package com.caihong.cms.manager.assist;

import java.util.List;

import com.caihong.cms.entity.assist.CmsComment;
import com.caihong.cms.entity.assist.CmsCommentExt;
import com.caihong.common.page.Pagination;
import com.caihong.core.entity.CmsUser;

public interface CmsCommentMng {
	public Pagination getPage(Integer siteId, Integer contentId,
			Integer greaterThen, Boolean checked, Boolean recommend,
			boolean desc, int pageNo, int pageSize);

	public Pagination getPageForTag(Integer siteId, Integer contentId,
			Integer greaterThen, Boolean checked, Boolean recommend,
			boolean desc, int pageNo, int pageSize);
	
	/**
	 * 
	 * @param siteId
	 * @param contentId
	 * @param toUserId 写评论的用户
	 * @param fromUserId 投稿的信息接收到的相关评论
	 * @param greaterThen
	 * @param checked
	 * @param recommend
	 * @param desc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getPageForMember(Integer siteId, Integer contentId,Integer toUserId,Integer fromUserId,
			Integer greaterThen, Boolean checked, Boolean recommend,
			boolean desc, int pageNo, int pageSize);
	
	public List<CmsComment> getListForMember(Integer siteId, Integer contentId,
			Integer toUserId,Integer fromUserId,Integer greaterThen,
			Boolean checked, Boolean recommend,
			boolean desc, Integer first, Integer count);
	/**
	 * 
	 * @param siteId
	 * @param userId 发表信息用户id
	 * @param commentUserId 评论用户id
	 * @param ip  评论来访ip
	 * @return
	 */
	public List<CmsComment> getListForDel(Integer siteId, Integer userId,Integer commentUserId,String ip);

	public List<CmsComment> getListForTag(Integer siteId, Integer contentId,
			Integer parentId,Integer greaterThen, Boolean checked, Boolean recommend,
			boolean desc, Integer first,int count);

	public CmsComment findById(Integer id);

	public CmsComment comment(Integer score,String text, String ip, Integer contentId,
			Integer siteId, Integer userId, boolean checked, boolean recommend,Integer parentId);

	public CmsComment update(CmsComment bean, CmsCommentExt ext);

	public int deleteByContentId(Integer contentId);

	public CmsComment deleteById(Integer id);

	public CmsComment[] deleteByIds(Integer[] ids);

	public void ups(Integer id);

	public void downs(Integer id);

	public CmsComment[] checkByIds(Integer[] ids, CmsUser user, boolean checked);
}