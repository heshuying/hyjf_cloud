/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.batch;

import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.HjhBailConfig;
import com.hyjf.am.trade.service.task.DayMarkLineTotalService;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.calculate.DateUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version DayMarkLineTotalController, v0.1 2018/9/4 16:36
 */
@Api(value = "日、月累计额度定时任务处理")
@RestController
@RequestMapping("/am-trade/day_mark_line_total")
public class DayMarkLineTotalController extends BaseController {

    @Autowired
    DayMarkLineTotalService dayMarkLineTotalService;

    /**
     * 日、月累计额度定时任务处理
     *
     * @return
     */
    @RequestMapping("/update_day_mark_line")
    public boolean updateDayMarkLine() {
        try {
            logger.info("------【日、月累计额度定时任务】处理开始------");

            // 获取当天日期yyyyMMdd
            SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
            String nowDay = yyyyMMdd.format(new Date());
            //获取前一天时间返回时间类型 yyyyMMdd
            String beforeDay = DateUtils.getBeforeDateOfDay();

            // 判断是否当月第一天
            boolean startDay = "01".equals(nowDay.substring(6, 8));
            if (startDay) {
                // 拉取所有保证金配置数据循环
                List<HjhBailConfig> hjhBailConfigList = this.dayMarkLineTotalService.selectAccumulateListAll();
                if (null == hjhBailConfigList || hjhBailConfigList.size() <= 0) {
                    logger.info("【日发标额度累计】未拉取所有保证金配置的数据、未取到数据。");
                    return true;
                }
                for (HjhBailConfig hjhBailConfig : hjhBailConfigList) {
                    // 获取当前机构的日累计额度key值
                    String redisKeyDMA = RedisConstants.DAY_MARK_ACCUMULATE + hjhBailConfig.getInstCode();
                    // 设定当月累加redis为0
                    RedisUtils.set(redisKeyDMA, "0");
                    // 删除前一天redis
                    redisDeal(startDay, nowDay, beforeDay, hjhBailConfig.getInstCode());
                }
            } else {
                // 拉取所有保证金配置数据循环
                List<HjhBailConfig> hjhBailConfigList = this.dayMarkLineTotalService.selectAccumulateList();
                if (null == hjhBailConfigList || hjhBailConfigList.size() <= 0) {
                    logger.info("【日发标额度累计】非月初拉取当前在用保证金配置的数据、未取到数据。");
                    return true;
                }
                for (HjhBailConfig hjhBailConfig : hjhBailConfigList) {
                    // 获取当前机构的日累计额度key值
                    String redisKeyDMA = RedisConstants.DAY_MARK_ACCUMULATE + hjhBailConfig.getInstCode();
                    // 前一天已用额度
                    String redisKeyDU = RedisConstants.DAY_USED + hjhBailConfig.getInstCode() + "_" + beforeDay;
                    // 日推标额度上限
//                    String redisKeyDML = RedisConstants.DAY_MARK_LINE + hjhBailConfig.getInstCode();
                    // 打开累计额度开关的
                    if (hjhBailConfig.getIsAccumulate() == 1) {
                        try {
                            // 累加前日剩余额度计入redis
                            BigDecimal redisValueDMA = BigDecimal.ZERO;
                            if (RedisUtils.exists(redisKeyDMA)) {
                                redisValueDMA = new BigDecimal(RedisUtils.get(redisKeyDMA));
                            }
                            // 日额度上限(redis未记录的话取数据库的值)
                            BigDecimal redisValueDML = hjhBailConfig.getDayMarkLine();
//                            if (RedisUtils.exists(redisKeyDML)) {
//                                redisValueDML = new BigDecimal(RedisUtils.get(redisKeyDML));
//                            }
                            // 日使用额度(没有的话说明前一天没使用过)

                            BigDecimal redisValueDU = BigDecimal.ZERO;
                            if (RedisUtils.exists(redisKeyDU)) {
                                redisValueDU = new BigDecimal(RedisUtils.get(redisKeyDU));
                            }
                            // 日剩余累加+日推标额度-日使用额度(正常的情况这里最小算出为0)
                            redisValueDMA = redisValueDMA.add(redisValueDML.subtract(redisValueDU));
                            if (redisValueDMA.compareTo(BigDecimal.ZERO) > 0) {
                                RedisUtils.set(redisKeyDMA, redisValueDMA.toString());
                            } else {
                                RedisUtils.set(redisKeyDMA, "0");
                            }
                        } catch (Exception e) {
                            logger.error("【日发标额度累计】计算日累计额度出错！");
                        }
                    }
                    // 设定当天已用日额度为0、删除前一天redis
                    redisDeal(startDay, nowDay, beforeDay, hjhBailConfig.getInstCode());
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info("------【日、月累计额度定时任务】处理异常终止------");
            return false;
        }
        logger.info("------【日、月累计额度定时任务】处理结束------");
        return true;
    }

    private void redisDeal(boolean startDay, String nowDay, String beforeDay, String instCode) {
        if (RedisUtils.exists(RedisConstants.DAY_USED + instCode + "_" + beforeDay)) {
            // 删除前一天已用额度
            RedisUtils.del(RedisConstants.DAY_USED + instCode + "_" + beforeDay);
        }
        // 当月第一天设置当月已用额度为0、删除上月累计redis
        if (startDay) {
            // 上月redis存在的话删除
            if (RedisUtils.exists(RedisConstants.MONTH_USED + instCode + "_" + beforeDay.substring(0, 6))) {
                RedisUtils.del(RedisConstants.MONTH_USED + instCode + "_" + beforeDay.substring(0, 6));
            }
        }
    }
}
