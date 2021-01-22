package com.luzm.amis.gen.bean;

import lombok.Data;

@Data
public class ControlsItem {
    private String name;
    private String label;
    private String validations;
    private String type;
    private boolean required;

}
