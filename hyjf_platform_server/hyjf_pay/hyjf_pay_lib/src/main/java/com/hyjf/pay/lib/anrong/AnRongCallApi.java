package com.hyjf.pay.lib.anrong;

import com.hyjf.pay.lib.anrong.bean.AnRongApiBean;

/**
 * 
 * 汇盈金服调用安融API接口
 * @author sss
 * @version hyjf 1.0
 * @since hyjf 1.0 2017年10月9日
 * @see 下午2:05:10
 */
public interface AnRongCallApi {

    /**
     * 
     * 调用安融接口
     * @author sss
     * @param bean
     * @return
     */
    public String callAnRongApi(AnRongApiBean bean,String url);


}
