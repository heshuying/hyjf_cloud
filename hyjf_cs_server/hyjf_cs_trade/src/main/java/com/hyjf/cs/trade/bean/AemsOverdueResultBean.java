package com.hyjf.cs.trade.bean;

import com.hyjf.am.vo.trade.AemsOverdueVO;

import java.util.List;

/**
 * @author xiehuili on 2019/3/12.
 */
public class AemsOverdueResultBean extends BaseResultBean{

    /**
     * 逾期相关数据
     */
    private List<AemsOverdueVO> aemsOverdueVO;
    /**
     * 逾期标志 （正常：normal，逾期overdue）
     */
    private String overdue;


    public String getOverdue() {
        return overdue;
    }

    public void setOverdue(String overdue) {
        this.overdue = overdue;
    }

    public List<AemsOverdueVO> getAemsOverdueVO() {
        return aemsOverdueVO;
    }

    public void setAemsOverdueVO(List<AemsOverdueVO> aemsOverdueVO) {
        this.aemsOverdueVO = aemsOverdueVO;
    }
}
