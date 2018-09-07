package com.hyjf.am.config.service;

import java.util.List;

import com.hyjf.am.config.dao.model.auto.ParamName;
import com.hyjf.am.resquest.admin.AdminParamNameRequest;
import com.hyjf.am.vo.config.ParamNameVO;

/**
 * @author by xiehuili on 2018/7/13.
 */
public interface ParamNameService {

    /**
     * 子账户类型 查询
     * @return
     */
    public List<ParamName> getParamNameList(String code);
    public List<ParamName> getNameCd(String code);
    /**
     *（条件）列表查询--其他相关字段
     * @return
     */
    public List<ParamNameVO>  selectProjectTypeParamList();

    /**
     * 查询数据字典数量
     * @auth sunpeikai
     * @param
     * @return
     */
    int getParamNamesCount(AdminParamNameRequest request);

    /**
     * 查询数据字典列表
     * @auth sunpeikai
     * @param
     * @return
     */
    List<ParamName> searchParamNamesList(AdminParamNameRequest request);

    /**
     * 检查数据库是否已存在该数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    boolean isExistsParamName(ParamNameVO paramNameVO);

    /**
     * 插入数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    int insertParamName(ParamNameVO paramNameVO);

    /**
     * 修改数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    int updateParamName(ParamNameVO paramNameVO);

    /**
     * 根据联合主键查询数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    ParamName searchParamNameByKey(ParamNameVO paramNameVO);

    /**
     * 删除数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    int deleteParamName(ParamNameVO paramNameVO);
}
