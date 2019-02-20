/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.exception.impl;

import com.hyjf.am.resquest.admin.AssetExceptionRequest;
import com.hyjf.am.resquest.admin.BailConfigAddRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.admin.exception.AssetExceptionService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.AssetExceptionCustomizeVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version AssetExceptionServiceImpl, v0.1 2018/9/28 19:13
 */
@Service
public class AssetExceptionServiceImpl extends BaseServiceImpl implements AssetExceptionService {

    /**
     * 异常标的列表总件数
     *
     * @param assetExceptionRequest
     * @return
     */
    @Override
    public Integer selectAssetExceptionCount(AssetExceptionRequest assetExceptionRequest) {
        return this.assetExceptionCustomizeMapper.countBorrowDelete(assetExceptionRequest);
    }

    /**
     * 异常标的列表查询
     *
     * @param assetExceptionRequest
     * @return
     */
    @Override
    public List<AssetExceptionCustomizeVO> selectAssetExceptionRecordList(AssetExceptionRequest assetExceptionRequest) {
        return this.assetExceptionCustomizeMapper.selectBorrowDeleteList(assetExceptionRequest);
    }

    /**
     * 插入异常标的并更新保证金
     *
     * @param assetExceptionRequest
     * @return
     */
    @Override
    public Boolean insertAssetException(AssetExceptionRequest assetExceptionRequest) {
        // 项目编号
        String borrowNid = assetExceptionRequest.getBorrowNid();
        // 获取借款详情
        Borrow borrow = getBorrow(borrowNid);
        BorrowInfo borrowInfo = getBorrowInfoByNid(borrowNid);
        // 获取资产来源
        String instCode = borrowInfo.getInstCode();
        // 获取借款金额
        BigDecimal account = borrow.getAccount();

        // 异常标的信息
        BorrowDelete borrowDelete = new BorrowDelete();
        borrowDelete.setInstCode(instCode);
        borrowDelete.setBorrowNid(borrowNid);
        borrowDelete.setAccount(account);
        borrowDelete.setExceptionType(assetExceptionRequest.getExceptionType());
        borrowDelete.setExceptionRemark(assetExceptionRequest.getExceptionRemark());
        borrowDelete.setStatus(borrow.getStatus());
        borrowDelete.setExceptionTime(assetExceptionRequest.getExceptionTime());
        borrowDelete.setCreateUserId(assetExceptionRequest.getCreateUserId());
        borrowDelete.setCreateTime(assetExceptionRequest.getCreateTime());

        boolean re;
        // 获取该机构保证金配置
        HjhBailConfig hjhBailConfig = selectHjhBailConfigByInstCode(instCode);
        if (null != hjhBailConfig) {
            // 插入异常标的
            re = borrowDeleteMapper.insertSelective(borrowDelete) > 0 ? true : false;
            if (re) {
                // 发标已发额度
                hjhBailConfig.setLoanMarkLine(hjhBailConfig.getLoanMarkLine().subtract(account));
                // loan_balance在贷余额
                hjhBailConfig.setLoanBalance(hjhBailConfig.getLoanBalance().subtract(account));
                // 发标额度余额remain_mark_line
                hjhBailConfig.setRemainMarkLine(hjhBailConfig.getRemainMarkLine().add(account));

                // 周期内发标已发额度
                BigDecimal sendedAccountByCycBD = BigDecimal.ZERO;
                String sendedAccountByCyc = this.selectSendedAccountByCyc(instCode, hjhBailConfig.getTimestart(), hjhBailConfig.getTimeend());
                if (StringUtils.isNotBlank(sendedAccountByCyc)) {
                    sendedAccountByCycBD = new BigDecimal(sendedAccountByCyc);
                }
                hjhBailConfig.setCycLoanTotal(sendedAccountByCycBD);

                re = this.hjhBailConfigMapper.updateByPrimaryKey(hjhBailConfig) > 0 ? true : false;
            }
        } else {
            re = false;
        }
        return re;
    }

    /**
     * 项目编号是否存在
     *
     * @param borrowNid
     * @return
     */
    @Override
    public String isExistsBorrow(String borrowNid) {
        // 查询该项目编号是否存在借款表中
        Borrow borrow = this.getBorrow(borrowNid);
        if (null == borrow) {
            return "项目编号不存在！";
        }
        // 出借或出借之后状态的标的无法添加
        if (borrow.getStatus() != 0 && borrow.getStatus() != 1) {
            return "已进入出借或出借之后状态的标的无法添加。";
        }
        // 判断是否已经添加到异常表中
        BorrowDeleteExample example = new BorrowDeleteExample();
        example.createCriteria().andBorrowNidEqualTo(borrow.getBorrowNid());
        List<BorrowDelete> borrowDeleteList = this.borrowDeleteMapper.selectByExample(example);
        if (null != borrowDeleteList && borrowDeleteList.size() > 0) {
            return "该标的编号已存在异常表中、请勿重复添加。";
        }
        // 检查标的来源机构是否添加保证金配置表
        BorrowInfo borrowInfo = this.getBorrowInfoByNid(borrowNid);
        HjhBailConfig hjhBailConfig = this.selectHjhBailConfigByInstCode(borrowInfo.getInstCode());
        if (null == hjhBailConfig) {
            return "该标的来源机构未配置保证金！";
        }
        // 验证通过返回空字符串
        return "";
    }

