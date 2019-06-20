package com.hyjf.data.market.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 优惠券发放失败列表
 * </p>
 *
 * @author auto
 * @since 2019-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HtCouponSendFail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 活动id
     */
    private Integer activityId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 券编号
     */
    private String couponCode;

    /**
     * 失败原因
     */
    private String failDesc;

    /**
     * 发放类型
     */
    private String sendFlag;

    /**
     * 补发状态 0-未补发  1-已补发
     */
    private Integer sendStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
