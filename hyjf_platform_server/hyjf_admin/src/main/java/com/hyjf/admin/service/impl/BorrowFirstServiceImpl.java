/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.response.BorrowBailInfoResponseBean;
import com.hyjf.admin.beans.response.BorrowFireInfoResponseBean;
import com.hyjf.admin.beans.response.BorrowFirstResponseBean;
import com.hyjf.admin.beans.vo.AdminBorrowFirstCustomizeVO;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.mq.AutoIssueMessageProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.BorrowFirstService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.resquest.admin.BorrowFireRequest;
import com.hyjf.am.resquest.admin.BorrowFirstRequest;
import com.hyjf.am.vo.admin.BorrowFirstCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowConfigVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author wangjun
 * @version BorrowFirstServiceImpl, v0.1 2018/7/3 15:11
 */
@Service
public class BorrowFirstServiceImpl implements BorrowFirstService {
    private static Logger logger = LoggerFactory.getLogger(BorrowFirstServiceImpl.class);

    private static final String OK = "OK";
    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    AmUserClient amUserClient;

    @Autowired
    AutoIssueMessageProducer autoIssueMessageProducer;

    /**
     * 借款初审列表
     *
     * @param borrowFirstRequest
     * @return
     */
    @Override
    public BorrowFirstResponseBean getBorrowFirstList(BorrowFirstRequest borrowFirstRequest) {
        BorrowFirstResponseBean borrowFirstResponseBean = new BorrowFirstResponseBean();
        //查询总条数
        Integer count = amTradeClient.countBorrowFirst(borrowFirstRequest);
        borrowFirstResponseBean.setTotal(count);
        //分页参数
        Page page = Page.initPage(borrowFirstRequest.getCurrPage(), borrowFirstRequest.getPageSize());
        page.setTotal(count);
        borrowFirstRequest.setLimitStart(page.getOffset());
        borrowFirstRequest.setLimitEnd(page.getLimit());
        //查询列表 总计
        if (count > 0) {
            List<BorrowFirstCustomizeVO> list = amTradeClient.selectBorrowFirstList(borrowFirstRequest);
            List<AdminBorrowFirstCustomizeVO> adminList = CommonUtils.convertBeanList(list, AdminBorrowFirstCustomizeVO.class);
            String sumAccount = amTradeClient.sumBorrowFirstAccount(borrowFirstRequest);
            borrowFirstResponseBean.setRecordList(adminList);
            borrowFirstResponseBean.setSumAccount(sumAccount);
        }
        return borrowFirstResponseBean;
    }

    /**
     * 已交保证金详细画面信息
     *
     * @return
     */
    @Override
    public AdminResult getBailInfo(String borrowNid) {
        if (StringUtils.isBlank(borrowNid)) {
            return new AdminResult(BaseResult.FAIL, "标的编号为空！");
        } else {
            BorrowVO borrowVO = amTradeClient.selectBorrowByNid(borrowNid);
            BorrowInfoVO borrowInfoVO = amTradeClient.selectBorrowInfoByNid(borrowNid);
            if (borrowVO == null || borrowInfoVO == null) {
                return new AdminResult(BaseResult.FAIL, "未查询到标的信息！");
            }
            BorrowBailInfoResponseBean borrowBailInfoResponseBean = new BorrowBailInfoResponseBean();
            BeanUtils.copyProperties(borrowVO, borrowBailInfoResponseBean);
            borrowBailInfoResponseBean.setName(borrowInfoVO.getName());
            UserVO userVO = amUserClient.findUserById(borrowVO.getUserId());
            if(userVO != null){
                borrowBailInfoResponseBean.setUserName(userVO.getUsername());
            }
            BorrowConfigVO borrowConfigVO = amTradeClient.getBorrowConfig(CustomConstants.BORROW_BAIL_RATE);
            // 计算公式：保证金金额=借款金额×3％
            BigDecimal bailPercent = new BigDecimal(borrowConfigVO.getConfigValue());
            double accountBail = (borrowVO.getAccount().multiply(bailPercent)).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
            borrowBailInfoResponseBean.setAccountBail(accountBail);
            return new AdminResult(borrowBailInfoResponseBean);
        }
    }

    /**
     * 交保证金
     *
     * @param borrowNid
     * @return
     */
    @Override
    public AdminResult insertBorrowBail(String borrowNid, String currUserId) {
        if (StringUtils.isNotBlank(borrowNid)) {
            // 交保证金
            boolean borrowBailFlag = amTradeClient.insertBorrowBail(borrowNid, currUserId);
            if (borrowBailFlag) {
                return new AdminResult();
            } else {
                return new AdminResult(BaseResult.FAIL, "交保证金失败！");
            }
        } else {
            return new AdminResult(BaseResult.FAIL, "标的编号为空！");
        }
    }

