package com.hyjf.am.trade.service.impl.task.issuerecover;/**
 * @Auther: Walter
 * @Date: 2018/7/11 19:03
 * @Description:
 */

import com.hyjf.am.trade.dao.mapper.auto.BorrowBailMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowConfigMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowInfoMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.mq.consumer.hjh.issuerecover.AutoBailMessageConsumer;
import com.hyjf.am.trade.service.task.issuerecover.AutoBailMessageService;
import com.hyjf.am.vo.task.issuerecover.BorrowWithBLOBs;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/11 19:03
 * @Description: AutoBailMessageServiceImpl
 */
@Service
public class AutoBailMessageServiceImpl implements AutoBailMessageService {
    private static final Logger logger = LoggerFactory.getLogger(AutoBailMessageServiceImpl.class);
    @Resource
    private BorrowMapper borrowMapper;
    @Resource
    private BorrowInfoMapper borrowInfoMapper;
    @Resource
    private BorrowBailMapper borrowBailMapper;
    @Resource
    private BorrowConfigMapper borrowConfigMapper;
    @Override
    public Borrow getBorrowByBorrowNidrowNid(String borrowNid) {
        BorrowExample example = new BorrowExample();
        BorrowExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowNidEqualTo(borrowNid);
        List<Borrow> list = borrowMapper.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public BorrowInfo getById(Integer id) {
        return borrowInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateRecordBorrow(BorrowInfo borrowInfo, Borrow borrow, HjhAssetBorrowtype hjhAssetBorrowType) {
        // 借款编号存在
        if (StringUtils.isNotEmpty(borrow.getBorrowNid())) {

                // 该借款编号没有交过保证金
                BorrowBailExample exampleBail = new BorrowBailExample();
                BorrowBailExample.Criteria craBail = exampleBail.createCriteria();
                craBail.andBorrowNidEqualTo(borrow.getBorrowNid());
                List<BorrowBail> borrowBailList = this.borrowBailMapper.selectByExample(exampleBail);
                if (borrowBailList == null || borrowBailList.size() == 0) {
//							AdminSystem adminSystem = (AdminSystem) SessionUtils.getSession(CustomConstants.LOGIN_USER_INFO);
                    BorrowBail borrowBail = new BorrowBail();
                    // 借款人的ID
                    borrowBail.setBorrowUid(borrow.getUserId());
                    // 操作人的ID
                    borrowBail.setOperaterUid(1);
                    // 借款编号
                    borrowBail.setBorrowNid(borrow.getBorrowNid());
                    // 保证金数值
                    BigDecimal bailPercent = new BigDecimal(this.getBorrowConfig(CustomConstants.BORROW_BAIL_RATE));// 计算公式：保证金金额=借款金额×3％
                    BigDecimal accountBail = (borrow.getAccount()).multiply(bailPercent).setScale(2, BigDecimal.ROUND_DOWN);
                    borrowBail.setBailNum(accountBail);
                    // 10位系统时间（到秒）
                    borrowBail.setUpdateTime(new Date());
                    boolean bailFlag = this.borrowBailMapper.insertSelective(borrowBail) > 0 ? true : false;
                    if (bailFlag) {
                        borrow.setVerifyStatus(1);
                        boolean borrowFlag = this.borrowMapper.updateByPrimaryKey(borrow) > 0 ? true : false;
                        if (borrowFlag) {
                            return true;
                        }
                    }
                }
        }
        return false;
    }

    /**
     * 获取系统配置
     *
     * @return
     */
    public String getBorrowConfig(String configCd) {
        BorrowConfig borrowConfig = this.borrowConfigMapper.selectByPrimaryKey(configCd);
        return borrowConfig.getConfigValue();
    }
}
