package com.caihong.cms.dao.main.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.caihong.common.hibernate4.Finder;
import com.caihong.common.hibernate4.HibernateBaseDao;
import com.caihong.common.page.Pagination;
import com.caihong.cms.dao.main.ReserveDao;
import com.caihong.cms.entity.main.Reserve;

@Repository
public class ReserveDaoImpl extends HibernateBaseDao<Reserve, Integer> implements ReserveDao {
	public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}
	public Pagination search(Integer userid,Integer doctorid,Date startDate,Date endDate,Boolean paystatus,Integer status,int pageNo, int pageSize,String patientName,String doctorname){
		String hql=" select bean from Reserve bean where 1=1 ";
		Finder f=Finder.create(hql);
		if(userid!=null){
			f.append(" and bean.reserveUser.id=:userid").setParam("userid", userid);
		}
		if(doctorid!=null){
			f.append(" and bean.doctorUser.id=:doctorid").setParam("doctorid", doctorid);
		}
		if(startDate!=null){
			f.append(" and bean.time>=:startDate").setParam("startDate", startDate);
		}
		if(endDate!=null){
			f.append(" and bean.time<=:endDate").setParam("endDate", endDate);
		}
		if(paystatus!=null){
			f.append(" and bean.payStatus=:paystatus").setParam("paystatus", paystatus);
		}
		if(status!=null){
			f.append(" and bean.status=:status").setParam("status", status);
		}
		if(StringUtils.isNotBlank(doctorname)){
			f.append(" and bean.doctorUser.realname like :doctorname").setParam("doctorname", "%"+doctorname+"%");
		}
		if(StringUtils.isNotBlank(patientName)){
			f.append(" and bean.patient.name like :patientName").setParam("patientName", "%"+patientName+"%");
		}
		f.setCacheable(true);
		return find(f, pageNo, pageSize);
	}
	public Reserve findById(Integer id) {
		Reserve entity = get(id);
		return entity;
	}

	public Reserve save(Reserve bean) {
		getSession().save(bean);
		return bean;
	}

	public Reserve deleteById(Integer id) {
		Reserve entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<Reserve> getEntityClass() {
		return Reserve.class;
	}
}