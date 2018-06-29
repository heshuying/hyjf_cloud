/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.assetpush.InfoBean;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.hjh.HjhLabelVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectRepayVO;
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
import com.hyjf.cs.trade.client.ApiAssetClient;
import com.hyjf.cs.trade.client.AutoSendClient;
import com.hyjf.cs.trade.mq.AutoSendProducer;
import com.hyjf.cs.trade.mq.Producer;
import com.hyjf.cs.trade.service.ApiAssetPushService;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.util.ErrorCodeConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

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

    private Logger _log = LoggerFactory.getLogger(ApiAssetPushServcieImpl.class);

    private static final Long MAX_ASSET_MONEY = 1000000L;

    @Autowired
    private ApiAssetClient apiAssetClient;

    @Autowired
    private AutoSendClient autoSendClient;

    @Autowired
    private AutoSendProducer autoSendProducer;

    @Override
    public void sendToMQ(HjhPlanAssetVO hjhPlanAsset) {
        JSONObject params = new JSONObject();
        params.put("mqMsgId", GetCode.getRandomCode(10));
        params.put("assetId", hjhPlanAsset.getAssetId());
        params.put("instCode", hjhPlanAsset.getInstCode());
        try {
            autoSendProducer.messageSend(new Producer.MassageContent(MQConstant.ASSET_PUST_TOPIC, UUID.randomUUID().toString(), JSONObject.toJSONBytes(params)));
        } catch (MQException e) {
            _log.error("自动录标发送消息失败...", e);
        }
    }

    @Override
    public PushResultBean assetPush(PushRequestBean pushRequestBean) {

        PushResultBean resultBean = new PushResultBean();

        // 查看机构表是否存在
        HjhAssetBorrowTypeVO assetBorrow = apiAssetClient.selectAssetBorrowType(pushRequestBean.getInstCode(), pushRequestBean.getAssetType());
        if (assetBorrow == null) {
            _log.info(pushRequestBean.getInstCode() + "  " + pushRequestBean.getAssetType() + " ------机构编号不存在");
            return resultBean;
        }

        // 获取还款方式,项目类型
        List<BorrowProjectRepayVO> projectRepays = apiAssetClient.selectProjectRepay(assetBorrow.getBorrowCd() + "");

        // 检查请求资产总参数
        List<PushBean> assets = pushRequestBean.getReqData();
        try {
            if (assets == null || assets.size() == 0) {
                return resultBean;
            }
            if (assets.size() > 1000) {
                resultBean.setStatusDesc("请求参数过长");
                _log.error("------请求参数过长-------");
                return resultBean;
            }

        } catch (Exception e) {
            _log.error(e.getMessage(), e);
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

                UserInfoVO userInfo = apiAssetClient.selectUserInfoByNameAndCard(truename, idcard);
                if (userInfo == null) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000001);
                    pushBean.setRetMsg("没有用户信息");
                    continue;
                } else if (userInfo.getRoleId() != 2) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000003);
                    pushBean.setRetMsg("用户不是借款人");
                    continue;
                }
                BankOpenAccountVO bankOpenAccount = apiAssetClient.selectBankAccountById(userInfo.getUserId());
                if (bankOpenAccount == null) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000002);
                    pushBean.setRetMsg("没有用户开户信息");
                    continue;
                }
                UserVO users = apiAssetClient.selectUsersById(userInfo.getUserId());
                if (users == null) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000001);
                    pushBean.setRetMsg("没有用户信息");
                    continue;
                }

                //校验还款方式
                if (!checkIsMonthStyle(pushBean.getBorrowStyle(), pushBean.getIsMonth()) || !checkBorrowStyle(projectRepays, pushBean.getBorrowStyle())) {
                    _log.info(pushRequestBean.getInstCode() + " 还款方式不正确 " + projectRepays);
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

                    stzAccount = apiAssetClient.selectStzfWhiteList(pushRequestBean.getInstCode(), pushBean.getEntrustedAccountId());

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


                // 包装资产信息
                HjhPlanAssetVO record = new HjhPlanAssetVO();
                // 信批需求新增字段属于选填(string)不加校验
                BeanUtils.copyProperties(record, pushBean);
                // 性别,如果没传，用身份证的
                String idCard = pushBean.getIdcard();
                int sexInt = Integer.parseInt(idCard.substring(16, 17));// 性别
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
                record.setVerifyStatus(1);// 默认审核通过
                record.setStatus(0);

                int nowTime = GetDate.getNowTime10(); // 当前时间
                record.setRecieveTime(nowTime);
                record.setCreateTime(nowTime);
                record.setUpdateTime(nowTime);
                record.setCreateUserId(1);// 默认系统用户
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


                int result = apiAssetClient.insertAssert(record);
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
                _log.error(e.getMessage(), e);
                if (e instanceof DuplicateKeyException) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000008);
                    pushBean.setRetMsg("资产已入库");
                } else {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000004);
                    pushBean.setRetMsg("系统异常,资产未进库");
                }
            }
        }

        _log.info(pushRequestBean.getInstCode()+" 结束推送资产");

        //商家信息未解析版
        _log.info("----------------商家信息导入开始-----------------------");
        List<InfoBean> riskInfo = pushRequestBean.getRiskInfo();
        if (Validator.isNotNull(riskInfo)) {
            if (riskInfo.size() > 1000) {
                resultBean.setStatusDesc("商家信息数量超限");
                _log.error("------------商家信息数量超限-----------");
                return resultBean;
            }
            this.apiAssetClient.insertRiskInfo(riskInfo);
        }
        _log.info("----------------商家信息导入完成-----------------------");

        resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
        resultBean.setStatusDesc("请求成功");

        // 设置返回结果
        resultBean.setData(retassets);

        return resultBean;
    }


    @Override
    public HjhLabelVO getLabelId(BorrowVO borrowVO, HjhPlanAssetVO hjhPlanAssetVO) {

        HjhLabelVO resultLabel = null;

        List<HjhLabelVO> list = autoSendClient.seleHjhLabel(borrowVO.getBorrowStyle());
        if (list != null && list.size() <= 0) {
            _log.info(borrowVO.getBorrowStyle()+" 该原始标还款方式 没有一个标签");
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
            autoSendProducer.messageSend(new Producer.MassageContent(MQConstant.BORROW_RECORD_TOPIC, UUID.randomUUID().toString(),JSONObject.toJSONBytes(params)));
        } catch (MQException e) {
            e.printStackTrace();
            _log.error("自动备案送消息失败...", e);
        }
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
}
