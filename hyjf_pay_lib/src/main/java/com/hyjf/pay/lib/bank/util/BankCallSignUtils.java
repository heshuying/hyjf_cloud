/**
 * Description:银行存管认证用类
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: GOGTZ-T
 * @version: 1.0
 * Created at: 2015年11月23日 上午11:48:46
 * Modification History:
 * Modified by :
 */
package com.hyjf.pay.lib.bank.util;

import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hyjf.common.util.PropUtils;
import com.hyjf.common.util.RSAHelper;
import com.hyjf.common.util.RSAKeyUtil;
import com.hyjf.pay.lib.chinapnr.util.ChinapnrUtil;

public class BankCallSignUtils implements Serializable {
	private static Logger log = LoggerFactory.getLogger(BankCallSignUtils.class);
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6267945484521034348L;

	/** THIS_CLASS */
	private static final String THIS_CLASS = BankCallSignUtils.class.getName();

	/** 商户客户号 **/
	public static final String BANK_BANKCODE = PropUtils.getSystem(BankCallConstant.BANK_BANKCODE);

	/** 商户公钥文件地址 **/
	public static final String BANK_PUB_KEY_PATH = PropUtils.getSystem(BankCallConstant.BANK_PUB_KEY_PATH);

	/** 商户私钥文件地址 **/
	public static final String BANK_PRI_KEY_PATH = PropUtils.getSystem(BankCallConstant.BANK_PRI_KEY_PATH);

	/** 服务端公钥地址 **/
	public static final String BANK_PRI_SERVER_KEY_PATH = PropUtils.getSystem(BankCallConstant.BANK_PRI_KEY_PATH);

	/** 商户私钥文件地址 **/
	public static final String BANK_PRI_KEY_PASS = PropUtils.getSystem(BankCallConstant.BANK_PRI_KEY_PASS);
	
	static Logger _log = LoggerFactory.getLogger(BankCallSignUtils.class);

	/**
	 * RSA方式加签
	 *
	 * @param custId
	 * @param forEncryptionStr
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String encryptByRSA(TreeMap<String, String> reqMap) throws Exception {

		String methodName = "encryptByRSA";
		// 生成待签名字符串
		String signStr = mergeMap(reqMap);
		log.info("加签处理开始");
		// 生成签名
		String sign = null;
		RSAHelper signer = null;
		try {
			//_log.info("获取签名私钥:" + BANK_PRI_KEY_PATH);
			RSAKeyUtil rsaKey = new RSAKeyUtil(new File(BANK_PRI_KEY_PATH), BANK_PRI_KEY_PASS);
			signer = new RSAHelper(rsaKey.getPrivateKey());
			sign = signer.sign(signStr);
		} catch (Exception e) {
			_log.info("签名校验异常" + e.getMessage());
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
	public static boolean verifyByRSA(String signText, String dataText) throws Exception {
		String methodName = "verifyByRSA";
		log.info("检证处理开始");
		signText = signText.replaceAll("[\\t\\n\\r]", "");
		log.debug("检证内容:" + dataText);
		boolean b = false;
		try {
			RSAKeyUtil ru = new RSAKeyUtil(new File(BANK_PUB_KEY_PATH));
			RSAHelper signHelper = new RSAHelper(ru.getPublicKey());
			b = signHelper.verify(dataText, signText);
		} catch (Exception e) {
			_log.info("签名校验异常" + e.getMessage());
		}
		return b;
	}

	/**
	 * 获取Map的待签名字符串
	 * 
	 * @param map
	 * @return
	 */
	public static String mergeMap(TreeMap<String, String> map) {
		// 字典序排序后生成待签名字符串
		StringBuffer buff = new StringBuffer();
		Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
		Map.Entry<String, String> entry;
		while (iter.hasNext()) {
			entry = iter.next();
			if (!"sign".equals(entry.getKey())) {
				if (entry.getValue() == null) {
					entry.setValue("");
					buff.append("");
				} else {
					buff.append(String.valueOf(entry.getValue()));
				}
			}
		}
		String requestMerged = buff.toString();
		return requestMerged;
	}
}
