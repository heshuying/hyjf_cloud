package com.hyjf.cs.trade.service.consumer.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.resquest.hgreportdata.cert.CertReportEntitRequest;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.hgreportdata.cert.CertErrLogVO;
import com.hyjf.am.vo.hgreportdata.cert.CertLogVO;
import com.hyjf.am.vo.hgreportdata.cert.CertReportEntityVO;
import com.hyjf.am.vo.hgreportdata.cert.CertUserVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.service.BaseClient;
import com.hyjf.cs.trade.client.AmConfigClient;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallUtil;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertSendUtils;
import com.hyjf.cs.trade.service.consumer.BaseHgCertReportService;
import org.apache.commons.lang3.StringUtils;
import org.cert.open.CertToolV1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

//import com.hyjf.mybatis.model.auto.*;

/**
 * @author sss
 */
@Service
public class BaseHgCertReportServiceImpl  implements BaseHgCertReportService {

    Logger logger = LoggerFactory.getLogger(BaseHgCertReportServiceImpl.class);
    @Autowired
    SystemConfig systemConfig;
    @Autowired
    AmUserClient amUserClient;
    @Autowired
    AmTradeClient amTradeClient;
    @Autowired
    AmConfigClient amConfigClient;
    @Autowired
    private BaseClient baseClient;

    private String baseUrl = "http://CS-MESSAGE/cs-message/certStatistical/";

    /** Jedis  */
    public static JedisPool pool = RedisUtils.getPool();

    /**
     * 工具类
     */
    public static CertToolV1 tool = new CertToolV1();

    /**
     * 调用webservice接口并返回数据
     * @author sunss
     * @param bean
     * @return
     */
    @Override
    public CertReportEntityVO insertAndSendPost(CertReportEntityVO bean) {
        try {
            // 设置共通参数
            bean = setCommonParam(bean);
        }catch (Exception e) {
            logger.error("设置参数失败",e);
        }
        Map<String, String> params = getBankParam(bean);
        // 插入mongo
        this.insertCertReport(bean);
        String rtnMsg = CertSendUtils.postRequest(systemConfig.getCertCrtpath(),bean.getUrl(), params );
        logger.info("返回结果为:"+rtnMsg);
        bean = setResult(bean,rtnMsg);
        // 如果错误的话  保存到错误表里面
        if(!CertCallConstant.CERT_RETURN_STATUS_SUCCESS.equals(bean.getReportStatus())){
            JSONObject resp = CertCallUtil.parseResult(rtnMsg);
            String retCode = resp.getString("code");
            String retMess = resp.getString("message");
            CertErrLogVO errLog = new CertErrLogVO();
            errLog.setInfType(Integer.parseInt(bean.getInfType()));
            errLog.setLogOrdId(bean.getLogOrdId());
            errLog.setSendCount(1);
            errLog.setSendTime(GetDate.getNowTime10());
            errLog.setSendStatus(Integer.parseInt(bean.getReportStatus()));
            /*if(bean != null){
                errLog.setResultCode(retCode);
                errLog.setResultMsg(retMess);
            }else{
                errLog.setResultCode("");
                errLog.setResultMsg("");
            }*/
            errLog.setResultCode(retCode);
            errLog.setResultMsg(retMess);
            amConfigClient.insertCertErrorLog(errLog);
        }
        return bean;
    }

    /**
     * 组装参数
     * @param bean
     * @return
     */
    protected Map<String, String> getBankParam(CertReportEntityVO bean){
        // 组装调用接口需要的参数对象
        JSONObject bankParam = new JSONObject();
        bankParam.put("version", bean.getVersion());
        bankParam.put("batchNum", bean.getBatchNum());
        bankParam.put("checkCode", bean.getCheckCode());
        bankParam.put("totalNum", bean.getTotalNum());
        bankParam.put("sentTime", bean.getSentTime());
        bankParam.put("sourceCode", bean.getSourceCode());
        bankParam.put("infType", bean.getInfType());
        bankParam.put("dataType", bean.getDataType());
        bankParam.put("timestamp", bean.getTimestamp());
        bankParam.put("nonce", bean.getNonce());
        bankParam.put("dataList", bean.getDataList().toJSONString());

        Map<String, String> params = new HashMap<>(2);
        params.put("apiKey", bean.getApiKey());
        params.put("msg", bankParam.toJSONString());
        return params;
    }

