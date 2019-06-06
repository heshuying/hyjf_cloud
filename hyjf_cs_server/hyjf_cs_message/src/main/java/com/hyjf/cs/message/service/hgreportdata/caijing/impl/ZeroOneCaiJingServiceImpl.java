package com.hyjf.cs.message.service.hgreportdata.caijing.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.CertificateAuthorityRequest;
import com.hyjf.am.resquest.user.LoanSubjectCertificateAuthorityRequest;
import com.hyjf.am.vo.hgreportdata.caijing.ZeroOneBorrowDataVO;
import com.hyjf.am.vo.hgreportdata.caijing.ZeroOneDataVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowManinfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowUserVO;
import com.hyjf.am.vo.user.CertificateAuthorityVO;
import com.hyjf.am.vo.user.LoanSubjectCertificateAuthorityVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.ZeroOneCaiJingEnum;
import com.hyjf.common.http.HttpDeal;
import com.hyjf.common.security.util.MD5;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.message.bean.hgreportdata.caijing.CaiJingPresentationLog;
import com.hyjf.cs.message.bean.hgreportdata.caijing.ZeroOneDataEntity;
import com.hyjf.cs.message.bean.hgreportdata.caijing.ZeroOneResponse;
import com.hyjf.cs.message.client.AmTradeClient;
import com.hyjf.cs.message.client.AmUserClient;
import com.hyjf.cs.message.service.hgreportdata.caijing.CaiJingPresentationLogService;
import com.hyjf.cs.message.service.hgreportdata.caijing.ZeroOneCaiJingService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author: yinhui
 * @Date: 2019/6/3 10:33
 * @Version 1.0
 */
@Service
public class ZeroOneCaiJingServiceImpl implements ZeroOneCaiJingService {

    private final Logger logger = LoggerFactory.getLogger(ZeroOneCaiJingServiceImpl.class);

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private CaiJingPresentationLogService presentationLogService;


    /**
     * 借款记录接口报送
     */
    @Override
    public void borrowRecordSub() {
        logger.info("借款记录接口报送开始");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = GetDate.date_sdf.format(cal.getTime());
//        String dateStart = GetDate.getDayStart(yesterday);
//        String dateEnd = GetDate.getDayEnd(yesterday);
        String dateStart = "2018-4-24";
        String dateEnd = "2018-4-24";
        logger.info("借款记录接口查询开始时间：" + dateStart, "结束时间：" + dateEnd);
        List<ZeroOneBorrowDataVO> borrowDataVOList = amTradeClient.queryBorrowRecordSub(dateStart, dateEnd);
        CaiJingPresentationLog presentationLog = new CaiJingPresentationLog();
        presentationLog.setLogType("借款记录");
        presentationLog.setCount(borrowDataVOList.size());
        if (!CollectionUtils.isEmpty(borrowDataVOList)) {
            logger.info("借款记录接口报送数据为空");
            return;
        }
        logger.info("借款记录接口报送条数=" + borrowDataVOList.size());

        for (ZeroOneBorrowDataVO voList : borrowDataVOList) {
            String customerCAId = queryCustomerCAId(voList.getId());
            if (customerCAId == null) {
                logger.info("借款记录接口报送,报送标的编号" + voList.getId() + "的借款用户编号为空");
                return;
            }
            voList.setUsername(customerCAId);
            voList.setUserid(customerCAId);
        }
        ZeroOneResponse zeroOneResponse = sendDataReport(ZeroOneCaiJingEnum.LEND.getName(), String.valueOf(JSONObject.toJSON(borrowDataVOList)));
        if (zeroOneResponse != null && zeroOneResponse.result_code == 1) {
            //报送成功
            logger.info("出借记录接口报送成功");
            presentationLog.setStatus(1);
            presentationLog.setDescription("");
        } else {
            presentationLog.setStatus(0);
            presentationLog.setDescription(zeroOneResponse.result_msg);
        }
        //插入mongo表
        this.presentationLogService.insertLog(presentationLog);
        logger.info("出借记录接口报送结束");
    }

