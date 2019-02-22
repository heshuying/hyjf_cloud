package com.hyjf.admin.controller.borrow;

import java.io.File;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowCommonService;
import com.hyjf.admin.service.CustomerTransferService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.bean.commonimage.BorrowCommonImage;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BorrowCommonResponse;
import com.hyjf.am.response.admin.BorrowCustomizeResponse;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.resquest.admin.BorrowBeanRequest;
import com.hyjf.am.resquest.admin.BorrowCommonRequest;
import com.hyjf.am.vo.admin.BorrowCustomizeVO;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowCommonCarVO;
import com.hyjf.am.vo.trade.borrow.BorrowCommonCompanyAuthenVO;
import com.hyjf.am.vo.trade.borrow.BorrowCommonVO;
import com.hyjf.am.vo.trade.borrow.BorrowHousesVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author dongzeshan
 * @version V1.0  
 * @package com.hyjf.admin.maintenance.AdminPermissions
 * @date 2015/07/09 17:00
 */

@Api(value = "产品中心-借款增加",tags ="产品中心-借款增加")
@RestController
@RequestMapping("/hyjf-admin/borrow/borrowcommon")
public class BorrowCommonController extends BaseController {
	/** 查看权限 */
	public static final String PERMISSIONS = "borrow";
	@Autowired
	private BorrowCommonService borrowCommonService;
	@Autowired
	private  CustomerTransferService  customerTransferService;
    /**
     * 引入配置文件
     */
    @Autowired
    private SystemConfig systemConfig;

	/**
     * 迁移到详细画面
     *
     * @param borrowCommonRequest
     * @return
     */
	@ApiOperation(value = "查询信息")
	@PostMapping("/infoAction")
    public AdminResult<BorrowCommonResponse> moveToInfoAction(@RequestBody @Valid BorrowCommonRequest borrowCommonRequest) {
		BorrowCommonResponse bcr=borrowCommonService.moveToInfoAction(borrowCommonRequest);
		if(bcr==null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(bcr)) {
			return new AdminResult<>(FAIL, bcr.getMessage());

		}
		bcr.setHousesTypeList(customerTransferService.searchParamNameList(CustomConstants.HOUSES_TYPE));
		bcr.setCompanySizeList(customerTransferService.searchParamNameList(CustomConstants.COMPANY_SIZE));
		bcr.setLevelList(customerTransferService.searchParamNameList(CustomConstants.BORROW_LEVEL));
		bcr.setCurrencyList(customerTransferService.searchParamNameList(CustomConstants.CURRENCY_STATUS));
		bcr.setLinkList(borrowCommonService.getLinks().getResultList());
		Map<String ,String> financePurposeMap = CacheUtil.getParamNameMap(CustomConstants.FINANCE_PURPOSE);
		bcr.setFinancePurposeList(borrowCommonService.mapToParamNameVO(financePurposeMap));
		Map<String ,String> positionMap = CacheUtil.getParamNameMap(CustomConstants.POSITION);
		bcr.setPositionList(borrowCommonService.mapToParamNameVO(positionMap));

		return new AdminResult<BorrowCommonResponse>(bcr);
    }

	/**
	 * 添加信息
	 *
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "插入")
	@PostMapping("/insertAction")
	public  AdminResult<BorrowCommonResponse> insertAction(@RequestBody  BorrowCommonRequest borrowCommonRequest,HttpServletRequest request) throws Exception {
		borrowCommonRequest.setAdminId(Integer.valueOf(this.getUser(request).getId()));
		borrowCommonRequest.setAdminUsername(this.getUser(request).getUsername());
		BorrowCommonResponse bcr = borrowCommonService.insertAction(borrowCommonRequest);
//		if(bcr==null) {
//			return new AdminResult<>(FAIL, FAIL_DESC);
//		}
		if (!Response.isSuccess(bcr)) {
			if(bcr.getRtn().equals("1")) {
				return new AdminResult<>("98", bcr.getMessage());
			}else {
				return new AdminResult<>(FAIL, bcr.getMessage());
			}
			

		}
		bcr.setHousesTypeList(customerTransferService.searchParamNameList(CustomConstants.HOUSES_TYPE));
		bcr.setCompanySizeList(customerTransferService.searchParamNameList(CustomConstants.COMPANY_SIZE));
		bcr.setLevelList(customerTransferService.searchParamNameList(CustomConstants.BORROW_LEVEL));
		bcr.setCurrencyList(customerTransferService.searchParamNameList(CustomConstants.CURRENCY_STATUS));
		bcr.setLinkList(borrowCommonService.getLinks().getResultList());
		Map<String ,String> financePurposeMap = CacheUtil.getParamNameMap(CustomConstants.FINANCE_PURPOSE);
		bcr.setFinancePurposeList(borrowCommonService.mapToParamNameVO(financePurposeMap));
		Map<String ,String> positionMap = CacheUtil.getParamNameMap(CustomConstants.POSITION);
		bcr.setPositionList(borrowCommonService.mapToParamNameVO(positionMap));
	
		return new AdminResult<BorrowCommonResponse>(bcr);
	}


	/**
	 * 用户是否存在
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "检查用户Id")
	@PostMapping("/isExistsUser")
	public AdminResult isExistsUser(@RequestBody @Valid Map<String, String> name) {

		int usersFlag=this.borrowCommonService.isExistsUser(name.get("userName"));
		logger.info("usersFlag is :{}", usersFlag);
		if (usersFlag == 1) {
			return new AdminResult<>(FAIL, "该用户不存在");
		} else if (usersFlag == 2) {
			return new AdminResult<>(FAIL, "用户未开户");
		} else if (usersFlag == 3) {
			return new AdminResult<>(FAIL, "该用户已被禁用");
		} else if (usersFlag == 4) {
			return new AdminResult<>(FAIL, "该用户不是借款人");
		}else if (usersFlag == 5) {
			// 服务费授权
			return new AdminResult<>(FAIL, "未进行服务费授权");
		} else if (usersFlag == 6) {
			// 还款授权
			return new AdminResult<>(FAIL, "未进行还款授权");
		} else if (usersFlag == 7) {
			// 还款授权和服务费授权
			return new AdminResult<>(FAIL, "未进行服务费授权和还款授权");
		}

		return new AdminResult();
	}

	/**
	 * 项目申请人是否存在
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "项目申请人是否存在")
	@PostMapping("/isExistsApplicant")
	public AdminResult isExistsApplicant(@RequestBody @Valid   String applicant) {
		  AdminSystemResponse bcs = this.borrowCommonService.isExistsApplicant(applicant);
			if (!Response.isSuccess(bcs)) {
				return new AdminResult<>(FAIL, "项目申请人不存在");

			}
			return new AdminResult();
	}



	@ApiOperation(value = " 获取标的风险投资等级")
	@PostMapping("/getBorrowLevelAction")
	public AdminResult<String> getBorrowLevelAction(@RequestBody @Valid Map<String, String> investLevel) {
		String borrowLevel = borrowCommonService.getBorrowLevelAction(investLevel.get("investLevel"));
		return new AdminResult<>(borrowLevel);

	}
//	/**
//	 * 验证发标金额是否合法
//	 *
//	 * @param request
//	 * @return
//	 */
	@ApiOperation(value = "验证发标金额是否合法")
	@PostMapping("/isAccountLegal")
	public AdminResult isAccountLegal(@RequestBody @Valid Map<String, String> accountm) {
		String accountM=accountm.get("accountM");
		BigDecimal account = new BigDecimal(accountM);
		BigDecimal increaseMoney = new BigDecimal("100");
		if (account.compareTo(increaseMoney) < 0) {
			// 发标金额应大于递增金额
			return new AdminResult<>(FAIL, "借款金额应大于递增金额");
		}
		// 不是100的整数倍
		if (account.divideAndRemainder(increaseMoney)[1].compareTo(BigDecimal.ZERO) != 0) {
			// 发标金额应大于递增金额
			return new AdminResult<>(FAIL, "借款金额应该是整百金额");
		}
		return new AdminResult();
	}


