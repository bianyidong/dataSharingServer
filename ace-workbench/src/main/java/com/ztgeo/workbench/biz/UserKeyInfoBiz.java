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
     *@描述  声测策略 客户端秘钥对rsa生成，身份库中读取，平台秘钥对读取配置文件
     *@参数  [] 是否是java使用 如果是 结果直接返回 如果不是 则转换成xml格式
     *@返回值  com.github.wxiaoqi.security.common.msg.ObjectRestResponse<com.ztgeo.workbench.entity.UserKeyInfoResult>
     *@创建人  Wei
     *@创建时间  2018/9/6
     */
    public ObjectRestResponse<UserKeyInfoResult> updateUserSecretKey(boolean type) {
        String workBenchPubKey = null;//平台公钥
        String clientPriKey = null;//客户端私钥
        String symmetricPubkey=null; //参谋秘钥
        //1.调用获取id的方法获得实体类
        UserKeyInfo userKeyInfo = new UserKeyInfo();
        userKeyInfo.setUserRealId(getUserId());
        UserKeyInfo userKeyInfoNow = mapper.selectOne(userKeyInfo);
        ThrowBizExceptionUtil.checkObjIsNull(userKeyInfoNow,"根据ID未查询到相关用户!");
        ThrowBizExceptionUtil.checkObjIsNull(userKeyInfoNow.getUserIdentityId(),"根据ID未查询到相关用户!");
        //2. 使用工具类生成客户端对称加密的秘钥
        Map<String, String> paramSecretKey = UserkeyUtils.generateResSecret();
        ThrowBizExceptionUtil.checkObjIsNull(paramSecretKey,"生成秘钥失败！");
        //3. 根据传入的生成方式确定返回值
        /*
        * java返回和保存到库中的base64的客户端私钥 和 服务端公钥 主程序解密时应该反编译后生成私钥公钥使用
        * 非java语言返回xml格式字符串，但保存在库中的为base64的串
        *
        * */
        if(type==false){ //需要转换为非java版可以使用的秘钥信息
            clientPriKey = UserkeyUtils.getRSAPrivateKeyAsNetFormat(paramSecretKey.get("priKey"));//非java版提供的私钥
            workBenchPubKey =UserkeyUtils.getRSAPublicKeyAsNetFormat(secretConfig.getWFpubKey());//配置文件中的平台公钥的转换
        }else{
            clientPriKey = paramSecretKey.get("priKey");
            workBenchPubKey = secretConfig.getWFpubKey();
        }
        ThrowBizExceptionUtil.checkStrIsBlank(clientPriKey,"客户端私钥获取失败！");
        ThrowBizExceptionUtil.checkStrIsBlank(workBenchPubKey,"平台公钥获取失败！");
        //4. 调用参数加密秘钥
        symmetricPubkey = UserkeyUtils.generateAesKey();
        ThrowBizExceptionUtil.checkStrIsBlank(symmetricPubkey,"参数加密秘钥获取失败！");
        //5.组装参数
        userKeyInfoNow.setUpdTime(new Date()); //更新时间
        userKeyInfoNow.setSymmetricPubkey(symmetricPubkey);//参数秘钥
        userKeyInfoNow.setSignSecretKey(clientPriKey);//客户私钥
        userKeyInfoNow.setSignPubKey(paramSecretKey.get("pubKey"));//客户公钥
        userKeyInfoNow.setUpdUserId(getUserId());
        //6.更新数据
        mapper.updateByPrimaryKeySelective(userKeyInfoNow);
        //7. 相关信息返回
        UserKeyInfoResult userKeyInfoResult = new UserKeyInfoResult();
        BeanUtils.copyProperties(userKeyInfoNow,userKeyInfoResult);
        userKeyInfoResult.setWorkBenchPubkey(workBenchPubKey);
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