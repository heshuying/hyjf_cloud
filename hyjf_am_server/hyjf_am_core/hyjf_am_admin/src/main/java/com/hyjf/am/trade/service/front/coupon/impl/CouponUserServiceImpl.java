/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.coupon.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.admin.mq.base.CommonProducer;
import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.am.resquest.admin.AdminCouponUserRequestBean;
import com.hyjf.am.resquest.admin.CouponUserBeanRequest;
import com.hyjf.am.resquest.admin.CouponUserRequest;
import com.hyjf.am.resquest.trade.CouponUserSearchRequest;
import com.hyjf.am.trade.dao.mapper.auto.CouponOperationHistoryMapper;
import com.hyjf.am.trade.dao.mapper.auto.CouponUserMapper;
import com.hyjf.am.trade.dao.mapper.customize.CouponUserCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.CouponUserListCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.CouponConfig;
import com.hyjf.am.trade.dao.model.auto.CouponOperationHistoryWithBLOBs;
import com.hyjf.am.trade.dao.model.auto.CouponUser;
import com.hyjf.am.trade.dao.model.auto.CouponUserExample;
import com.hyjf.am.trade.dao.model.customize.CouponUserCustomize;
import com.hyjf.am.trade.dao.model.customize.CouponUserListCustomize;
import com.hyjf.am.trade.service.front.coupon.CouponUserService;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CreateUUID;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version CouponUserServiceImpl, v0.1 2018/6/19 19:13
 */
@Service
public class CouponUserServiceImpl implements CouponUserService {

    private static final Logger logger = LoggerFactory.getLogger(CouponUserServiceImpl.class);

    @Autowired
    private CouponUserMapper couponUserMapper;
    @Autowired
    private CouponUserCustomizeMapper couponUserCustomizeMapper;
    @Autowired
    private CouponUserListCustomizeMapper couponUserListCustomizeMapper;
    @Autowired
    private CouponOperationHistoryMapper couponOperationHistoryMapper;
    @Autowired
    private CommonProducer commonProducer;


