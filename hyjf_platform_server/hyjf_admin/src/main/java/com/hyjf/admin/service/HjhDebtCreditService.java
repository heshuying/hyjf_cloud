package com.hyjf.admin.service;


import com.hyjf.admin.common.service.BaseService;
import com.hyjf.am.response.admin.HjhDebtCreditReponse;
import com.hyjf.am.resquest.admin.HjhDebtCreditListRequest;
import com.hyjf.am.vo.admin.HjhDebtCreditVo;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;

import java.util.List;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/3
 * @Description: 汇计划-转让记录接口
 */
public interface HjhDebtCreditService extends BaseService{

    List<BorrowStyleVO> selectBorrowStyleList();

    HjhDebtCreditReponse queryHjhDebtCreditList(HjhDebtCreditListRequest request);

    void queryHjhDebtCreditListStatusName(List<HjhDebtCreditVo> hjhDebtCreditVoList);


}
