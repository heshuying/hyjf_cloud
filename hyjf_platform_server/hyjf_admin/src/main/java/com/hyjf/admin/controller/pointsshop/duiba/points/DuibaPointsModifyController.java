/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.pointsshop.duiba.points;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.pointsshop.duiba.points.DuibaPointsModifyService;
import com.hyjf.admin.service.pointsshop.duiba.points.DuibaPointsService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DuibaPointsModifyResponse;
import com.hyjf.am.response.admin.DuibaPointsResponse;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.vo.admin.DuibaPointsModifyVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 兑吧积分调整表
 *
 * @author PC-LIUSHOUYI
 * @version DuibaPointsModifyController, v0.1 2019/5/29 9:47
 */
@Api(value = "积分商城-兑吧积分账户修改明细", tags = "积分商城-兑吧积分账户修改明细")
@RestController
@RequestMapping("/hyjf-admin/duiba/pointslistmodify")
public class DuibaPointsModifyController extends BaseController {

    @Autowired
    DuibaPointsModifyService duibaPointsModifyService;

    @Autowired
    DuibaPointsService duibaPointsService;

    private static final String PERMISSIONS = "dbpoints";

    @ApiOperation(value = "兑吧积分账户修改明细", notes = "兑吧积分账户修改明细")
    @PostMapping("/selectDuibaPointsModifyList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<DuibaPointsModifyResponse> selectDuibaPointsModifyList(@RequestBody DuibaPointsRequest requestBean) {
        DuibaPointsModifyResponse response = duibaPointsModifyService.selectDuibaPointsModifyList(requestBean);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        // 积分类型
        List<ParamNameVO> integralTypeList = duibaPointsModifyService.getParamNameList(CustomConstants.INTEGRAL_TYPE);
        if (null != integralTypeList && integralTypeList.size() > 0) {
            response.setIntegralTypeList(integralTypeList);
        }
        // 积分业务名称
        List<ParamNameVO> integralBusinessNameList = duibaPointsModifyService.getParamNameList(CustomConstants.INTEGRAL_BUSINESS_NAME);
        if (null != integralBusinessNameList && integralBusinessNameList.size() > 0) {
            response.setIntegralBusinessNameList(integralBusinessNameList);
        }
        // 审核状态
        List<ParamNameVO> auditStatusList = duibaPointsModifyService.getParamNameList(CustomConstants.AUDIT_STATUS);
        if (null != auditStatusList && auditStatusList.size() > 0) {
            response.setAuditStatusList(auditStatusList);
        }
        // 操作类型
        List<ParamNameVO> operationTypeList = duibaPointsModifyService.getParamNameList(CustomConstants.OPERATION_TYPE);
        if (null != operationTypeList && operationTypeList.size() > 0) {
            response.setOperationTypeList(operationTypeList);
        }
        return new AdminResult<DuibaPointsModifyResponse>(response);
    }

