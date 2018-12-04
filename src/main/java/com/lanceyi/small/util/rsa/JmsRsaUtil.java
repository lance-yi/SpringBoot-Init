package com.lanceyi.small.util.rsa;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by e311 on 2017/4/6.
 */
public class JmsRsaUtil {

    //1024位的证书，加密时最大支持117个字节，解密时为128；
// 2048位的证书，加密时最大支持245个字节，解密时为256。
    /** 指定key的大小 */
    private static int KEYSIZE = 1024;

    /**
     * 生成密钥对
     */
    public static Map<String, String> generateKeyPair() throws Exception {
        /** RSA算法要求有一个可信任的随机数源 */
        SecureRandom sr = new SecureRandom();
        /** 为RSA算法创建一个KeyPairGenerator对象 */
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
        kpg.initialize(KEYSIZE, sr);
        /** 生成密匙对 */
        KeyPair kp = kpg.generateKeyPair();
        /** 得到公钥 */
        Key publicKey = kp.getPublic();
        byte[] publicKeyBytes = publicKey.getEncoded();
        String pub = new String(JmsBase64Util.encodeBase64(publicKeyBytes),
                JmsRsaConfig.CHAR_ENCODING);
        /** 得到私钥 */
        Key privateKey = kp.getPrivate();
        byte[] privateKeyBytes = privateKey.getEncoded();
        String pri = new String(JmsBase64Util.encodeBase64(privateKeyBytes),
                JmsRsaConfig.CHAR_ENCODING);

        Map<String, String> map = new HashMap<String, String>();
        map.put(JmsRsaConfig.publicKey, pub);
        map.put(JmsRsaConfig.privateKey, pri);
        RSAPublicKey rsp = (RSAPublicKey) kp.getPublic();
        BigInteger bint = rsp.getModulus();
        byte[] b = bint.toByteArray();
        byte[] deBase64Value = JmsBase64Util.encodeBase64(b);
        String retValue = new String(deBase64Value);
        map.put("modulus", retValue);
        return map;
    }

    /**
     * 加密方法 source： 源数据
     */
    public static String encrypt(String source, String publicKey)
            throws Exception {
        Key key = getPublicKey(publicKey);
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(JmsRsaConfig.RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] b = source.getBytes();
        /** 执行加密操作 */
        byte[] b1 = cipher.doFinal(b);
        return new String(JmsBase64Util.encodeBase64(b1),
                JmsRsaConfig.CHAR_ENCODING);
    }

    /**
     * 加密方法 source： 源数据
     */
    public static String encrypt(byte[] source, String publicKey)
            throws Exception {
        Key key = getPublicKey(publicKey);
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(JmsRsaConfig.RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        /** 执行加密操作 */
        byte[] b1 = cipher.doFinal(source);
        return new String(JmsBase64Util.encodeBase64(b1),
                JmsRsaConfig.CHAR_ENCODING);
    }

    /**
     * 多包数据加密
     * @param source           加密内容
     * @param publicKey        公钥
     * @return
     * @throws Exception
     */
    public static String packageEncrypt(String source, String publicKey) throws Exception{
        byte[] b = source.getBytes();
        String miStr = "";
        int index = 0;
        int len = 0;
        while (b.length > 117 && index < b.length){
            len = b.length - index;
            len = len > 117?117:len;
            byte[] dest = new byte[len];
            System.arraycopy(b, index, dest, 0, len);
            miStr = miStr + encrypt(dest, publicKey);
            index += len;
        }
        len = b.length - index;
        if (len > 0){
            byte[] dest = new byte[len];
            System.arraycopy(b, index, dest, 0, len);
            miStr = miStr + encrypt(dest, publicKey);
        }
        return miStr;
    }

    /**
     * 解密算法 cryptograph:密文
     */
    public static String decrypt(String cryptograph, String privateKey)
            throws Exception {
        Key key = getPrivateKey(privateKey);
        /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        Cipher cipher = Cipher.getInstance(JmsRsaConfig.RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] b1 = JmsBase64Util.decodeBase64(cryptograph.getBytes());
        /** 执行解密操作 */
        byte[] b = cipher.doFinal(b1);
        return new String(b);
    }

    /**
     * <P>
     * 私钥解密
     * </p>
     */
    public static byte[] decryptByByte(String cryptograph, String privateKey)
            throws Exception {
        Key key = getPrivateKey(privateKey);
        /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        Cipher cipher = Cipher.getInstance(JmsRsaConfig.RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] b1 = JmsBase64Util.decodeBase64(cryptograph.getBytes());
        /** 执行解密操作 */
        byte[] b = cipher.doFinal(b1);
        return b;
    }

    public static String packageDecrypt(String cryptograph, String privateKey) throws Exception{
        String minStr = "";
        List<byte[]> lst= new ArrayList<byte[]>();
        int index = 0;
        int len = 0;
        while (cryptograph.length() > 172 && index < cryptograph.length()){
            len = cryptograph.length() - index;
            len = len > 172?172:len;
            lst.add(decryptByByte(cryptograph.substring(index, index + len), privateKey));
            index += len;
        }
        len = cryptograph.length() - index;
        if (len > 0){
            lst.add(decryptByByte(cryptograph.substring(index, index + len), privateKey));
        }
        byte[] bmin = new byte[(lst.size() - 1) * 117 + lst.get(lst.size() - 1).length];
        for (int i = 0; i < lst.size(); i ++){
            System.arraycopy(lst.get(i), 0, bmin, i * 117, lst.get(i).length);
        }
        return new String(bmin);
    }





    /**
     * 得到公钥
     *
     * @param key
     *            密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
                JmsBase64Util.decodeBase64(key.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 得到私钥
     *
     * @param key
     *            密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
                JmsBase64Util.decodeBase64(key.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static String sign(String content, String privateKey) {
        String charset = JmsRsaConfig.CHAR_ENCODING;
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    JmsBase64Util.decodeBase64(privateKey.getBytes()));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            Signature signature = Signature.getInstance("SHA1WithRSA");

            signature.initSign(priKey);
            signature.update(content.getBytes(charset));

            byte[] signed = signature.sign();

            return new String(JmsBase64Util.encodeBase64(signed));
        } catch (Exception e) {

        }

        return null;
    }

    public static boolean checkSign(String content, String sign, String publicKey)
    {
        try
        {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = JmsBase64Util.decode2(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));


            Signature signature = Signature
                    .getInstance("SHA1WithRSA");

            signature.initVerify(pubKey);
            signature.update( content.getBytes("utf-8") );

            boolean bverify = signature.verify( JmsBase64Util.decode2(sign) );
            return bverify;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }



}
