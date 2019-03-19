package com.ztgeo.workbench.biz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ztgeo.workbench.common.ProcessStatusEnum;
import com.ztgeo.workbench.entity.WfmProcessApiStepRel;
import com.ztgeo.workbench.entity.WfmProcessStep;
import com.ztgeo.workbench.exception.ZtgeoBizException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztgeo.workbench.entity.WfmProcess;
import com.ztgeo.workbench.mapper.WfmProcessMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * 模板表
 *
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-11-26 15:26:20
 */
@Service
public class WfmProcessBiz extends BusinessBiz<WfmProcessMapper,WfmProcess> {

    @Autowired
    private WfmProcessStepBiz wfmProcessStepBiz;

    @Autowired
    private WfmProcessApiStepRelBiz wfmProcessApiStepRelBiz;

    /**
     * 新建模板
     * @param processInfo 模板信息
     * @return 保存成功返回true，否则返回false
     */
    public boolean addSimpleNewProcess(String processInfo) {
        // 检查是否非空
        if(StringUtils.isBlank(processInfo)){
            return false;
        }
        // 解析模板数据
        JSONObject processJson = JSONObject.parseObject(processInfo);
        JSONObject diagramJson= processJson.getJSONObject("diagramJson"); // 流程图图表信息
        JSONObject reqProcessFormJson= processJson.getJSONObject("reqProcessForm"); // 模板基本信息
        // 保存模板数据
        WfmProcess wfmProcess = new WfmProcess();
        String pId = UUIDUtils.generateUuid();
        wfmProcess.setPId(pId);
        wfmProcess.setProcName(reqProcessFormJson.getString("name"));
        wfmProcess.setTotalLimit(reqProcessFormJson.getInteger("totalLimit"));
        wfmProcess.setStatus("未处理");
        wfmProcess.setNote(reqProcessFormJson.getString("desc"));
        wfmProcess.setImageJson(diagramJson.toString());
        wfmProcess.setIsDeleted(false);
        wfmProcess.setCrtUserId(BaseContextHandler.getUserID());
        wfmProcess.setCrtTime(new Date());
        wfmProcess.setUpdUserId(BaseContextHandler.getUserID());
        wfmProcess.setUpdTime(new Date());
        mapper.insert(wfmProcess);
        return true;
    }


