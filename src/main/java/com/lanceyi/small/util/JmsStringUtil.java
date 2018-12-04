package com.lanceyi.small.util;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * Created by e311 on 2017/4/14.
 */
public class JmsStringUtil {

    public static  boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
    public static  boolean isEmpty(String str){
        return StringUtils.isEmpty(str) || StringUtils.isEmpty(StringUtils.trimAllWhitespace(str));
    }

    public static String pingParam(Map<String,String> mapData){
        Set<String> dataKeySet = mapData.keySet();
        dataKeySet.remove("signContent");
        String[] keyArray = dataKeySet.toArray(new String[0]);
        Arrays.sort(keyArray);
        // 拼接有序的参数名-值串
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : keyArray)
        {
            String value = mapData.get(key);
            if(JmsStringUtil.isEmpty(value)){
                continue;
            }
            stringBuilder.append(key).append(":").append(value).append(",");
        }
        String codes = stringBuilder.toString();
        codes = codes.substring(0,codes.length()-1);

        return codes;
    }



}