    /**
     * 设置返回结果
     * @param bean
     * @param rtnMsg
     * @return
     */
    protected CertReportEntityVO setResult(CertReportEntityVO bean, String rtnMsg) {
        // 上报结果  0初始，1成功，9失败 99 无响应
        // 返回结果  例  {"resultMsg": {"code": "0000","message": "[调试]数据已成功上报，正在等待处理，请使用对账接口查看数据状态"}
        JSONObject resp = CertCallUtil.parseResult(rtnMsg);
        String retCode = resp.getString("code");
        String retMess = resp.getString("message");
        CertLogVO certLog = new CertLogVO();
        if(null == rtnMsg||rtnMsg.equals("")){
            //请求失败  无响应
            bean.setReportStatus(CertCallConstant.CERT_RETURN_STATUS_NO);
            certLog.setSendStatus(Integer.parseInt(CertCallConstant.CERT_RETURN_STATUS_NO));
        }else{

            if(CertCallConstant.CERT_RESPONSE_SUCCESS.equals(retCode)){
                // 请求成功
                bean.setReportStatus(CertCallConstant.CERT_RETURN_STATUS_SUCCESS);
                certLog.setSendStatus(Integer.parseInt(CertCallConstant.CERT_RETURN_STATUS_SUCCESS));
            }else{
                // 请求失败
                bean.setReportStatus(CertCallConstant.CERT_RETURN_STATUS_FAIL);
                certLog.setSendStatus(Integer.parseInt(CertCallConstant.CERT_RETURN_STATUS_FAIL));
            }
        }
        bean.setRetMess(rtnMsg);
        // 操作数据库 修改
        CertReportEntitRequest certReportEntitRequest = new CertReportEntitRequest();
        BeanUtils.copyProperties(bean,certReportEntitRequest);
        certReportEntitRequest.setResp(resp.toJSONString());
        this.updateCertReport(certReportEntitRequest);
        // 插入发送记录表
        certLog.setResultCode(retCode);
        if(rtnMsg!=null &&rtnMsg.length()>200){
            String sub_rtnMsg = StringUtils.left(rtnMsg,200);
            certLog.setResultMsg(sub_rtnMsg);
        }else {
            certLog.setResultMsg(rtnMsg);
        }

        this.insertCertLog(certLog,bean);

        return bean;
    }

    /**
     * 插入发送记录表
     * @param certLog
     * @param bean
     */
    private boolean insertCertLog(CertLogVO certLog, CertReportEntityVO bean) {
        certLog.setInfType(Integer.parseInt(bean.getInfType()));
        certLog.setLogOrdId(bean.getLogOrdId());
        certLog.setSendTime(GetDate.getNowTime10());
        certLog.setQueryResult(0);
        return amConfigClient.insertCertLog(certLog);
    }

