package com.ztgeo.workbench.biz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.Query;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ztgeo.workbench.entity.ApiAuthRel;
import com.ztgeo.workbench.entity.ApiBaseInfo;
import com.ztgeo.workbench.entity.User;
import com.ztgeo.workbench.mapper.ApiAuthRelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author zoupeidong
 * @version 2018-08-27 16:57:29
 * @email 806316372@qq.com
 */
@Service
public class ApiAuthRelBiz extends BusinessBiz<ApiAuthRelMapper, ApiAuthRel> {

    @Autowired
    private ApiBaseInfoBiz apiBaseInfoBiz;

    @Autowired
    private UserKeyInfoBiz userKeyInfoBiz;

    /**
     * 根据userId获取有权限访问的API信息
     */
    public TableResultResponse<ApiBaseInfo> getAPIInfoByUserId(String userId, Map<String, Object> params) {
        Query query = new Query(params);
        List<ApiBaseInfo> list = apiBaseInfoBiz.getAPIInfoByUserId(userId, params.get("apiName"), (query.getPage() - 1) * query.getLimit(), query.getLimit());
        return new TableResultResponse<>(list.size(), list);
    }

    /**
     * 获取所有用户权限信息
     */
    public JSONObject getAllUserAuth(List<User> userList, String apiPubKey) {
        JSONObject userAuthJson = new JSONObject(); // 用于返回结果
        List<String> userOwnAuthList = new ArrayList<>(); // 有权限的用户ID集合
        JSONArray alluserJson = new JSONArray(); // 所有用户信息
        // 查询所有用户是否有权限
        for (User user : userList) {
            if (!Objects.equals(user.getId(), BaseContextHandler.getUserID()) && !Objects.equals(1, BaseContextHandler.getUserID())) { // 不包括当前用户本身和超级管理员
                String userIdentityId = userKeyInfoBiz.selectUserIdentityIdByUserId(user.getId()); // 查询用户身份标识
                if (!Objects.equals(userIdentityId, null)) {
                    int count = mapper.selectUserAuthByUserIdAndApiId(userIdentityId, apiPubKey); //查询用户权限,1条或0条，大于1条说明数据有误
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("userPubkey", userIdentityId);
                    jsonObject.put("username", user.getName());
                    alluserJson.add(jsonObject);
                    if (1 == count) { // 有权限
                        userOwnAuthList.add(userIdentityId);
                    }
                }
            }
        }
        // 封装结果,{key1:所有用户pubkey和名称,key2:有权限的ID数组列表}
        userAuthJson.put("allUserInfo", alluserJson);
        userAuthJson.put("ownAuth", userOwnAuthList);
        return userAuthJson;
    }

    /**
     * 更新用户权限
     */
    public void updateUserAuth(String userJson, String apiPubkey) {
        JSONObject userAuth = JSONObject.parseObject(userJson);
        List<String> addIdList = JSONObject.parseArray(userAuth.get("addIdList").toString(), String.class);
        List<String> deleteIdList = JSONObject.parseArray(userAuth.get("deleteIdList").toString(), String.class);
        // 添加权限
        for (int i = 0; i < addIdList.size(); i++) {
            ApiAuthRel apiAuthRel = new ApiAuthRel();
            apiAuthRel.setRelId(UUIDUtils.generateShortUuid());
            apiAuthRel.setApiPubkey(apiPubkey);
            apiAuthRel.setUserPubkey(addIdList.get(i));
            mapper.insertSelective(apiAuthRel);
        }
        // 删除权限
        for (int i = 0; i < deleteIdList.size(); i++) {
            mapper.deleteByApiPubkeyAndUserPubkey(deleteIdList.get(i), apiPubkey);
        }
    }

}