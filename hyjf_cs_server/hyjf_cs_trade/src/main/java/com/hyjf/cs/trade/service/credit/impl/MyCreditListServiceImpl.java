/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.credit.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.DebtConfigResponse;
import com.hyjf.am.response.trade.MyCreditListQueryResponse;
import com.hyjf.am.resquest.trade.MyCreditListQueryRequest;
import com.hyjf.am.resquest.trade.MyCreditListRequest;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.vo.config.DebtConfigVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.BeforeInterestAfterPrincipalUtils;
import com.hyjf.common.util.calculate.CalculatesUtil;
import com.hyjf.common.util.calculate.DuePrincipalAndInterestUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.bean.CreditDetailsRequestBean;
import com.hyjf.cs.trade.bean.CreditResultBean;
import com.hyjf.cs.trade.bean.TenderBorrowCreditCustomize;
import com.hyjf.cs.trade.client.AmConfigClient;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.client.CsMessageClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.auth.AuthService;
import com.hyjf.cs.trade.service.credit.MyCreditListService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.smscode.SmsCodeService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @Description 资产管理  我要债转相关
 * @Author sunss
 * @Date 2018/6/30 10:18
 */
@Service
public class MyCreditListServiceImpl extends BaseTradeServiceImpl implements MyCreditListService {

    /**
     * 折让率格式
     */
    private static String regex = "\\d{1}\\.\\d{1}";
    /**
     * 折让率范围开始
     */
    private static float creditDiscountStart = 0.5f;
    /**
     * 折让率范围结束
     */
    private static float creditDiscountEnd = 2.0f;
    private static DecimalFormat DF_COM_VIEW = new DecimalFormat("######0.00");
    private final String FORMAT_DEFAULT_AMOUNT = "0.00";
    @Autowired
    private CommonProducer commonProducer;
    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    private CsMessageClient amMongoClient;
    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private AmConfigClient amConfigClient;

    @Autowired
    private SmsCodeService sendSmsCode;
    @Autowired
    private AuthService authService;

    /**
     * 我要债转列表页 获取参数
     *
     * @param request
     * @param userId
     * @return
     */
    @Override
    public WebResult getCreditListData(MyCreditListRequest request, Integer userId) {

        // 获取可债转金额   转让中本金   累计已转让本金
        CreditPageVO moneys = amTradeClient.selectCreditPageMoneyTotal(userId);
        AccountVO account = amTradeClient.getAccountByUserId(userId);
        moneys.setHoldMoneyTotal(account.getBankAwaitCapital());
        // 判断用户所处的渠道如果不允许债转，可债转金额为0  start
        if (!amTradeClient.isAllowChannelAttorn(userId)) {
            logger.info("判断用户所处渠道不允许债转,可债转金额0....userId is:{}", userId);
            //throw new CheckException(MsgEnum.ERR_ALLOW_CHANNEL_ATTORN);
            moneys.setCanCreditMoney(BigDecimal.ZERO);
        }
        AppUtmRegVO appChannelStatisticsDetails = amMongoClient.getAppChannelStatisticsDetailByUserId(userId);
        if (appChannelStatisticsDetails != null) {
            UtmPlatVO utmPlat = amUserClient.selectUtmPlatByUtmId(userId);
            if (utmPlat != null && utmPlat.getAttornFlag() == 0) {
                logger.info("判断用户所处渠道不允许债转,可债转金额0....userId is:{}", userId);
                //throw new CheckException(MsgEnum.ERR_ALLOW_CHANNEL_ATTORN);
                moneys.setCanCreditMoney(BigDecimal.ZERO);
            }
        }
        WebResult result =  new WebResult();
        result.setData(moneys);
        return result;
    }

    /**
     * 我要债转列表页 获取列表
     *
     * @param request
     * @param userId
     * @return
     */
    @Override
    public WebResult getCreditList(MyCreditListQueryRequest request, Integer userId) {
        WebResult webResult = new WebResult();
        // 初始化分页参数，并组合到请求参数
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        if (!amTradeClient.isAllowChannelAttorn(userId)) {
            logger.info("判断用户所处渠道不允许债转,可债转金额0....userId is:{}", userId);
            //throw new CheckException(MsgEnum.ERR_ALLOW_CHANNEL_ATTORN);
            webResult.setData(new ArrayList<>());
            page.setTotal(0);
            webResult.setPage(page);
            return webResult;
        }
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        request.setUserId(userId);
        MyCreditListQueryResponse res = amTradeClient.countMyCreditList(request);

        if (!Response.isSuccess(res)) {
            logger.error("查询count异常");
            throw new RuntimeException("查询count异常");
        }
        int count = res.getCount();
        page.setTotal(count);
        webResult.setData(new ArrayList<>());
        if (count > 0) {
            List<TenderCreditCustomizeVO> result = new ArrayList<>();
            MyCreditListQueryResponse dataResponse = amTradeClient.searchMyCreditList(request);
            if (!Response.isSuccess(dataResponse)) {
                logger.error("查询list数据异常");
                throw new RuntimeException("查询list数据异常");
            }
            result = CommonUtils.convertBeanList(dataResponse.getResultList(), TenderCreditCustomizeVO.class);
            webResult.setData(result);
        }
        webResult.setPage(page);
        return webResult;
    }

