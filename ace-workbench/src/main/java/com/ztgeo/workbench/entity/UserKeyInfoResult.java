package com.ztgeo.workbench.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 在原有的秘钥类上新增公共秘钥的属性
 * Create by Wei on 2018/9/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserKeyInfoResult extends UserKeyInfo{
    private String workBenchPubkey; //平台加签验证的私钥key

    private String routerSafeURL; // 安全接口转发路由URL

}
