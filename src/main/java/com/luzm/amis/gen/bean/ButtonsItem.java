package com.luzm.amis.gen.bean;

import lombok.Data;

@Data
public class ButtonsItem{
	private String actionType;
	private Drawer drawer;
	private String label;
	private String type;
	private String level;
	private String confirmText;
	private String api;
}
