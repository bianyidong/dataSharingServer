package com.ztgeo.workbench.rest;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ztgeo.workbench.biz.ApiBodyInfoBiz;
import com.ztgeo.workbench.entity.ApiBodyInfo;
import com.ztgeo.workbench.entity.ApiQueryInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

@RestController
@RequestMapping("apiBodyInfo")
@CheckClientToken
@CheckUserToken
public class ApiBodyInfoController extends BaseController<ApiBodyInfoBiz,ApiBodyInfo,String> {

    /**
     *  删除API接口 body信息
     */
    @RequestMapping(value="deleteOneApiBodyInfo/{id}",method = RequestMethod.DELETE)
    public ObjectRestResponse<ApiBodyInfo> deleteApiBodyInfo(@PathVariable String id){
        baseBiz.deleteById(id);
        return new ObjectRestResponse<ApiBodyInfo>();
    }

}