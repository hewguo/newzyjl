package com.zyjl.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @ClassName DES
 * @Description TODO
 * @Author hewguo
 * @Date 2020-11-11 12:33
 * @Version 1.0
 **/
public class DES {
    public static String encryptDES(String encryptString, String encryptKey) throws Exception {
        SecretKeySpec key = new SecretKeySpec(getKey(encryptKey), "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(1, key);
        return bytesToHexString(cipher.doFinal(encryptString.getBytes()));
    }

    public static final String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        for (byte b : bArray) {
            String sTemp = Integer.toHexString(b & 255);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] getKey(String keyRule) {
        byte[] keyByte = keyRule.getBytes();
        byte[] byteTemp = new byte[8];
        int i = 0;
        while (i < byteTemp.length && i < keyByte.length) {
            byteTemp[i] = keyByte[i];
            i++;
        }
        return new SecretKeySpec(byteTemp, "DES").getEncoded();
    }

    public static String decryptDES(byte[] decryptString, String decryptKey) throws Exception {
        SecretKeySpec key = new SecretKeySpec(getKey(decryptKey), "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(2, key);
        return new String(cipher.doFinal(decryptString));
    }

    public static byte[] hexStringToByte(String hex) {
        int len = hex.length() / 2;
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) ((toByte(achar[pos]) << 4) | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

}
