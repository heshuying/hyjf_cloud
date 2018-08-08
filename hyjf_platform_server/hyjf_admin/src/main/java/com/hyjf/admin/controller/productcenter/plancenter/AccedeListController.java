/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter.plancenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.AccedeListViewRequest;
import com.hyjf.admin.beans.response.BorrowInvestResponseBean;
import com.hyjf.admin.beans.vo.AdminAccedeListCustomizeVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.mq.FddProducer;
import com.hyjf.admin.mq.MailProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.AccedeListService;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.BorrowInvestService;
import com.hyjf.admin.service.PlanListService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AccedeListResponse;
import com.hyjf.am.resquest.admin.AccedeListRequest;
import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.resquest.admin.PlanListRequest;
import com.hyjf.am.vo.fdd.FddGenerateContractBeanVO;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.trade.hjh.AccedeListCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeSumVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanDetailVO;
import com.hyjf.am.vo.trade.hjh.UserHjhInvistDetailVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.FddGenerateContractConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.file.FileUtil;
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
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.net.URLEncoder;
import java.util.*;

/*import com.hyjf.admin.utils.PdfGenerator;*/
/**
 * @author libin
 * @version AccedeListController.java, v0.1 2018年7月7日 下午3:00:56
 */
@Api(value = "汇计划加入明细列表",tags = "汇计划加入明细列表")
@RestController
@RequestMapping("/hyjf-admin/joinplan")
public class AccedeListController extends BaseController{
	
