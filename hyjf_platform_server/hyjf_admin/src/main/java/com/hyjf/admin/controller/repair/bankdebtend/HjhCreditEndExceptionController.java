package com.hyjf.admin.controller.repair.bankdebtend;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.exception.HjhCreditEndExceptionService;
import com.hyjf.am.response.admin.HjhDebtCreditReponse;
import com.hyjf.am.resquest.admin.HjhDebtCreditListRequest;
import com.hyjf.am.vo.admin.HjhDebtCreditVo;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 异常中心-汇计划结束债权异常处理
 * @author hesy
 * @version HjhCreditEndExceptionController, v0.1 2018/7/12 16:55
 */
@Api(value = "异常中心-汇计划结束债权异常处理", tags = "异常中心-汇计划结束债权异常处理")
@RestController
@RequestMapping("/hyjf-admin/exception/hjhcreditend")
public class HjhCreditEndExceptionController extends BaseController {
    @Autowired
    HjhCreditEndExceptionService hjhCreditEndExceptionService;

    /**
     * 汇计划结束债权列表
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "汇计划结束债权列表", notes = "汇计划结束债权列表")
    @PostMapping("/getlist")
    public JSONObject getList(@RequestBody HjhDebtCreditListRequest requestBean){
        JSONObject jsonObject = null;
        HjhDebtCreditReponse hjhDebtCreditReponse = hjhCreditEndExceptionService.queryHjhDebtCreditList(requestBean);
        List<HjhDebtCreditVo> hjhDebtCreditVoList = new ArrayList<HjhDebtCreditVo>();
        if (null != hjhDebtCreditReponse) {
            List<HjhDebtCreditVo> listAccountDetail = hjhDebtCreditReponse.getResultList();
            Integer recordCount = hjhDebtCreditReponse.getRecordTotal();
            if (null != listAccountDetail && listAccountDetail.size() > 0) {
                hjhDebtCreditVoList.addAll(listAccountDetail);
            }
            if (!hjhDebtCreditVoList.isEmpty()) {
                hjhCreditEndExceptionService.queryHjhDebtCreditListStatusName(hjhDebtCreditVoList);
                jsonObject = this.success(String.valueOf(recordCount), hjhDebtCreditVoList);
            } else {
                jsonObject = this.fail("暂无符合条件数据");
            }
        }
        return jsonObject;
    }

    /**
     * 汇计划结束债权
     * @param creditNid
     * @return
     */
    @ApiOperation(value = "汇计划结束债权", notes = "汇计划结束债权")
    @GetMapping("/request_debtend/{creditNid}")
    public JSONObject updateDebtEndAction(@PathVariable String creditNid){
        //请求参数校验
        if(StringUtils.isBlank(creditNid)){
            return this.fail("请求参数错误");
        }

        HjhDebtCreditVO credit = hjhCreditEndExceptionService.selectHjhDebtCreditByCreditNid(creditNid);
        BankOpenAccountVO sellerBankOpenAccount = hjhCreditEndExceptionService.getBankOpenAccount(credit.getUserId());
        String sellerUsrcustid = sellerBankOpenAccount.getAccount();//出让用户的江西银行电子账号

        if (credit.getCreditCapitalWait().compareTo(BigDecimal.ZERO) == 0) {
            //获取出让人投标成功的授权号
            String sellerAuthCode = this.hjhCreditEndExceptionService.getSellerAuthCode(credit.getSellOrderId(), credit.getSourceType());
            if (sellerAuthCode == null) {
               return this.fail("未取得出让人出借的授权码。");
            }

            try {
                boolean result = hjhCreditEndExceptionService.requestDebtEnd(credit,sellerUsrcustid,sellerAuthCode);
                if(!result){
                    return this.fail("该债权结束失败。");
                }

                result = hjhCreditEndExceptionService.updateCreditForEnd(credit);
                if(!result){
                    return this.fail("银行结束债权成功，更新债权表失败。");
                }
            } catch (Exception e) {
                logger.error("债转编号[" + creditNid + "]银行结束债权异常。", e);
                return this.fail("结束债权异常");
            }
        }

        return this.success();
    }
}
