package com.hyjf.admin.controller.borrow;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.BorrowCommonService;
import com.hyjf.admin.service.CustomerTransferService;
import com.hyjf.am.bean.commonimage.BorrowCommonImage;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BorrowCommonResponse;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.admin.BorrowCommonRequest;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowCommonCarVO;
import com.hyjf.am.vo.trade.borrow.BorrowCommonCompanyAuthenVO;
import com.hyjf.am.vo.trade.borrow.BorrowCommonNameAccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowCommonVO;
import com.hyjf.am.vo.trade.borrow.BorrowHousesVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectRepayVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.user.AdminPreRegistListVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author GOGTZ-Z
 * @version V1.0  
 * @package com.hyjf.admin.maintenance.AdminPermissions
 * @date 2015/07/09 17:00
 */

@Api(value = "借款增加",tags ="借款增加")
@RestController
@RequestMapping("/hyjf-admin/borrow/borrowcommon")
public class BorrowCommonController extends BaseController {


	@Autowired
	private BorrowCommonService borrowCommonService;
	@Autowired
	private  CustomerTransferService  customerTransferService;

	/**
     * 迁移到详细画面
     *
     * @param request
     * @param form
     * @return
     */
	@ApiOperation(value = "查询信息")
	@PostMapping("/infoAction")
    public AdminResult<BorrowCommonResponse>  moveToInfoAction(@RequestBody @Valid BorrowCommonRequest borrowCommonRequest) {
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
	public  AdminResult<BorrowCommonResponse> insertAction(@RequestBody @Valid BorrowCommonRequest borrowCommonRequest) throws Exception {
		
		BorrowCommonResponse bcr = borrowCommonService.insertAction(borrowCommonRequest);
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
	public AdminResult isExistsUser(@PathVariable  String userId) {

		int usersFlag=this.borrowCommonService.isExistsUser(userId);
		if (usersFlag == 1) {
			return new AdminResult<>(FAIL, "用户名不存在");
		} else if (usersFlag == 2) {
			return new AdminResult<>(FAIL, "用户没有账号");
		} else if (usersFlag == 3) {
			return new AdminResult<>(FAIL, "用户被禁用");
		} else if (usersFlag == 4) {
			return new AdminResult<>(FAIL, "借款人用户名必须是借款人账户");
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
	public AdminResult isExistsApplicant(@PathVariable  String applicant) {
		  AdminSystemResponse bcs = this.borrowCommonService.isExistsApplicant(applicant);
			if (!Response.isSuccess(bcs)) {
				return new AdminResult<>(FAIL, "项目申请人不存在");

			}
			return new AdminResult();
	}

//	/**
//	 * 验证发标金额是否合法
//	 *
//	 * @param request
//	 * @return
//	 */
	@ApiOperation(value = "验证发标金额是否合法")
	@PostMapping("/isAccountLegal")
	public AdminResult isAccountLegal(@PathVariable  String accountM) {

		BigDecimal account = new BigDecimal(accountM);
		BigDecimal increaseMoney = new BigDecimal("100");
		if (account.compareTo(increaseMoney) < 0) {
			// 发标金额应大于递增金额
			return new AdminResult<>(FAIL, "发标金额应大于递增金额");
		}
		// 不是100的整数倍
		if (account.divideAndRemainder(increaseMoney)[1].compareTo(BigDecimal.ZERO) != 0) {
			// 发标金额应大于递增金额
			return new AdminResult<>(FAIL, "发标金额应大于递增金额");
		}
		return new AdminResult();
	}


	/**
	 * 垫付机构用户名是否存在
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "垫付机构用户名是否存在")
	@PostMapping("/isRepayOrgUser")
	public AdminResult isRepayOrgUser(@PathVariable String userName) {
		 List<UserVO> user = this.borrowCommonService.selectUserByUsername(userName);
		if(!user.isEmpty()) {
			if(user.get(0).getBankOpenAccount()==1) {
				 UserInfoVO userinfo = borrowCommonService.findUserInfoById(user.get(0).getUserId());
				if(userinfo.getRoleId()!=3) {
					return new AdminResult<>(FAIL, "请填写用户角色为垫付机构的已开户用户！！");
				}
			}else {
				return new AdminResult<>(FAIL, "请填写用户角色为垫付机构的已开户用户！");
			}
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
		Map<String, String> data = null;
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
		Map<String, String> data = null;
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
	public AdminResult isExistsBorrowPreNidRecord(@PathVariable  String borrowPreNid) {
//		LogUtil.startLog(BorrowCommonController.class.toString(), BorrowCommonDefine.ISEXISTSBORROWPRENIDRECORD);
//		String message = this.borrowCommonService.isExistsBorrowPreNidRecord(request);
//		LogUtil.endLog(BorrowCommonController.class.toString(), BorrowCommonDefine.ISEXISTSBORROWPRENIDRECORD);
		boolean borrowPreNidFlag = borrowCommonService.isExistsBorrowPreNidRecord(borrowPreNid);
		if(borrowPreNidFlag) {
			return new AdminResult<>();
		}else {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}

	}

	/**
	 * 获取融资服务费率 & 账户管理费率
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "获取融资服务费率 & 账户管理费率")
	@PostMapping("/getBorrowServiceScale")
	public AdminResult<String> getBorrowServiceScale(@RequestBody @Valid BorrowCommonRequest borrowCommonRequest) {
		String ncv = this.borrowCommonService.getBorrowServiceScale(borrowCommonRequest.getBorrowPeriod(), borrowCommonRequest.getBorrowStyle(), borrowCommonRequest.getProjectType(), borrowCommonRequest.getInstCode());
		return new AdminResult<String>(ncv);
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
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
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
			String fileRealName = String.valueOf(new Date().getTime());
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

		String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

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
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);

		Iterator<String> itr = multipartRequest.getFileNames();
		MultipartFile multipartFile = null;

		List<BorrowCommonCarVO> borrowCarinfoList = new ArrayList<BorrowCommonCarVO>();

		while (itr.hasNext()) {
			multipartFile = multipartRequest.getFile(itr.next());
			String fileRealName = String.valueOf(new Date().getTime());
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

		String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

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
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);

		Iterator<String> itr = multipartRequest.getFileNames();
		MultipartFile multipartFile = null;

		List<BorrowHousesVO> recordList = new ArrayList<BorrowHousesVO>();

		while (itr.hasNext()) {
			multipartFile = multipartRequest.getFile(itr.next());
			String fileRealName = String.valueOf(new Date().getTime());
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

		String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

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
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);

		Iterator<String> itr = multipartRequest.getFileNames();
		MultipartFile multipartFile = null;

		List<BorrowCommonCompanyAuthenVO> recordList = new ArrayList<BorrowCommonCompanyAuthenVO>();

		while (itr.hasNext()) {
			multipartFile = multipartRequest.getFile(itr.next());
			String fileRealName = String.valueOf(new Date().getTime());
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
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);

		Iterator<String> itr = multipartRequest.getFileNames();
		MultipartFile multipartFile = null;

		//返回结果
		Map<String,String> resultMap = new HashMap<String,String>();

		while (itr.hasNext()) {
			multipartFile = multipartRequest.getFile(itr.next());
			String fileRealName = String.valueOf(new Date().getTime());
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
						}else if(rowNum == 2){//垫付机构用户名
							resultMap.put("repayOrgName", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 3){//项目标题
							resultMap.put("projectName", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 4){//借款标题
							resultMap.put("jkName", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 5){//借款金额
							resultMap.put("account", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 6){//年化收益
							resultMap.put("borrowApr", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 7){//融资用途
							resultMap.put("financePurpose", this.getValue(hssfRow.getCell(1)));
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
							resultMap.put("housesType", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 22){//建筑面积
							resultMap.put("housesArea", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 23){//资产数量
							resultMap.put("housesCnt", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 24){//评估价值（元）
							resultMap.put("housesToprice", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 25){//资产所属
							resultMap.put("housesBelong", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 26){//借款类型
							resultMap.put("companyOrPersonal", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 27){//融资主体
							resultMap.put("comName", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 28){//法人
							resultMap.put("comLegalPerson", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 29){//注册地区
							resultMap.put("comLocationCity", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 30){//主营业务
							resultMap.put("comMainBusiness", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 31){//在平台逾期次数
							resultMap.put("comOverdueTimes", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 32){//在平台逾期金额
							resultMap.put("comOverdueAmount", this.getValue(hssfRow.getCell(1)));
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
							resultMap.put("old", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 42){//岗位职业
							resultMap.put("position", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 43){//性别
							resultMap.put("sex", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 44){//婚姻状况
							resultMap.put("merry", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 45){//工作城市
							resultMap.put("location_c", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 46){//户籍地
							resultMap.put("domicile", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 47){//在平台逾期次数
							resultMap.put("overdueTimes", this.getValue(hssfRow.getCell(1)));
						}else if(rowNum == 48){//在平台逾期金额
							resultMap.put("overdueAmount", this.getValue(hssfRow.getCell(1)));
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

				String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

				String[] titles = new String[] { "" };
				// 声明一个工作薄
				HSSFWorkbook workbook = new HSSFWorkbook();

				// 生成一个表格
				HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName);

				int rowNum = 0;
				for (int i = 0; i < 87; i++) {
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
						}else if(rowNum == 2){//垫付机构用户名
							if (celLength == 0) {
								cell.setCellValue("垫付机构用户名");
							}else if (celLength == 1) {
								cell.setCellValue("");
							}
						}else if(rowNum == 3){//项目标题
							if (celLength == 0) {
								cell.setCellValue("项目标题");
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
						}else if(rowNum == 6){//年化收益
							if (celLength == 0) {
								cell.setCellValue("年化收益");
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
	@PostMapping("/getProductTypeAction/{instCode}")
	public AdminResult<BorrowCommonResponse> getProductTypeAction(@PathVariable  String instCode) {
		BorrowCommonResponse bcr=borrowCommonService.getProductTypeAction(instCode);
		return new AdminResult<BorrowCommonResponse>(bcr);
	}

	/**
	 * 受托用户是否存在
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = " 受托用户是否存在")
	@PostMapping("/isEntrustedExistsUser/{userName}")
	public int isEntrustedExistsUser(@PathVariable  String userName) {
		return this.borrowCommonService.isEntrustedExistsUser(userName);
	}

	/**
	 * 借款主体CA认证check
	 * 
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "借款主体CA认证check")
	@PostMapping("/isBorrowUserCACheck")
	public boolean isBorrowUserCACheck(@PathVariable  String param,@PathVariable  String name) {


		boolean ret = this.borrowCommonService.isBorrowUserCACheck(param, name);
		return ret;
	}

	/**
	 * 社会信用代码或身份证号CA认证check
	 * 
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "借款主体CA认证check")
	@PostMapping("/isCAIdNoCheck")
	public boolean isCAIdNoCheck(@PathVariable  String param,@PathVariable  String name) {
		boolean ret = this.borrowCommonService.isCAIdNoCheck(param, name);

		return ret;
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
		if (hssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			// 返回布尔类型的值
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			// 返回数值类型的值
			return String.valueOf(hssfCell.getNumericCellValue());
		} else if (hssfCell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			// 单元格为公式类型时
			if (hssfCell.getCachedFormulaResultType() == Cell.CELL_TYPE_NUMERIC) {
				// 返回数值类型的值
				return String.valueOf(hssfCell.getNumericCellValue());
			}else{
				// 返回字符串类型的值
				return String.valueOf(hssfCell.getStringCellValue());
			}
		} else {
			// 返回字符串类型的值
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}
}