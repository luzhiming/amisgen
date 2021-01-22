package com.luzm.amis.gen.show;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ShowColumnGenServiceManager {
    private static Map<Class, ShowColumnGenService> byClazz = new HashMap<>();
    private static Map<String, ShowColumnGenService> byName = new HashMap<>();
    private static ShowColumnGenServiceManager instance = new ShowColumnGenServiceManager();

    private ShowColumnGenServiceManager() {
        initClazz();
        initName();
    }

    private void initClazz() {
        byClazz.put(Date.class, DefaultShowColumnGenService.getInstance());
    }

    private void initName() {
        byName.put("img", ImageColumnShowService.getInstance());
        byName.put("image", ImageColumnShowService.getInstance());
    }

    public static ShowColumnGenServiceManager getInstance() {
        return instance;
    }

    public ShowColumnGenService getShowColumnService(Field field) {
        Class<? extends Field> clazz = field.getClass();
        ShowColumnGenService showColumnGenService = byClazz.get(clazz);
        if (showColumnGenService != null) {
            return showColumnGenService;
        }
        String name = field.getName();
        Set<Map.Entry<String, ShowColumnGenService>> showColumnGenServiceEntriesByName = byName.entrySet();
        for (Map.Entry<String, ShowColumnGenService> stringShowColumnGenServiceEntry : showColumnGenServiceEntriesByName) {
            String key = stringShowColumnGenServiceEntry.getKey();
            if (name.toLowerCase().endsWith(key)) {
                return stringShowColumnGenServiceEntry.getValue();
            }
        }
        // 默认返回
        return DefaultShowColumnGenService.getInstance();
    }
}
