package com.caihong.core.entity;

import com.caihong.core.entity.base.BaseCmsWorkflowNode;



public class CmsWorkflowNode extends BaseCmsWorkflowNode {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsWorkflowNode () {
		super();
	}

	/**
	 * Constructor for required fields
	 */
	public CmsWorkflowNode (
		com.caihong.core.entity.CmsRole role,
		boolean countersign) {

		super (
			role,
			countersign);
	}

/*[CONSTRUCTOR MARKER END]*/


}