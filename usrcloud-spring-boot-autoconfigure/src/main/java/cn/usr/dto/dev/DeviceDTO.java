package cn.usr.dto.dev;
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

import java.io.Serializable;

/**
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2018-03-06 23:49
 */
public class DeviceDTO implements Serializable {
    /**
     * 设备编号
     * false
     * 普通设备：最长12位,系统会自动补全至20位。不填写系统自动添加最大编号的下一条。
        LoRa集中器 :填写注册码。
        CoAP/NB-IoT 和 LoRa模块： 为MAC或IMEI
     */
    private String deviceId;
    /**
     * 设备名称
     * true
     */
    private String name;
    /**
     * 通讯密码
     * false
     * 必须为 8 位 （不填写默认用账号默认的设备通讯密码）
     */
    private String pass;
    /**
     * 硬件版本
     * false
     */
    private String hardver;
    /**
     * 软件版本
     * false
     */
    private String softver;
    /**
     * 图片地址
     * false
     */
    private String img;
    /**
     * 分组id
     * false
     * 默認0
     */
    private String groupId;
    /**
     * 合包长度
     * false
     */
    private String length;
    /**
     * 自定义地段
     * false
     */
    private String customFields;
    /**
     * 地图位置（经纬度）
     * true
     */
    private String position;
    /**
     * 地图位置（详细地址）
     */
    private String address;
    /**
     * CoAP/NB-IoT 和 LoRa模块必填
     */
    private String sn;

    /**
     *
     * @param name 名称
     * @param pass 通讯密码
     */
    public DeviceDTO(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    /**
     * 0:ModbusRTU 1:ModbusTCP 2:TCP透传
     默认设备 CoAP/NB-IoT LoRa模块 必填，LoRa集中器 非必填
     */
    private String protocol ="2";
    /**
     * 当设备类型为 默认设备/LoRa模块 且通讯协议为 ModbusRTU/ModbusTCP 时必填；
     当设备类型为 CoAP/NB-IoT 且通讯协议为 ModbusRTU/ModbusTCP 时 采集频率为-1；
     其他情况不需要填写
     */
    private Integer pollingInterval;
    /**
     * 设备类型：
     0 默认设备设备
     1 LoRa集中器
     2 CoAP/NB-IoT
     3 LoRa模块
     */
    private String type = "0";



    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getHardver() {
        return hardver;
    }

    public void setHardver(String hardver) {
        this.hardver = hardver;
    }

    public String getSoftver() {
        return softver;
    }

    public void setSoftver(String softver) {
        this.softver = softver;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getCustomFields() {
        return customFields;
    }

    public void setCustomFields(String customFields) {
        this.customFields = customFields;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Integer getPollingInterval() {
        return pollingInterval;
    }

    public void setPollingInterval(Integer pollingInterval) {
        this.pollingInterval = pollingInterval;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
