package com.ztgeo.workbench.rest;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ztgeo.workbench.biz.BlackListIpBiz;
import com.ztgeo.workbench.entity.BlackListIp;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("blackListIp")
//@CheckClientToken
//@CheckUserToken
public class BlackListIpController extends BaseController<BlackListIpBiz, BlackListIp, Integer> {
    /**
     * 分页查询IP黑名单信息
     */
    @RequestMapping(value = "getAllBlackListIp", method = RequestMethod.GET)
    public TableResultResponse<BlackListIp> getAllBlackListIp(@RequestParam Map<String, Object> params) {
        return baseBiz.getAllBlackListIp(params);
    }

    /**
     * 查询单条IP详细信息
     */
    @RequestMapping(value = "getOneBlackIp/{id}", method = RequestMethod.GET)
    public ObjectRestResponse<BlackListIp> getOneBlackIp(@PathVariable int id) {
        BlackListIp blackListIp = baseBiz.getOneBlackIp(id);
        return new ObjectRestResponse<BlackListIp>().data(blackListIp);
    }

    /**
     * 添加黑名单IP
     */
    @RequestMapping(value = "addOneBlackIp", method = RequestMethod.POST)
    public ObjectRestResponse<BlackListIp> addOneBlackIp(@RequestBody BlackListIp blackListIp) {
        baseBiz.addOneBlackIp(blackListIp);
        return new ObjectRestResponse<BlackListIp>().data(blackListIp);
    }

    /**
     * 修改黑名单信息
     */
    @RequestMapping(value = "updateOneBlackIp/{id}", method = RequestMethod.PUT)
    public ObjectRestResponse<BlackListIp> updateOneBlackIp(@PathVariable int id, @RequestBody BlackListIp blackListIp) {
        blackListIp.setIpId(id);
        baseBiz.updateOneBlackIp(blackListIp);
        return new ObjectRestResponse<BlackListIp>().data(blackListIp);
    }

}