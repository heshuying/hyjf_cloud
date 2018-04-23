package com.hyjf.common.util;

import java.util.Date;

/**
 * 计算时间长度，并进行优化的工具类<br>
 * TimeYouhuaTool model=new TimeYouhuaTool();<br>
 * model.printBeginDate();//输出开始时间<br>
 * model.printYongshi("最左侧的提示说明");//输出距离上次用时<br>
 * model.printZongYongshi();//输出总用时<br>
 * 
 * @author 孙亮
 * @date 2015年9月14日 上午8:17:30
 */
public class TimeCountTool {
	/**
	 * 开始时间
	 */
	private Date beginDate = new Date();
	/**
	 * 结束时间
	 */
	private Date endDate = new Date();
	private Date zongBeginDate = new Date();

	/**
	 * 输出开始时间
	 */
	public String printBeginDate() {
		String result = "开始计算时间:" + GetDate.dateToString(beginDate);
		System.out.println("开始计算时间:" + GetDate.dateToString(beginDate));
		return result;
	}

	/**
	 * 进行计算并输出用时，然后给出新的时间周期
	 * 
	 * @param message
	 * @param model
	 * @return
	 */
	public String printYongshi(String message) {
		String result = "";
		endDate = new Date();
		result = message + "用时:" + GetDate.countTime(beginDate, endDate);
		beginDate = endDate;
		System.out.println(result);
		return result;
	}

	/**
	 * 输出总用时
	 */
	public String printZongYongshi() {
		String result = "";
		endDate = new Date();
		result = "总用时:" + GetDate.countTime(zongBeginDate, endDate);
		System.out.println(result);
		return result;
	}
}
