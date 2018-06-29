package com.hyjf.cs.trade.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.*;
import com.hyjf.am.resquest.user.CertificateAuthorityRequest;
import com.hyjf.am.resquest.user.LoanSubjectCertificateAuthorityRequest;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.user.CertificateAuthorityVO;
import com.hyjf.am.vo.user.LoanSubjectCertificateAuthorityVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.FddGenerateContractConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.CreditAssignedBean;
import com.hyjf.cs.trade.bean.fdd.FddDessenesitizationBean;
import com.hyjf.cs.trade.bean.fdd.FddGenerateContractBean;
import com.hyjf.cs.trade.client.*;
import com.hyjf.cs.trade.mq.FddProducer;
import com.hyjf.cs.trade.mq.Producer;
import com.hyjf.pay.lib.fadada.bean.DzqzCallBean;
import com.hyjf.pay.lib.fadada.util.DzqzCallUtil;
import com.hyjf.pay.lib.fadada.util.DzqzConstant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 法大大 handle
 * 
 * @author jun
 * @date 20180627
 */
@Component
public class FddHandle {

	private static final Logger logger = LoggerFactory.getLogger(FddHandle.class);

	@Autowired
	private AmBorrowClient amBorrowClient;
	@Autowired
	private AmUserClient amUserClient;
	@Autowired
	private BorrowManinfoClient borrowManinfoClient;
	@Autowired
	private BorrowUserClient borrowUserClient;
	@Autowired
	private BorrowTenderClient borrowTenderClient;
	@Autowired
	private AssetManageClient assetManageClient;
	@Autowired
	private BankCreditTenderClient bankCreditTenderClient;
	@Autowired
	private BorrowCreditClient borrowCreditClient;
	@Autowired
	private HjhDebtCreditClient hjhDebtCreditClient;
	@Autowired
	private HjhDebtCreditTenderClient hjhDebtCreditTenderClient;

	@Value("${hyjf.pay.fdd.nofify.url}")
	private String HYJF_PAY_FDD_NOTIFY_URL;

	@Value("${hyjf.fdd.customerid}")
	private String FDD_HYJF_CUSTOMERID;

	@Autowired
	private FddProducer fddProducer;
	
