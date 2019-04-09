package com.hyjf.am.admin.mq.consumer;

import java.util.List;

import com.hyjf.am.vo.market.SellDailyVO;

/**
 * @author xiasq
 * @version SellDailyDataDto, v0.1 2019-04-08 14:41
 */
public class SellDailyDataDto {

	/**
	 * 按照一级分部，二级分部分组查询统计数据集合
	 */
	private List<SellDailyVO> list;
	/**
	 * 运营部
	 */
	private SellDailyVO shOCSellDaily;
	/**
	 * app数据
	 */
	private SellDailyVO appSellDaily;
	/**
	 * 千乐渠道
	 */
	private SellDailyVO qlSellDaily;

    public List<SellDailyVO> getList() {
        return list;
    }

    public void setList(List<SellDailyVO> list) {
        this.list = list;
    }

    public SellDailyVO getShOCSellDaily() {
        return shOCSellDaily;
    }

    public void setShOCSellDaily(SellDailyVO shOCSellDaily) {
        this.shOCSellDaily = shOCSellDaily;
    }

    public SellDailyVO getAppSellDaily() {
        return appSellDaily;
    }

    public void setAppSellDaily(SellDailyVO appSellDaily) {
        this.appSellDaily = appSellDaily;
    }

    public SellDailyVO getQlSellDaily() {
        return qlSellDaily;
    }

    public void setQlSellDaily(SellDailyVO qlSellDaily) {
        this.qlSellDaily = qlSellDaily;
    }
}
