package com.ztgeo.workbench.mapper;


import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ztgeo.workbench.entity.ApiAccessRecord;
import com.ztgeo.workbench.entity.NoticeBaseInfo;
import com.ztgeo.workbench.entity.NoticeRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * 通知基础信息配置表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-09-14 11:58:02
 */
public interface NoticeRecordMapper extends CommonMapper<NoticeRecord> {
    // 根据userId获取有权限访问的API信息
    List<NoticeRecord> getNotAllApiBaseInfo(@Param("userId") String userId, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("startTime") Object startTime, @Param("endTime") Object endTime);
    //查询单条的通知信息
    NoticeRecord getOneNoticeRecord(@Param("recordId") String recordId);
}
