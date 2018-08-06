/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.WebsiteService;
import com.hyjf.am.response.admin.AccountWebListResponse;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.trade.AccountTradeVO;
import com.hyjf.pay.lib.chinapnr.ChinapnrBean;
import com.hyjf.pay.lib.chinapnr.util.ChinaPnrConstant;
import com.hyjf.pay.lib.chinapnr.util.ChinapnrUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangqingqing
 * @version WebsiteServiceImpl, v0.1 2018/7/6 10:53
 */
@Service
public class WebsiteServiceImpl implements WebsiteService {

    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    CsMessageClient csMessageClient;

    @Override
    public List<AccountTradeVO> selectTradeTypes() {
        List<AccountTradeVO> list = amTradeClient.selectTradeTypes();
        return list;
    }

    @Override
    public AccountWebListResponse queryAccountWebList(AccountWebListVO accountWebList) {
        AccountWebListResponse response = csMessageClient.queryAccountWebList(accountWebList);
        return response;
    }

    @Override
    public String selectBorrowInvestAccount(AccountWebListVO accountWebList) {
        String total = csMessageClient.selectBorrowInvestAccount(accountWebList);
        return total;
    }

    @Override
    public double getCompanyYuE(String companyId) {
        // 得到接口API对象
        // PnrApi api = new ChinaPnrApiImpl();
        ChinapnrBean pnrBean = new ChinapnrBean();
        // 版本号
        pnrBean.setVersion(ChinaPnrConstant.VERSION_10);
        // 消息类型
        pnrBean.setCmdId(ChinaPnrConstant.CMDID_QUERY_ACCTS);
        // 商户客户号
        pnrBean.setMerCustId(companyId);
        // 签名
        pnrBean.setChkValue(ChinaPnrConstant.PARAM_CHKVALUE);

        double acctBalsSum = 0.00;
        // 调用汇付天下API接口
        JSONObject jsonObject = ChinapnrUtil.callApiBgForYuE(pnrBean);
        // 取得所有子账户详细信息
        String acctDetails = jsonObject.getString("AcctDetails");
        if(StringUtils.isNotEmpty(acctDetails)){
            JSONArray acctDetailsList = JSONArray.parseArray(acctDetails);
            // 遍历所有子账户
            for(Object object:acctDetailsList){
                JSONObject acctObject = (JSONObject)object;
                // 取得公司账户余额(所有子账户余额相加)
                acctBalsSum += acctObject.getDoubleValue("AcctBal");
            }
        }
        return acctBalsSum;
    }
}
