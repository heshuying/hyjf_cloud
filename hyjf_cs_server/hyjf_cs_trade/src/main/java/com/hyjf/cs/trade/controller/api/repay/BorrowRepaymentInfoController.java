package com.hyjf.cs.trade.controller.api.repay;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.ApiBorrowRepaymentInfoRequest;
import com.hyjf.am.vo.trade.ApiBorrowRepaymentInfoCustomizeVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.trade.bean.ApiBorrowRepaymentInfoRequestBean;
import com.hyjf.cs.trade.bean.ResultApiBean;
import com.hyjf.cs.trade.service.repay.BorrowRepaymentInfoService;
import com.hyjf.cs.trade.service.svrcheck.CommonSvrChkService;
import com.hyjf.cs.trade.util.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @version BorrowRepaymentInfoController, v0.1 2018/8/27 16:33
 * @Author: Zha Daojian
 */
@Api(value = "api端-第三方还款明细查询接口",tags = "api端-第三方还款明细查询接口")
@RestController
@RequestMapping("/hyjf-api/server/repayment")
public class BorrowRepaymentInfoController  extends BaseController {


    @Autowired
    private  BorrowRepaymentInfoService borrowRepaymentInfoService;
    @Autowired
    private CommonSvrChkService commonSvrChkService;

    @PostMapping("/repaymentInfoList.do")
    @ApiParam(required = true, name = "repaymentInfoList", value = "第三方还款明细查询接口")
    @ApiOperation(value = "第三方还款明细查询接口", httpMethod = "POST", notes = "第三方资产状态查询接口")
    public ResultApiBean repaymentInfoList(@RequestBody @Valid ApiBorrowRepaymentInfoRequestBean bean) {
        logger.info(bean.getAccountId() + "第三方还款明细查询接口开始-----------------------------");
        logger.info("第三方请求参数：" + JSONObject.toJSONString(bean));
        // 验证
        // 共通验证
        commonSvrChkService.checkRequired(bean);
        // 验证资产编号
        CheckUtil.check(Validator.isNotNull(bean.getAssetId()), MsgEnum.STATUS_ZC000018);
        // 分页验证
        commonSvrChkService.checkLimit(bean.getLimitStart(), bean.getLimitEnd());
        // 验签
        CheckUtil.check(SignUtil.verifyRequestSign(bean, "/server/repayment/repaymentInfoList"),
                MsgEnum.ERR_SIGN);


        logger.info(bean.getInstCode() + "  ----还款明细查询接口开始");
        // 返回查询结果
        ApiBorrowRepaymentInfoRequest request =  new ApiBorrowRepaymentInfoRequest();
        BeanUtils.copyProperties(bean, request);
        List<ApiBorrowRepaymentInfoCustomizeVO> list =borrowRepaymentInfoService.selectBorrowRepaymentInfoList(request);
        logger.info(bean.getInstCode() + "  ----还款明细查询接口結束");
        return new ResultApiBean<List<ApiBorrowRepaymentInfoCustomizeVO>>(list);
    }

}