	/**
	 * 散标投资
	 * 
	 * @param bean
	 */
	public void tenderGenerateContract(FddGenerateContractBean bean) throws Exception {
		logger.info("--------------开始生成居间投资协议----订单号：" + bean.getOrdid());
		// 投资人用户ID
		Integer tenderUserId = bean.getTenderUserId();
		if (tenderUserId == null || tenderUserId == 0) {
			throw new Exception("投资人用户ID为空.");
		}
		// 投资人代收利息
		BigDecimal tenderInterest = bean.getTenderInterest();
		if (tenderInterest == null) {
			throw new Exception("投资人代收利息为空.");
		}
		// 投资订单号
		String tenderNid = bean.getOrdid();
		if (StringUtils.isBlank(tenderNid)) {
			throw new Exception("投资订单号为空.");
		}
		// 标的编号
		String borrowNid = bean.getBorrowNid();
		if (StringUtils.isBlank(borrowNid)) {
			throw new Exception("标的编号为空.");
		}
		// 借款详情
		BorrowVO borrow = this.amBorrowClient.getBorrowByNid(borrowNid);
		if (borrow == null) {
			throw new Exception("根据标的编号检索借款详情失败,借款编号:[" + borrowNid + "].");
		}
		String planNid = borrow.getPlanNid();
		// 借款方真实姓名或企业名称
		String borrowTrueName = null;
		// 借款方CA认证客户编号
		String borrowerCustomerID = null;
		// 借款人证件号码
		String borrowIdCard = null;
		// del by liuyang 20180326 借款人信息 借款主体为准
		// modify by cwyang 20180320 散标情况下进行修改
		if (StringUtils.isNotBlank(planNid)) {
			// 借款人用户ID
			Integer borrowUserId = borrow.getUserId();
			// 借款人用户信息
			UserVO borrowUser = this.amUserClient.findUserById(borrowUserId);
			if (borrowUser == null) {
				throw new Exception("根据借款人用户ID,查询借款人信息失败,借款人用户ID:[" + borrowUserId + "].");
			}

			// 借款人用户详情信息
			UserInfoVO borrowUserInfo = this.amUserClient.findUsersInfoById(borrowUserId);
			if (borrowUserInfo == null) {
				throw new Exception("根据借款人用户ID,查询借款人详情信息失败,借款人用户ID:[" + borrowUserId + "].");
			}

			// 如果是企业用户
			if (borrowUser.getUserType() == 1) {
				CorpOpenAccountRecordVO corpOpenAccountRecord = this.amUserClient
						.selectCorpOpenAccountRecordByUserId(borrowUserId);
				borrowTrueName = corpOpenAccountRecord.getBusiName();
				borrowIdCard = corpOpenAccountRecord.getBusiCode();
			} else {
				borrowTrueName = borrowUserInfo.getTruename();
				borrowIdCard = borrowUserInfo.getIdcard();
			}
			CertificateAuthorityVO certificateAuthorityVO = this.amUserClient
					.selectCertificateAuthorityByUserId(String.valueOf(borrowUserId));
			if (Validator.isNotNull(certificateAuthorityVO)) {
				borrowerCustomerID = certificateAuthorityVO.getCustomerId();
			}
		} else {
			if ("1".equals(borrow.getCompanyOrPersonal())) {
				// 借款主体为企业借款
				BorrowUserVO borrowUsers = this.borrowUserClient.getBorrowUser(borrowNid);
				if (borrowUsers == null) {
					throw new Exception("根据标的编号查询借款主体为企业借款的相关信息失败,标的编号:[" + borrowNid + "]");
				}
				borrowTrueName = borrowUsers.getUsername();
				borrowIdCard = borrowUsers.getSocialCreditCode();
				// 获取CA认证客户编号
				borrowerCustomerID = this.getCACustomerID(borrowUsers);
				if (StringUtils.isBlank(borrowerCustomerID)) {
					throw new Exception("企业借款获取CA认证客户编号失败,企业名称:[" + borrowUsers.getUsername() + "],社会统一信用代码:["
							+ borrowUsers.getSocialCreditCode() + "].");
				}
			} else if ("2".equals(borrow.getCompanyOrPersonal())) {
				// 借款主体为个人借款
				BorrowManinfoVO borrowManinfo = this.borrowManinfoClient.getBorrowManinfo(borrowNid);
				if (borrowManinfo == null) {
					throw new Exception("借款主体为个人借款时,获取个人借款信息失败,标的编号:[" + borrowNid + "].");
				}
				borrowTrueName = borrowManinfo.getName();
				borrowIdCard = borrowManinfo.getCardNo();
				// 获取CA认证客户编号
				borrowerCustomerID = this.getPersonCACustomerID(borrowManinfo);
				if (StringUtils.isBlank(borrowerCustomerID)) {
					throw new Exception("获取个人借款CA认证客户编号失败,姓名:[" + borrowManinfo.getName() + "],身份证号:["
							+ borrowManinfo.getCardNo() + "].");
				}
			}
		}
		// del by liuyang 20180326 借款人信息 借款主体为准 end
		// 投资人用户信息
		UserVO tenderUser = this.amUserClient.findUserById(tenderUserId);
		if (tenderUser == null) {
			throw new Exception("根据投资人用户ID,查询投资人信息失败,投资人用户ID:[" + tenderUserId + "].");
		}
		// 投资人用户详情信息
		UserInfoVO tenderUsersInfo = this.amUserClient.findUsersInfoById(tenderUserId);
		if (tenderUsersInfo == null) {
			throw new Exception("根据投资人用户ID,查询投资人详情信息失败,投资人用户ID:[" + tenderUserId + "].");
		}
		// 查询投资记录
		BorrowTenderRequest btRequest = new BorrowTenderRequest();
		btRequest.setTenderUserId(tenderUserId);
		btRequest.setBorrowNid(borrowNid);
		btRequest.setTenderNid(tenderNid);
		BorrowTenderVO borrowTender = this.borrowTenderClient.selectBorrowTender(btRequest);
		if (borrowTender == null) {
			throw new Exception(
					"投资记录不存在,投资订单号:[" + tenderNid + "],投资人用户ID:[" + tenderUserId + "],标的编号:[" + borrowNid + "].");
		}

		String tenderTrueName = null;
		String tenderIdCard = null;
		tenderTrueName = tenderUsersInfo.getTruename();
		tenderIdCard = tenderUsersInfo.getIdcard();
		if (tenderUser.getUserType() == 1) {
			CorpOpenAccountRecordVO info = this.amUserClient.selectCorpOpenAccountRecordByUserId(tenderUserId);
			tenderTrueName = info.getBusiName();
			tenderIdCard = info.getBusiCode();
		}

		/** 标的基本数据 */
		String borrowStyle = borrow.getBorrowStyle();// 还款方式
		Integer borrowPeriod = borrow.getBorrowPeriod();
		String borrowDate = "";
		// 是否月标(true:月标, false:天标)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_END.equals(borrowStyle);
		if (isMonth) {// 月表
			borrowDate = borrowPeriod + "个月";
		} else {
			borrowDate = borrowPeriod + "天";
		}
		JSONObject paramter = new JSONObject();
		paramter.put("nid", borrowTender.getNid());// 借款编号
		paramter.put("recoverTime", bean.getSignDate());// 签署时间
		paramter.put("realName", tenderTrueName);// 投资人真实姓名
		paramter.put("idCard", tenderIdCard);// 证件号码
		paramter.put("borrowUsername", borrowTrueName);// 借款人真实姓名
		paramter.put("BorrowidCard", borrowIdCard);// 借款人证件号码

		paramter.put("account", borrow.getAccount().toString());// 借款金额
		paramter.put("borrowPeriod", borrowDate);// 借款期限
		paramter.put("borrowApr", borrow.getBorrowApr() + "%");// 借款利率
		paramter.put("borrowStyleName", this.getBorrowStyle(borrow.getBorrowStyle()));// 还款方式
		paramter.put("userInvestAccount", borrowTender.getAccount().toString());// 投资人出借金额
		paramter.put("ecoverAccountInterest", tenderInterest.toString());// 借款人预期收益

		bean.setBorrowerCustomerID(borrowerCustomerID);
		bean.setContractName(FddGenerateContractConstant.CONTRACT_DOC_TITLE);

		boolean isSign = this.isCreatContract(borrowTender.getNid());
		if (isSign) {// 单独走签署接口
			this.updateSignContract(bean);
		} else {
			String paramStr = paramter.toJSONString();

            List<FddTempletVO> fddTemplets = this.borrowTenderClient.getFddTempletList(FddGenerateContractConstant.PROTOCOL_TYPE_TENDER);
            if (fddTemplets != null && fddTemplets.size() == 1) {
                FddTempletVO fddTemplet = fddTemplets.get(0);
                String templetId = fddTemplet.getTempletId();
                DzqzCallBean callBean = new DzqzCallBean();
                callBean.setParameter_map(paramStr);
                callBean.setTemplate_id(templetId);
                callBean.setTxCode(DzqzConstant.FDD_GENERATECONTRACT);
                callBean.setContract_id(bean.getOrdid());
                callBean.setDoc_title(FddGenerateContractConstant.CONTRACT_DOC_TITLE);
                callBean.setDynamic_tables(null);
                callBean.setUserId(tenderUserId);
                callBean.setFont_size("12");
                callBean.setFont_type("1");
                DzqzCallBean dzqzCallBean = DzqzCallUtil.callApiBg(callBean);
                String result = dzqzCallBean.getResult();
                String code = dzqzCallBean.getCode();
                dzqzCallBean.setTemplate_id(templetId);
                if ("success".equals(result) && FddGenerateContractConstant.FDD_RETURN_CODE_1000.equals(code)) {
                    //协议生成成功，开始签署并进行脱敏处理
                    updateSaveDownUrl(dzqzCallBean, bean);
                    updateSignContract(bean);
                } else {
                    logger.info("--------------开始生成居间投资协议返回错误，订单号：" + bean.getOrdid() + "错误码：" + code + ",错误描述：" + dzqzCallBean.getMsg());
                }
            } else {
                logger.info("--------------开始生成居间投资协议异常，无法匹配模板----订单号：" + bean.getOrdid());
            }
        }

	}

	 /**
     * 存储下载地址和观看地址
     * @param dzqzCallBean 法大大返回结果
     *
     * @param bean         存储需要的参数
     */
	private void updateSaveDownUrl(DzqzCallBean dzqzCallBean, FddGenerateContractBean bean) {

        TenderAgreementVO info = new TenderAgreementVO();
        int nowTime = GetDate.getNowTime10();
        info.setUserId(bean.getTenderUserId());
        info.setUserName(bean.getTenderUserName());
        info.setBorrowNid(bean.getBorrowNid());
        info.setTenderType(bean.getTenderType());
        info.setTenderNid(bean.getOrdid());
        info.setStatus(1);//生成成功
        info.setContractNumber(bean.getOrdid());
        info.setTempletId(dzqzCallBean.getTemplate_id());
        info.setContractCreateTime(nowTime);
        info.setContractName(bean.getContractName());
        info.setCreateTime(GetDate.getDate(nowTime));
        info.setCreateUserId(bean.getTenderUserId());
        info.setCreateUserName(bean.getTenderUserName());
        int flag = this.borrowTenderClient.saveTenderAgreement(info);
        if (flag == 0) {
            logger.info("--------------存储居间协议失败-订单号：" + bean.getOrdid());
        }

    }

