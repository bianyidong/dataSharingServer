package com.ztgeo.workbench.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.ztgeo.workbench.crypto.HttpOperation;
import com.ztgeo.workbench.entity.NoticeRecord;
import com.ztgeo.workbench.mapper.NoticeRecordMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * 通知基础信息配置表
 *
 * @author zoupeidong
 * @version 2018-09-14 11:58:02
 * @email 806316372@qq.com
 */
@Service
public class NoticeRecordBiz extends BusinessBiz<NoticeRecordMapper, NoticeRecord> {

    /**
     * 查询规定日期内的接口访问详情
     */
    public TableResultResponse<NoticeRecord> getApiNoticeDataDetail(Map<String, Object> params) {
        // 获得开始和结束时间
        Object startTime = params.get("startTime");
        Object endTime = params.get("endTime");
        // 获得分页条件
        int pageNo = Integer.valueOf(params.get("page").toString());
        int pageSize = Integer.valueOf(params.get("limit").toString());
        List<NoticeRecord> list = mapper.getNotAllApiBaseInfo(BaseContextHandler.getUserID(),pageNo-1,pageSize,startTime,endTime );
        return new TableResultResponse<>(list.size(), list);
    }
    /**
     * 查询单条A通知详细信息并重发此数据
     */
    public NoticeRecord getOneNoticeRecord(String recordId) {
        NoticeRecord noticeRecord= mapper.getOneNoticeRecord(recordId);
        String url = noticeRecord.getReceiverUrl();
        String data = noticeRecord.getRequestData();
        // 发送并接收数据
        String rspData = HttpOperation.sendJsonHttp(url, data);
        if (rspData == null || rspData.length() == 0) {
            throw new RuntimeException("发送失败");
        }else {
            noticeRecord.setRecordId(noticeRecord.getRecordId());
            noticeRecord.setStatus(0);
            mapper.updateByPrimaryKeySelective(noticeRecord);
        }
        return noticeRecord;
    }

}