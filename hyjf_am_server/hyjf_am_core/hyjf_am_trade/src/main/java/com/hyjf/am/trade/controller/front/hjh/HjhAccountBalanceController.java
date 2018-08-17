/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.hjh;

import com.hyjf.am.response.admin.HjhAccountBalanceResponse;
import com.hyjf.am.trade.service.front.hjh.HjhAccountBalanceService;
import com.hyjf.am.vo.trade.HjhAccountBalanceVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author liubin
 * @version HjhAccountBalanceController, v0.1 2018/8/2 10:42
 */
@Api(value = "汇计划按日对账统计表")
@RestController
@RequestMapping("/am-trade/hjhAccountBalanceController")
public class HjhAccountBalanceController {
    @Autowired
    private HjhAccountBalanceService hjhAccountBalanceService;

    /**
     * 获取该日期的实际债转和复投金额
     * @param date
     * @return
     */
    @GetMapping("/getHjhAccountBalanceForActList/{date}")
    public HjhAccountBalanceResponse getHjhAccountBalanceForActList(@PathVariable(value = "date") Date date){
        HjhAccountBalanceResponse response = new HjhAccountBalanceResponse();
        List<HjhAccountBalanceVO> list = this.hjhAccountBalanceService.getHjhAccountBalanceForActList(date);
        if (!CollectionUtils.isEmpty(list)) {
            response.setResultList(list);
        }
        return response;
    }

}