	/**
	 * 签署合同
	 * 
	 * @param bean
	 */
	private void updateSignContract(FddGenerateContractBean bean) {
		// 协议类型
		int transType = bean.getTransType();

		String txCode = DzqzConstant.FDD_EXTSIGN_AUTO;

		int tenderUserId = bean.getTenderUserId();
		String customerId = null;
		CertificateAuthorityVO authority = this.amUserClient
				.selectCertificateAuthorityByUserId(String.valueOf(tenderUserId));
		if (Validator.isNotNull(authority)) {
			customerId = authority.getCustomerId();
		}

		String payurl = HYJF_PAY_FDD_NOTIFY_URL;
		String notifyUrl = payurl + DzqzConstant.REQUEST_MAPPING_CALLAPI_SIGNNODIFY + "?transType=" + transType;
		// 投资人签署
		DzqzCallBean callBean = new DzqzCallBean();
		callBean.setContract_id(bean.getOrdid());
		callBean.setCustomer_id(customerId);
		callBean.setClient_role("3");
		callBean.setTxCode(txCode);
		callBean.setUserId(tenderUserId);
		callBean.setSign_keyword(FddGenerateContractConstant.FDD_SIGN_KEYWORK_TENDER);
		// 计划加入协议
		if (FddGenerateContractConstant.PROTOCOL_TYPE_PLAN == transType) {
			callBean.setDoc_title(FddGenerateContractConstant.CONTRACT_DOC_TITLE_PLAN);
		} else if (FddGenerateContractConstant.PROTOCOL_TYPE_TENDER == transType) {// 居间服务协议
			callBean.setDoc_title(FddGenerateContractConstant.CONTRACT_DOC_TITLE);
		} else if (FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT == transType
				|| FddGenerateContractConstant.FDD_TRANSTYPE_PLAN_CRIDET == transType) {// 债转协议
			callBean.setDoc_title(FddGenerateContractConstant.CONTRACT_DOC_TITLE_CREDIT);
			callBean.setSign_keyword(FddGenerateContractConstant.FDD_SIGN_KEYWORK_BORROWER);
		}

		callBean.setKeywordStrategy("0");
		callBean.setNotify_url(notifyUrl);
		DzqzCallBean dzqzCallBean = DzqzCallUtil.callApiBg(callBean);
		String result = dzqzCallBean.getResult();
		String code = dzqzCallBean.getCode();
		if ("success".equals(result) && FddGenerateContractConstant.FDD_RETURN_CODE_1000.equals(code)) {
			if (FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT == transType
					|| FddGenerateContractConstant.FDD_TRANSTYPE_PLAN_CRIDET == transType) {// 债权转让协议
				// 出让人签章
				Integer creditUserID = bean.getCreditUserID();
				String creditCustomerID = this.getCustomerIDByUserID(creditUserID);
				callBean.setCustomer_id(creditCustomerID);
				callBean.setClient_role("4");
				callBean.setSign_keyword(FddGenerateContractConstant.FDD_SIGN_KEYWORK_TENDER);
				callBean.setNotify_url(notifyUrl);
				DzqzCallUtil.callApiBg(callBean);
			} else {
				// 接入平台签署
				String platFromCustomerId = FDD_HYJF_CUSTOMERID;
				callBean.setCustomer_id(platFromCustomerId);
				callBean.setClient_role("1");
				if (FddGenerateContractConstant.PROTOCOL_TYPE_PLAN == transType) {
					callBean.setSign_keyword(FddGenerateContractConstant.FDD_SIGN_KEYWORK_BORROWER);
				} else {
					callBean.setSign_keyword(FddGenerateContractConstant.FDD_SIGN_KEYWORK_PLATFORM);
				}
				callBean.setNotify_url(notifyUrl);
				DzqzCallBean resultBean = DzqzCallUtil.callApiBg(callBean);
				String result2 = resultBean.getResult();
				String code2 = resultBean.getCode();
				if ("success".equals(result2) && FddGenerateContractConstant.FDD_RETURN_CODE_1000.equals(code2)) {
					if (FddGenerateContractConstant.PROTOCOL_TYPE_TENDER == transType) {// 居间服务协议
						// 借款人签署
						callBean.setCustomer_id(bean.getBorrowerCustomerID());
						callBean.setClient_role("4");
						callBean.setSign_keyword(FddGenerateContractConstant.FDD_SIGN_KEYWORK_BORROWER);
						callBean.setNotify_url(notifyUrl);
						DzqzCallUtil.callApiBg(callBean);
					}
				}
			}
		} else {
			logger.info(
					"----------------签署异常，订单号：" + bean.getOrdid() + ",返回值：" + code + ",返回描述：" + dzqzCallBean.getMsg());
		}
		// 签署结果由异步通知进行处理
	}

	/**
	 * 通过userID获得CA认证的客户ID
	 * 
	 * @param
	 * @return
	 */
	private String getCustomerIDByUserID(Integer userId) {
		if (userId == null) {
			return null;
		}
		String code = "1000";
		String customerID = this.amUserClient.getCustomerIDByUserID(userId, code);
		return customerID;
	}

