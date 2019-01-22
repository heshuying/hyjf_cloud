package com.hyjf.cs.trade.service.consumer.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.cert.CertLogVO;
import com.hyjf.am.vo.trade.cert.CertReportEntityVO;
import com.hyjf.am.vo.trade.cert.CertUserVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.UserEvalationResultCustomizeVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.cs.trade.service.consumer.BaseHgCertReportService;
import org.apache.commons.lang3.StringUtils;
import org.cert.open.CertToolV1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.hyjf.mybatis.model.auto.*;

/**
 * @author sss
 */
@Service
public class BaseHgCertReportServiceImpl  implements BaseHgCertReportService {

    Logger logger = LoggerFactory.getLogger(BaseHgCertReportServiceImpl.class);

    @Autowired
//    protected CertReportDao certReportDao;

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
       /* try {
            // 设置共通参数
            bean = setCommonParam(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> params = getBankParam(bean);
        // 插入mongo
        this.certReportDao.insert(bean);
        String rtnMsg = CertSendUtils.postRequest(bean.getUrl(), params );
        logger.info("返回结果为:"+rtnMsg);
        bean = setResult(bean,rtnMsg);
        // 如果错误的话  保存到错误表里面
        if(!CertCallConstant.CERT_RETURN_STATUS_SUCCESS.equals(bean.getReportStatus())){
            JSONObject resp = CertCallUtil.parseResult(rtnMsg);
            String retCode = resp.getString("code");
            String retMess = resp.getString("message");
            CertErrLog errLog = new CertErrLog();
            errLog.setInfType(Integer.parseInt(bean.getInfType()));
            errLog.setLogOrdId(bean.getLogOrdId());
            errLog.setSendCount(1);
            errLog.setSendTime(GetDate.getNowTime10());
            errLog.setSendStatus(Integer.parseInt(bean.getReportStatus()));
            if(bean != null){
                errLog.setResultCode(retCode);
                errLog.setResultMsg(retMess);
            }else{
                errLog.setResultCode("");
                errLog.setResultMsg("");
            }
            certErrLogMapper.insert(errLog);
        }*/
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
      /*  // 上报结果  0初始，1成功，9失败 99 无响应
        // 返回结果  例  {"resultMsg": {"code": "0000","message": "[调试]数据已成功上报，正在等待处理，请使用对账接口查看数据状态"}
        JSONObject resp = CertCallUtil.parseResult(rtnMsg);
        String retCode = resp.getString("code");
        String retMess = resp.getString("message");
        CertLog certLog = new CertLog();
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
        Query q1 = Query.query(Criteria.where("logOrdId").is(bean.getLogOrdId()));
        Update u1 = new Update();
        u1.set("reportStatus", bean.getReportStatus());
        u1.set("retMess", resp);
        this.certReportDao.update(q1, u1);
        // 插入发送记录表
        certLog.setResultCode(retCode);
        if(rtnMsg!=null &&rtnMsg.length()>200){
            String sub_rtnMsg = StringUtils.left(rtnMsg,200);
            certLog.setResultMsg(sub_rtnMsg);
        }else {
            certLog.setResultMsg(rtnMsg);
        }

        this.insertCertLog(certLog,bean);*/

