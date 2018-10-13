package com.hyjf.cs.user.controller.app.bankopen;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.bean.OpenAccountPageBean;
import com.hyjf.cs.user.controller.BaseUserController;
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
import java.util.Map;

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
    public AppResult<Object> openBankAccount(@RequestHeader(value = "userId") Integer userId, @RequestHeader(value = "sign") String sign, @RequestBody @Valid BankOpenVO bankOpenVO, HttpServletRequest request) {
        logger.info("app openBankAccount start, bankOpenVO is :{}", JSONObject.toJSONString(bankOpenVO));
        AppResult<Object> result = new AppResult<Object>();
        // 验证请求参数
        if (userId == null) {
            throw new ReturnMessageException(MsgEnum.ERR_USER_NOT_LOGIN);
        }
        // 获取登录信息
        UserVO user = bankOpenService.getUsersById(userId);
        // 检查参数
        bankOpenService.checkRequestParam(user, bankOpenVO);
        // 拼装参数 调用江西银行
        // 同步调用路径
        OpenAccountPageBean openBean = new OpenAccountPageBean();

        try {
            PropertyUtils.copyProperties(openBean, bankOpenVO);
        } catch (Exception e) {
            e.printStackTrace();
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