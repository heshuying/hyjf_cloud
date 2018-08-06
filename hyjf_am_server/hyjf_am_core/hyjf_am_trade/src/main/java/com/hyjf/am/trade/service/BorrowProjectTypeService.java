package com.hyjf.am.trade.service;

import com.hyjf.am.response.trade.BorrowProjectTypeResponse;
import com.hyjf.am.resquest.trade.BorrowProjectTypeRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectType;
import com.hyjf.am.vo.trade.borrow.BorrowProjectRepayVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;

import java.util.List;

/**
 *
 * @author zhangyk
 * @date 2018/6/25 11:24
 */
public interface BorrowProjectTypeService {


    public BorrowProjectType getProjectTypeByBorrowNid(String borrowNid) ;

    /*
    * 分页查询分项目类型
    * */
    public List<BorrowProjectTypeVO>  selectProjectTypeList(BorrowProjectTypeVO borrowProjectTypeVO);

    /*
    * 根据id查询项目类型
    * */
    public BorrowProjectType selectProjectTypeRecord(Integer id);

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
     * 汇直投项目类型维护插入
     *
     * @param record
     */
    public BorrowProjectTypeResponse insertRecord(BorrowProjectTypeRequest record);
    /**
     * 汇直投项目类型维护插入
     *
     * @param record
     */
    public  BorrowProjectTypeResponse updateRecord(BorrowProjectTypeRequest record);
    /**
     * 汇直投项目类型维护删除
     * @param borrowCd
     */
    public void deleteProjectType(String borrowCd);
    /**
     * 汇直投项目类型维护删除
     * @param borrowCd
     */
    public void deleteAsset(Integer borrowCd);
    /**
     * 检查项目名称唯一性
     * @param borrowCd
     * @return
     */
    public int borrowCdIsExists( String borrowCd);

}
