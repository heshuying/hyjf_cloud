/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.batch;

import com.hyjf.am.response.trade.CouponRepayMonitorResponse;
import com.hyjf.am.trade.dao.model.auto.CouponRepayMonitor;
import com.hyjf.am.trade.service.task.CouponRepayMonitorService;
import com.hyjf.am.vo.trade.CouponRepayMonitorVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yaoy
 * @version CouponRepayMonitorController, v0.1 2018/6/22 9:34
 */
@RestController
@RequestMapping("/am-trade/batch")
public class CouponRepayMonitorController {

    @Autowired
    private CouponRepayMonitorService couponRepayMonitorService;

    @GetMapping("/selectCouponRepayMonitor/{nowDay}")
    public CouponRepayMonitorResponse selectCouponRepayMonitor(@PathVariable String nowDay) {
        CouponRepayMonitorResponse response = new CouponRepayMonitorResponse();
        List<CouponRepayMonitor> couponRepayMonitorList = couponRepayMonitorService.selectCouponRepayMonitor(nowDay);
        if (!CollectionUtils.isEmpty(couponRepayMonitorList)) {
            List<CouponRepayMonitorVO> couponRepayMonitorVOList = CommonUtils.convertBeanList(couponRepayMonitorList,CouponRepayMonitorVO.class);
            response.setResultList(couponRepayMonitorVOList);
        }
        return response;
    }


    @PostMapping("/insertCouponRepayMonitor")
    public int insertCouponRepayMonitor(@RequestBody CouponRepayMonitorVO couponRepayMonitorVO ) {
        int result = 0;
        if (couponRepayMonitorVO != null) {
            CouponRepayMonitor couponRepayMonitor = new CouponRepayMonitor();
            BeanUtils.copyProperties(couponRepayMonitorVO,couponRepayMonitor);
            result = couponRepayMonitorService.insertCouponRepayMonitor(couponRepayMonitor);
        }
        return result;
    }

    @PostMapping("/updateCouponRepayMonitor")
    public int updateCouponRepayMonitor(@RequestBody CouponRepayMonitorVO couponRepayMonitorVO) {
        int result = 0;
        if (couponRepayMonitorVO != null) {
            CouponRepayMonitor couponRepayMonitor = new CouponRepayMonitor();
            BeanUtils.copyProperties(couponRepayMonitorVO,couponRepayMonitor);
            result = couponRepayMonitorService.updateCouponRepayMonitor(couponRepayMonitor);
        }
        return result;
    }
}
