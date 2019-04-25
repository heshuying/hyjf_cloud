package com.hyjf.cs.trade.service.consumer.impl.hgdatareport.cert.transferproject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.resquest.trade.HjhDebtCreditRequest;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.transferproject.CertTransferProjectMessageConsumer;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.transferproject.CertTransferProjectService;
import com.hyjf.cs.trade.service.consumer.impl.BaseHgCertReportServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pcc
 */

@Service
public class CertTransferProjectServiceImpl extends BaseHgCertReportServiceImpl implements CertTransferProjectService {
	@Autowired
	AmTradeClient amTradeClient;
	@Autowired
	AmUserClient amUserClient;
	@Autowired
	SystemConfig systemConfig;

	Logger logger = LoggerFactory.getLogger(CertTransferProjectServiceImpl.class);
	private String thisMessName = "转让项目信息上报";
	private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";

	@Override
	public JSONArray createDate(String creditNid,String flag) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if("1".equals(flag)){
			List<BorrowCreditVO> creditList=amTradeClient.getBorrowCreditListByCreditNid(creditNid);
			if(creditList!=null&&creditList.size()>0){
				BorrowCreditVO credit=creditList.get(0);
				BorrowAndInfoVO borrowAndInfoVO = amTradeClient.selectBorrowByNid(credit.getBidNid());
				UserInfoVO usersInfo=this.amUserClient.findUsersInfoById(credit.getCreditUserId());
				try {
					for (int i = 0; i < 1; i++) {
						Map<String, Object> param = new LinkedHashMap<String, Object>();
						//接口版本号
						param.put("version", CertCallConstant.CERT_CALL_VERSION);
						//平台编号
						param.put("sourceCode", systemConfig.getCertSourceCode());
						//转让编号
						param.put("transferId", credit.getCreditNid()+"");
						//存管银行债权编号
						param.put("bankNo", "-1");// 选填
						//债权来源
						param.put("fromType", "1");// 选填
						//债权项目编号或承接项目编码
						param.put("finClaimId", credit.getTenderNid());// 选填
						//投资人用户标示Hash
						param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
						//原散标编号
						param.put("sourceProductCode",credit.getBidNid());// 选填
						//计划转让利率
						param.put("transferInterestRate",borrowAndInfoVO.getBorrowApr().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));// 选填
						//计划转让本金(元)
						param.put("transferAmount", credit.getCreditCapital().toString());
						//浮动金额   0-(credit_capital*credit_discount/100)
						param.put("floatMoney", 
								BigDecimal.ZERO.subtract(credit.getCreditCapital().multiply(credit.getCreditDiscount().divide(new BigDecimal(100)))).toString());
						//转让项目发布的日期
						param.put("transferDate", GetDate.formatDate(credit.getCreateTime()));
						list.add(param);
					}
				} catch (Exception e) {
					// 错误时，以下日志必须出力（预警捕捉点）
					logger.error(logHeader , e);
				}
			}
		}else{
			HjhDebtCreditRequest request=new HjhDebtCreditRequest();
			request.setCreditNid(creditNid);
			List<HjhDebtCreditVO> hjhDebtCreditList=amTradeClient.getHjhDebtCreditListByCreditNid(creditNid);
			if(hjhDebtCreditList!=null&&hjhDebtCreditList.size()>0){
				HjhDebtCreditVO hjhDebtCredit=hjhDebtCreditList.get(0);
				BorrowAndInfoVO borrowAndInfoVO = amTradeClient.selectBorrowByNid(hjhDebtCredit.getBorrowNid());
				UserInfoVO usersInfo=this.amUserClient.findUsersInfoById(hjhDebtCredit.getUserId());
				try {
					for (int i = 0; i < 1; i++) {
						Map<String, Object> param = new LinkedHashMap<String, Object>();
						//接口版本号
						param.put("version", CertCallConstant.CERT_CALL_VERSION);
						//平台编号
						param.put("sourceCode", systemConfig.getCertSourceCode());
						//转让编号
						param.put("transferId", hjhDebtCredit.getCreditNid());
						//存管银行债权编号
						param.put("bankNo", "-1");// 选填
						//债权来源
						param.put("fromType", hjhDebtCredit.getSellOrderId().equals(hjhDebtCredit.getInvestOrderId())?"1":"2");// 选填
						//债权项目编号或承接项目编码
						param.put("finClaimId", hjhDebtCredit.getSellOrderId());// 选填
						//投资人用户标示Hash
						param.put("userIdcardHash", tool.idCardHash(usersInfo.getIdcard()));
						//原散标编号
						param.put("sourceProductCode",hjhDebtCredit.getBorrowNid());// 选填
						//计划转让利率
						param.put("transferInterestRate",borrowAndInfoVO.getBorrowApr().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));// 选填
						//计划转让本金(元)
						param.put("transferAmount", hjhDebtCredit.getCreditCapital().toString());
						//浮动金额
						param.put("floatMoney", "0");
						//转让项目发布的日期
						param.put("transferDate", GetDate.formatDate(hjhDebtCredit.getCreateTime()));
						list.add(param);
					}
				} catch (Exception e) {
					// 错误时，以下日志必须出力（预警捕捉点）
					logger.error(logHeader , e);
				}
			}
		}
		return JSONArray.parseArray(JSON.toJSONString(list));
	}

}

