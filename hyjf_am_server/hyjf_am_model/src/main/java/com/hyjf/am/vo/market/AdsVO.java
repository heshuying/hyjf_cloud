package com.hyjf.am.vo.market;

import com.hyjf.am.vo.BaseVO;

/**
 * @author xiasq
 * @version AdsVO, v0.1 2018/5/14 16:13
 */
public class AdsVO extends BaseVO {
    private int timeStart;
    private int timeEnd;

    public int getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(int timeStart) {
        this.timeStart = timeStart;
    }

    public int getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(int timeEnd) {
        this.timeEnd = timeEnd;
    }
}
