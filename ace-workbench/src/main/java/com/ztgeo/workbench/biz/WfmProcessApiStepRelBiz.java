package com.ztgeo.workbench.biz;

import org.springframework.stereotype.Service;

import com.ztgeo.workbench.entity.WfmProcessApiStepRel;
import com.ztgeo.workbench.mapper.WfmProcessApiStepRelMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.List;

/**
 * 流程接口服务关系表
 *
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-11-26 15:26:20
 */
@Service
public class WfmProcessApiStepRelBiz extends BusinessBiz<WfmProcessApiStepRelMapper,WfmProcessApiStepRel> {
    public void addNewProcessStep(WfmProcessApiStepRel wfmProcessApiStepRel) {
        mapper.insert(wfmProcessApiStepRel);
    }

    /**
     * 获取模板API关系列表
     *
     * @param sId 步骤ID
     */
    public List<WfmProcessApiStepRel> selectProcessApiInfoBySId(String sId) {
        return mapper.selectProcessApiInfoBySId(sId);
    }
}