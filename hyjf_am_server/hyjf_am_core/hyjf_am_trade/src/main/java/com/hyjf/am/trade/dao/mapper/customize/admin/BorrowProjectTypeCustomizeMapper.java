package com.hyjf.am.trade.dao.mapper.customize.admin;

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
    public List<BorrowProjectTypeVO>  selectProjectTypeList(BorrowProjectTypeVO borrowProjectType);

}
