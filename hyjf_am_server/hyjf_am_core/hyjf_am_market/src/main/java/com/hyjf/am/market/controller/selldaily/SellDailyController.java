/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.controller.selldaily;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.market.dao.model.auto.SellDaily;
import com.hyjf.am.market.service.SellDailyService;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.market.SellDailyResponse;
import com.hyjf.am.vo.market.SellDailyVO;
import com.hyjf.common.util.CommonUtils;

/**
 * @author yaoyong
 * @version SellDailyController, v0.1 2018/11/16 17:51
 */
@RestController
@RequestMapping("/am-market/sell_daily")
public class SellDailyController {

    @Autowired
    private SellDailyService sellDailyService;

    @RequestMapping("/selectbydatestr/{dateStr}")
    public SellDailyResponse selectByDateStr(@PathVariable String dateStr) {
        SellDailyResponse response = new SellDailyResponse();
        List<SellDaily> list = sellDailyService.selectDailyByDateStr(dateStr);
        if (!CollectionUtils.isEmpty(list)) {
            response.setResultList(CommonUtils.convertBeanList(list, SellDailyVO.class));
        }
        return response;
    }

    @RequestMapping("/selectocsum/{formatDateStr}")
    public SellDailyResponse selectOCSum(@PathVariable String formatDateStr) {
        SellDailyResponse response = new SellDailyResponse();
        SellDaily sellDaily = sellDailyService.selectOCSum(formatDateStr);
        if (sellDaily != null) {
            SellDailyVO sellDailyVO = new SellDailyVO();
            BeanUtils.copyProperties(sellDaily, sellDailyVO);
            response.setResult(sellDailyVO);
        }
        return response;
    }

    @RequestMapping("/select_primary_divisionsum/{formatDateStr}/{drawOrder}")
    public SellDailyResponse selectPrimaryDivisionSum(@PathVariable String formatDateStr, @PathVariable int drawOrder) {
        SellDailyResponse response = new SellDailyResponse();
        SellDaily sellDaily = sellDailyService.selectPrimaryDivisionSum(formatDateStr, drawOrder);
        if (sellDaily != null) {
            SellDailyVO sellDailyVO = new SellDailyVO();
            BeanUtils.copyProperties(sellDaily, sellDailyVO);
            response.setResult(sellDailyVO);
        }
        return response;
    }

    @RequestMapping("/select_allsum/{formatDateStr}")
    public SellDailyResponse selectAllSum(@PathVariable String formatDateStr) {
        SellDailyResponse response = new SellDailyResponse();
        SellDaily sellDaily = sellDailyService.selectAllSum(formatDateStr);
        if (sellDaily != null) {
            SellDailyVO sellDailyVO = new SellDailyVO();
            BeanUtils.copyProperties(sellDaily, sellDailyVO);
            response.setResult(sellDailyVO);
        }
        return response;
    }

    /**
     * 当前日期是否已经生成销售日报
     * @return
     */
	@RequestMapping("/has_generator_data_today")
	public BooleanResponse hasGeneratorDataToday() {
		return new BooleanResponse(sellDailyService.hasGeneratorDataToday());
	}

    /**
     * 批量插入
     */
    @RequestMapping("/batch_insert_sell_daily")
    public void batchInsertSellDaily(@RequestBody List<SellDailyVO> voList) {
        sellDailyService.batchInsertSellDaily(voList);
    }

    @RequestMapping("/calculate_rate")
    public void calculateRate() {
        sellDailyService.calculateRate();
    }
}