    @ApiOperation(value = "兑吧批量调整积分审核", notes = "兑吧批量调整积分审核")
    @PostMapping("/auditModifyPoints")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_AUDIT)
    public AdminResult auditModifyPoints(HttpServletRequest request, @RequestBody DuibaPointsRequest requestBean) {

        // 获取操作人id
        AdminSystemVO adminSystemVO = this.getUser(request);
        requestBean.setModifyName(adminSystemVO.getUsername());
        requestBean.setModifyId(adminSystemVO.getId());

        // 判断参数
        if (null == requestBean.getAuditStatus()) {
            return new AdminResult<>(FAIL, "未传送审核结果！");
        }

        // 后期对接工作流后调用工作流接口上送审批状态并获取最终审批结果

        // 审核不通过 更新修改明细表相关审核明细的状态
        if (2 == requestBean.getAuditStatus()) {
            boolean re = this.duibaPointsModifyService.updatePointsModifyStatus(requestBean);
            if (!re) {
                logger.error("积分调整审核不通过处理失败，订单号:" + requestBean.getOrderId());
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
        }

        // 根据订单号获取订单详情
        DuibaPointsModifyVO duibaPointsModifyVO = this.duibaPointsModifyService.selectDuibaPointsModifyByOrdid(requestBean.getOrderId());
        if (null == duibaPointsModifyVO) {
            return new AdminResult<>(FAIL, "未获取到当前待审批积分调整的订单号");
        }
        if (null == duibaPointsModifyVO.getStatus() || 2 == duibaPointsModifyVO.getStatus()) {
            return new AdminResult<>(FAIL, "当前订单已审核或审核状态异常");
        }
        // 获取订单调整类型
        requestBean.setModifyType(duibaPointsModifyVO.getPointsType());
        // 获取订单调整积分数
        requestBean.setModifyPoints(duibaPointsModifyVO.getPoints());
        // 获取订单调整用户id
        List<Integer> list = new ArrayList<>();
        list.add(duibaPointsModifyVO.getUserId());
        requestBean.setUserId(duibaPointsModifyVO.getUserId());
        requestBean.setUserIdList(list);

        // 审核通过
        if (1 == requestBean.getAuditStatus()) {
            // 调减的情况
            if (1 == requestBean.getModifyType()) {
                // 查询用户是否够扣分的
                boolean re = this.duibaPointsService.selectRemainPoints(requestBean);
                if (!re) {
                    return new AdminResult<>(FAIL, "审核失败（用户积分不足）");
                }
            }
            // 更新账户剩余积分
            boolean re = this.duibaPointsModifyService.updateDuibaPoints(requestBean);
            if (!re) {
                logger.error("审核通过后调整用户积分失败，订单号:" + requestBean.getOrderId());
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            // 积分调整成功后更新审核状态
            re = this.duibaPointsModifyService.updatePointsModifyStatus(requestBean);
            if (!re) {
                logger.error("积分调整审核通过处理失败，订单号:" + requestBean.getOrderId());
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            // 插入明细表
            re = this.duibaPointsModifyService.insertDuibaPoints(requestBean);
            if (!re) {
                logger.error("积分调整审核通过处理失败，订单号:" + requestBean.getOrderId());
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
        }
        return new AdminResult<>(SUCCESS, SUCCESS_DESC);
    }

    /**
     * 兑吧积分调整明细列表列表导出
     *
     * @param request
     * @param response
     * @param response
     */
    @ApiOperation(value = "兑吧积分调整明细列表列表导出", notes = "兑吧积分调整明细列表列表导出")
    @PostMapping("/exportDuibaPointsModifyList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportToExcel(HttpServletRequest request, @RequestBody DuibaPointsRequest requestBean, HttpServletResponse response) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "积分调整审核列表";
        if(0 == requestBean.getExportType()) {
            sheetName = "积分调整明细列表";
        }
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) +  CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        //请求第一页5000条
        requestBean.setPageSize(defaultRowMaxCount);

        DuibaPointsModifyResponse duibaPointsModifyResponse = this.duibaPointsModifyService.selectDuibaPointsModifyList(requestBean);

        Integer totalCount = duibaPointsModifyResponse.getRecordTotal();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = "";

        for (int i = 1; i <= sheetCount; i++) {
            requestBean.setCurrPage(i);
            sheetNameTmp = sheetName + "_第" + (i) + "页";
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, duibaPointsModifyResponse.getResultList());
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        // 序号、账户名、姓名、调整积分数、调整类型、调整人、创建时间、状态
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("userName", "账户名");
        map.put("trueName", "姓名");
        map.put("points", "调整积分数");
        map.put("pointsTypeStr", "调整类型");
        map.put("modifyName", "调整人");
        map.put("createTime", "创建时间");
        map.put("statusStr", "状态");
        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter createTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer createTime = (Integer) object;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if(createTime != null){
                    Date data = new Date(createTime*1000L);
                    return sdf.format(data);
                }else {
                    return "";
                }
            }
        };
        return mapAdapter;
    }
}
