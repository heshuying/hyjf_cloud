/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.htltrade.impl;

import com.hyjf.am.resquest.user.HtlTradeRequest;
import com.hyjf.am.vo.trade.HtlProductIntoRecordVO;
import com.hyjf.am.vo.trade.HtlProductRedeemVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.service.htltrade.HtlTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: HtlTradeServiceImpl, v0.1 2018/7/25 15:17
 */
@Service
public class HtlTradeServiceImpl extends BaseServiceImpl implements HtlTradeService {

    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AmUserClient amUserClient;

    /**
     * 获得购买列表数
     * @param htlTradeRequest
     * @return
     */
    @Override
    public Integer countHtlIntoRecord(HtlTradeRequest htlTradeRequest) {
        return amTradeClient.countHtlIntoRecord(htlTradeRequest);
    }
    /**
     * 获取购买产品列表
     * @param htlTradeRequest
     * @return
     */
    @Override
    public List<HtlProductIntoRecordVO> getIntoRecordList(HtlTradeRequest htlTradeRequest) {
        List<HtlProductIntoRecordVO> htlProductIntoRecordVOList = amTradeClient.getIntoRecordList(htlTradeRequest);
        if(!CollectionUtils.isEmpty(htlProductIntoRecordVOList)){
            for(HtlProductIntoRecordVO htlProductIntoRecordVO:htlProductIntoRecordVOList){
                UserVO userVO = amUserClient.findUserById(htlProductIntoRecordVO.getUserId());
                htlProductIntoRecordVO.setUsername(userVO.getUsername());
                htlProductIntoRecordVO.setMobile(userVO.getMobile());
                UserVO referee = amUserClient.findUserById(htlProductIntoRecordVO.getReferee());
                htlProductIntoRecordVO.setRefername(referee.getUsername());
            }
        }
        return htlProductIntoRecordVOList;
    }
    /**
     * 获得汇天利转出列表数
     * @param htlTradeRequest
     * @return
     */
    @Override
    public Integer countProductRedeemRecord(HtlTradeRequest htlTradeRequest) {
        return amTradeClient.countProductRedeemRecord(htlTradeRequest);
    }
    /**
     * 获取汇天利转出记录列表(自定义)
     * @param htlTradeRequest
     * @return
     */
    @Override
    public List<HtlProductRedeemVO> getRedeemRecordList(HtlTradeRequest htlTradeRequest) {
        List<HtlProductRedeemVO> htlProductRedeemVOList = amTradeClient.getRedeemRecordList(htlTradeRequest);
        if(!CollectionUtils.isEmpty(htlProductRedeemVOList)){
            for(HtlProductRedeemVO htlProductRedeemVO:htlProductRedeemVOList){
                UserVO userVO = amUserClient.findUserById(htlProductRedeemVO.getUserId());
                htlProductRedeemVO.setUsername(userVO.getUsername());
                htlProductRedeemVO.setMobile(userVO.getMobile());
                UserVO referee = amUserClient.findUserById(htlProductRedeemVO.getReferee());
                htlProductRedeemVO.setRefername(referee.getUsername());
                UserInfoVO userInfoVO = amUserClient.findUserInfoById(htlProductRedeemVO.getUserId());
                htlProductRedeemVO.setTurename(userInfoVO.getTruename());
            }
        }
        return htlProductRedeemVOList;
    }
}
