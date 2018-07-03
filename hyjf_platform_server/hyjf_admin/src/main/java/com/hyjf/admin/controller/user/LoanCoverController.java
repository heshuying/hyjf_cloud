package com.hyjf.admin.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.LoanCoverService;
import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.user.LoanCoverUserRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.user.LoanCoverUserVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserCenterController, v0.1 2018/6/19 15:08
 */
@Api(value = "借款盖章用户接口")
@RestController
@RequestMapping("/hyjf-admin/usersLoancover")
public class LoanCoverController extends BaseController {

    @Autowired
    private LoanCoverService loanCoverService;
    private static Logger logger = LoggerFactory.getLogger(LoanCoverController.class);


    /**
     * 列表维护画面初始化
     *
     * @return
     */
    @ApiOperation(value = "借款盖章用户", notes = "获取借款盖章用户列表")
    @PostMapping(value = "/selectLoancoverList")
    @ResponseBody
    public JSONObject selectLoancoverList(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) {
        LoanCoverUserRequest loanCoverUserRequest = serParamRequest(map);
        JSONObject jsonObject = new JSONObject();
        List<LoanCoverUserVO> loanCoverUserVOList = loanCoverService.selectUserMemberList(loanCoverUserRequest);
        String status = Response.FAIL;
        if (null != loanCoverUserVOList && loanCoverUserVOList.size() > 0) {
            jsonObject.put("record", loanCoverUserVOList);
            status = Response.SUCCESS;
        }
        jsonObject.put("status", status);
        return jsonObject;
    }

    private LoanCoverUserRequest serParamRequest(Map<String, Object> mapParam) {
        LoanCoverUserRequest request = new LoanCoverUserRequest();
        if (null != mapParam && mapParam.size() > 0) {

            if (mapParam.containsKey("name")) {
                request.setName(mapParam.get("name").toString());
            }

            if (mapParam.containsKey("idNo")) {
                request.setIdNo(mapParam.get("idNo").toString());
            }
            if (mapParam.containsKey("mobile")) {
                request.setMobile(mapParam.get("mobile").toString());
            }
            if (mapParam.containsKey("code")) {
                request.setCode(mapParam.get("code").toString());
            }
            if (mapParam.containsKey("customerId")) {
                request.setCustomerId(mapParam.get("customerId").toString());
            }
            if (mapParam.containsKey("idType")) {
                request.setIdType(Integer.parseInt(mapParam.get("idType").toString()));
            }
            if (mapParam.containsKey("endCreate")) {
                request.setEndCreate(mapParam.get("endCreate").toString());
            }
            if (mapParam.containsKey("startCreate")) {
                request.setStartCreate(mapParam.get("startCreate").toString());
            }

            /*if (mapParam.containsKey("limit") && StringUtils.isNotBlank(mapParam.get("limit").toString())) {
                request.setLimit((Integer) mapParam.get("limit"));
            }*/
            /*if (mapParam.containsKey("limitEnd")&& StringUtils.isNotBlank(mapParam.get("limitEnd").toString())) {
                request.setLimitEnd((Integer)mapParam.get("limitEnd"));
            }*/
        }
        return request;
    }

    /**
     * 添加借款盖章用户
     *
     * @return
     */
    @ApiOperation(value = "借款盖章用户", notes = "添加借款盖章用户")
    @PostMapping(value = "/insertLoancover")
    @ResponseBody
    public JSONObject insertLoancover(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) {
        JSONObject result = new JSONObject();
        String msg = "";
        LoanCoverUserRequest loanCoverUserRequest = serParamRequest(map);
        //获取登录用户Id
        AdminSystemVO adminSystemVO = this.getUser(request);
        loanCoverUserRequest.setCreateUserId(Integer.parseInt(adminSystemVO.getId()));
        loanCoverUserRequest.setCreateUserName(adminSystemVO.getUsername());
        //获取登录用户名
        // 调用校验
        if (!validatorParm(loanCoverUserRequest)) {
            // 失败返回
            result.put("status", Response.FAIL);
            result.put("msg", "参数错误");
            return result;
        }
        if (!loanCoverService.selectIsExistsRecordByIdNo(loanCoverUserRequest.getIdNo())) {
            result.put("status", Response.FAIL);
            result.put("msg", "该输入统一社会信用代码或身份证已存在");
            return result;
        }
        loanCoverUserRequest.setCreateTime(new Date());
        loanCoverService.insertLoanCoverUser(loanCoverUserRequest);
//        if(){}
        result.put("status", Response.SUCCESS);
        return result;
    }

