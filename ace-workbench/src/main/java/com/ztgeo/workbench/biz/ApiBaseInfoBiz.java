package com.ztgeo.workbench.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.Query;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ztgeo.workbench.entity.ApiBodyInfo;
import com.ztgeo.workbench.entity.ApiHeaderInfo;
import com.ztgeo.workbench.entity.ApiQueryInfo;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ztgeo.workbench.entity.ApiBaseInfo;
import com.ztgeo.workbench.mapper.ApiBaseInfoMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import tk.mybatis.mapper.entity.Example;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author zoupeidong
 * @version 2018-08-27 16:57:29
 * @email 806316372@qq.com
 */
@Service
public class ApiBaseInfoBiz extends BusinessBiz<ApiBaseInfoMapper, ApiBaseInfo> {

    @Autowired
    private ApiHeaderInfoBiz apiHeaderInfoBiz;

    @Autowired
    private ApiQueryInfoBiz apiQueryInfoBiz;

    @Autowired
    private ApiBodyInfoBiz apiBodyInfoBiz;
    @Value("${customAttributes.URL}")
    private String URL; // 存储用户发送数据的数据库名

    /**
     * 查询单条Api详细信息
     */
    public ApiBaseInfo getOneApiBaseInfo(String id) {
        return mapper.getOneApiBaseInfo(id);
    }

    /**
     * 根据userId获取有权限访问的API信息
     *
     * @param userId 真实用户ID
     */
    public TableResultResponse<ApiBaseInfo> getNotAllApiBaseInfo(String userId, Map<String, Object> params) {
        Query query = new Query(params);
        List<ApiBaseInfo> list = mapper.getNotAllApiBaseInfo(userId, params.get("apiName"), (query.getPage() - 1) * query.getLimit(), query.getLimit());
        list.stream().forEach(apiBaseInfo -> {
//            String path = apiBaseInfo.getBaseUrl() + StringUtils.substringBeforeLast(apiBaseInfo.getdPath(), "/") + apiBaseInfo.getPath();
            String path = apiBaseInfo.getBaseUrl()  + apiBaseInfo.getPath();
            apiBaseInfo.setPath(path);
        });
        return new TableResultResponse<>(list.size(), list);
    }

    /**
     * 根据userId获取非本用户的所有接口信息
     *
     * @param userId 真实用户ID
     */
    public TableResultResponse<ApiBaseInfo> getNotAllInterfaceInfo(String userId, Map<String, Object> params) {
        Query query = new Query(params);
        List<ApiBaseInfo> list = mapper.getNotAllInterfaceInfo(userId, params.get("apiName"), (query.getPage() - 1) * query.getLimit(), query.getLimit());
        list.stream().forEach(apiBaseInfo -> {
            String path = apiBaseInfo.getBaseUrl()+ apiBaseInfo.getPath();
            apiBaseInfo.setPath(path);
        });
        return new TableResultResponse<>(list.size(), list);
    }
    /**
     * 获取当前用户所拥有的API信息list
     *
     * @param params 包括模糊查询字段apiName,分页参数page和limit
     * @return 分页后的当前用户所拥有的API信息list
     */
    public TableResultResponse<ApiBaseInfo> getAllApiBaseInfo(Map<String, Object> params) {
        Example example = new Example(ApiBaseInfo.class);
        example.selectProperties("apiId", "apiName", "apiPubkey", "baseUrl", "path", "method", "enabledStatus", "responsiblePersonName", "responsiblePersonTel");
        if (!Objects.equals(params.get("apiName"), null) && !StringUtils.isBlank(params.get("apiName").toString())) {
            example.createCriteria().andEqualTo("apiType", 0).andEqualTo("apiOwnerId", BaseContextHandler.getUserID()).andLike("apiName", "%" + params.get("apiName").toString().trim() + "%");
        } else {
            example.createCriteria().andEqualTo("apiType", 0).andEqualTo("apiOwnerId", BaseContextHandler.getUserID());
        }
        Query query = new Query(params);
        this.query2criteria(query, example);
        Page<ApiBaseInfo> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<ApiBaseInfo> list = this.selectByExample(example);
        return new TableResultResponse<>(result.getTotal(), list);
    }

