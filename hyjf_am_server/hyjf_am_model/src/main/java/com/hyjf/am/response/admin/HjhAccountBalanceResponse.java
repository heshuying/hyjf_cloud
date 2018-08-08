package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.HjhAccountBalanceVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/7  9:49
 */
public class HjhAccountBalanceResponse extends Response<HjhAccountBalanceVO> {

    // 数据查询条数 主要用于分页情况，原子层向组合层返回
    private  Integer  count;

    @ApiModelProperty(value = "列表")
    private List<HjhAccountBalanceVO> recordList;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<HjhAccountBalanceVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<HjhAccountBalanceVO> recordList) {
        this.recordList = recordList;
    }
}