    @Override
    public List<CouponUser> selectCouponUser(int nowBeginDate, int nowEndDate) {

        int BeginDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf));
        int EndDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf, 1));

        //两天后
        int towBeginDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf, 2));
        int towEndDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf, 3));

        //三天后
        int threeBeginDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf, 3));
        int threeEndDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf, 4));

        //七天后
        int sevenBeginDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf, 7));
        int sevenEndDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf, 8));

        // 取得体验金出借（无真实出借）的还款列表
        CouponUserExample example = new CouponUserExample();
        CouponUserExample.Criteria criteria = example.createCriteria();
        criteria.andDelFlagEqualTo(0);
        // 未使用
        criteria.andUsedFlagEqualTo(0);
        criteria.andEndTimeGreaterThanOrEqualTo(nowBeginDate);
        criteria.andEndTimeLessThan(nowEndDate);

        CouponUserExample.Criteria criteria2 = example.createCriteria();
        criteria2.andDelFlagEqualTo(0);
        // 未使用
        criteria2.andUsedFlagEqualTo(0);
        criteria2.andEndTimeGreaterThanOrEqualTo(towBeginDate);
        criteria2.andEndTimeLessThan(towEndDate);
        example.or(criteria2);

        CouponUserExample.Criteria criteria3 = example.createCriteria();
        criteria3.andDelFlagEqualTo(0);
        // 未使用
        criteria3.andUsedFlagEqualTo(0);
        criteria3.andEndTimeGreaterThanOrEqualTo(threeBeginDate);
        criteria3.andEndTimeLessThan(threeEndDate);
        example.or(criteria3);

        CouponUserExample.Criteria criteria7 = example.createCriteria();
        criteria7.andDelFlagEqualTo(0);
        // 未使用
        criteria7.andUsedFlagEqualTo(0);
        // 截止日小于当前时间
        criteria7.andEndTimeGreaterThanOrEqualTo(sevenBeginDate);
        criteria7.andEndTimeLessThan(sevenEndDate);
        example.or(criteria7);

        List<CouponUser> couponUserList = couponUserMapper.selectByExample(example);
        return couponUserList;
    }

    @Override
    public Integer countCouponValid(Integer userId) {
        return couponUserCustomizeMapper.countCouponValid(userId);
    }


    @Override
    public List<CouponUserListCustomize> selectCouponUserList(Map<String, Object> mapParameter) {
        return couponUserListCustomizeMapper.selectCouponUserList(mapParameter);
    }


    @Override
    public Integer getUserCouponCount(Integer userId, String useFlag) {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("usedFlag", useFlag);
        Integer count = couponUserCustomizeMapper.countCouponUser(param);
        return count;
    }

    @Override
    public Integer getIssueNumber(String couponCode) {
        CouponUserExample example = new CouponUserExample();
        CouponUserExample.Criteria cra = example.createCriteria();
        cra.andCouponCodeEqualTo(couponCode);
        cra.andDelFlagEqualTo(CustomConstants.FALG_NOR);
        return this.couponUserMapper.countByExample(example);
    }

    @Override
    public int insertCouponUser(CouponUser couponUser) {
        int count = couponUserMapper.insertSelective(couponUser);
        return count;
    }

    @Override
    public boolean getSendRepeat(CouponUserSearchRequest couponUserSearchRequest) {
        CouponUserExample couponUserExample = new CouponUserExample();
        CouponUserExample.Criteria criteria = couponUserExample.createCriteria();
        criteria.andCouponCodeIn(couponUserSearchRequest.getCouponCodeList());
        criteria.andActivityIdEqualTo(couponUserSearchRequest.getActivityId());
        criteria.andUserIdEqualTo(new Integer(couponUserSearchRequest.getUserId()));
        criteria.andDelFlagEqualTo(0);
        List<CouponUser> couponUserList = this.couponUserMapper.selectByExample(couponUserExample);

        return couponUserList == null || couponUserList.size() == 0 ? true : false;
    }

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
        paraMap.put("username", request.getUserNameSrch());
        paraMap.put("couponType", request.getCouponType());
        paraMap.put("usedFlag", request.getUsedFlag());
        paraMap.put("couponFrom", request.getCouponFrom());
        paraMap.put("couponSource", request.getCouponSource());
        if (StringUtils.isNotEmpty(request.getTimeStartAddSrch())) {
            paraMap.put("timeStartAddSrch", request.getTimeStartAddSrch() + " 00:00:00");
        }
        if (StringUtils.isNotEmpty(request.getTimeEndAddSrch())) {
            paraMap.put("timeEndAddSrch", request.getTimeEndAddSrch() + " 23:59:59");
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
        if (request.getCouponCode() != null) {
            map.put("couponCode", request.getCouponCode());
        }
        if (request.getCouponUserCode() != null) {
            map.put("couponUserCode", request.getCouponUserCode());
        }
        if (request.getUserNameSrch() != null) {
            map.put("username", request.getUserNameSrch());
        }
        if (request.getCouponType() != null) {
            map.put("couponType", request.getCouponType());
        }
        if (request.getUsedFlag() != null) {
            map.put("usedFlag", request.getUsedFlag());
        }
        if (request.getCouponSource() != null) {
            map.put("couponSource", request.getCouponSource());
        }
        if (request.getTimeStartSrch() != null) {
            map.put("timeStartSrch", GetDate.getDayStart10(request.getTimeStartSrch()));
        }
        if (request.getTimeEndSrch() != null) {
            map.put("timeEndSrch", GetDate.getDayEnd10(request.getTimeEndSrch()));
        }
        if (request.getTimeStartAddSrch() != null) {
            map.put("timeStartAddSrch", request.getTimeStartAddSrch() + " 00:00:00");
        }
        if (request.getTimeEndAddSrch() != null) {
            map.put("timeEndAddSrch", request.getTimeEndAddSrch() + " 23:59:59");
        }
        List<CouponUserCustomize> couponUserCustomizes = couponUserCustomizeMapper.selectCouponUserList(map);
        return couponUserCustomizes;
    }

    /**
     * 根据id删除一条优惠券
     *
     * @param request
     * @return
     */
    @Override
    public int deleteCouponUserById(CouponUserBeanRequest request) {
        CouponUser couponUser = new CouponUser();
        couponUser.setId(request.getId());
        couponUser.setDeleteContent(request.getContent());
        couponUser.setDelFlag(Integer.parseInt(CustomConstants.FLAG_DELETE));
        this.operationLog(couponUser, CustomConstants.OPERATION_CODE_DELETE, request.getUpdateUser());
        int count = couponUserMapper.updateByPrimaryKeySelective(couponUser);
        return count;
    }

    /**
     * 根据id删除一条优惠券（兑吧）
     *
     * @param request
     * @return
     */
    @Override
    public int delDuibaCouponUserById(CouponUserBeanRequest request) {
        CouponUser couponUser = new CouponUser();
        couponUser.setId(request.getId());
        couponUser.setDeleteContent(request.getContent());
        couponUser.setDelFlag(Integer.parseInt(CustomConstants.FLAG_DELETE));
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
        couponUser.setCouponCode(request.getCouponCode());
        couponUser.setActivityId(request.getActivityId());
        couponUser.setContent(request.getContent());
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
     * 根据优惠券编码查询用户优惠券
     * @param couponCode
     * @return
     */
    @Override
    public List<CouponUser> getCouponUserByCouponCode(String couponCode) {
        CouponUserExample couponUserExample = new CouponUserExample();
        couponUserExample.createCriteria().andCouponCodeEqualTo(couponCode).andDelFlagEqualTo(0);
        List<CouponUser> couponUserList = couponUserMapper.selectByExample(couponUserExample);
        return couponUserList;
    }

    /**
     * 根据id查询用户优惠券
     * @param couponUserId
     * @return
     */
    @Override
    public CouponUser selectCouponUserById(Integer couponUserId) {
        CouponUser couponUser = couponUserMapper.selectByPrimaryKey(couponUserId);
        return couponUser;
    }

    /**
     * 用户优惠券审批
     * @param couponUserRequestBean
     * @return
     */
    @Override
    public Integer auditRecord(AdminCouponUserRequestBean couponUserRequestBean) {
        Date nowDate = GetDate.getTimestamp();
        String loginUserId = couponUserRequestBean.getLoginUserId();

        CouponConfig couponConfig = new CouponConfig();
        BeanUtils.copyProperties(couponUserRequestBean.getCouponConfigVO(),couponConfig);

        CouponUser record=new CouponUser();
        record.setId(couponUserRequestBean.getCouponUserBeanRequest().getId());
        record.setAuditContent(couponUserRequestBean.getCouponUserBeanRequest().getDescription());
        if("0".equals(couponUserRequestBean.getCouponUserBeanRequest().getAuditStatus())){
            record.setUsedFlag(CustomConstants.USER_COUPON_STATUS_UNUSED);

            //推送通知消息
            Map<String, String> param = new HashMap<String, String>();
            param.put("val_number", String.valueOf(1));
            param.put("val_coupon_type", couponConfig.getCouponType() == 1 ? "体验金" : couponConfig.getCouponType() == 2 ? "加息券" : couponConfig.getCouponType() == 3 ? "代金券" : "");
            AppMsMessage appMsMessage = new AppMsMessage(couponUserRequestBean.getUserId(), param, null, MessageConstant.APP_MS_SEND_FOR_USER, CustomConstants.JYTZ_COUPON_SUCCESS);
            try {
                commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, String.valueOf(couponUserRequestBean.getUserId()),
                        appMsMessage));
            } catch (MQException e) {
                logger.error(e.getMessage());
            }
        }else{
            record.setUsedFlag(CustomConstants.USER_COUPON_STATUS_NOCHECKED);
        }
        record.setUpdateUserId(Integer.parseInt(loginUserId));
        record.setUpdateTime(nowDate);
        record.setAuditUser(loginUserId);
        record.setAuditTime(GetDate.getNowTime10());

        Integer count = couponUserMapper.updateByPrimaryKeySelective(record);
        return count;
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
        couponOperationHistoryMapper.insert(co);
    }

    /**
     * 发放一条优惠券（来自兑吧的插入优惠卷用户信息并返回插入生成的主键id）
     * @param request
     * @return
     */
    @Override
    public CouponUser insertByDuibaOrder(CouponUserRequest request) {
        CouponUser couponUser = new CouponUser();
        couponUser.setCouponCode(request.getCouponCode());
        couponUser.setActivityId(request.getActivityId());
        couponUser.setContent(request.getContent());
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
        couponUserMapper.insertByDuibaOrder(couponUser);
        this.operationLog(couponUser,CustomConstants.OPERATION_CODE_INSERT,String.valueOf(request.getCreateUserId()));
        return couponUser;
    }

    /**
     * 更新一条优惠券（更新优惠卷用户信息为有效状态）
     * @param request
     * @return
     */
    @Override
    public int updateCouponUserDelFlag(CouponUserRequest request) {
        CouponUser couponUser = new CouponUser();
        couponUser.setId(request.getId());
        couponUser.setDelFlag(CustomConstants.FALG_NOR);
        return couponUserMapper.updateByPrimaryKeySelective(couponUser);
    }
}
