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
    public String callAnRongApi(AnRongApiBean bean);

    /**
     * 
     * 查询用户
     * @author sss
     * @param bean
     * @return
     */
    public AnRongApiBean queryUser(AnRongApiBean bean);
    
    /**
     * 
     * 共享信息
     * @author sss
     * @param bean
     * @return
     */
    public AnRongApiBean sendMess(AnRongApiBean bean);
}
