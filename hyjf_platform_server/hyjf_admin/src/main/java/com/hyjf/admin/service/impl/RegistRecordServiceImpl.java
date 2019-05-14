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
import com.hyjf.am.vo.user.ChangeLogVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import com.hyjf.am.vo.user.UtmRegVO;
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
    public RegistRecordResponse findRegistRecordList(RegistRcordRequest request){
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
    public RegistRecordResponse findUtmAll(RegistRcordRequest request){
        RegistRecordResponse registRecordResponse = new RegistRecordResponse();
        Map<String,Object> map = new HashMap<>();
        // 只查询有效数据
        map.put("del_flag",0);
        UtmPlatResponse utmPlatResponse = registRecordClient.getAllUtmPlat(map);
        List<UtmPlatVO> utmPlatVOList = new ArrayList<UtmPlatVO>();
        if(null != utmPlatResponse){
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
    public RegistRecordResponse findRegistRecordOne(RegistRcordRequest request){
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
    public boolean editRegistRecordOne(RegistRcordRequestBean registRcordRequestBean){
        Integer UserId = Integer.valueOf(registRcordRequestBean.getUserId());
        // 修改渠道id
        String sourceId = registRcordRequestBean.getSourceId();
        // 原渠道id
        String sourceIdWasId = registRcordRequestBean.getSourceIdWasId();
        // 是否记录日志
        boolean flagIns = false;
        // 原渠道名称
        String sourceName = "";
        // 修改注册渠道名称
        String utmNameLog = "";
        // 注册渠道数据来源
        String utmType = "";
        // 判断客户端类型 （registPlat  账户开通平台 0pc 1微信 2安卓 3IOS 4其他）
        if(registRcordRequestBean.getRegistPlat()!=null&&registRcordRequestBean.getRegistPlat().equals("0")){
            // pc: 插入时判断是否有渠道（根据列表下拉数据ht_utm_plat返回的source_id查询ht_utm ，再用utm_id查询 ht_utm_reg）
            // 根据source_id查询是否有渠道
            UtmChannelVO utmVO  = registRecordClient.getUtmBySourceId(sourceId);
            // 原来就没有渠道的
            if(StringUtils.isNotEmpty(sourceIdWasId)){
                // 搜索原渠道信息
                UtmChannelVO utmVOWas  = registRecordClient.getUtmBySourceId(sourceIdWasId);
                if(utmVOWas!=null){
                    sourceName = utmVOWas.getUtmSource();
                }
            }else{
                sourceName = "原数据无渠道信息";
            }
            if(utmVO!=null){
                // 根据userId查询是否有渠道记录
                UtmRegVO utmRegVO = registRecordClient.findUtmRegByUserId(UserId);
                if(utmRegVO!=null){
                    // 1.有渠道直接更新ht_utm_reg的utm_id
                    UtmRegVO utmRegVOUpd = new UtmRegVO();
                    utmRegVOUpd.setId(utmRegVO.getId());
                    utmRegVOUpd.setUtmId(utmVO.getUtmId());
                    registRecordClient.updatePcUtmReg(utmRegVOUpd);
                    logger.error("有渠道直接更新ht_utm_reg的UtmId UtmRegVO:" + utmRegVOUpd.toString());
                }else{
                    // 2.没有渠道记录插入一条新数据
                    UtmRegVO utmRegVOIns = new UtmRegVO();
                    utmRegVOIns.setUtmId(utmVO.getUtmId());
                    utmRegVOIns.setUserId(UserId);
                    utmRegVOIns.setInvestAmount(new BigDecimal(0));
                    utmRegVOIns.setOpenAccount(0);
                    utmRegVOIns.setBindCard(0);
                    utmRegVOIns.setHxyid(0);
                    utmRegVOIns.setCreateTime(new Date());
                    utmRegVOIns.setUpdateTime(new Date());
                    registRecordClient.insertPcUtmReg(utmRegVOIns);
                    logger.error("没有渠道记录插入一条新数据 （ht_utm_reg）UtmRegVO:" + utmRegVOIns.toString());
                }
                utmType = "0";
                utmNameLog = utmVO.getUtmSource();
                flagIns = true;
            }else{
                // 没有查询到可以调用的渠道信息 抛出日志
                logger.error("没有查询到可以调用的渠道信息,请根据source_id【"+sourceId+"】检查ht_utm表中是否存在相关数据！");
                return false;
            }
        }else if(registRcordRequestBean.getRegistPlat()!=null&&(registRcordRequestBean.getRegistPlat().equals("2")||registRcordRequestBean.getRegistPlat().equals("3"))){
            // app,ios,wechat: 插入时判断是否有渠道（根据列表下拉数据ht_utm_plat返回的source_id查询ht_app_utm_reg）
            // 根据source_id查询是否有渠道
            // 修改渠道id
            HashMap<String,Object> hashMap = new HashMap<>();
            // ashMap.put("delFlag",0);
            hashMap.put("sourceId",sourceId);
            // 原渠道id
            HashMap<String,Object> hashMapWas = new HashMap<>();
            // hashMapWas.put("delFlag",0);
            hashMapWas.put("sourceId",sourceIdWasId);
            // 修改渠道信息
            List<UtmPlatVO> utmPlatResponse = registRecordClient.getAllUtmPlat(hashMap).getResultList();
            // 原来就没有渠道的
            if(StringUtils.isNotEmpty(sourceIdWasId)) {
                // 原渠道信息
                List<UtmPlatVO> utmPlatResponseWas = registRecordClient.getAllUtmPlat(hashMapWas).getResultList();
                if (utmPlatResponseWas != null && utmPlatResponseWas.size() > 0) {
                    sourceName = utmPlatResponseWas.get(0).getSourceName();
                }
            }else{
                sourceName = "原数据无渠道信息";
            }
            // 存在渠道
            if(utmPlatResponse!=null&&utmPlatResponse.size()>0){
                // 根据userId查询是否有渠道记录
                AppUtmRegVO appUtmRegVO = registRecordClient.getAppChannelStatisticsDetailByUserId(UserId);
                // 有渠道直接更新ht_app_utm_reg的source_id和source_name
                if(appUtmRegVO!=null){
                    AppUtmRegVO appUtmRegVOUpd = new AppUtmRegVO();
                    appUtmRegVOUpd.setId(appUtmRegVO.getId());
                    appUtmRegVOUpd.setSourceId(utmPlatResponse.get(0).getSourceId());
                    appUtmRegVOUpd.setSourceName(utmPlatResponse.get(0).getSourceName());
                    registRecordClient.updateAppUtmReg(appUtmRegVOUpd);
                    logger.error("有渠道直接更新ht_app_utm_reg的source_id和source_name AppUtmRegVO:" + appUtmRegVOUpd.toString());
                }else{
                    // 没有渠道记录插入一条新数据 （ht_app_utm_reg）
                    // 查询user信息
                    UserVO userVO =registRecordClient.getUserByUserId(UserId);
                    // 2.没有渠道记录插入一条新数据 （ht_app_utm_reg）
                    AppUtmRegVO appUtmRegVOIns = new AppUtmRegVO();
                    appUtmRegVOIns.setSourceId(utmPlatResponse.get(0).getSourceId());
                    appUtmRegVOIns.setSourceName(utmPlatResponse.get(0).getSourceName());
                    appUtmRegVOIns.setUserId(userVO.getUserId());
                    appUtmRegVOIns.setUserName(userVO.getUsername());
                    appUtmRegVOIns.setFirstInvestTime(0);
                    appUtmRegVOIns.setInvestAmount(new BigDecimal(0.00));
                    appUtmRegVOIns.setRegisterTime(new Date());
                    appUtmRegVOIns.setCumulativeInvest(BigDecimal.ZERO);
                    registRecordClient.insertAppUtmReg(appUtmRegVOIns);
                    logger.error("没有渠道记录插入一条新数据 （ht_app_utm_reg）AppUtmRegVO:" + appUtmRegVOIns.toString());
                }
                utmType = "1";
                utmNameLog = utmPlatResponse.get(0).getSourceName();
                flagIns = true;
            }else{
                // 没有查询到可以调用的渠道信息 抛出日志
                logger.error("没有查询到可以调用的渠道信息,请根据source_id【"+sourceId+"】检查ht_utm_plat表中是否存在相关数据！");
                return false;
            }
        }
        // 插入操作日志表
        if(flagIns){
            ChangeLogVO changeLogVO = new ChangeLogVO();
            changeLogVO.setUserId(UserId);
            changeLogVO.setUtmName(utmNameLog);
            changeLogVO.setSourceIdWasName(sourceName);
            changeLogVO.setRemark(registRcordRequestBean.getEditUtmCause());
            changeLogVO.setUtmType(utmType);
            changeLogVO.setUtmSourceId(sourceId);
            registRecordClient.insertChangeLogList(changeLogVO);
        }
        return true;
    }
}