    /**
     * 用户中心查询出借可债转详情
     *
     * @param request
     * @param userId
     * @return
     */
    @Override
    public WebResult tenderToCreditDetail(CreditDetailsRequestBean request, Integer userId) {
        CreditResultBean creditResultBean = new CreditResultBean();
        WebResult webResult = new WebResult();
        UserVO user = amUserClient.findUserById(userId);
        DebtConfigResponse response = amConfigClient.getDebtConfig();
        DebtConfigVO config = response.getResult();
        // 缴费授权
        HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthVO(userId);
        creditResultBean.setPaymentAuthStatus(hjhUserAuth==null?0:hjhUserAuth.getAutoPaymentStatus());
        creditResultBean.setPaymentAuthOn(authService.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH).getEnabledStatus());
        creditResultBean.setIsCheckUserRole(systemConfig.getRoleIsopen());
        UserInfoVO userInfoVO = amUserClient.findUsersInfoById(userId);
        creditResultBean.setRoleId(userInfoVO==null?"0":userInfoVO.getRoleId()+"");
        if(config!=null){
            creditResultBean.setDebtConfigVO(config);
        }else{
            logger.info(this.getClass().getName(), "searchTenderToCreditDetail", "配置表无数据请配置");
            creditResultBean.setResultFlag(CustomConstants.RESULT_FAIL);
            creditResultBean.setMsg("配置表无数据请配置");
            creditResultBean.setData(null);
            webResult.setData(creditResultBean);
            return webResult;
        }
        creditResultBean.setMobile(user.getMobile());
        Integer nowTime = GetDate.getNowTime10();
        if (StringUtils.isEmpty(request.getBorrowNid()) || StringUtils.isEmpty(request.getTenderNid())) {
            // 转让失败,无法获取借款和出借编号
            throw  new CheckException(MsgEnum.ERROR_CREDIT_PARAM);
        }
        TenderCreditCustomizeVO tenderToCreditDetail = amTradeClient.selectTenderToCreditDetail(userId, request.getBorrowNid(),
                request.getTenderNid());
        if(tenderToCreditDetail==null){
            // 获取债转数据为空
            throw  new CheckException(MsgEnum.ERROR_CREDIT_PARAM);
        }
        int lastdays = 0;
        String borrowNid = tenderToCreditDetail.getBorrowNid();
        // 根据borrowNid判断是否分期
        BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(borrowNid);
        String borrowStyle = borrow.getBorrowStyle();
        if (borrowStyle.equals(CalculatesUtil.STYLE_END) || borrowStyle.equals(CalculatesUtil.STYLE_ENDDAY)) {
            try {
                lastdays = GetDate.daysBetween(GetDate.getDateTimeMyTimeInMillis(nowTime),
                        GetDate.getDateTimeMyTimeInMillis(Integer.parseInt(tenderToCreditDetail.getRecoverTime())));
            } catch (Exception e) {
                // 债转数据错误
                throw  new CheckException(MsgEnum.ERROR_CREDIT_DATA_ERROR);
            }
        }

        // 等额本息和等额本金和先息后本
        if (borrowStyle.equals(CalculatesUtil.STYLE_MONTH) || borrowStyle.equals(CalculatesUtil.STYLE_PRINCIPAL) || borrowStyle.equals(CalculatesUtil.STYLE_ENDMONTH)) {
            List<BorrowRepayPlanVO> list = this.amTradeClient.getBorrowRepayPlansByPeriod(borrowNid, borrow.getBorrowPeriod());
            if (list != null && list.size() > 0) {
                try {
                    lastdays = GetDate.daysBetween(GetDate.getDateTimeMyTimeInMillis(nowTime), GetDate.getDateTimeMyTimeInMillis(list.get(0).getRepayTime()));
                } catch (Exception e) {
                    // 债转数据错误
                    throw  new CheckException(MsgEnum.ERROR_CREDIT_DATA_ERROR);
                }
            }
        }

        // 债转详细预计服务费计算
        ExpectCreditFeeVO resultMap = amTradeClient.selectExpectCreditFee(request.getBorrowNid(), request.getTenderNid());
        tenderToCreditDetail.setLastDays(lastdays + "");
        creditResultBean.setData(tenderToCreditDetail);
        creditResultBean.setCalData(resultMap);

