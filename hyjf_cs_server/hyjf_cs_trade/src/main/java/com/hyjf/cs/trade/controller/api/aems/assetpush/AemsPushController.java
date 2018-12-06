/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.api.aems.assetpush;

import com.hyjf.cs.trade.controller.BaseTradeController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * 资产推送接口
 *
 * @author fuqiang
 * @version ApiAssetPushController, v0.1 2018/6/11 17:52
 */
@Api(tags = "api端-Aems资产推送接口")
@RestController
@RequestMapping("/hyjf-api/aems/assetpush")
public class AemsPushController extends BaseTradeController {

    Logger logger = LoggerFactory.getLogger(AemsPushController.class);

    private static final Long MAX_ASSET_MONEY = 1000000L;

   /* @Autowired
    private AemsPushService pushService;

    @Autowired
    private AemsRiskInfoService riskInfoService;

    @Autowired
    private AutoService autoService;

    *//**
     * 资产推送接口(个人)
     * @param AemsPushRequestBean
     * @param request
     * @param response
     * @return
     *//*
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,value = AemsPushDefine.PUSH_ACTION)
    public PushResultBean push(@RequestBody AemsPushRequestBean pushRequestBean, HttpServletRequest request, HttpServletResponse response) {

        PushResultBean resultBean = new PushResultBean();
        resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
        resultBean.setStatusDesc("请求参数非法");

        // 验证请求参数
        List<AemsPushBean>  reqData = pushRequestBean.getReqData();
        if (Validator.isNull(reqData) || Validator.isNull(pushRequestBean.getInstCode())
                || Validator.isNull(pushRequestBean.getAssetType()) || Validator.isNull(pushRequestBean.getChkValue())) {
            logger.info("------请求参数非法-------" + pushRequestBean);
            return resultBean;
        }

        // 验签
        if (!this.AEMSVerifyRequestSign(pushRequestBean, AemsPushDefine.REQUEST_MAPPING+AemsPushDefine.PUSH_ACTION)) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000002);
            logger.info("-------------------验签失败！--------------------");
            resultBean.setStatusDesc("验签失败！");
            return resultBean;
        }

        logger.info(pushRequestBean.getInstCode()+" 开始推送资产 ");

        if(CustomConstants.INST_CODE_HYJF.equals(pushRequestBean.getInstCode())){
            logger.info(pushRequestBean.getInstCode() + "  "+ pushRequestBean.getAssetType() + " ------平台不能推送资产");
            return resultBean;
        }

        // 查看机构表是否存在
        HjhAssetBorrowType assetBorrow = this.pushService.selectAssetBorrowType(pushRequestBean.getInstCode(),pushRequestBean.getAssetType());
        if (assetBorrow == null) {
            logger.info(pushRequestBean.getInstCode() + "  "+ pushRequestBean.getAssetType() + " ------机构编号不存在");
            return resultBean;
        }

        // 授信期内校验
        HjhBailConfig bailConfig = pushService.getBailConfig(pushRequestBean.getInstCode());
        if(bailConfig == null){
            logger.info("保证金配置不存在:{}", pushRequestBean.getInstCode());
            return resultBean;
        }
        if(GetDate.getNowTime10() < GetDate.getDayStart10(bailConfig.getTimestart())
                || GetDate.getNowTime10() > GetDate.getDayEnd10(bailConfig.getTimeend())){
            logger.info("未在授信期内，不能推标");
            return resultBean;
        }

        // 获取还款方式,项目类型
        List<BorrowProjectRepay> projectRepays = this.pushService.selectProjectRepay(assetBorrow.getBorrowCd()+"");

        // 检查请求资产总参数
        List<AemsPushBean> assets = reqData;
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
            e.printStackTrace();
            logger.error(e.getMessage());

            return resultBean;
        }

        // 返回结果
        List<AemsPushBean> retassets = new ArrayList<AemsPushBean>();

        for (AemsPushBean pushBean : assets) {

            try {

                String truename = pushBean.getTruename();
                String idcard = pushBean.getIdcard();

                // 返回结果，下面的修改应该会返回到这里
                retassets.add(pushBean);

                //资产编号判空
                if(Validator.isNull(pushBean.getAssetId())){
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000007);
                    pushBean.setRetMsg("资产编号不能为空");
                    continue;
                }
                //资产编号判重
                int count=pushService.selectAssertCountByAssetId(pushBean.getAssetId());
                if (count>0){
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000007);
                    pushBean.setRetMsg("资产已存在");
                    continue;
                }

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

                UsersInfo userInfo = this.pushService.selectUserInfoByNameAndCard(truename, idcard);
                if (userInfo == null) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000001);
                    pushBean.setRetMsg("没有用户信息");
                    continue;
                } else if (userInfo.getRoleId() != 2) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000003);
                    pushBean.setRetMsg("用户不是借款人");
                    continue;
                }
                BankOpenAccount bankOpenAccount = this.pushService.selectBankAccountById(userInfo.getUserId());
                if (bankOpenAccount == null) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000002);
                    pushBean.setRetMsg("没有用户开户信息");
                    continue;
                }
                Users users = this.pushService.selectUsersById(userInfo.getUserId());
                if (users == null) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000001);
                    pushBean.setRetMsg("没有用户信息");
                    continue;
                }

                //校验还款方式
                if(!checkIsMonthStyle(pushBean.getBorrowStyle(),pushBean.getIsMonth()) || !checkBorrowStyle(projectRepays,pushBean.getBorrowStyle())){
                    logger.info(pushRequestBean.getInstCode()+" 还款方式不正确 "+projectRepays);
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000005);
                    pushBean.setRetMsg("不支持这种还款方式");
                    continue;
                }

                // 受托支付
                STZHWhiteList stzAccount = null;
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
                    // 校验受托人是否授权
                    BankOpenAccount stOpenAccount = this.autoService.getBankOpenAccount(pushBean.getEntrustedAccountId());
                    Users stUser = this.pushService.selectUsersById(stOpenAccount.getUserId());
                    Integer stResult = CommonUtils.checkPaymentAuthStatus(stUser.getPaymentAuthStatus());
                    if(stResult==0){
                        pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000015);
                        pushBean.setRetMsg("受托账户未进行服务费授权");
                        retassets.add(pushBean);// 返回提示
                        continue;
                    }
                    *//*UsersInfo stUserInfo = this.pushService.getUsersInfoByUserId(stUser.getUserId());
                    if (stUserInfo.getRoleId() != 2) {
                        pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000010);
                        pushBean.setRetMsg("受托账户不是借款人");
                        continue;
                    }*//*
                }
                // 校验是否授权  合规三期
                Integer paymentAuth = users.getPaymentAuthStatus();
                HjhUserAuth hjhUserAuth = this.autoService.getHjhUserAuthByUserId(users.getUserId());
                Integer repayAuth = hjhUserAuth == null ? 0 : hjhUserAuth.getAutoRepayStatus();
                Integer authResult = CommonUtils.checkAuthStatus(repayAuth, paymentAuth);
                if (authResult == 5) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000011);
                    pushBean.setRetMsg("借款人必须服务费授权");
                    retassets.add(pushBean);// 返回提示
                    continue;
                } else if (authResult == 6) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000012);
                    pushBean.setRetMsg("借款人必须还款授权");
                    retassets.add(pushBean);// 返回提示
                    continue;
                } else if (authResult == 7) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000014);
                    pushBean.setRetMsg("借款人必须服务费授权和还款授权");
                    retassets.add(pushBean);// 返回提示
                    continue;
                }

                //update by wj 2018-05-24 年收入，月收入非空校验 start
                if(StringUtils.isBlank(pushBean.getAnnualIncome())){
                    pushBean.setRetCode("ZT000013");
                    pushBean.setRetMsg("年收入为空");
                    continue;
                }

                if(StringUtils.isBlank(pushBean.getMonthlyIncome())){
                    pushBean.setRetCode("ZT000014");
                    pushBean.setRetMsg("月收入为空");
                    continue;
                }
                //update by wj 2018-05-24 年收入，月收入非空校验 end

                // 信批需求(资产只有个人,若第三方不传则为默认值插入资产表中) start
                // 年收入
                //del by wj 2018-05-24 start
//				if(StringUtils.isBlank(pushBean.getAnnualIncome())){
//					pushBean.setAnnualIncome("10万以内");
//				}
                //del by wj 2018-05-24 end
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

                //del by wj 2018-05-24 end
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
                // add by nxl 20180710互金系统,新添加借款人地址 Start
                if (pushBean.getAddress() == null || pushBean.getAddress().length() >99) {
                    pushBean.setRetCode("ZT000015");
                    pushBean.setRetMsg("借款人地址信息不正确");
                    continue;
                }
                // add by nxl 20180710互金系统,新添加借款人地址End

                // 包装资产信息
                HjhPlanAsset record = new HjhPlanAsset();
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


                int result = this.pushService.insertAssert(record, null);
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
                logger.error(e.getMessage());
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

        logger.info(pushRequestBean.getInstCode()+" 结束推送资产");

        //商家信息未解析版
        logger.info("----------------商家信息导入开始-----------------------");
        List<AemsInfoBean> riskInfo = pushRequestBean.getRiskInfo();
        if (Validator.isNotNull(riskInfo)) {
            if (riskInfo.size() > 1000) {
                resultBean.setStatusDesc("商家信息数量超限");
                logger.error("------------商家信息数量超限-----------");
                return resultBean;
            }
            this.riskInfoService.insertRiskInfo(riskInfo);
        }
        logger.info("----------------商家信息导入完成-----------------------");

        resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
        resultBean.setStatusDesc("请求成功");

        // 设置返回结果
        resultBean.setData(retassets);

        return resultBean;
    }

    *//**
     * 资产推送接口(企业)
     * @param AemsPushRequestBean
     * @return
     *//*
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,value = AemsPushDefine.PUSH_COMPANY_ACTION)
    public PushResultBean pushcompany(@RequestBody AemsPushRequestBean pushRequestBean) {
        PushResultBean resultBean = new PushResultBean();
        //获得推送时间
        String pushInstCode = pushRequestBean.getInstCode();
        Integer pushAssetType = pushRequestBean.getAssetType();
        resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
        resultBean.setStatusDesc("请求参数非法");

        // 验证请求参数
        List<AemsPushBean>  reqData = pushRequestBean.getReqData();
        if (Validator.isNull(reqData) || Validator.isNull(pushRequestBean.getInstCode())
                || Validator.isNull(pushRequestBean.getAssetType()) || Validator.isNull(pushRequestBean.getChkValue())) {
            logger.info("------请求参数非法-------" + pushRequestBean);
            return resultBean;
        }

        // 验签
        if (!this.AEMSVerifyRequestSign(pushRequestBean, AemsPushDefine.REQUEST_MAPPING+AemsPushDefine.PUSH_COMPANY_ACTION)) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000002);
            logger.info("-------------------验签失败！--------------------");
            resultBean.setStatusDesc("验签失败！");
            return resultBean;
        }

        logger.info(pushRequestBean.getInstCode()+" 开始验证资产信息 ");

        if(CustomConstants.INST_CODE_HYJF.equals(pushRequestBean.getInstCode())){
            logger.info(pushRequestBean.getInstCode() + "  "+ pushRequestBean.getAssetType() + " ------不能推送本平台资产");
            resultBean.setStatusDesc("不能推送本平台资产！");
            return resultBean;
        }

        // 查看机构表是否存在
        HjhAssetBorrowType assetBorrow = this.pushService.selectAssetBorrowType(pushRequestBean.getInstCode(),pushRequestBean.getAssetType());
        if (assetBorrow == null) {
            logger.info(pushRequestBean.getInstCode() + "  "+ pushRequestBean.getAssetType() + " ------机构编号不存在");
            resultBean.setStatusDesc("机构编号不存在！");
            return resultBean;
        }

        // 授信期内校验
        HjhBailConfig bailConfig = pushService.getBailConfig(pushRequestBean.getInstCode());
        if(bailConfig == null){
            logger.info("保证金配置不存在:{}", pushRequestBean.getInstCode());
            return resultBean;
        }
        if(GetDate.getNowTime10() < GetDate.getDayStart10(bailConfig.getTimestart())
                || GetDate.getNowTime10() > GetDate.getDayEnd10(bailConfig.getTimeend())){
            logger.info("未在授信期内，不能推标");
            return resultBean;
        }

        // 获取还款方式,项目类型
        List<BorrowProjectRepay> projectRepays = this.pushService.selectProjectRepay(assetBorrow.getBorrowCd()+"");

        // 检查请求资产总参数
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
        List<AemsPushBean> retassets = new ArrayList<AemsPushBean>();
        //定义判断标识
        boolean flag = false;
        for (AemsPushBean pushBean : reqData) {
            try {
				*//*if (pushBean.getAccount() > MAX_ASSET_MONEY) {
					pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000006);
					pushBean.setRetMsg("资产金额超过一百万");
					continue;
				}*//*

                //资产编号判空
                if(Validator.isNull(pushBean.getAssetId())){
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000007);
                    pushBean.setRetMsg("资产编号不能为空");
                    continue;
                }
                //资产编号判重
                int count=pushService.selectAssertCountByAssetId(pushBean.getAssetId());
                if (count>0){
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000007);
                    pushBean.setRetMsg("资产已存在");
                    continue;
                }


                //校验还款方式
                if(!checkIsMonthStyle(pushBean.getBorrowStyle(),pushBean.getIsMonth()) || !checkBorrowStyle(projectRepays,pushBean.getBorrowStyle())){
                    logger.info(pushRequestBean.getInstCode()+" 还款方式不正确 "+projectRepays);
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000005);
                    pushBean.setRetMsg("还款方式不正确,不支持这种还款方式");
                    retassets.add(pushBean);// 返回提示
                    continue;
                }

                // 受托支付
                STZHWhiteList stzAccount = null;
                if(pushBean.getEntrustedFlg() != null && pushBean.getEntrustedFlg().intValue() == 1){
                    // 校验
                    if(StringUtils.isBlank(pushBean.getEntrustedAccountId())){
                        pushBean.setRetCode("ZT000011");
                        pushBean.setRetMsg("受托支付电子账户为空");
                        retassets.add(pushBean);// 返回提示
                        continue;
                    }


                    stzAccount = this.pushService.selectStzfWhiteList(pushRequestBean.getInstCode(), pushBean.getEntrustedAccountId());
                    if(stzAccount == null){
                        pushBean.setRetCode("ZT000012");
                        pushBean.setRetMsg("受托支付电子账户未授权");
                        retassets.add(pushBean);// 返回提示
                        continue;
                    }
                    // 校验受托人是否授权
                    BankOpenAccount stOpenAccount = this.autoService.getBankOpenAccount(pushBean.getEntrustedAccountId());
                    Users stUser = this.pushService.selectUsersById(stOpenAccount.getUserId());
                    Integer stResult = CommonUtils.checkPaymentAuthStatus(stUser.getPaymentAuthStatus());
                    if(stResult==0){
                        pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000015);
                        pushBean.setRetMsg("受托账户未进行服务费授权");
                        retassets.add(pushBean);// 返回提示
                        continue;
                    }
                   *//* UsersInfo stUserInfo = this.pushService.getUsersInfoByUserId(stUser.getUserId());
                    if (stUserInfo.getRoleId() != 2) {
                        pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000010);
                        pushBean.setRetMsg("受托账户不是借款人");
                        continue;
                    }*//*
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
                // add by nxl 20180710互金系统,新添加企业注册地址,企业注册编码Start
                if (StringUtils.isBlank(pushBean.getRegistrationAddress()) || pushBean.getRegistrationAddress().length() >99) {
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
                HjhPlanAsset record = new HjhPlanAsset();
                // 信批需求新增字段属于选填(string)不加校验
                BeanUtils.copyProperties(record, pushBean);
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
                    Users users = this.pushService.selectUserInfoByUsername(pushBean.getUserName());
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
                        pushBean.setRetMsg("个人用户不能推送企业资产！");
                        retassets.add(pushBean);// 返回提示
                        continue;
                    }
                    //通过用户id获得用户真实姓名和身份证号
                    UsersInfo userInfos = this.pushService.selectUserInfoByUserId(users.getUserId());
                    if(userInfos == null){
                        pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000001);
                        pushBean.setRetMsg("没有用户信息，请注册！--userinfo");
                        retassets.add(pushBean);// 返回提示
                        continue;
                    }
                    //通过用户id获得借款人的开户电子账号
                    BankOpenAccount bankOpenAccount = this.pushService.selectBankAccountById(users.getUserId());
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
                    CorpOpenAccountRecord userCorpOpenAccountRecordInfo = this.pushService.selectUserBusiNameByUsername(pushBean.getUserName());
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
                    // 合规校验服务费授权还款授权
                    Integer paymentAuth = users.getPaymentAuthStatus();
                    HjhUserAuth hjhUserAuth = this.autoService.getHjhUserAuthByUserId(users.getUserId());
                    Integer repayAuth = hjhUserAuth == null ? 0 : hjhUserAuth.getAutoRepayStatus();
                    Integer authResult = CommonUtils.checkAuthStatus(repayAuth, paymentAuth);
                    if (authResult == 5) {
                        pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000011);
                        pushBean.setRetMsg("借款人必须服务费授权");
                        retassets.add(pushBean);// 返回提示
                        continue;
                    } else if (authResult == 6) {
                        pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000012);
                        pushBean.setRetMsg("借款人必须还款授权");
                        retassets.add(pushBean);// 返回提示
                        continue;
                    } else if (authResult == 7) {
                        pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000014);
                        pushBean.setRetMsg("借款人必须服务费授权和还款授权");
                        retassets.add(pushBean);// 返回提示
                        continue;
                    }

                    *//*--- 包装推送资产信息 start ---*//*
                    record.setAccountId(bankOpenAccount.getAccount());
                    record.setTruename(userInfos.getTruename());
                    record.setIdcard(userInfos.getIdcard());
                    record.setCreateUserId(1);// 默认系统用户
                    record.setUpdateUserId(1);
                    record.setInstCode(pushInstCode);
                    record.setAssetType(pushAssetType);
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
                    if(StringUtils.isBlank(pushBean.getFirstPayment())){
                        pushBean.setFirstPayment("经营收入");
                    }
                    record.setFirstPayment(pushBean.getFirstPayment());
                    if(StringUtils.isBlank(pushBean.getSecondPayment())){
                        pushBean.setSecondPayment("第三方保障");
                    }
                    record.setSecondPayment(pushBean.getSecondPayment());
                    if(StringUtils.isBlank(pushBean.getCostIntrodution())){
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
                    if(StringUtils.isBlank(pushBean.getOverdueTimes())){
                        pushBean.setOverdueTimes("0");
                    }
                    record.setOverdueTimes(pushBean.getOverdueTimes());
                    if(StringUtils.isBlank(pushBean.getOverdueAmount())){
                        pushBean.setOverdueAmount("0");
                    }
                    record.setOverdueAmount(pushBean.getOverdueAmount());
                    if(StringUtils.isBlank(pushBean.getLitigation())){
                        pushBean.setLitigation("无或已处理");
                    }
                    record.setLitigation(pushBean.getLitigation());
                    record.setUserId(users.getUserId());
                    record.setMobile(users.getMobile());
                    *//*---  end ---*//*
                }else{
                    logger.info(pushRequestBean.getInstCode()+"必传字段未传");
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000001);
                    pushBean.setRetMsg("必传字段未传，请传输！");
                    retassets.add(pushBean);// 返回提示
                    continue;
                }
                logger.info(pushRequestBean.getInstCode()+" 审核完成，开始推送资产 ");
                //检查是否存在重复资产
                HjhPlanAsset duplicateAssetId = this.pushService.checkDuplicateAssetId(pushBean.getAssetId());
                if (duplicateAssetId != null){
                    logger.error("【assetId】重复，请更换");
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000001);
                    pushBean.setRetMsg("【assetId】重复，请更换！");
                    retassets.add(pushBean);// 返回提示
                    continue;
                }
                int result = this.pushService.insertAssert(record, null);
                if (result == 1) {
                    pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000008);
                    pushBean.setRetMsg("资产已入库");
                    logger.info(pushRequestBean.getInstCode()+" 资产已入库");
                    flag = true;
                    // 送到队列
                    this.pushService.sendToMQ(record);
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
        List<AemsInfoBean> riskInfo = pushRequestBean.getRiskInfo();
        if (Validator.isNotNull(riskInfo)) {
            if (riskInfo.size() > 1000) {
                resultBean.setStatusDesc("商家信息数量超限");
                logger.error("------------商家信息数量超限-----------");
                return resultBean;
            }
            this.riskInfoService.insertRiskInfo(riskInfo);
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

    *//**
     * 检验还款方式
     * @param projectRepays
     * @param borrowStyle
     * @return
     *//*
    private boolean checkBorrowStyle(List<BorrowProjectRepay> projectRepays, String borrowStyle){
        if(projectRepays == null){
            return false;
        }
        for (BorrowProjectRepay borrowProjectRepay : projectRepays) {
            if(borrowStyle.equals(borrowProjectRepay.getRepayMethod())){
                return true;
            }
        }

        return false;
    }

    *//**
     * 检验还款方式
     * @param borrowSytle
     * @param isMontth
     * @return
     *//*
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

    *//**
     * 企业资产推送，必填字段判断
     *//*
    private boolean checkCompanyPushInfo(AemsPushBean pushBean){
        if(pushBean.getBorrowCompanyName() == null || pushBean.getBorrowCompanyName().equals("") || pushBean.getAssetId() == null ||
                pushBean.getAssetId().equals("") || pushBean.getBorrowPeriod() == null || pushBean.getBorrowPeriod().equals("") || pushBean.getIsMonth() == null ||
                pushBean.getIsMonth().equals("") || pushBean.getBorrowStyle() == null || pushBean.getBorrowStyle().equals("") || pushBean.getUserName() == null || pushBean.getUserName().equals("") ||
                pushBean.getAccount() == null || pushBean.getAccount().equals("") || pushBean.getUseage() == null  || pushBean.getUseage().equals("") || pushBean.getFinancialSituation() == null ||
                pushBean.getFinancialSituation().equals("") || pushBean.getLegalPerson() == null || pushBean.getLegalPerson().equals("") || pushBean.getUnifiedSocialCreditCode() == null ||
                pushBean.getUnifiedSocialCreditCode().equals("") || pushBean.getIndustryInvolved() == null || pushBean.getIndustryInvolved().equals("") || pushBean.getOverdueTimes() == null ||
                pushBean.getOverdueTimes().equals("") ||pushBean.getOverdueAmount() == null || pushBean.getOverdueAmount().equals("")){
            return false;
        }
        return true;
    }*/

}
