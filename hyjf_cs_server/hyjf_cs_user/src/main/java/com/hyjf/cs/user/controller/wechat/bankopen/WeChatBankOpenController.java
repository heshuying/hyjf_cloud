package com.hyjf.cs.user.controller.wechat.bankopen;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.common.bean.result.WeChatResult;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author sunss
 */
@Api(value = "微信端用户开户",tags = "weChat端-用户开户")
@RestController
@RequestMapping("/hyjf-wechat/wx/user")
public class WeChatBankOpenController extends BaseUserController {
    private static final Logger logger = LoggerFactory.getLogger(WeChatBankOpenController.class);

    @Autowired
    private BankOpenService bankOpenService;

    /**
     * 获取开户信息
     *
     * @return
     * @Author: sunss
     */
    @ApiOperation(value = "微信端获取开户信息", notes = "微信端获取开户信息")
    @GetMapping(value = "/initopen")
    @ResponseBody
    public Map userInfo(@RequestHeader(value = "userId") int userId) {
        logger.info("openAccount userInfo start, userId is :{}", userId);
        Map<String,String> result = new HashedMap();
        UserVO userVO = bankOpenService.getUsersById(userId);
        if (userVO != null) {
            logger.info("app openAccount userInfo, success, userId is :{}", userVO.getUserId());
            String mobile = userVO.getMobile();
            if (StringUtils.isEmpty(mobile)) {
                mobile = "";
            }
            result.put("mobile",mobile);
            result.put("status","000");
            result.put("statusDesc","操作成功");
        } else {
            logger.error("openAccount userInfo failed...");
            result.put("status","99");
            result.put("statusDesc","操作失败");
        }
        return result;
    }

    @ApiOperation(value = "微信端用户开户", notes = "微信端用户开户")
    @PostMapping(value = "/open")
    @ResponseBody
    public WeChatResult openBankAccount(@RequestHeader(value = "userId") Integer userId,  @RequestHeader(value = "sign") String sign,@RequestBody @Valid BankOpenVO bankOpenVO, HttpServletRequest request) {
        logger.info("wechat openBankAccount start, bankOpenVO is :{}", JSONObject.toJSONString(bankOpenVO));
        WeChatResult reuslt = new WeChatResult();
        bankOpenVO.setUserId(userId);
        // 获取登录信息
        UserVO user = bankOpenService.getUsersById(userId);

        // 检查参数
        bankOpenService.checkRequestParam(user, bankOpenVO);
        // 拼装参数 调用江西银行
        // 同步调用路径
        OpenAccountPageBean openBean = new OpenAccountPageBean();
        logger.info("bean对象拷贝");
        try {
            PropertyUtils.copyProperties(openBean, bankOpenVO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        openBean.setChannel(BankCallConstant.CHANNEL_WEI);
        openBean.setUserId(user.getUserId());
        openBean.setIp(CustomUtil.getIpAddr(request));
        openBean.setClientHeader(ClientConstants.CLIENT_HEADER_WX);
        // 开户角色
        openBean.setIdentity(BankCallConstant.ACCOUNT_USER_IDENTITY_1);
        openBean.setPlatform(ClientConstants.WECHAT_CLIENT+"");
        // 组装调用江西银行的MV
        logger.info("组装调用江西银行的MV");
        Map<String,Object> data = bankOpenService.getOpenAccountMV(openBean,sign);
        reuslt.setObject(data);
        //保存开户日志  银行卡号不必传了
        int uflag = this.bankOpenService.updateUserAccountLog(user.getUserId(), user.getUsername(), openBean.getMobile(), openBean.getOrderId(), CustomConstants.CLIENT_WECHAT, openBean.getTrueName(), openBean.getIdNo(), "");
        if (uflag == 0) {
            logger.info("保存开户日志失败,手机号:[" + openBean.getMobile() + "],用户ID:[" + user.getUserId() + "]");
            throw new ReturnMessageException(MsgEnum.ERR_SYSTEM_UNUSUAL);
        }
        logger.info("开户end");
        return reuslt;
    }

}