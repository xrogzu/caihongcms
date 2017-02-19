package com.caihong.cms.entity.main;

import java.io.Serializable;

public class ReserveAttachment  implements Serializable {
	public ReserveAttachment(){
		initialize();
	}
	public ReserveAttachment (
			java.lang.String path,
			java.lang.String name) {

			this.setPath(path);
			this.setName(name);
			initialize();
		}
	protected void initialize () {}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private java.lang.String path;
	private java.lang.String name;
	private java.lang.String filename;
	
	public java.lang.String getPath() {
		return path;
	}
	public void setPath(java.lang.String path) {
		this.path = path;
	}
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public java.lang.String getFilename() {
		return filename;
	}
	public void setFilename(java.lang.String filename) {
		this.filename = filename;
	}
}
