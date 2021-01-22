package com.luzm.amis.gen.show;


import com.luzm.amis.gen.bean.ColumnsItem;

import java.lang.reflect.Field;

public interface ShowColumnGenService {
    ColumnsItem gen(Field field);
}
