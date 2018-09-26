/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.borrow.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.assetpush.InfoBean;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectRepayVO;
import com.hyjf.am.vo.trade.hjh.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.trade.hjh.HjhLabelVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.IdCard15To18;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.assetpush.PushBean;
import com.hyjf.cs.trade.bean.assetpush.PushRequestBean;
import com.hyjf.cs.trade.bean.assetpush.PushResultBean;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.mq.producer.AutoSendProducer;
import com.hyjf.cs.trade.service.borrow.ApiAssetPushService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.util.ErrorCodeConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author fuqiang
 * @version ApiAssetPushServcieImpl, v0.1 2018/6/11 18:06
 */
@Service
public class ApiAssetPushServcieImpl extends BaseTradeServiceImpl implements ApiAssetPushService {

    private Logger logger = LoggerFactory.getLogger(ApiAssetPushServcieImpl.class);

    private static final Long MAX_ASSET_MONEY = 1000000L;

    @Autowired
    private AmTradeClient autoSendClient;

    @Autowired
    private AutoSendProducer autoSendProducer;

    @Override
    public void sendToMQ(HjhPlanAssetVO hjhPlanAsset) {
        JSONObject params = new JSONObject();
        params.put("mqMsgId", GetCode.getRandomCode(10));
        params.put("assetId", hjhPlanAsset.getAssetId());
        params.put("instCode", hjhPlanAsset.getInstCode());
        try {
            autoSendProducer.messageSend(new MessageContent(MQConstant.HJH_AUTO_ISSUERECOVER_TOPIC, UUID.randomUUID().toString(), JSONObject.toJSONBytes(params)));
        } catch (MQException e) {
            logger.error("自动录标发送消息失败...", e);
        }
    }

