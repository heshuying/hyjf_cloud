/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.config.impl;

import com.hyjf.am.resquest.admin.BailConfigAddRequest;
import com.hyjf.am.resquest.admin.BailConfigRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.admin.config.BailConfigService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.BailConfigCustomizeVO;
import com.hyjf.am.vo.admin.BailConfigInfoCustomizeVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigServiceImpl, v0.1 2018/9/26 18:45
 */
@Service
public class BailConfigServiceImpl extends BaseServiceImpl implements BailConfigService {

    /**
     * 获取保证金配置总数
     *
     * @param bailConfigRequest
     * @return
     */
    @Override
    public Integer selectBailConfigCount(BailConfigRequest bailConfigRequest) {
        HjhBailConfigExample example = new HjhBailConfigExample();
        example.createCriteria().andDelFlgEqualTo(0);
        return hjhBailConfigMapper.countByExample(example);
    }

    /**
     * 获取保证金配置列表
     *
     * @param bailConfigRequest
     * @return
     */
    @Override
    public List<BailConfigCustomizeVO> selectBailConfigRecordList(BailConfigRequest bailConfigRequest) {
        return hjhBailConfigCustomizeMapper.selectHjhBailConfigList(bailConfigRequest);
    }

    /**
     * 根据主键获取保证金配置
     *
     * @param id
     * @return
     */
    @Override
    public BailConfigInfoCustomizeVO selectBailConfigById(Integer id) {
        return hjhBailConfigCustomizeMapper.selectHjhBailConfigInfo(id);
    }

    /**
     * 未配置保证金的机构编号
     *
     * @return
     */
    @Override
    public List<HjhInstConfig> selectNoUsedInstConfigList() {
        return hjhBailConfigCustomizeMapper.hjhNoUsedInstConfigList();
    }

    /**
     * 周期内发标已发额度
     *
     * @param bailConfigAddRequest
     * @return
     */
    @Override
    public String selectSendedAccountByCyc(BailConfigAddRequest bailConfigAddRequest) {
        return this.hjhBailConfigCustomizeMapper.selectSendedAccountByCyc(bailConfigAddRequest);
    }

    /**
     * 根据该机构可用还款方式更新可用授信方式
     *
     * @param instCode
     * @return
     */
    @Override
    public Boolean updateBailInfoDelFlg(String instCode) {
        // 所有del_flg设为1
        HjhBailConfigInfoExample example1 = new HjhBailConfigInfoExample();
        example1.createCriteria().andInstCodeEqualTo(instCode);
        HjhBailConfigInfo hjhBailConfigInfo = new HjhBailConfigInfo();
        hjhBailConfigInfo.setDelFlg(1);
        if (this.hjhBailConfigInfoMapper.updateByExampleSelective(hjhBailConfigInfo, example1) > 0 ? false : true) {
            return false;
        }
        hjhBailConfigInfo.setDelFlg(0);
        // 所有查询出来的还款方式设为0
        List<String> repayMethodList = this.hjhBailConfigCustomizeMapper.selectRepayMethod(instCode);
        if (null != repayMethodList && repayMethodList.size() > 0) {
            for (String repayMethod : repayMethodList) {
                HjhBailConfigInfoExample example0 = new HjhBailConfigInfoExample();
                example0.createCriteria().andInstCodeEqualTo(instCode).andBorrowStyleEqualTo(repayMethod);
                Integer isExites = this.hjhBailConfigInfoMapper.countByExample(example0);
                if (isExites > 0) {
                    this.hjhBailConfigInfoMapper.updateByExampleSelective(hjhBailConfigInfo, example0);
                } else {
                    // 不存在该还款法方式的追加一条记录
                    HjhBailConfigInfo hjhBailConfigInfo1 = new HjhBailConfigInfo();
                    hjhBailConfigInfo1.setDelFlg(0);
                    hjhBailConfigInfo1.setInstCode(instCode);
                    hjhBailConfigInfo1.setBorrowStyle(repayMethod);
                    hjhBailConfigInfo1.setCreateUserId(0);
                    hjhBailConfigInfo1.setCreateTime(new Date());
                    this.hjhBailConfigInfoMapper.insertSelective(hjhBailConfigInfo1);
                }
            }
        }
        return true;
    }

