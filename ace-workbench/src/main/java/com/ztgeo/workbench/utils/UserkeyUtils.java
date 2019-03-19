package com.ztgeo.workbench.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Slf4j
public class UserkeyUtils {

    //算法说明
    private static String algorithm="AES";

    //自定义秘钥
    private static String key = UUID.randomUUID().toString().replaceAll("-","");

    /**
     * 根据用户ID生成用户pubkey，用来标识用户的身份
     *
     * @param userId 用户ID
     * @return userPubkey
     */
    public static String generateUserPubkey(String userId) {
        try {
            if (StringUtils.isBlank(userId)) {
                return null;
            }
            MessageDigest digest = MessageDigest.getInstance("sha");
            byte[] sha1Digest = digest.digest(userId.getBytes("UTF-8"));
            return Hex.encodeHexString(sha1Digest);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *@描述  生成aes加密加密秘钥的key 这里使用uuid进行生成
     *@参数  []
     *@返回值  java.lang.String
     *@创建人  Wei
     *@创建时间  2018/9/5
     */
    public static String generateAesKey(){
        String resultKey = "";
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance(algorithm);
            //初始化密钥
            keyGenerator.init(new SecureRandom(key.getBytes()));
            //生成秘钥
            SecretKey getKey = keyGenerator.generateKey();
            resultKey=new String(Base64.getEncoder().encode(getKey.getEncoded()));
        } catch (NoSuchAlgorithmException e) {
            log.error("生成aes公钥异常！");
            e.printStackTrace();
        }
        return resultKey;
    }


    /**
     *@描述  生成RSA公钥私钥对
     *@参数  []
     *@返回值  java.util.Map<java.lang.String,java.lang.String>
     *@创建人  Wei
     *@创建时间  2018/9/6
     */
    public static Map<String,String> generateResSecret(){
        //1.用于返回数据对象
        HashMap<String, String> map = null;
        //2. 构建公钥私钥对
        KeyPairGenerator rsa = null;
        try {
            rsa = KeyPairGenerator.getInstance("RSA");
            KeyPair keyPair = rsa.generateKeyPair();
            //3. 公钥私钥加入map中(base64之后生成的加密串)
            map = new HashMap<>();
            map.put("priKey",new String(Base64.getEncoder().encode(keyPair.getPrivate().getEncoded())));
            map.put("pubKey",new String(Base64.getEncoder().encode(keyPair.getPublic().getEncoded())));
        } catch (NoSuchAlgorithmException e) {
            log.error("不支持的签名算法！");
            e.printStackTrace();
        }
        return map;
    }

    /**
     *@描述  通过私钥串获取其他语言使用的xml串
     *@参数  [encodedPrivkey]
     *@返回值  java.lang.String
     *@创建人  Wei
     *@创建时间  2018/9/7
     */
    public static String getRSAPrivateKeyAsNetFormat(String encodedPrivkey) {

        try {
            StringBuffer buff = new StringBuffer(1024);

            PKCS8EncodedKeySpec pvkKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(encodedPrivkey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateCrtKey pvkKey = (RSAPrivateCrtKey) keyFactory
                    .generatePrivate(pvkKeySpec);

            buff.append("<RSAKeyValue>");
            buff.append("<Modulus>"
                    + b64encode(removeMSZero(pvkKey.getModulus()
                    .toByteArray())) + "</Modulus>");

            buff.append("<Exponent>"
                    + b64encode(removeMSZero(pvkKey.getPublicExponent()
                    .toByteArray())) + "</Exponent>");

            buff.append("<P>"
                    + b64encode(removeMSZero(pvkKey.getPrimeP().toByteArray()))
                    + "</P>");

            buff.append("<Q>"
                    + b64encode(removeMSZero(pvkKey.getPrimeQ().toByteArray()))
                    + "</Q>");

            buff.append("<DP>"
                    + b64encode(removeMSZero(pvkKey.getPrimeExponentP()
                    .toByteArray())) + "</DP>");

            buff.append("<DQ>"
                    + b64encode(removeMSZero(pvkKey.getPrimeExponentQ()
                    .toByteArray())) + "</DQ>");

            buff.append("<InverseQ>"
                    + b64encode(removeMSZero(pvkKey.getCrtCoefficient()
                    .toByteArray())) + "</InverseQ>");

            buff.append("<D>"
                    + b64encode(removeMSZero(pvkKey.getPrivateExponent()
                    .toByteArray())) + "</D>");
            buff.append("</RSAKeyValue>");

            return buff.toString().replaceAll("[ \t\n\r]", "");
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }

    }


    public static String getRSAPublicKeyAsNetFormat(String pukKeyStr) {
        try {
            StringBuffer buff = new StringBuffer(1024);
            //PKCS8EncodedKeySpec pvkKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(pukKeyStr));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey pukKey = (RSAPublicKey) keyFactory
                    .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(pukKeyStr)));
            buff.append("<RSAKeyValue>");
            buff.append("<Modulus>"
                    + b64encode(removeMSZero(pukKey.getModulus()
                    .toByteArray())) + "</Modulus>");
            buff.append("<Exponent>"
                    + b64encode(removeMSZero(pukKey.getPublicExponent()
                    .toByteArray())) + "</Exponent>");
            buff.append("</RSAKeyValue>");
            return buff.toString().replaceAll("[ \t\n\r]", "");
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }




    /**
     *@描述 base64的处理
     *@参数  [data]
     *@返回值  java.lang.String
     *@创建人  Wei
     *@创建时间  2018/9/7
     */
    private static String b64encode(byte[] data) {

        String b64str = new String(Base64.getEncoder().encode(data));
        return b64str;
    }

    private static byte[] removeMSZero(byte[] data) {
        byte[] data1;
        int len = data.length;
        if (data[0] == 0) {
            data1 = new byte[data.length - 1];
            System.arraycopy(data, 1, data1, 0, len - 1);
        } else
            data1 = data;

        return data1;
    }

}
