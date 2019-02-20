/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.hjhcommission;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.HjhCommissionViewRequest;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.*;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhCommissionResponse;
import com.hyjf.am.resquest.admin.HjhCommissionRequest;
import com.hyjf.am.vo.admin.TenderCommissionVO;
import com.hyjf.am.vo.trade.hjh.HjhCommissionCustomizeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author libin
 * @version HjhCommissionController.java, v0.1 2018年8月7日 下午2:38:45
 */
@Api(value = "资金中心-汇计划提成列表",tags = "资金中心-汇计划提成列表")
@RestController
@RequestMapping("/hyjf-admin/hjhcommission")
public class HjhCommissionController extends BaseController{

	@Autowired
	private HjhCommissionService hjhCommissionService;

	@Autowired
	private AccedeListService accedeListService;

	@Autowired
	BankAccountManageService bankAccountManageService;

	@Autowired
	private BankMerchantAccountService bankMerchantAccountService;

	@Autowired
	private AdminTransferExceptionLogService transferLogService;

	@Autowired
	private SystemConfig systemConfig;

	/*权限*/
	public static final String PERMISSIONS = "pushMoneyListHJH";

	private static final Logger logger = LoggerFactory.getLogger(HjhCommissionController.class);
	/*类名*/
	private static final String THIS_CLASS = HjhCommissionController.class.getName();

	/**
	 * 列表查询(初始无参/查询带参 共用)    已测试
	 *
	 * @param request
	 * @return 进入汇计划提成列表
	 */
	@ApiOperation(value = "汇计划提成列表查询", notes = "汇计划提成列表查询")
	@PostMapping(value = "/search")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<ListResult<HjhCommissionCustomizeVO>> init(HttpServletRequest request, @RequestBody @Valid HjhCommissionViewRequest viewRequest) {
		// 初始化原子层请求实体
		HjhCommissionRequest form = new HjhCommissionRequest();
		// 初始化返回LIST
		List<HjhCommissionCustomizeVO> returnList = null;
		// 将画面检索参数request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		// 默认为汇计划类的出借
		form.setTenderType(2);
		// 列表查询
		HjhCommissionResponse response = hjhCommissionService.selectHjhCommissionList(form);
		if(response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		if(CollectionUtils.isNotEmpty(response.getResultList())){
			// 将原子层返回集合转型为组合层集合用于返回 response为原子层 AssetListCustomizeVO，在此转成组合层AdminAssetListCustomizeVO
			returnList = CommonUtils.convertBeanList(response.getResultList(), HjhCommissionCustomizeVO.class);
			return new AdminResult<ListResult<HjhCommissionCustomizeVO>>(ListResult.build(returnList, response.getCount()));
		} else {
			return new AdminResult<ListResult<HjhCommissionCustomizeVO>>(ListResult.build(returnList, 0));
		}
	}

	/**
	 * 汇计划提成列表 加入金额/提成金额 累计             已测试
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "汇计划提成列表 加入金额/提成金额 累计", notes = "汇计划提成列表 加入金额/提成金额 累计")
	@PostMapping(value = "/sum")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public JSONObject getSumTotal(HttpServletRequest request, @RequestBody @Valid HjhCommissionViewRequest viewRequest) {
		JSONObject jsonObject = new JSONObject();
		// 初始化原子层请求实体
		HjhCommissionRequest form = new HjhCommissionRequest();
		// 将画面检索参数request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		// 默认为汇计划类的出借
		form.setTenderType(2);
		HjhCommissionResponse response = hjhCommissionService.selecthjhCommissionTotal(form);
		if(response == null) {
			jsonObject.put("error", FAIL);
			return jsonObject;
		}
		if (!Response.isSuccess(response)) {
			jsonObject.put("error", FAIL);
			return jsonObject;
		}
/*		if(response.getTotalMap() == null || response.getTotalMap().isEmpty()){
			jsonObject.put("error", FAIL);
			return jsonObject;
		}*/
		jsonObject.put("totalMap", response.getTotalMap());
		jsonObject.put("status", SUCCESS);
		return jsonObject;
	}

