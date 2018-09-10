package com.hyjf.pay.lib.fadada.bean;

import com.hyjf.common.spring.SpringUtils;
import com.hyjf.pay.lib.config.FddSystemConfig;

import java.io.File;
import java.io.Serializable;


/**
 * 电子签章参数类
 */
public class DzqzCallBean extends DzqzCallApiBean implements Serializable {
    private static FddSystemConfig fddSystemConfig = SpringUtils.getBean(FddSystemConfig.class);

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    /**
     * logid
     */
    private String logordid;

    /**
     * 秘钥
     */
    private String secret;

    /**
     * 请求URL
     */
    private String url;

    /**
     * 接入方ID
     */
    private String app_id;

    /**
     * 版本号
     */
    private String v;

    /**
     * 请求时间
     */
    private String timestamp;

    /**
     * 文档标题
     */
    private String doc_title;

    /**
     * 模板ID
     */
    private String template_id;

    /**
     * 合同编号
     */
    private String contract_id;

    /**
     * 字体大小
     */
    private String font_size;

    /**
     * 字体类型
     */
    private String font_type;

    /**
     * 填充内容
     */
    private String parameter_map;

    /**
     * 动态表格
     */
    private String dynamic_tables;

    /**
     * 处理结果
     */
    private String result;

    /**
     * 状态码
     */
    private String code;

    /**
     * 描述
     */
    private String msg;

    /**
     * 合同下载地址
     */
    private String download_url;

    /**
     * 合同查看地址
     */
    private String viewpdf_url;

    /**
     * 操作编码
     */
    private String txCode;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 文档地址
     */
    private String doc_url;
    /**
     * PDF 模板
     */
    private File file;

    private String txDate;

    private String txTime;

    /**
     * 客户姓名
     */
    private String customer_name;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 证件类型
     */
    private String ident_type;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 手机号
     */
    private  String mobile;


    /**
     * 客户编号
     */
    private String customer_id;
    /**
     * 客户角色
     */
    private String client_role;
    /**
     * 定位类型
     */
    private Integer positionType;
    /**
     * 定位关键字
     */
    private String sign_keyword;
    /**
     * 关键字签章策略
     */
    private String keywordStrategy;
    /**
     * 定位坐标
     */
    private String signaturePositions;
    /**
     * 异步通知地址
     */
    private String notify_url;
    /**
     * 交易号
     */
    private String transaction_id;
    /**
     * 签章结果代码
     */
    private String result_code;
    /**
     * 签章结果描述
     */
    private String result_desc;
    /**
     * 签署状态码
     */
    private String sign_status;
    /**
     * 签署状态说明
     */
    private String sign_status_desc;
    /**
     * 合同类型
     */
    private Integer transType;
    /**
     * 下载文档保存地址
     */
    private String savePath;

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public String getSign_status() {
        return sign_status;
    }

    public void setSign_status(String sign_status) {
        this.sign_status = sign_status;
    }

    public String getSign_status_desc() {
        return sign_status_desc;
    }

    public void setSign_status_desc(String sign_status_desc) {
        this.sign_status_desc = sign_status_desc;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getResult_desc() {
        return result_desc;
    }

    public void setResult_desc(String result_desc) {
        this.result_desc = result_desc;
    }

    public String getKeywordStrategy() {
        return keywordStrategy;
    }

    public void setKeywordStrategy(String keywordStrategy) {
        this.keywordStrategy = keywordStrategy;
    }

    public Integer getPositionType() {
        return positionType;
    }

    public void setPositionType(Integer positionType) {
        this.positionType = positionType;
    }

    public String getSignaturePositions() {
        return signaturePositions;
    }

    public void setSignaturePositions(String signaturePositions) {
        this.signaturePositions = signaturePositions;
    }

    public String getClient_role() {
        return client_role;
    }

    public void setClient_role(String client_role) {
        this.client_role = client_role;
    }

    public String getSign_keyword() {
        return sign_keyword;
    }

    public void setSign_keyword(String sign_keyword) {
        this.sign_keyword = sign_keyword;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTxDate() {
        return txDate;
    }

    public void setTxDate(String txDate) {
        this.txDate = txDate;
    }

    public String getTxTime() {
        return txTime;
    }

    public void setTxTime(String txTime) {
        this.txTime = txTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLogordid() {
        return logordid;
    }

    public void setLogordid(String logordid) {
        this.logordid = logordid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTxCode() {
        return txCode;
    }

    public void setTxCode(String txCode) {
        this.txCode = txCode;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDoc_title() {
        return doc_title;
    }

    public void setDoc_title(String doc_title) {
        this.doc_title = doc_title;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getContract_id() {
        return contract_id;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id;
    }

    public String getFont_size() {
        return font_size;
    }

    public void setFont_size(String font_size) {
        this.font_size = font_size;
    }

    public String getFont_type() {
        return font_type;
    }

    public void setFont_type(String font_type) {
        this.font_type = font_type;
    }

    public String getParameter_map() {
        return parameter_map;
    }

    public void setParameter_map(String parameter_map) {
        this.parameter_map = parameter_map;
    }

    public String getDynamic_tables() {
        return dynamic_tables;
    }

    public void setDynamic_tables(String dynamic_tables) {
        this.dynamic_tables = dynamic_tables;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setErrMsg(String msg) {
        this.result = ERROR;
        this.code = "";
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getViewpdf_url() {
        return viewpdf_url;
    }

    public void setViewpdf_url(String viewpdf_url) {
        this.viewpdf_url = viewpdf_url;
    }

    public String getDoc_url() {
        return doc_url;
    }

    public void setDoc_url(String doc_url) {
        this.doc_url = doc_url;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

	public void setCommonItem() {
        // todo 在pay-server中处理
//		this.app_id = fddSystemConfig.getFddAppId();
//		this.secret = fddSystemConfig.getFddAppSeret();
//		this.v = fddSystemConfig.getFddVersion();
//		this.url = fddSystemConfig.getFddVisitUrl();
	}

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdent_type() {
        return ident_type;
    }

    public void setIdent_type(String ident_type) {
        this.ident_type = ident_type;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
}
