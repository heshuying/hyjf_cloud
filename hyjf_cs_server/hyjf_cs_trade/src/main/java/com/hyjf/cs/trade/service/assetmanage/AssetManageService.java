package com.hyjf.cs.trade.service.assetmanage;

import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.resquest.trade.AssetManagePlanRequest;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.MyCreditDetailBean;
import com.hyjf.cs.trade.bean.ObligatoryRightAjaxBean;
import com.hyjf.cs.trade.bean.PlanAjaxBean;
import com.hyjf.cs.trade.bean.RepayPlanInfoBean;
import com.hyjf.cs.trade.service.BaseTradeService;
import com.hyjf.cs.trade.vo.WebGetRepayMentRequestVO;

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

    /**
     * 获取用户还款计划数据
     * @param request
     * @return
     */
    RepayPlanInfoBean getRepayPlanInfo(WebGetRepayMentRequestVO request);

    /**
     * 获取用户散标转让记录详情
     * @param creditNid
     * @return
     */
    MyCreditDetailBean getMyCreditAssignDetail(String creditNid);

    /**
     * 获取我加入的计划详情信息
     * @author zhangyk
     * @date 2018/8/18 16:06
     */
    WebResult getMyPlanInfoDetail(AssetManagePlanRequest request, Integer userId);
}
