package com.ztgeo.workbench.biz;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.Query;
import com.ztgeo.workbench.entity.IpMappingInfo;
import com.ztgeo.workbench.mapper.IpMappingInfoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * ip映射表
 *
 * @author robertshell
 * @version 2019-01-08 11:15:17
 * @email 18851165563@163.com
 */
@Service
public class IpMappingInfoBiz extends BusinessBiz<IpMappingInfoMapper, IpMappingInfo> {

    @Override
    public void insertSelective(IpMappingInfo entity) {
        mapper.insertIpMappingInfo(entity);
    }

    @Override
    public void updateSelectiveById(IpMappingInfo entity) {
        mapper.updateIpMappingInfoById(entity);
    }

    @Override
    public IpMappingInfo selectById(Object id) {
        return mapper.selectIpMappingInfoById(Integer.parseInt(id.toString()));
    }

    /**
     * 查询分页显示
     *
     * @param params
     * @return
     * @apiNote
     */
    public TableResultResponse<IpMappingInfo> selectIpMappingInfos(Map<String, Object> params) {
        Query query = new Query(params);
        List<IpMappingInfo> list = mapper.selectIpMappingInfos((query.getPage() - 1) * query.getLimit(), query.getLimit(), params.get("ipContent") == null ? "" : params.get("ipContent").toString());
        int getAllList = mapper.selectCount(new IpMappingInfo());
        return new TableResultResponse<>(getAllList, list);
    }
}