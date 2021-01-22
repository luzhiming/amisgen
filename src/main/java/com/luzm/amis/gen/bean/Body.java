package com.luzm.amis.gen.bean;

import java.util.List;

public class Body{
	private List<ControlsItem> controls;
	private String api;
	private String type;

	public void setControls(List<ControlsItem> controls){
		this.controls = controls;
	}

	public List<ControlsItem> getControls(){
		return controls;
	}

	public void setApi(String api){
		this.api = api;
	}

	public String getApi(){
		return api;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}
}