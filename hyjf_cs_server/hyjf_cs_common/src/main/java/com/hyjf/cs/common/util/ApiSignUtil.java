package com.hyjf.cs.common.util;

import com.hyjf.common.util.RSAHelper;
import com.hyjf.common.util.RSAKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ApiSignUtil {

	private static final Logger logger = LoggerFactory.getLogger(ApiSignUtil.class);
	
	/** 汇盈金服公钥文件地址(请求) **/

	private static String HYJF_REQ_PUB_KEY_PATH;

	/** 汇盈金服私钥文件地址(请求) **/
	private static String HYJF_REQ_PRI_KEY_PATH = "";

	/** 汇盈金服(请求)密码 **/
	private static String HYJF_REQ_KEY_PASS = "";

	private static String HYJF_RES_PUB_KEY_PATH = "";

	/** 汇盈金服公钥文件地址(返回) ！！！这部分不能对外,对外提供公钥 ！！！**/
	private static String HYJF_RES_PRI_KEY_PATH = "";

	private static String HYJF_RES_KEY_PASS = "";

	/**
	 * RSA方式加签
	 *
	 * @return
	 * @throws Exception
	 */
	public static String encryptByRSA(String... encPramas) {
		logger.debug(HYJF_REQ_PUB_KEY_PATH + "::::::::" + HYJF_REQ_PRI_KEY_PATH + "::::::::" + HYJF_REQ_KEY_PASS + "::::::::" + HYJF_RES_PUB_KEY_PATH + "::::::::" + HYJF_RES_PRI_KEY_PATH + ":::::::::" + HYJF_RES_KEY_PASS);
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
			logger.error("验签出错  ",e);
		}
		return b;
	}

	/**
	 * RSA方式加签
	 *
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


	@Value("${hyjf.req.pub.key}")
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
	}
}
