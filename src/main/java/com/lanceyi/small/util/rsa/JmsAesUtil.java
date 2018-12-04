package com.lanceyi.small.util.rsa;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class JmsAesUtil {
	/**
	 * 加密
	 * 
	 * @return
	 */
	public static byte[] encrypt(byte[] data, byte[] key) {
;
		if(key.length!=16){
			throw new RuntimeException("Invalid JmsAesUtil key length (must be 16 bytes)");
		}
		try {
			SecretKeySpec secretKey = new SecretKeySpec(key, JmsRsaConfig.ALGORITHM);
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec seckey = new SecretKeySpec(enCodeFormat,JmsRsaConfig.ALGORITHM);
			// 创建密码器
			Cipher cipher = Cipher.getInstance(JmsRsaConfig.AES_ALGORITHM);
			// 初始化
			cipher.init(Cipher.ENCRYPT_MODE, seckey);
			byte[] result = cipher.doFinal(data);
			return result;
		} catch (Exception e){
			throw new RuntimeException("密文解析失败，可能是密文被篡改", e);
		}
	}

	/**
	 * 解密
	 * 
	 * @return
	 */
	public static byte[] decrypt(byte[] data, byte[] key) {

		if(key.length!=16){
			throw new RuntimeException("Invalid JmsRsaConfig.ALGORITHM key length (must be 16 bytes)");
		}
		try {
			SecretKeySpec secretKey = new SecretKeySpec(key, JmsRsaConfig.ALGORITHM);
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, JmsRsaConfig.ALGORITHM);
			// 创建密码器
			Cipher cipher = Cipher.getInstance(JmsRsaConfig.AES_ALGORITHM);
			// 初始化
			cipher.init(Cipher.DECRYPT_MODE, seckey);
			byte[] result = cipher.doFinal(data);
			return result;
		} catch (Exception e){
			throw new RuntimeException("密文解析失败，可能是密文被篡改", e);
		}
	}
	
	public static String encryptToBase64(String data, String key){
		try {
			byte[] valueByte = encrypt(data.getBytes(JmsRsaConfig.CHAR_ENCODING), key.getBytes(JmsRsaConfig.CHAR_ENCODING));
			return new String(JmsBase64Util.encode(valueByte));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("密文解析失败，可能是密文被篡改", e);
		}
		
	}
	
	public static String decryptFromBase64(String data, String key){
		try {
			byte[] originalData = JmsBase64Util.decode(data.getBytes());
			byte[] valueByte = decrypt(originalData, key.getBytes(JmsRsaConfig.CHAR_ENCODING));
			return new String(valueByte, JmsRsaConfig.CHAR_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("密文解析失败，可能是密文被篡改", e);
		}
	}
	
	public static String encryptWithKeyBase64(String data, String key){
		try {
			byte[] valueByte = encrypt(data.getBytes(JmsRsaConfig.CHAR_ENCODING), JmsBase64Util.decode(key.getBytes()));
			return new String(JmsBase64Util.encode(valueByte));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("encrypt fail!", e);
		}
	}
	
	public static String decryptWithKeyBase64(String data, String key){
		try {
			byte[] originalData = JmsBase64Util.decode(data.getBytes());
			byte[] valueByte = decrypt(originalData, JmsBase64Util.decode(key.getBytes()));
			return new String(valueByte, JmsRsaConfig.CHAR_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("密文解析失败，可能是密文被篡改", e);
		}
	}
	
	public static byte[] genarateRandomKey(){
		KeyGenerator keygen = null;
		try {
			keygen = KeyGenerator.getInstance(JmsRsaConfig.AES_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(" genarateRandomKey fail!", e);
		}
		SecureRandom random = new SecureRandom();
		keygen.init(random);
		Key key = keygen.generateKey();
		return key.getEncoded();
	}
	
	public static String genarateRandomKeyWithBase64(){
		return new String(JmsBase64Util.encode(genarateRandomKey()));
	}
	
	
//	public static void main(String[] agrs){
//		String a = "{action:'common',method:'showAgreement', params:{agreementType:22}}";//"{action:'common',method:'getConfig',params:{configName:''}}"
//		String sign = encryptToBase64(a, "15256dddd5652356");
//		System.out.println(sign);
//		System.out.println(decryptFromBase64(sign, "15256dddd5652356"));
//	}
}
