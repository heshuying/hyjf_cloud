/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.hgreportdata.nifa.impl;

import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.*;
import com.hyjf.am.trade.service.hgreportdata.nifa.NifaFileDealService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.NifaReportLogVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version NifaFileDealServiceImpl, v0.1 2018/9/4 16:53
 */
@Service
public class NifaFileDealServiceImpl extends BaseServiceImpl implements NifaFileDealService {

    /**
     * 获取未成功下载日志的数据
     *
     * @return
     */
    @Override
    public List<NifaReportLog> selectNifaReportLogDownloadPath() {
        NifaReportLogExample example = new NifaReportLogExample();
        example.createCriteria().andFeedbackResultNotEqualTo(1).andUploadTimeLessThan(GetDate.getDayStart10(new Date()));
        List<NifaReportLog> nifaReportLogList = this.nifaReportLogMapper.selectByExample(example);
        if (null != nifaReportLogList && nifaReportLogList.size() > 0) {
            return nifaReportLogList;
        }
        return null;
    }

    /**
     * 获取居间服务协议模板上传信息
     *
     * @param templetId
     * @return
     */
    @Override
    public FddTemplet selectFddTemplet(String templetId) {
        FddTempletExample example = new FddTempletExample();
        example.createCriteria().andTempletIdEqualTo(templetId);
        List<FddTemplet> fddTempletList = this.fddTempletMapper.selectByExample(example);
        if (null != fddTempletList && fddTempletList.size() > 0) {
            return fddTempletList.get(0);
        }
        return null;
    }

    /**
     * 获取未成功下载日志的数据
     *
     * @return
     */
    @Override
    public List<NifaReportLog> selectNifaReportLogList() {
        NifaReportLogExample example = new NifaReportLogExample();
        NifaReportLogExample.Criteria cra = example.createCriteria();
        // 获取状态不是成功的数据
        cra.andFileUploadStatusNotEqualTo(1);
        List<NifaReportLog> nifaReportLogList = this.nifaReportLogMapper.selectByExample(example);
        if (null != nifaReportLogList && nifaReportLogList.size() > 0) {
            return nifaReportLogList;
        }
        return null;
    }

    /**
     * 判断文件是否生成过
     *
     * @return
     */
    @Override
    public List<NifaReportLog> selectNifaReportLogByFileName(String fileName) {
        NifaReportLogExample example = new NifaReportLogExample();
//        NifaReportLogExample.Criteria cra = example.createCriteria();
        // 获取状态不是成功的数据
        example.createCriteria().andUploadNameEqualTo(fileName);
        List<NifaReportLog> nifaReportLogList = this.nifaReportLogMapper.selectByExample(example);
        if (null != nifaReportLogList && nifaReportLogList.size() > 0) {
            return nifaReportLogList;
        }
        return null;
    }

    /**
     * 更新处理结果
     *
     * @param nifaReportLogVO
     * @return
     */
    @Override
    public boolean updateNifaReportLog(NifaReportLogVO nifaReportLogVO) {
        NifaReportLog nifaReportLog = CommonUtils.convertBean(nifaReportLogVO, NifaReportLog.class);
        boolean result = this.nifaReportLogMapper.updateByPrimaryKeySelective(nifaReportLog) > 0 ? true : false;
        if (!result) {
            logger.error("【互金上传文件】更新上传日志表失败！Id:" + nifaReportLog.getId());
        }
        return result;
    }

    /**
     * 获取当天数据已处理次数生成文件名
     *
     * @param beforDate
     * @param fileType
     * @return
     */
    @Override
    public List<NifaReportLog> selectMaxFileName(String beforDate, String fileType) {
        NifaReportLogExample example = new NifaReportLogExample();
        example.createCriteria().andHistoryDateEqualTo(beforDate).andPackageInformationEqualTo(fileType);
        example.setOrderByClause("upload_name desc");
        List<NifaReportLog> nifaReportLogList = this.nifaReportLogMapper.selectByExample(example);
        if (null != nifaReportLogList && nifaReportLogList.size() > 0) {
            return nifaReportLogList;
        }
        return null;
    }

    /**
     * 获取最新的合同模板
     *
     * @return
     */
    @Override
    public List<NifaContractTemplateCustomize> selectNifaContractTemplate() {
        List<NifaContractTemplateCustomize> nifaContractTemplate = nifaContractTemplateCustomizeMapper.selectNifaContractTemplate();
        if (null != nifaContractTemplate && nifaContractTemplate.size() > 0) {
            return nifaContractTemplate;
        }
        return null;
    }

    /**
     * 插入上送记录
     *
     * @param nifaReportLog
     * @return
     */
    @Override
    public boolean insertNifaReportLog(NifaReportLog nifaReportLog) {
        return nifaReportLogMapper.insertSelective(nifaReportLog) > 0 ? true : false;
    }

