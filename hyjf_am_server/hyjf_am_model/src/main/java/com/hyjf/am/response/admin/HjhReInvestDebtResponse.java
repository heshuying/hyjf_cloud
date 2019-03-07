package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.hjh.HjhReInvestDebtVO;

/**
 * @Author : huanghui
 */
public class HjhReInvestDebtResponse extends Response<HjhReInvestDebtVO> {

    private int count;

    private HjhReInvestDebtVO sumHjhReInvestDebtVO;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public HjhReInvestDebtVO getSumHjhReInvestDebtVO() {
        return sumHjhReInvestDebtVO;
    }

    public void setSumHjhReInvestDebtVO(HjhReInvestDebtVO sumHjhReInvestDebtVO) {
        this.sumHjhReInvestDebtVO = sumHjhReInvestDebtVO;
    }
}
