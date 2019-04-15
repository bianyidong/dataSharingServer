package com.ztgeo.workbench.rest;

import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ztgeo.workbench.biz.ApiAccessRecordBiz;
import com.ztgeo.workbench.entity.ApiAccessRecord;
import com.ztgeo.workbench.entity.ApiBaseInfo;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("apiAccessRecord")
@CheckClientToken
@CheckUserToken
public class ApiAccessRecordController extends BaseController<ApiAccessRecordBiz, ApiAccessRecord, String> {

    /**
     * 查询最近一周每天的接口访问次数
     *
     * @return 折线图横轴纵轴数据
     */
    @RequestMapping(value = "getApiAccessWeekRecord", method = RequestMethod.GET)
    public ObjectRestResponse<JSONObject> getApiAccessWeekRecord() {
        JSONObject resultJson = baseBiz.getApiAccessWeekRecord();
        return new ObjectRestResponse<JSONObject>().data(resultJson);
    }

    /**
     * 查询单条APi详细信息
     */
    @RequestMapping(value = "getOneApiAccessRecord/{id}", method = RequestMethod.GET)
    public ObjectRestResponse<ApiAccessRecord> getOneApiAccessRecord(@PathVariable String id) {
        ApiAccessRecord apiAccessRecord= baseBiz.getOneApiAccessRecord(id);
        System.out.println(apiAccessRecord);
        return new ObjectRestResponse<ApiAccessRecord>().data(apiAccessRecord);
    }

    /**
     * 分页查询通用接口
     *
     * @param params 分页参数
     */
    @GetMapping("getApiAccessRecordByPage")
    public TableResultResponse<ApiAccessRecord> getApiAccessRecordByPage(@RequestParam Map<String, Object> params) {
        return baseBiz.getApiAccessRecordByPage(params);
    }


    /**
     * 分页查询安全接口
     */
    @GetMapping("getSafeApiAccessRecordByPage")
    public TableResultResponse<ApiAccessRecord> getSafeApiAccessRecordByPage(@RequestParam Map<String, Object> params) {
        return baseBiz.getSafeApiAccessRecordByPage(params);
    }

}