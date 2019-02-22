/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.MerchantAccountService;
import com.hyjf.am.response.admin.MerchantAccountResponse;
import com.hyjf.am.resquest.admin.AdminMerchantAccountRequest;
import com.hyjf.am.resquest.admin.MerchantAccountListRequest;
import com.hyjf.am.vo.admin.AdminMerchantAccountSumCustomizeVO;
import com.hyjf.am.vo.admin.MerchantAccountVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.pay.lib.chinapnr.ChinapnrBean;
import com.hyjf.pay.lib.chinapnr.util.ChinaPnrConstant;
import com.hyjf.pay.lib.chinapnr.util.ChinapnrUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhangqingqing
 * @version MerchantAccountServiceImpl, v0.1 2018/7/5 10:24
 */
@Service
public class MerchantAccountServiceImpl implements MerchantAccountService {

    private static final Logger logger = LoggerFactory.getLogger(MerchantAccountServiceImpl.class);

    /** 商户客户号 */
    @Value("${hyjf.chinapnr.mercustid}")
    private String merCustId;

    @Autowired
    AmTradeClient amTradeClient;

    @Override
    public boolean updateMerchantAccount(List<MerchantAccountVO> merchantAccounts) {
        int nowTime = GetDate.getNowTime10();
        if(merchantAccounts!=null&&merchantAccounts.size()>0){
            // 调用汇付接口,查询余额
            ChinapnrBean bean = new ChinapnrBean();
            // 版本号(必须)
            bean.setVersion(ChinaPnrConstant.VERSION_10);
            // 消息类型(必须)
            bean.setCmdId(ChinaPnrConstant.CMDID_QUERY_ACCTS);
            // 商户客户号
            bean.setMerCustId(merCustId);
            // 发送请求获取结果
            ChinapnrBean resultBean = ChinapnrUtil.callApiBg(bean);
            String respCode = resultBean == null ? "" : resultBean.getRespCode();
            // 如果接口调用成功
            if (null!=resultBean&&ChinaPnrConstant.RESPCODE_SUCCESS.equals(respCode)) {
                //如果接口返回的账户结果串不为空
                if (resultBean != null&&StringUtils.isNotBlank(resultBean.getAcctDetails())) {
                    try {
                        String acctDetails = resultBean.getAcctDetails();
                        //转换账户结果串为json数组
                        JSONArray acctDetailsList = JSONArray.parseArray(acctDetails);
                        // 遍历所有子账户
                        for(Object object:acctDetailsList){
                            JSONObject acctObject = (JSONObject)object;
                            // 取得公司账户余额(所有子账户余额相加)
                            String accType= acctObject.getString("AcctType");
                            String accCode= acctObject.getString("SubAcctId");
                            BigDecimal avlBal = StringUtils.isBlank(acctObject.getString("AvlBal")) ? new BigDecimal("0") : acctObject.getBigDecimal("AvlBal");
                            BigDecimal acctBal = StringUtils.isBlank(acctObject.getString("AcctBal")) ? new BigDecimal("0") : acctObject.getBigDecimal("AcctBal");
                            BigDecimal frzBal = StringUtils.isBlank(acctObject.getString("FrzBal")) ? new BigDecimal("0") : acctObject.getBigDecimal("FrzBal");
                            for(MerchantAccountVO merchantAccount:merchantAccounts){
                                if(StringUtils.isNotBlank(accType)&&StringUtils.isNotBlank(accCode)&&accType.equals(merchantAccount.getSubAccountType())&&accCode.equals(merchantAccount.getSubAccountCode())){
                                    merchantAccount.setAccountBalance(acctBal);
                                    merchantAccount.setAvailableBalance(avlBal);
                                    merchantAccount.setFrost(frzBal);
                                    merchantAccount.setUpdatetime(nowTime);
                                    this.amTradeClient.updateByPrimaryKeySelective(merchantAccount);
                                }
                            }
                        }
                    } catch (Exception e) {
                        return false;
                    }
                    return true;
                } else {
                    logger.info("接口返回的账户结果串为空");
                    return false;
                }
            } else {
                logger.info("接口调用失败");
                return false;
            }
        }
        logger.info("商户子账户列表查询失败");
        return false;
    }

    @Override
    public MerchantAccountResponse selectRecordList(MerchantAccountListRequest request) {
        MerchantAccountResponse merchantAccounts = amTradeClient.selectRecordList(request);
        return merchantAccounts;
    }


    /**
     * 分页查询账户平台设置列表
     * @return
     */
    @Override
    public MerchantAccountResponse selectMerchantAccountListByPage(AdminMerchantAccountRequest request){
        return amTradeClient.selectMerchantAccountListByPage(request);
    }
    /**
     * 根据id查询账户平台设置
     * @return
     */
    @Override
    public MerchantAccountResponse searchAccountConfigInfo(Integer id){
        return amTradeClient.searchAccountConfigInfo(id);
    }
    /**
     * 添加账户平台设置
     * @return
     */
    @Override
    public MerchantAccountResponse saveAccountConfig(AdminMerchantAccountRequest request){
        return amTradeClient.saveAccountConfig(request);
    }
    /**
     * 修改账户平台设置
     * @return
     */
    @Override
    public MerchantAccountResponse updateAccountConfig(AdminMerchantAccountRequest request){
        return amTradeClient.updateAccountConfig(request);
    }

    /**
     * 子账户类型 查询
     * @return
     */
    @Override
    public List<ParamNameVO> getParamNameList(String code){
        return amTradeClient.getParamNameList(code);
    }
    /**
     *
     * 根据子账户名称检索
     * @param subAccountName
     * @return
     */
    @Override
    public int countAccountListInfoBySubAccountName(String ids, String subAccountName){
        return amTradeClient.countAccountListInfoBySubAccountName(ids,subAccountName);
    }

    /**
     *
     * 根据子账户代号检索
     * @param subAccountCode
     * @return
     */
    @Override
    public int countAccountListInfoBySubAccountCode(String ids, String subAccountCode){
        return amTradeClient.countAccountListInfoBySubAccountCode(ids,subAccountCode);
    }

    @Override
    public AdminMerchantAccountSumCustomizeVO searchAccountSum() {
        return amTradeClient.searchAccountSum();
    }


}
