package com.hyjf.data.controller.jinchuang;

import com.hyjf.data.service.db.DBService;
import com.hyjf.data.service.sensors.SenSorsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther:yangchangwei
 * @Date:2019/6/18
 * @Description:
 */
@Api(value = "金创展厅数据",tags = "金创展厅数据")
@RestController
@RequestMapping("/jinchuang")
public class JinChuangDataController {

    private static final Logger logger = LoggerFactory.getLogger(JinChuangDataController.class);

    @Autowired
    private SenSorsService senSorsService;

    @Autowired
    private DBService dbService;

    @ApiOperation(value = "神策", notes = "神策")
    @GetMapping(value = "/sensors/updatedata")
    @ResponseBody
    public String updateSensorsData(){
        logger.info("--------------金创展厅开始更新神策相关数据！-------");

        //更新（近30天包扣当天）用户转化
        senSorsService.updateMonthUserConvert();
        //更新（近30天包扣当天）用户行为
        senSorsService.updateMonthUserBehavior();
        //更新当月交易规模（包含当天）
        senSorsService.updateMonthTransTotal();
        //更新当月注册用户数量（包含当天）
        senSorsService.updateMonthRegisterNumber();
        //更新总出借笔数
        senSorsService.updateMonthTenderSumNumber();
        //更新散标出借笔数
        senSorsService.updateMonthTenderNumber();
        //更新智投出借笔数
        senSorsService.updateMonthHjhTenderNumber();
        //更新承接笔数
        senSorsService.updateMonthCreditTenderNumber();
        //更新出借金额分布
        senSorsService.updateSixMonthTenderAmountDistributed();
        //更新用户出借次数
        senSorsService.updateSixMonthTenderNumberDistributed();

        return "true";
    }

    @ApiOperation(value = "每日数据更新", notes = "每日数据更新")
    @GetMapping(value = "/day/updatedata")
    @ResponseBody
    public String updateDataforDay(){
        logger.info("--------------金创展厅开始更新每日数据！-------");

        //更新每月用户收益
        dbService.getUserInterestMonth();
        //更新近6个月的性别分布
        dbService.getSixMonthSexDistributed();

        return "true";
    }

}
