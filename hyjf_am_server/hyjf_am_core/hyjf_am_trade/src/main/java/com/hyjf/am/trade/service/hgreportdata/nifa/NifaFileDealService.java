/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.hgreportdata.nifa;

import com.hyjf.am.trade.dao.model.auto.FddTemplet;
import com.hyjf.am.trade.dao.model.auto.NifaReportLog;
import com.hyjf.am.trade.dao.model.customize.*;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.admin.NifaReportLogVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version NifaFileDealService, v0.1 2018/9/4 16:53
 */
public interface NifaFileDealService extends BaseService {

    /**
     * 获取未成功下载日志的数据
     *
     * @return
     */
    List<NifaReportLog> selectNifaReportLogDownloadPath();

    /**
     * 获取居间服务协议模板上传信息
     *
     * @param templetId
     * @return
     */
    FddTemplet selectFddTemplet(String templetId);

    /**
     * 获取未成功下载日志的数据
     *
     * @return
     */
    List<NifaReportLog> selectNifaReportLogList();

    /**
     * 判断文件是否生成过
     *
     * @return
     */
    List<NifaReportLog> selectNifaReportLogByFileName(String fileName);

    /**
     * 更新处理结果
     *
     * @param nifaReportLogVO
     * @return
     */
    boolean updateNifaReportLog(NifaReportLogVO nifaReportLogVO);

    /**
     * 获取当天数据已处理次数生成文件名
     *
     * @param beforDate
     * @param fileType
     * @return
     */
    List<NifaReportLog> selectMaxFileName(String beforDate, String fileType);

    /**
     * 获取最新的合同模板
     *
     * @return
     */
    List<NifaContractTemplateCustomize> selectNifaContractTemplate();

    /**
     * 插入上送记录
     *
     * @param nifaReportLog
     * @return
     */
    boolean insertNifaReportLog(NifaReportLog nifaReportLog);

    /**
     * 查询未上送的合同要素数据
     *
     * @return
     */
    List<NifaContractEssenceCustomize> selectNifaContractEssenceList();

    List<NifaRepayInfoCustomize> selectNifaRepayInfoList();

    List<NifaContractStatusCustomize> selectNifaContractStatus();

    List<NifaReceivedPaymentsCustomize> selectNifaReceivedPaymentsList();
}
