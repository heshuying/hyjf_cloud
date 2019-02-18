package com.hyjf.am.trade.controller.admin.productcenter.plancenter.reinvestdebt;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhReInvestDebtResponse;
import com.hyjf.am.resquest.admin.HjhReInvestDebtRequest;
import com.hyjf.am.resquest.admin.Paginator;
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

        if (count > 0){
            Paginator paginator = new Paginator(request.getCurrPage(), count);
            request.setLimitStart(paginator.getOffset());
            if (request.getPageSize() > 0){
                request.setLimitEnd(request.getPageSize());
            }else{
                request.setLimitEnd(paginator.getLimit());
            }
            // 获取数据列表
            List<HjhReInvestDebtVO> responseList = this.hjhReInvestDebtService.getReinvestDebtList(request);

            if (!CollectionUtils.isEmpty(responseList)){
                response.setResultList(responseList);
                response.setCount(count);
                response.setRtn(Response.SUCCESS);
            }
        }

        return response;
    }

}
