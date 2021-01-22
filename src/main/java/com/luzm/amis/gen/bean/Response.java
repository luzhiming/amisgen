package com.luzm.amis.gen.bean;

import lombok.Data;

import java.util.List;

@Data
public class Response{
	private String type;
	private String title;
	private List<BodyItem> body;
}