package com.hyjf.admin.service;

import com.hyjf.am.response.admin.BorrowSendTypeResponse;
import com.hyjf.am.resquest.admin.BorrowSendTypeRequest;
import com.hyjf.am.vo.admin.BorrowSendTypeVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;

/**
 * @author by xiehuili on 2018/8/1.
 */
public interface SendTypeService {
    /**
     * 查询流程配置中的发标/复审
     * @param adminRequest
     * @return
     */
    public BorrowSendTypeResponse selectBorrowSendList(BorrowSendTypeRequest adminRequest);
    /**
     * 查询流程配置中的发标/复审页面
     * @param sendCd
     * @return
     */
    public BorrowSendTypeVO getBorrowSendInfo(String sendCd);

    /**
     * 获取数据字典表的下拉列表
     * @param code
     * @return
     */
    public List<ParamNameVO> getParamNameList(String code);

    /**
     * 数据插入
     * @param adminRequest
     */
    public BorrowSendTypeResponse insertBorrowSend(BorrowSendTypeRequest adminRequest);

    /**
     * 数据修改
     * @param adminRequest
     */
    public BorrowSendTypeResponse updateBorrowSend(BorrowSendTypeRequest adminRequest);
    /**
     * 删除
     * @param sendCd
     */
    public BorrowSendTypeResponse daleteBorrowSend(String sendCd);
}
