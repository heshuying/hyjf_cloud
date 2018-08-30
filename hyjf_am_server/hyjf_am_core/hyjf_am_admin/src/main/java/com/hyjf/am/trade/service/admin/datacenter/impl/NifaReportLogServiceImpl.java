/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.datacenter.impl;

import com.hyjf.am.resquest.admin.NifaReportLogRequest;
import com.hyjf.am.trade.dao.mapper.auto.NifaReportLogMapper;
import com.hyjf.am.trade.dao.model.auto.NifaReportLog;
import com.hyjf.am.trade.dao.model.auto.NifaReportLogExample;
import com.hyjf.am.trade.service.admin.datacenter.NifaReportLogService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author nxl
 * @version NifaReportLogServiceImpl, v0.1 2018/8/17 9:57
 */
@Service
public class NifaReportLogServiceImpl extends BaseServiceImpl implements NifaReportLogService {

    @Autowired
    private NifaReportLogMapper nifaReportLogMapper;

    /**
     * 查找互金协会报送日志列表
     * @param request
     * @param limtStart
     * @param limtEnd
     * @return
     */
    @Override
    public List<NifaReportLog> selectNifaReportLogList(NifaReportLogRequest request,int limtStart,int limtEnd){
        NifaReportLogExample example = new NifaReportLogExample();
        NifaReportLogExample.Criteria creteria = example.createCriteria();
        SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateStart = null;
        Date dateEnd = null;

        if (null != request) {
            try {
                if (StringUtils.isNotEmpty(request.getCreateTimeStart())) {
                    dateStart = smp.parse(request.getCreateTimeStart()+" 00:00:00");
                    creteria.andCreateTimeGreaterThanOrEqualTo(dateStart);
                }
                if (StringUtils.isNotEmpty(request.getCreateTimeEnd())) {
                    dateEnd = smp.parse(request.getCreateTimeEnd()+" 23:59:59");
                    creteria.andCreateTimeLessThanOrEqualTo(dateEnd);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (null!=request.getFileUploadStatus()) {
                creteria.andFileUploadStatusEqualTo(request.getFileUploadStatus());
            }
        }
        if (limtStart != -1) {
            example.setLimitStart(limtStart);
            example.setLimitEnd(limtEnd);
        }
        List<NifaReportLog> nifaReportLogList = nifaReportLogMapper.selectByExample(example);
        return nifaReportLogList;
    }
    /**
     * 根据筛选条件查找互金协会报送日志总数
     * @param request
     * @return
     */
    @Override
    public int countNifaReportLog(NifaReportLogRequest request){
        NifaReportLogExample example = new NifaReportLogExample();
        NifaReportLogExample.Criteria creteria = example.createCriteria();
        SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateStart = null;
        Date dateEnd = null;

        if (null != request) {
            try {
                if (StringUtils.isNotEmpty(request.getCreateTimeStart())) {
                    dateStart = smp.parse(request.getCreateTimeStart()+" 00:00:00");
                    creteria.andCreateTimeGreaterThanOrEqualTo(dateStart);
                }
                if (StringUtils.isNotEmpty(request.getCreateTimeEnd())) {
                    dateEnd = smp.parse(request.getCreateTimeEnd()+" 23:59:59");
                    creteria.andCreateTimeLessThanOrEqualTo(dateEnd);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (null!=request.getFileUploadStatus()) {
                creteria.andFileUploadStatusEqualTo(request.getFileUploadStatus());
            }
        }
        int intCount = nifaReportLogMapper.countByExample(example);
        return intCount;
    }

    /**
     * 根据id查找数据
     * @param logId
     * @return
     */
    @Override
    public NifaReportLog selectNifaReportLogById(int logId){
        NifaReportLog nifaReportLog = nifaReportLogMapper.selectByPrimaryKey(logId);
        return  nifaReportLog;
    }
}
