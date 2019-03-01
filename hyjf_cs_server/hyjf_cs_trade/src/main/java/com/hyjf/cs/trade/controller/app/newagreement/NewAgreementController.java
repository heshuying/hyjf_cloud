/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.app.newagreement;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;
import com.hyjf.am.resquest.trade.CreditTenderRequest;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.TenderToCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.UserHjhInvistDetailCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.util.calculate.CalculatesUtil;
import com.hyjf.cs.trade.bean.newagreement.NewAgreementBean;
import com.hyjf.cs.trade.bean.newagreement.NewAgreementResultBean;
import com.hyjf.cs.trade.bean.newagreement.NewCreditAssignedBean;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.agreement.CreateAgreementService;
import com.hyjf.cs.trade.service.consumer.hgdatareport.nifa.NifaContractEssenceMessageService;
import com.hyjf.cs.trade.service.newagreement.NewAgreementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * APP端协议controller
 * @author libin
 * @version NewAgreementController.java, v0.1 2018年7月25日 下午2:05:17
 */
@Api(tags = "app端-协议接口")
@RestController
@RequestMapping(value = "/hyjf-app/new/agreement")
public class NewAgreementController extends BaseTradeController{
	
	private static final Logger logger = LoggerFactory.getLogger(NewAgreementController.class);
	
    /**债权转让协议前端地址*/
    public static final String TRANS_FER_AGREEMENT_PATH = "/agreement/TransferAgreement";
    
    /**居间服务借款协议前端地址*/
    public static final String SERVICE_LOAN_AGREEMENT_PATH = "/agreement/ServiceLoanAgreement";
    
	@Autowired
	private NewAgreementService agreementService;

   @Autowired
   private CreateAgreementService createAgreementService;

    @Autowired
   private NifaContractEssenceMessageService nifaContractEssenceMessageService;

	@Autowired
    private SystemConfig systemConfig;

    //初始化放款/承接时间(大于2018年3月28号法大大上线时间)
    private static final int ADD_TIME = 1922195200;

    //放款/承接时间(2018-3-28法大大上线时间）
    private static final int ADD_TIME328 = 1522195200;
	
    /**
     * 
     * （一）居间服务借款协议
     * @author libin
     * @param sign
     * @param tenderNid
     * @param borrowNid
     * @param userIdStr
     * @return
     */
    @ApiOperation(value = "居间服务借款协议内容的获取", httpMethod = "POST", notes = "居间服务借款协议内容的获取")
    @ResponseBody
    @GetMapping("/interServiceLoanAgreement")
    public NewAgreementResultBean interServiceLoanAgreement(HttpServletRequest request, HttpServletResponse response) {
    	logger.info("*******************************居间服务借款协议************************************");
        NewAgreementResultBean newAgreementResultBean = new NewAgreementResultBean();
        newAgreementResultBean.setAgreementImages("");
        JSONObject jsonObject = new JSONObject();
        String sign = request.getParameter("sign");
        String tenderNid = request.getParameter("tenderNid");
        String borrowNid = request.getParameter("borrowNid");
        String userIdStr = request.getParameter("userId");
        logger.info("get sign is: {}",sign);
        logger.info("get tenderNid is: {}",tenderNid);
        logger.info("get borrowNid is: {}",borrowNid);
        Integer userId = null;
        try {
            if(StringUtils.isNumeric(userIdStr)){
                userId=Integer.parseInt(userIdStr);
            }else{
                userId = SecretUtil.getUserId(sign);
            }
            /*userId = WebUtils.getUserId(request); */// 用户ID
            if (userId != null && userId.intValue() != 0) {
                jsonObject = this.interServiceLoanAgreement(userId,tenderNid,borrowNid);
                //获取法大大合同url
                /*原代码List<TenderAgreement> tenderAgreements= fddGenerateContractService.selectByExample(tenderNid);*/
                List<TenderAgreementVO> tenderAgreements= agreementService.getTenderAgreementByTenderNid(tenderNid);
                String agreementImages = "";
                if(null != tenderAgreements && tenderAgreements.size() > 0){
                    String imgUrl = tenderAgreements.get(0).getImgUrl();
                    if(StringUtils.isNotBlank(imgUrl) ){
                        /*String basePathImage = PropUtils.getSystem(FddGenerateContractConstant.HYJF_FTP_BASEPATH_IMG);*/
                        //ftp文件路劲
                        String basePathImage = systemConfig.getBasePathImage();
                        if(!basePathImage.endsWith("/")){
                            basePathImage = basePathImage.concat("/");
                        }
                        /*String basePathurl = PropUtils.getSystem(FddGenerateContractConstant.HYJF_FTP_URL);*/
                        //ftp映射路劲
                        String basePathurl = systemConfig.getBasePathurl();
                        if (!basePathurl.endsWith("/")){
                            basePathurl = basePathurl.concat("/");
                        }
                        String url = basePathurl+basePathImage;

                        imgUrl = imgUrl.replaceAll("PDF/", url+"PDF/");
                    }else{
                        imgUrl="";
                    }
                    agreementImages = imgUrl;
                }
                newAgreementResultBean.setAgreementImages(agreementImages);
                newAgreementResultBean.setStatus(BaseResultBeanFrontEnd.SUCCESS);
                newAgreementResultBean.setStatusDesc(BaseResultBeanFrontEnd.SUCCESS_MSG);
                newAgreementResultBean.setInfo(jsonObject);
            } else {
            	logger.info(this.getClass().getName(), "userCreditContractAssign", "用户未登录");
                newAgreementResultBean.setStatus(BaseResultBeanFrontEnd.SUCCESS);
                newAgreementResultBean.setStatusDesc("用户未登录");
                newAgreementResultBean.setInfo(jsonObject);
            }
        } catch (Exception e) {
        	logger.info(this.getClass().getName(), "userCreditContractAssign", "系统异常");
            newAgreementResultBean.setStatus(BaseResultBeanFrontEnd.FAIL);
            newAgreementResultBean.setStatusDesc("系统异常");
            newAgreementResultBean.setInfo(jsonObject);
        }
        logger.info("get newAgreementResultBean is: {}",JSONObject.toJSON(newAgreementResultBean));
        return newAgreementResultBean;
    }
	
