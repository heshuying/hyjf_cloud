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
 * 积分调整申请表
 * </p>
 *
 * @author auto
 * @since 2019-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HtDuibaPointsModify implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 真实姓名
     */
    private String trueName;

    /**
     * 申请订单号
     */
    private String modifyOrderId;

    /**
     * 调整积分数
     */
    private Integer points;

    /**
     * 当时调整后总积分数
     */
    private Integer total;

    /**
     * 操作类型：0调增 1调减
     */
    private Boolean pointsType;

    /**
     * 调整人用户名
     */
    private String modifyName;

    /**
     * 审批人用户名
     */
    private String reviewName;

    /**
     * 调整原因
     */
    private String modifyReason;

    /**
     * 当前审批节点
     */
    private Integer flowOrder;

    /**
     * 审核状态: 0待审核 1审核通过 2审核不通过 
     */
    private Boolean status;

    /**
     * 备注
     */
    private String remark;

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


}
