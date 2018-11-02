package com.hyjf.admin.controller.productcenter.plancenter.planrepay;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.HjhRepayRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.PlanRepayService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhDebtCreditReponse;
import com.hyjf.am.response.trade.HjhRepayResponse;
import com.hyjf.am.resquest.admin.HjhDebtCreditListRequest;
import com.hyjf.am.resquest.admin.HjhRepayRequest;
import com.hyjf.am.vo.admin.HjhDebtCreditVo;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 产品中心 --> 汇计划 --> 订单退出  Warning : 汇计划三期 订单退出改为计划还款
 * @Author : huanghui
 */
@Api(value = "产品中心-汇计划-计划还款",tags ="产品中心-汇计划-计划还款")
@RestController
@RequestMapping(value = "/hyjf-admin/planrepay")
public class PlanRepayController extends BaseController {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private PlanRepayService planRepayService;

    /**
     * 资金中心 - 订单退出 检索下拉框
     * @return
     */
    @ApiOperation(value = "计划还款检索下拉框", notes = "计划还款检索下拉框")
    @RequestMapping(value = "/dropDownBox", method = RequestMethod.GET)
    public JSONObject dropDownBox() {
        JSONObject jsonObject = new JSONObject();

        // 类型List
        List<Object> orderStatusList = new ArrayList<>();

        // 初始Map
        Map<String, Object> orderStatusMap = new HashedMap();
        Map<String, Object> orderStatusMap1 = new HashedMap();
        Map<String, Object> orderStatusMap2 = new HashedMap();
        //设置对应键值对
        orderStatusMap.put("key", 8);
        orderStatusMap.put("value", "未还款");
        orderStatusMap1.put("key", 10);
        orderStatusMap1.put("value", "还款中");
        orderStatusMap2.put("key", 11);
        orderStatusMap2.put("value", "还款完成");

        orderStatusList.add(orderStatusMap);
        orderStatusList.add(orderStatusMap1);
        orderStatusList.add(orderStatusMap2);

        // 初始组装所有数据的Map
        Map<String, Object> allMap = new HashedMap();

        allMap.put("orderStatusList", orderStatusList);

        if (orderStatusList != null){
            jsonObject.put("status", BaseResult.SUCCESS);
            jsonObject.put("statusDesc", BaseResult.SUCCESS_DESC);
            jsonObject.put("data", allMap);
        }else {
            jsonObject.put("status", Response.FAIL);
            jsonObject.put("statusDesc", Response.FAIL_MSG);
            jsonObject.put("data", "");
        }

        return jsonObject;
    }

