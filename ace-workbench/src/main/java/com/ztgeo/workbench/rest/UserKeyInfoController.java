package com.ztgeo.workbench.rest;

import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ztgeo.workbench.biz.UserKeyInfoBiz;
import com.ztgeo.workbench.entity.User;
import com.ztgeo.workbench.entity.UserKeyInfo;
import com.ztgeo.workbench.entity.UserKeyInfoResult;
import com.ztgeo.workbench.feign.AdminFeign;
import feign.Param;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

/*
* 用于各种key的生成策略
* */
@RestController
@RequestMapping("userKeyInfo")
@CheckClientToken
@CheckUserToken
public class UserKeyInfoController extends BaseController<UserKeyInfoBiz, UserKeyInfo, String> {

    @Autowired
    private AdminFeign adminFeign;

    /**
     * 用于返回用户秘钥信息 无需传入任何信息
     *
     * @return 返回秘钥相关信息不包含敏感信息
     */
    @ApiOperation(value = "查询用户秘钥相关信息，不包含敏感性信息（不对外公开的信息被忽略）")
    @GetMapping("getUserSecretKey")
    public ObjectRestResponse<UserKeyInfoResult> getUserSecretKey() {
        return baseBiz.getUserSecretKey();
    }

    /**
     * 用于秘钥相关的重新生成/和一键获取秘钥信息
     *
     * @return 返回更新后的秘钥相关信息
     */
    @ApiOperation(value = "用于秘钥相关的重新生成/和一键获取秘钥信息，返回的数据中包含身份标识和平台公钥，可直接填充到表单中")
    @PutMapping("updateUserSecretKey")
    public ObjectRestResponse<UserKeyInfoResult> updateUserSecretKey(@ApiParam("语言类型，java->true else->false") boolean langType) {
        return baseBiz.updateUserSecretKey(langType);
    }

    /**
     * 根据userID生成可公开的用户身份标识
     */
    @PostMapping("generateUserIdentityId/{userId}")
    public ObjectRestResponse<String> generateUserIdentityId(@PathVariable("userId") String userId, @RequestParam("username") String username, @RequestParam("name") String name) {
        baseBiz.generateUserIdentityId(userId, username, name);
        return new ObjectRestResponse<String>().data("");
    }

    /**
     * 获取单个机构基础信息
     */
    @GetMapping("getNoticeCorpBaseInfo")
    public ObjectRestResponse<JSONObject> getNoticeCorpBaseInfo() {
        return adminFeign.getNoticeCorpBaseInfoById(BaseContextHandler.getUserID());
    }

    /**
     * 更新单个用户信息
     */
    @PutMapping("updateNoticeCorpBaseInfo")
    public ObjectRestResponse<User> updateNoticeCorpBaseInfo(@RequestBody User user) {
        return adminFeign.updateNoticeCropBaseInfo(user, BaseContextHandler.getUserID());
    }

}