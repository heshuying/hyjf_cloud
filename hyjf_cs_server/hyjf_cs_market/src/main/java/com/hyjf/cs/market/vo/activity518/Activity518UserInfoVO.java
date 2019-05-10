package com.hyjf.cs.market.vo.activity518;

import com.hyjf.am.vo.activity.ActivityUserRewardVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author xiasq
 * @version Activity518UserInfoVO, v0.1 2019/4/30 13:57
 */
@ApiModel(value = "用户信息接口返回参数")
public class Activity518UserInfoVO {
    @ApiModelProperty("剩余抽奖次数")
    private int times;
    @ApiModelProperty("已抽奖次数")
    private int alreadyTimes;
    @ApiModelProperty("累计年化出借金额")
    private String amount;
    @ApiModelProperty("抽奖记录")
    private List<RewardRecord> record;

    @ApiModel(value = "抽奖记录")
    public static class RewardRecord{
        @ApiModelProperty("抽奖时间")
        private String time;
        /**
         * 0：18元代金券
         * 1：58元代金券
         * 2：518元代金券
         * 3：0.8%加息券
         * 4：1.0%加息券
         * 5：iPhone XS（256G）
         * 6： 华为P30（256G）
         */
        @ApiModelProperty("获得奖品  奖品代码见接口文档")
        private int award;

        public RewardRecord(ActivityUserRewardVO rewardVO) {
           DateFormat bf = new SimpleDateFormat("MM月dd日 HH:mm:ss");
           Date createTime =  rewardVO.getCreateTime();
           this.time = bf.format(createTime);
           this.award = Integer.parseInt(rewardVO.getRewardType());
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getAward() {
            return award;
        }

        public void setAward(int award) {
            this.award = award;
        }
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<RewardRecord> getRecord() {
        return record;
    }

    public void setRecord(List<RewardRecord> record) {
        this.record = record;
    }

    public int getAlreadyTimes() {
        return alreadyTimes;
    }

    public void setAlreadyTimes(int alreadyTimes) {
        this.alreadyTimes = alreadyTimes;
    }
}
