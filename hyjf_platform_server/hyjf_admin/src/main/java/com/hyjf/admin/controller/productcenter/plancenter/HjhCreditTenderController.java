/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter.plancenter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.mq.FddProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.AccedeListService;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.HjhCreditTenderService;
import com.hyjf.admin.service.TenderCancelExceptionService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhCreditTenderResponse;
import com.hyjf.am.resquest.admin.HjhCreditTenderRequest;
import com.hyjf.am.vo.fdd.FddGenerateContractBeanVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhCreditTenderCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.FddGenerateContractConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author libin
 * @version HjhCreditTenderController.java, v0.1 2018年7月11日 下午2:18:37
 */
@Api(value = "汇计划承接记录列表")
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
    private FddProducer fddProducer;
	
    /** 权限 */
	public static final String PERMISSIONS = "hjhcredittender";
	private static final String PARAM_NAME = "hyjf_param_name:";
	
	/**
	 * 画面初始化
	 *
	 * @param request
	 * @return 汇计划承接记录列表   已测试
	 */
	@ApiOperation(value = "汇计划承接记录列表", notes = "汇计划承接记录列表初始化")
	@PostMapping(value = "/init")
	@ResponseBody
	public JSONObject init(HttpServletRequest request, @RequestBody @Valid HjhCreditTenderRequest form) {
		JSONObject jsonObject = new JSONObject();
		// 初始化下拉菜单
        // 还款方式
        List<BorrowStyleVO> borrowStyleList = adminCommonService.selectBorrowStyleList();
        jsonObject.put("borrowStyleList", borrowStyleList);
        // 承接方式
        Map<String, String> clientList = adminCommonService.getParamNameMap(PARAM_NAME + "PLAN_ASSIGN_TYPE");
        jsonObject.put("clientList", clientList);
		return jsonObject;
	}
	
    /**
     * 汇计划承接记录列表查询      已测试
     *
     * @param request
     * @return 汇计划承接记录列表查询       
     */
    @ApiOperation(value = "汇计划承接记录列表", notes = "汇计划承接记录列表查询")
    @PostMapping(value = "/searchAction")
    @ResponseBody
    /*@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW) */  
    public AdminResult<ListResult<HjhCreditTenderCustomizeVO>> search(HttpServletRequest request, @RequestBody @Valid HjhCreditTenderRequest form) {
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
		return new AdminResult<ListResult<HjhCreditTenderCustomizeVO>>(ListResult.build(response.getResultList(), response.getCount())) ;
    }
    
    /**
     * 导出功能
     *
     * @param request
     * @return 汇计划承接记录列表查询        已测试
     */
    @ApiOperation(value = "汇计划承接记录列表", notes = "汇计划承接记录列表导出")
    @PostMapping(value = "/exportAction")
    @ResponseBody
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid HjhCreditTenderRequest form) throws Exception {
		// 表格sheet名称
		String sheetName = "汇添金计划承接记录";
		String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		String[] titles = new String[] { "序号","承接人","承接计划编号","承接计划订单号","出让人","债转编号","原项目编号","还款方式","承接本金","垫付利息","实际支付金额"," 承接时间","承接方式","项目总期数","承接时所在期数"};
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
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
						cell.setCellValue(debtCreditTender.getAssignOrderId());
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
					// 承接方式
					else if (celLength == 12) {
						cell.setCellValue(debtCreditTender.getAssignTypeName());
					}
					// 项目总期数
					else if (celLength == 13) {
						cell.setCellValue(debtCreditTender.getBorrowPeriod());
					}
					// 承接时所在期数
					else if (celLength == 14) {
						cell.setCellValue(debtCreditTender.getAssignPeriod());
					}
				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
	
	/**
	 * PDF脱敏图片预览
	 * @param request
	 * @return
	 */
    @ApiOperation(value = "汇计划承接记录列表", notes = "PDF脱敏图片预览")
    @PostMapping(value = "/pdfPreviewAction")
    @ResponseBody
    public JSONObject pdfPreviewAction(HttpServletRequest request,@RequestBody @Valid HjhCreditTenderRequest form) {
    	JSONObject jsonObject = new JSONObject();
    	TenderAgreementVO tenderAgreementVO;
    	String nid = null ;
    	// 此字段的使用在bean中有详细的解释
    	if(StringUtils.isNotEmpty(form.getAssignOrderId())){
    		nid = form.getAssignOrderId();
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
	 */
	@ApiOperation(value = "汇计划承接记录列表", notes = "PDF文件签署")
    @PostMapping(value = "/pdfSignAction")
    @ResponseBody
    public JSONObject pdfSignAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid HjhCreditTenderRequest form) throws MQException {
    	JSONObject ret = new JSONObject();
    	TenderAgreementVO tenderAgreement;
        // 用户ID
        String userId = form.getUserIdHidden();
        // 标的编号
        String borrowNid = form.getBorrowNidHidden();
        // 承接订单号(相当于nid 跟planorderid无关)
        String assignNid = form.getAssignNidHidden();
        // 债转编号
        String creditNid = form.getCreditNidHidden();
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(borrowNid) || StringUtils.isBlank(assignNid) || StringUtils.isBlank(creditNid)) {
			ret.put("result", "请求参数为空");
			ret.put("status", "error");
			return ret;
        }
        // 查询标的详情
        BorrowVO borrow = tenderCancelExceptionService.getBorrowByBorrowNid(borrowNid);
        if (borrow == null) {
            ret.put("result", "标的不存在");
            ret.put("status", "error");
            return ret;
        }
    	UserVO users = this.accedeListService.getUserByUserId(Integer.valueOf(userId));
		if(users == null ){
			ret.put("result", "获取用户信息异常");
			ret.put("status", "error");
			return ret;
		}
		// 获取承接记录
		HjhDebtCreditTenderVO ct = this.hjhCreditTenderService.selectHjhCreditTenderRecord(form);
		if (ct == null) {
            ret.put("result", "获取承接记录失败");
            ret.put("status", "error");
            return ret;
        }
		// 获取投资协议记录
		List<TenderAgreementVO> tenderAgreementList = this.accedeListService.selectTenderAgreementByNid(assignNid);
		tenderAgreement = tenderAgreementList.get(0);
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
            fddProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC,MQConstant.FDD_GENERATE_CONTRACT_TAG, UUID.randomUUID().toString(), JSON.toJSONBytes(bean)));
        }
        ret.put("result", "操作成功,签署MQ已发送");
        ret.put("status", "success");
    	return ret;
    	
	}
}