    /**
     * 获取发标信息
     *
     * @param borrowNid
     * @return
     */
    @Override
    public AdminResult getFireInfo(String borrowNid) {
        if (StringUtils.isNotBlank(borrowNid)) {
            // 0 未使用引擎 ; 1使用引擎 前台已经注释 暂时删除
//            int engineFlag = this.borrowFirstClient.isEngineUsed(borrowNid);
//            jsonObject.put("engineFlag", engineFlag);

            BorrowVO borrowVO = amTradeClient.selectBorrowByNid(borrowNid);
            BorrowInfoVO borrowInfoVO = amTradeClient.selectBorrowInfoByNid(borrowNid);
            if (borrowVO != null && borrowInfoVO != null) {
                BorrowFireInfoResponseBean response = new BorrowFireInfoResponseBean();
                BeanUtils.copyProperties(borrowVO, response);
                response.setName(borrowInfoVO.getName());
                response.setCreateTime(GetDate.date2Str(borrowVO.getCreateTime(), GetDate.datetimeFormat));

                if (borrowVO.getOntime() != null && borrowVO.getOntime() != 0) {
                    response.setOntime(GetDate.timestamptoStrYYYYMMDDHHMM(String.valueOf(borrowVO.getOntime())));
                }

                //前台页面未使用
//                boolean borrowBailFlag = this.borrowFirstClient.getBorrowBail(borrowNid);
//                jsonObject.put("borrowBailFlag", borrowBailFlag);

                return new AdminResult(response);
            } else {
                return new AdminResult(BaseResult.FAIL, "未查询到标的信息！");
            }
        } else {
            return new AdminResult(BaseResult.FAIL, "标的编号为空！");
        }
    }

    /**
     * 发标
     *
     * @param borrowNid
     * @return
     */
    @Override
    public AdminResult updateBorrowFireInfo(String borrowNid, String verifyStatus, String ontime) {
        //项目验证
        String checkResult = this.checkItems(borrowNid, verifyStatus, ontime);
        if (!OK.equals(checkResult)) {
            return new AdminResult(BaseResult.FAIL, checkResult);
        }

        //获取标的信息
        BorrowVO borrowVO = amTradeClient.selectBorrowByNid(borrowNid);
        BorrowInfoVO borrowInfoVO = amTradeClient.selectBorrowInfoByNid(borrowNid);
        if (borrowVO == null || borrowInfoVO == null) {
            return new AdminResult(BaseResult.FAIL, "未查询到标的信息");
        }
        //借款有效时间
        borrowVO.setBorrowValidTime(borrowInfoVO.getBorrowValidTime());

        BorrowFireRequest borrowFireRequest = new BorrowFireRequest();
        borrowFireRequest.setBorrowNid(borrowNid);
        borrowFireRequest.setVerifyStatus(verifyStatus);
        borrowFireRequest.setOntime(ontime);

        boolean updateFlag = amTradeClient.updateOntimeRecord(borrowFireRequest);
        if(!updateFlag){
            return new AdminResult(BaseResult.FAIL,"数据更新失败");
        } else {
            if (borrowVO.getIsEngineUsed().equals(1) && verifyStatus.equals("4")) {
                // 成功后到关联计划队列
                JSONObject params = new JSONObject();
                params.put("borrowNid", borrowVO.getBorrowNid());
                try {
                    //自动关联计划
                    autoIssueMessageProducer.messageSend(new MessageContent(MQConstant.ROCKETMQ_BORROW_ISSUE_TOPIC, UUID.randomUUID().toString(), JSONObject.toJSONBytes(params)));
                    logger.info("标的编号：" + borrowNid + "-----已发送至自动关联计划MQ");
                } catch (Exception e){
                    logger.error("标的编号：" + borrowNid + "-----发送自动关联计划MQ异常", e);
                }
            }
        }

        return new AdminResult();
    }

    private String checkItems(String borrowNid, String verifyStatus, String ontime) {
        //检查标的编号
        if (StringUtils.isBlank(borrowNid)) {
            return "标的编号为空！";
        }
        //检查发标状态
        if (StringUtils.isBlank(verifyStatus)) {
            return "发标状态为空！";
        }
        //定时发标(verifyStatus :3) 检查定时发标时间
        if (StringUtils.equals(verifyStatus, "3")) {
            if (StringUtils.isBlank(ontime)) {
                return "定时发标时间为空！";
            }
            try {
                if (ontime.length() != 16) {
                    return "定时发标时间位数不正确！";
                } else {
                    SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    fm.setLenient(false);
                    fm.parse(ontime);
                }
            } catch (Exception e) {
                return "定时发标时间格式不正确！";
            }

            String systeDate = GetDate.getServerDateTime(14, new Date());
            if (systeDate.compareTo(ontime) >= 0) {
                return "定时发标时间不正确！";
            }
        }
        return OK;
    }
}
