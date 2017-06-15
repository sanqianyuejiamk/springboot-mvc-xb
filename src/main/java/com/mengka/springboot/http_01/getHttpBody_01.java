package com.mengka.springboot.http_01;

import com.alibaba.fastjson.JSON;
import com.mengka.springboot.model.MengkaApplyRsp;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.CharArrayBuffer;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author mengka
 * @date 2017/06/15.
 */
@Slf4j
public class getHttpBody_01 {

    public static void main(String[] args) throws Exception {
        log.info("getHttpBody_01 start..");

        String path = "http://127.0.0.1:8089/v1/kv/a1";

        //step01：启动wireMockServer_01

        //step02：获取http body
        test_get_http_body(path);

        MengkaApplyRsp mengkaApplyRsp = get(path,MengkaApplyRsp.class);
        log.info("mengkaApplyRsp = {}",JSON.toJSONString(mengkaApplyRsp));

        log.info("getHttpBody_01 end..");
    }

    /**
     *  获取http对象结果
     *
     * @param path
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T get(String path,Class<T> clazz)throws Exception{
        String json = test_get_http_body(path);
        return JSON.parseObject(json, clazz);
    }

    /**
     *  获取http json结果
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static String test_get_http_body(String path) throws Exception {
        URL url = new URL(path);
        URLConnection con = url.openConnection();
        con.setConnectTimeout(10000);

        InputStream in = con.getInputStream();
        String encoding = con.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        String body = getContent(in, encoding);
        //或
        String body2 = getContent2(in, encoding);
        log.info("test_get_http_body body = {}",body);
        return body;
    }

    public static String getContent(InputStream inputStream, String charset) throws Exception {
        int i = -1;
        if (i < 0) {
            i = 4096;
        }
        final Reader reader = new InputStreamReader(inputStream, charset);
        final CharArrayBuffer buffer = new CharArrayBuffer(i);
        final char[] tmp = new char[1024];
        int l;
        while ((l = reader.read(tmp)) != -1) {
            buffer.append(tmp, 0, l);
        }
        return buffer.toString();
    }

    public static String getContent2(InputStream inputStream, String charset)throws Exception{
        String temp;
        String ret="";
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(isr);
        while((temp = br.readLine()) != null){
            ret += temp;
        }
        br.close();
        isr.close();
        return ret;
    }
}
