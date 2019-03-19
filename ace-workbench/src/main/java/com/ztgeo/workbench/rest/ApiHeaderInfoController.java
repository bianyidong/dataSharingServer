package com.ztgeo.workbench.rest;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ztgeo.workbench.biz.ApiHeaderInfoBiz;
import com.ztgeo.workbench.entity.ApiHeaderInfo;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

@RestController
@RequestMapping("apiHeaderInfo")
@CheckClientToken
@CheckUserToken
public class ApiHeaderInfoController extends BaseController<ApiHeaderInfoBiz,ApiHeaderInfo,String> {

    /**
     *  删除API Header信息
     */
    @RequestMapping(value="deleteOneApiHeaderInfo/{id}",method = RequestMethod.DELETE)
    public ObjectRestResponse<ApiHeaderInfo> deleteOneApiHeaderInfo(@PathVariable String id){
        baseBiz.deleteById(id);
        return new ObjectRestResponse<ApiHeaderInfo>();
    }

}