package com.ztgeo.workbench.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ztgeo.workbench.biz.WfmActivityApiStepRelBiz;
import com.ztgeo.workbench.entity.WfmActivityApiStepRel;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

@RestController
@RequestMapping("wfmActivityApiStepRel")
@CheckClientToken
@CheckUserToken
public class WfmActivityApiStepRelController extends BaseController<WfmActivityApiStepRelBiz,WfmActivityApiStepRel,String> {

}