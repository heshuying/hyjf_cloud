/*
 * Copyright(c) 2012-2016 JD Pharma.Ltd. All Rights Reserved.
 * 
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * O\ = /O
 * ____/`---'\____
 * .' \\| |// `.
 * / \\||| : |||// \
 * / _||||| -:- |||||- \
 * | | \\\ - /// | |
 * | \_| ''\---/'' | |
 * \ .-\__ `-` ___/-. /
 * ___`. .' /--.--\ `. . __
 * ."" '< `.___\_<|>_/___.' >'"".
 * | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * 佛祖保佑 永无BUG
 */

package com.hyjf.common.util;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * decimal数字格式化工具类
 * @author renxingchen
 * @version hyjf 1.0
 * @since hyjf 1.0 2016年6月30日
 * @see 下午1:37:11
 */
public class DecimalFormatUtil {

    private static ThreadLocal<Map<String, DecimalFormat>> tl = new ThreadLocal<Map<String, DecimalFormat>>() {
        @Override
        protected Map<String, DecimalFormat> initialValue() {
            Map<String, DecimalFormat> dfMap = new HashMap<String, DecimalFormat>();
            dfMap.put(CustomConstants.DF_FOR_VIEW_PATTERN, new DecimalFormat(CustomConstants.DF_FOR_VIEW_PATTERN));
            dfMap.put(CustomConstants.DF_FOR_VIEW_PATTERN_COMMA, new DecimalFormat(
                    CustomConstants.DF_FOR_VIEW_PATTERN_COMMA));
            return dfMap;
        }
    };

    public static String format(double d, String pattern) {
        return tl.get().get(pattern).format(d);
    }

    public static void main(String[] args) {
        double a = 2000000.476;
        System.err.println(new DecimalFormat(CustomConstants.DF_FOR_VIEW_PATTERN).format(a));
    }

}