    /**
     * 获取当前用户所拥有的API信息list
     *
     * @param params 包括模糊查询字段apiName,分页参数page和limit
     * @return 分页后的当前用户所拥有的API信息list
     */
    public TableResultResponse<ApiBaseInfo> getAllApiBaseInfoIsNull(Map<String, Object> params) {
        Example example = new Example(ApiBaseInfo.class);
        example.selectProperties("apiId", "apiName", "apiPubkey", "baseUrl", "path", "method", "enabledStatus", "responsiblePersonName", "responsiblePersonTel");
        if (!Objects.equals(params.get("apiName"), null) && !StringUtils.isBlank(params.get("apiName").toString())) {
            example.createCriteria().andEqualTo("apiOwnerId", BaseContextHandler.getUserID()).andEqualTo("apiType","1").andLike("apiName", "%" + params.get("apiName").toString().trim() + "%");
        } else {
            example.createCriteria().andEqualTo("apiOwnerId", BaseContextHandler.getUserID()).andEqualTo("apiType", "1");
        }
        Query query = new Query(params);
        this.query2criteria(query, example);
        Page<ApiBaseInfo> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<ApiBaseInfo> list = this.selectByExample(example);
        return new TableResultResponse<>(result.getTotal(), list);
    }

    /**
     * 添加不带加密加签的信息
     *
     * @param apiBaseInfo 基础API信息
     */
    public void addOneApiBaseInfoNotPubkey(ApiBaseInfo apiBaseInfo) {
        // 获取当前时间
        Date currentDate = new Date(Instant.now().toEpochMilli());
        // 设置ApiBaseInfo表主键
        apiBaseInfo.setApiId(UUIDUtils.generateShortUuid());
        // 设置API拥有者ID
        apiBaseInfo.setApiOwnerId(BaseContextHandler.getUserID());
        // 设置API拥有者名称
        apiBaseInfo.setApiOwnerName(BaseContextHandler.getUsername());
        // 设置API记录创建者ID
        apiBaseInfo.setCrtUserId(BaseContextHandler.getUserID());
        // 设置API记录修改者ID
        apiBaseInfo.setUpdUserId(BaseContextHandler.getUserID());
        // 设置API记录创建时间
        apiBaseInfo.setCrtTime(currentDate);
        // 设置APi记录修改时间
        apiBaseInfo.setUpdTime(currentDate);
        //设置接口类型0代表安全接口，1代表通用接口
        apiBaseInfo.setApiType("1");
        mapper.insert(apiBaseInfo);
    }

