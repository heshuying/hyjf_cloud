package com.hyjf.pay.lib.anrong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.hyjf.common.http.HttpDealBank;
import com.hyjf.pay.lib.anrong.bean.AnRongApiBean;
import com.hyjf.pay.lib.anrong.util.AnRongParamConstant;
import com.hyjf.pay.lib.bank.util.BankCallConstant;

/**
 * 
 * 汇盈金服调用安融API接口
 * @author sss
 * @version hyjf 1.0
 * @since hyjf 1.0 2017年10月9日
 * @see 下午1:49:10
 */
public class AnRongCallApiImpl implements AnRongCallApi{
    private static Logger log = LoggerFactory.getLogger(AnRongCallApiImpl.class);
	@Value("${hyjf.anrong.req.queryUrl}")
	private static String queryUrl;
	@Value("${hyjf.anrong.req.sendUrl}")
	private static String sendUrl;
    public AnRongCallApiImpl() {
    }
    
    @Override
    public String callAnRongApi(AnRongApiBean bean) {
     // 方法名
        log.info("[调用安融API接口开始]");
        log.debug("参数: " + bean == null ? "无" : bean.getAllParams() + "]");
        String result = null;
        try {
            // 发送请求
            String HTTP_URL = "";
            // 获得接口URL
            if(AnRongParamConstant.TXCODE_QUERY.equals(bean.get(BankCallConstant.PARAM_TXCODE))){
                HTTP_URL = queryUrl;
            }else if (AnRongParamConstant.TXCODE_SEND_MESS.equals(bean.get(BankCallConstant.PARAM_TXCODE))){
                HTTP_URL = sendUrl;
            }
            // 清除参数
            bean.getAllParams().remove(BankCallConstant.PARAM_TXCODE);
            result = HttpDealBank.anrongPost(HTTP_URL, bean.getAllParams());
            log.debug("[返回结果:" + result + "]");
        } catch (Exception e) {
           log.error(String.valueOf(e));
        }
        log.debug("[调用安融API接口结束]");
        return result;
    }

    @Override
    public AnRongApiBean queryUser(AnRongApiBean bean) {
        // 消息类型
        bean.set(BankCallConstant.PARAM_TXCODE, AnRongParamConstant.TXCODE_QUERY);
        return bean;
    }

    @Override
    public AnRongApiBean sendMess(AnRongApiBean bean) {
        // 消息类型
        bean.set(BankCallConstant.PARAM_TXCODE, AnRongParamConstant.TXCODE_SEND_MESS);
        return bean;
    }
    
}
