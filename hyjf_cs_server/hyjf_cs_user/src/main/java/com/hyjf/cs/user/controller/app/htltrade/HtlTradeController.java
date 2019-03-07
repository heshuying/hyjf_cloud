/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.htltrade;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.HtlTradeRequest;
import com.hyjf.am.vo.trade.HtlProductIntoRecordVO;
import com.hyjf.am.vo.trade.HtlProductRedeemVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.user.bean.HtlTradeResultBean;
import com.hyjf.cs.user.service.htltrade.HtlTradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 * @author sunpeikai
 * @version HtlTradeController, v0.1 2018/7/25 9:21
 */
@Api(value = "app端-获取我的账单(汇天利)",tags = "app端-获取我的账单(汇天利)")
@RestController
@CrossOrigin("*")
@RequestMapping(value = "/hyjf-app/htl")
public class HtlTradeController extends BaseController {

	@Autowired
	public HtlTradeService htlTradeService;


	/**
	 * 获取我的账单
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "获取我的账单(汇天利)")
	@PostMapping(value = "/htlList")
	public JSONObject getHtlList(HttpServletRequest request,@RequestHeader(value = "userId") Integer userId,
								 @RequestHeader(value = "version") String version,
								 @RequestHeader(value = "sign") String sign,
								 @RequestHeader(value = "key") String key) {

		JSONObject ret = new JSONObject();
		ret.put("request", "/hyjf-app/htl/htlList");
		// 网络状态
		String netStatus = request.getParameter("netStatus");
		// 平台
		String platform = request.getParameter("platform");
		//账单类型：1全部，2购入，3赎回
		String tradeType= request.getParameter("tradeType");

		Integer page = Integer.valueOf(request.getParameter("page"));

		Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));

		logger.info("trade -> [{}],userId -> [{}]",tradeType,userId);

		// 检查参数正确性
		if (Validator.isNull(version) || Validator.isNull(netStatus) || Validator.isNull(platform) || Validator.isNull(tradeType) ||  Validator.isNull(sign)) {
			ret.put("status", "1");
			ret.put("statusDesc", "请求参数非法");
			return ret;
		}

//        // 判断sign是否存在
//        boolean isSignExists = SecretUtil.isExists(sign);
//        if (!isSignExists) {
//            ret.put("status", "1");
//            ret.put("statusDesc", "请求参数非法");
//            return ret;
//        }

		// 取得加密用的Key
		if (Validator.isNull(key)) {
			ret.put("status", "1");
			ret.put("statusDesc", "请求参数非法");
			return ret;
		}


		List<HtlProductIntoRecordVO> tenders = new ArrayList<>();//购入
		int tenderSize = 0;
		List<HtlProductRedeemVO> redeems = new ArrayList<>();//赎回
		int redeemSize = 0;
		List<HtlTradeResultBean> htlList= new ArrayList<HtlTradeResultBean>();//汇总
		int count=0;

		//汇天利购买记录
		if ("1".equals(tradeType) || "2".equals(tradeType)) {
			HtlTradeRequest htlTradeRequest = new HtlTradeRequest();
			try {
				htlTradeRequest.setUserId(userId);
				htlTradeRequest.setLimitStart(pageSize * (page - 1));
				htlTradeRequest.setLimitEnd(pageSize);
				//出借状态 0表示成功状态
				htlTradeRequest.setStatus(0);
				tenderSize = htlTradeService.countHtlIntoRecord(htlTradeRequest);
				count+=tenderSize;
				tenders = htlTradeService.getIntoRecordList(htlTradeRequest);
				if(!CollectionUtils.isEmpty(tenders)){
					for(HtlProductIntoRecordVO tender: tenders){
						HtlTradeResultBean htlTradeResultBean= new HtlTradeResultBean();
						htlTradeResultBean.setAmount(CustomConstants.DF_FOR_VIEW.format(tender.getAmount()));
						htlTradeResultBean.setTradeType("2");
						htlTradeResultBean.setCreateTime(tender.getInvestTimeStr());
						htlList.add(htlTradeResultBean);
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				ret.put("status", "1");
				ret.put("statusDesc", "获取购买列表失败");
				return ret;
			}
		}
		//汇天利赎回记录
		if ("1".equals(tradeType) || "3".equals(tradeType)) {
			HtlTradeRequest htlTradeRequest = new HtlTradeRequest();
			try {
				htlTradeRequest.setUserId(userId);
				htlTradeRequest.setLimitStart(pageSize * (page - 1));
				htlTradeRequest.setLimitEnd(pageSize);
				//赎回状态：0成功，1失败
				htlTradeRequest.setStatus(0);
				redeemSize = htlTradeService.countProductRedeemRecord(htlTradeRequest);
				count+=redeemSize;
				redeems = htlTradeService.getRedeemRecordList(htlTradeRequest);
				if(!CollectionUtils.isEmpty(redeems)){
					for(HtlProductRedeemVO redeem: redeems){
						HtlTradeResultBean htlTradeResultBean= new HtlTradeResultBean();
						htlTradeResultBean.setAmount(CustomConstants.DF_FOR_VIEW.format(redeem.getAmount()));
						htlTradeResultBean.setTradeType("3");
						htlTradeResultBean.setCreateTime(redeem.getRedeemTimeStr());
						htlList.add(htlTradeResultBean);
					}
				}

			} catch (Exception e) {
				logger.error(e.getMessage());
				ret.put("status", "1");
				ret.put("statusDesc", "获取赎回列表失败");
				return ret;
			}
		}
		//按时间排序
		Collections.sort(htlList, new Comparator<HtlTradeResultBean>(){
			@Override
			public int compare(HtlTradeResultBean o1, HtlTradeResultBean o2) {
				return o2.getCreateTime().compareTo(o1.getCreateTime());
			}
		});

		ret.put("status", "0");
		ret.put("statusDesc", "成功");
		ret.put("count", count+"");
		ret.put("htlList", htlList);

		return ret;
	}

}




