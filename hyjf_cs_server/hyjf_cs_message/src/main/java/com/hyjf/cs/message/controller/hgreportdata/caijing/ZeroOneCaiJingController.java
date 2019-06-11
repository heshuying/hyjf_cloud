package com.hyjf.cs.message.controller.hgreportdata.caijing;

import com.hyjf.cs.message.service.hgreportdata.caijing.ZeroOneCaiJingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 零壹财经数据推送
 * @Author: yinhui
 * @Date: 2019/6/3 10:29
 * @Version 1.0
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/zeroOneCaiJingController")
public class ZeroOneCaiJingController {

    @Autowired
    private ZeroOneCaiJingService zeroOneCaiJingService;

    /**
     * 出借记录报送
     */
    @RequestMapping("/investRecordSub")
    public void investRecordSub(){

        // 出借记录报送
//        zeroOneCaiJingService.investRecordSub();
        // 提前还款报送
        zeroOneCaiJingService.advancedRepay("","");
    }

    /**
     * 借款记录报送
     */
    @RequestMapping("/borrowRecordSub")
    public void borrowRecordSub() {
        //借款记录报送
        zeroOneCaiJingService.borrowRecordSub("","");
    }

    /**
     * 历史数据记录报送
     */
    @RequestMapping("/historySub")
    public void historySub() {
        Integer time[] = {4,5,6,7,8,9,10,11,12,1,2,3,4,5,6,7};
        StringBuilder startDate = null;
        StringBuilder endDate = null;
        for (int i =0;i<time.length;i++) {
            startDate = new StringBuilder();
            endDate = new StringBuilder();

            if(i > 8){

                System.out.print( startDate.append("2019-").append(time[i]).append("-01"));
                System.out.print("  , ");
                System.out.println( endDate.append("2019-").append(time[i]).append("-32"));
            }else{
                System.out.print( startDate.append("2018-").append(time[i]).append("-01"));
                System.out.print("  , ");
                System.out.println( endDate.append("2018-").append(time[i]).append("-32"));
            }


        }
    }
}
