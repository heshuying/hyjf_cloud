/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.market.dao.mapper.auto.SellDailyMapper;
import com.hyjf.am.market.dao.mapper.customize.market.SellDailyCustomizeMapper;
import com.hyjf.am.market.dao.model.auto.SellDaily;
import com.hyjf.am.market.dao.model.auto.SellDailyExample;
import com.hyjf.am.market.service.SellDailyService;
import com.hyjf.am.vo.market.SellDailyVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;

/**
 * @author yaoyong
 * @version SellDailyServiceImpl, v0.1 2018/11/16 17:54
 */
@Service
public class SellDailyServiceImpl implements SellDailyService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private SellDailyMapper sellDailyMapper;
    @Resource
    private SellDailyCustomizeMapper sellDailyCustomizeMapper;

    private static final String NMZX_DIVISION_NAME = "纳觅咨询";
    private static final String QGR_DIVISION_NAME = "裕峰瑞";
    private static final String DTHJ_DIVISION_NAME = "大唐汇金";
    private static final String SHRJ_DIVISION_NAME = "上海嵘具";
    private static final String YYZX_DIVISION_NAME = "运营中心";
    private static final String HZSW_DIVISION_NAME = "惠众";

    private static final int DRAW_ORDER_LEVEL1 = 1;
    private static final int DRAW_ORDER_LEVEL2 = 2;
    private static final int DRAW_ORDER_LEVEL3 = 3;
    private static final int DRAW_ORDER_LEVEL4 = 4;

    /** 查询所有分部 */
    private static final Integer QUERY_ALL_DIVISION_TYPE = 1;
    /** 上海运营中心-网络运营部 id:327 */
    private static final Integer QUERY_OC_THREE_DIVISION_TYPE = 2;
    /** 查询APP推广 */
    private static final Integer QUERY_APP_TYPE = 3;
    /** 不需要显示的网点 */
    private static final List NMZX_IGNORE_TWODIVISION_LIST = Arrays.asList("胶州分部");
    private static final List DTHJ_IGNORE_TWODIVISION_LIST = Arrays.asList("樟树分部", "东莞分部", "西安分部");

    @Override
    public List<SellDaily> selectDailyByDateStr(String dateStr) {
        SellDailyExample example = new SellDailyExample();
        SellDailyExample.Criteria criteria = example.createCriteria();
        criteria.andDateStrEqualTo(dateStr);
        example.setOrderByClause(" id asc ");
        List<SellDaily> list = sellDailyMapper.selectByExample(example);
        return list;
    }

    @Override
    public SellDaily selectOCSum(String formatDateStr) {
        return sellDailyCustomizeMapper.selectOCSum(formatDateStr);
    }

    @Override
    public SellDaily selectPrimaryDivisionSum(String dateStr, int drawOrder) {
        Map<String, Object> map = new HashMap<>();
        map.put("dateStr", dateStr);
        map.put("drawOrder", drawOrder);
        return sellDailyCustomizeMapper.selectPrimaryDivisionSum(map);
    }

    @Override
    public SellDaily selectAllSum(String dateStr) {
        return sellDailyCustomizeMapper.selectAllSum(dateStr);
    }

    @Override
    public boolean hasGeneratorDataToday() {
        SellDailyExample example = new SellDailyExample();
        SellDailyExample.Criteria criteria = example.createCriteria();
        criteria.andDateStrEqualTo(GetDate.getFormatDateStr());
        List<SellDaily> list = sellDailyMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        return true;
    }

    @Override
    public void batchInsertSellDaily(List<SellDailyVO> voList) {
        List<SellDaily> currentMonthTotalTenderList = CommonUtils.convertBeanList(voList, SellDaily.class);
        sellDailyCustomizeMapper.batchInsertSellDaily(currentMonthTotalTenderList);
    }

    @Override
    public void calculateRate(String dateStr) {
        sellDailyCustomizeMapper.calculateRate(dateStr);
    }

}
