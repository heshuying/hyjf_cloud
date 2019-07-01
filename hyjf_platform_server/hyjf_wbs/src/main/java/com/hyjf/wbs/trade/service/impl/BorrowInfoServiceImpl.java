package com.hyjf.wbs.trade.service.impl;

import com.hyjf.wbs.trade.dao.model.customize.BorrowCustomize;
import com.hyjf.wbs.trade.service.BorrowInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: wxd
 * @Date: 2019-04-30 10:37
 * @Description:
 */
@Service
public class BorrowInfoServiceImpl extends BaseServiceImpl implements BorrowInfoService {
    @Override
    public BorrowCustomize selectByNid(String nid) {
       List<BorrowCustomize> borrowCustomize =  borrowCustomizeMapper.getBorrowCustomize(nid);
       if(borrowCustomize!=null&&borrowCustomize.size()>0){
            return borrowCustomize.get(0);
       }
       return null;
    }
}
