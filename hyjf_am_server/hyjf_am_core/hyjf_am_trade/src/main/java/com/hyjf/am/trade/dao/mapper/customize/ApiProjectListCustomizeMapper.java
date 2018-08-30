/**
 * Description:会员用户开户记录初始化列表查询
 * Copyright: (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 上午11:01:57
 * Modification History:
 * Modified by : 
 * */

package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.InvestListCustomize;

import java.util.List;
import java.util.Map;

public interface ApiProjectListCustomizeMapper {
    /**
	 * 获取投资详细信息
	 * @param {instCode：机构编号（必填）,startTime:开始时间（必填），endTime:结束时间（必填），account：电子账号（选填），borrowNid：标的编号}
	 * @return
	 */
	List<InvestListCustomize> searchInvestListNew(Map<String, Object> params);

}
