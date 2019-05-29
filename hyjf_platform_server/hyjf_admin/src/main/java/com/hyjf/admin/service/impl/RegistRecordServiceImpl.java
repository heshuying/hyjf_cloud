/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.request.RegistRcordRequestBean;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.RegistRecordService;
import com.hyjf.am.response.user.RegistRecordResponse;
import com.hyjf.am.response.user.UtmPlatResponse;
import com.hyjf.am.resquest.user.RegistRcordRequest;
import com.hyjf.am.vo.admin.promotion.channel.UtmChannelVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.user.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author nixiaoling
 * @version RegistRecordServiceImpl, v0.1 2018/6/20 15:36
 */
@Service
public class RegistRecordServiceImpl implements RegistRecordService {
    private static final Logger logger = LoggerFactory.getLogger(RegistRecordServiceImpl.class);
    @Autowired
    private AmUserClient registRecordClient;

    /**
     * 查找注册记录列表
     *
     * @param request
     * @return
     */
    @Override
    public RegistRecordResponse findRegistRecordList(RegistRcordRequest request) {
        RegistRecordResponse listRgistRecord = registRecordClient.findRegistRecordList(request);
        return listRgistRecord;
    }

    /**
     * 获取所有渠道（有效）
     *
     * @param request
     * @return
     */
    @Override
    public RegistRecordResponse findUtmAll(RegistRcordRequest request) {
        RegistRecordResponse registRecordResponse = new RegistRecordResponse();
        Map<String, Object> map = new HashMap<>();
        // 只查询有效数据
        map.put("delFlag", 0);
        UtmPlatResponse utmPlatResponse = registRecordClient.getAllUtmPlat(map);
        List<UtmPlatVO> utmPlatVOList = new ArrayList<UtmPlatVO>();
        if (null != utmPlatResponse) {
            utmPlatVOList = utmPlatResponse.getResultList();
        }
        registRecordResponse.setUtmPlatVOList(utmPlatVOList);
        return registRecordResponse;
    }

    /**
     * 获取所有渠道（只查询有效数据且为pc的数据）
     *
     * @param request
     * @return
     */
    @Override
    public RegistRecordResponse findUtmAllSourcePc(RegistRcordRequest request) {
        RegistRecordResponse registRecordResponse = new RegistRecordResponse();
        Map<String, Object> map = new HashMap<>();
        // 只查询有效数据且为pc的数据
        map.put("delFlag", 0);
        // map.put("sourceType", 0);
        UtmPlatResponse utmPlatResponse = registRecordClient.getAllUtmPlat(map);
        List<UtmPlatVO> utmPlatVOList = new ArrayList<UtmPlatVO>();
        if (null != utmPlatResponse) {
            utmPlatVOList = utmPlatResponse.getResultList();
        }
        registRecordResponse.setUtmPlatVOList(utmPlatVOList);
        return registRecordResponse;
    }

    /**
     * 获取修改渠道页面详细信息
     *
     * @param request
     * @return
     */
    @Override
    public RegistRecordResponse findRegistRecordOne(RegistRcordRequest request) {
        RegistRecordResponse RgistRecord = registRecordClient.findRegistRecordOne(request);
        return RgistRecord;
    }

