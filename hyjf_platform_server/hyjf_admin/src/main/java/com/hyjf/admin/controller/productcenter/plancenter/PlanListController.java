/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter.plancenter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.HjhPlanCapitalRequestBean;
import com.hyjf.admin.beans.request.PlanListViewRequest;
import com.hyjf.admin.beans.vo.AdminHjhPlanVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.PlanListService;
import com.hyjf.admin.utils.AdminValidatorFieldCheckUtil;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhPlanCapitalResponse;
import com.hyjf.am.response.admin.HjhPlanResponse;
import com.hyjf.am.resquest.admin.HjhPlanCapitalRequest;
import com.hyjf.am.resquest.admin.PlanListRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.HjhPlanCapitalVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanDetailVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanSumVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author libin
 * @version PlanListController.java, v0.1 2018年7月6日 上午9:08:43
 */
@Api(value = "产品中心-汇计划-计划列表",tags = "产品中心-汇计划-计划列表")
@RestController
@RequestMapping("/hyjf-admin/hjhplan")
public class PlanListController extends BaseController{
	
	@Autowired
	private PlanListService planListService;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private CommonProducer commonProducer;
	
    /** 权限 */	
	public static final String PERMISSIONS = "planlist";
	
    /**
     * 画面初始化
     *
     * @param request
     * @return 计划列表         已测试
     */
    @ApiOperation(value = "计划列表", notes = "计划列表初始化")
    @PostMapping(value = "/search")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)  
    public AdminResult<ListResult<AdminHjhPlanVO>> search(HttpServletRequest request, @RequestBody @Valid PlanListViewRequest viewRequest) {
    	// 初始化原子层请求实体
    	PlanListRequest form = new PlanListRequest();
		// 初始化返回LIST
		List<AdminHjhPlanVO> volist = null;
    	// 将画面请求request赋值给原子层 request
    	BeanUtils.copyProperties(viewRequest, form);
    	// 画面检索条件无需初始化 还款方式 endday 和  end
    	// 根据删选条件获取计划列表
    	HjhPlanResponse response = this.planListService.getHjhPlanListByParam(form);
		if(response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		if(CollectionUtils.isNotEmpty(response.getResultList())){
			// 将原子层返回集合转型为组合层集合用于返回 response为原子层 AssetListCustomizeVO，在此转成组合层AdminAssetListCustomizeVO
			volist = CommonUtils.convertBeanList(response.getResultList(), AdminHjhPlanVO.class);
			return new AdminResult<ListResult<AdminHjhPlanVO>>(ListResult.build(volist, response.getCount()));
		} else {
			return new AdminResult<ListResult<AdminHjhPlanVO>>(ListResult.build(volist, 0));
		}
    }
    
	/**
	 * 计划列表开放额度/累积加入/待还总额 累计            已测试
	 *
	 * @param request
	 * @return 
	 */
	@ApiOperation(value = "计划列表", notes = "计划列表开放额度/累积加入/待还总额累计")
	@PostMapping(value = "/sum")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)   
	public JSONObject getSumTotal(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid PlanListViewRequest viewRequest) {
		JSONObject jsonObject = new JSONObject();
    	// 初始化原子层请求实体
    	PlanListRequest form = new PlanListRequest();
    	// 将画面请求request赋值给原子层 request
    	BeanUtils.copyProperties(viewRequest, form);
		HjhPlanSumVO sumVO = this.planListService.getCalcSumByParam(form);
		if(sumVO != null){
			jsonObject.put("sumWaitTotal", sumVO.getSumWaitTotal());
			jsonObject.put("sumOpenAccount", sumVO.getSumOpenAccount());
			jsonObject.put("sumJoinTotal", sumVO.getSumJoinTotal());
			jsonObject.put("status", SUCCESS);
		} else {
			jsonObject.put("msg", "查询为空");
			jsonObject.put("status", FAIL);
		}
		return jsonObject;
	}
	
	
	/**
	 * 计划列表初始化添加计划画面     已测试
	 *
	 * @param request
	 * @return 
	 */
	@ApiOperation(value = "计划列表", notes = "计划列表初始添加计划画面")
	@PostMapping(value = "/addplan")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD, ShiroConstants.PERMISSION_MODIFY}) 
	public JSONObject getAddPlanView(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid PlanListViewRequest viewRequest) {
		JSONObject jsonObject = new JSONObject();
		//添加时---获取还款方式  月/日 无需初始化 end/endday
		//添加时---初始化 webhost 用来在详情画面上传图片
		String webhost = UploadFileUtils.getDoPath(systemConfig.getWebPdfHost()); //具体需要修改  主干是取：hyjf.web.host=http://test.hyjf.com:8080/hyjf-web
		webhost = webhost.substring(0, webhost.length() - 1);
		jsonObject.put("webhost", webhost);
		//修改时---计划编号需要判空(注意 前台在添加时会有计划编号已存在的校验，若果已经存在则报错 画面不能操作(调用存在接口))
		// 请求能到这说明这个操作一定是修改
    	// 初始化原子层请求实体
    	PlanListRequest form = new PlanListRequest();
    	// 将画面请求request赋值给原子层 request
    	BeanUtils.copyProperties(viewRequest, form);

		String planNid = form.getDebtPlanNid();
		if (StringUtils.isNotBlank(planNid) && planNid.length() < 3
				&& !"HJH".equals(planNid.substring(0, 3))) {
			jsonObject.put("error", "智投编号必须以HJH开头");
		}
		if (StringUtils.isNotEmpty(planNid)) {
			List<HjhPlanDetailVO> planList = this.planListService.getHjhPlanDetailByPlanNid(form);
			if (planList != null && planList.size() != 0) {
				HjhPlanDetailVO vo = planList.get(0);
				// 将vo组装到form中
				this.getPlanCommonFiled(form,vo);
				jsonObject.put("content", form);
				jsonObject.put("status", SUCCESS);
			} else {
				// 如果没有查到记录，说明需要修改的这个 计划编号 在DB没有查到记录
				jsonObject.put("error", "智投编号"+ planNid +"不存在");
				jsonObject.put("status", FAIL);
			}
		}
		return jsonObject;
	}

	/**
	 * 将查询的结果铺装到返回实体中
	 * 
	 * @Title getPlanCommonFiled
	 * @param planCommonBean
	 * @param debtPlanWithBLOBs
	 */
	private void getPlanCommonFiled(PlanListRequest form, HjhPlanDetailVO vo) {
		// 计划编号
		form.setDebtPlanNid(this.getValue(String.valueOf(vo.getPlanNid())));
		// 计划名称
		form.setDebtPlanName(this.getValue(String.valueOf(vo.getPlanName())));
		// 还款方式
		form.setBorrowStyle(this.getValue(String.valueOf(vo.getBorrowStyle())));
		// 锁定期
		form.setLockPeriod(this.getValue(String.valueOf(vo.getLockPeriod())));
		// 锁定期天、月
		form.setIsMonth(this.getValue(String.valueOf(vo.getIsMonth())));
		// 预期出借利率
		form.setExpectApr(this.getValue(String.valueOf(vo.getExpectApr())));
		// 最低加入金额
		form.setDebtMinInvestment(this.getValue(String.valueOf(vo.getMinInvestment())));
		// 最高加入金额
		form.setDebtMaxInvestment(this.getValue(String.valueOf(vo.getMaxInvestment())));
		// 出借增量
		form.setDebtInvestmentIncrement(this.getValue(String.valueOf(vo.getInvestmentIncrement())));
		// 可用券配置
		form.setCouponConfig(this.getValue(String.valueOf(vo.getCouponConfig())));
		// 出借状态
		form.setDebtPlanStatus(this.getValue(String.valueOf(vo.getPlanInvestStatus())));
		// 显示状态
		form.setPlanDisplayStatusSrch(this.getValue(String.valueOf(vo.getPlanDisplayStatus())));
		// 计划介绍
		form.setPlanConcept(this.getValue(String.valueOf(vo.getPlanConcept())));
		// 计划原理
		form.setPlanPrinciple(this.getValue(String.valueOf(vo.getPlanPrinciple())));
		// 风控保障措施
		form.setSafeguardMeasures(this.getValue(String.valueOf(vo.getSafeguardMeasures())));
		// 风险保证金措施
		form.setMarginMeasures(this.getValue(String.valueOf(vo.getMarginMeasures())));
		// 常见问题
		form.setNormalQuestion(this.getValue(String.valueOf(vo.getNormalQuestions())));
		// 添加时间
		form.setAddTime(this.getValue(String.valueOf(vo.getAddTime())));
		// 最小出借笔数
		form.setMinInvestCounts(this.getValue(String.valueOf(vo.getMinInvestCounts())));
		// 风险投资等级
		form.setInvestLevel(this.getValue(String.valueOf(vo.getInvestLevel())));
	}
	
	private String getValue(String value) {
		if (StringUtils.isNotEmpty(value)) {
			return value;
		}
		return "";
	}
	
	/**
	 * 计划名称  是否已经存在AJAX    已测试
	 * 
	 * @Title isExistsApplicant
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "计划列表", notes = "计划名称 是否已经存在AJAX")
	@PostMapping(value = "/isdebtplannameexist")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH) 
	public AdminResult<String> planNameAjaxCheck(HttpServletRequest request, @RequestBody @Valid PlanListViewRequest viewRequest) {
    	// 初始化原子层请求实体
    	PlanListRequest form = new PlanListRequest();
    	// 将画面请求request赋值给原子层 request
    	BeanUtils.copyProperties(viewRequest, form);
		// 原 String param = request.getParameter("param");  是从 HttpServletRequest 取参数
		String planName = form.getPlanNameSrch();
		// 判空后期有校验
		//form.setPlanNidSrch(planNid);
		form.setPlanNameSrch(planName);
		HjhPlanResponse response = this.planListService.getPlanNameAjaxCheck(form);
		if(response==null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<String>(response.getMessage());
	}
	
	/**
	 * 计划编号  是否已经存在AJAX   已测试
	 * 
	 * @Title isExistsApplicant
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "计划列表", notes = "计划编号 是否已经存在AJAX")
	@PostMapping(value = "/isdebtplannidexist")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH) 
	public AdminResult<String> planNidAjaxCheck(HttpServletRequest request, @RequestBody @Valid PlanListViewRequest viewRequest) {
    	// 初始化原子层请求实体
    	PlanListRequest form = new PlanListRequest();
		// 判空后期有校验
		form.setPlanNidSrch(viewRequest.getPlanNidSrch());
		HjhPlanResponse response = this.planListService.getPlanNidAjaxCheck(form);
		if(response==null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<String>(response.getMessage());
	}
	
	/**
	 * 点击启用/关闭按键     已测试
	 * 
	 * @Title moveToInfoAction
	 * @param request
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "计划列表", notes = "点击启用/关闭按键")
	@PostMapping(value = "/switch")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY) 
	public AdminResult<String> switchAction(HttpServletRequest request, @RequestBody @Valid PlanListViewRequest viewRequest) {
    	// 初始化原子层请求实体
    	PlanListRequest form = new PlanListRequest();
    	// 将画面请求request赋值给原子层 request
    	BeanUtils.copyProperties(viewRequest, form);
		HjhPlanResponse response = new HjhPlanResponse();
		// 计划编码
		String planNid = form.getDebtPlanNid();
		if (StringUtils.isNotEmpty(planNid)) {
			response = this.planListService.updatePlanStatusByPlanNid(form);
		}
		if(response==null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<String>(response.getMessage());	
	}
	
	
	/**
	 * 点击显示/隐藏按键    已测试
	 * 
	 * @Title moveToInfoAction
	 * @param request
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "计划列表", notes = "点击显示/隐藏按键")
	@PostMapping(value = "/display")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
	public AdminResult<String> displayAction(HttpServletRequest request, @RequestBody @Valid PlanListViewRequest viewRequest) {
    	// 初始化原子层请求实体
    	PlanListRequest form = new PlanListRequest();
    	// 将画面请求request赋值给原子层 request
    	BeanUtils.copyProperties(viewRequest, form);
		HjhPlanResponse response = new HjhPlanResponse();
		// 计划编码
		String planNid = form.getDebtPlanNid();
		if (StringUtils.isNotEmpty(planNid)) {
			response = this.planListService.updatePlanDisplayByPlanNid(form);
		}
		if(response==null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<String>(response.getMessage());	
	}
	
	/**
	 * 汇计划添加信息(添加更改共用)
	 * @Title insertAction
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "计划列表", notes = "汇计划添加信息(添加更改共用)")
	@PostMapping(value = "/insert")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
	public JSONObject addNewPlan(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid PlanListViewRequest viewRequest) {
		JSONObject jsonObject = new JSONObject();
		// 获取登录这userid
		int userId = Integer.valueOf(this.getUser(request).getId());
    	// 初始化原子层请求实体
    	PlanListRequest form = new PlanListRequest();
    	// 将画面请求request赋值给原子层 request
    	BeanUtils.copyProperties(viewRequest, form);
		form.setUserid(userId);
		// 计划编码
		String planNid = form.getDebtPlanNid();
		// 可用券配置(如果前台配置为空，优惠券设置为0)
		if(StringUtils.isEmpty(form.getCouponConfig())){
		    form.setCouponConfig("0");
		}
		// 计划编号是否存在( true：存在, false：不存在)
		boolean isExistRecord = StringUtils.isNotEmpty(planNid) && this.planListService.isExistsRecord(planNid);
		// 画面校验
		this.validatorFieldCheck(jsonObject, form, isExistRecord);
		// 如果画面校验出错--->回info画面(添加就是空白，修改就是铺记录值) 调用info画面初始化接口
		if (AdminValidatorFieldCheckUtil.hasValidateError(jsonObject) || jsonObject.containsKey("errorMsg")) {
			this.getAddPlanView(request,response,viewRequest);
		}
		//汇计划二期迭代最高可投金额为空设置默认值为1250000
		if(StringUtils.isEmpty(form.getDebtMaxInvestment())){
			form.setDebtMaxInvestment("1250000");
		}
		if (isExistRecord) {
			// (修改请求)更新操作
			int flg = this.planListService.updateRecord(form);
			if(flg > 0){
				//success();
				jsonObject.put("status", SUCCESS);
				jsonObject.put("statusDesc", SUCCESS_DESC);
				// 智投修改成功发送mq到应急中心上报数据add by nxl
				try {
					JSONObject params = new JSONObject();
					params.put("planNid", planNid);
					commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.HJHPLAN_MODIFY_TAG, UUID.randomUUID().toString(), params),
							MQConstant.HG_REPORT_DELAY_LEVEL);
				} catch (Exception e) {
					logger.error("新加智投发送mq消息到合规数据上报失败！planNid : " + planNid ,e);
				}
			} else {
				//fail("");
				jsonObject.put("status", FAIL);
				jsonObject.put("statusDesc", FAIL_DESC);
			}
		} else {
			// (新增请求)插入操作
			int flag = this.planListService.insertRecord(form);
			if(flag > 0){
				//success();
				jsonObject.put("status", SUCCESS);
				jsonObject.put("statusDesc", SUCCESS_DESC);
				// 新增成功发送mq到互金上报数据add by liushouyi
				try {
					JSONObject params = new JSONObject();
					params.put("planNid", planNid);
					//应急中心二期修改，新增计划修改mq延时10秒（用于和修改智投分开，防止新增完毕立马修改）mod by nxl
					commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.HJHPLAN_ADD_TAG, UUID.randomUUID().toString(), params),
							MQConstant.HG_REPORT_DELAY_LEVEL_TEN);
				} catch (Exception e) {
					logger.error("新加智投发送mq消息到合规数据上报失败！planNid : " + planNid ,e);
				}
			} else {
				//fail("");
				jsonObject.put("status", FAIL);
				jsonObject.put("statusDesc", FAIL_DESC);
			}
		}
		return jsonObject;
	}
	
	/**
	 * 画面校验
	 * 
	 * @Title validatorFieldCheck
	 * @param mav
	 * @param planListBean
	 * @param isExistsRecord
	 */
	private void validatorFieldCheck(JSONObject jsonObject, PlanListRequest form, boolean isExistsRecord) {
		// 计划名称
		if (!isExistsRecord) {
			String planName = form.getDebtPlanName();
			int count = this.planListService.countByPlanName(planName);
			if (count > 0) {
				jsonObject.put("errorMsg", "智投编号重复！");
			}
		}
		// 计划期限+还款方式
		if (!isExistsRecord) {
			String lockPeriod =form.getLockPeriod();
			String isMonth = form.getIsMonth();
			int lockPeriodCount=0;
			if(StringUtils.isEmpty(isMonth)){
				String borrowStyle = form.getBorrowStyle();//(二期使用月时放开)
				lockPeriodCount = this.planListService.isLockPeriodExist(lockPeriod, borrowStyle,isMonth);
			}
			if (lockPeriodCount > 0) {
				AdminValidatorFieldCheckUtil.validateSpecialError(jsonObject, "lockPeriod", "repeat");
				jsonObject.put("errorMsg", "计划期限和还款方式对应的计划已存在！");
			}
		}
		// 还款方式
		if(StringUtils.isEmpty(form.getBorrowStyle())){
			jsonObject.put("errorMsg", "请输入还款方式！");
		}
		// 预期出借利率
		AdminValidatorFieldCheckUtil.validateSignlessNumLength(jsonObject, "expectApr", form.getExpectApr(), 2, 2, true);
		// 原 最低加入金额
		AdminValidatorFieldCheckUtil.validateDecimal(jsonObject, "debtMinInvestment", form.getDebtMinInvestment(), 10, true);
		/*this.validateDecimal(jsonObject,"最低加入金额",form.getDebtMinInvestment(), 10);*/
		// 原 出借增量
		AdminValidatorFieldCheckUtil.validateDecimal(jsonObject, "debtInvestmentIncrement", form.getDebtInvestmentIncrement(), 10, true);
		/*this.validateDecimal(jsonObject,"出借增量",form.getDebtInvestmentIncrement(), 10);*/
		// 可用券配置
		if(StringUtils.isEmpty(form.getCouponConfig())){
			jsonObject.put("errorMsg", "请输入可用券配置！");
		}
		// 出借状态
		if(StringUtils.isEmpty(form.getDebtPlanStatus())){
			jsonObject.put("errorMsg", "请输入出借状态！");
		}
		// 计划介绍
		if(StringUtils.isEmpty(form.getPlanConcept())){
			jsonObject.put("errorMsg", "请输入计划介绍！");
		}
		// 常见问题
		if(StringUtils.isEmpty(form.getNormalQuestion())){
			jsonObject.put("errorMsg", "请输入常见问题！");
		}
	}	
	
	/**
	 * 导出功能
	 * 
	 * @param request
	 * @param form
	 */
    /*@ApiOperation(value = "计划列表", notes = "计划列表导出")
    @PostMapping(value = "/export")
    @ResponseBody
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid PlanListViewRequest viewRequest) throws Exception {
		// 表格sheet名称
		String sheetName = "计划列表";
		String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		String[] titles = new String[] { "序号","计划编号","计划名称", "还款方式","锁定期", "预期出借利率率","最低加入金额（元）","最高加入金额（元）","出借增量（元）", "最小出借笔数", "开放额度（元）", "累计加入金额（元）","待还总额（元）","计划状态","添加时间" };
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
    	// 初始化原子层请求实体
    	PlanListRequest form = new PlanListRequest();
    	BeanUtils.copyProperties(viewRequest, form);
    	// 不带分页的查询
    	HjhPlanResponse res = this.planListService.getHjhPlanListByParamWithoutPage(form);
    	if(CollectionUtils.isNotEmpty(res.getResultList())){
			int sheetCount = 1;
			int rowNum = 0;
			for (int i = 0; i < res.getResultList().size(); i++) {
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
					HjhPlanVO hjhPlan = res.getResultList().get(i);

					// 创建相应的单元格
					Cell cell = row.createCell(celLength);

					// 序号
					if (celLength == 0) {
						cell.setCellValue(i + 1);
					}
					// 计划编号
					else if (celLength == 1) {
						cell.setCellValue(StringUtils.isEmpty(hjhPlan.getPlanNid()) ? StringUtils.EMPTY : hjhPlan.getPlanNid());
					}
					// 计划名称
					else if (celLength == 2) {
						cell.setCellValue(StringUtils.isEmpty(hjhPlan.getPlanName()) ? StringUtils.EMPTY : hjhPlan.getPlanName());
					}
					// 还款方式
					else if (celLength == 3) {
						if ("endday".equals(hjhPlan.getBorrowStyle())) {
							cell.setCellValue("按天计息，到期还本还息");
						} else if ("end".equals(hjhPlan.getBorrowStyle())) {
							cell.setCellValue("按月计息，到期还本还息");
						} else {
							cell.setCellValue(hjhPlan.getBorrowStyle());
						}
					}
					// 锁定期
					else if (celLength == 4) {
						if (hjhPlan.getIsMonth() == 0) {
							cell.setCellValue(hjhPlan.getLockPeriod()+ "天");
						} else if (hjhPlan.getIsMonth() == 1) {
							cell.setCellValue(hjhPlan.getLockPeriod()+ "个月");
						}
					}
					// 预期出借利率率
					else if (celLength == 5) {
						cell.setCellValue( hjhPlan.getExpectApr() + "%");
					}
					
					// 最低加入金额（元）
					else if (celLength == 6) {
						if(hjhPlan.getMinInvestment() != null){
							cell.setCellValue(hjhPlan.getMinInvestment().toString());
						}else{
							cell.setCellValue("0.00");
						}
					}
					// 最高加入金额（元）
					else if (celLength == 7) {
						if(hjhPlan.getMaxInvestment() != null){
							cell.setCellValue(hjhPlan.getMaxInvestment().toString());
						}else{
							cell.setCellValue("0.00");
						}
					}
					// 出借增量（元）
					else if (celLength == 8) {
						if(hjhPlan.getInvestmentIncrement() != null){
							cell.setCellValue(hjhPlan.getInvestmentIncrement().toString());
						}else{
							cell.setCellValue("0.00");
						}
					}
					// 最小出借笔数
					else if (celLength == 9) {
						if(hjhPlan.getMinInvestCounts() != null){
							cell.setCellValue(hjhPlan.getMinInvestCounts().toString());
						}else{
							cell.setCellValue("0.00");
						}
					}
					// 开放额度（元）
					else if (celLength == 10) {
						if(hjhPlan.getAvailableInvestAccount() != null){
							cell.setCellValue(hjhPlan.getAvailableInvestAccount().toString());
						}else{
							cell.setCellValue("0.00");
						}
					}
					// 累计加入金额（元）
					else if (celLength == 11) {
						if(hjhPlan.getJoinTotal() != null){
							cell.setCellValue(hjhPlan.getJoinTotal().toString());
						}else{
							cell.setCellValue("0.00");
						}
					}
					// 待还总额（元）
					else if (celLength == 12) {
						if(hjhPlan.getRepayWaitAll() != null){
							cell.setCellValue(hjhPlan.getRepayWaitAll().toString());
						}else{
							cell.setCellValue("0.00");
						}
					}
					// 计划状态
					else if (celLength == 13) {
						if (hjhPlan.getPlanInvestStatus() == 1) {
							cell.setCellValue("启用");
						} else if (hjhPlan.getPlanInvestStatus() == 2) {
							cell.setCellValue("关闭");
						}
					}
					// 添加时间
					else if (celLength == 14) {
						if(hjhPlan.getAddTime()!= null){                        
							cell.setCellValue(GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(hjhPlan.getAddTime()));
						}
					}	
				}
			}
    	}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }*/

	/**
	 * 导出功能
	 *
	 * @param request
	 * @param form
	 */
	@ApiOperation(value = "计划列表", notes = "计划列表导出")
	@PostMapping(value = "/export")
	@ResponseBody
	public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid PlanListViewRequest viewRequest) throws Exception {
		//sheet默认最大行数
		int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
		// 表格sheet名称
		String sheetName = "智投管理";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
		DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
		// 初始化原子层请求实体
		PlanListRequest form = new PlanListRequest();
		BeanUtils.copyProperties(viewRequest, form);
		// 不带分页的查询
		List<HjhPlanVO> resultList = null;
		HjhPlanResponse resultResponse = this.planListService.getHjhPlanListByParamWithoutPage(form);
		resultList = resultResponse.getResultList();

		Integer totalCount = resultList.size();

		Map<String, String> beanPropertyColumnMap = buildMap();
		Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
		String sheetNameTmp = sheetName + "_第1页";
		if (totalCount == 0) {

			helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
		}else {
			helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList);
		}

		DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
	}

	private Map<String, String> buildMap() {
		Map<String, String> map = Maps.newLinkedHashMap();
		map.put("planNid", "智投编号");
		map.put("planName", "智投名称");
		map.put("borrowStyle", "还款方式");
		map.put("lockPeriodView", "服务回报期限");
		map.put("expectApr", "参考年回报率");
		map.put("minInvestment", "最低授权服务金额（元）");
		map.put("maxInvestment", "最高授权服务金额（元）");
		map.put("investmentIncrement", "出借增量（元）");
		map.put("minInvestCounts", "最小出借笔数");
		map.put("availableInvestAccount", "开放额度（元）");
		map.put("joinTotal", "累计授权服务金额（元）");
		map.put("repayWaitAll", "待还总额（元）");
		map.put("planInvestStatus", "智投状态");
		map.put("addTime", "添加时间");

		return map;
	}
	private Map<String, IValueFormatter> buildValueAdapter() {
		Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
		IValueFormatter borrowStyleAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				String value = (String) object;
				if ("endday".equals(value)) {
					return "按天计息，到期还本还息";
				} else if ("end".equals(value)) {
					return "按月计息，到期还本还息";
				} else {
					return value;
				}
			}
		};

		IValueFormatter expectAprAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				BigDecimal value = (BigDecimal) object;
				return value.toString() + "%";
			}
		};

		IValueFormatter planInvestStatusAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				Integer value = (Integer) object;
				if (value == 1) {
					return "启用";
				} else if (value == 2) {
					return "关闭";
				}else {
					return "";
				}
			}
		};

		IValueFormatter addTimeAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				Integer value = (Integer) object;
				return GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(value);
			}
		};

		mapAdapter.put("borrowStyle", borrowStyleAdapter);
		mapAdapter.put("expectApr", expectAprAdapter);
		mapAdapter.put("planInvestStatus", planInvestStatusAdapter);
		mapAdapter.put("addTime", addTimeAdapter);
		return mapAdapter;
	}


	public List<Integer> mapToList( Map<String, String> map){
		List<Integer> result = new ArrayList<>();
		Iterator iterator = map.keySet().iterator();
		while(iterator.hasNext()){
			String key =iterator.next().toString();
			result.add(Integer.parseInt(key));
		}
		return result;
	}
}