    /**
     * 新增模板信息
     * @deprecated 暂时舍弃
     */
    public boolean addNewProcess(String processInfo) {
        try{
            // 检查是否非空
            if(StringUtils.isBlank(processInfo)){
                return false;
            }
            // 解析模板数据
            JSONObject processJson = JSONObject.parseObject(processInfo);
            JSONObject diagramJson= processJson.getJSONObject("diagramJson"); // 流程图图表信息
            JSONObject reqProcessFormJson= processJson.getJSONObject("reqProcessForm"); // 模板基本信息
            JSONArray stepApiRelJson = processJson.getJSONArray("stepApiRel"); // 步骤和API关系表
            // 保存模板数据
            WfmProcess wfmProcess = new WfmProcess();
            String pId = UUIDUtils.generateUuid();
            wfmProcess.setPId(pId);
            wfmProcess.setProcName(reqProcessFormJson.getString("name"));
            wfmProcess.setTotalLimit(4320); // 默认三天
            wfmProcess.setStatus("未处理");
            wfmProcess.setNote(reqProcessFormJson.getString("desc"));
            wfmProcess.setImageJson(diagramJson.toString());
            wfmProcess.setIsDeleted(false);
            wfmProcess.setCrtUserId(BaseContextHandler.getUserID());
            wfmProcess.setCrtTime(new Date());
            wfmProcess.setUpdUserId(BaseContextHandler.getUserID());
            wfmProcess.setUpdTime(new Date());
            mapper.insert(wfmProcess);
            for (int i = 0; i < stepApiRelJson.size(); i++) {
                JSONObject stepJsonInfo = stepApiRelJson.getJSONObject(i);
                String stepName = stepJsonInfo.getString("stepName");
                String stepDesc = stepJsonInfo.getString("desc");
                String leaderCorp = stepJsonInfo.getString("corpName");
                String leaderName = stepJsonInfo.getString("name");
                String leaderTel = stepJsonInfo.getString("tel");
                String sId = UUIDUtils.generateUuid();
                WfmProcessStep wfmProcessStep = new WfmProcessStep();
                wfmProcessStep.setSId(sId);
                wfmProcessStep.setPId(pId);
                wfmProcessStep.setStepName(stepName);
                wfmProcessStep.setStepStatus("未完成");
                wfmProcessStep.setNote(stepDesc);
                wfmProcessStep.setLeaderCorp(leaderCorp);
                wfmProcessStep.setLeaderName(leaderName);
                wfmProcessStep.setLeaderTel(leaderTel);
                wfmProcessStepBiz.addNewProcessStep(wfmProcessStep);
                JSONArray apiListJson = stepJsonInfo.getJSONArray("apiList");
                for (int j = 0; j < apiListJson.size(); j++) {
                    JSONObject apiJson = apiListJson.getJSONObject(j);
                    String apiIdStr = apiJson.getString("apiId");
                    WfmProcessApiStepRel wfmProcessApiStepRel = new WfmProcessApiStepRel();
                    wfmProcessApiStepRel.setRelId(UUIDUtils.generateUuid());
                    wfmProcessApiStepRel.setSId(sId);
                    wfmProcessApiStepRel.setApiId(apiIdStr);
                    wfmProcessApiStepRel.setStatus(false);
                    wfmProcessApiStepRelBiz.addNewProcessStep(wfmProcessApiStepRel);
                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取模板名称列表
     */
    public List<WfmProcess> getAllProcessName() {
        return mapper.getAllProcessName();
    }

    /**
     * 根据模板ID查询模板所有信息
     * @param pId 模板主键ID
     * @deprecated 暂时舍弃
     */
    public JSONObject getProcessInfoById(String pId) {
        try{
            JSONObject resultJson = new JSONObject();
            // 获取流程图数据
            WfmProcess wfmProcess = mapper.getProcessInfoById(pId);
            resultJson.put("diagramJson",wfmProcess.getImageJson());
            // 获取模板基本信息数据
            JSONObject reqProcessFormJSON = new JSONObject();
            reqProcessFormJSON.put("name",wfmProcess.getProcName()); // 模板名称
            reqProcessFormJSON.put("desc",wfmProcess.getNote()); // 模板说明
            reqProcessFormJSON.put("totalLimit",wfmProcess.getTotalLimit()); // 流程限制时间
            resultJson.put("reqProcessForm",reqProcessFormJSON);
            // 获取模板API关系列表
            List<WfmProcessStep> WfmProcessStepList = wfmProcessStepBiz.getProcessStepInfo(wfmProcess.getPId());
            JSONArray stepFormListJsonArr = new JSONArray();
            for (int i = 0; i < WfmProcessStepList.size(); i++) {
                WfmProcessStep wfmProcessStep = WfmProcessStepList.get(i);
                JSONObject stepFormListJson = new JSONObject();
                stepFormListJson.put("corpName",wfmProcessStep.getLeaderCorp()); // 主管部门
                stepFormListJson.put("stepName",wfmProcessStep.getStepName()); // 步骤名称
                stepFormListJson.put("name",wfmProcessStep.getLeaderName()); // 负责人姓名
                stepFormListJson.put("tel",wfmProcessStep.getLeaderTel()); // 负责人电话
                stepFormListJson.put("desc",wfmProcessStep.getNote()); // 步骤说明
                List<WfmProcessApiStepRel> WfmProcessApiStepRelList = wfmProcessApiStepRelBiz.selectProcessApiInfoBySId(wfmProcessStep.getSId());
                JSONArray apiListJsonArr = new JSONArray();
                for (int j = 0; j < WfmProcessApiStepRelList.size(); j++) {
                    WfmProcessApiStepRel wfmProcessApiStepRel = WfmProcessApiStepRelList.get(j);
                    JSONObject apiListJson = new JSONObject();
                    apiListJson.put("apiId",wfmProcessApiStepRel.getApiPubkey());
                    apiListJson.put("apiName",wfmProcessApiStepRel.getApiName());
                    apiListJsonArr.add(apiListJson);
                }
                stepFormListJson.put("apiList",apiListJsonArr);
                stepFormListJsonArr.add(stepFormListJson);
            }
            resultJson.put("stepApiRel",stepFormListJsonArr);
            return resultJson;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据模板ID查询模板所有信息
     * @param pId 模板主键ID
     *
     */
    public JSONObject getSimpleProcessInfoById(String pId) {
        try{
            JSONObject resultJson = new JSONObject();
            // 获取流程图数据
            WfmProcess wfmProcess = mapper.getProcessInfoById(pId);
            resultJson.put("diagramJson",wfmProcess.getImageJson());
            // 获取模板基本信息数据
            JSONObject reqProcessFormJSON = new JSONObject();
            reqProcessFormJSON.put("name",wfmProcess.getProcName()); // 模板名称
            reqProcessFormJSON.put("desc",wfmProcess.getNote()); // 模板说明
            reqProcessFormJSON.put("totalLimit",wfmProcess.getTotalLimit()); // 流程限制时间
            resultJson.put("reqProcessForm",reqProcessFormJSON);
            return resultJson;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据模板ID修改模板信息
     *
     * @param pId         模板主键ID
     * @param processInfo 模板信息
     * @return
     */
    public void updateProcessInfoById(String pId, String processInfo) {
        // 检查是否非空
        if(StringUtils.isBlank(processInfo) || StringUtils.isBlank(pId))
            throw new ZtgeoBizException("参数错误，未获取到模板信息或模板ID");
        // 解析模板数据
        JSONObject processJson = JSONObject.parseObject(processInfo);
        JSONObject diagramJson= processJson.getJSONObject("diagramJson"); // 流程图图表信息
        JSONObject reqProcessFormJson= processJson.getJSONObject("reqProcessForm"); // 模板基本信息
        // 保存模板数据
        WfmProcess wfmProcess = new WfmProcess();
        wfmProcess.setPId(pId);
        wfmProcess.setProcName(reqProcessFormJson.getString("name"));
        wfmProcess.setTotalLimit(reqProcessFormJson.getInteger("totalLimit"));
        wfmProcess.setNote(reqProcessFormJson.getString("desc"));
        wfmProcess.setImageJson(diagramJson.toString());
        wfmProcess.setIsDeleted(false);
        wfmProcess.setUpdUserId(BaseContextHandler.getUserID());
        wfmProcess.setUpdTime(new Date());
        mapper.updateByPrimaryKeySelective(wfmProcess);
    }
}