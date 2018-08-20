/**
 * Description:汇盈金服调用汇付天下API接口
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: GOGTZ-T
 * @version: 1.0
 *           Created at: 2015年11月23日 下午4:26:10
 *           Modification History:
 *           Modified by :
 */
package com.hyjf.pay.lib.chinapnr;

import com.hyjf.common.http.HttpDeal;
import com.hyjf.common.spring.SpringUtils;
import com.hyjf.common.util.MD5Util2;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.PnrApi;
import com.hyjf.pay.lib.PnrApiBean;
import com.hyjf.pay.lib.chinapnr.util.ChinaPnrConstant;
import com.hyjf.pay.lib.chinapnr.util.ChinaPnrSignUtils;
import com.hyjf.pay.lib.config.PaySystemConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author GOGTZ-T
 */

public class ChinaPnrApiImpl implements PnrApi {
    private static Logger log = LoggerFactory.getLogger(ChinaPnrApiImpl.class);
    /** THIS_CLASS */
    private static final String THIS_CLASS = ChinaPnrApiImpl.class.getName();

    /** 版本号 */
    @Value("${hyjf.chinapnr.version}")
    private String _version;

    /** 商户客户号 */
    @Value("${hyjf.chinapnr.mercustid}")
    private String _merCustId;

    /** 商户子账户号 */
    @Value("${hyjf.chinapnr.meracctId}")
    private String _merAcctId;

    /** 商户后台回调地址 */
    @Value("${hyjf.chinapnr.callback.url}")
    private String _bgRetUrl;

    /** 汇付天下地址 */
    @Value("${hyjf.chinapnr.url}")
    private String chinapnrUrl;

    /**
     * 调用汇付天下API接口
     *
     * @param bean
     * @return
     */
    @Override
    public String callChinaPnrApi(PnrApiBean bean) {

        // 方法名
        String methodName = "callChinaPnrApi";

        log.info("[调用汇付天下API接口开始]");
        log.debug( "参数: " + bean == null ? "无" : bean.getAllParams() + "]");

        String result = null;
        // 未签名时,进行签名
        if (Validator.isNull(bean.get(ChinaPnrConstant.PARAM_CHKVALUE))) {
            bean.set(ChinaPnrConstant.PARAM_CHKVALUE, bean.getChkValueMerged());
        }
        // 签名为空时,返回空字符串
        if (Validator.isNull(bean.get(ChinaPnrConstant.PARAM_CHKVALUE))) {
            return "";
        }

        try {
            // 发送请求
            result = HttpDeal.post(chinapnrUrl, bean.getAllParams());
            log.debug("[返回结果:" + result + "]");
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }

        log.error("[调用汇付天下API接口结束]");

        return result;
    }

