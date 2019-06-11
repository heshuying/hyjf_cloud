package com.hyjf.am.trade.service.front.borrow.impl;

import com.hyjf.am.trade.dao.mapper.customize.BorrowManinfoCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowManinfo;
import com.hyjf.am.trade.dao.model.auto.BorrowManinfoExample;
import com.hyjf.am.trade.service.front.borrow.BorrowManinfoService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.borrow.BorrowManinfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 页面录入借款人信息
 * @author zhangyk upd by liushouyi
 */
@Service
public class BorrowManinfoServiceImpl extends BaseServiceImpl implements BorrowManinfoService {

    @Resource
    private BorrowManinfoCustomizeMapper maninfoCustomizeMapper;

    /**
     * 根据借款编号获取借款人信息
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowManinfo getBorrowManinfoByNid(String borrowNid) {
        BorrowManinfoExample example = new BorrowManinfoExample();
        BorrowManinfoExample.Criteria cri = example.createCriteria();
        cri.andBorrowNidEqualTo(borrowNid);
        List<BorrowManinfo> list = borrowManinfoMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<BorrowManinfoVO> getBorrowManinfoList(List<String> nids) {
        return maninfoCustomizeMapper.getBorrowManinfoList(nids);
    }
}