    /**
     * 删除异常标的并更新保证金
     *
     * @param assetExceptionRequest
     * @return
     */
    @Override
    public Boolean deleteAssetException(AssetExceptionRequest assetExceptionRequest) {
        boolean re = false;
        // 获取主键
        Integer id = assetExceptionRequest.getId();
        // 判断异常标的中是否存在
        BorrowDelete borrowDelete = this.borrowDeleteMapper.selectByPrimaryKey(id);
        if (null != borrowDelete) {
            // 获取标的编号
            String borrowNid = borrowDelete.getBorrowNid();
            // 获取借款详情
            Borrow borrow = getBorrow(borrowNid);
            BorrowInfo borrowInfo = getBorrowInfoByNid(borrowNid);
            // 获取标的资产来源和借款金额
            String instCode = borrowInfo.getInstCode();
            BigDecimal account = borrow.getAccount();
            // 获取该机构保证金配置
            HjhBailConfig hjhBailConfig = this.selectHjhBailConfigByInstCode(instCode);
            if (null != hjhBailConfig) {
                re = this.borrowDeleteMapper.deleteByPrimaryKey(id) > 0 ? true : false;
                if (re) {
                    // 发标已发额度
                    hjhBailConfig.setLoanMarkLine(hjhBailConfig.getLoanMarkLine().add(account));
                    // 周期内发标已发额度
                    BigDecimal sendedAccountByCycBD = BigDecimal.ZERO;
                    String sendedAccountByCyc = this.selectSendedAccountByCyc(instCode, hjhBailConfig.getTimestart(), hjhBailConfig.getTimeend());
                    if (StringUtils.isNotBlank(sendedAccountByCyc)) {
                        sendedAccountByCycBD = new BigDecimal(sendedAccountByCyc);
                    }
                    hjhBailConfig.setCycLoanTotal(sendedAccountByCycBD);
                    // loan_balance在贷余额
                    hjhBailConfig.setLoanBalance(hjhBailConfig.getLoanBalance().add(account));
                    // 发标额度余额remain_mark_line
                    hjhBailConfig.setRemainMarkLine(hjhBailConfig.getRemainMarkLine().subtract(account));
                    re = this.hjhBailConfigMapper.updateByPrimaryKey(hjhBailConfig) > 0 ? true : false;
                }
            } else {
                re = false;
            }
        }
        return re;
    }

    /**
     * 修改异常标的并更新保证金
     *
     * @param assetExceptionRequest
     * @return
     */
    @Override
    public Boolean updateAssetException(AssetExceptionRequest assetExceptionRequest) {
        BorrowDelete borrowDelete = new BorrowDelete();
        borrowDelete.setId(assetExceptionRequest.getId());
        borrowDelete.setExceptionType(assetExceptionRequest.getExceptionType());
        borrowDelete.setExceptionRemark(assetExceptionRequest.getExceptionRemark());
        borrowDelete.setExceptionTime(assetExceptionRequest.getExceptionTime());
        borrowDelete.setUpdateTime(assetExceptionRequest.getUpdateTime());
        borrowDelete.setUpdateUserId(assetExceptionRequest.getUpdateUserId());
        return this.borrowDeleteMapper.updateByPrimaryKeySelective(borrowDelete) > 0;
    }

    /**
     * 根据资产来源获取保证金详情
     *
     * @param instCode
     * @return
     */
    private HjhBailConfig selectHjhBailConfigByInstCode(String instCode) {
        HjhBailConfigExample example = new HjhBailConfigExample();
        example.createCriteria().andInstCodeEqualTo(instCode).andDelFlgEqualTo(0);
        List<HjhBailConfig> hjhBailConfigList = this.hjhBailConfigMapper.selectByExample(example);
        if (null != hjhBailConfigList && hjhBailConfigList.size() > 0) {
            return hjhBailConfigList.get(0);
        }
        return null;
    }

    /**
     * 获取周期内发标已发额度
     *
     * @param instCode
     * @return
     */
    private String selectSendedAccountByCyc(String instCode, String timeStart, String timeEnd) {
        BailConfigAddRequest bailConfigAddRequest = new BailConfigAddRequest();
        bailConfigAddRequest.setInstCode(instCode);
        bailConfigAddRequest.setTimestart(timeStart);
        bailConfigAddRequest.setTimeend(timeEnd);
        return this.hjhBailConfigCustomizeMapper.selectSendedAccountByCyc(bailConfigAddRequest);
    }

}
