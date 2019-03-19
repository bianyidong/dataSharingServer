package com.ztgeo.workbench.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ztgeo.workbench.entity.UserKeyInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

/**
 * Create by Wei on 2018/9/5
 */
public interface UserKeyInfoMapper extends CommonMapper<UserKeyInfo> {

    @Select({"select user_identity_id from user_key_info where user_real_id = #{userId}"})
    @ResultType(String.class)
    String selectUserIdentityIdByUserId(@Param("userId") String userId);

}
