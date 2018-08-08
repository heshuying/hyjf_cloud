package com.hyjf.cs.trade.mq.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.fdd.FddDessenesitizationBean;
import com.hyjf.am.bean.fdd.FddGenerateContractBean;
import com.hyjf.am.resquest.trade.*;
import com.hyjf.am.resquest.user.CertificateAuthorityRequest;
import com.hyjf.am.resquest.user.LoanSubjectCertificateAuthorityRequest;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.user.CertificateAuthorityVO;
import com.hyjf.am.vo.user.LoanSubjectCertificateAuthorityVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.FddGenerateContractConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.file.FavFTPUtil;
import com.hyjf.common.file.FileUtil;
import com.hyjf.common.file.SFTPParameter;
import com.hyjf.common.pdf.ImageUtil;
import com.hyjf.common.pdf.PDFToImage;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.CreditAssignedBean;
import com.hyjf.cs.trade.client.*;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.mq.producer.FddProducer;
import com.hyjf.cs.trade.mq.producer.MailProducer;
import com.hyjf.pay.lib.fadada.bean.DzqzCallBean;
import com.hyjf.pay.lib.fadada.util.DzqzCallUtil;
import com.hyjf.pay.lib.fadada.util.DzqzConstant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * 法大大 handle
 * 
 * @author jun
 * @date 20180627
 */
@Component
public class FddHandle {

	private static final Logger logger = LoggerFactory.getLogger(FddHandle.class);
	/** 用户ID */
	private static final String VAL_USERID = "userId";
	/** 用户名 */
	private static final String VAL_NAME = "val_name";
	/** 性别 */
	private static final String VAL_SEX = "val_sex";

	@Autowired
	private AmBorrowClient amBorrowClient;
	@Autowired
	private AmUserClient amUserClient;
	@Autowired
	private AmTradeClient amTradeClient;
	@Autowired
	private BorrowManinfoClient borrowManinfoClient;
	@Autowired
	private BorrowUserClient borrowUserClient;
	@Autowired
	private BorrowTenderClient borrowTenderClient;
	@Autowired
	private BorrowCreditClient borrowCreditClient;
	@Autowired
	private HjhDebtCreditClient hjhDebtCreditClient;
	@Autowired
	private HjhAccedeClient hjhAccedeClient;
	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private FddProducer fddProducer;
	@Autowired
	private MailProducer mailProducer;

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

