package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.HjhDebtCreditClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.HjhDebtCreditService;
import com.hyjf.am.response.admin.HjhDebtCreditReponse;
import com.hyjf.am.resquest.admin.HjhDebtCreditListRequest;
import com.hyjf.am.vo.admin.HjhDebtCreditVo;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.util.CustomConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/4
 * @Description:
 */
@Service
public class HjhDebtCreditServiceImpl extends BaseServiceImpl implements HjhDebtCreditService{

    @Autowired
    private HjhDebtCreditClient hjhDebtCreditClient;

    /**
     * 查询汇计划转让列表
     * @param request
     * @return
     */
    @Override
    public HjhDebtCreditReponse queryHjhDebtCreditList(HjhDebtCreditListRequest request) {

        HjhDebtCreditReponse reponse = hjhDebtCreditClient.queryHjhDebtCreditList(request);
        return reponse;
    }

    /**
     * 组装汇计划转让列表显示状态名称
     * @param hjhDebtCreditVoList
     */
    @Override
    public void queryHjhDebtCreditListStatusName(List<HjhDebtCreditVo> hjhDebtCreditVoList) {


        //转让状态
        List<ParamNameVO> hjhDebtCreditStatus = this.getParamNameList(CustomConstants.HJH_DEBT_CREDIT_STATUS);
        //汇计划债转还款状态
        List<ParamNameVO> hjhDebtRepayStatus = this.getParamNameList(CustomConstants.HJH_DEBT_REPAY_STATUS);
        for (HjhDebtCreditVo vo: hjhDebtCreditVoList
             ) {
            String repayStatusName = this.getParamName(vo.getRepayStatus(),hjhDebtRepayStatus);
            String creditStatusName = this.getParamName(vo.getCreditStatus(), hjhDebtCreditStatus);
            vo.setRepayStatusName(repayStatusName);
            vo.setCreditStatusName(creditStatusName);
        }
    }




}
