package com.luzm.amis.gen.bean;

import lombok.Data;

import java.util.List;

@Data
public class BodyItem {
    private Filter filter;
    private boolean syncLocation;
    private List<ColumnsItem> columns;
    private String api;
    private String type;
    private String actionType;
    private Dialog dialog;
    private String level;
    private String className;
    private String label;

}