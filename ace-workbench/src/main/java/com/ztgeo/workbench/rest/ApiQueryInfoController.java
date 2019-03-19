package com.ztgeo.workbench.rest;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ztgeo.workbench.biz.ApiQueryInfoBiz;
import com.ztgeo.workbench.entity.ApiBaseInfo;
import com.ztgeo.workbench.entity.ApiQueryInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

@RestController
@RequestMapping("apiQueryInfo")
@CheckClientToken
@CheckUserToken
public class ApiQueryInfoController extends BaseController<ApiQueryInfoBiz,ApiQueryInfo,String> {

    /**
     *  删除API接口 query信息
     */
    @RequestMapping(value="deleteOneApiQueryInfo/{id}",method = RequestMethod.DELETE)
    public ObjectRestResponse<ApiQueryInfo> deleteApiQueryInfo(@PathVariable String id){
        baseBiz.deleteById(id);
        return new ObjectRestResponse<ApiQueryInfo>();
    }

}