package com.hyjf.cs.trade.controller.api.repay;

import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.trade.bean.BaseResultBean;
import com.hyjf.cs.trade.bean.RepayRequestBean;
import com.hyjf.cs.trade.bean.RepayResultBean;
import com.hyjf.cs.trade.service.repay.RepayService;
import com.hyjf.cs.trade.service.synbalance.SynBalanceService;
import com.hyjf.cs.trade.util.ErrorCodeConstant;
import com.hyjf.cs.trade.util.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @version RepayController, v0.1 2018/8/27 14:39
 * @Author: Zha Daojian
 */

@Api(value = "api端-第三方资产状态查询接口",tags = "api端-第三方资产状态查询接口")
@RestController
@RequestMapping("/hyjf-api/server/user/repay")
public class RepayController extends BaseController {

    @Autowired
    private SynBalanceService synBalanceService;

    @Autowired
    private RepayService repayService;

    @PostMapping("/getrepayresult")
    @ApiParam(required = true, name = "findDetailById", value = "第三方资产状态查询接口")
    @ApiOperation(value = "第三方资产状态查询接口", httpMethod = "POST", notes = "第三方资产状态查询接口")
    public BaseResultBean getrepayresult(@RequestBody @Valid RepayRequestBean repaybean){
        RepayResultBean resultBean = new RepayResultBean();
        try {
            checkRepayResult(repaybean);
        } catch (Exception e) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000002);
            resultBean.setStatusDesc(e.getMessage());
            return resultBean;
        }
        BatchBorrowRecoverRequest batchBorrowRecoverRequest = new BatchBorrowRecoverRequest();
        // 借款编号 检索条件
        batchBorrowRecoverRequest.setBorrowNid(repaybean.getBorrowNid());
        // 投资人 检索条件
        batchBorrowRecoverRequest.setApiType(1);
        Integer count = this.repayService.countBatchCenter(batchBorrowRecoverRequest);
        if (count != null && count > 0) {
            List<BatchBorrowRecoverVo> recordList = this.repayService.getBatchBorrowRecoverList(batchBorrowRecoverRequest);
            BatchBorrowRecoverVo info = recordList.get(0);
            resultBean.setStatus(BaseResultBean.STATUS_SUCCESS);
            resultBean.setStatusDesc(BaseResultBean.STATUS_DESC_SUCCESS);
            resultBean.setBorrowNid(info.getBorrowNid());
            resultBean.setBatchNo(info.getBatchNo());
            resultBean.setRole(info.getIsRepayOrgFlag());
            resultBean.setUserName(info.getUserName());
            resultBean.setPeriodNow(info.getPeriodNow());
            resultBean.setBorrowPeriod(info.getBorrowPeriod());
            resultBean.setBorrowAccount(info.getBorrowAccount());
            resultBean.setBatchAmount(info.getBatchAmount());
            resultBean.setBatchServiceFee(info.getBatchServiceFee());
            resultBean.setSucAmount(info.getSucAmount());
            resultBean.setFailAmount(info.getFailAmount());
            resultBean.setBatchStatus(info.getStatus());
            return resultBean;
        }
        resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_HK000002);
        resultBean.setStatusDesc("借款标的没有还款记录!");
        return resultBean;
    }

    private BankOpenAccountVO checkRepayResult(RepayRequestBean info) {
        if (StringUtils.isBlank(info.getAccountId()) || StringUtils.isBlank(info.getBorrowNid()) || StringUtils.isBlank(info.getInstCode())) {
            throw new RuntimeException("参数非法,BorrowNid或accountId或instcode不得为空!");
        }
        //验签
        if (SignUtil.verifyRequestSign(info, "userRepayResult")) {
            logger.info("-------------------验签失败！--------------------");
            throw new RuntimeException("验签失败!");

        }
        BankOpenAccountVO bankOpenAccount = synBalanceService.getBankOpenAccount(info.getAccountId());
        if (bankOpenAccount == null) {
            logger.info("-------------------该用户没有在平台开户！--------------------");
            throw new RuntimeException("该用户没有在平台开户!");
        }
        return bankOpenAccount;
    }

}
