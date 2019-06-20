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
 * 奖品配置表
 * </p>
 *
 * @author auto
 * @since 2019-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HtInvitePrizeConf implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 奖品名称
     */
    private String prizeName;

    /**
     * 奖品数量
     */
    private Integer prizeQuantity;

    /**
     * 所需推荐星数量
     */
    private Integer recommendQuantity;

    /**
     * 奖品剩余数量
     */
    private Integer prizeReminderQuantity;

    /**
     * 奖品分组
     */
    private String prizeGroupCode;

    /**
     * 奖品类别 1：实物奖品，2：优惠券
     */
    private Boolean prizeType;

    private String couponCode;

    /**
     * 中奖概率
     */
    private BigDecimal prizeProbability;

    /**
     * 实物奖品图片
     */
    private String prizePicUrl;

    /**
     * 奖品类别  1：兑奖，2：抽奖
     */
    private Boolean prizeKind;

    /**
     * 奖品排序  
     */
    private Integer prizeSort;

    /**
     * 奖品状态，0：启用，1：禁用
     */
    private Integer prizeStatus;

    /**
     * 兑奖成功时的提示消息
     */
    private String successMessage;

    /**
     * 奖品的适用时间，以月为单位
     */
    private Integer prizeApplyTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标识 0：未删除，1：已删除
     */
    private Boolean delFlag;

    /**
     * 创建人
     */
    private Integer createUserId;

    /**
     * 修改人
     */
    private Integer updateUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
