package com.ztgeo.workbench.mapper;

import com.ztgeo.workbench.entity.ApiAuthRel;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ztgeo.workbench.entity.ApiBaseInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-08-27 16:57:29
 */
public interface ApiAuthRelMapper extends CommonMapper<ApiAuthRel> {

    // 查询所有用户是否有权限
    @Select("select count(*) from api_auth_rel aar where aar.user_pubkey=#{id} and aar.api_pubkey=#{apiPubKey}")
    int selectUserAuthByUserIdAndApiId(@Param("id") String id, @Param("apiPubKey") String apiPubKey);

    // 根据userPubkey和apiPubkey删除权限
    @Delete("delete from api_auth_rel where user_pubkey=#{userPubkey} and api_pubkey=#{apiPubkey}")
    void deleteByApiPubkeyAndUserPubkey(@Param("userPubkey") String userPubkey, @Param("apiPubkey") String apiPubkey);

}
