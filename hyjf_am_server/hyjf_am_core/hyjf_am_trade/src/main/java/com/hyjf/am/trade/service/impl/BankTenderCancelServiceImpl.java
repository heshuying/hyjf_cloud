package com.hyjf.am.trade.service.impl;

import com.hyjf.am.resquest.trade.TenderCancelRequest;
import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.BankTenderCancelService;
import com.hyjf.am.vo.trade.borrow.BorrowTenderTmpVO;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 投资撤销异常
 * @author jijun
 * @date 20180625
 */
@Service
public class BankTenderCancelServiceImpl implements BankTenderCancelService {

    private static final Logger logger = LoggerFactory.getLogger(BankTenderCancelServiceImpl.class);

    @Autowired
    private BorrowTenderTmpMapper borrowTenderTmpMapper;
    @Autowired
    private FreezeHistoryMapper freezeHistoryMapper;

    @Override
    public List<BorrowTenderTmp> getBorrowTenderTmpsForTenderCancel() {
        Date dayStart10 = GetDate.getDayStartOfSomeDay(new Date());//当天开始时间
        BorrowTenderTmpExample example = new BorrowTenderTmpExample();
        example.createCriteria().andIsBankTenderEqualTo(1).andCreateTimeLessThan(dayStart10).andStatusNotEqualTo(3);//除上次处理异常数据
        example.setLimitStart(0);
        example.setLimitEnd(99);
        List<BorrowTenderTmp> tmpList = this.borrowTenderTmpMapper.selectByExample(example);
        return tmpList;
    }


    @Override
    public boolean updateBidCancelRecord(TenderCancelRequest request) {
        BorrowTenderTmpVO tenderTmp= request.getBorrowTenderTmpVO();
        String username= request.getUserName();

        if (tenderTmp==null || StringUtils.isBlank(username)){
            logger.info("传入参数为空,撤销投资操作失败!");
            return false;
        }

        Integer userId = tenderTmp.getUserId();
        boolean tenderTmpFlag = this.borrowTenderTmpMapper.deleteByPrimaryKey(tenderTmp.getId()) > 0 ? true : false;
        if (!tenderTmpFlag) {
            logger.info("删除投资日志表失败，投资订单号：" + tenderTmp.getNid());
            return false;
        }
        FreezeHistory freezeHistory = new FreezeHistory();
        freezeHistory.setTrxId(tenderTmp.getNid());
        freezeHistory.setNotes("自动任务银行投资撤销");
        freezeHistory.setFreezeUser(username);
        freezeHistory.setFreezeTime(GetDate.getNowTime10());
        boolean freezeHisLog = this.freezeHistoryMapper.insert(freezeHistory) > 0 ? true : false;
        if (!freezeHisLog) {
            logger.info("插入投资删除日志表失败，投资订单号：" + tenderTmp.getNid());
            return false;
        }
        return true;
    }


    @Override
    public boolean updateTenderCancelExceptionData(BorrowTenderTmp info) {
        BorrowTenderTmp record;
        BorrowTenderTmpExample example = new BorrowTenderTmpExample();
        example.createCriteria().andNidEqualTo(info.getNid());
        List<BorrowTenderTmp> tempInfo = borrowTenderTmpMapper.selectByExample(example);
        record = tempInfo.get(0);
        record.setStatus(3);
        int result=borrowTenderTmpMapper.updateByPrimaryKey(record);
        if (result>0){
            return true;
        }else{
            return false;
        }

    }
}
