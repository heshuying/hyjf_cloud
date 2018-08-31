/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.CouponConfigClient;
import com.hyjf.admin.service.CouponConfigService;
import com.hyjf.am.response.admin.CouponConfigCustomizeResponse;
import com.hyjf.am.response.admin.CouponTenderResponse;
import com.hyjf.am.response.trade.CouponConfigResponse;
import com.hyjf.am.response.trade.CouponUserResponse;
import com.hyjf.am.resquest.admin.CouponConfigRequest;
import com.hyjf.am.resquest.admin.CouponUserRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yaoyong
 * @version CouponConfigServiceImpl, v0.1 2018/7/5 11:24
 */
@Service
public class CouponConfigServiceImpl implements CouponConfigService {
    @Autowired
    AmTradeClient amTradeClient;
    @Autowired
    AmConfigClient amConfigClient;
    /**
     * 获取优惠券发行列表
     * @param couponConfigRequest
     * @return
     */
    @Override
    public CouponConfigCustomizeResponse getRecordList(CouponConfigRequest couponConfigRequest) {
        return amTradeClient.getRecordList(couponConfigRequest);
    }

    /**
     * 获取编辑页信息
     * @param couponConfigRequest
     * @return
     */
    @Override
    public CouponConfigResponse getCouponConfig(CouponConfigRequest couponConfigRequest) {
        return amTradeClient.getCouponConfig(couponConfigRequest);
    }

    /**
     * 保存修改信息
     * @param request
     * @return
     */
    @Override
    public CouponConfigResponse saveCouponConfig(CouponConfigRequest request) {
        return amTradeClient.saveCouponConfig(request);
    }

    /**
     * 添加发行信息
     * @param couponConfigRequest
     * @return
     */
    @Override
    public CouponConfigResponse insertAction(CouponConfigRequest couponConfigRequest) {
        return amTradeClient.insertAction(couponConfigRequest);
    }

    /**
     * 删除发行信息
     * @param couponConfigRequest
     * @return
     */
    @Override
    public CouponConfigResponse deleteAction(CouponConfigRequest couponConfigRequest) {
        return amTradeClient.deleteAction(couponConfigRequest);
    }

    /**
     * 获取审核信息
     * @param ccfr
     * @return
     */
    @Override
    public CouponConfigResponse getAuditInfo(CouponConfigRequest ccfr) {
        return amTradeClient.getAuditInfo(ccfr);
    }

    /**
     * 更新审核信息
     * @param couponConfigRequest
     * @return
     */
    @Override
    public CouponConfigResponse updatrAudit(CouponConfigRequest couponConfigRequest) {
        return amTradeClient.updateAuditInfo(couponConfigRequest);
    }

    /**
     * 根据优惠券编号查询
     * @param couponCode
     * @return
     */
    @Override
    public CouponUserResponse getIssueNumber(String couponCode) {
        return amTradeClient.getIssueNumber(couponCode);
    }

    /**
     * 获取项目类别
     * @return
     */
    @Override
    public List<BorrowProjectTypeVO> getCouponProjectTypeList() {
        List<BorrowProjectTypeVO> list=new ArrayList<>();
        BorrowProjectTypeVO borrowProjectType1=new BorrowProjectTypeVO();
        borrowProjectType1.setBorrowCd("1");
        borrowProjectType1.setBorrowName("汇直投");
        list.add(borrowProjectType1);

        BorrowProjectTypeVO borrowProjectType2=new BorrowProjectTypeVO();
        borrowProjectType2.setBorrowCd("2");
        borrowProjectType2.setBorrowName("汇消费");
        list.add(borrowProjectType2);

        BorrowProjectTypeVO borrowProjectType3=new BorrowProjectTypeVO();
        borrowProjectType3.setBorrowCd("3");
        borrowProjectType3.setBorrowName("新手汇");
        list.add(borrowProjectType3);

        BorrowProjectTypeVO borrowProjectType4=new BorrowProjectTypeVO();
        borrowProjectType4.setBorrowCd("4");
        borrowProjectType4.setBorrowName("尊享汇");
        list.add(borrowProjectType4);

//        BorrowProjectType borrowProjectType5=new BorrowProjectType();
//        borrowProjectType5.setBorrowCd("5");
//        borrowProjectType5.setBorrowName("汇添金");
//        list.add(borrowProjectType5);

        BorrowProjectTypeVO borrowProjectType6=new BorrowProjectTypeVO();
        borrowProjectType6.setBorrowCd("6");
        borrowProjectType6.setBorrowName("汇计划");
        list.add(borrowProjectType6);
        return list;
    }

    @Override
    public String getAdminInfoByUserId(String userId) {
        CouponTenderResponse response = amConfigClient.getAdminUserByUserId(userId);
        return response.getAttrbute();
    }
}
