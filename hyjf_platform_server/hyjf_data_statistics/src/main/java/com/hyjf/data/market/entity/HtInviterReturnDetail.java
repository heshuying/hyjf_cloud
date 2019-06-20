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
 * 邀请人返现明细
 * </p>
 *
 * @author auto
 * @since 2019-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HtInviterReturnDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账户名
     */
    private String userName;

    /**
     * 姓名
     */
    private String trueName;

    /**
     * 单笔当月产生的业绩
     */
    private BigDecimal returnPerformance;

    /**
     * 获得返现金额
     */
    private BigDecimal returnAmount;

    /**
     * 投资人账户名
     */
    private String tenderName;

    /**
     * 投资订单号
     */
    private String tenderNo;

    /**
     * 单笔投资金额
     */
    private BigDecimal tenderAmount;

    /**
     * 投资期限
     */
    private String term;

    /**
     * 产品类型
     */
    private String productType;

    /**
     * 产品编号
     */
    private String productNo;

    /**
     * 加入时间
     */
    private String joinTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 部门id
     */
    private Integer debtId;

    /**
     * 部门名
     */
    private String debtName;


}