    /**
     * 投资记录接口报送
     */
    @Override
    public void investRecordSub(){
        logger.info("投资记录接口报送开始");
//        String date = "2019-02-02";
        String date = "2018-4-24";

        List<ZeroOneDataVO> zeroOneDataVOList = amTradeClient.queryInvestRecordSub(date,date);

        if(zeroOneDataVOList == null || zeroOneDataVOList.size() == 0){
            logger.info("投资记录接口无数据报送结束");
            return;
        }
        logger.info("投资记录接口报送条数="+zeroOneDataVOList.size());

        Set<Integer> listUserId = new HashSet<>();
        for(ZeroOneDataVO voList : zeroOneDataVOList){
            listUserId.add(voList.getUserIds());
        }

        Map<Integer,String> mapUserId =queryCustomerId(listUserId);
        if(mapUserId == null || mapUserId.size() == 0){
            logger.info("投资记录接口报送结束,报送用户id为空");
            return;
        }

        for(ZeroOneDataVO voList : zeroOneDataVOList){
            if(mapUserId.get(voList.getUserIds()) != null){
                voList.setUsername(mapUserId.get(voList.getUserIds()));
                voList.setUserid(mapUserId.get(voList.getUserIds()));
            }else{
                logger.info("投资记录接口报送 当前用户编号为空,userId="+voList.getUserIds());
                voList.setUserid(null);
                voList.setUsername(null);
            }

        }

        List<ZeroOneDataEntity> list = CommonUtils.convertBeanList(zeroOneDataVOList, ZeroOneDataEntity.class);

        ZeroOneResponse zeroOneResponse = sendDataReport(ZeroOneCaiJingEnum.INVEST.getName(),String.valueOf(JSONObject.toJSON(list)));
        if(zeroOneResponse != null && zeroOneResponse.result_code == 1){
            //报送成功
            logger.info("投资记录接口报送成功");
        }
        logger.info("投资记录接口报送结束");
    }

    /**
     * 提前还款接口报送
     */
    @Override
    public void advancedRepay() {
        logger.info("提前还款接口报送开始");
        String startdate = "2019-05-07";
        String enddate = "2019-05-09";

        List<ZeroOneDataVO> zeroOneDataVOList = amTradeClient.queryAdvancedRepay(startdate, enddate);

        if (zeroOneDataVOList == null || zeroOneDataVOList.size() == 0) {
            logger.info("提前还款接口无数据报送结束");
            return;
        }
        List<ZeroOneDataEntity> list = CommonUtils.convertBeanList(zeroOneDataVOList, ZeroOneDataEntity.class);

        ZeroOneResponse zeroOneResponse = sendDataReport(ZeroOneCaiJingEnum.ADVANCEDREPAY.getName(),String.valueOf(JSONObject.toJSON(list)));
        if(zeroOneResponse != null && zeroOneResponse.result_code == 1){
            //报送成功
            logger.info("提前还款接口报送成功");
        }
        logger.info("提前还款接口报送结束");
    }

