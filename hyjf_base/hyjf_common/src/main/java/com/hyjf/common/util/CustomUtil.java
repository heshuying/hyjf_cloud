package com.hyjf.common.util;

import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 共通方法文件
 * </p>
 *
 * @author gogtz
 * @version 1.0.0
 */
public class CustomUtil {

    private static final Logger logger = LoggerFactory.getLogger(CustomUtil.class);

    public static final Map<Character,String> signMap = new HashMap<>();
    static{
        signMap.put('0',"K");
        signMap.put('1',"A");
        signMap.put('2',"B");
        signMap.put('3',"C");
        signMap.put('4',"D");
        signMap.put('5',"F");
        signMap.put('6',"G");
        signMap.put('7',"H");
        signMap.put('8',"I");
        signMap.put('9',"J");
    }

    /**
     * 标的号加密规则：前三位替换为HYJF 数字替换为字母 1-A 2-B 3-C 4-D 5-F 6-G 7-H 8-I 9-J 0-K
     * @param nid
     * @return
     */
    public static String nidSign(String nid){
        char cnid[] = nid.toCharArray();

        if(cnid.length < 4){
            return "";
        }
        StringBuilder sbuild = new StringBuilder();
        sbuild.append("HYJF");
        for(int i =3 ;i <cnid.length;i++ ){
            sbuild.append(signMap.get(cnid[i]));
        }
        return sbuild.toString();
    }

    /**
     * 获得客户端真实IP地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        return GetCilentIP.getIpAddr(request);
    }

    /**
     * 格式化金额
     *
     * @param number
     * @return
     */
    public static String formatAmount(String number) {
        String ret = "0.00";
        if (Validator.isNull(number)) {
            return ret;
        }
        if (!NumberUtils.isCreatable(number)) {
            return ret;
        }

        try {
            BigDecimal b = new BigDecimal(number);
            ret = new DecimalFormat("############0.00").format(b.doubleValue());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return ret;
    }

    /**
     * 取得性别称呼
     * @param sex
     * @return
     */
    public static String getSexName(Integer sex) {
        if (1 == sex) {
            return "先生";
        }
        if (2 == sex) {
            return "女士";
        }
        return "";
    }


}
