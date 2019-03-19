package com.ztgeo.workbench.mapper;

import com.ztgeo.workbench.entity.WfmProcessStep;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模板步骤表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-11-26 15:26:20
 */
public interface WfmProcessStepMapper extends CommonMapper<WfmProcessStep> {

    List<WfmProcessStep> getProcessStepInfo(@Param("pId") String pId);
}
