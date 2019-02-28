/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.beans.request.ProtocolsRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.ProtocolsService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.FddTempletCustomizeResponse;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.FddTempletCustomizeVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.pay.lib.fadada.bean.DzqzCallBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author fuqiang
 * @version ProtocolsController, v0.1 2018/7/10 16:03
 */
@Api(tags = "配置中心-协议管理")
@RestController
@RequestMapping("/hyjf-admin/protocols")
public class ProtocolsController extends BaseController {
	@Autowired
	private ProtocolsService protocolsService;

	public static final String PERMISSIONS = "protocols";

	@Autowired
	private SystemConfig systemConfig;

	@ApiOperation(value = "展示协议管理列表", notes = "展示协议管理列表")
	@PostMapping("/init")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<ListResult<FddTempletCustomizeVO>> selectFddTempletList(
			@RequestBody ProtocolsRequestBean request) {
		FddTempletCustomizeResponse response = protocolsService.selectFddTempletList(request);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

	@ApiOperation(value = "添加协议管理", notes = "添加协议管理")
	@PostMapping("/insert")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
	public AdminResult insert(@RequestBody ProtocolsRequestBean requestBean, HttpServletRequest request) {
		AdminSystemVO user = getUser(request);
		requestBean.setCreateUserId(Integer.valueOf(user.getId()));
		requestBean.setCreateUserName(user.getUsername());
		FddTempletCustomizeResponse response = protocolsService.insertAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "修改协议管理", notes = "修改协议管理")
	@PostMapping("/update")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
	public AdminResult update(@RequestBody ProtocolsRequestBean requestBean, HttpServletRequest request) {
		AdminSystemVO user = getUser(request);
		requestBean.setCreateUserId(Integer.valueOf(user.getId()));
		requestBean.setCreateUserName(user.getUsername());
		FddTempletCustomizeResponse response = protocolsService.updateAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "画面迁移(含有id更新，不含有id添加)", notes = "画面迁移(含有id更新，不含有id添加)")
	@PostMapping("/infoAction")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult info(@RequestBody ProtocolsRequestBean requestBean) {
		logger.info(ProtocolsController.class.toString(), "infoAction");
		FddTempletCustomizeResponse response = new FddTempletCustomizeResponse();
		if (requestBean.getId() != null) {
			response = this.protocolsService.getRecordInfo(requestBean.getId());
			// modify by libin sonar start 非空判断拿到此处
			if (response == null) {
				return new AdminResult<>(FAIL, FAIL_DESC);
			}
			// modify by libin sonar end
		}
		response.setProtocolsForm(response.getResult());
		// 协议类型下拉
		List<ParamNameVO> paramNameList = this.protocolsService.getParamNameList("PROTOCOL_TYPE");
		response.setProtocolTypeList(paramNameList);
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		logger.info(ProtocolsController.class.toString(), "infoAction");
		return new AdminResult<>(response);
	}


	@ApiOperation(value = "取得新规的模板编号", notes = "取得新规的模板编号")
	@GetMapping("/getTempletId")
        public AdminResult protocolTypeAction(@RequestParam(value = "protocolType") Integer requestBean) {
		FddTempletCustomizeResponse response = new FddTempletCustomizeResponse();
		String templetId = protocolsService.getNewTempletId(requestBean);
		response.setTempletId(templetId);
		response.setCount(1);
		if (templetId == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(response);
	}

	/**
	 * 资料上传
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "pdf文件上传", notes = "pdf文件上传")
	@PostMapping(value = "uploadFile")
	public AdminResult<LinkedList<BorrowCommonImage>> uploadFile(@RequestParam("file") MultipartFile file,HttpServletRequest request) throws Exception {

		AdminResult<LinkedList<BorrowCommonImage>> adminResult = new AdminResult<>();
		// 错误信息对象
		// request转换为MultipartHttpServletRequest
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);

		String templetId = multipartRequest.getParameter("templetId");
		logger.info("---------------法大大协议开始上传模板----------，协议类型ID：" + templetId);
		// ======上传校验=======
		if (templetId == null || templetId.isEmpty() || "undefined".equals(templetId)){
			adminResult.setStatus(FAIL);
			adminResult.setStatusDesc("协议类型必须选择。");
			return adminResult;
		}
		try {

			if (file == null) {
				adminResult.setStatus(FAIL);
				logger.info("---------------法大大协议上传模板,获取上传模板失败！");
				adminResult.setStatusDesc("获取上传模板失败！");
				return adminResult;
			}
			String fileName = file.getOriginalFilename();
			if (!(fileName.substring(fileName.length() - 4)).toUpperCase().equals(".PDF")) {
				adminResult.setStatus(FAIL);
				adminResult.setStatusDesc("上传的模板必须是PDF格式。");
				logger.info("---------------法大大协议上传模板,上传的模板必须是PDF格式！");
				return adminResult;
			}
			// ======调模板上传FTP服务器=======
			String httpUrl = this.protocolsService.uploadTempletToFtp(file, "FddTemplet", 0);
			if (StringUtils.isBlank(httpUrl)) {
				adminResult.setStatus(FAIL);
				adminResult.setStatusDesc("上传FTP服务器失败。");
				logger.info("---------------法大大协议上传模板,上传FTP服务器失败！");
				return adminResult;
			}
			logger.info("模板下载地址打印，模板编号：{}，下载地址：{}",templetId,httpUrl);

			String userId = null;
			AdminSystemVO user = getUser(request);
			if (user != null) {
				userId = user.getId();
			}
			//调用发大大模板上传接口
			DzqzCallBean dzqzCallBean = this.protocolsService.uploadtemplateDZApi(httpUrl, templetId, userId);
			if (dzqzCallBean == null) {
				adminResult.setStatus(FAIL);
				adminResult.setStatusDesc("返回结果为空，上传模板失败。");
				logger.info("---------------法大大协议上传模板,返回结果为空，上传模板失败！");
				return adminResult;
			}else{
                String result = dzqzCallBean.getResult();
                String code = dzqzCallBean.getCode();
                if("success".equals(result)){
					LinkedList<BorrowCommonImage> files = new LinkedList<>();

					BorrowCommonImage fileMeta = new BorrowCommonImage();
					String originalFilename = file.getOriginalFilename();
					String fileRealName = String.valueOf(new Date().getTime());
					String  suf = UploadFileUtils.getSuffix(file.getOriginalFilename());
					fileRealName = fileRealName + suf;
					int index = originalFilename.lastIndexOf(".");
					if (index != -1) {
						fileMeta.setImageName(originalFilename.substring(0, index));
					} else {
						fileMeta.setImageName(originalFilename);
					}

					fileMeta.setImageRealName(fileRealName);
					fileMeta.setImageSize(file.getSize() / 1024 + "");// KB
					fileMeta.setImageType(file.getContentType());
					// 获取文件路径
					String fileUploadTempPath = systemConfig.getFileUplodeTempPath();
					String fileDomainUrl = systemConfig.getFileDomainUrl();

					fileMeta.setImagePath(fileUploadTempPath + fileRealName);
					fileMeta.setImageSrc(fileDomainUrl + fileUploadTempPath + fileRealName);
					files.add(fileMeta);
					adminResult.setData(files);
					adminResult.setStatus(SUCCESS);
					adminResult.setStatusDesc(SUCCESS_DESC);
					try {
						// 上传成功后将模板下载地址存入redis
						String key = RedisConstants.TEMPLATE_UPLOAD_URL + templetId;
						RedisUtils.set(key,httpUrl);
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("模板下载地址存入redis失败！");
					}
					return adminResult;
                }else{
					adminResult.setStatus(FAIL);
					adminResult.setStatusDesc(dzqzCallBean.getMsg());
					return adminResult;

                }
            }



		}catch (Exception e){
			e.printStackTrace();
			logger.info("--------------法大大协议模板上传异常------------");
		}

		adminResult.setStatus(FAIL);
		adminResult.setStatusDesc("未获取到上传文件！。");
		return adminResult;

	}

	/**
	 * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
	 * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
	 * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
//	@ApiOperation(value = "导出excel", notes = "导出excel")
//	@PostMapping("/exportaction")
//	public void exportExcel(@ModelAttribute ProtocolsRequestBean form, HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		// 表格sheet名称
//		String sheetName = "协议管理";
//		// 文件名称
//		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
//		// 需要输出的结果列表
//		List<FddTempletCustomizeVO> recordList = new ArrayList<>();
//		FddTempletCustomizeResponse fddResponse = this.protocolsService.selectFddTempletList(form);
//		if(fddResponse != null) {
//			recordList = fddResponse.getResultList();
//		}
//		String[] titles = new String[] { "序号", "模版编号", "协议类型", "启用状态", "CA认证", "认证时间", "操作人", "操作时间", "备注" };
//		// 声明一个工作薄
//		HSSFWorkbook workbook = new HSSFWorkbook();
//		// 生成一个表格
//		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
//
//		if (recordList != null && recordList.size() > 0) {
//
//			int sheetCount = 1;
//			int rowNum = 0;
//
//			for (int i = 0; i < recordList.size(); i++) {
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
//					FddTempletCustomizeVO data = recordList.get(i);
//					// 创建相应的单元格
//					Cell cell = row.createCell(celLength);
//					if (celLength == 0) {// 序号
//						cell.setCellValue(i + 1);
//					} else if (celLength == 1) {// 模版编号
//						cell.setCellValue(data.getTempletId());
//					} else if (celLength == 2) {// 协议类型
//						cell.setCellValue(data.getProtocolTypeName());
//					} else if (celLength == 3) {// 启用状态
//						cell.setCellValue(data.getIsActive().compareTo(1) == 0 ? "启用" : "关闭");
//					} else if (celLength == 4) {// CA认证
//						cell.setCellValue(data.getCaFlagName());
//					} else if (celLength == 5) {// 认证时间
//						if (data.getCertificateTime() == null){
//							cell.setCellValue("");
//						}else{
//							cell.setCellValue(GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(data.getCertificateTime()));
//						}
//					} else if (celLength == 6) {// 操作人
//						cell.setCellValue(data.getCreateUserName());
//					} else if (celLength == 7) {// 操作时间
//						Long time = data.getCreateTime().getTime() / 1000;
//						cell.setCellValue(GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(time.intValue()));
//					} else if (celLength == 8) {// 备注
//						cell.setCellValue(data.getRemark());
//					}
//				}
//			}
//		}
//		// 导出
//		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
//	}
	/**
	 * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
	 * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
	 * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ApiOperation(value = "导出excel", notes = "导出excel")
	@PostMapping("/exportaction")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
	 public void exportToExcel(@ModelAttribute ProtocolsRequestBean form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	        //sheet默认最大行数
	        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
	        // 表格sheet名称
	        String sheetName = "协议管理";
	        // 文件名称
	        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
	        // 声明一个工作薄
	        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
	        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();


			// 需要输出的结果列表
			List<FddTempletCustomizeVO> recordList = new ArrayList<>();
			form.setPageSize(defaultRowMaxCount);
			form.setCurrPage(1);
			FddTempletCustomizeResponse fddResponse = this.protocolsService.selectFddTempletList(form);
			if(fddResponse != null) {
				recordList = fddResponse.getResultList();
			}
	        Integer totalCount = fddResponse.getCount();

	        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
	        int minId = 0;
	        Map<String, String> beanPropertyColumnMap = buildMap();
	        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
	        String sheetNameTmp = sheetName + "_第1页";
	        if (totalCount == 0) {
	            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
	        }else {
				helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, recordList);
	        }
	        for (int i = 1; i < sheetCount; i++) {

	        	form.setPageSize(defaultRowMaxCount);
	        	form.setCurrPage(i+1);
	        	FddTempletCustomizeResponse fddResponse2 = this.protocolsService.selectFddTempletList(form);
	            if (fddResponse2 != null && fddResponse2.getResultList() != null && fddResponse2.getResultList().size()> 0) {
	                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
	                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  fddResponse2.getResultList());
	            } else {
	                break;
	            }
	        }
	        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
	    }

	    private Map<String, String> buildMap() {
	        Map<String, String> map = Maps.newLinkedHashMap();
	        map.put("templetId", "模版编号");
	        map.put("protocolTypeName", "协议类型");
	        map.put("isActive", "启用状态");
	        map.put("caFlagName", "CA认证");
	        map.put("certificateTime", "认证时间");
	        map.put("createUserName", "操作人");
	        map.put("createTime", "操作时间");
	        map.put("remark", "备注");
	        return map;
	    }
	    private Map<String, IValueFormatter> buildValueAdapter() {
	        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
	        IValueFormatter isActiveAdapter = new IValueFormatter() {
	            @Override
	            public String format(Object object) {
	                Integer isActive = (Integer) object;
					return  isActive.compareTo(1) == 0 ? "启用" : "关闭";
	            }
	        };
	        IValueFormatter certificateTimeAdapter = new IValueFormatter() {
	            @Override
	            public String format(Object object) {
	            	Integer certificateTime = (Integer) object;
	                return GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(certificateTime);
	            }
	        };
	        IValueFormatter createTimeAdapter = new IValueFormatter() {
	            @Override
	            public String format(Object object) {
					Date createTime = (Date) object;
					return GetDate.date2Str(createTime, GetDate.date_sdf);
	            }
	        };
	     
	        mapAdapter.put("isActive", isActiveAdapter);
	        mapAdapter.put("certificateTime", certificateTimeAdapter);
	        mapAdapter.put("createTime", createTimeAdapter);
	        return mapAdapter;
	    }
}