    /**
     * 新增API接口
     */
    public void addOneApiBaseInfo(ApiBaseInfo apiBaseInfo) {
        try {
            // 获取当前时间
            Date currentDate = new Date(Instant.now().toEpochMilli());
            // 设置ApiBaseInfo表主键
            apiBaseInfo.setApiId(UUIDUtils.generateShortUuid());
            apiBaseInfo.setApiPubkey(Hex.encodeHexString(MessageDigest.getInstance("sha").digest(apiBaseInfo.getApiId().getBytes("UTF-8"))));
            // 设置API拥有者ID
            apiBaseInfo.setApiOwnerId(BaseContextHandler.getUserID());
            // 设置API拥有者名称
            apiBaseInfo.setApiOwnerName(BaseContextHandler.getUsername());
            // 设置API记录创建者ID
            apiBaseInfo.setCrtUserId(BaseContextHandler.getUserID());
            // 设置API记录修改者ID
            apiBaseInfo.setUpdUserId(BaseContextHandler.getUserID());
            // 设置API记录创建时间
            apiBaseInfo.setCrtTime(currentDate);
            // 设置APi记录修改时间
            apiBaseInfo.setUpdTime(currentDate);
            //设置接口类型0安全1通用
            apiBaseInfo.setApiType("0");
            mapper.insert(apiBaseInfo);
            // 设置headerList参数并存库
            List<ApiHeaderInfo> apiHeaderInfoList = apiBaseInfo.getApiHeaderInfoList();
            for (int i = 0; i < apiHeaderInfoList.size(); i++) {
                ApiHeaderInfo apiHeaderInfo = apiHeaderInfoList.get(i);
                apiHeaderInfo.setHeaderId(UUIDUtils.generateShortUuid());
                apiHeaderInfo.setApiId(apiBaseInfo.getApiId());
                apiHeaderInfo.setCrtUserId(BaseContextHandler.getUserID());
                // 设置API记录修改者ID
                apiHeaderInfo.setUpdUserId(BaseContextHandler.getUserID());
                // 设置API记录创建时间
                apiHeaderInfo.setCrtTime(currentDate);
                // 设置APi记录修改时间
                apiHeaderInfo.setUpdTime(currentDate);
                apiHeaderInfoBiz.insertSelective(apiHeaderInfo);
            }
            // 设置QueryString参数并存库
            List<ApiQueryInfo> apiQueryInfoList = apiBaseInfo.getApiQueryInfoList();
            for (int i = 0; i < apiQueryInfoList.size(); i++) {
                ApiQueryInfo apiQueryInfo = apiQueryInfoList.get(i);
                apiQueryInfo.setQueryId(UUIDUtils.generateShortUuid());
                apiQueryInfo.setApiId(apiBaseInfo.getApiId());
                apiQueryInfo.setCrtUserId(BaseContextHandler.getUserID());
                // 设置API记录修改者ID
                apiQueryInfo.setUpdUserId(BaseContextHandler.getUserID());
                // 设置API记录创建时间
                apiQueryInfo.setCrtTime(currentDate);
                // 设置APi记录修改时间
                apiQueryInfo.setUpdTime(currentDate);
                apiQueryInfoBiz.insertSelective(apiQueryInfo);
            }
            // 设置body
            List<ApiBodyInfo> apiBodyInfoList = apiBaseInfo.getApiBodyInfoList();
            for (int i = 0; i < apiBodyInfoList.size(); i++) {
                ApiBodyInfo apiBodyInfo = apiBodyInfoList.get(i);
                apiBodyInfo.setBodyId(UUIDUtils.generateShortUuid());
                apiBodyInfo.setApiId(apiBaseInfo.getApiId());
                apiBodyInfo.setCrtUserId(BaseContextHandler.getUserID());
                // 设置API记录修改者ID
                apiBodyInfo.setUpdUserId(BaseContextHandler.getUserID());
                // 设置API记录创建时间
                apiBodyInfo.setCrtTime(currentDate);
                // 设置APi记录修改时间
                apiBodyInfo.setUpdTime(currentDate);
                apiBodyInfoBiz.insertSelective(apiBodyInfo);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改单个API，同时删除相关的其他信息(header,queryString,body)
     */
    public void updateOneApiBaseInfo(ApiBaseInfo apiBaseInfo) {
        // 获取当前时间
        Date currentDate = new Date(Instant.now().toEpochMilli());
        // 设置APi记录修改时间
        apiBaseInfo.setUpdTime(currentDate);
        // 更新api基础信息
        mapper.updateByPrimaryKeySelective(apiBaseInfo);
        // 更新api的header信息
        List<ApiHeaderInfo> apiHeaderInfoList = apiBaseInfo.getApiHeaderInfoList();
        for (int i = 0; i < apiHeaderInfoList.size(); i++) {
            ApiHeaderInfo apiHeaderInfo = apiHeaderInfoList.get(i);
            apiHeaderInfo.setApiId(apiBaseInfo.getApiId());
            // 判断主键是否为null，为null则新增，不为null则修改
            if (Objects.equals(null, apiHeaderInfo.getHeaderId())) {
                apiHeaderInfo.setHeaderId(UUIDUtils.generateShortUuid());
                apiHeaderInfo.setCrtUserId(BaseContextHandler.getUserID());
                // 设置API记录修改者ID
                apiHeaderInfo.setUpdUserId(BaseContextHandler.getUserID());
                // 设置API记录创建时间
                apiHeaderInfo.setCrtTime(currentDate);
                // 设置APi记录修改时间
                apiHeaderInfo.setUpdTime(currentDate);
                apiHeaderInfoBiz.insertSelective(apiHeaderInfo);
            } else {
                // 设置API记录修改者ID
                apiHeaderInfo.setUpdUserId(BaseContextHandler.getUserID());
                // 设置APi记录修改时间
                apiHeaderInfo.setUpdTime(currentDate);
                apiHeaderInfoBiz.updateSelectiveById(apiHeaderInfo);
            }
        }
        // 设置QueryString参数并存库
        List<ApiQueryInfo> apiQueryInfoList = apiBaseInfo.getApiQueryInfoList();
        for (int i = 0; i < apiQueryInfoList.size(); i++) {
            ApiQueryInfo apiQueryInfo = apiQueryInfoList.get(i);
            apiQueryInfo.setApiId(apiBaseInfo.getApiId());
            // 判断主键是否为null，为null则新增，不为null则修改
            if (Objects.equals(null, apiQueryInfo.getQueryId())) {
                apiQueryInfo.setQueryId(UUIDUtils.generateShortUuid());
                apiQueryInfo.setCrtUserId(BaseContextHandler.getUserID());
                // 设置API记录修改者ID
                apiQueryInfo.setUpdUserId(BaseContextHandler.getUserID());
                // 设置API记录创建时间
                apiQueryInfo.setCrtTime(currentDate);
                // 设置APi记录修改时间
                apiQueryInfo.setUpdTime(currentDate);
                apiQueryInfoBiz.insertSelective(apiQueryInfo);
            } else {
                // 设置API记录修改者ID
                apiQueryInfo.setUpdUserId(BaseContextHandler.getUserID());
                // 设置APi记录修改时间
                apiQueryInfo.setUpdTime(currentDate);
                apiQueryInfoBiz.updateSelectiveById(apiQueryInfo);
            }
        }
        // 设置body
        List<ApiBodyInfo> apiBodyInfoList = apiBaseInfo.getApiBodyInfoList();
        for (int i = 0; i < apiBodyInfoList.size(); i++) {
            ApiBodyInfo apiBodyInfo = apiBodyInfoList.get(i);
            apiBodyInfo.setApiId(apiBaseInfo.getApiId());
            // 判断主键是否为null，为null则新增，不为null则修改
            if (Objects.equals(null, apiBodyInfo.getBodyId())) {
                apiBodyInfo.setBodyId(UUIDUtils.generateShortUuid());
                apiBodyInfo.setCrtUserId(BaseContextHandler.getUserID());
                // 设置API记录修改者ID
                apiBodyInfo.setUpdUserId(BaseContextHandler.getUserID());
                // 设置API记录创建时间
                apiBodyInfo.setCrtTime(currentDate);
                // 设置APi记录修改时间
                apiBodyInfo.setUpdTime(currentDate);
                apiBodyInfoBiz.insertSelective(apiBodyInfo);
            } else {
                // 设置API记录修改者ID
                apiBodyInfo.setUpdUserId(BaseContextHandler.getUserID());
                // 设置APi记录修改时间
                apiBodyInfo.setUpdTime(currentDate);
                apiBodyInfoBiz.updateSelectiveById(apiBodyInfo);
            }
        }
    }

    /**
     * 删除单个API，同时删除相关的其他信息(header,queryString,body)
     *
     * @param id api主键ID
     */
    public void deleteOneApiBaseInfo(String id) {
        // 删除api基础信息
        mapper.deleteByPrimaryKey(id);
        // 删除api的header信息
        ApiHeaderInfo apiHeaderInfo = new ApiHeaderInfo();
        apiHeaderInfo.setApiId(id);
        apiHeaderInfoBiz.delete(apiHeaderInfo);
        // 删除api的queryString信息
        ApiQueryInfo apiQueryInfo = new ApiQueryInfo();
        apiQueryInfo.setApiId(id);
        apiQueryInfoBiz.delete(apiQueryInfo);
        // 删除api的body信息
        ApiBodyInfo apiBodyInfo = new ApiBodyInfo();
        apiBodyInfo.setApiId(id);
        apiBodyInfoBiz.delete(apiBodyInfo);
    }

    /**
     * 查询用户有权限访问的所有API信息
     *
     * @param userId  用户真实ID
     * @param apiName API名称
     * @param start   起始位置
     * @param limit   查询条数
     * @return 用户有权限访问的所有API信息List
     */
    public List<ApiBaseInfo> getAPIInfoByUserId(String userId, Object apiName, int start, int limit) {
        return mapper.getAPIInfoByUserId(userId, apiName, start, limit);
    }

    /**
     * 查询所有API信息，返回API名称和标识列表
     */
    public List<ApiBaseInfo> getAllStepApiBaseInfo() {
        List<ApiBaseInfo> list = mapper.getAllStepApiBaseInfo();
        return list;
    }

    /**
     * 查询所有api信息，包括安全接口和通用接口
     */
    public TableResultResponse<ApiBaseInfo> getAllApiInfoByPage(Map<String, Object> params) {
        Example example = new Example(ApiBaseInfo.class);
        example.selectProperties("apiId", "apiName", "apiPubkey", "baseUrl", "path", "method", "enabledStatus", "responsiblePersonName", "responsiblePersonTel");
        if (!Objects.equals(params.get("apiName"), null) && !StringUtils.isBlank(params.get("apiName").toString())) {
            example.createCriteria().andLike("apiName", "%" + params.get("apiName").toString().trim() + "%");
        }
        Query query = new Query(params);
        this.query2criteria(query, example);
        Page<ApiBaseInfo> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<ApiBaseInfo> list = this.selectByExample(example);
        return new TableResultResponse<>(result.getTotal(), list);
    }
}