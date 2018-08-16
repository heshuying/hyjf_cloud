package com.hyjf.am.trade.service.task.bank.autoreview.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.mapper.customize.BorrowCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.producer.SmsProducer;
import com.hyjf.am.trade.service.task.bank.autoreview.BatchAutoReviewHjhService;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.task.issuerecover.BorrowWithBLOBs;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/13 14:12
 * @Description: BatchAutoReviewHjhServiceImpl
 */
@Service
public class BatchAutoReviewHjhServiceImpl implements BatchAutoReviewHjhService {
    private static final Logger logger = LoggerFactory.getLogger(BatchAutoReviewHjhServiceImpl.class);
    @Resource
    private BorrowCustomizeMapper borrowCustomizeMapper;
    @Resource
    private SmsProducer smsProducer;
    @Resource
    private BorrowMapper borrowMapper;
    @Resource
    private BorrowInfoMapper borrowInfoMapper;
    @Resource
    private AccountMapper accountMapper;
    @Resource
    private BorrowApicronMapper borrowApicronMapper;
    @Resource
    private BorrowTenderMapper borrowTenderMapper;

    @Override
    public void sendMsgToNotFullBorrow() {
        // 查询符合条件的Borrow
        List<BorrowCommonCustomizeVO> borrowList = borrowCustomizeMapper.searchHjhNotFullBorrowMsg();
        if (borrowList != null && borrowList.size() > 0) {
            // 发送短信
            for (int i = 0; i < borrowList.size(); i++) {
                    // 替换参数
                    Map<String, String> messageStrMap = new HashMap<String, String>();
                    messageStrMap.put("val_borrowid", borrowList.get(i).getBorrowNid());
                    messageStrMap.put("val_date", borrowList.get(i).getTimeStartSrch());
                    // 发送短信
                    SmsMessage smsMessage = new SmsMessage(null, messageStrMap, null, null, MessageConstant.SMS_SEND_FOR_MANAGER, borrowList.get(i).getBorrowNid(), CustomConstants.PARAM_TPL_XMDQ,
                            CustomConstants.CHANNEL_TYPE_NORMAL);
                    try {
                        smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(smsMessage)));
                    } catch (MQException e2) {
                        logger.error("发送短信失败..", e2);
                    }
            }
        }
    }

    @Override
    public List<BorrowWithBLOBs> selectAutoReview() {
        /*------------upd by liushouyi HJH3 Start-------------------*/
        //使用引擎：计划
        List<BorrowWithBLOBs> borrowList = borrowCustomizeMapper.selectAutoReviewHJHBorrowNidList();
        /*------------upd by liushouyi HJH3 End-------------------*/
        return borrowList;
    }

    @Override
    public void updateBorrow(BorrowWithBLOBs borrows) throws Exception {
        /*--------------upd by liushouyi HJH3 Start----------------*/
        //遍历自动发标的数据原抽出表中所有字段、表关联后仅查借款编号
        Borrow borrow = this.getBorrowByNid(borrows.getBorrowNid());
        BorrowInfo borrowInfo = borrowInfoMapper.selectByPrimaryKey(borrow.getId());
        /*--------------upd by liushouyi HJH3 Start----------------*/
        // 获取当前时间
        int nowTime = GetDate.getNowTime10();
        String borrowNid = borrow.getBorrowNid();// 項目编号
        int borrowUserId = borrow.getUserId();// 借款人userId
        String borrowUserName = borrow.getBorrowUserName();// 借款人用户名
        String borrowStyle = borrow.getBorrowStyle();// 项目还款方式
        /** 标的基本数据 */
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();// 借款期数
        // 项目类型
        Integer projectType = borrowInfo.getProjectType();
        // 是否月标(true:月标, false:天标)
        boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);

        // 获取标的费率信息  后续放开
