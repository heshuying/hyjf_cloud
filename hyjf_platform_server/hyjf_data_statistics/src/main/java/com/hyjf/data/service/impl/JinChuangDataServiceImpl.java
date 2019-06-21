package com.hyjf.data.service.impl;

import com.hyjf.data.bean.jinchuang.JcRegisterTrade;
import com.hyjf.data.bean.jinchuang.JcTradeAmount;
import com.hyjf.data.service.JinChuangDataService;
import com.hyjf.data.vo.jinchuang.JcCustomerServiceVO;
import com.hyjf.data.vo.jinchuang.JcDataStatisticsVO;
import com.hyjf.data.vo.jinchuang.JcUserConversionVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther:yangchangwei
 * @Date:2019/6/18
 * @Description:
 */
@Service
public class JinChuangDataServiceImpl implements JinChuangDataService {
    @Override
    public JcUserConversionVO getUserConversion() {
        return null;
    }

    @Override
    public List<JcDataStatisticsVO> getDataStatistics() {
        return null;
    }

    @Override
    public String getUserPoint() {
        return null;
    }

    @Override
    public Map<String, Object> getUserAnalysis() {
        return null;
    }

    @Override
    public JcTradeAmount getTradeAmount() {
        return null;
    }

    @Override
    public List<JcRegisterTrade> getRegisterTrade() {
        return null;
    }

    @Override
    public JcCustomerServiceVO getCustomerService() {
        return null;
    }
}
