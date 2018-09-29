/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.Impl;

import com.hyjf.am.response.admin.HjhAccountBalanceResponse;
import com.hyjf.am.vo.trade.HjhAccountBalanceVO;
import com.hyjf.cs.common.service.BaseClient;
import com.hyjf.cs.message.mongo.ic.HjhAccountBalanceDao;
import com.hyjf.cs.message.service.BaseMessageServiceImpl;
import com.hyjf.cs.message.service.HjhAccountBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author liubin
 * @version HjhAccountBalanceServiceImpl, v0.1 2018/8/2 11:07
 */
@Service
public class HjhAccountBalanceServiceImpl extends BaseMessageServiceImpl implements HjhAccountBalanceService {
    @Autowired
    private HjhAccountBalanceDao hjhAccountBalanceDao;
    @Autowired
    private BaseClient baseClient;


    /**
     * 获取该期间的汇计划日交易量
     * @param date
     * @return
     */
    @Override
    public List<HjhAccountBalanceVO> getHjhAccountBalanceForActList(Date date) {
        HjhAccountBalanceResponse response = this.baseClient.getExe("http://AM-TRADE/am-trade/hjhAccountBalanceController/getHjhAccountBalanceForActList/" + date
                , HjhAccountBalanceResponse.class);
        return response.getResultList();
    }

    /**
     * 更新记录（汇计划按日对账统计表）
     * @param hjhAccountBalance
     * @return
     */
    @Override
    public Boolean updateAccountBalance(HjhAccountBalanceVO hjhAccountBalance) {
        Boolean result = false;
        // 判断数据是否已存在
        List<HjhAccountBalanceVO> list = this.hjhAccountBalanceDao.selectHjhAccountBalanceList(hjhAccountBalance);
        if (list == null ) {
            throw new RuntimeException("取得汇计划按日对账统计表的数据失败。");
        }
        if (list.size()>0){
            // 存在，更新记录（汇计划按日对账统计表）
            result = this.hjhAccountBalanceDao.updateHjhAccountBalance(hjhAccountBalance);
        }else{
            // 不存在，插入记录（汇计划按日对账统计表）
            result = this.hjhAccountBalanceDao.insertHjhAccountBalance(hjhAccountBalance);
        }
        return result;
    }
}
