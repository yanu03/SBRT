package kr.tracom.util;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Base64Utils;

import java.util.Base64;;

/**
 * AES 암호화 클래스 인코딩 : MS949 or EUC-KR
 *
 */
public class SsoCrypto {

	private static String key = null;
	private static String iv = null;
	//private static String ip = null;
	
	
	public static String getKey() {
		return key;
	}

	public static void setKey(String key) {
		SsoCrypto.key = key;
	}

	public static String getIv() {
		return iv;
	}

	public static void setIv(String iv) {
		SsoCrypto.iv = iv;
	}
	
	/*public static String getIp() {
		return ip;
	}

	public static void setIp(String ip) {
		SsoCrypto.ip = ip;
	}*/

	public static void setCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie(Constants.SsoCookieName, SsoCrypto.getKey() + Constants.Separator + SsoCrypto.getIv()  /*+ Constants.Separator +  CommonUtil.getIpAddress(request)*/);
		cookie.setComment(Constants.SsoCookieName);
		cookie.setPath("/");
		cookie.setMaxAge(60*60);
		response.addCookie(cookie);
	}
	
	public static void getCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if(Constants.SsoCookieName.equals(cookie.getName())) {
				String[] cookieVal = cookie.getValue().split(Constants.Separator);
				SsoCrypto.setKey(cookieVal[0]);
				SsoCrypto.setIv(cookieVal[1]);
				//SsoCrypto.setIp(cookieVal[2]);
				cookie.setMaxAge(0);
				break;
			}
		}
		
	}
	
	public static void init() {
		initKey();
		initIV();	
	}

	/**
	 * KEY init 함수
	 * 
	 */
	public static String initKey() {
		KeyGenerator generator;
		try {
			generator = KeyGenerator.getInstance("AES");
			SecureRandom random = new SecureRandom();
			generator.init(128, random);
			SecretKey secureKey = generator.generateKey();

			key = Base64.getEncoder().encodeToString(secureKey.getEncoded());
			return key;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * IV init 함수
	 * 
	 */
	public static String initIV() {
		byte[] IV = new byte[16];
		new SecureRandom().nextBytes(IV);
		iv = Base64.getEncoder().encodeToString(IV).substring(0, 16);
		//iv = key.substring(0, 16);
		return iv;
	}

	public static String encrypt(String strToEncrypt, String salt) {
		try {
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

			/*PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), salt.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");*/
			
			/*int len = iv.getBytes().length;
			if(len>key.getBytes().length) {
				len = key.toCharArray().length;
			}*/
			//System.arraycopy( iv.getBytes(), 0, key.getBytes(), 0, len);
			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			int aaa = cipher.getBlockSize();
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			return Base64Utils.encodeToUrlSafeString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public static String decrypt(String strToDecrypt, String salt) {
		try {

			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

			/*SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(key.toCharArray(), salt.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);*/
			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
			System.out.println("decrypt strToDecrypt = " + strToDecrypt + " ,iv = " + iv);
			return new String(cipher.doFinal(Base64Utils.decodeFromUrlSafeString(strToDecrypt)));
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}
}