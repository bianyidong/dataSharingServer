/*
 *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>

 *  AG-Enterprise 企业版源码
 *  郑重声明:
 *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  老A将追究授予人和传播人的法律责任!

 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.ztgeo.workbench.exception.handle;

import com.github.wxiaoqi.security.common.msg.BaseResponse;
import com.ztgeo.workbench.exception.ZtgeoBizException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常拦截处理器
 *
 * @author ace
 * @version 2017/9/8
 */
@ControllerAdvice("com.ztgeo.workbench")
@ResponseBody
public class GlobalExceptionHandler {

    private static final String GLOBALEXCEPTIONMSG = "系统异常，请联系管理员";

    // 全局500异常 杜绝底层错误详情抛至前台
    @ExceptionHandler(Exception.class)
    public BaseResponse ExceptionHandler(HttpServletResponse response, Exception ex) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); //外层status的设置
        return new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), GLOBALEXCEPTIONMSG);
    }

    //返回的HTTP异常状态码为500
    @ExceptionHandler(ZtgeoBizException.class)
    public BaseResponse cXBusyException(ZtgeoBizException ex) {
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }
}
