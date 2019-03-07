/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter.plancenter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.AccedeListViewRequest;
import com.hyjf.admin.beans.vo.AdminAccedeListCustomizeVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.AccedeListService;
import com.hyjf.admin.service.PlanListService;
import com.hyjf.admin.utils.PdfGenerator;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AccedeListResponse;
import com.hyjf.am.resquest.admin.AccedeListRequest;
import com.hyjf.am.resquest.admin.PlanListRequest;
import com.hyjf.am.vo.fdd.FddGenerateContractBeanVO;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.hjh.*;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.FddGenerateContractConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.file.FileUtil;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/*import com.hyjf.admin.utils.PdfGenerator;*/
/**
 * @author libin
 * @version AccedeListController.java, v0.1 2018年7月7日 下午3:00:56
 */
@Api(value = "产品中心-汇计划-加入明细",tags = "产品中心-汇计划-加入明细")
@RestController
@RequestMapping("/hyjf-admin/joinplan")
public class AccedeListController extends BaseController{
	
	@Autowired
	private AccedeListService accedeListService;
	@Autowired 
	private PlanListService planListService;

	@Autowired
	private CommonProducer commonProducer;

	@Autowired
	private SystemConfig systemConfig;

	
    /** 权限 */
	public static final String PERMISSIONS = "accedelist";
	/** 用户名 */
	private static final String VAL_NAME = "val_name";
	/** 性别 */
	private static final String VAL_SEX = "val_sex";
    /** 用户ID */
    private static final String VAL_USERID = "userId";
    private static final Logger _log = LoggerFactory.getLogger(AccedeListController.class);
    /**
     * 画面初始化
     *
     * @param request
     * @return 汇计划加入明细列表         已测试
     */
    @ApiOperation(value = "汇计划加入明细列表", notes = "汇计划加入明细列表初始化")
    @PostMapping(value = "/search")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<AdminAccedeListCustomizeVO>> search(HttpServletRequest request, @RequestBody @Valid AccedeListViewRequest viewRequest) {
    	// 初始化原子层请求实体
    	AccedeListRequest form = new AccedeListRequest();
		// 初始化返回LIST
		List<AdminAccedeListCustomizeVO> volist = null;
		// 将画面检索参数request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		java.math.BigDecimal num;
		java.math.BigDecimal hundred = new java.math.BigDecimal(100);
    	// 画面检索条件无需初始化  订单状态 与 操作平台都在JSP option 写好
    	// 根据删选条件获取加入计划列表
    	AccedeListResponse response = this.accedeListService.getAccedeListByParam(form);
		if(response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		if(CollectionUtils.isNotEmpty(response.getResultList())){
			for (AccedeListCustomizeVO accedeListCustomizeVO : response.getResultList()) {
				// 自动投标进度=已出借金额/加入金额（不考虑复投）
				// 确定分母不为0
				if(accedeListCustomizeVO.getJalreadyInvest() == null &&  accedeListCustomizeVO.getjAccedeAccount() == null && accedeListCustomizeVO.getjAccedeAccount().equals(BigDecimal.ZERO)){
					accedeListCustomizeVO.setAutoBidProgress("0");
				} else {
					num = accedeListCustomizeVO.getJalreadyInvest().divide(accedeListCustomizeVO.getjAccedeAccount(), 2);
					accedeListCustomizeVO.setAutoBidProgress(num.multiply(hundred).toString().replace(".00", ""));
				}
				
				if(StringUtils.isNotEmpty(accedeListCustomizeVO.getCreateTime())){
					accedeListCustomizeVO.setCreateTime(accedeListCustomizeVO.getCreateTime().replace(".0",""));
				}
			}
			volist = CommonUtils.convertBeanList(response.getResultList(), AdminAccedeListCustomizeVO.class);
			return new AdminResult<ListResult<AdminAccedeListCustomizeVO>>(ListResult.build(volist, response.getCount()));
		} else {
			return new AdminResult<ListResult<AdminAccedeListCustomizeVO>>(ListResult.build(volist, 0));
		}
    }

	/**
	 * 汇计划加入明细列表         已测试
	 *
	 * @param request
	 * @return 
	 */
	@ApiOperation(value = "汇计划加入明细列表", notes = "汇计划加入明细列表列总计")
	@PostMapping(value = "/sum")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)  
	public JSONObject getSumTotal(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AccedeListViewRequest viewRequest) {
		JSONObject jsonObject = new JSONObject();
		// 初始化原子层请求实体
		AccedeListRequest form = new AccedeListRequest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		HjhAccedeSumVO sumVO = this.accedeListService.getCalcSumByParam(form);
		if(sumVO != null){
			jsonObject.put("sumAccedeAccount", sumVO.getSumAccedeAccount());
			/*jsonObject.put("sumWaitTotal", sumVO.getSumWaitTotal());*/
			/*jsonObject.put("sumWaitCaptical", sumVO.getSumWaitCaptical());*/
			/*jsonObject.put("sumWaitInterest", sumVO.getSumWaitInterest());*/
			jsonObject.put("sumAvailableInvestAccount", sumVO.getSumAvailableInvestAccount());
			jsonObject.put("sumFrostAccount", sumVO.getSumFrostAccount());
			jsonObject.put("sumFairValue", sumVO.getSumFairValue());
			jsonObject.put("status", SUCCESS);
		} else {
			jsonObject.put("msg", "查询为空");
			jsonObject.put("status", FAIL);
		}
		return jsonObject;
	}
    
