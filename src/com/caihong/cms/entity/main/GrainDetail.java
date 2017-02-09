package com.caihong.cms.entity.main;

import com.caihong.cms.entity.main.base.BaseGrainDetail;
import com.caihong.common.web.GetGrainType;

public class GrainDetail extends BaseGrainDetail{
	 /* 
	 */
	private static final long serialVersionUID = 1L;
	
	public GrainDetail(){
		super();
	}
	public GetGrainType getGrainType(){		
		return GetGrainType.getGetGrainTypeValue(this.getType());
	}
}
