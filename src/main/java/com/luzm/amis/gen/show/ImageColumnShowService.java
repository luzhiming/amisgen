package com.luzm.amis.gen.show;


import com.luzm.amis.gen.bean.ColumnsItem;

import java.lang.reflect.Field;

public class ImageColumnShowService implements ShowColumnGenService {
    private static ImageColumnShowService instance = new ImageColumnShowService();

    private ImageColumnShowService() {
    }

    public static ImageColumnShowService getInstance() {
        return instance;
    }

    @Override
    public ColumnsItem gen(Field field) {
        ColumnsItem columnsItem = new ColumnsItem();
        columnsItem.setType("image");
        String name = field.getName();
        columnsItem.setLabel(name.toUpperCase());
        columnsItem.setSrc("${" + name + "}");
        return null;
    }
}
