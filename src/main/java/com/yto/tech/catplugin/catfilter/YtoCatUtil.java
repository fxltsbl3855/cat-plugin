package com.yto.tech.catplugin.catfilter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class YtoCatUtil {
    public static void fill(String exclusions, List<String> exclusionList) {
        if(exclusions == null || "".equals(exclusions.trim()) || "null".equals(exclusions.trim())){
            return;
        }
        StringTokenizer st = new StringTokenizer(exclusions,",");
        while(st.hasMoreTokens()){
            String e = st.nextToken();
            if(e == null || "".equals(e.trim()) || "null".equals(e.trim())){
                continue;
            }
            exclusionList.add(e.trim().toString().toLowerCase().replaceAll("\\*",""));
        }
    }

    public static boolean match(String url, List<String> exclusionList) {
        if(exclusionList == null || exclusionList.size() ==0){
            return false;
        }
        for(String temp : exclusionList){
            if(url.indexOf(temp)!=-1){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        List<String> exclusionList = new ArrayList<String>();
        fill("*.js    ,*.gif,*.jpg,*.png,*.css,*.ico  ",exclusionList);

        System.out.println( exclusionList);

        System.out.println(match("ok.css",exclusionList));

    }
}
