package com.hyjf.admin.controller.screendata;

import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.screendata.BorrowRepayDataService;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.trade.RepayResponse;
import com.hyjf.am.vo.trade.RepaymentPlanVO;
import com.hyjf.common.util.GetDate;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.hyjf.common.util.GetDate.date_sdf_key;

/**
 * 待回款数据统计
 * @author lisheng
 * @version BorrowRepayDataController, v0.1 2019/3/20 13:59
 */
@ApiIgnore
@RestController
@RequestMapping(value = "/batch/borrow/repay")
public class BorrowRepayDataController extends BaseController {
    private static int MAX_COUNT=100;

    @Autowired
    BorrowRepayDataService borrowRepayDataService;
    @GetMapping("/statistics")
    public void dealData() {
        logger.info("【待回款数据统计】开始。。。");
        SimpleDateFormat format = new SimpleDateFormat(date_sdf_key);
        String startDay = format.format(new Date());
        String firstDay = format.format(GetDate.getFirstDayOfMonthDate());
        if (StringUtils.equals(startDay, firstDay)) {
            logger.info("今天是本月第一天：" + startDay);
        }
        IntegerResponse response = borrowRepayDataService.countRepayUserList();
        if (response != null && response.getResultInt() == 0) {
            //查询出本月有回款计划的人（散标和计划）
            RepayResponse repayUser = borrowRepayDataService.findRepayUser(GetDate.getFirstDayOfMonth(), GetDate.getLastDayOfMonth(), 1, MAX_COUNT);
            Integer count = repayUser.getCount();
            Integer page = (count % MAX_COUNT) == 0 ? count / MAX_COUNT : count / MAX_COUNT + 1;
            if (count > 0) {
                for (int i = 1; i <= page; i++) {
                    if (i == 1) {
                        List<RepaymentPlanVO> resultList = repayUser.getResultList();
                        borrowRepayDataService.addRepayUserList(resultList);
                    } else {
                        RepayResponse repayResponse = borrowRepayDataService.findRepayUser(GetDate.getFirstDayOfMonth(), GetDate.getLastDayOfMonth(), i, MAX_COUNT);
                        if (repayResponse != null) {
                            borrowRepayDataService.addRepayUserList(repayResponse.getResultList());
                        }
                    }
                }
            }
            logger.info("【待回款数据统计】结束。。。");
        }
    }


}
