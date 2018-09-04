/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.borrow.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.resquest.user.BorrowFinmanNewChargeRequest;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.hjh.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.trade.hjh.HjhLabelVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.trade.client.AutoSendClient;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.mq.producer.MailProducer;
import com.hyjf.cs.trade.service.borrow.ApiAssetPushService;
import com.hyjf.cs.trade.service.borrow.AutoSendService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

/**
 * 自动录标
 *
 * @author fuqiang
 * @version AutoSendServiceImpl, v0.1 2018/6/12 17:05
 */
@Service
public class AutoSendServiceImpl extends BaseTradeServiceImpl implements AutoSendService {

    private static final Logger _log = LoggerFactory.getLogger(AutoSendServiceImpl.class);

    public static JedisPool pool = RedisUtils.getPool();

    @Value("${hyjf.env.test}")
    private Boolean env_test;

    @Value("${hyjf.alerm.email.test}")
    private static String emailList1;

    @Value("${hyjf.alerm.email}")
    private static String emaillist2;

    @Autowired
    private AutoSendClient autoSendClient;

    @Autowired
    private ApiAssetPushService apiAssetPushService;

    @Autowired
    private MailProducer mailProducer;
    /**
     * 邮件发送key
     */
    public static String LABEL_MAIL_KEY = "labelmailkey";

    @Override
    public boolean insertSendBorrow(HjhPlanAssetVO hjhPlanAssetVO, HjhAssetBorrowTypeVO hjhAssetBorrowTypeVO) throws Exception {
        // 验证资产风险保证金是否足够（redis）
        if (!checkAssetCanSend(hjhPlanAssetVO)) {
            _log.info("资产编号：" + hjhPlanAssetVO.getAssetId() + " 保证金不足");
            //add by cwyang 20180420 增加待补缴状态
            HjhPlanAssetVO planAssetVO = new HjhPlanAssetVO();
            planAssetVO.setId(hjhPlanAssetVO.getId());
            planAssetVO.setStatus(1);//待补缴保证金
            autoSendClient.updatePlanAsset(planAssetVO);
            //end
            return false;
        }

        // 获取管理费率，服务费率，自动发标费率
        // 项目类型(code):从ht_hjh_asset_borrowtype 取code 现金贷
        String projectCd = hjhAssetBorrowTypeVO.getBorrowCd() + "";
        String borrowClass = this.getBorrowProjectClass(projectCd);
        String queryBorrowStyle = null;
        // 费率配置表有点尴尬，还款方式只区分了天和月
        if ("endday".equals(hjhPlanAssetVO.getBorrowStyle())) {//天标
            queryBorrowStyle = "endday";
        } else {
            queryBorrowStyle = "month";
        }
        BorrowFinmanNewChargeRequest request = new BorrowFinmanNewChargeRequest();
        request.setBorrowClass(borrowClass);
        request.setInstCode(hjhPlanAssetVO.getInstCode());
        request.setAssetType(hjhPlanAssetVO.getAssetType());
        request.setBorrowPeriod(hjhPlanAssetVO.getBorrowPeriod());
        request.setQueryBorrowStyle(queryBorrowStyle);
        BorrowFinmanNewChargeVO borrowFinmanNewChargeVO = autoSendClient.selectBorrowApr(request);
        if (borrowFinmanNewChargeVO == null || borrowFinmanNewChargeVO.getAutoBorrowApr() == null) {
            _log.info("资产编号：" + hjhPlanAssetVO.getAssetId() + " 录标失败 ,没有取到项目费率");
            return false;
        }

        // 录标 a. 根据表配置判断发标项目类型
        if (!insertRecord(hjhPlanAssetVO, hjhAssetBorrowTypeVO, borrowFinmanNewChargeVO)) {
            _log.info("资产编号：" + hjhPlanAssetVO.getAssetId() + " 录标失败");
            return false;
        }

        return true;
    }

    /**
     * 验证资产风险保证金是否足够（redis）
     *
     * @param hjhPlanAssetVO
     * @return
     */
    private boolean checkAssetCanSend(HjhPlanAssetVO hjhPlanAssetVO) {
        String instCode = hjhPlanAssetVO.getInstCode();
        if (!RedisUtils.exists(RedisConstants.CAPITAL_TOPLIMIT_ + instCode)) {
            List<HjhInstConfigVO> list = autoSendClient.selectHjhInstConfigByInstCode(hjhPlanAssetVO.getInstCode());
            BigDecimal capitalToplimit = null;
            if (list != null && list.size() > 0) {
                capitalToplimit = list.get(0).getCapitalToplimit();
            }
            if (capitalToplimit != null) {
                RedisUtils.set(RedisConstants.CAPITAL_TOPLIMIT_ + instCode, capitalToplimit.toString());
            }
        }

        String capitalToplimit = RedisUtils.get(RedisConstants.CAPITAL_TOPLIMIT_ + instCode);
        BigDecimal lcapitalToplimit = new BigDecimal(capitalToplimit);
        BigDecimal assetAcount = new BigDecimal(hjhPlanAssetVO.getAccount());

        if (BigDecimal.ZERO.compareTo(lcapitalToplimit) >= 0) {
            _log.info("资产编号：" + hjhPlanAssetVO.getAssetId() + " 风险保证金小于等于零 " + capitalToplimit);
            // 风险保证金小于等于0不能发标
            return false;
        }

        if (assetAcount.compareTo(lcapitalToplimit) > 0) {
            // 风险保证金不够不能发标
            return false;
        }

        return true;
    }

    /**
     * 项目类型
     *
     * @return
     * @author Administrator
     */
    private String getBorrowProjectClass(String borrowCd) {

        List<BorrowProjectTypeVO> list = autoSendClient.selectBorrowProjectByBorrowCd(borrowCd);
        if (list != null && list.size() > 0) {
            return list.get(0).getBorrowClass();
        }
        return "";
    }

