package com.hyjf.cs.market.vo.activity51;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiasq
 * @version ActivityTimeVO, v0.1 2019/4/24 14:20
 */

@ApiModel(value = "查询当前时间是不是在活动时间范围内返回参数")
public class ActivityTimeVO {

    @ApiModelProperty("当前时间是否在活动时间范围， Y-是， N-不是")
    private String isActivityTimeFlag;
    @ApiModelProperty("描述")
    private String desc;

    public ActivityTimeVO(String isActivityTimeFlag, String desc) {
        this.isActivityTimeFlag = isActivityTimeFlag;
        this.desc = desc;
    }

    public String getIsActivityTimeFlag() {
        return isActivityTimeFlag;
    }

    public void setIsActivityTimeFlag(String isActivityTimeFlag) {
        this.isActivityTimeFlag = isActivityTimeFlag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
