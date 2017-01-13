package com.caihong.cms.dao.main.impl;

import org.springframework.stereotype.Repository;

import com.caihong.cms.dao.main.ContentExtDao;
import com.caihong.cms.entity.main.ContentExt;
import com.caihong.common.hibernate4.HibernateBaseDao;

@Repository
public class ContentExtDaoImpl extends HibernateBaseDao<ContentExt, Integer>
		implements ContentExtDao {
	public ContentExt findById(Integer id) {
		ContentExt entity = get(id);
		return entity;
	}

	public ContentExt save(ContentExt bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	protected Class<ContentExt> getEntityClass() {
		return ContentExt.class;
	}
}