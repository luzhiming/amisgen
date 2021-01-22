package com.luzm.amis.gen.bean;

import lombok.Data;

import java.util.List;

@Data
public class ColumnsItem{
	private List<ButtonsItem> buttons;
	private String label;
	private String type;
	private String name;
	private String src;
	private String format;
}