	/**
	 * 担保机构用户名是否存在
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "担保机构用户名是否存在")
	@PostMapping("/isRepayOrgUser")
	public AdminResult isRepayOrgUser(@RequestBody @Valid Map<String, String> name) {
		 List<UserVO> user = this.borrowCommonService.selectUserByUsername(name.get("userName"));
			if (user == null || user.size() == 0) {
				return new AdminResult<>(FAIL, "请填写用户角色为担保机构的已开户用户！！");
			}
			if(user.get(0).getStatus()!=0) {
				return new AdminResult<>(FAIL, "垫付机构已被禁用");
			}
				 UserInfoVO userinfo = borrowCommonService.findUserInfoById(user.get(0).getUserId());
				if(userinfo.getRoleId()!=3) {
					return new AdminResult<>(FAIL, "请填写用户角色为担保机构的已开户用户！！");
				}
		Integer authState = CommonUtils.checkPaymentAuthStatus(user.get(0).getPaymentAuthStatus());
		if (authState == 0) {
			return new AdminResult<>(FAIL, "未开通服务费授权！");
		}
		return new AdminResult();
	}

	/**
	 * 获取最新的借款预编码
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = " 获取最新的借款预编码")
	@PostMapping("/getBorrowPreNid")
	public AdminResult<Map<String, String>> getBorrowPreNid() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("borrowPreNid", this.borrowCommonService.getBorrowPreNid());
		return new AdminResult<Map<String, String>>(data);
	}

	/**
	 * 获取现金贷的借款预编号
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = " 获取现金贷的借款预编号")
	@PostMapping("/getXJDBorrowPreNid")
	public  AdminResult<Map<String, String>>  getXJDBorrowPreNid() {
		Map<String, String> data =  new HashMap<String, String>();
		data.put("borrowPreNid", this.borrowCommonService.getXJDBorrowPreNid());
		return new AdminResult<Map<String, String>>(data);
	}

	/**
	 * 借款预编码是否存在
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = " 借款预编码是否存在")
	@PostMapping("/isExistsBorrowPreNidRecord")
	public AdminResult<Boolean> isExistsBorrowPreNidRecord(@RequestBody @Valid  Map<String,String> borrowPreNid) {
//		LogUtil.startLog(BorrowCommonController.class.toString(), BorrowCommonDefine.ISEXISTSBORROWPRENIDRECORD);
//		String message = this.borrowCommonService.isExistsBorrowPreNidRecord(request);
//		LogUtil.endLog(BorrowCommonController.class.toString(), BorrowCommonDefine.ISEXISTSBORROWPRENIDRECORD);
		boolean borrowPreNidFlag = borrowCommonService.isExistsBorrowPreNidRecord(borrowPreNid.get("borrowPreNid"));
		return new AdminResult<>(!borrowPreNidFlag);

	}

	/**
	 * 获取放款服务费率 & 还款服务费率
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "获取放款服务费率 & 还款服务费率")
	@PostMapping("/getBorrowServiceScale")
	public AdminResult<BorrowCommonVO> getBorrowServiceScale(@RequestBody @Valid BorrowCommonRequest borrowCommonRequest) {
		BorrowCommonVO ncv = this.borrowCommonService.getBorrowServiceScale(borrowCommonRequest);
		return new AdminResult<BorrowCommonVO>(ncv);
	}
	@Value("${file.domain.url}")
    private String url; 
	@Value("${file.physical.path}")
	private String physical;
	@Value("${file.upload.temp.path}")
	private String temppath;
	/**
	 * 资料上传
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "资料上传")
	@PostMapping("/uploadFile")
	public AdminResult<LinkedList<BorrowCommonImage>> uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		String fileDomainUrl = UploadFileUtils.getDoPath(url);
		String filePhysicalPath = UploadFileUtils.getDoPath(physical);
		String fileUploadTempPath = UploadFileUtils.getDoPath(temppath);

		String logoRealPathDir = filePhysicalPath + fileUploadTempPath;

		File logoSaveFile = new File(logoRealPathDir);
		if (!logoSaveFile.exists()) {
			logoSaveFile.mkdirs();
		}

		BorrowCommonImage fileMeta = null;
		LinkedList<BorrowCommonImage> files = new LinkedList<BorrowCommonImage>();

		Iterator<String> itr = multipartRequest.getFileNames();
		MultipartFile multipartFile = null;

		while (itr.hasNext()) {
			multipartFile = multipartRequest.getFile(itr.next());
			String fileRealName = String.valueOf(System.currentTimeMillis());
			String originalFilename = multipartFile.getOriginalFilename();
			fileRealName = fileRealName + UploadFileUtils.getSuffix(multipartFile.getOriginalFilename());

			// 文件大小
			String errorMessage = UploadFileUtils.upload4Stream(fileRealName, logoRealPathDir, multipartFile.getInputStream(), 5000000L);

			fileMeta = new BorrowCommonImage();
			int index = originalFilename.lastIndexOf(".");
			if (index != -1) {
				fileMeta.setImageName(originalFilename.substring(0, index));
			} else {
				fileMeta.setImageName(originalFilename);
			}

			fileMeta.setImageRealName(fileRealName);
			fileMeta.setImageSize(multipartFile.getSize() / 1024 + "");// KB
			fileMeta.setImageType(multipartFile.getContentType());
			fileMeta.setErrorMessage(errorMessage);
			// 获取文件路径
			fileMeta.setImagePath(fileUploadTempPath + fileRealName);
			fileMeta.setImageSrc(fileDomainUrl + fileUploadTempPath + fileRealName);
			files.add(fileMeta);
		}
		return new AdminResult<LinkedList<BorrowCommonImage>>(files);
	}

	/**
	 * 导出功能
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @throws Exception
	 */
	@ApiOperation(value = "导出功能")
	@PostMapping("/downloadCarAction")
	public void downloadCarAction(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 表格sheet名称
		String sheetName = "抵押车辆模板";

		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + "xls";

		String[] titles = new String[] { "车辆品牌", "型号", "车系", "颜色", "出厂年份", "产地", "购买日期(例：2016-01-04)", "购买价格（元）", "是否有保险(否|有)", "评估价（元）","车牌号","车辆登记地","车架号" };
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 生成一个表格
		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName);

