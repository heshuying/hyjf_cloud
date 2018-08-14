/**
 * Description:我的投资
 * Copyright: (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 上午11:01:57
 * Modification History:
 * Modified by : 
 * */

package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.AppAlreadyRepayListCustomize;

import java.util.List;
import java.util.Map;

public interface AppUserInvestCustomizeMapper {

    /**
     * 
     * 查询用户投资次数 包含直投类、债转、汇添金
     * @author hsy
     * @param paraMap
     * @return
     */
    public Integer selectUserTenderCount(Map<String, Object> paraMap);



    List<AppAlreadyRepayListCustomize> selectAlreadyRepayList(Map<String, Object> params);
}
