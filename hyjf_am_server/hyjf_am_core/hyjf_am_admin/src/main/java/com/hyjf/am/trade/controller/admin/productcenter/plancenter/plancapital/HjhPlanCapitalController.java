package com.hyjf.am.trade.controller.admin.productcenter.plancenter.plancapital;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhReInvestDetailResponse;
import com.hyjf.am.resquest.admin.HjhReInvestDetailRequest;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.trade.dao.model.customize.HjhReInvestDetailCustomize;
import com.hyjf.am.trade.service.admin.hjhplan.HjhPlanCapitalService;
import com.hyjf.am.vo.trade.hjh.HjhReInvestDetailVO;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 汇计划-订单退出
 * @Author : huanghui
 */
@Api(value = "汇计划-资金计划")
@RestController
@RequestMapping("/am-trade/hjhPlanCapital")
public class HjhPlanCapitalController {

    @Autowired
    private HjhPlanCapitalService hjhPlanCapitalService;

    private static Logger logger = LoggerFactory.getLogger(HjhPlanCapitalController.class);

    /**
     * 资金计划 - 复投原始标的
     * @param request
     * @return
     */
    @PostMapping(value = "/hjhPlanCapitalReinvestInfo")
    public HjhReInvestDetailResponse hjhPlanCapitalReinvestInfo(@RequestBody HjhReInvestDetailRequest request){
        HjhReInvestDetailResponse response = new HjhReInvestDetailResponse();

        Integer count = this.hjhPlanCapitalService.queryReInvestDetailCount(request);

        if (request.getCurrPage() > 0){
            Paginator paginator = new Paginator(request.getCurrPage(), count);
            request.setLimitStart(paginator.getOffset());
            if (request.getPageSize() > 0){
                request.setLimitEnd(request.getPageSize());
            }else{
                request.setLimitEnd(paginator.getLimit());
            }
        }

        List<HjhReInvestDetailVO> reInvestDetailVOList = this.hjhPlanCapitalService.getReinvestInfo(request);

        if (!CollectionUtils.isEmpty(reInvestDetailVOList)){
            response.setResultList(reInvestDetailVOList);
            response.setCount(count);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

}
