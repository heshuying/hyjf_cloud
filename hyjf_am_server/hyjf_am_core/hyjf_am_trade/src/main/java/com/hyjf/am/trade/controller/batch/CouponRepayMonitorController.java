/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.batch;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminCouponRepayMonitorCustomizeResponse;
import com.hyjf.am.response.trade.CouponRepayMonitorResponse;
import com.hyjf.am.resquest.admin.CouponRepayRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.CouponRepayMonitor;
import com.hyjf.am.trade.dao.model.customize.admin.AdminCouponRepayMonitorCustomize;
import com.hyjf.am.trade.service.task.CouponRepayMonitorService;
import com.hyjf.am.vo.admin.AdminCouponRepayMonitorCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponRepayMonitorVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version CouponRepayMonitorController, v0.1 2018/6/22 9:34
 */
@RestController
@RequestMapping("/am-trade/couponRepayMonitor")
public class CouponRepayMonitorController extends BaseController {

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


    @PostMapping("/selectCouponRepayMonitorPage")
    public AdminCouponRepayMonitorCustomizeResponse selectCouponRepayMonitorPage(@RequestBody CouponRepayRequest form) {
        AdminCouponRepayMonitorCustomizeResponse response = new AdminCouponRepayMonitorCustomizeResponse();
        Map<String, Object> paraMap = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(form.getTimeStartSrch())){
            paraMap.put("timeStartSrch", form.getTimeStartSrch());
        }
        if(StringUtils.isNotEmpty(form.getTimeEndSrch())){
            paraMap.put("timeEndSrch", form.getTimeEndSrch());
        }
        List<AdminCouponRepayMonitorCustomize> couponRepayMonitorList = couponRepayMonitorService.selectCouponRepayMonitorPage(paraMap);
        if (!CollectionUtils.isEmpty(couponRepayMonitorList)) {
            List<AdminCouponRepayMonitorCustomizeVO> couponRepayMonitorVOList = CommonUtils.convertBeanList(couponRepayMonitorList,AdminCouponRepayMonitorCustomizeVO.class);
            response.setResultList(couponRepayMonitorVOList);
        }
        return response;
    }

    @PostMapping("/couponRepayMonitorCreatePage")
    public AdminCouponRepayMonitorCustomizeResponse couponRepayMonitorCreatePage(@RequestBody CouponRepayRequest form) {
        AdminCouponRepayMonitorCustomizeResponse response = new AdminCouponRepayMonitorCustomizeResponse();
        Integer count = this.couponRepayMonitorService.countRecordTotal(form);
        if(count>0){
            Paginator paginator = new Paginator(form.getPaginatorPage(), count);
            Map<String, Object> paraMap = new HashMap<>();
            paraMap.put("limitStart", paginator.getOffset());
            paraMap.put("limitEnd", paginator.getLimit());
            List<AdminCouponRepayMonitorCustomize> recordList = couponRepayMonitorService.selectCouponRepayMonitorPage(paraMap);
            if (recordList != null) {
                List<AdminCouponRepayMonitorCustomizeVO> avo = CommonUtils.convertBeanList(recordList,AdminCouponRepayMonitorCustomizeVO.class);
                response.setResultList(avo);
                response.setRecordTotal(count);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }

    @PostMapping("/selectInterestSum")
    public AdminCouponRepayMonitorCustomizeResponse selectInterestSum(@RequestBody CouponRepayRequest form) {
        AdminCouponRepayMonitorCustomizeResponse response = new AdminCouponRepayMonitorCustomizeResponse();
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("limitStart", form.getLimitStart());
        paraMap.put("limitEnd", form.getLimitEnd());
        List<AdminCouponRepayMonitorCustomize> couponRepayMonitorList = couponRepayMonitorService.selectInterestSum(paraMap);
        if (!CollectionUtils.isEmpty(couponRepayMonitorList)) {
            List<AdminCouponRepayMonitorCustomizeVO> couponRepayMonitorVOList = CommonUtils.convertBeanList(couponRepayMonitorList,AdminCouponRepayMonitorCustomizeVO.class);
            response.setResultList(couponRepayMonitorVOList);
        }
        return response;
    }

    @PostMapping("/insertCouponRepayMonitor")
    public CouponRepayMonitorResponse insertCouponRepayMonitor(@RequestBody CouponRepayMonitorVO couponRepayMonitorVO ) {
        CouponRepayMonitorResponse response = new CouponRepayMonitorResponse();
        if (couponRepayMonitorVO != null) {
            CouponRepayMonitor couponRepayMonitor = new CouponRepayMonitor();
            BeanUtils.copyProperties(couponRepayMonitorVO,couponRepayMonitor);
            int insertFlag = couponRepayMonitorService.insertCouponRepayMonitor(couponRepayMonitor);
            response.setInsertFlag(insertFlag);
        }
        return response;
    }

    @PostMapping("/updateCouponRepayMonitor")
    public CouponRepayMonitorResponse updateCouponRepayMonitor(@RequestBody CouponRepayMonitorVO couponRepayMonitorVO) {
        CouponRepayMonitorResponse response = new CouponRepayMonitorResponse();
        if (couponRepayMonitorVO != null) {
            CouponRepayMonitor couponRepayMonitor = new CouponRepayMonitor();
            BeanUtils.copyProperties(couponRepayMonitorVO,couponRepayMonitor);
            int updateFlag = couponRepayMonitorService.updateCouponRepayMonitor(couponRepayMonitor);
            response.getUpdateFlag();
        }
        return response;
    }
}