	/**
	 * 汇计划提成列表-校验发提成状态是不是已经发放   已测试
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "汇计划提成列表-校验发提成状态是不是已经发放", notes = "汇计划提成列表-校验发提成状态是不是已经发放")
	@PostMapping(value = "/checkstatus")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public JSONObject checkStatusAction(HttpServletRequest request , @RequestBody @Valid HjhCommissionViewRequest viewRequest) {
		JSONObject jsonObject = new JSONObject();
		int ids = 0;
		int status = 0;
		if(viewRequest.getIds() == 0){
			// 前端未传
			jsonObject.put("error", "请传入ids!");
		} else {
			ids = viewRequest.getIds();
		}
		TenderCommissionVO tenderCommission = this.hjhCommissionService.queryTenderCommissionByPrimaryKey(ids);
		if(tenderCommission != null){
			// 0:未发放;1:已发放;100:删除
			status = tenderCommission.getStatus();
			jsonObject.put("msg", "state : 0:未发放;1:已发放;100:删除");
			jsonObject.put("state", status);
			jsonObject.put("status", SUCCESS);
		} else {
			jsonObject.put("error", "未查询到该记录！");
			jsonObject.put("status", FAIL);
		}
		return jsonObject;
	}

	/**
	 * 发提成跳转展示页面(跳转详情画面) 前端自己传也行
	 * @param request
	 * @param form
	 * @return
	 */
/*	@ApiOperation(value = "发提成跳转展示页面(跳转详情画面)", notes = "发提成跳转展示页面(跳转详情画面)")
	@PostMapping(value = "/showpage")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)*/
/*	public JSONObject showPageAction(HttpServletRequest request , @RequestBody @Valid HjhCommissionViewRequest viewRequest) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ids", viewRequest.getIds());
		jsonObject.put("planOrderId", viewRequest.getPlanOrderId());
		jsonObject.put("planNid", viewRequest.getPlanNid());
		jsonObject.put("borrowStyle", viewRequest.getBorrowStyle());
		if(StringUtils.isNotEmpty(viewRequest.getLockPeriod())){
			jsonObject.put("lockPeriod", viewRequest.getLockPeriod().replace(",",""));
		}
		if(StringUtils.isNotEmpty(viewRequest.getUsername())){
			jsonObject.put("username", viewRequest.getUsername().replace(",",""));
		}
		jsonObject.put("commission", viewRequest.getCommission());
		jsonObject.put("expectApr", viewRequest.getExpectApr());
		jsonObject.put("countInterestTime", viewRequest.getCountInterestTime());
		
		jsonObject.put("status", SUCCESS);
		
		return jsonObject;
	}*/