    /**
     * 设置共通的参数
     * @param bean
     * @return
     */
    protected CertReportEntityVO setCommonParam(CertReportEntityVO bean) throws Exception {
        bean.setVersion(CertCallConstant.CERT_CALL_VERSION);
        JSONArray msg = bean.getDataList();
        long timestamp = System.currentTimeMillis();
        // seqId 规则  今天的递增字段+2位随机数
        String seqId = getBathNum() + CertCallUtil.getRandomNum(100);
        // 交易发生时间
        String tradeDate = "";
        if(bean.getTradeDate()!=null && !"".equals(bean.getTradeDate())){
            tradeDate = bean.getTradeDate();
        }else{
            tradeDate = GetDate.formatTime3();
        }
        bean.setTradeDate(tradeDate);
        // num 说明：如果推送 2017-01-01 到 2017-01-07 七天的数据，则 num 为 7。
        String dateNum =  bean.getDateNum()==null?"0":bean.getDateNum();
        // 批次号 规则  平台编码+交易时间+交易范围数+自增长字段+2位随机数
        String batchNum = tool.batchNumber(systemConfig.getCertSourceCode(), tradeDate ,dateNum,seqId);
        // 随机数
        String nonce = Integer.toHexString(new Random().nextInt());
        String token = CertCallUtil.getApiKey(systemConfig.getCertApiKey(), systemConfig.getCertSourceCode(), bean.getVersion(), timestamp, nonce);
        String url =systemConfig.getCertSeverPath()+ CertCallUtil.getUrl(bean.getInfType());
        // 判断是否测试环境
        if (systemConfig.getCertIsTest() == null || "true".equals(systemConfig.getCertIsTest())) {
            // 如果是测试环境
            url += CertCallConstant.CERT_TEST_URL;
            // 测试数据
            bean.setDataType("0");
        }else{
            // 正式数据
            bean.setDataType("1");
        }

        bean.setUrl(url);
        // 设置共通的值
        bean.setTimestamp(timestamp+"");
        bean.setNonce(nonce);
        bean.setSourceCode(systemConfig.getCertSourceCode());
        bean.setApiKey(token);
        bean.setCheckCode(tool.checkCode(timestamp+""));
        bean.setTotalNum(msg.size()+"");
        bean.setSentTime(GetDate.formatTime2());
        bean.setBatchNum(batchNum);
        bean.setLogOrdId(bean.getInfType()+"_"+batchNum);
        // 设置初始状态
        bean.setReportStatus(CertCallConstant.CERT_RETURN_STATUS_INIT);
        return bean;
    }



    /**
     * 根据用户ID获取企业开户信息
     * @param userId
     * @return
     * @author Michael
     */
    @Override
    public CorpOpenAccountRecordVO getCorpOpenAccountRecord(Integer userId) {
        CorpOpenAccountRecordVO corpOpenAccountRecordVO=amUserClient.getCorpOpenAccountRecord(userId);
        return corpOpenAccountRecordVO;
    }

    /**
     * 根据借款编号取借款主体为企业用户的信息
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowUserVO getBorrowUsers(String borrowNid) {
        BorrowUserVO borrowUserVO = amTradeClient.getBorrowUser(borrowNid);
        return borrowUserVO;
    }

    /**
     * 根据标的号检索借款主体个人借款信息
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowManinfoVO getBorrowManInfo(String borrowNid) {
        BorrowManinfoVO borrowManinfoVO = amTradeClient.getBorrowManinfo(borrowNid);
        return borrowManinfoVO;
    }

    /**
     * 获取风险测评结果
     * @param userId 用户ID
     * @return
     */
    @Override
    public UserEvalationResultVO getUserEvalationResult(Integer userId) {
        UserEvalationResultVO userEvalationResultVO = amUserClient.selectUserEvalationResultByUserId(userId);
        return userEvalationResultVO;
    }


    /**
     * 根据用户Id,查询用户银行卡信息
     *
     * @param userId
     * @return
     */
    @Override
    public BankCardVO getBankCardById(Integer userId) {
        BankCardVO bankCardVO = amUserClient.getBankCardByUserId(userId);
        return bankCardVO;
    }

    /**
     * 查询是否已经上送了
     *
     * @param userId
     * @return
     */
    @Override
    public CertUserVO getCertUserByUserId(Integer userId) {
        return amUserClient.getCertUserByUserId(userId);
    }

