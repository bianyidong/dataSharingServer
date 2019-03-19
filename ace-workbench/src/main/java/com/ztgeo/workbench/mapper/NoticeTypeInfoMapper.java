package com.ztgeo.workbench.mapper;

import com.ztgeo.workbench.entity.NoticeTypeInfo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 通知类型表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-09-14 11:58:02
 */
public interface NoticeTypeInfoMapper extends CommonMapper<NoticeTypeInfo> {
    //添加通知类型信息
    @Insert(" INSERT INTO notice_type_info(id,type_id,type_desc,crt_time,crt_user_id,upd_time,upd_user_id) " +
            "VALUES (#{ID},#{typeId},#{typeDesc},#{crtTime},#{crtUserId},#{updTime},#{updUserId})")
    void insertNoticeTypeInfo(@Param("ID") String Id, @Param("typeId") String typeId, @Param("typeDesc") String typeDesc, @Param("crtTime") Date crtTime,
                              @Param("crtUserId") String crtUserId, @Param("updTime") Date updTime, @Param("updUserId") String updUserId);
	
}
