package com.ztgeo.workbench.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.Query;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ztgeo.workbench.entity.ApiBaseInfo;
import com.ztgeo.workbench.entity.ApiRouterDefine;
import com.ztgeo.workbench.mapper.ApiRouterDefineMapper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 明文报文转发表
 *
 * @author zoupeidong
 * @email 806316372@qq.com
 * @version 2018-10-09 10:31:53
 */
@Service
public class ApiRouterDefineBiz extends BusinessBiz<ApiRouterDefineMapper,ApiRouterDefine> {

    /**
     * 新增router
     */
    public ApiRouterDefine addOneApiRouterDefine(ApiRouterDefine apiRouterDefine) {
        apiRouterDefine.setId(UUIDUtils.generateShortUuid());
        apiRouterDefine.setCrtUserId(BaseContextHandler.getUserID());
        apiRouterDefine.setCrtUserName(BaseContextHandler.getUsername());
        apiRouterDefine.setCrtTime(new Date());
        apiRouterDefine.setUpdUserId(BaseContextHandler.getUserID());
        apiRouterDefine.setUpdUserName(BaseContextHandler.getUsername());
        apiRouterDefine.setUpdTime(new Date());
        mapper.insert(apiRouterDefine);
        return apiRouterDefine;
    }

    /**
     * 查询router映射列表
     */
    public TableResultResponse<ApiRouterDefine> getAllApiRouterDefine(Map<String, Object> params) {
        Example example = new Example(ApiRouterDefine.class);
        Object url = params.get("url");
        if(Objects.equals(null,url)){ // 没有搜索条件
            example.createCriteria().andEqualTo("crtUserId",BaseContextHandler.getUserID());
        }else{ // 有搜索条件
            example.createCriteria().andEqualTo("crtUserId",BaseContextHandler.getUserID()).andLike("url","%"+url+"%");
        }
        Query query = new Query(params);
        this.query2criteria(query, example);
        Page<ApiRouterDefine> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<ApiRouterDefine> list = this.selectByExample(example);
        return new TableResultResponse<>(result.getTotal(), list);
    }
}