    /**
     * 获得批次号
     * @return
     */
    private String getBathNum(){
        Jedis jedis = pool.getResource();
        try{
            // 操作redis
            while ("OK".equals(jedis.watch(RedisConstants.CERT_BATCH_NUMBER_SEQ_ID))) {
                String numberStr = RedisUtils.get(RedisConstants.CERT_BATCH_NUMBER_SEQ_ID);
                JSONObject number = JSONObject.parseObject(numberStr);
                String nowData = GetDate.formatTimeYYYYMM();
                if(nowData.equals(number.get("nowData"))) {
                    Transaction tx = jedis.multi();
                    // 如果日期相等就直接加1
                    Integer seqId = number.getInteger("seqId");
                    ++seqId;
                    number.put("seqId",seqId);
                    tx.set(RedisConstants.CERT_BATCH_NUMBER_SEQ_ID, number.toJSONString());
                    List<Object> result1 = tx.exec();
                    if (result1 == null || result1.isEmpty()) {
                        continue;
                    } else {
                        // 成功
                        return seqId+"";
                    }
                }else {
                    // 重置为1
                    Transaction tx = jedis.multi();
                    number = new JSONObject();
                    number.put("nowData",nowData);
                    // 如果日期相等就直接加1
                    number.put("seqId","1");
                    tx.set(RedisConstants.CERT_BATCH_NUMBER_SEQ_ID, number.toJSONString());
                    List<Object> result1 = tx.exec();
                    if (result1 == null || result1.isEmpty()) {
                        continue;
                    } else {
                        // 成功
                        return "1";
                    }
                }

            }
        }catch (Exception e){
            logger.info("抛出异常:[{}]",e);
        }finally {
            RedisUtils.returnResource(pool, jedis);
        }
        return null;
    }

    /**
     * 根据用户哈希值查询是否已经上报过了
     * @param userIdcardHash
     * @return
     */
    @Override
    public CertUserVO getCertUserByUserIdcardHash(String userIdcardHash) {
        return amUserClient.getCertUserByUserIdcardHash(userIdcardHash);
    }

    /**
     * 根据借款人id和标的号查询
     * @param userId
     * @param borrowNid
     * @return
     */
    protected CertUserVO getCertUserByUserIdBorrowNid(Integer userId, String borrowNid) {
        return amUserClient.getCertUserByUserIdBorrowNid(userId,borrowNid);
    }

    /**
     * 获取标的的哈希值
     * @param borrow
     * @return
     * @throws Exception
     */
    protected String getUserHashValue(BorrowAndInfoVO borrow) throws Exception {
        String userIdcard = "";
        if (borrow != null) {
            if ("1".equals(borrow.getCompanyOrPersonal())) {
                // 公司
                BorrowUserVO borrowUsers = getBorrowUsers(borrow.getBorrowNid());
                // 统一社会信用代码
                userIdcard = borrowUsers.getSocialCreditCode();
                if (borrowUsers.getSocialCreditCode() == null || "".equals(borrowUsers.getSocialCreditCode())) {
                    // 注册号
                    userIdcard = borrowUsers.getRegistCode();
                }
            } else {
                // 个人
                BorrowManinfoVO borrowManinfo = getBorrowManInfo(borrow.getBorrowNid());
                // 身份证号
                userIdcard = borrowManinfo.getCardNo();
            }
        }
        return tool.idCardHash(userIdcard);
    }

    /**
     * 根据用户ID查询上报的标的
     *
     * @param userId
     * @return
     */
    @Override
    public List<CertUserVO> getCertUsersByUserId(int userId) {
        return amUserClient.getCertUsersByUserId(userId);
    }

    /**
     * 检查redis的值是否允许运行 允许返回true  不允许返回false
     *
     * @return
     */
    @Override
    public boolean checkCanRun() {
        // 合规数据上报 CERT 是否开始实时上报数据  0不上报  1上报
        String canRun = RedisUtils.get(RedisConstants.CERT_CAN_RUN);
        if (canRun == null || canRun.length() == 0) {
            // 如果是空的话不允许运行
            return false;
        }
        if ("1".equals(canRun)) {
            // 如果是1的话允许上报
            return true;
        }
        return false;
    }
    /**
     * 插入mongo，保存报送记录
     *
     * @param certReportEntityVO
     */
    public boolean insertCertReport(CertReportEntityVO certReportEntityVO) {
        String url = baseUrl + "insertAndSendPost";
        BooleanResponse response = this.baseClient.postExe(url, certReportEntityVO, BooleanResponse.class);
        return response.getResultBoolean();
    }
    /**
     * 修改mongo，修改报送状态
     *
     * @param certReportEntityVO
     */
    public boolean updateCertReport(CertReportEntitRequest certReportEntityVO) {
        String url = baseUrl + "updateCertReport";
        BooleanResponse response = this.baseClient.postExe(url, certReportEntityVO, BooleanResponse.class);
        return response.getResultBoolean();
    }
}
