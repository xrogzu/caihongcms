package com.caihong.cms.entity.main.base;

import java.io.Serializable;

import com.caihong.core.entity.CmsUser;

public class BaseGrainDetail implements Serializable{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		// primary key
		private java.lang.Long id;

		// fields
		private java.util.Date time;
		private CmsUser user;
		private CmsUser fromUser;
		private java.lang.Integer grainCnt;
		private java.lang.Integer type;
		public java.lang.Long getId() {
			return id;
		}
		public void setId(java.lang.Long id) {
			this.id = id;
		}
		public java.util.Date getTime() {
			return time;
		}
		public void setTime(java.util.Date time) {
			this.time = time;
		}
		public CmsUser getUser() {
			return user;
		}
		public void setUser(CmsUser user) {
			this.user = user;
		}
		public CmsUser getFromUser() {
			return fromUser;
		}
		public void setFromUser(CmsUser fromUser) {
			this.fromUser = fromUser;
		}
		public java.lang.Integer getGrainCnt() {
			return grainCnt;
		}
		public void setGrainCnt(java.lang.Integer grainCnt) {
			this.grainCnt = grainCnt;
		}
		public java.lang.Integer getType() {
			return type;
		}
		public void setType(java.lang.Integer type) {
			this.type = type;
		}
}
