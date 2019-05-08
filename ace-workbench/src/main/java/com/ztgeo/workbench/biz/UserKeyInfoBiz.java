package com.ztgeo.workbench.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;

import com.ztgeo.workbench.config.SecretConfig;
import com.ztgeo.workbench.entity.ApiBaseInfo;
import com.ztgeo.workbench.entity.UserKeyInfoResult;
import com.ztgeo.workbench.exception.ZtgeoBizException;
import com.ztgeo.workbench.mapper.UserKeyInfoMapper;
import com.ztgeo.workbench.utils.ThrowBizExceptionUtil;
import com.ztgeo.workbench.utils.UserkeyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ztgeo.workbench.entity.UserKeyInfo;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户密钥表
 *
 * @author Mr.AG
 * @email 1205690873@qq.com
 * @version 2018-09-03 15:49:50
 */
@Service
public class UserKeyInfoBiz extends BusinessBiz<UserKeyInfoMapper,UserKeyInfo> {

    @Autowired
    private SecretConfig secretConfig;
    @Value("${customAttributes.safeURL}")
    private String routerSafeURL;

    /**
     *@描述  获取用户秘钥相关信息
     *@参数  []type
     *@返回值  com.github.wxiaoqi.security.common.msg.ObjectRestResponse<com.ztgeo.workbench.entity.UserKeyInfo>
     *@创建人  Wei
     *@创建时间  2018/9/6
     */
    public ObjectRestResponse<UserKeyInfoResult> getUserSecretKey() {
       //1.调用获取id的方法获得实体类
        Example example = new Example(UserKeyInfo.class);
        example.createCriteria().andEqualTo("userRealId",getUserId());
        example.selectProperties("symmetricPubkey","userIdentityId","signSecretKey", "signPubKey");
        List<UserKeyInfo> userKeyInfoList = selectByExample(example);
        if(userKeyInfoList.size()==0){
            throw new ZtgeoBizException("根据ID未查询到相关用户！");
        }
        //2. 通过属性转移到返回类中
        UserKeyInfoResult userKeyInfoResult = new UserKeyInfoResult();
        BeanUtils.copyProperties(userKeyInfoList.get(0),userKeyInfoResult);
        //3. 从配置文件中读取填充平台验签的公钥值
        userKeyInfoResult.setWorkBenchPubkey(secretConfig.getWFpubKey());
        userKeyInfoResult.setRouterSafeURL(routerSafeURL);
        return new ObjectRestResponse<UserKeyInfo>().data(userKeyInfoResult);
    }

    public String getUserId(){
        String id = BaseContextHandler.getUserID();
        if (StringUtils.isBlank(id)){
            throw new ZtgeoBizException("未获取到用户ID！");
        }
        return id;
    }

    /**
     *@描述  客户端秘钥对rsa身份库中读取，平台秘钥对读取配置文件
     *@参数  [] 判断语言类型，Java或者其他语言，显示不同的格式
     *@返回值  com.github.wxiaoqi.security.common.msg.ObjectRestResponse<com.ztgeo.workbench.entity.UserKeyInfoResult>
     *@创建人  Wei
     *@创建时间  2018/9/6
     */
    public ObjectRestResponse<UserKeyInfoResult> updateUserSecretKey(boolean type) {
        String workBenchPubKey = null;//平台公钥
        String clientPriKey = null;//客户端私钥
        String symmetricPubkey=null; //参谋秘钥
        //1.调用获取id的方法获得实体类
        Example example = new Example(UserKeyInfo.class);
        example.createCriteria().andEqualTo("userRealId",getUserId());
        example.selectProperties("symmetricPubkey","userIdentityId","signSecretKey", "signPubKey");
        List<UserKeyInfo> userKeyInfoList = selectByExample(example);
        if(userKeyInfoList.size()==0){
            throw new ZtgeoBizException("根据ID未查询到相关用户！");
        }
       //2.判断不通的语言显示格式
        if(type==false){ //需要转换为非java版可以使用的秘钥信息
            clientPriKey = UserkeyUtils.getRSAPrivateKeyAsNetFormat(userKeyInfoList.get(0).getSignSecretKey());//非java版提供的私钥
            workBenchPubKey =UserkeyUtils.getRSAPublicKeyAsNetFormat(secretConfig.getWFpubKey());//配置文件中的平台公钥的转换
        }else{
            clientPriKey = userKeyInfoList.get(0).getSignSecretKey();
            workBenchPubKey = secretConfig.getWFpubKey();
        }
        ThrowBizExceptionUtil.checkStrIsBlank(clientPriKey,"客户端私钥获取失败！");
        ThrowBizExceptionUtil.checkStrIsBlank(workBenchPubKey,"平台公钥获取失败！");
        //3. 获得加密秘钥
        symmetricPubkey = userKeyInfoList.get(0).getSymmetricPubkey();
        ThrowBizExceptionUtil.checkStrIsBlank(symmetricPubkey,"参数加密秘钥获取失败！");
        //4. 相关信息返回
        UserKeyInfoResult userKeyInfoResult = new UserKeyInfoResult();
        userKeyInfoResult.setSymmetricPubkey(symmetricPubkey);
        userKeyInfoResult.setSignSecretKey(clientPriKey);
        userKeyInfoResult.setWorkBenchPubkey(workBenchPubKey);
        userKeyInfoResult.setUserIdentityId(userKeyInfoList.get(0).getUserIdentityId());
        userKeyInfoResult.setRouterSafeURL(routerSafeURL);
        return new ObjectRestResponse<UserKeyInfo>().data(userKeyInfoResult);
    }

    /**
     * 根据userID生成可公开的用户身份标识
     *
     * @param userId 用户真实ID
     * @param username 用户登录名
     * @param name 用户名称
     */
    public void generateUserIdentityId(String userId,String username,String name) {
        // 封装实体参数
        UserKeyInfo userKeyInfo = new UserKeyInfo();
        userKeyInfo.setKeyId(UUIDUtils.generateShortUuid());
        userKeyInfo.setUserRealId(userId);
        userKeyInfo.setUsername(username);
        userKeyInfo.setName(name);
        userKeyInfo.setUserIdentityId(UserkeyUtils.generateUserPubkey(userId));
        userKeyInfo.setCrtUserId(BaseContextHandler.getUserID());
        userKeyInfo.setCrtTime(new Date());
        userKeyInfo.setUpdUserId(BaseContextHandler.getUserID());
        userKeyInfo.setUpdTime(new Date());
        //数据加密密钥
        userKeyInfo.setSymmetricPubkey(UserkeyUtils.generateAesKey());
        Map<String, String> paramSecretKey = UserkeyUtils.generateResSecret();
        ThrowBizExceptionUtil.checkObjIsNull(paramSecretKey,"生成秘钥失败！");
        //签名私钥
        userKeyInfo.setSignSecretKey(paramSecretKey.get("priKey"));
        //签名公钥
        userKeyInfo.setSignPubKey(paramSecretKey.get("pubKey"));

        // 新增用户身份标识记录
        mapper.insertSelective(userKeyInfo);
    }

    /**
     *  根据用户真实ID查询用户可公开的身份标识
     *  @param userId 用户真实ID
     *  @return 用户可公开的身份标识
     */
    public String selectUserIdentityIdByUserId(String userId) {
        return mapper.selectUserIdentityIdByUserId(userId);
    }
}