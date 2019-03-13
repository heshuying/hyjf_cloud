/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.hgreportdata.nifa;

import com.hyjf.am.vo.hgreportdata.nifa.*;
import com.hyjf.cs.common.service.BaseService;
import com.hyjf.cs.message.bean.hgreportdata.nifa.*;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version NifaStatisticalService, v0.1 2019/1/19 10:07
 */
public interface NifaStatisticalService extends BaseService {

    /**
     * 根据借款编号和消息体查询该数据是否入库
     *
     * @param projectNo
     * @param msgBody
     * @return
     */
    NifaBorrowInfoEntity selectNifaBorrowInfoByProjectNo(String projectNo, String msgBody);

    /**
     * 插入借款人信息
     *
     * @param nifaBorrowerInfoVO
     */
    void insertNifaBorrowerInfo(NifaBorrowerInfoVO nifaBorrowerInfoVO);

    /**
     * 投资人信息插入
     *
     * @param nifaTenderInfoVO
     */
    void insertNifaTenderInfo(NifaTenderInfoVO nifaTenderInfoVO);

    /**
     * 借款信息插入
     *
     * @param nifaBorrowInfoVO
     */
    void insertNifaBorrowInfo(NifaBorrowInfoVO nifaBorrowInfoVO);

    /**
     * 已做成借款人数据的更新上报状态为1
     *
     * @param projectNo
     * @param msgBody
     */
    void updateNifaBorrowerInfo(String projectNo, String msgBody);

    /**
     * 已做成投资人数据的更新上报状态为1
     *
     * @param projectNo
     * @param msgBody
     */
    void updateNifaTenderInfo(String projectNo, String msgBody);

    /**
     * 散标债转数据拉取
     *
     * @param projectNo
     * @return
     */
    NifaCreditInfoEntity selectNifaCreditInfoByCreditNid(String projectNo);

    /**
     * 保存散标债转承接人信息
     *
     * @param nifaCreditTransferVO
     */
    void insertNifaCreditTransfer(NifaCreditTransferVO nifaCreditTransferVO);

    /**
     * 保存散标债转信息
     *
     * @param nifaCreditInfoVO
     */
    void insertNifaCreditInfo(NifaCreditInfoVO nifaCreditInfoVO);

    /**
     * 查询未上送的借款信息
     *
     * @param nifaBorrowInfoVO
     * @return
     */
    List<NifaBorrowInfoEntity> selectNifaBorrowInfoByHistoryDate(NifaBorrowInfoVO nifaBorrowInfoVO);

    /**
     * 处理完成借款信息后更新mongo数据状态
     *
     * @param nifaBorrowInfoVO
     */
    void updateNifaBorrowInfoByHistoryDate(NifaBorrowInfoVO nifaBorrowInfoVO);

    /**
     * 查询相应标的的投资人信息
     *
     * @param projectNo
     * @return
     */
    List<NifaTenderInfoEntity> selectNifaTenderInfo(List<String> projectNo);

    /**
     * 更新相应标的的投资人信息
     *
     * @param projectNo
     */
    void updateTenderInfo(List<String> projectNo);

    /**
     * 拉取相应借款人信息
     *
     * @param projectNo
     * @return
     */
    List<NifaBorrowerInfoEntity> selectNifaBorrowerInfo(List<String> projectNo);

    /**
     * 更新相应标的的借款人信息
     *
     * @param projectNo
     */
    void updateBorrowerInfo(List<String> projectNo);

    /**
     * 查询未上送的债转信息
     *
     * @param nifaCreditInfoVO
     * @return
     */
    List<NifaCreditInfoEntity> selectNifaCreditInfo(NifaCreditInfoVO nifaCreditInfoVO);

    /**
     * 更新上送的债转信息
     *
     * @param nifaCreditInfoVO
     */
    void updateNifaCreditInfo(NifaCreditInfoVO nifaCreditInfoVO);

    /**
     * 查询未上送的债转承接人信息
     *
     * @param projectNo
     * @return
     */
    List<NifaCreditTransferEntity> selectNifaCreditTransfer(List<String> projectNo);

    /**
     * 更新相应承接人上送状态
     *
     * @param projectNo
     */
    void updateCreditTransfer(List<String> projectNo);

    /**
     * 查询该天日期插入mongo的放还款标的
     *
     * @param historyData
     * @return
     */
    List<NifaBorrowInfoEntity> selectBorrowRepayPlanByHistoryData(String historyData);
}
