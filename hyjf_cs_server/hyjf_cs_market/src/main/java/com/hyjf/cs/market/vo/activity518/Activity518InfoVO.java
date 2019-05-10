package com.hyjf.cs.market.vo.activity518;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author xiasq
 * @version Activity518InfoVO, v0.1 2019/4/30 13:55
 */
@ApiModel(value = "活动信息返回参数")
public class Activity518InfoVO {

    @ApiModelProperty("活动状态，未开启:-1,进行中:0,已结束:1")
    private int started;

    @ApiModelProperty("排行榜")
    private List<Leaderboard> leaderboard;

    @ApiModelProperty("用户名")
    private String username = "";

    @ApiModelProperty("累计年化出借金额")
    private String amount = "";

    /**
     * 出借排行榜
     */
    @ApiModel(value = "活动信息返回参数")
    public static class Leaderboard {
        @ApiModelProperty("脱敏的手机号码")
        private String mobile;
        @ApiModelProperty("累计年化出借金额")
        private String amount;

        public Leaderboard(String username, String amount) {
            this.mobile = username;
            this.amount = amount;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }


    public int getStarted() {
        return started;
    }

    public void setStarted(int started) {
        this.started = started;
    }

    public List<Leaderboard> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(List<Leaderboard> leaderboard) {
        this.leaderboard = leaderboard;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}



