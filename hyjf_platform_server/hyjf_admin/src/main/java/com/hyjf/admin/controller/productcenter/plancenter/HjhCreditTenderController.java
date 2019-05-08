/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter.plancenter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.AdminHjhCreditTenderRequest;
import com.hyjf.admin.beans.vo.AdminHjhCreditTenderCustomizeVO;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.AccedeListService;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.HjhCreditTenderService;
import com.hyjf.admin.service.TenderCancelExceptionService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhCreditTenderResponse;
import com.hyjf.am.resquest.admin.HjhCreditTenderRequest;
import com.hyjf.am.vo.fdd.FddGenerateContractBeanVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.hjh.HjhCreditTenderCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhCreditTenderSumVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.FddGenerateContractConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author libin
 * @version HjhCreditTenderController.java, v0.1 2018年7月11日 下午2:18:37
 */
@Api(value = "产品中心-汇计划-承接记录",tags = "产品中心-汇计划-承接记录")
@RestController
@RequestMapping("/hyjf-admin/hjhcredittender")
public class HjhCreditTenderController extends BaseController{
	
	@Autowired
	private HjhCreditTenderService hjhCreditTenderService;
	@Autowired
	private SystemConfig systemConfig;
    @Autowired
    private AdminCommonService adminCommonService;
    @Autowired
    private AccedeListService accedeListService;
    @Autowired
    private TenderCancelExceptionService tenderCancelExceptionService;
    @Autowired
    private CommonProducer commonProducer;
	
    /** 权限 */
	public static final String PERMISSIONS = "hjhcredittender";
	
	/**
	 * 画面初始化
	 *
	 * @param request
	 * @return 汇计划承接记录列表   已测试
	 */
	@ApiOperation(value = "汇计划承接记录列表初始化", notes = "汇计划承接记录列表初始化")
	@PostMapping(value = "/init")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public JSONObject init(HttpServletRequest request, @RequestBody @Valid HjhCreditTenderRequest form) {
		JSONObject jsonObject = new JSONObject();
		// 初始化下拉菜单
        // 还款方式
        List<DropDownVO> borrowStyleList = adminCommonService.selectBorrowStyleList();
        // 承接方式
		List<DropDownVO> clientList = adminCommonService.getParamNameList("PLAN_ASSIGN_TYPE");
        if(CollectionUtils.isEmpty(borrowStyleList) &&  clientList.isEmpty()){
        	jsonObject.put("status", FAIL);
        } else {
        	jsonObject.put("borrowStyleList", borrowStyleList);
        	jsonObject.put("还款方式下拉菜单", "borrowStyleList");
        	jsonObject.put("clientList", clientList);
        	jsonObject.put("承接方式下拉菜单", "clientList");
        	jsonObject.put("status", SUCCESS);
        }
		return jsonObject;
	}
	
    /**
     * 汇计划承接记录列表查询      已测试
     *
     * @param request
     * @return 汇计划承接记录列表查询       
     */
    @ApiOperation(value = "汇计划承接记录列表查询", notes = "汇计划承接记录列表查询")
    @PostMapping(value = "/search")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<AdminHjhCreditTenderCustomizeVO>> search(HttpServletRequest request, @RequestBody @Valid AdminHjhCreditTenderRequest viewRequest) {
    	// 初始化原子层请求实体
    	HjhCreditTenderRequest form = new HjhCreditTenderRequest();
		// 初始化返回LIST
		List<AdminHjhCreditTenderCustomizeVO> volist = null;
		// 将画面检索参数request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
    	//是否从加入明细列表跳转 1:是 0:否
		if(form.getIsAccedelist()!=1){
		    form.setIsAccedelist(0);
		}
    	// 根据删选条件获取计划列表
    	HjhCreditTenderResponse response = this.hjhCreditTenderService.getHjhCreditTenderListByParam(form);
		if(response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		if(CollectionUtils.isNotEmpty(response.getResultList())){
			// 将原子层返回集合转型为组合层集合用于返回 response为原子层 AssetListCustomizeVO，在此转成组合层AdminAssetListCustomizeVO
			volist = CommonUtils.convertBeanList(response.getResultList(), AdminHjhCreditTenderCustomizeVO.class);
			return new AdminResult<ListResult<AdminHjhCreditTenderCustomizeVO>>(ListResult.build(volist, response.getCount()));
		} else {
			return new AdminResult<ListResult<AdminHjhCreditTenderCustomizeVO>>(ListResult.build(volist, 0));
		}
    }
    
    
	/**
	 * 汇计划承接记录列表 承接本金 垫付利息 实际支付金额 债转服务费 总计   已测试
	 *
	 * @param request
	 * @return 汇计划承接记录列表   
	 */
	@ApiOperation(value = "汇计划承接记录列表", notes = "汇计划承接记录列表 承接本金 垫付利息 实际支付金额 债转服务费 总计")
	@PostMapping(value = "/sum")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)   
	public JSONObject getSumTotal(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AdminHjhCreditTenderRequest viewRequest) {
		JSONObject jsonObject = new JSONObject();
		// 初始化原子层请求实体
    	HjhCreditTenderRequest form = new HjhCreditTenderRequest();
    	// 将画面请求request赋值给原子层 request
    	BeanUtils.copyProperties(viewRequest, form);
    	HjhCreditTenderSumVO sumVO = this.hjhCreditTenderService.getCalcSumByParam(form);
    	if(sumVO != null){
    		// 承接本金总计
    		jsonObject.put("sumAssignCapital", sumVO.getSumAssignCapital());
    		// 承接利息总计
    		jsonObject.put("sumAssignInterestAdvance", sumVO.getSumAssignInterestAdvance());
    		// 支付金额总计
    		jsonObject.put("sumAssignPay", sumVO.getSumAssignPay());
    		// 服务费总计
    		jsonObject.put("sumAssignServiceFee", sumVO.getSumAssignServiceFee());
    		jsonObject.put("status", SUCCESS);
    	} else {
			jsonObject.put("msg", "查询为空");
			jsonObject.put("status", SUCCESS);
		}
    	return jsonObject;
	}
    
