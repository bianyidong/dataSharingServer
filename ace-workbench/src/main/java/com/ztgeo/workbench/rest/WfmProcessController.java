package com.ztgeo.workbench.rest;

import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ztgeo.workbench.biz.WfmProcessBiz;
import com.ztgeo.workbench.entity.WfmProcess;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.util.List;

@RestController
@RequestMapping("wfmProcess")
@CheckClientToken
@CheckUserToken
public class WfmProcessController extends BaseController<WfmProcessBiz, WfmProcess, String> {

    /**
     * 新增模板信息
     *
     * @param processInfo 模板信息(流程图json、流程基本信息、步骤信息)
     * @return 返回添加成功或失败
     */
    @PostMapping("addNewProcess")
    public ObjectRestResponse addNewProcess(@RequestBody String processInfo) {
        boolean result = baseBiz.addSimpleNewProcess(processInfo);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        if (result) {
            objectRestResponse.setStatus(200);
        } else {
            objectRestResponse.setStatus(500);
        }
        return objectRestResponse;
    }

    /**
     * 获取模板名称列表
     */
    @GetMapping("getAllProcessName")
    public List<WfmProcess> getAllProcessName() {
        return baseBiz.getAllProcessName();
    }

    /**
     * 根据模板ID查询模板所有信息
     */
    @GetMapping("getProcessInfoById/{pId}")
    public ObjectRestResponse getProcessInfoById(@PathVariable("pId") String pId) {
        JSONObject jsonObject = baseBiz.getSimpleProcessInfoById(pId);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.setData(jsonObject);
        return objectRestResponse;
    }

    /**
     * 根据模板ID修改模板信息
     *
     * @param pId         模板主键ID
     * @param processInfo 模板信息
     * @return
     */
    @PutMapping("updateProcessInfoById/{pId}")
    public ObjectRestResponse updateProcessInfoById(@PathVariable("pId") String pId, @RequestBody String processInfo) {
        baseBiz.updateProcessInfoById(pId, processInfo);
        return ObjectRestResponse.ok();
    }

}