    /**
     * 
     *（二）汇计划出借服务协议    已测试
     * @author libin
     * @param request
     * @return
     */
    @ApiOperation(value = "APP端协议接口", notes = "汇计划出借服务协议")
    @ResponseBody
    @GetMapping("/hjhInfo")
    public NewAgreementResultBean hjhInfo(HttpServletRequest request) {
        NewAgreementResultBean newAgreementResultBean = new NewAgreementResultBean();
        newAgreementResultBean.setAgreementImages("");
		String accedeOrderId = request.getParameter("accedeOrderId");
		String sign = request.getParameter("sign");
		logger.info("get sign is: {}",sign);
		logger.info("get accedeOrderId is: {}",accedeOrderId);
		JSONObject jsonObject = new JSONObject();
		try {
		    if (StringUtils.isEmpty(sign)
	                || StringUtils.isEmpty(accedeOrderId)) {
	            newAgreementResultBean.setStatus(BaseResultBeanFrontEnd.SUCCESS);
	            newAgreementResultBean.setStatusDesc(BaseResultBeanFrontEnd.SUCCESS_MSG);
	            newAgreementResultBean.setInfo(jsonObject);
	            return newAgreementResultBean;
	        }
	        if (StringUtils.isNotEmpty(accedeOrderId)) {
	        	//获取法大大合同url---原底层 example.createCriteria().andTenderNidEqualTo(tenderNid);
                //原List<TenderAgreement> tenderAgreements= fddGenerateContractService.selectByExample(accedeOrderId);
	        	//现底层 example.createCriteria().andTenderNidEqualTo(nid);
	        	List<TenderAgreementVO> tenderAgreements= agreementService.getTenderAgreementByTenderNid(accedeOrderId);
                String agreementImages = "";
                if(null != tenderAgreements && tenderAgreements.size() > 0){
                    String imgUrl = tenderAgreements.get(0).getImgUrl();
                    if(StringUtils.isNotBlank(imgUrl) ){
                        //ftp文件路劲
                        String basePathImage = systemConfig.getBasePathImage();
                        if(!basePathImage.endsWith("/")){
                            basePathImage = basePathImage.concat("/");
                        }
                        //ftp映射路劲
                        String basePathurl = systemConfig.getBasePathurl();
                        if (!basePathurl.endsWith("/")){
                           basePathurl = basePathurl.concat("/");
                        }

                        String url = basePathurl+basePathImage;
                        imgUrl = imgUrl.replaceAll("PDF/", url+"PDF/");
                    }else{
                        imgUrl="";
                    }
                    agreementImages = imgUrl;
                }
                newAgreementResultBean.setAgreementImages(agreementImages);
	            Integer userId = SecretUtil.getUserId(sign);
	            logger.info("get userId is: {}",userId);
	            // 1基本信息
	            Map<String, Object> params = new HashMap<String, Object>();
	            params.put("accedeOrderId", accedeOrderId);
	            params.put("userId", userId);
	            /*原UsersInfo userInfo = agreementService.getUsersInfoByUserId(userId);*/
	            UserInfoVO userInfo = this.agreementService.getUsersInfoByUserId(userId);
	            /*原Users users=agreementService.getUsers(userId);*/
	            UserVO users = this.agreementService.getUserByUserId(userId);
	            /*原UserHjhInvistDetailCustomize userHjhInvistDetailCustomize = agreementService.selectUserHjhInvistDetail(params);*/
	            // 方法复用
	            UserHjhInvistDetailCustomizeVO userHjhInvistDetailCustomize = agreementService.selectUserHjhInvistDetail(params);
	            jsonObject.put("accedeOrderId" , accedeOrderId );
	            jsonObject.put("addTime" ,userHjhInvistDetailCustomize.getCountInterestTime());
	            jsonObject.put("truename" , userInfo.getTruename() );
	            jsonObject.put("idcard" , userInfo.getIdcard() );
	            jsonObject.put("username" , users.getUsername() );
	            jsonObject.put("accedeAccount" , userHjhInvistDetailCustomize.getAccedeAccount() );
	            jsonObject.put("planPeriod" , userHjhInvistDetailCustomize.getPlanPeriod() );
	            jsonObject.put("planApr" , userHjhInvistDetailCustomize.getPlanApr() );
	            jsonObject.put("countInterestTime" , userHjhInvistDetailCustomize.getCountInterestTime() );
	            jsonObject.put("quitTime" , userHjhInvistDetailCustomize.getQuitTime() );
	            jsonObject.put("incomeManageMode" , "收益复投" );
	            jsonObject.put("shouldPayTotal" , userHjhInvistDetailCustomize.getShouldPayTotal() );
	            newAgreementResultBean.setStatus(BaseResultBeanFrontEnd.SUCCESS);
	            newAgreementResultBean.setStatusDesc(BaseResultBeanFrontEnd.SUCCESS_MSG);
	            newAgreementResultBean.setInfo(jsonObject);
	        } else {
	            newAgreementResultBean.setStatus(BaseResultBeanFrontEnd.SUCCESS);
	            newAgreementResultBean.setStatusDesc(BaseResultBeanFrontEnd.SUCCESS_MSG);
	            newAgreementResultBean.setInfo(jsonObject);
	        }
        } catch (Exception e) {
        	logger.info(this.getClass().getName(), "userCreditContractAssign", "系统异常");
            newAgreementResultBean.setStatus(BaseResultBeanFrontEnd.FAIL);
            newAgreementResultBean.setStatusDesc("系统异常");
            newAgreementResultBean.setInfo(jsonObject);
        }
		logger.info("get newAgreementResultBean is: {}",JSONObject.toJSON(newAgreementResultBean));
		return newAgreementResultBean;
    }

