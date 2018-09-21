/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.vip;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BankAccountManageService;
import com.hyjf.admin.service.VipManageService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.VipDetailListResponse;
import com.hyjf.am.response.admin.VipManageResponse;
import com.hyjf.am.response.admin.VipUpdateGradeListResponse;
import com.hyjf.am.resquest.admin.VipDetailListRequest;
import com.hyjf.am.resquest.admin.VipManageRequest;
import com.hyjf.am.resquest.admin.VipUpdateGradeListRequest;
import com.hyjf.am.vo.admin.OADepartmentCustomizeVO;
import com.hyjf.am.vo.admin.VipDetailListVO;
import com.hyjf.am.vo.admin.VipManageVO;
import com.hyjf.am.vo.admin.VipUpdateGradeListVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author yaoyong
 * @version VIPManageController, v0.1 2018/7/2 14:49
 */
@Api(tags = "VIP中心-VIP管理")
@RestController
@RequestMapping("/hyjf-admin/vipmanage")
public class VipManageController extends BaseController {

    /** 查看权限 */
    public static final String PERMISSIONS = "vipmanage";

    @Autowired
    private VipManageService vipManageService;
    @Autowired
    private BankAccountManageService bankAccountManageService;

    @ApiOperation(value = "页面初始化", notes = "页面初始化")
    @PostMapping("/vipManageList")
    @AuthorityAnnotation(key = PERMISSIONS,value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult searchUser(HttpServletRequest request, @RequestBody VipManageRequest vipManageRequest) {
        VipManageResponse response = vipManageService.searchList(vipManageRequest);
        // 用户角色
        List<ParamNameVO> userRoles = this.vipManageService.getParamNameList("USER_ROLE");
        // 用户属性
        List<ParamNameVO> userPropertys = this.vipManageService.getParamNameList("USER_PROPERTY");
        // 开户状态
        List<ParamNameVO> accountStatus = this.vipManageService.getParamNameList("ACCOUNT_STATUS");
        // 用户状态
        List<ParamNameVO> userStatus = this.vipManageService.getParamNameList("USER_STATUS");
        // 注册平台
        List<ParamNameVO> registPlat = this.vipManageService.getParamNameList("CLIENT");
        //部门
        String[] list = new String[]{};

        JSONArray ja = this.bankAccountManageService.getCrmDepartmentList(list);
        JSONObject ret = new JSONObject();
        if (ja != null) {
            //在部门树中加入 0=部门（其他）,因为前端不能显示id=0,就在后台将0=其他转换为-10086=其他
            JSONObject jo = new JSONObject();
            jo.put("value", "-10086");
            jo.put("title", "其他");
            JSONArray array = new JSONArray();
            jo.put("key", UUID.randomUUID());
            jo.put("children", array);
            ja.add(jo);
            ret = new JSONObject();
            ret.put("data", ja);
        }
        response.setUserRoles(userRoles);
        response.setUserPropertys(userPropertys);
        response.setAccountStatus(accountStatus);
        response.setUserStatus(userStatus);
        response.setRegistPlat(registPlat);
        response.setDepartmentList(ret);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "VIP详情页面", notes = "VIP详情页面")
    @RequestMapping (value = "/vipdetailInit/{userId}",method = RequestMethod.GET)
    @AuthorityAnnotation(key = PERMISSIONS,value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult vipDetailInit(@PathVariable String userId) {
        VipDetailListRequest vdr = new VipDetailListRequest();
        vdr.setUserId(userId);
        VipDetailListResponse vdl = vipManageService.searchDetailList(vdr);
        if (!CollectionUtils.isEmpty(vdl.getResultList())) {
            List<VipDetailListVO> vipDetailListVOS = vdl.getResultList();
            for (VipDetailListVO vipDetailList : vipDetailListVOS) {
                String nid = vipDetailList.getTenderNid();
                BorrowTenderVO borrowTenderVO = vipManageService.getBorrowTenderList(nid);
                vipDetailList.setAccount(borrowTenderVO.getAccount().toPlainString());
            }
        }
        if (vdl == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(vdl)) {
            return new AdminResult<>(FAIL, vdl.getMessage());

        }
        return new AdminResult<>(vdl);
    }

    @ApiOperation(value = "VIP升级详情页面", notes = "VIP升级详情页面")
    @RequestMapping(value = "/vipupgradeInit/{userId}",method = RequestMethod.GET)
    @AuthorityAnnotation(key = PERMISSIONS,value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult vipUpdateGradeInit(@PathVariable String userId) {
        VipUpdateGradeListRequest vgl = new VipUpdateGradeListRequest();
        vgl.setUserId(userId);
        VipUpdateGradeListResponse vgr = vipManageService.searchUpdateGradeList(vgl);
        if (vgr == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(vgr)) {
            return new AdminResult<>(FAIL, vgr.getMessage());
        }
        return new AdminResult<>(vgr);
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
    @ApiOperation(value = "导出",notes = "导出")
    @RequestMapping(value = "/exportVips",method = RequestMethod.POST)
    public void exportExcel( VipManageRequest vipManageRequest, HttpServletRequest request,
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
        List<VipManageVO> recordList = new ArrayList<>();
        VipManageResponse vipManageResponse = vipManageService.searchList(vipManageRequest);
        if (vipManageResponse != null) {
            recordList = vipManageResponse.getResultList();
        }
        String[] titles = new String[] { "序号", "分公司", "分部", "团队", "用户名", "姓名", "手机号码","VIP等级","V值","VIP购买时间", "用户角色", "用户属性", "推荐人",
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
    }
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
            if (map.containsKey("limit") && StringUtils.isNotBlank(map.get("limit").toString())) {
                request.setLimit((Integer) map.get("limit"));
            }
        }
        return request;
    }
}
