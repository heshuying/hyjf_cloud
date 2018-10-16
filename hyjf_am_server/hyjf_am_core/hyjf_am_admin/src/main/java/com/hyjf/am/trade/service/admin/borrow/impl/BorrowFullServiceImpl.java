/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.borrow.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.am.admin.mq.producer.BorrowLoanRepayProducer;
import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.admin.BorrowFullRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.BorrowFullCustomize;
import com.hyjf.am.trade.service.admin.borrow.BorrowFullService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version BorrowFullServiceImpl, v0.1 2018/7/6 15:17
 */
@Service
public class BorrowFullServiceImpl extends BaseServiceImpl implements BorrowFullService {

    @Autowired
    BorrowLoanRepayProducer borrowLoanRepayProducer;

    /**
     * 获取借款复审列表count
     *
     * @param borrowFullRequest
     * @return
     */
    @Override
    public Integer countBorrowFull(BorrowFullRequest borrowFullRequest) {
        return borrowFullCustomizeMapper.countBorrowFull(borrowFullRequest);
    }

    /**
     * 查询借款复审列表
     *
     * @param borrowFullRequest
     * @return
     */
    @Override
    public List<BorrowFullCustomize> selectBorrowFullList(BorrowFullRequest borrowFullRequest) {
        List<BorrowFullCustomize> list = borrowFullCustomizeMapper.selectBorrowFullList(borrowFullRequest);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, String> map = CacheUtil.getParamNameMap("REVERIFY_STATUS");
            list.forEach((borrowFullCustomize) -> borrowFullCustomize.setReverifyStatusName(map.getOrDefault(borrowFullCustomize.getReverifyStatus(), null)));
        }
        return list;
    }

    /**
     * 复审列表合计
     *
     * @param borrowFullRequest
     * @return
     */
    @Override
    public BorrowFullCustomize getSumAccount(BorrowFullRequest borrowFullRequest) {
        return borrowFullCustomizeMapper.sumAccount(borrowFullRequest);
    }

    /**
     * 复审详细信息
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowFullCustomize getFullInfo(String borrowNid) {
        return borrowFullCustomizeMapper.selectFullInfo(borrowNid);
    }

    /**
     * 借款复审详细列表count
     *
     * @param borrowNid
     * @return
     */
    @Override
    public Integer countFullList(String borrowNid) {
        return borrowFullCustomizeMapper.countFullList(borrowNid);
    }

    /**
     * 复审详细信息列表
     *
     * @param borrowFullRequest
     * @return
     */
    @Override
    public List<BorrowFullCustomize> getFullList(BorrowFullRequest borrowFullRequest) {
        List<BorrowFullCustomize> list = borrowFullCustomizeMapper.selectFullList(
                borrowFullRequest.getBorrowNidSrch(), borrowFullRequest.getLimitStart(), borrowFullRequest.getLimitEnd());
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, String> map = CacheUtil.getParamNameMap("CLIENT");
            list.forEach((borrowFullCustomize) -> borrowFullCustomize.setOperatingDeck(map.getOrDefault(borrowFullCustomize.getOperatingDeck(), null)));
        }
        return list;
    }

    /**
     * 复审详细列表合计
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowFullCustomize getSumAmount(String borrowNid) {
        return borrowFullCustomizeMapper.sumAmount(borrowNid);
    }

    /**
     * 复审提交
     *
     * @param borrowFullRequest
     * @return
     */
    @Override
    public Response updateBorrowFull(BorrowFullRequest borrowFullRequest) {
        // 标的编号
        String borrowNid = borrowFullRequest.getBorrowNidSrch();
        // 标的
        Borrow borrow = getBorrow(borrowNid);
        // 借款人userId
        Integer borrowUserId = borrow.getUserId();
        // 借款人用户名
        String borrowUserName = borrow.getBorrowUserName();
        // 借款期数
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
        // 项目类型
        Integer projectType = borrow.getProjectType();
        // 项目还款方式
        String borrowStyle = borrow.getBorrowStyle();
        // 是否月标(true:月标, false:天标)
        boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
        int nowTime = GetDate.getNowTime10();
        String nid = borrow.getBorrowNid() + "_" + borrow.getUserId() + "_1";

        //校验标的是否爆标
        logger.info("==================== 开始校验是否爆标");
        boolean result = this.checkBorrowTenderOverFlow(borrowNid, borrowFullRequest.getAccountId());
        logger.info("校验结果: " + result);
        if (result) {
            return new Response(Response.FAIL,
                    "标的爆标,请进行处理![用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
        }

        // 如果标的投资记录存在没有授权码的记录，则不进行放款
        int countErrorTender = this.countBorrowTenderError(borrowNid);
        if (countErrorTender == 0) {
            BorrowApicronExample example = new BorrowApicronExample();
            BorrowApicronExample.Criteria crt = example.createCriteria();
            crt.andNidEqualTo(nid);
            crt.andApiTypeEqualTo(0);
            List<BorrowApicron> borrowApicrons = borrowApicronMapper.selectByExample(example);
            if (borrowApicrons == null || borrowApicrons.size() == 0) {
                BorrowExample borrowExample = new BorrowExample();
                borrowExample.createCriteria().andIdEqualTo(borrow.getId()).andReverifyStatusEqualTo(borrow.getReverifyStatus());
                // 复审时间
                borrow.setReverifyTime(nowTime);
                // 复审人ID
                borrow.setReverifyUserid(borrowFullRequest.getCurrUserId());
                // 复审用户名
                borrow.setReverifyUserName(borrowFullRequest.getCurrUserName());
                // 复审状态
                borrow.setReverifyStatus(1);
                // 复审状态（复审通过）
                borrow.setStatus(3);
                // 复审备注
                borrow.setReverifyRemark(borrowFullRequest.getReverifyRemark());
                // 借款成功时间 该字段已删除
//                borrow.setBorrowSuccessTime(nowTime);
                // 更新时间
                borrow.setUpdatetime(GetDate.getNowTime());
                boolean borrowUpdateFlag = this.borrowMapper.updateByExampleSelective(borrow, borrowExample) > 0 ? true : false;
                if (borrowUpdateFlag) {
                    // 放款任务表
                    BorrowApicron borrowApicron = new BorrowApicron();
                    // 交易凭证号
                    borrowApicron.setNid(nid);
                    // 用户ID
                    borrowApicron.setUserId(borrow.getUserId());
                    // 用户名
                    borrowApicron.setUserName(borrowUserName);
                    // 借款编号
                    borrowApicron.setBorrowNid(borrow.getBorrowNid());
                    borrowApicron.setBorrowAccount(borrow.getAccount());
                    if (!isMonth) {
                        borrowApicron.setBorrowPeriod(1);
                    } else {
                        borrowApicron.setBorrowPeriod(borrowPeriod);
                    }
                    borrowApicron.setFailTimes(0);
                    // 放款状态
                    borrowApicron.setStatus(1);
                    // 任务类型0放款1还款
                    borrowApicron.setApiType(0);
                    borrowApicron.setWebStatus(0);
                    // 默认值，代表还款期数
                    borrowApicron.setPeriodNow(0);
                    // 债转还款状态
                    borrowApicron.setCreditRepayStatus(0);
                    // 融通宝相关
                    if (projectType == 13) {
                        borrowApicron.setExtraYieldStatus(0);
                        borrowApicron.setExtraYieldRepayStatus(0);
                    } else {
                        borrowApicron.setExtraYieldStatus(1);
                        borrowApicron.setExtraYieldRepayStatus(1);
                    }
                    borrowApicron.setCreateTime(GetDate.getNowTime());
                    borrowApicron.setUpdateTime(GetDate.getNowTime());
                    // add by liushouyi HJH3
                    // 汇计划的复审带上计划编号
                    if (StringUtils.isNotBlank(borrow.getPlanNid())) {
                        borrowApicron.setPlanNid(borrow.getPlanNid());//计划编号
                    }
                    boolean apicronFlag = this.borrowApicronMapper.insertSelective(borrowApicron) > 0 ? true : false;
                    if (apicronFlag) {
                        //2018-10-15 复审之后之后发送MQ进行放款
                        if (StringUtils.isNotBlank(borrow.getPlanNid())) {
                            //计划标的放款
                            try {
                                borrowLoanRepayProducer.messageSend(
                                        new MessageContent(MQConstant.BORROW_REALTIMELOAN_PLAN_REQUEST_TOPIC, borrowApicron.getBorrowNid(), JSON.toJSONBytes(borrowApicron)));
                            } catch (MQException e) {
                                logger.error("[编号：" + borrowNid + "]发送计划放款MQ失败！", e);
                            }
                        } else {
                            //直投标的放款
                            try {
                                borrowLoanRepayProducer.messageSend(
                                        new MessageContent(MQConstant.BORROW_REALTIMELOAN_ZT_REQUEST_TOPIC, borrowApicron.getBorrowNid(), JSON.toJSONBytes(borrowApicron)));
                            } catch (MQException e) {
                                logger.error("[编号：" + borrowNid + "]发送直投放款MQ失败！", e);
                            }
                        }

                        return new Response();
                    } else {
                        return new Response(Response.FAIL,
                                "[编号：" + borrowNid + "]apicron更新失败！");
                    }
                } else {
                    return new Response(Response.FAIL, "[编号：" + borrowNid + "]标的更新失败！");
                }
            } else {
                return new Response(Response.FAIL, "[编号：" + borrowNid + "]apicron无记录！");
            }
        } else {
            return new Response(Response.FAIL,
                    "[编号：" + borrowNid + "]标的记录存在没有授权码的记录，请确认！");
        }
    }

    /**
     * 校验投资数据的合法性
     *
     * @param borrowNid
     * @return
     */
    private int countBorrowTenderError(String borrowNid) {
        BorrowTenderExample example = new BorrowTenderExample();
        BorrowTenderExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowNidEqualTo(borrowNid);
        criteria.andStatusEqualTo(0);
        criteria.andAuthCodeIsNull();
        int count = this.borrowTenderMapper.countByExample(example);
        return count;
    }

    /**
     * 校验投资数据是否爆标
     *
     * @param borrowNid
     * @return true:爆标/异常  false:正常
     */
    private boolean checkBorrowTenderOverFlow(String borrowNid, String accountId) {
        //校验投资金额是否超标
        BorrowTenderExample btexample = new BorrowTenderExample();
        btexample.createCriteria().andBorrowNidEqualTo(borrowNid);
        List<BorrowTender> btList = this.borrowTenderMapper.selectByExample(btexample);
        BigDecimal sumTender = new BigDecimal(0);
        if (btList != null && btList.size() > 0) {
            for (int i = 0; i < btList.size(); i++) {
                sumTender = sumTender.add(btList.get(i).getAccount());
            }
        }
        logger.info("=========标的" + borrowNid + "投资总额:" + sumTender);

        BorrowExample borrowExample = new BorrowExample();
        borrowExample.createCriteria().andBorrowNidEqualTo(borrowNid);
        List<Borrow> borrowList = this.borrowMapper.selectByExample(borrowExample);
        BigDecimal borrowAccount = borrowList.get(0).getAccount();
        logger.info("=========标的" + borrowNid + "总额:" + borrowAccount);
        if (sumTender.compareTo(borrowAccount) > 0) {
            logger.error("=====标的" + borrowNid + "===投资总额超过标的总额!========");
            return true;
        }
        //校验投资掉单造成的爆标
        BorrowTenderTmpExample example = new BorrowTenderTmpExample();
        example.createCriteria().andIsBankTenderEqualTo(1).andBorrowNidEqualTo(borrowNid);
        List<BorrowTenderTmp> borrowtmpList = borrowTenderTmpMapper.selectByExample(example);
        int index = 0;
        //复审时存在全部掉单的投资数据,可能导致爆标的出现
        if (borrowtmpList != null && borrowtmpList.size() > 0) {
            for (int i = 0; i < borrowtmpList.size(); i++) {
                BorrowTenderTmp info = borrowtmpList.get(i);
                BankCallBean callBean = this.queryBorrowTenderList(accountId, info.getNid(), info.getUserId() + "");
                if (callBean != null && BankCallStatusConstant.RESPCODE_SUCCESS.equals(callBean.getRetCode())
                        && org.apache.commons.lang3.StringUtils.isNoneBlank(callBean.getAuthCode())) {
                    logger.error("==========存在全部掉单的投资数据,可能导致爆标,投资订单号:" + info.getNid());
                    tenderCancel(info, accountId);
                    index++;
                }
            }
            if (index == 0) {
                return false;
            } else {
                return true;
            }

        } else {
            return false;
        }
    }

    /**
     * 根据相应信息接口查询投标申请
     *
     * @param accountId
     * @param orgOrderId
     * @return
     */
    private BankCallBean queryBorrowTenderList(String accountId, String orgOrderId, String userId) {
        BankCallBean bean = new BankCallBean();
        //调用银行接口部分共同参数已删除

        //交易代码
        bean.setTxCode(BankCallConstant.TXCODE_BID_APPLY_QUERY);
        //用户ID
        bean.setLogUserId(userId);
        //电子账号
        bean.setAccountId(accountId);
        bean.setOrgOrderId(orgOrderId);
        bean.setLogOrderId(GetOrderIdUtils.getUsrId(Integer.parseInt(userId)));
        // 调用接口
        return BankCallUtils.callApiBg(bean);
    }

    /**
     * 投资撤销
     *
     * @param info
     * @param accountID
     */
    private void tenderCancel(BorrowTenderTmp info, String accountID) {
        String nid = info.getNid();
        BankCallBean callBean = bidCancel(info.getUserId(), accountID, info.getBorrowNid(), nid, info.getAccount().toString());
        if (Validator.isNotNull(callBean)) {
            String retCode = org.apache.commons.lang3.StringUtils.isNotBlank(callBean.getRetCode()) ? callBean.getRetCode() : "";
            //投资正常撤销或投资订单不存在则删除冗余数据
            if (retCode.equals(BankCallConstant.RESPCODE_SUCCESS) || retCode.equals(BankCallConstant.RETCODE_BIDAPPLY_NOT_EXIST1)
                    || retCode.equals(BankCallConstant.RETCODE_BIDAPPLY_NOT_EXIST2) || retCode.equals(BankCallConstant.RETCODE_BIDAPPLY_NOT_RIGHT)) {
                try {
                    boolean flag = updateBidCancelRecord(info);
                    if (flag) {
                        logger.info("===============投资掉单数据已撤销,原投资订单号:" + nid);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("投资撤销数据处理异常!原订单号:" + nid + "异常原因:" + e.getMessage());
                }
            } else {
                throw new RuntimeException("投资撤销接口返回错误!原订单号:" + nid + ",返回码:" + retCode);
            }
        } else {
            throw new RuntimeException("投资撤销接口异常!");
        }
    }

    /**
     * 投资撤销历史数据处理
     *
     * @param tenderTmp
     * @return
     * @throws Exception
     */
    private boolean updateBidCancelRecord(BorrowTenderTmp tenderTmp) throws Exception {
        Integer userId = tenderTmp.getUserId();
        boolean tenderTmpFlag = this.borrowTenderTmpMapper.deleteByPrimaryKey(tenderTmp.getId()) > 0 ? true : false;
        if (!tenderTmpFlag) {
            throw new Exception("删除投资日志表失败，投资订单号：" + tenderTmp.getNid());
        }
        FreezeHistory freezeHistory = new FreezeHistory();
        freezeHistory.setTrxId(tenderTmp.getNid());
        freezeHistory.setNotes("自动任务银行投资撤销");
        freezeHistory.setFreezeUser(this.getUserNameByUserId(userId));
        freezeHistory.setFreezeTime(GetDate.getNowTime10());
        boolean freezeHisLog = freezeHistoryMapper.insert(freezeHistory) > 0 ? true : false;
        if (!freezeHisLog) {
            throw new Exception("插入投资删除日志表失败，投资订单号：" + tenderTmp.getNid());
        }
        return true;
    }

    /**
     * 银行投资撤销
     *
     * @param userId
     * @param accountId
     * @param productId
     * @param orgOrderId
     * @param txAmount
     * @return
     */
    public BankCallBean bidCancel(Integer userId, String accountId, String productId, String orgOrderId, String txAmount) {
        // 标的投资撤销
        BankCallBean bean = new BankCallBean();
        // 部分共同参数删除

        // 交易代码
        String orderId = GetOrderIdUtils.getOrderId2(userId);
        bean.setTxCode(BankCallMethodConstant.TXCODE_BID_CANCEL);
        // 电子账号
        bean.setAccountId(accountId);
        // 订单号(必须)
        bean.setOrderId(orderId);
        // 交易金额
        bean.setTxAmount(CustomUtil.formatAmount(txAmount));
        // 标的号
        bean.setProductId(productId);
        // 原标的订单号
        bean.setOrgOrderId(orgOrderId);
        // 订单号
        bean.setLogOrderId(orderId);
        // 订单日期
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        // 用户Id
        bean.setLogUserId(String.valueOf(userId));
        // 用户名
        bean.setLogUserName(this.getUserNameByUserId(userId));
        // 备注
        bean.setLogRemark("投标申请撤销");
        // 调用银行接口
        BankCallBean result = BankCallUtils.callApiBg(bean);
        return result;
    }

    /**
     * 根据用户ID获取用户姓名
     *
     * @param userId
     * @return
     */
    private String getUserNameByUserId(Integer userId) {
        if (userId != null) {
            return borrowFullCustomizeMapper.getUserNameByUserId(userId);
        }
        return null;
    }

    /**
     * 流标
     *
     * @param borrowFullRequest
     * @return
     */
    @Override
    public Response updateBorrowOver(BorrowFullRequest borrowFullRequest) {
        Date systemNowDate = new Date();
        String borrowNid = borrowFullRequest.getBorrowNidSrch();
        if (StringUtils.isNotEmpty(borrowNid)) {
            BorrowExample borrowExample = new BorrowExample();
            BorrowExample.Criteria borrowCra = borrowExample.createCriteria();
            borrowCra.andBorrowNidEqualTo(borrowNid);

            List<Borrow> borrowList = this.borrowMapper.selectByExample(borrowExample);

            if (borrowList != null && borrowList.size() == 1) {
                Borrow borrow = borrowList.get(0);
                // 复审人ID
                borrow.setReverifyUserid(borrowFullRequest.getCurrUserId());
                // 复审状态（流标）
                borrow.setStatus(6);
                // 复审备注
                borrow.setReverifyRemark(GetDate.getServerDateTime(6, new Date()) + " " + borrowFullRequest.getCurrUserId() + " 复审流标");
                // 更新时间
                borrow.setUpdatetime(systemNowDate);
                //更新标的信息
                int updateResult = this.borrowMapper.updateByExampleSelective(borrow, borrowExample);
                if (updateResult > 0) {
                    return new Response();
                } else {
                    return new Response(Response.FAIL, "[编号：" + borrowNid + "]标的更新失败！");
                }
            } else {
                return new Response(Response.FAIL, "未查询到标的信息！");
            }
        } else {
            return new Response(Response.FAIL, "标的编号为空！");
        }
    }
}
