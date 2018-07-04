/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.vip;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.VipManageService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.VipDetailListResponse;
import com.hyjf.am.response.admin.VipManageResponse;
import com.hyjf.am.resquest.admin.VipDetailListRequest;
import com.hyjf.am.resquest.admin.VipManageRequest;
import com.hyjf.am.vo.admin.VipDetailListVO;
import com.hyjf.am.vo.admin.VipManageVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 * @version VIPManageController, v0.1 2018/7/2 14:49
 */
@Api(value = "vip管理接口")
@RestController
@RequestMapping("/hyjf-admin/vipManage")
public class VipManageController extends BaseController {

    @Autowired
    private VipManageService vipManageService;

    @ApiOperation(value = "vip管理", notes = "vip管理页面初始化")
    @PostMapping("/init")
    public JSONObject vipManageInit() {
        JSONObject jsonObject = vipManageService.initVipManage();
        return jsonObject;
    }

    @ApiOperation(value = "vip管理", notes = "vip管理列表查询")
    @PostMapping("/vipManageList")
    public AdminResult<ListResult<VipManageVO>> searchUser(HttpServletRequest request, @RequestBody VipManageRequest vipManageRequest) {
        VipManageResponse vmr = vipManageService.searchList(vipManageRequest);
        if (vmr == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(vmr)) {
            return new AdminResult<>(FAIL, vmr.getMessage());

        }
        return new AdminResult<ListResult<VipManageVO>>(ListResult.build(vmr.getResultList(), vmr.getCount())) ;
    }

    @ApiOperation(value = "vip管理", notes = "vip详情页面")
    @PostMapping("/vipdetailInit")
    public AdminResult<ListResult<VipDetailListVO>> vipDetailInit(HttpServletRequest request, HttpServletResponse response, @RequestBody String userId) {
        VipDetailListRequest vdr = new VipDetailListRequest();
        vdr.setUserId(userId);
        VipDetailListResponse vdl = vipManageService.searchDetailList(vdr);
        if (vdl == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(vdl)) {
            return new AdminResult<>(FAIL, vdl.getMessage());

        }
        return new AdminResult<ListResult<VipDetailListVO>>(ListResult.build(vdl.getResultList(), vdl.getCount()));
    }


