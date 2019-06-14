package com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hyjf.am.vo.hgreportdata.cert.CertReportEntityVO;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.cert.open.CertException;
import org.cert.utils.BCrypt;
import org.cert.utils.Base64Coder;
import org.cert.utils.SHA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * //合规数据上报 CERT 共通方法
 * @author sunss
 * @version hyjf 1.0
 * @since hyjf 1.0 2018年11月26日
 */
public class CertCallUtil implements Serializable {
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -7131277696965280498L;

    // 创建 Pattern 对象
    private static Pattern r = Pattern.compile("(\\d{3})");
    static Logger logger = LoggerFactory.getLogger(CertCallUtil.class);

    /**
     * 获取URL
     * @param infType
     * @return
     */
	public static String getUrl(String infType){
	    // 用户信息
	    if(CertCallConstant.CERT_INF_TYPE_USER_INFO.equals(infType)){
	        return CertCallConstant.CERT_CALL_USER_INFO_URL;
        }
        // 散标
        if(CertCallConstant.CERT_INF_TYPE_SCATTER_INVEST.equals(infType)){
            return CertCallConstant.CERT_CALL_SCATTER_INVEST_URL;
        }
        // 散标状态
        if(CertCallConstant.CERT_INF_TYPE_STATUS.equals(infType)){
            return CertCallConstant.CERT_CALL_STATUS_URL;
        }
        // 还款计划
        if(CertCallConstant.CERT_INF_TYPE_REPAY_PLAN.equals(infType)){
            return CertCallConstant.CERT_CALL_REPAY_PLAN_URL;
        }
        // 产品信息
        if(CertCallConstant.CERT_INF_TYPE_FINANCE.equals(infType)){
            return CertCallConstant.CERT_CALL_FINANCE_URL;
        }
        // 产品散标配置
        if(CertCallConstant.CERT_INF_TYPE_FINANCE_SCATTER_CONFIG.equals(infType)){
            return CertCallConstant.CERT_CALL_FINANCE_SCATTER_CONFIG_URL;
        }
        // 债权信息
        if(CertCallConstant.CERT_INF_TYPE_CREDITOR.equals(infType)){
            return CertCallConstant.CERT_CALL_CREDITOR_URL;
        }
        // 转让项目
        if(CertCallConstant.CERT_INF_TYPE_TRANSFER_PROJECT.equals(infType)){
            return CertCallConstant.CERT_CALL_TRANSFER_PROJECT_URL;
        }
        // 转让状态
        if(CertCallConstant.CERT_INF_TYPE_TRANSFER_STATUS.equals(infType)){
            return CertCallConstant.CERT_CALL_TRANSFER_STATUS_URL;
        }
        // 承接信息
        if(CertCallConstant.CERT_INF_TYPE_UNDER_TAKE.equals(infType)){
            return CertCallConstant.CERT_CALL_UNDER_TAKE_URL;
        }
        // 交易流水
        if(CertCallConstant.CERT_INF_TYPE_TRANSACT.equals(infType)){
            return CertCallConstant.CERT_CALL_TRANSACT_URL;
        }
        return null;
    }

    /**
     * 获取订单编号   batchNum + 三位随机数 + 三位随机数
     * @param batchNum
     * @return
     */
    public static String getLogOrdId(String batchNum) {
        int random1 = getRandomNum(100);
        return batchNum + random1 ;
    }

    /**
     *
     * 根据tmp返回指定位数的随机整数
     *
     * @author renxingchen
     * @param tmp
     *            100，1000，10000
     * @return
     */
    public static int getRandomNum(int tmp) {
        int randomNum = 0;
        while (randomNum < (tmp / 10)) {
            randomNum = (int) (Math.random() * tmp);
        }
        return randomNum;
    }

    /**
     *
     * apiKey加密
     * @author sunss
     * @param apiKey
     * @param source_code
     * @param versionStr
     * @param currentTime
     * @param nonce
     * @return
     */
    public static String getApiKey(String apiKey, String source_code, String versionStr, Long currentTime,
                             String nonce) {
        double version_double = Double.valueOf(versionStr);
        int version = (int) (version_double * 100);
        String versionHex = "0x" + Integer.toHexString(version);
        StringBuilder s = new StringBuilder();
        s.append(source_code);
        s.append(versionHex);
        s.append(apiKey);
        s.append(currentTime);
        s.append(nonce);
        String key = SHA.SHA256(s.toString());
        return key;
    }

    /**
     * 用户类型  1-自然人／2-企业   数据库中为 0普通用户 1企业用户
     * @param userType
     * @return
     */
    public static String convertUserType(Integer userType) {
        if(null==userType){
            return "";
        }
        if("0".equals(userType+"")){
            return "1";
        }
        if("1".equals(userType+"")){
            return "2";
        }
        return "";
    }

