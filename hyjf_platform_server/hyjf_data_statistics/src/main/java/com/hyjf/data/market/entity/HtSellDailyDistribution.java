package com.hyjf.data.market.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 销售日报配置表
 * </p>
 *
 * @author auto
 * @since 2019-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HtSellDailyDistribution implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 业务名称
     */
    private String businessName;

    /**
     * 联系人邮箱
     */
    private String email;

    /**
     * 时间点 1:每个工作日 2:每天  3:每月第一个工作日
     */
    private Boolean timePoint;

    /**
     * 邮件发送具体时间
     */
    private LocalTime time;

    /**
     * 状态 1:有效,2:无效
     */
    private Boolean status;

    /**
     * 创建人
     */
    private String createName;

    /**
     * 更新人
     */
    private String updateName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
