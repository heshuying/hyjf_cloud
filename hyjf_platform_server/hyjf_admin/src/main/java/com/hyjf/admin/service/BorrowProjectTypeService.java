package com.hyjf.admin.service;

import com.hyjf.am.response.trade.BorrowProjectTypeResponse;
import com.hyjf.am.resquest.trade.BorrowProjectTypeRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectRepayVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/27.
 */
public interface BorrowProjectTypeService {
    /**
     * 查询列表
     * @param adminRequest
     * @return
     */
    BorrowProjectTypeResponse selectProjectTypeList(BorrowProjectTypeRequest adminRequest);

    /**
     * 查询项目类型 详情
     * @param adminRequest
     * @return
     */
    BorrowProjectTypeResponse selectProjectTypeRecord(BorrowProjectTypeRequest adminRequest);
    /**
     * 根据主键判断汇直投项目类型维护中数据是否存在
     * @return
     */
    public boolean isExistsRecord(BorrowProjectTypeVO record);

    /**
     * 获取单个汇直投项目类型维护
     * @return
     */
    public BorrowProjectTypeVO getRecord(BorrowProjectTypeVO record);

    /**
     * 根据项目编号查询还款方式
     * @param str
     */
    public List<BorrowProjectRepayVO> selectRepay(String str);
    /**
     * 查询类型表
     */
    public List<BorrowStyleVO> selectStyles();
    /**
     * 获取数据字典表的下拉列表
     *
     * @return
     */
    public List<ParamNameVO> getParamNameList(String nameClass);
    /**
     * 汇直投项目类型维护插入
     *
     * @param record
     */
    public BorrowProjectTypeResponse insertRecord(BorrowProjectTypeRequest record);
    /**
     * 汇直投项目类型维护修改
     *
     * @param record
     */
    public void updateRecord(BorrowProjectTypeRequest record);
    /**
     *  汇直投项目类型维护删除
     * @param adminRequest
     */
    public BorrowProjectTypeResponse deleteProjectType( BorrowProjectTypeRequest adminRequest);

    /**
     * 检查项目名称唯一性
     * @param borrowCd
     * @return
     */
    public int borrowCdIsExists(BorrowProjectTypeRequest borrowCd);
}