    /**
     * 用户姓名/名称转换  投资人／借款人姓名／企业名称 说明：允许脱敏处理，姓氏不允许脱敏，名字可脱敏。企业名称不用脱敏处理
     * @param userType
     * @param username
     * @param companyName
     * @return
     */
    public static String convertUserName(Integer userType, String username, String companyName) {
        if(null==userType){
            return "";
        }
        if("0".equals(userType+"")){
            return desUserName(username);
        }
        if("1".equals(userType+"")){
            return companyName;
        }
        return "";
    }

    /**
     * 姓氏不允许脱敏，名字可脱敏  例子:王*  王**
     * @param username
     * @return
     */
    public static String desUserName(String username) {
        if (StringUtils.isBlank(username)) {
            return "";
        }
        String name = StringUtils.left(username, 1);
        return StringUtils.rightPad(name, StringUtils.length(username), "*");
    }

    /**
     * 证件类型  用户类型（userType）=1必填 用户类型（userType）=2填写-1 1-身份证；2-护照；3-军官证；4-台湾居民来往大陆通行证；5-港澳居民来往内地通行证；6-外国人永久居留身份证；0-其它
     * @param userType
     * @return
     */
    public static String convertCardType(Integer userType) {
        if("0".equals(userType+"")){
            return "1";
        }
        if("1".equals(userType+"")){
            return "7";
        }
        return "";
    }

    /**
     * 允许脱敏处理,但只能脱敏后4
     * @param userIdcard
     * @return
     */
    public static String desUserIdcard(String userIdcard,boolean isCompany) {
        if (StringUtils.isBlank(userIdcard)) {
            return "";
        }
        if(isCompany){
            // 如果是企业的话  不用脱敏
            return userIdcard;
        }
        String name = StringUtils.left(userIdcard, userIdcard.length()-4);
        return StringUtils.rightPad(name, StringUtils.length(userIdcard), "*");
    }

