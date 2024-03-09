package com.momentumtrading.signals.utils;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Response {

    private int statusCode;
    private String responseBody;
    private List<Header> responseHeaders;

    public Response getResponse(CloseableHttpClient client,
                                RequestBuilder requestBuilder) {
        CloseableHttpResponse httpResponse;
        try {
            httpResponse = client.execute(requestBuilder.build());
            setStatusCode(httpResponse);
            setBody(httpResponse);
            setHeader(httpResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Response setRedirect(CloseableHttpClient client,
                                RequestBuilder requestBuilder) {
        CloseableHttpResponse httpResponse;
        try {
            httpResponse = client.execute(requestBuilder.build());
            setStatusCode(httpResponse);
            setBody(httpResponse);
            setHeader(httpResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    private void setStatusCode(CloseableHttpResponse closeableHttpResponse) {
        this.statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
    }

    private void setBody(CloseableHttpResponse closeableHttpResponse) {
        this.responseBody = getResponseString(closeableHttpResponse);
    }

    private void setHeader(CloseableHttpResponse closeableHttpResponse) {
        this.responseHeaders = List.of(closeableHttpResponse.getAllHeaders());
    }

    public String getResponseString(HttpResponse response) {
        if (response.getEntity() == null)
            return null;
        try {
            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return responseBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public String getResponseHeader(String name) {
        for (Header header : responseHeaders
        ) {
            if (header.getName().equals(name)) {
                return header.getValue();
            }
        }
        return null;
    }
    public String getJsonStringField(String field) {
        JSONObject jsonObject = new JSONObject(getResponseBody());
        return jsonObject.getString(field);
    }


    public String getResponseAllHeader(String name) {
        String list = "";
        for (Header header : responseHeaders
        ) {
            if (header.getName().equals(name)) {
                list += header.getValue();
            }
        }
        return list;
    }

}