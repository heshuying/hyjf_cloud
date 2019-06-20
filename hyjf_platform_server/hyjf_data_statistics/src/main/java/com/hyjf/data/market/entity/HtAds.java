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
 * 新闻表（具体规则不明）
 * </p>
 *
 * @author auto
 * @since 2019-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HtAds implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    /**
     * 广告类型
     */
    private Integer typeId;

    private String url;

    /**
     * 调用代码
     */
    private String code;

    /**
     * 内容
     */
    private String content;

    private String image;

    /**
     * 排序
     */
    private Integer order;

    private Integer hits;

    private Integer status;

    /**
     * 分享url
     */
    private String shareUrl;

    /**
     * 分享图片url
     */
    private String shareImage;

    /**
     * 是否在首页特色banner位置显示，0为不显示，1显示
     */
    private Integer isIndex;

    private String startTime;

    private String endTime;

    /**
     * 是否已结束(只针对活动banner有效0:否,1:是)
     */
    private Boolean isEnd;

    private String shareTitle;

    /**
     * 活动列表图
     */
    private String activitiImage;

    /**
     * 活动描述
     */
    private String activitiDesc;

    /**
     * 共享内容
     */
    private String shareContent;

    /**
     * 客户端类型 0为pc广告  1为手机广告
     */
    private Boolean clientType;

    /**
     * 是否限制新手 1：限制 2：不限制
     */
    private Boolean newUserShow;

    /**
     * 创建人
     */
    private Integer createUserId;

    /**
     * 修改人
     */
    private Integer updateUserId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 拆分状态 1：android广告管理 2：ios广告管理 3: 微信广告管理
     */
    private Boolean platformType;


}
