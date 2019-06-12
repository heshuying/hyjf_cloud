/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.market;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author wangjun
 * @version DuiBaPointsDetailVO, v0.1 2019/6/11 11:22
 */
public class DuiBaPointsDetailVO extends BaseVO {

    @ApiModelProperty(value = "当前页数，按照请求直接返回")
    public int currentPage;

    @ApiModelProperty(value = "是否全部加载")
    public boolean end;

    @ApiModelProperty(value = "累计获取")
    public String creditsGot;

    @ApiModelProperty(value = "累计使用")
    public String creditsUsed;

    @ApiModelProperty(value = "积分明细列表")
    public List<DuiBaPointsListDetailVO> list;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public String getCreditsGot() {
        return creditsGot;
    }

    public void setCreditsGot(String creditsGot) {
        this.creditsGot = creditsGot;
    }

    public String getCreditsUsed() {
        return creditsUsed;
    }

    public void setCreditsUsed(String creditsUsed) {
        this.creditsUsed = creditsUsed;
    }

    public List<DuiBaPointsListDetailVO> getList() {
        return list;
    }

    public void setList(List<DuiBaPointsListDetailVO> list) {
        this.list = list;
    }
}
