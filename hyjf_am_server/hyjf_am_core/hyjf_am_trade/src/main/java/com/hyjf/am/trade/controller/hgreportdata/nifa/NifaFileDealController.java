/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.hgreportdata.nifa;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.admin.NifaContractTemplateResponse;
import com.hyjf.am.response.admin.NifaReportLogResponse;
import com.hyjf.am.response.hgreportdata.nifa.*;
import com.hyjf.am.response.trade.FddTempletResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.FddTemplet;
import com.hyjf.am.trade.dao.model.auto.NifaReportLog;
import com.hyjf.am.trade.dao.model.customize.*;
import com.hyjf.am.trade.service.hgreportdata.nifa.NifaFileDealService;
import com.hyjf.am.vo.admin.NifaContractTemplateVO;
import com.hyjf.am.vo.admin.NifaReportLogVO;
import com.hyjf.am.vo.hgreportdata.nifa.NifaContractEssenceVO;
import com.hyjf.am.vo.hgreportdata.nifa.NifaContractStatusVO;
import com.hyjf.am.vo.hgreportdata.nifa.NifaReceivedPaymentsVO;
import com.hyjf.am.vo.hgreportdata.nifa.NifaRepayInfoVO;
import com.hyjf.am.vo.trade.FddTempletVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version NifaFileDealController, v0.1 2018/9/4 16:36
 */
@Api(value = "互金上传下载文件任务")
@RestController
@RequestMapping("/am-trade/nifaFileDeal")
public class NifaFileDealController extends BaseController {

    @Autowired
    private NifaFileDealService nifaFileDealService;

