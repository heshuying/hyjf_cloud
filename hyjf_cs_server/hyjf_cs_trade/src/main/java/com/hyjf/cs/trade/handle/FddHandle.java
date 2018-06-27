package com.hyjf.cs.trade.handle;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.resquest.user.CertificateAuthorityRequest;
import com.hyjf.am.resquest.user.LoanSubjectCertificateAuthorityRequest;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.borrow.BorrowManinfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.borrow.BorrowUserVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.CertificateAuthorityVO;
import com.hyjf.am.vo.user.LoanSubjectCertificateAuthorityVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.fdd.fddgeneratecontract.FddGenerateContractBean;
import com.hyjf.cs.trade.client.AmBorrowClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.client.BorrowManinfoClient;
import com.hyjf.cs.trade.client.BorrowTenderClient;
import com.hyjf.cs.trade.client.BorrowUserClient;
import com.hyjf.cs.trade.client.FddClient;

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
	private FddClient fddClient;
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
            throw new Exception("投资记录不存在,投资订单号:[" + tenderNid + "],投资人用户ID:[" + tenderUserId + "],标的编号:[" + borrowNid + "].");
        }

        String tenderTrueName = null;
        String tenderIdCard = null;
        tenderTrueName = tenderUsersInfo.getTruename();
        tenderIdCard = tenderUsersInfo.getIdcard();
       /* if(tenderUser.getUserType() == 1){
            CorpOpenAccountRecord info = getCorpOpenAccountInfoByUserID(tenderUserId);
            tenderTrueName = info.getBusiName();
            tenderIdCard = info.getBusiCode();
        }*/
		
		

	}

	/**
	 * 获取借款主体为个人的CA认证客户编号
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
        if(list != null && list.size()> 0){
            return list.get(0).getCustomerId();
        }

        // 借款主体CA认证记录表
        LoanSubjectCertificateAuthorityRequest request1 = new LoanSubjectCertificateAuthorityRequest();
        request1.setName(borrowManinfo.getName());
        request1.setIdType(0);
        request1.setIdNo(borrowManinfo.getCardNo());
        List<LoanSubjectCertificateAuthorityVO> resultList 
        	= this.amUserClient.getLoanSubjectCertificateAuthorityList(request1);
        
        if (resultList!=null&&resultList.size()>0){
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
        List<LoanSubjectCertificateAuthorityVO> resultList 
        	= this.amUserClient.getLoanSubjectCertificateAuthorityList(request1);

        if (resultList!=null&&resultList.size()>0){
            return resultList.get(0).getCustomerId();
        }
		return null;
	}

	/**
	 * 计划加入
	 * 
	 * @param bean
	 */
	public void planJoinGenerateContract(FddGenerateContractBean bean) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 债转投资
	 * 
	 * @param bean
	 */
	public void creditGenerateContract(FddGenerateContractBean bean) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 计划债转
	 * 
	 * @param bean
	 */
	public void planCreditGenerateContract(FddGenerateContractBean bean) throws Exception {
		// TODO Auto-generated method stub

	}

}
