package com.hyjf.lib.anrong;

import com.hyjf.common.http.HttpDealBank;
import com.hyjf.common.util.PropUtils;
import com.hyjf.lib.anrong.bean.AnRongApiBean;
import com.hyjf.lib.anrong.bean.AnRongConstant;
import com.hyjf.lib.anrong.util.AnRongParamConstant;
import com.hyjf.lib.bank.util.BankCallConstant;
import com.hyjf.lib.bank.util.BankCallUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final String THIS_CLASS = AnRongCallApiImpl.class.getName();
    
    public AnRongCallApiImpl() {
    }
    
    @Override
    public String callAnRongApi(AnRongApiBean bean) {
     // 方法名
        String methodName = "callAnRongApi";
        log.info("[调用安融API接口开始]");
        log.debug("参数: " + bean == null ? "无" : bean.getAllParams() + "]");
        String result = null;
        try {
            // 发送请求
            String HTTP_URL = "";
            // 获得接口URL
            if(AnRongParamConstant.TXCODE_QUERY.equals(bean.get(BankCallConstant.PARAM_TXCODE))){
                HTTP_URL = PropUtils.getSystem(AnRongConstant.PARM_REQ_QUERY_URL);
            }else if (AnRongParamConstant.TXCODE_SEND_MESS.equals(bean.get(BankCallConstant.PARAM_TXCODE))){
                HTTP_URL = PropUtils.getSystem(AnRongConstant.PARM_REQ_SEND_URL);
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
