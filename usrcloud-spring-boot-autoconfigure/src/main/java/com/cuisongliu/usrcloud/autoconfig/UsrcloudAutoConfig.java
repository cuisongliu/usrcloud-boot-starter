package com.cuisongliu.usrcloud.autoconfig;
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

import cn.usr.service.UsrcloudApi;
import cn.usr.service.UsrcloudMqttCallback;
import cn.usr.service.UsrcloudMqttClient;
import cn.usr.service.impl.UsrcloudApiImpl;
import cn.usr.service.impl.UsrcloudMqttClientImpl;
import com.cuisongliu.usrcloud.autoconfig.properties.UsrcloudProperties;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * usrcloud springboot 集成
 *
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2018-02-28 22:05
 */
@Configuration
@EnableConfigurationProperties({UsrcloudProperties.class})
public class UsrcloudAutoConfig {
    private static Logger logger = LoggerFactory.getLogger(UsrcloudAutoConfig.class);
    @Autowired
    private UsrcloudMqttCallback usrcloudMqttCallback;

    @Bean("usrcloudMqttClient")
    public UsrcloudMqttClient usrcloudMqttClient(UsrcloudProperties usrcloudProperties){
        UsrcloudMqttClient adapter = new UsrcloudMqttClientImpl();
        adapter.setUsrCloudMqttCallback(usrcloudMqttCallback);
        try {
            adapter.connect(usrcloudProperties.getAccount(),usrcloudProperties.getPassword(),usrcloudProperties.getTimeout(),usrcloudProperties.getKeepAlive());
            logger.debug("连接有人云成功.");
        } catch (MqttException e) {
            logger.error("连接有人云失败.");
            System.exit(-1);
        }
        return adapter;
    }

    @Bean
    public UsrcloudApi usrCloudApi(UsrcloudProperties usrcloudProperties){
        return new UsrcloudApiImpl(usrcloudProperties.getAccount(),usrcloudProperties.getPassword());
    }

}
