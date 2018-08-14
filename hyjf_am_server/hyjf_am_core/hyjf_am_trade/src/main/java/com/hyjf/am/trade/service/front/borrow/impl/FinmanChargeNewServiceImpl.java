package com.hyjf.am.trade.service.front.borrow.impl;

import com.hyjf.am.resquest.admin.FinmanChargeNewRequest;
import com.hyjf.am.trade.service.front.borrow.FinmanChargeNewService;
import com.hyjf.am.vo.trade.borrow.BorrowFinmanNewChargeVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiehuili on 2018/8/14.
 */
@Service
public class FinmanChargeNewServiceImpl implements FinmanChargeNewService {

    @Override
    public  int countRecordTotal(FinmanChargeNewRequest adminRequest){
          return 0;
    }
    @Override
    public List<BorrowFinmanNewChargeVO> getRecordList(FinmanChargeNewRequest adminRequest, int limitStart, int limitEnd){
      return null;
    }
}
