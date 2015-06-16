package com.application.restfulclient;

import java.io.Serializable;

public abstract class DocumentAbstract implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public abstract String toJSONString();
	

}
