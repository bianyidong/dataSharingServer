package com.ztgeo.workbench.rest;

import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ztgeo.workbench.biz.ApiAuthRelBiz;
import com.ztgeo.workbench.entity.ApiAuthRel;
import com.ztgeo.workbench.entity.ApiBaseInfo;
import com.ztgeo.workbench.entity.User;
import com.ztgeo.workbench.feign.AdminFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("apiAuthRel")
@CheckClientToken
@CheckUserToken
public class ApiAuthRelController extends BaseController<ApiAuthRelBiz,ApiAuthRel,String> {

    @Autowired
    private AdminFeign adminFeign;

    /**
     * 分页查询单个用户所能访问的所有API信息
     */
    @RequestMapping(value="getAPIInfoByUserId",method = RequestMethod.GET)
    public TableResultResponse<ApiBaseInfo> getAPIInfoByUserId(@RequestParam Map<String, Object> params){
        return baseBiz.getAPIInfoByUserId(BaseContextHandler.getUserID(),params);
    }


    /**
     * 获取单个API用户权限列表
     * @param id apiId
     */
    @RequestMapping(value = "getAllUserAuth/{id}", method = RequestMethod.GET)
    public ObjectRestResponse<JSONObject> getAllUserAuth(@PathVariable String id) {
        // 查询所有用户
        List<User> userList = adminFeign.all();
        JSONObject userAuthJson = baseBiz.getAllUserAuth(userList,id);
        return new ObjectRestResponse<JSONObject>().data(userAuthJson);
    }

    /**
     * 更新用户权限信息
     * @param id api_pubkey
     */
    @RequestMapping(value = "updateUserAuth/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse<String> updateUserAuth(@RequestBody String json,@PathVariable() String id) {
        baseBiz.updateUserAuth(json,id);
        return new ObjectRestResponse<String>().data("");
    }

}