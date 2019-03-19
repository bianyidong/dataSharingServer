package com.ztgeo.workbench.demo;

import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ztgeo.workbench.utils.UserkeyUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

/**
 * 模拟用户身份鉴权
 */
public class UserAuthDemo {

    public static String originUserId = "870e63c49ddb4c4d8dbf2d1596f340aa"; // 机构用户ID
    public static String originApiId = "cDKE2Wj4"; // api路由表主键ID

    /**
     * 生成UserID(pubkey):用户ID+sha摘要算法+十六字节编码
     * 生成Secret:uuid+sha摘要算法+base64编码
     */
    public static void generateUserIDAndSecret() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("sha");
        // 生成pubkey
        byte[] sha1Digest = digest.digest(originUserId.getBytes("UTF-8"));
        System.out.println(sha1Digest.length);
        System.out.println("生成pubkey:"+Hex.encodeHexString(sha1Digest));
        // 生成secret
        String secretUUID = UUIDUtils.generateShortUuid();
        String secret = Base64.encodeBase64String(digest.digest(secretUUID.getBytes("UTF-8")));
        System.out.println("生成secret:"+secret);
        // 生成apipubid
        System.out.println("生成apipubkey:"+Hex.encodeHexString(digest.digest(originApiId.getBytes("UTF-8"))));
    }

    /**
     * 通用header生成方法
     */
    public static void generateHeader() throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        // 确定header的key
        String header1 = "AuthBody"; // 消息内容
        String header2 = "SignInfo"; // 签名信息
        // 生成摘要(HmacSha256算法)
        String pubkey = "633ccd279e2de321fc8172fb79e824bc391623fd";
        String secret = "YbziAom0d2gwSLcovS7PUrUUS4Q=";
        String url = "localhost";
        String apiPubId = "a9ab6cfc48fc03a103e27e7235a87cf58f1def9c";
        String timestamp = String.valueOf(Instant.now().toEpochMilli());
        String tempStr = pubkey + secret + url + apiPubId + timestamp;
        System.out.println("待加密的字符串:"+tempStr);
        SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
        // 模拟生成header
        System.out.println(header1+":"+Base64.encodeBase64String((pubkey+","+apiPubId+","+timestamp).getBytes("UTF-8")));
        System.out.println(header2+":"+Hex.encodeHexString(mac.doFinal(tempStr.getBytes("UTF-8"))));
        System.out.println("AuthBody的base64解码:"+new String(Base64.decodeBase64(Base64.encodeBase64String((pubkey+","+apiPubId+","+timestamp).getBytes("UTF-8")))));
    }

}
