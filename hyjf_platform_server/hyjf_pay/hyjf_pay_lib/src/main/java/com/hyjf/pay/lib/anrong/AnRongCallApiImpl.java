package com.hyjf.pay.lib.anrong;

import com.hyjf.common.http.HttpDealBank;
import com.hyjf.pay.lib.anrong.bean.AnRongApiBean;
import com.hyjf.pay.lib.anrong.util.AnRongParamConstant;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * 汇盈金服调用安融API接口
 * @author sss
 * @version hyjf 1.0
 * @since hyjf 1.0 2017年10月9日
 * @see 下午1:49:10
 */
@Service
public class AnRongCallApiImpl implements AnRongCallApi{
    private static Logger log = LoggerFactory.getLogger(AnRongCallApiImpl.class);
    @Override
    public String callAnRongApi(AnRongApiBean bean,String url) {
     // 方法名
        log.info("[调用安融API接口开始]");
        log.debug("参数: " + bean == null ? "无" : bean.getAllParams() + "]");
        String result = null;
        try {
            // 发送请求
            String HTTP_URL = url;
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

    public AnRongApiBean queryUser(AnRongApiBean bean) {
        // 消息类型
        bean.set(BankCallConstant.PARAM_TXCODE, AnRongParamConstant.TXCODE_QUERY);
        return bean;
    }

    public AnRongApiBean sendMess(AnRongApiBean bean) {
        // 消息类型
        bean.set(BankCallConstant.PARAM_TXCODE, AnRongParamConstant.TXCODE_SEND_MESS);
        return bean;
    }
    
}
