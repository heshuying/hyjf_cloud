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
 * 活动列表
 * </p>
 *
 * @author auto
 * @since 2019-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HtActivityList implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 活动名称
     */
    private String title;

    /**
     * 活动开始时间
     */
    private Integer timeStart;

    /**
     * 活动结束时间
     */
    private Integer timeEnd;

    private String imgPc;

    private String imgApp;

    private String imgWei;

    private String activityPcUrl;

    private String activityAppUrl;

    private String activityWeiUrl;

    /**
     * 主图
     */
    private String img;

    /**
     * 二维码
     */
    private String qr;

    /**
     * 平台
     */
    private String platform;

    /**
     * 前台地址
     */
    private String urlForeground;

    /**
     * 后台管理地址
     */
    private String urlBackground;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
