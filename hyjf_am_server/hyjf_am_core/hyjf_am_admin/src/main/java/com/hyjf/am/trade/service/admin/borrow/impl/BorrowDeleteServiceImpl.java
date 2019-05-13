/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.borrow.impl;

import com.hyjf.am.admin.mq.base.CommonProducer;
import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.admin.BorrowRegistUpdateRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.admin.borrow.BorrowDeleteService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.BorrowDeleteConfirmCustomizeVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 标的删除
 * @author hesy
 */
@Service
public class BorrowDeleteServiceImpl extends BaseServiceImpl implements BorrowDeleteService {

    @Resource
    private CommonProducer commonProducer;

    /**
     * 标的删除确认页面数据获取
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowDeleteConfirmCustomizeVO selectDeleteConfirm(String borrowNid){
        return borrowDeleteCustomizeMapper.selectDeleteConfirm(borrowNid);
    }

    /**
     * 标的删除成功后删除对应标的数据
     */
    @Override
    public Response deleteBorrow(BorrowRegistUpdateRequest requestBean) {
        BorrowInfoWithBLOBs borrowInfo = this.getBorrowInfoByNid(requestBean.getBorrowNid());
        Borrow borrow = this.getBorrow(requestBean.getBorrowNid());

        // 删除borrow表标的数据
        BorrowExample borrowExample = new BorrowExample();
        borrowExample.createCriteria().andBorrowNidEqualTo(requestBean.getBorrowNid());
        borrowMapper.deleteByExample(borrowExample);

        // 删除borrow_info表标的数据
        BorrowInfoExample borrowInfoExample = new BorrowInfoExample();
        borrowInfoExample.createCriteria().andBorrowNidEqualTo(requestBean.getBorrowNid());
        borrowInfoMapper.deleteByExample(borrowInfoExample);

        // 删除borrow_maninfo表标的数据
        BorrowManinfoExample borrowManinfoExample = new BorrowManinfoExample();
        borrowManinfoExample.createCriteria().andBorrowNidEqualTo(requestBean.getBorrowNid());
        borrowManinfoMapper.deleteByExample(borrowManinfoExample);

        // 删除borrow_user表标的数据
        BorrowUserExample borrowUserExample = new BorrowUserExample();
        borrowUserExample.createCriteria().andBorrowNidEqualTo(requestBean.getBorrowNid());
        borrowUserMapper.deleteByExample(borrowUserExample);

        // 删除borrow_carinfo表标的数据
        BorrowCarinfoExample borrowCarinfoExample = new BorrowCarinfoExample();
        borrowCarinfoExample.createCriteria().andBorrowNidEqualTo(requestBean.getBorrowNid());
        borrowCarinfoMapper.deleteByExample(borrowCarinfoExample);

        // 删除borrow_houses表标的数据
        BorrowHousesExample borrowHousesExample = new BorrowHousesExample();
        borrowHousesExample.createCriteria().andBorrowNidEqualTo(requestBean.getBorrowNid());
        borrowHousesMapper.deleteByExample(borrowHousesExample);

        // 删除borrow_company_authen表标的数据
        BorrowCompanyAuthenExample borrowCompanyAuthenExample = new BorrowCompanyAuthenExample();
        borrowCompanyAuthenExample.createCriteria().andBorrowNidEqualTo(requestBean.getBorrowNid());
        borrowCompanyAuthenMapper.deleteByExample(borrowCompanyAuthenExample);

        // 添加标的修改日志
        BorrowLog borrowLog = new BorrowLog();
        borrowLog.setBorrowNid(requestBean.getBorrowNid());
//                    String statusNameString = getBorrowStatusName(borrowBean.getStatus());
//                    borrowLog.setBorrowStatus(statusNameString);
        borrowLog.setBorrowStatusCd(borrow.getStatus());
        borrowLog.setType("删除");
        borrowLog.setCreateTime(new Date());
        borrowLog.setCreateUserId(Integer.parseInt(requestBean.getCurrUserId()));
        borrowLog.setCreateUserName(requestBean.getCurrUserName());
        borrowLogMapper.insert(borrowLog);

        return new Response();
    }
}
