package com.caihong.cms.manager.main.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caihong.common.hibernate4.Updater;
import com.caihong.common.page.Pagination;
import com.caihong.cms.dao.main.SmsSendRecordDao;
import com.caihong.cms.entity.main.SmsSendRecord;
import com.caihong.cms.manager.main.SmsSendRecordMng;

@Service
@Transactional
public class SmsSendRecordMngImpl implements SmsSendRecordMng {
	@Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize,String... args) {
		Pagination page =null;
		if(args.length>0){
			page= dao.getPage(pageNo, pageSize,args);
		}else{
			page= dao.getPage(pageNo, pageSize);
		}
		return page;
	}

	@Transactional(readOnly = true)
	public SmsSendRecord findById(Integer id) {
		SmsSendRecord entity = dao.findById(id);
		return entity;
	}

	public SmsSendRecord save(SmsSendRecord bean) {
		bean.setCreateOn(new Date());
		bean.setSendStatus("1");
		dao.save(bean);
		return bean;
	}

	public SmsSendRecord update(SmsSendRecord bean) {
		Updater<SmsSendRecord> updater = new Updater<SmsSendRecord>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public SmsSendRecord deleteById(Integer id) {
		SmsSendRecord bean = dao.deleteById(id);
		return bean;
	}
	
	public SmsSendRecord[] deleteByIds(Integer[] ids) {
		SmsSendRecord[] beans = new SmsSendRecord[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private SmsSendRecordDao dao;

	@Autowired
	public void setDao(SmsSendRecordDao dao) {
		this.dao = dao;
	}
}