	/**
	 * 判断是否生成合同
	 * 
	 * @param nid
	 * @return
	 */
	private boolean isCreatContract(String nid) {
		List<TenderAgreementVO> tenderAgreementList = assetManageClient.selectTenderAgreementByNid(nid);
		if (CollectionUtils.isNotEmpty(tenderAgreementList)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取还款方式
	 * 
	 * @param borrowStyle
	 * @return
	 */
	private String getBorrowStyle(String borrowStyle) {
		BorrowStyleVO borrowStyleVO = this.amBorrowClient.getBorrowStyle(borrowStyle);
		if (Validator.isNotNull(borrowStyleVO)) {
			return borrowStyleVO.getName();
		} else {
			return "";
		}
	}

	/**
	 * 获取借款主体为个人的CA认证客户编号
	 * 
	 * @param borrowManinfo
	 * @return
	 */
	private String getPersonCACustomerID(BorrowManinfoVO borrowManinfo) {
		// 用户CA认证记录表
		CertificateAuthorityRequest request = new CertificateAuthorityRequest();
		request.setTrueName(borrowManinfo.getName());
		request.setIdNo(borrowManinfo.getCardNo());
		request.setIdType(0);
		List<CertificateAuthorityVO> list = this.amUserClient.getCertificateAuthorityList(request);
		if (list != null && list.size() > 0) {
			return list.get(0).getCustomerId();
		}

		// 借款主体CA认证记录表
		LoanSubjectCertificateAuthorityRequest request1 = new LoanSubjectCertificateAuthorityRequest();
		request1.setName(borrowManinfo.getName());
		request1.setIdType(0);
		request1.setIdNo(borrowManinfo.getCardNo());
		List<LoanSubjectCertificateAuthorityVO> resultList = this.amUserClient
				.getLoanSubjectCertificateAuthorityList(request1);

		if (resultList != null && resultList.size() > 0) {
			return resultList.get(0).getCustomerId();
		}
		return null;
	}

	/**
	 * 获取CA认证客户编号
	 * 
	 * @param borrowUsers
	 * @return
	 */
	private String getCACustomerID(BorrowUserVO borrowUsers) {
		CertificateAuthorityRequest request = new CertificateAuthorityRequest();
		request.setTrueName(borrowUsers.getUsername());
		request.setIdNo(borrowUsers.getSocialCreditCode());
		request.setIdType(1);
		List<CertificateAuthorityVO> list = this.amUserClient.getCertificateAuthorityList(request);
		if (list != null && list.size() > 0) {
			return list.get(0).getCustomerId();
		}
		// 借款主体CA认证记录表
		LoanSubjectCertificateAuthorityRequest request1 = new LoanSubjectCertificateAuthorityRequest();
		request1.setName(borrowUsers.getUsername());
		request1.setIdType(1);
		request1.setIdNo(borrowUsers.getSocialCreditCode());
		List<LoanSubjectCertificateAuthorityVO> resultList = this.amUserClient
				.getLoanSubjectCertificateAuthorityList(request1);

		if (resultList != null && resultList.size() > 0) {
			return resultList.get(0).getCustomerId();
		}
		return null;
	}

	
	
	/**
     * 保存签署成功后的数据
     * @param platerCallBean
     * @param contract_id
     * @param borrowNid
     * @param transType
     * @param instCode
     * @param isCreditCompany
     */
    private void updateSaveSignInfo(DzqzCallBean platerCallBean, String contract_id, String borrowNid, Integer transType, String instCode, boolean isTenderCompany, boolean isCreditCompany) {
        String download_url = platerCallBean.getDownload_url();
        String viewpdf_url = platerCallBean.getViewpdf_url();
        List<TenderAgreementVO> agreements = this.assetManageClient.getTenderAgreementListByTenderNidAndStatusNot2(contract_id);
        if (agreements != null && agreements.size() > 0) {
        	TenderAgreementVO tenderAgreement = agreements.get(0);
        	int nowTime = GetDate.getNowTime10();
            tenderAgreement.setContractSignTime(nowTime);
            tenderAgreement.setDownloadUrl(download_url);
            tenderAgreement.setViewpdfUrl(viewpdf_url);
            tenderAgreement.setUpdateTime(GetDate.getDate(nowTime));
            tenderAgreement.setUpdateUserId(tenderAgreement.getCreateUserId());
            tenderAgreement.setUpdateUserName(tenderAgreement.getUserName());
            tenderAgreement.setStatus(2);//签署成功
            int update = this.borrowTenderClient.updateTenderAgreement(tenderAgreement);
            if (update > 0) {
                String savePath = null;
                String path = "/pdf_tem/";
                String ftpPath = null;
                if (FddGenerateContractConstant.PROTOCOL_TYPE_TENDER == transType || FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT == transType
                        || FddGenerateContractConstant.FDD_TRANSTYPE_PLAN_CRIDET == transType){
                    savePath = path + "pdf/" + borrowNid ;
                    ftpPath = "PDF/" + instCode + "/" + borrowNid + "/" + contract_id + "/";
                }else if(FddGenerateContractConstant.PROTOCOL_TYPE_PLAN == transType){
                    savePath = path + "pdf/" + instCode ;
                    ftpPath = "PDF/" + instCode  + "/" + contract_id + "/";
                }
                //下载协议并脱敏
                FddDessenesitizationBean bean = new FddDessenesitizationBean();
                bean.setAgrementID(tenderAgreement.getId().toString());
                bean.setSavePath(savePath);
                bean.setTransType(transType.toString());
                bean.setFtpPath(ftpPath);
                bean.setDownloadUrl(download_url);
                bean.setOrdid(contract_id);
                bean.setTenderCompany(isTenderCompany);
                bean.setCreditCompany(isCreditCompany);
                try {
					fddProducer.messageSend(new Producer.MassageContent(MQConstant.FDD_TOPIC,MQConstant.FDD_DOWNPDF_AND_DESSENSITIZATION_TAG,JSON.toJSONBytes(bean)));
				} catch (MQException e) {
					e.printStackTrace();
					logger.error("法大大发送下载脱敏消息失败...", e);	
				}
            }
        }
    }

	
	
	/**
	 * 计划加入
	 * 
	 * @param bean
	 */
	public void planJoinGenerateContract(FddGenerateContractBean bean) throws Exception {
		logger.info("--------------开始生成计划加入协议----计划加入订单号：" + bean.getOrdid());
		 if(StringUtils.isBlank(bean.getOrdid())){
	            throw new RuntimeException("-------参数ordid不得为空！------");
	        }

	        if(bean.getTenderUserId() == 0){
	            throw new RuntimeException("-------参数TenderUserId不得为空！------");
	        }

	        if(StringUtils.isBlank(bean.getPlanStartDate())){
	            throw new RuntimeException("-------参数PlanStartDate不得为空！------");
	        }

	        if(StringUtils.isBlank(bean.getPlanEndDate())){
	            throw new RuntimeException("-------参数PlanEndDate不得为空！------");
	        }

	        UserInfoVO userInfo=this.amUserClient.findUsersInfoById(bean.getTenderUserId());
	        UserVO users = this.amUserClient.findUserById(bean.getTenderUserId());
	        String tenderTrueName = null;
	        String tenderIdCard = null;
	        tenderTrueName = userInfo.getTruename();
	        tenderIdCard = userInfo.getIdcard();
	        if(users.getUserType() == 1){
	        	CorpOpenAccountRecordVO info = this.amUserClient.selectCorpOpenAccountRecordByUserId(bean.getTenderUserId());
	            tenderTrueName = info.getBusiName();
	            tenderIdCard = info.getBusiCode();
	        }

	        Map<String ,Object> params=new HashMap<String ,Object>();
	        params.put("accedeOrderId", bean.getOrdid());
	        params.put("userId", bean.getTenderUserId());
	        UserHjhInvistDetailCustomizeVO userHjhInvistDetailCustomize = this.selectUserHjhInvistDetail(params);
	        bean.setOrdid(bean.getOrdid());
	        bean.setTransType(FddGenerateContractConstant.PROTOCOL_TYPE_PLAN);
	        bean.setTenderTrueName(tenderTrueName);
	        bean.setIdCard(tenderIdCard);
	        bean.setTenderUserId(bean.getTenderUserId());
	        bean.setTenderUserName(users.getUsername());
	        bean.setTenderAccountFMT(userHjhInvistDetailCustomize.getAccedeAccount());
	        bean.setBorrowDate(userHjhInvistDetailCustomize.getPlanPeriod());//计划期限
	        bean.setBorrowRate(userHjhInvistDetailCustomize.getPlanApr());//计划收益率
	        bean.setSignDate(GetDate.getDataString(GetDate.date_sdf));//签署日期
		
	        JSONObject paramter = new JSONObject();
	        paramter.put("accedeOrderId", bean.getOrdid());//合同编号
	        paramter.put("addTime",bean.getSignDate());//签署时间
	        paramter.put("truename", bean.getTenderTrueName());//投资人真实姓名
	        paramter.put("idcard",bean.getIdCard());//证件号码
//	        paramter.put("username", bean.getTenderUserName());//投资人用户名
	        paramter.put("accedeAccount", bean.getTenderAccountFMT());//投资人加入金额
	        paramter.put("planPeriod",bean.getBorrowDate());//计划期限
	        paramter.put("planApr", bean.getBorrowRate());//计划收益率
	        paramter.put("countInterestTime",bean.getPlanStartDate());//计划生效日期
	        paramter.put("quitTime",bean.getPlanEndDate());//计划到期日期
	        paramter.put("shouyifutou",FddGenerateContractConstant.SHOUYI_FUTOU);//收益处理方式
	        paramter.put("shouldPayTotal", bean.getTenderInterestFmt());//计划本金及预期收益
	        
	        String paramStr = paramter.toJSONString();
	        bean.setContractName(FddGenerateContractConstant.CONTRACT_DOC_TITLE_PLAN);
	        
	        boolean isSign = isCreatContract(bean.getOrdid());
	        if (isSign){//单独走签署接口
	            updateSignContract(bean);
	        }else {  
	            List<FddTempletVO> fddTemplets = this.borrowTenderClient.getFddTempletList(FddGenerateContractConstant.PROTOCOL_TYPE_PLAN);
	            if (fddTemplets != null && fddTemplets.size() == 1) {
	            	FddTempletVO fddTemplet = fddTemplets.get(0);
	                String templetId = fddTemplet.getTempletId();
	                DzqzCallBean callBean = new DzqzCallBean();
	                callBean.setParameter_map(paramStr);
	                callBean.setTemplate_id(templetId);
	                callBean.setTxCode(DzqzConstant.FDD_GENERATECONTRACT);
	                callBean.setContract_id(bean.getOrdid());
	                callBean.setDoc_title(FddGenerateContractConstant.CONTRACT_DOC_TITLE_PLAN);
	                callBean.setDynamic_tables(null);
	                callBean.setUserId(bean.getTenderUserId());
	                callBean.setFont_size("12");
	                callBean.setFont_type("1");
	                DzqzCallBean dzqzCallBean = DzqzCallUtil.callApiBg(callBean);
	                String result = dzqzCallBean.getResult();
	                String code = dzqzCallBean.getCode();
	                dzqzCallBean.setTemplate_id(templetId);
	                if ("success".equals(result) && FddGenerateContractConstant.FDD_RETURN_CODE_1000.equals(code)) {
	                    //协议生成成功，开始签署并进行脱敏处理
	                    updateSaveDownUrl(dzqzCallBean, bean);
	                    updateSignContract(bean);
	                } else {
	                    logger.info("--------------开始生成居间投资协议返回错误，订单号：" + bean.getOrdid() + "错误码：" + code + ",错误描述：" + dzqzCallBean.getMsg());
	                }
	            } else {
	                logger.info("--------------开始生成居间投资协议异常，无法匹配模板----订单号：" + bean.getOrdid());
	            }
	        }
	        
	        
	}

	
	/**
	 * 会计划投资详情
	 * @param params
	 * @return
	 */
	private UserHjhInvistDetailCustomizeVO selectUserHjhInvistDetail(Map<String, Object> params) {
		return this.amBorrowClient.selectUserHjhInvistDetail(params);
	}

	/**
	 * 债转投资
	 * 
	 * @param bean
	 */
	public void creditGenerateContract(FddGenerateContractBean bean) throws Exception {
        JSONObject paramter = new JSONObject();
        // 承接订单号
        String assignOrderId = bean.getAssignOrderId();
        if (StringUtils.isBlank(assignOrderId)) {
            logger.info("债转承接订单号为空");
            throw new Exception("债转承接订单号为空");
        }
        // 承接人用户ID
        Integer tenderUserId = bean.getTenderUserId();
        if (tenderUserId == null || tenderUserId == 0) {
            throw new Exception("承接用户ID为空");
        }
        // 标的编号
        String borrowNid = bean.getBorrowNid();
        if (StringUtils.isBlank(borrowNid)) {
            throw new Exception("借款编号为空");
        }
        // 原始投资订单号
        String creditTenderNid = bean.getCreditTenderNid();
        if (StringUtils.isBlank(creditTenderNid)) {
            throw new Exception("原始投资订单号");
        }
        // 债转编号
        String creditNid = bean.getCreditNid();
        if (StringUtils.isBlank(creditNid)) {
            throw new Exception("债转编号为空");
        }

        // 获取债转投资信息
        CreditTenderRequest request = new CreditTenderRequest();
        request.setAssignNid(assignOrderId);
        request.setBidNid(borrowNid);
        request.setCreditNid(creditNid);
        request.setCreditTenderNid(creditTenderNid);
        List<CreditTenderVO> creditTenderList = this.bankCreditTenderClient.getCreditTenderList(request);
        if (creditTenderList != null && creditTenderList.size() > 0) {

            CreditTenderVO creditTender = creditTenderList.get(0);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("creditNid", creditTender.getCreditNid());
            params.put("assignNid", creditTender.getAssignNid());
			List<TenderToCreditDetailCustomizeVO> tenderToCreditDetailList = this.bankCreditTenderClient.selectWebCreditTenderDetailForContract(params);
            if (tenderToCreditDetailList != null && tenderToCreditDetailList.size() > 0) {
                if (tenderToCreditDetailList.get(0).getCreditRepayEndTime() != null) {
                    tenderToCreditDetailList.get(0).setCreditRepayEndTime(GetDate.getDateMyTimeInMillis(Integer.parseInt(tenderToCreditDetailList.get(0).getCreditRepayEndTime())));
                }
                if (tenderToCreditDetailList.get(0).getCreditTime() != null) {
                    try {
                        tenderToCreditDetailList.get(0).setCreditTime(GetDate.formatDate(GetDate.parseDate(tenderToCreditDetailList.get(0).getCreditTime(), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                // 债转期限
                paramter.put("creditTerm", tenderToCreditDetailList.get(0).getCreditTerm());
                // 债转时间
                paramter.put("creditTime", tenderToCreditDetailList.get(0).getCreditTime());
                // 转让债权本金
                paramter.put("assignCapital", creditTender.getAssignCapital().toString());
                //转让价款
                paramter.put("assignPay", creditTender.getAssignPay().toString());
                // 签署时间
                paramter.put("addTime", tenderToCreditDetailList.get(0).getSignTime());
            }


            // 获取借款标的信息
            BorrowVO borrow = this.amBorrowClient.getBorrowByNid(borrowNid);
            if (borrow == null) {
                logger.info("根据标的编号获取标的信息为空,标的编号:" + borrowNid + "].");
                throw new Exception("根据标的编号获取标的信息为空,标的编号:" + borrowNid + "].");
            }

            // 借款人用户ID
            Integer borrowUserId = borrow.getUserId();

            // 获取债转信息
			BorrowCreditRequest request1 = new BorrowCreditRequest();
			request1.setCreditNid(creditNid);
			request1.setBidNid(borrowNid);
			request1.setTenderNid(creditTenderNid);

			List<BorrowCreditVO> borrowCredit=this.borrowCreditClient.getBorrowCreditList(request1);

            if (borrowCredit == null || borrowCredit.size() != 1) {
                throw new Exception("根据债转编号查询债转信息失败,债转编号:[" + creditNid + "].");
            }
            // 出让人用户ID
            Integer creditUserId = creditTender.getCreditUserId();

            // 获取承接人平台信息
            UserVO tenderUser = this.amUserClient.findUserById(tenderUserId);
            if (tenderUser == null) {
                throw new Exception("根据用户ID查询用户信息失败, 承接人用户ID:[" + tenderUserId + "]");
            }
            // 获取承接人身份信息
            UserInfoVO tenderUserInfo = this.amUserClient.findUsersInfoById(tenderUserId);
            if (tenderUserInfo == null) {
                throw new Exception("根据用户ID查询用户详情信息失败, 承接人用户ID:[" + tenderUserId + "]");
            }
            // 获取融资方平台信息
            UserVO borrowUsers =this.amUserClient.findUserById(borrowUserId);
            if (borrowUsers == null) {
                throw new Exception("根据借款人用户ID,查询借款人用户信息失败,借款人用户ID:[" + borrowUserId + "].");
            }
            // 获取借款人信息
            UserInfoVO borrowUsersInfo=this.amUserClient.findUsersInfoById(borrowUserId);
            if (borrowUsersInfo == null) {
                throw new Exception("根据借款人用户ID,查询借款人用户详情信息失败,借款人用户ID:[" + borrowUserId + "].");
            }
            // 获取出让人平台信息
            UserVO creditUser =this.amUserClient.findUserById(creditUserId);
            if (creditUser == null) {
                throw new Exception("根据出让人用户ID,查询出让人用户信息失败,出让人用户ID:[" + creditUserId + "].");
            }
            // 获取债转人身份信息
            UserInfoVO creditUsersInfo = this.amUserClient.findUsersInfoById(creditUserId);
            if (creditUsersInfo == null) {
                throw new Exception("根据出让人用户ID,查询出让人用户信息详情失败,出让人用户ID:[" + creditUserId + "].");
            }

            if (borrow.getReverifyTime() != null) {
                borrow.setReverifyTime(GetDate.getDateMyTimeInMillis(Integer.parseInt(borrow.getReverifyTime())));
            }
            if (borrow.getRepayLastTime() != null) {
                borrow.setRepayLastTime(GetDate.getDateMyTimeInMillis(Integer.parseInt(borrow.getRepayLastTime())));
            }

            /** 标的基本数据 */
            String borrowStyle = borrow.getBorrowStyle();// 还款方式
            Integer borrowPeriod = borrow.getBorrowPeriod();
            String borrowDate = "";
            Object creditTerm = paramter.get("creditTerm");
            // 是否月标(true:月标, false:天标)
            boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                    || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_END.equals(borrowStyle);
            if(isMonth){//月表
                borrowDate = borrowPeriod + "个月";
            }else{
                borrowDate = borrowPeriod + "天";
            }
            creditTerm = creditTerm + "天";

            paramter.put("creditTerm",creditTerm);

            // 标的编号
            paramter.put("borrowNid", borrow.getBorrowNid());
            //编号
            paramter.put("NID", assignOrderId);
            //借款本金总额
            paramter.put("borrowAccount", borrow.getAccount().toString());
            // 借款利率
            paramter.put("borrowApr", borrow.getBorrowApr() + "%");
            // 还款方式
            paramter.put("borrowStyle", this.getBorrowStyle(borrow.getBorrowStyle()));
            // 借款期限
            paramter.put("borrowPeriod", borrowDate);

            // 出让人相关 start
            // 出让人用户真实姓名
            paramter.put("CreditTruename", creditUsersInfo.getTruename());
            // 出让人身份证号
            paramter.put("CreditIdcard", creditUsersInfo.getIdcard());
            // 出让人用户名
//            paramter.put("CreditUsername", creditUser.getUsername());
            // 出让人相关 end

            String tenderTrueName = null;
            String tenderIdCard = null;
            tenderTrueName = tenderUserInfo.getTruename();
            tenderIdCard = tenderUserInfo.getIdcard();
            if(tenderUser.getUserType() == 1){
                CorpOpenAccountRecordVO info = this.getCorpOpenAccountInfoByUserID(bean.getTenderUserId());
                if (Validator.isNotNull(info)){
					tenderTrueName = info.getBusiName();
					tenderIdCard = info.getBusiCode();
				}
            }

            // 承接人用户 start
            // 承接人真实姓名
            paramter.put("truename", tenderTrueName);
            // 承接人身份证号
            paramter.put("idcard", tenderIdCard);
            // 承接人用户名
//            paramter.put("username", tenderUser.getUsername());
            // 承接人用户 end

            // 借款人用户名
            paramter.put("BorrowUsername",borrowUsers.getUsername());

            String paramStr = paramter.toJSONString();

            bean.setTenderUserName(tenderUser.getUsername());
            bean.setOrdid(assignOrderId);
            bean.setTenderType(1);
            bean.setCreditUserID(creditUserId);
            bean.setContractName(FddGenerateContractConstant.CONTRACT_DOC_TITLE_CREDIT);

            boolean isSign = isCreatContract(assignOrderId);
            if (isSign){//单独走签署接口
                updateSignContract(bean);
            }else {
				List<FddTempletVO> fddTemplets = this.borrowTenderClient.getFddTempletList(FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT);

                if (fddTemplets != null && fddTemplets.size() == 1) {
                    FddTempletVO fddTemplet = fddTemplets.get(0);
                    String templetId = fddTemplet.getTempletId();
                    DzqzCallBean callBean = new DzqzCallBean();
                    callBean.setParameter_map(paramStr);
                    callBean.setTemplate_id(templetId);
                    callBean.setTxCode(DzqzConstant.FDD_GENERATECONTRACT);
                    callBean.setContract_id(bean.getOrdid());
                    callBean.setDoc_title(FddGenerateContractConstant.CONTRACT_DOC_TITLE_CREDIT);
                    callBean.setDynamic_tables(null);
                    callBean.setUserId(tenderUserId);
                    callBean.setFont_size("12");
                    callBean.setFont_type("1");
                    DzqzCallBean dzqzCallBean = DzqzCallUtil.callApiBg(callBean);
                    String result = dzqzCallBean.getResult();
                    String code = dzqzCallBean.getCode();
                    dzqzCallBean.setTemplate_id(templetId);
                    if ("success".equals(result) && FddGenerateContractConstant.FDD_RETURN_CODE_1000.equals(code)) {
                        //协议生成成功，开始签署并进行脱敏处理
                        //TODO 存储下载地址
                        updateSaveDownUrl(dzqzCallBean, bean);
                        //TODO 签署
                        updateSignContract(bean);
                    } else {
                        logger.info("--------------开始生成债转投资协议返回错误，订单号：" + bean.getOrdid() + "错误码：" + code + ",错误描述：" + dzqzCallBean.getMsg());
                    }
                } else {
                    logger.info("--------------开始生成债转投资协议异常，无法匹配模板----订单号：" + bean.getOrdid());
                }
            }
        }

	}

	/**
	 * 获取企业用户信息
	 * @param tenderUserId
	 * @return
	 */
	private CorpOpenAccountRecordVO getCorpOpenAccountInfoByUserID(Integer tenderUserId) {
		return this.amUserClient.selectCorpOpenAccountRecordByUserId(tenderUserId);
	}

	/**
	 * 计划债转
	 * 
	 * @param bean
	 */
	public void planCreditGenerateContract(FddGenerateContractBean bean) throws Exception {
		logger.info("开始生成计划债转协议---------------------承接订单号:" + bean.getAssignNid());
		try {
			//计划债转是在事务内提交，可能获取不到数据，需要暂时休眠1秒，待数据提交
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Map<String, Object> resultMap = null;
		List<HjhDebtCreditTenderVO> hjhCreditTenderList =
				this.hjhDebtCreditClient.selectHjhCreditTenderListByAssignOrderId(bean.getAssignNid());

		String signTime = null;
		JSONObject paramter = new JSONObject();
		if(hjhCreditTenderList!=null && hjhCreditTenderList.size()>0) {
			HjhDebtCreditTenderVO hjhCreditTender = hjhCreditTenderList.get(0);
			paramter.put("assignCapital",hjhCreditTender.getAssignCapital().toString());
			paramter.put("assignPay",hjhCreditTender.getAssignPay().toString());
			//调用下载计划债转协议的方法
			CreditAssignedBean tenderCreditAssignedBean = new CreditAssignedBean();

			tenderCreditAssignedBean.setBidNid(hjhCreditTender.getBorrowNid());// 标号
			tenderCreditAssignedBean.setCreditNid(hjhCreditTender.getCreditNid());// 债转编号
			tenderCreditAssignedBean.setCreditTenderNid(hjhCreditTender.getInvestOrderId());//原始投资订单号
			tenderCreditAssignedBean.setAssignNid(hjhCreditTender.getAssignOrderId());//债转后的新的"投资"订单号
			tenderCreditAssignedBean.setCurrentUserId(hjhCreditTender.getUserId());//承接人id
//            tenderUserid = hjhCreditTender.getUserId();
			Date createTime = hjhCreditTender.getCreateTime();
			signTime = GetDate.dateToString2(createTime, "yyyyMMdd");
			// 模板参数对象(查新表)
			resultMap = this.selectHJHUserCreditContract(tenderCreditAssignedBean);
		}else{
			logger.error("开始生成计划债转协议失败-----------汇计划债转投资表中无该记录----------承接订单号:" + bean.getAssignNid());
		}

		BorrowVO borrow = (BorrowVO) resultMap.get("borrow");
		UserInfoVO creditUsersInfo = (UserInfoVO) resultMap.get("usersInfoCredit");
		UserVO creditUser = (UserVO) resultMap.get("usersCredit");
		UserInfoVO tenderUserInfo = (UserInfoVO) resultMap.get("usersInfo");
		UserVO tenderUser = (UserVO) resultMap.get("users");
		HjhDebtCreditVO borrowCredit = (HjhDebtCreditVO) resultMap.get("borrowCredit");
		UserVO borrowUsers = this.amUserClient.findUserById(borrow.getUserId());

		/** 标的基本数据 */
		String borrowStyle = borrow.getBorrowStyle();// 还款方式
		Integer borrowPeriod = borrow.getBorrowPeriod();
		String borrowDate = "";

		String creditTerm = borrowCredit.getCreditTerm().toString();
		// 是否月标(true:月标, false:天标)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_END.equals(borrowStyle);
		if(isMonth){//月表
			borrowDate = borrowPeriod + "个月";
		}else{
			borrowDate = borrowPeriod + "天";
		}
		creditTerm = creditTerm + "天";
		//转让剩余期限
		paramter.put("creditTerm", creditTerm);
		//转让日期
		paramter.put("creditTime", GetDate.dateToString2(borrowCredit.getCreateTime(),"yyyyMMdd"));

		// 标的编号
		paramter.put("borrowNid", borrow.getBorrowNid());
		// 编号
		paramter.put("NID", bean.getAssignNid());
		//签署日期
		paramter.put("addTime", signTime);

		//借款本金总额
		paramter.put("borrowAccount", borrow.getAccount().toString());
		// 借款利率
		paramter.put("borrowApr", borrow.getBorrowApr() + "%");
		// 还款方式
		paramter.put("borrowStyle", this.getBorrowStyle(borrow.getBorrowStyle()));
		// 借款期限
		paramter.put("borrowPeriod", borrowDate);


		// 出让人相关 start
		// 出让人用户真实姓名
		paramter.put("CreditTruename", creditUsersInfo.getTruename());
		// 出让人身份证号
		paramter.put("CreditIdcard", creditUsersInfo.getIdcard());
		// 出让人用户名
//        paramter.put("CreditUsername", creditUser.getUsername());
		// 出让人相关 end

		String tenderTrueName = null;
		String tenderIdCard = null;
		tenderTrueName = tenderUserInfo.getTruename();
		tenderIdCard = tenderUserInfo.getIdcard();
		if(tenderUser.getUserType() == 1){
			CorpOpenAccountRecordVO info = getCorpOpenAccountInfoByUserID(bean.getTenderUserId());
			tenderTrueName = info.getBusiName();
			tenderIdCard = info.getBusiCode();
		}

		// 承接人用户 start
		// 承接人真实姓名
		paramter.put("truename", tenderUserInfo.getTruename());
		// 承接人身份证号
		paramter.put("idcard", tenderUserInfo.getIdcard());
		// 承接人用户名
//        paramter.put("username", tenderUser.getUsername());
		// 承接人用户 end

		// 借款人用户名
		paramter.put("BorrowUsername",borrowUsers.getUsername());

		String paramStr = paramter.toJSONString();

		bean.setTenderUserId(tenderUser.getUserId());
		bean.setTenderUserName(tenderUser.getUsername());
		bean.setOrdid(bean.getAssignNid());
		bean.setTenderType(1);
		bean.setCreditUserID(creditUser.getUserId());
		bean.setContractName(FddGenerateContractConstant.CONTRACT_DOC_TITLE_CREDIT);

		boolean isSign = isCreatContract(bean.getAssignNid());
		if (isSign){//单独走签署接口
			updateSignContract(bean);
		}else {

			List<FddTempletVO> fddTemplets = this.borrowTenderClient.getFddTempletList(FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT);
			if (fddTemplets != null && fddTemplets.size() == 1) {
				FddTempletVO fddTemplet = fddTemplets.get(0);
				String templetId = fddTemplet.getTempletId();
				DzqzCallBean callBean = new DzqzCallBean();
				callBean.setParameter_map(paramStr);
				callBean.setTemplate_id(templetId);
				callBean.setTxCode(DzqzConstant.FDD_GENERATECONTRACT);
				callBean.setContract_id(bean.getOrdid());
				callBean.setDoc_title(FddGenerateContractConstant.CONTRACT_DOC_TITLE_CREDIT);
				callBean.setDynamic_tables(null);
				callBean.setFont_size("12");
				callBean.setFont_type("1");
				callBean.setUserId(tenderUser.getUserId());
				DzqzCallBean dzqzCallBean = DzqzCallUtil.callApiBg(callBean);
				String result = dzqzCallBean.getResult();
				String code = dzqzCallBean.getCode();
				dzqzCallBean.setTemplate_id(templetId);
				if ("success".equals(result) && FddGenerateContractConstant.FDD_RETURN_CODE_1000.equals(code)) {
					//协议生成成功，开始签署并进行脱敏处理
					updateSaveDownUrl(dzqzCallBean, bean);
					updateSignContract(bean);
				} else {
					logger.info("--------------开始生成计划债转投资协议返回错误，订单号：" + bean.getAssignNid() + "错误码：" + code + ",错误描述：" + dzqzCallBean.getMsg());
				}
			} else {
				logger.info("--------------开始生成计划债转投资协议异常，无法匹配模板----订单号：" + bean.getAssignNid());
			}
		}
	}

	private Map<String,Object> selectHJHUserCreditContract(CreditAssignedBean tenderCreditAssignedBean) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 获取债转投资信息
		//查询 hyjf_hjh_debt_credit_tender 表
		HjhDebtCreditTenderRequest request = new HjhDebtCreditTenderRequest();
		request.setBorrowNid(tenderCreditAssignedBean.getBidNid());
		request.setCreditNid(tenderCreditAssignedBean.getCreditNid());
		request.setInvestOrderId(tenderCreditAssignedBean.getCreditTenderNid());
		request.setAssignOrderId(tenderCreditAssignedBean.getAssignNid());
		List<HjhDebtCreditTenderVO> creditTenderList =this.hjhDebtCreditTenderClient.getHjhDebtCreditTenderList(request);
		// 当前用户的id
		Integer currentUserId = tenderCreditAssignedBean.getCurrentUserId();

		if (creditTenderList != null && creditTenderList.size() > 0) {
			HjhDebtCreditTenderVO creditTender = creditTenderList.get(0);
			Map<String, Object> params = new HashMap<String, Object>();

			params.put("creditNid", creditTender.getCreditNid());//取得 hyjf_hjh_debt_credit_tender 表的债转编号
			params.put("assignOrderId", creditTender.getAssignOrderId());//取得 hyjf_hjh_debt_credit_tender 表的债转编号

			//查看债转详情
			List<TenderToCreditDetailCustomizeVO> tenderToCreditDetailList=this.bankCreditTenderClient.selectHJHWebCreditTenderDetail(params);

			if (tenderToCreditDetailList != null && tenderToCreditDetailList.size() > 0) {
				if (tenderToCreditDetailList.get(0).getCreditRepayEndTime() != null) {
					tenderToCreditDetailList.get(0).setCreditRepayEndTime(GetDate.getDateMyTimeInMillis(Integer.parseInt(tenderToCreditDetailList.get(0).getCreditRepayEndTime())));
				}
				if (tenderToCreditDetailList.get(0).getCreditTime() != null) {
					try {
						tenderToCreditDetailList.get(0).setCreditTime(GetDate.formatDate(GetDate.parseDate(tenderToCreditDetailList.get(0).getCreditTime(), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd"));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				resultMap.put("tenderToCreditDetail", tenderToCreditDetailList.get(0));
			}


			// 获取借款标的信息
			BorrowVO borrow=this.amBorrowClient.getBorrowByNid(creditTender.getBorrowNid());

			// 获取债转信息(新表)
			HjhDebtCreditRequest request1 = new HjhDebtCreditRequest();
			request1.setBorrowNid(tenderCreditAssignedBean.getBidNid());
			request1.setCreditNid(tenderCreditAssignedBean.getCreditNid());
			request1.setInvestOrderId(tenderCreditAssignedBean.getCreditTenderNid());
			List<HjhDebtCreditVO> borrowCredit=this.hjhDebtCreditClient.getHjhDebtCreditList(request1);

			// 获取承接人身份信息
			UserInfoVO usersInfo=this.amUserClient.findUsersInfoById(creditTender.getUserId());
			// 获取承接人平台信息
			UserVO users=this.amUserClient.findUserById(creditTender.getUserId());

			// 获取融资方平台信息
			UserVO usersBorrow=this.amUserClient.findUserById(borrow.getUserId());

			// 获取债转人身份信息
			UserInfoVO usersInfoCredit=this.amUserClient.findUsersInfoById(creditTender.getCreditUserId());

			// 获取债转人平台信息
			UserVO usersCredit=this.amUserClient.findUserById(creditTender.getCreditUserId());

			// 将int类型时间转成字符串
			String createTime = GetDate.dateToString2(creditTender.getCreateTime(),"yyyyMMdd");
			String addip = GetDate.getDateMyTimeInMillis(creditTender.getAssignRepayEndTime());// 借用ip字段存储最后还款时
			resultMap.put("createTime", createTime);//记得去JSP上替换 creditResult.data.creditTender.addip 字段，要新建一个JSP
			resultMap.put("addip", addip);//记得去JSP上替换 creditResult.data.creditTender.addip 字段，要新建一个JSP
			resultMap.put("creditTender", creditTender);

			if (borrow != null) {
				if (borrow.getReverifyTime() != null) {
					borrow.setReverifyTime(GetDate.getDateMyTimeInMillis(Integer.parseInt(borrow.getReverifyTime())));
				}
				if (borrow.getRepayLastTime() != null) {
					borrow.setRepayLastTime(GetDate.getDateMyTimeInMillis(Integer.parseInt(borrow.getRepayLastTime())));
				}
				resultMap.put("borrow", borrow);
				// 获取借款人信息
				UserInfoVO usersInfoBorrow=this.amUserClient.findUsersInfoById(borrow.getUserId());


				if (usersInfoBorrow != null) {
					resultMap.put("usersInfoBorrow", usersInfoBorrow);
				}
			}

			if (borrowCredit != null && borrowCredit.size() > 0) {
				resultMap.put("borrowCredit", borrowCredit);
			}


			if (usersInfo != null) {
				resultMap.put("usersInfo", usersInfo);
			}


			if (usersBorrow != null) {

				resultMap.put("usersBorrow", usersBorrow);
			}

			if (users != null) {
				resultMap.put("users", users);
			}

			if (usersCredit != null) {
				resultMap.put("usersCredit", usersCredit);
			}

			if (usersInfoCredit != null) {

				resultMap.put("usersInfoCredit", usersInfoCredit);
			}

			//String phpWebHost = PropUtils.getSystem("hyjf.web.host.php");
			/*if (org.apache.commons.lang.StringUtils.isNotEmpty(phpWebHost)) {
				resultMap.put("phpWebHost", phpWebHost);
			} else {
				resultMap.put("phpWebHost", "http://site.hyjf.com");
			}*/
		}
		return resultMap;
	}

}
