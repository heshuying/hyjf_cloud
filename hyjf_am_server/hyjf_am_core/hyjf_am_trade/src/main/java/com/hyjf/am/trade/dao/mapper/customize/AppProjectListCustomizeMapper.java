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

import com.hyjf.am.trade.dao.model.customize.AppProjectInvestListCustomize;
import com.hyjf.am.vo.trade.AppProjectListCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanCustomizeVO;

import java.util.List;
import java.util.Map;

public interface AppProjectListCustomizeMapper {

	int countProjectInvestRecordTotal(Map<String,Object> params);

    List<AppProjectInvestListCustomize> selectProjectInvestList(Map<String,Object> params);

    List<HjhPlanCustomizeVO> getIndexHjhExtensionPlanListByLockTime(Map map);

    List<HjhPlanCustomizeVO> getIndexHjhExtensionPlanList(Map map);

    List<AppProjectListCustomizeVO> selectHomeProjectList(Map map);

    List<AppProjectListCustomizeVO> selectHomeRepayProjectList(Map map);
}
