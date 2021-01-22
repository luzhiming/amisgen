package com.luzm.amis.gen.show;

import java.util.Date;

public enum ControlsItemTypeEnum {
    DATE(Date.class, "text", "DateTime"),
    TEXT(String.class, "text", "text"),
    ;
    private final Class typeClazz;
    private final String showType;
    private final String inputType;

    ControlsItemTypeEnum(Class typeClazz, String showType, String inputType) {
        this.typeClazz = typeClazz;
        this.showType = showType;
        this.inputType = inputType;
    }

    public Class getTypeClazz() {
        return typeClazz;
    }

    public String getShowType() {
        return showType;
    }

    public String getInputType() {
        return inputType;
    }

    public static ControlsItemTypeEnum getByClazz(Class clazz) {
        ControlsItemTypeEnum[] values = ControlsItemTypeEnum.values();
        for (ControlsItemTypeEnum value : values) {
            if (value.getTypeClazz() == clazz) {
                return value;
            }
        }
        return TEXT;
    }
}
