package com.hyjf.am.trade.service.admin.underlinerecharge;

import com.hyjf.am.response.admin.UnderLineRechargeResponse;
import com.hyjf.am.resquest.admin.UnderLineRechargeRequest;
import com.hyjf.am.trade.dao.model.auto.UnderLineRecharge;
import com.hyjf.am.vo.admin.UnderLineRechargeVO;

import java.util.List;

/**
 * 线下充值类型配置
 * @Author : huanghui
 */
public interface UnderLineRechargeService {


    /**
     * 充值类型类表
     * @param request
     * @return
     */
    List<UnderLineRecharge> selectUnderLineList(UnderLineRechargeRequest request);

    /**
     * 充值类型条数
     * @param request
     * @return
     */
    Integer getUnderLineRechaegeCount(UnderLineRechargeRequest request);

    /**
     * 写入数据库
     * @param request
     * @return
     */
    int insterUnderRechargeCode(UnderLineRechargeRequest request);

    /**
     * 获取 充值类型列表.
     * @param request
     * @return
     */
    List<UnderLineRecharge> getUnderLineRechargeList(UnderLineRechargeRequest request);

    /**
     * 获取指定类型的数据
     * @param request
     * @return
     */
    List<UnderLineRecharge> getUnderLineRechargeListByCode(UnderLineRechargeRequest request);

    /**
     * 更新数据
     * @param request
     * @return
     */
    Integer updateAction(UnderLineRechargeRequest request);

    /**
     * 删除指定数据
     * @param id
     * @return
     */
    Integer deleteById(Integer id);
}
