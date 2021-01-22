package com.luzm.amis.gen.show;


import com.luzm.amis.gen.bean.ColumnsItem;

import java.lang.reflect.Field;

public class DateShowColumnGenService implements ShowColumnGenService {
    private static DateShowColumnGenService instance = new DateShowColumnGenService();

    private DateShowColumnGenService() {
    }

    public static DateShowColumnGenService getInstance() {
        return instance;
    }

    @Override
    public ColumnsItem gen(Field field) {
        ColumnsItem columnsItem = new ColumnsItem();
        String name = field.getName();
        columnsItem.setLabel(name.toUpperCase());
        columnsItem.setName(name);
        columnsItem.setType("datetime");
        columnsItem.setFormat("YYYY-MM-DD HH:mm:ss");
        return columnsItem;
    }
}
