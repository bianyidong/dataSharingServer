package com.ztgeo.workbench.mapper;

import com.ztgeo.workbench.entity.ApiBaseInfo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.json.simple.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * @author zoupeidong
 * @version 2018-08-27 16:57:29
 * @email 806316372@qq.com
 */
public interface ApiBaseInfoMapper extends CommonMapper<ApiBaseInfo> {

    // 查询单个API详细信息
    ApiBaseInfo getOneApiBaseInfo(@Param("apiId") String apiId);

    // 根据userId获取有权限访问的API信息
    List<ApiBaseInfo> getAPIInfoByUserId(@Param("userId") String userId, @Param("apiName") Object apiName, @Param("start") int start, @Param("limit") int limit);

    // 根据userId获取有权限访问的API信息
    List<ApiBaseInfo> getNotAllApiBaseInfo(@Param("userId") String userId, @Param("apiName") Object apiName, @Param("start") int start, @Param("limit") int limit);

    // 查询所有API信息，返回API名称和标识列表
    @Select({"select api_pubkey apiPubkey,api_name apiName from api_base_info"})
    List<ApiBaseInfo> getAllStepApiBaseInfo();

    //添加不带加密加签接口信息
    @Insert("INSERT INTO api_base_info (api_id,api_name,base_url,path,method,enabled_status,api_owner_id,api_owner_name,crt_user_id,crt_time,upd_user_id,upd_time)" +
            " values(#{apiId},#{apiName},#{baseUrl},#{path},#{method},#{status},#{apiownerId},#{apiownerName},#{crtuserId},#{crtTime},#{updUserId},#{updTime})")
    void insertBaseInfo(@Param("apiId") String apiId, @Param("apiName") String apiName, @Param("baseUrl") String baseUrl, @Param("path") String path,
                        @Param("method") String method, @Param("status") int status, @Param("apiownerId") String apiownerId, @Param("apiownerName") String apiownerName,
                        @Param("crtuserId") String crtuserId, @Param("crtTime") Date crtTime, @Param("updUserId") String updUserId, @Param("updTime") Date updTime);
}
