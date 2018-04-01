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

import cn.usr.entity.MqttPropertise;
import cn.usr.service.UsrcloudMqttCallback;
import cn.usr.service.UsrcloudMqttClient;
import cn.usr.utils.BeasUtils;
import com.cuisongliu.usrcloud.autoconfig.properties.UsrcloudProperties;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2018-03-08 19:54
 */
public class UsrcloudMqttClientImpl implements MqttCallbackExtended, UsrcloudMqttClient{
    private Logger logger = LoggerFactory.getLogger(UsrcloudMqttClient.class);
    private UsrcloudMqttCallback usrCloudMqttCallback;
    private volatile MqttAsyncClient mqttAsyncClient;
    private final UsrcloudProperties properties;

    public UsrcloudMqttClientImpl(UsrcloudProperties properties) {
        this.properties = properties;
    }

    @Override
    public void setUsrCloudMqttCallback(final UsrcloudMqttCallback usrCloudMqttCallback) {
        this.usrCloudMqttCallback = usrCloudMqttCallback;
    }

    @Override
    public void connect() throws MqttException {
        final String clientId = (MqttPropertise.CLIENTID_PREFIX + properties.getAccount()).trim();
        this.mqttAsyncClient = new MqttAsyncClient(MqttPropertise.SERVER_ADDRESS, clientId, new MemoryPersistence());
        final MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(properties.getCleanSession());
        options.setUserName(properties.getAccount());
        options.setPassword(BeasUtils.getMD5(properties.getPassword()).toCharArray());
        options.setConnectionTimeout(properties.getTimeout());
        options.setKeepAliveInterval(properties.getKeepAlive());
        options.setAutomaticReconnect(properties.getAutomaticReconnect());
        this.mqttAsyncClient.setCallback(this);
        this.mqttAsyncClient.connect(options, null, new IMqttActionListener() {
            @Override
            public void onSuccess(final IMqttToken token) {
                if (UsrcloudMqttClientImpl.this.usrCloudMqttCallback != null) {
                    UsrcloudMqttClientImpl.this.usrCloudMqttCallback.onConnectAck(0, "\u8fde\u63a5\u6210\u529f");
                }
            }

            @Override
            public void onFailure(final IMqttToken token, final Throwable throwable) {
                if (UsrcloudMqttClientImpl.this.usrCloudMqttCallback != null) {
                    UsrcloudMqttClientImpl.this.usrCloudMqttCallback.onConnectAck(1, throwable.toString());
                }
            }
        });
    }

    @Override
    public boolean disConnectUnCheck() throws MqttException {
        if (this.mqttAsyncClient == null && !this.mqttAsyncClient.isConnected()) {
            return false;
        }
        this.mqttAsyncClient.disconnect();
        return true;
    }

    @Override
    public void subscribeForDevId(final String devId) throws MqttException {
        if (this.mqttAsyncClient == null && !this.mqttAsyncClient.isConnected()) {
            throw new MqttException(32104);
        }
        final String topic = MqttPropertise.TOPIC_SUBSCRIBE_DEV_RAW.replaceAll("<Id>", devId);
        this.subscribe(topic);
    }

    @Override
    public void subscribeForUsername() throws MqttException {
        if (this.mqttAsyncClient == null && !this.mqttAsyncClient.isConnected()) {
            throw new MqttException(32104);
        }
        final String topic = MqttPropertise.TOPIC_SUBSCRIBE_USER_RAW.replaceAll("<Account>", this.properties.getAccount());
        this.subscribe(topic);
    }

    @Override
    public void subscribeParsedByDevId(final String devId) throws MqttException {
        if (this.mqttAsyncClient == null && !this.mqttAsyncClient.isConnected()) {
            throw new MqttException(32104);
        }
        final String topic = MqttPropertise.TOPIC_SUBSCRIBE_DEV_PARSED.replaceAll("<Id>", devId);
        this.subscribe(topic);
    }

    @Override
    public void subscribeParsedForUsername() throws MqttException {
        if (this.mqttAsyncClient == null && !this.mqttAsyncClient.isConnected()) {
            throw new MqttException(32104);
        }
        final String topic = MqttPropertise.TOPIC_SUBSCRIBE_USER_PARSED.replaceAll("<Account>", this.properties.getAccount());
        this.subscribe(topic);
    }

