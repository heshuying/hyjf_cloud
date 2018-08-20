package com.hyjf.cs.trade.controller.web.tender.hjh;

import com.hyjf.am.vo.trade.HjhLockVo;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.hjh.HjhCommisionComputeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 汇计划提成计算
 * @author hesy
 * @version HjhCommisionComputeController, v0.1 2018/8/17 12:13
 */
@Api(value = "web端-汇计划提成计算",tags = "web端-汇计划提成计算")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/hyjf-web/hjh/commision")
public class HjhCommisionComputeController extends BaseTradeController {
    @Autowired
    HjhCommisionComputeService computeService;

    /**
     * 提成计算
     * @param request
     * @return
     */
    @GetMapping("/compute")
    @ApiOperation(value = "汇计划提成计算", notes = "汇计划提成计算")
    public Boolean commisionCompute(HttpServletRequest request){
        List<HjhAccedeVO> resultList = computeService.getAccedesWaitCompute();
        if(resultList == null){
            return true;
        }

        logger.info("汇计划当前待计算提成总记录数:" + resultList.size());
        try {
            for (HjhAccedeVO accedeVO : resultList){
                logger.info("汇计划当前计算提成记录订单号:" + accedeVO.getAccedeOrderId());
                HjhLockVo lockVo = computeService.getBeanForCompute(accedeVO.getAccedeOrderId());
                if(lockVo != null){
                    computeService.commisionCompute(lockVo);
                }
            }
        } catch (Exception e) {
            logger.error("汇计划提成计算失败", e);
            return false;
        }

        return true;

    }
}
