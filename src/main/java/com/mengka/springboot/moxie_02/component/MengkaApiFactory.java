package com.mengka.springboot.moxie_02.component;

import com.mengka.springboot.moxie_02.util.ObjectMappers;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @author huangyy
 * @description
 * @date 2017/05/11.
 */
@Slf4j
public class MengkaApiFactory {

    private Retrofit retrofit;

    private MengkaApiFactory(final AuthToken authToken, final String baseUrl, final String userAgent, final Long timeout) {
        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .readTimeout(timeout, TimeUnit.MILLISECONDS)
                .writeTimeout(timeout, TimeUnit.MILLISECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request.Builder requestBuilder = chain.request().newBuilder();

                        if (chain.request().header("Authorization") == null) {
                            URL url = chain.request().url().url();
                            String path = url.getPath();
                            log.info("-----mengkaApiFactory----- path = {}",path);
                            String authorizationValue = authToken.getToken();
                            requestBuilder.addHeader("Authorization", authorizationValue);
                        }

                        Request newRequest = requestBuilder
                                .addHeader("User-Agent", userAgent)
                                .build();

                        return chain.proceed(newRequest);
                    }
                }).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(ObjectMappers.getObjectMapper()))
                .callFactory(httpClient)
                .build();
    }

    public <T> T newApi(Class<T> clz) {
        if (clz.isInterface())
            return retrofit.create(clz);
        throw new IllegalArgumentException("interface class required");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private static final String DEFAULT_USER_AGENT = "Mengka-Java-SDK/1.0";
        private static final Long DEFAULT_SO_TIMEOUT = 10000L; // 10s
        private AuthToken authToken;
        private String userAgent;
        private String baseUrl;
        private Long timeout = 0L;

        public Builder withAuthToken(AuthToken authToken) {
            this.authToken = authToken;
            return this;
        }


        public Builder withUserAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public Builder withBaseUrl(String url) {
            this.baseUrl = url;
            return this;
        }

        public Builder withTimeout(Long ms) {
            this.timeout = ms;
            return this;
        }

        public MengkaApiFactory build() {
            if (baseUrl == null || baseUrl.isEmpty()) {
                throw new IllegalArgumentException("baseUrl invalid");
            }

            String customUserAgent;
            if (userAgent == null || userAgent.isEmpty()) {
                customUserAgent = DEFAULT_USER_AGENT;
            } else {
                customUserAgent = userAgent;
            }
            Long socketTimeOut = timeout == 0L ? DEFAULT_SO_TIMEOUT : timeout;
            return new MengkaApiFactory(this.authToken, this.baseUrl, customUserAgent, socketTimeOut);
        }
    }
}
