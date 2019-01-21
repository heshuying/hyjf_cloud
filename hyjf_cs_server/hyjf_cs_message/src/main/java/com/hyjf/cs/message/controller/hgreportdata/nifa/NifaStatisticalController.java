/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.hgreportdata.nifa;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.hgreportdata.nifa.NifaBorrowInfoResponse;
import com.hyjf.am.vo.hgreportdata.nifa.NifaBorrowInfoVO;
import com.hyjf.am.vo.hgreportdata.nifa.NifaBorrowerInfoVO;
import com.hyjf.am.vo.hgreportdata.nifa.NifaTenderInfoVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.hgreportdata.nifa.NifaBorrowInfoEntity;
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
@RequestMapping("/cs-message/nifa_statistical")
public class NifaStatisticalController extends BaseController {

    @Autowired
    NifaStatisticalService nifaStatisticalService;

    /**
     * 查询该借款数据是否上报完成
     *
     * @return
     */
    @PostMapping("/get_nifa_borrow_info_by_project_no")
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
    @PostMapping("/insert_nifa_borrower_info")
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
    @PostMapping("/insert_nifa_tender_info")
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
    @PostMapping("/insert_nifa_borrow_info")
    public BooleanResponse insertNifaBorrowInfo(@RequestBody @Valid NifaBorrowInfoVO nifaBorrowInfoVO) {
        nifaStatisticalService.insertNifaBorrowInfo(nifaBorrowInfoVO);
        return new BooleanResponse(true);
    }

    /**
     * 已做成借款人数据的更新上报状态为1
     *
     * @return
     */
    @PostMapping("/update_nifa_borrower_info")
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
    @PostMapping("/update_nifa_tender_info")
    public BooleanResponse updateNifaTenderInfo(@RequestBody @Valid NifaTenderInfoVO nifaTenderInfoVO) {
        String projectNo = nifaTenderInfoVO.getProjectNo();
        String msgBody = nifaTenderInfoVO.getMessage();
        nifaStatisticalService.updateNifaTenderInfo(projectNo, msgBody);
        return new BooleanResponse(true);
    }
}
