package io.github.wyp0596.util;

import okhttp3.*;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by wyp0596 on 30/12/2017.
 */
public abstract class HttpUtils {

    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static final MediaType FORM
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.SECONDS)
            .build();

    private HttpUtils() {
        throw new UnsupportedOperationException();
    }

    public static void setClient(OkHttpClient client) {
        HttpUtils.client = client;
    }

    public static Response getForResponse(String url) throws UncheckedIOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String getForString(String url) throws UncheckedIOException {
        ResponseBody body = getForResponse(url).body();
        try {
            return body == null ? null : body.string();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static Response postForResponse(String url, Object req) throws UncheckedIOException {
        String json = req instanceof String ? (String) req : GsonUtils.toJson(req);
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSON, json))
                .build();
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String postForString(String url, Object json) throws UncheckedIOException {
        ResponseBody body = postForResponse(url, json).body();
        try {
            return body == null ? null : body.string();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static <T> T postForObject(String url, Object json, Class<T> targetClass) {
        String resp = postForString(url, json);
        return GsonUtils.fromJson(resp, targetClass);
    }

    public static Response postFormForResponse(String url, Map<String, String> formBody, Map<String, String> headersMap) throws UncheckedIOException {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBody.forEach(formBodyBuilder::add);
        RequestBody requestBody = formBodyBuilder.build();

        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(headersMap))
                .post(requestBody)
                .build();

        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static Response head(String url) throws UncheckedIOException {
        Request request = new Request.Builder().url(url).head().build();
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static Response put(String url, Object req) throws UncheckedIOException {
        String json = req instanceof String ? (String) req : GsonUtils.toJson(req);
        Request request = new Request.Builder()
                .url(url)
                .put(RequestBody.create(JSON, json))
                .build();
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static Response delete(String url) throws UncheckedIOException {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
