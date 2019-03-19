package com.ztgeo.workbench.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.Query;
import com.ztgeo.workbench.entity.ApiBaseInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.ztgeo.workbench.entity.WfmActivity;
import com.ztgeo.workbench.mapper.WfmActivityMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import tk.mybatis.mapper.entity.Example;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 模板活动表
 *
 * @author zoupeidong
 * @version 2018-11-26 15:26:20
 * @email 806316372@qq.com
 */
@Service
public class WfmActivityBiz extends BusinessBiz<WfmActivityMapper, WfmActivity> {

    /**
     * 分页查询API基础信息
     */
    public TableResultResponse<WfmActivity> getAllActivityInfo(Map<String, Object> params) {
        Example example = new Example(WfmActivity.class);
        example.selectProperties("aId", "procName", "totalLimit", "status", "crtTime");
        if (!Objects.equals(params.get("aId"), null) && !StringUtils.isBlank(params.get("aId").toString())) {
            example.createCriteria().andLike("aId", "%" + params.get("aId").toString().trim() + "%");
        }
        if (!Objects.equals(params.get("procName"), null) && !StringUtils.isBlank(params.get("procName").toString())) {
            example.createCriteria().andLike("procName", "%" + params.get("procName").toString().trim() + "%");
        }
        if (!Objects.equals(params.get("aId"), null) && !StringUtils.isBlank(params.get("aId").toString()) && !Objects.equals(params.get("procName"), null) && !StringUtils.isBlank(params.get("procName").toString())) {
            example.createCriteria().andLike("aId", "%" + params.get("aId").toString().trim() + "%").andLike("procName", "%" + params.get("procName").toString().trim() + "%");
        }
        Query query = new Query(params);
        this.query2criteria(query, example);
        Page<WfmActivity> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<WfmActivity> list = this.selectByExample(example);
        // 处理总时长和结束时间
        for (int i = 0; i < list.size(); i++) {
            WfmActivity wfmActivity = list.get(i);
            int totalLimit = wfmActivity.getTotalLimit();
            Date date = wfmActivity.getCrtTime();
            Instant startTime = date.toInstant();
            Instant endTime = startTime.plusSeconds(totalLimit);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime ldt = LocalDateTime.ofInstant(endTime, ZoneId.of("+8"));
            wfmActivity.setEndTimeStr(ldt.format(dtf));
            // 若结束时间早于当前时间，则将状态改为超时
            Instant currentTime = Instant.now();
            if(endTime.isBefore(currentTime) && Objects.equals("处理中",wfmActivity.getStatus())){
                wfmActivity.setStatus("已超时");
            }
        }
        return new TableResultResponse<>(result.getTotal(), list);
    }

    /**
     * 根据业务编号查询流程图json信息
     * @param businessNo 业务编号
     */
    public WfmActivity getImageJsonByBusinessNo(String businessNo) {
        try {
            return mapper.selectByPrimaryKey(businessNo);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}