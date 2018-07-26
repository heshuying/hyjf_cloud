/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter.plancenter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.hyjf.am.response.Response;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.utils.AdminValidatorFieldCheckUtil;
import com.hyjf.admin.beans.request.PlanListViewRequest;
import com.hyjf.admin.beans.vo.AdminHjhPlanVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.PlanListService;
import com.hyjf.am.response.admin.HjhPlanResponse;
import com.hyjf.am.resquest.admin.PlanListRequest;
import com.hyjf.am.vo.trade.hjh.HjhPlanDetailVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanSumVO;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.CommonUtils;

import org.springframework.beans.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author libin
 * @version PlanListController.java, v0.1 2018年7月6日 上午9:08:43
 */
@Api(value = "计划列表",description = "计划列表")
@RestController
@RequestMapping("/hyjf-admin/hjhplan")
public class PlanListController extends BaseController{
	
	@Autowired
	private PlanListService planListService;
	@Autowired
	private SystemConfig systemConfig;
	
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
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD) 
	public JSONObject getAddPlanView(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid PlanListViewRequest viewRequest) {
		JSONObject jsonObject = new JSONObject();
		//添加时---获取还款方式  月/日 无需初始化 end/endday
		//添加时---初始化 webhost 用来在详情画面上传图片
		String webhost = UploadFileUtils.getDoPath(systemConfig.getWebHost()); //具体需要修改
		webhost = webhost.substring(0, webhost.length() - 1);
		jsonObject.put("webhost", webhost);
		//修改时---计划编号需要判空(注意 前台在添加时会有计划编号已存在的校验，若果已经存在则报错 画面不能操作(调用存在接口))
		// 请求能到这说明这个操作一定是修改
    	// 初始化原子层请求实体
    	PlanListRequest form = new PlanListRequest();
    	// 将画面请求request赋值给原子层 request
    	BeanUtils.copyProperties(viewRequest, form);
		String planNid = form.getDebtPlanNid();
		if (StringUtils.isNotEmpty(planNid)) {
			List<HjhPlanDetailVO> planList = this.planListService.getHjhPlanDetailByPlanNid(form);
			/*List<HjhPlanVO> planList = this.allocationEngineService.getHjhPlanByPlanNid(planNid);*/
			if (planList != null && planList.size() != 0) {
				// 说明通过计划编号在DB查到一条记录，那个可以将这条记录返回
				// 根据前台传入的计划编号查询出一个计划，然后把计划的内容拼装在form bean 中返回给前台展示
				this.getPlanInfo(form);
				jsonObject.put("content", viewRequest);
				jsonObject.put("status", SUCCESS);
			} else {
				// 如果没有查到记录，说明需要修改的这个 计划编号 在DB没有查到记录
				jsonObject.put("error", "计划编号"+ planNid +"不存在");
				jsonObject.put("status", FAIL);
			}
		}
		if (StringUtils.isNotBlank(planNid) && planNid.length() < 3
				&& !"HJH".equals(planNid.substring(0, 3))) {
			jsonObject.put("error", "计划编号必须以HJH开头");
		}
		return jsonObject;
	}
	
	/**
	 * 查询和拼装
	 *
	 * @param request
	 * @return 
	 */
	private void getPlanInfo(PlanListRequest form){  //原PlanListBean = PlanListRequest
		// planNid非空 前面已经校验
		List<HjhPlanDetailVO> planList = this.planListService.getHjhPlanDetailByPlanNid(form);
		HjhPlanDetailVO vo = planList.get(0);
		if (vo != null) {
			// 将查询的结果铺装到返回实体中
			this.getPlanCommonFiled(form, vo); // planListBean, hjhPlanWithBLOBs
		}	
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
		// 预期年化收益率
		form.setExpectApr(this.getValue(String.valueOf(vo.getExpectApr())));
		// 最低加入金额
		form.setDebtMinInvestment(this.getValue(String.valueOf(vo.getMinInvestment())));
		// 最高加入金额
		form.setDebtMaxInvestment(this.getValue(String.valueOf(vo.getMaxInvestment())));
		// 投资增量
		form.setDebtInvestmentIncrement(this.getValue(String.valueOf(vo.getInvestmentIncrement())));
		// 可用券配置
		form.setCouponConfig(this.getValue(String.valueOf(vo.getCouponConfig())));
		// 投资状态
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
		//添加时间
		form.setAddTime(this.getValue(String.valueOf(vo.getAddTime())));
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
    	// 将画面请求request赋值给原子层 request
    	BeanUtils.copyProperties(viewRequest, form);
		// 原 String param = request.getParameter("param");  是从 HttpServletRequest 取参数
		String planNid = form.getPlanNidSrch();
		// 判空后期有校验
		//form.setPlanNidSrch(planNid);
		form.setPlanNidSrch(planNid);
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
		if (AdminValidatorFieldCheckUtil.hasValidateError(jsonObject)) {
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
				success();
			} else {
				fail("");
			}
		} else {
			// (新增请求)插入操作
			int flag = this.planListService.insertRecord(form);
			if(flag > 0){
				success();
			} else {
				fail("");
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
				AdminValidatorFieldCheckUtil.validateSpecialError(jsonObject, "debtPlanName", "repeat");
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
			}
		}
		// 还款方式
		AdminValidatorFieldCheckUtil.validateRequired(jsonObject, "borrowStyle", form.getBorrowStyle());
		// 预期年化收益
		AdminValidatorFieldCheckUtil.validateSignlessNumLength(jsonObject, "expectApr", form.getExpectApr(), 2, 2, true);
		// 最低加入金额(只验证数字格式)
		AdminValidatorFieldCheckUtil.validateDecimal(jsonObject, "debtMinInvestment", form.getDebtMinInvestment(), 10, true);
		// 最高加入金额(只验证数字格式)
		/*AdminValidatorFieldCheckUtil.validateDecimal(jsonObject, "debtMaxInvestment", form.getDebtMaxInvestment(), 10, true);*/
		// 投资增量
		AdminValidatorFieldCheckUtil.validateDecimal(jsonObject, "debtInvestmentIncrement", form.getDebtInvestmentIncrement(), 10, true);
		// 可用券配置
		AdminValidatorFieldCheckUtil.validateRequired(jsonObject, "couponConfig", form.getCouponConfig());
		// 投资状态
		AdminValidatorFieldCheckUtil.validateRequired(jsonObject, "debtPlanStatus", form.getDebtPlanStatus());
		// 计划介绍
		AdminValidatorFieldCheckUtil.validateRequired(jsonObject, "planConcept", form.getPlanConcept());
		// 常见问题
		AdminValidatorFieldCheckUtil.validateRequired(jsonObject, "normalQuestion", form.getNormalQuestion());
	}
	
}