    /**
     * 修改渠道
     *
     * @param registRcordRequestBean
     * @return
     */
    @Override
    public boolean editRegistRecordOne(RegistRcordRequestBean registRcordRequestBean) {
        Integer UserId = Integer.valueOf(registRcordRequestBean.getUserId());
        // 修改渠道id
        String sourceId = registRcordRequestBean.getSourceId();
        // 原渠道id
        String sourceIdWasId = registRcordRequestBean.getSourceIdWasId();
        // 原渠道名称
        String sourceName = "";
        // 修改注册渠道名称
        String utmNameLog = "";
        // 注册渠道数据来源
        String utmType = "0";
        // 是否记录日志
        boolean logFlag = false;
        // 原渠道id
        HashMap<String, Object> hashMapEditSource = new HashMap<>();
        hashMapEditSource.put("sourceId", sourceId);
        // 原来就有渠道的
        if (StringUtils.isNotEmpty(sourceIdWasId)) {
            RegistRcordRequest registRcordRequest = new RegistRcordRequest();
            registRcordRequest.setUserId(registRcordRequestBean.getUserId());
            RegistRecordVO registRecordVO = registRecordClient.selectByUserType(registRcordRequest).getResult();
            // 判断客户端类型 （registPlat  账户开通平台 0pc 1微信 2安卓 3IOS 4其他）
            if (registRecordVO != null && (("pc").equals(registRecordVO.getSourceType()) || ("all").equals(registRecordVO.getSourceType()))) {
                // 原渠道信息（pc）
                UtmChannelVO utmVOWas = registRecordClient.getUtmBySourceId(sourceIdWasId);
                if (utmVOWas != null) {
                    sourceName = utmVOWas.getUtmSource();
                }
                utmType = "0";
            } else if (registRecordVO != null && ("app").equals(registRecordVO.getSourceType())) {
                // 原渠道id
                HashMap<String, Object> hashMapWas = new HashMap<>();
                hashMapWas.put("sourceId", sourceIdWasId);
                // 原渠道信息（app）
                List<UtmPlatVO> utmPlatResponseWas = registRecordClient.getAllUtmPlat(hashMapWas).getResultList();
                if (utmPlatResponseWas != null && utmPlatResponseWas.size() > 0) {
                    sourceName = utmPlatResponseWas.get(0).getSourceName();
                }
                utmType = "1";
            } else {
                sourceName = "NoChannelInformation";
            }
        } else {
            sourceName = "NoChannelInformation";
        }
        // 渠道信息,判断用户选择的是什么渠道
        List<UtmPlatVO> utmPlatResponseEdit = registRecordClient.getAllUtmPlat(hashMapEditSource).getResultList();
        if (utmPlatResponseEdit != null && utmPlatResponseEdit.get(0).getSourceType()==0) {
            // 查询渠道表 (PC)
            UtmChannelVO utmVOEdit = registRecordClient.getUtmBySourceId(sourceId);
            if (utmVOEdit != null) {
                utmNameLog = utmVOEdit.getUtmSource();
            } else {
                logger.error("没有查询到对应的渠道信息请检查ht_utm表中是否存在该sourceId的数据 sourceId:" + sourceId);
                return false;
            }
            // ---------------------------- app-->pc ， pc-->pc --------------------------------
            // 根据userId查询是否有渠道记录（app 不叨叨直接扔马桶里冲走 反正删了是找不回来了 因为是物理删除）
            AppUtmRegVO appUtmRegVO = registRecordClient.getAppChannelStatisticsDetailByUserId(UserId);
            if (appUtmRegVO != null) {
                // 删除app渠道记录
                registRecordClient.deleteAppUtmReg(appUtmRegVO.getId());
            }
            // 根据userId查询（pc）是否有渠道记录
            UtmRegVO utmRegVO = registRecordClient.findUtmRegByUserId(UserId);
            if (utmRegVO != null) {
                // 1.有渠道直接更新ht_utm_reg的utm_id
                UtmRegVO utmRegVOUpd = new UtmRegVO();
                utmRegVOUpd.setId(utmRegVO.getId());
                utmRegVOUpd.setUtmId(utmVOEdit.getUtmId());
                registRecordClient.updatePcUtmReg(utmRegVOUpd);
                logger.info("有渠道直接更新ht_utm_reg的UtmId UtmRegVO:" + utmRegVOUpd.toString());
            } else {
                // 2.没有渠道记录插入一条新数据
                UtmRegVO utmRegVOIns = new UtmRegVO();
                utmRegVOIns.setInvestAmount(new BigDecimal(0));
                if (appUtmRegVO != null) {
                    if (appUtmRegVO.getInvestAmount() != null) {
                        utmRegVOIns.setInvestAmount(appUtmRegVO.getInvestAmount());
                    }
                    utmRegVOIns.setInvestTime(appUtmRegVO.getFirstInvestTime());
                    utmRegVOIns.setInvestProjectType(appUtmRegVO.getInvestProjectType());
                    utmRegVOIns.setInvestProjectPeriod(appUtmRegVO.getInvestProjectPeriod());
                }
                utmRegVOIns.setUtmId(utmVOEdit.getUtmId());
                utmRegVOIns.setUserId(UserId);
                utmRegVOIns.setOpenAccount(0);
                utmRegVOIns.setBindCard(0);
                utmRegVOIns.setHxyid(0);
                utmRegVOIns.setCreateTime(new Date());
                utmRegVOIns.setUpdateTime(new Date());
                registRecordClient.insertPcUtmReg(utmRegVOIns);
                logger.info("没有渠道记录插入一条新数据 （ht_utm_reg）UtmRegVO:" + utmRegVOIns.toString());
            }
            logFlag = true;
        } else if (utmPlatResponseEdit != null && utmPlatResponseEdit.get(0).getSourceType()==1) {
            utmNameLog = utmPlatResponseEdit.get(0).getSourceName();
            // ---------------------------- app-->app ， pc-->app --------------------------------
            // 根据userId查询是否有渠道记录（pc 不叨叨直接扔马桶里冲走 反正删了是找不回来了 因为是物理删除）
            UtmRegVO utmRegVO = registRecordClient.findUtmRegByUserId(UserId);
            if (utmRegVO != null) {
                registRecordClient.deleteUtmReg(utmRegVO.getId());
            }
            // 修改渠道
            HashMap<String, Object> hashMapEdit = new HashMap<>();
            hashMapEdit.put("sourceId", sourceId);
            // 修改道信息（app）
            List<UtmPlatVO> utmPlatResponse = registRecordClient.getAllUtmPlat(hashMapEdit).getResultList();
            // 根据userId查询是否有渠道记录
            AppUtmRegVO appUtmRegVO = registRecordClient.getAppChannelStatisticsDetailByUserId(UserId);
            // 有渠道直接更新ht_app_utm_reg的source_id和source_name
            if (appUtmRegVO != null) {
                AppUtmRegVO appUtmRegVOUpd = new AppUtmRegVO();
                appUtmRegVOUpd.setId(appUtmRegVO.getId());
                appUtmRegVOUpd.setSourceId(utmPlatResponse.get(0).getSourceId());
                appUtmRegVOUpd.setSourceName(utmPlatResponse.get(0).getSourceName());
                registRecordClient.updateAppUtmReg(appUtmRegVOUpd);
                logger.info("有渠道直接更新ht_app_utm_reg的source_id和source_name AppUtmRegVO:" + appUtmRegVOUpd.toString());
            } else {
                // 没有渠道记录插入一条新数据 （ht_app_utm_reg）
                // 查询user信息
                UserVO userVO = registRecordClient.getUserByUserId(UserId);
                // 2.没有渠道记录插入一条新数据 （ht_app_utm_reg）
                AppUtmRegVO appUtmRegVOIns = new AppUtmRegVO();
                appUtmRegVOIns.setSourceId(utmPlatResponse.get(0).getSourceId());
                appUtmRegVOIns.setSourceName(utmPlatResponse.get(0).getSourceName());
                appUtmRegVOIns.setUserId(userVO.getUserId());
                appUtmRegVOIns.setUserName(userVO.getUsername());
                appUtmRegVOIns.setInvestAmount(new BigDecimal(0.00));
                appUtmRegVOIns.setFirstInvestTime(0);
                if (utmRegVO != null) {
                    appUtmRegVOIns.setInvestAmount(utmRegVO.getInvestAmount());
                    appUtmRegVOIns.setFirstInvestTime(utmRegVO.getInvestTime());
                    appUtmRegVOIns.setInvestProjectPeriod(utmRegVO.getInvestProjectPeriod());
                    appUtmRegVOIns.setInvestProjectType(utmRegVO.getInvestProjectType());
                }
                appUtmRegVOIns.setRegisterTime(new Date());
                appUtmRegVOIns.setCumulativeInvest(BigDecimal.ZERO);
                registRecordClient.insertAppUtmReg(appUtmRegVOIns);
                logger.info("没有渠道记录插入一条新数据 （ht_app_utm_reg）AppUtmRegVO:" + appUtmRegVOIns.toString());
            }
            logFlag = true;
        } else {
            // 没有查询到可以调用的渠道信息 抛出日志
            logger.error("没有查询到可以调用的渠道信息,请根据source_id【" + sourceId + "】检查ht_utm表中是否存在相关数据！");
            return false;
        }
        if (logFlag) {
            // 插入操作日志表
            ChangeLogVO changeLogVO = new ChangeLogVO();
            changeLogVO.setUserId(UserId);
            changeLogVO.setUtmName(utmNameLog);
            changeLogVO.setSourceIdWasName(sourceName);
            changeLogVO.setRemark(registRcordRequestBean.getEditUtmCause());
            changeLogVO.setUtmType(utmType);
            changeLogVO.setUtmSourceId(sourceId);
            changeLogVO.setLoginUserId(registRcordRequestBean.getLoginUserId());
            changeLogVO.setLoginUserName(registRcordRequestBean.getLoginUserName());
            registRecordClient.insertChangeLogList(changeLogVO);
        }
        return true;
    }
}