    /**
     * 导出功能
     *
     * @param request
     * @return 汇计划承接记录列表查询        已测试
     */
    /*@ApiOperation(value = "汇计划承接记录列表", notes = "汇计划承接记录列表导出")
    @PostMapping(value = "/export")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AdminHjhCreditTenderRequest viewRequest) throws Exception {
		// 表格sheet名称
		String sheetName = "汇添金计划承接记录";
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		String[] titles = new String[] { "序号","承接人","承接计划编号","承接计划订单号","出让人","债转编号","原项目编号","还款方式","承接本金","垫付利息","实际支付金额"," 承接时间","承接方式","项目总期数","承接时所在期数"};
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
		
		HjhCreditTenderRequest form = new HjhCreditTenderRequest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		List<HjhCreditTenderCustomizeVO> resultList = this.hjhCreditTenderService.getHjhCreditTenderListByParamWithOutPage(form);
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
					HjhCreditTenderCustomizeVO debtCreditTender = resultList.get(i);

					// 创建相应的单元格
					Cell cell = row.createCell(celLength);
					// 序号
					if (celLength == 0) {
						cell.setCellValue(i + 1);
					}
					// 承接人
					else if (celLength == 1) {
						cell.setCellValue(debtCreditTender.getAssignUserName());
					}

					// 承接计划编号
					else if (celLength == 2) {
						cell.setCellValue(debtCreditTender.getAssignPlanNid());
					}
					// 承接计划订单号
					else if (celLength == 3) {
						cell.setCellValue(debtCreditTender.getAssignOrderId());
					}
					// 出让人
					else if (celLength == 4) {
						cell.setCellValue(debtCreditTender.getCreditUserName());
					}
					// 债转编号
					else if (celLength == 5) {
						cell.setCellValue(debtCreditTender.getCreditNid());
					}
					// 原项目编号
					else if (celLength == 6) {
						cell.setCellValue(debtCreditTender.getBorrowNid());
					}
					// 还款方式
					else if (celLength == 7) {
						cell.setCellValue(debtCreditTender.getRepayStyleName());
					}
					// 承接本金
					else if (celLength == 8) {
						cell.setCellValue(debtCreditTender.getAssignCapital());
					}
					// 垫付利息
					else if (celLength == 9) {
						cell.setCellValue(debtCreditTender.getAssignInterestAdvance());
					}
					// 实际支付金额
					else if (celLength == 10) {
						cell.setCellValue(debtCreditTender.getAssignPay());
					}
					// 承接时间
					else if (celLength == 11) {
						cell.setCellValue(debtCreditTender.getAssignTime());
					}
					// 债转服务费率
					else if (celLength == 12){
						cell.setCellValue(debtCreditTender.getAssignServiceApr());
					}
					// 债转服务费
					else if (celLength == 13){
						cell.setCellValue(debtCreditTender.getAssignServiceFee());
					}
					*//*updte  by  zhangyk 修改导出数据表格 start*//*
					// 是否复投承接
					*//*else if (celLength == 14){
						cell.setCellValue(debtCreditTender.getTenderType());
					}*//*

					// 承接方式
					else if (celLength == 14) {
						cell.setCellValue(debtCreditTender.getTenderType());
					}
					// 项目总期数
					else if (celLength == 15) {
						cell.setCellValue(debtCreditTender.getBorrowPeriod());
					}
					// 承接时所在期数
					else if (celLength == 16) {
						cell.setCellValue(debtCreditTender.getAssignPeriod());
					}
					*//*updte  by  zhangyk 修改导出数据表格 end*//*
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
	 * @return 汇计划承接记录列表查询        已测试
	 */
	@ApiOperation(value = "汇计划承接记录列表导出", notes = "汇计划承接记录列表导出")
	@PostMapping(value = "/export")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
	public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AdminHjhCreditTenderRequest viewRequest) throws Exception {
		//sheet默认最大行数
		int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
		// 表格sheet名称
		String sheetName = "智投服务承接记录";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
		DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
		HjhCreditTenderRequest form = new HjhCreditTenderRequest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		List<HjhCreditTenderCustomizeVO> resultList = this.hjhCreditTenderService.getHjhCreditTenderListByParamWithOutPage(form);

		Integer totalCount = resultList.size();
		int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
		Map<String, String> beanPropertyColumnMap = buildMap();
		Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
		String sheetNameTmp = sheetName + "_第1页";

		if (totalCount == 0) {
			helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
		}

		for (int i = 1; i <= sheetCount; i++) {
			//请求第一页5000条
			form.setPageSize(defaultRowMaxCount);
			form.setCurrPage(i);
			List<HjhCreditTenderCustomizeVO> resultResponse2 = hjhCreditTenderService.paging(form,resultList);
			if (resultResponse2 != null && resultResponse2.size()> 0) {
				sheetNameTmp = sheetName + "_第" + (i) + "页";
				helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  resultResponse2);
			} else {
				break;
			}
		}

		DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
	}

	private Map<String, String> buildMap() {
		Map<String, String> map = Maps.newLinkedHashMap();
		map.put("assignUserName", "承接人");
		map.put("assignPlanNid", "承接智投编号");
		map.put("assignPlanOrderId", "承接智投订单号");
		map.put("assignOrderId", "承接订单号");
        map.put("sellOrderId", "出让人智投订单号");
		map.put("creditUserName", "出让人");
		map.put("creditNid", "债转编号");
		map.put("borrowNid", "原项目编号");
		map.put("repayStyleName", "还款方式");
		map.put("assignCapital", "承接本金");
		map.put("assignInterestAdvance", "垫付利息");
		map.put("assignPay", "实际支付金额");
		map.put("assignTime", "承接时间");
		map.put("assignServiceApr","债转服务费率");
		map.put("assignServiceFee","债转服务费(元)");
		map.put("tenderType", "复投承接(是/否)");
		map.put("periodView", "项目期数");

		return map;
	}

	private Map<String, IValueFormatter> buildValueAdapter() {
		Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
		return mapAdapter;
	}

	
	/**
	 * PDF脱敏图片预览    已测试
	 * @param request
	 * @return
	 */
    @ApiOperation(value = "PDF脱敏图片预览", notes = "PDF脱敏图片预览")
    @PostMapping(value = "/pdfpreview")
    @ResponseBody
    public JSONObject pdfPreviewAction(HttpServletRequest request,@RequestBody @Valid AdminHjhCreditTenderRequest viewRequest) {
    	JSONObject jsonObject = new JSONObject();
    	TenderAgreementVO tenderAgreementVO;
    	String nid = null ;
		HjhCreditTenderRequest form = new HjhCreditTenderRequest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
    	// 此字段的使用在bean中有详细的解释
    	if(StringUtils.isNotEmpty(form.getAssignOrderId())){
    		nid = form.getAssignOrderId();
    	} else {
    		jsonObject.put("error","未传入加入订单号");
    		jsonObject.put("status", FAIL);
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
    			jsonObject.put("status", SUCCESS);
    		}
    	}
    	return jsonObject;
    }
    
	/**
	 * PDF文件签署   已测试
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "PDF文件签署", notes = "PDF文件签署")
    @PostMapping(value = "/pdfsign")
    @ResponseBody
    public JSONObject pdfSignAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AdminHjhCreditTenderRequest viewRequest) throws MQException {
    	JSONObject ret = new JSONObject();
    	TenderAgreementVO tenderAgreement = new TenderAgreementVO();
		HjhCreditTenderRequest form = new HjhCreditTenderRequest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
        // 用户ID
        String userId = form.getUserIdHidden();
        // 标的编号
        String borrowNid = form.getBorrowNidHidden();
        // 承接订单号(相当于nid 跟planorderid无关)
        String assignNid = form.getAssignNidHidden();
        // 债转编号
        String creditNid = form.getCreditNidHidden();
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(borrowNid) || StringUtils.isBlank(assignNid) || StringUtils.isBlank(creditNid)) {
			ret.put("statusDesc", "请求参数为空");
			ret.put("status", FAIL);
			return ret;
        }
        // 查询标的详情
        BorrowAndInfoVO borrow = tenderCancelExceptionService.getBorrowByBorrowNid(borrowNid);
        if (borrow == null) {
            ret.put("statusDesc", "标的不存在");
            ret.put("status", FAIL);
            return ret;
        }
    	UserVO users = this.accedeListService.getUserByUserId(Integer.valueOf(userId));
		if(users == null ){
			ret.put("statusDesc", "获取用户信息异常");
			ret.put("status", FAIL);
			return ret;
		}
		// 获取承接记录
		HjhDebtCreditTenderVO ct = this.hjhCreditTenderService.selectHjhCreditTenderRecord(form);
		if (ct == null) {
            ret.put("statusDesc", "获取承接记录失败");
            ret.put("status", FAIL);
            return ret;
        }
		// 获取出借协议记录
		List<TenderAgreementVO> tenderAgreementList = this.accedeListService.selectTenderAgreementByNid(assignNid);
		if(CollectionUtils.isNotEmpty(tenderAgreementList)){
			tenderAgreement = tenderAgreementList.get(0);
		} else {
            ret.put("statusDesc", "获取协议记录失败");
            ret.put("status", FAIL);
            return ret;
		}
		// 如果签署成功,下载失败
		if (tenderAgreement != null && tenderAgreement.getStatus() == 2) {
			// PDF脱敏加下载处理发送MQ
			this.hjhCreditTenderService.updateSaveSignInfo(tenderAgreement, tenderAgreement.getBorrowNid(), FddGenerateContractConstant.FDD_TRANSTYPE_PLAN_CRIDET, borrow.getInstCode());
		} else {
            FddGenerateContractBeanVO bean = new FddGenerateContractBeanVO();
            bean.setTransType(4);
            bean.setTenderType(3);
            bean.setAssignNid(assignNid);
            bean.setOrdid(assignNid);
            /*rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_GENERATE_CONTRACT, JSONObject.toJSONString(bean));*/
			commonProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC,MQConstant.FDD_GENERATE_CONTRACT_TAG, UUID.randomUUID().toString(), bean));
        }
        ret.put("statusDesc", "操作成功,签署MQ已发送");
        ret.put("status", SUCCESS);
    	return ret;
    	
	}
	
	/**
	 * 运营记录 - 承接明细(计划管理画面跳转过来)
	 * @param request
	 * @param response
	 * @param form
     * @return
     */
