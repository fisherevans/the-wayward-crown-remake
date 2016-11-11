package com.fisherevans.twc.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class StringUtil {
    public static String join(Collection<String> strings, String glue) {
        if(strings.isEmpty()) {
            return "";
        }
        Iterator<String> stringsItr = strings.iterator();
        String base = stringsItr.next();
        while(stringsItr.hasNext()) {
            base += glue + stringsItr.next();
        }
        return base;
    }

    public static String join(String[] strings, String glue) {
        return join(Arrays.asList(strings), glue);
    }
}
