package com.hyjf.am.trade.service.task.issuerecover.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.producer.MailProducer;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.task.issuerecover.AutoIssueRecoverService;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.am.vo.task.issuerecover.BorrowWithBLOBs;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 汇计划自动发标修复
 * @author walter.limeng
 * @version AutoIssueRecoverJob, v0.1 2018/7/11 10:30
 */
@Service
public class AutoIssueRecoverServiceImpl extends BaseServiceImpl implements AutoIssueRecoverService {

    @Resource
    private MailProducer mailProducer;

    @Value("${hyjf.env.test}")
    private Boolean env_test;

    @Value("${hyjf.alerm.email.test}")
    private static String emailList1;

    @Value("${hyjf.alerm.email}")
    private static String emaillist2;

    /**
     * 邮件发送key
     */
    public static String LABEL_MAIL_KEY = "labelmailkey";
    public static JedisPool pool = RedisUtils.getPool();

    @Override
    public List<HjhPlanAsset> selectAssetList(List statusList) {
        HjhPlanAssetExample example = new HjhPlanAssetExample();
        HjhPlanAssetExample.Criteria crt = example.createCriteria();
        crt.andVerifyStatusEqualTo(1);
        crt.andStatusIn(statusList);
        List<HjhPlanAsset> list = this.hjhPlanAssetMapper.selectByExample(example);

        return list;
    }

    @Override
    public List<HjhPlanAsset> selectBorrowAssetList() {
        HjhPlanAssetExample example = new HjhPlanAssetExample();
        HjhPlanAssetExample.Criteria crt = example.createCriteria();
        crt.andVerifyStatusEqualTo(1);
        crt.andStatusEqualTo(7);
        crt.andLabelIdIsNotNull();
        crt.andPlanNidIsNull();

        List<HjhPlanAsset> list = this.hjhPlanAssetMapper.selectByExample(example);

        return list;
    }

    @Override
    public List<HjhDebtCredit> selectCreditAssetList() {
        HjhDebtCreditExample example = new HjhDebtCreditExample();
        HjhDebtCreditExample.Criteria crt = example.createCriteria();
        crt.andCreditStatusEqualTo(0);
        crt.andLabelIdEqualTo(0);
        crt.andPlanNidNewEqualTo(StringUtils.EMPTY);

        List<HjhDebtCredit> list = this.hjhDebtCreditMapper.selectByExample(example);

        return list;
    }

    @Override
    public List<Borrow> selectBorrowList() {
        BorrowExample example = new BorrowExample();
        BorrowExample.Criteria crt = example.createCriteria();
        crt.andStatusEqualTo(2);
        crt.andVerifyStatusEqualTo(4);
        /*-----------------upd by liushouyi HJH3 Start-------------------*/
        //标的做成时增加了标签id的匹配、此处标签id=0的条件会把已经添加标签的标的漏掉
        //crt.andLabelIdEqualTo(0);
        /*-----------------upd by liushouyi HJH3 End-------------------*/
        crt.andPlanNidIsNull();
        crt.andIsEngineUsedEqualTo(1);

        List<Borrow> list = this.borrowMapper.selectByExample(example);

        return list;
    }

    @Override
    public List<BorrowWithBLOBs> selectAutoBorrowNidList() {
        return borrowCustomizeMapper.selectAutoBorrowNidList();
    }

    @Override
    public HjhPlanAsset getHjhPlanAssetById(Integer planId) {
        return hjhPlanAssetMapper.selectByPrimaryKey(planId);
    }