    /**
     * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     * <p>
     * //     * @param request
     * //     * @param response
     *
     * @throws Exception
     */
   /* @RequestMapping("/exportvips")
    public void exportExcel( @RequestBody Map<String, Object> map, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        // 表格sheet名称
        String sheetName = "VIP列表";
        // 文件名称
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date())
                + CustomConstants.EXCEL_EXT;
        //解决IE浏览器导出列表中文乱码问题
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
            // win10 ie edge 浏览器 和其他系统的ie
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
        }
        ;
        // 需要输出的结果列表
        //封装查询条件
        Map<String, Object> userMap = new HashMap<String, Object>();
        String regionName = StringUtils.isNotEmpty(form.getRegionName()) ? form.getRegionName() : null;
        String branchName = StringUtils.isNotEmpty(form.getBranchName()) ? form.getBranchName() : null;
        String departmentName = StringUtils.isNotEmpty(form.getDepartmentName()) ? form.getDepartmentName() : null;
        String userName = StringUtils.isNotEmpty(form.getUserName()) ? form.getUserName() : null;
        String realName = StringUtils.isNotEmpty(form.getRealName()) ? form.getRealName() : null;
        String mobile = StringUtils.isNotEmpty(form.getMobile()) ? form.getMobile() : null;
        String recommendName = StringUtils.isNotEmpty(form.getRecommendName()) ? form.getRecommendName() : null;
        String userRole = StringUtils.isNotEmpty(form.getUserRole()) ? form.getUserRole() : null;
        String userProperty = StringUtils.isNotEmpty(form.getUserProperty()) ? form.getUserProperty() : null;
        String accountStatusStr = StringUtils.isNotEmpty(form.getAccountStatus()) ? form.getAccountStatus() : null;
        String userStatusStr = StringUtils.isNotEmpty(form.getUserStatus()) ? form.getUserStatus() : null;
        String registPlatStr = StringUtils.isNotEmpty(form.getRegistPlat()) ? form.getRegistPlat() : null;
        String is51Str = StringUtils.isNotEmpty(form.getIs51()) ? form.getIs51() : null;
        String regTimeStart = StringUtils.isNotEmpty(form.getRegTimeStart()) ? form.getRegTimeStart() : null;
        String regTimeEnd = StringUtils.isNotEmpty(form.getRegTimeEnd()) ? form.getRegTimeEnd() : null;
        // 部门
        String[] list = new String[] {};
        if (Validator.isNotNull(form.getCombotreeSrch())) {
            if (form.getCombotreeSrch().contains(StringPool.COMMA)) {
                list = form.getCombotreeSrch().split(StringPool.COMMA);
                form.setCombotreeListSrch(list);
            } else {
                list = new String[] { form.getCombotreeSrch() };
                form.setCombotreeListSrch(list);
            }
        }
        String[] combotreeListSrchStr = form.getCombotreeListSrch();
        if (form.getRegTimeStart() != null) {
            userMap.put("regTimeStart", regTimeStart);
        }
        if (form.getRegTimeEnd() != null) {
            userMap.put("regTimeEnd", regTimeEnd);
        }
        userMap.put("regionName", regionName);
        userMap.put("branchName", branchName);
        userMap.put("departmentName", departmentName);
        userMap.put("userName", userName);
        userMap.put("realName", realName);
        userMap.put("mobile", mobile);
        userMap.put("recommendName", recommendName);
        userMap.put("userRole", userRole);
        userMap.put("userProperty", userProperty);
        userMap.put("accountStatus", accountStatusStr);
        userMap.put("userStatus", userStatusStr);
        userMap.put("registPlat", registPlatStr);
        userMap.put("is51", is51Str);
        userMap.put("combotreeListSrch", combotreeListSrchStr);
        List<VipManageVO> recordList = this.vipManageService.getRecordList(userMap, -1, -1);
        String[] titles = new String[] { "序号", "分公司", "分部", "团队", "用户名", "姓名", "手机号码","VIP等级","V值","VIP购买时间", "用户角色", "用户属性", "推荐人", "51老用户",
                "用户状态", "开户状态","会员开通渠道", "注册平台", "注册时间" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (recordList != null && recordList.size() > 0) {

            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < recordList.size(); i++) {
                rowNum++;
                if (i != 0 && i % 60000 == 0) {
                    sheetCount++;
                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles,
                            (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }

                // 新建一行
                Row row = sheet.createRow(rowNum);
                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                    VipManageVO user = recordList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) { // 大区
                        cell.setCellValue(user.getRegionName());
                    } else if (celLength == 2) { // 分公司
                        cell.setCellValue(user.getBranchName());
                    } else if (celLength == 3) { // 团队
                        cell.setCellValue(user.getDepartmentName());
                    } else if (celLength == 4) {// 用户名
                        cell.setCellValue(user.getUserName());
                    } else if (celLength == 5) {// 姓名
                        cell.setCellValue(user.getRealName());
                    } else if (celLength == 6) {// 手机号码
                        cell.setCellValue(user.getMobile());
                    } else if (celLength == 7) {// VIP等级名称
                        cell.setCellValue(user.getVipName());
                    } else if (celLength == 8) {// 累计V值
                        cell.setCellValue(user.getVipValue());
                    } else if (celLength == 9) {// VIP购买时间
                        cell.setCellValue(user.getVipAddTime());
                    } else if (celLength == 10) {// 用户角色
                        cell.setCellValue(user.getUserRole());
                    } else if (celLength == 11) {// 用户属性
                        cell.setCellValue(user.getUserProperty());
                    } else if (celLength == 12) {// 推荐人
                        cell.setCellValue(user.getRecommendName());
                    } else if (celLength == 13) {// 51老用户
                        cell.setCellValue(user.getIs51());
                    } else if (celLength == 14) {// 用户状态
                        cell.setCellValue(user.getUserStatus());
                    } else if (celLength == 15) {// 开户状态
                        cell.setCellValue(user.getAccountStatus());
                    }else if (celLength == 16) {// 会员开通渠道
                        cell.setCellValue(user.getVipPlatform());
                    } else if (celLength == 17) {// 注册平台
                        cell.setCellValue(user.getRegistPlat());
                    } else if (celLength == 18) {// 注册时间
                        cell.setCellValue(user.getRegTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }*/
    private VipManageRequest setParamRequest(Map<String, Object> map) {
        VipManageRequest request = new VipManageRequest();
        if (null != map && map.size() > 0) {
            if (map.containsKey("regTimeStart")) {
                request.setRegTimeStart(map.get("regTimeStart").toString());
            }
            if (map.containsKey("regTimeEnd")) {
                request.setRegTimeEnd(map.get("regTimeEnd").toString());
            }
            if (map.containsKey("userName")) {
                request.setUserName(map.get("userName").toString());
            }
            if (map.containsKey("realName")) {
                request.setRealName(map.get("realName").toString());
            }
            if (map.containsKey("mobile")) {
                request.setMobile(map.get("mobile").toString());
            }
            if (map.containsKey("recommendName")) {
                request.setRecommendName(map.get("recommendName").toString());
            }
            if (map.containsKey("combotreeListSrch")) {
                request.setCombotreeListSrch(map.get("combotreeListSrch").toString());
            }
            if (map.containsKey("userRole")) {
                request.setUserRole(map.get("userRole").toString());
            }
            if (map.containsKey("userProperty")) {
                request.setUserProperty(map.get("userProperty").toString());
            }
            if (map.containsKey("userStatus")) {
                request.setUserStatus(map.get("userStatus").toString());
            }
            if (map.containsKey("is51")) {
                request.setIs51(map.get("is51").toString());
            }
            if (map.containsKey("limit") && StringUtils.isNotBlank(map.get("limit").toString())) {
                request.setLimit((Integer) map.get("limit"));
            }
        }
        return request;
    }
}
