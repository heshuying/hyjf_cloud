package com.hyjf.cs.trade.service;

import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.cs.trade.bean.ObligatoryRightAjaxBean;

/**
 * @author pangchengchao
 * @version AssetManageService, v0.1 2018/6/20 17:31
 */
public interface AssetManageService extends BaseTradeService  {
    AccountVO getAccount(Integer userId);

    /**
     *  获取用户当前持有债权列表分页数据
     * @param form
     * @return
     */
    ObligatoryRightAjaxBean createCurrentHoldObligatoryRightListPage(AssetManageBeanRequest form);
    /**
     * @Description 获取用户已回款债权列表分页数据
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    ObligatoryRightAjaxBean createRepayMentListPage(AssetManageBeanRequest form);
    /**
     * @Description 获取用户转让记录债权页面json数据
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    ObligatoryRightAjaxBean getCreditRecordList(AssetManageBeanRequest form);
}
