package com.ztgeo.workbench.mapper;

import com.ztgeo.workbench.entity.WfmProcessApiStepRel;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 流程接口服务关系表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-11-26 15:26:20
 */
public interface WfmProcessApiStepRelMapper extends CommonMapper<WfmProcessApiStepRel> {

    List<WfmProcessApiStepRel> selectProcessApiInfoBySId(@Param("sId") String sId);
}
