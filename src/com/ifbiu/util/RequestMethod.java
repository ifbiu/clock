package com.ifbiu.util;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

public class RequestMethod {
    static String userword;
    static String pwd;

    public static String getHttpRequestData(String urlP, String data,String token) {

        // 首先抓取异常并处理
        String returnString = "1";
        try {
            // 代码实现以GET请求方式为主,POST跳过
            /** 1 GET方式请求数据 start*/
            StringBuilder sb = new StringBuilder();
            sb.append(urlP);
            data = URLEncoder.encode(data, "UTF-8");
            sb.append(data);


            // 1  创建URL对象,接收用户传递访问地址对象链接
            URL url = new URL(sb.toString());

            // 2 打开用户传递URL参数地址
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();

            // 3 设置HTTP请求的一些参数信息
            connect.setRequestMethod("GET"); // 参数必须大写
            connect.setRequestProperty("token", token);
            connect.connect();


            // 4 获取URL请求到的数据，并创建数据流接收
            InputStream isString = connect.getInputStream();

            // 5 构建一个字符流缓冲对象,承载URL读取到的数据
            BufferedReader isRead = new BufferedReader(new InputStreamReader(isString));

            // 6 输出打印获取到的文件流
            String str = "";
            while ((str = isRead.readLine()) != null) {
                str = new String(str.getBytes(), "UTF-8"); //解决中文乱码问题
//          System.out.println("文件解析打印：");
//          System.out.println(str);
                returnString = str;
            }

            // 7 关闭流
            isString.close();
            connect.disconnect();

            // 8 JSON转List对象
            // do somthings

        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnString;
    }

    //get方式请求
    public static String restCallerGet(String url, String param,String token) {
        //path 接口路径 xxx/xxx/xxx
        //param 入参 ?xxx=x&xxx=x&xxx=x
        //接口ip

        String data = "";

        //url拼接
        String lasturl = url + param;
        try{
            URL urlNew = new URL(lasturl);
            //打开和url之间的连接
            HttpURLConnection urlConn = (HttpURLConnection) urlNew.openConnection();

            //请求头
            urlConn.setRequestProperty("Accept-Charset", "utf-8");
            urlConn.setRequestProperty("Content-Type", "application/json; charset=GBK");

            urlConn.setDoOutput(true);
            urlConn.setDoInput(true);
            urlConn.setRequestMethod("GET");//GET和POST必须全大写
            urlConn.setRequestProperty("token", token);
            urlConn.connect();

            int code = urlConn.getResponseCode();//获得响应码
            if(code == 200) {//响应成功，获得响应的数据
                InputStream is = urlConn.getInputStream();//得到数据流（输入流）
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = is.read(buffer)) != -1) {
                    String res = new String(buffer, 0, length);
                    data += res;
                }
                //System.out.println(data);
            }
            urlConn.disconnect();   //断开连接

        }catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    //post方式请求
    public static String restCallerPost(String url, String param,String token) {
        //path 接口路径 xxx/xxx/xxx
        //param 入参json {}

        int responseCode;
        //String urlParam = "?aaa=1&bbb=2";
        String urlParam = "";

        String data = "";

        //url拼接
        String lasturl = url + urlParam;
        try {
            URL restURL = new URL(lasturl);
            HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
            conn.setRequestMethod("POST");
            //请求头
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            if (token != null){
                conn.setRequestProperty("token", token);
            }
            conn.setDoOutput(true);

            //输入流
            //OutputStream os = conn.getOutputStream();
            //解决中文乱码
            OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            os.write(param);
            os.flush();
            // 输出response code
            responseCode = conn.getResponseCode();
            // 输出response
            if(responseCode == 200){
                //输出流
                //BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                //解决中文乱码
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                data = reader.readLine();
            } else {
                data = "false";
            }
            // 断开连接
            os.close();
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}