    @Override
    public PushResultBean assetPush(PushRequestBean pushRequestBean) {

        PushResultBean resultBean = new PushResultBean();

        // 查看机构表是否存在
        HjhAssetBorrowTypeVO assetBorrow = amTradeClient.selectAssetBorrowType(pushRequestBean.getInstCode(), pushRequestBean.getAssetType());
        if (assetBorrow == null) {
            logger.info(pushRequestBean.getInstCode() + "  " + pushRequestBean.getAssetType() + " ------机构编号不存在");
            return resultBean;
        }

        // 获取还款方式,项目类型
        List<BorrowProjectRepayVO> projectRepays = amTradeClient.selectProjectRepay(assetBorrow.getBorrowCd() + "");

        // 检查请求资产总参数
        List<PushBean> assets = pushRequestBean.getReqData();
        try {
            if (assets == null || assets.size() == 0) {
                return resultBean;
            }
            if (assets.size() > 1000) {
                resultBean.setStatusDesc("请求参数过长");
                logger.error("------请求参数过长-------");
                return resultBean;
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return resultBean;
        }

        // 返回结果
        List<PushBean> retassets = new ArrayList<>();

        for (PushBean pushBean : assets) {

            try {

                String truename = pushBean.getTruename();
                String idcard = pushBean.getIdcard();

                // 返回结果，下面的修改应该会返回到这里
                retassets.add(pushBean);

                // 金额不是100以及100的整数倍时将通过接口拒绝接收资产
                if (pushBean.getAccount() == null || (pushBean.getAccount() % 10) != 0 || pushBean.getBorrowPeriod() == null
                        || StringUtils.isBlank(truename) || StringUtils.isBlank(idcard) || StringUtils.isBlank(pushBean.getBorrowStyle())) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000007);
                    pushBean.setRetMsg("资产信息不正确");
                    continue;
                }
                if (pushBean.getWorkCity() != null && pushBean.getWorkCity().length() > 20) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000007);
                    pushBean.setRetMsg("资产信息不正确(工作城市过长)");
                    continue;
                }
                if (pushBean.getAccount() > MAX_ASSET_MONEY) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000006);
                    pushBean.setRetMsg("资产金额超过一百万");
                    continue;
                }
                //查询用户信息（user表）
                UserInfoVO userInfo = amUserClient.selectUserInfoByNameAndCard(truename, idcard);
                if (userInfo == null) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000001);
                    pushBean.setRetMsg("没有用户信息");
                    continue;
                } else if (userInfo.getRoleId() != 2) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000003);
                    pushBean.setRetMsg("用户不是借款人");
                    continue;
                }
                BankOpenAccountVO bankOpenAccount = amUserClient.selectBankAccountById(userInfo.getUserId());
                if (bankOpenAccount == null) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000002);
                    pushBean.setRetMsg("没有用户开户信息");
                    continue;
                }
                //查询用户信息（userinfo表）
                UserVO users = amUserClient.selectUsersById(userInfo.getUserId());
                if (users == null) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000001);
                    pushBean.setRetMsg("没有用户信息");
                    continue;
                }

                //校验还款方式
                if (!checkIsMonthStyle(pushBean.getBorrowStyle(), pushBean.getIsMonth()) || !checkBorrowStyle(projectRepays, pushBean.getBorrowStyle())) {
                    logger.info(pushRequestBean.getInstCode() + " 还款方式不正确 " + projectRepays);
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000005);
                    pushBean.setRetMsg("不支持这种还款方式");
                    continue;
                }

                // 受托支付
                STZHWhiteListVO stzAccount = null;
                if (pushBean.getEntrustedFlg() != null && pushBean.getEntrustedFlg().intValue() == 1) {
                    // 校验
                    if (StringUtils.isBlank(pushBean.getEntrustedAccountId())) {
                        pushBean.setRetCode("ZT000011");
                        pushBean.setRetMsg("受托支付电子账户为空");
                        continue;
                    }
                    //查询用户受托支付电子账户是否授权
                    stzAccount = amTradeClient.selectStzfWhiteList(pushRequestBean.getInstCode(), pushBean.getEntrustedAccountId());

                    if (stzAccount == null) {
                        pushBean.setRetCode("ZT000012");
                        pushBean.setRetMsg("受托支付电子账户未授权");
                        continue;
                    }

                }

                // 信批需求(资产只有个人,若第三方不传则为默认值插入资产表中) start
                // 年收入
                if (StringUtils.isBlank(pushBean.getAnnualIncome())) {
                    pushBean.setAnnualIncome("10万以内");
                }
                // 征信报告逾期情况
                if (StringUtils.isBlank(pushBean.getOverdueReport())) {
                    pushBean.setOverdueReport("暂无数据");
                }
                // 重大负债状况
                if (StringUtils.isBlank(pushBean.getDebtSituation())) {
                    pushBean.setDebtSituation("无");
                }
                // 其他平台借款情况
                if (StringUtils.isBlank(pushBean.getOtherBorrowed())) {
                    pushBean.setOtherBorrowed("暂无数据");
                }
                // 借款资金运用情况
                if (StringUtils.isBlank(pushBean.getIsFunds())) {
                    pushBean.setIsFunds("正常");
                }
                // 借款方经营状况及财务状况
                if (StringUtils.isBlank(pushBean.getIsManaged())) {
                    pushBean.setIsManaged("正常");
                }
                // 借款方还款能力变化情况
                if (StringUtils.isBlank(pushBean.getIsAbility())) {
                    pushBean.setIsAbility("正常");
                }
                // 借款人逾期情况
                if (StringUtils.isBlank(pushBean.getIsOverdue())) {
                    pushBean.setIsOverdue("暂无");
                }
                // 借款人涉诉情况
                if (StringUtils.isBlank(pushBean.getIsComplaint())) {
                    pushBean.setIsComplaint("暂无");
                }
                // 借款人受行政处罚情况
                if (StringUtils.isBlank(pushBean.getIsPunished())) {
                    pushBean.setIsPunished("暂无");
                }
                // 信批需求(资产只有个人) end
                // add by nxl 20180710互金系统,新添加借款人地址 Start
                if (pushBean.getAddress() == null || pushBean.getAddress().length() >99) {
                    pushBean.setRetCode("ZT000015");
                    pushBean.setRetMsg("借款人地址信息不正确");
                    continue;
                }
                // add by nxl 20180710互金系统,新添加借款人地址End

                // 包装资产信息
                HjhPlanAssetVO record = new HjhPlanAssetVO();
                // 信批需求新增字段属于选填(string)不加校验
                BeanUtils.copyProperties(record, pushBean);
                // 性别,如果没传，用身份证的
                String idCard = pushBean.getIdcard();
                // 性别
                int sexInt = Integer.parseInt(idCard.substring(16, 17));
                if (sexInt % 2 == 0) {
                    sexInt = 2;
                } else {
                    sexInt = 1;
                }

                record.setSex(sexInt);
                // 年纪，如果没传，用身份证的，从user表获取
                record.setAge(IdCard15To18.getAgeById(idcard));

                record.setInstCode(pushRequestBean.getInstCode());
                record.setAssetType(pushRequestBean.getAssetType());

                record.setUserId(userInfo.getUserId());
                record.setUserName(bankOpenAccount.getUserName());
                record.setAccountId(bankOpenAccount.getAccount());
                record.setMobile(users.getMobile());

                // 状态
                // 默认审核通过
                record.setVerifyStatus(1);
                record.setStatus(0);

                // 当前时间
                int nowTime = GetDate.getNowTime10();
                record.setRecieveTime(nowTime);
                record.setCreateTime(nowTime);
                record.setUpdateTime(nowTime);
                // 默认系统用户
                record.setCreateUserId(1);
                record.setUpdateUserId(1);

                // 受托支付
                if (stzAccount != null) {
                    record.setEntrustedFlg(1);
                    record.setEntrustedUserId(stzAccount.getStUserId());
                    record.setEntrustedUserName(stzAccount.getStUserName());
                    record.setEntrustedAccountId(stzAccount.getStAccountid());
                }

                // 平台直接默认填写：写死字段
                record.setCreditLevel("AA");
                record.setCostIntrodution("加入费用0元");
                record.setLitigation("无或已处理");
                record.setOverdueTimes("0");
                record.setOverdueAmount("0");

                record.setUseage("个人资金周转");
                record.setFirstPayment("个人收入");
                record.setSecondPayment("第三方保障");

                //推送资产
                int result = amTradeClient.insertAssert(record);
                if (result == 1) {
                    pushBean.setRetCode(ErrorCodeConstant.SUCCESS);
                    // 送到队列
                    this.sendToMQ(record);
                } else {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000004);
                    pushBean.setRetMsg("系统异常,资产未进库");
                }

            } catch (Exception e) {
                // ZT000008
                logger.error(e.getMessage(), e);
                if (e instanceof DuplicateKeyException) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000008);
                    pushBean.setRetMsg("资产已入库");
                } else {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000004);
                    pushBean.setRetMsg("系统异常,资产未进库");
                }
            }
        }

        logger.info(pushRequestBean.getInstCode()+" 结束推送资产");

        //商家信息未解析版
        logger.info("----------------商家信息导入开始-----------------------");
        List<InfoBean> riskInfo = pushRequestBean.getRiskInfo();
        if (Validator.isNotNull(riskInfo)) {
            if (riskInfo.size() > 1000) {
                resultBean.setStatusDesc("商家信息数量超限");
                logger.error("------------商家信息数量超限-----------");
                return resultBean;
            }
            //推送商家信息
            this.amTradeClient.insertRiskInfo(riskInfo);
        }
        logger.info("----------------商家信息导入完成-----------------------");

        resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
        resultBean.setStatusDesc("请求成功");

        // 设置返回结果
        resultBean.setData(retassets);

        return resultBean;
    }


    @Override
    public HjhLabelVO getLabelId(BorrowAndInfoVO borrowVO, HjhPlanAssetVO hjhPlanAssetVO) {

        HjhLabelVO resultLabel = null;

        List<HjhLabelVO> list = autoSendClient.seleHjhLabel(borrowVO.getBorrowStyle());
        if (list != null && list.size() <= 0) {
            logger.info(borrowVO.getBorrowStyle()+" 该原始标还款方式 没有一个标签");
            return resultLabel;
        }
        // continue过滤输入了但是不匹配的标签，如果找到就是第一个
        for (HjhLabelVO hjhLabelVO : list) {
            // 标的期限
//			int score = 0;
            if(hjhLabelVO.getLabelTermEnd() != null && hjhLabelVO.getLabelTermEnd().intValue()>0 && hjhLabelVO.getLabelTermStart()!=null
                    && hjhLabelVO.getLabelTermStart().intValue()>0){
                if(borrowVO.getBorrowPeriod() >= hjhLabelVO.getLabelTermStart() && borrowVO.getBorrowPeriod() <= hjhLabelVO.getLabelTermEnd()){
//					score = score+1;
                }else{
                    continue;
                }
            }else if ((hjhLabelVO.getLabelTermEnd() != null && hjhLabelVO.getLabelTermEnd().intValue()>0) ||
                    (hjhLabelVO.getLabelTermStart()!=null && hjhLabelVO.getLabelTermStart().intValue()>0)) {
                if(borrowVO.getBorrowPeriod().equals(hjhLabelVO.getLabelTermStart()) || borrowVO.getBorrowPeriod().equals(hjhLabelVO.getLabelTermEnd())){
//					score = score+1;
                }else{
                    continue;
                }
            }else{
                continue;
            }
            // 标的实际利率
            if(hjhLabelVO.getLabelAprStart() != null && hjhLabelVO.getLabelAprStart().compareTo(BigDecimal.ZERO)>0 &&
                    hjhLabelVO.getLabelAprEnd()!=null && hjhLabelVO.getLabelAprEnd().compareTo(BigDecimal.ZERO)>0){
                if(borrowVO.getBorrowApr().compareTo(hjhLabelVO.getLabelAprStart())>=0 && borrowVO.getBorrowApr().compareTo(hjhLabelVO.getLabelAprEnd())<=0){
//					score = score+1;
                }else{
                    continue;
                }
            }else if (hjhLabelVO.getLabelAprStart() != null && hjhLabelVO.getLabelAprStart().compareTo(BigDecimal.ZERO)>0) {
                if(borrowVO.getBorrowApr().compareTo(hjhLabelVO.getLabelAprStart())==0 ){
//					score = score+1;
                }else{
                    continue;
                }

            }else if (hjhLabelVO.getLabelAprEnd()!=null && hjhLabelVO.getLabelAprEnd().compareTo(BigDecimal.ZERO)>0 ) {
                if(borrowVO.getBorrowApr().compareTo(hjhLabelVO.getLabelAprEnd())==0){
//					score = score+1;
                }else {
                    continue;
                }
            }
            // 标的实际支付金额
            if(hjhLabelVO.getLabelPaymentAccountStart() != null && hjhLabelVO.getLabelPaymentAccountStart().compareTo(BigDecimal.ZERO)>0 &&
                    hjhLabelVO.getLabelPaymentAccountEnd()!=null && hjhLabelVO.getLabelPaymentAccountEnd().compareTo(BigDecimal.ZERO)>0){
                if(borrowVO.getAccount().compareTo(hjhLabelVO.getLabelPaymentAccountStart())>=0 && borrowVO.getAccount().compareTo(hjhLabelVO.getLabelPaymentAccountEnd())<=0){
//					score = score+1;
                }else{
                    continue;
                }
            }else if (hjhLabelVO.getLabelPaymentAccountStart() != null && hjhLabelVO.getLabelPaymentAccountStart().compareTo(BigDecimal.ZERO)>0) {
                if(borrowVO.getAccount().compareTo(hjhLabelVO.getLabelPaymentAccountStart())==0 ){
//					score = score+1;
                }else{
                    continue;
                }

            }else if (hjhLabelVO.getLabelPaymentAccountEnd()!=null && hjhLabelVO.getLabelPaymentAccountEnd().compareTo(BigDecimal.ZERO)>0 ) {
                if(borrowVO.getAccount().compareTo(hjhLabelVO.getLabelPaymentAccountEnd())==0){
//					score = score+1;
                }else{
                    continue;
                }
            }
            // 资产来源
            if(StringUtils.isNotBlank(hjhLabelVO.getInstCode())){
                if(hjhLabelVO.getInstCode().equals(borrowVO.getInstCode())){
//					score = score+1;
                }else{
                    continue;
                }
            }
            // 产品类型
            if(hjhLabelVO.getAssetType() != null && hjhLabelVO.getAssetType().intValue() > 0){
                if(hjhLabelVO.getAssetType().equals(borrowVO.getAssetType())){
                    ;
                }else{
                    continue;
                }
            }
            // 项目类型
            if(hjhLabelVO.getProjectType() != null && hjhLabelVO.getProjectType().intValue() > 0){
                if(hjhLabelVO.getProjectType().equals(borrowVO.getProjectType())){
                    ;
                }else{
                    continue;
                }
            }

            // 推送时间节点
            if(hjhPlanAssetVO != null && hjhPlanAssetVO.getRecieveTime() != null && hjhPlanAssetVO.getRecieveTime().intValue() > 0){
                Date reciveDate = GetDate.getDate(hjhPlanAssetVO.getRecieveTime());

                if(hjhLabelVO.getPushTimeStart() != null && hjhLabelVO.getPushTimeEnd()!=null){
                    if(reciveDate.getTime() >= hjhLabelVO.getPushTimeStart().getTime() &&
                            reciveDate.getTime() <= hjhLabelVO.getPushTimeEnd().getTime()){
//						score = score+1;
                    }else{
                        continue;
                    }
                }else if (hjhLabelVO.getPushTimeStart() != null) {
                    if(reciveDate.getTime() == hjhLabelVO.getPushTimeStart().getTime() ){
//						score = score+1;
                    }else{
                        continue;
                    }

                }else if (hjhLabelVO.getPushTimeEnd()!=null) {
                    if(reciveDate.getTime() == hjhLabelVO.getPushTimeEnd().getTime() ){
//						score = score+1;
                    }else{
                        continue;
                    }
                }

            }

            // 如果找到返回最近的一个
            return hjhLabelVO;

        }

        return resultLabel;
    }

    @Override
    public void sendToMQ(HjhPlanAssetVO hjhPlanAssetVO, String mqGroup) {

        // 加入到消息队列
        JSONObject params = new JSONObject();
        params.put("mqMsgId", GetCode.getRandomCode(10));
        params.put("assetId", hjhPlanAssetVO.getAssetId());
        params.put("instCode", hjhPlanAssetVO.getInstCode());
        try {
            autoSendProducer.messageSend(new MessageContent(MQConstant.ROCKETMQ_BORROW_RECORD_TOPIC, UUID.randomUUID().toString(),JSONObject.toJSONBytes(params)));
        } catch (MQException e) {
            e.printStackTrace();
            logger.error("自动备案送消息失败...", e);
        }
    }

    /**
     * 企业资产推送
     *
     * @param pushRequestBean
     * @return
     */
    @Override
    public PushResultBean companyAssetPush(PushRequestBean pushRequestBean) {
        PushResultBean resultBean = new PushResultBean();

        // 查看机构表是否存在
        HjhAssetBorrowTypeVO assetBorrow = amTradeClient.selectAssetBorrowType(pushRequestBean.getInstCode(),pushRequestBean.getAssetType());
        if (assetBorrow == null) {
            logger.info(pushRequestBean.getInstCode() + "  "+ pushRequestBean.getAssetType() + " ------机构编号不存在");
            resultBean.setStatusDesc("机构编号不存在！");
            return resultBean;
        }

        // 获取还款方式,项目类型
        List<BorrowProjectRepayVO> projectRepays = amTradeClient.selectProjectRepay(assetBorrow.getBorrowCd()+"");

        // 检查请求资产总参数
        List<PushBean>  reqData = pushRequestBean.getReqData();
        try {
            if (reqData.size() > 1000) {
                resultBean.setStatusDesc("请求参数过长");
                logger.error("------请求参数过长-------");
                return resultBean;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return resultBean;
        }
        // 返回结果
        List<PushBean> retassets = new ArrayList<PushBean>();
        //定义判断标识
        boolean flag = false;
        for (PushBean pushBean : reqData) {
            try {
				/*if (pushBean.getAccount() > MAX_ASSET_MONEY) {
					pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000006);
					pushBean.setRetMsg("资产金额超过一百万");
					continue;
				}*/
                //校验还款方式
                if(!checkIsMonthStyle(pushBean.getBorrowStyle(),pushBean.getIsMonth()) || !checkBorrowStyle(projectRepays,pushBean.getBorrowStyle())){
                    logger.info(pushRequestBean.getInstCode()+" 还款方式不正确 "+projectRepays);
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000005);
                    pushBean.setRetMsg("还款方式不正确,不支持这种还款方式");
                    retassets.add(pushBean);// 返回提示
                    continue;
                }

                // 受托支付
                STZHWhiteListVO stzAccount = null;
                if(pushBean.getEntrustedFlg() != null && pushBean.getEntrustedFlg().intValue() == 1){
                    // 校验
                    if(org.apache.commons.lang.StringUtils.isBlank(pushBean.getEntrustedAccountId())){
                        pushBean.setRetCode("ZT000011");
                        pushBean.setRetMsg("受托支付电子账户为空");
                        retassets.add(pushBean);// 返回提示
                        continue;
                    }

                    stzAccount = amTradeClient.selectStzfWhiteList(pushRequestBean.getInstCode(), pushBean.getEntrustedAccountId());

                    if(stzAccount == null){
                        pushBean.setRetCode("ZT000012");
                        pushBean.setRetMsg("受托支付电子账户未授权");
                        retassets.add(pushBean);// 返回提示
                        continue;
                    }
                }
                // 征信报告逾期情况
                if(org.apache.commons.lang.StringUtils.isBlank(pushBean.getOverdueReport())){
                    pushBean.setOverdueReport("暂无数据");
                }
                // 重大负债状况
                if(org.apache.commons.lang.StringUtils.isBlank(pushBean.getDebtSituation())){
                    pushBean.setDebtSituation("无");
                }
                // 其他平台借款情况
                if(org.apache.commons.lang.StringUtils.isBlank(pushBean.getOtherBorrowed())){
                    pushBean.setOtherBorrowed("暂无数据");
                }
                // 借款资金运用情况
                if(org.apache.commons.lang.StringUtils.isBlank(pushBean.getIsFunds())){
                    pushBean.setIsFunds("正常");
                }
                // 借款方经营状况及财务状况
                if(org.apache.commons.lang.StringUtils.isBlank(pushBean.getIsManaged())){
                    pushBean.setIsManaged("正常");
                }
                // 借款方还款能力变化情况
                if(org.apache.commons.lang.StringUtils.isBlank(pushBean.getIsAbility())){
                    pushBean.setIsAbility("正常");
                }
                // 借款人逾期情况
                if(org.apache.commons.lang.StringUtils.isBlank(pushBean.getIsOverdue())){
                    pushBean.setIsOverdue("暂无");
                }
                // 借款人涉诉情况
                if(org.apache.commons.lang.StringUtils.isBlank(pushBean.getIsComplaint())){
                    pushBean.setIsComplaint("暂无");
                }
                // 借款人受行政处罚情况
                if(org.apache.commons.lang.StringUtils.isBlank(pushBean.getIsPunished())){
                    pushBean.setIsPunished("暂无");
                }
                // add by nxl 20180710互金系统,新添加企业注册地址,企业注册编码Start
                if (StringUtils.isBlank(pushBean.getRegistrationAddress()) || pushBean.getRegistrationAddress().length() > 99) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000013);
                    pushBean.setRetMsg("企业注册地址信息不正确");
                    retassets.add(pushBean);// 返回提示
                    continue;
                }
                if(StringUtils.isBlank(pushBean.getCorporateCode())){
                    pushBean.setCorporateCode("");
                }
                // add by nxl 20180710互金系统,新添企业注册地址,企业注册编码End

                // 包装资产信息
                HjhPlanAssetVO record = new HjhPlanAssetVO();
                // 信批需求新增字段属于选填(string)不加校验
                org.apache.commons.beanutils.BeanUtils.copyProperties(record, pushBean);
                // 状态
                record.setVerifyStatus(1);// 默认审核通过
                record.setStatus(0);

                int nowTime = GetDate.getNowTime10(); // 当前时间
                record.setRecieveTime(nowTime);
                record.setCreateTime(nowTime);
                record.setUpdateTime(nowTime);
                record.setCreateUserId(1);// 默认系统用户
                record.setUpdateUserId(1);

                // 受托支付
                if(stzAccount != null){
                    record.setEntrustedFlg(1);
                    record.setEntrustedUserId(stzAccount.getStUserId());
                    record.setEntrustedUserName(stzAccount.getStUserName());
                    record.setEntrustedAccountId(stzAccount.getStAccountid());
                }

                // 平台默认字段
                record.setCreditLevel("AA");
                record.setCostIntrodution("加入费用0元");
                record.setLitigation("无或已处理");
                record.setOverdueTimes("0");
                record.setOverdueAmount("0");
                record.setUseage("个人资金周转");
                record.setFirstPayment("个人收入");
                record.setSecondPayment("第三方保障");
                //企业推送-必传字段非空校验
                if(checkCompanyPushInfo(pushBean)){
                    //通过用户名获得用户的详细信息
                    UserVO users = amUserClient.selectUserInfoByUsername(pushBean.getUserName());
                    //判断用户是否注册
                    if(users == null){
                        //自动注册
                        pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000001);
                        pushBean.setRetMsg("没有用户信息，请注册！--users");
                        retassets.add(pushBean);// 返回提示
                        continue;
                    }
                    if (users.getUserType() == 0){
                        pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000001);
                        pushBean.setRetMsg("用户类型不是企业用户");
                        retassets.add(pushBean);// 返回提示
                        continue;
                    }
                    //通过用户id获得用户真实姓名和身份证号
                    UserInfoVO userInfos = amUserClient.selectUserInfoByUserId(users.getUserId());
                    if(userInfos == null){
                        pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000001);
                        pushBean.setRetMsg("没有用户信息，请注册！--userinfo");
                        retassets.add(pushBean);// 返回提示
                        continue;
                    }
                    //通过用户id获得借款人的开户电子账号
                    BankOpenAccountVO bankOpenAccount = amUserClient.selectBankAccountById(users.getUserId());
                    //判断用户是否开户（汇盈金服、江西银行）
                    if (bankOpenAccount == null){
                        pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000002);
                        pushBean.setRetMsg("没有用户开户信息，请在线下开户！");
                        retassets.add(pushBean);// 返回提示
                        continue;
                    }
                    //判断借款用户是否是机构合作用户
                    if(users.getIsInstFlag() == 0 || users.getIsInstFlag().equals("0")){
                        pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000007);
                        pushBean.setRetMsg("借款用户不是机构合作用户！");
                        retassets.add(pushBean);// 返回提示
                        continue;
                    }
                    //查看用户对应的企业编号
                    CorpOpenAccountRecordVO userCorpOpenAccountRecordInfo = amUserClient.selectUserBusiNameByUsername(pushBean.getUserName());
                    if(userCorpOpenAccountRecordInfo == null){
                        pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000001);
                        pushBean.setRetMsg("企业用户未录入对应的企业信息！");
                        retassets.add(pushBean);// 返回提示
                        continue;
                    }
                    //判断借款人用户名所属企业与传入的企业名称是否一致
                    if(!userCorpOpenAccountRecordInfo.getBusiName().equals(pushBean.getBorrowCompanyName())){
                        pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000001);
                        pushBean.setRetMsg("借款人用户名所属企业与传入的企业名称不一致！");
                        retassets.add(pushBean);// 返回提示
                        continue;
                    }
					/*--- 包装推送资产信息 start ---*/
                    record.setAccountId(bankOpenAccount.getAccount());
                    record.setTruename(userInfos.getTruename());
                    record.setIdcard(userInfos.getIdcard());
                    record.setCreateUserId(1);// 默认系统用户
                    record.setUpdateUserId(1);
                    record.setInstCode(pushRequestBean.getInstCode());
                    record.setAssetType(pushRequestBean.getAssetType());
                    //0：个人，1：企业
                    record.setBorrowType("1");
                    record.setAssetId(pushBean.getAssetId());
                    record.setBorrowPeriod(pushBean.getBorrowPeriod());
                    record.setBorrowStyle(pushBean.getBorrowStyle());
                    record.setEntrustedFlg(pushBean.getEntrustedFlg());
                    record.setEntrustedAccountId(pushBean.getEntrustedAccountId());
                    record.setUserName(pushBean.getUserName());
                    record.setBorrowCompanyName(pushBean.getBorrowCompanyName());
                    record.setAccount(pushBean.getAccount());
                    record.setUseage(pushBean.getUseage());
                    if(org.apache.commons.lang.StringUtils.isBlank(pushBean.getFirstPayment())){
                        pushBean.setFirstPayment("经营收入");
                    }
                    record.setFirstPayment(pushBean.getFirstPayment());
                    if(org.apache.commons.lang.StringUtils.isBlank(pushBean.getSecondPayment())){
                        pushBean.setSecondPayment("第三方保障");
                    }
                    record.setSecondPayment(pushBean.getSecondPayment());
                    if(org.apache.commons.lang.StringUtils.isBlank(pushBean.getCostIntrodution())){
                        pushBean.setCostIntrodution("加入费用0元");
                    }
                    record.setCostIntrodution(pushBean.getCostIntrodution());
                    record.setFinancialSituation(pushBean.getFinancialSituation());
                    record.setLegalPerson(pushBean.getLegalPerson());
                    record.setRegistrationArea(pushBean.getRegistrationArea());
                    record.setRegistrationDate(pushBean.getRegistrationDate());
                    record.setMainBusiness(pushBean.getMainBusiness());
                    record.setUnifiedSocialCreditCode(pushBean.getUnifiedSocialCreditCode());
                    record.setRegisteredCapital(pushBean.getRegisteredCapital());
                    record.setIndustryInvolved(pushBean.getIndustryInvolved());
                    if(org.apache.commons.lang.StringUtils.isBlank(pushBean.getOverdueTimes())){
                        pushBean.setOverdueTimes("0");
                    }
                    record.setOverdueTimes(pushBean.getOverdueTimes());
                    if(org.apache.commons.lang.StringUtils.isBlank(pushBean.getOverdueAmount())){
                        pushBean.setOverdueAmount("0");
                    }
                    record.setOverdueAmount(pushBean.getOverdueAmount());
                    if(org.apache.commons.lang.StringUtils.isBlank(pushBean.getLitigation())){
                        pushBean.setLitigation("无或已处理");
                    }
                    record.setLitigation(pushBean.getLitigation());
                    record.setUserId(users.getUserId());
                    record.setMobile(users.getMobile());
					/*---  end ---*/
                }else{
                    logger.info(pushRequestBean.getInstCode()+"必传字段未传");
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000001);
                    pushBean.setRetMsg("必传字段未传，请传输！");
                    retassets.add(pushBean);// 返回提示
                    continue;
                }
                logger.info(pushRequestBean.getInstCode()+" 审核完成，开始推送资产 ");
                //检查是否存在重复资产
                List<HjhPlanAssetVO> duplicateAssetId = amTradeClient.checkDuplicateAssetId(pushBean.getAssetId());
                if (!CollectionUtils.isEmpty(duplicateAssetId)){
                    logger.error("【assetId】重复，请更换");
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000001);
                    pushBean.setRetMsg("【assetId】重复，请更换！");
                    retassets.add(pushBean);// 返回提示
                    continue;
                }
                int result = amTradeClient.insertAssert(record);
                if (result == 1) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000008);
                    pushBean.setRetMsg("资产已入库");
                    logger.info(pushRequestBean.getInstCode()+" 资产已入库");
                    flag = true;
                    // 送到队列
                    this.sendToMQ(record);
                    logger.info(pushRequestBean.getInstCode()+" mq已发送");
                }
            }catch (Exception e) {
                e.printStackTrace();
                pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000004);
                if (pushBean.getRetMsg() == null || pushBean.getRetMsg().equals("")){
                    pushBean.setRetMsg("系统异常,资产未进库");
                }
            }
            retassets.add(pushBean);// 返回推送提示
        }
        logger.info(pushRequestBean.getInstCode()+" 推送资产结束");

        //商家信息未解析版
        logger.info("----------------商家信息导入开始-----------------------");
        List<InfoBean> riskInfo = pushRequestBean.getRiskInfo();
        if (Validator.isNotNull(riskInfo)) {
            if (riskInfo.size() > 1000) {
                resultBean.setStatusDesc("商家信息数量超限");
                logger.error("------------商家信息数量超限-----------");
                return resultBean;
            }
            amTradeClient.insertRiskInfo(riskInfo);
        }
        logger.info("----------------商家信息导入完成-----------------------");

        if (flag){
            resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
            resultBean.setStatusDesc("请求成功");
            resultBean.setData(retassets);// 设置返回结果
        }else {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
            resultBean.setStatusDesc("请求异常，具体原因请查看retMsg字段返回结果~");
            resultBean.setData(retassets);// 设置返回结果
        }

        return resultBean;
    }


    private boolean checkBorrowStyle(List<BorrowProjectRepayVO> projectRepays, String borrowStyle) {
        if (projectRepays == null) {
            return false;
        }
        for (BorrowProjectRepayVO borrowProjectRepayVO : projectRepays) {
            if (borrowStyle.equals(borrowProjectRepayVO.getRepayMethod())) {
                return true;
            }
        }

        return false;
    }

    private boolean checkIsMonthStyle(String borrowSytle, int isMontth) {
        if (isMontth == 0) {
            if ("endday".equals(borrowSytle)) {
                return true;
            } else {
                return false;
            }
        } else if (isMontth == 1) {
            if ("endday".equals(borrowSytle)) {
                return false;
            } else {
                return true;
            }
        }

        return false;
    }

    /**
     * 企业资产推送，必填字段判断
     *
     * @param pushBean
     * @return
     */
    private boolean checkCompanyPushInfo(PushBean pushBean){
        if(StringUtils.isBlank(pushBean.getBorrowCompanyName()) || StringUtils.isBlank(pushBean.getAssetId()) || pushBean.getBorrowPeriod() == null ||
           pushBean.getIsMonth() == null || StringUtils.isBlank(pushBean.getBorrowStyle()) || StringUtils.isBlank(pushBean.getUserName()) ||
           StringUtils.isBlank(pushBean.getIndustryInvolved()) || StringUtils.isBlank(pushBean.getOverdueTimes()) ||
           pushBean.getAccount() == null || StringUtils.isBlank(pushBean.getUnifiedSocialCreditCode()) ||
           StringUtils.isBlank(pushBean.getOverdueAmount())){

            return false;
        }
        return true;
    }
}
