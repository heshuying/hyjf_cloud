/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 * @author: lb
 * @version: 1.0
 * Created at: 2017年10月12日 下午2:11:50
 * Modification History:
 * Modified by : 
 */
package com.hyjf.cs.trade.service.invest.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.InvestListCustomizeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.trade.service.invest.BorrowListService;
import com.hyjf.cs.trade.service.svrcheck.CommonSvrChkService;
import com.hyjf.cs.trade.util.ProjectConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hyjf.cs.trade.bean.api.ApiInvestListReqBean;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lb
 */

@Service
public class BorrowListServiceImpl extends BaseTradeServiceImpl implements BorrowListService {
	//private Logger logger = LoggerFactory.getLogger(BorrowListServiceImpl.class);
	@Autowired
	private CommonSvrChkService commonSvrChkService;

	@Autowired
	private AmTradeClient amTradeClient;

	/**
	 * 获取投资记录列表查询
	 * 
	 * @param bean{instCode：机构编号（必填）,startTime:开始时间（必填），
	 *            endTime:结束时间（必填），account：电子账号（选填），borrowNid：标的编号}
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
		CheckUtil.check(ProjectConstant.verifyRequestSign(bean, ProjectConstant.API_METHOD_INVEST_LIST), MsgEnum.ERR_SIGN);

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