		String payurl = systemConfig.getHyjfPayFddNotifyUrl();
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
				String platFromCustomerId = systemConfig.getHyjfFddCustomerid();
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
		List<TenderAgreementVO> tenderAgreementList = amTradeClient.selectTenderAgreementByNid(nid);
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
        List<TenderAgreementVO> agreements = this.amTradeClient.getTenderAgreementListByTenderNidAndStatusNot2(contract_id);
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
					fddProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC,MQConstant.FDD_DOWNPDF_AND_DESSENSITIZATION_TAG,JSON.toJSONBytes(bean)));
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
        List<CreditTenderVO> creditTenderList = this.amTradeClient.getCreditTenderList(request);
        if (creditTenderList != null && creditTenderList.size() > 0) {

            CreditTenderVO creditTender = creditTenderList.get(0);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("creditNid", creditTender.getCreditNid());
            params.put("assignNid", creditTender.getAssignNid());
			List<TenderToCreditDetailCustomizeVO> tenderToCreditDetailList = this.amTradeClient.selectWebCreditTenderDetailForContract(params);
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

	/**
	 * 查询汇计划债转投资表
	 * @param tenderCreditAssignedBean
	 * @return
	 */
	private Map<String,Object> selectHJHUserCreditContract(CreditAssignedBean tenderCreditAssignedBean) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 获取债转投资信息
		//查询 hyjf_hjh_debt_credit_tender 表
		HjhDebtCreditTenderRequest request = new HjhDebtCreditTenderRequest();
		request.setBorrowNid(tenderCreditAssignedBean.getBidNid());
		request.setCreditNid(tenderCreditAssignedBean.getCreditNid());
		request.setInvestOrderId(tenderCreditAssignedBean.getCreditTenderNid());
		request.setAssignOrderId(tenderCreditAssignedBean.getAssignNid());
		List<HjhDebtCreditTenderVO> creditTenderList =this.amTradeClient.getHjhDebtCreditTenderList(request);
		// 当前用户的id
		Integer currentUserId = tenderCreditAssignedBean.getCurrentUserId();

		if (creditTenderList != null && creditTenderList.size() > 0) {
			HjhDebtCreditTenderVO creditTender = creditTenderList.get(0);
			Map<String, Object> params = new HashMap<String, Object>();

			params.put("creditNid", creditTender.getCreditNid());//取得 hyjf_hjh_debt_credit_tender 表的债转编号
			params.put("assignOrderId", creditTender.getAssignOrderId());//取得 hyjf_hjh_debt_credit_tender 表的债转编号

			//查看债转详情
			List<TenderToCreditDetailCustomizeVO> tenderToCreditDetailList=this.amTradeClient.selectHJHWebCreditTenderDetail(params);

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

			String phpWebHost = "";
			//PropUtils.getSystem("hyjf.web.host.php");
			//TODO 在老系統中找不到對應的資源文件,後面解決
			if (org.apache.commons.lang.StringUtils.isNotEmpty(phpWebHost)) {
				resultMap.put("phpWebHost", phpWebHost);
			} else {
				resultMap.put("phpWebHost", "http://site.hyjf.com");
			}
		}
		return resultMap;
	}


	/**
	 * 自动签署结果异步返回处理
	 * @param bean
	 */
	public void updateAutoSignData(DzqzCallBean bean) {

		//协议类型
		Integer transType = bean.getTransType();

		//居间服务协议签署处理
		//合同编号
		String contract_id = bean.getContract_id();
		logger.info("--------------------合同编号：" + contract_id + "，开始处理自动签署异步处理-------");
		//投资人
		Integer userId = null;

		String borrowNid = null;

		//机构编号
		String instCode = null;

		// 出让人用户ID
		Integer creditUserId = null;
		// 借款人CA认证客户编号
		String borrowerCustomerID = null;

		if(FddGenerateContractConstant.PROTOCOL_TYPE_TENDER == transType){//居间服务协议
			List<BorrowTenderVO> tenderList = this.borrowTenderClient.getBorrowTenderListByNid(contract_id);
			if (tenderList != null && tenderList.size() > 0) {
				BorrowTenderVO borrowTender = tenderList.get(0);
				userId = borrowTender.getUserId(); //投资人
				borrowNid = borrowTender.getBorrowNid();//标的号
				BorrowVO borrow = this.amBorrowClient.getBorrowByNid(borrowNid);
				instCode = borrow.getInstCode();//机构编号
				//判断是否为企业借款
				boolean result = this.isCompanyUser(borrow);
				if (result){

					// 获取CA认证客户编号
					if(org.apache.commons.lang.StringUtils.isNotBlank(borrow.getPlanNid())){//计划标的
						Integer borrowUserId = borrow.getUserId();
						borrowerCustomerID = getCustomerIDByUserID(borrowUserId);
						if(org.apache.commons.lang.StringUtils.isBlank(borrowerCustomerID)){
							logger.info("企业借款获取CA认证客户编号失败,用户ID" + borrowUserId);
						}
					}else{
						// 借款主体为企业借款
						BorrowUserVO borrowUsers=this.borrowUserClient.getBorrowUser(borrowNid);
						if (borrowUsers == null){
							logger.info("根据标的编号查询借款主体为企业借款的相关信息失败,标的编号:["+borrowNid+"]");
						}
						borrowerCustomerID = this.getCACustomerID(borrowUsers);
						if(org.apache.commons.lang.StringUtils.isBlank(borrowerCustomerID)){
							logger.info("企业借款获取CA认证客户编号失败,企业名称:["+borrowUsers.getUsername()+"],社会统一信用代码:["+borrowUsers.getSocialCreditCode()+"].");
						}
					}

				}else{
					if(org.apache.commons.lang.StringUtils.isNotBlank(borrow.getPlanNid())){//计划标的
						Integer borrowUserId = borrow.getUserId();
						borrowerCustomerID = getCustomerIDByUserID(borrowUserId);
						if(org.apache.commons.lang.StringUtils.isBlank(borrowerCustomerID)){
							logger.info("个人借款获取CA认证客户编号失败,用户ID" + borrowUserId);
						}
					}else {
						// 借款主体为个人借款
						BorrowManinfoVO borrowManinfo=this.borrowManinfoClient.getBorrowManinfo(borrowNid);
						if (borrowManinfo == null) {
							logger.info("借款主体为个人借款时,获取个人借款信息失败,标的编号:[" + borrowNid + "].");
						}
						// 获取CA认证客户编号
						borrowerCustomerID = this.getPersonCACustomerID(borrowManinfo);
						if (org.apache.commons.lang.StringUtils.isBlank(borrowerCustomerID)) {
							logger.info("获取个人借款CA认证客户编号失败,姓名:[" + borrowManinfo.getName() + "],身份证号:[" + borrowManinfo.getCardNo() + "].");
						}
					}
				}
			}
		}else if(FddGenerateContractConstant.PROTOCOL_TYPE_PLAN == transType){//计划加入协议
			HjhAccedeVO hjhAccede=this.hjhAccedeClient.getHjhAccedeByAccedeOrderId(contract_id);
			if(Validator.isNotNull(hjhAccede)){
				userId = hjhAccede.getUserId();
				instCode = hjhAccede.getPlanNid();
			}
		}else if(FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT == transType){//债转服务协议
			List<CreditTenderVO> creditTenderList=this.amTradeClient.selectCreditTender(contract_id);
			if (creditTenderList != null && creditTenderList.size() > 0) {
				CreditTenderVO creditTender = creditTenderList.get(0);
				userId = creditTender.getUserId();// 承接人
				borrowNid = creditTender.getBidNid();// 原标的号
				BorrowVO borrow=this.amBorrowClient.getBorrowByNid(borrowNid);
				instCode = borrow.getInstCode();// 机构编号
				creditUserId = creditTender.getCreditUserId();// 出让人
				borrowerCustomerID = getCustomerIDByUserID(creditUserId);
			}
		}else if(FddGenerateContractConstant.FDD_TRANSTYPE_PLAN_CRIDET == transType){// 计划债转服务协议
			List<HjhDebtCreditTenderVO> hjhCreditTenderList = this.hjhDebtCreditClient.selectHjhCreditTenderListByAssignOrderId(contract_id);
			//hyjf_hjh_debt_credit_tender
			if(hjhCreditTenderList!=null && hjhCreditTenderList.size()>0) {
				HjhDebtCreditTenderVO hjhCreditTender = hjhCreditTenderList.get(0);
				userId = hjhCreditTender.getUserId();// 承接人
				borrowNid = hjhCreditTender.getBorrowNid();// 标的号
				BorrowVO borrow=this.amBorrowClient.getBorrowByNid(borrowNid);
				instCode = borrow.getInstCode();// 机构编号
				creditUserId = hjhCreditTender.getCreditUserId();// 出让人
				borrowerCustomerID = getCustomerIDByUserID(creditUserId);
			}
		}


		//通过三方的客户编号查询法大大签署结果，如果都成功的话，开始更新下载地址，并重置状态
		String tenderCustomerID = getCustomerIDByUserID(userId);
		if (tenderCustomerID == null) {
			throw new RuntimeException("用户：" + userId + ",未进行CA认证，无法获取客户编号！");
		}


		//平台客户编号
		String platFromCustomerId =systemConfig.getHyjfFddCustomerid();

		//查询投资人/承接人是否为企业用户
		boolean isTenderCompany = false;
		UserVO users = this.amUserClient.findUserById(userId);
		Integer userType = users.getUserType();
		if (userType == 1){
			isTenderCompany = true;
		}

		//查询出让人是否为企业用户
		boolean isCreditCompany = false;
		if(creditUserId != null){
			UserVO creditUsers = this.amUserClient.findUserById(creditUserId);
			Integer creditUserType = creditUsers.getUserType();
			if (creditUserType == 1){
				isCreditCompany = true;
			}
		}
		//调用查询接口并保存返回数据
		//查询投资人签署结果
		DzqzCallBean callBean = querySignResult(contract_id, tenderCustomerID, userId);
		if (callBean != null) {
			String sign_status = callBean.getSign_status();
			if (!"1".equals(sign_status)) {
				logger.info("--------------------合同编号：" + contract_id + "，甲方签署失败-------");
				return;
			}
			logger.info("--------------------合同编号：" + contract_id + "，甲方签署完成-------");
		} else {
			throw new RuntimeException("用户：" + userId + ",查询签署结果异常！");
		}

		if(FddGenerateContractConstant.PROTOCOL_TYPE_TENDER == transType || FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT == transType
				|| FddGenerateContractConstant.FDD_TRANSTYPE_PLAN_CRIDET == transType){

			// 查询借款人签署结果
			DzqzCallBean borrowCallBean = querySignResult(contract_id, borrowerCustomerID, 123);
			if (borrowCallBean != null) {
				String sign_status = borrowCallBean.getSign_status();
				if (!"1".equals(sign_status)) {
					logger.info("--------------------合同编号：" + contract_id + "，乙方签署失败-------");
					return;
				}
				logger.info("--------------------合同编号：" + contract_id + "，乙方签署完成-------");
			} else {
				throw new RuntimeException("查询签署结果异常！");
			}
		}

		if ( FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT == transType
				|| FddGenerateContractConstant.FDD_TRANSTYPE_PLAN_CRIDET == transType){
			logger.info("--------------------合同编号：" + contract_id + "，签署完成，开始更新-------");
			updateSaveSignInfo(callBean, contract_id, borrowNid, transType, instCode,isTenderCompany,isCreditCompany);
		}else{
			//查询平台签署结果
			DzqzCallBean platerCallBean = querySignResult(contract_id, platFromCustomerId, userId);
			if (platerCallBean != null) {
				String sign_status = platerCallBean.getSign_status();
				if (!"1".equals(sign_status)) {
					logger.info("--------------------合同编号：" + contract_id + "，平台签署失败-------");
					return;
				} else {
					logger.info("--------------------合同编号：" + contract_id + "，签署完成，开始更新-------");
					//三方均签署成功，更新数据库
					updateSaveSignInfo(platerCallBean, contract_id, borrowNid, transType, instCode,isTenderCompany,false);
				}
				logger.info("--------------------合同编号：" + contract_id + "，平台签署完成-------");
			} else {
				throw new RuntimeException("接入平台,查询签署结果异常！");
			}
		}


	}

	/**
	 * 查询签署结果
	 *  @param contract_id      合同编号
	 * @param tenderCustomerID 客户编号
	 * @param userId
	 */
	private DzqzCallBean querySignResult(String contract_id, String tenderCustomerID, Integer userId) {
		DzqzCallBean bean = new DzqzCallBean();
		bean.setTxCode(DzqzConstant.FDD_QUERY_SIGNSTATUS);
		bean.setCustomer_id(tenderCustomerID);
		bean.setContract_id(contract_id);
		bean.setUserId(userId);
		DzqzCallBean callBean = DzqzCallUtil.callApiBg(bean);
		return callBean;
	}

	/**
	 *  判断是否为企业借款
	 * @param borrow
	 * @return
	 */
	private boolean isCompanyUser(BorrowVO borrow) {
		String planNid = borrow.getPlanNid();
		if(org.apache.commons.lang.StringUtils.isNotBlank(planNid)){//计划标的
			Integer userId = borrow.getUserId();
			UserVO users=this.amUserClient.findUserById(userId);
			Integer userType = users.getUserType();
			if(1 == userType){
				return true;
			}
		}else{
			String companyOrPersonal = borrow.getCompanyOrPersonal();
			if("1".equals(companyOrPersonal)){
				return true;
			}
		}

		return false;
	}

	/**
	 *  下载并脱敏处理
	 * @param savePath        文件保存地址
	 * @param tenderAgreementID 签署数据
	 * @param transType 协议类型
	 * @param download_url 协议下载地址
	 * @param creditCompany
	 */
	public void downPDFAndDesensitization(String savePath, String tenderAgreementID, String transType, String ftpPath, String download_url, boolean tenderCompany, boolean creditCompany) {
		String fileName = null;
		String fileType = null;
		if (Integer.valueOf(transType) == FddGenerateContractConstant.PROTOCOL_TYPE_TENDER){
			fileName = FddGenerateContractConstant.CONTRACT_DOC_FILE_NAME_TENDER;
			fileType = "9.png";
		}else if(Integer.valueOf(transType) == FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT || Integer.valueOf(transType) == FddGenerateContractConstant.FDD_TRANSTYPE_PLAN_CRIDET){
			fileName = FddGenerateContractConstant.CONTRACT_DOC_FILE_NAME_CREDIT;
			fileType = "2.png";
		}else if(Integer.valueOf(transType) == FddGenerateContractConstant.PROTOCOL_TYPE_PLAN){
			fileName = FddGenerateContractConstant.CONTRACT_DOC_FILE_NAME_PLAN;
			fileType = "6.png";
		}
		try {
			String downFileName =fileName+".pdf";
			FileUtil.downLoadFromUrl(download_url,downFileName,savePath);

			savePath = savePath + "/";
			//获取文件路径
			String filePath = savePath + fileName + ".pdf";
			//上传PDF文件
			this.uplodTmImage(filePath,ftpPath,0);
			//开始脱敏文件
			//获取文件页数
			PDDocument pdDocument = PDFToImage.pdfInfo(filePath);
			int pages = pdDocument.getNumberOfPages();

			//是否企业用户
			boolean isCompanyUser = false;
			TenderAgreementVO tenderAgrementInfo = this.amTradeClient.getTenderAgreementInfoByPrimaryKey(tenderAgreementID);
			String borrowNid = tenderAgrementInfo.getBorrowNid();
			if(StringUtils.isNotBlank(borrowNid)){
				BorrowVO borrow=this.amBorrowClient.getBorrowByNid(borrowNid);
				String planNid = borrow.getPlanNid();
				if(StringUtils.isNotBlank(planNid)){//计划标的
					Integer borrowUserId = borrow.getUserId();
					UserVO users=this.amUserClient.findUserById(borrowUserId);
					Integer userType = users.getUserType();
					if(1 == userType){
						isCompanyUser = true;
					}
				}else{
					if("1".equals(borrow.getCompanyOrPersonal())){
						isCompanyUser = true;
					}
				}
			}

			//拼接URL
			List jointPathList = new ArrayList();
			String imageSavePath = savePath + fileName;
			//转换成图片
			PDFToImage.pdf2img(filePath, imageSavePath, PDFToImage.IMG_TYPE_PNG);
			//签章待脱敏图片地址
			String imageFilePath = imageSavePath +"/"+  fileName + fileType;
			//真实姓名待脱敏图片地址
			String trueImageFilePath = imageSavePath +"/"+  fileName + "0.png";
			logger.info("---------------待脱敏图片地址：" + imageFilePath);
			File tmfile = new File(imageFilePath);
			String upParentDir = tmfile.getParent();
			logger.info("---------------脱敏图片上级目录：" + upParentDir);
			//图片脱敏并存储
			if (FddGenerateContractConstant.PROTOCOL_TYPE_TENDER == Integer.valueOf(transType)){

				tmConduct(imageSavePath,imageFilePath,fileName,isCompanyUser,trueImageFilePath,jointPathList,pages,
						Integer.valueOf(transType),tenderCompany,creditCompany);

			}else if(Integer.valueOf(transType) == FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT ||
					Integer.valueOf(transType) == FddGenerateContractConstant.FDD_TRANSTYPE_PLAN_CRIDET){

				tmConduct(imageSavePath,imageFilePath,fileName,isCompanyUser,trueImageFilePath,jointPathList,pages,
						Integer.valueOf(transType),tenderCompany,creditCompany);

			}else{
				for (int i = 0; i < pages; i++) {
					jointPathList.add(imageSavePath + "/" + fileName + i + ".png");
				}
			}
			//拼接后的PDf路径
			String tmpdfPath  = imageSavePath + "/" + fileName +"_tm.pdf";
			//拼接脱敏图片
			jointPDFimage(jointPathList,imageSavePath + "/pdfimage.png");
			//重新拼接为PDF文件
			replaceImageToPdf(jointPathList,tmpdfPath);

			boolean uploadPDF = uplodTmImage(tmpdfPath, ftpPath, 0);
			if(uploadPDF){
				boolean upResult = uplodTmImage(imageSavePath + "/pdfimage.png",ftpPath,1);
				if(upResult){
					this.updateTenderAgreementImageURL(tenderAgreementID,ftpPath+"pdfimage.png",ftpPath + fileName +"_tm.pdf");

					// 发送邮件
					if (Integer.valueOf(transType) == FddGenerateContractConstant.PROTOCOL_TYPE_TENDER){
						BorrowRecoverVO recover = this.amTradeClient.selectBorrowRecoverByTenderNid(tenderAgreementID);
						if (recover != null && StringUtils.isBlank(recover.getAccedeOrderId())){
							this.sendMail(recover);
						}
					}else if(Integer.valueOf(transType) == FddGenerateContractConstant.PROTOCOL_TYPE_PLAN){
						HjhAccedeVO hjhAccede = this.hjhAccedeClient.getHjhAccedeByAccedeOrderId(tenderAgrementInfo.getTenderNid());
						this.sendPlanMail(hjhAccede);
					}
				}else{
					logger.info("----------脱敏图片上传失败-----------");
				}
			}else{
				logger.info("----------脱敏PDF上传失败-----------");
			}

		} catch (Exception e) {
			logger.info("------------脱敏协议错误，错误信息" + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 发送邮件(计划订单状态由投资成功变为锁定中，发送此邮件提醒用户投资成功)
	 * @param hjhAccede
	 */
	private void sendPlanMail(HjhAccedeVO hjhAccede) {

		logger.info("计划订单状态由投资成功变为锁定中，发送此邮件提醒用户投资--------------------------开始");
		try {
			Map<String, Object> contents = new HashMap<String, Object>();
			//1基本信息
			Map<String ,Object> params=new HashMap<String ,Object>();
			params.put("accedeOrderId", hjhAccede.getAccedeOrderId());
			int userId = hjhAccede.getUserId();
			params.put("userId", userId);
			UserInfoVO userInfo=this.amUserClient.findUsersInfoById(Integer.valueOf(hjhAccede.getUserId()));
			contents.put("userInfo", userInfo);
			contents.put("username", hjhAccede.getUserName());
			UserHjhInvistDetailCustomizeVO userHjhInvistDetailCustomize = this.selectUserHjhInvistDetail(params);
			contents.put("userHjhInvistDetail", userHjhInvistDetailCustomize);
			Map<String, String> msg = new HashMap<String, String>();
			msg.put(VAL_USERID, String.valueOf(userId));
			// 向每个投资人发送邮件
			if (Validator.isNotNull(msg.get(VAL_USERID)) && NumberUtils.isNumber(msg.get(VAL_USERID))) {
				UserVO users=this.amUserClient.findUserById(userId);
				if (users == null || Validator.isNull(users.getEmail()) || users.getIsSmtp()==1) {
					logger.info("=============cwyang eamil is users == null===========");
					return;
				}
				String email = users.getEmail();
				if (org.apache.commons.lang3.StringUtils.isBlank(email) || users.getIsSmtp()==1) {
					logger.info("=============cwyang eamil users.getIsSmtp()==1===========");
					return;
				}
				logger.info("=============cwyang eamil is " + email);
				msg.put(VAL_NAME, users.getUsername());
				UserInfoVO usersInfo=this.amUserClient.findUsersInfoById(Integer.valueOf(userId));
				if (Validator.isNotNull(usersInfo) && Validator.isNotNull(usersInfo.getSex())) {
					if (usersInfo.getSex() % 2 == 0) {
						msg.put(VAL_SEX, "女士");
					} else {
						msg.put(VAL_SEX, "先生");
					}
				}
				String fileName = hjhAccede.getAccedeOrderId()+".pdf";
				//String filePath = CustomConstants.HYJF_MAKEPDF_TEMPPATH + "/fdd/";
				//String filePath = PropUtils.getSystem(CustomConstants.HYJF_MAKEPDF_TEMPPATH) + "/" + "BorrowLoans_" + GetDate.getMillis() + StringPool.FORWARD_SLASH;
				String filePath = "/pdf_tem/pdf/" + hjhAccede.getPlanNid();
				TenderAgreementVO tenderAgreement = new TenderAgreementVO();
				List<TenderAgreementVO> tenderAgreementsNid=this.amTradeClient.selectTenderAgreementByNid(hjhAccede.getAccedeOrderId());
				/***************************下载法大大协议******************************************/
				//下载法大大协议--投资服务协议
				if(tenderAgreementsNid!=null && tenderAgreementsNid.size()>0){
					tenderAgreement = tenderAgreementsNid.get(0);
					logger.info("sendMail", "***************************下载法大大协议--投资orderId:"+hjhAccede.getAccedeOrderId());
					logger.info("sendMail", "***************************下载法大大协议--投资pdfUrl:"+tenderAgreement.getImgUrl());
					if(tenderAgreement!=null){
						String pdfUrl = tenderAgreement.getDownloadUrl();
						if(StringUtils.isNotBlank(pdfUrl)){
							logger.info("sendMail", "***************************下载法大大协议--pdfUrl:"+pdfUrl);
							//FileUtil.getRemoteFile(pdfUrl, filePath + fileName);
							FileUtil.downLoadFromUrl(pdfUrl,fileName,filePath);
						}
					}
				}
				String[] emails = { email };
				//先用EMAILPARAM_TPL_LOANS测试，后期改成EMAITPL_EMAIL_LOCK_REPAY
				logger.info("sendMail***************************下载法大大协议--投资filePath:"+filePath + fileName);
				MailMessage mailMessage = new MailMessage(Integer.valueOf(userId), msg, "汇计划投资服务协议", null, new String[] { filePath + "/" + fileName }, emails, CustomConstants.EMAITPL_EMAIL_LOCK_REPAY,
						MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS);
				// 发送邮件
				mailProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(),JSON.toJSONBytes(mailMessage)));


				logger.info("计划订单状态由投资成功变为锁定中，发送此邮件提醒用户投资--------------------------结束!");
			}
		} catch (Exception e) {
			logger.info("计划订单状态由投资成功变为锁定中，发送此邮件提醒用户投资成功-----发送邮件失败");
		}

	}


	/**
	 * 发送邮件(投资成功)居间服务协议
	 * @param borrowRecover
	 */
	private void sendMail(BorrowRecoverVO borrowRecover) {

		int userId = borrowRecover.getUserId();
		String orderId = borrowRecover.getNid();
		Map<String, String> msg = new HashMap<String, String>();
		msg.put(VAL_USERID, String.valueOf(userId));
		try {
			// 向每个投资人发送邮件
			if (Validator.isNotNull(msg.get(VAL_USERID)) && NumberUtils.isNumber(msg.get(VAL_USERID))) {
				UserVO users = this.amUserClient.findUserById(userId);
				if (users == null || Validator.isNull(users.getEmail())) {
					return;
				}
				String email = users.getEmail();
				if (org.apache.commons.lang3.StringUtils.isBlank(email) || users.getIsSmtp()==1) {
					return;
				}
				logger.info("开始发送邮件。投资订单号:" + orderId);
				msg.put(VAL_NAME, users.getUsername());
				UserInfoVO usersInfo =this.amUserClient.findUsersInfoById(Integer.valueOf(userId));
				if (Validator.isNotNull(usersInfo) && Validator.isNotNull(usersInfo.getSex())) {
					if (usersInfo.getSex() % 2 == 0) {
						msg.put(VAL_SEX, "女士");
					} else {
						msg.put(VAL_SEX, "先生");
					}
				}


				String fileName = "";
				//String filePath = PropUtils.getSystem(CustomConstants.HYJF_MAKEPDF_TEMPPATH) + "BorrowLoans_" + GetDate.getMillis() + StringPool.FORWARD_SLASH;
				String  filePath = "/pdf_tem/pdf/" + orderId;
				TenderAgreementVO tenderAgreement = new TenderAgreementVO();
				List<TenderAgreementVO> tenderAgreementsNid=this.amTradeClient.selectTenderAgreementByNid(orderId);
				/***************************下载法大大协议******************************************/
				//下载法大大协议--居间
				if(tenderAgreementsNid!=null && tenderAgreementsNid.size()>0){
					tenderAgreement = tenderAgreementsNid.get(0);
					logger.info("sendMail***************************下载法大大协议--投资orderId:"+orderId);
					logger.info("sendMail***************************下载法大大协议--投资pdfUrl:"+tenderAgreement.getImgUrl());
					if(tenderAgreement!=null){
						File file= createFaddPDFImgFile(tenderAgreement,filePath);
						fileName =  file.getName();
					}
				}
				String[] emails = { email };
				logger.info("sendMail***************************下载法大大协议--汇盈金服互联网金融服务平台居间服务协议filePath:"+filePath + fileName);
				MailMessage mailMessage = new MailMessage(Integer.valueOf(userId), msg, "汇盈金服互联网金融服务平台居间服务协议", null, new String[] { filePath +"/" + fileName }, emails, CustomConstants.EMAILPARAM_TPL_LOANS,
						MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS);
				// 发送邮件
				mailProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(),JSON.toJSONBytes(mailMessage)));

				// 更新BorrowRecover邮件发送状态
				borrowRecover.setSendmail(1);
				this.amTradeClient.updateBorrowRecover(borrowRecover);

				logger.info("结束发送邮件。投资订单号:" + orderId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}




	}

	/**
	 * 下载法大大协议
	 * @param tenderAgreement
	 * @param savePath
	 * 返回 0:下载成功；1:下载失败；2:没有生成法大大合同记录
	 */
	private File createFaddPDFImgFile(TenderAgreementVO tenderAgreement, String savePath) {

		SFTPParameter para = new SFTPParameter() ;
		String ftpIP = systemConfig.getHyjfFtpIp();
		String port = systemConfig.getHyjfFtpPort();
		String basePathImage = systemConfig.getHyjfFtpBasepathImg();
		String basePathPdf = systemConfig.getHyjfFtpBasepathPdf();
		String password = systemConfig.getHyjfFtpPassword();
		String username = systemConfig.getHyjfFtpUsername();
		para.hostName = ftpIP;//ftp服务器地址
		para.userName = username;//ftp服务器用户名
		para.passWord = password;//ftp服务器密码
		para.port = Integer.valueOf(port);//ftp服务器端口
		para.downloadPath =basePathImage;//ftp服务器文件目录
		para.savePath = savePath;
		para.fileName = tenderAgreement.getTenderNid();
		String imgUrl = tenderAgreement.getImgUrl();
		String pdfUrl = tenderAgreement.getPdfUrl();
		if(StringUtils.isNotBlank(pdfUrl)){
			imgUrl = pdfUrl;
		}else if(StringUtils.isNotBlank(imgUrl)){
			imgUrl = tenderAgreement.getImgUrl();
		}else{
			return null;
		}
		String imagepath = imgUrl.substring(0,imgUrl.lastIndexOf("/"));
		String imagename = imgUrl.substring(imgUrl.lastIndexOf("/")+1);
		para.downloadPath =basePathImage+ "/" +imagepath;//ftp服务器文件目录
		System.out.println("downloadPath___下载图片_______________________:"+para.downloadPath);
		para.sftpKeyFile = imagename;
		if(StringUtils.isNotBlank(pdfUrl)){
			para.downloadPath =  basePathPdf+ "/" +imagepath;//ftp服务器文件目录
			System.out.println("downloadPDFPath___下载PDF_______________________:"+para.downloadPath + "/" + imagename);
		}
		return  FavFTPUtil.downloadDirectory(para);
	}

	private void updateTenderAgreementImageURL(String tenderAgreementID, String iamgeurl, String tmpdfPath) {
		TenderAgreementVO tenderAgreement = new TenderAgreementVO();
		tenderAgreement.setId(Integer.parseInt(tenderAgreementID));
		tenderAgreement.setImgUrl(iamgeurl);
		tenderAgreement.setPdfUrl(tmpdfPath);
		tenderAgreement.setStatus(3);//下载成功
		this.amTradeClient.updateTenderAgreement(tenderAgreement);
	}


	/**
	 *
	 * @param imagePathList
	 * 转换图片列表
	 * @param pdfSavePath
	 * PDF存储地址
	 * @return
	 */
	private boolean replaceImageToPdf(List imagePathList,String pdfSavePath){

		try{
			FileUtil.imageTOpdf(imagePathList,pdfSavePath);
		}catch (Exception e){
			logger.info("-----------------脱敏图片转换成pdf失败--------");
			return false;
		}

		return true;
	}

	/**
	 *
	 * @param jointPathList
	 * @return
	 */
	private boolean jointPDFimage(List jointPathList,String imageSavePath) throws Exception {

		String[] files = new String[jointPathList.size()];
		for (int i = 0; i < jointPathList.size(); i++) {
			files[i] = (String) jointPathList.get(i);
		}
		FileUtil.mergeImage(files,2,imageSavePath);

		return true;
	}

	/**
	 * 脱敏处理
	 * @param imageSavePath
	 * 图片保存路径
	 * @param imageFilePath
	 * 签章待脱敏图片路径
	 * @param fileName
	 * 图片名称
	 * @param isCompanyUser
	 * 是否企业户
	 * @param trueImageFilePath
	 * 协议内容待脱敏图片路径
	 * @param jointPathList
	 * 脱敏后拼接图片列表
	 * @param pages
	 * pdf页数
	 * @param pdfType
	 * @param creditCompany
	 */
	private void tmConduct(String imageSavePath, String imageFilePath, String fileName, boolean isCompanyUser,
						   String trueImageFilePath, List jointPathList, int pages, int pdfType, boolean isTenderConmpany, boolean creditCompany){
		//出让人、借款人真实姓名脱敏图片
		String borrowTrueNametmImage = "/image/companyname.png";
		//出让人、借款人签章图片
		String borrowSigntmImage = "/image/companyname.png";
		//出让人、借款人身份证号码图片
		String borrowCardNoImage = "/image/cardno.png";

		//承接人、投资人真实姓名脱敏图片
		String tenderTrueNametmImage = "/image/companyname.png";
		//承接人、投资人签章图片
		String tenderSigntmImage = "/image/companyname.png";
		//承接人、投资人身份证号码图片
		String tenderCardNoImage = "/image/cardno.png";

		if(Integer.valueOf(pdfType) == FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT ||
				Integer.valueOf(pdfType) == FddGenerateContractConstant.FDD_TRANSTYPE_PLAN_CRIDET){
			if(creditCompany){//出让人为企业
				borrowTrueNametmImage = "/image/companyname.png";
				borrowSigntmImage = "/image/seal.png";
				borrowCardNoImage = borrowTrueNametmImage;
			}
			if(isTenderConmpany){//承接人为企业
				tenderTrueNametmImage = "/image/companyname.png";
				tenderSigntmImage = "/image/seal.png";
				tenderCardNoImage = tenderTrueNametmImage;
			}

		}else{
			if(isCompanyUser){
				borrowTrueNametmImage = "/image/companyname.png";
				borrowSigntmImage = "/image/seal.png";
				borrowCardNoImage = borrowTrueNametmImage;
			}
			if(isTenderConmpany){
				tenderTrueNametmImage = "/image/companyname.png";
				tenderSigntmImage = "/image/seal.png";
				tenderCardNoImage = tenderTrueNametmImage;
			}
		}
		String tmName_sign = "";
		String tmName_content = "tm_0";
		if(FddGenerateContractConstant.PROTOCOL_TYPE_TENDER == Integer.valueOf(pdfType)){
			tmName_sign = "tm_9";
		}else if(Integer.valueOf(pdfType) == FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT ||
				Integer.valueOf(pdfType) == FddGenerateContractConstant.FDD_TRANSTYPE_PLAN_CRIDET){
			tmName_sign = "tm_2";
		}
		String output = imageSavePath;
		String source = imageFilePath;    //签章源图片路径
		String path = this.getClass().getResource("/").getFile().toString();
		File file = new File(path);
		String fileParent = file.getParent();
		String signIcon = fileParent + borrowSigntmImage; //签章覆盖图片路径
		String tenderSignIcon = fileParent + tenderSigntmImage; //投资人。承接人签章覆盖图片路径
		String signimageName = fileName + tmName_sign;  //签章脱敏后图片名称
		String imageType = "png";  //图片类型jpg,jpeg,png,gif
		Integer degree = null; //水印旋转角度-45，null表示不旋转
		//转让人/借款人 脱敏签章(个人显示第一个字，企业全部脱敏)
		int index_x = 0;
		int index_y = 0;
		if(FddGenerateContractConstant.PROTOCOL_TYPE_TENDER == Integer.valueOf(pdfType)){
			index_x = 440;
			index_y = 1120;
			if(isCompanyUser){
				index_x = 380;
				index_y = 1000;
			}
		}else if(Integer.valueOf(pdfType) == FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT ||
				Integer.valueOf(pdfType) == FddGenerateContractConstant.FDD_TRANSTYPE_PLAN_CRIDET){
			index_x = 430;
			index_y = 1100;
			if(creditCompany){
				index_x = 410;
				index_y = 1100;
			}
		}
		ImageUtil.markImageByMoreIcon(signIcon, source, output, signimageName, imageType, degree, index_x, index_y);

		//受让人/投资人 脱敏签章（个人显示第一个字，企业全部脱敏）
		source = output + "/" + signimageName + ".png";
		if(FddGenerateContractConstant.PROTOCOL_TYPE_TENDER == Integer.valueOf(pdfType)){
			index_x = 440;
			index_y = 920;
			if(isTenderConmpany){
				index_x = 380;
				index_y = 920;
			}
		}else if(Integer.valueOf(pdfType) == FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT ||
				Integer.valueOf(pdfType) == FddGenerateContractConstant.FDD_TRANSTYPE_PLAN_CRIDET){
			index_x = 430;
			index_y = 1300;
			if(isTenderConmpany){
				index_x = 368;
				index_y = 1190;
			}
		}
		ImageUtil.markImageByMoreIcon(tenderSignIcon, source, output, signimageName, imageType, degree, index_x, index_y);

		//脱敏转让人/借款人 真实姓名（个人显示第一个字，其他脱敏，企业全部脱敏）
		String trueImageName = fileName + tmName_content;//真实姓名脱敏后图片
		String trueSource = trueImageFilePath;//待脱敏图片路径
		String icon = fileParent + borrowTrueNametmImage;  //覆盖图片路径
		if(FddGenerateContractConstant.PROTOCOL_TYPE_TENDER == Integer.valueOf(pdfType)){
			index_x = 374;
			index_y = 600;
			if(isCompanyUser){
				index_x = 354;
				index_y = 600;
			}
		}else if(Integer.valueOf(pdfType) == FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT ||
				Integer.valueOf(pdfType) == FddGenerateContractConstant.FDD_TRANSTYPE_PLAN_CRIDET){
			index_x = 390;
			index_y = 450;
			if(creditCompany){
				index_x = 370;
				index_y = 450;
			}
		}
		ImageUtil.markImageByMoreIcon(icon, trueSource, output, trueImageName, imageType, degree, index_x, index_y);

		trueSource = output + "/" + trueImageName + ".png";

		String cardnoIcon = fileParent + borrowCardNoImage;

		//脱敏转让人/借款人 证件号码（个人显示前3后4，企业全部脱敏）
		if(FddGenerateContractConstant.PROTOCOL_TYPE_TENDER == Integer.valueOf(pdfType)){
			index_x = 340;
			index_y = 650;
			if(isCompanyUser){
				index_x = 300;
				index_y = 650;
			}
		}else if(Integer.valueOf(pdfType) == FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT ||
				Integer.valueOf(pdfType) == FddGenerateContractConstant.FDD_TRANSTYPE_PLAN_CRIDET){
			index_x = 340;
			index_y = 505;
			if(creditCompany){
				index_x = 300;
				index_y = 505;
			}
		}
		ImageUtil.markImageByMoreIcon(cardnoIcon, trueSource, output, trueImageName, imageType, degree, index_x, index_y);

		String tenderTrueNameIcon = fileParent + tenderTrueNametmImage;
		//脱敏受让人/投资人 真实姓名（个人显示第一个，企业全部脱敏）
		if(FddGenerateContractConstant.PROTOCOL_TYPE_TENDER == Integer.valueOf(pdfType)){
			index_x = 378;
			index_y = 450;
			if(isTenderConmpany){
				index_x = 348;
				index_y = 450;
			}
		}else if(Integer.valueOf(pdfType) == FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT ||
				Integer.valueOf(pdfType) == FddGenerateContractConstant.FDD_TRANSTYPE_PLAN_CRIDET){
			index_x = 385;
			index_y = 605;
			if(isTenderConmpany){
				index_x = 365;
				index_y = 605;
			}
		}
		ImageUtil.markImageByMoreIcon(tenderTrueNameIcon, trueSource, output, trueImageName, imageType, degree, index_x, index_y);

		cardnoIcon = fileParent + tenderCardNoImage;
		//脱敏受让人/投资人 证件号码
		if(FddGenerateContractConstant.PROTOCOL_TYPE_TENDER == Integer.valueOf(pdfType)){
			index_x = 330;
			index_y = 500;
			if(isTenderConmpany){
				index_x = 294;
				index_y = 500;
			}
		}else if(Integer.valueOf(pdfType) == FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT ||
				Integer.valueOf(pdfType) == FddGenerateContractConstant.FDD_TRANSTYPE_PLAN_CRIDET){
			index_x = 333;
			index_y = 660;
			if(isTenderConmpany){
				index_x = 295;
				index_y = 660;
			}
		}
		ImageUtil.markImageByMoreIcon(cardnoIcon, trueSource, output, trueImageName, imageType, degree, index_x, index_y);


		//获取拼接图片列表
		jointPathList.add(imageSavePath + "/" + trueImageName + ".png");
		for (int i = 0; i < pages-2; i++) {
			jointPathList.add(imageSavePath + "/" + fileName + (i+1) + ".png");
		}
		jointPathList.add(imageSavePath + "/" + signimageName + ".png");
		FileUtil.deltree(imageFilePath);
		FileUtil.deltree(trueImageFilePath);
	}


	/**
	 * 上传脱敏文件
	 * @param upParentDir
	 * @param type
	 * 是否删除上传目录 0：否 1：是
	 * @return
	 */
	private boolean uplodTmImage(String upParentDir, String saveDir, int type) {

		boolean ret = true;

		String ftpIP = systemConfig.getHyjfFtpIp();
		String port = systemConfig.getHyjfFtpPort();
		String basePathImage = systemConfig.getHyjfFtpBasepathImg();
		if(type == 0){//上传pdf
			basePathImage = systemConfig.getHyjfFtpBasepathPdf();
		}
		String password = systemConfig.getHyjfFtpPassword();
		String username = systemConfig.getHyjfFtpUsername();
		try {
			logger.info("----------待上传目录：" + upParentDir);
			File parentDir = new File(upParentDir);
			String upParaFile = parentDir.getParent();
			if(parentDir.isDirectory()){

				logger.info("----------待删除目录：" + upParaFile);
				File[] files = parentDir.listFiles();
				for (File file : files) {
					String fileName = file.getName();
					logger.info("--------循环目录，开始上传文件：" + fileName);
					try(FileInputStream ins = new FileInputStream(file)){
						boolean flag = FavFTPUtil.uploadFile(ftpIP, Integer.valueOf(port), username, password,
								basePathImage, saveDir, fileName, ins);
						if (!flag){
							throw new RuntimeException("上传失败!fileName:" + fileName);
						}
					}catch (Exception e){
						throw new RuntimeException("上传失败!fileName:" + fileName);
					}
				}
			}else{
				String fileName = parentDir.getName();
				logger.info("--------开始上传文件：" + fileName);
				try(FileInputStream ins = new FileInputStream(parentDir)){
					boolean flag = FavFTPUtil.uploadFile(ftpIP, Integer.valueOf(port), username, password,
							basePathImage, saveDir, fileName, ins);
					if (!flag){
						throw new RuntimeException("上传失败!fileName:" + fileName);
					}
				}catch (Exception e){
					throw new RuntimeException("上传失败!fileName:" + fileName);
				}
			}
			if(type == 1){
				//删除原目录
				FileUtil.deltree(upParaFile);
			}

		}catch (Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			ret = false;
		}
		return ret;

	}
}
