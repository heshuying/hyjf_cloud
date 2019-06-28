package com.hyjf.data.service.sensors;

import java.util.Map;

/**
 * @Auther:yangchangwei
 * @Date:2019/6/25
 * @Description: 神策获取信息接口类
 */
public interface SenSorsService {

    /**
     * 根据参数获取接口返回数据
     * @param parm
     * @param url
     * @return
     */
    Map getSenSorsData(Map parm,String url);

    /**
     * 更新当月用户转化（近30天，包扣当天）
     * @return
     */
    boolean updateMonthUserConvert();

    /**
     * 更新用户行为（近30天，包扣当天）
     */
    void updateMonthUserBehavior();

    /**
     * 更新当月交易规模（包含当天）
     */
    void updateMonthTransTotal();

    /**
     * 更新当月注册用户数量（包含当天）
     */
    void updateMonthRegisterNumber();

    /**
     * 更新当月总出借数（包含当天）
     */
    void updateMonthTenderSumNumber();

    /**
     * 更新当月散标出借笔数（含当天）
     */
    void updateMonthTenderNumber();

    /**
     * 更新当月智投出借笔数（含当天）
     */
    void updateMonthHjhTenderNumber();

    /**
     * 更新当月承接笔数（含当天）
     */
    void updateMonthCreditTenderNumber();

    /**
     * 更新6个月的出借金额分布（含当天）
     */
    void updateSixMonthTenderAmountDistributed();

    /**
     * 更新6个月的用户出借次数（含当天）
     */
    void updateSixMonthTenderNumberDistributed();

}
