package com.ztgeo.workbench.rest;

import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.ztgeo.workbench.biz.DataBackUpBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 处理数据备份
 */
@RestController
@RequestMapping("dataBackUp")
@CheckClientToken
@CheckUserToken
public class DataBackUpController{

    @Autowired
    private DataBackUpBiz dataBackUpBiz;

    /**
     * 根据用户登录名查询发送的信息和接收的信息(分页查询)
     */
    @GetMapping("getBackupDataByPage")
    public TableResultResponse<JSONObject> getBackupDataByPage(@RequestParam Map<String, Object> params){
        try{
            return dataBackUpBiz.getApiAccessDataDetail(params);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据用户登录名查询通知信息
     */
    @RequestMapping(value = "getApiNoticeDataDetail", method = RequestMethod.GET)
    public TableResultResponse<JSONObject> getApiNoticeDataDetail(@RequestParam Map<String, Object> params){
        try{
            return dataBackUpBiz.getApiNoticeDataDetail(params);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
