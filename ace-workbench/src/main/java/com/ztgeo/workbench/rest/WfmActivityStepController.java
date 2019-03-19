package com.ztgeo.workbench.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ztgeo.workbench.biz.WfmActivityStepBiz;
import com.ztgeo.workbench.entity.WfmActivityStep;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

@RestController
@RequestMapping("wfmActivityStep")
@CheckClientToken
@CheckUserToken
public class WfmActivityStepController extends BaseController<WfmActivityStepBiz,WfmActivityStep,String> {

}