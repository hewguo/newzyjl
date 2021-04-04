package com.zyjl.util;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName AES
 * @Description TODO
 * @Author hewguo
 * @Date 2020-11-11 11:11
 * @Version 1.0
 **/
public class AES {
    //public static String KEY= "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKta2b5Vw5YkWHCAj4rJCwS227\r/35FZ29e4I6pS2B8zSq2RgBpXUuMg7oZF1Qt3x0iyg8PeyblyNeCRB6gIMehFThe\r1Y7m1FaQyaZp+CJYOTLM4/THKp9UndrEgJ/5a83vP1375YCV2lMvWARrNlBep4RN\rnESUJhQz58Gr/F39TwIDAQAB";
    public static String KEY= "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHJLKxt2y3szY1k5O5yq3wbyPWRo57wl5Zyhl60oV/PCG0pEWBzDeab9eeMNein103TkNj7qFZLaqCLGb6fTz6sV9fqSZUlcl4F0twxo6LIoaKt925Wj1E93duYFbaM0uGJkqdU+OcRbvCODE/3M34wj/6TLKl/PXwWU76PTjp5wIDAQAB";


    public static String encrypt(String input, String key) {
        byte[] crypted = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(1, skey);
            crypted = cipher.doFinal(input.getBytes());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return DES.bytesToHexString(crypted);
    }

//    public static String encryptCBC(String input, String key) {
//        byte[] crypted = null;
//        try {
//            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            cipher.init(1, skey, new IvParameterSpec(InformationProvider.m0iv().getBytes()));
//            crypted = cipher.doFinal(input.getBytes());
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }
//        return DES.bytesToHexString(crypted);
//    }

    public static String decrypt(String input, String key) {
        byte[] output = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(2, skey);
            output = cipher.doFinal(Base64.decodeBase64(input.getBytes()));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return new String(output);
    }

    public static String decryptCBC(String input, String key) {
        byte[] output = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            cipher.init(2, skey, new IvParameterSpec(InformationProvider.m0iv().getBytes()));
            cipher.init(2, skey, new IvParameterSpec("7632870983764590".getBytes()));
            output = cipher.doFinal(Base64.decodeBase64(input.getBytes()));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return new String(output);
    }


    public static PublicKey getPublicKey(String str) throws Exception{
        try {
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(com.zyjl.util.Base64.decode(str, 2)));
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e2) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e3) {
            throw new Exception("公钥数据为空");
        }

    }

    public static String decoder(String str){
        byte[] decode=com.zyjl.util.Base64.decode(str,0);
        try{
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            PublicKey kk=getPublicKey(KEY);
            instance.init(2, getPublicKey(KEY));
            return new String(instance.doFinal(decode), "UTF-8").trim();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "error decoder";
    }

    public static String parseToString(long time, String format) {
        return new SimpleDateFormat(format).format(new Date(time));
    }

    public static String getSimpleDate() {
        return parseToString(System.currentTimeMillis(), "yyyy-MM-dd");
    }

    public static String decryptMsg(String str){
        String key="dGskdP(@6Ty2Ks*9";
        return decryptCBC(str,key);
    }

    public static void main(String[] args){
        String temp="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";
        String tt="7kXZvGsDAo7W7QMr8pEjzZxkoMZn/8FJoObiJjivCqTFC5MwXicRo1YYTozsybPxFLC6tAzvAbfGWFKiX8zSBQ==";
        String key="dGskdP(@6Ty2Ks*9";

        System.out.println(AES.decryptCBC(tt,key));
    }

}
