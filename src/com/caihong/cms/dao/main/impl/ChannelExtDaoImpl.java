package com.caihong.cms.dao.main.impl;

import org.springframework.stereotype.Repository;

import com.caihong.cms.dao.main.ChannelExtDao;
import com.caihong.cms.entity.main.ChannelExt;
import com.caihong.common.hibernate4.HibernateBaseDao;

@Repository
public class ChannelExtDaoImpl extends HibernateBaseDao<ChannelExt, Integer>
		implements ChannelExtDao {
	public ChannelExt save(ChannelExt bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	protected Class<ChannelExt> getEntityClass() {
		return ChannelExt.class;
	}
}