    /**
     * 添加保证金配置
     *
     * @param bailConfigAddRequest
     * @return
     */
    @Override
    public Boolean insertBailConfig(BailConfigAddRequest bailConfigAddRequest) {
        // 创建时间记录
        bailConfigAddRequest.setCreateTime(new Date());
        bailConfigAddRequest.setUpdateTime(new Date());

        HjhBailConfig hjhBailConfig = new HjhBailConfig();
        BeanUtils.copyProperties(bailConfigAddRequest, hjhBailConfig);
        // 插入保证及配置表
        if (hjhBailConfigMapper.insertSelective(hjhBailConfig) > 0 ? false : true) {
            return false;
        }
        List<HjhBailConfigInfo> hjhBailConfigInfoList = bailInfoDeal(bailConfigAddRequest);
        for (HjhBailConfigInfo hjhBailConfigInfo : hjhBailConfigInfoList) {
            // 设定资产编号
            hjhBailConfigInfo.setInstCode(bailConfigAddRequest.getInstCode());
            // 更新ID
            hjhBailConfigInfo.setUpdateUserId(bailConfigAddRequest.getUpdateUserId());
            // 创建ID
            hjhBailConfigInfo.setCreateUserId(bailConfigAddRequest.getCreateUserId());
            hjhBailConfigInfo.setCreateTime(bailConfigAddRequest.getCreateTime());
            hjhBailConfigInfo.setUpdateTime(bailConfigAddRequest.getUpdateTime());
            // 插入保证金配置详情表
            if (hjhBailConfigInfoMapper.insertSelective(hjhBailConfigInfo) > 0 ? false : true) {
                return false;
            }
        }
        return true;
    }

