package com.ztgeo.workbench.rest;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ztgeo.workbench.biz.NoticeTypeInfoBiz;
import com.ztgeo.workbench.entity.ApiBaseInfo;
import com.ztgeo.workbench.entity.NoticeBaseInfo;
import com.ztgeo.workbench.entity.NoticeTypeInfo;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.util.Map;

@RestController
@RequestMapping("noticeTypeInfo")
@CheckClientToken
@CheckUserToken
public class NoticeTypeInfoController extends BaseController<NoticeTypeInfoBiz,NoticeTypeInfo,Integer> {

    /**
     * 分页查询通知类型
     */
    @GetMapping("getAllNoticeType")
    public TableResultResponse<NoticeTypeInfo> getAllNoticeType(@RequestParam Map<String, Object> params){
        return baseBiz.getAllNoticeType(params);
    }
    /**
     * 分页查询访问API基础信息
     */
    @RequestMapping(value = "getAllTypeIdByUserId", method = RequestMethod.GET)
    public TableResultResponse<NoticeTypeInfo> getAllTypeIdByUserId(@RequestParam Map<String, Object> params) {
        return baseBiz.getAllTypeIdByUserId(BaseContextHandler.getUserID(),params);
    }
    /**
     * 添加通知类型
     */
    @RequestMapping(value = "insertNoticeTypeInfo", method = RequestMethod.POST)
    public ObjectRestResponse<NoticeTypeInfo> insertNoticeTypeInfo(@RequestBody NoticeTypeInfo noticeTypeInfo) {
        baseBiz.insertNoticeTypeInfo(noticeTypeInfo);
        return new ObjectRestResponse<NoticeTypeInfo>().data(noticeTypeInfo);
    }
    /**
     * 删除API接口
     */
    @RequestMapping(value = "deleteOneNoticeBaseInfo/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse<NoticeTypeInfo> deleteOneNoticeBaseInfo(@PathVariable String id) {
        baseBiz.deleteOneNoticeBaseInfo(id);
        return new ObjectRestResponse<NoticeTypeInfo>();
    }

}