	/**
     * 
     * （三）债权转让协议   已测试
     * 
     * @author libin
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "APP端协议接口", notes = "债权转让协议")
    @ResponseBody
    @GetMapping("/userCreditContract")
    public NewAgreementResultBean userCreditContract(HttpServletRequest request, HttpServletResponse response) {
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
                // 债转承接信息 使用  债转id  主键查询  债转表 hyjf_hjh_debt_credit_tender 表
                HjhDebtCreditTenderVO hjhDebtCreditTender = this.agreementService.getHjhDebtCreditTenderByPrimaryKey(Integer.parseInt(nid));
                if (hjhDebtCreditTender != null) {
                    //获取承接订单;
                    /*String assignNid =  hjhDebtCreditTender.getAssignOrderId();*/
                    logger.info("我的计划-计划详情-资产列表-协议，债转标号assignNid :{}", hjhDebtCreditTender.getAssignOrderId());
                    // 原代码将移动端传入的 assignNid 当做查询用的 tenderNid  example.createCriteria().andTenderNidEqualTo(nid);
                    List<TenderAgreementVO> tenderAgreements= agreementService.getTenderAgreementByTenderNid(hjhDebtCreditTender.getAssignOrderId());
                    //获取法大大合同url
                    String agreementImages = "";
                    if(null != tenderAgreements && tenderAgreements.size() > 0){
                        String imgUrl = tenderAgreements.get(0).getImgUrl();
                        if(StringUtils.isNotBlank(imgUrl) ){
                        	//ftp文件路劲
                        	/*原String basePathImage = PropUtils.getSystem(FddGenerateContractConstant.HYJF_FTP_BASEPATH_IMG);*/
                        	String basePathImage = systemConfig.getBasePathImage();
                        	if (!basePathImage.endsWith("/")){
                                basePathImage = basePathImage.concat("/");
                            }
                        	//ftp映射路劲
                            /*原String basePathurl = PropUtils.getSystem(FddGenerateContractConstant.HYJF_FTP_URL);*/
                        	String basePathurl = systemConfig.getBasePathurl();
                        	if (!basePathurl.endsWith("/")){
                                basePathurl = basePathurl.concat("/");
                            }

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
                    UserVO creditUser = this.agreementService.getUserByUserId(hjhDebtCreditTender.getCreditUserId());
                    // 承接人信息详情
                    UserInfoVO usersInfo = this.agreementService.getUsersInfoByUserId(hjhDebtCreditTender.getUserId());
                    // 承接人
                    UserVO user = this.agreementService.getUserByUserId(hjhDebtCreditTender.getUserId());
                    // 标的信息
                    BorrowAndInfoVO borrow = this.agreementService.getBorrowByNid(hjhDebtCreditTender.getBorrowNid());
                    // 借款人
                    UserVO borrowUser = this.agreementService.getUserByUserId(borrow.getUserId());
                    // 债转信息
                    /*HjhDebtCredit hjhDebtCredit = this.agreementService.getHjhDebtCreditByCreditNid(hjhDebtCreditTender.getCreditNid());*/
                    // 表 ht_hjh_debt_credit
                    HjhDebtCreditVO hjhDebtCredit = this.agreementService.getHjhDebtCreditByCreditNid(hjhDebtCreditTender.getCreditNid());
                    /*jsonObject.put("addTime", GetDate.times10toStrYYYYMMDD(hjhDebtCreditTender.getCreateTime()));*/
                    // ht_debt_credit_tender 创建时间
                    jsonObject.put("addTime", hjhDebtCreditTender.getCreateTime());
                    // ht_debt_credit 剩余天数
                    jsonObject.put("remainderPeriod", hjhDebtCredit.getRemainDays()+"天");
                    /*jsonObject.put("assignTime", GetDate.times10toStrYYYYMMDD(hjhDebtCredit.getCreateTime()));*/
                    // ht_debt_credit 创建时间
                    jsonObject.put("assignTime", hjhDebtCredit.getCreateTime());
                    // 承接本金
                    jsonObject.put("assignCapital", df.format(hjhDebtCreditTender.getAssignCapital()));
                    // 支付金额
                    jsonObject.put("assignPay", df.format(hjhDebtCreditTender.getAssignPrice()));
                    // 承接订单号
                    jsonObject.put("orderId", hjhDebtCreditTender.getAssignOrderId());
                    // 原标标号
                    jsonObject.put("borrowNid", hjhDebtCreditTender.getBorrowNid());
                    // 借贷总金额
                    jsonObject.put("borrowAccount", df.format(borrow.getAccount()));
                    // 借款利率
                    jsonObject.put("borrowApr", borrow.getBorrowApr()+"%");
                    // 还款方式
                    jsonObject.put("borrowStyle", getBorrowStyle(borrow.getBorrowStyle()));
                    // 借款期限
                    jsonObject.put("borrowPeriod", getBorrowPeriod(borrow.getBorrowStyle(), borrow.getBorrowPeriod()));
                    if(user.getUserId().equals(userId)){
                    	// 真实姓名
                        jsonObject.put("newCreditTruename", usersInfo.getTruename());
                        // 身份证
                        jsonObject.put("newCreditIdcard", usersInfo.getIdcard());
                        // 用户名
                        jsonObject.put("newCreditUsername", user.getUsername());
                    }else{
                        String truename=usersInfo.getTruename();
                        String encryptedTruename=truename.substring(0, 1);
                        for (int i = 0; i < truename.length()-1; i++) {
                            encryptedTruename+="*";
                        }
                        // 加密用户名
                        jsonObject.put("newCreditTruename", encryptedTruename);
                        String idCard=usersInfo.getIdcard();
                        String encryptedIdCard=idCard.substring(0, 4);
                        for (int i = 0; i < idCard.length()-4; i++) {
                            encryptedIdCard+="*";
                        }
                        // 加密身份证
                        jsonObject.put("newCreditIdcard", encryptedIdCard);
                        String userName= user.getUsername();
                        String encryptedUserName=userName.substring(0, 1);
                        for (int i = 0; i < 5; i++) {
                            encryptedUserName+="*";
                        }
                        // 加密用户名
                        jsonObject.put("newCreditUsername", encryptedUserName);  
                    }
                    if(creditUser.getUserId().equals(userId)){
                    	// 用户名
                        jsonObject.put("oldCreditUsername", creditUser.getUsername());
                        // 真实姓名
                        jsonObject.put("oldCreditTruename", creditUserInfo.getTruename());
                        // 身份证号
                        jsonObject.put("oldCreditIdcard", creditUserInfo.getIdcard());
                    }else{
                        String userName= creditUser.getUsername();
                        String encryptedUserName=userName.substring(0, 1);
                        for (int i = 0; i < 5; i++) {
                            encryptedUserName+="*";
                        }
                        // 加密
                        jsonObject.put("oldCreditUsername", encryptedUserName);
                        String truename=creditUserInfo.getTruename();
                        String encryptedTruename=truename.substring(0, 1);
                        for (int i = 0; i < truename.length()-1; i++) {
                            encryptedTruename+="*";
                        }
                        // 加密
                        jsonObject.put("oldCreditTruename", encryptedTruename);
                        String idCard=creditUserInfo.getIdcard();
                        String encryptedIdCard=idCard.substring(0, 4);
                        for (int i = 0; i < idCard.length()-4; i++) {
                            encryptedIdCard+="*";
                        }
                        jsonObject.put("oldCreditIdcard", encryptedIdCard);
                    }
                    if(borrow.getUserId().equals(userId)){
                        
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
                            if (!basePathImage.endsWith("/")){
                                basePathImage = basePathImage.concat("/");
                            }
                            /*原 String basePathurl = PropUtils.getSystem(FddGenerateContractConstant.HYJF_FTP_URL);*/                          //ftp映射路劲
                            String basePathurl = systemConfig.getBasePathurl();
                            if (!basePathurl.endsWith("/")){
                                basePathurl = basePathurl.concat("/");
                            }


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
     * （四）开户协议
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "APP端协议接口", notes = "开户协议")
    @ResponseBody
    @GetMapping("/openAgreement")
    public NewAgreementResultBean openAgreement(HttpServletRequest request, HttpServletResponse response) {
        NewAgreementResultBean newAgreementResultBean = new NewAgreementResultBean();
        String sign = request.getParameter("sign"); // 随机字符串
        logger.info("get sign is: {}",sign);
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isEmpty(sign)) {
            newAgreementResultBean.setStatus(BaseResultBeanFrontEnd.SUCCESS);
            newAgreementResultBean.setStatusDesc(BaseResultBeanFrontEnd.SUCCESS_MSG);
            newAgreementResultBean.setInfo(jsonObject);
            return newAgreementResultBean;
        }
        try {
             // 用户id
            Integer userId = SecretUtil.getUserIdNoException(sign);
            if (userId == null) {
                newAgreementResultBean.setStatus(BaseResultBeanFrontEnd.SUCCESS);
                newAgreementResultBean.setStatusDesc(BaseResultBeanFrontEnd.SUCCESS_MSG);
                newAgreementResultBean.setInfo(jsonObject);
                return newAgreementResultBean;
            }
            /*原Users user = agreementService.getUsers(userId);*/
            UserVO user = this.agreementService.getUserByUserId(userId);
            jsonObject.put("username" , user.getUsername());
            newAgreementResultBean.setStatus(BaseResultBeanFrontEnd.SUCCESS);
            newAgreementResultBean.setStatusDesc(BaseResultBeanFrontEnd.SUCCESS_MSG);
            newAgreementResultBean.setInfo(jsonObject);
        } catch (Exception e) {
        	logger.info(this.getClass().getName(), "userCreditContractAssign", "系统异常");
            newAgreementResultBean.setStatus(BaseResultBeanFrontEnd.FAIL);
            newAgreementResultBean.setStatusDesc("系统异常");
            newAgreementResultBean.setInfo(jsonObject);
        }
        logger.info("get newAgreementResultBean is: {}",JSONObject.toJSON(newAgreementResultBean));
        return newAgreementResultBean;
    }
    
    /**
     * app 我的计划-计划详情-资产列表-协议（转让）列表
     * @return
     */
    @ApiOperation(value = "协议列表内容的获取", httpMethod = "POST", notes = "协议列表内容的获取")
    @ResponseBody
    @GetMapping("/userCreditContractList")
    public NewAgreementResultBean userCreditContractList(HttpServletRequest request) {
        NewAgreementResultBean newAgreementResultBean = new NewAgreementResultBean();
        String sign = request.getParameter("sign"); // 随机字符串
        String version = request.getParameter("version");
        String nid = request.getParameter("nid");
        String borrowType=request.getParameter("borrowType");
        logger.info("get sign is: {}",sign);
        logger.info("get version is: {}",version);
        logger.info("我的计划-计划详情-资产列表-协议，债转id :{}", nid);
        Integer userId=null;
        try {
            // 用户id
           userId = SecretUtil.getUserId(sign);
       } catch (Exception e) {
    	   logger.info(this.getClass().getName(), "userCreditContractAssign", "系统异常");
           newAgreementResultBean.setStatus(BaseResultBeanFrontEnd.FAIL);
           newAgreementResultBean.setStatusDesc("系统异常");
           return newAgreementResultBean;
       }
        if("HJH".equals(borrowType)){
            List<NewAgreementBean> list=new ArrayList<NewAgreementBean>();
            String investOrderId=null;
            // 债转承接信息
            HjhDebtCreditTenderVO hjhDebtCreditTender = this.agreementService.getHjhDebtCreditTenderByAssignOrderId(nid);
            while (hjhDebtCreditTender!=null && investOrderId==null) {
                boolean isAgreements = false;//是否下载债转协议
                List<TenderAgreementVO> tenderAgreementsAss = createAgreementService.selectTenderAgreementByNid(nid);//债权转让协议
                if (tenderAgreementsAss != null && tenderAgreementsAss.size() > 0) {
                    TenderAgreementVO tenderAgreement = tenderAgreementsAss.get(0);
                    Integer fddStatus = tenderAgreement.getStatus();
                    //法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功
                    if (fddStatus.equals(3)) {
                        isAgreements = true;
                    }
                } else {
                    int addTime = (hjhDebtCreditTender.getCreateTime() == null ? 0 : GetDate.getTime10(hjhDebtCreditTender.getCreateTime()));
                    /**
                     * 1.2018年3月28号以后出借（放款时间/承接时间为准）生成的协议(法大大签章协议）如果协议状态不是"下载成功"时 点击下载按钮提示“协议生成中”。
                     * 2.2018年3月28号以前出借（放款时间/承接时间为准）生成的协议(CFCA协议）点击下载CFCA协议。
                     * 3.智投中承接债转，如果债转协议中有2018-3-28之前的，2018-3-28之前承接的下载CFCA债转协议，2018-3-28之后承接的下载法大大债转协议。
                     */
                    if (addTime < ADD_TIME328) {
                        //下载老版本协议
                        isAgreements = true;
                    }
                }
                if (isAgreements) {
                    NewAgreementBean newAgreementBean = new NewAgreementBean("《债权转让协议》" + hjhDebtCreditTender.getAssignOrderDate(),
                            /*PropUtils.getSystem(CustomConstants.HYJF_WEB_URL) +*/
                            systemConfig.getAppFrontHost() +
                                    TRANS_FER_AGREEMENT_PATH + "?nid=" + hjhDebtCreditTender.getId() + "&borrowType=" + borrowType + "&userId=" + userId);

                    list.add(newAgreementBean);
                }
                if (!hjhDebtCreditTender.getInvestOrderId().equals(hjhDebtCreditTender.getSellOrderId())) {
                    // 债转承接信息
                    hjhDebtCreditTender = this.agreementService.getHjhDebtCreditTenderByAssignOrderId(hjhDebtCreditTender.getSellOrderId());
                } else {
                    investOrderId = hjhDebtCreditTender.getInvestOrderId();
                }
            }
            
            /*原 BorrowTender borrowTender=agreementService.getBorrowTenderByNid(investOrderId); 使用 criteria.andNidEqualTo(nid);*/
            //现 获取用户出借信息(方法复用) example.createCriteria().andNidEqualTo(tenderNid);
            List<BorrowTenderVO> tenderList = this.agreementService.getBorrowTenderListByNid(investOrderId);//居间协议
            BorrowTenderVO borrowTender = null;
            if(CollectionUtils.isNotEmpty(tenderList)){
            	borrowTender = tenderList.get(0);
            } 
            if(borrowTender != null){
                boolean isAgreementsBorrow = false;//是否生成居间协议
                List<TenderAgreementVO> tenderAgreementsNid = createAgreementService.selectTenderAgreementByNid(investOrderId);//居间协议
                if(tenderAgreementsNid!=null && tenderAgreementsNid.size()>0){
                    TenderAgreementVO tenderAgreement = tenderAgreementsNid.get(0);
                    Integer fddStatus = tenderAgreement.getStatus();
                    //法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功
                    if(fddStatus.equals(3)){
                        isAgreementsBorrow = true;
                    }
                }else{
                    BorrowRecoverVO borrowRecoverVO = nifaContractEssenceMessageService.selectBorrowRecover(investOrderId);
                    int addTime = (borrowRecoverVO.getCreateTime() == null? 0 : GetDate.getTime10(borrowRecoverVO.getCreateTime()));
                    logger.info("---------------------------------------居间协议addTime:"+addTime);
                    /**
                     * 1.2018年3月28号以后出借（放款时间/承接时间为准）生成的协议(法大大签章协议）如果协议状态不是"下载成功"时 点击下载按钮提示“协议生成中”。
                     * 2.2018年3月28号以前出借（放款时间/承接时间为准）生成的协议(CFCA协议）点击下载CFCA协议。
                     * 3.智投中承接债转，如果债转协议中有2018-3-28之前的，2018-3-28之前承接的下载CFCA债转协议，2018-3-28之后承接的下载法大大债转协议。
                     */
                    if (addTime < ADD_TIME328) {
                        //下载老版本协议
                        isAgreementsBorrow = true;
                    }
                }
                if(isAgreementsBorrow) {
                    NewAgreementBean newAgreementBean = new NewAgreementBean("《居间服务借款协议》",
                            /*PropUtils.getSystem(CustomConstants.HYJF_WEB_URL)+*/
                            systemConfig.getAppFrontHost() +
                                    SERVICE_LOAN_AGREEMENT_PATH + "?tenderNid=" + borrowTender.getNid() +
                                    "&borrowNid=" + borrowTender.getBorrowNid() + "&userId=" + userId);
                    list.add(newAgreementBean);
                    newAgreementResultBean.setStatus(BaseResultBeanFrontEnd.SUCCESS);
                    newAgreementResultBean.setStatusDesc(BaseResultBeanFrontEnd.SUCCESS_MSG);
                    newAgreementResultBean.setList(list);
                }
            }
        }else{
            List<NewAgreementBean> list=new ArrayList<NewAgreementBean>();
            // 原 债转承接信息  criteria.andAssignNidEqualTo(nid);
            /*CreditTender creditTender = this.agreementService.getCreditTenderByCreditNid(nid);*/
            CreditTenderVO creditTender = this.agreementService.getCreditTenderByAssignNid(nid);
            boolean isAgreements = false;//是否承接协议
            List<TenderAgreementVO> tenderAgreementsNid = createAgreementService.selectTenderAgreementByNid(nid);//债权转让协议
            if(tenderAgreementsNid!=null && tenderAgreementsNid.size()>0){
                TenderAgreementVO tenderAgreement = tenderAgreementsNid.get(0);
                Integer fddStatus = tenderAgreement.getStatus();
                //法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功
                if(fddStatus.equals(3)){
                    isAgreements = true;
                }
            }else{
                int addTime = (creditTender.getCreateTime() == null? 0 : GetDate.getTime10(creditTender.getCreateTime()));

                /**
                 * 1.2018年3月28号以后出借（放款时间/承接时间为准）生成的协议(法大大签章协议）如果协议状态不是"下载成功"时 点击下载按钮提示“协议生成中”。
                 * 2.2018年3月28号以前出借（放款时间/承接时间为准）生成的协议(CFCA协议）点击下载CFCA协议。
                 * 3.智投中承接债转，如果债转协议中有2018-3-28之前的，2018-3-28之前承接的下载CFCA债转协议，2018-3-28之后承接的下载法大大债转协议。
                 */
                if (addTime < ADD_TIME328) {
                    logger.info("1---------------------------------------债转承接信息addTime:"+addTime);
                    //下载老版本协议
                    isAgreements = true;
                }
                logger.info("2---------------------------------------债转承接信息addTime:"+addTime);
            }
            if(isAgreements) {
                NewAgreementBean newAgreementBean = new NewAgreementBean("《债权转让协议》",
                        /*PropUtils.getSystem(CustomConstants.HYJF_WEB_URL)+*/
                        systemConfig.getAppFrontHost() +
                                TRANS_FER_AGREEMENT_PATH + "?bidNid=" + creditTender.getBidNid() +
                                "&creditNid=" + creditTender.getCreditNid() +
                                "&creditTenderNid=" + creditTender.getCreditTenderNid() +
                                "&assignNid=" + creditTender.getAssignNid() +
                                "&sign=" + sign +
                                "&borrowType=" + borrowType);
                list.add(newAgreementBean);
            }
            /*原BorrowTender borrowTender=agreementService.getBorrowTenderByNid(creditTender.getCreditTenderNid());  criteria.andNidEqualTo(nid)*/
            //现 获取用户出借信息(方法复用) example.createCriteria().andNidEqualTo(tenderNid);
            List<BorrowTenderVO> tenderList = this.agreementService.getBorrowTenderListByNid(creditTender.getCreditTenderNid());
            BorrowTenderVO borrowTender = null;
            if(CollectionUtils.isNotEmpty(tenderList)){
            	borrowTender = tenderList.get(0);
            } 
            if(borrowTender != null){
                boolean isAgreementsBorrow = false;//是否生成居间协议
                List<TenderAgreementVO> tenderAgreementsBo = createAgreementService.selectTenderAgreementByNid(creditTender.getCreditTenderNid());//居间协议
                if(tenderAgreementsBo!=null && tenderAgreementsBo.size()>0){
                    TenderAgreementVO tenderAgreement = tenderAgreementsBo.get(0);
                    Integer fddStatus = tenderAgreement.getStatus();
                    //法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功
                    if(fddStatus.equals(3)){
                        isAgreementsBorrow = true;
                    }
                }else{
                    BorrowRecoverVO borrowRecoverVO = nifaContractEssenceMessageService.selectBorrowRecover(creditTender.getCreditTenderNid());
                    int addTime = (borrowRecoverVO.getCreateTime() == null? 0 : GetDate.getTime10(borrowRecoverVO.getCreateTime()));
                    logger.info("---------------------------------------居间协议addTime:"+addTime);
                    /**
                     * 1.2018年3月28号以后出借（放款时间/承接时间为准）生成的协议(法大大签章协议）如果协议状态不是"下载成功"时 点击下载按钮提示“协议生成中”。
                     * 2.2018年3月28号以前出借（放款时间/承接时间为准）生成的协议(CFCA协议）点击下载CFCA协议。
                     * 3.智投中承接债转，如果债转协议中有2018-3-28之前的，2018-3-28之前承接的下载CFCA债转协议，2018-3-28之后承接的下载法大大债转协议。
                     */
                    if (addTime < ADD_TIME328) {
                        //下载老版本协议
                        isAgreementsBorrow = true;
                    }
                }
                if(isAgreementsBorrow) {
                    NewAgreementBean newAgreementBean1 = new NewAgreementBean("《居间服务借款协议》",
                            /*PropUtils.getSystem(CustomConstants.HYJF_WEB_URL)+*/
                            systemConfig.getAppFrontHost() +
                                    SERVICE_LOAN_AGREEMENT_PATH + "?tenderNid=" + borrowTender.getNid() +
                                    "&borrowNid=" + borrowTender.getBorrowNid() + "&userId=" + userId);
                    list.add(newAgreementBean1);
                    newAgreementResultBean.setStatus(BaseResultBeanFrontEnd.SUCCESS);
                    newAgreementResultBean.setStatusDesc(BaseResultBeanFrontEnd.SUCCESS_MSG);
                    newAgreementResultBean.setList(list);
                }
            }
        }
        logger.info("get newAgreementResultBean is: {}",JSONObject.toJSON(newAgreementResultBean));
        return newAgreementResultBean;
    }

	/**
	 * 用户中心债转被出借的协议
	 * 
	 * @return
	 */
    public JSONObject selectUserCreditContract(NewCreditAssignedBean tenderCreditAssignedBean,Integer userId) {
    	JSONObject resultMap = new JSONObject();
    	
    	/*获取债转出借信息 start*/
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
    	/*获取债转出借信息 end*/
    	
    	// 获取债转出借信息不为空时继续
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
            BorrowAndInfoVO borrow = this.agreementService.getBorrowByNid(creditTender.getBidNid());
			
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
			creditTender.setCreateTime(creditTender.getCreateTime());
			
			creditTender.setAddIp(GetDate.getDateMyTimeInMillis(creditTender.getAssignRepayEndTime()));// 借用ip字段存储最后还款时间
			resultMap.put("assignCapital", df.format(creditTender.getAssignCapital()));
			resultMap.put("assignPay", df.format(creditTender.getAssignPrice()));
			if (borrow != null) {
				if (borrow.getReverifyTime() != null) {
                    borrow.setReverifyTimeStr(GetDate.getDateMyTimeInMillis(borrow.getReverifyTime()));
				}
				if (borrow.getRepayLastTime() != null) {
                    borrow.setRepayLastTimeStr(GetDate.getDateMyTimeInMillis(borrow.getRepayLastTime()));
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
                if(usersCredit.getUserId()!= null && userId.equals(usersCredit.getUserId())){
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
    
    
    public JSONObject interServiceLoanAgreement(Integer userId, String tenderNid, String borrowNid) {
        JSONObject jsonObject=new JSONObject();
        //原 获取标的信息
/*        BorrowExample borrowExample=new BorrowExample();
        borrowExample.createCriteria().andBorrowNidEqualTo(borrowNid);
        List<Borrow> borrows=borrowMapper.selectByExample(borrowExample);
        if(borrows==null||borrows.size()==0){
            return jsonObject;  
        }
        Borrow borrow=borrows.get(0);*/
        //现 获取标的信息
        BorrowAndInfoVO borrow = this.agreementService.getBorrowByNid(borrowNid);
        
        //原 获取用户出借信息
/*        BorrowTenderExample borrowTenderExample=new BorrowTenderExample();
        borrowTenderExample.createCriteria().andNidEqualTo(tenderNid);
        List<BorrowTender> borrowTenders=borrowTenderMapper.selectByExample(borrowTenderExample);
        if(borrowTenders==null||borrowTenders.size()==0){
            return jsonObject;  
        }
        BorrowTender borrowTender=borrowTenders.get(0);*/
        
        //现 获取用户出借信息(方法复用) example.createCriteria().andNidEqualTo(tenderNid);
        List<BorrowTenderVO> tenderList = this.agreementService.getBorrowTenderListByNid(tenderNid);
        BorrowTenderVO borrowTender = null;
        if(CollectionUtils.isNotEmpty(tenderList)){
        	borrowTender = tenderList.get(0);
        } 
        
        //原 获取借款人信息
/*        UsersInfo borrowUserInfo=getUsersInfoByUserId(borrow.getUserId());
        if(borrowUserInfo==null){
            return jsonObject; 
        }*/
        //现 获取借款人信息
        UserInfoVO borrowUserInfo = this.agreementService.getUsersInfoByUserId(borrow.getUserId());
        if(borrowUserInfo==null){
            return jsonObject; 
        }
        
        //原 获取出借人信息
/*        UsersInfo lendersUserInfo=getUsersInfoByUserId(borrowTender.getUserId());
        if(lendersUserInfo==null){
            return jsonObject; 
        }*/
        //现 获取出借人信息
        UserInfoVO lendersUserInfo = this.agreementService.getUsersInfoByUserId(borrowTender.getUserId());
        if(lendersUserInfo==null){
            return jsonObject; 
        }
        
        // 原 
/*        Users lendersUser=getUsers(borrowTender.getUserId());
        if(lendersUser==null){
            return jsonObject; 
        }*/
        // 现
        UserVO lendersUser = this.agreementService.getUserByUserId(borrowTender.getUserId());
        
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        jsonObject.put("tenderNid", tenderNid);
        try {
            jsonObject.put("signingTime", GetDate.formatDate(GetDate.parseDate(borrowTender.getLoanOrderDate(), "yyyyMMdd"), "yyyy-MM-dd"));
        } catch (Exception e) {
            jsonObject.put("signingTime", "待确认");
        }
        
        if(userId.equals(borrowTender.getUserId())){
            jsonObject.put("lendersTrueName", lendersUserInfo.getTruename());
            jsonObject.put("lendersCredentialNo", lendersUserInfo.getIdcard());
            jsonObject.put("lendersUserName", lendersUser.getUsername());  
        }else{
            String truename=lendersUserInfo.getTruename();
            String encryptedTrueName=truename.substring(0, 1);
            for (int i = 0; i < truename.length()-1; i++) {
                encryptedTrueName+="*";
            }
            jsonObject.put("lendersTrueName", encryptedTrueName);
            String idcode=lendersUserInfo.getIdcard();
            String encryptedIdcode=idcode.substring(0, 4);
            for (int i = 0; i < idcode.length()-4; i++) {
                encryptedIdcode+="*";
            }
            jsonObject.put("lendersCredentialNo", encryptedIdcode);
            String userName=lendersUser.getUsername();

            String encryptedUserName=userName.substring(0, 1);
            for (int i = 0; i < 5; i++) {
                encryptedUserName+="*";
            }
            jsonObject.put("lendersUserName", encryptedUserName);
        }
        jsonObject.put("borrowTrueName", borrowUserInfo.getTruename().substring(0,1)+(borrowUserInfo.getTruename().length()==2?"*":"**"));
        jsonObject.put("borrowAccount", df.format(borrow.getAccount()));
        jsonObject.put("borrowPeriod", getBorrowPeriod(borrow.getBorrowStyle(),borrow.getBorrowPeriod()));
        jsonObject.put("borrowApr", borrow.getBorrowApr().toString()+"%");
        jsonObject.put("borrowStyle", getBorrowStyle(borrow.getBorrowStyle()));
        jsonObject.put("tenderAccount", df.format(borrowTender.getAccount()));
        jsonObject.put("tenderInterest", df.format(borrowTender.getRecoverAccountInterest()));
        return jsonObject;
    }
    
    /**
     * 获得 协议模板pdf显示地址
     * @param aliasName
     * @return
     */
    @ApiOperation(value = "app-获得协议模板pdf显示地址", httpMethod = "POST", notes = "app-获得协议模板pdf显示地址")
    @PostMapping("/getAgreementPdfOrImg")
    public NewAgreementResultBean gotAgreementPdfOrImg(@RequestParam(value="aliasName",required = true) String aliasName) {
        return agreementService.setProtocolImg(aliasName);
    }

    @ApiOperation(value = "app-获得前台展示协议名称", httpMethod = "GET", notes = "app-获得前台展示协议名称")
    @GetMapping("/getdisplayNameDynamic")
    public JSONObject getdisplayNameDynamic() {
        JSONObject jsonObject = agreementService.getdisplayNameDynamicMethod();
        return jsonObject;
    }
    
}
