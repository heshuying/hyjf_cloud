package com.hyjf.cs.market.vo.activity518;

/**
 * @author xiasq
 * @version Activity518InfoVO, v0.1 2019/4/30 13:55
 */
public class Activity518DrawVO {
    /**
     * @Author walter.limeng
     * @Description //中奖代码  0：18元代金券，1：58元代金券，2：518元代金券，3：0.8%加息券，4：1.0%加息券 ，5：iPhone XS（256G），6： 华为P30（256G）
     * @Date 16:24 2019-05-05
     **/

    private Integer currentAward;

    public Integer getCurrentAward() {
        return currentAward;
    }

    public void setCurrentAward(Integer currentAward) {
        this.currentAward = currentAward;
    }
}
