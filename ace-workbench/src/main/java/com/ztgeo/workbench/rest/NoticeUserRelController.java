package com.ztgeo.workbench.rest;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ztgeo.workbench.biz.NoticeUserRelBiz;
import com.ztgeo.workbench.entity.NoticeUserRel;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

@RestController
@RequestMapping("noticeUserRel")
@CheckClientToken
@CheckUserToken
public class NoticeUserRelController extends BaseController<NoticeUserRelBiz,NoticeUserRel,String> {

    /**
     * 更新通知和用户关系表
     */
    @PutMapping("updateNoticeUserRelList/{typeId}")
    public ObjectRestResponse<NoticeUserRel> updateNoticeUserRelList(@PathVariable("typeId") String typeId, @RequestBody String json){
        baseBiz.updateNoticeUserRelList(typeId,json);
        return new ObjectRestResponse<NoticeUserRel>();
    }



}