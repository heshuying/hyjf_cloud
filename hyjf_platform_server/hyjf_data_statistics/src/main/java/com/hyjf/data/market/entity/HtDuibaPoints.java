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
 * 用户积分明细表
 * </p>
 *
 * @author auto
 * @since 2019-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HtDuibaPoints implements Serializable {

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
     * 当前操作积分数
     */
    private Integer points;

    /**
     * 当前操作后总积分数
     */
    private Integer total;

    /**
     * 类型 0:获取 1:使用
     */
    private Boolean type;

    /**
     * 积分业务名称: 0出借 1商品兑换 2订单取消
     */
    private Boolean businessName;

    /**
     * 兑吧订单号
     */
    private String duibaOrderId;

    /**
     * 汇盈订单号
     */
    private String hyOrderId;

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
