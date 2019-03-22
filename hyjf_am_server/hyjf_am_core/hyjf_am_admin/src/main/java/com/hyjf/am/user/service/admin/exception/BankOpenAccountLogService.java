package com.hyjf.am.user.service.admin.exception;

import com.hyjf.am.response.user.OpenAccountEnquiryResponse;
import com.hyjf.am.resquest.admin.OpenAccountEnquiryDefineRequest;
import com.hyjf.am.resquest.user.BankOpenAccountLogRequest;
import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.dao.model.auto.BankOpenAccountLog;
import com.hyjf.am.user.dao.model.customize.OpenAccountEnquiryCustomize;
import com.hyjf.am.user.service.BaseService;

import java.util.List;

/**
 * @version BankOpenAccountLogSrvice, v0.1 2018/8/21 14:41
 * @Author: Zha Daojian
 */
public interface BankOpenAccountLogService extends BaseService {

    /**
    * @author Zha Daojian
    * @date 2018/8/21 18:53
    * @param request
    * @return com.hyjf.am.user.dao.model.customize.OpenAccountEnquiryCustomize
    **/
    OpenAccountEnquiryCustomize accountEnquiry(BankOpenAccountLogRequest request);



    /**
     * 根据手机号或者身份证号码获取掉单用户信息
     *
     * @param mobile,idcard
     * @return java.util.List<com.hyjf.admin.beans.vo.BankOpenAccountLogVO>
     * @author Zha Daojian
     * @date 2018/8/21 13:54
     **/
    List<BankOpenAccountLog> bankOpenAccountLogSelect(String mobile, String idcard);

    /**
     * 根据订单号查询用户的开户记录
     *
     * @param orderId
     * @return java.util.List<com.hyjf.admin.beans.vo.BankOpenAccountLogVO>
     * @author Zha Daojian
     * @date 2018/8/21 13:54
     **/
    BankOpenAccountLog selectBankOpenAccountLogByOrderId(String orderId);

    /**
     * 查询返回的电子账号是否已开户
    * @author Zha Daojian
    * @date 2018/8/23 9:36
    * @param accountId
    * @return java.lang.Boolean
    **/
    Boolean checkAccountByAccountId(String accountId);

    /**
     * 删除用户开户日志
     * @author Zha Daojian
     * @date 2018/8/23 9:36
     * @param userId
     * @return java.lang.Boolean
     **/
    Boolean deleteBankOpenAccountLogByUserId(Integer userId);

    /**
     * 根据USERID查询开户掉单
     * @param userId
     * @return
     */
    List<BankOpenAccountLog> selectBankOpenAccountLogByUserId(Integer userId);

    /**
     * 开户掉单，同步保存开户(User)数据
     * @param requestBean
     * @return
     */
    OpenAccountEnquiryResponse updateUser(OpenAccountEnquiryDefineRequest requestBean);

    int insertUserCard(BankCard bankCard);

}
