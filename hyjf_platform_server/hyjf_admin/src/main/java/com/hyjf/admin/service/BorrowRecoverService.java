package com.hyjf.admin.service;

import com.hyjf.admin.beans.BorrowRecoverBean;
import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.vo.admin.BorrowRecoverCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRecoverService, v0.1 2018/7/2 10:16
 */
public interface BorrowRecoverService {
    /**
     * @Description 
     * @Author pangchengchao
     * @Version v0.1
     * @Date 获取资金来源列表 
     */
    List<HjhInstConfigVO> selectHjhInstConfigByInstCode(String instCode);
    /**
     * @Description 
     * @Author pangchengchao
     * @Version v0.1
     * @Date 获取放款明细列表信息 
     */
    BorrowRecoverBean searchBorrowRecover(BorrowRecoverRequest form);
    /**
     * @Description 
     * @Author pangchengchao
     * @Version v0.1
     * @Date 放款明细导出列表 
     */
    List<BorrowRecoverCustomizeVO> exportBorrowRecoverList(BorrowRecoverRequest form);
}
