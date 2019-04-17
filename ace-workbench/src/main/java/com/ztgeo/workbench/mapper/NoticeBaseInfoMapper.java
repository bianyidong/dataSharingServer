package com.ztgeo.workbench.mapper;


import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.ztgeo.workbench.entity.NoticeBaseInfo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
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
public interface NoticeBaseInfoMapper extends CommonMapper<NoticeBaseInfo> {
    //根据UserId更新信息
    @Update("UPDATE notice_base_info SET method=#{method} ,notice_path=#{noticePath},upd_time=#{updTime} WHERE user_real_id=#{userRealId}")
    void updatenoticeBaseInfo(@Param("method") String method, @Param("noticePath") String noticePath,@Param("updTime") Date updTime, @Param("userRealId")  String userRealId);
    // 查询用户是否存在
    @Select("select count(*) from notice_base_info aar where user_real_id=#{userRealId} ")
    int selectNoticeBaseInfoByUserid(@Param("userRealId") String userRealId);
    //添加通知配置信息
    @Insert(" INSERT INTO notice_base_info(notice_id,user_real_id,name,username,notice_path,method,notice_note,crt_time,crt_user_id,upd_time,upd_user_id) " +
            "VALUES (#{noticeId},#{userRealId},#{name},#{username},#{noticePath},#{method},#{noticeNote},#{crtTime},1,#{updTime},1)")
    void insertNoticeBaseInfo(@Param("noticeId") String noticeId, @Param("userRealId") String userRealId,@Param("name") String name, @Param("username") String username,
                              @Param("noticePath") String noticePath,@Param("method") String method,@Param("noticeNote") String noticeNote,@Param("crtTime") Date crtTime,@Param("updTime") Date updTime);
   }