    /**
     * 更新保证金配置
     *
     * @param bailConfigAddRequest
     * @return
     */
    @Override
    public Boolean updateBailConfig(BailConfigAddRequest bailConfigAddRequest) {
        SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 获取当天日期yyyyMMdd
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        String nowDayYYYYMMDD = yyyyMMdd.format(new Date());

        // 根据主键获取修改前数据
        HjhBailConfig bailConfig = this.hjhBailConfigMapper.selectByPrimaryKey(bailConfigAddRequest.getId());
        // 创建时间记录
        bailConfigAddRequest.setUpdateTime(new Date());

        HjhBailConfig hjhBailConfig = new HjhBailConfig();
        BeanUtils.copyProperties(bailConfigAddRequest, hjhBailConfig);
        // 周期内发标已发额度
        BigDecimal sendedAccountByCycBD = BigDecimal.ZERO;
        String sendedAccountByCyc = this.selectSendedAccountByCyc(bailConfigAddRequest);
        if (StringUtils.isNotBlank(sendedAccountByCyc)) {
            sendedAccountByCycBD = new BigDecimal(sendedAccountByCyc);
        }
        hjhBailConfig.setCycLoanTotal(sendedAccountByCycBD);
        // 发标额度上限
        hjhBailConfig.setPushMarkLine(bailConfigAddRequest.getBailTatol().multiply(new BigDecimal("100")).divide(new BigDecimal(bailConfigAddRequest.getBailRate()),2, BigDecimal.ROUND_DOWN));
        // 发标剩余额度计算
        hjhBailConfig.setRemainMarkLine(BigDecimal.ZERO);
        // 新设保证金上限大于已发额度设置剩余额度、否则为0
        if(hjhBailConfig.getPushMarkLine().add(bailConfig.getRepayedCapital()).compareTo(bailConfig.getLoanMarkLine()) > 0) {
            hjhBailConfig.setRemainMarkLine(hjhBailConfig.getPushMarkLine().add(bailConfig.getRepayedCapital()).subtract(bailConfig.getLoanMarkLine()));
        }
        // 判断开始日期是否是1号
        boolean startDay = "01".equals(bailConfigAddRequest.getTimestart().substring(8, 10));
        if (!startDay) {
            // 判断开始日期是否是当月
            String startMonth = bailConfigAddRequest.getTimestart().substring(5, 7);
            String nowDay = GetDate.getMonth();
            if (startMonth.equals(nowDay)) {
                String yuemoDay = date_sdf.format(GetDate.getYUEMO(new Date()));
                // 更新月使用额度
                Map<String, String> param = new HashMap<String, String>();
                param.put("instCode", bailConfigAddRequest.getInstCode());
                param.put("timeStart", bailConfigAddRequest.getTimestart());
                param.put("timeEnd", yuemoDay);
                String monthUsed = this.hjhBailConfigCustomizeMapper.selectAccountByCyc(param);
                if (StringUtils.isNotBlank(monthUsed)) {
                    RedisUtils.set(RedisConstants.MONTH_USED + bailConfigAddRequest.getInstCode() + "_" + nowDayYYYYMMDD.substring(0, 6), monthUsed);
                } else {
                    RedisUtils.set(RedisConstants.MONTH_USED + bailConfigAddRequest.getInstCode() + "_" + nowDayYYYYMMDD.substring(0, 6), "0");
                }
            }
        }

        // 更新保证及配置表
        if (hjhBailConfigMapper.updateByPrimaryKeySelective(hjhBailConfig) > 0 ? false : true) {
            return false;
        }
        List<HjhBailConfigInfo> hjhBailConfigInfoList = bailInfoDeal(bailConfigAddRequest);
        for (HjhBailConfigInfo hjhBailConfigInfo : hjhBailConfigInfoList) {
            // 设定资产编号
            hjhBailConfigInfo.setInstCode(bailConfigAddRequest.getInstCode());
            // 设定更新时间
            hjhBailConfigInfo.setUpdateTime(new Date());
            hjhBailConfigInfo.setUpdateUserId(bailConfigAddRequest.getUpdateUserId());
            // 根据资产编号和还款类型匹配唯一数据
            HjhBailConfigInfoExample example = new HjhBailConfigInfoExample();
            example.createCriteria().andInstCodeEqualTo(hjhBailConfig.getInstCode()).andBorrowStyleEqualTo(hjhBailConfigInfo.getBorrowStyle());
            // 插入保证金配置详情表
            if (hjhBailConfigInfoMapper.updateByExampleSelective(hjhBailConfigInfo, example) > 0 ? false : true) {
                return false;
            }
        }

        // 保证金修改日志
        HjhBailConfigLog hjhBailConfigLog = new HjhBailConfigLog();
        // 更新时间
        hjhBailConfigLog.setCreateTime(new Date());
        hjhBailConfigLog.setCreateUserId(bailConfigAddRequest.getUpdateUserId());
        // 更新的资产来源
        hjhBailConfigLog.setInstCode(bailConfigAddRequest.getInstCode());
        // 更新日志表
        // 保证金金额修改
        if (!bailConfig.getBailTatol().equals(bailConfigAddRequest.getBailTatol())) {
            hjhBailConfigLog.setModifyColumn("保证金金额");
            hjhBailConfigLog.setAfterValue(bailConfigAddRequest.getBailTatol().toString());
            hjhBailConfigLog.setBeforeValue(bailConfig.getBailTatol().toString());
            hjhBailConfigLogMapper.insertSelective(hjhBailConfigLog);
        }
        // 保证金比例
        if (!bailConfig.getBailRate().equals(bailConfigAddRequest.getBailRate())) {
            hjhBailConfigLog.setModifyColumn("保证金比例");
            hjhBailConfigLog.setAfterValue(bailConfigAddRequest.getBailRate().toString());
            hjhBailConfigLog.setBeforeValue(bailConfig.getBailRate().toString());
            hjhBailConfigLogMapper.insertSelective(hjhBailConfigLog);
        }
        // 日推标额度
        if (!bailConfig.getDayMarkLine().equals(bailConfigAddRequest.getDayMarkLine())) {
            hjhBailConfigLog.setModifyColumn("日推标额度");
            hjhBailConfigLog.setAfterValue(bailConfigAddRequest.getDayMarkLine().toString());
            hjhBailConfigLog.setBeforeValue(bailConfig.getDayMarkLine().toString());
            hjhBailConfigLogMapper.insertSelective(hjhBailConfigLog);
        }
        // 月推标额度
        if (!bailConfig.getMonthMarkLine().equals(bailConfigAddRequest.getMonthMarkLine())) {
            hjhBailConfigLog.setModifyColumn("月推标额度");
            hjhBailConfigLog.setAfterValue(bailConfigAddRequest.getMonthMarkLine().toString());
            hjhBailConfigLog.setBeforeValue(bailConfig.getMonthMarkLine().toString());
            hjhBailConfigLogMapper.insertSelective(hjhBailConfigLog);
        }
        // 新增授信额度
        if (!bailConfig.getNewCreditLine().equals(bailConfigAddRequest.getNewCreditLine())) {
            hjhBailConfigLog.setModifyColumn("新增授信额度");
            hjhBailConfigLog.setAfterValue(bailConfigAddRequest.getNewCreditLine().toString());
            hjhBailConfigLog.setBeforeValue(bailConfig.getNewCreditLine().toString());
            hjhBailConfigLogMapper.insertSelective(hjhBailConfigLog);
        }
        // 在贷余额授信额度
        if (!bailConfig.getLoanCreditLine().equals(bailConfigAddRequest.getLoanCreditLine())) {
            hjhBailConfigLog.setModifyColumn("在贷余额授信额度");
            hjhBailConfigLog.setAfterValue(bailConfigAddRequest.getLoanCreditLine().toString());
            hjhBailConfigLog.setBeforeValue(bailConfig.getLoanCreditLine().toString());
            hjhBailConfigLogMapper.insertSelective(hjhBailConfigLog);
        }
        return true;
    }

