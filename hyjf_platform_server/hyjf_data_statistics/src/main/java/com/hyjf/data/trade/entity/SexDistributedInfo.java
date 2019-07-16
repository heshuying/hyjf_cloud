package com.hyjf.data.trade.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther:yangchangwei
 * @Date:2019/6/28
 * @Description: 性别分布
 */
@Data
public class SexDistributedInfo implements Serializable {

    private String sex;

    private String count;

}
