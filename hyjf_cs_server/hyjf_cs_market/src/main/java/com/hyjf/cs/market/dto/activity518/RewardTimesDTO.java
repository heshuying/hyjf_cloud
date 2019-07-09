package com.hyjf.cs.market.dto.activity518;

/**
 * @author xiasq
 * @version RewardTimesDTO, v0.1 2019/5/10 14:37
 */
public class RewardTimesDTO {
    /**
     * 剩余抽奖次数
     */
    private int times;

    /**
     * 已抽奖次数
     */
    private int alreadyTimes;

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getAlreadyTimes() {
        return alreadyTimes;
    }

    public void setAlreadyTimes(int alreadyTimes) {
        this.alreadyTimes = alreadyTimes;
    }
}
