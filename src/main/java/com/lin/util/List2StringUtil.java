package com.lin.util;

import java.util.List;

public class List2StringUtil {
	// 把list转换为string，用,分割  
    public static String listToString(List<String> stringList) {  
        if (stringList == null) {  
            return null;  
        }  
        StringBuilder result = new StringBuilder();  
        boolean flag = false;  
        for (String string : stringList) {  
            if (flag) {  
                result.append(",");  
            } else {  
                flag = true;  
            }  
            result.append(string);  
        }  
        return result.toString();  
    }  

}
