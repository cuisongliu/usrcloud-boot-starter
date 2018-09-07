package com.cuisongliu.usrcloud.autoconfig.properties;
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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * jwt 参数属性
 *
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2018-02-28 22:05
 */
@Configuration
@ConfigurationProperties(prefix = UsrcloudProperties.PREFIX)
public class UsrcloudProperties {
    public static final String PREFIX = "spring.usrcloud";
    /**
     * 用户名
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     *  设置超时时间，单位为秒
     */
    private Integer timeout = 60;
    /**
     * 心跳时间，单位为秒。即多长时间确认一次Client端是否在线
     */
    private Integer keepAlive =60;
    /**
     *
     */
    private Integer maxInflight = 10;

    /**
     * 不记忆连接中的任何状态
     */
    private Boolean cleanSession = true;
    /**
     * 是否自动重连
     */
    private Boolean automaticReconnect = true;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Integer keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Boolean getCleanSession() {
        return cleanSession;
    }

    public void setCleanSession(Boolean cleanSession) {
        this.cleanSession = cleanSession;
    }

    public Boolean getAutomaticReconnect() {
        return automaticReconnect;
    }

    public void setAutomaticReconnect(Boolean automaticReconnect) {
        this.automaticReconnect = automaticReconnect;
    }

    public Integer getMaxInflight() {
        return maxInflight;
    }

    public void setMaxInflight(Integer maxInflight) {
        this.maxInflight = maxInflight;
    }
}