	/**
	 * 导出功能     已测试
	 * 
	 * @param request
	 * @param modelAndView
	 * @param form
	 */
    /*@ApiOperation(value = "汇计划加入明细列表", notes = "汇计划加入明细列表导出")
    @PostMapping(value = "/export")
    @ResponseBody
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AccedeListViewRequest viewRequest) throws Exception {
		// 表格sheet名称
		String sheetName = "加入明细";
		java.math.BigDecimal num;
		java.math.BigDecimal hundred = new java.math.BigDecimal(100);
		@SuppressWarnings("deprecation")
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		*//*String[] titles = new String[] { "序号","加入订单号", "计划编号","锁定期", "预定利率","用户名","出借人id","出借人用户属性（当前)", "分公司(当前)", "部门(当前)", "团队(当前)","推荐人（当前）","推荐人ID（当前）","推荐人姓名（当前）", "推荐人用户属性（当前）", "分公司(当前)", "部门(当前)", "团队(当前)", "出借人用户属性（出借时）","推荐人(出借时)", "推荐人ID（出借时）", "推荐人姓名（出借时）", "推荐人用户属性(出借时)", "分公司(出借时)", "部门(出借时)", "团队(出借时)", "加入金额", "已出借金额(元)","待还总额(元) ","待还本金(元) ","待还利息(元) ","操作平台","订单状态",  "计息时间", "加入时间" };*//*
		String[] titles = new String[] { "序号","计划订单号", "计划编号",  "计划名称","锁定期", "预期出借利率率","用户名（出借人）","出借人id","出借人用户属性（当前)", "分公司(当前)", "部门(当前)", "团队(当前)","推荐人（当前）","推荐人ID（当前）","推荐人姓名（当前）", "推荐人用户属性（当前）", "分公司(当前)", "部门(当前)", "团队(当前)", "出借人用户属性（出借时）","推荐人(出借时)", "推荐人ID（出借时）", "推荐人姓名（出借时）", "推荐人用户属性(出借时)", "分公司(出借时)", "部门(出借时)", "团队(出借时)", "加入金额",
				"自动投标进度","可用余额(元) ", "冻结金额(元) ","公允价值(元) ","实际出借利率率","操作平台","订单状态","匹配期", "锁定时间", "加入时间" };
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
		// 初始化原子层请求实体
		AccedeListRequest form = new AccedeListRequest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		// 不带分页的查询
		List<AccedeListCustomizeVO> resultList = this.accedeListService.getAccedeListByParamWithoutPage(form);
		if (resultList != null && resultList.size() > 0) {
			int sheetCount = 1;
			int rowNum = 0;
			for (int i = 0; i < resultList.size(); i++) {
				rowNum++;
				if (i != 0 && i % 60000 == 0) {
					sheetCount++;
					sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
					rowNum = 1;
				}
				// 新建一行
				Row row = sheet.createRow(rowNum);
				// 循环数据
				for (int celLength = 0; celLength < titles.length; celLength++) {
					AccedeListCustomizeVO planAccedeDetail = resultList.get(i);

					// 创建相应的单元格
					Cell cell = row.createCell(celLength);

					// 序号
					if (celLength == 0) {
						cell.setCellValue(i + 1);
					}
					// 计划订单号
					else if (celLength == 1) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getPlanOrderId()) ? StringUtils.EMPTY : planAccedeDetail.getPlanOrderId());
					}
					// 计划编号
					else if (celLength == 2) {
						cell.setCellValue(planAccedeDetail.getDebtPlanNid());
					}
					// 计划名称
					else if (celLength == 3) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getDebtPlanName()) ? StringUtils.EMPTY : planAccedeDetail.getDebtPlanName());
					}
					// 锁定期
					else if (celLength == 4) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getDebtLockPeriod()) ? StringUtils.EMPTY : planAccedeDetail.getDebtLockPeriod());
					}
					// 预期出借利率率
					else if (celLength == 5) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getExpectApr()) ? StringUtils.EMPTY : planAccedeDetail.getExpectApr() + "%");
					}
					// 用户名
					else if (celLength == 6) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getUserName()) ? StringUtils.EMPTY : planAccedeDetail.getUserName());
					}
					// 出借人id
					else if (celLength == 7) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getUserId()) ? StringUtils.EMPTY : planAccedeDetail.getUserId());
					}
					// 出借人当前属性
					else if (celLength == 8) {
						if ("0".equals(planAccedeDetail.getUserAttribute())) {
							cell.setCellValue("无主单");
						} else if ("1".equals(planAccedeDetail.getUserAttribute())) {
							cell.setCellValue("有主单");
						} else if ("2".equals(planAccedeDetail.getUserAttribute())) {
							cell.setCellValue("线下员工");
						} else if ("3".equals(planAccedeDetail.getUserAttribute())) {
							cell.setCellValue("线上员工");
						}else {
							cell.setCellValue(planAccedeDetail.getUserAttribute());
						}
					}
					// 出借人公司
					else if (celLength == 9) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserRegionname2()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserRegionname2());
					}
					// 出借人公司
					else if (celLength == 10) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserBranchname2()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserBranchname2());
					}
					// 出借人公司
					else if (celLength == 11) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserDepartmentname2()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserDepartmentname2());
					}
					// 推荐人（当前）
					else if (celLength == 12) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getRefereeUserName()) ? StringUtils.EMPTY : planAccedeDetail.getRefereeUserName());
					}
					// 推荐人（当前）
					else if (celLength == 13) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getRefereeUserId()) ? StringUtils.EMPTY : planAccedeDetail.getRefereeUserId());
					}
					else if (celLength == 14) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getRefereeTrueName()) ? StringUtils.EMPTY : planAccedeDetail.getRefereeTrueName());
					}
					// 推荐人用户属性（当前）
					else if (celLength == 15) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getRecommendAttr()) ? StringUtils.EMPTY : planAccedeDetail.getRecommendAttr());
					}

					else if (celLength == 16) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserRegionname1()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserRegionname1());
					}
					// 推荐人用户属性（当前）
					else if (celLength == 17) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserBranchname1()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserBranchname1());
					}
					// 推荐人用户属性（当前）
					else if (celLength == 18) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserDepartmentname1()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserDepartmentname1());
					}
					
					// 出借人当前属性
					else if (celLength == 19) {
						if ("0".equals(planAccedeDetail.getAttribute())) {
							cell.setCellValue("无主单");
						} else if ("1".equals(planAccedeDetail.getAttribute())) {
							cell.setCellValue("有主单");
						} else if ("2".equals(planAccedeDetail.getAttribute())) {
							cell.setCellValue("线下员工");
						} else if ("3".equals(planAccedeDetail.getAttribute())) {
							cell.setCellValue("线上员工");
						}else {
							cell.setCellValue(planAccedeDetail.getAttribute());
						}

					}
					// 推荐人用户属性（当前）
					else if (celLength == 20) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteName()) ? StringUtils.EMPTY : planAccedeDetail.getInviteName());
					}
					else if (celLength == 21) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserId()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserId());
					}
					else if (celLength == 22) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteTrueName()) ? StringUtils.EMPTY : planAccedeDetail.getInviteTrueName());
					}
					// 推荐人用户属性（当前）
					else if (celLength == 23) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserAttributeName()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserAttributeName());
					}
					// 推荐人用户属性（当前）
					else if (celLength == 24) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserRegionname()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserRegionname());
					}
					// 推荐人用户属性（当前）
					else if (celLength == 25) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserBranchname()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserBranchname());
					}
					// 推荐人用户属性（当前）
					else if (celLength == 26) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserDepartmentname()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserDepartmentname());
					}
					// 加入金额
					else if (celLength == 27) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getAccedeAccount()) ? StringUtils.EMPTY : planAccedeDetail.getAccedeAccount());
					}
					// 自动投标进度
					else if (celLength == 28) {
						if(planAccedeDetail.getJalreadyInvest() == null &&  planAccedeDetail.getjAccedeAccount() == null && planAccedeDetail.getjAccedeAccount().equals(BigDecimal.ZERO)){
							cell.setCellValue("0%");
						} else {
							num = planAccedeDetail.getJalreadyInvest().divide(planAccedeDetail.getjAccedeAccount(), 2);
							cell.setCellValue(num.multiply(hundred).toString().replace(".00", "") + "%");
						}
					}
					// 可用余额
					else if (celLength == 29) {
						if(planAccedeDetail.getAvailableInvestAccount() != null){
							cell.setCellValue(planAccedeDetail.getAvailableInvestAccount());
						} else {
							cell.setCellValue("0.0");
						}
					}
					// 冻结金额
					else if (celLength == 30) {
						if(planAccedeDetail.getFrostAccount() != null){
							cell.setCellValue(planAccedeDetail.getFrostAccount());
						} else {
							cell.setCellValue("0.0");
						}
					}
					// 公允价值
					else if (celLength == 31) {
						if(planAccedeDetail.getFairValue()!= null){
						   cell.setCellValue(planAccedeDetail.getFairValue());
						} else {
							cell.setCellValue("0.0");
						}
					}
					// 实际出借利率率
					else if (celLength == 32) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getActualApr()) ? StringUtils.EMPTY : planAccedeDetail.getActualApr() + "%");
					}
					// 平台
					else if (celLength == 33) {
						if ("0".equals(planAccedeDetail.getPlatform())) {
							cell.setCellValue("PC");
						} else if ("1".equals(planAccedeDetail.getPlatform())) {
							cell.setCellValue("微官网");
						} else if ("2".equals(planAccedeDetail.getPlatform())) {
							cell.setCellValue("android");
						} else if ("3".equals(planAccedeDetail.getPlatform())) {
							cell.setCellValue("ios");
						}
					}
					// 订单状态
					else if (celLength == 34) {
						if (0 == Integer.parseInt(planAccedeDetail.getOrderStatus())) {
							cell.setCellValue("自动投标中");
						} else if (2 == Integer.parseInt(planAccedeDetail.getOrderStatus())) {
							cell.setCellValue("自动投标成功");
						} else if (3 == Integer.parseInt(planAccedeDetail.getOrderStatus())) {
							cell.setCellValue("锁定中");
						} else if (5 == Integer.parseInt(planAccedeDetail.getOrderStatus())) {
							cell.setCellValue("退出中");
						} else if (7 == Integer.parseInt(planAccedeDetail.getOrderStatus())) {
							cell.setCellValue("已退出");
						} else if (99 == Integer.parseInt(planAccedeDetail.getOrderStatus())) {
							cell.setCellValue("自动出借异常");
						}
					}
					// 匹配期
					else if (celLength == 35) {
						if (StringUtils.isNotEmpty(planAccedeDetail.getMatchDates())) {
							cell.setCellValue(planAccedeDetail.getMatchDates() + "天");
						}
					}
					// 锁定时间
					else if (celLength == 36) {
						if (StringUtils.isNotEmpty(planAccedeDetail.getCountInterestTime())) {
							cell.setCellValue(planAccedeDetail.getCountInterestTime());
						}
					}
					// 加入时间
					else if (celLength == 37) {
						if (planAccedeDetail.getCreateTime() != null) {
							cell.setCellValue(planAccedeDetail.getCreateTime());
						}
					}
				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }*/