//		String borrowClass = this.getBorrowProjectClass(borrow.getProjectType()+ "");
//		BorrowFinmanNewCharge borrowFinmanNewCharge = this.selectBorrowApr(borrowClass,borrow.getInstCode(),borrow.getAssetType(),borrow.getBorrowStyle(),borrow.getBorrowPeriod());
//		if(borrowFinmanNewCharge == null || borrowFinmanNewCharge.getChargeMode() == null){
//			_log.info("获取标的费率信息失败。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]"+borrowClass);
//			throw new RuntimeException("获取标的费率信息失败。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
//		}

        // 取得借款人账户信息
        Account borrowAccount = this.getAccountByUserId(borrowUserId);
        if (borrowAccount == null) {
            throw new RuntimeException("借款人账户不存在。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
        }
        // 借款人在汇付的账户信息
        if (borrowAccount.getAccountId() == null) {
            throw new RuntimeException("借款人未开户。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
        }
        String nid = borrow.getBorrowNid() + "_" + borrow.getUserId() + "_1";
        BorrowApicronExample example = new BorrowApicronExample();
        BorrowApicronExample.Criteria crt = example.createCriteria();
        crt.andNidEqualTo(nid);
        crt.andApiTypeEqualTo(0);
        List<BorrowApicron> borrowApicrons = borrowApicronMapper.selectByExample(example);
        if (borrowApicrons == null || borrowApicrons.size() == 0) {
            // 满标审核状态
            int borrowFullStatus = borrow.getBorrowFullStatus();
            if (borrowFullStatus == 1) {
                // 如果标的投资记录存在没有授权码的记录，则不进行放款
                int countErrorTender = this.countBorrowTenderError(borrowNid);
                if (countErrorTender == 0) {
                    // 判断满标时间
                    if (borrow.getBorrowFullTime() != null && borrow.getBorrowFullTime() != 0) {
                        // 满标时间判断
                        BorrowExample borrowExample = new BorrowExample();
                        borrowExample.createCriteria().andIdEqualTo(borrow.getId()).andReverifyStatusEqualTo(borrow.getReverifyStatus());
                        // 更新huiyingdai_borrow的如下字段：
                        borrow.setReverifyTime(nowTime);// 复审时间
                        borrow.setReverifyUserid("Auto"); // 复审人userId
                        borrow.setReverifyUserName("Auto");// 复审人用戶名
                        borrow.setReverifyStatus(1);// 复审状态
                        borrow.setStatus(3);// 复审状态（复审通过）
                        borrow.setReverifyRemark("系统自动复审通过");// 更新Remark
                        borrow.setUpdatetime(new Date());// 更新时间
                        boolean borrowUpdateFlag = this.borrowMapper.updateByExampleSelective(borrow, borrowExample)> 0 ? true : false;
                        if (borrowUpdateFlag) {
                            // 放款任务表
                            BorrowApicron borrowApicron = new BorrowApicron();
                            borrowApicron.setNid(nid);// 交易凭证号 生成规则：BorrowNid_userid_期数
                            borrowApicron.setUserId(borrowUserId); // 借款人编号
                            borrowApicron.setUserName(borrowUserName);// 用户名
                            borrowApicron.setBorrowNid(borrow.getBorrowNid()); // 借款编号
                            borrowApicron.setBorrowAccount(borrow.getAccount());
                            if(!isMonth){
                                borrowApicron.setBorrowPeriod(1);
                            }else{
                                borrowApicron.setBorrowPeriod(borrowPeriod);
                            }
                            borrowApicron.setFailTimes(0);
                            borrowApicron.setStatus(1);// 放款任务状态 0初始
                            borrowApicron.setApiType(0);// 任务类型放款
                            borrowApicron.setPeriodNow(0);// 放款期数
                            borrowApicron.setCreditRepayStatus(0);// 债转还款状态
                            if(projectType==13){
                                borrowApicron.setExtraYieldStatus(0);// 融通宝加息相关的放款状态
                                borrowApicron.setExtraYieldRepayStatus(0);// 融通宝相关的加息还款状态
                            }else{
                                borrowApicron.setExtraYieldStatus(1);// 融通宝加息相关的放款状态
                                borrowApicron.setExtraYieldRepayStatus(1);// 融通宝相关的加息还款状态
                            }
                            borrowApicron.setCreateTime(new Date());// 创建时间
                            borrowApicron.setUpdateTime(new Date());// 更新时间
                            borrowApicron.setPlanNid(borrow.getPlanNid());//计划编号
                            boolean apicronFlag = this.borrowApicronMapper.insertSelective(borrowApicron) > 0 ? true : false;
                            if (!apicronFlag) {
                                throw new Exception("更新borrow表失败,项目编号：" + borrow.getBorrowNid());
                            }

                        } else {
                            throw new Exception("更新borrow表失败,项目编号：" + borrow.getBorrowNid());
                        }
                    } else {
                        throw new Exception("borrow的borrowFullTime字段为空");
                    }
                }
            }
        }
    }

    /**
     * 校验投资数据的合法性
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

    public Borrow getBorrowByNid(String borrowNid) {
        BorrowExample example = new BorrowExample();
        BorrowExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowNidEqualTo(borrowNid);
        List<Borrow> list = borrowMapper.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    private Account getAccountByUserId(int userId) {
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andUserIdEqualTo(userId);
        List<Account> listAccount = this.accountMapper.selectByExample(accountExample);
        if (listAccount != null && listAccount.size() > 0) {
            return listAccount.get(0);
        }
        return null;
    }
}