    /**
     * 获取未成功下载日志的数据
     *
     * @return
     */
    @GetMapping("/selectNifaReportLogDownloadPath")
    public NifaReportLogResponse selectNifaReportLogDownloadPath() {
        NifaReportLogResponse response = new NifaReportLogResponse();
        List<NifaReportLog> nifaReportLogs = nifaFileDealService.selectNifaReportLogDownloadPath();
        if (!CollectionUtils.isEmpty(nifaReportLogs)) {
            List<NifaReportLogVO> voList = CommonUtils.convertBeanList(nifaReportLogs, NifaReportLogVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 拉取未成功上传的文件名集合
     *
     * @return
     */
    @GetMapping("/selectNifaReportLogList")
    public NifaFileReportLogResponse selectNifaReportLogList() {
        NifaFileReportLogResponse response = new NifaFileReportLogResponse();
        List<NifaReportLog> nifaReportLogs = nifaFileDealService.selectNifaReportLogList();
        if (!CollectionUtils.isEmpty(nifaReportLogs)) {
            List<NifaReportLogVO> voList = CommonUtils.convertBeanList(nifaReportLogs, NifaReportLogVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 判断文件是否生成过
     *
     * @return
     */
    @GetMapping("/selectNifaReportLogByFileName/{fileName}")
    public NifaFileReportLogResponse selectNifaReportLogByFileName(@PathVariable String fileName) {
        NifaFileReportLogResponse response = new NifaFileReportLogResponse();
        List<NifaReportLog> nifaReportLogs = nifaFileDealService.selectNifaReportLogByFileName(fileName);
        if (!CollectionUtils.isEmpty(nifaReportLogs)) {
            List<NifaReportLogVO> voList = CommonUtils.convertBeanList(nifaReportLogs, NifaReportLogVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 获取居间服务协议模板上传信息
     *
     * @return
     */
    @GetMapping("/selectFddTemplet/{templetId}")
    public FddTempletResponse selectFddTemplet(@PathVariable String templetId) {
        FddTempletResponse response = new FddTempletResponse();
        FddTemplet fddTemplet = nifaFileDealService.selectFddTemplet(templetId);
        if (null != fddTemplet) {
            FddTempletVO vo = CommonUtils.convertBean(fddTemplet, FddTempletVO.class);
            response.setResult(vo);
        }
        return response;
    }

    /**
     * 更新处理结果
     *
     * @param nifaReportLogVO
     * @return
     */
    @PostMapping(value = "/updateNifaReportLog")
    public BooleanResponse updateNifaReportLog(@RequestBody NifaReportLogVO nifaReportLogVO) {
        boolean uploadFlg = nifaFileDealService.updateNifaReportLog(nifaReportLogVO);
        return new BooleanResponse(uploadFlg);
    }

    /**
     * 判断文件是否生成过
     *
     * @return
     */
    @GetMapping("/selectMaxFileName/{beforDate}/{fileType}")
    public NifaReportLogResponse selectMaxFileName(@PathVariable String beforDate, @PathVariable String fileType) {
        NifaReportLogResponse response = new NifaReportLogResponse();
        List<NifaReportLog> nifaReportLogs = nifaFileDealService.selectMaxFileName(beforDate, fileType);
        if (!CollectionUtils.isEmpty(nifaReportLogs)) {
            List<NifaReportLogVO> voList = CommonUtils.convertBeanList(nifaReportLogs, NifaReportLogVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 获取最新的合同模板
     *
     * @return
     */
    @GetMapping("/selectNifaContractTemplate")
    public NifaContractTemplateResponse selectNifaContractTemplate() {
        NifaContractTemplateResponse response = new NifaContractTemplateResponse();
        List<NifaContractTemplateCustomize> nifaContractTemplateList = nifaFileDealService.selectNifaContractTemplate();
        if (!CollectionUtils.isEmpty(nifaContractTemplateList)) {
            List<NifaContractTemplateVO> voList = CommonUtils.convertBeanList(nifaContractTemplateList, NifaContractTemplateVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 查询未上送的合同要素数据
     *
     * @return
     */
    @GetMapping("/selectNifaContractEssenceList")
    public NifaContractEssenceResponse selectNifaContractEssenceList() {
        NifaContractEssenceResponse response = new NifaContractEssenceResponse();
        List<NifaContractEssenceCustomize> list = nifaFileDealService.selectNifaContractEssenceList();
        if (!CollectionUtils.isEmpty(list)) {
            List<NifaContractEssenceVO> voList = CommonUtils.convertBeanList(list, NifaContractEssenceVO.class);
            response.setResultList(voList);
        }
        return response;
    }


    /**
     * 查询为上送的还款数据
     *
     * @return
     */
    @GetMapping("/selectNifaRepayInfoList")
    public NifaRepayInfoResponse selectNifaRepayInfoList() {
        NifaRepayInfoResponse response = new NifaRepayInfoResponse();
        List<NifaRepayInfoCustomize> list = nifaFileDealService.selectNifaRepayInfoList();
        if (!CollectionUtils.isEmpty(list)) {
            List<NifaRepayInfoVO> voList = CommonUtils.convertBeanList(list, NifaRepayInfoVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 查询未上送的合同状态数据
     *
     * @return
     */
    @GetMapping("/selectNifaContractStatus")
    public NifaContractStatusResponse selectNifaContractStatus() {
        NifaContractStatusResponse response = new NifaContractStatusResponse();
        List<NifaContractStatusCustomize> list = nifaFileDealService.selectNifaContractStatus();
        if (!CollectionUtils.isEmpty(list)) {
            List<NifaContractStatusVO> voList = CommonUtils.convertBeanList(list, NifaContractStatusVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 查询为上送的回款记录
     *
     * @return
     */
    @GetMapping("/selectNifaReceivedPaymentsList")
    public NifaReceivedPaymentsResponse selectNifaReceivedPaymentsList() {
        NifaReceivedPaymentsResponse response = new NifaReceivedPaymentsResponse();
        List<NifaReceivedPaymentsCustomize> list = nifaFileDealService.selectNifaReceivedPaymentsList();
        if (!CollectionUtils.isEmpty(list)) {
            List<NifaReceivedPaymentsVO> voList = CommonUtils.convertBeanList(list, NifaReceivedPaymentsVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 插入上送记录
     *
     * @param nifaReportLogVO
     * @return
     */
    @PostMapping(value = "/insertNifaReportLog")
    public BooleanResponse insertNifaReportLog(@RequestBody NifaReportLogVO nifaReportLogVO) {
        NifaReportLog nifaReportLog = CommonUtils.convertBean(nifaReportLogVO, NifaReportLog.class);
        boolean uploadFlg = nifaFileDealService.insertNifaReportLog(nifaReportLog);
        return new BooleanResponse(uploadFlg);
    }
}
