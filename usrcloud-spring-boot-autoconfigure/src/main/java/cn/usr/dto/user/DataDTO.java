package cn.usr.dto.user;
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
 * 登录返回数据
 * 登录成功返回信息 data（json 格式字符串）中包含 token，
 * 注意 token 的有效时间为两小时，uid 为账号在数据库中的 ID 编号。
 * 新注册的帐号需要验证邮箱后才可使用，需要重发邮件的可通过 uid 使用重发激活邮件接口重新激活。
 * 如果因为未激活导致登录失败：data（json 格式字符串）中包含账号 id 用于发送激活邮件。
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2018-03-06 22:28
 */
public class DataDTO implements Serializable {

    /**
     *  用于请求其他接口验证身份的字段。有效时间为 2 小时
     */
    private String token;
    /**
     *  用于加密 sign 的密钥，可忽略。
     */
    private String signCode;
    /**
     *  用户在数据库中对应的 id
     */
    private String uid;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSignCode() {
        return signCode;
    }

    public void setSignCode(String signCode) {
        this.signCode = signCode;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
