package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.ParamName;
import com.hyjf.am.config.dao.model.auto.Submissions;
import com.hyjf.am.config.dao.model.customize.SubmissionsWithBLOBs;
import com.hyjf.am.resquest.config.SubmissionsRequest;
import com.hyjf.am.vo.config.SubmissionsCustomizeVO;

import java.util.List;

/**
 * @author lisheng
 * @version SubmissionsService, v0.1 2018/7/13 16:20
 */

public interface SubmissionsService {

    /**
     * 获取数据字典表的下拉列表
     *
     * @return
     */
    public List<ParamName> getParamNameList(String nameClass);

    /**
     * 根据查询条件 取得数据
     * @return
     */
    List<SubmissionsCustomizeVO> queryRecordList(SubmissionsRequest form,int limitStart, int limitEnd);


    int queryTotal(SubmissionsRequest form);


    /**
     * 更新意见反馈
     * @param submissions
     */
    boolean updateSubmissions(SubmissionsWithBLOBs submissions);

    /**
     * 添加意见反馈
     * @return
     */
     int addSubmission(Submissions submissions);


    /**
     * 查询版本管理信息
     * @param id
     * @return
     */
    Submissions queryRecordById(Integer id);
}
