package com.momentumtrading.signals.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@Slf4j
public class HttpsSteps {

    Gson gson;
    Response response = new Response();
    public CloseableHttpClient client = HttpClientBuilder.create().disableRedirectHandling().build();
    private RequestBuilder requestBuilder;

    public HttpsSteps() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        this.gson = builder.setPrettyPrinting().create();
    }

    public Builder withRestEndpoint(String uri, String httpMethod) {
        requestBuilder = RequestBuilder.create(httpMethod)
                .setUri(uri);
        return new Builder();
    }


    public Builder withEndPointQueryParams(String uri, String httpMethod, NameValuePair params) throws URISyntaxException {
        requestBuilder = RequestBuilder.create(httpMethod)
                .setUri(uri)
                .addParameters(params);
        return new Builder();
    }

    public String getUrlEnCoded(Map<String, String> map) {
        String result = "";
        for (String key : map.keySet()) {
            result += key + ":" + map.get(key) + "\n";
        }
        return result;
    }

    public String getDataEnCoded(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    public <T> String getEntity(T clazz) {
        return gson.toJson(clazz).toString();
    }

    public <T> T getSerializeObject(Response response, Class<T> tClass) {
        return gson.fromJson(response.getResponseBody(), tClass);
    }


    public class Builder {
        private int expectedHttpStatusCode;
        private String expectedResponseBody;

        public Builder expectStatus(int httpStatus){
            this.expectedHttpStatusCode = httpStatus;
            return this;
        }

        public Builder expectBody(String responseBody){
            responseBody = response.getResponseBody();
            this.expectedResponseBody = responseBody;
            return this;
        }

        public Response execute() {
            return response.getResponse(client, requestBuilder);
        }

        public Builder withBody(String body, ContentType contentType) {
            StringEntity requestEntity = new StringEntity(
                    body,
                    contentType);
            requestBuilder.setEntity(requestEntity);
            return this;
        }


        public Builder withRequestBodyAndHeaders(String requestBody, Map<String, String> headers) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                requestBuilder.setHeader(header.getKey(), header.getValue());
            }
            withBody(requestBody, ContentType.APPLICATION_JSON);
            return this;
        }

        public Builder withHeader(String name, String value) {
            requestBuilder.setHeader(name, value);
            return this;
        }

        public Builder withParams(String name, String value) {
            requestBuilder.addParameter(name, value);
            return this;
        }

        public Builder withHeaders(Map<String, String> headers) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                requestBuilder.setHeader(header.getKey(), header.getValue());
            }
            return this;
        }

        public Builder withJsonBody(String json) {
            return withBody(json, ContentType.APPLICATION_JSON);
        }

        public Builder addBodyFile(File file) {
            try {
                requestBuilder.setEntity(new InputStreamEntity(new FileInputStream(file)));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            return this;
        }


        public Builder addBody(HttpEntity entity) {
            requestBuilder.setEntity((HttpEntity) entity);
            return this;
        }
        public <T> String getEntity(T clazz) {
            return gson.toJson(clazz).toString();
        }

        public <T> T getSerializeObject(Response response, Class<T> tClass) {
            return gson.fromJson(response.getResponseBody(), tClass);
        }


    }

    public CloseableHttpClient sslAcceptCerts() {
        TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
        SSLContext sslContext;
        try {
            sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            throw new RuntimeException(e);
        }
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                NoopHostnameVerifier.INSTANCE);

        Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("https", sslsf)
                        .register("http", new PlainConnectionSocketFactory())
                        .build();

        BasicHttpClientConnectionManager connectionManager =
                new BasicHttpClientConnectionManager(socketFactoryRegistry);
        return client = HttpClients.custom().setSSLSocketFactory(sslsf)
                .setConnectionManager(connectionManager).build();
    }


    public String getCookie(Response response) {
        return response.getResponseHeader("Set-Cookie");
    }

    public String getCookies(Response response) {
        return response.getResponseAllHeader("Set-Cookie");
    }

    public String getToken(String url, String body) {
        Response response = withRestEndpoint(url, "POST")
                .withBody(body, ContentType.APPLICATION_FORM_URLENCODED)
                .execute();

       log.info(response.getResponseBody());

        if (response.getStatusCode() == 200) {
            String token = "Bearer " + response.getJsonStringField("access_token");
            log.info(" "+ token);
            return token;
        } else {
            log.error(response.getResponseBody() + " Status Code: " + response.getStatusCode());
            return null;
        }
    }

}
