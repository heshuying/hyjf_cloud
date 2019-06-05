/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.datacenter.electricitySales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.ElectricitySalesDataPushListService;
import com.hyjf.am.response.user.ElectricitySalesDataPushListResponse;
import com.hyjf.am.resquest.config.ElectricitySalesDataPushListRequest;
import com.hyjf.am.vo.config.ElectricitySalesDataPushListVO;
import com.hyjf.common.file.UploadFileUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author: dzs
 * @version: ElectricitySalesDataPushListController, v0.1 2018/8/15 14:08
 */
@Api(tags = "数据中心-电销数据推送记录")
@RestController
@RequestMapping("/hyjf-admin/electricitySales")
public class ElectricitySalesDataPushListController  extends BaseController {
	/** 权限 */
	public static final String PERMISSIONS = "DATACENTERCOUPON";
    @Autowired
    private  ElectricitySalesDataPushListService electricitySalesDataPushListService;

   
    /**
     * 线下修改信息同步查询列表list
     * @auth dzs
     * @param
     * @return
     */
    @ApiOperation(value = "list查询",notes = "list查询")
    @PostMapping(value = "/electricitySalesDataPushList")
	@AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_VIEW , ShiroConstants.PERMISSION_SEARCH})
    public AdminResult<ElectricitySalesDataPushListResponse> searchModifyInfoList(@RequestBody ElectricitySalesDataPushListRequest request){
        return new AdminResult<ElectricitySalesDataPushListResponse>(electricitySalesDataPushListService.searchList(request));
    }

    /**
	 * 借款内容填充
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = " 插入多条信息")
	@PostMapping("/insertAlectricitySalesDataPushList")
	public AdminResult<ElectricitySalesDataPushListResponse> insertAlectricitySalesDataPushList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		ElectricitySalesDataPushListRequest eRequest =new ElectricitySalesDataPushListRequest();
		List<ElectricitySalesDataPushListVO> electricitySalesDataPushList=new ArrayList<ElectricitySalesDataPushListVO>();
		
		MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest)request;

		Iterator<String> itr = multipartRequest.getFileNames();
		MultipartFile multipartFile = null;
		

		while (itr.hasNext()) {
			multipartFile = multipartRequest.getFile(itr.next());
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
						if(StringUtils.isEmpty(this.getValue(hssfRow.getCell(2)))){
							continue;
						}
						ElectricitySalesDataPushListVO vo=new ElectricitySalesDataPushListVO();
						vo.setOwnerUserName(this.getValue(hssfRow.getCell(1)));
						vo.setUserName(this.getValue(hssfRow.getCell(2)));
						electricitySalesDataPushList.add(vo);
					}
				}
			}
		}
		
		eRequest.setElectricitySalesDataPushList(electricitySalesDataPushList);
		return  new AdminResult<ElectricitySalesDataPushListResponse>(electricitySalesDataPushListService.insertElectricitySalesDataPushList(eRequest));
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
}
