package com.hyjf.pay.service;

import com.hyjf.pay.lib.anrong.bean.AnRongBean;

/**
 * 
 * 发送日志记录操作
 * @author sss
 * @version hyjf 1.0
 * @since hyjf 1.0 2017年10月10日
 * @see 下午2:08:57
 */
public interface AnRongCallService {

	/**
	 * 
	 * 插入发送日志
	 * @author sss
	 * @param bean
	 * @return
	 */
	public Long insertAnRongSendLog(AnRongBean bean);

	/**
	 * 
	 * 更新操作日志
	 * @author sss
	 * @param id
	 * @param result
	 * @return
	 */
	public boolean updateAnRongSendLog(Long id, String result);

}
