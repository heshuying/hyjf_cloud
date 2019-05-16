package com.hyjf.cs.trade.service.consumer.impl.hgdatareport.cert.transferstatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.BorrowCreditRequest;
import com.hyjf.am.resquest.trade.CreditTenderRequest;
import com.hyjf.am.resquest.trade.HjhDebtCreditRequest;
import com.hyjf.am.resquest.trade.HjhDebtCreditTenderRequest;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.transferstatus.CertTransferStatusService;
import com.hyjf.cs.trade.service.consumer.impl.BaseHgCertReportServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
/**
 * @author pcc
 */

@Service
public class CertTransferStatusServiceImpl extends BaseHgCertReportServiceImpl implements CertTransferStatusService {
	Logger logger = LoggerFactory.getLogger(CertTransferStatusServiceImpl.class);

    private String thisMessName = "转让状态信息上报";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";

	@Autowired
	AmTradeClient amTradeClient;
	@Autowired
	AmUserClient amUserClient;
	@Autowired
	SystemConfig systemConfig;
	@Override
	public JSONArray createDate(Map<String, Object> map, String flag) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(!"5".equals((String)map.get("transferStatus"))){
			try {
				for (int i = 0; i <1; i++) {
					Map<String, Object> param = new LinkedHashMap<String, Object>();
					//接口版本号
					param.put("version", CertCallConstant.CERT_CALL_VERSION);
					//平台编号
					param.put("sourceCode",systemConfig.getCertSourceCode());
					//转让项目编号
					param.put("transferId", map.get("transferId"));
					//状态编码
					param.put("transferStatus", map.get("transferStatus"));
					//成功转让本金金额(元)
					param.put("amount", map.get("amount"));
					//成功转让利息金额 (元)
					param.put("interest", map.get("interest"));
					//成功转让浮动金额 (元)
					param.put("floatMoney", map.get("floatMoney"));
					//状态更新时间
					param.put("productDate", map.get("productDate"));
					list.add(param);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}else{
			String borrowNid=(String) map.get("borrowNid");
			//Borrow borrow=this.getBorrowByBorrowNid(borrowNid);
			logger.info(logHeader +"borrowNid :"+ borrowNid);
			try {
				if("1".equals(flag)){
					BorrowCreditRequest BorrowCreditRequest=new BorrowCreditRequest();
					BorrowCreditRequest.setBidNid(borrowNid);
					List<BorrowCreditVO> creditList=amTradeClient.getBorrowCreditList(BorrowCreditRequest);
					if(creditList==null||creditList.size()==0){
						return null;
					}
					for (BorrowCreditVO borrowCredit : creditList) {
						Map<String, Object> param = new LinkedHashMap<String, Object>();
						//接口版本号
						param.put("version", CertCallConstant.CERT_CALL_VERSION);
						//平台编号
						param.put("sourceCode", systemConfig.getCertSourceCode());
						//转让项目编号
						param.put("transferId", borrowCredit.getCreditNid()+"");
						//状态编码
						param.put("transferStatus", map.get("transferStatus"));
						//成功转让本金金额(元)
						param.put("amount", map.get("amount"));
						//成功转让利息金额 (元)
						param.put("interest", map.get("interest"));
						//成功转让浮动金额 (元)
						param.put("floatMoney", map.get("floatMoney"));
						//状态更新时间
						param.put("productDate", map.get("productDate"));
						list.add(param);
					}
				}else{
					List<HjhDebtCreditVO> hjhDebtCreditList=amTradeClient.getHjhDebtCreditListByBorrowNid(borrowNid);
					if(hjhDebtCreditList==null||hjhDebtCreditList.size()==0){
						return null;
					}
					for (HjhDebtCreditVO hjhDebtCredit : hjhDebtCreditList) {
						Map<String, Object> param = new LinkedHashMap<String, Object>();
						//接口版本号
						param.put("version", CertCallConstant.CERT_CALL_VERSION);
						//平台编号
						param.put("sourceCode", systemConfig.getCertSourceCode());
						//转让项目编号
						param.put("transferId", hjhDebtCredit.getCreditNid()+"");
						//状态编码
						param.put("transferStatus", map.get("transferStatus"));
						//成功转让本金金额(元)
						param.put("amount", map.get("amount"));
						//成功转让利息金额 (元)
						param.put("interest", map.get("interest"));
						//成功转让浮动金额 (元)
						param.put("floatMoney", map.get("floatMoney"));
						//状态更新时间
						param.put("productDate", map.get("productDate"));
						list.add(param);
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			
		}
		if(list.size()==0){
			return null;
		}
		return JSONArray.parseArray(JSON.toJSONString(list));
	}

	@Override
	public Map<String, Object> getMap(String creditNid, String flag,
			String status, String borrowNid) {
		Map<String, Object> map=new HashMap<String, Object>();
		if("1".equals(flag)){
			if("5".equals(status)){
				List<BorrowRepayVO> list=amTradeClient.getBorrowRepayList(borrowNid);
				logger.info(logHeader +"List<BorrowRepayVO> :"+ JSONObject.toJSONString(list));
				//	转让项目编号
				map.put("borrowNid", borrowNid);
				//状态编码
				map.put("transferStatus", "5");
				//成功转让本金金额(元)
				map.put("amount", "0.00");
				//成功转让利息金额 (元)
				map.put("interest", "0.00");
				//成功转让浮动金额 (元)
				map.put("floatMoney", "0.00");
				//	状态更新时间
				map.put("productDate", GetDate.timestamptoStrYYYYMMDDHHMMSS((list.get(0).getRepayActionTime()+"")));
				return map;
			}
			List<BorrowCreditVO> creditList=amTradeClient.getBorrowCreditListByCreditNid(creditNid);
			if(creditList==null||creditList.size()==0){
				return map;
			}
			BorrowCreditVO credit=creditList.get(0);
			switch (status) {
			case "0":
				//转让项目编号
				map.put("transferId", credit.getCreditNid()+"");
				//状态编码
				map.put("transferStatus", "0");
				//	成功转让本金金额(元)
				map.put("amount", "0.00");
				//成功转让利息金额 (元)
				map.put("interest", "0.00");
				//成功转让浮动金额 (元)
				map.put("floatMoney", "0.00");
				//	状态更新时间
				map.put("productDate", GetDate.getDateTimeMyTimeInMillis(credit.getCreateTime()));
				break;
			case "1":
				//转让项目编号
				map.put("transferId", credit.getCreditNid()+"");
				//状态编码
				map.put("transferStatus", "1");
				//	成功转让本金金额(元)
				map.put("amount", "0.00");
				//成功转让利息金额 (元)
				map.put("interest", "0.00");
				//成功转让浮动金额 (元)
				map.put("floatMoney", "0.00");
				//	状态更新时间
				map.put("productDate", GetDate.getDateTimeMyTimeInMillis(credit.getCreateTime()));
				break;
			case "2":
			case "3":
				if(BigDecimal.ZERO.compareTo(credit.getCreditCapitalAssigned())==0){
					Integer now=GetDate.getNowTime10();
					//转让项目编号
					map.put("transferId", credit.getCreditNid()+"");
					//状态编码
					map.put("transferStatus", "3");
					//成功转让本金金额(元)
					map.put("amount", "0.00");
					//成功转让利息金额 (元)
					map.put("interest", "0.00");
					//成功转让浮动金额 (元)
					map.put("floatMoney", "0.00");
					//	状态更新时间
					map.put("productDate",  GetDate.timestamptoStrYYYYMMDDHHMMSS((now<=credit.getEndTime()?now:credit.getEndTime())+""));
				}else{
					CreditTenderRequest creditTenderRequest=new CreditTenderRequest();
					creditTenderRequest.setCreditNid(creditNid);
					List<CreditTenderVO> list=amTradeClient.getCreditTenderList(creditTenderRequest);
					//转让项目编号
					map.put("transferId", credit.getCreditNid()+"");
					//	状态编码
					map.put("transferStatus", "2");
					//成功转让本金金额(元)
					map.put("amount", credit.getCreditCapitalAssigned().toString());
					//成功转让利息金额 (元)
					map.put("interest", credit.getCreditInterestAssigned().toString());
					//成功转让浮动金额 (元)
					map.put("floatMoney", BigDecimal.ZERO.subtract(credit.getCreditCapitalAssigned().
							multiply(credit.getCreditDiscount().divide(new BigDecimal(100)))).setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
					if(credit.getCreditCapitalAssigned().compareTo(credit.getCreditCapital())==0){
						//状态更新时间
						map.put("productDate",  GetDate.getDateTimeMyTimeInMillis(list.get(list.size()-1).getCreateTime()));
					}else{
						//状态更新时间
						map.put("productDate",  GetDate.timestamptoStrYYYYMMDDHHMMSS(credit.getEndTime()+""));
					}
					
				}
				break;
			default:
				break;
			}
		}else{
			
			if("5".equals(status)){
				List<BorrowRepayVO> list=amTradeClient.getBorrowRepayList(borrowNid);
				//转让项目编号
				map.put("borrowNid", borrowNid);
				//状态编码
				map.put("transferStatus", "5");
				//成功转让本金金额(元)
				map.put("amount", "0.00");
				//成功转让利息金额 (元)
				map.put("interest", "0.00");
				//成功转让浮动金额 (元)
				map.put("floatMoney", "0.00");
				//状态更新时间
				map.put("productDate", GetDate.timestamptoStrYYYYMMDDHHMMSS((list.get(0).getRepayActionTime()+"")));
				return map;
			}

			List<HjhDebtCreditVO> hjhDebtCreditList=amTradeClient.getHjhDebtCreditListByCreditNid(creditNid);
			if(hjhDebtCreditList==null||hjhDebtCreditList.size()==0){
				return map;
			}
			HjhDebtCreditVO hjhDebtCredit=hjhDebtCreditList.get(0);
			switch (status) {
			case "0":
				
				//转让项目编号
				map.put("transferId", hjhDebtCredit.getCreditNid()+"");
				//状态编码
				map.put("transferStatus", "0");
				//成功转让本金金额(元)
				map.put("amount", "0.00");
				//成功转让利息金额 (元)
				map.put("interest", "0.00");
				//成功转让浮动金额 (元)
				map.put("floatMoney", "0.00");
				//状态更新时间
				map.put("productDate", GetDate.getDateTimeMyTimeInMillis(hjhDebtCredit.getCreateTime()));
				break;
			case "1":
				
				//转让项目编号
				map.put("transferId", hjhDebtCredit.getCreditNid()+"");
				//状态编码
				map.put("transferStatus", "1");
				//成功转让本金金额(元)
				map.put("amount", "0.00");
				//成功转让利息金额 (元)
				map.put("interest", "0.00");
				//成功转让浮动金额 (元)
				map.put("floatMoney", "0.00");
				//状态更新时间
				map.put("productDate", GetDate.getDateTimeMyTimeInMillis(hjhDebtCredit.getCreateTime()));
				break;
			case "2":
			case "3":
				if(BigDecimal.ZERO.compareTo(hjhDebtCredit.getCreditCapitalAssigned())==0){
					Integer now=GetDate.getNowTime10();
					//转让项目编号
					map.put("transferId", hjhDebtCredit.getCreditNid()+"");
					//状态编码
					map.put("transferStatus", "3");
					//成功转让本金金额(元)
					map.put("amount", "0.00");
					//成功转让利息金额 (元)
					map.put("interest", "0.00");
					//成功转让浮动金额 (元)
					map.put("floatMoney", "0.00");
					String endTime=hjhDebtCredit.getEndTime()==0?GetDate.getDateTimeMyTimeInMillis(hjhDebtCredit.getUpdateTime()):
                            GetDate.timestamptoStrYYYYMMDDHHMMSS((now<=hjhDebtCredit.getEndTime()?now:hjhDebtCredit.getEndTime())+"");
					//状态更新时间
					map.put("productDate",  endTime);
				}else{
					HjhDebtCreditTenderRequest hjhDebtCreditTenderRequest=new HjhDebtCreditTenderRequest();
					hjhDebtCreditTenderRequest.setCreditNid(creditNid);
					List<HjhDebtCreditTenderVO> list=amTradeClient.getHjhDebtCreditTenderList(hjhDebtCreditTenderRequest);
					//转让项目编号
					map.put("transferId", hjhDebtCredit.getCreditNid()+"");
					//状态编码
					map.put("transferStatus", "2");
					//成功转让本金金额(元)
					map.put("amount", hjhDebtCredit.getCreditCapitalAssigned().toString());
					//成功转让利息金额 (元)
					map.put("interest", hjhDebtCredit.getCreditInterestAssigned().toString());
					//成功转让浮动金额 (元)
					map.put("floatMoney", "0.00");
					if(hjhDebtCredit.getCreditCapitalAssigned().compareTo(hjhDebtCredit.getCreditCapital())==0){
						//状态更新时间
						map.put("productDate",  GetDate.getDateTimeMyTimeInMillis(list.get(list.size()-1).getCreateTime()));
					}else{
						//状态更新时间
						map.put("productDate", hjhDebtCredit.getEndTime()==0? GetDate.getDateTimeMyTimeInMillis(hjhDebtCredit.getUpdateTime()):GetDate.timestamptoStrYYYYMMDDHHMMSS(hjhDebtCredit.getEndTime()));
					}
				}
				break;

			default:
				break;
			}
		}
		return map;
	}

}

