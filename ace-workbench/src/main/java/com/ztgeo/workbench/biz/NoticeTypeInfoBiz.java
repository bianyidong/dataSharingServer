package com.ztgeo.workbench.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.Query;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ztgeo.workbench.entity.ApiBaseInfo;
import com.ztgeo.workbench.entity.NoticeBaseInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.ztgeo.workbench.entity.NoticeTypeInfo;
import com.ztgeo.workbench.mapper.NoticeTypeInfoMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import tk.mybatis.mapper.entity.Example;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 通知类型表
 *
 * @author zoupeidong
 * @version 2018-09-14 11:58:02
 * @email 806316372@qq.com
 */
@Service
public class NoticeTypeInfoBiz extends BusinessBiz<NoticeTypeInfoMapper, NoticeTypeInfo> {

    /**
     * 分页查询，获取所有通知类型，仅返回ID和类型字段值
     */
    public TableResultResponse<NoticeTypeInfo> getAllNoticeType(Map<String, Object> params) {
        Example example = new Example(NoticeTypeInfo.class);
        example.selectProperties("typeId", "typeDesc");
        if (!Objects.equals(params.get("typeId"), null) && !StringUtils.isBlank(params.get("typeId").toString())) {
            example.createCriteria().andLike("typeId", "%" + params.get("typeId").toString().trim() + "%").andEqualTo("crtUserId", BaseContextHandler.getUserID());
       }
        example.createCriteria().andEqualTo("crtUserId", BaseContextHandler.getUserID());
        example.orderBy("typeId").asc();
        Query query = new Query(params);
        this.query2criteria(query, example);
        Page<ApiBaseInfo> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<NoticeTypeInfo> list = this.selectByExample(example);
        return new TableResultResponse<>(result.getTotal(), list);
    }
    /**
     * 根据userId获取有权限访问的API信息
     *
     * @param userId 真实用户ID
     */
    public TableResultResponse<NoticeTypeInfo> getAllTypeIdByUserId(String userId, Map<String, Object> params) {
        Query query = new Query(params);
        List<NoticeTypeInfo> list = mapper.getAllTypeIdByUserId(userId, params.get("apiName"), (query.getPage() - 1) * query.getLimit(), query.getLimit());
        return new TableResultResponse<>(list.size(), list);
    }
    /**
     * 新增通知类型
     */
    public void insertNoticeTypeInfo(NoticeTypeInfo noticeTypeInfo) {
        try {
            Date currentDate = new Date(Instant.now().toEpochMilli());
            String noticeId = UUIDUtils.generateShortUuid();
            String typeId =noticeTypeInfo.getTypeId();
            String typeDesc=noticeTypeInfo.getTypeDesc();
            String crtUserId =BaseContextHandler.getUserID();
            String updUserId = BaseContextHandler.getUserID();
            mapper.insertNoticeTypeInfo(noticeId, typeId,typeDesc, currentDate, crtUserId, currentDate, updUserId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除通知类型
     * @param id
     */
    public void  deleteOneNoticeBaseInfo(String id){
        mapper.deleteByPrimaryKey(id);
    }

}