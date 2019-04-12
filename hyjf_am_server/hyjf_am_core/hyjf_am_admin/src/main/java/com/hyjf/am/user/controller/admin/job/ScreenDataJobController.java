package com.hyjf.am.user.controller.admin.job;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.RepayResponse;
import com.hyjf.am.user.service.admin.screendata.ScreenDataJobService;
import com.hyjf.am.vo.trade.RepaymentPlanVO;
import com.hyjf.common.paginator.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lisheng
 * @version ScreenDataJobController, v0.1 2019/3/20 17:36
 */
@RestController
@RequestMapping("/am-admin/screen/data")
public class ScreenDataJobController {
    @Autowired
    ScreenDataJobService screenDataJobService;

    @GetMapping(value = "/find_repay_user/{startTime}/{endTime}/{currPage}/{pageSize}")
    private RepayResponse findRepayUser(@PathVariable Integer startTime, @PathVariable  Integer endTime,@PathVariable Integer currPage,@PathVariable Integer pageSize) {
        RepayResponse response = new RepayResponse();
        Integer count = screenDataJobService.countRepayUser(startTime, endTime);
        if (count > 0) {
            Paginator paginator = new Paginator(currPage, count,pageSize);
            List<RepaymentPlanVO> repayUser = screenDataJobService.findRepayUser(startTime,endTime,paginator.getOffset(),paginator.getLimit());
            if (!CollectionUtils.isEmpty(repayUser)) {
                response.setRtn(Response.SUCCESS);
                response.setResultList(repayUser);
                response.setCount(count);
            }
        }
        return response;
    }
}
