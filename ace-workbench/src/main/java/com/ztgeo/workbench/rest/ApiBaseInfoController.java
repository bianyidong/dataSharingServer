package com.ztgeo.workbench.rest;

import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ztgeo.workbench.biz.ApiBaseInfoBiz;
import com.ztgeo.workbench.entity.ApiBaseInfo;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("apiBaseInfo")
@CheckClientToken
@CheckUserToken
public class ApiBaseInfoController extends BaseController<ApiBaseInfoBiz, ApiBaseInfo, String> {


    /**
     * 查询所有API信息，返回API名称和标识列表
     */
    @RequestMapping(value = "getAllStepApiBaseInfo", method = RequestMethod.GET)
    public ObjectRestResponse getAllStepApiBaseInfo() {
        List<ApiBaseInfo> list = baseBiz.getAllStepApiBaseInfo();
        return new ObjectRestResponse().ok(list);
    }
    /**
     * 分页查询访问API基础信息
     */
    @RequestMapping(value = "getNotAllApiBaseInfo", method = RequestMethod.GET)
    public TableResultResponse<ApiBaseInfo> getNotAllApiBaseInfo(@RequestParam Map<String, Object> params) {
        return baseBiz.getNotAllApiBaseInfo(BaseContextHandler.getUserID(),params);
    }
    /**
     * 查询单条APi详细信息
     */
    @RequestMapping(value = "getOneApiBaseInfo/{id}", method = RequestMethod.GET)
    public ObjectRestResponse<ApiBaseInfo> getOneApiBaseInfo(@PathVariable String id) {
        ApiBaseInfo apiBaseInfo = baseBiz.getOneApiBaseInfo(id);
        return new ObjectRestResponse<ApiBaseInfo>().data(apiBaseInfo);
    }

    /**
     * 分页查询API基础信息
     */
    @RequestMapping(value = "getAllApiBaseInfo", method = RequestMethod.GET)
    public TableResultResponse<ApiBaseInfo> getAllApiBaseInfo(@RequestParam Map<String, Object> params) {
        return baseBiz.getAllApiBaseInfo(params);
    }
    /**
     * 分页查询API基础信息(通用接口管理)
     */
    @RequestMapping(value = "getAllApiBaseInfoIsNull", method = RequestMethod.GET)
    public TableResultResponse<ApiBaseInfo> getAllApiBaseInfoIsNull(@RequestParam Map<String, Object> params) {
        return baseBiz.getAllApiBaseInfoIsNull(params);
    }

    /**
     * 新增API接口
     */
    @RequestMapping(value = "addOneApiBaseInfo", method = RequestMethod.POST)
    public ObjectRestResponse<ApiBaseInfo> addOneApiBaseInfo(@RequestBody ApiBaseInfo apiBaseInfo) {
        try {
            baseBiz.addOneApiBaseInfo(apiBaseInfo);
            return new ObjectRestResponse<ApiBaseInfo>().data(apiBaseInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return new ObjectRestResponse<ApiBaseInfo>();
        }
    }

    /**
     * 新增API接口(通用接口管理)
     */
    @RequestMapping(value = "addOneApiBaseInfoNotPubkey", method = RequestMethod.POST)
    public ObjectRestResponse<ApiBaseInfo> addOneApiBaseInfoNotPubkey(@RequestBody ApiBaseInfo apiBaseInfo) {
        try {
            baseBiz.addOneApiBaseInfoNotPubkey(apiBaseInfo);
            return new ObjectRestResponse<ApiBaseInfo>().data(apiBaseInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return new ObjectRestResponse<ApiBaseInfo>();
        }
    }

    /**
     * 修改API信息
     */
    @RequestMapping(value = "updateOneApiBaseInfo/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse<ApiBaseInfo> updateOneApiBaseInfo(@PathVariable String id, @RequestBody ApiBaseInfo apiBaseInfo) {
        apiBaseInfo.setApiId(id);
        baseBiz.updateOneApiBaseInfo(apiBaseInfo);
        return new ObjectRestResponse<ApiBaseInfo>().data(apiBaseInfo);
    }

    /**
     * 删除API接口
     */
    @RequestMapping(value = "deleteOneApiBaseInfo/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse<ApiBaseInfo> deleteOneApiBaseInfo(@PathVariable String id) {
        baseBiz.deleteOneApiBaseInfo(id);
        return new ObjectRestResponse<ApiBaseInfo>();
    }

    /**
     * 查询所有api信息，包括安全接口和通用接口
     */
    @GetMapping("getAllApiInfoByPage")
    public TableResultResponse<ApiBaseInfo> getAllApiInfoByPage(@RequestParam Map<String, Object> params){
        return baseBiz.getAllApiInfoByPage(params);
    }

}