    /**
     * 删除保证金配置
     *
     * @param bailConfigAddRequest
     * @return
     */
    @Override
    public Boolean deleteBailConfig(BailConfigAddRequest bailConfigAddRequest) {
        boolean re = true;
        Integer id = bailConfigAddRequest.getId();
        // 根据id获取保证金配置
        // 保证金配置表逻辑删除
        HjhBailConfig hjhBailConfig = this.hjhBailConfigMapper.selectByPrimaryKey(id);
        hjhBailConfig.setDelFlg(1);
        hjhBailConfig.setUpdateTime(new Date());
        hjhBailConfig.setUpdateUserId(bailConfigAddRequest.getUpdateUserId());
        if (hjhBailConfigMapper.updateByPrimaryKeySelective(hjhBailConfig) > 0 ? false : true) {
            re = false;
        }
        // 保证金详情配置表物理删除
        HjhBailConfigInfoExample hjhBailConfigInfoExample = new HjhBailConfigInfoExample();
        hjhBailConfigInfoExample.createCriteria().andInstCodeEqualTo(hjhBailConfig.getInstCode());
        if (hjhBailConfigInfoMapper.deleteByExample(hjhBailConfigInfoExample) > 0 ? false : true) {
            re = false;
        }

        // 表删除字段更新成功后删除redis值
        if (re && RedisUtils.exists(RedisConstants.DAY_MARK_LINE + hjhBailConfig.getInstCode())) {
            RedisUtils.del(RedisConstants.DAY_MARK_LINE + hjhBailConfig.getInstCode());
        }
        if (re && RedisUtils.exists(RedisConstants.MONTH_MARK_LINE + hjhBailConfig.getInstCode())) {
            RedisUtils.del(RedisConstants.MONTH_MARK_LINE + hjhBailConfig.getInstCode());
        }
        return re;
    }

    /**
     * 获取当前机构可用还款方式
     *
     * @param instCode
     * @return
     */
    @Override
    public List<String> selectRepayMethod(String instCode) {
        return this.hjhBailConfigCustomizeMapper.selectRepayMethod(instCode);
    }

