package me.tinggu.sample.common.rest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import me.tinggu.common.LogUtils;
import me.tinggu.sample.common.AppConstants;
import me.tinggu.sample.common.ServerConstants;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestUtils implements AppConstants, ParameterKeys, ServerConstants {

    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;

    public static <T> T createApi(Class<T> clazz) {
        if (retrofit == null) {
            synchronized (RestUtils.class) {
                if (retrofit == null) {
                    retrofit = getRetrofit();
                }
            }
        }
        return retrofit.create(clazz);
    }

    private static Retrofit getRetrofit() {
        Retrofit.Builder builder = new Retrofit.Builder();
//        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        builder.baseUrl(API_BASE_URL);//设置远程地址
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        builder.client(getOkHttpClient());
        return builder.build();
    }
    
    
    //        return  new GzipRequestInterceptor();
    private static Interceptor getRequestInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if ("GET".equals(request.method())) {
                    return chain.proceed(request);
                }
//                String url = request.url().toString();
//                url = url.substring(API_BASE_URL.length());
//                RequestBody body = request.body();
//                CacheBufferdSkin cache = new CacheBufferdSkin();
//                body.writeTo(cache);
//                DataCenter dataCenter = DataCenter.getDataCenter();
//                String timestamp = dataCenter.getServerTime();
//                String strBody = cache.bodyStr;
//                Request.Builder newRequestBuilder = request.newBuilder()
//                        .header("Content-Type", "application/json")
//                        .header(KEY_SIGN_PARAM, timestamp)
//                        .header(KEY_SIGN, getSing(timestamp, url, strBody));
//                boolean[] isSafeUrl = getUrlInfo(url, request.method());
//                String secretKey = null;
//                if (isSafeUrl[0]) {
//                    secretKey = getSecretkey(timestamp);
//                    String encryptBody = encryptBody(strBody, secretKey);
//                    body = RequestBody.create(MediaType.parse("application/json"), encryptBody);
//                    newRequestBuilder.method(request.method(), body);
//                }
//                request = newRequestBuilder.build();
                loggerRequest(request);
                Response response = chain.proceed(request);
                response = loggerResponse(request, response);
//                if (isSafeUrl[1]) {
//                    String resBody = response.body().toString();
//                    String decryptBody = decryptBody(resBody, secretKey);
//                    ResponseBody newResponseBody = ResponseBody.create(response.body().contentType(), decryptBody);
//                    Response.Builder newBuilderder = response.newBuilder().body(newResponseBody);
//                    response = newBuilderder.build();
//                    response = loggerResponse(request, response);
//                }
                return response;
            }
        };
    }


    private static Response loggerResponse(Request request, Response response) {
        try {
            if (LogUtils.DEBUG_ENABLED) {
                StringBuilder sb = new StringBuilder();
                String url = request.url().toString();
                Long start = System.currentTimeMillis();
                sb.append("---------------------request--------------------------------")
                        .append("\nrequest-->url").append("|").append(url)
                        .append("\nrequest.headers").append(":{\n").append(request.headers()).append("\n}");
                LogUtils.i("Retrofit", sb.toString());
                sb.delete(0, sb.length());
                sb.append("---------------------response--------------------------------")
                        .append("\nresponse-->url").append("|").append(url)
                        .append("\nresponse.body").append("|\n");

                LogUtils.i("Retrofit", sb.toString());
                final int LOG_CHUNK_SIZE = 4000;
                ResponseBody oldResponseBody = response.body();
                String oldBodyString = oldResponseBody.string();
                for (int i = 0, len = oldBodyString.length(); i < len; i += LOG_CHUNK_SIZE) {
                    int end = Math.min(len, i + LOG_CHUNK_SIZE);
                    LogUtils.i("Retrofit", oldBodyString.substring(i, end));
                }
                sb.delete(0, sb.length());
                sb.append("\nFly Time").append("|").append(System.currentTimeMillis() - start);
                LogUtils.i("Retrofit", sb.toString());

                ResponseBody newResponseBody = ResponseBody.create(oldResponseBody.contentType(),
                        oldBodyString);
                Response.Builder newBuilderder = response.newBuilder().body(newResponseBody);
                return newBuilderder.build();
            }
        } catch (Exception e) {
            return response;
        }
        return response;
    }

    private static void loggerRequest(Request request) {
        if (LogUtils.DEBUG_ENABLED) {
            try {
                String url = request.url().toString();
                CacheBufferdSkin cache = new CacheBufferdSkin();
                request.body().writeTo(cache);
                String strBody = cache.bodyStr;
                Headers headers = request.headers();
                LogUtils.i("loggerRequest--- " + " url:" + url + " \nheaders:" + headers + " \nbody:" + strBody);
            } catch (Exception e) {

            }
        }
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {

//            File cacheDir = new File(context.getCacheDir(), RESPONSE_CACHE);
//            okHttpClient.setCache(new Cache(cacheDir, RESPONSE_CACHE_SIZE));
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            okHttpClient =  builder.readTimeout(HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS).
                    connectTimeout(HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS).build();
            okHttpClient = new OkHttpClient();

//            okHttpClient.interceptors().add(getRequestInterceptor());
        }
        return okHttpClient;
    }


    /**
     * 拦截器压缩http请求体，许多服务器无法解析
     */
    static class GzipRequestInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            if (originalRequest.body() == null || originalRequest.header("Content-Encoding") != null) {
                return chain.proceed(originalRequest);
            }

            Request compressedRequest = originalRequest.newBuilder()
                    .header("Content-Encoding", "gzip")
                    .method(originalRequest.method(), gzip(originalRequest.body()))
                    .build();
            return chain.proceed(compressedRequest);
        }

        private RequestBody gzip(final RequestBody body) {
            return new RequestBody() {
                @Override
                public MediaType contentType() {
                    return body.contentType();
                }

                @Override
                public long contentLength() {
                    return -1; // 无法知道压缩后的数据大小
                }

                @Override
                public void writeTo(BufferedSink sink) throws IOException {
                    BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
                    body.writeTo(gzipSink);
                    gzipSink.close();
                }
            };
        }
    }

    public final class LoggingInterceptors {
        private final Logger logger = Logger.getLogger(LoggingInterceptors.class.getName());
        private final OkHttpClient client = new OkHttpClient();

        public LoggingInterceptors() {
            client.networkInterceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    long t1 = System.nanoTime();
                    Request request = chain.request();
                    logger.info(String.format("Sending request %s on %s%n%s",
                            request.url(), chain.connection(), request.headers()));
                    Response response = chain.proceed(request);
                    long t2 = System.nanoTime();
                    logger.info(String.format("Received response for %s in %.1fms%n%s",
                            request.url(), (t2 - t1) / 1e6d, response.headers()));
                    return response;
                }
            });
        }

        public void run() throws Exception {
            Request request = new Request.Builder()
                    .url("https://publicobject.com/helloworld.txt")
                    .build();
            Response response = client.newCall(request).execute();
            response.body().close();
        }

//        public static void main(String... args) throws Exception {
//            new LoggingInterceptors().run();
//        }
    }

    public static class SafeUrl {
        public String _url;
        public String _method;
        public boolean _encryptRequest;
        public boolean _decryptResponse;

        public SafeUrl(String url, String method, boolean encryptReq, boolean decryptResp) {
            _url = url;
            _method = method;
            _encryptRequest = encryptReq;
            _decryptResponse = decryptResp;
        }

    }


}
