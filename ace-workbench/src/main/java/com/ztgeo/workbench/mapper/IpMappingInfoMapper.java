package com.ztgeo.workbench.mapper;


import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ztgeo.workbench.entity.IpMappingInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ip映射表
 *
 * @author robertshell
 * @version 2019-01-08 11:15:17
 * @email 18851165563@163.com
 */
public interface IpMappingInfoMapper extends CommonMapper<IpMappingInfo> {
    void insertIpMappingInfo(IpMappingInfo ipMappingInfo);

    void updateIpMappingInfoById(IpMappingInfo ipMappingInfo);

    IpMappingInfo selectIpMappingInfoById(Integer id);

    //查询Ip映射处理器的所有信息
//    @Select(" SELECT id, ip_a,ip_b,ip_c FROM ip_mapping_info LIMIT #{pageNo},#{pageSize}")
    List<IpMappingInfo> selectIpMappingInfos(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("ipContentClause") String ipContentClause);
}
