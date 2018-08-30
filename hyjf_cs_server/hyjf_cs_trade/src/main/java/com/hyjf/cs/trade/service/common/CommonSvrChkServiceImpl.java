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
	
package com.hyjf.cs.trade.service.common;

import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.calculate.DateUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.bean.BaseBean;
import org.springframework.stereotype.Service;

/**
 * @author lb
 */
@Service
public class CommonSvrChkServiceImpl extends BaseServiceImpl implements CommonSvrChkService{
	
	/**
	 * 共通必须输入验证
	 * @param bean
	 * @author lb
	 */
	@Override
	public void checkRequired(BaseBean bean) {
		CheckUtil.check(Validator.isNotNull(bean.getTimestamp()), MsgEnum.STATUS_CE000001);
		CheckUtil.check(Validator.isNotNull(bean.getInstCode()), MsgEnum.STATUS_CE000001);
		CheckUtil.check(Validator.isNotNull(bean.getChkValue()), MsgEnum.STATUS_CE000001);
	}

	/**
	 * 分页验证
	 * @param limitStart 检索记录开始条数（从0开始）
	 * @param limitSize	检索记录条数
	 * @return
	 */
	@Override
	public void checkLimit(Integer limitStart, Integer limitSize) {
		//非空check
	    //此处为什么要注释掉--查道健20171201
		CheckUtil.check(!(limitStart==null || limitSize==null), MsgEnum.STATUS_CE000001);
		//起始记录数必须大于0
		CheckUtil.check(Validator.isDigit(limitStart+""), MsgEnum.ERR_OBJECT_DIGIT, "起始记录数");
		//检索记录数必须大于0 check
		CheckUtil.check(Validator.isDigit(limitSize+""), MsgEnum.ERR_OBJECT_DIGIT, "检索记录件数");
		//最大记录数check
		CheckUtil.check(limitSize<=BaseBean.PAGE_MAXSIZE, MsgEnum.ERR_PAGE_MAX, BaseBean.PAGE_MAXSIZE);

	}
	
	/**
     * 分页验证
     * @param recoverTimeStar 检索记录开始条数（从0开始）
     * @param recoverTimeEnd 检索记录条数
     * @return
     */
    @Override
    public void checkRecoverTime(Long recoverTimeStar, Long recoverTimeEnd) {
        //非空check
        CheckUtil.check(Validator.isNotNull(recoverTimeStar+""), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isNotNull(recoverTimeEnd+""), MsgEnum.STATUS_CE000001);
        long datas = DateUtils.differentDays(recoverTimeStar,recoverTimeEnd);
        //最大记录数一个星期check
        CheckUtil.check(datas<=7, MsgEnum.ERR_DATA_MAX, BaseBean.PAGE_MAXSIZE);

    }
}

	