    /**
     * 用户开户
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean userRegister(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_USER_REGISTER);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 商户后台回调地址
        bean.set(ChinaPnrConstant.PARAM_BGRETURL, _bgRetUrl);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_BGRETURL,
                        ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_USRID, ChinaPnrConstant.PARAM_USRNAME,
                        ChinaPnrConstant.PARAM_IDTYPE, ChinaPnrConstant.PARAM_IDNO, ChinaPnrConstant.PARAM_USRMP,
                        ChinaPnrConstant.PARAM_USREMAIL, ChinaPnrConstant.PARAM_MERPRIV,
                        ChinaPnrConstant.PARAM_PAGETYPE);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);

        return bean;
    }

    /**
     * 用户绑卡接口
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean userBindCard(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_USER_BIND_CARD);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 商户后台回调地址
        bean.set(ChinaPnrConstant.PARAM_BGRETURL, _bgRetUrl);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_USRCUSTID,
                        ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV,
                        ChinaPnrConstant.PARAM_PAGETYPE);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);

        return bean;
    }

    /**
     * 用户登录接口(页面)
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean userLogin(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_USER_LOGIN);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);

        return bean;
    }

    /**
     * 删除银行卡接口
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean delCard(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_DEL_CARD);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_USRCUSTID,
                        ChinaPnrConstant.PARAM_CARDID);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);

        return bean;
    }

    /**
     * 充值
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean netSave(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_NET_SAVE);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 商户后台回调地址
        bean.set(ChinaPnrConstant.PARAM_BGRETURL, _bgRetUrl);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_USRCUSTID,
                        ChinaPnrConstant.PARAM_ORDID, ChinaPnrConstant.PARAM_ORDDATE,
                        ChinaPnrConstant.PARAM_GATEBUSIID, ChinaPnrConstant.PARAM_OPENBANKID,
                        ChinaPnrConstant.PARAM_DCFLAG, ChinaPnrConstant.PARAM_TRANSAMT, ChinaPnrConstant.PARAM_RETURL,
                        ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_OPENACCTID,
                        ChinaPnrConstant.PARAM_CERTID, ChinaPnrConstant.PARAM_MERPRIV, ChinaPnrConstant.PARAM_REQEXT,
                        ChinaPnrConstant.PARAM_PAGETYPE);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);

        return bean;
    }

    /**
     * 资金（货款）冻结
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean usrFreezeBg(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_USR_FREEZE_BG);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 商户后台回调地址
        bean.set(ChinaPnrConstant.PARAM_BGRETURL, _bgRetUrl);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_USRCUSTID,
                        ChinaPnrConstant.PARAM_SUBACCTTYPE, ChinaPnrConstant.PARAM_SUBACCTID,
                        ChinaPnrConstant.PARAM_ORDID, ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_TRANSAMT,
                        ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);

        return bean;
    }

    /**
     * 资金（货款）解冻
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean usrUnFreeze(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_USR_UN_FREEZE);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 商户后台回调地址
        bean.set(ChinaPnrConstant.PARAM_BGRETURL, _bgRetUrl);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID, ChinaPnrConstant.PARAM_ORDDATE,
                        ChinaPnrConstant.PARAM_TRXID, ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_BGRETURL,
                        ChinaPnrConstant.PARAM_MERPRIV);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);

        return bean;
    }

    /**
     * 主动投标
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean initiativeTender(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_INITIATIVE_TENDER);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 商户后台回调地址
        bean.set(ChinaPnrConstant.PARAM_BGRETURL, _bgRetUrl);
        // 签名
        if (ChinaPnrConstant.VERSION_10.equals(bean.get(ChinaPnrConstant.PARAM_VERSION))) {
            // 接口 1.0
            String chkValue =
                    bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                            ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID,
                            ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_TRANSAMT,
                            ChinaPnrConstant.PARAM_USRCUSTID, ChinaPnrConstant.PARAM_MAXTENDERRATE,
                            ChinaPnrConstant.PARAM_BORROWERDETAILS, ChinaPnrConstant.PARAM_RETURL,
                            ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV);
            bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        } else {
            // 接口 2.0
            String chkValue =
                    bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                            ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID,
                            ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_TRANSAMT,
                            ChinaPnrConstant.PARAM_USRCUSTID, ChinaPnrConstant.PARAM_MAXTENDERRATE,
                            ChinaPnrConstant.PARAM_BORROWERDETAILS, ChinaPnrConstant.PARAM_ISFREEZE,
                            ChinaPnrConstant.PARAM_FREEZEORDID, ChinaPnrConstant.PARAM_RETURL,
                            ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV,
                            ChinaPnrConstant.PARAM_REQEXT, ChinaPnrConstant.PARAM_PAGETYPE);
            bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        }

        return bean;
    }

    /**
     * 自动投标
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean autoTender(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_AUTO_TENDER);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 商户后台回调地址
        bean.set(ChinaPnrConstant.PARAM_BGRETURL, _bgRetUrl);
        // 签名
        // 接口 2.0
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID, ChinaPnrConstant.PARAM_ORDDATE,
                        ChinaPnrConstant.PARAM_TRANSAMT, ChinaPnrConstant.PARAM_USRCUSTID,
                        ChinaPnrConstant.PARAM_MAXTENDERRATE, ChinaPnrConstant.PARAM_BORROWERDETAILS,
                        ChinaPnrConstant.PARAM_ISFREEZE, ChinaPnrConstant.PARAM_FREEZEORDID,
                        ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV,
                        ChinaPnrConstant.PARAM_REQEXT);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        return bean;
    }

    /**
     * 投标撤销
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean tenderCancle(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_TENDER_CANCLE);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 商户后台回调地址
        bean.set(ChinaPnrConstant.PARAM_BGRETURL, _bgRetUrl);
        // 签名
        if (ChinaPnrConstant.VERSION_10.equals(bean.get(ChinaPnrConstant.PARAM_VERSION))) {
            // 接口 1.0
            String chkValue =
                    bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                            ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID,
                            ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_TRANSAMT,
                            ChinaPnrConstant.PARAM_USRCUSTID, ChinaPnrConstant.PARAM_RETURL,
                            ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV);
            bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        } else {
            // 接口 2.0
            String chkValue =
                    bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                            ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID,
                            ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_TRANSAMT,
                            ChinaPnrConstant.PARAM_USRCUSTID, ChinaPnrConstant.PARAM_ISUNFREEZE,
                            ChinaPnrConstant.PARAM_UNFREEZEORDID, ChinaPnrConstant.PARAM_FREEZETRXID,
                            ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_BGRETURL,
                            ChinaPnrConstant.PARAM_MERPRIV, ChinaPnrConstant.PARAM_REQEXT,
                            ChinaPnrConstant.PARAM_PAGETYPE);
            bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        }

        return bean;
    }

    /**
     * 自动扣款（放款）
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean loans(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_LOANS);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 商户后台回调地址
        bean.set(ChinaPnrConstant.PARAM_BGRETURL, _bgRetUrl);
        // 签名
        if (ChinaPnrConstant.VERSION_10.equals(bean.get(ChinaPnrConstant.PARAM_VERSION))) {
            // 接口 1.0
            String chkValue =
                    bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                            ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID,
                            ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_OUTCUSTID,
                            ChinaPnrConstant.PARAM_TRANSAMT, ChinaPnrConstant.PARAM_FEE,
                            ChinaPnrConstant.PARAM_SUBORDID, ChinaPnrConstant.PARAM_SUBORDDATE,
                            ChinaPnrConstant.PARAM_INCUSTID, ChinaPnrConstant.PARAM_DIVDETAILS,
                            ChinaPnrConstant.PARAM_ISDEFAULT, ChinaPnrConstant.PARAM_BGRETURL,
                            ChinaPnrConstant.PARAM_MERPRIV);
            bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        } else {
            // 接口 2.0
            String chkValue =
                    bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                            ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID,
                            ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_OUTCUSTID,
                            ChinaPnrConstant.PARAM_TRANSAMT, ChinaPnrConstant.PARAM_FEE,
                            ChinaPnrConstant.PARAM_SUBORDID, ChinaPnrConstant.PARAM_SUBORDDATE,
                            ChinaPnrConstant.PARAM_INCUSTID, ChinaPnrConstant.PARAM_DIVDETAILS,
                            ChinaPnrConstant.PARAM_FEEOBJFLAG, ChinaPnrConstant.PARAM_ISDEFAULT,
                            ChinaPnrConstant.PARAM_ISUNFREEZE, ChinaPnrConstant.PARAM_UNFREEZEORDID,
                            ChinaPnrConstant.PARAM_FREEZETRXID, ChinaPnrConstant.PARAM_BGRETURL,
                            ChinaPnrConstant.PARAM_MERPRIV, ChinaPnrConstant.PARAM_REQEXT);
            bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        }

        return bean;
    }

    /**
     * 自动扣款（还款）
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean repayment(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_REPAYMENT);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 商户后台回调地址
        bean.set(ChinaPnrConstant.PARAM_BGRETURL, _bgRetUrl);
        // 签名
        if (ChinaPnrConstant.VERSION_10.equals(bean.get(ChinaPnrConstant.PARAM_VERSION))) {
            // 接口 1.0
            String chkValue =
                    bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                            ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID,
                            ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_OUTCUSTID,
                            ChinaPnrConstant.PARAM_SUBORDID, ChinaPnrConstant.PARAM_SUBORDDATE,
                            ChinaPnrConstant.PARAM_OUTACCTID, ChinaPnrConstant.PARAM_TRANSAMT,
                            ChinaPnrConstant.PARAM_FEE, ChinaPnrConstant.PARAM_INCUSTID,
                            ChinaPnrConstant.PARAM_INACCTID, ChinaPnrConstant.PARAM_DIVDETAILS,
                            ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV);
            bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        } else if (ChinaPnrConstant.VERSION_20.equals(bean.get(ChinaPnrConstant.PARAM_VERSION))) {
            // 接口 2.0
            String chkValue =
                    bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                            ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID,
                            ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_OUTCUSTID,
                            ChinaPnrConstant.PARAM_SUBORDID, ChinaPnrConstant.PARAM_SUBORDDATE,
                            ChinaPnrConstant.PARAM_OUTACCTID, ChinaPnrConstant.PARAM_TRANSAMT,
                            ChinaPnrConstant.PARAM_FEE, ChinaPnrConstant.PARAM_INCUSTID,
                            ChinaPnrConstant.PARAM_INACCTID, ChinaPnrConstant.PARAM_DIVDETAILS,
                            ChinaPnrConstant.PARAM_FEEOBJFLAG, ChinaPnrConstant.PARAM_BGRETURL,
                            ChinaPnrConstant.PARAM_MERPRIV, ChinaPnrConstant.PARAM_REQEXT);
            bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        } else {
            // 接口 3.0
            String chkValue =
                    bean.getChkValueMergedMD5(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                            ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_PROID,
                            ChinaPnrConstant.PARAM_ORDID, ChinaPnrConstant.PARAM_ORDDATE,
                            ChinaPnrConstant.PARAM_OUTCUSTID, ChinaPnrConstant.PARAM_SUBORDID,
                            ChinaPnrConstant.PARAM_SUBORDDATE, ChinaPnrConstant.PARAM_OUTACCTID,
                            ChinaPnrConstant.PARAM_PRINCIPALAMT, ChinaPnrConstant.PARAM_INTERESTAMT,
                            ChinaPnrConstant.PARAM_FEE, ChinaPnrConstant.PARAM_INCUSTID,
                            ChinaPnrConstant.PARAM_INACCTID, ChinaPnrConstant.PARAM_DIVDETAILS,
                            ChinaPnrConstant.PARAM_FEEOBJFLAG, ChinaPnrConstant.PARAM_DZOBJECT,
                            ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV,
                            ChinaPnrConstant.PARAM_REQEXT);
            bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        }

        return bean;
    }

    /**
     * 自动扣款转账（商户用）
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean transfer(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_TRANSFER);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 出账客户号=商户客户号
        bean.set(ChinaPnrConstant.PARAM_OUTCUSTID, _merCustId);
        if (StringUtils.isEmpty(bean.getAllParams().get("OutAcctId"))) {
            // 出账子账户="MDT000001"
            bean.set(ChinaPnrConstant.PARAM_OUTACCTID, _merAcctId);
        }
        // 商户后台回调地址
        bean.set(ChinaPnrConstant.PARAM_BGRETURL, _bgRetUrl);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_ORDID, ChinaPnrConstant.PARAM_OUTCUSTID,
                        ChinaPnrConstant.PARAM_OUTACCTID, ChinaPnrConstant.PARAM_TRANSAMT,
                        ChinaPnrConstant.PARAM_INCUSTID, ChinaPnrConstant.PARAM_INACCTID,
                        ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);

        return bean;
    }

    /**
     * 取现（页面）
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean cash(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_CASH);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 商户后台回调地址
        bean.set(ChinaPnrConstant.PARAM_BGRETURL, _bgRetUrl);
        // 签名
        if (ChinaPnrConstant.VERSION_10.equals(bean.get(ChinaPnrConstant.PARAM_VERSION))) {
            // 接口 1.0
            String chkValue =
                    bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                            ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID,
                            ChinaPnrConstant.PARAM_USRCUSTID, ChinaPnrConstant.PARAM_TRANSAMT,
                            ChinaPnrConstant.PARAM_OPENACCTID, ChinaPnrConstant.PARAM_RETURL,
                            ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_REMARK,
                            ChinaPnrConstant.PARAM_MERPRIV);
            bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        } else {
            // 接口 2.0
            String chkValue =
                    bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                            ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID,
                            ChinaPnrConstant.PARAM_USRCUSTID, ChinaPnrConstant.PARAM_TRANSAMT,
                            ChinaPnrConstant.PARAM_SERVFEE, ChinaPnrConstant.PARAM_SERVFEEACCTID,
                            ChinaPnrConstant.PARAM_OPENACCTID, ChinaPnrConstant.PARAM_RETURL,
                            ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_REMARK,
                            // ChinaPnrConstant.PARAM_CHARSET,
                            ChinaPnrConstant.PARAM_MERPRIV, ChinaPnrConstant.PARAM_REQEXT,
                            ChinaPnrConstant.PARAM_PAGETYPE);
            bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        }

        return bean;
    }

    /**
     * 
     * 查询交易详情
     * 
     * @author renxingchen
     * @return
     */
    public PnrApiBean queryTransDetail(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_QUERY_TRANS_DETAIL);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID,
                        ChinaPnrConstant.PARAM_QUERYTRANSTYPE);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        return bean;
    }

    /**
     * 
     * 查询预约授权状态
     * @author shuai
     * @return
     */
    public PnrApiBean queryTenderPlan(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_QUERY_TENDER_PLAN);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_USRCUSTID);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        return bean;
    }

    /**
     * 
     * 开启预约授权
     * @author shuai
     * @return
     */
    public PnrApiBean autoTenderPlan(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_AUTO_TENDER_PLAN);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_USRCUSTID,
                        ChinaPnrConstant.PARAM_TENDERPLANTYPE, ChinaPnrConstant.PARAM_TRANSAMT,
                        ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_MERPRIV, ChinaPnrConstant.PARAM_PAGETYPE);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        return bean;
    }

    /**
     * 
     * 关闭预约授权
     * @author shuai
     * @return
     */
    public PnrApiBean autoTenderPlanClose(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_AUTO_TENDER_PLAN_CLOSE);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_USRCUSTID,
                        ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_MERPRIV, ChinaPnrConstant.PARAM_PAGETYPE);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        return bean;
    }

    /**
     * 
     * 账户余额查询
     * 
     * @author renxingchen
     * @return
     */
    public PnrApiBean queryAccts(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_QUERY_ACCTS);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        return bean;
    }

    /**
     * 用户账户支付
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean usrAcctPay(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_USR_ACCT_PAY);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 商户后台回调地址
        bean.set(ChinaPnrConstant.PARAM_BGRETURL, _bgRetUrl);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_ORDID, ChinaPnrConstant.PARAM_USRCUSTID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_TRANSAMT,
                        ChinaPnrConstant.PARAM_INACCTID, ChinaPnrConstant.PARAM_INACCTTYPE,
                        ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);

        return bean;
    }

    /**
     * 债权转让接口
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean creditAssign(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_CREDIT_ASSIGN);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 商户后台回调地址
        bean.set(ChinaPnrConstant.PARAM_BGRETURL, _bgRetUrl);
        // 签名
        String chkValue = "";
        if (ChinaPnrConstant.VERSION_30.equals(bean.get(ChinaPnrConstant.PARAM_VERSION))) {
            // 3.0以上使用
            chkValue =
                    bean.getChkValueMergedMD5(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                            ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_SELLCUSTID,
                            ChinaPnrConstant.PARAM_CREDITAMT, ChinaPnrConstant.PARAM_CREDITDEALAMT,
                            ChinaPnrConstant.PARAM_BIDDETAILS, ChinaPnrConstant.PARAM_FEE,
                            ChinaPnrConstant.PARAM_DIVDETAILS, ChinaPnrConstant.PARAM_BUYCUSTID,
                            ChinaPnrConstant.PARAM_ORDID, ChinaPnrConstant.PARAM_ORDDATE,
                            ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_BGRETURL,
                            ChinaPnrConstant.PARAM_MERPRIV, ChinaPnrConstant.PARAM_REQEXT,
                            ChinaPnrConstant.PARAM_PAGETYPE, ChinaPnrConstant.PARAM_LCID,
                            ChinaPnrConstant.PARAM_TOTALLCAMT);

        } else {
            chkValue =
                    bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                            ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_SELLCUSTID,
                            ChinaPnrConstant.PARAM_CREDITAMT, ChinaPnrConstant.PARAM_CREDITDEALAMT,
                            ChinaPnrConstant.PARAM_BIDDETAILS, ChinaPnrConstant.PARAM_FEE,
                            ChinaPnrConstant.PARAM_DIVDETAILS, ChinaPnrConstant.PARAM_BUYCUSTID,
                            ChinaPnrConstant.PARAM_ORDID, ChinaPnrConstant.PARAM_ORDDATE,
                            ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_BGRETURL,
                            ChinaPnrConstant.PARAM_MERPRIV, ChinaPnrConstant.PARAM_REQEXT,
                            ChinaPnrConstant.PARAM_PAGETYPE);
        }
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        return bean;
    }

    /**
     * 债权转让接口
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean autoCreditAssign(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_AUTO_CREDIT_ASSIGN);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 商户后台回调地址
        bean.set(ChinaPnrConstant.PARAM_BGRETURL, _bgRetUrl);
        // 签名
        String chkValue = "";
        if (ChinaPnrConstant.VERSION_30.equals(bean.get(ChinaPnrConstant.PARAM_VERSION))) {
            // 3.0以上使用
            chkValue =
                    bean.getChkValueMergedMD5(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                            ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_SELLCUSTID,
                            ChinaPnrConstant.PARAM_CREDITAMT, ChinaPnrConstant.PARAM_CREDITDEALAMT,
                            ChinaPnrConstant.PARAM_BIDDETAILS, ChinaPnrConstant.PARAM_FEE,
                            ChinaPnrConstant.PARAM_DIVDETAILS, ChinaPnrConstant.PARAM_BUYCUSTID,
                            ChinaPnrConstant.PARAM_ORDID, ChinaPnrConstant.PARAM_ORDDATE,
                            ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_BGRETURL,
                            ChinaPnrConstant.PARAM_MERPRIV, ChinaPnrConstant.PARAM_REQEXT,
                            ChinaPnrConstant.PARAM_PAGETYPE, ChinaPnrConstant.PARAM_LCID,
                            ChinaPnrConstant.PARAM_TOTALLCAMT);

        } else {
            chkValue =
                    bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                            ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_SELLCUSTID,
                            ChinaPnrConstant.PARAM_CREDITAMT, ChinaPnrConstant.PARAM_CREDITDEALAMT,
                            ChinaPnrConstant.PARAM_BIDDETAILS, ChinaPnrConstant.PARAM_FEE,
                            ChinaPnrConstant.PARAM_DIVDETAILS, ChinaPnrConstant.PARAM_BUYCUSTID,
                            ChinaPnrConstant.PARAM_ORDID, ChinaPnrConstant.PARAM_ORDDATE,
                            ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_BGRETURL,
                            ChinaPnrConstant.PARAM_MERPRIV, ChinaPnrConstant.PARAM_REQEXT,
                            ChinaPnrConstant.PARAM_PAGETYPE);
        }
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        return bean;
    }

    /**
     * 生利宝交易接口
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean fssTrans(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_FSS_TRANS);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 商户后台回调地址
        bean.set(ChinaPnrConstant.PARAM_BGRETURL, _bgRetUrl);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_USRCUSTID,
                        ChinaPnrConstant.PARAM_ORDID, ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_RETURL,
                        ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV, ChinaPnrConstant.PARAM_REQEXT);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);

        return bean;
    }

    /**
     * 余额查询（页面）
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean queryBalance(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_QUERY_BALANCE);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_USRCUSTID);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);

        return bean;
    }

    /**
     * 余额查询（后台）
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean queryBalanceBg(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_QUERY_BALANCE_BG);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_USRCUSTID);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);

        return bean;
    }

    /**
     * 交易状态查询
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean queryTransStat(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_QUERYTRANSSTAT);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID, ChinaPnrConstant.PARAM_ORDDATE,
                        ChinaPnrConstant.PARAM_QUERYTRANSTYPE);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);

        return bean;
    }

    /**
     * 放还款对账
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean reconciliation(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_RECONCILIATION);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_BEGINDATE,
                        ChinaPnrConstant.PARAM_ENDDATE, ChinaPnrConstant.PARAM_PAGENUM,
                        ChinaPnrConstant.PARAM_PAGESIZE, ChinaPnrConstant.PARAM_QUERYTRANSTYPE);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);

        return bean;
    }

    /**
     * 商户扣款对账
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean trfReconciliation(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_TRF_RECONCILIATION);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_BEGINDATE,
                        ChinaPnrConstant.PARAM_ENDDATE, ChinaPnrConstant.PARAM_PAGENUM, ChinaPnrConstant.PARAM_PAGESIZE);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);

        return bean;
    }

    /**
     * 取现对账
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean cashReconciliation(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_CASH_RECONCILIATION);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 签名
        if (ChinaPnrConstant.VERSION_10.equals(bean.get(ChinaPnrConstant.PARAM_VERSION))) {
            // 接口 1.0
            String chkValue =
                    bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                            ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_BEGINDATE,
                            ChinaPnrConstant.PARAM_ENDDATE, ChinaPnrConstant.PARAM_PAGENUM,
                            ChinaPnrConstant.PARAM_PAGESIZE);
            bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        } else {
            // 接口 2.0
            String chkValue =
                    bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                            ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_BEGINDATE,
                            ChinaPnrConstant.PARAM_ENDDATE, ChinaPnrConstant.PARAM_PAGENUM,
                            ChinaPnrConstant.PARAM_PAGESIZE);
            bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        }

        return bean;
    }

    /**
     * 充值对账
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean saveReconciliation(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_SAVE_RECONCILIATION);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_BEGINDATE,
                        ChinaPnrConstant.PARAM_ENDDATE, ChinaPnrConstant.PARAM_PAGENUM, ChinaPnrConstant.PARAM_PAGESIZE);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);

        return bean;
    }

    /**
     * 银行卡查询接口
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean queryCardInfo(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_QUERY_CARD_INFO);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_USRCUSTID,
                        ChinaPnrConstant.PARAM_CARDID);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);

        return bean;
    }

    /**
     * 用户信息查询
     *
     * @param bean
     * @return
     */
    @Override
    public PnrApiBean queryUsrInfo(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_QUERY_USR_INFO);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_CERTID, ChinaPnrConstant.PARAM_REQEXT);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);

        return bean;
    }

    /**
     * 标的信息录入接口
     */
    @Override
    public PnrApiBean addBidInfo(PnrApiBean bean) {
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_ADD_BID_INFO);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 签名
        String chkValue =
                bean.getChkValueMergedMD5(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_PROID, ChinaPnrConstant.PARAM_BIDTYPE,
                        ChinaPnrConstant.PARAM_BORRTOTAMT, ChinaPnrConstant.PARAM_YEARRATE,
                        ChinaPnrConstant.PARAM_RETINTEREST, ChinaPnrConstant.PARAM_LASTRETDATE,
                        ChinaPnrConstant.PARAM_BIDSTARTDATE, ChinaPnrConstant.PARAM_BIDENDDATE,
                        ChinaPnrConstant.PARAM_RETTYPE, ChinaPnrConstant.PARAM_RETDATE,
                        ChinaPnrConstant.PARAM_GUARANTTYPE, ChinaPnrConstant.PARAM_BIDPRODTYPE,
                        ChinaPnrConstant.PARAM_RISKCTLTYPE, ChinaPnrConstant.PARAM_LIMITMINBIDAMT,
                        ChinaPnrConstant.PARAM_LIMITBIDSUM, ChinaPnrConstant.PARAM_LIMITMAXBIDSUM,
                        ChinaPnrConstant.PARAM_LIMITMINBIDSUM, ChinaPnrConstant.PARAM_BIDPAYFORSTATE,
                        ChinaPnrConstant.PARAM_BORRTYPE, ChinaPnrConstant.PARAM_BORRCUSTID,
                        ChinaPnrConstant.PARAM_BORRBUSICODE, ChinaPnrConstant.PARAM_BORRCERTTYPE,
                        ChinaPnrConstant.PARAM_BORRCERTID, ChinaPnrConstant.PARAM_BORRMOBIPHONE,
                        ChinaPnrConstant.PARAM_BORRPHONE, ChinaPnrConstant.PARAM_BORRWORKYEAR,
                        ChinaPnrConstant.PARAM_BORRINCOME, ChinaPnrConstant.PARAM_BORRMARRIAGE,
                        ChinaPnrConstant.PARAM_BORREMAIL, ChinaPnrConstant.PARAM_CHARSET,
                        ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV, ChinaPnrConstant.PARAM_REQEXT);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        return bean;
    }

    /**
     * 标的信息补录输入接口
     */
    @Override
    public PnrApiBean addBidAttachInfo(PnrApiBean bean) {
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_ADD_BID_ATTACH_INFO);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 签名
        String chkValue =
                bean.getChkValueMergedMD5(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_PROID, ChinaPnrConstant.PARAM_RETURL,
                        ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV, ChinaPnrConstant.PARAM_REQEXT);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        return bean;
    }

    /**
     * 标的审核状态查询接口
     */
    @Override
    public PnrApiBean queryBidInfo(PnrApiBean bean) {
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_QUERY_BID_INFO);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 签名
        String chkValue =
                bean.getChkValueMergedMD5(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_PROID, ChinaPnrConstant.PARAM_REQEXT,
                        ChinaPnrConstant.PARAM_MERPRIV);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        return bean;
    }

    /**
     * 
     * 此处为实现/覆载说明
     * 
     * @author renxingchen
     * @param bean
     * @return
     * @seecom.hyjf.pay.lib.PnrApi#corpRegister(com.hyjf.pay.lib.PnrApiBean)
     */
    @Override
    public PnrApiBean corpRegister(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_CORP_REGISTER);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 商户后台回调地址
        bean.set(ChinaPnrConstant.PARAM_BGRETURL, _bgRetUrl);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_USRID, ChinaPnrConstant.PARAM_USRNAME,
                        ChinaPnrConstant.PARAM_INSTUCODE, ChinaPnrConstant.PARAM_BUSICODE,
                        ChinaPnrConstant.PARAM_TAXCODE, ChinaPnrConstant.PARAM_MERPRIV,
                        ChinaPnrConstant.PARAM_GUARTYPE, ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_REQEXT);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        return bean;
    }

    /**
     * 企业用户绑定接口
     * 
     * @param bean
     * @return
     * @author Michael
     */

    @Override
    public PnrApiBean direcTrfAuth(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_DIRECT_TRF_AUTH);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 商户后台回调地址
        bean.set(ChinaPnrConstant.PARAM_BGRETURL, _bgRetUrl);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_USRCUSTID,
                        ChinaPnrConstant.PARAM_INUSRCUSTID, ChinaPnrConstant.PARAM_AUTHAMT,
                        ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_REQEXT);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        return bean;

    }

    /**
     * 定向转账
     * 
     * @param bean
     * @return
     * @author Michael
     */

    @Override
    public PnrApiBean direcTrf(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_DIRECT_TRF);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 商户后台回调地址
        bean.set(ChinaPnrConstant.PARAM_BGRETURL, _bgRetUrl);
        // 签名
        String chkValue =
                bean.getChkValueMerged(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID,
                        ChinaPnrConstant.PARAM_USRCUSTID, ChinaPnrConstant.PARAM_INUSRCUSTID,
                        ChinaPnrConstant.PARAM_TRANSAMT, ChinaPnrConstant.PARAM_RETURL,
                        ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV, ChinaPnrConstant.PARAM_REQEXT);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        return bean;

    }

    /**
     * 快捷充值限额信息查询
     * @param bean
     * @return
     * @author Michael
     */

    @Override
    public PnrApiBean queryPayQuota(PnrApiBean bean) {
        // 消息类型
        bean.set(ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.CMDID_QUERY_PAY_QUOTA);
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 商户后台回调地址
        bean.set(ChinaPnrConstant.PARAM_BGRETURL, _bgRetUrl);
        // 签名
        String chkValue =
                bean.getChkValueMergedMD5(ChinaPnrConstant.PARAM_VERSION, ChinaPnrConstant.PARAM_CMDID,
                        ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_OPENBANKID,
                        ChinaPnrConstant.PARAM_GATEBUSIID, ChinaPnrConstant.PARAM_REQEXT);
        bean.set(ChinaPnrConstant.PARAM_CHKVALUE, chkValue);
        return bean;
    }

    /**
     * 设置共通项目(版本号,商户客户号)
     *
     * @param bean
     */
    private void setCommonItems(PnrApiBean bean) {
        // 版本号
        if (!bean.getAllParams().containsKey(ChinaPnrConstant.PARAM_VERSION)) {
            bean.set(ChinaPnrConstant.PARAM_VERSION, _version);
        }
        // 商户客户号
        bean.set(ChinaPnrConstant.PARAM_MERCUSTID, _merCustId);
    }

    /**
     * 验证汇付天下签名
     *
     * @param bean
     * @return
     */
    @Override
    public ChinapnrBean verifyChinaPnr(PnrApiBean bean) {
        // 方法名
        String methodName = "verifyChinaPnr";
       log.info("[验证汇付天下签名开始]");
        log.debug( "参数: " + bean == null ? "无" : bean.getAllParams() + "]");

        ChinapnrBean ret = new ChinapnrBean();
        String result = null;
        try {
            // 验证用签名
            String forEncryptionStr = null;
            // 消息类型
            String cmdId = bean.get(ChinaPnrConstant.PARAM_CMDID);
            // 消息类型
            String respType = bean.get(ChinaPnrConstant.PARAM_RESPTYPE);
            //
            String retResult = null;

            // 企业开户
            if (ChinaPnrConstant.CMDID_CORP_REGISTER.equals(cmdId)) {
                forEncryptionStr =
                        bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_USRID,
                                ChinaPnrConstant.PARAM_USRNAME, ChinaPnrConstant.PARAM_USRCUSTID,
                                ChinaPnrConstant.PARAM_AUDITSTAT, ChinaPnrConstant.PARAM_TRXID,
                                ChinaPnrConstant.PARAM_OPENBANKID, ChinaPnrConstant.PARAM_CARDID,
                                ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_RESPEXT);
                retResult = bean.get(ChinaPnrConstant.PARAM_TRXID);

            }

            // 企业用户绑定接口
            if (ChinaPnrConstant.CMDID_DIRECT_TRF_AUTH.equals(cmdId)) {
                forEncryptionStr =
                        bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_USRCUSTID,
                                ChinaPnrConstant.PARAM_INUSRCUSTID, ChinaPnrConstant.PARAM_AUTHAMT,
                                ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_REQEXT);
                retResult = bean.get(ChinaPnrConstant.PARAM_TRXID);

            }
            // 定向转账
            if (ChinaPnrConstant.CMDID_DIRECT_TRF.equals(cmdId)) {
                forEncryptionStr =
                        bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID,
                                ChinaPnrConstant.PARAM_USRCUSTID, ChinaPnrConstant.PARAM_INUSRCUSTID,
                                ChinaPnrConstant.PARAM_TRANSAMT, ChinaPnrConstant.PARAM_RETURL,
                                ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV,
                                ChinaPnrConstant.PARAM_REQEXT);
                retResult = bean.get(ChinaPnrConstant.PARAM_TRXID);

            }

            // 用户开户
            if (ChinaPnrConstant.CMDID_USER_REGISTER.equals(cmdId)) {
                forEncryptionStr =
                        bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_USRID,
                                ChinaPnrConstant.PARAM_USRCUSTID, ChinaPnrConstant.PARAM_BGRETURL,
                                ChinaPnrConstant.PARAM_TRXID, ChinaPnrConstant.PARAM_RETURL,
                                ChinaPnrConstant.PARAM_MERPRIV);
                retResult = bean.get(ChinaPnrConstant.PARAM_TRXID);

            }
            // 用户绑卡接口
            else if (ChinaPnrConstant.CMDID_USER_BIND_CARD.equals(cmdId)) {
                forEncryptionStr =
                        bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_OPENACCTID,
                                ChinaPnrConstant.PARAM_OPENBANKID, ChinaPnrConstant.PARAM_USRCUSTID,
                                ChinaPnrConstant.PARAM_TRXID, ChinaPnrConstant.PARAM_BGRETURL,
                                ChinaPnrConstant.PARAM_MERPRIV);
                retResult = bean.get(ChinaPnrConstant.PARAM_TRXID);

            }
            // 用户解绑卡接口
            else if (ChinaPnrConstant.CMDID_BG_UNBIND_CARD.equals(cmdId)) {
                forEncryptionStr =
                        bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_CUSTID,
                                ChinaPnrConstant.PARAM_TRXID, ChinaPnrConstant.PARAM_BANKID,
                                ChinaPnrConstant.PARAM_CARDID, ChinaPnrConstant.PARAM_EXPRESSFLAG,
                                ChinaPnrConstant.PARAM_BGRETURL);
                retResult = bean.get(ChinaPnrConstant.PARAM_TRXID);

            }
            // 充值
            else if (ChinaPnrConstant.CMDID_NET_SAVE.equals(cmdId)) {
                forEncryptionStr =
                        bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_USRCUSTID,
                                ChinaPnrConstant.PARAM_ORDID, ChinaPnrConstant.PARAM_ORDDATE,
                                ChinaPnrConstant.PARAM_TRANSAMT, ChinaPnrConstant.PARAM_TRXID,
                                ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_BGRETURL,
                                ChinaPnrConstant.PARAM_MERPRIV);
                retResult = bean.get(ChinaPnrConstant.PARAM_TRXID);
            }
            // 资金（货款）冻结
            else if (ChinaPnrConstant.CMDID_USR_FREEZE_BG.equals(cmdId)) {
                forEncryptionStr =
                        bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_USRCUSTID,
                                ChinaPnrConstant.PARAM_SUBACCTTYPE, ChinaPnrConstant.PARAM_SUBACCTID,
                                ChinaPnrConstant.PARAM_ORDID, ChinaPnrConstant.PARAM_ORDDATE,
                                ChinaPnrConstant.PARAM_TRANSAMT, ChinaPnrConstant.PARAM_RETURL,
                                ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_TRXID,
                                ChinaPnrConstant.PARAM_MERPRIV);
                retResult = bean.get(ChinaPnrConstant.PARAM_ORDID);
            }
            // 资金（货款）解冻
            else if (ChinaPnrConstant.CMDID_USR_UN_FREEZE.equals(cmdId)) {
                forEncryptionStr =
                        bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID,
                                ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_TRXID,
                                ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_BGRETURL,
                                ChinaPnrConstant.PARAM_MERPRIV);
                retResult = bean.get(ChinaPnrConstant.PARAM_ORDID);
            }
            // 查询预约授权状态
            else if (ChinaPnrConstant.CMDID_QUERY_TENDER_PLAN.equals(cmdId)) {
                forEncryptionStr =
                        bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_RESPDESC, ChinaPnrConstant.PARAM_MERCUSTID,
                                ChinaPnrConstant.PARAM_USRCUSTID, ChinaPnrConstant.PARAM_TRANSSTAT);
                retResult = bean.get(ChinaPnrConstant.PARAM_TRANSSTAT);
            }
            // 开启预约授权
            else if (ChinaPnrConstant.CMDID_AUTO_TENDER_PLAN.equals(cmdId)) {
                forEncryptionStr =
                        bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_USRCUSTID,
                                ChinaPnrConstant.PARAM_TENDERPLANTYPE, ChinaPnrConstant.PARAM_TRANSAMT,
                                ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_MERPRIV);
                retResult = bean.get(ChinaPnrConstant.PARAM_RESPCODE);
            }
            // 关闭预约授权
            else if (ChinaPnrConstant.CMDID_AUTO_TENDER_PLAN_CLOSE.equals(cmdId)) {
                forEncryptionStr =
                        bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_USRCUSTID,
                                ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_MERPRIV);
                retResult = bean.get(ChinaPnrConstant.PARAM_RESPCODE);
            }
            // 主动投标
            else if (ChinaPnrConstant.CMDID_INITIATIVE_TENDER.equals(cmdId)) {
                // 1.0 返回参数验证
                forEncryptionStr =
                        bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID,
                                ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_TRANSAMT,
                                ChinaPnrConstant.PARAM_USRCUSTID, ChinaPnrConstant.PARAM_TRXID,
                                ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_BGRETURL,
                                ChinaPnrConstant.PARAM_MERPRIV);

                // RSA方式验签失败时
                if (!ChinaPnrSignUtils.verifyByRSA(forEncryptionStr, bean.get(ChinaPnrConstant.PARAM_CHKVALUE))) {
                    // 2.0 返回参数验证
                    forEncryptionStr =
                            bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID,
                                    ChinaPnrConstant.PARAM_RESPCODE, ChinaPnrConstant.PARAM_MERCUSTID,
                                    ChinaPnrConstant.PARAM_ORDID, ChinaPnrConstant.PARAM_ORDDATE,
                                    ChinaPnrConstant.PARAM_TRANSAMT, ChinaPnrConstant.PARAM_USRCUSTID,
                                    ChinaPnrConstant.PARAM_TRXID, ChinaPnrConstant.PARAM_ISFREEZE,
                                    ChinaPnrConstant.PARAM_FREEZEORDID, ChinaPnrConstant.PARAM_FREEZETRXID,
                                    ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_BGRETURL,
                                    ChinaPnrConstant.PARAM_MERPRIV, ChinaPnrConstant.PARAM_RESPEXT);
                }
                retResult = bean.get(ChinaPnrConstant.PARAM_ORDID);
            }
            // 自动投标
            else if (ChinaPnrConstant.CMDID_AUTO_TENDER.equals(cmdId)) {
                // 2.0 返回参数验证
                forEncryptionStr =
                        bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID,
                                ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_TRANSAMT,
                                ChinaPnrConstant.PARAM_USRCUSTID, ChinaPnrConstant.PARAM_TRXID,
                                ChinaPnrConstant.PARAM_ISFREEZE, ChinaPnrConstant.PARAM_FREEZEORDID,
                                ChinaPnrConstant.PARAM_FREEZETRXID, ChinaPnrConstant.PARAM_RETURL,
                                ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV,
                                ChinaPnrConstant.PARAM_RESPEXT);
                retResult = bean.get(ChinaPnrConstant.PARAM_ORDID);
            }
            // 投标撤销
            else if (ChinaPnrConstant.CMDID_TENDER_CANCLE.equals(cmdId)) {
                // 1.0 返回参数验证
                forEncryptionStr =
                        bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID,
                                ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_TRANSAMT,
                                ChinaPnrConstant.PARAM_USRCUSTID, ChinaPnrConstant.PARAM_RETURL,
                                ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV);

                // RSA方式验签失败时
                if (!ChinaPnrSignUtils.verifyByRSA(forEncryptionStr, bean.get(ChinaPnrConstant.PARAM_CHKVALUE))) {
                    // 2.0 返回参数验证
                    forEncryptionStr =
                            bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID,
                                    ChinaPnrConstant.PARAM_RESPCODE, ChinaPnrConstant.PARAM_MERCUSTID,
                                    ChinaPnrConstant.PARAM_ORDID, ChinaPnrConstant.PARAM_ORDDATE,
                                    ChinaPnrConstant.PARAM_TRANSAMT, ChinaPnrConstant.PARAM_USRCUSTID,

                                    ChinaPnrConstant.PARAM_ISUNFREEZE, ChinaPnrConstant.PARAM_UNFREEZEORDID,
                                    ChinaPnrConstant.PARAM_FREEZETRXID, ChinaPnrConstant.PARAM_RETURL,
                                    ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV,
                                    ChinaPnrConstant.PARAM_RESPEXT);
                }
                retResult = bean.get(ChinaPnrConstant.PARAM_ORDID);
            }
            // 自动扣款（放款）
            else if (ChinaPnrConstant.CMDID_LOANS.equals(cmdId)) {
                // 1.0 返回参数验证
                forEncryptionStr =
                        bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID,
                                ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_OUTCUSTID,
                                ChinaPnrConstant.PARAM_OUTACCTID, ChinaPnrConstant.PARAM_TRANSAMT,
                                ChinaPnrConstant.PARAM_FEE, ChinaPnrConstant.PARAM_INCUSTID,
                                ChinaPnrConstant.PARAM_INACCTID, ChinaPnrConstant.PARAM_SUBORDID,
                                ChinaPnrConstant.PARAM_SUBORDDATE, ChinaPnrConstant.PARAM_ISDEFAULT,
                                ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_OPENBANKID,
                                ChinaPnrConstant.PARAM_OPENACCTID, ChinaPnrConstant.PARAM_MERPRIV);

                // RSA方式验签失败时
                if (!ChinaPnrSignUtils.verifyByRSA(forEncryptionStr, bean.get(ChinaPnrConstant.PARAM_CHKVALUE))) {
                    // 2.0 返回参数验证
                    forEncryptionStr =
                            bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID,
                                    ChinaPnrConstant.PARAM_RESPCODE, ChinaPnrConstant.PARAM_MERCUSTID,
                                    ChinaPnrConstant.PARAM_ORDID, ChinaPnrConstant.PARAM_ORDDATE,
                                    ChinaPnrConstant.PARAM_OUTCUSTID, ChinaPnrConstant.PARAM_OUTACCTID,
                                    ChinaPnrConstant.PARAM_TRANSAMT, ChinaPnrConstant.PARAM_FEE,
                                    ChinaPnrConstant.PARAM_INCUSTID, ChinaPnrConstant.PARAM_INACCTID,
                                    ChinaPnrConstant.PARAM_SUBORDID, ChinaPnrConstant.PARAM_SUBORDDATE,
                                    ChinaPnrConstant.PARAM_FEEOBJFLAG, ChinaPnrConstant.PARAM_ISDEFAULT,
                                    ChinaPnrConstant.PARAM_ISUNFREEZE, ChinaPnrConstant.PARAM_UNFREEZEORDID,
                                    ChinaPnrConstant.PARAM_FREEZETRXID, ChinaPnrConstant.PARAM_BGRETURL,
                                    ChinaPnrConstant.PARAM_MERPRIV, ChinaPnrConstant.PARAM_RESPEXT);
                }
                retResult = bean.get(ChinaPnrConstant.PARAM_ORDID);
            }
            // 自动扣款（还款）
            else if (ChinaPnrConstant.CMDID_REPAYMENT.equals(cmdId)) {
                // 1.0 返回参数验证
                forEncryptionStr =
                        bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID,
                                ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_OUTCUSTID,
                                ChinaPnrConstant.PARAM_SUBORDID, ChinaPnrConstant.PARAM_SUBORDDATE,
                                ChinaPnrConstant.PARAM_OUTACCTID, ChinaPnrConstant.PARAM_TRANSAMT,
                                ChinaPnrConstant.PARAM_FEE, ChinaPnrConstant.PARAM_INCUSTID,
                                ChinaPnrConstant.PARAM_INACCTID, ChinaPnrConstant.PARAM_BGRETURL,
                                ChinaPnrConstant.PARAM_MERPRIV);

                // RSA方式验签失败时
                if (!ChinaPnrSignUtils.verifyByRSA(forEncryptionStr, bean.get(ChinaPnrConstant.PARAM_CHKVALUE))) {
                    // 3.0 返回参数验证
                    forEncryptionStr =
                            bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID,
                                    ChinaPnrConstant.PARAM_RESPCODE, ChinaPnrConstant.PARAM_MERCUSTID,
                                    ChinaPnrConstant.PARAM_PROID, ChinaPnrConstant.PARAM_ORDID,
                                    ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_OUTCUSTID,
                                    ChinaPnrConstant.PARAM_SUBORDID, ChinaPnrConstant.PARAM_SUBORDDATE,
                                    ChinaPnrConstant.PARAM_OUTACCTID, ChinaPnrConstant.PARAM_PRINCIPALAMT,
                                    ChinaPnrConstant.PARAM_INTERESTAMT, ChinaPnrConstant.PARAM_FEE,
                                    ChinaPnrConstant.PARAM_INCUSTID, ChinaPnrConstant.PARAM_INACCTID,
                                    ChinaPnrConstant.PARAM_FEEOBJFLAG, ChinaPnrConstant.PARAM_DZOBJECT,
                                    ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV,
                                    ChinaPnrConstant.PARAM_RESPEXT);
                    forEncryptionStr = MD5Util2.getMD5String(forEncryptionStr);// 3.0以上使用
                }
                retResult = bean.get(ChinaPnrConstant.PARAM_ORDID);
            }
            // 自动扣款转账（商户用）
            else if (ChinaPnrConstant.CMDID_TRANSFER.equals(cmdId)) {
                forEncryptionStr =
                        bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_RESPDESC, ChinaPnrConstant.PARAM_ORDID,
                                ChinaPnrConstant.PARAM_OUTCUSTID, ChinaPnrConstant.PARAM_OUTACCTID,
                                ChinaPnrConstant.PARAM_TRANSAMT, ChinaPnrConstant.PARAM_INCUSTID,
                                ChinaPnrConstant.PARAM_INACCTID, ChinaPnrConstant.PARAM_RETURL,
                                ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV);
                retResult = bean.get(ChinaPnrConstant.PARAM_ORDID);
            }
            // 取现（页面）[同步异步返回参数列表]
            else if (ChinaPnrConstant.CMDID_CASH.equals(cmdId) || ChinaPnrConstant.CMDID_CASH.equals(respType)) {
                System.out.println("-----取现（页面）[同步异步返回参数列表],cmdId/respType=" + cmdId + respType);
                // 2.0 异步返回参数验证
                forEncryptionStr =
                        bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_RESPTYPE, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_ORDID,
                                ChinaPnrConstant.PARAM_USRCUSTID, ChinaPnrConstant.PARAM_TRANSAMT,
                                ChinaPnrConstant.PARAM_OPENACCTID, ChinaPnrConstant.PARAM_OPENBANKID,
                                ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_BGRETURL,
                                ChinaPnrConstant.PARAM_MERPRIV, ChinaPnrConstant.PARAM_RESPEXT);

                // RSA方式验签失败时
                if (!ChinaPnrSignUtils.verifyByRSA(forEncryptionStr, bean.get(ChinaPnrConstant.PARAM_CHKVALUE))) {
                    // 2.0 同步返回参数验证
                    forEncryptionStr =
                            bean.getChkValueMerged(
                                    false,
                                    ChinaPnrConstant.PARAM_CMDID,
                                    ChinaPnrConstant.PARAM_RESPCODE, // ChinaPnrConstant.PARAM_RESPDESC,
                                    ChinaPnrConstant.PARAM_MERCUSTID,
                                    ChinaPnrConstant.PARAM_ORDID,
                                    ChinaPnrConstant.PARAM_USRCUSTID,
                                    ChinaPnrConstant.PARAM_TRANSAMT,
                                    // ChinaPnrConstant.PARAM_REALTRANSAMT,
                                    ChinaPnrConstant.PARAM_OPENACCTID, ChinaPnrConstant.PARAM_OPENBANKID,
                                    ChinaPnrConstant.PARAM_FEEAMT, ChinaPnrConstant.PARAM_FEECUSTID,
                                    ChinaPnrConstant.PARAM_FEEACCTID, ChinaPnrConstant.PARAM_SERVFEE,
                                    ChinaPnrConstant.PARAM_SERVFEEACCTID, ChinaPnrConstant.PARAM_RETURL,
                                    ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV,
                                    ChinaPnrConstant.PARAM_RESPEXT);
                }
                retResult = bean.get(ChinaPnrConstant.PARAM_ORDID);
            }
            // 用户账户支付
            else if (ChinaPnrConstant.CMDID_USR_ACCT_PAY.equals(cmdId)) {
                forEncryptionStr =
                        bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_ORDID, ChinaPnrConstant.PARAM_USRCUSTID,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_TRANSAMT,
                                ChinaPnrConstant.PARAM_INACCTID, ChinaPnrConstant.PARAM_INACCTTYPE,
                                ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_BGRETURL,
                                ChinaPnrConstant.PARAM_MERPRIV);
                retResult = bean.get(ChinaPnrConstant.PARAM_ORDID);
            }
            // 债权转让接口
            else if (ChinaPnrConstant.CMDID_CREDIT_ASSIGN.equals(cmdId)) {
                if (ChinaPnrConstant.RESPCODE_YUE2_FAIL.equals(bean.get(ChinaPnrConstant.PARAM_RESPCODE))) {
                    forEncryptionStr =
                            bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID,
                                    ChinaPnrConstant.PARAM_RESPCODE, ChinaPnrConstant.PARAM_MERCUSTID,
                                    ChinaPnrConstant.PARAM_SELLCUSTID, ChinaPnrConstant.PARAM_CREDITAMT,
                                    ChinaPnrConstant.PARAM_CREDITDEALAMT, ChinaPnrConstant.PARAM_FEE,
                                    ChinaPnrConstant.PARAM_BUYCUSTID, ChinaPnrConstant.PARAM_ORDID,
                                    ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_RETURL,
                                    ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_RESPEXT);
                    // RSA方式验签失败时
                    if (!ChinaPnrSignUtils.verifyByRSA(forEncryptionStr, bean.get(ChinaPnrConstant.PARAM_CHKVALUE))) {
                        forEncryptionStr =
                                bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID,
                                        ChinaPnrConstant.PARAM_RESPCODE, ChinaPnrConstant.PARAM_MERCUSTID,
                                        ChinaPnrConstant.PARAM_SELLCUSTID, ChinaPnrConstant.PARAM_CREDITAMT,
                                        ChinaPnrConstant.PARAM_CREDITDEALAMT, ChinaPnrConstant.PARAM_FEE,
                                        ChinaPnrConstant.PARAM_BUYCUSTID, ChinaPnrConstant.PARAM_ORDID,
                                        ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_RETURL,
                                        ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_RESPEXT,
                                        ChinaPnrConstant.PARAM_LCID, ChinaPnrConstant.PARAM_TOTALLCAMT// 3.0以上使用
                                );
                        forEncryptionStr = MD5Util2.getMD5String(forEncryptionStr);// 3.0以上使用
                    }
                } else {
                    forEncryptionStr =
                            bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID,
                                    ChinaPnrConstant.PARAM_RESPCODE, ChinaPnrConstant.PARAM_MERCUSTID,
                                    ChinaPnrConstant.PARAM_SELLCUSTID, ChinaPnrConstant.PARAM_CREDITAMT,
                                    ChinaPnrConstant.PARAM_CREDITDEALAMT, ChinaPnrConstant.PARAM_FEE,
                                    ChinaPnrConstant.PARAM_BUYCUSTID, ChinaPnrConstant.PARAM_ORDID,
                                    ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_RETURL,
                                    ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV,
                                    ChinaPnrConstant.PARAM_RESPEXT, ChinaPnrConstant.PARAM_PAGETYPE);

                    // RSA方式验签失败时
                    if (!ChinaPnrSignUtils.verifyByRSA(forEncryptionStr, bean.get(ChinaPnrConstant.PARAM_CHKVALUE))) {
                        forEncryptionStr =
                                bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID,
                                        ChinaPnrConstant.PARAM_RESPCODE, ChinaPnrConstant.PARAM_MERCUSTID,
                                        ChinaPnrConstant.PARAM_SELLCUSTID, ChinaPnrConstant.PARAM_CREDITAMT,
                                        ChinaPnrConstant.PARAM_CREDITDEALAMT, ChinaPnrConstant.PARAM_FEE,
                                        ChinaPnrConstant.PARAM_BUYCUSTID, ChinaPnrConstant.PARAM_ORDID,
                                        ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_RETURL,
                                        ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV,
                                        ChinaPnrConstant.PARAM_RESPEXT, ChinaPnrConstant.PARAM_LCID,
                                        ChinaPnrConstant.PARAM_TOTALLCAMT// 3.0以上使用
                                );
                        forEncryptionStr = MD5Util2.getMD5String(forEncryptionStr);// 3.0以上使用
                    }
                }
                retResult = bean.get(ChinaPnrConstant.PARAM_ORDID);
            }// 债权转让接口
            else if (ChinaPnrConstant.CMDID_AUTO_CREDIT_ASSIGN.equals(cmdId)) {
                if (ChinaPnrConstant.RESPCODE_YUE2_FAIL.equals(bean.get(ChinaPnrConstant.PARAM_RESPCODE))) {
                    forEncryptionStr =
                            bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID,
                                    ChinaPnrConstant.PARAM_RESPCODE, ChinaPnrConstant.PARAM_MERCUSTID,
                                    ChinaPnrConstant.PARAM_SELLCUSTID, ChinaPnrConstant.PARAM_CREDITAMT,
                                    ChinaPnrConstant.PARAM_CREDITDEALAMT, ChinaPnrConstant.PARAM_FEE,
                                    ChinaPnrConstant.PARAM_BUYCUSTID, ChinaPnrConstant.PARAM_ORDID,
                                    ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_RETURL,
                                    ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_RESPEXT);
                    // RSA方式验签失败时
                    if (!ChinaPnrSignUtils.verifyByRSA(forEncryptionStr, bean.get(ChinaPnrConstant.PARAM_CHKVALUE))) {
                        forEncryptionStr =
                                bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID,
                                        ChinaPnrConstant.PARAM_RESPCODE, ChinaPnrConstant.PARAM_MERCUSTID,
                                        ChinaPnrConstant.PARAM_SELLCUSTID, ChinaPnrConstant.PARAM_CREDITAMT,
                                        ChinaPnrConstant.PARAM_CREDITDEALAMT, ChinaPnrConstant.PARAM_FEE,
                                        ChinaPnrConstant.PARAM_BUYCUSTID, ChinaPnrConstant.PARAM_ORDID,
                                        ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_RETURL,
                                        ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_RESPEXT,
                                        ChinaPnrConstant.PARAM_LCID, ChinaPnrConstant.PARAM_TOTALLCAMT// 3.0以上使用
                                );
                        forEncryptionStr = MD5Util2.getMD5String(forEncryptionStr);// 3.0以上使用
                    }
                } else {
                    forEncryptionStr =
                            bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID,
                                    ChinaPnrConstant.PARAM_RESPCODE, ChinaPnrConstant.PARAM_MERCUSTID,
                                    ChinaPnrConstant.PARAM_SELLCUSTID, ChinaPnrConstant.PARAM_CREDITAMT,
                                    ChinaPnrConstant.PARAM_CREDITDEALAMT, ChinaPnrConstant.PARAM_FEE,
                                    ChinaPnrConstant.PARAM_BUYCUSTID, ChinaPnrConstant.PARAM_ORDID,
                                    ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_RETURL,
                                    ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV,
                                    ChinaPnrConstant.PARAM_RESPEXT, ChinaPnrConstant.PARAM_PAGETYPE);

                    // RSA方式验签失败时
                    if (!ChinaPnrSignUtils.verifyByRSA(forEncryptionStr, bean.get(ChinaPnrConstant.PARAM_CHKVALUE))) {
                        forEncryptionStr =
                                bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID,
                                        ChinaPnrConstant.PARAM_RESPCODE, ChinaPnrConstant.PARAM_MERCUSTID,
                                        ChinaPnrConstant.PARAM_SELLCUSTID, ChinaPnrConstant.PARAM_CREDITAMT,
                                        ChinaPnrConstant.PARAM_CREDITDEALAMT, ChinaPnrConstant.PARAM_FEE,
                                        ChinaPnrConstant.PARAM_BUYCUSTID, ChinaPnrConstant.PARAM_ORDID,
                                        ChinaPnrConstant.PARAM_ORDDATE, ChinaPnrConstant.PARAM_RETURL,
                                        ChinaPnrConstant.PARAM_BGRETURL, ChinaPnrConstant.PARAM_MERPRIV,
                                        ChinaPnrConstant.PARAM_RESPEXT, ChinaPnrConstant.PARAM_LCID,
                                        ChinaPnrConstant.PARAM_TOTALLCAMT// 3.0以上使用
                                );
                        forEncryptionStr = MD5Util2.getMD5String(forEncryptionStr);// 3.0以上使用
                    }
                }
                retResult = bean.get(ChinaPnrConstant.PARAM_ORDID);
            }
            // 生利宝交易接口
            else if (ChinaPnrConstant.CMDID_FSS_TRANS.equals(cmdId)) {
                forEncryptionStr =
                        bean.getChkValueMerged(false, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_USRCUSTID,
                                ChinaPnrConstant.PARAM_ORDID, ChinaPnrConstant.PARAM_ORDDATE,
                                ChinaPnrConstant.PARAM_TRANSTYPE, ChinaPnrConstant.PARAM_TRANSAMT,
                                ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_BGRETURL,
                                ChinaPnrConstant.PARAM_MERPRIV, ChinaPnrConstant.PARAM_RESPEXT);
                retResult = bean.get(ChinaPnrConstant.PARAM_ORDID);
            }
            // 标的信息录入接口
            else if (ChinaPnrConstant.CMDID_ADD_BID_INFO.equals(cmdId)) {
                forEncryptionStr =
                        bean.getChkValueMerged(true, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_PROID,
                                ChinaPnrConstant.PARAM_AUDITSTAT, ChinaPnrConstant.PARAM_BGRETURL,
                                ChinaPnrConstant.PARAM_MERPRIV, ChinaPnrConstant.PARAM_RESPEXT);
                forEncryptionStr = MD5Util2.getMD5String(forEncryptionStr);
                retResult = bean.get(ChinaPnrConstant.PARAM_PROID);
            }
            // 标的信息补录输入接口
            else if (ChinaPnrConstant.CMDID_ADD_BID_INFO.equals(cmdId)) {
                forEncryptionStr =
                        bean.getChkValueMerged(true, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_PROID,
                                ChinaPnrConstant.PARAM_RETURL, ChinaPnrConstant.PARAM_BGRETURL,
                                ChinaPnrConstant.PARAM_MERPRIV, ChinaPnrConstant.PARAM_RESPEXT);
                forEncryptionStr = MD5Util2.getMD5String(forEncryptionStr);
                retResult = bean.get(ChinaPnrConstant.PARAM_PROID);
            }
            // 标的审核状态查询接口
            else if (ChinaPnrConstant.CMDID_QUERY_BID_INFO.equals(cmdId)) {
                forEncryptionStr =
                        bean.getChkValueMerged(true, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_PROID,
                                ChinaPnrConstant.PARAM_BORRTOTAMT, ChinaPnrConstant.PARAM_BORRCUSTID,
                                ChinaPnrConstant.PARAM_STATUS, ChinaPnrConstant.PARAM_RESPEXT);
                forEncryptionStr = MD5Util2.getMD5String(forEncryptionStr);
                retResult = bean.get(ChinaPnrConstant.PARAM_PROID);
            }
            // 快捷充值限额查询接口
            else if (ChinaPnrConstant.CMDID_QUERY_PAY_QUOTA.equals(cmdId)) {
                forEncryptionStr =
                        bean.getChkValueMerged(true, ChinaPnrConstant.PARAM_CMDID, ChinaPnrConstant.PARAM_RESPCODE,
                                ChinaPnrConstant.PARAM_MERCUSTID, ChinaPnrConstant.PARAM_RESPEXT);
                forEncryptionStr = MD5Util2.getMD5String(forEncryptionStr);
                retResult = bean.get(ChinaPnrConstant.PARAM_TRXID);
            }

            // RSA方式验签
            boolean res = ChinaPnrSignUtils.verifyByRSA(forEncryptionStr, bean.get(ChinaPnrConstant.PARAM_CHKVALUE));
            ret.setVerifyFlag(res);
            ret.setVerifyResult("RECV_ORD_ID_" + StringUtils.trimToEmpty(retResult));
            log.debug("[RSA方式验签结果:" + result + "]");
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }

        log.debug("[验证汇付天下签名结束]");

        return ret;
    }

}
