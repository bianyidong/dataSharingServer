package com.ztgeo.workbench.rest;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ztgeo.workbench.biz.NoticeRecordBiz;
import com.ztgeo.workbench.entity.NoticeRecord;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RestController
@RequestMapping("noticeRecord")
@CheckClientToken
@CheckUserToken
public class NoticeRecordController extends BaseController<NoticeRecordBiz, NoticeRecord, String> {
    /**
     * 根据用户登录名查询通知信息
     */
    @RequestMapping(value = "getApiNoticeDataDetail", method = RequestMethod.GET)
    public TableResultResponse<NoticeRecord> getApiNoticeDataDetail(@RequestParam Map<String, Object> params){
        return baseBiz.getApiNoticeDataDetail(params);
    }
    /**
     * 查询单条通知详细信息并重发此数据
     */
    @RequestMapping(value = "getOneNoticeRecord/{recordId}", method = RequestMethod.GET)
    public ObjectRestResponse<NoticeRecord> getOneNoticeRecord(@PathVariable String recordId) {
        NoticeRecord noticeRecord= baseBiz.getOneNoticeRecord(recordId);
        System.out.println(noticeRecord);
        return new ObjectRestResponse<NoticeRecord>().data(noticeRecord);
    }
}