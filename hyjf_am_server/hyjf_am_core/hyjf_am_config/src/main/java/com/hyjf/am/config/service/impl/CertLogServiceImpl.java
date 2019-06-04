/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.CertLogMapper;
import com.hyjf.am.config.dao.model.auto.CertLog;
import com.hyjf.am.config.dao.model.auto.CertLogExample;
import com.hyjf.am.config.service.CertLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 合规数据上报 CERT 应急中心上报记录
 */
@Service
public class CertLogServiceImpl implements CertLogService {
    @Autowired
    protected CertLogMapper certLogMapper;

    /**
     * 根据id查找上报记录
     *
     * @param certLogId
     * @return
     */
    @Override
    public CertLog selectCertLogById(int certLogId) {
        //
        CertLog certLog = certLogMapper.selectByPrimaryKey(certLogId);
        return certLog;
    }

    /**
     * 查找上报记录
     *
     * @return
     */
    @Override
    public List<CertLog> selectCertLog() {
        CertLogExample example = new CertLogExample();
        CertLogExample.Criteria creteria = example.createCriteria();
        //查询结果 0初始  1成功  8批次号无效 9入库失败  2等待处理  99 无响应
        // 查询上报结果成功
        creteria.andSendStatusEqualTo(1);
        // 查询结果为初始
        List<Integer> resultIn = new ArrayList<>();
        // 初始  等待处理   无响应
        resultIn.add(0);
        /*resultIn.add(2);
        resultIn.add(99);*/
        //入库失败
        resultIn.add(9);
        creteria.andQueryResultIn(resultIn);
        //一次查找 100 条数据
        example.setLimitStart(0);
        example.setLimitEnd(100);

        example.setOrderByClause(" id asc");
        List<CertLog> certLogList = certLogMapper.selectByExample(example);
        if (null != certLogList && certLogList.size() > 0) {
            return certLogList;
        }
        return null;
    }

    /**
     * 更新发送日志
     * @param certLog
     * @return
     */
    @Override
    public int updateCertLog(CertLog certLog) {
        return certLogMapper.updateByPrimaryKeySelective(certLog);
    }


    /**
     * 查询待异步查询的日志数量
     * @return
     */
    @Override
    public int selectCertLogLength(){
        CertLogExample example = new CertLogExample();
        CertLogExample.Criteria creteria = example.createCriteria();
        // 查询上报结果成功
        creteria.andSendStatusEqualTo(1);
        // 查询结果为初始
        creteria.andQueryResultEqualTo(0);
        int intCount = certLogMapper.countByExample(example);
        return intCount;
    }
}
