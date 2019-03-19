package com.ztgeo.workbench.mapper;

import com.ztgeo.workbench.entity.WfmProcess;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模板表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-11-26 15:26:20
 */
public interface WfmProcessMapper extends CommonMapper<WfmProcess> {

    List<WfmProcess> getAllProcessName();

    WfmProcess getProcessInfoById(@Param("pId") String pId);
}
