/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 * @author: lb
 * @version: 1.0
 * Created at: 2017年10月16日 上午10:23:45
 * Modification History:
 * Modified by : 
 */
	
package com.hyjf.cs.trade.service.svrcheck;

import com.hyjf.cs.trade.bean.BaseBean;

/**
 * @author lb
 */

/**
 * 统一参数校验
 * @author zhangyk
 * @date 2018/8/27 10:46
 */
public interface CommonSvrChkService  {
	void checkRequired(BaseBean bean);
	void checkLimit(Integer limitStart, Integer limitSize);
	void checkRecoverTime(Long recoverTimeStar, Long recoverTimeEnd);
}

	