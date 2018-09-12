/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.vo.DropDownVO;

import java.util.List;

/**
 * @author wangjun
 * @version AdminCommonService, v0.1 2018/7/3 10:25
 */
public interface AdminCommonService {
    /**
     * 获取相应paramname数据
     *
     * @param param
     * @return
     */
    List<DropDownVO> getParamNameList(String param);

    /**
     * 还款方式下拉列表
     *
     * @return
     */
    List<DropDownVO> selectBorrowStyleList();

    /**
     * 资产来源下拉列表
     *
     * @return
     */
    List<DropDownVO> selectHjhInstConfigList();

    /**
     * 项目类型下拉列表
     * @auth sunpeikai
     * @param
     * @return
     */
    List<DropDownVO> selectProjectType();
}
