package com.ztgeo.workbench.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ztgeo.workbench.biz.WfmProcessStepBiz;
import com.ztgeo.workbench.entity.WfmProcessStep;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

@RestController
@RequestMapping("wfmProcessStep")
@CheckClientToken
@CheckUserToken
public class WfmProcessStepController extends BaseController<WfmProcessStepBiz,WfmProcessStep,String> {

}