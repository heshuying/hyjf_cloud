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
 * 兑吧订单表
 * </p>
 *
 * @author auto
 * @since 2019-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HtDuibaOrders implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 兑吧订单号
     */
    private String duibaOrderId;

    /**
     * 汇盈订单号
     */
    private String hyOrderId;

    /**
     * 订单兑换人用户名
     */
    private String userName;

    /**
     * 姓名
     */
    private String trueName;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 兑换内容
     */
    private String exchangeContent;

    /**
     * 商品类型
     */
    private String productType;

    /**
     * 售价
     */
    private BigDecimal sellingPrice;

    /**
     * 划线价
     */
    private BigDecimal markingPrice;

    /**
     * 成本
     */
    private BigDecimal cost;

    /**
     * 订单状态（0成功，1失败，2处理中）
     */
    private Boolean orderStatus;

    /**
     * 下单时间
     */
    private Integer orderTime;

    /**
     * 完成时间
     */
    private Integer completionTime;

    /**
     * 发货状态（0待发货，1已发货）
     */
    private Boolean deliveryStatus;

    /**
     * 收货信息
     */
    private String receivingInformation;

    /**
     * 虚拟商品充值状态
     */
    private String rechargeState;

    /**
     * 处理状态（0通过，1取消）
     */
    private Boolean processingState;

    /**
     * 商品编码
     */
    private String commodityCode;

    /**
     * 汇率
     */
    private BigDecimal exchangeRate;

    /**
     * 兑吧返回积分（计算售价的基础数据）
     */
    private Integer integralPrice;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 订单有效状态（0有效，1无效）
     */
    private Boolean activationType;

    /**
     * 优惠卷用户表id
     */
    private Integer couponUserId;


}