    private boolean validatorParm(LoanCoverUserRequest form) {
        if (Validator.isNull(form.getName())) {
            return false;
        }
        if (form.getName().length() > 50) {
            return false;
        }
        return true;
    }

    /**
     * 添加借款盖章用户
     *
     * @return
     */
    @ApiOperation(value = "借款盖章用户", notes = "修改借款盖章用户")
    @PostMapping(value = "/updateLoancover")
    @ResponseBody
    public JSONObject updateLoancover(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) {
        JSONObject result = new JSONObject();
        result = loanCoverService.updateLoanCoverUser(map);
        return result;
    }

    /*  *
       * 导出功能
       *
       * @param request
       * @param modelAndView
       * @param form*/
    @ApiOperation(value = "借款盖章用户", notes = "导出借款盖章用户")
    @PostMapping(value = "/exportLoancover")
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) throws Exception {
        LoanCoverUserRequest loanCoverUserRequest = serParamRequest(map);
        List<LoanCoverUserVO> loanCoverUserVOList = loanCoverService.selectUserMemberList(loanCoverUserRequest);
        // 表格sheet名称
        String sheetName = "借款盖章用户查询";
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date())
                + CustomConstants.EXCEL_EXT;
        String[] titles = new String[]{"序号", "手机号", "名称", "证件号", "用户类型", "邮箱", "客户编号", "状态", "状态码", "添加时间", "申请时间"};
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (loanCoverUserVOList != null && loanCoverUserVOList.size() > 0) {
            int sheetCount = 1;
            int rowNum = 0;
            for (int i = 0; i < loanCoverUserVOList.size(); i++) {
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
                    LoanCoverUserVO pInfo = loanCoverUserVOList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {
                        cell.setCellValue(pInfo.getMobile());
                    } else if (celLength == 2) {
                        cell.setCellValue(pInfo.getName());
                    } else if (celLength == 3) {
                        cell.setCellValue(pInfo.getIdNo());
                    } else if (celLength == 4) {
                        if (pInfo.getIdType() == 0) {
                            cell.setCellValue("个人");
                        } else {
                            cell.setCellValue("企业");
                        }
                    } else if (celLength == 5) {
                        cell.setCellValue(pInfo.getEmail());
                    } else if (celLength == 6) {
                        cell.setCellValue(pInfo.getCustomerId());
                    } else if (celLength == 7) {
                        if (pInfo.getStatus().equals("success")) {
                            cell.setCellValue("成功");
                        } else if (pInfo.getStatus().equals("error")) {
                            cell.setCellValue("失败");
                        } else {
                            cell.setCellValue(pInfo.getStatus());
                        }
                    } else if (celLength == 8) {
                        cell.setCellValue(pInfo.getCode());
                    } else if (celLength == 9) {
                        Date dateCreateTime = pInfo.getCreateTime();
                        String strCreateTime = format.format(dateCreateTime);
                        cell.setCellValue(strCreateTime);
                    } else if (celLength == 10) {
                        if (pInfo.getUpdateTime() != null) {
                            Date dateUpdateTime = pInfo.getUpdateTime();
                            String strUpdateTime = format.format(dateUpdateTime);
                            cell.setCellValue(strUpdateTime);
                        }
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
        logger.info("=============导出借款盖章用户完成=============");
    }
}
