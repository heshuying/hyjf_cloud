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

import com.hyjf.am.vo.trade.repay.WebUserRepayProjectListCustomizeVO;

import java.util.List;
import java.util.Map;

public interface WebUserRepayListCustomizeMapper {
	/**  查询借款人借款列表  */
	List<WebUserRepayProjectListCustomizeVO> selectUserRepayProjectList(Map<String, Object> params);
	/**  查询垫付机构借款列表   */
	List<WebUserRepayProjectListCustomizeVO> selectOrgRepayProjectList(Map<String, Object> params);
}
