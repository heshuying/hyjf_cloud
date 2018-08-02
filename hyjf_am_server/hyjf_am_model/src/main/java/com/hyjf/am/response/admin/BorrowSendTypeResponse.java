package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BorrowSendTypeVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;

/**
 * @author by xiehuili on 2018/8/1.
 */
public class BorrowSendTypeResponse  extends Response<BorrowSendTypeVO> {

    private int count;
//    获取数据字典表的下拉列表
    private List<ParamNameVO> enddayMonthList;
    //重定向url
    private String redirectUrl;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ParamNameVO> getEnddayMonthList() {
        return enddayMonthList;
    }

    public void setEnddayMonthList(List<ParamNameVO> enddayMonthList) {
        this.enddayMonthList = enddayMonthList;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
