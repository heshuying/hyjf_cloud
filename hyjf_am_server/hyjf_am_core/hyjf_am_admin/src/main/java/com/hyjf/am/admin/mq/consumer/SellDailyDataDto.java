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
	private SellDailyVO operationSellDaily;
	/**
	 * app数据
	 */
	private List<SellDailyVO> appSellDailyList;
	/**
	 * 千乐渠道
	 */
	private SellDailyVO qlSellDaily;

	private SellDailyVO creditSellDaily;

    public SellDailyVO getCreditSellDaily() {
        return creditSellDaily;
    }

    public void setCreditSellDaily(SellDailyVO creditSellDaily) {
        this.creditSellDaily = creditSellDaily;
    }

    public List<SellDailyVO> getList() {
        return list;
    }

    public void setList(List<SellDailyVO> list) {
        this.list = list;
    }

    public SellDailyVO getOperationSellDaily() {
        return operationSellDaily;
    }

    public void setOperationSellDaily(SellDailyVO operationSellDaily) {
        this.operationSellDaily = operationSellDaily;
    }

    public List<SellDailyVO> getAppSellDailyList() {
        return appSellDailyList;
    }

    public void setAppSellDailyList(List<SellDailyVO> appSellDailyList) {
        this.appSellDailyList = appSellDailyList;
    }

    public SellDailyVO getQlSellDaily() {
        return qlSellDaily;
    }

    public void setQlSellDaily(SellDailyVO qlSellDaily) {
        this.qlSellDaily = qlSellDaily;
    }
}
