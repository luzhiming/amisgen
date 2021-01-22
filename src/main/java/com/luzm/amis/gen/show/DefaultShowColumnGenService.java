package com.luzm.amis.gen.show;


import com.luzm.amis.gen.bean.ColumnsItem;

import java.lang.reflect.Field;

public class DefaultShowColumnGenService implements ShowColumnGenService {
    private static DefaultShowColumnGenService instance = new DefaultShowColumnGenService();

    private DefaultShowColumnGenService() {
    }

    public static DefaultShowColumnGenService getInstance() {
        return instance;
    }

    @Override
    public ColumnsItem gen(Field field) {
        ColumnsItem columnsItem = new ColumnsItem();
        String name = field.getName();
        columnsItem.setLabel(name.toUpperCase());
        columnsItem.setName(name);
        columnsItem.setType("text");
        return columnsItem;
    }
}
