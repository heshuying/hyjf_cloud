package com.hyjf.cs.market.controller.web.qianle;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.DataSearchRequest;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.am.vo.trade.DataSearchCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.market.bean.DataSearchBean;
import com.hyjf.cs.market.service.DataSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lisheng
 * @version DataSearchController, v0.1 2018/8/21 9:37
 */
@Api(tags = "web端-千乐数据查询统计")
@RestController
@RequestMapping("/hyjf-web/qianle")
public class DataSearchController {
    @Autowired
    DataSearchService dataSearchService;

    /**
     * 千乐数据查询
     * @return
     */

    @ApiOperation(value = "千乐数据查询", notes = "千乐数据查询")
    @GetMapping("/getQianleList")
    public JSONObject getQianleList(DataSearchBean dataSearchBean){
        JSONObject jsonObject = new JSONObject();
        DataSearchRequest dataSearchRequest = CommonUtils.convertBean(dataSearchBean, DataSearchRequest.class);

        List<DataSearchCustomizeVO> dataList = dataSearchService.findDataList(dataSearchRequest);
        return jsonObject;
    }



}
