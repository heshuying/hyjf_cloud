package com.hyjf.cs.trade.controller.wechat.user.myAsset;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.assetmanage.QueryMyProjectVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.WechatMyProjectService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author pangchengchao
 * @version WechatMyAssetController, v0.1 2018/7/24 12:02
 */

@Api(value = "wechat端用户资产管理接口",description = "wechat端用户资产管理接口")
@Controller
@RequestMapping("/hyjf-wechat/wx/myproject")
public class WechatMyProjectController extends BaseTradeController {
    //当前持有项目列表标示
    public static final String CURRENTHOLD_TYPE = "currentHold";
    //已回款项目列表标示
    public static final String REPAYMENT_TYPE = "repayment";
    //债转项目列表标示
    public static final String CREDITRECORD_TYPE = "creditRecord";
    //当前持有计划列表标示
    public static final String HOLD_PLAN_TYPE = "holdPlan";
    //已回款计划列表标示
    public static final String REPAYMENT_PLAN_TYPE = "repayMentPlan";

    @Autowired
    private WechatMyProjectService myProjectService;
    /**
     * 微信端获取首页散标详情
     * @author zhangyk
     * @date 2018/7/2 16:27
     */
    @ApiOperation(value = "微信端:获取我的散标信息", notes = "微信端:获取我的散标信息")
    @GetMapping(value = "/queryScatteredProject.do", produces = "application/json; charset=utf-8")
    public WeChatResult<QueryMyProjectVO> queryScatteredProject( HttpServletRequest request,
                                                                 @RequestHeader(value = "token", required = false) String token) {

        WeChatResult weChatResult = new WeChatResult();
        String type = request.getParameter("type");
        WebViewUserVO webViewUserVO = RedisUtils.getObj(RedisConstants.USER_TOKEN_REDIS + token, WebViewUserVO.class);
        if (Strings.isNullOrEmpty(type)) {
            return new WeChatResult(MsgEnum.ERR_PARAM_NUM);
        }

        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");

        int currentPage = Strings.isNullOrEmpty(currentPageStr) ? 1 : Integer.valueOf(currentPageStr);
        int pageSize = Strings.isNullOrEmpty(currentPageStr) ? 10 : Integer.valueOf(pageSizeStr);

        Integer userId =webViewUserVO.getUserId();

        QueryMyProjectVO vo = new QueryMyProjectVO();

        switch (type) {
            case CURRENTHOLD_TYPE:
                myProjectService.selectCurrentHoldObligatoryRightList(userId, currentPage, pageSize, vo);
                break;

            case REPAYMENT_TYPE:
                myProjectService.selectRepaymentList(userId, currentPage, pageSize, vo);
                break;

            case CREDITRECORD_TYPE:
                myProjectService.selectCreditRecordList(userId, currentPage, pageSize, vo);
                break;
            default:
                throw new IllegalArgumentException("not support type=" + type);
        }

        AccountVO account = myProjectService.getAccountByUserId(userId);
        Preconditions.checkArgument(account != null, "userId=【" + userId + "】没有账户信息！");
        vo.setAwait(account.getBankAwait() == null ? "0.00" : CommonUtils.formatAmount(account.getBankAwait()));

        weChatResult.setData(vo);

        return weChatResult;
    }

    /**
     * 计划列表
     * @param request
     * @return
     */
    @ApiOperation(value = "微信端:获取我的计划信息", notes = "微信端:获取我的计划信息")
    @GetMapping(value = "/queryPlanedProject", produces = "application/json; charset=utf-8")
    public WeChatResult queryPlanedProject(HttpServletRequest request,@RequestHeader(value = "token", required = false) String token) {
        WeChatResult weChatResult = new WeChatResult();
        WebViewUserVO webViewUserVO = RedisUtils.getObj(RedisConstants.USER_TOKEN_REDIS + token, WebViewUserVO.class);
        String type = request.getParameter("type");

        if (Strings.isNullOrEmpty(type)) {
            return new WeChatResult(MsgEnum.ERR_PARAM_NUM);
        }

        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");

        int currentPage = Strings.isNullOrEmpty(currentPageStr) ? 1 : Integer.valueOf(currentPageStr);
        int pageSize = Strings.isNullOrEmpty(currentPageStr) ? 10 : Integer.valueOf(pageSizeStr);

        QueryMyProjectVO vo = new QueryMyProjectVO();

        Integer userId =webViewUserVO.getUserId();
        switch (type) {
            case HOLD_PLAN_TYPE:
                myProjectService.selectCurrentHoldPlanList(userId, currentPage, pageSize, vo);
                break;
            case REPAYMENT_PLAN_TYPE:
                myProjectService.selectRepayMentPlanList(userId, currentPage, pageSize, vo);
                break;
            default:
                throw new IllegalArgumentException("not support type=" + type);
        }

        AccountVO account = myProjectService.getAccountByUserId(userId);
        Preconditions.checkArgument(account != null, "userId=【" + userId + "】没有账户信息！");
        vo.setAwait(account.getPlanAccountWait() == null ? "0.00" : CommonUtils.formatAmount(account.getPlanAccountWait()));

        weChatResult.setData(vo);
        return weChatResult;
    }
}
