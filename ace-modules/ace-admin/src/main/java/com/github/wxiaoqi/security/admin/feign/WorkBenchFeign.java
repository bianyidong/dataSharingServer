package com.github.wxiaoqi.security.admin.feign;

import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import feign.Param;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ace-workbench",configuration = FeignApplyConfiguration.class)
public interface WorkBenchFeign {

    /**
     *  根据userID生成可公开的用户身份标识
     */
    @PostMapping("/userKeyInfo/generateUserIdentityId/{userId}")
    ObjectRestResponse<String> generateUserIdentityId(@PathVariable("userId") String userId,@RequestParam("username") String username,@RequestParam("name") String name);
}
