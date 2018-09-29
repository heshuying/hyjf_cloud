package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.admin.FinmanChargeNewRequest;
import com.hyjf.am.vo.trade.borrow.BorrowFinmanNewChargeVO;

import java.util.List;

/**
 * @author xiehuili on 2018/8/14.
 */
public interface FinmanNewChargeCustomizeMapper {
    /**
     *
     * 检索融资管理费件数
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    public int countRecordTotal(FinmanChargeNewRequest adminRequest);

    /**
     *
     * 检索融资管理费列表
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    public List<BorrowFinmanNewChargeVO> getRecordList(FinmanChargeNewRequest adminRequest);
}
