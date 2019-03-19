package com.ztgeo.workbench.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ztgeo.workbench.biz.NoticeMonitorInfoBiz;
import com.ztgeo.workbench.entity.NoticeMonitorInfo;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

@RestController
@RequestMapping("noticeMonitorInfo")
@CheckClientToken
@CheckUserToken
public class NoticeMonitorInfoController extends BaseController<NoticeMonitorInfoBiz,NoticeMonitorInfo,String> {

}