	/**
	 * 导出功能     已测试
	 */
	@ApiOperation(value = "汇计划加入明细列表", notes = "汇计划加入明细列表导出")
	@PostMapping(value = "/export")
	@ResponseBody
	public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AccedeListViewRequest viewRequest) throws Exception {
		//sheet默认最大行数
		int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());

		// 具有组织机构查看权限
		String isOrganizationView = viewRequest.getIsOrganizationView();

		// 表格sheet名称
		String sheetName = "智投订单";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
		DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
		// 初始化原子层请求实体
		AccedeListRequest form = new AccedeListRequest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		// 不带分页的查询
		Integer totalCount = this.accedeListService.getAccedeListByParamCount(form);

		int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;

		Map<String, String> beanPropertyColumnMap = buildMap(isOrganizationView);
		Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
		String sheetNameTmp = sheetName + "_第1页";
		if (totalCount == 0) {

			helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
		}

		// 查询列表传入分页
		Paginator paginator;
		for (int i = 1; i <= sheetCount; i++) {
			//请求第一页5000条
			paginator = new Paginator(i, totalCount,defaultRowMaxCount);
			form.setLimitStart(paginator.getOffset());
			form.setLimitEnd(paginator.getLimit());

			List<AccedeListCustomizeVO> resultResponse2 = accedeListService.getAccedeListByParamList(form);
			this.dataClean(resultResponse2);
			if (resultResponse2 != null && resultResponse2.size()> 0) {
				sheetNameTmp = sheetName + "_第" + (i) + "页";
				helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  resultResponse2);
			} else {
				break;
			}
		}

		DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
	}

	/**
	 * 数据清洗
	 * @param resultList
	 * @return
	 */
	private List<AccedeListCustomizeVO> dataClean(List<AccedeListCustomizeVO> resultList) {
		if (resultList == null) {
			return Collections.emptyList();
		}
		java.math.BigDecimal num;
		java.math.BigDecimal hundred = new java.math.BigDecimal(100);
		for (AccedeListCustomizeVO record : resultList) {
			if (record.getJalreadyInvest() == null && record.getjAccedeAccount() == null && record.getjAccedeAccount().equals(BigDecimal.ZERO)) {
				record.setInvestScaleView("0%");
			} else {
				num = record.getJalreadyInvest().divide(record.getjAccedeAccount(), 2);
				record.setInvestScaleView(num.multiply(hundred).toString().replace(".00", "") + "%");
			}

			if (StringUtils.isBlank(record.getAvailableInvestAccount())) {
				record.setAvailableInvestAccount("0.0");
			}

			if (StringUtils.isBlank(record.getFrostAccount())) {
				record.setFrostAccount("0.0");
			}

			if (StringUtils.isBlank(record.getFairValue())) {
				record.setFairValue("0.0");
			}

			if (StringUtils.isBlank(record.getActualApr())) {
				record.setActualApr(record.getActualApr() + "%");
			}

			if ("0".equals(record.getPlatform())) {
				record.setPlatform("PC");
			} else if ("1".equals(record.getPlatform())) {
				record.setPlatform("微官网");
			} else if ("2".equals(record.getPlatform())) {
				record.setPlatform("android");
			} else if ("3".equals(record.getPlatform())) {
				record.setPlatform("ios");
			}

			if (0 == Integer.parseInt(record.getOrderStatus())) {
				record.setOrderStatus("自动投标中");
			} else if (2 == Integer.parseInt(record.getOrderStatus())) {
				record.setOrderStatus("自动投标成功");
			} else if (3 == Integer.parseInt(record.getOrderStatus())) {
				record.setOrderStatus("锁定中");
			} else if (5 == Integer.parseInt(record.getOrderStatus())) {
				record.setOrderStatus("退出中");
			} else if (7 == Integer.parseInt(record.getOrderStatus())) {
				record.setOrderStatus("已退出");
			} else if (99 == Integer.parseInt(record.getOrderStatus())) {
				record.setOrderStatus("自动出借异常");
			}

			if (StringUtils.isNotEmpty(record.getMatchDates())) {
				record.setMatchDates(record.getMatchDates() + "天");
			}

			if(record.getRefereeUserId() == null || "0".equals(record.getRefereeUserId())){
				record.setRefereeUserId("");
			}

			if(record.getInviteUserId() == null || "0".equals(record.getInviteUserId())){
				record.setInviteUserId("");
			}
		}

		return resultList;
	}

	private Map<String, String> buildMap(String isOrganizationView) {
		Map<String, String> map = Maps.newLinkedHashMap();
		map.put("planOrderId", "智投订单号");
		map.put("debtPlanNid", "智投编号");
		map.put("debtPlanName", "智投名称");
		map.put("debtLockPeriod", "服务回报期限");
		map.put("expectApr", "参考年回报率");
		map.put("userName", "用户名（出借人）");
		map.put("userId", "出借人id");
		map.put("userAttribute", "出借人用户属性（当前)");
		if (StringUtils.isNotBlank(isOrganizationView)) {
			map.put("inviteUserRegionname2", "分公司(当前)");
			map.put("inviteUserBranchname2", "部门(当前)");
			map.put("inviteUserDepartmentname2", "团队(当前)");
		}
		map.put("refereeUserName", "推荐人（当前）");
		map.put("refereeUserId", "推荐人ID（当前）");
		map.put("refereeTrueName", "推荐人姓名（当前）");
		map.put("recommendAttr", "推荐人用户属性（当前）");
		if (StringUtils.isNotBlank(isOrganizationView)) {
			map.put("inviteUserRegionname1", "分公司(当前)");
			map.put("inviteUserBranchname1", "部门(当前)");
			map.put("inviteUserDepartmentname1", "团队(当前)");
		}
		map.put("attribute", "出借人用户属性（出借时）");
		map.put("inviteName", "推荐人(出借时)");
		map.put("inviteUserId", "推荐人ID（出借时）");
		map.put("inviteTrueName", "推荐人姓名（出借时）");
		map.put("inviteUserAttributeName", "推荐人用户属性(出借时)");
		if (StringUtils.isNotBlank(isOrganizationView)) {
			map.put("inviteUserRegionname", "分公司(出借时)");
			map.put("inviteUserBranchname", "部门(出借时)");
			map.put("inviteUserDepartmentname", "团队(出借时)");
		}
		map.put("accedeAccount", "授权服务金额");
		map.put("investScaleView", "自动投标进度");
		map.put("availableInvestAccount", "可用余额(元) ");
		map.put("frostAccount", "冻结金额(元) ");
		map.put("fairValue", "公允价值(元) ");
		map.put("actualApr", "实际出借利率");
		map.put("platform", "操作平台");
		map.put("orderStatus", "订单状态");
		map.put("matchDates", "匹配期");
		map.put("countInterestTime", "开始计息时间");
		map.put("createTime", "授权服务时间");
		map.put("endDate", "预计开始退出时间");
		map.put("acctualPaymentTime", "实际退出时间");
		return map;
	}
	private Map<String, IValueFormatter> buildValueAdapter() {
		Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
		IValueFormatter userAttributeAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				String value = (String) object;
				if ("0".equals(value)) {
					return "无主单";
				} else if ("1".equals(value)) {
					return "有主单";
				} else if ("2".equals(value)) {
					return "线下员工";
				} else if ("3".equals(value)) {
					return "线上员工";
				}else {
					return value;
				}
			}
		};

		IValueFormatter attributeAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				String value = (String) object;
				if ("0".equals(value)) {
					return "无主单";
				} else if ("1".equals(value)) {
					return "有主单";
				} else if ("2".equals(value)) {
					return "线下员工";
				} else if ("3".equals(value)) {
					return "线上员工";
				}else {
					return value;
				}
			}
		};

		IValueFormatter expectAprAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				String value = (String) object;
				if (StringUtils.isNotBlank(value)) {
					return value + "%";
				}
				return value;
			}
		};

		IValueFormatter actualAprAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				String value = (String) object;
				if (StringUtils.isNotBlank(value)) {
					return value + "%";
				}
				return value;
			}
		};

		mapAdapter.put("userAttribute", userAttributeAdapter);
		mapAdapter.put("attribute", attributeAdapter);
		mapAdapter.put("expectApr", expectAprAdapter);
		mapAdapter.put("actualApr", actualAprAdapter);
		return mapAdapter;
	}
    
	/**
	 * 跳转到协议发送入力页面(注意 ：微服务协议部分不参考旧代码，参考新的汇计划三期的代码)   已测试
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
    @ApiOperation(value = "汇计划加入明细列表", notes = "跳转到协议发送入力页面")
    @PostMapping(value = "/toexportagreement")
    @ResponseBody
    public JSONObject toExportAgreementAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AccedeListViewRequest viewRequest) {
    	JSONObject jsonObject = new JSONObject();
    	// 初始化查询bean
    	AccedeListRequest form = new AccedeListRequest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
    	
    	PlanListRequest planListRequest = new PlanListRequest();
    	//从form中取值然后出道画面作为隐藏域
/*		String userid = request.getParameter("userid");
		String planOrderId = request.getParameter("planOrderId");
		String debtPlanNid = request.getParameter("debtPlanNid");*/
    	int userid = form.getUserId();
    	String planOrderId = form.getAccedeOrderIdSrch();
    	String debtPlanNid = form.getDebtPlanNidSrch();
		jsonObject.put("userid", userid);
		jsonObject.put("planOrderId", planOrderId);
		jsonObject.put("debtPlanNid", debtPlanNid);
		// 查询汇计划表获取还款方式
		if(StringUtils.isNotEmpty(debtPlanNid)){
			planListRequest.setDebtPlanNid(debtPlanNid);
			// 调用
			List<HjhPlanDetailVO> planList = this.planListService.getHjhPlanDetailByPlanNid(planListRequest);
			if(planList.size()>0){
				jsonObject.put("borrowStyle", planList.get(0).getBorrowStyle());
			}
		}
		// 查询汇计划加入表 
		if(StringUtils.isNotEmpty(planOrderId)){
			form.setAccedeOrderIdSrch(planOrderId);
			// 调用
			List<AccedeListCustomizeVO> resultList = this.accedeListService.getAccedeListByParamWithoutPage(form);
			if(CollectionUtils.isNotEmpty(resultList) && resultList.size()>0){
				AccedeListCustomizeVO accede = resultList.get(0);
				if(accede != null){
					jsonObject.put("accedeOrderId", accede.getPlanOrderId());
					jsonObject.put("planNid", accede.getDebtPlanNid());
					jsonObject.put("lockPeriod", accede.getDebtLockPeriod());
					jsonObject.put("expectApr", accede.getExpectApr());
					jsonObject.put("accedeAmount", accede.getAccedeAccount());
					jsonObject.put("orderStatus", accede.getOrderStatus());
					jsonObject.put("createTime", accede.getCreateTime());
				}
			} else {
				jsonObject.put("error", "该计划订单号查询的记录不存在！" + planOrderId);
			}
		}
		// 查询用户信息放到画面上
		if(userid != 0){
			// 将来这个方法抽成公共类
			UserVO users = this.accedeListService.getUserByUserId(Integer.valueOf(userid));
			if(users != null){
				jsonObject.put("userid", users.getUserId());
				jsonObject.put("email", users.getEmail());
			}
		}
    	return jsonObject;
    }
    
	/**
	 * 点击发送协议 email入力后请求    协议表为空暂时只测试完其他查询部分
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
    @ApiOperation(value = "汇计划加入明细列表", notes = "点击发送协议 email入力后请求")
    @PostMapping(value = "/exportagreement")
    @ResponseBody
    public JSONObject exportAgreementAction(HttpServletRequest request ,@RequestBody @Valid AccedeListViewRequest viewRequest) {
    	JSONObject jsonObject = new JSONObject();
    	// 初始化查询bean
    	AccedeListRequest form = new AccedeListRequest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
    	// 由前台请求传入
/*		String userid = request.getParameter("userid");
		String planOrderId = request.getParameter("planOrderId");
		String debtPlanNid = request.getParameter("debtPlanNid");
		String email = request.getParameter("email");*/
    	int userid = form.getUserId();
    	String planOrderId = form.getAccedeOrderIdSrch();
    	String debtPlanNid = form.getDebtPlanNidSrch();
    	String email = form.getEmail();
		if (StringUtils.isEmpty(email)) {
			jsonObject.put("status", "error");
			jsonObject.put("result", "请输入邮箱地址");
			return jsonObject;
		}
		if (!Validator.isEmailAddress(email)) {
			jsonObject.put("status", "error");
			jsonObject.put("result", "邮箱格式不正确!");
			return jsonObject;
		}
		if (StringUtils.isEmpty(planOrderId) || StringUtils.isEmpty(debtPlanNid)) {
			jsonObject.put("status", "error");
			jsonObject.put("result", "传递参数不正确");
			return jsonObject;
		}
		// 新旧协议发送区分
		// 通过汇计划订单号查询新协议
		String msg = new String();
		List<TenderAgreementVO> tenderAgreementsList = this.accedeListService.selectTenderAgreementByNid(planOrderId);
		// 只有新法大大协议存DB
		if(tenderAgreementsList!=null && tenderAgreementsList.size()>0){
			// 新增发送新协议
			msg = this.sendFddHjhServiceAgrm(String.valueOf(userid), planOrderId, debtPlanNid, email);
		} else {
			// 原发送旧协议 
			msg = this.resendMessageAction(String.valueOf(userid), planOrderId, debtPlanNid, email);
		}
		if("用户不存在".equals(msg)){
			jsonObject.put("result", msg);
			jsonObject.put("status", "error");
		} else if ("用户邮箱不存在".equals(msg)){
			jsonObject.put("result", msg);
			jsonObject.put("status", "error");
		} else if ("系统异常".equals(msg)){
			jsonObject.put("result", msg);
			jsonObject.put("status", "error");
		} else if("计划信息异常（0条或者大于1条信息）,下载汇计划出借计划服务协议协议PDF失败".equals(msg)){
			jsonObject.put("result", msg);
			jsonObject.put("status", "error");
		} else if("发送状态已修改".equals(msg)){
			jsonObject.put("result", "操作完成");
			jsonObject.put("status", "success");
		} 
