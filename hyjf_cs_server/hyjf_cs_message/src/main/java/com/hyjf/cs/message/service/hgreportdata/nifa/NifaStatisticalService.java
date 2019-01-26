/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.hgreportdata.nifa;

import com.hyjf.am.vo.hgreportdata.nifa.*;
import com.hyjf.cs.common.service.BaseService;
import com.hyjf.cs.message.bean.hgreportdata.nifa.NifaBorrowInfoEntity;
import com.hyjf.cs.message.bean.hgreportdata.nifa.NifaCreditInfoEntity;

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
}
