package com.ztgeo.workbench.rest;


import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ztgeo.workbench.biz.IpMappingInfoBiz;
import com.ztgeo.workbench.entity.IpMappingInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("ipMappingInfo")
//@CheckClientToken
//@CheckUserToken
public class IpMappingInfoController extends BaseController<IpMappingInfoBiz, IpMappingInfo, Integer> {

    @RequestMapping(value = "/addIPMapping", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("新增单个IP地址映射器")
    public ObjectRestResponse<IpMappingInfo> addIPMapping(@RequestBody IpMappingInfo ipMappingInfo) {
        baseBiz.insertSelective(ipMappingInfo);
        return new ObjectRestResponse<IpMappingInfo>().data(ipMappingInfo);
    }

    @RequestMapping(value = "/deleteIPMappingById/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation("根据id删除单行IP地址映射器")
    public ObjectRestResponse<IpMappingInfo> deleteIPMappingById(@PathVariable("id") Integer id) {
        baseBiz.deleteById(id);
        return new ObjectRestResponse<IpMappingInfo>();
    }

    @RequestMapping(value = "/selectIPMappingById/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("根据id查询单行IP地址映射器")
    public ObjectRestResponse<IpMappingInfo> selectIPMappingById(@PathVariable("id") Integer id) {
        IpMappingInfo ipMappingInfo = baseBiz.selectById(id);
        return new ObjectRestResponse<IpMappingInfo>().data(ipMappingInfo);
    }

    @RequestMapping(value = "/selectIPMappings", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("分页查询表格中所有的IP地址映射器信息")
    public TableResultResponse<IpMappingInfo> selectIPMappings(@RequestParam Map<String, Object> params) {
//        List<IpMappingInfo> ipMappingInfos = baseBiz.selectListAll();
//        Map<String, Object> map = new HashMap<>();
//        map.put("ipMappingInfos", ipMappingInfos);
//        return success(map);
        return baseBiz.selectIpMappingInfos(params);
    }

    @RequestMapping(value = "/updateIPMappingById/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation("根据id修改ip地址映射器")
    public ObjectRestResponse<IpMappingInfo> updateIPMappingById(@PathVariable("id") int id, @RequestBody IpMappingInfo ipMappingInfo) {
        ipMappingInfo.setId(id);
        baseBiz.updateSelectiveById(ipMappingInfo);
        return new ObjectRestResponse<IpMappingInfo>().data(ipMappingInfo);
    }


    private Map<String, Object> success(Map<String, Object> data) {
        Map<String, Object> map = new HashMap();
        map.putAll(data);
        map.put("status", true);
        map.put("reason", "操作成功");
        return map;
    }
//    private Map<String,Object> success() {
//        Map<String,Object> map = new HashMap();
//        map.put("status",true);
//        map.put("reason","操作成功");
//        return map;
//    }
}