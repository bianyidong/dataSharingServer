package com.ztgeo.workbench.mapper;

import com.ztgeo.workbench.entity.NoticeUserRel;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * 通知用户关系表. 表示用户的通知该转发给哪些已配置过地址的用户
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-09-14 11:58:02
 */
public interface NoticeUserRelMapper extends CommonMapper<NoticeUserRel> {

    @Delete("delete from notice_user_rel where user_real_id = #{userID} and notice_id = #{noticeId} and type_id = #{typeId}")
    void deleteByUserIdAndNoticeId(@Param("userID") String userID, @Param("noticeId") String noticeId, @Param("typeId") String typeId);

}
