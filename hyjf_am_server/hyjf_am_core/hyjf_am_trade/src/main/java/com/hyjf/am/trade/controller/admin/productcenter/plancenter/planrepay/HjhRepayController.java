package com.hyjf.am.trade.controller.admin.productcenter.plancenter.planrepay;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.HjhRepayResponse;
import com.hyjf.am.resquest.admin.HjhRepayRequest;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.trade.dao.model.auto.HjhRepay;
import com.hyjf.am.trade.service.admin.hjhplan.HjhRepayService;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 汇计划-订单退出
 * @Author : huanghui
 */
@Api(value = "汇计划")
@RestController
@RequestMapping("/am-trade/hjhRepay")
public class HjhRepayController {

    @Autowired
    private HjhRepayService hjhRepayService;

    private static Logger logger = LoggerFactory.getLogger(HjhRepayController.class);

    /**
     * 计划还款列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/hjhRepayList", method = RequestMethod.POST)
    public HjhRepayResponse hjhRepayList(@RequestBody HjhRepayRequest request){
        HjhRepayResponse response = new HjhRepayResponse();

        // 查询 总条数
        Integer count = this.hjhRepayService.getRepayCount(request);

        if (request.getCurrPage() > 0){
            Paginator paginator = new Paginator(request.getCurrPage(), count);
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }

        List<HjhRepayVO> repayVOList = this.hjhRepayService.selectByExample(request);

        if (!CollectionUtils.isEmpty(repayVOList)){
            response.setResultList(repayVOList);
            response.setCount(count);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
}
