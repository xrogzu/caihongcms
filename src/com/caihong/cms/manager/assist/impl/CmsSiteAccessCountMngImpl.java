package com.caihong.cms.manager.assist.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caihong.cms.dao.assist.CmsSiteAccessCountDao;
import com.caihong.cms.dao.assist.CmsSiteAccessDao;
import com.caihong.cms.entity.assist.CmsSiteAccessCount;
import com.caihong.cms.manager.assist.CmsSiteAccessCountMng;
import com.caihong.core.manager.CmsSiteMng;

/**
 * @author Tom
 */
@Service
@Transactional
public class CmsSiteAccessCountMngImpl implements CmsSiteAccessCountMng {

	public List<Object[]> statisticVisitorCountByDate(Integer siteId,Date begin, Date end) {
		return dao.statisticVisitorCountByDate(siteId, begin, end);
	}

	public List<Object[]> statisticVisitorCountByYear(Integer siteId,Integer year) {
		return dao.statisticVisitorCountByYear(siteId, year);
	}

	public CmsSiteAccessCount save(CmsSiteAccessCount count) {
		return dao.save(count);
	}

	public void statisticCount(Date date, Integer siteId) {
		List<Object[]> pageCounts = cmsAccessDao.statisticByPageCount(date,siteId);
		for (Object[] pageCount : pageCounts) {
			CmsSiteAccessCount bean = new CmsSiteAccessCount();
			bean.setSite(siteMng.findById(siteId));
			bean.setStatisticDate(date);
			Long visitors = (Long) pageCount[0];
			bean.setVisitors(visitors.intValue());
			bean.setPageCount((Integer) pageCount[1]);
			save(bean);
		}
	}

	@Autowired
	private CmsSiteAccessCountDao dao;
	@Autowired
	private CmsSiteAccessDao cmsAccessDao;
	@Autowired
	private CmsSiteMng siteMng;

}
