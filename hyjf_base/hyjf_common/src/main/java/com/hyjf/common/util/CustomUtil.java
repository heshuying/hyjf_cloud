package com.hyjf.common.util;

import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;

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
