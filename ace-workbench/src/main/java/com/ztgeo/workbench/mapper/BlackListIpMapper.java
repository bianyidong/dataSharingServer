package com.ztgeo.workbench.mapper;


import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ztgeo.workbench.entity.BlackListIp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * @author Mr.BYD
 * @version 2018-09-13 10:07:47
 * @email 1056460744@qq.com
 */
public interface BlackListIpMapper extends CommonMapper<BlackListIp> {
    // 查询单个IP详细信息
    @Select("SELECT INET_NTOA(ip_content) ipContentStr,ip_note ipNote FROM black_list_ip WHERE ip_id=#{ipId}")
    BlackListIp getOneBlackIp(@Param("ipId") int ipId);

    //查询黑名单的所有信息
    List<BlackListIp> getAPIIBlackIp(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("ipContentClause") String ipContentClause);

    //根据ipId更新信息
    @Update("UPDATE black_list_ip SET ip_content=INET_ATON(#{ipContent}), ip_note=#{ipNote},upd_time=#{updTime} WHERE ip_id=#{ipId}")
    void updateBlackIp(@Param("ipContent") String ipContent, @Param("ipNote") String ipNote, @Param("updTime") Date updTime, @Param("ipId") int ipId);

    //添加黑名单信息
    @Insert(" INSERT INTO black_list_ip(ip_content,ip_note,crt_user_id,crt_time,upd_user_id,upd_time) VALUES (INET_ATON(#{ipContent}),#{ipNote},1,#{crtTime},1,#{updTime})")
    void insertBlackIp(@Param("ipContent") String ipContent, @Param("ipNote") String ipNote, @Param("crtTime") Date crtTime, @Param("updTime") Date updTime);

}
