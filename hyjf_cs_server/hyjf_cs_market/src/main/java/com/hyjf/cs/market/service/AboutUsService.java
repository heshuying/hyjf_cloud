/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.response.trade.ContentArticleResponse;
import com.hyjf.am.resquest.trade.ContentArticleRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.vo.config.*;
import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.bean.result.WebResult;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fuqiang
 * @version AboutUsService, v0.1 2018/7/9 9:51
 */
public interface AboutUsService extends BaseMarketService {
	/**
	 * 获取公司简介
	 *
	 * @return
	 */
	ContentArticleVO getAboutUs();

	/**
	 * 获取累计投资金额
	 *
	 * @return
	 */
	String getTotalInvestmentAmount();

	/**
	 * 获取创始人信息
	 *
	 * @return
	 */
	TeamVO getFounder();

	/**
	 * 获取合作伙伴列表
	 *
	 * @param partnerType
	 *            合作类型
	 * @return
	 */
	List<LinkVO> getPartnersList(Integer partnerType);

	/**
	 * 获取所有公司记事记录
	 *
	 * @return
	 */
	List<EventVO> getEventsList();

	/**
	 * 检索活动列表数据
	 *
	 * @return
	 */
	List<ContentArticleVO> getNoticeListCount();

	/**
	 * 根据主键ID获取Aricle
	 *
	 * @param id
	 * @return
	 */
	ContentArticleVO getNoticeInfo(Integer id);

	/**
	 * 根据ID获取公司历程详情
	 * @param id
	 * @return
	 * @Author : huanghui
	 */
	EventVO getEventDetailById(Integer id);

	/**
	 * 获取招贤纳士列表
	 *
	 * @return
	 */
	List<JobsVo> getJobsList();

	/**
	 * 获取联系我们
	 *
	 * @return
	 */
	ContentArticleVO getContactUs();

    /**
     * 获取网贷知识
     * @return
     */
	ContentArticleResponse getHomeNoticeList(ContentArticleRequest request);


    /**
     * 帮助中心索引页面
     * @param request
     * @return
     */
	List<Map<String, Object>> getIndex(ContentArticleRequest request);


    /**
     * 查詢运营统计数据
     */
    TotalInvestAndInterestVO searchData();


	List<JxBankConfigVO> getBanksList();


	/**
	 * 累计投资总额
	 * @return
	 */
	BigDecimal selectTenderSum();

	/**
	 * 累计收益
	 * @return
	 */
	BigDecimal selectInterestSum();

	/**
	 * 累计投资笔数
	 * @return
	 */
	int selectTotalTenderSum();

}