    /**
     * 录标
     *
     * @param hjhPlanAssetVO
     * @throws Exception
     */
    private boolean insertRecord(HjhPlanAssetVO hjhPlanAssetVO, HjhAssetBorrowTypeVO hjhAssetBorrowTypeVO, BorrowFinmanNewChargeVO borrowFinmanNewChargeVO) throws Exception {

        boolean result = false;
        // borrow_class
        String beforeFix = borrowFinmanNewChargeVO.getProjectType();

        BorrowVO borrowVO = this.setBorrowCommonData(hjhPlanAssetVO, hjhAssetBorrowTypeVO, borrowFinmanNewChargeVO);


        // 获取标签ID
        HjhLabelVO label = apiAssetPushService.getLabelId(borrowVO, hjhPlanAssetVO);
        if (label == null || label.getId() == null) {
            _log.info(hjhPlanAssetVO.getAssetId() + " 没有获取到标签");
            /**汇计划三期邮件预警 BY LIBIN start*/
            // 如果redis不存在这个KEY(一天有效期)，那么可以发邮件
            if(!RedisUtils.exists(LABEL_MAIL_KEY + hjhPlanAssetVO.getAssetId())){
                StringBuffer msg = new StringBuffer();
                msg.append("资产ID：").append(hjhPlanAssetVO.getAssetId()).append("<br/>");
                msg.append("当前时间：").append(GetDate.formatTime()).append("<br/>");
                msg.append("错误信息：").append("该资产在自动录标时未打上标签！").append("<br/>");
                // 邮箱集合
                _log.info("自动录标时未打上标签环境 evn_test is test ? " + env_test);
                String emailList= "";
                if (env_test){
                    emailList = emailList1;
                }else{
                    emailList = emaillist2;
                }
                String [] toMail = emailList.split(",");
                MailMessage message = new MailMessage(null, null, "资产ID为：" + hjhPlanAssetVO.getAssetId(), msg.toString(), null, toMail, null,
                        MessageConstant.MAILSENDFORMAILINGADDRESSMSG);
                mailProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC,UUID.randomUUID().toString(), JSON.toJSONBytes(message)));
                    // String key, String value, int expireSeconds
                    RedisUtils.set(LABEL_MAIL_KEY + hjhPlanAssetVO.getAssetId(), hjhPlanAssetVO.getAssetId(), 24 * 60 * 60);
            } else {
                _log.info("此邮件key值还未过期(一天)");
            }
            /**汇计划三期邮件预警 BY LIBIN end*/
            return result;
        }

        // 获取下一个标的编号
        String borrowPreNidNew = getNextBorrowNid();

        // 标签ID
       // borrowVO.setLabelId(label.getId());

        // 默认使用引擎
        borrowVO.setIsEngineUsed(1);

        String borrowNid = beforeFix + borrowPreNidNew;
        //项目标题
        //borrowVO.setProjectName(borrowNid);
        // 借款编号
       // borrowVO.setBorrowNid(borrowNid);
        // 借款预编码
       // borrowVO.setBorrowPreNid(borrowPreNidNew);
        // 新借款预编码
       // borrowVO.setBorrowPreNidNew(borrowPreNidNew);


        // 借款表插入
        autoSendClient.insertSelective(borrowVO);
        // 个人信息
        if(hjhPlanAssetVO.getBorrowCompanyName() == null || hjhPlanAssetVO.getBorrowCompanyName().equals("")){
            // 个人信息
            this.insertBorrowManinfo(borrowNid, hjhPlanAssetVO, borrowVO);
        }else{
            //企业信息
            this.insertBorrowCompanyManinfo(borrowNid, hjhPlanAssetVO, borrowVO);
        }

        // 更新资产表
        HjhPlanAssetVO hjhPlanAssetnewVO = new HjhPlanAssetVO();
        hjhPlanAssetnewVO.setId(hjhPlanAssetVO.getId());
        // 标的编号，计划编号在关联资产更新
        hjhPlanAssetnewVO.setBorrowNid(borrowNid);
        hjhPlanAssetnewVO.setLabelId(label.getId());
        hjhPlanAssetnewVO.setLabelName(label.getLabelName());

        hjhPlanAssetnewVO.setStatus(3);//备案中
        //获取当前时间
        int nowTime = GetDate.getNowTime10();
        hjhPlanAssetnewVO.setUpdateTime(nowTime);
        hjhPlanAssetnewVO.setUpdateUserId(1);
        boolean borrowFlag = autoSendClient.updateHjhPlanAssetnew(hjhPlanAssetnewVO) > 0 ? true : false;
        if (borrowFlag) {
            result = true;
        }

        return result;
    }

    /**
     * 个人信息
     * @param borrowNid
     * @param hjhPlanAssetVO
     * @param borrowVO
     */
    private int insertBorrowManinfo(String borrowNid, HjhPlanAssetVO hjhPlanAssetVO, BorrowVO borrowVO) {
        BorrowManinfoVO borrowManinfo = new BorrowManinfoVO();

        borrowManinfo.setBorrowNid(borrowNid);
       // borrowManinfo.setBorrowPreNid(borrowVO.getBorrowPreNid());
        // 姓名
        if (StringUtils.isNotEmpty(hjhPlanAssetVO.getTruename())) {
            borrowManinfo.setName(hjhPlanAssetVO.getTruename());
        } else {
            borrowManinfo.setName(StringUtils.EMPTY);
        }
        // 性别
        if (hjhPlanAssetVO.getSex() != null) {
            borrowManinfo.setSex(hjhPlanAssetVO.getSex());
        } else {
            borrowManinfo.setSex(0);
        }
        // 年龄
        if (hjhPlanAssetVO.getAge() != null) {
            borrowManinfo.setOld(hjhPlanAssetVO.getAge());
        } else {
            borrowManinfo.setOld(0);
        }
        // 婚姻
        if (hjhPlanAssetVO.getMarriage() != null) {
            borrowManinfo.setMerry(hjhPlanAssetVO.getMarriage());
        } else {
            borrowManinfo.setMerry(0);
        }
        // 岗位职业
        if (StringUtils.isNotEmpty(hjhPlanAssetVO.getPosition())) {
            borrowManinfo.setPosition(hjhPlanAssetVO.getPosition());
        }
        // 省
//		if (StringUtils.isNotEmpty(hjhPlanAssetVO.getLocation_p())) {
//			borrowManinfo.setPro(hjhPlanAssetVO.getLocation_p());
//		} else {
//			borrowManinfo.setPro(StringUtils.EMPTY);
//		}
        // 市
        if (StringUtils.isNotEmpty(hjhPlanAssetVO.getWorkCity())) {
            borrowManinfo.setCity(hjhPlanAssetVO.getWorkCity());
        } else {
            borrowManinfo.setCity(StringUtils.EMPTY);
        }

        // 公司规模
        borrowManinfo.setSize(StringUtils.EMPTY);

        // 公司月营业额
        borrowManinfo.setBusiness(BigDecimal.ZERO);

        // 行业
//		if (StringUtils.isNotEmpty(borrowBean.getIndustry())) {
//			borrowManinfo.setIndustry(borrowBean.getIndustry());
//		} else {
//			borrowManinfo.setIndustry(StringUtils.EMPTY);
//		}

        // 现单位工作时间
//		if (StringUtils.isNotEmpty(borrowBean.getWtime())) {
//			borrowManinfo.setWtime(borrowBean.getWtime());
//		} else {
//			borrowManinfo.setWtime(StringUtils.EMPTY);
//		}

        // 授信额度
//		if (StringUtils.isNotEmpty(borrowBean.getUserCredit())) {
//			borrowManinfo.setCredit(Integer.valueOf((borrowBean.getUserCredit())));
//		} else {
//			borrowManinfo.setCredit(0);
//		}
        //身份证号
        if(StringUtils.isNotEmpty(hjhPlanAssetVO.getIdcard())){
            borrowManinfo.setCardNo(hjhPlanAssetVO.getIdcard());
        }else{
            borrowManinfo.setCardNo(StringUtils.EMPTY);
        }
        //户籍地
        if(StringUtils.isNotEmpty(hjhPlanAssetVO.getDomicile())){
            borrowManinfo.setDomicile(hjhPlanAssetVO.getDomicile());
        }else{
            borrowManinfo.setDomicile(StringUtils.EMPTY);
        }
        //在平台逾期次数
        if(StringUtils.isNotEmpty(hjhPlanAssetVO.getOverdueTimes())){
            borrowManinfo.setOverdueTimes(hjhPlanAssetVO.getOverdueTimes());
        }else{
            borrowManinfo.setOverdueTimes(StringUtils.EMPTY);
        }
        //在平台逾期金额
        if(StringUtils.isNotEmpty(hjhPlanAssetVO.getOverdueAmount())){
            borrowManinfo.setOverdueAmount(hjhPlanAssetVO.getOverdueAmount());
        }else{
            borrowManinfo.setOverdueAmount(StringUtils.EMPTY);
        }
        //涉诉情况
        if(StringUtils.isNotEmpty(hjhPlanAssetVO.getLitigation())){
            borrowManinfo.setLitigation(hjhPlanAssetVO.getLitigation());
        }else{
            borrowManinfo.setLitigation(StringUtils.EMPTY);
        }


        //个贷审核信息 身份证 0未审核 1已审核
        borrowManinfo.setIsCard(1);
        //个贷审核信息 收入状况 0未审核 1已审核
        borrowManinfo.setIsIncome(1);
        //个贷审核信息 信用状况 0未审核 1已审核
        borrowManinfo.setIsCredit(1);
        //个贷审核信息 婚姻状况 0未审核 1已审核
        borrowManinfo.setIsMerry(1);
        //个贷审核信息 工作状况 0未审核 1已审核
        borrowManinfo.setIsWork(1);

        //个贷审核信息 资产状况 0未审核 1已审核
        borrowManinfo.setIsAsset(0);
        //个贷审核信息 车辆状况0未审核 1已审核
        borrowManinfo.setIsVehicle(0);
        //个贷审核信息 行驶证 0未审核 1已审核
        borrowManinfo.setIsDrivingLicense(0);
        //个贷审核信息 车辆登记证 0未审核 1已审核
        borrowManinfo.setIsVehicleRegistration(0);
        //个贷审核信息 车辆登记证 0未审核 1已审核
        borrowManinfo.setIsVehicleRegistration(0);
        //个贷审核信息 户口本 0未审核 1已审核
        borrowManinfo.setIsAccountBook(0);

        //(个人)年收入
        if(StringUtils.isNotEmpty(hjhPlanAssetVO.getAnnualIncome())){
            borrowManinfo.setAnnualIncome(hjhPlanAssetVO.getAnnualIncome());
        }else{
            borrowManinfo.setAnnualIncome("10万以内");
        }
        //(个人)征信报告逾期情况
        if(StringUtils.isNotEmpty(hjhPlanAssetVO.getOverdueReport())){
            borrowManinfo.setOverdueReport(hjhPlanAssetVO.getOverdueReport());
        }else{
            borrowManinfo.setOverdueReport("暂无数据");
        }
        //(个人)重大负债状况
        if(StringUtils.isNotEmpty(hjhPlanAssetVO.getDebtSituation())){
            borrowManinfo.setDebtSituation(hjhPlanAssetVO.getDebtSituation());
        }else{
            borrowManinfo.setDebtSituation("无");
        }
        //(个人)其他平台借款情况
        if(StringUtils.isNotEmpty(hjhPlanAssetVO.getOtherBorrowed())){
            borrowManinfo.setOtherBorrowed(hjhPlanAssetVO.getOtherBorrowed());
        }else{
            borrowManinfo.setOtherBorrowed("暂无数据");
        }
        //(个人)借款资金运用情况
        if(StringUtils.isNotEmpty(hjhPlanAssetVO.getIsFunds())){
            borrowManinfo.setIsFunds(hjhPlanAssetVO.getIsFunds());
        }else{
            borrowManinfo.setIsFunds("正常");
        }
        //(个人)借款方经营状况及财务状况
        if(StringUtils.isNotEmpty(hjhPlanAssetVO.getIsManaged())){
            borrowManinfo.setIsManaged(hjhPlanAssetVO.getIsManaged());
        }else{
            borrowManinfo.setIsManaged("正常");
        }
        //(个人)借款方还款能力变化情况
        if(StringUtils.isNotEmpty(hjhPlanAssetVO.getIsAbility())){
            borrowManinfo.setIsAbility(hjhPlanAssetVO.getIsAbility());
        }else{
            borrowManinfo.setIsAbility("正常");
        }
        //(个人)借款方逾期情况
        if(StringUtils.isNotEmpty(hjhPlanAssetVO.getIsOverdue())){
            borrowManinfo.setIsOverdue(hjhPlanAssetVO.getIsOverdue());
        }else{
            borrowManinfo.setIsOverdue("暂无");
        }
        //(个人)借款方涉诉情况
        if(StringUtils.isNotEmpty(hjhPlanAssetVO.getIsComplaint())){
            borrowManinfo.setIsComplaint(hjhPlanAssetVO.getIsComplaint());
        }else{
            borrowManinfo.setIsComplaint("暂无");
        }
        //(个人)借款方受行政处罚情况
        if(StringUtils.isNotEmpty(hjhPlanAssetVO.getIsPunished())){
            borrowManinfo.setIsPunished(hjhPlanAssetVO.getIsPunished());
        }else{
            borrowManinfo.setIsPunished("暂无");
        }
        //借款人地址
        borrowManinfo.setAddress(hjhPlanAssetVO.getAddress());
        autoSendClient.insertBorrowManinfo(borrowManinfo);
        return 0;
    }

    /**
     * 企业信息
     * @param borrowNid
     * @param hjhPlanAsset
     * @param borrow
     * @return
     */
    public int insertBorrowCompanyManinfo(String borrowNid, HjhPlanAssetVO hjhPlanAsset, BorrowVO borrow) {
        _log.info("插入企业信息", JSONObject.toJSON(hjhPlanAsset));
        // 公司信息
        BorrowUserVO borrowUsers = new BorrowUserVO();

        borrowUsers.setBorrowNid(borrowNid);
        borrowUsers.setBorrowPreNid(borrow.getBorrowPreNid());

        if (StringUtils.isNotEmpty(hjhPlanAsset.getBorrowCompanyName())) {
            borrowUsers.setUsername(hjhPlanAsset.getBorrowCompanyName());
        } else {
            borrowUsers.setUsername(StringUtils.EMPTY);
        }
        //注册资本货币单位 	元-美元
        borrowUsers.setCurrencyName("元");

        if (StringUtils.isNotEmpty(hjhPlanAsset.getRegisteredCapital())) {
            borrowUsers.setRegCaptial(hjhPlanAsset.getRegisteredCapital());
        } else {
            borrowUsers.setRegCaptial(StringUtils.EMPTY);
        }

        if (StringUtils.isNotEmpty(hjhPlanAsset.getIndustryInvolved())) {
            borrowUsers.setIndustry(hjhPlanAsset.getIndustryInvolved());
        } else {
            borrowUsers.setIndustry(StringUtils.EMPTY);
        }

        if (StringUtils.isNotEmpty(hjhPlanAsset.getIsComplaint())) {
            borrowUsers.setLitigation(hjhPlanAsset.getIsComplaint());
        } else {
            borrowUsers.setLitigation(StringUtils.EMPTY);
        }

        if (StringUtils.isNotEmpty(hjhPlanAsset.getOverdueReport())) {
            borrowUsers.setCreReport(hjhPlanAsset.getOverdueReport());
        } else {
            borrowUsers.setCreReport(StringUtils.EMPTY);
        }

        if (StringUtils.isNotEmpty(hjhPlanAsset.getRegistrationDate())) {
            borrowUsers.setComRegTime(hjhPlanAsset.getRegistrationDate());
        } else {
            borrowUsers.setComRegTime(StringUtils.EMPTY);
        }
        //统一社会信用代码
        if (StringUtils.isNotEmpty(hjhPlanAsset.getUnifiedSocialCreditCode())) {
            borrowUsers.setSocialCreditCode(hjhPlanAsset.getUnifiedSocialCreditCode());
        } else {
            borrowUsers.setSocialCreditCode(StringUtils.EMPTY);
        }
        //法人
        if (StringUtils.isNotEmpty(hjhPlanAsset.getLegalPerson())) {
            borrowUsers.setLegalPerson(hjhPlanAsset.getLegalPerson());
        } else {
            borrowUsers.setLegalPerson(StringUtils.EMPTY);
        }
        //主营业务
        if (StringUtils.isNotEmpty(hjhPlanAsset.getMainBusiness())) {
            borrowUsers.setMainBusiness(hjhPlanAsset.getMainBusiness());
        } else {
            borrowUsers.setMainBusiness(StringUtils.EMPTY);
        }
        //在平台逾期次数
        if (StringUtils.isNotEmpty(hjhPlanAsset.getOverdueTimes())) {
            borrowUsers.setOverdueTimes(hjhPlanAsset.getOverdueTimes());
        } else {
            borrowUsers.setOverdueTimes(StringUtils.EMPTY);
        }
        //在平台逾期金额
        if (StringUtils.isNotEmpty(hjhPlanAsset.getOverdueAmount())) {
            borrowUsers.setOverdueAmount(hjhPlanAsset.getOverdueAmount());
        } else {
            borrowUsers.setOverdueAmount(StringUtils.EMPTY);
        }

        //企贷审核信息  0未审核 1已审核
        //企业证件
        borrowUsers.setIsCertificate(1);

        if (StringUtils.isNotEmpty(hjhPlanAsset.getIsManaged())) {
            borrowUsers.setIsOperation(Integer.valueOf(1));
        } else {
            borrowUsers.setIsOperation(0);
        }
        if (StringUtils.isNotEmpty(hjhPlanAsset.getFinancialSituation())) {
            borrowUsers.setIsFinance(1);
        } else {
            borrowUsers.setIsFinance(0);
        }
        //企业信用
        borrowUsers.setIsEnterpriseCreidt(1);
        //法人信息
        borrowUsers.setIsLegalPerson(1);
        //资产状况
        borrowUsers.setIsAsset(1);
        //购销合同
        borrowUsers.setIsPurchaseContract(1);
        //供销合同
        borrowUsers.setIsSupplyContract("1");
        //征信报告逾期情况
        borrowUsers.setOverdueReport("暂未提供");
        //重大负债情况
        borrowUsers.setDebtSituation("无");
        //其他平台借款情况
        borrowUsers.setOtherBorrowed("无");
        //借款资金运用情况
        borrowUsers.setIsFunds("正常");
        //借款人经营状况及财务状况
        borrowUsers.setIsManaged("正常");
        //借款人还款能力变化情况
        borrowUsers.setIsAbility("正常");
        //借款人逾期情况
        borrowUsers.setIsOverdue("暂无");
        //借款人涉诉情况
        borrowUsers.setIsComplaint("暂无");
        //借款人受行政处罚情况
        borrowUsers.setIsPunished("暂无");
        // add by nxl 20180808 互金系统添加企业注册地址地址和企业注册编码 Start
        if (StringUtils.isNotBlank(hjhPlanAsset.getRegistrationAddress())) {
            borrowUsers.setRegistrationAddress(hjhPlanAsset.getRegistrationAddress());
        }
        if (StringUtils.isNotBlank(hjhPlanAsset.getCorporateCode())) {
            borrowUsers.setCorporateCode(hjhPlanAsset.getCorporateCode());
        }
        // add by nxl 20180808 互金系统添加企业注册地址地址和企业注册编码 End
        //添加操作
        return amTradeClient.insertCompanyInfoToBorrowUsers(borrowUsers);
    }

    /**
     * 借款表插入
     *
     * @param hjhPlanAssetVO
     * @param hjhPlanAssetVO
     * @return
     * @throws Exception
     */
    private BorrowVO setBorrowCommonData(HjhPlanAssetVO hjhPlanAssetVO, HjhAssetBorrowTypeVO hjhAssetBorrowTypeVO, BorrowFinmanNewChargeVO borrowFinmanNewChargeVO)
            throws Exception {

        // 插入huiyingdai_borrow
        BorrowVO borrow = new BorrowVO();

        // 关联计划
        borrow.setIsShow(1); // 默认不展示

        borrow.setInstCode(hjhPlanAssetVO.getInstCode());
        borrow.setAssetType(hjhAssetBorrowTypeVO.getAssetType());

        // 受托支付
        if (hjhPlanAssetVO.getEntrustedFlg() != null && hjhPlanAssetVO.getEntrustedFlg().intValue() == 1) {
            borrow.setEntrustedFlg(1);
            borrow.setEntrustedUserId(hjhPlanAssetVO.getEntrustedUserId());
            borrow.setEntrustedUserName(hjhPlanAssetVO.getEntrustedUserName());
        }

        // 插入时间
        Date systemNowDate = GetDate.getDate();
        // 添加时间
        String addtime = String.valueOf(GetDate.getNowTime10());
        // 根据项目类型设置下列
        BorrowProjectTypeVO borrowProjectType = getProjectType(hjhAssetBorrowTypeVO.getBorrowCd() + "");
        borrow.setBorrowIncreaseMoney(borrowProjectType.getIncreaseMoney()); //递增投资金额
        borrow.setBorrowInterestCoupon(borrowProjectType.getInterestCoupon());
        borrow.setBorrowTasteMoney(borrowProjectType.getTasteMoney());//体验金

        borrow.setUserId(hjhPlanAssetVO.getUserId());
        // 借款人用户名
        borrow.setBorrowUserName(hjhPlanAssetVO.getUserName());

        // 项目申请人
//		String applicant = hjhAssetBorrowTypeVO.getApplicant();
        String repayOrgName = hjhAssetBorrowTypeVO.getRepayOrgName();
        borrow.setApplicant(hjhPlanAssetVO.getIdcard());

        // 垫付机构用户名不为空的情况
        if (StringUtils.isNotEmpty(repayOrgName)) {
            // 根据垫付机构用户名检索垫付机构用户ID
            List<UserVO> ulist = autoSendClient.selectUserByUsername(repayOrgName);
            // 如果用户名不存在，返回错误信息。
            if (ulist == null || ulist.size() == 0) {
                throw new Exception("获取垫付机构失败,垫付机构名称:" + repayOrgName);
            }
            Integer userId = ulist.get(0).getUserId();
            borrow.setRepayOrgUserId(userId);
            borrow.setIsRepayOrgFlag(1);
            // 垫付机构用户名
            borrow.setRepayOrgName(repayOrgName);
        } else {
            borrow.setRepayOrgUserId(0);
            borrow.setIsRepayOrgFlag(0);
        }

        // 借款标题
        borrow.setName("个人短期借款");
        // 状态
        borrow.setStatus(0);
        // 图片信息
        borrow.setBorrowPic("");
        // 点击次数
        borrow.setHits(0);
        borrow.setCommentCount(0);// 插入时不用的字段
        //新标（20170612改版后都为新标）
        borrow.setIsNew(1);

        // 借款方式
        // 车辆抵押:2 房产抵押:1
//		if (StringUtils.equals("2", borrowBean.getTypeCar())) {
//			borrow.setType(borrowBean.getTypeCar());
//		}
//
//		if (StringUtils.equals("1", borrowBean.getTypeHouse())) {
//			borrow.setType(borrowBean.getTypeHouse());
//		}
//
//		if (StringUtils.equals("2", borrowBean.getTypeCar()) && StringUtils.equals("1", borrowBean.getTypeHouse())) {
//			borrow.setType("3");
//		}

        if (StringUtils.isEmpty(borrow.getType())) {
            borrow.setType("0");
        }

        borrow.setViewType("");// 插入时不用的字段
        // 添加时间
        borrow.setAddtime(addtime);
        // 添加IP
        borrow.setAddip("localhost");
        // 冻结额度
        borrow.setAmountAccount(new BigDecimal(hjhPlanAssetVO.getAccount()));
        borrow.setAmountType("credit");// 插入时不用的字段
        borrow.setCashStatus(0);// 插入时不用的字段
        // 借款总金额
        borrow.setAccount(new BigDecimal(hjhPlanAssetVO.getAccount()));
        borrow.setBorrowAccountWait(new BigDecimal(hjhPlanAssetVO.getAccount()));
        borrow.setBorrowAccountWaitAppoint(new BigDecimal(hjhPlanAssetVO.getAccount()));
        borrow.setOtherWebStatus(0);// 插入时不用的字段

        // 财务状况
//		if (StringUtils.isEmpty(hjhPlanAssetVO.getAccountContents())) {
//			borrow.setAccountContents(StringUtils.EMPTY);
//		} else {
//			borrow.setAccountContents(hjhPlanAssetVO.getAccountContents());
//		}
        //borrow.setAccountContents(StringUtils.EMPTY);
        borrow.setBorrowType("credit");// 插入时不用的字段
        borrow.setBorrowPassword("");// 插入时不用的字段
        borrow.setBorrowFlag("");// 插入时不用的字段
        // 是否可以进行借款
        borrow.setBorrowStatus(0);
        // 满表审核状态
        borrow.setBorrowFullStatus(0);
        // 已经募集的金额
        borrow.setBorrowAccountYes(BigDecimal.ZERO);
        // // 剩余的金额
        // borrow.setBorrowAccountWait(BigDecimal.ZERO);
        // 募集完成率
        borrow.setBorrowAccountScale(BigDecimal.ZERO);
        // 还款方式
        borrow.setBorrowStyle(hjhPlanAssetVO.getBorrowStyle());
        // 借款期限
        borrow.setBorrowPeriod(hjhPlanAssetVO.getBorrowPeriod());
        borrow.setBorrowPeriodRoam(0);// 插入时不用的字段
        borrow.setBorrowDay(0);// 插入时不用的字段
        // 借款利率
        borrow.setBorrowApr(new BigDecimal(borrowFinmanNewChargeVO.getAutoBorrowApr()).multiply(new BigDecimal(100)));
        borrow.setLateInterestRate(borrowFinmanNewChargeVO.getLateInterest()); //逾期利率(汇计划用)late_interest_rate
        borrow.setLateFreeDays(borrowFinmanNewChargeVO.getLateFreeDays()); // 逾期免息天数(汇计划用)late_free_days

        // 项目描述
        //borrow.setBorrowContents(hjhPlanAssetVO.getAssetInfo());
        // 新增协议期限字段
//		if (StringUtils.isNotEmpty(borrowBean.getContractPeriod())) {
//			borrow.setContractPeriod(Integer.parseInt(borrowBean.getContractPeriod()));
//		}
        // 信用评级
        if (StringUtils.isNotEmpty(hjhPlanAssetVO.getCreditLevel())) {
            borrow.setBorrowLevel(hjhPlanAssetVO.getCreditLevel());
        }
        // ----------风险缓释金添加-------
        // 资产编号
//		borrow.setBorrowAssetNumber(borrowBean.getBorrowAssetNumber());
//		// 项目来源
//		borrow.setBorrowProjectSource(borrowBean.getBorrowProjectSource());
//		// 起息时间
//		borrow.setBorrowInterestTime(borrowBean.getBorrowInterestTime());
//		// 到期时间
//		borrow.setBorrowDueTime(borrowBean.getBorrowDueTime());
//		// 保障方式
//		borrow.setBorrowSafeguardWay(borrowBean.getBorrowSafeguardWay());
//		// 收益说明
//		borrow.setBorrowIncomeDescription(borrowBean.getBorrowIncomeDescription());
//		// 发行人
//		borrow.setBorrowPublisher(borrowBean.getBorrowPublisher());
        // 产品加息收益率
//		borrow.setBorrowExtraYield(new BigDecimal(StringUtils.isNotEmpty(borrowBean.getBorrowExtraYield()) ? borrowBean.getBorrowExtraYield() : "0"));
        // ----------风险缓释金添加 end-------

        /**************网站改版添加 ******************/
        //融资用途
        borrow.setFinancePurpose(hjhPlanAssetVO.getUseage());
        // 平台直接默认填写：借款用途
        borrow.setBorrowUse(hjhPlanAssetVO.getUseage());

        //月薪收入
        borrow.setMonthlyIncome(hjhPlanAssetVO.getMonthlyIncome());
        //还款来源
//		borrow.setPayment(hjhPlanAssetVO.getPayment());
        //第一还款来源
        borrow.setFirstPayment(hjhPlanAssetVO.getFirstPayment());
        //第二还款来源
        borrow.setSecondPayment(hjhPlanAssetVO.getSecondPayment());
        //费用说明
        borrow.setCostIntrodution(hjhPlanAssetVO.getCostIntrodution());
        //财务状况
//		borrow.setFianceCondition(hjhPlanAssetVO.getFianceCondition());
        /**************网站改版添加end ******************/

        borrow.setBorrowFrostAccount(BigDecimal.ZERO);// 插入时不用的字段
        borrow.setBorrowFrostScale("");// 插入时不用的字段
        borrow.setBorrowFrostSecond(BigDecimal.ZERO);// 插入时不用的字段

        // 借款有效时间
        borrow.setBorrowValidTime(Integer.parseInt(getBorrowConfig("BORROW_VALID_TIME")));
        borrow.setRegistStatus(0);// 银行备案状态
        // 银行备案时间
        borrow.setBankRegistDays(Integer.parseInt(getBorrowConfig("BORROW_REGIST_DAYS")));
        borrow.setRepayStatus(0);// 标的还款状态
        // 银行募集开始时间
        String rasieStartDate = GetOrderIdUtils.getOrderDate();
        borrow.setBankRaiseStartDate(rasieStartDate);
        // 银行募集结束时间
        String raiseEndDate = this.getBankRaiseEndDate(rasieStartDate, borrow.getBankRegistDays(), borrow.getBorrowValidTime());
        borrow.setBankRaiseEndDate(raiseEndDate);
        // 银行用借款期限
        if (borrow.getBorrowStyle().equals(CustomConstants.BORROW_STYLE_ENDDAY)) {
            borrow.setBankBorrowDays(borrow.getBorrowPeriod());
        }
        /** 月标直接写死每月30天，银行不校验.计算过于麻烦 */
        else {
            borrow.setBankBorrowDays(borrow.getBorrowPeriod() * 30);
        }
        // 借款成功时间
        borrow.setBorrowSuccessTime(0);
        // 借款到期时间
        borrow.setBorrowEndTime("");
        borrow.setBorrowPartStatus(0);// 插入时不用的字段
        //borrow.setBorrowUpfiles("");// 插入时不用的字段
        borrow.setCancelUserid(0);// 插入时不用的字段
        borrow.setCancelStatus(0);// 插入时不用的字段
        borrow.setCancelTime("");// 插入时不用的字段
        borrow.setCancelRemark("");// 插入时不用的字段
        borrow.setCancelContents("");// 插入时不用的字段

        // 最低投标金额
        if (StringUtils.isNotEmpty(borrowProjectType.getInvestStart())) {
            borrow.setTenderAccountMin(Integer.valueOf(borrowProjectType.getInvestStart()));
        } else {
            borrow.setTenderAccountMin(0);
        }

        // 最高投标金额
        if (StringUtils.isNotEmpty(borrowProjectType.getInvestEnd())) {
            borrow.setTenderAccountMax(Integer.valueOf(borrowProjectType.getInvestEnd()));
        } else {
            borrow.setTenderAccountMax(0);
        }
        // 投标次数
        borrow.setTenderTimes(0);
        // 最后投资时间
        borrow.setTenderLastTime("");
        borrow.setRepayAdvanceStatus(0);// 插入时不用的字段
        borrow.setRepayAdvanceTime("");// 插入时不用的字段
        borrow.setRepayAdvanceStep(0);// 插入时不用的字段
        // 应还款总额
        borrow.setRepayAccountAll(BigDecimal.ZERO);
        // 总还款利息
        borrow.setRepayAccountInterest(BigDecimal.ZERO);
        // 总还款本金
        borrow.setRepayAccountCapital(BigDecimal.ZERO);
        // 已还款总额
        borrow.setRepayAccountYes(BigDecimal.ZERO);
        // 已还款利息
        borrow.setRepayAccountInterestYes(BigDecimal.ZERO);
        // 已还款本金
        borrow.setRepayAccountCapitalYes(BigDecimal.ZERO);
        // 未还款总额
        borrow.setRepayAccountWait(BigDecimal.ZERO);
        // 未还款利息
        borrow.setRepayAccountInterestWait(BigDecimal.ZERO);
        // 未还款本金
        borrow.setRepayAccountCapitalWait(BigDecimal.ZERO);
        borrow.setRepayAccountTimes(0);// 插入时不用的字段
        borrow.setRepayMonthAccount(0);// 插入时不用的字段
        // 最后还款时间
        borrow.setRepayLastTime("");// 插入时不用的字段
        borrow.setRepayEachTime("");// 插入时不用的字段
        borrow.setRepayNextTime(0);// 插入时不用的字段
        borrow.setRepayNextAccount(BigDecimal.ZERO);// 插入时不用的字段
        // 还款次数
        borrow.setRepayTimes(0);
        borrow.setRepayFullStatus(0);// 插入时不用的字段
        borrow.setRepayFeeNormal(BigDecimal.ZERO); // 插入时不用的字段
        // 正常还款费用
        borrow.setRepayFeeAdvance(BigDecimal.ZERO); // 插入时不用的字段
        // 提前还款费用
        borrow.setRepayFeeLate(BigDecimal.ZERO); // 插入时不用的字段
        // 逾期还款费用
        borrow.setLateInterest(BigDecimal.ZERO); // 插入时不用的字段
        // 逾期利息
        borrow.setLateForfeit(BigDecimal.ZERO); // 插入时不用的字段
        // 逾期催缴费
        borrow.setVouchStatus(0); // 插入时不用的字段 是否是担保
        borrow.setVouchAdvanceStatus(0); // 插入时不用的字段
        borrow.setVouchUserStatus(0); // 插入时不用的字段 担保人担保状态
        borrow.setVouchUsers(""); // 插入时不用的字段 担保人列表
        borrow.setVouchAccount(BigDecimal.ZERO); // 插入时不用的字段
        // 总担保的金额
        borrow.setVouchAccountYes(BigDecimal.ZERO); // 插入时不用的字段
        // 已担保的金额
        borrow.setVouchAccountWait(BigDecimal.ZERO); // 插入时不用的字段
        borrow.setVouchAccountScale(0L); // 插入时不用的字段 已担保的比例
        borrow.setVouchTimes(0); // 插入时不用的字段 担保次数
        borrow.setVouchAwardStatus(0); // 插入时不用的字段 是否设置担保奖励
        borrow.setVouchAwardScale(BigDecimal.ZERO); // 插入时不用的字段
        // 担保比例
        borrow.setVouchAwardAccount(BigDecimal.ZERO); // 插入时不用的字段
        // 总付出的担保奖励

        borrow.setVoucherName("");// 插入时不用的字段
        borrow.setVoucherLianxi("");// 插入时不用的字段
        borrow.setVoucherAtt("");// 插入时不用的字段
        borrow.setVouchjgName("");// 插入时不用的字段
        borrow.setVouchjgLianxi("");// 插入时不用的字段
        borrow.setVouchjgJs("");// 插入时不用的字段
        borrow.setVouchjgXy("");// 插入时不用的字段
        borrow.setFastStatus(0);// 插入时不用的字段
        borrow.setVouchstatus(0);// 插入时不用的字段
        borrow.setGroupStatus(0);// 插入时不用的字段
        borrow.setGroupId(0);// 插入时不用的字段

        borrow.setAwardStatus(0); // 插入时不用的字段 是否奖励
        borrow.setAwardFalse(0); // 插入时不用的字段 投资失败是否也奖励
        // 插入时不用的字段 奖励金额
        borrow.setAwardAccount(BigDecimal.ZERO);
        // 插入时不用的字段 按比例奖励
        borrow.setAwardScale(BigDecimal.ZERO);
        // 插入时不用的字段 投标奖励总额
        borrow.setAwardAccountAll(BigDecimal.ZERO);

        borrow.setOpenAccount(0); // 插入时不用的字段 公开我的帐户资金情况
        borrow.setOpenBorrow(0); // 插入时不用的字段 公开我的借款资金情况
        borrow.setOpenTender(0); // 插入时不用的字段 公开我的投标资金情况
        borrow.setOpenCredit(0); // 插入时不用的字段 公开我的信用额度情况
        // 是否可以评论
        borrow.setCommentStaus(0);
        borrow.setCommentTimes(0); // 插入时不用的字段 评论次数
        borrow.setCommentUsertype(""); // 插入时不用的字段 可评论的用户
       // borrow.setDiyaContents(""); // 插入时不用的字段
        borrow.setBorrowPawnApp(""); // 插入时不用的字段
        borrow.setBorrowPawnAppUrl(""); // 插入时不用的字段
        borrow.setBorrowPawnAuth(""); // 插入时不用的字段
        borrow.setBorrowPawnAuthUrl(""); // 插入时不用的字段
        borrow.setBorrowPawnFormalities(""); // 插入时不用的字段
        borrow.setBorrowPawnFormalitiesUrl(""); // 插入时不用的字段
        borrow.setBorrowPawnType(""); // 插入时不用的字段
        borrow.setBorrowPawnTime(""); // 插入时不用的字段
       // borrow.setBorrowPawnDescription(""); // 插入时不用的字段
        borrow.setBorrowPawnValue(""); // 插入时不用的字段
        borrow.setBorrowPawnXin(""); // 插入时不用的字段
        borrow.setOrderTop(""); // 插入时不用的字段 置顶时间
        // 初审核人
        borrow.setVerifyUserid("0");
        // 正式发标时间
        borrow.setVerifyTime("0");
        // 初审通过时间
        borrow.setVerifyOverTime(0);
        // 初审核备注
        borrow.setVerifyRemark("");
        borrow.setVerifyContents(""); // 插入时不用的字段 审核备注
        borrow.setVerifyStatus(0); // 插入时不用的字段
        // 复审核人
        borrow.setReverifyUserid("0");
        // 复审核时间
        borrow.setReverifyTime("0");
        // 复审核备注
        borrow.setReverifyRemark("");
        borrow.setReverifyStatus(0); // 插入时不用的字段
        borrow.setReverifyContents(""); // 插入时不用的字段 审核复审标注
        borrow.setUpfilesId(""); // 插入时不用的字段 发标上传图片
        //borrow.setBorrowRunningUse(""); // 插入时不用的字段 资金运转-用途
        //borrow.setBorrowRunningSoruce(""); // 插入时不用的字段 资金运转-来源

//		// 担保机构 风险控制措施-机构
//		borrow.setBorrowMeasuresInstit(hjhPlanAssetVO.getBorrowMeasuresInstit());
//		// 机构介绍
//		borrow.setBorrowCompanyInstruction(hjhPlanAssetVO.getBorrowCompanyInstruction());
//		// 操作流程
//		borrow.setBorrowOperatingProcess(hjhPlanAssetVO.getBorrowOperatingProcess());
//		// 抵押物信息 风险控制措施-抵押物
//		borrow.setBorrowMeasuresMort(hjhPlanAssetVO.getBorrowMeasuresMort());
//		// 本息保障 险控制措施-措施  风控措施  现在默认写死
        String measureMea = "1、汇盈金服已对该项目进行了严格的审核，最大程度的确保借款方信息的真实性，但不保证审核信息完全无误。<br>2、汇盈金服仅为信息发布平台，不对出借人提供担保或承诺保本保息，出借人应根据自身的投资偏好和风险承受能力进行独立判断和作出决策。市场有风险，投资需谨慎。<br>";
        //borrow.setBorrowMeasuresMea(measureMea);
        //borrow.setBorrowAnalysisPolicy(""); // 插入时不用的字段 政策及市场分析-政策支持
        //borrow.setBorrowAnalysisMarket(""); // 插入时不用的字段 政策及市场分析-市场分析
        //borrow.setBorrowCompany(""); // 插入时不用的字段 企业背景
        //borrow.setBorrowCompanyScope(""); // 插入时不用的字段 企业信息-营业范围
        //borrow.setBorrowCompanyBusiness(""); // 插入时不用的字段 企业信息-经营状况
        //borrow.setXmupfilesId(""); // 插入时不用的字段
        //borrow.setDyupfilesId(""); // 插入时不用的字段
        // 项目资料
//		borrow.setFiles(this.getUploadImage(borrowBean, "", borrowNid));
        // 担保方式
        borrow.setGuaranteeType(0);
        // 项目类型
        borrow.setProjectType(hjhAssetBorrowTypeVO.getBorrowCd());

        // 融资服务费
        borrow.setServiceFeeRate(borrowFinmanNewChargeVO.getChargeRate());
        // 账户管理费率
        borrow.setManageFeeRate(borrowFinmanNewChargeVO.getManChargeRate());
        // 收益差率
        borrow.setDifferentialRate(borrowFinmanNewChargeVO.getReturnRate());

        //默认全选
        // 可投资平台_PC
        borrow.setCanTransactionPc("1");

        // 可投资平台_微网站
        borrow.setCanTransactionWei("1");

        // 可投资平台_IOS
        borrow.setCanTransactionIos("1");

        // 可投资平台_Android
        borrow.setCanTransactionAndroid("1");

        // 运营标签->hjh 默认不填
        borrow.setOperationLabel("0");
        // 判断是企业还是个人
        if(hjhPlanAssetVO.getBorrowCompanyName() == null || hjhPlanAssetVO.getBorrowCompanyName().equals("")){
            // 个人信息
            borrow.setCompanyOrPersonal("2");
        }else{
            //企业信息
            borrow.setCompanyOrPersonal("1");
        }

        // 定时发标
        borrow.setOntime(0);
        borrow.setBookingBeginTime(0);
        borrow.setBookingEndTime(0);
        borrow.setBookingStatus(0);
        borrow.setBorrowAccountScaleAppoint(BigDecimal.ZERO);
        // 汇资管的内容设置
//		this.setHZGInfo(borrowBean, borrow);
        // 更新时间
        borrow.setUpdatetime(systemNowDate);
        // 银行存管标识 0未进行银行存管 1已进行银行存管
        borrow.setBankInputFlag(0);


        return borrow;

    }

    /**
     * 获取平台项目编号信息
     *
     * @param borrowCd
     */
    private BorrowProjectTypeVO getProjectType(String borrowCd) {
        BorrowProjectTypeVO borrowProjectType = null;
        List<BorrowProjectTypeVO> projectTypes = autoSendClient.selectBorrowProjectByBorrowCd(borrowCd);
        if (projectTypes != null && projectTypes.size() == 1) {
            borrowProjectType = projectTypes.get(0);
        }
        return borrowProjectType;
    }

    /**
     * 获取系统配置
     *
     * @return
     */
    public String getBorrowConfig(String configCd) {
        BorrowConfigVO borrowConfigVO = autoSendClient.getBorrowConfig(configCd);
        return borrowConfigVO.getConfigValue();
    }

    /**
     * 算募集时间
     *
     * @param rasieStartDate
     * @param bankRegistDays
     * @param borrowValidTime
     * @return
     */
    private String getBankRaiseEndDate(String rasieStartDate, Integer bankRegistDays, Integer borrowValidTime) {
        Integer raiseStartdate = GetDate.strYYYYMMDD3Timestamp3(rasieStartDate);
        int raistEndDate = raiseStartdate + bankRegistDays * 60 * 60 * 24 + borrowValidTime * 60 * 60 * 24;
        return GetDate.getDateMyTimeInMillisYYYYMMDD(raistEndDate);
    }

    private String getNextBorrowNid() {
        // 操作redis
        // 拿取实际的项目编号
        String borrowPreNidNew = "";
        Jedis jedis = pool.getResource();

        String borrowPreNid = getBorrowPreNid();//初始标的号码 -->根据年月放当月最初值
        while ("OK".equals(jedis.watch(RedisConstants.GEN_HJH_BORROW_NID))) {
            List<Object> results = null;
            Transaction tx = jedis.multi();
            borrowPreNidNew = RedisUtils.get(RedisConstants.GEN_HJH_BORROW_NID);
            if (StringUtils.isBlank(borrowPreNidNew)) {
                tx.set(RedisConstants.GEN_HJH_BORROW_NID, borrowPreNid);
                borrowPreNidNew = borrowPreNid;
                results = tx.exec();
            } else if (borrowPreNidNew != null) {
                if (Long.parseLong(borrowPreNid) > Long.parseLong(borrowPreNidNew)) {
                    borrowPreNidNew = (String.valueOf(borrowPreNid));
                } else {
                    borrowPreNidNew = (String.valueOf(Long.valueOf(borrowPreNidNew) + 1));
                }
                tx.set(RedisConstants.GEN_HJH_BORROW_NID, borrowPreNidNew);
                results = tx.exec();
            }
            if (results == null || results.isEmpty()) {
                jedis.unwatch();
            } else {
                String ret = (String) results.get(0);
                if (ret != null && "OK".equals(ret)) {
                    borrowPreNid = borrowPreNidNew;
                    break;
                } else {
                    jedis.unwatch();
                }
            }
        }

        return borrowPreNidNew;

    }

    /**
     * 借款预编码，八位
     *
     * @return
     */
    private String getBorrowPreNid() {
        String yyyymm = GetDate.getServerDateTime(13, new Date());
        String mmdd = yyyymm.substring(2);
//		String borrowPreNid = this.borrowCustomizeMapper.getBorrowPreNid(mmdd);
        String borrowPreNid = StringUtils.EMPTY;
        if (StringUtils.isEmpty(borrowPreNid)) {
            return mmdd + "00000001";
        }
        if (borrowPreNid.length() == 8) {
            return mmdd + "00000001";
        }
        return String.valueOf(Long.valueOf(borrowPreNid) + 1);
    }

}
