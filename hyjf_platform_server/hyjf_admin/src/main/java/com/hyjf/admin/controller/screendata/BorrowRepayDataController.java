package com.hyjf.admin.controller.screendata;

import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.screendata.BorrowRepayDataService;
import com.hyjf.am.response.trade.RepayResponse;
import com.hyjf.am.vo.trade.RepaymentPlanVO;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Calendar;
import java.util.List;

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
    public void dealData(){
        logger.info("【待回款数据统计】开始。。。");

        //查询出本月有回款计划的人（散标和计划）
        RepayResponse repayUser = borrowRepayDataService.findRepayUser(getFirstDayOfMonth(), getLastDayOfMonth(), 1, MAX_COUNT);
        Integer count = repayUser.getCount();
        Integer page = (count % MAX_COUNT) ==0?count / MAX_COUNT : count / MAX_COUNT + 1;
        if (count > 0) {
            for (int i = 1; i <=page ; i++) {
                if (i ==1) {
                    List<RepaymentPlanVO> resultList = repayUser.getResultList();
                    borrowRepayDataService.addRepayUserList(resultList);
                }
                RepayResponse repayResponse = borrowRepayDataService.findRepayUser(getFirstDayOfMonth(), getLastDayOfMonth(), i, MAX_COUNT);
                if (repayResponse != null) {
                    borrowRepayDataService.addRepayUserList(repayResponse.getResultList());
                }
            }
        }
        logger.info("【待回款数据统计】结束。。。");
    }

    /**
     * 获取当前月最后一天
     * @return
     */
    private Integer getLastDayOfMonth() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return  GetDate.getDayEnd10(ca.getTime());
    }
    /**
     * 获取当前月第一天
     * @return
     */
    private Integer getFirstDayOfMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        //设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH,1);
        return  GetDate.getDayStart11(c.getTime());
    }

}
