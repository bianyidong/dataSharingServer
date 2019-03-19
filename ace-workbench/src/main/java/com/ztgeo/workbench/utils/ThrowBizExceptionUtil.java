package com.ztgeo.workbench.utils;

import com.ztgeo.workbench.exception.ZtgeoBizException;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * 用于封装扔出业务异常
 * Create by Wei on 2018/9/6
 */
public class ThrowBizExceptionUtil {

    /**
     *@描述  对象为null的判断
     *@参数  [object, errorMsg]
     *@返回值  void
     *@创建人  Wei
     *@创建时间  2018/9/6
     */
    public static void checkObjIsNull(Object object,String errorMsg){
        if (object==null){
            throw new ZtgeoBizException(errorMsg);
        }

    }


    /**
     *@描述  检查字符串是否是blank的方法
     *@参数  [strObj, errorMsg]
     *@返回值  void
     *@创建人  Wei
     *@创建时间  2018/9/6
     */
    public static void checkStrIsBlank(String strObj,String errorMsg){
        if (StringUtils.isBlank(strObj)){
            throw new ZtgeoBizException(errorMsg);
        }

    }
}
