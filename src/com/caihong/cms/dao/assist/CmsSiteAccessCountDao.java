package com.caihong.cms.dao.assist;

import java.util.Date;
import java.util.List;

import com.caihong.cms.entity.assist.CmsSiteAccessCount;
import com.caihong.common.hibernate4.Updater;

/**
 * @author Tom
 */
public interface CmsSiteAccessCountDao {

	public List<Object[]> statisticVisitorCountByDate(Integer siteId,Date begin,Date end);
	
	public List<Object[]> statisticVisitorCountByYear(Integer siteId,Integer year);

	public CmsSiteAccessCount save(CmsSiteAccessCount count);

	public CmsSiteAccessCount updateByUpdater(Updater<CmsSiteAccessCount> updater);

}
