/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin.coupon;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.CouponUserBeanRequest;
import com.hyjf.am.resquest.admin.CouponUserRequest;
import com.hyjf.am.trade.dao.mapper.auto.CouponOperationHistoryMapper;
import com.hyjf.am.trade.dao.mapper.auto.CouponUserMapper;
import com.hyjf.am.trade.dao.mapper.customize.coupon.CouponUserCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.CouponOperationHistoryWithBLOBs;
import com.hyjf.am.trade.dao.model.auto.CouponUser;
import com.hyjf.am.trade.dao.model.customize.trade.CouponUserCustomize;
import com.hyjf.am.trade.service.admin.coupon.AdminCouponUserService;
import com.hyjf.common.util.CreateUUID;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 * @version AdminCouponUserServiceImpl, v0.1 2018/7/23 17:00
 */
@Service
public class AdminCouponUserServiceImpl implements AdminCouponUserService {

    @Autowired
    private CouponUserCustomizeMapper couponUserCustomizeMapper;
    @Autowired
    private CouponUserMapper couponUserMapper;
    @Autowired
    private CouponOperationHistoryMapper couponOperationHistoryMapper;

    /**
     * 根据条件获取优惠券用户条数
     *
     * @param request
     * @return
     */
    @Override
    public Integer countCouponUser(CouponUserBeanRequest request) {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("userId", request.getUserId());
        paraMap.put("couponCode", request.getCouponCode());
        paraMap.put("couponUserCode", request.getCouponUserCode());
        paraMap.put("username", request.getUserName());
        paraMap.put("couponType", request.getCouponType());
        paraMap.put("usedFlag", request.getUsedFlag());
        paraMap.put("couponFrom", request.getCouponFrom());
        paraMap.put("couponSource", request.getCouponSource());
        if (StringUtils.isNotEmpty(request.getTimeStartAddSrch())) {
            paraMap.put("timeStartAddSrch", GetDate.getDayStart10(request.getTimeStartAddSrch()));
        }
        if (StringUtils.isNotEmpty(request.getTimeEndAddSrch())) {
            paraMap.put("timeEndAddSrch", GetDate.getDayEnd10(request.getTimeEndAddSrch()));
        }
        if (StringUtils.isNotEmpty(request.getTimeStartSrch())) {
            paraMap.put("timeStartSrch", GetDate.getDayStart10(request.getTimeStartSrch()));
        }
        if (StringUtils.isNotEmpty(request.getTimeEndSrch())) {
            paraMap.put("timeEndSrch", GetDate.getDayEnd10(request.getTimeEndSrch()));
        }
        Integer count = couponUserCustomizeMapper.countCouponUser(paraMap);
        return count;
    }

    /**
     * 查询优惠券用户列表
     *
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public List<CouponUserCustomize> getRecordList(CouponUserBeanRequest request, int offset, int limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("limitStart", offset);
        map.put("limitEnd", limit);
        List<CouponUserCustomize> couponUserCustomizes = couponUserCustomizeMapper.selectCouponUserList(map);
        return couponUserCustomizes;
    }

    /**
     * 根据id删除一条优惠券
     *
     * @param id
     * @return
     */
    @Override
    public int deleteCouponUserById(int id, String remark, String userId) {
        CouponUser couponUser = new CouponUser();
        couponUser.setId(id);
        couponUser.setDeleteContent(remark);
        couponUser.setDelFlag(Integer.parseInt(CustomConstants.FLAG_DELETE));
        this.operationLog(couponUser, CustomConstants.OPERATION_CODE_DELETE, userId);
        int count = couponUserMapper.updateByPrimaryKeySelective(couponUser);
        return count;
    }

    /**
     * 发放一条优惠券
     * @param request
     * @return
     */
    @Override
    public int insertCouponUser(CouponUserRequest request) {
        CouponUser couponUser = new CouponUser();
        couponUser.setEndTime(request.getEndTime());
        couponUser.setUserId(request.getUserId());
        couponUser.setCouponUserCode(request.getCouponUserCode());
        couponUser.setCreateUserId(request.getCreateUserId());
        couponUser.setCreateTime(request.getCreateTime());
        couponUser.setUpdateUserId(request.getUpdateUserId());
        couponUser.setUpdateTime(request.getUpdateTime());
        couponUser.setDelFlag(request.getDelFlag());
        couponUser.setUsedFlag(request.getUsedFlag());
        couponUser.setReadFlag(request.getReadFlag());
        couponUser.setCouponSource(request.getCouponSource());
        couponUser.setAttribute(request.getAttribute());
        couponUser.setChannel(request.getChannel());
        couponUserMapper.insertSelective(couponUser);
        this.operationLog(couponUser,CustomConstants.OPERATION_CODE_INSERT,String.valueOf(request.getCreateUserId()));
        return 1;
    }


    /**
     * 操作履历
     *
     * @param couponTo
     * @param operationCode
     */
    private void operationLog(CouponUser couponTo, int operationCode, String userId) {
        CouponOperationHistoryWithBLOBs co = new CouponOperationHistoryWithBLOBs();
        // 编号
        co.setUuid(CreateUUID.getUUID());
        // 优惠券编号
        co.setCouponCode(couponTo.getCouponCode());
        // 操作编号
        co.setOperationCode(operationCode);
        // 取得上传更新前的数据
        CouponUser recordFrom = couponUserMapper.selectByPrimaryKey(couponTo.getId());
        // 更新，删除的场合
        if (operationCode != CustomConstants.OPERATION_CODE_INSERT) {
            // 更新前的json数据
            co.setOperationContentFrom(JSONObject.toJSONString(recordFrom, true));
        }
        // 更新后的json数据
        co.setOperationContentTo(JSONObject.toJSONString(couponTo, true));

        // 操作者
        co.setCreateUserId(Integer.parseInt(userId));
        // 操作时间
        co.setCreateTime(GetDate.getDate());
        couponOperationHistoryMapper.insertSelective(co);

    }
}
