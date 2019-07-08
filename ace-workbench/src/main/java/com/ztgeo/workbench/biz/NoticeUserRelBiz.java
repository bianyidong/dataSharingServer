package com.ztgeo.workbench.biz;

import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.springframework.stereotype.Service;

import com.ztgeo.workbench.entity.NoticeUserRel;
import com.ztgeo.workbench.mapper.NoticeUserRelMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 通知用户关系表. 表示用户的通知该转发给哪些已配置过地址的用户
 *
 * @author zoupeidong
 * @version 2018-09-14 11:58:02
 * @email 806316372@qq.com
 */
@Service
public class NoticeUserRelBiz extends BusinessBiz<NoticeUserRelMapper, NoticeUserRel> {

    /**
     * 更新通知和用户关系表
     */
    public void updateNoticeUserRelList(String typeId,String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        List<String> addIdList = JSONObject.parseArray(jsonObject.getString("addIdList"), String.class);
        List<String> deleteIdList = JSONObject.parseArray(jsonObject.getString("deleteIdList"), String.class);
        // 添加通知的用户
        for (int i = 0; i < addIdList.size(); i++) {
            NoticeUserRel noticeUserRel = new NoticeUserRel();
            noticeUserRel.setRelId(UUIDUtils.generateShortUuid());
            noticeUserRel.setUserRealId(BaseContextHandler.getUserID());
            noticeUserRel.setTypeId(typeId);
            noticeUserRel.setNoticeId(addIdList.get(i));
            mapper.insertSelective(noticeUserRel);
        }
        // 删除不需通知的用户
        for (int i = 0; i < deleteIdList.size(); i++) {
            mapper.deleteByUserIdAndNoticeId(BaseContextHandler.getUserID(),deleteIdList.get(i),typeId);
        }
    }

    /**
     * 查询该用户已配置的需通知的机构列表
     *
     * @param typeId 通知类型ID
     * @return 该用户已配置的需通知的机构列表
     */
    public List<NoticeUserRel> selectNoticeCorpList(String typeId) {
        String userId = BaseContextHandler.getUserID();
        Example example = new Example(NoticeUserRel.class);
        example.selectProperties("noticeId");
        example.createCriteria().andEqualTo("userRealId", userId).andEqualTo("typeId", typeId);
        return  mapper.selectByExample(example);
    }
}