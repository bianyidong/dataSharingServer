package com.ztgeo.workbench.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ztgeo.workbench.entity.ApiAccessRecord;
import com.ztgeo.workbench.entity.ApiBaseInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

/**
 * API访问记录表
 * 
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-10-09 14:54:00
 */
public interface ApiAccessRecordMapper extends CommonMapper<ApiAccessRecord> {

    @Select({"select count(*) from api_access_record aar inner join api_base_info abi on aar.api_id = abi.api_id where aar.access_year = #{yearStr} and aar.access_month = #{monthStr} and aar.access_day = #{dayStr}"})
    @ResultType(Integer.class)
    int getApiAccessWeekRecord(@Param("yearStr") int year, @Param("monthStr") int monthValue, @Param("dayStr") int dayOfMonth);

    ApiAccessRecord getOneApiAccessRecord(@Param("id") String id);
}
