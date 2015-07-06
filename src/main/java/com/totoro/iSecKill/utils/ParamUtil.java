package com.totoro.iSecKill.utils;

import com.totoro.iSecKill.bean.Device;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by Chen on 2015/7/1.
 */
public class ParamUtil {

    public static String generateMac(Map<String, String> paramMap, String paramString) {
        try {
            return encryptWithMD5(reorderParam(paramMap, paramString) + "0102030405060708");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String reorderParam(Map<String, String> paramMap, String paramString) {
        TreeMap<String, String> localTreeMap = new TreeMap<String, String>(paramMap);
        if ((paramString == null) || ("".equals(paramString)))
            paramString = "asc";
        if ("desc".equals(paramString)) {
            localTreeMap = new TreeMap<String, String>(Collections.reverseOrder());
            localTreeMap.putAll(paramMap);
        }
        String str = "";
        Iterator<Map.Entry<String, String>> localIterator = localTreeMap.entrySet().iterator();
        while (localIterator.hasNext()) {
            Map.Entry<String, String> localEntry = localIterator.next();
            str = str + localEntry.getKey() + "=" + localEntry.getValue() + "&";
        }
        if ((!isEmpty(str)) && (str.lastIndexOf("&") == -1 + str.length()))
            str = str.substring(0, str.lastIndexOf("&"));
        return str;
    }

    private static String encryptWithMD5(String paramString) throws NoSuchAlgorithmException {
        MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
        localMessageDigest.update(paramString.getBytes());
        byte[] arrayOfByte = localMessageDigest.digest();
        StringBuilder localStringBuilder = new StringBuilder();
        for (int i = 0; i < arrayOfByte.length; ++i) {
            localStringBuilder.append(Character.forDigit(0xF & arrayOfByte[i] >>> 4, 16));
            localStringBuilder.append(Character.forDigit(0xF & arrayOfByte[i], 16));
        }
        return localStringBuilder.toString().toUpperCase();
    }

    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }

    public static String getUrl(Device device, String reqUrl) {
        return reqUrl + device.toString() + "_requuid=" +UUID.randomUUID().toString().toUpperCase();
    }
}
