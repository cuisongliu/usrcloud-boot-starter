package cn.usr.dto.user.login;
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


import cn.usr.dto.RequestUrlGenerator;
import cn.usr.utils.BeasUtils;

import java.io.Serializable;

/**
 * 登录类
 * @author cuisongliu [cuisongliu@qq.com]
 * @since 2018-03-06 22:09
 */
public class LoginRequestDTO implements Serializable, RequestUrlGenerator {
    /**
     * 账号
     */
    private String account;
    /**
     * 密码 MD5( password )
     */
    private String password;

    public LoginRequestDTO(String account, String password) {
        this.account = account;
        setPassword(password);
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = BeasUtils.getMD5(password);
    }

    @Override
    public String generatorUrl() {
        return "/user/login";
    }
}