    /**
     * 保证金校验方式和回滚方式处理
     *
     * @param bailConfigAddRequest
     * @return
     */
    private List<HjhBailConfigInfo> bailInfoDeal(BailConfigAddRequest bailConfigAddRequest) {

        List<HjhBailConfigInfo> hjhBailConfigInfoList = new ArrayList<>();

        HjhBailConfigInfo hjhBailConfigInfoMonth = new HjhBailConfigInfo();
        // 等额本息配置
        hjhBailConfigInfoMonth.setBorrowStyle("month");
        // 新增授信校验
        hjhBailConfigInfoMonth.setIsNewCredit(bailConfigAddRequest.getMonthNCL() == null ? 0 : bailConfigAddRequest.getMonthNCL());
        // 在贷余额授信校验
        hjhBailConfigInfoMonth.setIsLoanCredit(bailConfigAddRequest.getMonthLCL() == null ? 0 : bailConfigAddRequest.getMonthLCL());
        // 保证金的回滚方式
        hjhBailConfigInfoMonth.setRepayCapitalType(bailConfigAddRequest.getMonthRCT() == null ? 0 : bailConfigAddRequest.getMonthRCT());
        hjhBailConfigInfoList.add(hjhBailConfigInfoMonth);

        HjhBailConfigInfo hjhBailConfigInfoEnd = new HjhBailConfigInfo();
        // 按月计息，到期还本还息配置
        hjhBailConfigInfoEnd.setBorrowStyle("end");
        // 新增授信校验
        hjhBailConfigInfoEnd.setIsNewCredit(bailConfigAddRequest.getEndNCL() == null ? 0 : bailConfigAddRequest.getEndNCL());
        // 在贷余额授信校验
        hjhBailConfigInfoEnd.setIsLoanCredit(bailConfigAddRequest.getEndLCL() == null ? 0 : bailConfigAddRequest.getEndLCL());
        // 保证金的回滚方式
        hjhBailConfigInfoEnd.setRepayCapitalType(bailConfigAddRequest.getEndRCT() == null ? 0 : bailConfigAddRequest.getEndRCT());
        hjhBailConfigInfoList.add(hjhBailConfigInfoEnd);

        HjhBailConfigInfo hjhBailConfigInfoEndmonth = new HjhBailConfigInfo();
        // 先息后本配置
        hjhBailConfigInfoEndmonth.setBorrowStyle("endmonth");
        // 新增授信校验
        hjhBailConfigInfoEndmonth.setIsNewCredit(bailConfigAddRequest.getEndmonthNCL() == null ? 0 : bailConfigAddRequest.getEndmonthNCL());
        // 在贷余额授信校验
        hjhBailConfigInfoEndmonth.setIsLoanCredit(bailConfigAddRequest.getEndmonthLCL() == null ? 0 : bailConfigAddRequest.getEndmonthLCL());
        // 保证金的回滚方式
        hjhBailConfigInfoEndmonth.setRepayCapitalType(bailConfigAddRequest.getEndmonthRCT() == null ? 0 : bailConfigAddRequest.getEndmonthRCT());
        hjhBailConfigInfoList.add(hjhBailConfigInfoEndmonth);

        HjhBailConfigInfo hjhBailConfigInfoEndday = new HjhBailConfigInfo();
        // 按天计息，到期还本息配置
        hjhBailConfigInfoEndday.setBorrowStyle("endday");
        // 新增授信校验
        hjhBailConfigInfoEndday.setIsNewCredit(bailConfigAddRequest.getEnddayNCL() == null ? 0 : bailConfigAddRequest.getEnddayNCL());
        // 在贷余额授信校验
        hjhBailConfigInfoEndday.setIsLoanCredit(bailConfigAddRequest.getEnddayLCL() == null ? 0 : bailConfigAddRequest.getEnddayLCL());
        // 保证金的回滚方式
        hjhBailConfigInfoEndday.setRepayCapitalType(bailConfigAddRequest.getEnddayRCT() == null ? 0 : bailConfigAddRequest.getEnddayRCT());
        hjhBailConfigInfoList.add(hjhBailConfigInfoEndday);

        HjhBailConfigInfo hjhBailConfigInfoPrincipal = new HjhBailConfigInfo();
        // 等额本金配置
        hjhBailConfigInfoPrincipal.setBorrowStyle("principal");
        // 新增授信校验
        hjhBailConfigInfoPrincipal.setIsNewCredit(bailConfigAddRequest.getPrincipalNCL() == null ? 0 : bailConfigAddRequest.getPrincipalNCL());
        // 在贷余额授信校验
        hjhBailConfigInfoPrincipal.setIsLoanCredit(bailConfigAddRequest.getPrincipalLCL() == null ? 0 : bailConfigAddRequest.getPrincipalLCL());
        // 保证金的回滚方式
        hjhBailConfigInfoPrincipal.setRepayCapitalType(bailConfigAddRequest.getPrincipalRCT() == null ? 0 : bailConfigAddRequest.getPrincipalRCT());
        hjhBailConfigInfoList.add(hjhBailConfigInfoPrincipal);

        HjhBailConfigInfo hjhBailConfigInfoSeason = new HjhBailConfigInfo();
        // 等额本金配置
        hjhBailConfigInfoSeason.setBorrowStyle("season");
        // 新增授信校验
        hjhBailConfigInfoSeason.setIsNewCredit(bailConfigAddRequest.getSeasonNCL() == null ? 0 : bailConfigAddRequest.getSeasonNCL());
        // 在贷余额授信校验
        hjhBailConfigInfoSeason.setIsLoanCredit(bailConfigAddRequest.getSeasonLCL() == null ? 0 : bailConfigAddRequest.getSeasonLCL());
        // 保证金的回滚方式
        hjhBailConfigInfoSeason.setRepayCapitalType(bailConfigAddRequest.getSeasonRCT() == null ? 0 : bailConfigAddRequest.getSeasonRCT());
        hjhBailConfigInfoList.add(hjhBailConfigInfoSeason);

        HjhBailConfigInfo hjhBailConfigInfoEndmonths = new HjhBailConfigInfo();
        // 等额本金配置
        hjhBailConfigInfoEndmonths.setBorrowStyle("endmonths");
        // 新增授信校验
        hjhBailConfigInfoEndmonths.setIsNewCredit(bailConfigAddRequest.getEndmonthsNCL() == null ? 0 : bailConfigAddRequest.getEndmonthsNCL());
        // 在贷余额授信校验
        hjhBailConfigInfoEndmonths.setIsLoanCredit(bailConfigAddRequest.getEndmonthsLCL() == null ? 0 : bailConfigAddRequest.getEndmonthsLCL());
        // 保证金的回滚方式
        hjhBailConfigInfoEndmonths.setRepayCapitalType(bailConfigAddRequest.getEndmonthsRCT() == null ? 0 : bailConfigAddRequest.getEndmonthsRCT());
        hjhBailConfigInfoList.add(hjhBailConfigInfoEndmonths);

        return hjhBailConfigInfoList;
    }
}
