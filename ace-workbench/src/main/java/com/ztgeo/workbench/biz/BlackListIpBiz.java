package com.ztgeo.workbench.biz;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.Query;
import com.ztgeo.workbench.entity.BlackListIp;
import com.ztgeo.workbench.mapper.BlackListIpMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author Mr.BYD
 * @version 2018-09-13 10:07:47
 * @email 1056460744@qq.com
 */
@Service
public class BlackListIpBiz extends BusinessBiz<BlackListIpMapper, BlackListIp> {

    /**
     * 查询分页显示
     *
     * @param params
     * @return
     */
    public TableResultResponse<BlackListIp> getAllBlackListIp(Map<String, Object> params) {
        Query query = new Query(params);
        List<BlackListIp> list = mapper.getAPIIBlackIp((query.getPage() - 1) * query.getLimit(), query.getLimit(), params.get("ipContent") == null ? "" : params.get("ipContent").toString());
        return new TableResultResponse<>(list.size(), list);
    }

    /**
     * 查询单条IP详细信息
     *
     * @return
     */
    public BlackListIp getOneBlackIp(int id) {
        return mapper.getOneBlackIp(id);
    }

    /**
     * 新增黑名单Ip
     */
    public void addOneBlackIp(BlackListIp blackListIp) {
        try {
            Date currentDate = new Date(Instant.now().toEpochMilli());
            String ipNote = blackListIp.getIpNote();
            String ipContent = blackListIp.getIpContentStr();
            mapper.insertBlackIp(ipContent, ipNote, currentDate, currentDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改Ip黑名单
     */
    public void updateOneBlackIp(BlackListIp blackListIp) {
        // 获取当前时间
        Date currentDate = new Date(Instant.now().toEpochMilli());
        int id = blackListIp.getIpId();
        String ipNote = blackListIp.getIpNote();
        String ipContent = blackListIp.getIpContentStr();
        mapper.updateBlackIp(ipContent, ipNote, currentDate, id);
    }
}