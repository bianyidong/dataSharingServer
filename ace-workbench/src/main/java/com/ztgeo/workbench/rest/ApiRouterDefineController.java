package com.ztgeo.workbench.rest;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ztgeo.workbench.biz.ApiRouterDefineBiz;
import com.ztgeo.workbench.entity.ApiRouterDefine;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("apiRouterDefine")
@CheckClientToken
@CheckUserToken
public class ApiRouterDefineController extends BaseController<ApiRouterDefineBiz, ApiRouterDefine, String> {

    /**
     * 新增router
     */
    @RequestMapping(value = "addOneApiRouterDefine", method = RequestMethod.POST)
    public ObjectRestResponse<ApiRouterDefine> addOneApiRouterDefine(@RequestBody ApiRouterDefine apiRouterDefine) {
        apiRouterDefine = baseBiz.addOneApiRouterDefine(apiRouterDefine);
        return new ObjectRestResponse<ApiRouterDefine>().data(apiRouterDefine);
    }

    /**
     * 查询router映射列表
     */
    @RequestMapping(value = "getAllApiRouterDefine", method = RequestMethod.GET)
    public TableResultResponse<ApiRouterDefine> getAllApiRouterDefine(@RequestParam Map<String, Object> params) {
        return baseBiz.getAllApiRouterDefine(params);
    }


}