        webResult.setData(creditResultBean);
        return webResult;
    }

    /**
     * 用户中心验证出借人当天是否可以债转  每天三次
     *
     * @param request
     * @param userId
     * @return
     */
    @Override
    public WebResult tenderAbleToCredit(CreditDetailsRequestBean request, Integer userId) {
        WebResult webResult = new WebResult();
        if (StringUtils.isEmpty(request.getBorrowNid()) || StringUtils.isEmpty(request.getTenderNid())) {
            // 转让失败,无法获取借款和出借编号
            throw  new CheckException(MsgEnum.ERROR_CREDIT_PARAM);
        }

        Integer creditedNum = amTradeClient.tenderAbleToCredit(userId);
        Map<String,Object> data = new HashedMap();
        data.put("creditedNum",creditedNum);
        webResult.setData(data);
        return webResult;
    }

    /**
     * 检查是否可债转
     *
     * @param request
     * @param userId
     * @return
     */
    @Override
    public WebResult checkCanCredit(CreditDetailsRequestBean request, Integer userId) {
        WebResult webResult = new WebResult();
        if (StringUtils.isEmpty(request.getBorrowNid()) || StringUtils.isEmpty(request.getTenderNid())) {
            // 转让失败,无法获取借款和出借编号
            throw  new CheckException(MsgEnum.ERROR_CREDIT_PARAM);
        }
        Integer creditedNum = amTradeClient.tenderAbleToCredit(userId);
        if (creditedNum != null && creditedNum >= 3) {
            // 今天的转让次数已满3次,请明天再试
            throw  new CheckException(MsgEnum.ERROR_CREDIT_THREE);
        }
        return webResult;
    }

    /**
     * 债转提交保存
     *
     * @param request
     * @param userId
     * @return
     */
    @Override
    public WebResult saveTenderToCredit(TenderBorrowCreditCustomize request, Integer userId) {
        WebResult result = new WebResult();
        // 检查是否能债转
        logger.info("检查是否能够进行债转---userId:{}",userId);
        checkCanCredit(request,userId);
        logger.info("检查是否能够进行债转---userId:{}   通过",userId);
        checkTenderToCreditParam(request,userId);
        logger.info("检查债转参数结束---userId:{}   通过",userId);
        // 债转保存
        try{
            logger.info("开始插入债转表---userId:{}  ",userId);
            Integer creditNid = insertTenderToCredit(userId, request);
            logger.info("插入债转表结束---userId:{} ",userId);
            Map data = new HashedMap();
            // 结束日期
            data.put("creditEndTime", GetDate.timestamptoStrYYYYMMDDHHMMSS(request.getCreditEndTime()));
            // 转让价格
            data.put("creditPrice",request.getCreditPrice());
            // 转让本金
            data.put("creditCapital",request.getCreditCapital());
            // web的转让本金
            data.put("assignCapital",request.getCreditCapital());
            logger.info("债转保存，返回给前端数据 {}", JSONObject.toJSONString(data));
            result.setData(data);
            // 保存成功后,发送神策数据统计
            if (StringUtils.isNotEmpty(request.getPresetProps())){
                logger.info("发起债转时,神策预置属性:[" + request.getPresetProps() + "]");
                SensorsDataBean sensorsDataBean = new SensorsDataBean();
                // 将json串转换成Bean
                try {
                    Map<String, Object> sensorsDataMap = JSONObject.parseObject(request.getPresetProps(), new TypeReference<Map<String, Object>>() {
                    });
                    sensorsDataBean.setPresetProps(sensorsDataMap);
                    sensorsDataBean.setUserId(userId);
                    sensorsDataBean.setEventCode("submit_credit_assign");
                    sensorsDataBean.setCreditNid(String.valueOf(request.getCreditNid()));
                    // 发送神策数据统计MQ
                    this.sendSensorsDataMQ(sensorsDataBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // add 合规数据上报 埋点 liubin 20181122 start
            // 推送数据到MQ 转让 散
            JSONObject params = new JSONObject();
            params.put("creditNid", creditNid+"");
            params.put("flag", "1"); //1（散）2（智投）
            params.put("status", "0"); //0转让
            commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.TRANSFER_SUCCESS_TAG, UUID.randomUUID().toString(), params),
                    MQConstant.HG_REPORT_DELAY_LEVEL);
            // add 合规数据上报 埋点 liubin 20181122 end

        }catch (Exception e){
        	e.printStackTrace();
            result.setStatusInfo(MsgEnum.ERR_SYSTEM_UNUSUAL);
        }
        return result;
    }

    /**
     * 用户中心查询 债转详细预计服务费计算
     *
     * @param userId
     * @return
     */
    @Override
    public WebResult getExpectCreditFee(TenderBorrowCreditCustomize request, Integer userId) {
        WebResult result = new WebResult();
        // 当前日期
        Integer nowTime = GetDate.getNowTime10();
        // 查询borrow 和 BorrowRecover
        BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(request.getBorrowNid());
        if (borrow == null) {
            throw new CheckException(MsgEnum.ERROR_CREDIT_PARAM);
        }
        BorrowRecoverVO recover = this.amTradeClient.getBorrowRecoverByTenderNid(request.getTenderNid());
        if (recover == null) {
            throw new CheckException(MsgEnum.ERROR_CREDIT_PARAM);
        }
        // 债转计算
        Map<String, BigDecimal> creditCreateMap = selectExpectCreditFeeForBigDecimal(borrow, recover,
                request.getCreditDiscount(), nowTime);
        result.setData(creditCreateMap);
        return result;
    }

    /**
     * 发送短信验证码（ajax请求） 短信验证码数据保存
     *
     * @param request
     * @param userId
     * @return
     */
    @Override
    public WebResult sendCreditCode(TenderBorrowCreditCustomize request, Integer userId) {
        UserVO user = amUserClient.findUserById(userId);
        if(user.getMobile()==null){
            throw new CheckException(MsgEnum.STATUS_ZC000001);
        }
        WebResult result = new WebResult();
        sendCode(user.getUserId(),request.getIp(),user.getMobile(),request.getPlatform()+"");
        return result;
    }

    @Override
    public void sendCode(Integer userId, String ip,String mobile,String platform){
        String validCodeType = CustomConstants.PARAM_TPL_ZHUCE;
        sendSmsCode.sendSmsCodeCheckParam(validCodeType, mobile, userId, ip);
        try{
            sendSmsCode.sendSmsCode(validCodeType, mobile,platform,ip);
        }catch (MQException e){
            e.printStackTrace();
        }
    }

    /**
     * 短信验证码校验
     *
     * @param request
     * @param userId
     * @return
     */
    @Override
    public WebResult checkCode(TenderBorrowCreditCustomize request, Integer userId) {
    	
        UserVO user = amUserClient.findUserById(userId);
        String verificationType = CommonConstant.PARAM_TPL_ZHUCE;
        // 短信验证码
        String code = request.getTelcode();
        // 手机号码(必须,数字,最大长度)
       String mobile = user.getMobile();
        CheckUtil.check(StringUtils.isNotBlank(verificationType), MsgEnum.STATUS_CE000001);
        CheckUtil.check(StringUtils.isNotBlank(mobile), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isMobile(mobile), MsgEnum.ERR_FMT_MOBILE);
        CheckUtil.check(StringUtils.isNotBlank(code), MsgEnum.ERR_SMSCODE_BLANK);
        int result = amUserClient.onlyCheckMobileCode(mobile, code, verificationType, request.getPlatform(), CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_YIYAN);
        if (result == 0) {
            throw new CheckException(MsgEnum.STATUS_ZC000015);
        }
        return new WebResult();
    }

    /**
     * 检查债转提交保存的参数
     * @param request
     * @param userId
     */
    @Override
    public void checkTenderToCreditParam(TenderBorrowCreditCustomize request, Integer userId) {
        // 验证折让率
        //新增配置表校验add tanyy2018-9-27
        DebtConfigVO config = amConfigClient.getDebtConfig().getResult();
        if (org.apache.commons.lang.StringUtils.isEmpty(request.getCreditDiscount())||config==null) {
            // 折让率不能为空
            throw  new CheckException(MsgEnum.ERROR_CREDIT_CREDIT_DISCOUNT_NULL);
        } else {
            float creditDiscount = Float.parseFloat(request.getCreditDiscount());
            DebtConfigVO debtConfig = config;
            if (creditDiscount > debtConfig.getConcessionRateUp().floatValue() || creditDiscount < debtConfig.getConcessionRateDown().floatValue()) {
                // 折让率范围错误
                throw  new CheckException(MsgEnum.ERROR_CREDIT_DISCOUNT_ERROR);
            }

        }
        // 验证手机验证码
        if (StringUtils.isEmpty(request.getTelcode())) {
            // 手机验证码不能为空
            throw  new CheckException(MsgEnum.STATUS_ZC000010);
        } else {
            UserVO user = amUserClient.findUserById(userId);
            int result = amUserClient.checkMobileCode(user.getMobile(), request.getTelcode(), CommonConstant.PARAM_TPL_ZHUCE
                    , request.getPlatform(), CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_YIYAN);
            if (result == 0) {
                throw new CheckException(MsgEnum.STATUS_ZC000015);
            }
        }

        // 服务费授权校验
        if (!authService.checkPaymentAuthStatus(userId)) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_NEED_PAYMENT_AUTH);
        }
        if (!amTradeClient.isAllowChannelAttorn(userId)) {
            logger.info("判断用户所处渠道不允许债转,可债转金额0....userId is:{}", userId);
            throw new CheckException(MsgEnum.ERR_ALLOW_CHANNEL_ATTORN);
        }
    }

    /**
     * 插入转让信息
     * @param userId
     * @param request
     * @return
     */
    @Override
    public Integer insertTenderToCredit(int userId, TenderBorrowCreditCustomize request) {
        // 当前日期
        Integer nowTime = GetDate.getNowTime10();
        // 查询borrow 和 BorrowRecover
        BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(request.getBorrowNid());
        if (borrow == null) {
            throw new CheckException(MsgEnum.ERROR_CREDIT_PARAM);
        }
        BorrowRecoverVO recover = this.amTradeClient.getBorrowRecoverByTenderNid(request.getTenderNid());
        if (recover == null) {
            throw new CheckException(MsgEnum.ERROR_CREDIT_PARAM);
        }
        UserVO userVO = amUserClient.findUserById(userId);
        // 债转计算
        Map<String, BigDecimal> creditCreateMap = selectExpectCreditFeeForBigDecimal(borrow, recover,
                request.getCreditDiscount(), nowTime);
        // 声明要保存的债转对象
        BorrowCreditVO borrowCredit = new BorrowCreditVO();
        // 生成creditNid
        // 获取当前时间的日期
        String nowDate = (GetDate.yyyyMMdd.format(new Date()) != null && !"".equals(GetDate.yyyyMMdd.format(new Date()))) ? GetDate.yyyyMMdd.format(new Date()) : "0";
        Integer creditedNum = amTradeClient.tenderAbleToCredit(0);
        Integer creditNumToday = (creditedNum == null ? 0 : creditedNum);
        String creditNid = nowDate.substring(2) + String.format("%04d", (creditNumToday + 1));
        // 获取待债转数据
        TenderCreditCustomizeVO tenderToCreditDetail = amTradeClient.selectTenderToCreditDetail(userId, request.getBorrowNid(),
                request.getTenderNid());
        request.setCreditNid(creditNid);
        // 债转nid
        borrowCredit.setCreditNid(Integer.parseInt(creditNid));
        // 转让用户id
        borrowCredit.setCreditUserId(userId);
        int lastdays = 0;
        int holddays = 0;
        String borrowStyle = borrow.getBorrowStyle();
        // 到期还本还息和按天计息，到期还本还息
        if (borrowStyle.equals(CalculatesUtil.STYLE_END) || borrowStyle.equals(CalculatesUtil.STYLE_ENDDAY)) {
            try {
                String nowDateStr = GetDate.getDateTimeMyTimeInMillis(nowTime);
                String recoverDate = GetDate.getDateTimeMyTimeInMillis(recover.getRecoverTime());
                String hodeDate = GetDate.getDateTimeMyTimeInMillis(recover.getCreateTime());
                lastdays = GetDate.daysBetween(nowDateStr, recoverDate);
                holddays = GetDate.daysBetween(hodeDate, nowDateStr);
            } catch (Exception e) {
                // 债转数据错误
                throw new CheckException(MsgEnum.ERROR_CREDIT_DATA_ERROR);
            }
        }
        // 等额本息和等额本金和先息后本
        if (borrowStyle.equals(CalculatesUtil.STYLE_MONTH) || borrowStyle.equals(CalculatesUtil.STYLE_PRINCIPAL) || borrowStyle.equals(CalculatesUtil.STYLE_ENDMONTH)) {
            String bidNid = borrow.getBorrowNid();
            List<BorrowRepayPlanVO> borrowRepayPlans = amTradeClient.getBorrowRepayPlansByPeriod(bidNid, borrow.getBorrowPeriod());
            if (borrowRepayPlans != null && borrowRepayPlans.size() > 0) {
                try {
                    String hodeDate = GetDate.getDateTimeMyTimeInMillis(recover.getCreateTime());
                    lastdays = GetDate.daysBetween(GetDate.getDateTimeMyTimeInMillis(nowTime), GetDate.getDateTimeMyTimeInMillis(borrowRepayPlans.get(0).getRepayTime()));
                    holddays = GetDate.daysBetween(hodeDate, GetDate.getDateTimeMyTimeInMillis(nowTime));
                } catch (Exception e) {
                    // 债转数据错误
                    throw new CheckException(MsgEnum.ERROR_CREDIT_DATA_ERROR);
                }
            }
        }
        borrowCredit.setBorrowUserId(borrow.getUserId());
        borrowCredit.setBorrowUserName(borrow.getBorrowUserName());
        // 原标nid
        borrowCredit.setBidNid(tenderToCreditDetail.getBorrowNid());
        // 原标年化利率
        borrowCredit.setBidApr(new BigDecimal(tenderToCreditDetail.getBorrowApr()));
        // 原标标题
        borrowCredit.setBidName(tenderToCreditDetail.getBorrowName());
        // 投标nid
        borrowCredit.setTenderNid(tenderToCreditDetail.getTenderNid());
        // 转让状态 0.进行中,1.停止
        borrowCredit.setCreditStatus(0);
        // 排序
        borrowCredit.setCreditOrder(0);
        borrowCredit.setCreditUserName(userVO.getUsername());
        // 债转期限-天
        borrowCredit.setCreditTerm(lastdays);
        borrowCredit.setCreditTermHold(holddays);
        // 剩余的债转期数-期
        borrowCredit.setCreditPeriod(recover.getRecoverPeriod());
        // 客户端
        borrowCredit.setClient(request.getPlatform());
        // 债转本金
        borrowCredit.setCreditCapital(creditCreateMap.get("creditCapital"));
        // 债转总额
        borrowCredit.setCreditAccount(creditCreateMap.get("creditAccount"));
        // 债转总利息
        borrowCredit.setCreditInterest(creditCreateMap.get("creditInterest"));
        // 需垫付利息
        borrowCredit.setCreditInterestAdvance(creditCreateMap.get("assignInterestAdvance"));
        // 折价率
        borrowCredit.setCreditDiscount(new BigDecimal(request.getCreditDiscount()));
        // 总收入,
        borrowCredit.setCreditIncome(creditCreateMap.get("assignPay"));
        // 服务费
        borrowCredit.setCreditFee(creditCreateMap.get("assignPay").multiply(org.apache.commons.lang.StringUtils.isEmpty(request.getAttornRate())?new BigDecimal(0.01):new BigDecimal(request.getAttornRate()).divide(new BigDecimal(100))).setScale(2, BigDecimal.ROUND_DOWN));
        // 出让价格
        borrowCredit.setCreditPrice(creditCreateMap.get("creditPrice"));
        // 已认购本金
        borrowCredit.setCreditCapitalAssigned(BigDecimal.ZERO);
        // 已承接部分的利息
        borrowCredit.setCreditInterestAssigned(BigDecimal.ZERO);
        // 已垫付利息
        borrowCredit.setCreditInterestAdvanceAssigned(BigDecimal.ZERO);
        // 已还款总额
        borrowCredit.setCreditRepayAccount(BigDecimal.ZERO);
        // 已还本金
        borrowCredit.setCreditRepayCapital(BigDecimal.ZERO);
        // 已还利息
        borrowCredit.setCreditRepayInterest(BigDecimal.ZERO);
        // 债转最后还款日
        borrowCredit.setCreditRepayEndTime(Integer.parseInt(GetDate.get10Time(tenderToCreditDetail.getRepayLastTime())));
        // 上次还款日
        borrowCredit.setCreditRepayLastTime(recover.getRecoverYestime() != null ? recover.getRecoverYestime() : nowTime);
        // 下次还款日
        borrowCredit.setCreditRepayNextTime(recover.getRecoverTime() != null ? recover.getRecoverTime() : nowTime);
        // 最终实际还款日
        borrowCredit.setCreditRepayYesTime(0);
        // 创建日期
        borrowCredit.setCreateDate(Integer.parseInt(GetDate.yyyyMMdd.format(new Date())));
        // 创建时间
        borrowCredit.setAddTime(nowTime);
        // 结束时间
        borrowCredit.setEndTime(nowTime + 24 * 3600 * 3);
        // 认购时间
        borrowCredit.setAssignTime(0);
        // 出借次数
        borrowCredit.setAssignNum(0);
        // 还款状态 0还款中、1已还款、2还款失败
        borrowCredit.setRepayStatus(0);
        // 给前端展示用
        request.setCreditEndTime(borrowCredit.getEndTime());
        logger.info("creditCapital:"+borrowCredit.getCreditCapital());
        request.setCreditPrice(DF_COM_VIEW.format(borrowCredit.getCreditPrice().setScale(2, BigDecimal.ROUND_DOWN)));
        request.setCreditCapital(DF_COM_VIEW.format(borrowCredit.getCreditCapital().setScale(2, BigDecimal.ROUND_DOWN)));
        if ("endmonth".equals(borrow.getBorrowStyle())) {
            // 从第几期开始
            borrowCredit.setRecoverPeriod(borrow.getBorrowPeriod() - recover.getRecoverPeriod());
        } else {
            // 从第几期开始
            borrowCredit.setRecoverPeriod(0);
        }
        logger.info("开始操作债转表---对象:{}  ",JSONObject.toJSONString(borrowCredit));
        // 操作数据库表
        return amTradeClient.insertCredit(borrowCredit);
    }

    /**
     * 债转各项金额计算
     * @param borrow
     * @param borrowRecover
     * @param creditDiscount
     * @param nowTime
     * @return
     */
    public Map<String, BigDecimal> selectExpectCreditFeeForBigDecimal(BorrowAndInfoVO borrow, BorrowRecoverVO borrowRecover, String creditDiscount, int nowTime) {
        Map<String, BigDecimal> resultMap = new HashMap<String, BigDecimal>();

        // 债转本息
        BigDecimal creditAccount = BigDecimal.ZERO;
        // 债转期全部利息
        BigDecimal creditInterest = BigDecimal.ZERO;
        // 垫付利息 垫息总额=债权本金*年化收益÷360*融资期限-债权本金*年化收益÷360*剩余期限
        BigDecimal assignInterestAdvance = BigDecimal.ZERO;
        // 债转利息
        BigDecimal assignPayInterest = BigDecimal.ZERO;
        // 实付金额 承接本金*（1-折价率）+应垫付利息
        BigDecimal assignPay = BigDecimal.ZERO;
        // 预计收益 承接人债转本息—实付金额
        BigDecimal assignInterest = BigDecimal.ZERO;
        // 预计收益 出让人预期收益 =本金+本金持有期利息-本金*折让率-服务费
        BigDecimal expectInterest = BigDecimal.ZERO;
        // 可转本金
        BigDecimal creditCapital = BigDecimal.ZERO;
        // 折后价格
        BigDecimal creditPrice = BigDecimal.ZERO;
        // 已发生债转的未还利息
        BigDecimal creditRepayInterestWait = BigDecimal.ZERO;

        String borrowStyle = borrow.getBorrowStyle();
        // 可转本金
        creditCapital = borrowRecover.getRecoverCapital().subtract(borrowRecover.getRecoverCapitalYes()).subtract(borrowRecover.getCreditAmount());
        // 折后价格
        creditPrice = creditCapital.multiply(new BigDecimal(1).subtract(new BigDecimal(creditDiscount).divide(new BigDecimal(100)))).setScale(2, BigDecimal.ROUND_DOWN);
        // 年利率
        BigDecimal yearRate = borrow.getBorrowApr().divide(new BigDecimal("100"));
        // 服务费
        BigDecimal creditFee = BigDecimal.ZERO;

        DebtConfigResponse response = amConfigClient.getDebtConfig();
        DebtConfigVO config = response.getResult();
        if(config==null){
            throw new CheckException(MsgEnum.ERROR_CREDIT_CONFIG_NULL_ERROR);
        }

        // 到期还本还息和按天计息，到期还本还息
        if (borrowStyle.equals(CalculatesUtil.STYLE_END) || borrowStyle.equals(CalculatesUtil.STYLE_ENDDAY)) {
            int lastDays = 0;
            try {
                String nowDate = GetDate.getDateTimeMyTimeInMillis(nowTime);
                String recoverDate = GetDate.getDateTimeMyTimeInMillis(Integer.valueOf(borrowRecover.getRecoverTime()));
                lastDays = GetDate.daysBetween(nowDate, recoverDate);
            } catch (Exception e) {
                throw new CheckException(MsgEnum.ERROR_CREDIT_DATA_ERROR);
            }
            // 剩余天数
            if (borrowStyle.equals(CalculatesUtil.STYLE_ENDDAY)) {// 按天
                // 债转本息
                creditAccount = DuePrincipalAndInterestUtils.getDayPrincipalInterest(creditCapital, yearRate, borrow.getBorrowPeriod());
                // 债转期全部利息
                creditInterest = DuePrincipalAndInterestUtils.getDayInterest(creditCapital, yearRate, borrow.getBorrowPeriod());
                // 垫付利息 垫息总额=债权本金*年化收益÷360*融资期限-债权本金*年化收益÷360*剩余期限
                assignInterestAdvance = DuePrincipalAndInterestUtils.getDayAssignInterestAdvance(creditCapital, yearRate, borrow.getBorrowPeriod(), new BigDecimal(lastDays + ""));
                // 债转利息
                assignPayInterest = creditInterest;
                // 实付金额 承接本金*（1-折价率）+应垫付利息
                assignPay = creditPrice.add(assignInterestAdvance);
                // 预计收益 承接人债转本息—实付金额
                assignInterest = creditAccount.subtract(assignPay);// 计算出借收益
                // 预计收益 出让人预期收益 =本金+本金持有期利息-本金*折让率-服务费
                expectInterest = creditCapital.add(assignInterestAdvance).subtract(creditCapital.multiply(new BigDecimal(creditDiscount).divide(new BigDecimal(100))))
                        .subtract(assignPay.multiply(config.getAttornRate().divide(new BigDecimal(100))).setScale(2, BigDecimal.ROUND_DOWN));
            } else {// 按月
                // 债转本息
                creditAccount = DuePrincipalAndInterestUtils.getMonthPrincipalInterest(creditCapital, yearRate, borrow.getBorrowPeriod());
                // 债转期全部利息
                creditInterest = DuePrincipalAndInterestUtils.getMonthInterest(creditCapital, yearRate, borrow.getBorrowPeriod());
                // 垫付利息 垫息总额=债权本金*年化收益÷12*融资期限-债权本金*年化收益÷360*剩余天数
                assignInterestAdvance = DuePrincipalAndInterestUtils.getMonthAssignInterestAdvance(creditCapital, yearRate, borrow.getBorrowPeriod(), new BigDecimal(lastDays + ""));
                // 债转利息
                assignPayInterest = creditInterest;
                // 实付金额 承接本金*（1-折价率）+应垫付利息
                assignPay = creditPrice.add(assignInterestAdvance);
                // 预计收益 承接人债转本息—实付金额
                assignInterest = creditAccount.subtract(assignPay);// 计算出借收益
                // 预计到账金额 出让人预期收益 =本金+本金持有期利息-本金*折让率-服务费
                expectInterest = creditCapital.add(assignInterestAdvance).subtract(creditCapital.multiply(new BigDecimal(creditDiscount).divide(new BigDecimal(100))))
                        .subtract(assignPay.multiply(config.getAttornRate().divide(new BigDecimal(100))).setScale(2, BigDecimal.ROUND_DOWN));
            }
        }
        // 等额本息和等额本金和先息后本
        if (borrowStyle.equals(CalculatesUtil.STYLE_MONTH) || borrowStyle.equals(CalculatesUtil.STYLE_PRINCIPAL) || borrowStyle.equals(CalculatesUtil.STYLE_ENDMONTH)) {
            // 根据出借订单号检索已债转还款信息
            List<CreditRepayVO> creditRepayList = amTradeClient.selectCreditRepayList(borrowRecover.getTenderId());
            int lastDays = 0;
            List<BorrowRepayPlanVO> borrowRepayPlans = this.amTradeClient.selectBorrowRepayPlan(borrow.getBorrowNid(), 0);
            if (borrowRepayPlans != null && borrowRepayPlans.size() > 0) {
                try {
                    String nowDate = GetDate.getDateTimeMyTimeInMillis(nowTime);
                    String recoverDate = GetDate.getDateTimeMyTimeInMillis(borrowRepayPlans.get(0).getRepayTime());
                    lastDays = GetDate.daysBetween(nowDate, recoverDate);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new CheckException(MsgEnum.ERROR_CREDIT_DATA_ERROR);
                }
            }
            // 债转本息
            creditAccount = BeforeInterestAfterPrincipalUtils.getPrincipalInterestCount(creditCapital, yearRate, borrowRecover.getRecoverPeriod(), borrowRecover.getRecoverPeriod());
            // 每月应还利息
            BigDecimal interest = BeforeInterestAfterPrincipalUtils.getPerTermInterest(creditCapital, borrow.getBorrowApr().divide(new BigDecimal(100)), borrowRecover.getRecoverPeriod(),
                    borrowRecover.getRecoverPeriod());
            // 债转期全部利息
            // creditInterest =
            // BeforeInterestAfterPrincipalUtils.getInterestCount(creditCapital,
            // yearRate, borrowRecover.getRecoverPeriod(),
            // borrowRecover.getRecoverPeriod());
            if (creditRepayList != null && creditRepayList.size() > 0) {
                for (CreditRepayVO creditRepay : creditRepayList) {
                    creditRepayInterestWait = creditRepayInterestWait.add(creditRepay.getAssignInterest());
                }
            }
            creditInterest = borrowRecover.getRecoverInterestWait().subtract(creditRepayInterestWait);
            // 垫付利息 垫息总额=出借人认购本金/出让人转让本金*出让人本期利息）-（债权本金*年化收益÷360*本期剩余天数
            assignInterestAdvance = BeforeInterestAfterPrincipalUtils.getAssignInterestAdvance(creditCapital, creditCapital, yearRate, interest, new BigDecimal(lastDays + ""));
            // 债转利息
            assignPayInterest = creditInterest;
            // 实付金额 承接本金*（1-折价率）+应垫付利息
            assignPay = creditPrice.add(assignInterestAdvance);
            // 预计收益 承接人债转本息—实付金额
            assignInterest = creditAccount.subtract(assignPay);// 计算出借收益
            // 预计到账金额 出让人预期收益 =本金+本金持有期利息-本金*折让率-服务费
            expectInterest = creditCapital.add(assignInterestAdvance).subtract(creditCapital.multiply(new BigDecimal(creditDiscount).divide(new BigDecimal(100))))
                    .subtract(assignPay.multiply(config.getAttornRate().divide(new BigDecimal(100))).setScale(2, BigDecimal.ROUND_DOWN));
        }
        // 服务费
        creditFee = assignPay.multiply(config.getAttornRate().divide(new BigDecimal(100))).setScale(2, BigDecimal.ROUND_DOWN);
        resultMap.put("creditAccount", creditAccount.setScale(2, BigDecimal.ROUND_DOWN));// 债转本息
        resultMap.put("creditInterest", creditInterest.setScale(2, BigDecimal.ROUND_DOWN));// 预计收益
        resultMap.put("assignInterestAdvance", assignInterestAdvance.setScale(2, BigDecimal.ROUND_DOWN));// 垫付利息
        resultMap.put("assignPayInterest", assignPayInterest.setScale(2, BigDecimal.ROUND_DOWN));// 债转利息
        resultMap.put("assignPay", assignPay.setScale(2, BigDecimal.ROUND_DOWN));// 实付金额
        resultMap.put("assignInterest", assignInterest.setScale(2, BigDecimal.ROUND_DOWN));// 债转期全部利息
        resultMap.put("creditCapital", creditCapital.setScale(2, BigDecimal.ROUND_DOWN));// 可转本金
        resultMap.put("creditPrice", creditPrice.setScale(2, BigDecimal.ROUND_DOWN));// 折后价格
        resultMap.put("expectInterest", expectInterest.setScale(2, BigDecimal.ROUND_DOWN));// 预计到账金额
        resultMap.put("creditFee", creditFee.setScale(2, BigDecimal.ROUND_DOWN));// 服务费
        return resultMap;
    }

    /**
     *  发起转让成功后,发送神策数据统计MQ
     *
     * @param sensorsDataBean
     */
    private void sendSensorsDataMQ(SensorsDataBean sensorsDataBean) throws MQException {
        this.commonProducer.messageSendDelay(new MessageContent(MQConstant.SENSORSDATA_CREDIT_TOPIC, UUID.randomUUID().toString(), sensorsDataBean), 2);
    }
}
