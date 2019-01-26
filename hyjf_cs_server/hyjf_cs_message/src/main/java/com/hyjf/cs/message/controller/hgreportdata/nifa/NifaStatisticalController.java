/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.hgreportdata.nifa;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.hgreportdata.nifa.NifaBorrowInfoResponse;
import com.hyjf.am.response.hgreportdata.nifa.NifaCreditInfoResponse;
import com.hyjf.am.vo.hgreportdata.nifa.*;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.hgreportdata.nifa.NifaBorrowInfoEntity;
import com.hyjf.cs.message.bean.hgreportdata.nifa.NifaCreditInfoEntity;
import com.hyjf.cs.message.service.hgreportdata.nifa.NifaStatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 中互金借款人信息处理
 *
 * @author PC-LIUSHOUYI
 * @version NifaStatisticalController, v0.1 2019/1/18 17:26
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/nifaStatistical")
public class NifaStatisticalController extends BaseController {

    @Autowired
    NifaStatisticalService nifaStatisticalService;

    /**
     * 查询该借款数据是否上报完成
     *
     * @return
     */
    @PostMapping("/selectNifaBorrowInfoByProjectNo")
    public NifaBorrowInfoResponse selectNifaBorrowInfoByProjectNo(@RequestBody @Valid NifaBorrowInfoVO nifaBorrowInfoVO) {
        NifaBorrowInfoResponse response = new NifaBorrowInfoResponse();
        String projectNo = nifaBorrowInfoVO.getProjectNo();
        String msgBody = nifaBorrowInfoVO.getMessage();
        NifaBorrowInfoEntity list = nifaStatisticalService.selectNifaBorrowInfoByProjectNo(projectNo, msgBody);
        if (null != list) {
            NifaBorrowInfoVO result = CommonUtils.convertBean(list, NifaBorrowInfoVO.class);
            response.setResult(result);
        }
        return response;
    }

    /**
     * 借款人信息插入
     *
     * @param nifaBorrowerInfoVO
     * @return
     */
    @PostMapping("/insertNifaBorrowerInfo")
    public BooleanResponse insertNifaBorrowerInfo(@RequestBody @Valid NifaBorrowerInfoVO nifaBorrowerInfoVO) {
        nifaStatisticalService.insertNifaBorrowerInfo(nifaBorrowerInfoVO);
        return new BooleanResponse(true);
    }

    /**
     * 投资人信息插入
     *
     * @param nifaTenderInfoVO
     * @return
     */
    @PostMapping("/insertNifaTenderInfo")
    public BooleanResponse insertNifaTenderInfo(@RequestBody @Valid NifaTenderInfoVO nifaTenderInfoVO) {
        nifaStatisticalService.insertNifaTenderInfo(nifaTenderInfoVO);
        return new BooleanResponse(true);
    }

    /**
     * 借款信息插入
     *
     * @param nifaBorrowInfoVO
     * @return
     */
    @PostMapping("/insertNifaBorrowInfo")
    public BooleanResponse insertNifaBorrowInfo(@RequestBody @Valid NifaBorrowInfoVO nifaBorrowInfoVO) {
        nifaStatisticalService.insertNifaBorrowInfo(nifaBorrowInfoVO);
        return new BooleanResponse(true);
    }

    /**
     * 已做成借款人数据的更新上报状态为1
     *
     * @return
     */
    @PostMapping("/updateNifaBorrowerInfo")
    public BooleanResponse updateNifaBorrowerInfo(@RequestBody @Valid NifaBorrowerInfoVO nifaBorrowerInfoVO) {
        String projectNo = nifaBorrowerInfoVO.getProjectNo();
        String msgBody = nifaBorrowerInfoVO.getMessage();
        nifaStatisticalService.updateNifaBorrowerInfo(projectNo, msgBody);
        return new BooleanResponse(true);
    }

    /**
     * 已做成投资人数据的更新上报状态为1
     *
     * @return
     */
    @PostMapping("/updateNifaTenderInfo")
    public BooleanResponse updateNifaTenderInfo(@RequestBody @Valid NifaTenderInfoVO nifaTenderInfoVO) {
        String projectNo = nifaTenderInfoVO.getProjectNo();
        String msgBody = nifaTenderInfoVO.getMessage();
        nifaStatisticalService.updateNifaTenderInfo(projectNo, msgBody);
        return new BooleanResponse(true);
    }

    /**
     * 散标债转数据拉取
     *
     * @param nifaCreditInfoVO
     * @return
     */
    @PostMapping("/selectNifaCreditInfoByCreditNid")
    public NifaCreditInfoResponse selectNifaCreditInfoByCreditNid(@RequestBody @Valid NifaCreditInfoVO nifaCreditInfoVO) {
        NifaCreditInfoResponse response = new NifaCreditInfoResponse();
        String projectNo = nifaCreditInfoVO.getProjectNo();
        NifaCreditInfoEntity list = nifaStatisticalService.selectNifaCreditInfoByCreditNid(projectNo);
        if (null != list) {
            NifaCreditInfoVO result = CommonUtils.convertBean(list, NifaCreditInfoVO.class);
            response.setResult(result);
        }
        return response;
    }

    /**
     * 保存散标债转承接人信息
     *
     * @param nifaCreditTransferVO
     * @return
     */
    @PostMapping("/insertNifaCreditTransfer")
    public BooleanResponse insertNifaCreditTransfer(@RequestBody @Valid NifaCreditTransferVO nifaCreditTransferVO) {
        nifaStatisticalService.insertNifaCreditTransfer(nifaCreditTransferVO);
        return new BooleanResponse(true);
    }

    /**
     * 保存散标债转信息
     *
     * @param nifaCreditInfoVO
     * @return
     */
    @PostMapping("/insertNifaCreditInfo")
    public BooleanResponse insertNifaCreditInfo(@RequestBody @Valid NifaCreditInfoVO nifaCreditInfoVO) {
        nifaStatisticalService.insertNifaCreditInfo(nifaCreditInfoVO);
        return new BooleanResponse(true);
    }
}
