/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.exception.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.BankCardExceptionRequest;
import com.hyjf.am.user.dao.mapper.customize.AdminBankCardExceptionCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.dao.model.customize.AdminBankCardExceptionCustomize;
import com.hyjf.am.user.service.admin.exception.BankCardRepairService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetSessionOrRequestUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.chinapnr.ChinapnrBean;
import com.hyjf.pay.lib.chinapnr.util.ChinaPnrConstant;
import com.hyjf.pay.lib.chinapnr.util.ChinapnrUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: BankCardRepairServiceImpl, v0.1 2018/8/14 15:03
 */
@Service(value = "userBankCardRepairServiceImpl")
public class BankCardRepairServiceImpl extends BaseServiceImpl implements BankCardRepairService {

    private static final Logger logger = LoggerFactory.getLogger(BankCardRepairServiceImpl.class);

    @Autowired
    private AdminBankCardExceptionCustomizeMapper adminBankCardExceptionCustomizeMapper;

    @Value("${hyjf.chinapnr.mercustid}")
    private String PROP_MERCUSTID;

    /**
     * 银行卡异常count
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int getBankCardExceptionCount(BankCardExceptionRequest request) {
        return adminBankCardExceptionCustomizeMapper.queryAccountBankCount(request);
    }

    /**
     * 银行卡异常list
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<AdminBankCardExceptionCustomize> searchBankCardExceptionList(BankCardExceptionRequest request) {
        return adminBankCardExceptionCustomizeMapper.queryAccountBankList(request);
    }

    /**
     * 更新银行卡(admin后台异常中心-银行卡异常用)
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public String updateAccountBankByUserId(BankCardExceptionRequest request) {
        Integer userId = Integer.valueOf(request.getUserId());
        List<BankConfigVO> bankConfigVOList = request.getBankConfigVOList();
        // bank.setBankStatus(1)  这个字段改过了  不知道是不是对应status

        String respCode = "";
        try {
            AccountChinapnrExample example = new AccountChinapnrExample();
            example.createCriteria().andUserIdEqualTo(userId);
            List<AccountChinapnr> list = accountChinapnrMapper.selectByExample(example);
            if (list != null && list.size() != 0) {
                AccountChinapnr accountChinapnr = list.get(0);

                ChinapnrBean bean = new ChinapnrBean();
                bean.setVersion(ChinaPnrConstant.VERSION_10);// 接口版本号
                bean.setCmdId(ChinaPnrConstant.CMDID_QUERY_CARD_INFO); // 银行卡查询接口
                bean.setMerCustId(PROP_MERCUSTID); // 商户客户号
                bean.setUsrCustId(String.valueOf(accountChinapnr.getChinapnrUsrcustid()));// 用户客户号
                // bean.setBgRetUrl(ChinapnrUtil.getBgRetUrl()); //
                // 商户后台应答地址(必须)
                // bean.setBgRetUrl(PropUtils.getSystem(CustomConstants.HYJF_WEB_URL)
                // + request.getContextPath()
                // +UserBindCardDefine.REQUEST_MAPPING
                // +UserBindCardDefine.RETURN_MAPPING);// 商户后台应答地址(必须)

                // 调用汇付接口 4.4.11 银行卡查询接口
                ChinapnrBean chinapnrBean = ChinapnrUtil.callApiBg(bean);
                if(chinapnrBean != null){
                    respCode = chinapnrBean.getRespCode() == null ? "" : chinapnrBean.getRespCode();
                    AccountBankExample AccountBankExample = new AccountBankExample();
                    AccountBankExample.Criteria cri = AccountBankExample.createCriteria();
                    cri.andUserIdEqualTo(accountChinapnr.getUserId());
                    // 如果接口调用成功
                    if (ChinaPnrConstant.RESPCODE_SUCCESS.equals(respCode)) {
                        if(chinapnrBean.getUsrCardInfolist()==null) {
                            return respCode;
                        }
                        String UsrCardInfolist = chinapnrBean.getUsrCardInfolist();
                        JSONArray array = JSONObject.parseArray(UsrCardInfolist);
                        if (array.size() != 0) {
                            // if(accountChinapnr.getUserId()==500018){
                            // }
                            // =============================两步:第一步:更新银行卡,第二步:更新身份证号等等=============================
                            // =============================第一步:更新银行卡=============================
                            Boolean hasExpress = false;// 拥有快捷卡
                            List<AccountBank> accountBankList = new ArrayList<AccountBank>();
                            for (int j = 0; j < array.size(); j++) {
                                JSONObject obj = array.getJSONObject(j);
                                if (!obj.getString("RealFlag").equals("R")) {
                                    // 只有实名卡才入库
                                    continue;
                                }
                                AccountBank bank = new AccountBank();
                                bank.setUserId(accountChinapnr.getUserId());
                                bank.setCardType("0");// 普通卡
                                bank.setBankStatus(1);// 默认都是1
                                if (obj.getString("IsDefault").equals("Y")) {
                                    bank.setCardType("1");// 默认卡
                                }
                                if (obj.getString("ExpressFlag").equals("Y")) {
                                    bank.setBankStatus(0);// 快捷卡
                                    bank.setCardType("2");// 快捷卡
                                    hasExpress = true;
                                }
                                bank.setAccount(obj.getString("CardId"));
                                bank.setBank(obj.getString("BankId"));
                                bank.setProvince(obj.getString("ProvId"));
                                bank.setCity("0");
                                bank.setArea(obj.getInteger("AreaId"));
                                bank.setCreateIp(getIp());
                                bank.setUpdateIp(getIp());
                                if (StringUtils.isNotEmpty(obj.getString("UpdDateTime"))) {
                                    bank.setCreateTime(GetDate.str2Date(obj.getString("UpdDateTime"),new SimpleDateFormat("yyyyMMddHHmmss")));
                                    bank.setUpdateTime(GetDate.str2Date(obj.getString("UpdDateTime"),new SimpleDateFormat("yyyyMMddHHmmss")));
                                } else {
                                    bank.setCreateTime(GetDate.getNowTime());
                                    bank.setUpdateTime(GetDate.getNowTime());
                                }
                                accountBankList.add(bank);
                            }
                            if (!hasExpress) {
                                // 如果没有快捷卡,则将status都置为0
                                for (AccountBank bank : accountBankList) {
                                    bank.setBankStatus(0);
                                }
                            }

                            //补全银行卡操作记录
                            BankCardLogExample bankCardLogExample = new BankCardLogExample();
                            BankCardLogExample.Criteria aCriteria = bankCardLogExample.createCriteria();
                            aCriteria.andUserIdEqualTo(accountChinapnr.getUserId());
                            List<BankCardLog> bankCardLogList = bankCardLogMapper.selectByExample(bankCardLogExample);
                            List<String> accountList = new ArrayList<String>();
                            for(BankCardLog bankCardLog : bankCardLogList){
                                accountList.add(bankCardLog.getCardNo());
                            }
                            // 数据库操作
                            accountBankMapper.deleteByExample(AccountBankExample);
                            for (AccountBank bank : accountBankList) {
                                accountBankMapper.insertSelective(bank);
                                if(!accountList.contains(bank.getAccount())){
                                    //插入操作记录表
                                    BankCardLog bankCardLog = new BankCardLog();
                                    bankCardLog.setUserId(bank.getUserId());
                                    bankCardLog.setUserName(this.findUserByUserId(bank.getUserId()).getUsername());
                                    bankCardLog.setBankCode(bank.getBank());
                                    bankCardLog.setCardNo(bank.getAccount());
                                    //获取银行卡姓名
                                    for(BankConfigVO bankConfigVO:bankConfigVOList){
                                        String bankConfigCode = bankConfigVO.getCode();
                                        String userBankCode = bankCardLog.getBankCode();
                                        if(StringUtils.isNotEmpty(bankConfigCode)){
                                            if(StringUtils.isNotEmpty(userBankCode) && userBankCode.equals(bankConfigCode)){
                                                bankCardLog.setBankName(bankConfigVO.getName());
                                            }else{
                                                bankCardLog.setBankName("");
                                            }
                                        }else{
                                            bankCardLog.setBankName("");
                                        }
                                    }

                                    bankCardLog.setCardType(bank.getBankStatus()==0?0:2);//卡类型 0普通提现卡1默认卡2快捷支付卡
                                    bankCardLog.setOperationType(0);//操作类型  0绑定 1删除
                                    bankCardLog.setStatus(0);//成功
                                    bankCardLog.setCreateTime(bank.getCreateTime());//操作时间

                                    this.bankCardLogMapper.insertSelective(bankCardLog);
                                }
                            }

                            // 第二步,更新身份证号等等
                            UserInfoExample uie = new UserInfoExample();
                            uie.createCriteria().andUserIdEqualTo(accountChinapnr.getUserId());
                            List<UserInfo> l = userInfoMapper.selectByExample(uie);
                            if (l != null && l.size() == 1) {
                                UserInfo usersInfo = l.get(0);
                                JSONObject obj = array.getJSONObject(0);
                                // 身份证号
                                String CertId = obj.getString("CertId");
                                String UsrName = obj.getString("UsrName");
                                if (Validator.isNotNull(CertId)
                                        && (usersInfo.getIdcard() == null || usersInfo.getIdcard().equals("0")
                                        || usersInfo.getIdcard().length() < 18)) {
                                    if (CertId.length() == 18) {
                                        usersInfo.setIdcard(CertId);
                                        int sexInt = Integer.parseInt(CertId.substring(16, 17));
                                        if (sexInt % 2 == 0) {
                                            sexInt = 2;
                                        } else {
                                            sexInt = 1;
                                        }
                                        usersInfo.setSex(sexInt);
                                        // 出生日期
                                        String birthDay = CertId.substring(6, 14);
                                        usersInfo.setBirthday(birthDay);
                                    }
                                }
                                if (StringUtils.isEmpty(usersInfo.getTruename())
                                        || usersInfo.getTruename().equals("0")) {
                                    if (StringUtils.isNotEmpty(UsrName)) {
                                        usersInfo.setTruename(UsrName);
                                    }
                                }
                                userInfoMapper.updateByPrimaryKey(usersInfo);
                            }

                        } else {
                            accountBankMapper.deleteByExample(AccountBankExample);
                        }
                        return respCode;
                    } else {
                        String respDesc = "";
                        if(chinapnrBean.getRespDesc()==null) {
                            return respDesc;
                        }
                        if (StringUtils.isNotEmpty(chinapnrBean.getRespDesc())) {
                            respDesc = chinapnrBean.getRespDesc();
                        }
                        logger.error("更新用户银行卡信息失败,用户ID:【{}】,错误代码:【{}】,错误内容:【{}】",accountChinapnr.getUserId(),respCode,respDesc);
                        return respDesc;
                    }
                }

            } else {
                respCode = "无效的用户ID";
            }
            logger.info("更新用户银行卡信息结束,用户ID:" + userId);
        } catch (Exception e) {
            logger.error("更新用户银行卡信息失败,用户ID:" + userId);
        }
        return respCode;
    }

    /**
     * 获取ip
     * @auth sunpeikai
     * @param
     * @return
     */
    private static String getIp() {
        String ip = "";
        try {
            ip = GetCilentIP.getIpAddr(GetSessionOrRequestUtils.getRequest());
        } catch (Exception e) {
            // e.printStackTrace();
            ip = "127.0.0.1";
        }
        return ip;
    }
}
