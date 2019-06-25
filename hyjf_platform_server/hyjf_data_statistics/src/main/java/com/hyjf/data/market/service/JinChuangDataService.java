package com.hyjf.data.market.service;

import com.hyjf.am.vo.admin.JcCustomerServiceVO;
import com.hyjf.data.bean.jinchuang.JcRegisterTrade;
import com.hyjf.data.bean.jinchuang.JcTradeAmount;
import com.hyjf.data.bean.jinchuang.JcUserConversion;
import com.hyjf.data.vo.jinchuang.JcDataStatisticsVO;

import java.util.List;
import java.util.Map;

/**
 * @Auther:yangchangwei
 * @Date:2019/6/18
 * @Description: 金创获取数据
 */
public interface JinChuangDataService {

    /**
     * 获取用户转化数据
     */
    JcUserConversion getUserConversion();

    /**
     * 获取用户数据分析
     * @return
     */
    List<JcDataStatisticsVO> getDataStatistics();

    /**
     * 获取用户行为
     * @return
     */
    String getUserPoint();

    /**
     * 获取用户出借数据
     * @return
     */
    Map<String, Object> getUserAnalysis();

    /**
     * 获取累计交易金额
     * @return
     */
    JcTradeAmount getTradeAmount();

    /**
     * 获取注册交易
     * @return
     */
    List<JcRegisterTrade> getRegisterTrade();

    /**
     * 获取客户服务
     * @return
     */
    JcCustomerServiceVO getCustomerService();
}
