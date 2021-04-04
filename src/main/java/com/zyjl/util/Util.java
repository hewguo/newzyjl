package com.zyjl.util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.*;
import javax.script.Invocable;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class Util {

    public static final String SIGN="wxghy56@ue7y$r";
    public static final String APIURL="/api/weixin";
    public static final String WEBURL="https://handler.1010pic.com";
    public static final String CONFIG="/config/zyjl";
    public static final String APPNAME="wx_zyjl";
    public static final String MODEL="MI PAD 4|600|912|Android 8.1.0|6.7.3";
    public static final String ONLYBOOK="/search/onlybook?word=";
    public static final String ONLYBOOKGROUPBY="/search/onlybookgroupby?word=";
    public static final String BOOKDETAIL="/book/detail/";
    public static final String ANSWER="/book/answer/";
    public static final String JSFILEPATH="/md5.js";

    public static String jsBridge(String filename,String method,String ... param){
        String scriptResult ="";//脚本的执行结果
        // 获取JS执行引擎
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        try{
            //2.引擎读取 脚本字符串
            //engine.eval(new StringReader(routeScript));
            //如果js存在文件里，举例
            InputStream is=Util.class.getResourceAsStream(filename);
            BufferedReader reader=new BufferedReader(new InputStreamReader(is));

            //engine.eval(new FileReader(Util.class.getResource(filename).getPath()));
            engine.eval(reader);
            //3.将引擎转换为Invocable，这样才可以掉用js的方法
            Invocable invocable = (Invocable) engine;
            //4.使用 invocable.invokeFunction掉用js脚本里的方法，第一個参数为方法名，后面的参数为被调用的js方法的入参
            scriptResult = ""+ invocable.invokeFunction(method, param);
        }  catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return scriptResult;

    }

    /**
     * 获取uuid
     * @return
     */
    public static String getUUID(){
        String ret="";
        String t=Util.jsBridge(JSFILEPATH,"myGetTime");
        String temp=Util.APIURL+Util.CONFIG+Util.SIGN+t;
        String sign=Util.jsBridge(JSFILEPATH,"hexMD5",temp);
        String cmdStr="curl -H 'charset: utf-8' -H 'referer: https://servicewechat.com/wx2189ba2d1d89d758/7/page-frame.html'" +
                " -H 'appname: wx_zyjl' -H 'cookie: ' -H 'sign: "+sign+"' -H " +
                "'model: MI PAD 4|600|912|Android 8.1.0|6.7.3' -H 'content-type: application/json' -H " +
                "'time: "+t+"' -H 'uuid: ' -H " +
                "'User-Agent: Mozilla/5.0 (Linux; Android 8.1.0; MI PAD 4 Build/OPM1.171019.019; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/68.0.3440.91 Safari/537.36 MicroMessenger/6.7.3.1360(0x26070333) NetType/WIFI Language/zh_CN Process/appbrand2' -H " +
                "'Host: handler.1010pic.com' --compressed 'https://handler.1010pic.com/api/weixin/config/zyjl'";

        String[] cmds={"curl","-H","charset: utf-8",
                        "-H","referer: https://servicewechat.com/wx2189ba2d1d89d758/7/page-frame.html",
                        "-H","appname: "+Util.APPNAME,
                        "-H","sign: "+sign+"",
                        "-H","model: MI PAD 4|600|912|Android 8.1.0|6.7.3",
                        "-H","content-type: application/json",
                        "-H","time: "+t+"",
                        "-H","User-Agent: Mozilla/5.0 (Linux; Android 8.1.0; MI PAD 4 Build/OPM1.171019.019; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/68.0.3440.91 Safari/537.36 MicroMessenger/6.7.3.1360(0x26070333) NetType/WIFI Language/zh_CN Process/appbrand2",
                        "-H","Host: handler.1010pic.com",
                        "--compressed","https://handler.1010pic.com/api/weixin/config/zyjl"};
        ret=execCurl(cmds);
        JSONObject jsonObject= JSONObject.parseObject(ret);
        if(jsonObject.getIntValue("code")==0){
            ret=jsonObject.getJSONObject("result").getString("u");
        }else{
            ret="";
        }

        return ret;
    }

    //根据书名取得清单
    public static String getBookList(String bookname,String uuid,String strWhere){
        String ret="";
        //url编码
        String book= null;
        try {
            book = java.net.URLEncoder.encode(bookname, "UTF-8");
            String t=Util.jsBridge(JSFILEPATH,"myGetTime");
            String temp=Util.APIURL+Util.ONLYBOOK+book+strWhere+Util.SIGN+t;
            //System.out.println(temp);
            String sign=Util.jsBridge(JSFILEPATH,"hexMD5",temp);
            String[] cmds={"curl","-H","charset: utf-8",
                    "-H","referer: https://servicewechat.com/wx2189ba2d1d89d758/7/page-frame.html",
                    "-H","appname: "+Util.APPNAME,
                    "-H","sign: "+sign+"",
                    "-H","uuid: "+uuid,
                    "-H","model: MI PAD 4|600|912|Android 8.1.0|6.7.3",
                    "-H","content-type: application/json",
                    "-H","time: "+t+"",
                    "-H","User-Agent: Mozilla/5.0 (Linux; Android 8.1.0; MI PAD 4 Build/OPM1.171019.019; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/68.0.3440.91 Safari/537.36 MicroMessenger/6.7.3.1360(0x26070333) NetType/WIFI Language/zh_CN Process/appbrand2",
                    "-H","Host: handler.1010pic.com",
                    "--compressed",WEBURL+APIURL+ONLYBOOK+book+strWhere};
            for(String cmd :cmds){
                System.out.print(cmd+ " ");
            }
            System.out.println();
            ret=execCurl(cmds);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //ret=book;
        return ret;
    }

    /**
     * 根据book id获取详细信息
     * @param bookid
     * @return
     */
    public static String getBookDetail(String bookid,String uuid){
        String ret="";
        //url编码
        String book= null;
        try {
            String t=Util.jsBridge(JSFILEPATH,"myGetTime");
            String temp=Util.APIURL+Util.BOOKDETAIL+bookid+Util.SIGN+t;
            //System.out.println(temp);
            String sign=Util.jsBridge(JSFILEPATH,"hexMD5",temp);
            String[] cmds={"curl","-H","charset: utf-8",
                    "-H","referer: https://servicewechat.com/wx2189ba2d1d89d758/7/page-frame.html",
                    "-H","appname: "+Util.APPNAME,
                    "-H","sign: "+sign+"",
                    "-H","uuid: "+uuid,
                    "-H","model: MI PAD 4|600|912|Android 8.1.0|6.7.3",
                    "-H","content-type: application/json",
                    "-H","time: "+t+"",
                    "-H","User-Agent: Mozilla/5.0 (Linux; Android 8.1.0; MI PAD 4 Build/OPM1.171019.019; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/68.0.3440.91 Safari/537.36 MicroMessenger/6.7.3.1360(0x26070333) NetType/WIFI Language/zh_CN Process/appbrand2",
                    "-H","Host: handler.1010pic.com",
                    "--compressed",WEBURL+APIURL+BOOKDETAIL+bookid};
            ret=execCurl(cmds);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //ret=book;
        return ret;
    }

    //根据书名取得排序清单

    /**
     *
     * @param bookname
     * @param uuid
     * @param groupby  grade subject version
     * @return
     */
    public static String getBookListGroup(String bookname,String uuid,String groupby){
        String ret="";
        //url编码
        String book= null;
        try {
            book = java.net.URLEncoder.encode(bookname, "UTF-8");
            String t=Util.jsBridge(JSFILEPATH,"myGetTime");
            String temp=Util.APIURL+Util.ONLYBOOKGROUPBY+book+"&groupby="+groupby+Util.SIGN+t;
            //System.out.println(temp);
            String sign=Util.jsBridge(JSFILEPATH,"hexMD5",temp);
            String[] cmds={"curl","-H","charset: utf-8",
                    "-H","referer: https://servicewechat.com/wx2189ba2d1d89d758/7/page-frame.html",
                    "-H","appname: "+Util.APPNAME,
                    "-H","sign: "+sign+"",
                    "-H","uuid: "+uuid,
                    "-H","model: MI PAD 4|600|912|Android 8.1.0|6.7.3",
                    "-H","content-type: application/json",
                    "-H","time: "+t+"",
                    "-H","User-Agent: Mozilla/5.0 (Linux; Android 8.1.0; MI PAD 4 Build/OPM1.171019.019; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/68.0.3440.91 Safari/537.36 MicroMessenger/6.7.3.1360(0x26070333) NetType/WIFI Language/zh_CN Process/appbrand2",
                    "-H","Host: handler.1010pic.com",
                    "--compressed",WEBURL+APIURL+ONLYBOOKGROUPBY+book+"&groupby="+groupby};
//            String temp1="";
//            for(String cmd:cmds){
//                temp1+=cmd+" ";
//            }
//            System.out.println(temp1);
            ret=execCurl(cmds);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //ret=book;
        return ret;
    }

    /**
     * 根据id获取图书链接
     * @param id
     * @return
     */
    public static String getAnswer(String id,String uuid){
        String ret="";
        try {

            String t=Util.jsBridge(JSFILEPATH,"myGetTime");
            String temp=Util.APIURL+Util.ANSWER+id+Util.SIGN+t;
            //System.out.println(temp);
            String sign=Util.jsBridge(JSFILEPATH,"hexMD5",temp);
            String[] cmds={"curl","-H","charset: utf-8",
                    "-H","referer: https://servicewechat.com/wx2189ba2d1d89d758/7/page-frame.html",
                    "-H","appname: "+Util.APPNAME,
                    "-H","sign: "+sign+"",
                    "-H","uuid: "+uuid,
                    "-H","model: MI PAD 4|600|912|Android 8.1.0|6.7.3",
                    "-H","content-type: application/json",
                    "-H","time: "+t+"",
                    "-H","User-Agent: Mozilla/5.0 (Linux; Android 8.1.0; MI PAD 4 Build/OPM1.171019.019; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/68.0.3440.91 Safari/537.36 MicroMessenger/6.7.3.1360(0x26070333) NetType/WIFI Language/zh_CN Process/appbrand2",
                    "-H","Host: handler.1010pic.com",
                    "--compressed",WEBURL+APIURL+ANSWER+id};
            ret=execCurl(cmds);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //ret=book;
        System.out.println(ret);
        return ret;
    }

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

    public static void main(String[] args) throws Exception {
        String ret1="336b928c6baaa9c924f4ba749d58ee27";
        String str="/api/weixin/config/zyjlwxghy56@ue7y$r1577259798934";

        String str1=Util.jsBridge(JSFILEPATH,"hexMD5",str);
        System.out.println(str);
        System.out.println(str1);
        if(ret1.equals(str1)){
            System.out.println("true");
        }else {
            System.out.println("false");
        }

        String str2=Util.jsBridge(JSFILEPATH,"myGetTime");
        System.out.println(str2);
        String uuid=Util.getUUID();
        System.out.println(uuid);
        System.out.println(Util.getBookList("全效",uuid,""));
        System.out.println(Util.getBookListGroup("全效",uuid,"grade"));
    }

}
