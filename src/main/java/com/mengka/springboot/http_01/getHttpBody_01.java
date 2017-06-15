package com.mengka.springboot.http_01;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.CharArrayBuffer;
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

        //step01：启动wireMockServer_01

        //step02：获取http body
        test_get_http_body();

        log.info("getHttpBody_01 end..");
    }

    public static void test_get_http_body() throws Exception {
        URL url = new URL("http://127.0.0.1:8089/v1/kv/a1");
        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        String encoding = con.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        String body = getContent(in, encoding);
        System.out.println(body);
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
}
