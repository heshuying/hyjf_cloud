/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.extensioncenter;

import com.hyjf.admin.beans.request.ProtocolsRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.controller.promotion.channel.ChannelController;
import com.hyjf.admin.service.ChannelStatisticsDetailService;
import com.hyjf.admin.service.ProtocolsService;
import com.hyjf.admin.service.promotion.channel.ChannelService;
import com.hyjf.admin.utils.ValidatorFieldCheckUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.ChannelStatisticsDetailResponse;
import com.hyjf.am.response.admin.promotion.UtmResultResponse;
import com.hyjf.am.response.config.LandingPageResponse;
import com.hyjf.am.response.trade.FddTempletCustomizeResponse;
import com.hyjf.am.resquest.admin.ChannelStatisticsDetailRequest;
import com.hyjf.am.vo.admin.promotion.channel.ChannelCustomizeVO;
import com.hyjf.am.vo.admin.promotion.channel.UtmChannelVO;
import com.hyjf.am.vo.config.LandingPageVo;
import com.hyjf.am.vo.trade.FddTempletCustomizeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tanyy
 * @version ExtensionCenterController, v0.1 2018/7/16 16:03
 */
@Api(value = "PC统计明细")
@RestController
@RequestMapping("/hyjf-admin/channelstatisticsdetail")
public class ChannelStatisticsDetailController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(ChannelStatisticsDetailController.class);
	@Resource
	private ChannelStatisticsDetailService channelStatisticsDetailService;

	@ApiOperation(value = "PC统计明细", notes = "PC统计明细列表")
	@PostMapping("/searchaction")
	public AdminResult searchAction(@RequestBody ChannelStatisticsDetailRequest request) {
		logger.info("PC统计明细查询开始......");
		ChannelStatisticsDetailResponse response = channelStatisticsDetailService.searchAction(request);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));

	}

}
