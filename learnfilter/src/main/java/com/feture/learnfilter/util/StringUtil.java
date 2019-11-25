package com.feture.learnfilter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class StringUtil {
    private static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    public static String createSortedLinkString(Object source) {
        Iterator<Field> fields = Arrays.stream(source.getClass().getDeclaredFields()).filter(field -> !"sign".equalsIgnoreCase(field.getName())).sorted(Comparator.comparing(Field::getName)).iterator();

        StringBuffer sb = new StringBuffer();
        fields.forEachRemaining(field -> {
            field.setAccessible(true);
            try {
                sb.append(StringUtil.toUpperCaseFirstOne(field.getName()) + "=" + field.get(source) + "&");
                System.out.println("name====" + field.getName() + ",value====" + field.get(source));
            } catch (Exception e) {
                logger.error("createSortedLinkString error :{}", e);
            }
        });

        String target = sb.toString();
        if (target.endsWith("&")) {
            return target.substring(0, target.length() - 1);
        }

        return target;
    }
}
