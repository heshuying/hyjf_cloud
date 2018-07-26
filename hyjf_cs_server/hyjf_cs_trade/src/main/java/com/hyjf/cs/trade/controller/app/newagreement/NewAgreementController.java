/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.app.newagreement;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.CreditTenderRequest;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.TenderToCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.util.calculate.CalculatesUtil;
import com.hyjf.cs.trade.bean.BaseResultBeanFrontEnd;
import com.hyjf.cs.trade.bean.newagreement.NewAgreementResultBean;
import com.hyjf.cs.trade.bean.newagreement.NewCreditAssignedBean;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.BankWithdrawService;
import com.hyjf.cs.trade.service.newagreement.NewAgreementService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author libin
 * @version NewAgreementController.java, v0.1 2018年7月25日 下午2:05:17
 */
@Api(description = "APP端协议接口")
@RestController
@RequestMapping(value = "/hyjf-app/new/agreement")
public class NewAgreementController extends BaseTradeController{
	
	private static final Logger logger = LoggerFactory.getLogger(NewAgreementController.class);
	
	@Autowired
	private NewAgreementService agreementService;
	
    @Autowired
    BankWithdrawService bankWithdrawService;
	
	@Autowired
    SystemConfig systemConfig;
	
	/**
     * 
     * （三）债权转让协议
     * 
     * @author libin
     * @param request
     * @param response
     * @param appTenderCreditAssignedBean
     * @return
     */
    @ApiOperation(value = "APP端协议接口", notes = "债权转让协议")
    @ResponseBody
    @PostMapping("/userCreditContract")
    public NewAgreementResultBean userCreditContract(HttpServletRequest request, HttpServletResponse response ) {
    	logger.info("*******************************债权转让协议************************************");
    	NewAgreementResultBean newAgreementResultBean = new NewAgreementResultBean();
        newAgreementResultBean.setAgreementImages("");
        JSONObject jsonObject = new JSONObject();
        String borrowType = request.getParameter("borrowType");
        try {
            if(borrowType!=null&&"HJH".equals(borrowType)){
                String userId = request.getParameter("userId"); // 随机字符串
                // 注意：nid只是跟移动端约定这么定义，实际上这个nid是 ht_hjh_debt_credit_tender 的 id 主键
                String nid = request.getParameter("nid");
                if (StringUtils.isEmpty(userId)) {
                    userId="0";
                }
                logger.info("get userId is: {}",userId);
                logger.info("我的计划-计划详情-资产列表-协议，债转id :{}", nid);
                // 线程不安全,这里只是取值,暂用
                DecimalFormat df = CustomConstants.DF_FOR_VIEW;
                // 债转承接信息
                HjhDebtCreditTenderVO hjhDebtCreditTender = this.agreementService.getHjhDebtCreditTenderByPrimaryKey(Integer.parseInt(nid));
                if (hjhDebtCreditTender != null) {
                    //获取承接订单;
                    String assignNid =  hjhDebtCreditTender.getAssignOrderId();
                    logger.info("我的计划-计划详情-资产列表-协议，债转标号assignNid :{}", assignNid);
                    // 原代码将移动端传入的 assignNid 当做查询用的 tenderNid  example.createCriteria().andTenderNidEqualTo(nid);
                    List<TenderAgreementVO> tenderAgreements= agreementService.getTenderAgreementByTenderNid(assignNid);
                    //获取法大大合同url
                    String agreementImages = "";
                    if(null != tenderAgreements && tenderAgreements.size() > 0){
                        String imgUrl = tenderAgreements.get(0).getImgUrl();
                        if(StringUtils.isNotBlank(imgUrl) ){
                        	//ftp文件路劲
                        	/*原String basePathImage = PropUtils.getSystem(FddGenerateContractConstant.HYJF_FTP_BASEPATH_IMG);*/
                        	String basePathImage = systemConfig.getBasePathImage();
                        	//ftp映射路劲
                            /*原String basePathurl = PropUtils.getSystem(FddGenerateContractConstant.HYJF_FTP_URL);*/
                        	String basePathurl = systemConfig.getBasePathurl();
                            String url = basePathurl+basePathImage;
                            imgUrl = imgUrl.replaceAll("PDF/", url+"PDF/");
                        }else{
                            imgUrl="";
                        }
                        agreementImages = imgUrl;
                    }
                    // 转让人信息详情
                    UserInfoVO creditUserInfo = this.agreementService.getUsersInfoByUserId(hjhDebtCreditTender.getCreditUserId());
                    // 转让人
                    UserVO creditUser = this.bankWithdrawService.getUserByUserId(hjhDebtCreditTender.getCreditUserId());
                    // 承接人信息详情
                    UserInfoVO usersInfo = this.agreementService.getUsersInfoByUserId(hjhDebtCreditTender.getUserId());
                    // 承接人
                    UserVO user = this.agreementService.getUserByUserId(hjhDebtCreditTender.getUserId());
                    // 标的信息
                    BorrowVO borrow = this.agreementService.getBorrowByNid(hjhDebtCreditTender.getBorrowNid());
                    // 借款人
                    UserVO borrowUser = this.agreementService.getUserByUserId(borrow.getUserId());
                    // 债转信息
                    /*HjhDebtCredit hjhDebtCredit = this.agreementService.getHjhDebtCreditByCreditNid(hjhDebtCreditTender.getCreditNid());*/
                    HjhDebtCreditVO hjhDebtCredit = this.agreementService.getHjhDebtCreditByCreditNid(hjhDebtCreditTender.getCreditNid());
                    /*jsonObject.put("addTime", GetDate.times10toStrYYYYMMDD(hjhDebtCreditTender.getCreateTime()));*/
                    jsonObject.put("addTime", hjhDebtCreditTender.getCreateTime());
                    jsonObject.put("remainderPeriod", hjhDebtCredit.getRemainDays()+"天");
                    /*jsonObject.put("assignTime", GetDate.times10toStrYYYYMMDD(hjhDebtCredit.getCreateTime()));*/
                    jsonObject.put("assignTime", hjhDebtCredit.getCreateTime());
                    jsonObject.put("assignCapital", df.format(hjhDebtCreditTender.getAssignCapital()));
                    jsonObject.put("assignPay", df.format(hjhDebtCreditTender.getAssignPrice()));
                    jsonObject.put("orderId", hjhDebtCreditTender.getAssignOrderId());
                    jsonObject.put("borrowNid", hjhDebtCreditTender.getBorrowNid());
                    jsonObject.put("borrowAccount", df.format(borrow.getAccount()));
                    jsonObject.put("borrowApr", borrow.getBorrowApr()+"%");
                    jsonObject.put("borrowStyle", getBorrowStyle(borrow.getBorrowStyle()));
                    jsonObject.put("borrowPeriod", getBorrowPeriod(borrow.getBorrowStyle(), borrow.getBorrowPeriod()));
                    if(user.getUserId().equals(new Integer(userId))){
                        jsonObject.put("newCreditTruename", usersInfo.getTruename());
                        jsonObject.put("newCreditIdcard", usersInfo.getIdcard());
                        jsonObject.put("newCreditUsername", user.getUsername());
                    }else{
                        String truename=usersInfo.getTruename();
                        String encryptedTruename=truename.substring(0, 1);
                        for (int i = 0; i < truename.length()-1; i++) {
                            encryptedTruename+="*";
                        }
                        jsonObject.put("newCreditTruename", encryptedTruename);
                        String idCard=usersInfo.getIdcard();
                        String encryptedIdCard=idCard.substring(0, 4);
                        for (int i = 0; i < idCard.length()-4; i++) {
                            encryptedIdCard+="*";
                        }
                        jsonObject.put("newCreditIdcard", encryptedIdCard);
                        String userName= user.getUsername();
                        String encryptedUserName=userName.substring(0, 1);
                        for (int i = 0; i < 5; i++) {
                            encryptedUserName+="*";
                        }
                        jsonObject.put("newCreditUsername", encryptedUserName);  
                    }
                    if(creditUser.getUserId().equals(new Integer(userId))){
                        jsonObject.put("oldCreditUsername", creditUser.getUsername());
                        jsonObject.put("oldCreditTruename", creditUserInfo.getTruename());
                        jsonObject.put("oldCreditIdcard", creditUserInfo.getIdcard());
                    }else{
                        String userName= creditUser.getUsername();
                        String encryptedUserName=userName.substring(0, 1);
                        for (int i = 0; i < 5; i++) {
                            encryptedUserName+="*";
                        }
                        jsonObject.put("oldCreditUsername", encryptedUserName);
                        String truename=creditUserInfo.getTruename();
                        String encryptedTruename=truename.substring(0, 1);
                        for (int i = 0; i < truename.length()-1; i++) {
                            encryptedTruename+="*";
                        }
                        jsonObject.put("oldCreditTruename", encryptedTruename);
                        String idCard=creditUserInfo.getIdcard();
                        String encryptedIdCard=idCard.substring(0, 4);
                        for (int i = 0; i < idCard.length()-4; i++) {
                            encryptedIdCard+="*";
                        }
                        jsonObject.put("oldCreditIdcard", encryptedIdCard);
                    }
                    if(borrow.getUserId().equals(new Integer(userId))){
                        
                        jsonObject.put("borrowUsername", borrowUser.getUsername());  
                    } else {
                        String userName= borrowUser.getUsername();
                        String encryptedUserName=userName.substring(0, 1);
                        for (int i = 0; i < 5; i++) {
                            encryptedUserName+="*";
                        }
                        jsonObject.put("borrowUsername", encryptedUserName);
                    }
                    newAgreementResultBean.setAgreementImages(agreementImages);
                    newAgreementResultBean.setInfo(jsonObject);
                    
                }
                logger.info("get newAgreementResultBean is: {}",JSONObject.toJSON(newAgreementResultBean));
                return newAgreementResultBean;
            }else{
                String sign = request.getParameter("sign");
                String bidNid = request.getParameter("bidNid");
                String creditNid = request.getParameter("creditNid");
                String creditTenderNid = request.getParameter("creditTenderNid");
                String assignNid = request.getParameter("assignNid");
                NewCreditAssignedBean appTenderCreditAssignedBean=new NewCreditAssignedBean();
                appTenderCreditAssignedBean.setBidNid(bidNid);
                appTenderCreditAssignedBean.setCreditNid(creditNid);
                appTenderCreditAssignedBean.setCreditTenderNid(creditTenderNid);
                appTenderCreditAssignedBean.setAssignNid(assignNid);
                logger.info("get appTenderCreditAssignedBean is: {}",JSONObject.toJSON(appTenderCreditAssignedBean));
                // 获取用户id
                try {
                    if (StringUtils.isEmpty(sign)
                            || StringUtils.isEmpty(bidNid)|| StringUtils.isEmpty(creditNid)
                            || StringUtils.isEmpty(creditTenderNid)|| StringUtils.isEmpty(assignNid)) {
                        newAgreementResultBean.setStatus(BaseResultBeanFrontEnd.SUCCESS);
                        newAgreementResultBean.setStatusDesc(BaseResultBeanFrontEnd.SUCCESS_MSG);
                        newAgreementResultBean.setInfo(jsonObject);
                        return newAgreementResultBean;
                    }
                    Integer userId = SecretUtil.getUserId(sign);
                    //获取承接订单;
                    /*List<TenderAgreement> tenderAgreements= fddGenerateContractService.selectByExample(assignNid);*/
                    List<TenderAgreementVO> tenderAgreements= agreementService.getTenderAgreementByTenderNid(assignNid);
                    //获取法大大合同url
                    String agreementImages = "";
                    if(null != tenderAgreements && tenderAgreements.size() > 0){
                        String imgUrl = tenderAgreements.get(0).getImgUrl();
                        if(StringUtils.isNotBlank(imgUrl) ){
                            /*原 String basePathImage = PropUtils.getSystem(FddGenerateContractConstant.HYJF_FTP_BASEPATH_IMG);*/                          // ftp文件路劲
                            String basePathImage = systemConfig.getBasePathImage();
                            /*原 String basePathurl = PropUtils.getSystem(FddGenerateContractConstant.HYJF_FTP_URL);*/                          //ftp映射路劲
                            String basePathurl = systemConfig.getBasePathurl();
                            String url = basePathurl+basePathImage;
                            imgUrl = imgUrl.replaceAll("PDF/", url+"PDF/");
                        }else{
                            imgUrl="";
                        }
                        agreementImages = imgUrl;
                        newAgreementResultBean.setAgreementImages(agreementImages);
                    }
                    if (userId != null && userId.intValue() != 0) {
                        if (StringUtils.isEmpty(appTenderCreditAssignedBean.getBidNid()) || StringUtils.isEmpty(appTenderCreditAssignedBean.getCreditNid())
                                || StringUtils.isEmpty(appTenderCreditAssignedBean.getCreditTenderNid()) || StringUtils.isEmpty(appTenderCreditAssignedBean.getAssignNid())) {
                            newAgreementResultBean.setStatus(BaseResultBeanFrontEnd.SUCCESS);
                            newAgreementResultBean.setStatusDesc(BaseResultBeanFrontEnd.SUCCESS_MSG);
                            newAgreementResultBean.setInfo(jsonObject);
                            return newAgreementResultBean;
                        }
                        jsonObject = this.selectUserCreditContract(appTenderCreditAssignedBean,userId);
                        newAgreementResultBean.setInfo(jsonObject);
                    } else {
                    	logger.info(this.getClass().getName(), "userCreditContract", "用户未登录");
                        newAgreementResultBean.setStatus(BaseResultBeanFrontEnd.SUCCESS);
                        newAgreementResultBean.setStatusDesc("用户未登录");
                        newAgreementResultBean.setInfo(jsonObject);
                    }
                } catch (Exception e) {
                	logger.info(this.getClass().getName(), "userCreditContract", "系统异常");
                    newAgreementResultBean.setStatus(BaseResultBeanFrontEnd.FAIL);
                    newAgreementResultBean.setStatusDesc(BaseResultBeanFrontEnd.FAIL_MSG);
                    newAgreementResultBean.setInfo(jsonObject);
                }
                logger.info("get newAgreementResultBean is: {}",JSONObject.toJSON(newAgreementResultBean));
                return newAgreementResultBean;
            }
            
        } catch (Exception e) {
        	logger.info(this.getClass().getName(), "userCreditContractAssign", "系统异常");
            newAgreementResultBean.setStatus(BaseResultBeanFrontEnd.FAIL);
            newAgreementResultBean.setStatusDesc("系统异常");
            newAgreementResultBean.setInfo(jsonObject);
        }
        return newAgreementResultBean;
    }
    
    
    