    /**
     * 手机号 说明：允许脱敏处理，只允许后4位脱敏
     * @param mobile
     * @return
     */
    public static String desUserPhone(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return "";
        }
        String name = StringUtils.left(mobile, 7);
        return StringUtils.rightPad(name, StringUtils.length(mobile), "*");
    }

    /**
     * 手机号哈希
     * @param phone
     * @return
     * @throws Exception
     */
    public static JSONObject phoneHash(String phone) throws Exception {
        String salt = BCrypt.gensalt();
        String pHash = SHA.SHA256(phone + salt);
        String phoneBase64 = null;
        try {
            byte[] hash = pHash.getBytes("UTF-8");
            phoneBase64 = new String(Base64Coder.encode(hash));
        } catch (UnsupportedEncodingException var8) {
            throw new CertException(1003, var8);
        }

        String phoneHash = BCrypt.hashpw(phoneBase64, BCrypt.gensalt());
        JSONObject jo = new JSONObject();
        jo.put("phone",phoneHash);
        jo.put("salt",salt);
        return jo;
    }

    /**
     * 注册资金(万元) 取注册资金字段值，注册资金为人民币的转换成万元，注册资金单位为美元的按照汇率6.9换算后转换成万元 小数点后保留4位  舍去
     * @param regCaptial
     * @param currencyName  元和美元
     * @return
     */
    public static String convertUserFund(String regCaptial, String currencyName) {
        if(regCaptial==null||"".equals(regCaptial)){
            return "-1";
        }
        BigDecimal regCaptial_b = new BigDecimal(regCaptial);
        if(currencyName.equals("美元")){
            regCaptial_b = regCaptial_b.multiply(new BigDecimal("6.9"));
        }
        regCaptial_b = regCaptial_b.divide(new BigDecimal("10000"));
        BigDecimal result = regCaptial_b.setScale(4,BigDecimal.ROUND_DOWN);
        return result.toString();
    }

    /**
     * 风险评级 转换    借款人报送-1
     * 出借人类型对应关系 保守型-A 稳健型-I 成长型-Q 进取型-Z
     * @param type
     * @return
     */
    public static String convertRiskRating(String type) {
        if("保守型".equals(type)){
            return "A";
        }
        if("稳健型".equals(type)){
            return "I";
        }
        if("成长型".equals(type)){
            return "Q";
        }
        if("进取型".equals(type)){
            return "Z";
        }
        return "-1";

    }

    /**
     * 14.存管银行账号 报送用户江西银行电子帐号，除后四位外均脱敏 *代替
     * @param account
     * @return
     */
    public static String convertUserPayAccount(String account) {
        if (StringUtils.isBlank(account)) {
            return "";
        }
        String name = StringUtils.right(account, 4);
        return StringUtils.leftPad(name, StringUtils.length(account), "*");
    }

    /**
     * 投资年化收益率  投资单个散标的年化利率，散标打包成产品时，设定为-1注意：如果平台本身记录是日利率，请乘以 365以后上传，如果公布的是月利率，请乘以 12 以后上传
     *
     * @param borrowApr
     * @param borrowPeriod
     * @param borrowStyle
     * @return
     */
    public static String convertLoanRate(BigDecimal borrowApr, Integer borrowPeriod, String borrowStyle) {
        BigDecimal result = BigDecimal.ZERO;
        result = borrowApr.divide(new BigDecimal("100"));
        result = result.setScale(6,BigDecimal.ROUND_DOWN);
        return result.toString();
    }

    /**
     * // 借款期限 (天)  借款期限必须是天。注意：如果平台期限记录的是月，请乘以 30 以后上传，如果公布的是年，请乘以 365 以后上传
     * // 月标转换为天  计算方式：期限*30
     * @param borrowPeriod
     * @param borrowStyle
     * @return
     */
    public static String convertTerm(Integer borrowPeriod, String borrowStyle) {
        if("endday".equals(borrowStyle)){
           return borrowPeriod+"";
        }else{
            // 月
            return (borrowPeriod * 30)+"";
        }
    }

    /**
     * // 还款类型 1-等额本息／2-等额本金／3-按月付息到期还本／4-一次性还本付息5 按月还本付息／6 等本等息 0-其它
     * // 等额本息 报送1  按月计息到期还本付息/按天计息到期还本付息 报送4先息后本报送3  等额本金 报送2
     * @param borrowStyle
     * @return
     */
    public static String convertPayType(String borrowStyle) {
        if("month".equals(borrowStyle)){
            // 等额本息
            return "1";
        }
        if("end".equals(borrowStyle)){
            // 按月计息，到期还本还息
            return "4";
        }
        if("endmonth".equals(borrowStyle)){
            // 先息后本
            return "3";
        }
        if("endday".equals(borrowStyle)){
            // 按天计息，到期还本还息
            return "4";
        }
        if("principal".equals(borrowStyle)){
            // 等额本金
            return "2";
        }
        return "";

    }

    /**
     * // 借款类型 1-信用标/2-抵押标/3-担保标/4-流转标/5-净值标/6-信用+抵押/7-信用+担保/0-其他
     *  报送 资产类型值 1抵押  2质押  3信用
     * @param assetAttributes
     * @return
     */
    public static String convertLoanType(Integer assetAttributes) {
        switch (assetAttributes) {
            case 1:
                return "2";
            case 2:
                return "0";
            case 3:
                return "1";
            default:
                return "0";
        }
    }

    /**
     * // 借款主体信用评级  企业根据自己平台信用评级算法进行评级从高到低依次是 A-Z；如果没有评级则进行分类 A 是最好，Z 是最差。
     * // AAA 报送A  AA+报送E  AA报送K    AA-报送P  A报送U  BBB报送Z
     * @param borrowLevel
     * @return
     */
    public static String convertLoanCreditRating(String borrowLevel) {
        if("AAA".equals(borrowLevel)){
            return "A";
        }
        if("AA+".equals(borrowLevel)){
            return "E";
        }
        if("AA".equals(borrowLevel)){
            return "K";
        }
        if("AA-".equals(borrowLevel)){
            return "P";
        }
        if("A".equals(borrowLevel)){
            return "U";
        }
        if("BBB".equals(borrowLevel)){
            return "Z";
        }
        return "";
    }

    /**
     * 担保方式 1-抵押／2-质押／3-留置／4-定金／5-第三方担保/6-保险/9-风险自担/10-保证（信用）。如果没有担保方式填写-1.
     * 根据资产属性填充值报送 1，2，10  其他的填写10
     * @param assetAttributes
     * @return
     */
    public static String convertSecurityType(Integer assetAttributes) {
        switch (assetAttributes) {
            case 1:
                return "1";
            case 2:
                return "2";
            case 3:
                return "10";
            default:
                return "10";
        }
    }

    /**
     * 返回值格式化
     * @param rtnMsg
     */
    public static JSONObject parseResult(String rtnMsg) {
        JSONObject resp = new JSONObject();
        try {
            if(rtnMsg==null || "".equals(rtnMsg)){
                resp.put("code","");
                resp.put("message","应急中心返回空");
                return resp;
            }
            resp = JSONObject.parseObject(rtnMsg);
        }catch (Exception e){
            Matcher m = r.matcher(rtnMsg);
            String message = "应急中心返回值格式化错误";
            if (m.find( )) {
                message += ",错误信息：" + m.group(0);
            }
            resp = new JSONObject();
            resp.put("code","");
            resp.put("message",message);
        }
        return resp;
    }

    /**
     * 查询返回值格式化
     * @param rtnMsg
     * @return
     */
    public static JSONObject parseResultQuery(String rtnMsg) {
        JSONObject resp = new JSONObject();
        try {
            if(rtnMsg==null || "".equals(rtnMsg)){
                resp.put("errorMsg",CertCallConstant.CERT_QUERY_RESPONSE_NO);
                return resp;
            }
            resp = JSONObject.parseObject(rtnMsg);
        }catch (Exception e){
            resp.put("errorMsg",CertCallConstant.CERT_QUERY_RESPONSE_NO);
        }
        return resp;
    }

    /**
     * 重新把结果分多个批次上传  按照月份
     * 对象里面的格式   groupByDate 为年月日yyyyMM
     * @param array
     * @return
     * {'2018-01':[],'2018-02:[]'}
     */
    public static List<CertReportEntityVO> groupByDate(JSONArray array, String thisMessName, String type) {
        JSONObject groupResult = new JSONObject();
        if (array == null || array.size() == 0) {
            return null;
        }
        for (int i = 0; i < array.size(); i++) {
            JSONObject item = array.getJSONObject(i);
            // 取出需要排序的日期
            String groupDate = item.getString("groupByDate");
            // 如果已经有了  就add   没有就new一个
            if (groupResult.containsKey(groupDate)) {
                // add 进去
                JSONArray itemArray = groupResult.getJSONArray(groupDate);
                item.remove("groupByDate");
                itemArray.add(item);
            } else {
                // new 一个 list 放进去
                JSONArray itemArray = new JSONArray();
                item.remove("groupByDate");
                itemArray.add(item);
                groupResult.put(groupDate, itemArray);
            }
        }
        // 遍历组装数据
        List<CertReportEntityVO> entitys = new ArrayList<>();
        LinkedHashMap<String, JSONArray> jsonMap = JSONObject.parseObject(groupResult.toJSONString(), new TypeReference<LinkedHashMap<String, JSONArray>>() {
        });
        for (Map.Entry<String, JSONArray> item : jsonMap.entrySet()) {
            CertReportEntityVO entity = new CertReportEntityVO(thisMessName, type, null, item.getValue());
            // 设置交易时间为当前月份的一号
            entity.setTradeDate(item.getKey().replace("-","")+"01");
            // 查询这个月有多少天
            entity.setDateNum(GetDate.getDaysOfMonth(item.getKey()+"-01"));
            entitys.add(entity);
        }
        return entitys;
    }

    /**
     * 转换查询结果
     * @param errorMsg
     * @return
     */
    public static Integer convertQueryResult(String errorMsg) {
        if(CertCallConstant.CERT_QUERY_RESPONSE_SUCCESS.equals(errorMsg)){
            // 为入库成功
            return CertCallConstant.CERT_QUERY_RETURN_STATUS_SUCCESS;
        }
        if(CertCallConstant.CERT_QUERY_RESPONSE_FAILED.equals(errorMsg)){
            // 为入库失败
            return CertCallConstant.CERT_QUERY_RETURN_STATUS_FAILED;
        }
        if(CertCallConstant.CERT_QUERY_RESPONSE_HOLD.equals(errorMsg)){
            // 为等待处理
            return CertCallConstant.CERT_QUERY_RETURN_STATUS_HOLD;
        }
        if(CertCallConstant.CERT_QUERY_RESPONSE_ISNOT.equals(errorMsg)){
            // 为批次号无效
            return CertCallConstant.CERT_QUERY_RETURN_STATUS_ISNOT;
        }
        if(CertCallConstant.CERT_QUERY_RESPONSE_NO.equals(errorMsg)){
            // 为无响应
            return CertCallConstant.CERT_QUERY_RETURN_STATUS_NO;
        }
        return 0;
    }

    /**
     * 借款说明  报送借款用途字段值
     * @param financePurpose
     * @return
     */
    public static String convertLoanDescribe(String financePurpose) {
        if(financePurpose==null || "".equals(financePurpose)){
            return "未填写";
        }
        if("01".equals(financePurpose)){
            return "个人消费";
        }
        if("02".equals(financePurpose)){
            return "个人经营";
        }
        if("03".equals(financePurpose)){
            return "个人资金周转";
        }
        if("04".equals(financePurpose)){
            return "房贷";
        }
        if("05".equals(financePurpose)){
            return "企业经营";
        }
        if("06".equals(financePurpose)){
            return "企业周转";
        }
        if("99".equals(financePurpose)){
            return "其他";
        }
        return  "其他";
    }
}
