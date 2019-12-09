package com.yaoqun.classroom.util;

import java.lang.reflect.Field;


public class CommonUtils {
    /**列出domain所有get方法
     * @param object
     * @param name
     */
    public static void listGet(Class object,String name){
        Field[] fields = object.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            String type = fields[i].getType().getName();
            String realType = type.substring(type.lastIndexOf(".")+1);
            String value = fields[i].getName();
            String last = value.substring(0, 1).toUpperCase() + value.substring(1);
            if (last.toLowerCase().contains("serialversion")) {
                continue;
            }
            System.out.println(realType+" "+value+" = "+name+".get"+last+"();");
        }
        System.out.println("====================================");
    }

    /**列出domain所有set方法
     * @param object
     * @param name
     */
    public static void listSet(Class object,String name){
        Field[] fields = object.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            String value = fields[i].getName();
            String last = value.substring(0, 1).toUpperCase() + value.substring(1);
            if (last.toLowerCase().contains("serialversion")) {
                continue;
            }
            System.out.println(name+".set"+last+"();");
        }
        System.out.println("====================================");
    }
}
