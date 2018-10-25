package com.hyjf.cs.trade.service.synbalance.impl;

import com.hyjf.am.resquest.admin.UnderLineRechargeRequest;
import com.hyjf.am.resquest.trade.SynBalanceBeanRequest;
import com.hyjf.am.vo.admin.UnderLineRechargeVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.SynBalanceVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.synbalance.SynBalanceService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pangchengchao
 * @version SynBalanceServiceImpl, v0.1 2018/6/19 18:05
 */
@Service
public class SynBalanceServiceImpl extends BaseTradeServiceImpl implements SynBalanceService {
    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    AmUserClient amUserClient;


    /**
     * @Description 获取用户开户信息
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public BankOpenAccountVO getBankOpenAccount(String accountId) {
        return amUserClient.selectBankOpenAccountByAccountId(accountId);
    }
    /**
     * @Description 获取用户信息
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public UserVO getUsers(Integer userId) {
        return amUserClient.findUserById(userId);
    }
    /**
     * @Description 根据用户id获取账户信息
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public AccountVO getAccount(Integer userId) {
        return amUserClient.getAccount(userId);
    }
    /**
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public BankCallBean queryAccountDetails(Integer userId, String accountId, String startDate, String endDate, String type, String transType, String pageNum, String pageSize, String inpDate, String inpTime, String relDate, String traceNo) {
        // 参数不正确
        if (StringUtils.isEmpty(accountId) || StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate) || StringUtils.isEmpty(type)) {
            return null;
        }
        BankCallBean bean = new BankCallBean();
        // 接口版本号
        bean.setVersion(BankCallConstant.VERSION_10);
        // 消息类型
        bean.setTxCode(BankCallConstant.TXCODE_ACCOUNT_DETAILS_QUERY);
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        // 电子账号
        bean.setAccountId(accountId);
        // 起始日期
        bean.setStartDate(startDate);
        // 结束日期
        bean.setEndDate(endDate);
        // 交易种类 0-所有交易 1-转入交易 2-转出交易 9-指定交易类型
        bean.setType(type);
        if ("9".equals(type)) {
            // 交易类型
            bean.setTranType(transType);
        }
        // 翻页标识  空：首次查询；1：翻页查询；
        if (StringUtils.isNotEmpty(pageNum)&&!"1".equals(pageNum)) {
            bean.setRtnInd("1");
        } else {
            bean.setRtnInd("");
        }
        bean.setInpDate(inpDate);
        bean.setInpTime(inpTime);
        bean.setRelDate(relDate);
        bean.setTraceNo(traceNo);
        // 操作者ID
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        // 订单时间
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        bean.setLogRemark("资金交易明细查询");
        bean.setLogUserId(String.valueOf(userId));
        // 调用接口
        return BankCallUtils.callApiBg(bean);
    }
    /**
     * @Description 获取银行错误返回码
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public String getBankRetMsg(String retCode) {
        //如果错误码不为空
        if (StringUtils.isNotBlank(retCode)) {
            BankReturnCodeConfigVO retCodes =amUserClient.getBankReturnCodeConfig(retCode);
            if (retCodes != null) {
                String retMsg = retCodes.getErrorMsg();
                if (StringUtils.isNotBlank(retMsg)) {
                    return retMsg;
                } else {
                    return "请联系客服！";
                }
            } else {
                return "请联系客服！";
            }
        } else {
            return "操作失败！";
        }
    }
    /**
     * @Description 插入用户线下充值信息
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public boolean insertAccountDetails(AccountVO accountUser, SynBalanceVO synBalanceBean, String username, String ipAddr) {
        SynBalanceBeanRequest synBalanceBeanRequest=new SynBalanceBeanRequest();
        synBalanceBeanRequest.setAccountUser(accountUser);
        synBalanceBeanRequest.setSynBalanceBean(synBalanceBean);
        synBalanceBeanRequest.setUsername(username);
        synBalanceBeanRequest.setIpAddr(ipAddr);

        return  amTradeClient.insertAccountDetails(synBalanceBeanRequest);


    }

    /**
     * 获取数据表中线下充值类型
     * @return
     * @Author : huanghui
     */
    @Override
    public List<UnderLineRechargeVO> selectUnderLineRechargeList(UnderLineRechargeRequest request){
        return amTradeClient.selectUnderLineRechargeList(request);
    }
}
