package com.hyjf.cs.market.controller.web.qianle;

import com.hyjf.am.response.trade.DataSearchCustomizeResponse;
import com.hyjf.am.resquest.trade.DataSearchRequest;
import com.hyjf.am.vo.trade.DataSearchCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.market.bean.DataSearchBean;
import com.hyjf.cs.market.service.DataSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @ApiOperation(value = "千乐散标数据查询", notes = "千乐散标数据查询")
    @PostMapping("/querysanlist")
    public  WebResult<List<DataSearchCustomizeVO>> getQianleList(@RequestBody DataSearchBean request){
        DataSearchRequest dataSearchRequest = CommonUtils.convertBean(request, DataSearchRequest.class);
        DataSearchCustomizeResponse response = dataSearchService.findDataList(dataSearchRequest);
        WebResult webResult = new WebResult(response.getResultList());
        Page page = new Page();
        page.setTotal(response.getCount());
        page.setCurrPage(request.getCurrPage());
        page.setPageSize(request.getPageSize());
        webResult.setPage(page);
        return webResult;
    }





}