    /**
     * 查询未上送的合同要素数据
     *
     * @return
     */
    @Override
    public List<NifaContractEssenceCustomize> selectNifaContractEssenceList() {
        List<NifaContractEssenceCustomize> list = nifaContractEssenceCustomizeMapper.selectNifaContractEssenceList();
        if (null != list && list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public List<NifaRepayInfoCustomize> selectNifaRepayInfoList() {
        List<NifaRepayInfoCustomize> list = nifaRepayInfoCustomizeMapper.selectNifaRepayInfoList();
        if (null != list && list.size() > 0) {
            return list;
        }
        return null;
    }


    @Override
    public List<NifaContractStatusCustomize> selectNifaContractStatus() {
        List<NifaContractStatusCustomize> list = nifaContractStatusCustomizeMapper.selectNifaContractStatus();
        if (null != list && list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public List<NifaReceivedPaymentsCustomize> selectNifaReceivedPaymentsList() {
        List<NifaReceivedPaymentsCustomize> list = nifaReceivedPaymentsCustomizeMapper.selectNifaReceivedPaymentsList();
        if (null != list && list.size() > 0) {
            return list;
        }
        return null;
    }

    /**
     * 查询该天放款成功的数据
     *
     * @param historyData
     * @return
     */
    @Override
    public List<BorrowApicron> selectBorrowApicron(String historyData) {
        // 当天开始时间
        Date startTime = GetDate.stringToDate(historyData.concat(" 00:00:00"));
        // 当天结束时间
        Date endTime = GetDate.stringToDate(historyData.concat(" 23:59:59"));

        BorrowApicronExample example = new BorrowApicronExample();
        example.createCriteria().andApiTypeEqualTo(0).andStatusEqualTo(6).andUpdateTimeGreaterThanOrEqualTo(startTime).andUpdateTimeLessThanOrEqualTo(endTime);
        List<BorrowApicron> list = borrowApicronMapper.selectByExample(example);
        if (null != list && list.size() > 0) {
            return list;
        }
        return null;
    }

    /**
     * 查询该天还款成功的数据
     *
     * @param historyData
     * @return
     */
    @Override
    public List<BorrowRepay> selectBorrowRepayByHistoryData(String historyData) {
        // 当天开始时间
        Integer startTime = GetDate.dateString2Timestamp(historyData.concat(" 00:00:00"));
        // 当天结束时间
        Integer endTime = GetDate.dateString2Timestamp(historyData.concat(" 23:59:59"));

        BorrowRepayExample example = new BorrowRepayExample();
        example.createCriteria().andRepayTypeEqualTo("wait_yes").andRepayYestimeGreaterThanOrEqualTo(startTime).andRepayYestimeLessThanOrEqualTo(endTime);
        List<BorrowRepay> list = borrowRepayMapper.selectByExample(example);
        if (null != list && list.size() > 0) {
            return list;
        }
        return null;
    }

    /**
     * 查询该天分期还款成功的数据
     *
     * @param historyData
     * @return
     */
    @Override
    public List<BorrowRepayPlan> selectBorrowRepayPlanByHistoryData(String historyData) {
        // 当天开始时间
        Integer startTime = GetDate.dateString2Timestamp(historyData.concat(" 00:00:00"));
        // 当天结束时间
        Integer endTime = GetDate.dateString2Timestamp(historyData.concat(" 23:59:59"));

        BorrowRepayPlanExample example = new BorrowRepayPlanExample();
        example.createCriteria().andRepayTypeEqualTo("wait_yes").andRepayYestimeGreaterThanOrEqualTo(startTime).andRepayYestimeLessThanOrEqualTo(endTime);
        List<BorrowRepayPlan> list = borrowRepayPlanMapper.selectByExample(example);
        if (null != list && list.size() > 0) {
            return list;
        }
        return null;
    }

    /**
     * 查询该天放款成功的数据
     *
     * @param historyData
     * @return
     */
    @Override
    public List<Borrow> selectBorrowByHistoryDate(String historyData) {
        // 当天开始时间
        Integer startTime = GetDate.dateString2Timestamp(historyData.concat(" 00:00:00"));
        // 当天结束时间
        Integer endTime = GetDate.dateString2Timestamp(historyData.concat(" 23:59:59"));

        BorrowExample example = new BorrowExample();
        example.createCriteria().andRecoverLastTimeIsNotNull().andRecoverLastTimeGreaterThanOrEqualTo(startTime).andRecoverLastTimeLessThanOrEqualTo(endTime);
        List<Borrow> list = borrowMapper.selectByExample(example);
        if (null != list && list.size() > 0) {
            return list;
        }
        return null;
    }
}
