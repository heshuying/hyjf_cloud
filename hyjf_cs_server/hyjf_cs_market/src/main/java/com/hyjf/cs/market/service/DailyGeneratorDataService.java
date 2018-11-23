package com.hyjf.cs.market.service;

/**
 * @author fuqiang
 * @version DailyGeneratorDataService, v0.1 2018/11/19 15:48
 */
public interface DailyGeneratorDataService extends BaseMarketService {
    /**
     * 生成销售日报
     */
    void generatorSellDaily();
}
