/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: b
 * @version: 1.0
 * Created at: 2015年12月23日 上午10:14:27
 * Modification History:
 * Modified by : 
 */

package com.hyjf.common.security.utils;

import java.io.IOException;
/**
 * @author 郭勇
 */
import java.security.MessageDigest;

public class MD5Utils {

	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) throws IOException {
		// MD5_Test aa = new MD5_Test();
//		String key = "123456788765432112345678";
//		Integer timestamp = 1450830576;
//		StringBuffer all = new StringBuffer();
//		for (int i = 1450830576; i <= 1450840576; i++) {
//			String kkey = key + i;
//			String md5 = MD5Utils.MD5(kkey);
//			System.out.println(md5.substring(0, 24));
//			AppendFile.method1("D:\\key100.java", md5.substring(0, 24)+"\n");
//		}
//		System.out.println("Done");
//
//		String kkey = key + timestamp;
//		System.out.println("" + all);
//		long end1 = System.currentTimeMillis();
		// String md5 = MD5_Test.MD5(kkey);
		// System.out.println(md5.length());
		// System.out.print(md5.substring(0, 24));
		
//		password= MD5Utils.MD5(MD5Utils.MD5(password) + codeSalt);
		System.out.println(MD5Utils.MD5(MD5Utils.MD5("123456") + "iq3Gyf"));
		
	}
}
