package com.ztgeo.workbench.biz;

import com.alibaba.fastjson.JSONObject;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;

import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.Query;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ztgeo.workbench.entity.*;

import com.ztgeo.workbench.exception.ZtgeoBizException;
import com.ztgeo.workbench.mapper.DataBackUpMapper;
import com.ztgeo.workbench.mapper.NoticeBaseInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import tk.mybatis.mapper.entity.Example;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通知基础信息配置表
 *
 * @author zoupeidong
 * @version 2018-09-14 11:58:02
 * @email 806316372@qq.com
 */
@Service
public class NoticeBaseInfoBiz extends BusinessBiz<NoticeBaseInfoMapper, NoticeBaseInfo> {

    @Autowired
    private NoticeUserRelBiz noticeUserRelBiz;
    /**
     * 获取所有可接收通知的用户名和主键ID
     */
    public JSONObject getAllActiviveUser(String id) {
        // 查询所有可通知的机构列表to
        Example example = new Example(NoticeBaseInfo.class);
        example.selectProperties("noticeId", "name");
        example.createCriteria().andNotEqualTo("userRealId", BaseContextHandler.getUserID());
        List<NoticeBaseInfo> allNoticeCorp = mapper.selectByExample(example); // 所有可通知机构
        System.out.println("==========="+allNoticeCorp);
        // 查询该用户已配置的需通知的机构列表
        List<NoticeUserRel> noticeCorpList = noticeUserRelBiz.selectNoticeCorpList(id);
        System.out.println("========="+noticeCorpList);
        // 封装结果
        JSONObject resultJson = new JSONObject();
        resultJson.put("allNoticeCorp", allNoticeCorp);
        resultJson.put("noticeCorpList", noticeCorpList);
        return resultJson;
    }

    public ObjectRestResponse<NoticeBaseInfo> getnoticeBaseInfo() {
        //1.调用获取id的方法获得实体类
        Example example = new Example(NoticeBaseInfo.class);
        example.createCriteria().andEqualTo("userRealId", getUserId());
        example.selectProperties("method", "noticePath");
        List<NoticeBaseInfo> userBaseList = selectByExample(example);
        if (userBaseList.size() == 0) {
            throw new ZtgeoBizException("根据ID未查询到相关用户！");
        }
        //2. 通过属性转移到返回类中
        NoticeBaseInfo noticeBaseInfo = new NoticeBaseInfo();
        BeanUtils.copyProperties(userBaseList.get(0), noticeBaseInfo);
        return new ObjectRestResponse<NoticeBaseInfo>().data(noticeBaseInfo);

    }

    public void updatenoticeBaseInfo(NoticeBaseInfo noticeBaseInfo) {
        String userId = getUserId();
        String method = noticeBaseInfo.getMethod();
        String noticePath = noticeBaseInfo.getNoticePath();
        Date currentDate = new Date(Instant.now().toEpochMilli());
        mapper.updatenoticeBaseInfo(method, noticePath, currentDate, userId);
    }

    /**
     * 新增通知基础配置
     */
    public void insertNoticeBaseInfo(NoticeBaseInfo noticeBaseInfo) {
        try {
            Date currentDate = new Date(Instant.now().toEpochMilli());
            String noticeId = UUIDUtils.generateShortUuid();
            String userRealId = BaseContextHandler.getUserID();
            String name=BaseContextHandler.getName();
            String username =BaseContextHandler.getUsername();
            String noticePath = noticeBaseInfo.getNoticePath();
            String method = noticeBaseInfo.getMethod();
            String noticeNote ="";
            mapper.insertNoticeBaseInfo(noticeId, userRealId,name, username, noticePath, method, noticeNote, currentDate, currentDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean selectNoticeBaseInfoByUserid() {
        return mapper.selectNoticeBaseInfoByUserid(BaseContextHandler.getUserID()) == 1 ? true : false;
    }

    public String getUserId() {
        String id = BaseContextHandler.getUserID();
        if (StringUtils.isBlank(id)) {
            throw new ZtgeoBizException("未获取到用户ID！");
        }
        return id;
    }
}