/*    @ApiOperation(value = "汇计划承接记录列表", notes = "计划列表运营记录 - 承接明细")
    @PostMapping(value = "/optactionsearch")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<AdminHjhCreditTenderCustomizeVO>> optActionSearch(HttpServletRequest request, @RequestBody @Valid AdminHjhCreditTenderRequest viewRequest) {
    	// 初始化原子层请求实体
    	HjhCreditTenderRequest form = new HjhCreditTenderRequest();
		// 初始化返回LIST
		List<AdminHjhCreditTenderCustomizeVO> volist = null;
		
		// 将画面检索参数request赋值给原子层 request.此处重点是 传  assignPlanNid
		BeanUtils.copyProperties(viewRequest, form);
		
    	// 是否从加入明细列表跳转 1:是 0:否
		if(form.getIsAccedelist()!=1){
		    form.setIsAccedelist(0);
		}
		
		// 是否从债转标的页面调转(1:是)
		if (!"1".equals(viewRequest.getIsOptFlag())){
			form.setAssignTimeStart(GetDate.date2Str(new Date(),new SimpleDateFormat("yyyy-MM-dd")));
		}
    	// 根据删选条件获取计划列表
    	HjhCreditTenderResponse response = this.hjhCreditTenderService.getHjhCreditTenderListByParam(form);
		if(response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		if(CollectionUtils.isNotEmpty(response.getResultList())){
			// 将原子层返回集合转型为组合层集合用于返回 response为原子层 AssetListCustomizeVO，在此转成组合层AdminAssetListCustomizeVO
			volist = CommonUtils.convertBeanList(response.getResultList(), AdminHjhCreditTenderCustomizeVO.class);
			return new AdminResult<ListResult<AdminHjhCreditTenderCustomizeVO>>(ListResult.build(volist, response.getCount()));
		} else {
			return new AdminResult<ListResult<AdminHjhCreditTenderCustomizeVO>>(ListResult.build(volist, 0));
		}
    }*/
}
