package com.hyjf.admin.controller.exception.transferexception;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.AdminTransferExceptionLogAPIRequest;
import com.hyjf.admin.beans.vo.AdminTransferExceptionLogAPIVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.AdminTransferExceptionLogService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminTransferExceptionLogResponse;
import com.hyjf.am.vo.admin.TransferExceptionLogVO;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 转账异常
 */
@Api(value = "异常中心-银行转账异常",tags = "异常中心-银行转账异常")
@RestController
@RequestMapping("/hyjf-admin/exception/transferexception")
public class AdminTransferExceptionLogController extends BaseController {

    /** 用户ID */
    private static final String USERID = "userId";
    /** 还款金额 */
    private static final String VAL_BALANCE = "val_balance";
    /** 还款金额(优惠券用) */
    private static final String VAL_AMOUNT = "val_amount";

    private static final String PERMISSIONS = "transferexception";

    @Autowired
    private AdminTransferExceptionLogService transferLogService;

    /**
     * 短信mq生产端
     */
    @Autowired
    private CommonProducer commonProducer;
    /**
     * 引入配置文件
     */
    @Autowired
    private SystemConfig systemConfig;
    
	/**
	 * 画面初始化
	 * @param request
	 * @return
	 */
    @ApiOperation(value = "银行转账异常页面载入", notes = "银行转账异常页面载入")
	@PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<ListResult<AdminTransferExceptionLogAPIVO>> init(@RequestBody AdminTransferExceptionLogAPIRequest request) {
        AdminTransferExceptionLogResponse response = transferLogService.getRecordList(CommonUtils.convertBean(request,TransferExceptionLogVO.class));
        if (response == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }else if(!Response.isSuccess(response)){
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<AdminTransferExceptionLogAPIVO> apiList = CommonUtils.convertBeanList(response.getResultList(), AdminTransferExceptionLogAPIVO.class);
		return new AdminResult<ListResult<AdminTransferExceptionLogAPIVO>>(ListResult.build(apiList, response.getCount()));
	}


	
	/**
	 * 转账确认
	 */
   /* @ApiOperation(value = "转账确认", notes = "转账确认")
	@PostMapping("/transferConfirm")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_TRANSFER_EXCEPTION)
    public String confirmAction(TransferExceptionLogVO request) {
	    if(StringUtils.isEmpty(request.getUuid()) || request.getStatus() == null){
	        return "0";
	    }
	    
	    int result = transferLogService.updateRecordByUUID(request);
	    return result + "";
	}*/
	
	/**
	 * 重新执行转账
	 * @throws Exception 
	 */
    @ApiOperation(value = "重新执行转账", notes = "重新执行转账")
    @PostMapping("/transferAgain")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_TRANSFER_EXCEPTION)
    public AdminResult transferAgainAction(@RequestBody TransferExceptionLogVO request) {
        if(StringUtils.isEmpty(request.getUuid())){
            return new AdminResult<>(FAIL,"参数错误，请稍后再试！");
        }

        //同步块避免重复转账
        synchronized (this.getClass()) {
            TransferExceptionLogVO transfer = transferLogService.getRecordByUUID(request.getUuid());

            if(transfer == null){
                return new AdminResult<>(FAIL,"获取数据失败！");
            }
            if(transfer.getStatus() != 1){
                return new AdminResult<>(FAIL,"状态已更新！");
            }
            if(StringUtils.isEmpty(transfer.getOrderId())){
                String orderId = GetOrderIdUtils.getOrderId2(transfer.getUserId()); 
                transfer.setOrderId(orderId);
                transferLogService.updateRecordByUUID(transfer);
            }
            String merrpAccount = systemConfig.getBANK_MERRP_ACCOUNT();

            BankCallBean bean = new BankCallBean();
            bean.setVersion(BankCallConstant.VERSION_10);// 版本号
            bean.setTxCode(BankCallMethodConstant.TXCODE_VOUCHER_PAY);// 交易代码
            bean.setTxDate(GetOrderIdUtils.getTxDate()); // 交易日期
            bean.setTxTime(GetOrderIdUtils.getTxTime()); // 交易时间
            bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号
            bean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
            bean.setAccountId(merrpAccount);// 电子账号
            bean.setTxAmount(transfer.getTransAmt().toString());
            bean.setForAccountId(transfer.getAccountId());
            bean.setDesLineFlag("1");
            bean.setDesLine(transfer.getOrderId());// add by cwyang 用于红包发放的银行对账依据,对应accountList 表的Nid
            bean.setLogOrderId(transfer.getOrderId());// 订单号
            bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
            bean.setLogUserId(String.valueOf(transfer.getUserId()));
            bean.setLogClient(0);// 平台
            // 调用银行接口
            BankCallBean resultBean = BankCallUtils.callApiBg(bean);
            if (resultBean == null || !BankCallConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
                System.out.println("[转账异常] 重新发起转账失败，失败原因：" + (resultBean !=null ? resultBean.getRetMsg() : ""));
                // 转账失败
                return new AdminResult<>(FAIL,"转账失败！");
            }
            logger.info(this.getClass().getName(), "transferAgainAction", "respcode: " + resultBean.getRetCode());
            
            boolean result;
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("TransferExceptionLogVO",transfer);
                jsonObject.put("BankCallBean",resultBean);
                UserInfoCustomizeVO userInfoCustomize=transferLogService.queryUserInfoByUserId(transfer.getUserId());
                jsonObject.put("UserInfoCustomizeVO",userInfoCustomize);
                CouponRecoverVO recover=transferLogService.getCouponRecover(transfer.getRecoverId());
                jsonObject.put("CouponRecoverVO",recover);
                BorrowTenderCpnVO borrowTender=transferLogService.getCouponTenderInfoByNid(recover.getTenderId());
                jsonObject.put("BorrowTenderCpnVO",borrowTender);
                //网站收支明细记录
                AccountWebListVO accountWebList = new AccountWebListVO();
                // 出借者
                Integer userId=borrowTender.getUserId();
                UserInfoVO userInfo=transferLogService.getUserInfoByUserId(userId);
                if (Validator.isNotNull(userInfo)) {
                    Integer attribute = userInfo.getAttribute();
                    if (Validator.isNotNull(attribute)){
                        // 查找用户的的推荐人
                        SpreadsUserVO spreadsUserVO = transferLogService.getSpreadsUserByUserId(userId);
                        Integer refUserId=null;
                        if (Validator.isNotNull(spreadsUserVO)) {
                            refUserId = spreadsUserVO.getSpreadsUserId();
                        }

                        // 如果是线上员工或线下员工，推荐人的userId和username不插
                        if (refUserId != null && (attribute == 2 || attribute == 3)) {
                            // 查找用户信息
                            //EmployeeCustomize employeeCustomize = employeeCustomizeMapper.selectEmployeeByUserId(userId);
                            EmployeeCustomizeVO employeeCustomize = transferLogService.selectEmployeeByUserId(userId);
                            if (employeeCustomize != null) {
                                accountWebList.setRegionName(employeeCustomize.getRegionName());
                                accountWebList.setBranchName(employeeCustomize.getBranchName());
                                accountWebList.setDepartmentName(employeeCustomize.getDepartmentName());
                            }
                        }
                        // 如果是无主单，全插
                        else if (refUserId != null && (attribute == 1)) {
                            // 查找用户推荐人
                            EmployeeCustomizeVO employeeCustomize = transferLogService.selectEmployeeByUserId(refUserId);
                            if (employeeCustomize != null) {
                                accountWebList.setRegionName(employeeCustomize.getRegionName());
                                accountWebList.setBranchName(employeeCustomize.getBranchName());
                                accountWebList.setDepartmentName(employeeCustomize.getDepartmentName());
                            }
                        }
                        // 如果是有主单
                        else if (refUserId != null && (attribute == 0)) {
                            // 查找用户推荐人
                            EmployeeCustomizeVO employeeCustomize = transferLogService.selectEmployeeByUserId(refUserId);
                            if (employeeCustomize != null) {
                                accountWebList.setRegionName(employeeCustomize.getRegionName());
                                accountWebList.setBranchName(employeeCustomize.getBranchName());
                                accountWebList.setDepartmentName(employeeCustomize.getDepartmentName());
                            }
                        }

                    }else{
                        accountWebList.setTruename(userInfo.getTruename());
                        accountWebList.setFlag(1);
                    }

                }

                jsonObject.put("AccountWebListVO",accountWebList);

                result = transferLogService.transferAfter(jsonObject);
                if(result){
                    List<Map<String, String>> retMsgList = new ArrayList<Map<String, String>>();
                    Map<String, String> msg = new HashMap<String, String>();
                    retMsgList.add(msg);
                    msg.put(USERID, String.valueOf(borrowTender.getUserId()));
                    msg.put(VAL_AMOUNT, transfer.getTransAmt() == null ? "0.00" : transfer.getTransAmt().toString());
                    // 发送短信
                    this.sendSmsCoupon(retMsgList);
                }


            } catch (Exception e) {
                e.printStackTrace();
                return new AdminResult<>(FAIL,"更新失败！");
            }
            logger.info(this.getClass().getName(), "transferAgainAction", "transferAfter result: " + resultBean.getRetCode());
            // 重新转账成功更新异常表状态
            if (result) {
                //更新转账异常表状态
                request.setStatus(CustomConstants.TRANSFER_EXCEPTION_STATUS_YES);
                transferLogService.updateRecordByUUID(request);
                return new AdminResult<>(SUCCESS,"操作成功！");

            } else {
                return new AdminResult<>(FAIL,"更新失败！");
            }
        }
	}

    /**
     * 发送优惠券短信
     * @param msgList
     */
    private void sendSmsCoupon(List<Map<String,String>> msgList) {

        if (CollectionUtils.isNotEmpty(msgList)) {
            for (Map<String, String> msg : msgList) {
                if (Validator.isNotNull(msg.get(USERID)) && NumberUtils.isCreatable(msg.get(USERID)) && Validator.isNotNull(msg.get(VAL_AMOUNT))) {
                    UserVO users = transferLogService.getUserByUserId(Integer.valueOf(msg.get(USERID)));
                    if (users == null || Validator.isNull(users.getMobile()) || (users.getRecieveSms() != null && users.getRecieveSms() == 1)) {
                        return;
                    }
                    SmsMessage smsMessage = new SmsMessage(Integer.valueOf(msg.get(USERID)), msg, null, null, MessageConstant.SMS_SEND_FOR_USER, null,
                            CustomConstants.PARAM_TPL_COUPON_PROFIT, CustomConstants.CHANNEL_TYPE_NORMAL);
                    try {
                        commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(),
                                smsMessage));
                    } catch (MQException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
