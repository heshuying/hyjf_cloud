/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl.exception;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.mq.FDDUserInfoChangeProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.exception.MobileSynchronizeService;
import com.hyjf.admin.service.impl.BaseAdminServiceImpl;
import com.hyjf.am.resquest.admin.MobileSynchronizeRequest;
import com.hyjf.am.vo.admin.MobileSynchronizeCustomizeVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.GetCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author: sunpeikai
 * @version: MobileSynchronizeServiceImpl, v0.1 2018/8/13 11:50
 */
@Service
public class MobileSynchronizeServiceImpl extends BaseAdminServiceImpl implements MobileSynchronizeService {

    @Autowired
    private FDDUserInfoChangeProducer fddUserInfoChangeProducer;

    /**
     * 查询手机号同步数量  用于前端分页显示
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int countBankOpenAccountUser(MobileSynchronizeRequest request) {
        return amUserClient.countBankOpenAccountUser(request);
    }

    /**
     * 查询手机号同步列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<MobileSynchronizeCustomizeVO> selectBankOpenAccountUserList(MobileSynchronizeRequest request) {
        return amUserClient.selectBankOpenAccountUserList(request);
    }

    /**
     * 同步手机号
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public JSONObject updateMobile(Integer loginUserId,MobileSynchronizeRequest request) {
        // 返回结果
        JSONObject ret = new JSONObject();
        // 电子账户号
        String accountId = request.getAccountId();
        // 用户ID
        String userId = request.getUserId();
        // 用户ID为空
        if (StringUtils.isEmpty(userId)) {
            ret.put("status", "error");
            ret.put("result", "用户ID为空!");
            return ret;
        }
        // 用户电子账户号为空
        if (StringUtils.isEmpty(accountId)) {
            ret.put("status", "error");
            ret.put("result", "电子账户号为空!");
            return ret;
        }
        try {
            AdminSystemVO adminSystemVO = amConfigClient.getUserInfoById(loginUserId);
            request.setAdminSystemVO(adminSystemVO);
            boolean isUpdateFlag = amUserClient.updateMobile(request);
            if (isUpdateFlag) {
                // 加入到消息队列  同步手机后,重新发送CA认证客户信息修改MQ
                Map<String, String> params = new HashMap<String, String>();
                params.put("mqMsgId", GetCode.getRandomCode(10));
                params.put("userId", String.valueOf(userId));
                fddUserInfoChangeProducer.messageSend(new MessageContent(MQConstant.FDD_USERINFO_CHANGE_TOPIC,UUID.randomUUID().toString(),JSONObject.toJSONBytes(params)));

                ret.put("status", "success");
                ret.put("result", "手机号同步成功");
                return ret;
            }else{
                ret.put("status", "error");
                ret.put("result", "手机号同步失败");
                return ret;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
