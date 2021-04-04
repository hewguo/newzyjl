package com.zyjl.util;

import java.security.MessageDigest;

/**
 * @ClassName MD5
 * @Description TODO
 * @Author hewguo
 * @Date 2020-11-21 19:51
 * @Version 1.0
 **/
public class MD5 {
    private MD5() {
    }

    public static final String getMessageDigest(byte[] buffer) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] resultByteArray = mdTemp.digest();
            // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
            char[] resultCharArray =new char[resultByteArray.length * 2];
            // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去

            int index = 0;

            for (byte b : resultByteArray) {

                resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];

                resultCharArray[index++] = hexDigits[b& 0xf];

            }

            // 字符数组组合成字符串返回

            return new String(resultCharArray);

        } catch (Exception e) {
            return null;
        }
    }
}
