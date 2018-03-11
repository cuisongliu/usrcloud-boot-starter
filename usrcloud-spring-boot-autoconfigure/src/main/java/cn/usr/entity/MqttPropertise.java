package cn.usr.entity;
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

import cn.usr.utils.BeasUtils;

import java.util.Properties;

/**
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2018-03-08 19:52
 */
public class MqttPropertise
{
    private static Properties prop;
    public static final String SERVER_ADDRESS;
    public static final String CLIENTID_PREFIX;
    public static final String TOPIC_SUBSCRIBE_DEV_RAW;
    public static final String TOPIC_SUBSCRIBE_USER_RAW;
    public static final String TOPIC_PUBLISH_DEV_RAW;
    public static final String TOPIC_PUBLISH_USER_RAW;
    public static final String TOPIC_SUBSCRIBE_DEV_PARSED;
    public static final String TOPIC_SUBSCRIBE_USER_PARSED;
    public static final String TOPIC_PUBLISH_DEV_PARSED;
    public static final String JSON_SETDATAPOINT;
    public static final String JSON_QUERYDATAPOINT;
    public static final String DEVID = "<Id>";
    public static final String USERACCOUNT = "<Account>";
    public static final String POINTID = "%POINTID%";
    public static final String POINTVALUE = "%POINTVALUE%";
    public static final String SLAVEINDEX = "%SLAVEINDEX%";
    public static final String JSONKEY = "JsonTx";
    public static final int SUCCESS = 0;
    public static final int FAILE = 1;
    public static final int CONNECTCOMPLETE = 2;
    public static final int CONNECTBREAK = 3;

    public static final String API_ADDRESS;
    static {
        MqttPropertise.prop = BeasUtils.getPropertie(MqttPropertise.class, "mqtt.properties");

        SERVER_ADDRESS = MqttPropertise.prop.getProperty("server_address");
        CLIENTID_PREFIX = MqttPropertise.prop.getProperty("clientid_prefix");
        TOPIC_SUBSCRIBE_DEV_RAW = MqttPropertise.prop.getProperty("topic_subscribe_dev_raw");
        TOPIC_SUBSCRIBE_USER_RAW = MqttPropertise.prop.getProperty("topic_subscribe_user_raw");
        TOPIC_PUBLISH_DEV_RAW = MqttPropertise.prop.getProperty("topic_publish_dev_raw");
        TOPIC_PUBLISH_USER_RAW = MqttPropertise.prop.getProperty("topic_publish_user_raw");
        TOPIC_SUBSCRIBE_DEV_PARSED = MqttPropertise.prop.getProperty("topic_subscribe_dev_parsed");
        TOPIC_SUBSCRIBE_USER_PARSED = MqttPropertise.prop.getProperty("topic_subscribe_user_parsed");
        TOPIC_PUBLISH_DEV_PARSED = MqttPropertise.prop.getProperty("topic_publish_dev_parsed");
        JSON_SETDATAPOINT = MqttPropertise.prop.getProperty("json_setDataPoint");
        JSON_QUERYDATAPOINT = MqttPropertise.prop.getProperty("json_queryDataPoint");
        API_ADDRESS= MqttPropertise.prop.getProperty("api_address");
    }
}
