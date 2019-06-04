package com.hyjf.am.trade.controller.hgreportdata.caijing;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.hgreportdata.caijing.ZeroOneCaiJingResponse;
import com.hyjf.am.trade.service.hgreportdata.caijing.ZeroOneCaiJingDataService;
import com.hyjf.am.vo.hgreportdata.caijing.ZeroOneDataVO;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yinhui
 * @Date: 2019/6/3 14:17
 * @Version 1.0
 */
@Api(value = "零壹财经")
@RestController
@RequestMapping("/am-trade/zeroOneCaiJingData")
public class ZeroOneCaiJingDataController {

    @Autowired
    private ZeroOneCaiJingDataService caiJingDataService;

    /**
     * 查询指定日期的出借记录
     * @param searchStartDate
     * @param searchEndDate
     * @return
     */
    @GetMapping("/getBorrowUserInfo/{searchStartDate}/{searchEndDate}")
    public ZeroOneCaiJingResponse queryInvestRecordSub(@PathVariable String searchStartDate,@PathVariable String searchEndDate){
        ZeroOneCaiJingResponse response = new ZeroOneCaiJingResponse();

        if(StringUtils.isBlank(searchStartDate) || StringUtils.isBlank(searchEndDate)){
            response.setRtn(Response.ERROR);
            return response;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("searchStartDate",searchStartDate);
        map.put("searchEndDate",searchEndDate);

        List<ZeroOneDataVO> list = caiJingDataService.queryInvestRecordSub(map);
        if (!CollectionUtils.isEmpty(list)) {
            response.setResultList(list);
        }
        return response;
    }
}