    /**
     * 获取借款用户编号
     *
     * @param borrowNid
     * @return
     */
    private String queryCustomerCAId(String borrowNid) {
        // 借款方CA认证客户编号
        String borrowerCustomerID = null;
        // 借款详情
        BorrowAndInfoVO borrow = this.amTradeClient.getBorrowByNid(borrowNid);
        if (borrow != null) {
            String planNid = borrow.getPlanNid();
            if (StringUtils.isNotBlank(planNid)) {
                // 借款人用户ID
                Integer borrowUserId = borrow.getUserId();
                // 借款人用户信息
                UserVO borrowUser = this.amUserClient.findUserById(borrowUserId);
                if (borrowUser == null) {
                    logger.error("根据借款人用户ID,查询借款人信息失败,借款人用户ID:[" + borrowUserId + "].");
                    throw new RuntimeException("根据借款人用户ID,查询借款人信息失败,借款人用户ID:[" + borrowUserId + "].");
                }

                // 借款人用户详情信息
                UserInfoVO borrowUserInfo = this.amUserClient.findUsersInfoById(borrowUserId);
                if (borrowUserInfo == null) {
                    logger.error("根据借款人用户ID,查询借款人详情信息失败,借款人用户ID:[" + borrowUserId + "].");
                    throw new RuntimeException("根据借款人用户ID,查询借款人详情信息失败,借款人用户ID:[" + borrowUserId + "].");
                }
                CertificateAuthorityVO certificateAuthorityVO = this.amUserClient
                        .selectCertificateAuthorityByUserId(String.valueOf(borrowUserId));
                if (Validator.isNotNull(certificateAuthorityVO)) {
                    borrowerCustomerID = certificateAuthorityVO.getCustomerId();
                    return borrowerCustomerID;
                }
            } else {
                if (StringUtils.isNotEmpty(borrow.getCompanyOrPersonal()) && "1".equals(borrow.getCompanyOrPersonal())) {
                    // 借款主体为企业借款
                    BorrowUserVO borrowUsers = this.amTradeClient.getBorrowUser(borrowNid);
                    if (borrowUsers == null) {
                        logger.error("根据标的编号查询借款主体为企业借款的相关信息失败,标的编号:[" + borrowNid + "]");
                        throw new RuntimeException("根据标的编号查询借款主体为企业借款的相关信息失败,标的编号:[" + borrowNid + "]");
                    }
                    // 获取CA认证客户编号
                    borrowerCustomerID = this.getCACustomerID(borrowUsers);
                    if (StringUtils.isBlank(borrowerCustomerID)) {
                        logger.error("企业借款获取CA认证客户编号失败,企业名称:[" + borrowUsers.getUsername() + "],社会统一信用代码:["
                                + borrowUsers.getSocialCreditCode() + "].");
                        throw new RuntimeException("企业借款获取CA认证客户编号失败,企业名称:[" + borrowUsers.getUsername() + "],社会统一信用代码:["
                                + borrowUsers.getSocialCreditCode() + "].");
                    }
                    return borrowerCustomerID;
                } else if (StringUtils.isNotEmpty(borrow.getCompanyOrPersonal()) && "2".equals(borrow.getCompanyOrPersonal())) {
                    // 借款主体为个人借款
                    BorrowManinfoVO borrowManinfo = this.amTradeClient.getBorrowManinfo(borrowNid);
                    if (borrowManinfo == null) {
                        logger.error("借款主体为个人借款时,获取个人借款信息失败,标的编号:[" + borrowNid + "].");
                        throw new RuntimeException("借款主体为个人借款时,获取个人借款信息失败,标的编号:[" + borrowNid + "].");
                    }
                    // 获取CA认证客户编号
                    borrowerCustomerID = this.getPersonCACustomerID(borrowManinfo);
                    logger.info("获得借款人认证编号：" + borrowerCustomerID);
                    if (StringUtils.isBlank(borrowerCustomerID)) {
                        logger.error("获取个人借款CA认证客户编号失败,姓名:[" + borrowManinfo.getName() + "],身份证号:["
                                + borrowManinfo.getCardNo() + "].");
                        throw new RuntimeException("获取个人借款CA认证客户编号失败,姓名:[" + borrowManinfo.getName() + "],身份证号:["
                                + borrowManinfo.getCardNo() + "].");
                    }
                    return borrowerCustomerID;
                }
            }
        }
        return null;
    }


    /**
     * 获取借款主体为企业的CA认证客户编号
     *
     * @param borrowUsers
     * @return
     */
    private String getCACustomerID(BorrowUserVO borrowUsers) {
        CertificateAuthorityRequest request = new CertificateAuthorityRequest();
        request.setTrueName(borrowUsers.getUsername());
        request.setIdNo(borrowUsers.getSocialCreditCode());
        request.setIdType(1);
        List<CertificateAuthorityVO> list = this.amUserClient.getCertificateAuthorityList(request);
        if (list != null && list.size() > 0) {
            return list.get(0).getCustomerId();
        }
        // 借款主体CA认证记录表
        LoanSubjectCertificateAuthorityRequest request1 = new LoanSubjectCertificateAuthorityRequest();
        request1.setName(borrowUsers.getUsername());
        request1.setIdType(1);
        request1.setIdNo(borrowUsers.getSocialCreditCode());
        List<LoanSubjectCertificateAuthorityVO> resultList = this.amUserClient
                .getLoanSubjectCertificateAuthorityList(request1);

        if (resultList != null && resultList.size() > 0) {
            return resultList.get(0).getCustomerId();
        }
        return null;
    }