		CellStyle style = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		for (int i = 0; i < 10; i++) {
			sheet.setDefaultColumnStyle(i, style);
		}

		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
	}

	/**
	 * 资料上传
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "资料上传")
	@PostMapping("/uploadCarAction")
	public AdminResult<ListResult<BorrowCommonCarVO>> uploadCarAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;

		Iterator<String> itr = multipartRequest.getFileNames();
		MultipartFile multipartFile = null;

		List<BorrowCommonCarVO> borrowCarinfoList = new ArrayList<BorrowCommonCarVO>();

		while (itr.hasNext()) {
			multipartFile = multipartRequest.getFile(itr.next());
			String fileRealName = String.valueOf(System.currentTimeMillis());
			fileRealName = fileRealName + UploadFileUtils.getSuffix(multipartFile.getOriginalFilename());

			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(multipartFile.getInputStream());

			if (hssfWorkbook != null) {
				for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
					HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);

					// 循环行Row
					for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
						if (rowNum == 0) {
							continue;
						}
						BorrowCommonCarVO borrowCarinfo = new BorrowCommonCarVO();
						HSSFRow hssfRow = hssfSheet.getRow(rowNum);
						if (hssfRow == null
								|| (hssfRow.getCell(0) == null && hssfRow.getCell(1) == null && hssfRow.getCell(2) == null && hssfRow.getCell(3) == null && hssfRow.getCell(4) == null
										&& hssfRow.getCell(5) == null && hssfRow.getCell(6) == null && hssfRow.getCell(7) == null && hssfRow.getCell(8) == null && hssfRow.getCell(9) == null)) {
							continue;
						}
						
						if (!(StringUtils.isEmpty(this.getValue(hssfRow.getCell(0))) && StringUtils.isEmpty(this.getValue(hssfRow.getCell(1))) 
								&& StringUtils.isEmpty(this.getValue(hssfRow.getCell(2)))
								&& StringUtils.isEmpty(this.getValue(hssfRow.getCell(3))) && StringUtils.isEmpty(this.getValue(hssfRow.getCell(4)))
								&& StringUtils.isEmpty(this.getValue(hssfRow.getCell(5))) && StringUtils.isEmpty(this.getValue(hssfRow.getCell(6)))
								&& StringUtils.isEmpty(this.getValue(hssfRow.getCell(7))) && StringUtils.isEmpty(this.getValue(hssfRow.getCell(8)))
								&& StringUtils.isEmpty(this.getValue(hssfRow.getCell(9))) && StringUtils.isEmpty(this.getValue(hssfRow.getCell(10)))
								&& StringUtils.isEmpty(this.getValue(hssfRow.getCell(11))) && StringUtils.isEmpty(this.getValue(hssfRow.getCell(12))))) {
							// 车辆品牌
							borrowCarinfo.setBrand(this.getValue(hssfRow.getCell(0)));
							// 型号
							borrowCarinfo.setModel(this.getValue(hssfRow.getCell(1)));
							// 车系
							borrowCarinfo.setCarseries(this.getValue(hssfRow.getCell(2)));
							// 颜色
							borrowCarinfo.setColor(this.getValue(hssfRow.getCell(3)));
							// 出厂年份
							borrowCarinfo.setYear(this.getValue(hssfRow.getCell(4)));
							// 产地
							borrowCarinfo.setPlace(this.getValue(hssfRow.getCell(5)));
							// 购买日期
							borrowCarinfo.setBuytime(this.getValue(hssfRow.getCell(6)));
							// 购买价格（元）
							borrowCarinfo.setPrice(this.getValue(hssfRow.getCell(7)));
							// 是否有保险
							borrowCarinfo.setIsSafe(this.getValue(hssfRow.getCell(8)));
							// 评估价（元）
							borrowCarinfo.setToprice(this.getValue(hssfRow.getCell(9)));
							// 车牌号
							borrowCarinfo.setNumber(this.getValue(hssfRow.getCell(10)));
							// 车辆登记地
							borrowCarinfo.setRegistration(this.getValue(hssfRow.getCell(11)));
							// 车架号
							borrowCarinfo.setVin(this.getValue(hssfRow.getCell(12)));
							
							borrowCarinfoList.add(borrowCarinfo);
						}
					}
				}
			}
		}

		return new AdminResult<ListResult<BorrowCommonCarVO>>(ListResult.build(borrowCarinfoList, 0));
	}

	/**
	 * 导出功能
	 *
	 * @param request
	 * @param response
	 * @param form
	 * @throws Exception
	 */
	@ApiOperation(value = "导出功能")
	@PostMapping("/downloadHouseAction")
	public void downloadHouseAction(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 表格sheet名称
		String sheetName = "抵押房产模板";

		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + "xls";

		String[] titles = new String[] { "房产类型", "房产位置", "建筑面积", "市值", "资产数量","评估价值（元）","资产所属" };
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 生成一个表格
		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName);

		CellStyle style = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		for (int i = 0; i < 5; i++) {
			sheet.setDefaultColumnStyle(i, style);
		}

		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
	}

	/**
	 * 资料上传
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "导出功能")
	@PostMapping("/uploadHouseAction")
	public AdminResult<ListResult<BorrowHousesVO>> uploadHouseAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;

		Iterator<String> itr = multipartRequest.getFileNames();
		MultipartFile multipartFile = null;

		List<BorrowHousesVO> recordList = new ArrayList<BorrowHousesVO>();

		while (itr.hasNext()) {
			multipartFile = multipartRequest.getFile(itr.next());
			String fileRealName = String.valueOf(System.currentTimeMillis());
			fileRealName = fileRealName + UploadFileUtils.getSuffix(multipartFile.getOriginalFilename());

			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(multipartFile.getInputStream());

			if (hssfWorkbook != null) {
				for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
					HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);

					// 循环行Row
					for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
						if (rowNum == 0) {
							continue;
						}
						BorrowHousesVO record = new BorrowHousesVO();
						HSSFRow hssfRow = hssfSheet.getRow(rowNum);
						if (hssfRow == null || (hssfRow.getCell(0) == null && hssfRow.getCell(1) == null && hssfRow.getCell(2) == null && hssfRow.getCell(3) == null && hssfRow.getCell(4) == null)) {
							continue;
						}
						if (!(StringUtils.isEmpty(this.getValue(hssfRow.getCell(0))) && StringUtils.isEmpty(this.getValue(hssfRow.getCell(1))) 
								&& StringUtils.isEmpty(this.getValue(hssfRow.getCell(2))) && StringUtils.isEmpty(this.getValue(hssfRow.getCell(3))) 
								&& StringUtils.isEmpty(this.getValue(hssfRow.getCell(4))) && StringUtils.isEmpty(this.getValue(hssfRow.getCell(5))) 
								&& StringUtils.isEmpty(this.getValue(hssfRow.getCell(6))))) {
							// 房产类型
							record.setHousesType(this.getValue(hssfRow.getCell(0)));
							// 房产位置
							record.setHousesLocation(this.getValue(hssfRow.getCell(1)));
							// 建筑面积
							record.setHousesArea(this.getValue(hssfRow.getCell(2)));
							// 市值
							record.setHousesPrice(this.getValue(hssfRow.getCell(3)));
							// 资产数量
							if(StringUtils.isNotEmpty(this.getValue(hssfRow.getCell(4)))){
								record.setHousesCnt(Integer.valueOf(this.getValue(hssfRow.getCell(4))));
							}else{
								record.setHousesCnt(1);
							}
							// 抵押价值（元）
							record.setHousesToprice(this.getValue(hssfRow.getCell(5)));
							// 资产所属
							record.setHousesBelong(this.getValue(hssfRow.getCell(6)));
							recordList.add(record);
						}
					}
				}
			}
		}
		return new AdminResult<ListResult<BorrowHousesVO>>(ListResult.build(recordList, 0));
	}

	/**
	 * 导出功能
	 *
	 * @param request
	 * @param response
	 * @param form
	 * @throws Exception
	 */
	@ApiOperation(value = "导出功能")
	@PostMapping("/downloadAuthenAction")
	public void downloadAuthenAction(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 表格sheet名称
		String sheetName = "认证信息模板";

		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + "xls";

		String[] titles = new String[] { "展示顺序", "认证项目名称", "认证时间(例：2016-01-04)" };
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 生成一个表格
		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName);

		CellStyle style = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		for (int i = 0; i < 3; i++) {
			sheet.setDefaultColumnStyle(i, style);
		}

		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
	}

	/**
	 * 资料上传
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "导出功能")
	@PostMapping("/uploadAuthenAction")
	public AdminResult<ListResult<BorrowCommonCompanyAuthenVO>> uploadAuthenAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest)request;

		Iterator<String> itr = multipartRequest.getFileNames();
		MultipartFile multipartFile = null;

		List<BorrowCommonCompanyAuthenVO> recordList = new ArrayList<BorrowCommonCompanyAuthenVO>();

		while (itr.hasNext()) {
			multipartFile = multipartRequest.getFile(itr.next());
			String fileRealName = String.valueOf(System.currentTimeMillis());
			fileRealName = fileRealName + UploadFileUtils.getSuffix(multipartFile.getOriginalFilename());

			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(multipartFile.getInputStream());

			if (hssfWorkbook != null) {
				for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
					HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);

					// 循环行Row
					for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
						if (rowNum == 0) {
							continue;
						}
						BorrowCommonCompanyAuthenVO record = new BorrowCommonCompanyAuthenVO();
						HSSFRow hssfRow = hssfSheet.getRow(rowNum);
						if (hssfRow == null || (hssfRow.getCell(0) == null && hssfRow.getCell(1) == null && hssfRow.getCell(2) == null)) {
							continue;
						}
						if (!(StringUtils.isEmpty(this.getValue(hssfRow.getCell(0))) && StringUtils.isEmpty(this.getValue(hssfRow.getCell(1))) && StringUtils
								.isEmpty(this.getValue(hssfRow.getCell(2))))) {
							// 展示顺序
							record.setAuthenSortKey(this.getValue(hssfRow.getCell(0)));
							// 认证项目名称
							record.setAuthenName(this.getValue(hssfRow.getCell(1)));
							// 认证时间
							record.setAuthenTime(this.getValue(hssfRow.getCell(2)));

							recordList.add(record);
						}

					}
				}
			}
		}
		return new AdminResult<ListResult<BorrowCommonCompanyAuthenVO>>(ListResult.build(recordList, 0));
	}

	/**
	 * 借款内容填充
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = " 借款内容填充")
	@PostMapping("/contentFillAction")
	public AdminResult<Map<String,String>> contentFillAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest)request;

		Iterator<String> itr = multipartRequest.getFileNames();
		MultipartFile multipartFile = null;

		//返回结果
		Map<String,String> resultMap = new HashMap<String,String>();

		while (itr.hasNext()) {
			multipartFile = multipartRequest.getFile(itr.next());
			String fileRealName = String.valueOf(System.currentTimeMillis());
			fileRealName = fileRealName + UploadFileUtils.getSuffix(multipartFile.getOriginalFilename());

			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(multipartFile.getInputStream());

			if (hssfWorkbook != null) {
				for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
					HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);

					// 循环行Row
					for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {

						HSSFRow hssfRow = hssfSheet.getRow(rowNum);
						if (hssfRow == null || (hssfRow.getCell(0) == null && hssfRow.getCell(1) == null)) {
							continue;
						}
						if(StringUtils.isEmpty(this.getValue(hssfRow.getCell(1)))){
							continue;
						}
						if(rowNum == 0){//借款人用户名
							resultMap.put("username", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 1){//项目申请人
							resultMap.put("applicant", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 2){//担保机构用户名
							resultMap.put("repayOrgName", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 3){//项目名称
							resultMap.put("projectName", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 4){//借款标题
							resultMap.put("name", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 5){//借款金额
							resultMap.put("account", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 6){//出借利率
							resultMap.put("borrowApr", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 7){//融资用途
							resultMap.put("financePurpose", this.getValue2(hssfRow.getCell(1)));
						}else if(rowNum == 8){//月薪收入
							resultMap.put("monthlyIncome", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 9){//还款来源
							resultMap.put("payment", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 10){//第一还款来源
							resultMap.put("firstPayment", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 11){//第二还款来源
							resultMap.put("secondPayment", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 12){//费用说明
							resultMap.put("costIntrodution", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 13){//项目信息
							resultMap.put("borrowContents", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 14){//财务状况
							resultMap.put("fianceCondition", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 15){//车辆品牌
							resultMap.put("brand", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 16){//型号
							resultMap.put("model", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 17){//产地
							resultMap.put("place", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 18){//购买价格
							resultMap.put("price", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 19){//评估价（元）
							resultMap.put("toprice", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 20){//车牌号
							resultMap.put("number", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 21){//房产类型
							if(this.getValue(hssfRow.getCell(1)).equals("住宅") ){
								resultMap.put("housesType", "1");
							}else if(this.getValue(hssfRow.getCell(1)).equals("房产")){
								resultMap.put("housesType", "2");
							}else if(this.getValue(hssfRow.getCell(1)).equals("土地")){
								resultMap.put("housesType", "3");
							}else if(this.getValue(hssfRow.getCell(1)).equals("工业用房")){
								resultMap.put("housesType", "4");
							}else if(this.getValue(hssfRow.getCell(1)).equals("工业用地")){
								resultMap.put("housesType", "5");
							}
						}else if(rowNum == 22){//建筑面积
							resultMap.put("housesArea", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 23){//资产数量
							resultMap.put("housesCnt", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 24){//评估价值（元）
							resultMap.put("housesToprice", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 25){//资产所属
							resultMap.put("housesBelong", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 26){//借款类型
							if(this.getValue(hssfRow.getCell(1)).equals("个人")) {
								resultMap.put("companyOrPersonal","2");
							}else {
								resultMap.put("companyOrPersonal","1");
							}
						}else if(rowNum == 27){//融资主体
							resultMap.put("comName", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 28){//法人
							resultMap.put("comLegalPerson", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 29){//注册地区
							resultMap.put("registrationAddress", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 30){//主营业务
							resultMap.put("comMainBusiness", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 31){//在平台逾期次数
							resultMap.put("comOverdueTimes", this.getValue(hssfRow.getCell(1)).replace(".0", ""));
						}else if(rowNum == 32){//在平台逾期金额
							resultMap.put("comOverdueAmount", this.getValue(hssfRow.getCell(1)).replace(".0", ""));
						}else if(rowNum == 33){//注册时间
							resultMap.put("comRegTime", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 34){//统一社会信用代码
							resultMap.put("comSocialCreditCode", this.getValue(hssfRow.getCell(1)));
						}
						
						else if(rowNum == 35){//注册号
							String a = this.getValue(hssfRow.getCell(1));
							if(StringUtils.isNotEmpty(a)){
								Integer b = Integer.parseInt(a.split("\\.")[0]);
								resultMap.put("comRegistCode", b.toString());
							}	
						}
						
						else if(rowNum == 36){//注册资本
							resultMap.put("comRegCaptial", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 37){//所属行业
							resultMap.put("comUserIndustry", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 38){//涉诉情况
							resultMap.put("comLitigation", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 39){//姓名
							resultMap.put("manname", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 40){//身份证号
							resultMap.put("cardNo", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 41){//年龄
							resultMap.put("old", this.getValue(hssfRow.getCell(1)).replace(".0", ""));
						}else if(rowNum == 42){//岗位职业
							resultMap.put("position", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 43){//性别
							if( this.getValue(hssfRow.getCell(1)).equals("男")) {
								resultMap.put("sex", "1");
							}else{
								resultMap.put("sex", "2");
							}
							//resultMap.put("sex", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 44){//婚姻状况
							if( this.getValue(hssfRow.getCell(1)).equals("已婚") ){
								resultMap.put("merry", "1");
							}else if(this.getValue(hssfRow.getCell(1)).equals("未婚")){
								resultMap.put("merry", "2");
							}else if(this.getValue(hssfRow.getCell(1)).equals("离异")){
								resultMap.put("merry", "3");
							}else if(this.getValue(hssfRow.getCell(1)).equals("丧偶") ){
								resultMap.put("merry", "4");
							}
						//	resultMap.put("merry", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 45){//工作城市
							resultMap.put("location_c", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 46){//户籍地
							resultMap.put("domicile", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 47){//在平台逾期次数
							resultMap.put("overdueTimes", this.getValue(hssfRow.getCell(1)).replace(".0", ""));
						}else if(rowNum == 48){//在平台逾期金额
							resultMap.put("overdueAmount", this.getValue(hssfRow.getCell(1)).replace(".0", ""));
						}else if(rowNum == 49){//涉诉情况
							resultMap.put("litigation", this.getValue(hssfRow.getCell(1)));
						}
						// 信批需求新增
						else if(rowNum == 50){//年收入
							resultMap.put("annualIncome", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 51){//征信报告逾期情况
							resultMap.put("overdueReport", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 52){//重大负债状况
							resultMap.put("debtSituation", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 53){//其他平台借款情况
							resultMap.put("otherBorrowed", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 54){//(个人)借款资金运用情况
							resultMap.put("isFunds", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 55){//(个人)借款人经营状况及财务状况
							resultMap.put("isManaged", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 56){//(个人)借款人还款能力变化情况
							resultMap.put("isAbility", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 57){//(个人)借款人逾期情况
							resultMap.put("isOverdue", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 58){//(个人)借款人涉诉情况
							resultMap.put("isComplaint", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 59){//(个人)借款人受行政处罚情况
							resultMap.put("isPunished", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 60){//(企业)征信报告逾期情况
							resultMap.put("comOverdueReport", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 61){//(企业)重大负债状况
							resultMap.put("comDebtSituation", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 62){//(企业)其他平台借款情况
							resultMap.put("comOtherBorrowed", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 63){//(企业)借款资金运用情况
							resultMap.put("comIsFunds", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 64){//(企业)借款人经营状况及财务状况
							resultMap.put("comIsManaged", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 65){//(企业)借款人还款能力变化情况
							resultMap.put("comIsAbility", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 66){//(企业)借款人逾期情况
							resultMap.put("comIsOverdue", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 67){//(企业)借款人涉诉情况
							resultMap.put("comIsComplaint", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 68){//(企业)借款人受行政处罚情况
							resultMap.put("comIsPunished", this.getValue(hssfRow.getCell(1)));
						}
						/** 原企业勾选内容改上传 start */
						else if(rowNum == 69){//(企业)企业证件
							resultMap.put("comIsCertificate", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 70){//(企业)经营状况
							resultMap.put("comIsOperation", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 71){//(企业)财务状况
							resultMap.put("comIsFinance", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 72){//(企业)企业信用
							resultMap.put("comIsEnterpriseCreidt", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 73){//(企业)法人信息
							resultMap.put("comIsLegalPerson", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 74){//(企业)资产状况
							resultMap.put("comIsAsset", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 75){//(企业)购销合同
							resultMap.put("comIsPurchaseContract", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 76){//(企业)购销合同
							resultMap.put("comIsSupplyContract", this.getValue(hssfRow.getCell(1)));
						}
						/** 原企业勾选内容改上传 end */
						/** 原个人勾选内容改上传 start */
						else if(rowNum == 77){//(个人)身份证
							resultMap.put("isCard", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 78){//(个人)收入状况
							resultMap.put("isIncome", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 79){//(个人)信用状况
							resultMap.put("isCredit", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 80){//(个人)资产状况
							resultMap.put("isAsset", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 81){//(个人)车辆状况
							resultMap.put("isVehicle", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 82){//(个人)行驶证
							resultMap.put("isDrivingLicense", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 83){//(个人)车辆登记证
							resultMap.put("isVehicleRegistration", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 84){//(个人)婚姻状况
							resultMap.put("isMerry", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 85){//(个人)工作状况
							resultMap.put("isWork", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 86){//(个人)户口本
							resultMap.put("isAccountBook", this.getValue(hssfRow.getCell(1)));
						}	
						/** 原个人勾选内容改上传 end */
						// 互金,添加借款人地址,企业组织机构代码,企业注册地 add by nxl 20180809 Start
						else if(rowNum == 87){//(企业)企业注册地
							resultMap.put("registration", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 88){//(个人)借款人地址
							resultMap.put("address", this.getValue(hssfRow.getCell(1)));
						}
						else if(rowNum == 89){//(企业)企业组织机构代码
							resultMap.put("corporateCode", this.getValue(hssfRow.getCell(1)));
						}
						// 互金,添加借款人地址,企业组织机构代码,企业注册地 add by nxl 20180809 end
					}
				}
			}
		}
		return new AdminResult<Map<String,String>>(resultMap);
	}

	/**
	 * 下载借款内容模板
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ApiOperation(value = "下载借款内容模板")
	@PostMapping("/downloadContentFillAction")
	public void downloadContentFillAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 表格sheet名称
				String sheetName = "借款内容填充模板";

				String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + "xls";

				String[] titles = new String[] { "" };
				// 声明一个工作薄
				HSSFWorkbook workbook = new HSSFWorkbook();

				// 生成一个表格
				HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName);

				int rowNum = 0;
				for (int i = 0; i < 90; i++) {
					// 新建一行
					Row row = sheet.createRow(rowNum);

					// 循环数据
					for (int celLength = 0; celLength < titles.length; celLength++) {
						// 创建相应的单元格
						Cell cell = row.createCell(celLength);
						if(rowNum == 0){//借款人用户名
							if (celLength == 0) {
								cell.setCellValue("借款方用户名");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 1){//项目申请人
							if (celLength == 0) {
								cell.setCellValue("项目申请人");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 2){//担保机构用户名
							if (celLength == 0) {
								cell.setCellValue("担保机构用户名");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 3){//项目名称
							if (celLength == 0) {
								cell.setCellValue("项目名称");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 4){//借款标题
							if (celLength == 0) {
								cell.setCellValue("借款标题");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 5){//借款金额
							if (celLength == 0) {
								cell.setCellValue("借款金额");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 6){//出借利率
							if (celLength == 0) {
								cell.setCellValue("出借利率");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 7){//融资用途
							if (celLength == 0) {
								cell.setCellValue("融资用途");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 8){//月薪收入
							if (celLength == 0) {
								cell.setCellValue("月薪收入");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 9){//还款来源
							if (celLength == 0) {
								cell.setCellValue("还款来源");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 10){//第一还款来源
							if (celLength == 0) {
								cell.setCellValue("第一还款来源");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 11){//第二还款来源
							if (celLength == 0) {
								cell.setCellValue("第二还款来源");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 12){//费用说明
							if (celLength == 0) {
								cell.setCellValue("费用说明");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 13){//项目信息
							if (celLength == 0) {
								cell.setCellValue("项目信息");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 14){//财务状况
							if (celLength == 0) {
								cell.setCellValue("财务状况");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 15){//车辆品牌
							if (celLength == 0) {
								cell.setCellValue("车辆品牌");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 16){//型号
							if (celLength == 0) {
								cell.setCellValue("型号");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 17){//产地
							if (celLength == 0) {
								cell.setCellValue("产地");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 18){//购买价格
							if (celLength == 0) {
								cell.setCellValue("购买价格");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 19){//评估价（元）
							if (celLength == 0) {
								cell.setCellValue("评估价（元）");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 20){//车牌号
							if (celLength == 0) {
								cell.setCellValue("车牌号");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 21){//房产类型
							if (celLength == 0) {
								cell.setCellValue("房产类型(住宅|房产|土地|工业用房|工业用地)");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 22){//建筑面积
							if (celLength == 0) {
								cell.setCellValue("建筑面积");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 23){//资产数量
							if (celLength == 0) {
								cell.setCellValue("资产数量");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 24){//评估价值（元）
							if (celLength == 0) {
								cell.setCellValue("评估价值（元）");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 25){//资产所属
							if (celLength == 0) {
								cell.setCellValue("资产所属");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 26){//借款类型
							if (celLength == 0) {
								cell.setCellValue("借款类型（个人|企业）");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 27){//融资主体
							if (celLength == 0) {
								cell.setCellValue("融资主体");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 28){//法人
							if (celLength == 0) {
								cell.setCellValue("法人");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 29){//注册地区
							if (celLength == 0) {
								cell.setCellValue("注册地区");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 30){//主营业务
							if (celLength == 0) {
								cell.setCellValue("主营业务");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 31){//在平台逾期次数
							if (celLength == 0) {
								cell.setCellValue("在平台逾期次数");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 32){//在平台逾期金额
							if (celLength == 0) {
								cell.setCellValue("在平台逾期金额");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 33){//注册时间
							if (celLength == 0) {
								cell.setCellValue("注册时间");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 34){//统一社会信用代码
							if (celLength == 0) {
								cell.setCellValue("统一社会信用代码");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 35){//注册号
							if (celLength == 0) {
								cell.setCellValue("注册号");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 36){//注册资本
							if (celLength == 0) {
								cell.setCellValue("注册资本");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 37){//所属行业
							if (celLength == 0) {
								cell.setCellValue("所属行业");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 38){//涉诉情况
							if (celLength == 0) {
								cell.setCellValue("涉诉情况");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 39){//姓名
							if (celLength == 0) {
								cell.setCellValue("姓名");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 40){//身份证号
							if (celLength == 0) {
								cell.setCellValue("身份证号");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 41){//年龄
							if (celLength == 0) {
								cell.setCellValue("年龄");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 42){//岗位职业
							if (celLength == 0) {
								cell.setCellValue("岗位职业");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 43){//性别
							if (celLength == 0) {
								cell.setCellValue("性别(男|女)");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 44){//婚姻状况
							if (celLength == 0) {
								cell.setCellValue("婚姻状况(已婚|未婚)");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 45){//工作城市
							if (celLength == 0) {
								cell.setCellValue("工作城市");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 46){//户籍地
							if (celLength == 0) {
								cell.setCellValue("户籍地");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 47){//在平台逾期次数
							if (celLength == 0) {
								cell.setCellValue("在平台逾期次数");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 48){//在平台逾期金额
							if (celLength == 0) {
								cell.setCellValue("在平台逾期金额");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 49){//涉诉情况
							if (celLength == 0) {
								cell.setCellValue("涉诉情况");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						
						// 信批需求新增 start
						else if(rowNum == 50){//年收入
							if (celLength == 0) {
								cell.setCellValue("(个人)年收入");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						
						else if(rowNum == 51){//征信报告逾期情况：暂未提供；无；已处理
							if (celLength == 0) {
								cell.setCellValue("(个人)征信报告逾期情况");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						
						else if(rowNum == 52){//重大负债状况：无
							if (celLength == 0) {
								cell.setCellValue("(个人)重大负债状况");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						
						else if(rowNum == 53){//其他平台借款情况：无；暂未提供
							if (celLength == 0) {
								cell.setCellValue("(个人)其他平台借款情况");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						
						else if(rowNum == 54){//借款资金运用情况
							if (celLength == 0) {
								cell.setCellValue("(个人)借款资金运用情况");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						
						else if(rowNum == 55){//借款人经营状况及财务状况
							if (celLength == 0) {
								cell.setCellValue("(个人)借款方经营状况及财务状况");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						
						else if(rowNum == 56){//借款人还款能力变化情况
							if (celLength == 0) {
								cell.setCellValue("(个人)借款方还款能力变化情况");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						
						else if(rowNum == 57){//借款人逾期情况
							if (celLength == 0) {
								cell.setCellValue("(个人)借款方逾期情况");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						
						else if(rowNum == 58){//借款人涉诉情况
							if (celLength == 0) {
								cell.setCellValue("(个人)借款方涉诉情况");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						
						else if(rowNum == 59){//借款人受行政处罚情况
							if (celLength == 0) {
								cell.setCellValue("(个人)借款方受行政处罚情况");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						
						else if(rowNum == 60){//征信报告逾期情况：暂未提供；无；已处理
							if (celLength == 0) {
								cell.setCellValue("(企业)征信报告逾期情况");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						
						else if(rowNum == 61){//重大负债状况：无
							if (celLength == 0) {
								cell.setCellValue("(企业)重大负债状况");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						
						else if(rowNum == 62){//其他平台借款情况：无；暂未提供
							if (celLength == 0) {
								cell.setCellValue("(企业)其他平台借款情况");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						
						else if(rowNum == 63){//借款资金运用情况
							if (celLength == 0) {
								cell.setCellValue("(企业)借款资金运用情况");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						
						else if(rowNum == 64){//借款人经营状况及财务状况
							if (celLength == 0) {
								cell.setCellValue("(企业)借款方经营状况及财务状况");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						
						else if(rowNum == 65){//借款人还款能力变化情况
							if (celLength == 0) {
								cell.setCellValue("(企业)借款方还款能力变化情况");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						
						else if(rowNum == 66){//借款人逾期情况
							if (celLength == 0) {
								cell.setCellValue("(企业)借款方逾期情况");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						
						else if(rowNum == 67){//借款人涉诉情况
							if (celLength == 0) {
								cell.setCellValue("(企业)借款方涉诉情况");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						
						else if(rowNum == 68){//借款人受行政处罚情况
							if (celLength == 0) {
								cell.setCellValue("(企业)借款方受行政处罚情况");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						/**---------企业勾选--------- */
						else if(rowNum == 69){//企业证件
							if (celLength == 0) {
								cell.setCellValue("(企业)证件(0未审核 1已审核)");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						else if(rowNum == 70){//经营状况
							if (celLength == 0) {
								cell.setCellValue("(企业)经营状况(0未审核 1已审核)");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						else if(rowNum == 71){//财务状况
							if (celLength == 0) {
								cell.setCellValue("(企业)财务状况(0未审核 1已审核)");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						else if(rowNum == 72){//企业信用
							if (celLength == 0) {
								cell.setCellValue("(企业)企业信用(0未审核 1已审核)");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						else if(rowNum == 73){//法人信息
							if (celLength == 0) {
								cell.setCellValue("(企业)法人信息(0未审核 1已审核)");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						else if(rowNum == 74){//资产状况
							if (celLength == 0) {
								cell.setCellValue("(企业)资产状况(0未审核 1已审核)");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						else if(rowNum == 75){//购销合同
							if (celLength == 0) {
								cell.setCellValue("(企业)购销合同(0未审核 1已审核)");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						else if(rowNum == 76){//供销合同
							if (celLength == 0) {
								cell.setCellValue("(企业)供销合同(0未审核 1已审核)");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						/**---------企业勾选--------- */
						/**---------个人勾选--------- */
						else if(rowNum == 77){//身份证
							if (celLength == 0) {
								cell.setCellValue("(个人)身份证(0未审核 1已审核)");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						else if(rowNum == 78){//收入状况
							if (celLength == 0) {
								cell.setCellValue("(个人)收入状况(0未审核 1已审核)");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						else if(rowNum == 79){//信用状况
							if (celLength == 0) {
								cell.setCellValue("(个人)信用状况(0未审核 1已审核)");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						else if(rowNum == 80){//资产状况
							if (celLength == 0) {
								cell.setCellValue("(个人)资产状况(0未审核 1已审核)");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						else if(rowNum == 81){//车辆状况
							if (celLength == 0) {
								cell.setCellValue("(个人)车辆状况(0未审核 1已审核)");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						else if(rowNum == 82){//行驶证
							if (celLength == 0) {
								cell.setCellValue("(个人)行驶证(0未审核 1已审核)");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						else if(rowNum == 83){//车辆登记证
							if (celLength == 0) {
								cell.setCellValue("(个人)车辆登记证(0未审核 1已审核)");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						else if(rowNum == 84){//婚姻状况
							if (celLength == 0) {
								cell.setCellValue("(个人)婚姻状况(0未审核 1已审核)");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						else if(rowNum == 85){//工作状况
							if (celLength == 0) {
								cell.setCellValue("(个人)工作状况(0未审核 1已审核)");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						else if(rowNum == 86){//户口本
							if (celLength == 0) {
								cell.setCellValue("(个人)户口本(0未审核 1已审核)");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						// 互金,添加借款人地址,企业组织机构代码,企业注册地 add by nxl 20180809 Start
						else if(rowNum == 87){//企业注册地
							if (celLength == 0) {
								cell.setCellValue("(企业)企业注册地");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						else if(rowNum == 88){//借款人地址
							if (celLength == 0) {
								cell.setCellValue("(个人)借款人地址");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						else if(rowNum == 89){//企业注册地
							if (celLength == 0) {
								cell.setCellValue("(企业)企业组织机构代码");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}
						// 互金,添加借款人地址,企业组织机构代码,企业注册地 add by nxl 20180809 end
					}
					//行++
					rowNum++;
				}
				// 导出
				ExportExcel.writeExcelFile(response, workbook, titles, fileName);
	}

	/**
	 * 根据资产编号查询该资产下面的产品类型
	 *
	 * @param request
	 * @param attr
	 * @param instCode
	 * @return
	 */
	@ApiOperation(value = " 根据资产编号查询该资产下面的产品类型")
	@PostMapping("/getProductTypeAction")
	public AdminResult<BorrowCommonResponse> getProductTypeAction(@RequestBody @Valid  Map<String, String>  instCode) {
		BorrowCommonResponse bcr=borrowCommonService.getProductTypeAction(instCode.get("instCode"));
		return new AdminResult<BorrowCommonResponse>(bcr);
	}

	/**
	 * 受托用户是否存在
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = " 受托用户是否存在")
	@PostMapping("/isEntrustedExistsUser")
	public AdminResult isEntrustedExistsUser(@RequestBody @Valid  Map<String, String> userName) {
		 UserVO user = this.borrowCommonService.getUserByUserName(userName.get("userName"));
		if (user == null ) {
		// 借款人用户名不存在。
			throw new ReturnMessageException(MsgEnum.ERR_USERNAME_NOT_EXIST);
		}
		if (user.getBankOpenAccount()!=1) {
			throw new ReturnMessageException(MsgEnum.ERR_USERNAME_NOT_ACCOUNTS);
		}
		if (user.getStatus() != 0) {
			throw new ReturnMessageException(MsgEnum.ERR_USERNAME_NOT_USES);
		}
		if(user.getPaymentAuthStatus()==0){
			// 未服务费授权
			throw new ReturnMessageException(MsgEnum.ERR_PAYMENT_AUTH);
		}
		int usersFlag=this.borrowCommonService.isEntrustedExistsUser(userName.get("userName"));
//		if (usersFlag == 1) {
//			throw new ReturnMessageException(MsgEnum.ERR_USERNAME_NOT_EXIST);
//		} else if (usersFlag == 2) {
//			throw new ReturnMessageException(MsgEnum.ERR_USERNAME_NOT_ACCOUNTS);
//		} else 
		if (usersFlag == 3) {
			throw new ReturnMessageException(MsgEnum.ERR_USERNAME_NOT_USES);
		} else if (usersFlag == 4) {
			throw new ReturnMessageException(MsgEnum.ERR_USERNAME_NOT_IN);
		} else if (usersFlag == 6) {
			throw new ReturnMessageException(MsgEnum.ERR_USERNAME_IS_DISABLE);
		} 
		return new AdminResult<>() ;
	}

	/**
	 * 借款主体CA认证check
	 * 
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "根据借款主体,社会统一信用代码查询用户是否做过CA认证")
	@PostMapping("/isBorrowUserCACheck")
	public AdminResult<Boolean> isBorrowUserCACheck(@RequestBody @Valid Map<String, String> name) {


		boolean ret = this.borrowCommonService.isBorrowUserCACheck(name.get("name"));
		return new AdminResult<Boolean>(ret);
	}

	/**
	 * 社会信用代码或身份证号CA认证check
	 * 
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "根据社会统一信用代码或身份证号查询用户是否做过CA认证")
	@PostMapping("/isCAIdNoCheck")
	public AdminResult<Boolean> isCAIdNoCheck(@RequestBody @Valid Map<String, String> name) {
		boolean ret = this.borrowCommonService.isCAIdNoCheck( name.get("name"),"1");

		return new AdminResult<Boolean>(ret);
	}
	/**
	 * 得到Excel表中的值
	 * 
	 * @param hssfCell
	 *            Excel中的每一个格子
	 * @return Excel中每一个格子中的值
	 */
	private String getValue(HSSFCell hssfCell) {
		if (hssfCell == null) {
			return "";
		}
		switch (hssfCell.getCellTypeEnum()) {
			case BOOLEAN:
				// 返回布尔类型的值
				return String.valueOf(hssfCell.getBooleanCellValue());
			case NUMERIC:
				// 返回数值类型的值
				String s = String.valueOf(hssfCell.getNumericCellValue());
				return s.replace(".0", "");
			case FORMULA:
				// 单元格为公式类型时
				if (CellType.NUMERIC == hssfCell.getCachedFormulaResultTypeEnum()) {
					// 返回数值类型的值
					return String.valueOf(hssfCell.getNumericCellValue());
				} else {
					// 返回字符串类型的值
					return String.valueOf(hssfCell.getStringCellValue());
				}
			case STRING:
				// 返回字符串类型的值
				return String.valueOf(hssfCell.getStringCellValue());
			default:
				return "";
		}
	}
	private String getValue2(HSSFCell hssfCell) {
		if (hssfCell == null) {
			return "";
		}
		switch (hssfCell.getCellTypeEnum()) {
			case BOOLEAN:
				// 返回布尔类型的值
				return String.valueOf(hssfCell.getBooleanCellValue());
			case NUMERIC:
				// 返回数值类型的值
				String s = String.valueOf(hssfCell.getNumericCellValue());
				if(s.equals("99.0")) {
					return s.replace(".0", "");
				}
				return "0"+ s.replace(".0", "");
			case FORMULA:
				// 单元格为公式类型时
				if (CellType.NUMERIC == hssfCell.getCachedFormulaResultTypeEnum()) {
					// 返回数值类型的值
					return String.valueOf(hssfCell.getNumericCellValue());
				} else {
					// 返回字符串类型的值
					return String.valueOf(hssfCell.getStringCellValue());
				}
			case STRING:
				// 返回字符串类型的值
				return String.valueOf(hssfCell.getStringCellValue());
			default:
				return "";
		}
	}
	/**
     * 迁移到详细画面
     *
     * @param request
     * @param form
     * @return
     */
	@ApiOperation(value = "查询借款列表")
	@PostMapping("/selectBorrowStyleList")
	 @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<BorrowCustomizeResponse>  init(@RequestBody @Valid BorrowBeanRequest form) {
		BorrowCustomizeResponse bcr=borrowCommonService.init(form);

		if(bcr==null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(bcr)) {
			return new AdminResult<>(FAIL, bcr.getMessage());

		}
		List<BorrowCustomizeVO> vo = bcr.getResultList();
		List<BorrowCustomizeVO> vo2=new ArrayList<BorrowCustomizeVO>();
		Map<String, String> map = CacheUtil.getParamNameMap("BORROW_STATUS");
		bcr.setBs(map);
		if(vo!=null) {
			for (BorrowCustomizeVO borrowCustomizeVO : vo) {
				BorrowCustomizeVO bvo=new BorrowCustomizeVO();
				bvo=borrowCustomizeVO;
				bvo.setDelFlag(bvo.getStatus());
				bvo.setStatus(map.get(bvo.getStatus()));
				vo2.add(bvo);
			}
		}

		bcr.setSt(CacheUtil.getParamNameMap("ASSET_STATUS"));
		bcr.setResultList(vo2);
		bcr.setWebUrl(systemConfig.getWebPdfHost());
		return new AdminResult<BorrowCustomizeResponse>(bcr);
    }
	/**
     * 运营记录-原始标的
     *
     * @param request
     * @param form
     * @return
     */
	@ApiOperation(value = "查询借款列表")
	@PostMapping("/optAction")
	 @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<BorrowCustomizeResponse>  optAction(@RequestBody @Valid BorrowBeanRequest form) {
		BorrowCustomizeResponse bcr=borrowCommonService.init(form);
		if(bcr==null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(bcr)) {
			return new AdminResult<>(FAIL, bcr.getMessage());

		}
		bcr.setSt(CacheUtil.getParamNameMap("ASSET_STATUS"));
		return new AdminResult<BorrowCustomizeResponse>(bcr);
    }
//	/**
//	 * 导出功能
//	 *
//	 * @param request
//	 * @param modelAndView
//	 * @param form
//	 */
//	@ApiOperation(value = "导出功能")
//	@PostMapping("/exportOptAction")
//	public void exportOptAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid BorrowBeanRequest form) throws Exception {
//		form.setPageSize(-1);
//		form.setCurrPage(-1);
//		// 表格sheet名称
//		String sheetName = "借款列表";
//
//		List<BorrowCommonCustomizeVO> resultList = borrowCommonService.exportBorrowList(form);
//
//		String fileName =  URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
//
//		//UPD BY LIUSHOUYI 合规检查 START
//		/*
//		String[] titles = new String[] { "序号", "项目编号", "计划编号", "借款人ID", "借款人用户名", "项目申请人","项目名称", "借款标题", "项目类型", "资产来源", "借款金额（元）", "借款期限", "出借利率", "还款方式", "放款服务费率", "还款服务费率", "合作机构", "已借到金额", "剩余金额", "借款进度", "项目状态", "添加时间",
//				"初审通过时间", "定时发标时间","预约开始时间","预约截止时间", "实际发标时间", "出借截止时间", "满标时间", "复审通过时间", "放款完成时间", "最后还款日","备案时间","担保机构用户名","复审人员","所在地区","借款人姓名","属性","是否受托支付","收款人用户名","标签名称","备注" };
//		*/
//		String[] titles = new String[] { "序号", "项目编号", "计划编号", "借款人ID", "用户名", "项目申请人", "项目名称", "项目类型", "资产来源", "借款金额（元）", "借款期限", "年化利率", "还款方式", "放款服务费率", "还款服务费率", "合作机构", "已借到金额", "剩余金额", "借款进度", "项目状态", "添加时间",
//				"初审通过时间", "定时发标时间","预约开始时间","预约截止时间", "实际发标时间", "出借截止时间", "满标时间", "复审通过时间", "放款完成时间", "最后还款日","备案时间","复审人员","所在地区","借款人姓名","属性","是否受托支付","收款人用户名","标签名称","备注" ,"添加标的人员","标的备案人员","担保机构用户名","加息收益率"};
//		// UPD BY LIUSHOUYI 合规检查 END
//
//		// 声明一个工作薄
//		HSSFWorkbook workbook = new HSSFWorkbook();
//
//		// 生成一个表格
//		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
//
//		if (resultList != null && resultList.size() > 0) {
//
//			int sheetCount = 1;
//			int rowNum = 0;
//
//			for (int i = 0; i < resultList.size(); i++) {
//				rowNum++;
//				if (i != 0 && i % 60000 == 0) {
//					sheetCount++;
//					sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
//					rowNum = 1;
//				}
//
//				// 新建一行
//				Row row = sheet.createRow(rowNum);
//				// 循环数据
//				for (int celLength = 0; celLength < titles.length; celLength++) {
//					BorrowCommonCustomizeVO borrowCommonCustomize = resultList.get(i);
//
//					// 创建相应的单元格
//					Cell cell = row.createCell(celLength);
//
//					if (celLength == 0) {
//						cell.setCellValue(i + 1);
//					}
//					// 项目编号
//					else if (celLength == 1) {
//						cell.setCellValue(borrowCommonCustomize.getBorrowNid());
//					}
//					// 计划编号
//					else if (celLength == 2) {
//						cell.setCellValue(borrowCommonCustomize.getPlanNid());
//					}
//					// 借款人ID
//					else if (celLength == 3) {
//						cell.setCellValue(borrowCommonCustomize.getUserId());
//					}
//					// 借款人用户名
//					else if (celLength == 4) {
//						cell.setCellValue(borrowCommonCustomize.getUsername());
//					}
//					// 项目申请人
//					else if (celLength == 5) {
//						cell.setCellValue(borrowCommonCustomize.getApplicant());
//					}
//					// 项目名称
//					else if (celLength == 6) {
//						cell.setCellValue(StringUtils.isEmpty(borrowCommonCustomize.getProjectName()) ? ""
//								: borrowCommonCustomize.getProjectName());
//					}
//					// 项目类型
//					else if (celLength == 7) {
//						cell.setCellValue(borrowCommonCustomize.getBorrowProjectTypeName());
//					}
//					// 资产来源
//					else if (celLength == 8) {
//						cell.setCellValue(borrowCommonCustomize.getInstName());
//					}
//					// 借款金额（元）
//					else if (celLength == 9) {
//						cell.setCellValue(borrowCommonCustomize.getAccount());
//					}
//					// 借款期限
//					else if (celLength == 10) {
//						cell.setCellValue(borrowCommonCustomize.getBorrowPeriod());
//					}
//					// 出借利率
//					else if (celLength == 11) {
//						cell.setCellValue(borrowCommonCustomize.getBorrowApr());
//					}
//					// 还款方式
//					else if (celLength == 12) {
//						cell.setCellValue(borrowCommonCustomize.getBorrowStyle());
//					}
//					// 放款服务费率
//					else if (celLength == 13) {
//						cell.setCellValue(borrowCommonCustomize.getBorrowServiceScale());
//					}
//					// 还款服务费率
//					else if (celLength == 14) {
//						cell.setCellValue(borrowCommonCustomize.getBorrowManagerScale());
//					}
//					// 合作机构
//					else if (celLength == 15) {
//						cell.setCellValue(borrowCommonCustomize.getBorrowMeasuresInstit());
//					}
//					// 已借到金额
//					else if (celLength == 16) {
//						cell.setCellValue(borrowCommonCustomize.getBorrowAccountYes());
//					}
//					// 剩余金额
//					else if (celLength == 17) {
//						cell.setCellValue(borrowCommonCustomize.getBorrowAccountWait());
//					}
//					// 借款进度
//					else if (celLength == 18) {
//						cell.setCellValue(borrowCommonCustomize.getBorrowAccountScale());
//					}
//					// 项目状态
//					else if (celLength == 19) {
//						cell.setCellValue(borrowCommonCustomize.getStatus());
//					}
//					// 添加时间
//					else if (celLength == 20) {
//						cell.setCellValue(borrowCommonCustomize.getAddtime());
//					}
//					// 初审通过时间
//					else if (celLength == 21) {
//						cell.setCellValue(borrowCommonCustomize.getVerifyTime());
//					}
//					// 定时发标时间
//					else if (celLength == 22) {
//						cell.setCellValue(borrowCommonCustomize.getOntime());
//					}
//					// 预约开始时间
//					else if (celLength == 23) {
//						if (!"待发布".equals(borrowCommonCustomize.getStatus())) {
//							cell.setCellValue(borrowCommonCustomize.getBookingBeginTime());
//						}
//					}
//					// 预约截止时间
//					else if (celLength == 24) {
//						if (!"待发布".equals(borrowCommonCustomize.getStatus())) {
//							cell.setCellValue(borrowCommonCustomize.getBookingEndTime());
//						}
//					}
//					// 实际发标时间
//					else if (celLength == 25) {
//						cell.setCellValue(borrowCommonCustomize.getVerifyTime());
//					}
//					// 投稿截止时间
//					else if (celLength == 26) {
//						cell.setCellValue(borrowCommonCustomize.getBorrowValidTime());
//					}
//					// 满标时间
//					else if (celLength == 27) {
//						cell.setCellValue(borrowCommonCustomize.getBorrowFullTime());
//					}
//					// 复审通过时间
//					else if (celLength == 28) {
//						cell.setCellValue(borrowCommonCustomize.getReverifyTime());
//					}
//					// 放款完成时间
//					else if (celLength == 29) {
//						cell.setCellValue(borrowCommonCustomize.getRecoverLastTime());
//					}
//					// 最后还款日
//					else if (celLength == 30) {
//						cell.setCellValue(borrowCommonCustomize.getRepayLastTime());
//					}
//					// 备案时间
//					else if (celLength == 31) {
//						cell.setCellValue(borrowCommonCustomize.getRegistTime());
//					}
//					// 复审人员
//					else if (celLength == 32) {
//						cell.setCellValue(borrowCommonCustomize.getReverifyUserName());
//					}
//					// 所在地区
//                    else if (celLength == 33) {
//                        cell.setCellValue(borrowCommonCustomize.getLocation());
//                    }
//					// 借款人姓名
//                    else if (celLength == 34) {
//                        cell.setCellValue(borrowCommonCustomize.getBorrowerName());
//                    }
//					// 属性
//                    else if (celLength == 35) {
//                        cell.setCellValue(borrowCommonCustomize.getAttribute());
//                    }
//					// 是否受托支付
//                    else if (celLength == 36) {
//                    	if ("0".equals(borrowCommonCustomize.getEntrustedFlg())) {
//                    		cell.setCellValue("否");
//                    	} else {
//                    		cell.setCellValue("是");
//                    	}
//                    }
//					// 收款人用户名
//                    else if (celLength == 37) {
//                    	if ("0".equals(borrowCommonCustomize.getEntrustedFlg())) {
//                    		cell.setCellValue("未开通受托支付");
//                    	} else {
//                    		cell.setCellValue(borrowCommonCustomize.getEntrustedUsername());
//                    	}
//                    }
////					// 账户操作人
////                    else if (celLength == 38) {
////                        cell.setCellValue(borrowCommonCustomize.getCreateUserName());
////                    }
////                    // 备案人员 
////                    else if (celLength == 39) {
////                        cell.setCellValue(borrowCommonCustomize.getRegistUserName());
////                    }
//					// 标签名称 
//                    else if (celLength == 38) {
//                        cell.setCellValue(borrowCommonCustomize.getLabelNameSrch());
//                    }
//					// 备注 
//                    else if (celLength == 39) {
//                        cell.setCellValue(borrowCommonCustomize.getRemark());
//                    }
//					// 添加标的人员 
//                    else if (celLength == 40) {
//                        cell.setCellValue(borrowCommonCustomize.getCreatename());
//                    }
//					// 标的备案人员 
//                    else if (celLength == 41) {
//                        cell.setCellValue(borrowCommonCustomize.getRegistname());
//                    }
//					// 担保机构用户名
//					else if (celLength == 42) {
//						cell.setCellValue(borrowCommonCustomize.getRepayOrgUserName());
//					}
//                    // UPD BY LIUSHOUYI 合规检查 END
//					else if (celLength == 43) {
//						if (new BigDecimal(borrowCommonCustomize.getBorrowExtraYield()).compareTo(BigDecimal.ZERO) > 0) {
//							cell.setCellValue(borrowCommonCustomize.getBorrowExtraYield() + "%");
//						}
//					}
//				}
//			}
//		}
//		// 导出
//		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
//	}
	/**
	 * 导出功能
	 * 
	 * @param request
	 * @param modelAndView
	 * @param form
	 */
	@ApiOperation(value = "导出功能")
	@PostMapping("/exportOptAction")
	 @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportToExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid BorrowBeanRequest form) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "借款列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
//        //请求第一页5000条
//        form.setPageSize(defaultRowMaxCount);
//        form.setCurrPage(1);

		BorrowCustomizeResponse resultList = borrowCommonService.exportBorrowList(form);
		List<BorrowCommonCustomizeVO> list = resultList.getBorrowCommonCustomizeList();
        Integer totalCount = list.size();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }
        for (int i = 1; i <= sheetCount; i++) {

        	form.setPageSize(defaultRowMaxCount);
        	form.setCurrPage(i);
			List<BorrowCommonCustomizeVO> resultList2 = borrowCommonService.paging(form,list);
            if (resultList2 != null && resultList2.size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList2);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }
	   private Map<String, String> buildMap() {
	        Map<String, String> map = Maps.newLinkedHashMap();
	        map.put("borrowNid", "项目编号");
	        map.put("planNid", "智投编号");
	        map.put("userId", "借款人ID");
	        map.put("username", "借款人用户名");
//	        map.put("applicant", "项目申请人");
	        map.put("projectName", "项目名称");
	        map.put("borrowProjectTypeName", "项目类型");
	        map.put("instName", "资产来源");
	        map.put("account", "借款金额");
	        map.put("borrowPeriod", "借款期限");
	        map.put("borrowApr", "出借利率");
	        map.put("borrowStyle", "还款方式");
	        map.put("borrowServiceScale", "放款服务费率");
	        map.put("borrowManagerScale", "还款服务费率");
	        map.put("borrowMeasuresInstit", "合作机构");
	        map.put("borrowAccountYes", "已借到金额");
	        map.put("borrowAccountWait", "剩余金额");
	        map.put("borrowAccountScale", "借款进度");
	        map.put("status", "项目状态");
	        map.put("addtime", "添加时间");
	        map.put("verifyTime", "初审通过时间");
	        map.put("ontime", "定时发标时间");
	        map.put("bookingBeginTime", "预约开始时间");
	        map.put("bookingEndTime", "预约截止时间");
	        map.put("verifyTime2", "实际发标时间");
	        map.put("borrowValidTime", "出借截止时间");
	        map.put("borrowFullTime", "满标时间");
	        map.put("reverifyTime", "复审通过时间");
	        map.put("recoverLastTime", "放款完成时间");
	        map.put("repayLastTime", "最后还款日");
	        map.put("registTime", "备案时间");
	      //  map.put("reverifyUserName", "复审人员");
	        map.put("location", "所在地区");
	        map.put("borrowerName", "借款人姓名");
	        map.put("attribute", "属性");
	        map.put("entrustedFlg", "是否受托支付");
	        map.put("entrustedUsername", "收款人用户名");
	        map.put("labelNameSrch", "标签名称");
//	        map.put("createUserName", "账户操作人");
//	        map.put("registUserName", "备案人员");
	        
	        map.put("remark", "备注");
	        map.put("createname", "添加标的人员");
	        map.put("registname", "标的备案人员");
	        map.put("repayOrgUserName", "担保机构用户名");
	        map.put("borrowExtraYield", "加息收益率");
	        return map;
	    }
	    private Map<String, IValueFormatter> buildValueAdapter() {
	        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
	        IValueFormatter entrustedFlgAdapter = new IValueFormatter() {
	            @Override
	            public String format(Object object) {
	                String entrustedFlg = (String) object;
	                if("1".equals(entrustedFlg)) {
	                	return "是";
	                }else {
	                	return "否";
	                }

	            }
	        };
	        IValueFormatter borrowExtraYieldAdapter = new IValueFormatter() {
	            @Override
	            public String format(Object object) {
	                String borrowExtraYield = (String) object;
	                if(borrowExtraYield!=null&&new BigDecimal(borrowExtraYield).compareTo(BigDecimal.ZERO) > 0) {
	                	return borrowExtraYield+"%";
	                }else {
	                	return "";
	                }
	            }
	        };
	        IValueFormatter addtimeAdapter = new IValueFormatter() {
	            @Override
	            public String format(Object object) {
	            	Date addtime = (Date) object;
	            	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            	 return formatter.format(addtime);

	            }
	        };
	        IValueFormatter statusAdapter = new IValueFormatter() {
	            @Override
	            public String format(Object object) {
	            	Integer status = Integer.valueOf(String.valueOf(object));
	            	if(status==null){
	            		return "";
	            	}else if(status==0) {
	                	return "备案中";
	                }else if(status==1) {
	                	return "初审中";
	                }else if(status==2) {
	                	return "出借中";
	                }else if(status==3) {
	                	return "复审中";
	                }else if(status==4) {
	                	return "还款中";
	                }else if(status==5) {
	                	return "已还款";
	                }else if(status==6) {
	                	return "流标";
	                }else if(status==7) {
	                	return "受托";
	                }else {
	                	return "";
	                }
	            }
	        };
// 0备案中,1初审中,2出借中,3复审中(满标),4还款中,5已还款,6流标,7受托 
	        mapAdapter.put("entrustedFlg", entrustedFlgAdapter);
	        mapAdapter.put("borrowExtraYield", borrowExtraYieldAdapter);
	        mapAdapter.put("status", statusAdapter);
	        mapAdapter.put("addtime", addtimeAdapter);
	        return mapAdapter;
	    }
}