/*		if (msg == null && "发送状态已修改".equals(msg)) {

		} else if (!"系统异常".equals(msg)) {
			jsonObject.put("result", msg);
			jsonObject.put("status", "error");
		} else {
			jsonObject.put("result", "异常纪录，请刷新后后重试");
			jsonObject.put("status", "error");
		}*/
    	return jsonObject;
    }

	/**
	 * 发送法大大新协议
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
    private String sendFddHjhServiceAgrm(String userid, String planOrderId, String debtPlanNid,String sendEmail){
    	AccedeListRequest request = new AccedeListRequest();
		try {
			// 参考FddDownPDFandDessensitizationMessageHandle.sendPlanMail
			// 向每个出借人发送邮件
			if (Validator.isNotNull(userid) && NumberUtils.isCreatable(userid)) {
				UserVO users = this.accedeListService.getUserByUserId(Integer.valueOf(userid));
				if (users == null) {
					return "用户不存在";
				}
				String email = users.getEmail();
				if(StringUtils.isNotBlank(sendEmail)){
					// 当用户使用入力发送协议时
					email = sendEmail;
				}
				if (Validator.isNull(email)) {
					return "用户邮箱不存在";
				}
				Map<String, String> msg = new HashMap<String, String>();
				msg.put(VAL_NAME, users.getUsername());
				// 获取用户信息
				UserInfoVO usersInfo = this.accedeListService.getUsersInfoByUserId(Integer.valueOf(userid));
				if (Validator.isNotNull(usersInfo) && Validator.isNotNull(usersInfo.getSex())) {
					if (usersInfo.getSex() == 2) {
						msg.put(VAL_SEX, "女士");
					} else {
						msg.put(VAL_SEX, "先生");
					}
				}
				msg.put(VAL_USERID, userid);
				msg.put(VAL_NAME, users.getUsername());
				String fileName = planOrderId+".pdf";
				String filePath = "/pdf_tem/pdf/" + debtPlanNid;
				TenderAgreementVO tenderAgreement = new TenderAgreementVO();
				List<TenderAgreementVO> tenderAgreementsNid = this.accedeListService.selectTenderAgreementByNid(planOrderId);
				/***************************下载法大大协议******************************************/
				if(tenderAgreementsNid!=null && tenderAgreementsNid.size()>0){
					tenderAgreement = tenderAgreementsNid.get(0);
					 _log.info("sendMail", "***************************下载法大大协议--出借orderId:"+planOrderId);
					 _log.info("sendMail", "***************************下载法大大协议--出借pdfUrl:"+tenderAgreement.getImgUrl());
				        if(tenderAgreement!=null){
	                        String pdfUrl = tenderAgreement.getDownloadUrl();
	                        if(StringUtils.isNotBlank(pdfUrl)){
	                            _log.info("sendMail", "***************************下载法大大协议--pdfUrl:"+pdfUrl);
	                            //FileUtil.getRemoteFile(pdfUrl, filePath + fileName);
	                            FileUtil.downLoadFromUrl(pdfUrl,fileName,filePath);
	                        }
	                    }	 
				}
				String[] emails = { email };
				 _log.info("sendMail***************************下载法大大协议--出借filePath:"+filePath + fileName);
				// mod by nxl 智投服务 汇计划出借服务协议->智投服务协议
                /*MailMessage mailMessage = new MailMessage(Integer.valueOf(userid), msg, "汇计划出借服务协议", null, new String[] { filePath + "/" + fileName }, emails, CustomConstants.EMAITPL_EMAIL_LOCK_REPAY,
                		MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS);*/
				MailMessage mailMessage = new MailMessage(Integer.valueOf(userid), msg, "智投服务协议", null, new String[] { filePath + "/" + fileName }, emails, CustomConstants.EMAITPL_EMAIL_LOCK_REPAY,
						MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS);
				// 发送邮件
				commonProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(),mailMessage));
				// 邮件发送成功后修改DB发邮件的状态
				request.setAccedeOrderIdSrch(planOrderId);
				request.setDebtPlanNidSrch(debtPlanNid);
				int flg = this.accedeListService.updateSendStatusByParam(request);
				if(flg> 0 ){
					return "发送状态已修改";
				}
			} else {
				return "系统异常";
			}
		} catch (Exception e) {
			_log.info(AccedeListController.class.getName(), "sendMail", e);
		}
		return "";
    }
    
	/**
	 * 发送法大大旧协议
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
    private String resendMessageAction(String userid, String planOrderId, String debtPlanNid,String sendEmail){
    	AccedeListRequest request = new AccedeListRequest();
		try {
			PdfGenerator pdfGenerator = new PdfGenerator();
			// 向每个出借人发送邮件
			if (Validator.isNotNull(userid) && NumberUtils.isCreatable(userid)) {
				UserVO users = this.accedeListService.getUserByUserId(Integer.valueOf(userid));
				if (users == null) {
					return "用户不存在";
				}
				String email = users.getEmail();
				if(StringUtils.isNotBlank(sendEmail)){
					email=sendEmail;
				}
				if (Validator.isNull(email)) {
					return "用户邮箱不存在";
				}
				Map<String, String> msg = new HashMap<String, String>();
				msg.put(VAL_NAME, users.getUsername());
				// 获取用户信息
				UserInfoVO usersInfo = this.accedeListService.getUsersInfoByUserId(Integer.valueOf(userid));
				if (Validator.isNotNull(usersInfo) && Validator.isNotNull(usersInfo.getSex())) {
					if (usersInfo.getSex() == 2) {
						msg.put(VAL_SEX, "女士");
					} else {
						msg.put(VAL_SEX, "先生");
					}
				}
				// 文件名称
				String fileName = debtPlanNid + "_" + planOrderId + ".pdf";
				// 生成临时文件夹目录：/hyjfdata/data/upfiles/filetemp/pdfPlanAccedes_/
				String filePath = systemConfig.getHYJF_MAKEPDF_TEMPPATH() + "PlanAccedes_"  + GetDate.getMillis() + StringPool.FORWARD_SLASH;
				request.setDebtPlanNidSrch(debtPlanNid);
				request.setAccedeOrderIdSrch(planOrderId);
				request.setUserId(Integer.valueOf(userid));
				List<AccedeListCustomizeVO> recordList = this.accedeListService.getAccedeListByParamWithoutPage(request);
				// 该加入记录存在的前提下
				if (recordList != null && recordList.size() == 1) {
					// PDF将要录入信息
					Map<String, Object> contents = new HashMap<String, Object>();
					UserInfoVO usersInfo1 = this.accedeListService.getUsersInfoByUserId(Integer.valueOf(userid));
					// 1.放入用户信息
					contents.put("userInfo", usersInfo1);
					contents.put("username", users.getUsername());
					UserHjhInvistDetailVO userHjhInvistDetailCustomize = this.accedeListService.selectUserHjhInvistDetail(request);
					contents.put("userHjhInvistDetail", userHjhInvistDetailCustomize);
					// 依据模板生成内容------旧的协议下载的组建还未做好
					String pdfUrl = pdfGenerator.generateLocal(fileName, CustomConstants.NEW_HJH_INVEST_CONTRACT, contents);
					if (StringUtils.isNotEmpty(pdfUrl)) {
						File path = new File(filePath);
						if (!path.exists()) {
							path.mkdirs();
						}
						FileUtil.getRemoteFile(pdfUrl.substring(0, pdfUrl.length() - 1), filePath + fileName);
					}
					String[] emails = { email };
					
					// 将生成的PDF嵌入邮件模板中
					// 需要产品提供邮件模板????---- CustomConstants.EMAILPARAM_TPL_LOANS
					// mod by nxl 智投服务 汇计划出借服务协议->智投服务协议
//					MailMessage message = new MailMessage(Integer.valueOf(userid), msg, "汇盈金服互联网金融服务平台汇计划出借服务协议",null, new String[] { filePath + fileName }, emails, CustomConstants.HJD_JOIN_AGREEMENT, MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS);
					MailMessage message = new MailMessage(Integer.valueOf(userid), msg, "汇盈金服互联网金融服务平台智投服务协议",null, new String[] { filePath + fileName }, emails, CustomConstants.HJD_JOIN_AGREEMENT, MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS);
					// 发送邮件
					commonProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(),message));
					// 邮件发送成功后修改DB发邮件的状态
					// 邮件发送成功后修改DB发邮件的状态
					request.setAccedeOrderIdSrch(planOrderId);
					request.setDebtPlanNidSrch(debtPlanNid);
					int flg = this.accedeListService.updateSendStatusByParam(request);
					if(flg> 0 ){
						return "发送状态已修改";
					}
					/*return null;*/
				}
			} else {
				_log.info("计划信息异常（0条或者大于1条信息）,下载汇计划出借计划服务协议协议PDF失败");
				return "计划信息异常（0条或者大于1条信息）,下载汇计划出借计划服务协议协议PDF失败";
			}
		} catch (Exception e) {
			_log.info(AccedeListController.class.getName(), "sendMail", e);
			return "系统异常";
		}
		return "";
    }
    
	/**
	 * 带参跳转出借明细列表初始化下拉菜单   直接带参数请求汇直投的出借明细接口
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
/*	@SuppressWarnings("unchecked")
	@ApiOperation(value = "汇计划加入明细列表", notes = "跳转出借明细列表初始化以及查询")
	@PostMapping(value = "/tenderinfo")
	@ResponseBody
	public AdminResult tenderInfoAction(HttpServletRequest request, @RequestBody @Valid AccedeListViewRequest viewRequest) {
		// 初始化查询beam
		AccedeListRequest form = new AccedeListRequest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
        //查询类赋值
        BorrowInvestRequest borrowInvestRequest = new BorrowInvestRequest();
        if(StringUtils.isNotEmpty(form.getAccedeOrderIdSrch())  && StringUtils.isNotEmpty(form.getDebtPlanNidSrch())){
    		// 传 汇计划加入订单号
            borrowInvestRequest.setAccedeOrderIdSrch(form.getAccedeOrderIdSrch());
    		// 传 计划编号
            borrowInvestRequest.setPlanNidSrch(form.getDebtPlanNidSrch());
        }
        BorrowInvestResponseBean responseBean = borrowInvestService.getBorrowInvestList(borrowInvestRequest);
        //还款方式
        List<BorrowStyleVO> borrowStyleList = adminCommonService.selectBorrowStyleList();
        responseBean.setBorrowStyleList(borrowStyleList);
        //操作平台
        Map<String, String> clientList = adminCommonService.getParamNameMap("CLIENT");
        responseBean.setClientList(clientList);
        //出借方式
        Map<String, String> investTypeList = adminCommonService.getParamNameMap("INVEST_TYPE");
        responseBean.setInvestTypeList(investTypeList);
        return new AdminResult(responseBean);
	}*/

	/**
	 * PDF脱敏图片预览     已测试
	 * @param request
	 * @return
	 */
    @ApiOperation(value = "汇计划加入明细列表", notes = "PDF脱敏图片预览")
    @PostMapping(value = "/pdfpreview")
    @ResponseBody
    public JSONObject pdfPreviewAction(HttpServletRequest request,@RequestBody @Valid AccedeListViewRequest viewRequest) {
    	JSONObject jsonObject = new JSONObject();
    	TenderAgreementVO tenderAgreementVO;
    	String nid = null ;
    	AccedeListRequest form = new AccedeListRequest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
    	if(StringUtils.isNotEmpty(form.getAccedeOrderIdSrch())){
    		nid = form.getAccedeOrderIdSrch();
    	} else {
    		jsonObject.put("error","未传入加入订单号");
    	}
    	List<TenderAgreementVO> tenderAgreementList = this.accedeListService.selectTenderAgreementByNid(nid);
    	if(tenderAgreementList != null && tenderAgreementList.size()>0){
    		tenderAgreementVO = tenderAgreementList.get(0);
    		if(StringUtils.isNotBlank(tenderAgreementVO.getImgUrl())){
    			String imgUrl = tenderAgreementVO.getImgUrl();
    			String[] imgs = imgUrl.split(";");
    			List<String> imgList = Arrays.asList(imgs);
    			jsonObject.put("imgList",imgList);
/*    			// 文件服务器
    			String fileDomainUrl = systemConfig.getFtpurl() + systemConfig.getFtpbasepathimg();
    			jsonObject.put("fileDomainUrl",fileDomainUrl);*/
    		}
    	}
    	return jsonObject;
    }
    
	/**
	 * PDF文件签署
	 * @param request
	 * @return
	 * @throws MQException 
	 */
	@ApiOperation(value = "汇计划-计划订单PDF签署", notes = "汇计划-计划订单PDF签署")
    @PostMapping(value = "/pdfsign")
    @ResponseBody
    public JSONObject pdfSignAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AccedeListViewRequest viewRequest) throws MQException {
    	JSONObject ret = new JSONObject();
    	TenderAgreementVO tenderAgreement;
    	AccedeListCustomizeVO accede = null;
    	AccedeListRequest form = new AccedeListRequest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		// 用户ID
		String userid = String.valueOf(viewRequest.getUserId());
		// 计入加入订单号
		String planOrderId = viewRequest.getAccedeOrderIdSrch();
		// 参数判断
		if(StringUtils.isBlank(userid) || StringUtils.isBlank(planOrderId)){
			ret.put("statusDesc", "请求参数为空");
			ret.put("status", FAIL);
			return ret;
		}
		form.setAccedeOrderIdSrch(planOrderId);
		List<AccedeListCustomizeVO> resultList = this.accedeListService.getAccedeListByParamWithoutPage(form);
		if (CollectionUtils.isNotEmpty(resultList)) {
			if(resultList.get(0) != null){
				accede = resultList.get(0);
			} else {
				ret.put("statusDesc", "用户加入记录不存在");
				ret.put("status", FAIL);
				return ret;
			}
		} else {
			ret.put("statusDesc", "用户加入记录不存在");
			ret.put("status", FAIL);
			return ret;
		}
    	UserVO users = this.accedeListService.getUserByUserId(Integer.valueOf(userid));
		if(users == null ){
			ret.put("statusDesc", "用户不存在");
			ret.put("status", FAIL);
			return ret;
		}
		//modify by cwyang 2019-1-9 没有协议的情况下去生成协议而不是报错返回
		List<TenderAgreementVO> tenderAgreementList = this.accedeListService.selectTenderAgreementByNid(planOrderId);
		if(CollectionUtils.isEmpty(tenderAgreementList)){
			FddGenerateContractBeanVO bean = new FddGenerateContractBeanVO();
			bean.setOrdid(planOrderId);
			bean.setTenderUserId(users.getUserId());
			bean.setTenderUserName(users.getUsername());
			bean.setTransType(FddGenerateContractConstant.PROTOCOL_TYPE_PLAN);
			bean.setTenderType(2);
			bean.setSignDate(accede.getCountInterestTime());
			bean.setPlanStartDate(accede.getCountInterestTime());
			bean.setPlanEndDate(GetDate.getDateMyTimeInMillis(Integer.valueOf(accede.getQuitTime())));
			bean.setTenderInterestFmt(String.valueOf(accede.getWaitTotal()));
			// 法大大生成合同接口
			/*this.rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_GENERATE_CONTRACT, JSONObject.toJSONString(bean));*/
			_log.info("==========汇计划-计划订单PDF签署发送法大大MQ(生成合同)=========");
			commonProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC,MQConstant.FDD_GENERATE_CONTRACT_TAG, UUID.randomUUID().toString(), bean));
			ret.put("statusDesc", "操作成功,签署MQ已发送");
			ret.put("status", SUCCESS);
			return ret;
		}
		tenderAgreement = tenderAgreementList.get(0);
		if(tenderAgreement != null && tenderAgreement.getStatus() == 2){
			// PDF下载加脱敏
			_log.info("==========汇计划-计划订单PDF签署发送法大大MQ(PDF下载脱敏)=========");
			this.accedeListService.updateSaveSignInfo(tenderAgreement, "", FddGenerateContractConstant.PROTOCOL_TYPE_PLAN, accede.getDebtPlanNid());
		}
		ret.put("statusDesc", "操作成功,签署MQ已发送");
		ret.put("status", SUCCESS);
		return ret;
    }
}
