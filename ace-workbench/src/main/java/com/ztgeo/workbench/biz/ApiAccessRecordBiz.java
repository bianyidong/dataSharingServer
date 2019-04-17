package com.ztgeo.workbench.biz;

import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.Query;
import com.ztgeo.workbench.crypto.HttpOperation;
import com.ztgeo.workbench.entity.ApiAccessRecord;
import com.ztgeo.workbench.entity.ApiBaseInfo;
import com.ztgeo.workbench.exception.ZtgeoBizException;
import com.ztgeo.workbench.mapper.ApiAccessRecordMapper;
import com.ztgeo.workbench.mapper.ApiBaseInfoMapper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * API访问记录表
 *
 * @author zoupeidong
 * @version 2018-10-09 14:54:00
 * @email 806316372@qq.com
 */
@Service
public class ApiAccessRecordBiz extends BusinessBiz<ApiAccessRecordMapper, ApiAccessRecord> {
    /**
     * 查询单条接口记录详细信息并发送
     */
    public ApiAccessRecord getOneApiAccessRecord(String id) {
        ApiAccessRecord apiAccessRecord= mapper.getOneApiAccessRecord(id);
        String url = apiAccessRecord.getApiUrl();
        String data = apiAccessRecord.getRequestData();
        // 发送并接收数据
        String rspData = HttpOperation.sendJsonHttp(url, data);
        if (rspData == null || rspData.length() == 0) {
            throw new RuntimeException("发送失败");
        }else {
            apiAccessRecord.setId(apiAccessRecord.getId());
            apiAccessRecord.setResponseData(rspData);
            apiAccessRecord.setStatus(0);
            mapper.updateByPrimaryKeySelective(apiAccessRecord);
        }
        return apiAccessRecord;
    }

    /**
     * 统计API最近一周访问记录
     */
    public JSONObject getApiAccessWeekRecord() {
        JSONObject resultJson = new JSONObject();
        String[] dateArr = new String[7];
        int[] countArr = new int[7];
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i <= 6; i++) {
            if (i != 0) {
                localDate = localDate.minusDays(1);
            }
            int countResult = mapper.getApiAccessWeekRecord(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
            String dateStr = dateTimeFormatter.format(localDate);
            dateArr[6 - i] = dateStr;
            countArr[6 - i] = countResult;
        }
        resultJson.put("dateArr", dateArr);
        resultJson.put("countArr", countArr);
        return resultJson;
    }

    /**
     * 分页查询通用接口
     *
     * @param params 分页参数
     */
    public TableResultResponse<ApiAccessRecord> getApiAccessRecordByPage(Map<String, Object> params) {
        try {
            // 获得开始和结束时间
            Object startTime = params.get("startTime");
            Object endTime = params.get("endTime");
            Example example = new Example(ApiAccessRecord.class);
            example.selectProperties("apiName", "apiUrl", "accessClientIp", "accessTime", "requestData", "responseData");
            example.orderBy("accessTime").desc();
            if (!Objects.equals(startTime, null) && !Objects.equals(endTime, null)) {
                example.createCriteria().andEqualTo("apiType", 1).andGreaterThanOrEqualTo("accessTime", startTime).andLessThanOrEqualTo("accessTime", endTime);
            } else {
                example.createCriteria().andEqualTo("apiType", 1);
            }
            params.remove("startTime");
            params.remove("endTime");
            Query query = new Query(params);
            this.query2criteria(query, example);
            Page<ApiAccessRecord> result = PageHelper.startPage(query.getPage(), query.getLimit());
            List<ApiAccessRecord> list = this.selectByExample(example);
            return new TableResultResponse<>(result.getTotal(), list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ZtgeoBizException("平台内部错误");
        }
    }

    /**
     * 分页查询安全接口
     *
     * @param params 分页参数,时间参数
     */
    public TableResultResponse<ApiAccessRecord> getSafeApiAccessRecordByPage(Map<String, Object> params) {
        try {
            // 获得开始和结束时间
            Object startTime = params.get("startTime");
            Object endTime = params.get("endTime");
            Example example = new Example(ApiAccessRecord.class);
            example.selectProperties("id", "apiName", "apiUrl", "accessClientIp", "accessTime", "requestData", "responseData");
            example.orderBy("accessTime").desc();
            if (!Objects.equals(BaseContextHandler.getUsername(), "admin")) {
                if (!Objects.equals(startTime, null) && !Objects.equals(endTime, null)) {
                    example.createCriteria().andEqualTo("apiType", 0).andGreaterThanOrEqualTo("accessTime", startTime).andLessThanOrEqualTo("accessTime", endTime).andEqualTo("userRealId", BaseContextHandler.getUserID());
                } else {
                    example.createCriteria().andEqualTo("apiType", 0).andEqualTo("userRealId", BaseContextHandler.getUserID());
                }
            } else {
                if (!Objects.equals(startTime, null) && !Objects.equals(endTime, null)) {
                    example.createCriteria().andEqualTo("apiType", 0).andGreaterThanOrEqualTo("accessTime", startTime).andLessThanOrEqualTo("accessTime", endTime);
                } else {
                    example.createCriteria().andEqualTo("apiType", 0);
                }
            }
            params.remove("startTime");
            params.remove("endTime");
            Query query = new Query(params);
            this.query2criteria(query, example);
            Page<ApiAccessRecord> result = PageHelper.startPage(query.getPage(), query.getLimit());
            List<ApiAccessRecord> list = this.selectByExample(example);
            return new TableResultResponse<>(result.getTotal(), list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ZtgeoBizException("平台内部错误");
        }
    }
}