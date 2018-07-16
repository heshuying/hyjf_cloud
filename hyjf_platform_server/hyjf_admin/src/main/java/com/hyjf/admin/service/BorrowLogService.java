package com.hyjf.admin.service;

import com.hyjf.admin.beans.BorrowLogBean;
import com.hyjf.am.resquest.admin.BorrowLogRequset;
import com.hyjf.am.vo.admin.BorrowLogCustomizeVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowLogService, v0.1 2018/7/11 10:20
 */
public interface BorrowLogService {
    BorrowLogBean selectBorrowLogList(BorrowLogRequset request);

    List<BorrowLogCustomizeVO> exportBorrowLogList(BorrowLogRequset copyForm);
}