	/**
	 * 带条件导出
	 * 1.无法指定相应的列的顺序，
	 * 2.无法配置，excel文件名，excel sheet名称
	 * 3.目前只能导出一个sheet
	 * 4.列的宽度的自适应，中文存在一定问题
	 * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
	 * @param request
	 * @return 带条件导出         已测试
	 */
	// @ApiOperation(value = "汇计划提成列表带条件导出EXCEL", notes = "带条件导出EXCEL")
	// @PostMapping(value = "/export")
	// @ResponseBody
	// @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
	/*public void exportExcelBack(HttpServletRequest request, HttpServletResponse response,@RequestBody HjhCommissionViewRequest viewRequest) throws Exception {
		// 表格sheet名称
		String sheetName = "汇计划提成列表";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		// 初始化原子层请求实体
		HjhCommissionRequest form = new HjhCommissionRequest();
		// 初始化返回LIST
		List<HjhCommissionCustomizeVO> returnList = null;
		// 将画面检索参数request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		// 默认为汇计划类的出借
		form.setTenderType(2);
		// 列表查询
		HjhCommissionResponse res = hjhCommissionService.selectHjhCommissionList(form);
		if(res != null) {
			returnList = res.getResultList();
		}
		// 列头
        String[] titles =
                new String[] { "序号", "加入订单号", "计划编号", "还款方式", "锁定期", "预期出借利率率", "提成人", "提成人真实姓名", "提成人用户属性(出借时)", "出借人用户名",
                		"出借人用户属性(出借时)",
                		"加入金额", "提成金额",
                        "提成发放状态", "提成发放时间" ,"计划订单加入时间","计划订单锁定时间"};
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, fileName + "_第1页");

        if (returnList != null && returnList.size() > 0) {
            int sheetCount = 1;
            int rowNum = 0;
            for (int i = 0; i < returnList.size(); i++) {
                rowNum++;
                if (i != 0 && i % 60000 == 0) {
                    sheetCount++;
                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, (fileName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }
                // 新建一行
                Row row = sheet.createRow(rowNum);
                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                	HjhCommissionCustomizeVO bean = returnList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);

                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    //计划订单号
                    else if (celLength == 1) {
                        cell.setCellValue(bean.getOrdid());
                    }
                    //计划编号
                    else if (celLength == 2) {
                        cell.setCellValue(bean.getBorrowNid());
                    }
                    //还款方式
                    else if (celLength == 3) {
                    	String str = "";
                    	if ("month".equals(bean.getBorrowStyleHjh())) {
							str = "等额本息";
						}else if("season".equals(bean.getBorrowStyleHjh())) {
							str = "按季还款";
						}else if("end".equals(bean.getBorrowStyleHjh())) {
							str = "按月计息，到期还本还息";
						}else if("endmonth".equals(bean.getBorrowStyleHjh())) {
							str = "先息后本";
						}else if("endday".equals(bean.getBorrowStyleHjh())) {
							str = "按天计息，到期还本还息";
						}else if("endmonths".equals(bean.getBorrowStyleHjh())) {
							str = "按月付息到期还本";
						}else if("principal".equals(bean.getBorrowStyleHjh())) {
							str = "等额本金";
						}
                        cell.setCellValue(str);
                    }
                    //锁定期
                    else if (celLength == 4) {
                    	String str = "";
                    	if (bean.getLockPeriod()==null) {
                    		cell.setCellValue(str);
                    	}else {
	                		if ("1".equals(bean.getIsMonth())) {
	                			str = "个月";
	                		}else if("0".equals(bean.getIsMonth())) {
	                			str = "天";
	                		}
	                		cell.setCellValue(bean.getLockPeriod() + str);}
                    }
                  //预期出借利率率
                    else if (celLength == 5) {
                    	cell.setCellValue( bean.getExpectApr().toString()+" %");
                    }

                   //提成人
                    else if (celLength == 6) {
                    	//UsernameTender 是出借人用户名
                        *//*cell.setCellValue(bean.getUsernameTender());*//*
                        // Username是 提成人用户名
                        cell.setCellValue(bean.getUsername());
                    }

                  //提成人真实姓名
                    else if (celLength == 7) {
                    	cell.setCellValue(bean.getTrueNameTender());
                    }
                  //提成人用户属性（出借时）
                    else if (celLength == 8) {
                        String attribute = "";
                        if ("0".equals(bean.getAttribute())) {
                            attribute = "无主单";
                        } else if ("1".equals(bean.getAttribute())) {
                            attribute = "有主单";
                        } else if ("2".equals(bean.getAttribute())) {
                            attribute = "线下员工";
                        } else if ("3".equals(bean.getAttribute())) {
                            attribute = "线上员工";
                        }
                        cell.setCellValue(attribute);
                    }
                    //出借人用户名
                    else if (celLength == 9) {
                        cell.setCellValue(bean.getUsernameTender());
                    }
                    //出借人用户属性(出借时)
                    else if (celLength == 10) {
                        String attribute = "";
                        if ("0".equals(bean.getAttributeTender())) {
                            attribute = "无主单";
                        } else if ("1".equals(bean.getAttributeTender())) {
                            attribute = "有主单";
                        } else if ("2".equals(bean.getAttributeTender())) {
                            attribute = "线下员工";
                        } else if ("3".equals(bean.getAttributeTender())) {
                            attribute = "线上员工";
                        }
                        cell.setCellValue(attribute);
                    }
                    //加入金额
                    else if (celLength == 11) {
                        cell.setCellValue(bean.getAccountTender().toString() + "元");
                    }
                    //提成金额
                    else if (celLength == 12) {
                        cell.setCellValue(bean.getCommission().toString() + "元");
                    }
                   //提成发放状态
                    else if (celLength == 13) {
                        cell.setCellValue(bean.getStatusName());
                    }
                    //提成发放时间
                    else if (celLength == 14) {
                        cell.setCellValue(bean.getSendTimeView());
                    }
                    //计划订单加入时间
                    else if (celLength == 15) {
                    	cell.setCellValue(bean.getAddTime());
                    }
                    //计划订单锁定时间
                    else if (celLength == 16) {
                    	cell.setCellValue(bean.getCountInterestTimeView());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
	}*/

