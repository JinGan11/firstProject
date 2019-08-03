package com.ucar.combination.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:公用方法
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-08-03 09:22:58
 */
public class CommonMethod {
    /**
     * list<XXXDto>转list<map>
     * @param listObj
     * @return
     * @author qingyu.lan@ucarinc.com
     * @throws IllegalAccessException
     */
    public static List<Map<String, Object>> objectToMap(List<Object> listObj) throws IllegalAccessException {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < listObj.size(); i++) {
            Object obj = listObj.get(i);
            Map<String, Object> map = new HashMap<>();
            Class<?> clazz = obj.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(obj);
                map.put(fieldName, value);
            }
            list.add(map);
        }
        return list;
    }
}
