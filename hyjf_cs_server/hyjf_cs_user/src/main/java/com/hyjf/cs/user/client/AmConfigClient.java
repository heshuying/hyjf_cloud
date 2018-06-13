package com.hyjf.cs.user.client;

import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.config.SmsConfigVO;

/**
 * @author xiasq
 * @version AmConfigClient, v0.1 2018/4/23 20:00
 */
public interface AmConfigClient {
    SmsConfigVO findSmsConfig();

    /**
     * @Description 根据银行卡号查询bankId
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/5 14:57
     */
    String getBankIdByCardNo(String cardNo);

    /**
     * @Description 根据bankId查询所属银行
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/5 15:13
     */
    BanksConfigVO getBankNameByBankId(String bankId);
}
