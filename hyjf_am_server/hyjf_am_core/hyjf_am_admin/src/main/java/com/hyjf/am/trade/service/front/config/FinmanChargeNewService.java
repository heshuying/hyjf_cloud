package com.hyjf.am.trade.service.front.config;

import com.hyjf.am.resquest.admin.FinmanChargeNewRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowFinmanNewCharge;
import com.hyjf.am.vo.trade.borrow.BorrowFinmanNewChargeVO;

import java.util.List;

/**
 * @author xiehuili on 2018/8/14.
 */
public interface FinmanChargeNewService {

    int countRecordTotal(FinmanChargeNewRequest adminRequest);

    List<BorrowFinmanNewChargeVO> getRecordList(FinmanChargeNewRequest adminRequest, int limitStart, int limitEnd);
    /**
     * 查询费率配置
     *  @author xiehuili
     * @return
     */
    BorrowFinmanNewCharge getRecordInfo(String manChargeCd);
    /**
     * 添加费率配置
     *  @author xiehuili
     * @return
     */

    public int insertFinmanChargeNew(FinmanChargeNewRequest adminRequest);
    /**
     * 修改费率配置
     *  @author xiehuili
     * @return
     */
    public int updateFinmanChargeNew(FinmanChargeNewRequest adminRequest);
    /**
     * 删除费率配置
     *  @author xiehuili
     * @return
     */
    public int deleteFinmanChargeNew(FinmanChargeNewRequest adminRequest);

    /**
     *
     * 根据表的类型,期数,项目类型检索管理费件数
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    public int countRecordByProjectType(FinmanChargeNewRequest adminRequest);

    /**
     *
     * 根据borrowClass查询borrowClass是否存在
     * @author xiehuili
     * @return
     */
    String getBorrowClass(String borrowClass);

}