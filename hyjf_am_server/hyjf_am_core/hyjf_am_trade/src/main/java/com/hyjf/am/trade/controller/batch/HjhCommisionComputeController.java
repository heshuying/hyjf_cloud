package com.hyjf.am.trade.controller.batch;

import com.hyjf.am.response.Response;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.service.CommisionComputeService;
import com.hyjf.am.trade.service.front.batch.BatchHjhBorrowRepayService;
import com.hyjf.am.vo.trade.HjhLockVo;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 汇计划提成计算
 * @author hesy
 * @version HjhCommisionComputeController, v0.1 2018/8/17 11:40
 */
@RestController
@RequestMapping("/am-trade/hjhcommision")
public class HjhCommisionComputeController extends BaseController {
    @Autowired
    CommisionComputeService commisionComputeService;
    @Autowired
    BatchHjhBorrowRepayService batchHjhBorrowRepayService;
    /**
     * 计划提成计算
     */
    @PostMapping("/compute")
    public Response commisionCompute(@RequestBody HjhLockVo hjhLockVo){
        logger.info("汇计划提成计算开始......");

        try {
            logger.info("计算提成，订单号：" + hjhLockVo.getAccedeOrderId());
            commisionComputeService.commisionCompute(null, hjhLockVo);
        } catch (Exception e) {
            logger.error("计算提成异常", e);
            return new Response(Response.ERROR, "计算提成异常");
        }

        logger.info("汇计划提成计算完成.......");
        return new Response();
    }

    /**
     * 待计算提成加入列表
     * @return
     */
    @GetMapping("accedes_waitcompute")
    public Response getAccedesWaitCompute(){
        Response<HjhAccedeVO> responseBean = new Response<>();
        List<HjhAccede> resultList = commisionComputeService.selectHasCommisionAccedeList();
        if (!CollectionUtils.isEmpty(resultList)) {
            List<HjhAccedeVO> couponUserVOList = CommonUtils.convertBeanList(resultList,HjhAccedeVO.class);
            responseBean.setResultList(couponUserVOList);
        }
        return responseBean;
    }


}
