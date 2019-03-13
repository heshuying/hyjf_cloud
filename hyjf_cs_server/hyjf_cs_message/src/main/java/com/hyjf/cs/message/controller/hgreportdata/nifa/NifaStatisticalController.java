/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.hgreportdata.nifa;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.hgreportdata.nifa.*;
import com.hyjf.am.vo.hgreportdata.nifa.*;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.hgreportdata.nifa.*;
import com.hyjf.cs.message.service.hgreportdata.nifa.NifaStatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
        NifaBorrowInfoEntity re = nifaStatisticalService.selectNifaBorrowInfoByProjectNo(projectNo, msgBody);
        if (null != re) {
            NifaBorrowInfoVO result = CommonUtils.convertBean(re, NifaBorrowInfoVO.class);
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
        NifaCreditInfoEntity re = nifaStatisticalService.selectNifaCreditInfoByCreditNid(projectNo);
        if (null != re) {
            NifaCreditInfoVO result = CommonUtils.convertBean(re, NifaCreditInfoVO.class);
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

    /**
     * 查询未上送的借款信息
     *
     * @return
     */
    @PostMapping("/selectNifaBorrowInfoByHistoryDate")
    public NifaBorrowInfoResponse selectNifaBorrowInfoByHistoryDate(@RequestBody @Valid NifaBorrowInfoVO nifaBorrowInfoVO) {
        NifaBorrowInfoResponse response = new NifaBorrowInfoResponse();
        List<NifaBorrowInfoEntity> list = nifaStatisticalService.selectNifaBorrowInfoByHistoryDate(nifaBorrowInfoVO);
        if (null != list && list.size()>0) {
            List<NifaBorrowInfoVO> result = CommonUtils.convertBeanList(list, NifaBorrowInfoVO.class);
            response.setResultList(result);
        }
        return response;
    }

    /**
     * 处理完成借款信息后更新mongo数据状态
     *
     * @return
     */
    @PostMapping("/updateNifaBorrowInfoByHistoryDate")
    public BooleanResponse updateNifaBorrowInfoByHistoryDate(@RequestBody @Valid NifaBorrowInfoVO nifaBorrowInfoVO) {
        nifaStatisticalService.updateNifaBorrowInfoByHistoryDate(nifaBorrowInfoVO);
        return new BooleanResponse(true);
    }

    /**
     * 查询相应标的的投资人信息
     *
     * @param nifaBorrowInfoVOList
     * @return
     */
    @PostMapping("/selectNifaTenderInfo")
    public NifaTenderInfoResponse selectNifaTenderInfo(@RequestBody @Valid List<NifaBorrowInfoVO> nifaBorrowInfoVOList) {
        NifaTenderInfoResponse response = new NifaTenderInfoResponse();
        List<String> listQuery = new ArrayList<>();
        for (NifaBorrowInfoVO nifaBorrowInfoVO : nifaBorrowInfoVOList) {
            listQuery.add(nifaBorrowInfoVO.getProjectNo());
        }
        List<NifaTenderInfoEntity> list = nifaStatisticalService.selectNifaTenderInfo(listQuery);
        if (null != list && list.size()>0) {
            List<NifaTenderInfoVO> result = CommonUtils.convertBeanList(list, NifaTenderInfoVO.class);
            response.setResultList(result);
        }
        return response;
    }

    /**
     * 更新相应标的的投资人信息
     *
     * @return
     */
    @PostMapping("/updateTenderInfo")
    public BooleanResponse updateTenderInfo(@RequestBody @Valid List<NifaBorrowInfoVO> nifaBorrowInfoVOList) {
        List<String> listQuery = new ArrayList<>();
        for (NifaBorrowInfoVO nifaBorrowInfoVO : nifaBorrowInfoVOList) {
            listQuery.add(nifaBorrowInfoVO.getProjectNo());
        }

        nifaStatisticalService.updateTenderInfo(listQuery);
        return new BooleanResponse(true);
    }

    /**
     * 拉取相应借款人信息
     *
     * @param nifaBorrowInfoVOList
     * @return
     */
    @PostMapping("/selectNifaBorrowerInfo")
    public NifaBorrowerInfoResponse selectNifaBorrowerInfo(@RequestBody @Valid  List<NifaBorrowInfoVO> nifaBorrowInfoVOList) {
        NifaBorrowerInfoResponse response = new NifaBorrowerInfoResponse();
        List<String> listQuery = new ArrayList<>();
        for (NifaBorrowInfoVO nifaBorrowInfoVO : nifaBorrowInfoVOList) {
            listQuery.add(nifaBorrowInfoVO.getProjectNo());
        }

        List<NifaBorrowerInfoEntity> list = nifaStatisticalService.selectNifaBorrowerInfo(listQuery);
        if (null != list && list.size()>0) {
            List<NifaBorrowerInfoVO> result = CommonUtils.convertBeanList(list, NifaBorrowerInfoVO.class);
            response.setResultList(result);
        }
        return response;
    }

    /**
     * 更新相应标的的借款人信息
     *
     * @return
     */
    @PostMapping("/updateBorrowerInfo")
    public BooleanResponse updateBorrowerInfo(@RequestBody @Valid List<NifaBorrowInfoVO> nifaBorrowInfoVOList) {
        List<String> listQuery = new ArrayList<>();
        for (NifaBorrowInfoVO nifaBorrowInfoVO : nifaBorrowInfoVOList) {
            listQuery.add(nifaBorrowInfoVO.getProjectNo());
        }
        nifaStatisticalService.updateBorrowerInfo(listQuery);
        return new BooleanResponse(true);
    }

    /**
     * 查询未上送的债转信息
     *
     * @param nifaCreditInfoVO
     * @return
     */
    @PostMapping("/selectNifaCreditInfo")
    public NifaCreditInfoResponse selectNifaCreditInfo(@RequestBody @Valid NifaCreditInfoVO nifaCreditInfoVO) {
        NifaCreditInfoResponse response = new NifaCreditInfoResponse();
        List<NifaCreditInfoEntity> list = nifaStatisticalService.selectNifaCreditInfo(nifaCreditInfoVO);
        if (null != list && list.size()>0) {
            List<NifaCreditInfoVO> result = CommonUtils.convertBeanList(list, NifaCreditInfoVO.class);
            response.setResultList(result);
        }
        return response;
    }

    /**
     * 更新上送的债转信息
     *
     * @param nifaCreditInfoVO
     * @return
     */
    @PostMapping("/updateNifaCreditInfo")
    public BooleanResponse updateNifaCreditInfo(@RequestBody @Valid NifaCreditInfoVO nifaCreditInfoVO) {
        nifaStatisticalService.updateNifaCreditInfo(nifaCreditInfoVO);
        return new BooleanResponse(true);
    }

    /**
     * 查询未上送的债转承接人信息
     *
     * @param nifaCreditInfoEntities
     * @return
     */
    @PostMapping("/selectNifaCreditTransfer")
    public NifaCreditTransferResponse selectNifaCreditTransfer(@RequestBody @Valid List<NifaCreditInfoVO> nifaCreditInfoEntities) {
        NifaCreditTransferResponse response = new NifaCreditTransferResponse();
        List<String> listQuery = new ArrayList<>();
        for (NifaCreditInfoVO nifaCreditInfoVO : nifaCreditInfoEntities) {
            listQuery.add(nifaCreditInfoVO.getProjectNo());
        }
        List<NifaCreditTransferEntity> list = nifaStatisticalService.selectNifaCreditTransfer(listQuery);
        if (null != list && list.size()>0) {
            List<NifaCreditTransferVO> result = CommonUtils.convertBeanList(list, NifaCreditTransferVO.class);
            response.setResultList(result);
        }
        return response;
    }

    /**
     * 更新相应承接人上送状态
     *
     * @return
     */
    @PostMapping("/updateCreditTransfer")
    public BooleanResponse updateCreditTransfer(@RequestBody @Valid List<NifaCreditInfoVO> nifaCreditInfoEntities) {
        List<String> listQuery = new ArrayList<>();
        for (NifaCreditInfoVO nifaCreditInfoVO : nifaCreditInfoEntities) {
            listQuery.add(nifaCreditInfoVO.getProjectNo());
        }
        nifaStatisticalService.updateCreditTransfer(listQuery);
        return new BooleanResponse(true);
    }

    /**
     * 查询该天日期插入mongo的放还款标的
     *
     * @param historyData
     * @return
     */
    @GetMapping("/selectBorrowRepayPlanByHistoryData/{historyData}")
    public NifaBorrowInfoResponse selectBorrowRepayPlanByHistoryData(@PathVariable String historyData) {
        NifaBorrowInfoResponse response = new NifaBorrowInfoResponse();
        List<NifaBorrowInfoEntity> list = this.nifaStatisticalService.selectBorrowRepayPlanByHistoryData(historyData);
        if(!CollectionUtils.isEmpty(list)) {
            List<NifaBorrowInfoVO> listVO = CommonUtils.convertBeanList(list,NifaBorrowInfoVO.class);
            response.setResultList(listVO);
        }
        return response;
    }
}
