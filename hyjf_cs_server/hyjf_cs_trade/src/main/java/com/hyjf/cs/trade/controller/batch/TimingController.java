package com.hyjf.cs.trade.controller.batch;

import com.hyjf.cs.trade.service.batch.TimingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Author: yinhui
 * @Date: 2018/12/18 16:19
 * @Version 1.0
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/batch/timing")
public class TimingController {

    @Autowired
    private TimingService timingService;

    /**
     * 不拆分散标自动发标任务
     * @return
     */
    @RequestMapping("/noneSplitBorrow")
    public String noneSplitBorrow() {
        timingService.noneSplitBorrow();
        return "success";
    }

    /**
     * 计划自动发标任务
     * @return
     */
    @RequestMapping("/hjhBorrow")
    public String hjhBorrow() {
        timingService.hjhBorrow();
        return "success";
    }

    /**
     * 计划自动发标任务
     * @return
     */
    @RequestMapping("/splitBorrow")
    public String splitBorrow() {
        timingService.splitBorrow();
        return "success";
    }

    /**
     * 优惠劵过期提醒
     * @return
     */
    @RequestMapping("/couponExpired")
    public String couponExpired() {
        timingService.couponExpired();
        return "success";
    }

    /**
     * 平台数据统计定时任务
     * @return
     */
    @RequestMapping("/dataInfo")
    public String dataInfo() {
        timingService.dataInfo();
        return "success";
    }

    /**
     * 红包账户（aleve和eve文件处理）定时任务
     * @return
     */
    @RequestMapping("/downloadFile")
    public String downloadFile() {
        timingService.downloadRedFile();
        return "success";
    }

    /**
     * 红包账户（aleve和eve文件处理）定时任务
     * @return
     */
    @RequestMapping("/holidays")
    public String holidays() {
        timingService.holidays();
        return "success";
    }

}
