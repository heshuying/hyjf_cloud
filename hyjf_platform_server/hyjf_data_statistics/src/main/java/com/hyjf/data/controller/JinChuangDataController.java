package com.hyjf.data.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/hyjf_data_statistics")
public class JinChuangDataController {

    @ApiOperation(value = "测试", notes = "测试")
    @PostMapping(value = "/update")
    @ResponseBody
    public String updateData(){

        return "success";
    }

}