    /**
     * 获得用户编号
     * @param userId
     * @return
     */
    private Map<Integer,String> queryCustomerId(Set<Integer> userId){
        if(userId == null || userId.size() == 0){
            return null;
        }
        Map<Integer,String> mapUserId = new HashMap<>();

        List<CertificateAuthorityVO> voList = amUserClient.queryCustomerId(userId);
        for(CertificateAuthorityVO vo : voList){
            mapUserId.put(vo.getUserId(),vo.getCustomerId());
        }
        return mapUserId;
    }

    /**
     * 数据推送
     * @param type 类型，借款=lend ,出借=invest ,提前还款=advanced-repay
     * @param json 数据json
     * @return
     */
    private ZeroOneResponse sendDataReport(String type,String json){

        String url = "http://data.caijing.com/hub/p2p/";

        StringBuilder stbuUrl = new StringBuilder();
        stbuUrl.append(url);
        stbuUrl.append(type);
        stbuUrl.append("/commit.json");

        Map<String,String> sendMap = new HashMap<>();
        ZeroOneResponse zeroOneResponse = null;
        try{
            sendMap.put("visit_key","www.hyjf.com");
            sendMap.put("time",String.valueOf(System.currentTimeMillis()));
            sendMap.put("data",json);

            //Sige md5(密钥&visit_key=域名&time=时间戳&data=data字符串的byte长度)
            StringBuilder stringBuilder = new StringBuilder();
            //生产密钥
            stringBuilder.append("5A25DD85BD76829D537C7E59AA5DA766");
            stringBuilder.append("&visit_key=");
            stringBuilder.append(sendMap.get("visit_key"));
            stringBuilder.append("&time=");
            stringBuilder.append(sendMap.get("time"));
            stringBuilder.append("&data=");
            stringBuilder.append(json.getBytes().length);

            String sign = MD5.toMD5CodeCaps(stringBuilder.toString());

            sendMap.put("sign",sign);

            //Post请求
            String response = HttpDeal.post(stbuUrl.toString(),sendMap);

            zeroOneResponse = JSONObject.parseObject(response,ZeroOneResponse.class);

        }catch (Exception e){
            logger.error("零壹财经数据报送错误 error:",e);
        }

        return zeroOneResponse;
    }

    /**
     * 获取借款主体为个人的CA认证客户编号
     *
     * @param borrowManinfo
     * @return
     */
    private String getPersonCACustomerID(BorrowManinfoVO borrowManinfo) {
        // 用户CA认证记录表
        CertificateAuthorityRequest request = new CertificateAuthorityRequest();
        request.setTrueName(borrowManinfo.getName());
        request.setIdNo(borrowManinfo.getCardNo());
        request.setIdType(0);
        List<CertificateAuthorityVO> list = this.amUserClient.getCertificateAuthorityList(request);
        if (list != null && list.size() > 0) {
            return list.get(0).getCustomerId();
        }
        // 借款主体CA认证记录表
        LoanSubjectCertificateAuthorityRequest request1 = new LoanSubjectCertificateAuthorityRequest();
        request1.setName(borrowManinfo.getName());
        request1.setIdType(0);
        request1.setIdNo(borrowManinfo.getCardNo());
        List<LoanSubjectCertificateAuthorityVO> resultList = this.amUserClient
                .getLoanSubjectCertificateAuthorityList(request1);
        if (resultList != null && resultList.size() > 0) {
            return resultList.get(0).getCustomerId();
        }
        return null;
    }
}