    @Override
    public HjhAssetBorrowtype selectAssetBorrowType(HjhPlanAsset mqHjhPlanAsset) {
        HjhAssetBorrowtypeExample example = new HjhAssetBorrowtypeExample();
        HjhAssetBorrowtypeExample.Criteria cra = example.createCriteria();
        cra.andInstCodeEqualTo(mqHjhPlanAsset.getInstCode());
        cra.andAssetTypeEqualTo(mqHjhPlanAsset.getAssetType());
        cra.andIsOpenEqualTo(1);

        List<HjhAssetBorrowtype> list = this.hjhAssetBorrowtypeMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

    @Override
    public boolean insertSendBorrow(HjhPlanAsset hjhPlanAsset, HjhAssetBorrowtype hjhAssetBorrowType) {
        // 验证资产风险保证金是否足够（redis）
        Integer checkResult = checkAssetCanSend(hjhPlanAsset);
        if(checkResult ==  -1){
            return false;
        }
        if (checkResult > 0) {
            logger.info("资产编号："+hjhPlanAsset.getAssetId()+" 录标校验未通过:" + checkResult);
            //add by cwyang 20180420 增加待补缴状态
            HjhPlanAsset planAsset = new HjhPlanAsset();
            planAsset.setId(hjhPlanAsset.getId());
            //待补缴保证金
            planAsset.setStatus(checkResult);
            this.hjhPlanAssetMapper.updateByPrimaryKeySelective(planAsset);
            //end
            return false;
        }

        // 获取管理费率，服务费率，自动发标费率
        // 项目类型(code):从hyjf_hjh_asset_borrowtype 取code 现金贷
        String projectCd = hjhAssetBorrowType.getBorrowCd()+"";
        String borrowClass = this.getBorrowProjectClass(projectCd);
        String queryBorrowStyle = null;
        // 费率配置表有点尴尬，还款方式只区分了天和月
        if ("endday".equals(hjhPlanAsset.getBorrowStyle())) {//天标
            queryBorrowStyle  = "endday";

        }else {
            queryBorrowStyle = "month";
        }
        BorrowFinmanNewCharge borrowFinmanNewCharge = this.selectBorrowApr(borrowClass,hjhPlanAsset.getInstCode(),hjhPlanAsset.getAssetType(),queryBorrowStyle,hjhPlanAsset.getBorrowPeriod());
        if(borrowFinmanNewCharge == null || borrowFinmanNewCharge.getAutoBorrowApr() == null){
            logger.info("资产编号："+hjhPlanAsset.getAssetId()+" 录标失败 ,没有取到项目费率");
            return false;
        }

        // 录标 a. 根据表配置判断发标项目类型
        if(!insertRecord(hjhPlanAsset,hjhAssetBorrowType,borrowFinmanNewCharge)){
            logger.info("资产编号："+hjhPlanAsset.getAssetId()+" 录标失败");
            return false;
        }

        // 更新额度
        updateForSend(hjhPlanAsset.getInstCode(), new BigDecimal(hjhPlanAsset.getAccount()));
        // 累加日已用额度
        String dayUsedKey = RedisConstants.DAY_USED + hjhPlanAsset.getInstCode() + "_" + GetDate.getDate("yyyyMMdd");
        RedisUtils.add(dayUsedKey, String.valueOf(hjhPlanAsset.getAccount()));
        // 累加月已用额度
        String monthKey = RedisConstants.MONTH_USED + hjhPlanAsset.getInstCode() + "_" + GetDate.getDate("yyyyMM");
        RedisUtils.add(monthKey, String.valueOf(hjhPlanAsset.getAccount()));

        return true;
    }

    /**
     * 发标完成更新相应额度
     * @param instCode
     * @param assetAccount
     * @return
     */
    private Integer updateForSend(String instCode, BigDecimal assetAccount){
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("amount", assetAccount);
        paraMap.put("instCode", instCode);

        return  apiBailConfigInfoCustomizeMapper.updateForSendBorrow(paraMap);
    }

    /**
     * 录标
     *
     * @param hjhPlanAsset
     * @throws Exception
     */
    private boolean insertRecord(HjhPlanAsset hjhPlanAsset,HjhAssetBorrowtype hjhAssetBorrowType,BorrowFinmanNewCharge borrowFinmanNewCharge) {

        boolean result = false;


        // borrow_class
        String beforeFix = borrowFinmanNewCharge.getProjectType();

        Borrow borrow = this.setBorrowCommonData(hjhPlanAsset, hjhAssetBorrowType, borrowFinmanNewCharge);


        BorrowInfoWithBLOBs borrowInfo = this.setBorrowInfo(hjhPlanAsset, hjhAssetBorrowType, borrowFinmanNewCharge, borrow);

        // 获取标签ID
        HjhLabel label = this.getLabelId(borrow,hjhPlanAsset,borrowInfo);
        if(label == null || label.getId() ==null){
            logger.info(hjhPlanAsset.getAssetId()+" 没有获取到标签");

            /**汇计划三期邮件预警 BY LIBIN start*/
            // 如果redis不存在这个KEY(一天有效期)，那么可以发邮件
            if(!RedisUtils.exists(LABEL_MAIL_KEY + hjhPlanAsset.getAssetId())){
                StringBuffer msg = new StringBuffer();
                msg.append("资产ID：").append(hjhPlanAsset.getAssetId()).append("<br/>");
                msg.append("当前时间：").append(GetDate.formatTime()).append("<br/>");
                msg.append("错误信息：").append("该资产在自动录标时未打上标签！").append("<br/>");
                String emailList= "";
                if (env_test){
                    emailList = emailList1;
                }else{
                    emailList = emaillist2;
                }
                logger.info("配置文件读取的测试邮件地址:[{}]",emailList1);
                logger.info("配置文件读取的线上邮件地址:[{}]",emaillist2);
                logger.info("赋值给本地变量的邮件地址:[{}]",emailList);
                String [] toMail = emailList.split(",");
                MailMessage mailMessage = new MailMessage(null, null, "资产ID为：" + hjhPlanAsset.getAssetId(), msg.toString(), null, toMail, CustomConstants.MAILSENDFORMAILINGADDRESSMSG,
                        MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS);
                // 发送邮件
                try {
                    mailProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC,UUID.randomUUID().toString(), JSON.toJSONBytes(mailMessage)));
                    RedisUtils.set(LABEL_MAIL_KEY + hjhPlanAsset.getAssetId(), hjhPlanAsset.getAssetId(), 24 * 60 * 60);
                } catch (MQException e2) {
                    logger.error("发送邮件失败..", e2);
                }

            } else {
                logger.info("此邮件key值还未过期(一天)");
            }
            /**汇计划三期邮件预警 BY LIBIN end*/
            return result;
        }

        // 获取下一个标的编号
        String borrowPreNidNew = getNextBorrowNid();

        // 标签ID
        borrow.setLabelId(label.getId());
        /*--------add by liushouyi HJH3 Start---------*/
        // 默认使用引擎
        borrow.setIsEngineUsed(1);
        /*--------add by liushouyi HJH3 End---------*/

        String borrowNid = beforeFix + borrowPreNidNew;
        //项目标题
        borrowInfo.setProjectName(borrowNid);
        // 借款编号
        borrow.setBorrowNid(borrowNid);
        borrowInfo.setBorrowNid(borrowNid);
        // 借款预编码
        borrowInfo.setBorrowPreNid(borrowPreNidNew);
        // 新借款预编码
        borrowInfo.setBorrowPreNidNew(borrowPreNidNew);
        borrowInfo.setBorrowUserName(borrow.getBorrowUserName());

        // 标的还款后的回滚方式
        HjhBailConfigInfoExample example = new HjhBailConfigInfoExample();
        example.createCriteria().andInstCodeEqualTo(hjhPlanAsset.getInstCode()).andBorrowStyleEqualTo(hjhPlanAsset.getBorrowStyle());
        List<HjhBailConfigInfo> hjhBailConfigInfoList = this.hjhBailConfigInfoMapper.selectByExample(example);
        // 推送资产的时候校验回滚方式是否配置、若未配置不得推送资产
        if(null!=hjhBailConfigInfoList && hjhBailConfigInfoList.size()>0) {
            borrow.setRepayCapitalType(hjhBailConfigInfoList.get(0).getRepayCapitalType());
        }

        String borrowStyle = borrow.getBorrowStyle();
        Integer isMonth = 0;// 0:天标 1：月标
        if(!CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)){
            isMonth = 1;
        }
        borrow.setIsMonth(isMonth);

        // 借款表插入
        this.borrowMapper.insertSelective(borrow);
