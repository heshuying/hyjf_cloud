package com.hyjf.am.trade.service.admin.borrow.impl;

import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.trade.dao.model.customize.AdminBorrowRecoverCustomize;
import com.hyjf.am.trade.service.admin.borrow.AdminBorrowRecoverService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.paginator.Paginator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version AdminBorrowRecoverServiceImpl, v0.1 2018/7/2 16:28
 */
@Service
public class AdminBorrowRecoverServiceImpl extends BaseServiceImpl implements AdminBorrowRecoverService {


    @Override
    public int countBorrowRecover(BorrowRecoverRequest request) {
        return this.borrowRecoverCustomizeMapper.countBorrowRecover(request);
    }

    @Override
    public List<AdminBorrowRecoverCustomize> selectBorrowRecoverList(BorrowRecoverRequest request) {
        List<AdminBorrowRecoverCustomize> list=this.borrowRecoverCustomizeMapper.selectBorrowRecoverList(request);
        for (AdminBorrowRecoverCustomize customize:list) {
            customize.setIsRecover(CacheUtil.getParamName("LOAN_STATUS",customize.getIsRecover()));

            if(customize.getTenderUserAttribute()!=null){
                customize.setTenderUserAttribute(CacheUtil.getParamName("USER_PROPERTY",customize.getTenderUserAttribute()));
            }
            if(customize.getInviteUserAttribute()!=null){
                customize.setInviteUserAttribute(CacheUtil.getParamName("USER_PROPERTY",customize.getInviteUserAttribute()));
            }
        }
        return list;
    }

    @Override
    public List<AdminBorrowRecoverCustomize> exportBorrowRecoverList(BorrowRecoverRequest request) {
		Paginator paginator = new Paginator(request.getCurrPage(),this.borrowRecoverCustomizeMapper.countBorrowRecover(request),request.getPageSize());
		request.setLimitStart(paginator.getOffset());
		request.setLimitEnd(paginator.getLimit());
        List<AdminBorrowRecoverCustomize> list=this.borrowRecoverCustomizeMapper.exportBorrowRecoverList(request);
        
         Map<String, String> loanStatus = CacheUtil.getParamNameMap("LOAN_STATUS");
         Map<String, String> userProperty = CacheUtil.getParamNameMap("USER_PROPERTY");
        for (AdminBorrowRecoverCustomize customize:list) {
            customize.setIsRecover(loanStatus.get(customize.getIsRecover()));

            if(customize.getTenderUserAttribute()!=null){
                customize.setTenderUserAttribute(userProperty.get(customize.getTenderUserAttribute()));
            }
            if(customize.getInviteUserAttribute()!=null){
                customize.setInviteUserAttribute(userProperty.get(customize.getInviteUserAttribute()));
            }
        }
        return list;
    }

    @Override
    public AdminBorrowRecoverCustomize sumBorrowRecoverList(BorrowRecoverRequest request) {
        return this.borrowRecoverCustomizeMapper.sumBorrowRecoverList(request);
    }
}
