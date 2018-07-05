package com.hyjf.admin.client;

import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.vo.admin.BorrowRecoverCustomizeVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRecoverClient, v0.1 2018/7/2 10:17
 */
public interface BorrowRecoverClient {
    Integer countBorrowRecover(BorrowRecoverRequest borrowRecoverCustomize);

    List<BorrowRecoverCustomizeVO> selectBorrowRecoverList(BorrowRecoverRequest borrowRecoverCustomize);

    BorrowRecoverCustomizeVO sumBorrowRecoverList(BorrowRecoverRequest borrowRecoverCustomize);
}
