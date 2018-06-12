/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.borrow.controller;

import com.hyjf.am.vo.assetpush.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.assetpush.STZHWhiteListVO;
import com.hyjf.am.vo.borrow.BorrowProjectRepayVO;
import com.hyjf.am.vo.borrow.HjhPlanAssetVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.IdCard15To18;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.borrow.bean.assetpush.PushBean;
import com.hyjf.cs.borrow.bean.assetpush.PushRequestBean;
import com.hyjf.cs.borrow.bean.assetpush.PushResultBean;
import com.hyjf.cs.borrow.service.ApiAssetPushService;
import com.hyjf.cs.borrow.util.ErrorCodeConstant;
import com.hyjf.cs.borrow.util.SignUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 资产推送接口
 * @author fuqiang
 * @version ApiAssetPushController, v0.1 2018/6/11 17:52
 */
@RestController
@RequestMapping("/api")
public class ApiAssetPushController {

    Logger _log = LoggerFactory.getLogger(ApiAssetPushController.class);

    private static final Long MAX_ASSET_MONEY = 1000000L;

    @Autowired
    private ApiAssetPushService pushService;

    @PostMapping("/push")
    public PushResultBean push(@RequestBody PushRequestBean pushRequestBean, HttpServletRequest request, HttpServletResponse response) {

        PushResultBean resultBean = new PushResultBean();
        resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
        resultBean.setStatusDesc("请求参数非法");

        // 验证请求参数
        List<PushBean> reqData = pushRequestBean.getReqData();
        if (Validator.isNull(reqData) || Validator.isNull(pushRequestBean.getInstCode())
                || Validator.isNull(pushRequestBean.getAssetType()) || Validator.isNull(pushRequestBean.getChkValue())) {
            _log.info("------请求参数非法-------" + pushRequestBean);
            return resultBean;
        }

        // 验签
        if (SignUtil.verifyRequestSign(pushRequestBean, "/push")) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000002);
            _log.info("-------------------验签失败！--------------------");
            resultBean.setStatusDesc("验签失败！");
            return resultBean;
        }

        _log.info(pushRequestBean.getInstCode()+" 开始推送资产 ");

        if(CustomConstants.INST_CODE_HYJF.equals(pushRequestBean.getInstCode())){
            _log.info(pushRequestBean.getInstCode() + "  "+ pushRequestBean.getAssetType() + " ------平台不能推送资产");
            return resultBean;
        }

        // 查看机构表是否存在
        HjhAssetBorrowTypeVO assetBorrow = this.pushService.selectAssetBorrowType(pushRequestBean.getInstCode(),pushRequestBean.getAssetType());
        if (assetBorrow == null) {
            _log.info(pushRequestBean.getInstCode() + "  "+ pushRequestBean.getAssetType() + " ------机构编号不存在");
            return resultBean;
        }

        // 获取还款方式,项目类型
        List<BorrowProjectRepayVO> projectRepays = this.pushService.selectProjectRepay(assetBorrow.getBorrowCd()+"");

        // 检查请求资产总参数
        List<PushBean> assets = reqData;
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
            e.printStackTrace();
            _log.error(e.getMessage());
            return resultBean;
        }

        // 返回结果
        List<PushBean> retassets = new ArrayList<PushBean>();

        for (PushBean pushBean : assets) {

            try {

                String truename = pushBean.getTruename();
                String idcard = pushBean.getIdcard();

                // 返回结果，下面的修改应该会返回到这里
                retassets.add(pushBean);

                // 金额不是100以及100的整数倍时将通过接口拒绝接收资产
                if (pushBean.getAccount() == null || (pushBean.getAccount()%10)!=0 || pushBean.getBorrowPeriod() == null
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

                UserInfoVO userInfo = this.pushService.selectUserInfoByNameAndCard(truename, idcard);
                if (userInfo == null) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000001);
                    pushBean.setRetMsg("没有用户信息");
                    continue;
                } else if (userInfo.getRoleId() != 2) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000003);
                    pushBean.setRetMsg("用户不是借款人");
                    continue;
                }
                BankOpenAccountVO bankOpenAccount = this.pushService.selectBankAccountById(userInfo.getUserId());
                if (bankOpenAccount == null) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000002);
                    pushBean.setRetMsg("没有用户开户信息");
                    continue;
                }
                UserVO users = this.pushService.selectUsersById(userInfo.getUserId());
                if (users == null) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000001);
                    pushBean.setRetMsg("没有用户信息");
                    continue;
                }

                //校验还款方式
                if(!checkIsMonthStyle(pushBean.getBorrowStyle(),pushBean.getIsMonth()) || !checkBorrowStyle(projectRepays,pushBean.getBorrowStyle())){
                    _log.info(pushRequestBean.getInstCode()+" 还款方式不正确 "+projectRepays);
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000005);
                    pushBean.setRetMsg("不支持这种还款方式");
                    continue;
                }

                // 受托支付
                STZHWhiteListVO stzAccount = null;
                if(pushBean.getEntrustedFlg()!=null && pushBean.getEntrustedFlg().intValue() ==1){
                    // 校验
                    if(StringUtils.isBlank(pushBean.getEntrustedAccountId())){
                        pushBean.setRetCode("ZT000011");
                        pushBean.setRetMsg("受托支付电子账户为空");
                        continue;
                    }

                    stzAccount = this.pushService.selectStzfWhiteList(pushRequestBean.getInstCode(), pushBean.getEntrustedAccountId());

                    if(stzAccount == null){
                        pushBean.setRetCode("ZT000012");
                        pushBean.setRetMsg("受托支付电子账户未授权");
                        continue;
                    }

                }

                // 信批需求(资产只有个人,若第三方不传则为默认值插入资产表中) start
                // 年收入
                if(StringUtils.isBlank(pushBean.getAnnualIncome())){
                    pushBean.setAnnualIncome("10万以内");
                }
                // 征信报告逾期情况
                if(StringUtils.isBlank(pushBean.getOverdueReport())){
                    pushBean.setOverdueReport("暂无数据");
                }
                // 重大负债状况
                if(StringUtils.isBlank(pushBean.getDebtSituation())){
                    pushBean.setDebtSituation("无");
                }
                // 其他平台借款情况
                if(StringUtils.isBlank(pushBean.getOtherBorrowed())){
                    pushBean.setOtherBorrowed("暂无数据");
                }
                // 借款资金运用情况
                if(StringUtils.isBlank(pushBean.getIsFunds())){
                    pushBean.setIsFunds("正常");
                }
                // 借款方经营状况及财务状况
                if(StringUtils.isBlank(pushBean.getIsManaged())){
                    pushBean.setIsManaged("正常");
                }
                // 借款方还款能力变化情况
                if(StringUtils.isBlank(pushBean.getIsAbility())){
                    pushBean.setIsAbility("正常");
                }
                // 借款人逾期情况
                if(StringUtils.isBlank(pushBean.getIsOverdue())){
                    pushBean.setIsOverdue("暂无");
                }
                // 借款人涉诉情况
                if(StringUtils.isBlank(pushBean.getIsComplaint())){
                    pushBean.setIsComplaint("暂无");
                }
                // 借款人受行政处罚情况
                if(StringUtils.isBlank(pushBean.getIsPunished())){
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
                if(stzAccount != null){
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


                int result = this.pushService.insertAssert(record);
                if (result == 1) {
                    pushBean.setRetCode(ErrorCodeConstant.SUCCESS);
                    // 送到队列
                    this.pushService.sendToMQ(record);
                } else {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000004);
                    pushBean.setRetMsg("系统异常,资产未进库");
                }

            } catch (Exception e) {
                // ZT000008
                _log.error(e.getMessage());
                e.printStackTrace();
                if (e instanceof DuplicateKeyException) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000008);
                    pushBean.setRetMsg("资产已入库");
                }else{
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000004);
                    pushBean.setRetMsg("系统异常,资产未进库");
                }
            }
        }

        _log.info(pushRequestBean.getInstCode()+" 结束推送资产");

        return null;
    }

    private boolean checkBorrowStyle(List<BorrowProjectRepayVO> projectRepays,String borrowStyle){
        if(projectRepays == null){
            return false;
        }
        for (BorrowProjectRepayVO borrowProjectRepayVO : projectRepays) {
            if(borrowStyle.equals(borrowProjectRepayVO.getRepayMethod())){
                return true;
            }
        }

        return false;
    }

    private boolean checkIsMonthStyle(String borrowSytle, int isMontth){
        if(isMontth == 0){
            if("endday".equals(borrowSytle)){
                return true;
            }else{
                return false;
            }
        }else if (isMontth == 1){
            if("endday".equals(borrowSytle)){
                return false;
            }else{
                return true;
            }
        }

        return false;
    }
}