	@Autowired
	private AccedeListService accedeListService;
	@Autowired 
	private PlanListService planListService;
    @Autowired  
    BorrowInvestService borrowInvestService;
    @Autowired
    AdminCommonService adminCommonService;
	@Autowired
	private FddProducer fddProducer;
	@Autowired
	private MailProducer mailProducer;
	@Autowired
	private SystemConfig systemConfig;
/*	@Autowired
	private PdfGenerator pdfGenerator;*/
	
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
			jsonObject.put("sumAlreadyInvest", sumVO.getSumAlreadyInvest());
			jsonObject.put("sumWaitTotal", sumVO.getSumWaitTotal());
			jsonObject.put("sumWaitCaptical", sumVO.getSumWaitCaptical());
			jsonObject.put("sumWaitInterest", sumVO.getSumWaitInterest());
			jsonObject.put("status", SUCCESS);
		} else {
			jsonObject.put("msg", "查询为空");
			jsonObject.put("status", FAIL);
		}
		return jsonObject;
	}
    
	/**
	 * 导出功能
	 * 
	 * @param request
	 * @param modelAndView
	 * @param form
	 */
    @ApiOperation(value = "汇计划加入明细列表", notes = "汇计划加入明细列表导出")
    @PostMapping(value = "/export")
    @ResponseBody
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AccedeListViewRequest viewRequest) throws Exception {
		// 表格sheet名称
		String sheetName = "加入明细";
		@SuppressWarnings("deprecation")
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		String[] titles = new String[] { "序号","加入订单号", "计划编号","锁定期", "预定利率","用户名","投资人id","投资人用户属性（当前)", "分公司(当前)", "部门(当前)", "团队(当前)","推荐人（当前）","推荐人ID（当前）","推荐人姓名（当前）", "推荐人用户属性（当前）", "分公司(当前)", "部门(当前)", "团队(当前)", "投资人用户属性（投资时）","推荐人(投资时)", "推荐人ID（投资时）", "推荐人姓名（投资时）", "推荐人用户属性(投资时)", "分公司(投资时)", "部门(投资时)", "团队(投资时)", "加入金额", "已投资金额(元)","待还总额(元) ","待还本金(元) ","待还利息(元) ","操作平台","订单状态",  "计息时间", "加入时间" };
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
					// 锁定期
					else if (celLength == 3) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getDebtLockPeriod()) ? StringUtils.EMPTY : planAccedeDetail.getDebtLockPeriod() + "天");
					}
					// 预定利率
					else if (celLength == 4) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getExpectApr()) ? StringUtils.EMPTY : planAccedeDetail.getExpectApr() + "%");
					}
					// 用户名
					else if (celLength == 5) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getUserName()) ? StringUtils.EMPTY : planAccedeDetail.getUserName());
					}
					// 投资人id
					else if (celLength == 6) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getUserId()) ? StringUtils.EMPTY : planAccedeDetail.getUserId());
					}
					// 投资人当前属性
					else if (celLength == 7) {
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
					// 投资人公司
					else if (celLength == 8) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserRegionname2()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserRegionname2());
					}
					// 投资人公司
					else if (celLength == 9) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserBranchname2()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserBranchname2());
					}
					// 投资人公司
					else if (celLength == 10) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserDepartmentname2()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserDepartmentname2());
					}
					// 推荐人（当前）
					else if (celLength == 11) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getRefereeUserName()) ? StringUtils.EMPTY : planAccedeDetail.getRefereeUserName());
					}
					// 推荐人（当前）
					else if (celLength == 12) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getRefereeUserId()) ? StringUtils.EMPTY : planAccedeDetail.getRefereeUserId());
					}
					else if (celLength == 13) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getRefereeTrueName()) ? StringUtils.EMPTY : planAccedeDetail.getRefereeTrueName());
					}
					// 推荐人用户属性（当前）
					else if (celLength == 14) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getRecommendAttr()) ? StringUtils.EMPTY : planAccedeDetail.getRecommendAttr());
					}

					else if (celLength == 15) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserRegionname1()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserRegionname1());
					}
					// 推荐人用户属性（当前）
					else if (celLength == 16) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserBranchname1()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserBranchname1());
					}
					// 推荐人用户属性（当前）
					else if (celLength == 17) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserDepartmentname1()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserDepartmentname1());
					}
					
					// 投资人当前属性
					else if (celLength == 18) {
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
					else if (celLength == 19) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteName()) ? StringUtils.EMPTY : planAccedeDetail.getInviteName());
					}
					else if (celLength == 20) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserId()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserId());
					}
					else if (celLength == 21) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteTrueName()) ? StringUtils.EMPTY : planAccedeDetail.getInviteTrueName());
					}
					// 推荐人用户属性（当前）
					else if (celLength == 22) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserAttributeName()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserAttributeName());
					}
					// 推荐人用户属性（当前）
					else if (celLength == 23) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserRegionname()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserRegionname());
					}
					// 推荐人用户属性（当前）
					else if (celLength == 24) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserBranchname()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserBranchname());
					}
					// 推荐人用户属性（当前）
					else if (celLength == 25) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserDepartmentname()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserDepartmentname());
					}
					// 加入金额
					else if (celLength == 26) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getAccedeAccount()) ? StringUtils.EMPTY : planAccedeDetail.getAccedeAccount());
					}
					// 已投资金额
					else if (celLength == 27) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getAlreadyInvest()) ? StringUtils.EMPTY : planAccedeDetail.getAlreadyInvest());
					}
					// 待收总额
					else if (celLength == 28) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getWaitTotal()) ? StringUtils.EMPTY : planAccedeDetail.getWaitTotal());
					}
					// 待收本金
					else if (celLength == 29) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getWaitCaptical()) ? StringUtils.EMPTY : planAccedeDetail.getWaitCaptical());
					}
					// 待收利息
					else if (celLength == 30) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getWaitInterest()) ? StringUtils.EMPTY : planAccedeDetail.getWaitInterest());
					}

					// 平台
					else if (celLength == 31) {
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
					else if (celLength == 32) {
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
							cell.setCellValue("自动投资异常");
						}
					}
					// 计息时间
					else if (celLength == 33) {
						if (StringUtils.isNotEmpty(planAccedeDetail.getCountInterestTime())) {
							cell.setCellValue(planAccedeDetail.getCountInterestTime());
						}
					}
					// 加入时间
					else if (celLength == 34) {
						if (StringUtils.isNotEmpty(planAccedeDetail.getCreateTime())) {
							cell.setCellValue(planAccedeDetail.getCreateTime());
						}
					}
					// 预期年化
					else if (celLength == 35) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getExpectApr()) ? StringUtils.EMPTY : planAccedeDetail.getExpectApr() + "%");
					}
					// 用户属性（当前）
					else if (celLength == 36) {
						if ("0".equals(planAccedeDetail.getUserAttribute())) {
							cell.setCellValue("无主单");
						} else if ("1".equals(planAccedeDetail.getUserAttribute())) {
							cell.setCellValue("有主单");
						} else if ("2".equals(planAccedeDetail.getUserAttribute())) {
							cell.setCellValue("线下员工");
						} else if ("3".equals(planAccedeDetail.getUserAttribute())) {
							cell.setCellValue("线上员工");
						}
					}
				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
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
			if(resultList.size()>0){
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
		if (msg == null) {
			jsonObject.put("result", "操作完成");
			jsonObject.put("status", "success");
		} else if (!"系统异常".equals(msg)) {
			jsonObject.put("result", msg);
			jsonObject.put("status", "error");
		} else {
			jsonObject.put("result", "异常纪录，请刷新后后重试");
			jsonObject.put("status", "error");
		}
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
			// 向每个投资人发送邮件
			if (Validator.isNotNull(userid) && NumberUtils.isNumber(userid)) {
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
					 _log.info("sendMail", "***************************下载法大大协议--投资orderId:"+planOrderId);
					 _log.info("sendMail", "***************************下载法大大协议--投资pdfUrl:"+tenderAgreement.getImgUrl());
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
				 _log.info("sendMail***************************下载法大大协议--投资filePath:"+filePath + fileName);

                MailMessage mailMessage = new MailMessage(Integer.valueOf(userid), msg, "汇计划投资服务协议", null, new String[] { filePath + "/" + fileName }, emails, CustomConstants.EMAITPL_EMAIL_LOCK_REPAY,
                		MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS);
				// 发送邮件
				mailProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(),JSON.toJSONBytes(mailMessage)));
				// 邮件发送成功后修改DB发邮件的状态
				request.setAccedeOrderIdSrch(planOrderId);
				request.setDebtPlanNidSrch(debtPlanNid);
				int flg = this.accedeListService.updateSendStatusByParam(request);
				if(flg> 0 ){
					return "发送状态已修改";
				}
				return null; 
			} else {
				return "系统异常";
			}
		} catch (Exception e) {
			_log.info(AccedeListController.class.getName(), "sendMail", e);
		}
		return "系统异常";
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
			// 向每个投资人发送邮件
			if (Validator.isNotNull(userid) && NumberUtils.isNumber(userid)) {
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
					/*String pdfUrl = pdfGenerator.generateLocal(fileName, CustomConstants.NEW_HJH_INVEST_CONTRACT, contents);*/
					String pdfUrl = "";
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
					MailMessage message = new MailMessage(Integer.valueOf(userid), msg, "汇盈金服互联网金融服务平台汇计划投资服务协议",null, new String[] { filePath + fileName }, emails, CustomConstants.HJD_JOIN_AGREEMENT, MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS);
					// 发送邮件
					mailProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(),JSON.toJSONBytes(message)));
					// 邮件发送成功后修改DB发邮件的状态
					// 邮件发送成功后修改DB发邮件的状态
					request.setAccedeOrderIdSrch(planOrderId);
					request.setDebtPlanNidSrch(debtPlanNid);
					int flg = this.accedeListService.updateSendStatusByParam(request);
					if(flg> 0 ){
						return "发送状态已修改";
					}
					return null;
				}
			} else {
				System.out.println("计划信息异常（0条或者大于1条信息）,下载汇计划投资计划服务协议协议PDF失败。");
				return "计划信息异常（0条或者大于1条信息）,下载汇计划投资计划服务协议协议PDF失败。";
			}
		} catch (Exception e) {
			_log.info(AccedeListController.class.getName(), "sendMail", e);
		}
		return "系统异常";
    }
    
	/**
	 * 带参跳转投资明细列表初始化下拉菜单   已测试
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "汇计划加入明细列表", notes = "跳转投资明细列表初始化以及查询")
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
        //投资方式
        Map<String, String> investTypeList = adminCommonService.getParamNameMap("INVEST_TYPE");
        responseBean.setInvestTypeList(investTypeList);
        return new AdminResult(responseBean);
	}

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
    			// 文件服务器
    			String fileDomainUrl = systemConfig.getFtpurl() + systemConfig.getFtpbasepathimg();
    			jsonObject.put("fileDomainUrl",fileDomainUrl);
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
	@ApiOperation(value = "汇计划加入明细列表", notes = "PDF文件签署")
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
		String userid = request.getParameter("userId");
		// 计入加入订单号
		String planOrderId = request.getParameter("planOrderId");
		// 参数判断
		if(StringUtils.isBlank(userid) || StringUtils.isBlank(planOrderId)){
			ret.put("result", "请求参数为空");
			ret.put("status", FAIL);
			return ret;
		}
		form.setAccedeOrderIdSrch(planOrderId);
		List<AccedeListCustomizeVO> resultList = this.accedeListService.getAccedeListByParamWithoutPage(form);
		if (CollectionUtils.isNotEmpty(resultList)) {
			if(resultList.get(0) != null){
				accede = resultList.get(0);
			} else {
				ret.put("result", "用户加入记录不存在");
				ret.put("status", FAIL);
				return ret;
			}
		} else {
			ret.put("result", "用户加入记录不存在");
			ret.put("status", FAIL);
			return ret;
		}
		
    	UserVO users = this.accedeListService.getUserByUserId(Integer.valueOf(userid));
		if(users == null ){
			ret.put("result", "用户不存在");
			ret.put("status", FAIL);
			return ret;
		}
		
		List<TenderAgreementVO> tenderAgreementList = this.accedeListService.selectTenderAgreementByNid(planOrderId);
		tenderAgreement = tenderAgreementList.get(0);
		if(tenderAgreement != null && tenderAgreement.getStatus() == 2){
			// PDF下载加脱敏
			this.accedeListService.updateSaveSignInfo(tenderAgreement, "", FddGenerateContractConstant.PROTOCOL_TYPE_PLAN, accede.getDebtPlanNid());
		} else {
			FddGenerateContractBeanVO bean = new FddGenerateContractBeanVO();
			bean.setOrdid(planOrderId);
			bean.setTenderUserId(users.getUserId());
			bean.setTenderUserName(users.getUsername());
			bean.setTransType(FddGenerateContractConstant.PROTOCOL_TYPE_PLAN);
			bean.setTenderType(2);
			bean.setSignDate(accede.getCountInterestTime());
			bean.setPlanStartDate(accede.getCountInterestTime());
			bean.setPlanEndDate(GetDate.getDateMyTimeInMillis(accede.getQuitTime()));
			bean.setTenderInterestFmt(String.valueOf(accede.getWaitTotal()));
			// 法大大生成合同接口
			/*this.rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_GENERATE_CONTRACT, JSONObject.toJSONString(bean));*/
            fddProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC,MQConstant.FDD_GENERATE_CONTRACT_TAG, UUID.randomUUID().toString(), JSON.toJSONBytes(bean)));
		}
		ret.put("result", "操作成功,签署MQ已发送");
		ret.put("status", SUCCESS);
		return ret;
    }
}
