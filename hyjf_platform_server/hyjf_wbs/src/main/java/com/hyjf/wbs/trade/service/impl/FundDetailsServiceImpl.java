/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.trade.service.impl;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.hyjf.wbs.common.EntUtmIds;
import com.hyjf.wbs.exceptions.WbsFundDetailsException;
import com.hyjf.wbs.qvo.FundDetailsQO;
import com.hyjf.wbs.qvo.FundDetailsVO;
import com.hyjf.wbs.trade.dao.mapper.customize.FundDetailsCustomizeMapper;
import com.hyjf.wbs.trade.dao.model.customize.FundDetailsCustomize;
import com.hyjf.wbs.trade.service.FundDetailsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * @author cui
 * @version FundDetailsServiceImpl, v0.1 2019/7/1 10:29
 */
@Service
public class FundDetailsServiceImpl implements FundDetailsService {

	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	// 1.充值 2.提现
	private static final Integer[] ARRAY_BUSINESSTYPE={1,2};

	private final Integer ASSET_ID_HYJF=1;

	@Autowired
    private FundDetailsCustomizeMapper detailsCustomizeMapper;

	@Override
	public List<FundDetailsVO> queryFundDetails(FundDetailsQO fundDetailsQO) {

	    validateParameters(fundDetailsQO);

        FundDetailsCustomize fundDetailsCustomize=buildCustomize(fundDetailsQO);

        List<FundDetailsVO> lstDetailsVO= Lists.newArrayList();

        if (fundDetailsQO.getBusinessType().equals(ARRAY_BUSINESSTYPE[0])){
		    //充值
            lstDetailsVO.addAll(detailsCustomizeMapper.queryRechargeFundDetails(fundDetailsCustomize));

        }else{
		    //提现
            lstDetailsVO.addAll(detailsCustomizeMapper.queryWithdrawFundDetails(fundDetailsCustomize));
        }

        //回写请求参数
        lstDetailsVO.forEach(detail->{
            detail.setBusinessType(fundDetailsQO.getBusinessType());
            detail.setAssetId(fundDetailsQO.getAssetId()==null?ASSET_ID_HYJF:fundDetailsQO.getAssetId());
            detail.setEntId(fundDetailsQO.getEntId());
            detail.setStartTime(fundDetailsQO.getStartTime());
            detail.setEndTime(fundDetailsQO.getEndTime());
        });

        return lstDetailsVO;
	}

    private FundDetailsCustomize buildCustomize(FundDetailsQO fundDetailsQO) {
	    FundDetailsCustomize customize=new FundDetailsCustomize();
        BeanUtils.copyProperties(fundDetailsQO,customize);
        if (!Strings.isNullOrEmpty(fundDetailsQO.getAssetCustomerId())){
            customize.setAssetCustomerIdInt(Integer.valueOf(fundDetailsQO.getAssetCustomerId()));
        }
        //转换成平台的渠道号8001=>11200009
        String utmId=EntUtmIds.getUtmId(String.valueOf(fundDetailsQO.getEntId()));
        if(Strings.isNullOrEmpty(utmId)){
            throw new WbsFundDetailsException("未找到entId【"+fundDetailsQO.getEntId()+"】对应的UTMID");
        }

        List<String> lstUtm=Splitter.on(",").trimResults().splitToList(utmId);
        lstUtm.forEach(utmIdd->customize.getAssetEntId().add(Integer.valueOf(utmIdd)));
        return customize;
    }

    private void validateParameters(FundDetailsQO fundDetailsQO) {
	    //校验查询周期
        checkQueryScope(fundDetailsQO);

        //财富端ID
        if(fundDetailsQO.getEntId()==null){
            throw new WbsFundDetailsException("财富端ID为空！");
        }

        //业务类型
        Integer businessType=fundDetailsQO.getBusinessType();
        if(businessType==null){
            throw new WbsFundDetailsException("业务类型为空！");
        }

        if(!Arrays.asList(ARRAY_BUSINESSTYPE).contains(businessType)){
            throw new WbsFundDetailsException("不支持的业务类型【"+businessType+"】");
        }
    }

    /**
	 * 查询周期不能超过1天
	 * 
	 * @param fundDetailsQO
	 */
	private void checkQueryScope(FundDetailsQO fundDetailsQO) {

		String startTime = fundDetailsQO.getStartTime();
		String endTime = fundDetailsQO.getEndTime();
		if (Strings.isNullOrEmpty(startTime) || Strings.isNullOrEmpty(endTime)) {
			throw new WbsFundDetailsException("起始时间和结束时间不能为空!");
		}

		LocalDateTime start = LocalDateTime.parse(startTime, dtf);
		LocalDateTime end = LocalDateTime.parse(endTime, dtf);

		Duration result = Duration.between(start, end);
		if (result.toDays() >= 1) {
			throw new WbsFundDetailsException("查询时间间隔大于一天,起始时间【" + startTime + "】结束时间【" + endTime + "】");
		}
	}

}
