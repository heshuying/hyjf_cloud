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
 * 春节活动奖品明细表
 * </p>
 *
 * @author auto
 * @since 2019-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HtNewYearActivityReward implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 姓名
     */
    private String trueName;

    /**
     * 收件人姓名
     */
    private String recipientName;

    /**
     * 收件人地址
     */
    private String recipientAddress;

    /**
     * 收件人手机号
     */
    private String recipientMobile;

    /**
     * 奖励名称
     */
    private String reward;

    /**
     * 发放状态 1 已发放 0 未发放
     */
    private Boolean status;

    /**
     * 发放时间
     */
    private LocalDateTime releaseTime;

    /**
     * 获得时间
     */
    private LocalDateTime getTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
