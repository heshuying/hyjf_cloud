/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.common.service;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.util.CustomConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 组合层共用ServiceImpl基类
 * @author liubin
 * @version BaseController, v0.1 2018/6/15 19:18
 */
public class BaseServiceImpl implements BaseService{


    @Autowired
    private AmConfigClient amConfigClient;

    /**
     * 根据hyjf_param_name 的nameCd和nameClass获得param描述
     * @param nameCd
     * @param params
     * @return
     */
    @Override
    public String getParamName(String nameCd, List<ParamNameVO> params) {

        if(params != null && params.size() > 0){
            for (ParamNameVO vo : params
                 ) {
                if(nameCd.equals(vo.getNameCd())){
                    return vo.getName();
                }
            }
        }
        return null;
    }

    /**
     * 根据nameClass 获得hyjf_param_name 的列表
     * @param nameClass
     * @return
     */
    @Override
    public List<ParamNameVO> getParamNameList(String nameClass) {
        List<ParamNameVO> paramNameList = amConfigClient.getParamNameList(nameClass);
        return paramNameList;
    }
}
