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

/**
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2018-03-08 19:53
 */
public interface UsrcloudMqttCallback
{
    /**
     *  连接响应回调函数
     *  0x00	连接成功
        0x01	连接断开
        0x02	与服务器完成连接
        0x03	与服务器连接断开
     * @param returnCode 返回码
     * @param description 返回码代表的含义
     */
    void onConnectAck(final int returnCode, final String description);

    /**
     * 订阅响应 回调函数
     * 0x00	订阅成功
       0x01	订阅失败
     * @param messageId 消息ID。
     * @param clientId  客户端ID。
     * @param topics  订阅主题
     * @param returnCode 返回码
     */
    void onSubscribeAck(final int messageId, final String clientId, final String topics, final int returnCode);

    /**
     * 取消订阅响应 回调函数
     * 0x00	取消订阅成功
       0x01	取消订阅失败
     * @param messageId 消息ID。
     * @param clientId 客户端ID。
     * @param topics  	订阅主题
     * @param returnCode 返回码
     */
    void onDisSubscribeAck(final int messageId, final String clientId, final String topics, final int returnCode);

    /**
     * 交换机操作
     * 收到数据 回调函数
     * @param messageId 消息ID
     * @param topic 主题消息来源
     * @param data 收到的数据
     */
    void onReceiveEvent(final int messageId, final String topic, final byte[] data);

    /**
     *  云组态操作
     *  收到已解析数据的回调函数
     * @param messageId 消息ID
     * @param topic 主题消息来源。
     * @param jsonData 已解析的收到的数据
     */
    void onReceiveParsedEvent(final int messageId, final String topic, final String jsonData);

    /**
     * 推送响应 回调函数
     * true	    推送成功
       false	推送失败
     * @param messageId  消息ID。
     * @param topic  推送主题
     * @param isSuccess  推送标识符。
     */
    void onPublishDataAck(final int messageId, final String topic, final boolean isSuccess);

    /**
     * 本次推送结果回调函数
     * @param messageId  消息ID
     * @param topic  本次推送主题
     */
    void onPublishDataResult(final int messageId, final String topic);
}

