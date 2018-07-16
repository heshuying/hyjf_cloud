package com.hyjf.admin.client;

import com.hyjf.am.resquest.admin.BorrowLogRequset;
import com.hyjf.am.vo.admin.BorrowLogCustomizeVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowLogClient, v0.1 2018/7/11 10:29
 */
public interface BorrowLogClient {
    Integer countBorrowLog(BorrowLogRequset request);

    List<BorrowLogCustomizeVO> selectBorrowLogList(BorrowLogRequset request);

    List<BorrowLogCustomizeVO> exportBorrowLogList(BorrowLogRequset request);
}
