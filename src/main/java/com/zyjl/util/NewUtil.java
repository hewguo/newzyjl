package com.zyjl.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import sun.tools.java.SyntaxError;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @ClassName NewUtil
 * @Description TODO
 * @Author hewguo
 * @Date 2020-11-21 18:28
 * @Version 1.0
 **/
public class NewUtil {

    public static String VERA="796";
    public static String WDF="HSud8%tdFe9k@we5a-a9H&d3*i)k_@dYK9$b1!%eR7kFg8R9#21Hd9@1T";
    //cookie会随着登录的信息变化，如果用户13586768110重新登录后需要及时变化
    public static String Cookie="Cookie:WrQz_f8d5_saltkey=umQMiy3f; WrQz_f8d5_auth=1041lKMALXDHFBVw9n33%2FE7lNxDCRvjxLFOqZffEm0bSGvi1HAvg8gA6bzLgmM5rA%2FU9VN32WVi%2Bm%2Bh1NF2W30C2Y6WDfw";

    /**
     * 执行外部命令curl
     * @param cmds
     * @return
     */
    public static String execCurl(String[] cmds) {
        ProcessBuilder process = new ProcessBuilder(cmds);
        Process p;
        try {
            p = process.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            return builder.toString();

        } catch (IOException e) {
            System.out.print("error");
            e.printStackTrace();
        }
        return null;

    }

    public static String getUUID(){
        String uuid2 = UUID.randomUUID().toString().replace("-", "");
        return uuid2;
    }

    public static long getcurrenttime(){
        return  System.currentTimeMillis() / 1000;
    }

    public static String combineSign(String url, long currentTime, String uuid, String key) {
        StringBuilder sb = new StringBuilder();
        sb.append(url).append(key).append(uuid).append(currentTime);
        System.out.println("=====\n"+sb.toString()+"\n"+MD5.getMessageDigest(sb.toString().getBytes())+"\n=====");
        return MD5.getMessageDigest(sb.toString().getBytes());
    }

