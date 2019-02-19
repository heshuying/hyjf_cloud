package com.hyjf.am.trade.service.api.aems.repay.impl;

import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.BorrowInfoExample;
import com.hyjf.am.trade.service.api.aems.repay.AemsUserRepayService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AemsUserRepayServiceImpl extends BaseServiceImpl implements AemsUserRepayService {
    @Override
    public BorrowInfoVO getBorrow(int userId, String roleId, String borrowNid) {
        BorrowInfoVO borrowInfoVO = null;
        // 获取当前的用户还款的项目
        BorrowInfoExample example = new BorrowInfoExample();
        BorrowInfoExample.Criteria borrowCrt = example.createCriteria();
        borrowCrt.andBorrowNidEqualTo(borrowNid);
        if (StringUtils.isNotEmpty(roleId) && "3".equals(roleId)) {// 如果是垫付机构
            borrowCrt.andRepayOrgUserIdEqualTo(userId);
        } else {// 普通借款人
            borrowCrt.andUserIdEqualTo(userId);
        }
        List<BorrowInfo> borrows = borrowInfoMapper.selectByExample(example);
        if (borrows != null && borrows.size() == 1) {
            BorrowInfo borrowInfo = borrows.get(0);
            borrowInfoVO = CommonUtils.convertBean(borrowInfo, BorrowInfoVO.class);
        }
        return borrowInfoVO;
    }
}
