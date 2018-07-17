package com.hyjf.admin.service.impl.exception;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.exception.ManualReverseExceptionService;
import com.hyjf.am.resquest.admin.ManualReverseAddRequest;
import com.hyjf.am.resquest.admin.ManualReverseCustomizeRequest;
import com.hyjf.am.vo.admin.ManualReverseCustomizeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 手动冲正
 * @author hesy
 * @version ManualReverseExceptionServiceImpl, v0.1 2018/7/13 14:22
 */
@Service
public class ManualReverseExceptionServiceImpl extends BaseServiceImpl implements ManualReverseExceptionService {
    @Autowired
    AmTradeClient amTradeClient;
    @Autowired
    AmUserClient amUserClient;

    /**
     * 手动冲正列表
     * @param requestBean
     * @return
     */
    @Override
    public List<ManualReverseCustomizeVO> getManualReverseList(ManualReverseCustomizeRequest requestBean) {
        return amTradeClient.getManualReverseList(requestBean);
    }

    /**
     * 手动冲正列表总记录数
     * @param requestBean
     * @return
     */
    @Override
    public int getManualReverseCount(ManualReverseCustomizeRequest requestBean) {
        return amTradeClient.getManualReverseCount(requestBean);
    }

    /**
     * 手动冲正更新
     * @param requestBean
     * @return
     */
    @Override
    public Boolean updateManualReverse(ManualReverseAddRequest requestBean) {
        return amTradeClient.updateManualReverse(requestBean);
    }

    /**
     * 校验请求参数
     * @param requestBean
     * @return
     */
    @Override
    public boolean checkForManualReverse(ManualReverseAddRequest requestBean){
        if(!Validator.isNumber(requestBean.getSeqNo())){
           return false;
        }
        if (StringUtils.isEmpty(requestBean.getUserName()) && StringUtils.isEmpty(requestBean.getAccountId())) {
           return false;
        } else if (StringUtils.isEmpty(requestBean.getUserName()) && StringUtils.isNotEmpty(requestBean.getAccountId())) {
            //通过账号ID获取用户信息
            UserVO userVO = amUserClient.getUserByAccountId(requestBean.getAccountId());
            if (StringUtils.isEmpty(userVO.getUsername())) {
               return false;
            }
            requestBean.setUserName(userVO.getUsername());
        }
        return true;
    }

    /**
     * 根据userName获取电子账号
     * @param userName
     * @return
     */
    @Override
    public String getAccountIdByUserName(String userName){
        BankOpenAccountVO openAccountVO = amUserClient.queryBankOpenAccountByUserName(userName);
        if(openAccountVO == null){
            return "";
        }

        return openAccountVO.getAccount();
    }
}
