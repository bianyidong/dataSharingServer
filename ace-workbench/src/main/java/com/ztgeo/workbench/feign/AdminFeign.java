package com.ztgeo.workbench.feign;

import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;

import com.ztgeo.workbench.entity.NoticeBaseInfo;
import com.ztgeo.workbench.entity.User;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "ace-admin", configuration = FeignApplyConfiguration.class)
public interface AdminFeign {

    /**
     * 获取所有用户信息
     */
    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    List<User> all();

    /**
     * 获取单个用户信息
     *
     * @deprecated
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    ObjectRestResponse<JSONObject> getnoticeCorpBaseInfoByid(@PathVariable("id") String id);

    /**
     * 获取单个用户信息
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    ObjectRestResponse<JSONObject> getNoticeCorpBaseInfoById(@PathVariable("id") String id);

    /**
     * 修改单个用户信息
     *
     * @deprecated
     */
    @RequestMapping(value = "/user/updateUser/{id}", method = RequestMethod.PUT)
    ObjectRestResponse<User> updatenoticeCropBaseInfo(@RequestBody User user, @PathVariable("id") String id);

    /**
     * 修改单个用户信息
     */
    @RequestMapping(value = "/user/updateUser/{id}", method = RequestMethod.PUT)
    ObjectRestResponse<User> updateNoticeCropBaseInfo(@RequestBody User user, @PathVariable("id") String id);

}
