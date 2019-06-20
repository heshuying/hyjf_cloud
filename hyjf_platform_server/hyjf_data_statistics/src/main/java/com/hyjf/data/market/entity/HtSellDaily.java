package com.hyjf.data.market.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 销售日报数据表
 * </p>
 *
 * @author auto
 * @since 2019-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HtSellDaily implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 统计时间 yyyy.MM.dd
     */
    private String dateStr;

    /**
     * 绘制顺序
     */
    private Integer drawOrder;

    /**
     * 一级部门
     */
    private String primaryDivision;

    /**
     * 二级部门
     */
    private String twoDivision;

    /**
     * 门店数量
     */
    private Integer storeNum;

    /**
     * 本月累计规模业绩
     */
    private BigDecimal investTotalMonth;

    /**
     * 本月累计已还款
     */
    private BigDecimal repaymentTotalMonth;

    /**
     * 上月对应累计规模业绩
     */
    private BigDecimal investTotalPreviousMonth;

    /**
     * 环比增速（本月累计规模业绩/上月对应累计规模业绩 - 1）
     */
    private String investRatioGrowth;

    /**
     * 本月累计提现
     */
    private BigDecimal withdrawTotalMonth;

    /**
     * 提现占比（本月累计提现/本月累计已还款）
     */
    private String withdrawRate;

    /**
     * 本月累计充值
     */
    private BigDecimal rechargeTotalMonth;

    /**
     * 本月累计年化业绩
     */
    private BigDecimal investAnnualTotalMonth;

    /**
     * 上月累计年化业绩
     */
    private BigDecimal investAnnualTotalPreviousMonth;

    /**
     * 环比增速（本月累计年化业绩/上月累计年化业绩-1）
     */
    private String investAnnularRatioGrowth;

    /**
     * 昨日规模业绩
     */
    private BigDecimal investTotalYesterday;

    /**
     * 昨日还款
     */
    private BigDecimal repaymentTotalYesterday;

    /**
     * 昨日年化业绩
     */
    private BigDecimal investAnnualTotalYesterday;

    /**
     * 昨日提现
     */
    private BigDecimal withdrawTotalYesterday;

    /**
     * 昨日充值
     */
    private BigDecimal rechargeTotalYesterday;

    /**
     * 昨日净资金流（充值-提现）
     */
    private BigDecimal netCapitalInflowYesterday;

    /**
     * 当日待还
     */
    private BigDecimal nonRepaymentToday;

    /**
     * 昨日注册数
     */
    private Integer registerTotalYesterday;

    /**
     * 充值≥3000人数（昨日注册）
     */
    private Integer rechargeGt3000UserNum;

    /**
     * 投资≥3000人数（昨日注册）
     */
    private Integer investGt3000UserNum;

    /**
     * 本月累计投资3000以上新客户数
     */
    private Integer investGt3000MonthUserNum;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