        return bean;
    }

    /**
     * 插入发送记录表
     * @param certLog
     * @param bean
     */
    private void insertCertLog(CertLogVO certLog, CertReportEntityVO bean) {
        /*certLog.setInfType(Integer.parseInt(bean.getInfType()));
        certLog.setLogOrdId(bean.getLogOrdId());
        certLog.setSendTime(GetDate.getNowTime10());
        certLog.setQueryResult(0);
        certLogMapper.insert(certLog);*/
    }

    /**
     * 设置共通的参数
     * @param bean
     * @return
     */
    protected CertReportEntityVO setCommonParam(CertReportEntityVO bean) throws Exception {
       /* bean.setVersion(CertCallConstant.CERT_CALL_VERSION);
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
        String batchNum = tool.batchNumber(CertCallConstant.CERT_SOURCE_CODE, tradeDate ,dateNum,seqId);
        // 随机数
        String nonce = Integer.toHexString(new Random().nextInt());
        String token = CertCallUtil.getApiKey(CertCallConstant.CERT_API_KEY, CertCallConstant.CERT_SOURCE_CODE, bean.getVersion(), timestamp, nonce);
        String url = CertCallConstant.CERT_SEVER_PATH + CertCallUtil.getUrl(bean.getInfType());
        // 判断是否测试环境
        if (CertCallConstant.CERT_IS_TEST == null || "true".equals(CertCallConstant.CERT_IS_TEST)) {
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
        bean.setSourceCode(CertCallConstant.CERT_SOURCE_CODE);
        bean.setApiKey(token);
        bean.setCheckCode(tool.checkCode(timestamp+""));
        bean.setTotalNum(msg.size()+"");
        bean.setSentTime(GetDate.formatTime2());
        bean.setBatchNum(batchNum);
        bean.setLogOrdId(bean.getInfType()+"_"+batchNum);
        // 设置初始状态
        bean.setReportStatus(CertCallConstant.CERT_RETURN_STATUS_INIT);*/
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
        /*CorpOpenAccountRecordExample example = new CorpOpenAccountRecordExample();
        CorpOpenAccountRecordExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        cra.andIsBankEqualTo(1);//江西银行
        List<CorpOpenAccountRecord> list = this.corpOpenAccountRecordMapper.selectByExample(example);
        if(list != null && list.size() > 0){
            return list.get(0);
        }*/
        return null;
    }

    /**
     * 根据借款编号取借款主体为企业用户的信息
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowUserVO getBorrowUsers(String borrowNid) {
        /*BorrowUsersExample example = new BorrowUsersExample();
        BorrowUsersExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        List<BorrowUsers> list = this.borrowUsersMapper.selectByExample(example);
        if(list!= null && list.size()>0){
            return list.get(0);
        }*/
        return null;
    }

    /**
     * 根据标的号检索借款主体个人借款信息
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowManinfoVO getBorrowManInfo(String borrowNid) {
        /*BorrowManinfoExample example = new BorrowManinfoExample();
        BorrowManinfoExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowNidEqualTo(borrowNid);
        List<BorrowManinfo> list = this.borrowManinfoMapper.selectByExample(example);

        if (list != null && list.size() > 0 ){
            return list.get(0);
        }*/
        return null;
    }

    /**
     * 获取风险测评结果
     * @param userId 用户ID
     * @return
     */
    @Override
    public UserEvalationResultCustomizeVO getUserEvalationResult(Integer userId) {
        UserEvalationResultCustomizeVO userEvalationResultCustomize = null;
       /* UserEvalationResultExampleCustomize creditExample = new UserEvalationResultExampleCustomize();
        UserEvalationResultExampleCustomize.Criteria criteria = creditExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<UserEvalationResultCustomize> list = userEvalationResultCustomizeMapper.selectByExample(creditExample);

        if(list != null && list.size() > 0){
            userEvalationResultCustomize = list.get(0);
        }*/
        return userEvalationResultCustomize;
    }


    /**
     * 根据用户Id,查询用户银行卡信息
     *
     * @param userId
     * @return
     */
    @Override
    public BankCardVO getBankCardById(Integer userId) {
        /*BankCardExample example = new BankCardExample();
        BankCardExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId).andStatusEqualTo(1);
        List<BankCard> list = bankCardMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }*/

        return null;
    }

    /**
     * 查询是否已经上送了
     *
     * @param userId
     * @return
     */
    @Override
    public CertUserVO getCertUserByUserId(Integer userId) {
       /* CertUserExample example = new CertUserExample();
        CertUserExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        List<CertUser> list = certUserMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }*/

        return null;
    }

    /**
     * 获取项目类型
     *
     * @param projectType
     * @return
     */
    @Override
    public BorrowProjectTypeVO getBorrowProjectType(String projectType) {
        if (StringUtils.isBlank(projectType)) {
            return null;
        }
        BorrowProjectTypeVO borrowProjectType = null;
        /*// 查找用户
        BorrowProjectTypeExample borrowProjectTypeExample = new BorrowProjectTypeExample();
        BorrowProjectTypeExample.Criteria criteria2 = borrowProjectTypeExample.createCriteria();
        criteria2.andBorrowCdEqualTo(projectType);
        List<BorrowProjectType> list = borrowProjectTypeMapper.selectByExample(borrowProjectTypeExample);
        if (list != null && !list.isEmpty()) {
            borrowProjectType = list.get(0);

        }*/
        return borrowProjectType;
    }

    /**
     * 获得批次号
     * @return
     */
    private String getBathNum(){
        Jedis jedis = pool.getResource();
       /* // 操作redis
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

        }*/
        return null;
    }

    /**
     * 根据用户哈希值查询是否已经上报过了
     * @param userIdcardHash
     * @return
     */
    @Override
    public CertUserVO getCertUserByUserIdcardHash(String userIdcardHash) {
        /*CertUserExample example = new CertUserExample();
        CertUserExample.Criteria cra = example.createCriteria();
        cra.andUserIdCardHashEqualTo(userIdcardHash);
        List<CertUser> list = certUserMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }*/
        return null;
    }

    /**
     * 根据借款人id和标的号查询
     * @param userId
     * @param borrowNid
     * @return
     */
    protected CertUserVO getCertUserByUserIdBorrowNid(Integer userId, String borrowNid) {
       /* CertUserExample example = new CertUserExample();
        CertUserExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        cra.andBorrowNidEqualTo(borrowNid);
        List<CertUser> list = certUserMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }*/
        return null;
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
        //return tool.idCardHash(userIdcard);
        return null;
    }

    /**
     * 根据用户ID查询上报的标的
     *
     * @param userId
     * @return
     */
    @Override
    public List<CertUserVO> getCertUsersByUserId(int userId) {
        /*CertUserExample example = new CertUserExample();
        CertUserExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        List<CertUser> list = certUserMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list;
        }*/
        return null;
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
}