    /**
     * 订单退出初始化列表
     * @param repayRequestBean
     * @return
     */
    @ApiOperation(value = "订单退出", notes = "订单退出初始化列表")
    @PostMapping(value = "/init")
    public AdminResult<ListResult<HjhRepayVO>> init(@RequestBody HjhRepayRequestBean repayRequestBean){

        HjhRepayRequest repayRequest = new HjhRepayRequest();
        BeanUtils.copyProperties(repayRequestBean, repayRequest);

//        初始化时应默认清算时间不能为空
//        if (StringUtils.isEmpty(repayRequest.getRepayTimeStart()) && StringUtils.isEmpty(repayRequest.getRepayTimeEnd())){
//            return new AdminResult<>(FAIL, "请输入清算开始和结束时间!");
//        }

//        实际退出时间不能为空
//        if (StringUtils.isNotEmpty(repayRequest.getActulRepayTimeStart()) && StringUtils.isNotEmpty(repayRequest.getActulRepayTimeEnd())){
//            return new AdminResult<>(FAIL, "请输入实际退出开始和结束时间!");
//        }

        //防止前端将起始时间传错误.
//        try {
//            Date timeStart = dateFormat.parse(repayRequest.getRepayTimeStart());
//            Date timeEnd = dateFormat.parse(repayRequest.getRepayTimeEnd());
//
//            if (timeStart.getTime() > timeEnd.getTime()){
//                return new AdminResult<>(FAIL, "清算结束时间应大于等于开始时间!");
//            }

//            Date actuTimeStart = dateFormat.parse(repayRequest.getActulRepayTimeStart());
//            Date actuTimeEnd = dateFormat.parse(repayRequest.getActulRepayTimeEnd());
//            if (actuTimeStart.getTime() > actuTimeEnd.getTime()){
//                return new AdminResult<>(FAIL, "实际退出结束时间应大于等于开始时间!");
//            }
//        }catch (ParseException e){
//            return new AdminResult<>(FAIL, e.getMessage());
//        }

        //初始化返回List
        List<HjhRepayVO> returnList = new ArrayList<>();
        // 查询列表
        HjhRepayResponse hjhRepayResponse = this.planRepayService.selectHjhRepayList(repayRequest);
        if (hjhRepayResponse == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        if (!Response.isSuccess(hjhRepayResponse)){
            return new AdminResult<>(FAIL, hjhRepayResponse.getMessage());
        }

        if (CollectionUtils.isNotEmpty(hjhRepayResponse.getResultList())){
            returnList = CommonUtils.convertBeanList(hjhRepayResponse.getResultList(), HjhRepayVO.class);
            return new AdminResult<ListResult<HjhRepayVO>>(ListResult.build2(returnList, hjhRepayResponse.getCount(), hjhRepayResponse.getSumHjhRepayVO()));
        }else {
            return new AdminResult<ListResult<HjhRepayVO>>(ListResult.build(returnList, 0));
        }
    }


   /* *//**
     * 订单退出初始化列表
     * @param repayRequestBean
     * @return
     *//*
    @ApiOperation(value = "订单退出", notes = "订单退出列表导出")
    @PostMapping(value = "/exportAction")
    public void exportAction(HttpServletRequest request, HttpServletResponse response, HjhRepayRequestBean repayRequestBean) throws Exception {

        HjhRepayRequest repayRequest = new HjhRepayRequest();
        BeanUtils.copyProperties(repayRequestBean, repayRequest);

        // currPage<0 为全部,currPage>0 为具体某一页
        repayRequestBean.setCurrPage(-1);

        // 表格sheet名称
        String sheetName = "计划还款";

        HashMap<String,Object> paraMap = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(repayRequestBean.getAccedeOrderIdSrch())){
            repayRequest.setAccedeOrderIdSrch(repayRequestBean.getAccedeOrderIdSrch());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getPlanNidSrch())){
            repayRequest.setPlanNidSrch(repayRequestBean.getPlanNidSrch());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getUserNameSrch())){
            repayRequest.setUserNameSrch(repayRequestBean.getUserNameSrch());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getDebtLockPeriodSrch())){
            repayRequest.setDebtLockPeriodSrch(repayRequestBean.getDebtLockPeriodSrch());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getRepayStatusSrch())){
            repayRequest.setRepayStatusSrch(repayRequestBean.getRepayStatusSrch());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getOrderStatusSrch())){
            repayRequest.setOrderStatusSrch(repayRequestBean.getOrderStatusSrch());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getBorrowStyleSrch())){
            repayRequest.setBorrowStyleSrch(repayRequestBean.getBorrowStyleSrch());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getRepayTimeStart())){
            repayRequest.setRepayTimeStart(repayRequestBean.getRepayTimeStart());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getRepayTimeEnd())){
            repayRequest.setRepayTimeEnd(repayRequestBean.getRepayTimeEnd());
//            paraMap.put("repayTimeEnd", GetDate.getDayEnd10(form.getRepayTimeEnd()));
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getActulRepayTimeStart())){
            repayRequest.setActulRepayTimeStart(repayRequestBean.getActulRepayTimeStart());
//            paraMap.put("actulRepayTimeStart", GetDate.getDayStart10(form.getActulRepayTimeStart()));
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getActulRepayTimeEnd())){
            repayRequest.setActulRepayTimeEnd(repayRequestBean.getActulRepayTimeEnd());
//            paraMap.put("actulRepayTimeEnd", GetDate.getDayEnd10(form.getActulRepayTimeEnd()));
        }
        // 汇计划三期新增 推荐人查询
        if(StringUtils.isNotEmpty(repayRequestBean.getRefereeNameSrch())){
            repayRequest.setRefereeNameSrch(repayRequestBean.getRefereeNameSrch());
        }

//        List<HjhPlanRepayCustomize> resultList = planRepayListService.exportPlanRepayList(paraMap);
//        List<HjhPlanRepayCustomize> resultList = planRepayListService.exportPlanRepayList(paraMap);
//        List<HjhRepayResponse> resultList = this.planRepayService.selectHjhRepayList(paraMap);

        //初始化返回List
        List<HjhRepayVO> returnList = null;
        // 查询列表
        HjhRepayResponse hjhRepayList = this.planRepayService.selectHjhRepayList(repayRequest);

        if (hjhRepayList.getCount() > 0){
            returnList = CommonUtils.convertBeanList(hjhRepayList.getResultList(), HjhRepayVO.class);
        }

        //根据计划编号获取计划锁定期天月和还款方式
        for(int i = 0; i < returnList.size(); i++){
            if(returnList.get(i).getPlanNid()!=null){
                String planNid=returnList.get(i).getPlanNid();
//                HjhPlan hjhplan=planRepayListService.getPlan(planNid);
//                HjhPlanVO hjhplan= planRepayService.getPlan(planNid);
//                if(hjhplan.getIsMonth()!=null){
//                    returnList.get(i).setIsMonth(hjhplan.getIsMonth());
//                }
//                if(hjhplan.getBorrowStyle()!=null){
//                    returnList.get(i).setBorrowStyle(hjhplan.getBorrowStyle());
//                }
            }

        }
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        String[] titles = new String[] {
                "序号","计划订单号", "计划编号", "计划名称", "锁定期","预期年化收益率","用户名（投资人）",
                "投资人用户属性（当前）",
                "推荐人(当前)", "分公司(当前)", "部门(当前)", "团队(当前)",
                "加入金额(元)", "预期收益(元)",
                "实际收益(元)", "实际回款总额(元)",
                "还款方式","订单状态",  "实际退出时间", "清算时间","清算服务费" ,"清算服务费率" ,
                "投资服务费率" ,"清算进度","最晚退出时间","加入时间","订单锁定时间"};
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (returnList != null && returnList.size() > 0) {
            int sheetCount = 1;
            int rowNum = 0;
            for (int i = 0; i < returnList.size(); i++) {
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
//                    HjhPlanRepayCustomize planAccedeDetail = resultList.get(i);
//                    HjhRepayVO planAccedeDetail = returnList.get(i);
                    HjhRepayVO planAccedeDetail = returnList.get(i);
//                    HjhReInvestDebtVO hjhReInvestDebt = returnList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    // 计划订单号
                    else if (celLength == 1) {
                        cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getAccedeOrderId()) ? StringUtils.EMPTY : planAccedeDetail.getAccedeOrderId());
                    }
                    // 计划编号
                    else if (celLength == 2) {
                        cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getPlanNid()) ? StringUtils.EMPTY : planAccedeDetail.getPlanNid());
                    }
                    // 计划名称
                    else if (celLength == 3) {
                        cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getPlanName()) ? StringUtils.EMPTY : planAccedeDetail.getPlanName());
                    }
                    // 锁定期
                    else if (celLength == 4) {
                        if (planAccedeDetail.getLockPeriod()  == null) {
                            cell.setCellValue(0);
                        } else {
//                            if(planAccedeDetail.getIsMonth()==0){
//                                cell.setCellValue(planAccedeDetail.getLockPeriod()+ "天");
//                            }
//                            if(planAccedeDetail.getIsMonth()==1){
                            cell.setCellValue(planAccedeDetail.getLockPeriod()+ "个月");
//                            }

                        }
                    }
                    // 预期年化收益率
                    else if (celLength == 5) {
                        cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getExpectApr()) ? StringUtils.EMPTY : planAccedeDetail.getExpectApr() + "%");
                    }
                    // 用户名
                    else if (celLength == 6) {
                        cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getUserName()) ? StringUtils.EMPTY : planAccedeDetail.getUserName());
                    }
                    // 投资人用户属性（当前）
                    else if (celLength == 7) {
                        cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getRecommendAttr()) ? StringUtils.EMPTY : planAccedeDetail.getRecommendAttr());
                    }
                    //推荐人用户属性
*//*					else if (celLength == 7) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserAttributeName()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserAttributeName());
					}*//*
                    // 推荐人用户名（当前）
                    else if (celLength == 8) {
                        cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserName()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserName());
                    }
                    // 推荐人用户部门信息（当前）
                    else if (celLength == 9) {
                        cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserRegionName()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserRegionName());
                    }
                    // 推荐人用户部门信息（当前）
                    else if (celLength == 10) {
                        cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserBranchName()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserBranchName());
                    }
                    // 推荐人用户部门信息（当前）
                    else if (celLength == 11) {
                        cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getInviteUserDepartmentName()) ? StringUtils.EMPTY : planAccedeDetail.getInviteUserDepartmentName());
                    }
*//*					// 推荐人用户属性（退出时）
					else if (celLength == 12) {
						cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getRecommendAttr()) ? StringUtils.EMPTY : planAccedeDetail.getRecommendAttr());
					}*//*
                    // 加入金额
                    else if (celLength == 12) {
                        if (planAccedeDetail.getAccedeAccount()  == null) {
                            cell.setCellValue(0);
                        } else {
                            cell.setCellValue(planAccedeDetail.getAccedeAccount().toString());
                        }
                    }
                    // 预期收益
                    else if (celLength == 13) {
                        if (planAccedeDetail.getRepayInterest()  == null) {
                            cell.setCellValue(0);
                        } else {
                            cell.setCellValue(planAccedeDetail.getRepayInterest().toString());
                        }
                    }
                    //实际收益(元)
                    else if (celLength == 14) {
                        if (planAccedeDetail.getActualRevenue()  == null) {
                            cell.setCellValue(0);
                        } else {
                            cell.setCellValue(planAccedeDetail.getActualRevenue().toString());
                        }
                    }
                    //实际回款总额(元)
                    else if (celLength == 15) {
                        if (planAccedeDetail.getActualPayTotal()  == null) {
                            cell.setCellValue(0);
                        } else {
                            cell.setCellValue(planAccedeDetail.getActualPayTotal().toString());
                        }
                    }
	*//*				// 回款状态
					else if (celLength == 16) {
						if (0 == planAccedeDetail.getRepayStatus()) {
							cell.setCellValue("未回款");
						} else if (1 == planAccedeDetail.getRepayStatus()) {
							cell.setCellValue("部分回款");
						} else if (2 == planAccedeDetail.getRepayStatus()) {
							cell.setCellValue("已回款");
						}
					}*//*
                    //还款方式
                    else if(celLength == 16){
                        if (planAccedeDetail.getBorrowStyle()  == null) {
                            cell.setCellValue("");
                        } else {
                            if(planAccedeDetail.getBorrowStyle().equals("month")){
                                cell.setCellValue("等额本息");
                            }if(planAccedeDetail.getBorrowStyle().equals("season")){
                                cell.setCellValue("按季还款");
                            }if(planAccedeDetail.getBorrowStyle().equals("end")){
                                cell.setCellValue("按月计息，到期还本还息");
                            }if(planAccedeDetail.getBorrowStyle().equals("endmonth")){
                                cell.setCellValue("先息后本");
                            }if(planAccedeDetail.getBorrowStyle().equals("endday")){
                                cell.setCellValue("按天计息，到期还本还息");
                            }if(planAccedeDetail.getBorrowStyle().equals("endmonths")){
                                cell.setCellValue("按月付息到期还本");
                            }if(planAccedeDetail.getBorrowStyle().equals("principal")){
                                cell.setCellValue("等额本金");
                            }
                        }
                    }
                    // 订单状态
                    else if (celLength == 17) {
                        if (0 == planAccedeDetail.getOrderStatus()) {
                            cell.setCellValue("自动投标中");
                        } else if (2 == planAccedeDetail.getOrderStatus()) {
                            cell.setCellValue("自动投标成功");
                        } else if (3 == planAccedeDetail.getOrderStatus()) {
                            cell.setCellValue("锁定中");
                        } else if (5 == planAccedeDetail.getOrderStatus()) {
                            cell.setCellValue("退出中");
                        } else if (7 == planAccedeDetail.getOrderStatus()) {
                            cell.setCellValue("已退出");
                        } else if (99 == planAccedeDetail.getOrderStatus()) {
                            cell.setCellValue("自动投资异常");
                        }
                    }
                    // 计划实际还款时间 (实际退出时间)
                    else if (celLength == 18) {
//                        if (planAccedeDetail.getRepayActualTime()  == null) {
                        cell.setCellValue("");
//                        } else {
//                            cell.setCellValue(GetDate.timestamptoStrYYYYMMDDHHMMSS(planAccedeDetail.getRepayActualTime()));
//                        }
                    }
                    // 清算时间
                    else if (celLength == 19) {
//                        if (planAccedeDetail.getRepayShouldTime()  == null) {
                        cell.setCellValue(0);
//                        } else {
//                            cell.setCellValue(GetDate.timestamptoStrYYYYMMDDHHMMSS(planAccedeDetail.getRepayShouldTime()));
//                        }
                    }
                    // 清算服务费
                    else if (celLength == 20) {
                        if (planAccedeDetail.getLqdServiceFee()  == null) {
                            cell.setCellValue(0);
                        } else {
                            cell.setCellValue(planAccedeDetail.getLqdServiceFee().toString());
                        }
                    }
                    // 清算服务费率
                    else if (celLength == 21) {
                        if (planAccedeDetail.getLqdServiceApr()  == null) {
                            cell.setCellValue(0);
                        } else {
                            cell.setCellValue(planAccedeDetail.getLqdServiceApr().toString());
                        }
                    }
                    // 投资服务费率
                    else if (celLength == 22) {
                        if (planAccedeDetail.getInvestServiceApr()  == null) {
                            cell.setCellValue(0);
                        } else {
                            cell.setCellValue(planAccedeDetail.getInvestServiceApr());
                        }
                    }
                    // 清算进度
                    else if (celLength == 23) {
                        if (planAccedeDetail.getLqdProgress()  == null) {
                            cell.setCellValue(0);
                        } else {
                            cell.setCellValue(planAccedeDetail.getLqdProgress());
                        }
                    }
                    // 最晚退出时间
                    else if (celLength == 24) {
                        if (StringUtils.isEmpty(planAccedeDetail.getLastQuitTime())) {
                            cell.setCellValue(0);
                        } else {
                            cell.setCellValue(planAccedeDetail.getLastQuitTime());
                        }
                    }
                    // 汇计划加入时间
                    else if (celLength == 25) {
                        if (planAccedeDetail.getJoinTime()  == 0) {
                            cell.setCellValue(0);
                        } else {
                            cell.setCellValue(GetDate.timestamptoStrYYYYMMDDHHMMSS(planAccedeDetail.getJoinTime()));
                        }
                    }
                    // 订单锁定时间 = 加入计划的计息时间
                    else if (celLength == 26) {
                        if (planAccedeDetail.getOrderLockTime() == 0) {
                            cell.setCellValue(0);
                        } else {
                            cell.setCellValue(GetDate.timestamptoStrYYYYMMDDHHMMSS(planAccedeDetail.getOrderLockTime()));
                        }
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);

    }*/

    /**
     * 订单退出初始化列表
     * @param repayRequestBean
     * @return
     */
    @ApiOperation(value = "订单退出", notes = "订单退出列表导出")
    @PostMapping(value = "/exportAction")
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody HjhRepayRequestBean repayRequestBean) throws Exception {
        HjhRepayRequest repayRequest = new HjhRepayRequest();
        BeanUtils.copyProperties(repayRequestBean, repayRequest);
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "计划还款";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        //请求第一页5000条
        repayRequest.setPageSize(defaultRowMaxCount);
        repayRequest.setCurrPage(1);
        HashMap<String,Object> paraMap = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(repayRequestBean.getAccedeOrderIdSrch())){
            repayRequest.setAccedeOrderIdSrch(repayRequestBean.getAccedeOrderIdSrch());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getPlanNidSrch())){
            repayRequest.setPlanNidSrch(repayRequestBean.getPlanNidSrch());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getUserNameSrch())){
            repayRequest.setUserNameSrch(repayRequestBean.getUserNameSrch());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getDebtLockPeriodSrch())){
            repayRequest.setDebtLockPeriodSrch(repayRequestBean.getDebtLockPeriodSrch());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getRepayStatusSrch())){
            repayRequest.setRepayStatusSrch(repayRequestBean.getRepayStatusSrch());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getOrderStatusSrch())){
            repayRequest.setOrderStatusSrch(repayRequestBean.getOrderStatusSrch());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getBorrowStyleSrch())){
            repayRequest.setBorrowStyleSrch(repayRequestBean.getBorrowStyleSrch());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getRepayTimeStart())){
            repayRequest.setRepayTimeStart(repayRequestBean.getRepayTimeStart());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getRepayTimeEnd())){
            repayRequest.setRepayTimeEnd(repayRequestBean.getRepayTimeEnd());
//            paraMap.put("repayTimeEnd", GetDate.getDayEnd10(form.getRepayTimeEnd()));
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getActulRepayTimeStart())){
            repayRequest.setActulRepayTimeStart(repayRequestBean.getActulRepayTimeStart());
//            paraMap.put("actulRepayTimeStart", GetDate.getDayStart10(form.getActulRepayTimeStart()));
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getActulRepayTimeEnd())){
            repayRequest.setActulRepayTimeEnd(repayRequestBean.getActulRepayTimeEnd());
//            paraMap.put("actulRepayTimeEnd", GetDate.getDayEnd10(form.getActulRepayTimeEnd()));
        }
        // 汇计划三期新增 推荐人查询
        if(StringUtils.isNotEmpty(repayRequestBean.getRefereeNameSrch())){
            repayRequest.setRefereeNameSrch(repayRequestBean.getRefereeNameSrch());
        }

