package cn.usr.service.impl;
/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 cuisongliu@qq.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import cn.usr.dto.BasicResponseDTO;
import cn.usr.dto.RequestUrlGenerator;
import cn.usr.dto.user.login.LoginRequestDTO;
import cn.usr.dto.user.login.LoginResponseDTO;
import cn.usr.entity.MqttPropertise;
import cn.usr.service.UsrcloudApi;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * api实现
 *
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2018-03-10 20:44
 */
public final class UsrcloudApiImpl implements UsrcloudApi {
    private String account;
    private String password;

    @Override
    public String login() {
        LoginRequestDTO dto = new LoginRequestDTO(account,password);
        LoginResponseDTO res = HttpClientTools.doPostSSL(dto,LoginResponseDTO.class);
        if (null!=res && res.getStatus()!=0){
            return null;
        }
        return null!=res ? res.getData().getToken():null;
    }

    @Override
    public <T extends BasicResponseDTO> T doPostSSL(RequestUrlGenerator requestModel, Class<T> clazz) {
        return HttpClientTools.doPostSSL(requestModel,clazz);
    }

    /**
     * @param account  账号
     * @param password 密码
     */
    public UsrcloudApiImpl(String account, String password) {
        this.account = account;
        this.password = password;
    }


    /**
     * 有人云接口调用
     *
     * @author cuisongliu [cuisongliu@qq.com]
     * @since 2018-03-06 20:51
     */
    private static class HttpClientTools {
        private static PoolingHttpClientConnectionManager connMgr;
        private static RequestConfig requestConfig;
        private static final int MAX_TIMEOUT = 7000;
        private static Logger logger = LoggerFactory.getLogger(HttpClientTools.class);

        static {
            // 设置连接池
            connMgr = new PoolingHttpClientConnectionManager();
            // 设置连接池大小
            connMgr.setMaxTotal(100);
            connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
            RequestConfig.Builder configBuilder = RequestConfig.custom();
            // 设置连接超时
            configBuilder.setConnectTimeout(MAX_TIMEOUT);
            // 设置读取超时
            configBuilder.setSocketTimeout(MAX_TIMEOUT);
            // 设置从连接池获取连接实例的超时
            configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
            // 在提交请求之前 测试连接是否可用
            configBuilder.setStaleConnectionCheckEnabled(true);
            requestConfig = configBuilder.build();
        }


        /**
         * 发送 SSL POST 请求（HTTPS），JSON形式
         *
         * @param requestModel JSON对象
         * @return
         */
        static <T extends BasicResponseDTO> T doPostSSL(RequestUrlGenerator requestModel, Class<T> clazz) {
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
            HttpPost httpPost = new HttpPost(MqttPropertise.API_ADDRESS + requestModel.generatorUrl());
            CloseableHttpResponse response = null;
            String httpStr = null;
            try {
                httpPost.setConfig(requestConfig);
                String jsonStr = JSONObject.toJSONString(requestModel);
                logger.debug("请求的Json:" + jsonStr);
                StringEntity stringEntity = new StringEntity(jsonStr, "UTF-8");//解决中文乱码问题
                stringEntity.setContentEncoding("UTF-8");
                stringEntity.setContentType("application/json");
                httpPost.setEntity(stringEntity);
                response = httpClient.execute(httpPost);
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    return null;
                }
                HttpEntity entity = response.getEntity();
                if (entity == null) {
                    return null;
                }
                httpStr = EntityUtils.toString(entity, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (response != null) {
                    try {
                        EntityUtils.consume(response.getEntity());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            //可以记录日志
            T responseDTO = JSON.parseObject(httpStr, clazz);
            logger.debug("返回的Json:" + httpStr);
            return responseDTO;
        }

        /**
         * 创建SSL安全连接
         *
         * @return
         */
        static private SSLConnectionSocketFactory createSSLConnSocketFactory() {
            SSLConnectionSocketFactory sslsf = null;
            try {
                SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        return true;
                    }
                }).build();
                sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
                    @Override
                    public boolean verify(String arg0, SSLSession arg1) {
                        return true;
                    }

                    @Override
                    public void verify(String host, SSLSocket ssl) throws IOException {
                    }

                    @Override
                    public void verify(String host, X509Certificate cert) throws SSLException {
                    }

                    @Override
                    public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
                    }
                });
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
            return sslsf;
        }

    }

}


