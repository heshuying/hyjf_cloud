package com.hyjf.cs.market.controller.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.StringResponse;
import com.hyjf.cs.market.controller.BaseMarketController;
import com.hyjf.cs.market.service.DailyGeneratorDataService;

import springfox.documentation.annotations.ApiIgnore;

/**
 * @author fuqiang
 * @version DailyGeneratorDataController, v0.1 2018/11/19 15:46
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-market/dailyGeneratorData")
public class DailyGeneratorDataController extends BaseMarketController {

	@Autowired
	private DailyGeneratorDataService dailyGeneratorDataService;

	@RequestMapping("/data")
	public StringResponse dailyGenerator() {
		try {
			dailyGeneratorDataService.generatorSellDaily();
		} catch (Exception e) {
			logger.error("销售日报生成出错......", e);
			return new StringResponse("error");
		}
		return new StringResponse("success");
	}
}
