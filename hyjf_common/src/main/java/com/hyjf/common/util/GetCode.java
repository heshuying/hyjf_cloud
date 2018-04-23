package com.hyjf.common.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

public class GetCode {

	public static String getRandomCode(int length) {
		String[] sourceCode = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E",
				"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
				"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
				"v", "w", "x", "y", "z" };
		List<String> list = Arrays.asList(sourceCode);
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();
		String result = afterShuffle.substring(0, length);
		return result;
	}
	
	public static String getRandomCodeCoupon(int length) {
		String[] sourceCode = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		List<String> list = Arrays.asList(sourceCode);
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();
		String result = afterShuffle.substring(0, length);
		return result;
	}

	/**
	 * 获取短信验证码（六位随机整数）
	 * @param length
	 * @return
	 */
	public static String getRandomSMSCode(int length) {
		Random random = new Random();
		String result = "";
		for (int i = 0; i < length; i++) {
			result += random.nextInt(10);
		}
		return result;
	}
	
	 /**
     * 获取优惠券类别编号
     *
     * @param couponType 优惠券类型 couponType 1:体验金，2：加息券，3：代金券
     * @return 编号
     */
    public static String getCouponCode(int couponType) {
    	String couponCode = StringUtils.EMPTY;
    	if(couponType == 1 ){
    		// 体验金
    		couponCode = "PT"+GetCode.getRandomCodeCoupon(7);
    	}else if(couponType == 2 ){
    		// 加息券
    		couponCode = "PJ"+GetCode.getRandomCodeCoupon(7);
    	}else if(couponType == 3 ){
    		// 代金券
    		couponCode = "PD"+GetCode.getRandomCodeCoupon(7);
    	}
        return couponCode;
    }
    
    /**
     * 获取优惠券用户编号
     * @param couponType couponType 1:体验金，2：加息券，3：代金券
     * @return
     */
    public static String getCouponUserCode(int couponType){
    	long timestamp = new Date().getTime();
    	String couponUserCode = StringUtils.EMPTY;
    	if(couponType == 1 ){
    		// 体验金
    		couponUserCode = "YT"+timestamp+GetCode.getRandomCodeCoupon(3);
    	}else if(couponType == 2 ){
    		// 加息券
    		couponUserCode = "YJ"+timestamp+GetCode.getRandomCodeCoupon(3);
    	}else if(couponType == 3 ){
    		// 代金券
    		couponUserCode = "YD"+timestamp+GetCode.getRandomCodeCoupon(3);
    	}
    	return couponUserCode;
    }
    
    /**
     * 
     * 生成邀请新用户活动奖品的groupcode，生成规则：时间戳+三位随机数字
     * @author hsy
     * @return
     */
    public static String generatePrizeGroupCode(){
        long timestamp = new Date().getTime();
        return timestamp+""; //+ getRandomCodeCoupon(3);
    }
    
    /**
     * 
     * 生成机构编号
     * @author hsy
     * @param length
     * @return
     */
    public static String generateInstCode(int length){
        Random random = new Random();
        String result = "";
        for (int i = 0; i < length; i++) {
            result += random.nextInt(10);
        }
        return result;
    }
    
    
    public static void main(String[] args) { 
        System.out.println(generateInstCode(6)); 
    }

}
