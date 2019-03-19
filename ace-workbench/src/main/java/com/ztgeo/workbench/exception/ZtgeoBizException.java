package com.ztgeo.workbench.exception;

import com.github.ag.core.exception.BaseException;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * 中天吉奥业务逻辑异常类
 * Create by Wei on 2018/8/30
 */
@Slf4j
public class ZtgeoBizException extends BaseException {

    //拟定业务逻辑异常统一异常码为202
    private static final Integer BIZERRORCOED = 500;

    //默认返回业务异常信息
    private static final String BIZERRORMSG = "业务逻辑异常";

    //异常码和错误信息均未定义的异常
    private ZtgeoBizException(){
        super(BIZERRORMSG,BIZERRORCOED);
        log.error(BIZERRORMSG);
    }

    //未定义状态码及错误信息的业务异常
    public ZtgeoBizException(String msg){
        super(msg,BIZERRORCOED);
        log.error(msg);
    }

    //已定义异常状态码的异常
    public ZtgeoBizException(Integer errorCode,String msg){
        super(msg,errorCode);
        log.error(msg);
    }

}
