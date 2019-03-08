package com.hyjf.am.trade.controller.admin.productcenter.plancenter.plancapital;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhReInvestDetailResponse;
import com.hyjf.am.resquest.admin.HjhReInvestDetailRequest;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.trade.service.admin.hjhplan.HjhPlanCapitalService;
import com.hyjf.am.vo.trade.hjh.HjhReInvestDetailVO;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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

        if (count > 0 && count != null){
            // 初始化总计数据
            String sumAccount = this.hjhPlanCapitalService.queryReInvestDetailTotal(request);

            Paginator paginator = new Paginator(request.getCurrPage(), count, request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());

            logger.info("复投原始标的入参==>date:" + request.getDate() + ";planNid:" + request.getPlanNid() + "; 分页:" + request.getLimitStart() + "::" + request.getLimitEnd());

            List<HjhReInvestDetailVO> reInvestDetailVOList = this.hjhPlanCapitalService.getReinvestInfo(request);



            if (!CollectionUtils.isEmpty(reInvestDetailVOList)){
                HjhReInvestDetailVO hjhReInvestDetailVO = new HjhReInvestDetailVO();
                response.setResultList(reInvestDetailVOList);
                response.setCount(count);
                hjhReInvestDetailVO.setAccedeAccount(sumAccount);
                response.setSumHjhReInvestDetailVO(hjhReInvestDetailVO);
                response.setRtn(Response.SUCCESS);
            }
        }

        return response;
    }

}