	/**
	 * 导出excel
	 *
	 * @param viewRequest
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ApiOperation(value = "汇计划提成列表带条件导出EXCEL", notes = "带条件导出EXCEL")
	@PostMapping(value = "/export")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,@RequestBody HjhCommissionViewRequest viewRequest) throws Exception {
		// 封装查询条件
		// 初始化原子层请求实体
		HjhCommissionRequest form = new HjhCommissionRequest();
		// 初始化返回LIST
		List<HjhCommissionCustomizeVO> returnList = null;
		// 将画面检索参数request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		// 默认为汇计划类的出借
		form.setTenderType(2);
		//sheet默认最大行数
		int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
		// 表格sheet名称
		String sheetName = "推广提成发放列表";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
		DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
		//form.setLimitFlg(true);
		//查询导出列表总数
		Integer totalCount = hjhCommissionService.getHjhCommissionCountForExport(form);
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
			form.setLimit(defaultRowMaxCount);
			HjhCommissionResponse listResponse = hjhCommissionService.selectHjhCommissionList(form);
			if (listResponse != null && listResponse.getResultList().size()> 0) {
				for(HjhCommissionCustomizeVO hjhCommissionCustomizeVO : listResponse.getResultList()){
					//锁定期
					String str = "";
					if (hjhCommissionCustomizeVO.getLockPeriod()==null) {
						hjhCommissionCustomizeVO.setLockPeriod(str);
					}else {
						if ("1".equals(hjhCommissionCustomizeVO.getIsMonth())) {
							str = "个月";
						}else if("0".equals(hjhCommissionCustomizeVO.getIsMonth())) {
							str = "天";
						}
						hjhCommissionCustomizeVO.setLockPeriod(hjhCommissionCustomizeVO.getLockPeriod() + str);
					}
				}
				sheetNameTmp = sheetName + "_第" + i + "页";
				helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  listResponse.getResultList());
			} else {
				break;
			}
		}
		DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
	}

	private Map<String, String> buildMap() {
		Map<String, String> map = Maps.newLinkedHashMap();
		map.put("ordid", "智投订单号");
		map.put("borrowNid", "智投编号");
		map.put("borrowStyleHjh", "还款方式");
		map.put("lockPeriod", "服务回报期限");
		map.put("expectApr", "参考年回报率");
		map.put("username", "提成人");
		map.put("trueNameTender", "提成人真实姓名");
		map.put("attribute", "提成人用户属性(出借时)");
		map.put("usernameTender", "出借人用户名");
		map.put("attributeTender", "出借人用户属性(出借时)");
		map.put("accountTender", "授权服务金额");
		map.put("commission", "提成金额");
		map.put("statusName", "提成发放状态");
		map.put("sendTimeView", "提成发放时间");
		map.put("addTime", "授权服务订单时间");
		map.put("countInterestTimeView", "智投订单锁定时间");
		return map;
	}

	private Map<String, IValueFormatter> buildValueAdapter() {
		Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
		IValueFormatter borrowStyleHjhAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				String borrowStyleHjh = (String) object;
				String borrowStyleHjhStr = "";
				if ("month".equals(borrowStyleHjh)) {
					borrowStyleHjhStr = "等额本息";
				}else if("season".equals(borrowStyleHjh)) {
					borrowStyleHjhStr = "按季还款";
				}else if("end".equals(borrowStyleHjh)) {
					borrowStyleHjhStr = "按月计息，到期还本还息";
				}else if("endmonth".equals(borrowStyleHjh)) {
					borrowStyleHjhStr = "先息后本";
				}else if("endday".equals(borrowStyleHjh)) {
					borrowStyleHjhStr = "按天计息，到期还本还息";
				}else if("endmonths".equals(borrowStyleHjh)) {
					borrowStyleHjhStr = "按月付息到期还本";
				}else if("principal".equals(borrowStyleHjh)) {
					borrowStyleHjhStr = "等额本金";
				}
				return borrowStyleHjhStr;
			}
		};

		IValueFormatter expectAprAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				BigDecimal expectApr = (BigDecimal) object;
				return (expectApr.toString()+" %");
			}
		};

		IValueFormatter attributeAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				String attribute = (String) object;
				String attributeStr = "";
				if ("0".equals(attribute)) {
					attributeStr = "无主单";
				} else if ("1".equals(attribute)) {
					attributeStr = "有主单";
				} else if ("2".equals(attribute)) {
					attributeStr = "线下员工";
				} else if ("3".equals(attribute)) {
					attributeStr = "线上员工";
				}
				return attributeStr;
			}
		};

		IValueFormatter attributeTenderAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				String attributeTender = (String) object;
				String attribute = "";
				if ("0".equals(attributeTender)) {
					attribute = "无主单";
				} else if ("1".equals(attributeTender)) {
					attribute = "有主单";
				} else if ("2".equals(attributeTender)) {
					attribute = "线下员工";
				} else if ("3".equals(attributeTender)) {
					attribute = "线上员工";
				}
				return attribute;
			}
		};

		IValueFormatter accountTenderAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				BigDecimal accountTender = (BigDecimal) object;
				return (accountTender.toString() + "元");
			}
		};

		IValueFormatter commissionAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				BigDecimal commission = (BigDecimal) object;
				return (commission.toString() + "元");
			}
		};

		mapAdapter.put("borrowStyleHjh", borrowStyleHjhAdapter);
		mapAdapter.put("expectApr", expectAprAdapter);
		mapAdapter.put("attribute", attributeAdapter);
		mapAdapter.put("attributeTender", attributeTenderAdapter);
		mapAdapter.put("accountTender", accountTenderAdapter);
		mapAdapter.put("commission", commissionAdapter);
		return mapAdapter;
	}

	/**
	 * 取得部门信息   已测试
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "汇计划提成列表取得部门信息", notes = "取得部门信息")
	@PostMapping(value = "/getcrmdepartmentlist")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
	public JSONObject getCrmDepartmentListAction(@RequestBody HjhCommissionViewRequest viewRequest) {
		JSONObject jsonObject = new JSONObject();
		// 部门
		String[] list = new String[] {};
		if (Validator.isNotNull(viewRequest.getDepIds())) {
			if (viewRequest.getDepIds().contains(StringPool.COMMA)) {
				list = viewRequest.getDepIds().split(StringPool.COMMA);
			} else {
				list = new String[] { viewRequest.getDepIds() };
			}
		}
		JSONArray ja = this.hjhCommissionService.getCrmDepartmentList(list);
		if (ja != null) {
			jsonObject.put("部门信息", ja.toString());
			jsonObject.put("status", SUCCESS);
		} else {
			jsonObject.put("error", "未查询到该记录！");
			jsonObject.put("status", FAIL);
		}
		return jsonObject;
	}



	/**
	 * 汇计划发提成
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "汇计划提成列表汇计划发提成", notes = "汇计划发提成")
	@PostMapping(value = "/confirmpushmoney")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_CONFIRM)
	public JSONObject confirmPushMoneyAction(HttpServletRequest request, @RequestBody HjhCommissionViewRequest viewRequest) {
		// 调用基类方法获取登录者userid
		int loginUserId = Integer.valueOf(this.getUser(request).getId());
		/*int loginUserId = 5274;*/
		// 调用基类方法获取登录者userName
		String loginUserName = this.getUser(request).getUsername();
		/*String loginUserName = "hyjf288535";*/
		// 初始化原子层请求实体
		HjhCommissionRequest form = new HjhCommissionRequest();
		// 将画面检索参数request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		// 默认为汇计划类的出借
		form.setTenderType(2);
		JSONObject ret = new JSONObject();
		// 提成ID 主键查询提成表
		Integer id = form.getIds();
		/*原TenderCommission tenderCommission = this.pushMoneyService.queryTenderCommissionByPrimaryKey(id);*/
		TenderCommissionVO tenderCommission = this.hjhCommissionService.queryTenderCommissionByPrimaryKey(id);
		// 如果 未发放 //且 提成>0
		if (tenderCommission != null && tenderCommission.getStatus() == 0 && tenderCommission.getAccountTender().compareTo(BigDecimal.ZERO) > 0) {
			Integer userId = tenderCommission.getUserId();
			/** 验证员工在平台的身份属性是否和crm的一致 如果不一致则不发提成 begin */
			/** redis 锁 */
			/*原boolean reslut = RedisUtils.tranactionSet("PUSH_MONEY:" + id, 5);*/
			boolean reslut = RedisUtils.tranactionSet(RedisConstants.PUSH_MONEY_ + id, 50);
			// 如果没有设置成功，说明有请求来设置过
			if(!reslut){
				ret.put("status", FAIL);
				ret.put("result", "数据已发生变化,请刷新页面!");
				return ret;
			}
			/*原UsersInfo usersInfo = this.pushMoneyService.getUsersInfoByUserId(userId);*/
			// 获取用户信息
			UserInfoVO usersInfo = this.accedeListService.getUsersInfoByUserId(userId);
			// 获取用户信息详情
			UserInfoCustomizeVO userInfoCustomize=transferLogService.queryUserInfoByUserId(userId);
			// cuttype 提成发放方式（3线上2线下）
			Integer cuttype = this.hjhCommissionService.queryCrmCuttype(userId);
			if (usersInfo.getAttribute() != null && usersInfo.getAttribute() > 1) {
				if (!usersInfo.getAttribute().equals(cuttype)) {
					ret.put("status", FAIL);
					ret.put("result", "该用户属性异常！");
					/*logger.error(THIS_CLASS, "/confirmpushmoney", new Exception("该用户平台属性与CRM 不符！[userId=" + userId + "]")); */
					logger.error("汇计划提成列表汇计划发提成/confirmpushmoney" + "该用户平台属性与CRM 不符！[userId=" + userId + "]");
/*                    原LogUtil.errorLog(THIS_CLASS, PushMoneyManageHjhDefine.CONFIRM_PUSHMONEY, new Exception(
                            "该用户平台属性与CRM 不符！[userId=" + userId + "]"));*/
					return ret;
				}
			}
			/** 验证员工在平台的身份属性是否和crm的一致 如果不一致则不发提成 end */
			/*原BankOpenAccount bankOpenAccountInfo = returncashService.getBankOpenAccount(userId);*/
			// 取得用户在银行的账户信息
			BankOpenAccountVO bankOpenAccountInfo = bankAccountManageService.getBankOpenAccount(userId);
			if (bankOpenAccountInfo != null && !Validator.isNull(bankOpenAccountInfo.getAccount())) {
				// 查询商户子账户余额
				/*原String merrpAccount = PropUtils.getSystem(BankCallConstant.BANK_MERRP_ACCOUNT);*/
				String merrpAccount = systemConfig.getBANK_MERRP_ACCOUNT();
				/*原BigDecimal bankBalance = pushMoneyService.getBankBalance(Integer.parseInt(ShiroUtil.getLoginUserId()), merrpAccount);*/
				BigDecimal bankBalance = bankMerchantAccountService.getBankBalance(loginUserId, merrpAccount);
				// 如果接口调用成功
				if (bankBalance != null) {
					// 检查余额是否充足
					if (bankBalance.compareTo(tenderCommission.getCommission()) < 0) {
						logger.error(THIS_CLASS, "/confirmpushmoney", new Exception("推广提成子账户余额不足,请先充值或向该子账户转账"));
						ret.put("status", FAIL);
						ret.put("result", "推广提成子账户余额不足,请先充值或向该子账户转账");
						return ret;
					}
				} else {
					logger.info("没有查询到商户可用余额");
					logger.error(THIS_CLASS, "/confirmpushmoney", new Exception("调用银行接口发生错误"));
					ret.put("status", FAIL);
					ret.put("result", "没有查询到商户可用余额");
					return ret;
				}
				// IP地址
				String ip = CustomUtil.getIpAddr(request);
				String orderId = GetOrderIdUtils.getOrderId2(userId);
				BankCallBean bean = new BankCallBean();
				bean.setVersion(BankCallConstant.VERSION_10);// 版本号
				bean.setTxCode(BankCallMethodConstant.TXCODE_VOUCHER_PAY);// 交易代码
				bean.setTxDate(GetOrderIdUtils.getTxDate()); // 交易日期
				bean.setTxTime(GetOrderIdUtils.getTxTime()); // 交易时间
				bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号
				bean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
				bean.setAccountId(merrpAccount);// 电子账号
				bean.setTxAmount(tenderCommission.getCommission().toString());
				bean.setForAccountId(bankOpenAccountInfo.getAccount());
				bean.setDesLineFlag("1");
				bean.setDesLine(tenderCommission.getOrdid());
				bean.setLogOrderId(orderId);// 订单号
				bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
				bean.setLogUserId(String.valueOf(userId));
				bean.setLogClient(0);// 平台
				bean.setLogIp(ip);

				BankCallBean resultBean;
				try {
					resultBean = BankCallUtils.callApiBg(bean);
				} catch (Exception e) {
					e.printStackTrace();
					ret.put("status", FAIL);
					ret.put("result", "请求红包接口失败");
					return ret;
				}
				if (resultBean == null || !BankCallConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
					logger.error(THIS_CLASS, "/confirmpushmoney", new Exception("调用红包接口发生错误"));
					ret.put("status", FAIL);
					ret.put("result", "调用红包接口发生错误");
					return ret;
				}
				int cnt = 0;
				// 接口返回正常时,执行更新操作
				try {
					tenderCommission.setLoginUserName(loginUserName);
					tenderCommission.setAccount(bankOpenAccountInfo.getAccount());
					if(userInfoCustomize != null){
						tenderCommission.setUserName(StringUtils.isEmpty(userInfoCustomize.getUserName()) ? "" : userInfoCustomize.getUserName());
						tenderCommission.setRegionName(StringUtils.isEmpty(userInfoCustomize.getRegionName()) ? "" : userInfoCustomize.getRegionName());
						tenderCommission.setBranchName(StringUtils.isEmpty(userInfoCustomize.getBranchName()) ? "" : userInfoCustomize.getBranchName());
						tenderCommission.setDepartmentName(StringUtils.isEmpty(userInfoCustomize.getDepartmentName()) ? "" : userInfoCustomize.getDepartmentName());
						tenderCommission.setTrueName(StringUtils.isEmpty(userInfoCustomize.getTrueName()) ? "" : userInfoCustomize.getTrueName());
						tenderCommission.setSex(StringUtils.isEmpty(userInfoCustomize.getSex()) ? "" : userInfoCustomize.getSex());
						tenderCommission.setMobile(StringUtils.isEmpty(userInfoCustomize.getMobile()) ? "" : userInfoCustomize.getMobile());
						if(userInfoCustomize.getAttribute()!= null){
							tenderCommission.setAttribute(userInfoCustomize.getAttribute());
						} else {
							tenderCommission.setAttribute(99);
						}
					}
					// 发提成处理
					cnt = this.hjhCommissionService.updateTenderCommissionRecord(tenderCommission, resultBean, null);
				} catch (Exception e) {
					logger.error(THIS_CLASS, "/confirmpushmoney", e);
				}
				// 返现成功
				if (cnt > 0) {
					ret.put("status", SUCCESS);
					ret.put("result", "发提成操作成功!");
					logger.info("提成发放成功，用户id：" + userId + " 金额:"  + tenderCommission.getCommission().toString());
				} else {
					ret.put("status", FAIL);
					ret.put("result", "发提成时发生错误,请重新操作!");
					logger.error(THIS_CLASS, "/confirmpushmoney", new Exception("发提成时发生错误,请重新操作!"));
				}
				/*LogUtil.endLog(this.getClass().getName(), PushMoneyManageHjhDefine.CONFIRM_PUSHMONEY);*/
				return ret;
			}else {
				ret.put("status", FAIL);
				ret.put("result", "该用户未开户");
				logger.error(THIS_CLASS, "/confirmpushmoney", new Exception("参数不正确[userId="
						+ userId + "]"));
				return ret;
			}
		}
		return ret;
	}
}
