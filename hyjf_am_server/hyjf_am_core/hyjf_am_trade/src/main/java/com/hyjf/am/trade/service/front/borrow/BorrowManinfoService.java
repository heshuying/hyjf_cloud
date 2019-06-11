package com.hyjf.am.trade.service.front.borrow;

import com.hyjf.am.trade.dao.model.auto.BorrowManinfo;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.trade.borrow.BorrowManinfoVO;

import java.util.List;
import java.util.Set;

/**
 * 页面录入借款人信息
 * @author zhangyk upd by liushouyi
 */
public interface BorrowManinfoService extends BaseService {

    /**
     * 根据借款编号获取借款人信息
     *
     * @param borrowNid
     * @return
     */
    BorrowManinfo getBorrowManinfoByNid(String borrowNid);

    /**
     * 根据标的编号批量查询借款人信息
     * @param nids
     * @return
     */
    List<BorrowManinfoVO> getBorrowManinfoList(List<String> nids);
}