    private void subscribe(final String topic) throws MqttException {
        final int[] Qos = { 0 };
        final String[] topics = { topic.trim() };
        this.mqttAsyncClient.subscribe(topics, Qos).setActionCallback(new IMqttActionListener() {
            @Override
            public void onSuccess(final IMqttToken iMqttToken) {
                if (UsrcloudMqttClientImpl.this.usrCloudMqttCallback != null) {
                    UsrcloudMqttClientImpl.this.usrCloudMqttCallback.onSubscribeAck(iMqttToken.getMessageId(), iMqttToken.getClient().getClientId(), Arrays.toString(iMqttToken.getTopics()), 0);
                }
            }

            @Override
            public void onFailure(final IMqttToken iMqttToken, final Throwable throwable) {
                if (UsrcloudMqttClientImpl.this.usrCloudMqttCallback != null) {
                    UsrcloudMqttClientImpl.this.usrCloudMqttCallback.onSubscribeAck(iMqttToken.getMessageId(), iMqttToken.getClient().getClientId(), Arrays.toString(iMqttToken.getTopics()), 1);
                }
            }
        });
    }

    @Override
    public void disSubscribeForDevId(final String devId) throws MqttException {
        if (this.mqttAsyncClient == null && !this.mqttAsyncClient.isConnected()) {
            throw new MqttException(32104);
        }
        final String topic = MqttPropertise.TOPIC_SUBSCRIBE_DEV_RAW.replaceAll("<Id>", devId);
        this.unSubscribe(topic);
    }

    @Override
    public void disSubscribeForUsername() throws MqttException {
        if (this.mqttAsyncClient == null && !this.mqttAsyncClient.isConnected()) {
            throw new MqttException(32104);
        }
        final String topic = MqttPropertise.TOPIC_SUBSCRIBE_USER_RAW.replaceAll("<Account>", this.properties.getAccount());
        this.unSubscribe(topic);
    }

    @Override
    public void disSubscribeParsedForDevId(final String devId) throws MqttException {
        if (this.mqttAsyncClient == null && !this.mqttAsyncClient.isConnected()) {
            throw new MqttException(32104);
        }
        final String topic = MqttPropertise.TOPIC_SUBSCRIBE_DEV_PARSED.replaceAll("<Id>", devId);
        this.unSubscribe(topic);
    }

    @Override
    public void disSubscribeParsedForUsername() throws MqttException {
        if (this.mqttAsyncClient == null && !this.mqttAsyncClient.isConnected()) {
            throw new MqttException(32104);
        }
        final String topic = MqttPropertise.TOPIC_SUBSCRIBE_USER_PARSED.replaceAll("<Account>", this.properties.getAccount());
        this.unSubscribe(topic);
    }

    private void unSubscribe(final String topic) throws MqttException {
        this.mqttAsyncClient.unsubscribe(topic).setActionCallback(new IMqttActionListener() {
            @Override
            public void onSuccess(final IMqttToken iMqttToken) {
                if (UsrcloudMqttClientImpl.this.usrCloudMqttCallback != null) {
                    UsrcloudMqttClientImpl.this.usrCloudMqttCallback.onDisSubscribeAck(iMqttToken.getMessageId(), iMqttToken.getClient().getClientId(), Arrays.toString(iMqttToken.getTopics()), 0);
                }
            }

            @Override
            public void onFailure(final IMqttToken iMqttToken, final Throwable throwable) {
                if (UsrcloudMqttClientImpl.this.usrCloudMqttCallback != null) {
                    UsrcloudMqttClientImpl.this.usrCloudMqttCallback.onDisSubscribeAck(iMqttToken.getMessageId(), iMqttToken.getClient().getClientId(), Arrays.toString(iMqttToken.getTopics()), 1);
                }
            }
        });
    }

    @Override
    public void publishForDevId(final String devId, final byte[] data) throws MqttException {
        if (this.mqttAsyncClient == null && !this.mqttAsyncClient.isConnected()) {
            throw new MqttException(32104);
        }
        final String topic = MqttPropertise.TOPIC_PUBLISH_DEV_RAW.replaceAll("<Id>", devId);
        this.publishData(topic, data);
    }

