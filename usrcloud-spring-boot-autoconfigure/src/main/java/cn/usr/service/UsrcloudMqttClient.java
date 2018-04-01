package cn.usr.service;
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

import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2018-03-08 19:53
 */
public interface UsrcloudMqttClient {

    void setUsrCloudMqttCallback(final UsrcloudMqttCallback p0);

    /**
     * 初始化客户端
     */
    void connect() throws MqttException;

    /**
     * 断开连接
     */
    boolean disConnectUnCheck() throws MqttException;

    /**
     * 云交换机操作
     * subscribeForDevId 订阅消息( 单个设备 )
     * 无返回值	最终订阅结果要通过 onSubscribeAck 设置的回调函数来判断。
     *
     * @param devId 设备ID。指定要订阅哪个设备发来的消息。
     * @throws MqttException
     */
    void subscribeForDevId(final String devId) throws MqttException;

    /**
     * 云组态操作
     * 订阅已解析的消息(单个设备)
     *
     * @param devId 设备ID。指定要订阅哪个设备发来的消息。
     *              无返回值	最终订阅结果要通过 onSubscribeAck 设置的回调函数来判断。
     * @throws MqttException
     */
    void subscribeParsedByDevId(final String devId) throws MqttException;

    /**
     * 云交换机操作
     * 订阅消息( 账户的所有设备 )
     * 无返回值	最终订阅结果要通过 onSubscribeAck 设置的回调函数来判断。
     *
     * @throws MqttException
     */
    void subscribeForUsername() throws MqttException;

    /**
     * 云组态操作
     * 订阅消息( 账户的所有设备的监控状态变化 )
     *
     * @throws MqttException
     */
    void subscribeParsedForUsername() throws MqttException;

    /**
     * 云交换机操作
     * disSubscribeForDevId 取消订阅( 单个设备 )
     *
     * @param devId 设备ID。指定要取消订阅哪个设备。
     *              最取消订阅结果要通过 onDisSubscribeAck 设置的回调函数来判断
     * @throws MqttException
     */
    void disSubscribeForDevId(final String devId) throws MqttException;

    /**
     * 云组态操作
     * 取消订阅已解析的消息( 单个设备 )
     *
     * @param devId 设备ID。指定要取消订阅哪个设备。
     * @throws MqttException
     */
    void disSubscribeParsedForDevId(final String devId) throws MqttException;

    /**
     * 云交换机操作
     * disSubscribeForUsername 取消订阅( 账户下的所有设备 )
     * 最取消订阅结果要通过 onDisSubscribeAck 设置的回调函数来判断
     *
     * @throws MqttException
     */
    void disSubscribeForUsername() throws MqttException;

    /**
     * 云组态操作
     * 取消订阅账号下的所有设备的监控状态变化( 账户下的所有设备 )
     *
     * @throws MqttException
     */
    void disSubscribeParsedForUsername() throws MqttException;

    /**
     * 云交换操作
     * 推送数据（向单个设备）
     *
     * @param devId 设备ID,指定要把数据发给哪个设备。只能填一个。
     * @param data  要发送的数据。
     * @throws MqttException
     */
    void publishForDevId(final String devId, final byte[] data) throws MqttException;

    /**
     * 云交换操作
     * 推送数据（向账户下的所有设备）
     *
     * @param data 要发送的数据。
     * @throws MqttException
     */
    void publishForUsername(final byte[] data) throws MqttException;

    /**
     * 云组态操作
     * 设置单个数据点值
     *
     * @param devId      设备ID
     * @param slaveIndex 从机序号
     * @param pointId    数据点ID
     * @param value      数据点的值
     * @throws MqttException
     */
    void publishParsedSetDataPoint(final String devId, final String slaveIndex, final String pointId, final String value) throws MqttException;

    /**
     * 云组态操作
     * 查询单个数据点值
     *
     * @param devId      设备ID
     * @param slaveIndex 从机序号
     * @param pointId    数据点ID
     * @throws MqttException
     */
    void publishParsedQueryDataPoint(final String devId, final String slaveIndex, final String pointId) throws MqttException;
}
