package com.ztgeo.workbench.rest;

import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ztgeo.workbench.biz.NoticeBaseInfoBiz;
import com.ztgeo.workbench.entity.BlackListIp;
import com.ztgeo.workbench.entity.NoticeBaseInfo;
import com.ztgeo.workbench.entity.User;
import com.ztgeo.workbench.feign.AdminFeign;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;


@RestController
@RequestMapping("noticeBaseInfo")
@CheckClientToken
@CheckUserToken
public class NoticeBaseInfoController extends BaseController<NoticeBaseInfoBiz, NoticeBaseInfo, String> {
    @Autowired
    private AdminFeign adminFeign;

    /**
     * 获取所有可接收通知的用户名和主键ID
     */

    @GetMapping("getAllActiviveUser/{id}")
    public JSONObject getAllActiviveUser(@PathVariable("id") String id) {
        return baseBiz.getAllActiviveUser(id);
    }

    /**
     * 查询有无记录
     *
     * @return
     */
    @GetMapping("selectNoticeBaseInfoByUserid")
    public ObjectRestResponse<NoticeBaseInfo> selectNoticeBaseInfoByUserid() {
        return ObjectRestResponse.ok(baseBiz.selectNoticeBaseInfoByUserid());
    }

    /**
     * 添加通知配置信息
     */
    @RequestMapping(value = "insertNoticeBaseInfo", method = RequestMethod.POST)
    public ObjectRestResponse<NoticeBaseInfo> insertNoticeBaseInfo(@RequestBody NoticeBaseInfo noticeBaseInfo) {
        baseBiz.insertNoticeBaseInfo(noticeBaseInfo);
        return new ObjectRestResponse<NoticeBaseInfo>().data(noticeBaseInfo);
    }

    @ApiOperation(value = "查询通知地址配置相关信息）")
    @GetMapping("getnoticeBaseInfo")
    public ObjectRestResponse<NoticeBaseInfo> getnoticeBaseInfo() {
        return baseBiz.getnoticeBaseInfo();
    }

    /**
     * 修改更新地址
     */
    @RequestMapping(value = "updatenoticeBaseInfo", method = RequestMethod.PUT)
    public ObjectRestResponse<NoticeBaseInfo> updatenoticeBaseInfo(@RequestBody NoticeBaseInfo noticeBaseInfo) {
        baseBiz.updatenoticeBaseInfo(noticeBaseInfo);
        return new ObjectRestResponse<NoticeBaseInfo>().data(noticeBaseInfo);
    }

    /**
     * 获取单个用户信息
     *
     * @deprecated
     */
    @RequestMapping(value = "getnoticeCorpBaseInfo", method = RequestMethod.GET)
    public ObjectRestResponse<JSONObject> getnoticeCorpBaseInfo() {
        return adminFeign.getnoticeCorpBaseInfoByid(BaseContextHandler.getUserID());
    }

    /**
     * 更新单个用户信息
     *
     * @deprecated
     */
    @RequestMapping(value = "updatenoticeCorpBaseInfo", method = RequestMethod.PUT)
    public ObjectRestResponse<User> updatenoticeCorpBaseInfo(@RequestBody User user) {
        return adminFeign.updatenoticeCropBaseInfo(user, BaseContextHandler.getUserID());
    }
}