    @Override
    public void publishForUsername(final byte[] data) throws MqttException {
        if (this.mqttAsyncClient == null && !this.mqttAsyncClient.isConnected()) {
            throw new MqttException(32104);
        }
        final String topic = MqttPropertise.TOPIC_PUBLISH_USER_RAW.replaceAll("<Account>", this.properties.getAccount());
        this.publishData(topic, data);
    }

    @Override
    public void publishParsedSetDataPoint(final String devId, final String slaveIndex, final String pointId, final String value) throws MqttException {
        if (this.mqttAsyncClient == null && !this.mqttAsyncClient.isConnected()) {
            throw new MqttException(32104);
        }
        final String topic = MqttPropertise.TOPIC_PUBLISH_DEV_PARSED.replaceAll("<Id>", devId);
        final String data = MqttPropertise.JSON_SETDATAPOINT.replaceAll("%SLAVEINDEX%", slaveIndex).replaceAll("%POINTID%", pointId).replaceAll("%POINTVALUE%", value);
        this.publishData(topic, data.getBytes());
    }

    @Override
    public void publishParsedQueryDataPoint(final String devId, final String slaveIndex, final String pointId) throws MqttException {
        if (this.mqttAsyncClient == null && !this.mqttAsyncClient.isConnected()) {
            throw new MqttException(32104);
        }
        final String topic = MqttPropertise.TOPIC_PUBLISH_DEV_PARSED.replaceAll("<Id>", devId);
        final String data = MqttPropertise.JSON_QUERYDATAPOINT.replaceAll("%SLAVEINDEX%", slaveIndex).replaceAll("%POINTID%", pointId);
        this.publishData(topic, data.getBytes());
    }

    private void publishData(final String topic, final byte[] data) throws MqttException {
        final MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(1);
        mqttMessage.setRetained(true);
        mqttMessage.setPayload(data);
        final IMqttDeliveryToken publish = this.mqttAsyncClient.publish(topic, mqttMessage);
        if (this.usrCloudMqttCallback != null) {
            this.usrCloudMqttCallback.onPublishDataResult(publish.getMessageId(), Arrays.toString(publish.getTopics()));
        }
        publish.setActionCallback(new IMqttActionListener() {
            @Override
            public void onSuccess(final IMqttToken iMqttToken) {
                if (UsrcloudMqttClientImpl.this.usrCloudMqttCallback != null) {
                    UsrcloudMqttClientImpl.this.usrCloudMqttCallback.onPublishDataAck(iMqttToken.getMessageId(), Arrays.toString(iMqttToken.getTopics()), true);
                }
            }

            @Override
            public void onFailure(final IMqttToken iMqttToken, final Throwable throwable) {
                if (UsrcloudMqttClientImpl.this.usrCloudMqttCallback != null) {
                    UsrcloudMqttClientImpl.this.usrCloudMqttCallback.onPublishDataAck(iMqttToken.getMessageId(), Arrays.toString(iMqttToken.getTopics()), true);
                }
            }
        });
    }

    @Override
    public void connectionLost(final Throwable cause) {
        logger.error("MQTT连接断开",cause);
        if (this.usrCloudMqttCallback != null) {
            if (this.properties.getAutomaticReconnect()){
                //如果设置了自动重连
                try {
                    this.connect();
                } catch (MqttException e) {
                    this.usrCloudMqttCallback.onConnectAck(3, e.toString());
                }
            }else {
                this.usrCloudMqttCallback.onConnectAck(3, cause.toString());
            }

        }
    }

    @Override
    public void deliveryComplete(final IMqttDeliveryToken token) {
    }

    @Override
    public void messageArrived(final String topic, final MqttMessage message) throws Exception {
        if (this.usrCloudMqttCallback != null) {
            if (topic.contains("JsonTx")) {
                this.usrCloudMqttCallback.onReceiveParsedEvent(message.getId(), topic, message.toString());
            }
            else {
                this.usrCloudMqttCallback.onReceiveEvent(message.getId(), topic, message.getPayload());
            }
        }
    }

    @Override
    public void connectComplete(final boolean b, final String s) {
        if (this.usrCloudMqttCallback != null) {
            this.usrCloudMqttCallback.onConnectAck(2, "\u4e0e\u670d\u52a1\u5668\u5b8c\u6210\u8fde\u63a5");
        }
    }
}
