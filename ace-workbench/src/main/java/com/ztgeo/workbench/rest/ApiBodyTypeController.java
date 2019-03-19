package com.ztgeo.workbench.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ztgeo.workbench.biz.ApiBodyTypeBiz;
import com.ztgeo.workbench.entity.ApiBodyType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

@RestController
@RequestMapping("apiBodyType")
@CheckClientToken
@CheckUserToken
public class ApiBodyTypeController extends BaseController<ApiBodyTypeBiz,ApiBodyType,String> {

}