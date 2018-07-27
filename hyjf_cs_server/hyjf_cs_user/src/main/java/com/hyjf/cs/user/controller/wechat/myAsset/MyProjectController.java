package com.hyjf.cs.user.controller.wechat.myAsset;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.assetmanage.QueryMyProjectVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.controller.wechat.annotation.SignValidate;
import com.hyjf.cs.user.service.myproject.MyProjectService;
import com.hyjf.cs.user.util.RequestUtil;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 我的资产接口，散标、计划......
 *  jijun on 20180727
 */
@Api(value = "wechat端我的资产",description = "wechat端我的资产")
@Controller
@RequestMapping("/hyjf-wechat/myproject")
public class MyProjectController extends BaseUserController {

    @Autowired
    private MyProjectService myProjectService;

    @Autowired
    private RequestUtil requestUtil;
    /**
     * 查询我的资产-散标
     * @param request
     * @return
     */
    @SignValidate
    @RequestMapping(value = "/queryScatteredProject", method = RequestMethod.GET)
    @ResponseBody
    public WeChatResult queryScatteredProject(HttpServletRequest request) {

        WeChatResult result = new WeChatResult();

        String type = request.getParameter("type");

        if (StringUtils.isEmpty(type)) {
            result.buildErrorResponse(MsgEnum.STATUS_CE000001);
            return result;
        }

        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");

        int currentPage = Strings.isNullOrEmpty(currentPageStr) ? 1 : Integer.valueOf(currentPageStr);
        int pageSize = Strings.isNullOrEmpty(currentPageStr) ? 10 : Integer.valueOf(pageSizeStr);

        Integer userId = this.requestUtil.getRequestUserId(request);

        QueryMyProjectVO vo = new QueryMyProjectVO();

        switch (type) {
            case CustomConstants.CURRENTHOLD_TYPE:
                myProjectService.selectCurrentHoldObligatoryRightList(String.valueOf(userId), currentPage, pageSize, vo);
                break;

            case CustomConstants.REPAYMENT_TYPE:
                myProjectService.selectRepaymentList(String.valueOf(userId), currentPage, pageSize, vo);
                break;

            case CustomConstants.CREDITRECORD_TYPE:
                myProjectService.selectCreditRecordList(String.valueOf(userId), currentPage, pageSize, vo);
                break;
            default:
                throw new IllegalArgumentException("not support type=" + type);
        }

        AccountVO account = myProjectService.getAccount(userId);
        Preconditions.checkArgument(account != null, "userId=【" + userId + "】没有账户信息！");
        vo.setAwait(account.getBankAwait() == null ? "0.00" : CommonUtils.formatAmount(account.getBankAwait()));

        result.setData(vo);

        return result;
    }

    /**
     * 计划列表
     * @param request
     * @return
     */
    @SignValidate
    @RequestMapping(value = "/queryPlanedProject", method = RequestMethod.GET)
    @ResponseBody
    public WeChatResult queryPlanedProject(HttpServletRequest request) {
        WeChatResult result = new WeChatResult();
        String type = request.getParameter("type");
        if (StringUtils.isEmpty(type)) {
            result.buildErrorResponse(MsgEnum.STATUS_CE000001);
            return result;
        }

        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");

        int currentPage = Strings.isNullOrEmpty(currentPageStr) ? 1 : Integer.valueOf(currentPageStr);
        int pageSize = Strings.isNullOrEmpty(currentPageStr) ? 10 : Integer.valueOf(pageSizeStr);

        QueryMyProjectVO vo = new QueryMyProjectVO();

        String userId = String.valueOf(requestUtil.getRequestUserId(request));
        Preconditions.checkArgument(!Strings.isNullOrEmpty(userId));

        switch (type) {
            case CustomConstants.HOLD_PLAN_TYPE:
                myProjectService.selectCurrentHoldPlanList(userId, currentPage, pageSize, vo);
                break;
            case CustomConstants.REPAYMENT_PLAN_TYPE:
                myProjectService.selectRepayMentPlanList(userId, currentPage, pageSize, vo);
                break;
            default:
                throw new IllegalArgumentException("not support type=" + type);
        }

        AccountVO account = myProjectService.getAccount(Integer.valueOf(userId));
        Preconditions.checkArgument(account != null, "userId=【" + userId + "】没有账户信息！");
        vo.setAwait(account.getPlanAccountWait() == null ? "0.00" : CommonUtils.formatAmount(account.getPlanAccountWait()));

        result.setData(vo);
        return result;
    }

}
