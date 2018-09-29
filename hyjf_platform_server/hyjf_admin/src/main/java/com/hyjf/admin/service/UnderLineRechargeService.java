package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.UnderLineRechargeRequestBean;
import com.hyjf.am.response.admin.UnderLineRechargeResponse;

/**
 * 线下充值类型配置
 * @Author : huanghui
 */
public interface UnderLineRechargeService {

    /**
     * 获取充值类型列表
     * @param requestBean
     * @return
     */
    UnderLineRechargeResponse selectUnderLineList(UnderLineRechargeRequestBean requestBean);

    /**
     * 插入
     * @param requestBean
     * @return
     */
    UnderLineRechargeResponse insertRecord(UnderLineRechargeRequestBean requestBean);

    /**
     * 获取当前 code 数据
     * @param code
     * @return
     */
    boolean checkValidate(String code);

    /**
     * 更新数据
     * @param requestBean
     * @return
     */
    boolean updateUnderLineRecharge(UnderLineRechargeRequestBean requestBean);

    /**
     * 删除指定数据
     * @param id
     * @return
     */
    boolean deleteUnderLineRecharge(Integer id);
}
