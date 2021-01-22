package com.luzm.amis.gen.bean;

import lombok.Data;

import java.util.List;

@Data
public class Filter{
	private List<ControlsItem> controls;
	private String title;
}