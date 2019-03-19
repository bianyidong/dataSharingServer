package com.ztgeo.workbench.biz;

import org.springframework.stereotype.Service;

import com.ztgeo.workbench.entity.WfmProcessStep;
import com.ztgeo.workbench.mapper.WfmProcessStepMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.List;

/**
 * 模板步骤表
 *
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-11-26 15:26:20
 */
@Service
public class WfmProcessStepBiz extends BusinessBiz<WfmProcessStepMapper,WfmProcessStep> {

    /**
     * 添加模板步骤信息
     */
    public void addNewProcessStep(WfmProcessStep wfmProcessStep) {
        mapper.insert(wfmProcessStep);
    }

    /**
     * 获取模板API关系列表
     */
    public List<WfmProcessStep> getProcessStepInfo(String pId) {
        return mapper.getProcessStepInfo(pId);
    }
}