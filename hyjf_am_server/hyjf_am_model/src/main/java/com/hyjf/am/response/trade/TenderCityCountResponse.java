package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.TenderCityCountVO;
import com.hyjf.am.vo.trade.TenderSexCountVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/9/1  13:47
 */
public class TenderCityCountResponse  extends Response {

    private List<TenderCityCountVO> listTenderCityCountVO;
    private List<TenderSexCountVO> listTenderSexCountVO;
    private Integer age;
    private BigDecimal accountMonth;
    private Integer count;
    private float aFloat;

    public List<TenderCityCountVO> getListTenderCityCountVO() {
        return listTenderCityCountVO;
    }

    public void setListTenderCityCountVO(List<TenderCityCountVO> listTenderCityCountVO) {
        this.listTenderCityCountVO = listTenderCityCountVO;
    }

    public List<TenderSexCountVO> getListTenderSexCountVO() {
        return listTenderSexCountVO;
    }

    public void setListTenderSexCountVO(List<TenderSexCountVO> listTenderSexCountVO) {
        this.listTenderSexCountVO = listTenderSexCountVO;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getAccountMonth() {
        return accountMonth;
    }

    public void setAccountMonth(BigDecimal accountMonth) {
        this.accountMonth = accountMonth;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public float getaFloat() {
        return aFloat;
    }

    public void setaFloat(float aFloat) {
        this.aFloat = aFloat;
    }
}