    //根据书名取得清单
    public static String getBookList(String bookname,String strWhere,String pageno){
        String ret="";
        //url编码
        String book= null;
        try{
            String url="http://handler.1010pic.com/api/v5/search/onlybook/v3?page="+pageno+"&word="+bookname+strWhere;
            long currentTime =getcurrenttime();
            String uuid=getUUID();
            String sign=combineSign(url,currentTime,uuid,NewUtil.WDF);

            String[] cmds={"curl",
                    "-H","VERA: "+VERA,
                    "-H","UUID: "+uuid,
                    "-H","SIGN: "+sign+"",
                    "-H","TIME: "+Long.toString(currentTime),
                    "-H","CHANNEL: xiaomi",
                    "-H","SDKVERSION:27",
                    //"-H","Accept-Encoding:gzip",
                    "-H","User-Agent:okhttp/3.8.1",
                    "-H","Host: handler.1010pic.com",
                    url};
            return AES.decryptMsg(execCurl(cmds));

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static String getBookListGroup(String bookname,String groupby){
        String ret="";
        //url编码

        try {
            String url="http://handler.1010pic.com/api/v5/search/onlybookgroupby?word="+bookname+"&groupby="+groupby;
            long currentTime =getcurrenttime();
            String uuid=getUUID();
            String sign=combineSign(url,currentTime,uuid,NewUtil.WDF);

            String[] cmds={"curl",
                    "-H","VERA: "+VERA,
                    "-H","UUID: "+uuid,
                    "-H","SIGN: "+sign+"",
                    "-H","TIME: "+Long.toString(currentTime),
                    "-H","CHANNEL: xiaomi",
                    "-H","SDKVERSION:27",
                    //"-H","Accept-Encoding:gzip",
                    "-H","User-Agent:okhttp/3.8.1",
                    "-H","Host: handler.1010pic.com",
                    url};
            return AES.decryptMsg(execCurl(cmds));

        } catch (Exception e) {
            e.printStackTrace();
        }
        //ret=book;
        return ret;
    }

    public static String getBookDetail(String bookid){
        String ret="";
        //url编码
       try {
            String url="http://handler.1010pic.com/api/v5/book/detail/"+bookid;
            long currentTime =getcurrenttime();
            String uuid=getUUID();
            String sign=combineSign(url,currentTime,uuid,NewUtil.WDF);

            String[] cmds={"curl",
                    "-H","VERA: "+VERA,
                    "-H","UUID: "+uuid,
                    "-H","SIGN: "+sign+"",
                    "-H","TIME: "+Long.toString(currentTime),
                    "-H","CHANNEL: xiaomi",
                    "-H","SDKVERSION:27",
                    //"-H","Accept-Encoding:gzip",
                    "-H","User-Agent:okhttp/3.8.1",
                    "-H","Host: handler.1010pic.com",
                    url};
            return AES.decryptMsg(execCurl(cmds));

        } catch (Exception e) {
            e.printStackTrace();
        }
        //ret=book;
        return ret;
    }

    public static String getAnswer(String id){
        String ret="";
        //url编码
        try {
            String url="http://handler.1010pic.com/api/v5/book/answer_v2/"+id+"/0";
            //String url="http://handler.1010pic.com/api/v5/book/funcs/"+id;
            long currentTime =getcurrenttime();
            String uuid=getUUID();
            String sign=combineSign(url,currentTime,uuid,NewUtil.WDF);

            String[] cmds={"curl",
                    "-H","VERA: "+VERA,
                    "-H","UUID: "+uuid,
                    "-H","SIGN: "+sign+"",
                    "-H","TIME: "+Long.toString(currentTime),
                    "-H","CHANNEL: xiaomi",
                    "-H","SDKVERSION:27",
                    //"-H","Accept-Encoding:gzip",
                    "-H","User-Agent:okhttp/3.8.1",
                    "-H","Host: handler.1010pic.com",
                    "-H",Cookie,
                    url};
            return AES.decryptMsg(execCurl(cmds));

        } catch (Exception e) {
            e.printStackTrace();
        }
        //ret=book;
        return ret;
    }

    public static void main(String[] args) throws Exception {
//        String url="http://handler.1010pic.com/api/v5/search/onlybook/v3?page=1&word=励耘";
//        long currentTime =getcurrenttime();
//        String uuid=getUUID();
//        String sign=combineSign(url,currentTime,uuid,NewUtil.WDF);
//
//        String[] cmds={"curl",
//                "-H","VERA: "+VERA,
//                "-H","UUID: "+uuid,
//                "-H","SIGN: "+sign+"",
//                "-H","TIME: "+Long.toString(currentTime),
//                "-H","CHANNEL: xiaomi",
//                "-H","SDKVERSION:27",
//                //"-H","Accept-Encoding:gzip",
//                "-H","User-Agent:okhttp/3.8.1",
//                "-H","Host: handler.1010pic.com",
//                url};
//
//        String ret=execCurl(cmds);
//        //System.out.println(ret);
//        String result=AES.decryptMsg(ret);
//        System.out.println(result);
//
//        JSONObject jsonObject= JSONObject.parseObject(result);
//        java.util.List<NewBookInfo> bookInfos= JSONArray.parseArray(jsonObject.getJSONObject("result").getJSONArray("lists").toString(),NewBookInfo.class);
//        for(NewBookInfo bookInfo:bookInfos){
//            System.out.println(bookInfo.toString());
//        }
        //System.out.println(getBookListGroup("实验班","subject"));
        //System.out.println(getBookList("实验班","","1"));
//        System.out.println(getBookDetail("00006060210000000010"));
//        System.out.println(getAnswer("344435"));
//
//        System.out.println(System.currentTimeMillis() / 1000);
//        System.out.println(System.currentTimeMillis() );
//
//        String url="http://handler.1010pic.com/api/v5/book/answer_v2/"+"344435"+"/0";

        //String url="http://handler.1010pic.com/api/v5/book/funcs/"+id;
//        long currentTime =getcurrenttime();
//        String uuid=getUUID();
//        String sign=combineSign(url,currentTime,uuid,NewUtil.WDF);
//        System.out.println(sign);
//        System.out.println(getBookListGroup("实验班","grade"));

        System.out.println("====================");
        String tmp="http://handler.1010pic.com/api/v5/search/onlybookgroupby?word=实验班&groupby=gradeHSud8%tdFe9k@we5a-a9H&d3*i)k_@dYK9$b1!%eR7kFg8R9#21Hd9@1Tfc6f0a14666e4b9dbb6d75a3ae01b3971606279256";
        tmp="http://handler.1010pic.com/api/v5/search/onlybook/v3?page=1&word=%E5%AE%9E%E9%AA%8C%E7%8F%AD";
        String uuid="f3e9d4fbdf1e41ddb2ac98ac3f4fd094";
        long time=1606286215;
        System.out.println("111-------"+combineSign(tmp,time,uuid,WDF));
        tmp="http://handler.1010pic.com/api/v5/search/onlybook/v3?page=1&word=实验班";
        System.out.println("222-------"+combineSign(tmp,time,uuid,WDF));

        System.out.println(MD5.getMessageDigest(tmp.getBytes()));
        System.out.println("----------------------------");
        System.out.println(testgetBookListGroup());

        System.out.println(getAnswer("349815   "));
    }

    public static String testgetBookListGroup(){
        String ret="";
        //url编码

        try {
            String url="http://handler.1010pic.com/api/v5/search/onlybookgroupby?word=实验班&groupby=grade";
            long currentTime =1606281441;//getcurrenttime();
            String uuid="15ed689eaca64e4b90ecc2df069a9d59";//getUUID();
            String sign=combineSign(url,currentTime,uuid,NewUtil.WDF);
            System.out.println("sign is:"+sign);

            String[] cmds={"curl",
                    "-H","VERA: "+VERA,
                    "-H","UUID: "+uuid,
                    "-H","SIGN: "+sign+"",
                    "-H","TIME: "+Long.toString(currentTime),
                    "-H","CHANNEL: xiaomi",
                    "-H","SDKVERSION:27",
                    //"-H","Accept-Encoding:gzip",
                    "-H","User-Agent:okhttp/3.8.1",
                    "-H","Host: handler.1010pic.com",
                    url};
            System.out.println(convertArrayToString(cmds));
            return AES.decryptMsg(execCurl(cmds));

        } catch (Exception e) {
            e.printStackTrace();
        }
        //ret=book;
        return ret;
    }

    public static String convertArrayToString(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return "";
        }
        String res = "";
        for (int i = 0, len = strArr.length; i < len; i++) {
            res += strArr[i];
            if (i < len - 1) {
                res += ",";
            }
        }
        return res;
    }

}
