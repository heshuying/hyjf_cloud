package com.hyjf.common.util;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//@Component
public class ApiSignUtil {
	
	/** 汇盈金服公钥文件地址(请求) **/
//	@Value("${hyjf.req.pub.key}")
	private static String HYJF_REQ_PUB_KEY_PATH = "";
//	public static final String HYJF_REQ_PUB_KEY_PATH = PropUtils.getSystem("hyjf.req.pub.key");

	/** 汇盈金服私钥文件地址(请求) **/
//	@Value("${hyjf.req.pri.key}")
	private static String HYJF_REQ_PRI_KEY_PATH = "";
//	public static final String HYJF_REQ_PRI_KEY_PATH = PropUtils.getSystem("hyjf.req.pri.key");
	
	/** 汇盈金服(请求)密码 **/
//	@Value("${hyjf.req.password}")
	private static String HYJF_REQ_KEY_PASS = "";
//	public static final String HYJF_REQ_KEY_PASS = PropUtils.getSystem("hyjf.req.password");

//	@Value("${hyjf.res.pub.key}")
	private static String HYJF_RES_PUB_KEY_PATH = "";
//	public static final String HYJF_RES_PUB_KEY_PATH = PropUtils.getSystem("hyjf.res.pub.key");
	
	/** 汇盈金服公钥文件地址(返回) ！！！这部分不能对外,对外提供公钥 ！！！**/
//	@Value("${hyjf.res.pri.key}")
	private static String HYJF_RES_PRI_KEY_PATH = "";
//	public static final String HYJF_RES_PRI_KEY_PATH = PropUtils.getSystem("hyjf.res.pri.key");

//	@Value("${hyjf.res.password}")
	private static String HYJF_RES_KEY_PASS = "";
//	public static final String HYJF_RES_KEY_PASS = PropUtils.getSystem("hyjf.res.password");

	/**
	 * RSA方式加签
	 *
	 * @param custId
	 * @param forEncryptionStr
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String encryptByRSA(String... encPramas) {
		System.out.println(HYJF_REQ_PUB_KEY_PATH + "::::::::" + HYJF_REQ_PRI_KEY_PATH + "::::::::" + HYJF_REQ_KEY_PASS + "::::::::" + HYJF_RES_PUB_KEY_PATH + "::::::::" + HYJF_RES_PRI_KEY_PATH + ":::::::::" + HYJF_RES_KEY_PASS);
		// 生成待签名字符串
		StringBuffer buff = new StringBuffer();
		for (String param : encPramas) {
			buff.append(StringUtils.trimToEmpty(param));
		}
		String signStr = buff.toString();
		
		// 生成签名
		String sign = null;
		RSAHelper signer = null;
		try {
			RSAKeyUtil rsaKey = new RSAKeyUtil(new File(HYJF_RES_PRI_KEY_PATH), HYJF_RES_KEY_PASS);
			signer = new RSAHelper(rsaKey.getPrivateKey());
			sign = signer.sign(signStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sign;
	}

	/**
	 * RSA方式验签
	 *
	 * @param forEncryptionStr
	 * @param chkValue
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyByRSA(String instCode, String signText, String dataText) {
		
		boolean b = false;
		if (signText == null || dataText == null) {
			return b;
		}
		
		signText = signText.replaceAll("[\\t\\n\\r]", "");
		try {
			RSAKeyUtil ru = new RSAKeyUtil(new File(HYJF_REQ_PUB_KEY_PATH+instCode+".crt"));
			RSAHelper signHelper = new RSAHelper(ru.getPublicKey());
			b = signHelper.verify(dataText, signText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * RSA方式加签
	 *
	 * @param custId
	 * @param forEncryptionStr
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String encryptByRSAForRequest(String instCode,String... encPramas) {
		
		// 生成待签名字符串
		StringBuffer buff = new StringBuffer();
		for (String param : encPramas) {
			buff.append(StringUtils.trimToEmpty(param));
		}
		String signStr = buff.toString();
		
		// 生成签名
		String sign = null;
		RSAHelper signer = null;
		try {
			RSAKeyUtil rsaKey = new RSAKeyUtil(new File(HYJF_REQ_PRI_KEY_PATH+instCode+".p12"), HYJF_REQ_KEY_PASS);
			signer = new RSAHelper(rsaKey.getPrivateKey());
			sign = signer.sign(signStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sign;
	}

	/**
	 * RSA方式验签
	 *
	 * @param forEncryptionStr
	 * @param chkValue
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyByRSAForRequest(String signText, String dataText) {
		signText = signText.replaceAll("[\\t\\n\\r]", "");
		
		boolean b = false;
		try {
			RSAKeyUtil ru = new RSAKeyUtil(new File(HYJF_RES_PUB_KEY_PATH));
			RSAHelper signHelper = new RSAHelper(ru.getPublicKey());
			b = signHelper.verify(dataText, signText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}


/*	@Value("${hyjf.req.pub.key}")
	public void setHyjfReqPubKeyPath(String hyjfReqPubKeyPath) {
		HYJF_REQ_PUB_KEY_PATH = hyjfReqPubKeyPath;
	}
	@Value("${hyjf.req.pri.key}")
	public void setHyjfReqPriKeyPath(String hyjfReqPriKeyPath) {
		HYJF_REQ_PRI_KEY_PATH = hyjfReqPriKeyPath;
	}
	@Value("${hyjf.req.password}")
	public void setHyjfReqKeyPass(String hyjfReqKeyPass) {
		HYJF_REQ_KEY_PASS = hyjfReqKeyPass;
	}
	@Value("${hyjf.res.pub.key}")
	public void setHyjfResPubKeyPath(String hyjfResPubKeyPath) {
		HYJF_RES_PUB_KEY_PATH = hyjfResPubKeyPath;
	}
	@Value("${hyjf.res.pri.key}")
	public void setHyjfResPriKeyPath(String hyjfResPriKeyPath) {
		HYJF_RES_PRI_KEY_PATH = hyjfResPriKeyPath;
	}
	@Value("${hyjf.res.password}")
	public void setHyjfResKeyPass(String hyjfResKeyPass) {
		HYJF_RES_KEY_PASS = hyjfResKeyPass;
	}*/
}
