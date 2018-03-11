package cn.usr.dto.dev.device;
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


import cn.usr.dto.BasicRequestDTO;
import cn.usr.dto.dev.DeviceDTO;

import java.io.Serializable;

/**
 * 请求参数
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2018-03-06 22:09
 */
public class AddDeviceRequestDTO extends BasicRequestDTO implements Serializable {
    /**
     * 设备的所属用户，用于一级账号给二级账号添加设备，不填写默认为当前登录账号 （非必填）
     */
    private String subAccount;
    /**
     * 設備信息
     */
    private DeviceDTO device;

    public AddDeviceRequestDTO(String token,String name, String pass) {
        super(token);
        this.device = new DeviceDTO(name,pass);
    }

    public DeviceDTO getDevice() {
        return device;
    }

    public void setDevice(DeviceDTO device) {
        this.device = device;
    }

    public String getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

    @Override
    public String generatorUrl() {
        return "/dev/addDevice";
    }

}