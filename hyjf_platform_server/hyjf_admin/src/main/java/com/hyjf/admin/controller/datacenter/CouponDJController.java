/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.datacenter;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.DataCenterCouponBean;
import com.hyjf.admin.beans.request.DadaCenterCouponRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.DataCenterCouponService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DataCenterCouponResponse;
import com.hyjf.am.vo.admin.coupon.DataCenterCouponCustomizeVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yinhui
 * @version CouponDJController, v0.1 2018/7/18 18:18
 */
@Api(tags = "数据中心-优惠券--代金券")
@RestController
@RequestMapping("/hyjf-admin/datacenter/coupon_dj")
public class CouponDJController extends BaseController {
	@Autowired
	private DataCenterCouponService couponService;

	/** 权限 */
	public static final String PERMISSIONS = "DATACENTERCOUPON";

	@ApiOperation(value = "数据中心-代金券", notes = "数据中心-代金券列表查询")
    @PostMapping("/get_coupon_list")
	@AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_VIEW , ShiroConstants.PERMISSION_SEARCH})
	public AdminResult<ListResult<DataCenterCouponCustomizeVO>> getCouponList(@RequestBody DadaCenterCouponRequestBean requestBean) {
		DataCenterCouponResponse response = couponService.searchAction(requestBean, "DJ");
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}


	private DataCenterCouponCustomizeVO createDataCenterCouponCustomize(DataCenterCouponBean form) {
		DataCenterCouponCustomizeVO dataCenterCouponCustomize = new DataCenterCouponCustomizeVO();

       /* if(StringUtils.isNotEmpty(form.getOrderId())){
            couponBackMoneyCustomize.setNid(form.getOrderId());
        }
        if(StringUtils.isNotEmpty(form.getUsername())){
            couponBackMoneyCustomize.setUsername(form.getUsername());
        }
        if(StringUtils.isNotEmpty(form.getCouponCode())){
            couponBackMoneyCustomize.setCouponCode(form.getCouponCode());
        }
        if(StringUtils.isNotEmpty(form.getBorrowNid())){
            couponBackMoneyCustomize.setBorrowNid(form.getBorrowNid());
        }
        if(StringUtils.isNotEmpty(form.getCouponReciveStatus())){
            couponBackMoneyCustomize.setReceivedFlg(form.getCouponReciveStatus());
        }
        if(StringUtils.isNotEmpty(form.getTimeStartSrch())){
            couponBackMoneyCustomize.setTimeStartSrch(form.getTimeStartSrch());
        }
        if(StringUtils.isNotEmpty(form.getTimeEndSrch())){
            couponBackMoneyCustomize.setTimeEndSrch(form.getTimeEndSrch());
        }*/
		return dataCenterCouponCustomize;
	}
    @ApiOperation(value = "导出代金券列表", notes = "导出代金券列表")
	@GetMapping("/export_dj_action")
	@AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_EXPORT })
	 public void exportToExcel(HttpServletRequest request, HttpServletResponse response, DataCenterCouponBean form) throws Exception {
	        //sheet默认最大行数
			int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
			// 表格sheet名称
			String sheetName = "代金券列表";
	        // 文件名称
	        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) +  CustomConstants.EXCEL_EXT;
	        // 声明一个工作薄
	        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
	        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

			DataCenterCouponCustomizeVO dataCenterCouponCustomize =createDataCenterCouponCustomize(form);
			List<DataCenterCouponCustomizeVO> resultList  = this.couponService.getRecordListDJ(dataCenterCouponCustomize);
	        
	        Integer totalCount = resultList.size();

	        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
	        Map<String, String> beanPropertyColumnMap = buildMap();
	        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
	        String sheetNameTmp = "";
	        for (int i = 1; i <= sheetCount; i++) {
				int start=(i-1) * defaultRowMaxCount;
				int end = Math.min(totalCount, i * defaultRowMaxCount);

				sheetNameTmp = sheetName + "_第" + (i) + "页";
				helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList.subList(start, end));
	        }
	        
	        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
	    }

		private Map<String, String> buildMap() {
			Map<String, String> map = Maps.newLinkedHashMap();
			map.put("title", "来源");
			map.put("grantNum", "已发放数量");
			map.put("usedNum", "已使用数量");
			map.put("expireNum", "已失效数量");
			map.put("utilizationRate", "使用率");
			map.put("failureRate", "失效率");
			map.put("recoverInterest", "总收益");
			map.put("recivedMoney", "已发放收益");
			map.put("norecivedMoney", "待发放收益");
			map.put("realTenderMoney", "累计真实投资金额");

			return map;
		}
	    private Map<String, IValueFormatter> buildValueAdapter() {
	        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
	        return mapAdapter;
	    }

}
