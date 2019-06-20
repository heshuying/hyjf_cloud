package com.hyjf.data.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "hive", notes = "hive")
    @PostMapping(value = "/hive/updatedata")
    @ResponseBody
    public String updateHiveData(){
        logger.info("--------------金创展厅开始更新hive相关数据！-------");
        return "success";
    }

    @ApiOperation(value = "神策", notes = "神策")
    @PostMapping(value = "/sensors/updatedata")
    @ResponseBody
    public String updateSensorsData(){
        logger.info("--------------金创展厅开始更新神策相关数据！-------");
        return "success";
    }

}
