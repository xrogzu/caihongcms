package com.caihong.cms.manager.assist;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.caihong.cms.entity.assist.CmsVoteItem;
import com.caihong.cms.entity.assist.CmsVoteSubTopic;
import com.caihong.cms.entity.assist.CmsVoteTopic;

public interface CmsVoteSubTopicMng {

	public CmsVoteSubTopic findById(Integer id);
	
	public List<CmsVoteSubTopic> findByVoteTopic(Integer voteTopicId);
	
	public CmsVoteTopic save(CmsVoteTopic bean, Set<CmsVoteSubTopic> subTopics);

	public CmsVoteSubTopic save(CmsVoteSubTopic bean, List<CmsVoteItem> items);

	public CmsVoteSubTopic update(CmsVoteSubTopic bean, Collection<CmsVoteItem> items);
	
	public CmsVoteTopic update(CmsVoteTopic bean, Collection<CmsVoteSubTopic> subTopics);
	
	public Collection<CmsVoteSubTopic> update(Collection<CmsVoteSubTopic> subTopics,CmsVoteTopic topic);

	public CmsVoteSubTopic deleteById(Integer id);

	public CmsVoteSubTopic[] deleteByIds(Integer[] ids);

}