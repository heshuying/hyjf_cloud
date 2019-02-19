/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.aems.invest.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.api.ApiRepayListRequest;
import com.hyjf.am.vo.api.ApiRepayListCustomizeVO;
import com.hyjf.am.vo.trade.InvestListCustomizeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.trade.bean.api.ApiInvestListReqBean;
import com.hyjf.cs.trade.service.aems.invest.AemsBorrowListService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.svrcheck.CommonSvrChkService;
import com.hyjf.cs.trade.util.ProjectConstant;
import com.hyjf.cs.trade.util.SignUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AEMS系统:第三方查询投资记录Service实现类
 *
 * @author liuyang
 * @version AemsBorrowListServiceImpl, v0.1 2018/12/13 17:05
 */
@Service
public class AemsBorrowListServiceImpl extends BaseTradeServiceImpl implements AemsBorrowListService {

    @Autowired
    private CommonSvrChkService commonSvrChkService;

    /**
     * 获取回款记录列表
     *
     * @param request
     * @return
     */
    @Override
    public List<ApiRepayListCustomizeVO> searchRepayList(ApiRepayListRequest request) {
        return amTradeClient.searchRepayList(request);
    }

    /**
     * 获取投资记录
     *
     * @param bean
     * @return
     */
    @Override
    public ApiResult getInvestList(ApiInvestListReqBean bean) {
        ApiResult result = new ApiResult();
        Map<String, Object> params = new HashMap<String, Object>();
        // 验证必填参数和分页参数
        commonSvrChkService.checkRequired(bean);
        commonSvrChkService.checkLimit(bean.getLimitStart(), bean.getLimitEnd());
        // 验证
        CheckUtil.check(Validator.isNotNull(bean.getInstCode()), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isNotNull(bean.getStartTime()), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isNotNull(bean.getEndTime()), MsgEnum.STATUS_CE000001);
        logger.info("bean:{}", JSONObject.toJSONString(bean));

        // 验签（测试暂时关闭验签）
        CheckUtil.check(SignUtil.AEMSVerifyRequestSign(bean,"/aems/invest/investList"), MsgEnum.ERR_SIGN);

        List<InvestListCustomizeVO> list = searchInvestListNew(bean);
        result.setData(list);
        return result;
    }

    /**
     * 查询逻辑
     * @author wenxin
     * @date 2018/8/27 14:06
     */
    private  List<InvestListCustomizeVO> searchInvestListNew(ApiInvestListReqBean bean){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("startTime", bean.getStartTime());
        params.put("endTime", bean.getEndTime());
        params.put("borrowNid", bean.getBorrowNid());
        params.put("limitStart",bean.getLimitStart());
        params.put("limitEnd",bean.getLimitEnd());
        //获取投资信息
        List<InvestListCustomizeVO> InvestList = amTradeClient.searchInvestListNew(params);
        List<Integer> usr = new ArrayList<Integer>();
        for (InvestListCustomizeVO vo:InvestList){
            usr.add(Integer.parseInt(vo.getUser_id_usr().trim()));
        }
        //获取accountId
        List<BankOpenAccountVO>  bankAcList =  amTradeClient.sarchInvestOfBankOpenAccount(usr);
        for (InvestListCustomizeVO InvestVo:InvestList){
            for (BankOpenAccountVO BankOpenVo: bankAcList){
                if(BankOpenVo.getUserId() != null && InvestVo.getUser_id_usr().equals(BankOpenVo.getUserId().toString())){
                    InvestVo.setAccountId(BankOpenVo.getAccount());
                }
            }
        }

        List<InvestListCustomizeVO> InvestListRet = new ArrayList<InvestListCustomizeVO>();
        //查询条件过滤
        if(StringUtils.isNotEmpty(bean.getAccountId())) {
            for (InvestListCustomizeVO InvestVo : InvestList) {
                if (InvestVo.getAccountId().equals(bean.getAccountId())) {
                    InvestListRet.add(InvestVo);
                }
            }
        }else{
            InvestListRet.addAll(InvestList);
        }

        return InvestListRet;
    }
}
