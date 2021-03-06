package com.hyjf.am.trade.controller.admin.productcenter.plancenter.reinvestdebt;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhReInvestDebtResponse;
import com.hyjf.am.resquest.admin.HjhReInvestDebtRequest;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.trade.dao.model.customize.HjhReInvestDebtCustomize;
import com.hyjf.am.trade.service.admin.hjhplan.HjhReInvestDebtService;
import com.hyjf.am.vo.trade.hjh.HjhReInvestDebtVO;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 汇计划-资金计划-复投承接债权
 * @Author : huanghui
 */
@Api(value = "汇计划-资金计划")
@RestController
@RequestMapping("/am-trade/hjhReInvestDebt")
public class HjhReInvestDebtController {

    @Autowired
    private HjhReInvestDebtService hjhReInvestDebtService;

    private static Logger logger = LoggerFactory.getLogger(HjhReInvestDebtController.class);

    /**
     * 资金计划 - 复投承接债权
     * @param request
     * @return
     */
    @RequestMapping(value = "/hjhReInvestDebtList", method = RequestMethod.POST)
    public HjhReInvestDebtResponse hjhReInvestDebtList(@RequestBody HjhReInvestDebtRequest request){
        HjhReInvestDebtResponse response = new HjhReInvestDebtResponse();

        Integer count = this.hjhReInvestDebtService.getReinvestDebtCount(request);


        if (count > 0 && count != null){

            // 取合计值
            HjhReInvestDebtCustomize total = this.hjhReInvestDebtService.queryReInvestDebtTotal(request);
            Paginator paginator = new Paginator(request.getCurrPage(), count, request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
            logger.info(HjhReInvestDebtController.class + ":" + request.toString());

            // 获取数据列表
            List<HjhReInvestDebtVO> responseList = this.hjhReInvestDebtService.getReinvestDebtList(request);


            if (!CollectionUtils.isEmpty(responseList)){
                HjhReInvestDebtVO hjhReInvestDebtVO = new HjhReInvestDebtVO();
                response.setResultList(responseList);
                response.setCount(count);
                hjhReInvestDebtVO.setAssignCapital(total.getAssignCapital());
                hjhReInvestDebtVO.setAssignInterestAdvance(total.getAssignInterestAdvance());
                hjhReInvestDebtVO.setAssignPay(total.getAssignPay());
                response.setSumHjhReInvestDebtVO(hjhReInvestDebtVO);
                response.setRtn(Response.SUCCESS);
            }
        }

        return response;
    }

}
