package com.cy.util;

import com.cy.data.UString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by cy on 2017/6/30.
 */
public class UtilString {
    /**首字母大写*/
    public static String upperFirstLetter(String str){
        str =str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase())  ;
        return str;
    }

    public static String valueOf(String s){
        if (s==null) return "";
        return s;
    }

    public static String addTailIfNotHas(String original,String tail){
        if (UString.isEmpty(original)) return tail;
        if (UString.isEmpty(tail)) return original;
        return original.endsWith(tail) ? original : original + tail;
    }

    public static String removeTailIfHas(String original,String tail){
        if (UString.isEmpty(original)) return original;
        if (UString.isEmpty(tail)) return original;
        return original.endsWith(tail) ? original.substring(0,original.length()-tail.length()) : original;
    }

    public static String readResourcesFileContent(String resourcePathName) {
        //resource路径必须/开头
        resourcePathName = resourcePathName.startsWith("/") ? resourcePathName : "/" + resourcePathName;
        StringBuilder stringBuilder = new StringBuilder();
        InputStream is = UFile.class.getResourceAsStream(resourcePathName);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String s = "";
        try {
            while ((s = br.readLine()) != null) {
                stringBuilder.append(s).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = stringBuilder.toString();
        if (result.length() > 0) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }
}
