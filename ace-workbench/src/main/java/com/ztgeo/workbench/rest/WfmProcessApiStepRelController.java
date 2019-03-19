package com.ztgeo.workbench.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ztgeo.workbench.biz.WfmProcessApiStepRelBiz;
import com.ztgeo.workbench.entity.WfmProcessApiStepRel;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

@RestController
@RequestMapping("wfmProcessApiStepRel")
@CheckClientToken
@CheckUserToken
public class WfmProcessApiStepRelController extends BaseController<WfmProcessApiStepRelBiz,WfmProcessApiStepRel,String> {

}