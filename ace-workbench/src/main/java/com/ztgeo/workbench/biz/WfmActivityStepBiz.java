package com.ztgeo.workbench.biz;

import org.springframework.stereotype.Service;

import com.ztgeo.workbench.entity.WfmActivityStep;
import com.ztgeo.workbench.mapper.WfmActivityStepMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 活动步骤表
 *
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-11-26 15:26:20
 */
@Service
public class WfmActivityStepBiz extends BusinessBiz<WfmActivityStepMapper,WfmActivityStep> {
}