	/**
	 * 用户中心债转被投资的协议
	 * 
	 * @return
	 */
    public JSONObject selectUserCreditContract(NewCreditAssignedBean tenderCreditAssignedBean,Integer userId) {
    	JSONObject resultMap = new JSONObject();
    	
    	/*获取债转投资信息 start*/
    	List<CreditTenderVO> creditTenderList = null;
    	// 初始化查询bean(方法复用)
    	CreditTenderRequest request = new CreditTenderRequest();
    	if(StringUtils.isNotEmpty(tenderCreditAssignedBean.getAssignNid()) &&
    			StringUtils.isNotEmpty(tenderCreditAssignedBean.getBidNid()) &&
    			StringUtils.isNotEmpty(tenderCreditAssignedBean.getCreditNid()) &&
    			StringUtils.isNotEmpty(tenderCreditAssignedBean.getCreditTenderNid())){
    		
            request.setAssignNid(tenderCreditAssignedBean.getAssignNid());
            request.setBidNid(tenderCreditAssignedBean.getBidNid());
            request.setCreditNid(tenderCreditAssignedBean.getCreditNid());
            request.setCreditTenderNid(tenderCreditAssignedBean.getCreditTenderNid());
    		
            resultMap.put("orderId", tenderCreditAssignedBean.getAssignNid());
            
            creditTenderList = this.agreementService.getCreditTenderList(request);
    	} else {
    		 resultMap.put("errorMsg", "参数为空！");
    	}
    	/*获取债转投资信息 end*/
    	
    	// 获取债转投资信息不为空时继续
		if (creditTenderList != null && creditTenderList.size() > 0) {
		    DecimalFormat df = CustomConstants.DF_FOR_VIEW;
			CreditTenderVO creditTender = creditTenderList.get(0);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("creditNid", creditTender.getCreditNid());
			params.put("assignNid", creditTender.getAssignNid());
			/*原 List<TenderToCreditDetailCustomize> tenderToCreditDetailList = tenderCreditCustomizeMapper.selectWebCreditTenderDetail(params);*/
			List<TenderToCreditDetailCustomizeVO> tenderToCreditDetailList = this.agreementService.selectWebCreditTenderDetailForContract(params);
			if (tenderToCreditDetailList != null && tenderToCreditDetailList.size() > 0) {
				if (tenderToCreditDetailList.get(0).getCreditRepayEndTime() != null) {
					tenderToCreditDetailList.get(0).setCreditRepayEndTime(GetDate.getDateMyTimeInMillis(Integer.parseInt(tenderToCreditDetailList.get(0).getCreditRepayEndTime())));
				}
				if (tenderToCreditDetailList.get(0).getCreditTime() != null) {
					try {
						tenderToCreditDetailList.get(0).setCreditTime(GetDate.formatDate(GetDate.parseDate(tenderToCreditDetailList.get(0).getCreditTime(), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd"));
					} catch (Exception e) {
					    tenderToCreditDetailList.get(0).setCreditTime(tenderToCreditDetailList.get(0).getCreditTime());
					}
				}
				try {
				    resultMap.put("addTime", GetDate.formatDate(GetDate.parseDate(creditTender.getAssignCreateDate()+"", "yyyyMMdd"), "yyyy-MM-dd"));
				} catch (Exception e) {
				    resultMap.put("addTime", creditTender.getAssignCreateDate());
                }
				resultMap.put("remainderPeriod", tenderToCreditDetailList.get(0).getCreditTerm()+"天， "+
				        tenderToCreditDetailList.get(0).getCreditTime()+" 起，至 "+tenderToCreditDetailList.get(0).getCreditRepayEndTime()+" 止");
				resultMap.put("assignTime", tenderToCreditDetailList.get(0).getCreditTime());
			}
			
			// 原 获取借款标的信息
/*			BorrowExample borrowExample = new BorrowExample();
			BorrowExample.Criteria borrowCra = borrowExample.createCriteria();
			borrowCra.andBorrowNidEqualTo(creditTender.getBidNid());
			List<Borrow> borrow = this.borrowMapper.selectByExample(borrowExample);*/
            // 现 获取借款标的信息
            BorrowVO borrow = this.agreementService.getBorrowByNid(creditTender.getBidNid());
			
			// 原 获取承接人身份信息
/*			UsersInfoExample usersInfoExample = new UsersInfoExample();
			UsersInfoExample.Criteria usersInfoCra = usersInfoExample.createCriteria();
			usersInfoCra.andUserIdEqualTo(creditTender.getUserId());
			List<UsersInfo> usersInfo = this.usersInfoMapper.selectByExample(usersInfoExample);*/
            // 现  获取承接人身份信息
			UserInfoVO usersInfo = this.agreementService.getUsersInfoByUserId(creditTender.getUserId());
			
			// 原 获取承接人平台信息
/*			UsersExample usersExample = new UsersExample();
			UsersExample.Criteria usersCra = usersExample.createCriteria();
			usersCra.andUserIdEqualTo(creditTender.getUserId());
			List<Users> users = this.usersMapper.selectByExample(usersExample);*/
			// 现 获取承接人平台信息
			UserVO users = this.agreementService.getUserByUserId(creditTender.getUserId());
	
			// 原 获取融资方平台信息
/*			UsersExample usersBorrowExample = new UsersExample();
			UsersExample.Criteria usersBorrowCra = usersBorrowExample.createCriteria();
			usersBorrowCra.andUserIdEqualTo(borrow.get(0).getUserId());
			List<Users> usersBorrow = this.usersMapper.selectByExample(usersBorrowExample);*/
			// 现 获取融资方平台信息
			UserVO usersBorrow = this.agreementService.getUserByUserId(borrow.getUserId());
	
			// 原 获取债转人身份信息
/*			UsersInfoExample usersInfoExampleCredit = new UsersInfoExample();
			UsersInfoExample.Criteria usersInfoCraCredit = usersInfoExampleCredit.createCriteria();
			usersInfoCraCredit.andUserIdEqualTo(creditTender.getCreditUserId());
			List<UsersInfo> usersInfoCredit = this.usersInfoMapper.selectByExample(usersInfoExampleCredit);*/
			// 现 获取债转人身份信息
			UserInfoVO usersInfoCredit = this.agreementService.getUsersInfoByUserId(creditTender.getCreditUserId());
			
			// 原 获取债转人平台信息
/*			UsersExample usersExampleCredit = new UsersExample();
			UsersExample.Criteria usersCraCredit = usersExampleCredit.createCriteria();
			usersCraCredit.andUserIdEqualTo(creditTender.getCreditUserId());
			List<Users> usersCredit = this.usersMapper.selectByExample(usersExampleCredit);*/
			// 现 获取债转人平台信息
			UserVO usersCredit = this.agreementService.getUserByUserId(creditTender.getCreditUserId());
			
			
			// 将int类型时间转成字符串
			/*creditTender.setAddTime(GetDate.times10toStrYYYYMMDD(Integer.valueOf(creditTender.getAddTime())));*/
			creditTender.setAddTime(creditTender.getAddTime());
			
			creditTender.setAddip(GetDate.getDateMyTimeInMillis(creditTender.getAssignRepayEndTime()));// 借用ip字段存储最后还款时间
			resultMap.put("assignCapital", df.format(creditTender.getAssignCapital()));
			resultMap.put("assignPay", df.format(creditTender.getAssignPrice()));
			if (borrow != null) {
				if (borrow.getReverifyTime() != null) {
					borrow.setReverifyTime(GetDate.getDateMyTimeInMillis(Integer.parseInt(borrow.getReverifyTime())));
				}
				if (borrow.getRepayLastTime() != null) {
					borrow.setRepayLastTime(GetDate.getDateMyTimeInMillis(Integer.parseInt(borrow.getRepayLastTime())));
				}
				resultMap.put("borrowNid", borrow.getBorrowNid());
				resultMap.put("borrowAccount", df.format(borrow.getAccount()));
				
				resultMap.put("borrowApr", borrow.getBorrowApr()+"%");
				resultMap.put("borrowStyle", getBorrowStyle(borrow.getBorrowStyle()));
				resultMap.put("borrowPeriod", getBorrowPeriod(borrow.getBorrowStyle(), borrow.getBorrowPeriod())+
				        "，"+ borrow.getReverifyTime()+" 起，至  "+
				        borrow.getRepayLastTime()+" 止");
			}
			
		    if (usersInfo != null) {
		        if(userId.equals(usersInfo.getUserId())){
	                resultMap.put("newCreditTruename", usersInfo.getTruename());
	                resultMap.put("newCreditIdcard", usersInfo.getIdcard());
		        } else {
		            String truename=usersInfo.getTruename();
                    String encryptedTruename=truename.substring(0, 1);
                    for (int i = 0; i < truename.length()-1; i++) {
                        encryptedTruename+="*";
                    }
                    resultMap.put("newCreditTruename", encryptedTruename);
                    String idCard=usersInfo.getIdcard();
                    String encryptedIdCard=idCard.substring(0, 4);
                    for (int i = 0; i < idCard.length()-4; i++) {
                        encryptedIdCard+="*";
                    }
                    resultMap.put("newCreditIdcard", encryptedIdCard);
		        }
            }
            if (users != null) {
                if(userId.equals(users.getUserId())){
                    resultMap.put("newCreditUsername", users.getUsername());
                }else{
                    String userName= users.getUsername();
                    String encryptedUserName=userName.substring(0, 1);
                    for (int i = 0; i < 5; i++) {
                        encryptedUserName+="*";
                    }
                    resultMap.put("newCreditUsername", encryptedUserName);
                }
            }

            if (usersCredit != null) {
                if(userId.equals(usersCredit.getUserId())){
                    resultMap.put("oldCreditUsername", usersCredit.getUsername());
                }else{
                    String userName= usersCredit.getUsername();
                    String encryptedUserName=userName.substring(0, 1);
                    for (int i = 0; i < 5; i++) {
                        encryptedUserName+="*";
                    }
                    resultMap.put("oldCreditUsername", encryptedUserName);
                }
                
            }
            if (usersInfoCredit != null) {
                if(userId.equals(usersCredit.getUserId())){
                    resultMap.put("oldCreditTruename", usersInfoCredit.getTruename());
                    resultMap.put("oldCreditIdcard", usersInfoCredit.getIdcard());
                }else{
                    String truename=usersInfoCredit.getTruename();
                    String encryptedTruename=truename.substring(0, 1);
                    for (int i = 0; i < truename.length()-1; i++) {
                        encryptedTruename+="*";
                    }
                    resultMap.put("oldCreditTruename", encryptedTruename);
                    String idCard=usersInfoCredit.getIdcard();
                    String encryptedIdCard=idCard.substring(0, 4);
                    for (int i = 0; i < idCard.length()-4; i++) {
                        encryptedIdCard+="*";
                    }
                    resultMap.put("oldCreditIdcard", encryptedIdCard);
                }
            }
			
			if (usersBorrow != null) {
			    if(userId.equals(usersBorrow.getUserId())){
			        resultMap.put("borrowUsername", usersBorrow.getUsername());
			    } else {
			        String userName= usersBorrow.getUsername();
                    String encryptedUserName=userName.substring(0, 1);
                    for (int i = 0; i < 5; i++) {
                        encryptedUserName+="*";
                    }
			        resultMap.put("borrowUsername", encryptedUserName);
			    }
			}
			
		}else{
		    resultMap.put("addTime", "");
            resultMap.put("remainderPeriod", "");
            resultMap.put("assignTime", "");
            resultMap.put("assignCapital", "");
            resultMap.put("assignPay", "");
            resultMap.put("orderId", "");
            resultMap.put("borrowNid", "");
            resultMap.put("borrowAccount", "");
            resultMap.put("borrowApr", "");
            resultMap.put("borrowStyle", "");
            resultMap.put("borrowPeriod", "");
            resultMap.put("newCreditTruename", "");
            resultMap.put("newCreditIdcard", "");
            resultMap.put("borrowUsername", "");
            resultMap.put("newCreditUsername", "");
            resultMap.put("oldCreditUsername", "");
            resultMap.put("oldCreditTruename", "");
            resultMap.put("oldCreditIdcard", "");
		}
		return resultMap;
    }
    
    private String getBorrowStyle(String borrowStyle) {
        switch (borrowStyle) {
        case CalculatesUtil.STYLE_END:// 还款方式为”按月计息，到期还本还息“
            return "按月计息，到期还本还息";
        case CalculatesUtil.STYLE_ENDDAY:// 还款方式为”按天计息，到期还本还息“；
            return "按天计息，到期还本还息";
        case CalculatesUtil.STYLE_ENDMONTH:// 还款方式为”先息后本“；
            return "先息后本";
        case CalculatesUtil.STYLE_MONTH:// 还款方式为”等额本息“；
            return "等额本息";
        case CalculatesUtil.STYLE_PRINCIPAL:// 还款方式为”等额本金“；
            return "等额本金";
        default:
            return "按月计息，到期还本还息";
        }
    }
    
    private String getBorrowPeriod(String borrowStyle, Integer borrowPeriod) {
        if(CalculatesUtil.STYLE_ENDDAY.equals(borrowStyle)){
            return borrowPeriod+"天";
        }else{
            return borrowPeriod+"个月";
        }
    }
    
}
