package com.hyjf.cs.user.controller.app.bankopen;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.admin.UserOperationLogEntityVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.UserOperationLogConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.bean.OpenAccountPageBean;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.service.bankopen.BankOpenService;
import com.hyjf.cs.user.vo.BankOpenVO;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author sunss
 */
@Api(value = "app端用户开户",tags = "app端-用户开户")
@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/hyjf-app/user/open")
public class AppBankOpenController extends BaseUserController {

    @Autowired
    private BankOpenService bankOpenService;
    @Autowired
    CommonProducer commonProducer;

    /**
     * 获取开户信息
     *
     * @return
     * @Author: sunss
     */
    @ApiOperation(value = "获取开户信息", notes = "获取开户信息")
    @PostMapping(value = "/userInfo")
    @ResponseBody
    public Map userInfo(@RequestHeader(value = "userId", required = false) Integer userId, HttpServletRequest request) {
        logger.info("app openAccount userInfo start, userId is :{}", userId);
        Map<String,String> result = new HashedMap();
        UserVO userVO = bankOpenService.getUsersById(userId);
        if (userVO != null) {
            logger.info("app openAccount userInfo, success, userId is :{}", userVO.getUserId());
            String mobile = userVO.getMobile();
            if (StringUtils.isEmpty(mobile)) {
                mobile = "";
            }
            // 合规审批 用以区分企业用户或者个人用户已达到企业用户跳转开户指南画面 add by huanghui start
            result.put("userType", String.valueOf(userVO.getUserType()));
            // 合规审批 用以区分企业用户或者个人用户已达到企业用户跳转开户指南画面 add by huanghui end
            result.put("phone",mobile);
            result.put("status","000");
            result.put("statusDesc","操作成功");
        } else {
            logger.error("openAccount userInfo failed...");
            result.put("status","99");
            result.put("statusDesc","操作失败");
        }
        return result;
    }

    @ApiOperation(value = "用户开户", notes = "用户开户")
    @PostMapping(value = "/openBankAccount")
    @ResponseBody
    public AppResult<Object> openBankAccount(@RequestHeader(value = "userId") Integer userId, @RequestHeader(value = "sign") String sign, @Valid BankOpenVO bankOpenVO, HttpServletRequest request) {
        logger.info("app openBankAccount start, bankOpenVO is :{}", JSONObject.toJSONString(bankOpenVO));
        AppResult<Object> result = new AppResult<Object>();
        // 验证请求参数
        if (userId == null) {
            throw new ReturnMessageException(MsgEnum.ERR_USER_NOT_LOGIN);
        }
        // 获取登录信息
        UserVO user = bankOpenService.getUsersById(userId);
        UserInfoVO userInfoVO = bankOpenService.getUserInfo(userId);
        
        // modify by libin start
        if(userInfoVO==null){
        	logger.error("使用"+ userId + "查询用户详情信息为空！");
        	throw new ReturnMessageException(MsgEnum.ERR_USER_NOT_LOGIN);
        }
        if(userInfoVO.getRoleId()==null){
        	logger.error("该用户" + userId + "角色为空！");
        	throw new ReturnMessageException(MsgEnum.ERR_USER_NOT_LOGIN);
        }
        // modify by libin end
       
        // 检查参数
        bankOpenService.checkRequestParam(user, bankOpenVO);

        UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
        userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE3);
        userOperationLogEntity.setIp(GetCilentIP.getIpAddr(request));
        userOperationLogEntity.setPlatform(Integer.valueOf(bankOpenVO.getPlatform()));
        userOperationLogEntity.setRemark("");
        userOperationLogEntity.setOperationTime(new Date());
        userOperationLogEntity.setUserName(user.getUsername());
        userOperationLogEntity.setUserRole(String.valueOf(userInfoVO.getRoleId()));
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.USER_OPERATION_LOG_TOPIC, UUID.randomUUID().toString(), userOperationLogEntity));
        } catch (MQException e) {
            logger.error("保存用户日志失败", e);
        }
        // 拼装参数 调用江西银行
        // 同步调用路径
        OpenAccountPageBean openBean = new OpenAccountPageBean();

        try {
            PropertyUtils.copyProperties(openBean, bankOpenVO);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        openBean.setChannel(BankCallConstant.CHANNEL_APP);
        openBean.setUserId(user.getUserId());
        openBean.setIp(CustomUtil.getIpAddr(request));
        openBean.setClientHeader(ClientConstants.CLIENT_HEADER_APP);
        // 开户角色
        openBean.setIdentity(BankCallConstant.ACCOUNT_USER_IDENTITY_1);
        // 组装调用江西银行的MV
        Map<String,Object> data = bankOpenService.getOpenAccountMV(openBean,sign);
        result.setData(data);
        //保存开户日志  银行卡号不必传了
        int uflag = this.bankOpenService.updateUserAccountLog(user.getUserId(), user.getUsername(), openBean.getMobile(), openBean.getOrderId(), bankOpenVO.getPlatform(), openBean.getTrueName(), openBean.getIdNo(), "", "");
        if (uflag == 0) {
            logger.error("App端保存开户日志失败,手机号:[" + openBean.getMobile() + "],用户ID:[" + user.getUserId() + "]");
            throw new ReturnMessageException(MsgEnum.ERR_SYSTEM_UNUSUAL);
        }
        logger.info("app端开户end");
        result.setStatus("000");
        return result;
    }

    /**
     * @Description 查询开户失败原因
     * @Author sunss
     */
    @ApiOperation(value = "开户查询开户失败原因", notes = "查询开户失败原因")
    @PostMapping("/seachFiledMess")
    @ResponseBody
    public AppResult<Object> seachFiledMess(@RequestHeader(value = "userId") int userId,@RequestParam("logOrdId") String logOrdId) {
        logger.info("查询开户失败原因start,logOrdId:{}", logOrdId);
        WebResult<Object> result = bankOpenService.getFiledMess(logOrdId,userId);
        AppResult<Object> appResult = new AppResult<>();
        appResult.setData(result.getData());
        appResult.setStatus(result.getStatus());
        appResult.setStatusDesc(result.getStatusDesc());
        return appResult;
    }
}