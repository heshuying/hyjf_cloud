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
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