//        List<HjhPlanRepayCustomize> resultList = planRepayListService.exportPlanRepayList(paraMap);
//        List<HjhPlanRepayCustomize> resultList = planRepayListService.exportPlanRepayList(paraMap);
//        List<HjhRepayResponse> resultList = this.planRepayService.selectHjhRepayList(paraMap);

        //初始化返回List
        List<HjhRepayVO> returnList = null;
        // 查询列表
        HjhRepayResponse hjhRepayList = this.planRepayService.selectHjhRepayList(repayRequest);

        if (hjhRepayList.getCount() > 0){
            returnList = CommonUtils.convertBeanList(hjhRepayList.getResultList(), HjhRepayVO.class);
        }


        Integer totalCount = hjhRepayList.getCount();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        int minId = 0;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {

            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, returnList);
        }
        for (int i = 1; i < sheetCount; i++) {

            repayRequest.setPageSize(defaultRowMaxCount);
            repayRequest.setCurrPage(i + 1);
            HjhRepayResponse hjhRepayList2 = this.planRepayService.selectHjhRepayList(repayRequest);
            if (hjhRepayList2 != null && hjhRepayList2.getResultList().size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  hjhRepayList2.getResultList());
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("accedeOrderId", "计划订单号");
        map.put("planNid", "计划编号");
        map.put("planName", "计划名称");
        map.put("lockPeriod", "锁定期");
        map.put("expectApr", "预期年化收益率");
        map.put("userName", "用户名（投资人）");
        map.put("recommendAttr", "投资人用户属性（当前）");
        map.put("inviteUserName", "推荐人(当前)");
        map.put("inviteUserRegionName", "分公司(当前)");
        map.put("inviteUserBranchName", "部门(当前)");
        map.put("inviteUserDepartmentName", "团队(当前)");
        map.put("accedeAccount", "加入金额(元)");
        map.put("repayInterest", "预期收益(元)");
        map.put("actualRevenue", "实际收益(元)");
        map.put("actualPayTotal", "实际回款总额(元)");
        map.put("borrowStyle", "还款方式");
        map.put("orderStatus", "订单状态");
        map.put("repayActualTime", "实际退出时间");
        map.put("repayShouldTime", "清算时间");
        map.put("lqdServiceFee", "清算服务费");
        map.put("lqdServiceApr", "清算服务费率");
        map.put("investServiceApr", "投资服务费率");
        map.put("lqdProgress", "清算进度");
        map.put("lastQuitTime", "最晚退出时间");
        map.put("joinTime", "加入时间");
        map.put("orderLockTime", "订单锁定时间");

        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter lockPeriodAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String value = (String) object;
                return StringUtils.isBlank(value) ?"":value+"个月";
            }
        };

        IValueFormatter expectAprAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String value = (String) object;
                return StringUtils.isBlank(value) ?"":value+"%";
            }
        };

        IValueFormatter borrowStyleAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String value = (String) object;
                if(StringUtils.isBlank(value)){
                    return "";
                }if(value.equals("month")){
                    return "等额本息";
                }else if(value.equals("season")){
                    return "按季还款";
                }else if(value.equals("end")){
                    return "按月计息，到期还本还息";
                }else if(value.equals("endmonth")){
                    return "先息后本";
                }else if(value.equals("endday")){
                    return "按天计息，到期还本还息";
                }else if(value.equals("endmonths")){
                    return "按月付息到期还本";
                }else if(value.equals("principal")){
                    return "等额本金";
                }

                return "";
            }
        };

        IValueFormatter orderStatusAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer value = (Integer) object;
                if(value == 0){
                    return "自动投标中";
                }else if(value == 2){
                    return "自动投标成功";
                }else if(value == 3){
                    return "锁定中";
                }else if(value == 5){
                    return "退出中";
                }else if(value == 7){
                    return "已退出";
                }else if(value == 99){
                    return "自动投资异常";
                }

                return "";
            }
        };

        IValueFormatter joinTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer value = (Integer) object;
                if(value == 0){
                    return "0";
                }else {
                    return GetDate.timestamptoStrYYYYMMDDHHMMSS(value);
                }
            }
        };

        IValueFormatter orderLockTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer value = (Integer) object;
                if(value == 0){
                    return "0";
                }else {
                    return GetDate.timestamptoStrYYYYMMDDHHMMSS(value);
                }
            }
        };

        mapAdapter.put("lockPeriod", lockPeriodAdapter);
        mapAdapter.put("expectApr", expectAprAdapter);
        mapAdapter.put("borrowStyle", borrowStyleAdapter);
        mapAdapter.put("orderStatus", orderStatusAdapter);
        mapAdapter.put("joinTime", joinTimeAdapter);
        mapAdapter.put("orderLockTime", orderLockTimeAdapter);
        return mapAdapter;
    }

}
