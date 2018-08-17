package com.hyjf.cs.trade.service.hjh.impl;

import com.hyjf.am.vo.trade.HjhLockVo;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 计划提成计算服务类
 * @author hesy
 * @version HjhCommisionComputeServiceImpl, v0.1 2018/8/17 14:15
 */
@Service
public class HjhCommisionComputeServiceImpl extends BaseTradeServiceImpl implements com.hyjf.cs.trade.service.hjh.HjhCommisionComputeService {

    /**
     * 待计算提成加入列表
     * @return
     */
    @Override
    public List<HjhAccedeVO> getAccedesWaitCompute() {
        return amTradeClient.getAccedesWaitCompute();
    }

    /**
     * 提成计算
     * @param hjhLockVo
     * @return
     */
    @Override
    public Boolean commisionCompute(HjhLockVo hjhLockVo){
        return amTradeClient.commisionCompute(hjhLockVo);
    }

    /**
     * 获取计算bean
     * @param accedeOrderId
     * @return
     */
    @Override
    public HjhLockVo getBeanForCompute(String accedeOrderId) {
        List<HjhAccedeVO> accedeVOS = amTradeClient.selectHjhAccedeListByOrderId(accedeOrderId);
        if(accedeVOS != null){
            HjhAccedeVO hjhAccedeVO = accedeVOS.get(0);
            UserInfoVO inverestUserInfo = amUserClient.selectUserInfoByUserId(hjhAccedeVO.getInviteUserId());

            HashMap map = new HashMap();
            map.put("projectType",2);
            map.put("userType","线上员工");
            Integer pushMoneyOnline = amTradeClient.getCommisionConfig(map);
            map.put("userType","51老用户");
            Integer pushMoney51 = amTradeClient.getCommisionConfig(map);
            Integer commissionUserID = getCommissionUser(hjhAccedeVO, pushMoneyOnline, pushMoney51, inverestUserInfo);
            UserInfoVO commissioUserInfoVO = null;
            if(commissionUserID != null && commissionUserID > 0 ){
                commissioUserInfoVO = amUserClient.selectUserInfoByUserId(commissionUserID);
            }
            BankOpenAccountVO bankOpenAccountVO = amUserClient.selectBankAccountById(commissionUserID);
            List<UserInfoCustomizeVO> userInfoCustomizeVOS = amUserClient.queryDepartmentInfoByUserId(commissionUserID);

            HjhLockVo hjhLockVo = new HjhLockVo();
            hjhLockVo.setAccedeOrderId(accedeOrderId);
            hjhLockVo.setInverestUserInfo(inverestUserInfo);
            hjhLockVo.setCommissioUserInfoVO(commissioUserInfoVO);
            hjhLockVo.setBankOpenAccountVO(bankOpenAccountVO);
            hjhLockVo.setUserInfoCustomizeVOS(userInfoCustomizeVOS);

            return hjhLockVo;
        }
        return null;
    }

    /**
     *
     * 计算提成用户id
     * @author hsy
     * @return
     */
    private Integer getCommissionUser(HjhAccedeVO record, Integer pushMoneyOnline,
                                      Integer pushMoney51, UserInfoVO userInfoInvite) {
        if(pushMoneyOnline == 1 && record.getUserAttribute() !=null && record.getUserAttribute() == 3){
            return record.getUserId();
        }else if(pushMoneyOnline == 1 && record.getInviteUserAttribute() != null && record.getInviteUserAttribute() == 3){
            return record.getInviteUserId();
        }else if(pushMoney51 == 1 && userInfoInvite.getIs51() != null && userInfoInvite.getIs51() == 1 && userInfoInvite.getAttribute() <2){
            return record.getInviteUserId();
        }
        return null;
    }
}
