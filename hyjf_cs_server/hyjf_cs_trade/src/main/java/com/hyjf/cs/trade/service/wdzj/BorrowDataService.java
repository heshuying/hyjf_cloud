package com.hyjf.cs.trade.service.wdzj;

import com.hyjf.am.vo.wdzj.BorrowListCustomizeVO;
import com.hyjf.am.vo.wdzj.PreapysListCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * @author hesy
 * @version BorrowDataService, v0.1 2018/7/16 15:35
 */
public interface BorrowDataService {
    List<BorrowListCustomizeVO> selectBorrowList(Map<String, Object> requestBean);

    Integer countBorrowList(Map<String, Object> requestBean);

    String sumBorrowAmount(Map<String, Object> requestBean);

    List<PreapysListCustomizeVO> selectPreapysList(Map<String, Object> requestBean);

    Integer countPreapysList(Map<String, Object> requestBean);
}
