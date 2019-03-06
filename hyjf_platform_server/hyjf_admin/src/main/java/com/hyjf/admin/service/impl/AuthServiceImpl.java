package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.AuthBean;
import com.hyjf.am.resquest.user.UserAuthRequest;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hesy
 * @version AuthServiceImpl, v0.1 2018/10/15 11:10
 */
@Service
public class AuthServiceImpl extends BaseAdminServiceImpl implements com.hyjf.admin.service.AuthService {
/*    public final static String KEY_PAYMENT_AUTH = "AUTHCONFIG:paymentAuth"; // 缴费授权
    public final static String KEY_REPAYMENT_AUTH = "AUTHCONFIG:repaymentAuth"; // 还款授权
    public final static String KEY_AUTO_TENDER_AUTH = "AUTHCONFIG:autoTenderAuth"; // 自动投标
    public final static String KEY_AUTO_CREDIT_AUTH = "AUTHCONFIG:autoCreditAuth"; // 自动债转
    public final static String KEY_IS_CHECK_USER_ROLES = "CHECKE:ISCHECKUSERROLES"; // 是否校验用户角色*/

    @Override
    public BankCallBean getTermsAuthQuery(int userId, String channel) {
        BankOpenAccountVO bankOpenAccount = getBankOpenAccount(userId);
        // 调用查询出借人签约状态查询
        BankCallBean selectbean = new BankCallBean();
        selectbean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        selectbean.setTxCode(BankCallConstant.TXCODE_TERMS_AUTH_QUERY);
        selectbean.setTxDate(GetOrderIdUtils.getTxDate());
        selectbean.setTxTime(GetOrderIdUtils.getTxTime());
        selectbean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        selectbean.setChannel(channel);
        selectbean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));// 电子账号
        selectbean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        // 操作者ID
        selectbean.setLogUserId(String.valueOf(userId));
        selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        selectbean.setLogClient(0);
        // 返回参数
        BankCallBean retBean = null;
        // 调用接口
        retBean = BankCallUtils.callApiBg(selectbean);
        return retBean;
    }

    @Override
    public boolean checkDefaultConfig(BankCallBean bean, String authType) {
        UserVO user= getUsersById(Integer.parseInt(bean.getLogUserId()));
        // 授权类型
        String txcode = bean.getTxCode();
        if(BankCallConstant.TXCODE_TERMS_AUTH_QUERY.equals(txcode)){
            //自动投标功能开通标志
            String autoBidStatus = bean.getAutoBid();
            //缴费授权
            String paymentAuth = bean.getPaymentAuth();
            //还款授权
            String repayAuth = bean.getRepayAuth();
            switch (authType) {
                case AuthBean.AUTH_TYPE_AUTO_BID:
                    if(StringUtils.isNotBlank(autoBidStatus)&&"1".equals(autoBidStatus)
                            &&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),authType)){
                        HjhUserAuthConfigVO config=this.getAuthConfigFromCache(RedisConstants.KEY_AUTO_TENDER_AUTH);
                        if(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getAutoBidDeadline())){
                            return true;
                        }
                        if(user.getUserType()!=1){
                            if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getAutoBidMaxAmt()))){
                                return true;
                            }
                        }else{
                            if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getAutoBidMaxAmt()))){
                                return true;
                            }
                        }

                    }
                    break;
                case AuthBean.AUTH_TYPE_PAYMENT_AUTH:
                    if(StringUtils.isNotBlank(paymentAuth)&&"1".equals(paymentAuth)
                            &&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),authType)){
                        HjhUserAuthConfigVO config=this.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH);
                        if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getPaymentDeadline())){
                            return true;
                        }
                        if(user.getUserType()!=1){
                            if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getPaymentMaxAmt()))){
                                return true;
                            }
                        }else{
                            if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getPaymentMaxAmt()))){
                                return true;
                            }
                        }
                    }
                    break;
                case AuthBean.AUTH_TYPE_REPAY_AUTH:
                    if(StringUtils.isNotBlank(repayAuth)&&"1".equals(repayAuth)
                            &&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),authType)){
                        HjhUserAuthConfigVO config=this.getAuthConfigFromCache(RedisConstants.KEY_REPAYMENT_AUTH);
                        if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getRepayDeadline())){
                            return true;
                        }
                        if(user.getUserType()!=1){
                            if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getRepayMaxAmt()))){
                                return true;
                            }
                        }else{
                            if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getRepayMaxAmt()))){
                                return true;
                            }
                        }
                    }
                    break;
                case AuthBean.AUTH_TYPE_MERGE_AUTH:
                    if(StringUtils.isNotBlank(autoBidStatus)&&"1".equals(autoBidStatus)
                            &&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),AuthBean.AUTH_TYPE_AUTO_BID)){
                        HjhUserAuthConfigVO config=this.getAuthConfigFromCache(RedisConstants.KEY_AUTO_TENDER_AUTH);
                        if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getAutoBidDeadline())){
                            return true;
                        }
                        if(user.getUserType()!=1){
                            if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getAutoBidMaxAmt()))){
                                return true;
                            }
                        }else{
                            if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getAutoBidMaxAmt()))){
                                return true;
                            }
                        }
                    }
                    if(StringUtils.isNotBlank(paymentAuth)&&"1".equals(paymentAuth)
                            &&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),AuthBean.AUTH_TYPE_PAYMENT_AUTH)){
                        HjhUserAuthConfigVO config=this.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH);
                        if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getPaymentDeadline())){
                            return true;
                        }
                        if(user.getUserType()!=1){
                            if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getPaymentMaxAmt()))){
                                return true;
                            }
                        }else{
                            if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getPaymentMaxAmt()))){
                                return true;
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }else if(BankCallConstant.TXCODE_TERMS_AUTH_PAGE.equals(txcode)){
            //自动投标功能开通标志
            String autoBidStatus = bean.getAutoBid();
            //缴费授权
            String paymentAuth = bean.getPaymentAuth();
            //还款授权
            String repayAuth = bean.getRepayAuth();
            if(StringUtils.isNotBlank(autoBidStatus)&&"1".equals(autoBidStatus)
                    &&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),authType)){
                HjhUserAuthConfigVO config=this.getAuthConfigFromCache(RedisConstants.KEY_AUTO_TENDER_AUTH);
                if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getAutoBidDeadline())){
                    return true;
                }
                if(user.getUserType()!=1){
                    if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getAutoBidMaxAmt()))){
                        return true;
                    }
                }else{
                    if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getAutoBidMaxAmt()))){
                        return true;
                    }
                }
            }
            if(StringUtils.isNotBlank(paymentAuth)&&"1".equals(paymentAuth)
                    &&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),authType)){
                HjhUserAuthConfigVO config=this.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH);
                if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getPaymentDeadline())){
                    return true;
                }
                if(user.getUserType()!=1){
                    if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getPaymentMaxAmt()))){
                        return true;
                    }
                }else{
                    if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getPaymentMaxAmt()))){
                        return true;
                    }
                }
            }
            if(StringUtils.isNotBlank(repayAuth)&&"1".equals(repayAuth)
                    &&!this.checkIsAuth(Integer.parseInt(bean.getLogUserId()),authType)){
                HjhUserAuthConfigVO config=this.getAuthConfigFromCache(RedisConstants.KEY_REPAYMENT_AUTH);
                if(!GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")).equals(bean.getRepayDeadline())){
                    return true;
                }
                if(user.getUserType()!=1){
                    if(!config.getPersonalMaxAmount().equals(Integer.parseInt(bean.getRepayMaxAmt()))){
                        return true;
                    }
                }else{
                    if(!config.getEnterpriseMaxAmount().equals(Integer.parseInt(bean.getRepayMaxAmt()))){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkIsAuth(Integer userId, String txcode) {
        HjhUserAuthVO hjhUserAuth = getHjhUserAuthByUserId(userId);
        String endTime = "";
        int status = 0;
        String nowTime = GetDate.date2Str(new Date(),GetDate.yyyyMMdd);
        // 缴费授权
        if(hjhUserAuth==null){
            return false;
        }
        if (AuthBean.AUTH_TYPE_PAYMENT_AUTH.equals(txcode)) {
            endTime = hjhUserAuth.getAutoPaymentEndTime()==null?"0":hjhUserAuth.getAutoPaymentEndTime();
            status = hjhUserAuth.getAutoPaymentStatus();
        }else if(AuthBean.AUTH_TYPE_REPAY_AUTH.equals(txcode)){
            endTime = hjhUserAuth.getAutoRepayEndTime()==null?"0":hjhUserAuth.getAutoRepayEndTime();
            status = hjhUserAuth.getAutoRepayStatus();
        }else if(AuthBean.AUTH_TYPE_AUTO_BID.equals(txcode)){
            endTime = hjhUserAuth.getAutoBidEndTime()==null?"0":hjhUserAuth.getAutoBidEndTime();
            status = hjhUserAuth.getAutoInvesStatus();
        }else if(AuthBean.AUTH_TYPE_AUTO_CREDIT.equals(txcode)){
            endTime = nowTime+1;
            status = hjhUserAuth.getAutoCreditStatus();
        }

        if(!AuthBean.AUTH_TYPE_MERGE_AUTH.equals(txcode)){
            // 0是未授权
            if (status - 0 == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
                return false;
            }
        }else{
            boolean paymentflag=false;
            boolean invesflag=false;
            boolean creditflag=false;
            endTime = nowTime+1;
            status = hjhUserAuth.getAutoCreditStatus();
            // 0是未授权
            if (status - 0 == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
                creditflag=true;
            }
            endTime = hjhUserAuth.getAutoBidEndTime()==null?"0":hjhUserAuth.getAutoBidEndTime();
            status = hjhUserAuth.getAutoInvesStatus();
            // 0是未授权
            if (status - 0 == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
                invesflag=true;
            }
            endTime = hjhUserAuth.getAutoPaymentEndTime()==null?"0":hjhUserAuth.getAutoPaymentEndTime();
            status = hjhUserAuth.getAutoPaymentStatus();
            // 0是未授权
            if (status - 0 == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
                paymentflag=true;
            }
            if(paymentflag||invesflag||creditflag){
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateUserAuth(Integer userId, BankCallBean retBean, String authType) {
        HjhUserAuthVO hjhUserAuth = this.getHjhUserAuthByUserId(userId);
        UserAuthRequest request=new UserAuthRequest();
        Date nowTime = GetDate.getNowTime();
        String orderId = retBean.getOrderId();
        logger.info("updateUserAuth, userId:" + userId + " orderId:" + orderId);
        if (StringUtils.isNotBlank(orderId)) {
            HjhUserAuthLogVO hjhUserAuthLog = amUserClient.selectByExample(orderId);
            // 更新用户签约授权日志表
            if (hjhUserAuthLog != null) {
                hjhUserAuthLog.setUpdateTime(nowTime);
                hjhUserAuthLog.setUpdateUserId(userId);
                hjhUserAuthLog.setOrderStatus(1);
                hjhUserAuthLog.setAuthCreateTime(GetDate.getNowTime10());
                hjhUserAuthLog.setRemark("授权成功");
                request.setHjhUserAuthLogVO(hjhUserAuthLog);
            }
        }

        if (retBean != null && BankCallConstant.RESPCODE_SUCCESS.equals(retBean.get(BankCallConstant.PARAM_RETCODE))) {
            // 更新user表状态为授权成功
            UserVO user=this.getUsersById(userId);
            if(retBean.getPaymentAuth()!=null){
                user.setPaymentAuthStatus(Integer.parseInt(retBean.getPaymentAuth()));
                request.setUser(user);
            }


            //更新用户签约授权状态信息表
            if(hjhUserAuth==null){
                hjhUserAuth=new HjhUserAuthVO();
                hjhUserAuth.setUserId(user.getUserId());
                // 设置状态
                setAuthType(hjhUserAuth,retBean,authType);

                hjhUserAuth.setUserName(user.getUsername());
                hjhUserAuth.setCreateUserId(user.getUserId());
                hjhUserAuth.setCreateTime(nowTime);
                hjhUserAuth.setUpdateTime(nowTime);
                hjhUserAuth.setUpdateUserId(userId);
                hjhUserAuth.setDelFlag(0);
                request.setHjhUserAuth(hjhUserAuth);
            }else{
                HjhUserAuthVO updateHjhUserAuth=new HjhUserAuthVO();
                updateHjhUserAuth.setUserId(user.getUserId());
                // 设置状态
                setAuthType(updateHjhUserAuth,retBean,authType);
                updateHjhUserAuth.setId(hjhUserAuth.getId());
                updateHjhUserAuth.setUpdateTime(nowTime);
                updateHjhUserAuth.setUpdateUserId(userId);
                request.setHjhUserAuth(updateHjhUserAuth);
            }
        }
        amUserClient.updateUserAuth(request);
    }

    @Override
    public HjhUserAuthVO getHjhUserAuthByUserId(Integer userId) {
        HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthByUserId(userId);
        return hjhUserAuth;
    }

    // 设置状态
    private void setAuthType(HjhUserAuthVO hjhUserAuth, BankCallBean bean, String authType) {
        // 授权类型
        String txcode = bean.getTxCode();
        HjhUserAuthConfigVO config=this.getAuthConfigFromCache(RedisConstants.KEY_AUTO_CREDIT_AUTH);
        if(BankCallConstant.TXCODE_TERMS_AUTH_QUERY.equals(txcode)){
            //自动投标功能开通标志
            String autoBidStatus = bean.getAutoBid();
            //自动债转功能开通标志
            String autoTransfer = bean.getAutoTransfer();
            //缴费授权
            String paymentAuth = bean.getPaymentAuth();
            //还款授权
            String repayAuth = bean.getRepayAuth();
            // 用户ID
            Integer userId = hjhUserAuth.getUserId();
            // 根据用户ID 查询用户信息
            UserVO users = this.getUsersById(userId);

            switch (authType) {
                case AuthBean.AUTH_TYPE_AUTO_BID:
                    if(StringUtils.isNotBlank(autoBidStatus)&&"1".equals(autoBidStatus)){
                        hjhUserAuth.setAutoOrderId(bean.getOrderId());
                        hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
                        hjhUserAuth.setAutoBidTime(GetDate.getNowTime10());
                        hjhUserAuth.setAutoInvesStatus(Integer.parseInt(autoBidStatus));
                        hjhUserAuth.setAutoBidEndTime(bean.getAutoBidDeadline());
                        hjhUserAuth.setInvesMaxAmt(bean.getAutoBidMaxAmt());
                        // add by liuyang 神策数据统计修改 20180927 start
						/*if ("10000000".equals(users.getInstCode())) {
							try {
								SensorsDataBean sensorsDataBean = new SensorsDataBean();
								sensorsDataBean.setUserId(userId);
								// 汇计划授权结果
								sensorsDataBean.setEventCode("plan_auth_result");
								sensorsDataBean.setOrderId(bean.getOrderId());
								// 授权类型
								sensorsDataBean.setAuthType(AuthBean.AUTH_TYPE_AUTO_BID);
								this.sendSensorsDataMQ(sensorsDataBean);
							} catch (Exception e) {
								logger.error(e.getMessage());
							}
						}*/
                        // add by liuyang 神策数据统计修改 20180927 end

                    }
                    break;
                case AuthBean.AUTH_TYPE_AUTO_CREDIT:
                    if(StringUtils.isNotBlank(autoTransfer)&&"1".equals(autoTransfer)){
                        hjhUserAuth.setAutoCreditOrderId(bean.getOrderId());
                        hjhUserAuth.setAutoCreditStatus(Integer.parseInt(autoTransfer));
                        hjhUserAuth.setAutoCreditTime(GetDate.getNowTime10());
                        hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
                        hjhUserAuth.setCreditMaxAmt(config.getPersonalMaxAmount()+"");
                        hjhUserAuth.setAutoCreditEndTime(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));

                        // add by liuyang 神策数据统计修改 20180927 start
						/*if ("10000000".equals(users.getInstCode())) {
							try {
								SensorsDataBean sensorsDataBean = new SensorsDataBean();
								sensorsDataBean.setUserId(userId);
								// 汇计划授权结果
								sensorsDataBean.setEventCode("plan_auth_result");
								sensorsDataBean.setOrderId(bean.getOrderId());
								// 授权类型
								sensorsDataBean.setAuthType(AuthBean.AUTH_TYPE_AUTO_CREDIT);
								this.sendSensorsDataMQ(sensorsDataBean);
							} catch (Exception e) {
								logger.error(e.getMessage());
							}
						}*/
                        // add by liuyang 神策数据统计修改 20180927 end

                    }
                    break;
                case AuthBean.AUTH_TYPE_PAYMENT_AUTH:
                    if(StringUtils.isNotBlank(paymentAuth)&&"1".equals(paymentAuth)){
                        hjhUserAuth.setAutoPaymentStatus(Integer.parseInt(paymentAuth));
                        hjhUserAuth.setAutoPaymentEndTime(bean.getPaymentDeadline());
                        hjhUserAuth.setAutoPaymentTime(GetDate.getNowTime10());
                        hjhUserAuth.setPaymentMaxAmt(bean.getPaymentMaxAmt());

                        // add by liuyang 神策数据统计修改 20180927 start
						/*if ("10000000".equals(users.getInstCode())) {
							try {
								SensorsDataBean sensorsDataBean = new SensorsDataBean();
								sensorsDataBean.setUserId(userId);
								// 事件类型:服务费授权结果
								sensorsDataBean.setEventCode("fee_auth_result");
								sensorsDataBean.setOrderId(bean.getOrderId());
								// 授权类型
								sensorsDataBean.setAuthType(AuthBean.AUTH_TYPE_PAYMENT_AUTH);
								this.sendSensorsDataMQ(sensorsDataBean);
							} catch (Exception e) {
								logger.error(e.getMessage());
							}
						}*/
                        // add by liuyang 神策数据统计修改 20180927 end
                    }
                    break;
                case AuthBean.AUTH_TYPE_REPAY_AUTH:
                    if(StringUtils.isNotBlank(repayAuth)&&"1".equals(repayAuth)){
                        hjhUserAuth.setAutoRepayStatus(Integer.parseInt(repayAuth));
                        hjhUserAuth.setAutoRepayEndTime(bean.getRepayDeadline());
                        hjhUserAuth.setAutoRepayTime(GetDate.getNowTime10());
                        hjhUserAuth.setRepayMaxAmt(bean.getRepayMaxAmt());
                    }
                    break;
                case AuthBean.AUTH_TYPE_MERGE_AUTH:
                    if(StringUtils.isNotBlank(autoBidStatus)&&"1".equals(autoBidStatus)){
                        hjhUserAuth.setAutoOrderId(bean.getOrderId());
                        hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
                        hjhUserAuth.setAutoBidTime(GetDate.getNowTime10());
                        hjhUserAuth.setAutoInvesStatus(Integer.parseInt(autoBidStatus));
                        hjhUserAuth.setAutoBidEndTime(bean.getAutoBidDeadline());
                        hjhUserAuth.setInvesMaxAmt(bean.getAutoBidMaxAmt());
                    }
                    if(StringUtils.isNotBlank(autoTransfer)&&"1".equals(autoTransfer)){

                        hjhUserAuth.setAutoCreditOrderId(bean.getOrderId());
                        hjhUserAuth.setAutoCreditStatus(Integer.parseInt(autoTransfer));
                        hjhUserAuth.setAutoCreditTime(GetDate.getNowTime10());
                        hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
                        hjhUserAuth.setCreditMaxAmt(config.getPersonalMaxAmount()+"");
                        hjhUserAuth.setAutoCreditEndTime(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
                    }
                    if(StringUtils.isNotBlank(paymentAuth)&&"1".equals(paymentAuth)){
                        hjhUserAuth.setAutoPaymentStatus(Integer.parseInt(paymentAuth));
                        hjhUserAuth.setAutoPaymentEndTime(bean.getPaymentDeadline());
                        hjhUserAuth.setAutoPaymentTime(GetDate.getNowTime10());
                        hjhUserAuth.setPaymentMaxAmt(bean.getPaymentMaxAmt());
                    }

                    // add by liuyang 神策数据统计修改 20180927 start
					/*if ("10000000".equals(users.getInstCode())) {
						try {
							SensorsDataBean sensorsDataBean = new SensorsDataBean();
							sensorsDataBean.setUserId(userId);
							// 汇计划授权结果
							sensorsDataBean.setEventCode("plan_auth_result");
							sensorsDataBean.setOrderId(bean.getOrderId());
							// 授权类型
							sensorsDataBean.setAuthType(AuthBean.AUTH_TYPE_MERGE_AUTH);
							this.sendSensorsDataMQ(sensorsDataBean);
						} catch (Exception e) {
							logger.error(e.getMessage());
						}
					}*/
                    // add by liuyang 神策数据统计修改 20180927 end
                    break;
                default:
                    break;
            }

        }else if(BankCallConstant.TXCODE_TERMS_AUTH_PAGE.equals(txcode)){
            //自动投标功能开通标志
            String autoBidStatus = bean.getAutoBid();
            //自动债转功能开通标志
            String autoTransfer = bean.getAutoTransfer();
            //缴费授权
            String paymentAuth = bean.getPaymentAuth();
            //还款授权
            String repayAuth = bean.getRepayAuth();
            if(StringUtils.isNotBlank(autoBidStatus)){
                hjhUserAuth.setAutoInvesStatus(Integer.parseInt(autoBidStatus));
                hjhUserAuth.setAutoOrderId(bean.getOrderId());
                hjhUserAuth.setAutoBidTime(GetDate.getNowTime10());
                hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
                hjhUserAuth.setAutoBidEndTime(bean.getAutoBidDeadline());
                hjhUserAuth.setInvesMaxAmt(bean.getAutoBidMaxAmt());
            }
            if(StringUtils.isNotBlank(autoTransfer)){
                hjhUserAuth.setAutoCreditStatus(Integer.parseInt(autoTransfer));
                hjhUserAuth.setAutoCreditOrderId(bean.getOrderId());
                hjhUserAuth.setAutoCreditTime(GetDate.getNowTime10());
                hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
                hjhUserAuth.setCreditMaxAmt(bean.getAutoCreditMaxAmt());
                hjhUserAuth.setAutoCreditEndTime(GetDate.date2Str(GetDate.countDate(1,config.getAuthPeriod()),new SimpleDateFormat("yyyyMMdd")));
            }
            if(StringUtils.isNotBlank(paymentAuth)){
                hjhUserAuth.setAutoPaymentStatus(Integer.parseInt(paymentAuth));
                hjhUserAuth.setAutoPaymentEndTime(bean.getPaymentDeadline());
                hjhUserAuth.setPaymentMaxAmt(bean.getPaymentMaxAmt());
                hjhUserAuth.setAutoPaymentTime(GetDate.getNowTime10());
            }
            if(StringUtils.isNotBlank(repayAuth)){
                hjhUserAuth.setAutoRepayStatus(Integer.parseInt(repayAuth));
                hjhUserAuth.setAutoRepayEndTime(bean.getRepayDeadline());
                hjhUserAuth.setRepayMaxAmt(bean.getRepayMaxAmt());
                hjhUserAuth.setAutoRepayTime(GetDate.getNowTime10());
            }
        }

    }

    /**
     * 从redis里面获取授权配置
     * @param key
     * @return
     */
    public static HjhUserAuthConfigVO getAuthConfigFromCache(String key){
        HjhUserAuthConfigVO hjhUserAuthConfig=RedisUtils.getObj(key,HjhUserAuthConfigVO.class);
        return hjhUserAuthConfig;
    }
}
