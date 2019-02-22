package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/30.
 */
public interface BorrowProjectTypeCustomizeMapper {

    /**
     * 分页查询分项目类型
     * @param borrowProjectType
     * @return
     */
    public Integer  selectProjectTypeCount(BorrowProjectTypeVO borrowProjectType);
    /**
     * 分页查询分项目类型
     * @param borrowProjectType
     * @return
     */
    public List<BorrowProjectTypeVO>  selectProjectTypeList(BorrowProjectTypeVO borrowProjectType);
    /**
     * 根据borrowCd查询
     * @param record
     * @return
     */
    BorrowProjectTypeVO selectByBorrowCd(BorrowProjectTypeVO record);

    /**
     * 查询分项目类型后分组
     * @param borrowProjectType
     * @return
     */
    public List<BorrowProjectTypeVO>  selectProjectTypeListGroupBy(BorrowProjectTypeVO borrowProjectType);

}