//        borrowInfo.setId(id);
        borrowInfoMapper.insertSelective(borrowInfo);
        // 个人信息
        this.insertBorrowManinfo(borrowNid, hjhPlanAsset,borrowInfo.getBorrowPreNid(), borrow);

        // 更新资产表
        HjhPlanAsset hjhPlanAssetnew = new HjhPlanAsset();
        hjhPlanAssetnew.setId(hjhPlanAsset.getId());
        // 标的编号，计划编号在关联资产更新
        hjhPlanAssetnew.setBorrowNid(borrowNid);
        hjhPlanAssetnew.setLabelId(label.getId());
        hjhPlanAssetnew.setLabelName(label.getLabelName());

        hjhPlanAssetnew.setStatus(3);//备案中
        //获取当前时间
        hjhPlanAssetnew.setUpdateTime(new Date());
        hjhPlanAssetnew.setUpdateUserId(1);
        boolean borrowFlag = this.hjhPlanAssetMapper.updateByPrimaryKeySelective(hjhPlanAssetnew)>0?true:false;
        if(borrowFlag){
            result = true;
        }

        return result;
    }

    /**
     * 个人信息
     *
     * @param borrowNid
     * @param hjhPlanAsset
     * @param borrow
     * @return
     */
    public int insertBorrowManinfo(String borrowNid, HjhPlanAsset hjhPlanAsset, String borrowPreNid,Borrow borrow) {

        BorrowManinfo borrowManinfo = new BorrowManinfo();

        borrowManinfo.setBorrowNid(borrowNid);
        borrowManinfo.setBorrowPreNid(borrowPreNid);
        // 姓名
        if (StringUtils.isNotEmpty(hjhPlanAsset.getTruename())) {
            borrowManinfo.setName(hjhPlanAsset.getTruename());
        } else {
            borrowManinfo.setName(StringUtils.EMPTY);
        }
        // 性别
        if (hjhPlanAsset.getSex() != null) {
            borrowManinfo.setSex(hjhPlanAsset.getSex());
        } else {
            borrowManinfo.setSex(0);
        }
        // 年龄
        if (hjhPlanAsset.getAge() != null) {
            borrowManinfo.setOld(hjhPlanAsset.getAge());
        } else {
            borrowManinfo.setOld(0);
        }
        // 婚姻
        if (hjhPlanAsset.getMarriage() != null) {
            borrowManinfo.setMerry(hjhPlanAsset.getMarriage());
        } else {
            borrowManinfo.setMerry(0);
        }
        // 岗位职业
        if (StringUtils.isNotEmpty(hjhPlanAsset.getPosition())) {
            borrowManinfo.setPosition(hjhPlanAsset.getPosition());
        }
        // 省
//		if (StringUtils.isNotEmpty(hjhPlanAsset.getLocation_p())) {
//			borrowManinfo.setPro(hjhPlanAsset.getLocation_p());
//		} else {
//			borrowManinfo.setPro(StringUtils.EMPTY);
//		}
        // 市
        if (StringUtils.isNotEmpty(hjhPlanAsset.getWorkCity())) {
            borrowManinfo.setCity(hjhPlanAsset.getWorkCity());
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
        if(StringUtils.isNotEmpty(hjhPlanAsset.getIdcard())){
            borrowManinfo.setCardNo(hjhPlanAsset.getIdcard());
        }else{
            borrowManinfo.setCardNo(StringUtils.EMPTY);
        }
        //户籍地
        if(StringUtils.isNotEmpty(hjhPlanAsset.getDomicile())){
            borrowManinfo.setDomicile(hjhPlanAsset.getDomicile());
        }else{
            borrowManinfo.setDomicile(StringUtils.EMPTY);
        }
        //在平台逾期次数
        if(StringUtils.isNotEmpty(hjhPlanAsset.getOverdueTimes())){
            borrowManinfo.setOverdueTimes(hjhPlanAsset.getOverdueTimes());
        }else{
            borrowManinfo.setOverdueTimes(StringUtils.EMPTY);
        }
        //在平台逾期金额
        if(StringUtils.isNotEmpty(hjhPlanAsset.getOverdueAmount())){
            borrowManinfo.setOverdueAmount(hjhPlanAsset.getOverdueAmount());
        }else{
            borrowManinfo.setOverdueAmount(StringUtils.EMPTY);
        }
        //涉诉情况
        if(StringUtils.isNotEmpty(hjhPlanAsset.getLitigation())){
            borrowManinfo.setLitigation(hjhPlanAsset.getLitigation());
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
        if(StringUtils.isNotEmpty(hjhPlanAsset.getAnnualIncome())){
            borrowManinfo.setAnnualIncome(hjhPlanAsset.getAnnualIncome());
        }else{
            borrowManinfo.setAnnualIncome("10万以内");
        }
        //(个人)征信报告逾期情况
        if(StringUtils.isNotEmpty(hjhPlanAsset.getOverdueReport())){
            borrowManinfo.setOverdueReport(hjhPlanAsset.getOverdueReport());
        }else{
            borrowManinfo.setOverdueReport("暂无数据");
        }
        //(个人)重大负债状况
        if(StringUtils.isNotEmpty(hjhPlanAsset.getDebtSituation())){
            borrowManinfo.setDebtSituation(hjhPlanAsset.getDebtSituation());
        }else{
            borrowManinfo.setDebtSituation("无");
        }
        //(个人)其他平台借款情况
        if(StringUtils.isNotEmpty(hjhPlanAsset.getOtherBorrowed())){
            borrowManinfo.setOtherBorrowed(hjhPlanAsset.getOtherBorrowed());
        }else{
            borrowManinfo.setOtherBorrowed("暂无数据");
        }
        //(个人)借款资金运用情况
        if(StringUtils.isNotEmpty(hjhPlanAsset.getIsFunds())){
            borrowManinfo.setIsFunds(hjhPlanAsset.getIsFunds());
        }else{
            borrowManinfo.setIsFunds("正常");
        }
        //(个人)借款方经营状况及财务状况
        if(StringUtils.isNotEmpty(hjhPlanAsset.getIsManaged())){
            borrowManinfo.setIsManaged(hjhPlanAsset.getIsManaged());
        }else{
            borrowManinfo.setIsManaged("正常");
        }
        //(个人)借款方还款能力变化情况
        if(StringUtils.isNotEmpty(hjhPlanAsset.getIsAbility())){
            borrowManinfo.setIsAbility(hjhPlanAsset.getIsAbility());
        }else{
            borrowManinfo.setIsAbility("正常");
        }
        //(个人)借款方逾期情况
        if(StringUtils.isNotEmpty(hjhPlanAsset.getIsOverdue())){
            borrowManinfo.setIsOverdue(hjhPlanAsset.getIsOverdue());
        }else{
            borrowManinfo.setIsOverdue("暂无");
        }
        //(个人)借款方涉诉情况
        if(StringUtils.isNotEmpty(hjhPlanAsset.getIsComplaint())){
            borrowManinfo.setIsComplaint(hjhPlanAsset.getIsComplaint());
        }else{
            borrowManinfo.setIsComplaint("暂无");
        }
        //(个人)借款方受行政处罚情况
        if(StringUtils.isNotEmpty(hjhPlanAsset.getIsPunished())){
            borrowManinfo.setIsPunished(hjhPlanAsset.getIsPunished());
        }else{
            borrowManinfo.setIsPunished("暂无");
        }
        if(StringUtils.isNotBlank(hjhPlanAsset.getAddress())){
            borrowManinfo.setAddress(hjhPlanAsset.getAddress());
        }
        this.borrowManinfoMapper.insertSelective(borrowManinfo);

        return 0;
    }

    private String getNextBorrowNid(){
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
                if(Long.parseLong(borrowPreNid)>Long.parseLong(borrowPreNidNew)){
                    borrowPreNidNew = (String.valueOf(borrowPreNid));
                }else{
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

    /**
     * 标的匹配标签,取匹配最优的标签
     * @param borrow
     * @return
     */
    public HjhLabel getLabelId(Borrow borrow, HjhPlanAsset hjhPlanAsset,BorrowInfo borrowInfo) {

        HjhLabel resultLabel = null;

        HjhLabelExample example = new HjhLabelExample();
        HjhLabelExample.Criteria cra = example.createCriteria();

        cra.andDelFlagEqualTo(0);
        cra.andLabelStateEqualTo(1);
        cra.andBorrowStyleEqualTo(borrow.getBorrowStyle());
        cra.andIsCreditEqualTo(0); // 原始标
        cra.andIsLateEqualTo(0); // 是否逾期
        example.setOrderByClause(" update_time desc ");

        List<HjhLabel> list = this.hjhLabelMapper.selectByExample(example);
        if (list != null && list.size() <= 0) {
            logger.info(borrow.getBorrowStyle()+" 该原始标还款方式 没有一个标签");
            return resultLabel;
        }
        // continue过滤输入了但是不匹配的标签，如果找到就是第一个
        for (HjhLabel hjhLabel : list) {
            // 标的期限
//			int score = 0;
            if(hjhLabel.getLabelTermEnd() != null && hjhLabel.getLabelTermEnd().intValue()>0 && hjhLabel.getLabelTermStart()!=null
                    && hjhLabel.getLabelTermStart().intValue()>0){
                if(borrow.getBorrowPeriod() >= hjhLabel.getLabelTermStart() && borrow.getBorrowPeriod() <= hjhLabel.getLabelTermEnd()){
//					score = score+1;
                }else{
                    continue;
                }
            }else if ((hjhLabel.getLabelTermEnd() != null && hjhLabel.getLabelTermEnd().intValue()>0) ||
                    (hjhLabel.getLabelTermStart()!=null && hjhLabel.getLabelTermStart().intValue()>0)) {
                if(borrow.getBorrowPeriod().equals(hjhLabel.getLabelTermStart()) || borrow.getBorrowPeriod().equals(hjhLabel.getLabelTermEnd())){
//					score = score+1;
                }else{
                    continue;
                }
            }else{
                continue;
            }
            // 标的实际利率
            if(hjhLabel.getLabelAprStart() != null && hjhLabel.getLabelAprStart().compareTo(BigDecimal.ZERO)>0 &&
                    hjhLabel.getLabelAprEnd()!=null && hjhLabel.getLabelAprEnd().compareTo(BigDecimal.ZERO)>0){
                if(borrow.getBorrowApr().compareTo(hjhLabel.getLabelAprStart())>=0 && borrow.getBorrowApr().compareTo(hjhLabel.getLabelAprEnd())<=0){
//					score = score+1;
                }else{
                    continue;
                }
            }else if (hjhLabel.getLabelAprStart() != null && hjhLabel.getLabelAprStart().compareTo(BigDecimal.ZERO)>0) {
                if(borrow.getBorrowApr().compareTo(hjhLabel.getLabelAprStart())==0 ){
//					score = score+1;
                }else{
                    continue;
                }

            }else if (hjhLabel.getLabelAprEnd()!=null && hjhLabel.getLabelAprEnd().compareTo(BigDecimal.ZERO)>0 ) {
                if(borrow.getBorrowApr().compareTo(hjhLabel.getLabelAprEnd())==0){
//					score = score+1;
                }else {
                    continue;
                }
            }
            // 标的实际支付金额
            if(hjhLabel.getLabelPaymentAccountStart() != null && hjhLabel.getLabelPaymentAccountStart().compareTo(BigDecimal.ZERO)>0 &&
                    hjhLabel.getLabelPaymentAccountEnd()!=null && hjhLabel.getLabelPaymentAccountEnd().compareTo(BigDecimal.ZERO)>0){
                if(borrow.getAccount().compareTo(hjhLabel.getLabelPaymentAccountStart())>=0 && borrow.getAccount().compareTo(hjhLabel.getLabelPaymentAccountEnd())<=0){
//					score = score+1;
                }else{
                    continue;
                }
            }else if (hjhLabel.getLabelPaymentAccountStart() != null && hjhLabel.getLabelPaymentAccountStart().compareTo(BigDecimal.ZERO)>0) {
                if(borrow.getAccount().compareTo(hjhLabel.getLabelPaymentAccountStart())==0 ){
//					score = score+1;
                }else{
                    continue;
                }

            }else if (hjhLabel.getLabelPaymentAccountEnd()!=null && hjhLabel.getLabelPaymentAccountEnd().compareTo(BigDecimal.ZERO)>0 ) {
                if(borrow.getAccount().compareTo(hjhLabel.getLabelPaymentAccountEnd())==0){
//					score = score+1;
                }else{
                    continue;
                }
            }
            // 资产来源
            if(StringUtils.isNotBlank(hjhLabel.getInstCode())){
                if(hjhLabel.getInstCode().equals(borrowInfo.getInstCode())){
//					score = score+1;
                }else{
                    continue;
                }
            }
            // 产品类型
            if(hjhLabel.getAssetType() != null && hjhLabel.getAssetType().intValue() >= 0){
                if(hjhLabel.getAssetType().equals(borrowInfo.getAssetType())){
                    ;
                }else{
                    continue;
                }
            }
            // 项目类型
            if(hjhLabel.getProjectType() != null && hjhLabel.getProjectType().intValue() >= 0){
                if(hjhLabel.getProjectType().equals(borrowInfo.getProjectType())){
                    ;
                }else{
                    continue;
                }
            }

            // 推送时间节点
            if(hjhPlanAsset != null && hjhPlanAsset.getRecieveTime() != null && hjhPlanAsset.getRecieveTime().intValue() > 0){
                Date reciveDate = GetDate.getDate(hjhPlanAsset.getRecieveTime());

                if(hjhLabel.getPushTimeStart() != null && hjhLabel.getPushTimeEnd()!=null){
                    if(reciveDate.getTime() >= hjhLabel.getPushTimeStart().getTime() &&
                            reciveDate.getTime() <= hjhLabel.getPushTimeEnd().getTime()){
//						score = score+1;
                    }else{
                        continue;
                    }
                }else if (hjhLabel.getPushTimeStart() != null) {
                    if(reciveDate.getTime() == hjhLabel.getPushTimeStart().getTime() ){
//						score = score+1;
                    }else{
                        continue;
                    }

                }else if (hjhLabel.getPushTimeEnd()!=null) {
                    if(reciveDate.getTime() == hjhLabel.getPushTimeEnd().getTime() ){
//						score = score+1;
                    }else{
                        continue;
                    }
                }

            }

            // 如果找到返回最近的一个
            return hjhLabel;

        }

        return resultLabel;
    }

    /**
     * 根据项目类型，期限，获取借款利率
     *
     * @return
     */
    private BorrowFinmanNewCharge selectBorrowApr(String projectType,String instCode, Integer instProjectType, String borrowStyle,Integer chargetime) {
        BorrowFinmanNewChargeExample example = new BorrowFinmanNewChargeExample();
        BorrowFinmanNewChargeExample.Criteria cra = example.createCriteria();
        cra.andProjectTypeEqualTo(projectType);
        cra.andInstCodeEqualTo(instCode);
        cra.andAssetTypeEqualTo(instProjectType);
        cra.andManChargeTimeTypeEqualTo(borrowStyle);
        cra.andManChargeTimeEqualTo(chargetime);
        cra.andStatusEqualTo(0);

        List<BorrowFinmanNewCharge> list = this.borrowFinmanNewChargeMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        return null;

    }

    private BorrowInfoWithBLOBs setBorrowInfo(HjhPlanAsset hjhPlanAsset, HjhAssetBorrowtype hjhAssetBorrowType, BorrowFinmanNewCharge borrowFinmanNewCharge,Borrow borrow){
        BorrowInfoWithBLOBs borrowInfo = new BorrowInfoWithBLOBs();
        borrowInfo.setInstCode(hjhPlanAsset.getInstCode());
        borrowInfo.setAssetType(hjhAssetBorrowType.getAssetType());
        borrowInfo.setUserId(hjhPlanAsset.getUserId());
        borrowInfo.setAccountContents("");

        borrowInfo.setAddIp(borrow.getAddIp());


        // 受托支付
        if (hjhPlanAsset.getEntrustedFlg() != null && hjhPlanAsset.getEntrustedFlg().intValue() ==1) {
            borrowInfo.setEntrustedFlg(1);
            borrowInfo.setEntrustedUserId(hjhPlanAsset.getEntrustedUserId());
            borrowInfo.setEntrustedUserName(hjhPlanAsset.getEntrustedUserName());
        }
        // 根据项目类型设置下列
        BorrowProjectType borrowProjectType = getProjectType(hjhAssetBorrowType.getBorrowCd()+"");
        if(borrowProjectType != null){
            borrowInfo.setBorrowIncreaseMoney(borrowProjectType.getIncreaseMoney()); //递增投资金额
            borrowInfo.setBorrowInterestCoupon(borrowProjectType.getInterestCoupon());
            borrowInfo.setBorrowTasteMoney(borrowProjectType.getTasteMoney());//体验金
        }
        borrowInfo.setApplicant(hjhPlanAsset.getIdcard());
        String repayOrgName = hjhAssetBorrowType.getRepayOrgName();
        // 垫付机构用户名不为空的情况
        if (StringUtils.isNotEmpty(repayOrgName)) {
            // 根据垫付机构用户名检索垫付机构用户ID
            AccountExample accountExample = new AccountExample();
            AccountExample.Criteria accountCri = accountExample.createCriteria();
            accountCri.andUserNameEqualTo(repayOrgName);
            List<Account> ulist = this.accountMapper.selectByExample(accountExample);
            // 如果用户名不存在，返回错误信息。
            if (ulist == null || ulist.size() == 0) {
                return null;
            }
            Integer userId = ulist.get(0).getUserId();
            borrowInfo.setRepayOrgUserId(userId);
            borrowInfo.setIsRepayOrgFlag(1);
            // 垫付机构用户名
            borrowInfo.setRepayOrgName(repayOrgName);
        } else {
            borrowInfo.setRepayOrgUserId(0);
            borrowInfo.setIsRepayOrgFlag(0);
        }

        // 借款标题
        borrowInfo.setName("个人短期借款");

        //新标（20170612改版后都为新标）
        borrowInfo.setIsNew(1);
        if (StringUtils.isEmpty(borrowInfo.getType())) {
            borrowInfo.setType("0");
        }


        // 新增协议期限字段
//		if (StringUtils.isNotEmpty(borrowBean.getContractPeriod())) {
//			borrow.setContractPeriod(Integer.parseInt(borrowBean.getContractPeriod()));
//		}
        // 信用评级
        if (StringUtils.isNotEmpty(hjhPlanAsset.getCreditLevel())) {
            borrowInfo.setBorrowLevel(hjhPlanAsset.getCreditLevel());
        }
        /**************网站改版添加 ******************/
        //融资用途
        borrowInfo.setFinancePurpose(hjhPlanAsset.getUseage());
        // 平台直接默认填写：借款用途
        borrowInfo.setBorrowUse(hjhPlanAsset.getUseage());

        //月薪收入
        borrowInfo.setMonthlyIncome(hjhPlanAsset.getMonthlyIncome());
        //还款来源
//		borrow.setPayment(hjhPlanAsset.getPayment());
        //第一还款来源
        borrowInfo.setFirstPayment(hjhPlanAsset.getFirstPayment());
        //第二还款来源
        borrowInfo.setSecondPayment(hjhPlanAsset.getSecondPayment());
        //费用说明
        borrowInfo.setCostIntrodution(hjhPlanAsset.getCostIntrodution());
        //财务状况
//		borrow.setFianceCondition(hjhPlanAsset.getFianceCondition());
        /**************网站改版添加end ******************/


        // 借款有效时间
        borrowInfo.setBorrowValidTime(Integer.parseInt(getBorrowConfig("BORROW_VALID_TIME")));
        // 银行备案时间
        borrowInfo.setBankRegistDays(Integer.parseInt(getBorrowConfig("BORROW_REGIST_DAYS")));

        // 银行募集开始时间
        String rasieStartDate = GetOrderIdUtils.getOrderDate();
        borrowInfo.setBankRaiseStartDate(rasieStartDate);
        // 银行募集结束时间
        String raiseEndDate = this.getBankRaiseEndDate(rasieStartDate, borrowInfo.getBankRegistDays(), borrowInfo.getBorrowValidTime());
        borrowInfo.setBankRaiseEndDate(raiseEndDate);
        // 银行用借款期限
        if (borrow.getBorrowStyle().equals(CustomConstants.BORROW_STYLE_ENDDAY)) {
            borrowInfo.setBankBorrowDays(hjhPlanAsset.getBorrowPeriod());
        }
        /** 月标直接写死每月30天，银行不校验.计算过于麻烦 */
        else {
            borrowInfo.setBankBorrowDays(hjhPlanAsset.getBorrowPeriod() * 30);
        }

        // 最低投标金额
        if (StringUtils.isNotEmpty(borrowProjectType.getInvestStart())) {
            borrowInfo.setTenderAccountMin(Integer.valueOf(borrowProjectType.getInvestStart()));
        } else {
            borrowInfo.setTenderAccountMin(0);
        }

        // 最高投标金额
        if (StringUtils.isNotEmpty(borrowProjectType.getInvestEnd())) {
            borrowInfo.setTenderAccountMax(Integer.valueOf(borrowProjectType.getInvestEnd()));
        } else {
            borrowInfo.setTenderAccountMax(0);
        }

        // 项目类型
        borrowInfo.setProjectType(hjhAssetBorrowType.getBorrowCd());

        borrowInfo.setBorrowExtraYield(BigDecimal.ZERO);
        //默认全选
        // 可投资平台_PC
        borrowInfo.setCanTransactionPc("1");

        // 可投资平台_微网站
        borrowInfo.setCanTransactionWei("1");

        // 可投资平台_IOS
        borrowInfo.setCanTransactionIos("1");

        // 可投资平台_Android
        borrowInfo.setCanTransactionAndroid("1");

        // 运营标签->hjh 默认不填
        borrowInfo.setOperationLabel("0");
        // 企业还是  默认是个人
        borrowInfo.setCompanyOrPersonal(2);
        borrowInfo.setUpfilesId(""); // 插入时不用的字段 发标上传图片


        return  borrowInfo;
    }

    /**
     * 借款表插入
     *
     * @param hjhPlanAsset
     * @param hjhPlanAsset
     * @return
     * @throws Exception
     */
    private Borrow setBorrowCommonData(HjhPlanAsset hjhPlanAsset, HjhAssetBorrowtype hjhAssetBorrowType, BorrowFinmanNewCharge borrowFinmanNewCharge){

        // 插入huiyingdai_borrow
        Borrow borrow = new Borrow();

        // 关联计划
        borrow.setIsShow(1); // 默认不展示
        // 添加IP
        borrow.setAddIp("localhost");
        // 状态
        borrow.setStatus(0);
        borrow.setRepayStatus(0);// 标的还款状态

        // 插入时间
        Date systemNowDate = GetDate.getDate();
        // 添加时间
        String addtime = String.valueOf(GetDate.getNowTime10());



        borrow.setUserId(hjhPlanAsset.getUserId());
        // 借款人用户名
        borrow.setBorrowUserName(hjhPlanAsset.getUserName());

        borrow.setBorrowEndTime("");
        borrow.setRepayLastTime(0);


        // 项目申请人
//		String applicant = hjhAssetBorrowType.getApplicant();

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


        // 借款总金额
        borrow.setAccount(new BigDecimal(hjhPlanAsset.getAccount()));
        borrow.setBorrowAccountWait(new BigDecimal(hjhPlanAsset.getAccount()));
        borrow.setCreateUserName("admin");
        borrow.setCreateTime(systemNowDate);
        // 财务状况
//		if (StringUtils.isEmpty(hjhPlanAsset.getAccountContents())) {
//			borrow.setAccountContents(StringUtils.EMPTY);
//		} else {
//			borrow.setAccountContents(hjhPlanAsset.getAccountContents());
//		}
        borrow.setBorrowValidTime(Integer.parseInt(getBorrowConfig("BORROW_VALID_TIME")));
        // 是否可以进行借款
        borrow.setBorrowStatus(0);
        // 满表审核状态
        borrow.setBorrowFullStatus(0);
        // 已经募集的金额
        borrow.setBorrowAccountYes(BigDecimal.ZERO);
        // // 剩余的金额
        // borrow.setBorrowAccountWait(BigDecimal.ZERO);

        // 还款方式
        borrow.setBorrowStyle(hjhPlanAsset.getBorrowStyle());
        // 借款期限
        borrow.setBorrowPeriod(hjhPlanAsset.getBorrowPeriod());

        // 借款利率
        borrow.setBorrowApr(new BigDecimal(borrowFinmanNewCharge.getAutoBorrowApr()).multiply(new BigDecimal(100)));
        borrow.setLateInterestRate(borrowFinmanNewCharge.getLateInterest()); //逾期利率(汇计划用)late_interest_rate
        borrow.setLateFreeDays(borrowFinmanNewCharge.getLateFreeDays()); // 逾期免息天数(汇计划用)late_free_days


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


        borrow.setRegistStatus(0);// 银行备案状态

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

        borrow.setRepayNextTime(0);// 插入时不用的字段
        borrow.setRepayFullStatus(0);// 插入时不用的字段
        borrow.setRepayFeeNormal(BigDecimal.ZERO); // 插入时不用的字段

        // 初审核人
        borrow.setVerifyUserid("0");
        // 正式发标时间
        borrow.setVerifyTime(0);
        // 初审通过时间
        borrow.setVerifyOverTime(0);
        // 初审核备注
        borrow.setVerifyRemark("");
        borrow.setVerifyContents(""); // 插入时不用的字段 审核备注
        borrow.setVerifyStatus(0); // 插入时不用的字段
        // 复审核人
        borrow.setReverifyUserid("0");
        // 复审核时间
        borrow.setReverifyTime(0);
        // 复审核备注
        borrow.setReverifyRemark("");
        borrow.setReverifyStatus(0); // 插入时不用的字段
        borrow.setReverifyContents(""); // 插入时不用的字段 审核复审标注

//		// 担保机构 风险控制措施-机构
//		borrow.setBorrowMeasuresInstit(hjhPlanAsset.getBorrowMeasuresInstit());
//		// 机构介绍
//		borrow.setBorrowCompanyInstruction(hjhPlanAsset.getBorrowCompanyInstruction());
//		// 操作流程
//		borrow.setBorrowOperatingProcess(hjhPlanAsset.getBorrowOperatingProcess());
//		// 抵押物信息 风险控制措施-抵押物
//		borrow.setBorrowMeasuresMort(hjhPlanAsset.getBorrowMeasuresMort());
//		// 本息保障 险控制措施-措施  风控措施  现在默认写死
        // 项目类型
        borrow.setProjectType(hjhAssetBorrowType.getBorrowCd());
        // 融资服务费
        borrow.setServiceFeeRate(borrowFinmanNewCharge.getChargeRate());
        // 账户管理费率
        borrow.setManageFeeRate(borrowFinmanNewCharge.getManChargeRate());
        // 收益差率
        borrow.setDifferentialRate(borrowFinmanNewCharge.getReturnRate());
        // 定时发标
        borrow.setOntime(0);

        // 汇资管的内容设置
//		this.setHZGInfo(borrowBean, borrow);
        // 更新时间
        borrow.setUpdatetime(systemNowDate);


        return borrow;

    }

    /**
     * 算募集时间
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

    /**
     * 获取系统配置
     *
     * @return
     */
    @Override
    public String getBorrowConfig(String configCd) {
        BorrowConfig borrowConfig = this.borrowConfigMapper.selectByPrimaryKey(configCd);
        return borrowConfig.getConfigValue();
    }

    /**
     * 项目类型
     *
     * @return
     * @author Administrator
     */
    private String getBorrowProjectClass(String borrowCd) {
        BorrowProjectTypeExample example = new BorrowProjectTypeExample();
        BorrowProjectTypeExample.Criteria cra = example.createCriteria();
        cra.andStatusEqualTo(0);
        cra.andBorrowCdEqualTo(borrowCd);

        List<BorrowProjectType> list = this.borrowProjectTypeMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0).getBorrowClass();
        }
        return "";
    }

    /**
     * 获取平台项目编号信息
     * @param borrowCd
     */
    private BorrowProjectType getProjectType(String borrowCd) {
        BorrowProjectType borrowProjectType = null;
        BorrowProjectTypeExample example = new BorrowProjectTypeExample();
        BorrowProjectTypeExample.Criteria cra = example.createCriteria();
        cra.andBorrowCdEqualTo(borrowCd);
        cra.andStatusEqualTo(0);


        List<BorrowProjectType> projectTypes = this.borrowProjectTypeMapper.selectByExample(example);
        if(projectTypes != null && projectTypes.size() ==1){
            borrowProjectType = projectTypes.get(0);
        }

        return borrowProjectType;
    }

    /**
     * 验证资产风险保证金是否足够（redis）
     * @param hjhPlanAsset
     * @return
     */
    private Integer checkAssetCanSend(HjhPlanAsset hjhPlanAsset) {
        String instCode = hjhPlanAsset.getInstCode();
        HjhBailConfig bailConfig = this.getBailConfig(instCode);
        if(bailConfig == null){
            logger.error("没有添加保证金配置");
            return -1;
        }

        BigDecimal availableBalance = bailConfig.getRemainMarkLine();
        BigDecimal assetAcount = new BigDecimal(hjhPlanAsset.getAccount());

        // 可用发标额度余额校验
        if (BigDecimal.ZERO.compareTo(availableBalance) >= 0) {
            logger.info("资产编号："+hjhPlanAsset.getAssetId()+" 可用发标额度余额小于等于零 " + availableBalance);
            // 可用发标额度余额小于等于0不能发标
            return 1;
        }
        if(assetAcount.compareTo(availableBalance) > 0){
            // 可用发标额度余额不够不能发标
            return 1;
        }

        // 日推标额度校验
        BigDecimal dayAvailable = BigDecimal.ZERO;
        // 今日已用额度
        String dayUsedKey = RedisConstants.DAY_USED + instCode + "_" + GetDate.getDate("yyyyMMdd");
        BigDecimal dayUsed = getValueInRedis(dayUsedKey);
        logger.info("dayUsedKey: " + dayUsedKey + " day userd in redis: " + dayUsed);
        // 累积可用额度
        String accumulateKey = RedisConstants.DAY_MARK_ACCUMULATE + instCode;
        BigDecimal accumulate = getValueInRedis(accumulateKey);
        logger.info("accumulateKey: " + accumulateKey + " accumulate in redis: " + accumulate);
        dayAvailable = dayAvailable.add(bailConfig.getDayMarkLine()).subtract(dayUsed);
        if(bailConfig.getIsAccumulate() == 1){
            dayAvailable = dayAvailable.add(accumulate);
            logger.info("已开启日累计额度，当前可用额度：" + dayAvailable);
        }
        if(dayAvailable.compareTo(assetAcount) < 0){
            logger.info("日推标可用额度不足，资产编号：" + instCode + " 当前可用额度：{}，推送额度:{}", dayAvailable, assetAcount);
            return 23;
        }

        // 月推标额度校验
        BigDecimal monthAvailable = BigDecimal.ZERO;
        String monthKey = RedisConstants.MONTH_USED + instCode + "_" + GetDate.getDate("yyyyMM");
        BigDecimal monthUsed = getValueInRedis(monthKey);
        logger.info("monthKey: " + monthKey + " month userd in redis: " + monthUsed);
        monthAvailable = monthAvailable.add(bailConfig.getMonthMarkLine()).subtract(monthUsed);
        if(monthAvailable.compareTo(assetAcount) < 0){
            logger.info("月推标可用额度不足，资产编号：" + instCode + " 当前可用额度：{}，推送额度:{}", monthAvailable, assetAcount);
            return 24;
        }

        // 授信额度校验
        HjhBailConfigInfo configInfo = getConfigInfo(hjhPlanAsset.getBorrowStyle(), hjhPlanAsset.getInstCode());
        if(configInfo == null){
            logger.info("HjhBailConfigInfo不存在，机构编号：{}, 还款方式：{}", hjhPlanAsset.getInstCode(), hjhPlanAsset.getBorrowStyle());
            return -1;
        }

        if(configInfo.getIsNewCredit() == 1 && configInfo.getIsLoanCredit() != 1){
            // 新增授信校验
            if(!checkNewCredit(bailConfig, assetAcount)){
                return 21;
            }

        }else if(configInfo.getIsNewCredit() != 1 && configInfo.getIsLoanCredit() == 1){
            // 在贷余额授信校验
            if(!checkLoanCredit(bailConfig, assetAcount)){
                return 22;
            }

        }else if(configInfo.getIsNewCredit() == 1 && configInfo.getIsLoanCredit() == 1){
            // 新增授信校验或在贷余额校验一个校验通过就可以
            if(!checkNewCredit(bailConfig, assetAcount) && !checkLoanCredit(bailConfig, assetAcount)){
                return 21;
            }
        }
        // 新增授信校验或在贷余额校验都没有配置
        if(configInfo.getIsNewCredit() != 1 && configInfo.getIsLoanCredit() != 1){
            logger.error("因还款方式未配置保证金授信方式，推标失败");
            return -1;
        }

        return 0;
    }

    /**
     * 新增授信额度校验
     * @return
     */
    private boolean checkNewCredit(HjhBailConfig bailConfig, BigDecimal account){
        // 新增授信额度
        BigDecimal newCreditLine = bailConfig.getNewCreditLine();
        // 周期内发标已发额度
        BigDecimal cycLoanTotal = bailConfig.getCycLoanTotal().add(account);
        logger.info("周期内发标已发额度+本次推标额度：" + cycLoanTotal);

        if(newCreditLine.compareTo(cycLoanTotal) < 0){
            logger.info("周期内发标已发额度超过授信额度");
            return false;
        }
        return true;
    }

    /**
     * 在贷余额额度校验
     * @return
     */
    private boolean checkLoanCredit(HjhBailConfig bailConfig, BigDecimal account){
        // 在贷授信额度
        BigDecimal loanCreditLine = bailConfig.getLoanCreditLine();
        // 历史标的发标总额
        BigDecimal hisLoanTotal = bailConfig.getLoanMarkLine().add(account);
        logger.info("机构编号：{}, 发标已发额度+本次推标额度：{}", bailConfig.getInstCode(), hisLoanTotal);
        // 已还本金
        BigDecimal repayedCapital = bailConfig.getRepayedCapital();
        logger.info("机构编号：{}, 已还本金：{}", bailConfig.getInstCode(), repayedCapital);

        if(loanCreditLine.compareTo(hisLoanTotal.subtract(repayedCapital)) < 0){
            logger.info("机构编号：{}, 在贷余额额度已超过授信额度", bailConfig.getInstCode());
            return false;
        }
        return true;
    }

    /**
     * 获取保证金配置详情
     *
     * @param borrowStyle
     * @param instCode
     * @return
     */
    private HjhBailConfigInfo getConfigInfo(String borrowStyle, String instCode){
        HjhBailConfigInfoExample example = new HjhBailConfigInfoExample();
        example.createCriteria().andInstCodeEqualTo(instCode).andBorrowStyleEqualTo(borrowStyle).andDelFlgEqualTo(0);

        List<HjhBailConfigInfo> list = hjhBailConfigInfoMapper.selectByExample(example);
        if(list != null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    /**
     * 获取redis值
     *
     * @param key
     * @return
     */
    private BigDecimal getValueInRedis(String key){
        logger.info("get value in redis, key:{}", key);
        String value = RedisUtils.get(key);
        logger.info("value in redis, key:{}, value:{}", key, value);

        if (StringUtils.isBlank(value)){
            RedisUtils.set(key, "0");
            return BigDecimal.ZERO;
        }
        return new BigDecimal(value);
    }

}
