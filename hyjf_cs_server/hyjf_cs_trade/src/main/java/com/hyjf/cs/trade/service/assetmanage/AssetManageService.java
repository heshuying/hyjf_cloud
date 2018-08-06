package com.hyjf.cs.trade.service.assetmanage;

import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.cs.trade.bean.ObligatoryRightAjaxBean;
import com.hyjf.cs.trade.bean.PlanAjaxBean;
import com.hyjf.cs.trade.service.BaseTradeService;

/**
 * @author pangchengchao
 * @version AssetManageService, v0.1 2018/6/20 17:31
 */
public interface AssetManageService extends BaseTradeService {
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
     * @Description 获取用户已回款债权列表分页数据
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    ObligatoryRightAjaxBean getCreditRecordList(AssetManageBeanRequest form);

    /**
     * @Description 获取用户当前持有列表分页数据
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    PlanAjaxBean getCurrentHoldPlan(AssetManageBeanRequest form);
    /**
     * @Description 获取用户已回款计划页面json数据
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    PlanAjaxBean getRepayMentPlan(AssetManageBeanRequest form);
}
