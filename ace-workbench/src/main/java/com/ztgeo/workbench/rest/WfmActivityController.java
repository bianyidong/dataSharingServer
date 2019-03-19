package com.ztgeo.workbench.rest;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ztgeo.workbench.biz.WfmActivityBiz;
import com.ztgeo.workbench.entity.ApiBaseInfo;
import com.ztgeo.workbench.entity.WfmActivity;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.util.Map;

@RestController
@RequestMapping("wfmActivity")
@CheckClientToken
@CheckUserToken
public class WfmActivityController extends BaseController<WfmActivityBiz,WfmActivity,Integer> {

    /**
     * 分页查询API基础信息
     */
    @RequestMapping(value = "getAllActivityInfo", method = RequestMethod.GET)
    public TableResultResponse<WfmActivity> getAllActivityInfo(@RequestParam Map<String, Object> params) {
        return baseBiz.getAllActivityInfo(params);
    }

    /**
     * 根据业务编号查询流程图json信息
     * @param businessNo 业务编号
     */
    @GetMapping("getImageJsonByBusinessNo/{businessNo}")
    public ObjectRestResponse getImageJsonByBusinessNo(@PathVariable("businessNo") String businessNo){
        WfmActivity wfmActivity = baseBiz.getImageJsonByBusinessNo(businessNo);
        return new ObjectRestResponse<WfmActivity>().data(wfmActivity);
    }

}